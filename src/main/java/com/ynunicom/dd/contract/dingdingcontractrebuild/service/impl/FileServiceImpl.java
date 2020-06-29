package com.ynunicom.dd.contract.dingdingcontractrebuild.service.impl;

import com.ynunicom.dd.contract.dingdingcontractrebuild.exception.BussException;
import com.ynunicom.dd.contract.dingdingcontractrebuild.service.FileService;
import com.ynunicom.dd.contract.dingdingcontractrebuild.service.UserInfoService;
import com.ynunicom.dd.contract.dingdingcontractrebuild.utils.DocToPDF;
import com.ynunicom.dd.contract.dingdingcontractrebuild.utils.UserVerify;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

/**
 * @author: jinye.Bai
 * @date: 2020/6/28 16:57
 */
@Service
public class FileServiceImpl implements FileService {

    @Resource
    String filePath;

    @Resource
    UserInfoService userInfoService;

    @SneakyThrows
    private HttpServletResponse get(String accessToken, String fileName, HttpServletResponse httpServletResponse, String userId,boolean fileType){
        if (UserVerify.verify(accessToken,userId,userInfoService)){
            throw new BussException("用户验证失败");
        }
        File file = null;
        if (fileType){
            file = new File(filePath+"/"+fileName);
        }
        else {
            file = new File("/pdf"+"/"+fileName);
        }
        if (!file.exists()){
            throw new BussException(fileName+"文件不存在");
        }
        httpServletResponse.setHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode(fileName,"UTF-8"));
        FileInputStream fileInputStream = new FileInputStream(file);
        OutputStream outputStream = httpServletResponse.getOutputStream();
        byte[] bytes = new byte[1024];
        while(fileInputStream.read(bytes)!=-1){
            outputStream.write(bytes);
        }
        outputStream.flush();
        outputStream.close();
        fileInputStream.close();
        return httpServletResponse;
    }

    private boolean checkHavePdf(String fileName){
        File file = new File("/pdf/"+fileName);
        return file.exists();
    }

    @SneakyThrows
    @Override
    public HttpServletResponse getDoc(String accessToken, String fileName, HttpServletResponse httpServletResponse,String userId) {
        return get(accessToken,fileName,httpServletResponse,userId,true);
    }

    @SneakyThrows
    @Override
    public HttpServletResponse getPdf(String accessToken, String fileName, HttpServletResponse httpServletResponse, String userId) {
        if (checkHavePdf(fileName)){
            return get(accessToken,fileName,httpServletResponse,userId,false);
        }
        String[] docFileNames = fileName.split("\\.");
        StringBuilder docFileNameBuilder = new StringBuilder();
        for (int i = 0;i<docFileNames.length-1;i++) {
            docFileNameBuilder.append(docFileNames[i]);
        }
        StringBuilder tempBuilder = new StringBuilder(docFileNameBuilder);

        docFileNameBuilder.append(".docx");
        String docFileName = docFileNameBuilder.toString();
        String pdfFileName = null;
        try {
            pdfFileName = DocToPDF.trans(docFileName,filePath);
            return  get(accessToken,pdfFileName,httpServletResponse,userId,false);
        }
        catch (BussException e){
            docFileName = tempBuilder.append(".doc").toString();
            pdfFileName = DocToPDF.trans(docFileName,filePath);
            return  get(accessToken,pdfFileName,httpServletResponse,userId,false);
        }
    }

    @SneakyThrows
    @Override
    public HttpServletResponse getOther(String accessToken, String fileName, HttpServletResponse httpServletResponse, String userId) {
        File file = new File(filePath+"/"+fileName);
        if (!file.exists()){
            throw new BussException(fileName+"文件不存在");
        }
        httpServletResponse.setHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode(fileName,"UTF-8"));
        OutputStream outputStream = httpServletResponse.getOutputStream();
        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] bytes = new byte[1024];
        while (fileInputStream.read(bytes)!=-1){
            outputStream.write(bytes);
        }
        outputStream.flush();
        outputStream.close();
        fileInputStream.close();
        return httpServletResponse;
    }
}