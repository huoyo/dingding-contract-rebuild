package com.ynunicom.dd.contract.dingdingcontractrebuild.utils;

import com.ynunicom.dd.contract.dingdingcontractrebuild.exception.BussException;
import lombok.SneakyThrows;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.UUID;

/**
 * @author: jinye.Bai
 * @date: 2020/6/19 10:50
 */
public class FileSaver {

    @SneakyThrows
    public static String save(String filePath,MultipartFile file){
        if (file.isEmpty()){
            return null;
        }
        String fileName =  file.getOriginalFilename();
        File temp = new File(filePath);
        if (!temp.exists()){
            if (!temp.mkdir()){
                throw new BussException("创建文件夹失败");
            }
        }
        File outFile = new File(filePath+'/'+fileName);
        if (outFile.exists()){
            String newFileName = filePath+"/"+System.currentTimeMillis()+ fileName;
            outFile = new File(newFileName);
        }
        InputStream inputStream = file.getInputStream();
        FileOutputStream fileOutputStream = new FileOutputStream(outFile);
        byte[] buffer = new byte[1024];
        while (inputStream.read(buffer)!=-1){
            fileOutputStream.write(buffer);
        }
        fileOutputStream.flush();
        fileOutputStream.close();
        inputStream.close();
        return outFile.getName();
    }
}
