package com.ynunicom.dd.contract.dingdingcontractrebuild.utils;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiFileUploadChunkRequest;
import com.dingtalk.api.request.OapiFileUploadSingleRequest;
import com.dingtalk.api.request.OapiFileUploadTransactionRequest;
import com.dingtalk.api.response.OapiFileUploadChunkResponse;
import com.dingtalk.api.response.OapiFileUploadSingleResponse;
import com.dingtalk.api.response.OapiFileUploadTransactionResponse;
import com.taobao.api.FileItem;
import com.taobao.api.internal.util.WebUtils;
import com.ynunicom.dd.contract.dingdingcontractrebuild.config.info.AppInfo;
import com.ynunicom.dd.contract.dingdingcontractrebuild.exception.BussException;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.io.RandomAccessFile;

/**
 * @author: jinye.Bai
 * @date: 2020/6/19 15:45
 */
@Component
public class UploadToDingPan {

    @Resource
    AppInfo appInfo;

    @Resource
    String filePath;

    @SneakyThrows
    private long chunk(File file){
        //源文件
        File sourceFile = file;
        //块文件目录
        String chunkFileFolder = filePath;
        //块文件大小
        long chunkFileSize = 1024 * 1024;
        //块数
        long chunkFileNum = (long) Math.ceil(sourceFile.length() * 1.0 /chunkFileSize);
        //创建读文件的对象
        RandomAccessFile raf_read = new RandomAccessFile(sourceFile,"r");
        //缓冲区
        byte[] b = new byte[1024];
        for(int i=0;i<chunkFileNum;i++){
            //块文件
            File chunkFile = new File(chunkFileFolder+file.getName()+i);
            //创建向块文件的写对象
            RandomAccessFile raf_write = new RandomAccessFile(chunkFile,"rw");
            int len = -1;
            while((len = raf_read.read(b))!=-1){
                raf_write.write(b,0,len);
                //如果块文件的大小达到 1M开始写下一块儿
                if(chunkFile.length()>=chunkFileSize){
                    break;
                }
            }
            raf_write.close();
        }
        raf_read.close();
        return chunkFileNum;
    }

    /**
     *
     * @param fileName
     * @param accessToken
     * @return 返回值是media_id,是钉盘的id凭证
     */
    @SneakyThrows
    public String doUpload(String fileName,String accessToken){
        File file  = new File(filePath+'/'+fileName);
        if (file.length()<=0){
            throw new BussException("上传文件为空文件，上传失败");
        }
        if (file.length()<8300000L){
            return singleUpload(file,accessToken);
        }
        else {
            return chunkUpload(file,accessToken);
        }
    }

    @SneakyThrows
    private String singleUpload(File file, String accessToken){
        OapiFileUploadSingleRequest request = new OapiFileUploadSingleRequest();
        request.setFileSize(file.length());
        request.setAgentId(appInfo.getAgentId());
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/file/upload/single?"+ WebUtils.buildQuery(request.getTextParams(),"utf-8"));
        request = new OapiFileUploadSingleRequest();
        request.setFile(new FileItem(file.getPath()));
        OapiFileUploadSingleResponse response = client.execute(request,accessToken);
        if (!response.isSuccess()){
            throw new BussException("文件上传至钉盘失败");
        }
        return response.getMediaId();
    }

    @SneakyThrows
    private String chunkUpload(File file, String accessToken){
        String uploadId;
        long chunkFileNum = chunk(file);
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/file/upload/transaction");
        OapiFileUploadTransactionRequest request = new OapiFileUploadTransactionRequest();
        request.setAgentId(appInfo.getAgentId());
        request.setFileSize(file.length());
        request.setChunkNumbers(chunkFileNum);
        request.setHttpMethod("GET");
        OapiFileUploadTransactionResponse response = client.execute(request,accessToken);
        if (!response.isSuccess()){
            throw new BussException("文件分块上传请求失败");
        }
        uploadId = response.getUploadId();

        for (long i = 0;i<chunkFileNum;i++){
            OapiFileUploadChunkRequest innerRequest = new OapiFileUploadChunkRequest();
            innerRequest.setAgentId(appInfo.getAgentId());
            innerRequest.setChunkSequence(i+1);
            innerRequest.setUploadId(uploadId);
            DingTalkClient innerClient = new DefaultDingTalkClient("https://oapi.dingtalk.com/file/upload/chunk?"+WebUtils.buildQuery(innerRequest.getTextParams(),"utf-8"));
            innerRequest = new OapiFileUploadChunkRequest();
            innerRequest.setFile(new FileItem(filePath+file.getName()+i));
            OapiFileUploadChunkResponse innerResponse = innerClient.execute(innerRequest,accessToken);
            if (!innerResponse.isSuccess()){
                throw new Exception("分块上传失败");
            }
        }

        DingTalkClient finalClient = new DefaultDingTalkClient("https://oapi.dingtalk.com/file/upload/transaction");
        OapiFileUploadTransactionRequest finalRequest = new OapiFileUploadTransactionRequest();
        finalRequest.setAgentId(appInfo.getAgentId());
        finalRequest.setFileSize(file.length());
        finalRequest.setChunkNumbers(chunkFileNum);
        finalRequest.setUploadId(uploadId);
        finalRequest.setHttpMethod("GET");
        OapiFileUploadTransactionResponse finalResponse = finalClient.execute(finalRequest,accessToken);
        if (!finalResponse.isSuccess()){
            throw new Exception("分块上传确认失败");
        }
        return finalResponse.getMediaId();
    }

}
