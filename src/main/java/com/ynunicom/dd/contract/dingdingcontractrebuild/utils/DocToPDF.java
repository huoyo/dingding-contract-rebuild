package com.ynunicom.dd.contract.dingdingcontractrebuild.utils;

import com.aspose.words.Document;
import com.aspose.words.License;
import com.aspose.words.SaveFormat;
import com.ynunicom.dd.contract.dingdingcontractrebuild.exception.BussException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * @author: jinye.Bai
 * @date: 2020/6/28 13:38
 */
@Slf4j
public class DocToPDF {

    private boolean getLicense(){
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("WEB-INF/classes/License.xml");
        License license = new License();
        try {
            license.setLicense(inputStream);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static String trans(String docFileName, String filePath) throws Exception {
        File docFile = new File(filePath+"/"+docFileName);
        if (!docFile.exists()){
            throw new BussException(docFileName+"文件不存在，无法转换");
        }
        String[] pdfFileNames = docFileName.split("\\.");
        StringBuilder pdfFileNameBuilder = new StringBuilder();
        for (int i = 0;i<pdfFileNames.length-1;i++) {
            pdfFileNameBuilder.append(pdfFileNames[i]);
        }
        pdfFileNameBuilder.append(".pdf");
        String pdfFileName = pdfFileNameBuilder.toString();
        File path = new File("/pdf");
        if (!path.exists()){
            if (!path.mkdir()){
                throw new BussException("pdf文件夹创建失败");
            }
        }
        if (new File(path+"/"+pdfFileName).exists()){
            return pdfFileName.toString();
        }
        if (!new DocToPDF().getLicense()){
            throw new BussException("凭证文件获取失败");
        }
        Long before = System.currentTimeMillis();
        Document document = new Document(filePath+"/"+docFileName);
        File pdfFile = new File("/pdf/"+pdfFileName);
        FileOutputStream fileOutputStream = new FileOutputStream(pdfFile);
        document.save(fileOutputStream, SaveFormat.PDF);
        fileOutputStream.close();
        log.info("文件"+docFileName+"转换为文件"+pdfFileName+"完成，耗时"+String.valueOf((System.currentTimeMillis()-before)/1000)+"秒");
        return pdfFileName.toString();
    }
}
