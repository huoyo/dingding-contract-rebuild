package com.ynunicom.dd.contract.dingdingcontractrebuild.dto.requestBody;

import com.ynunicom.dd.contract.dingdingcontractrebuild.dao.ContractTemplateEntity;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @author: jinye.Bai
 * @date: 2020/6/23 11:28
 */
@Data
public class ContractTemplateUploadRequestBody implements Serializable {

    @NotNull
    private MultipartFile file;

    @NotBlank
    private String standTextNo;

    @NotBlank
    private String version;

    @NotBlank
    private String standeTextName;

    @NotBlank
    private String standTextType;

    private String standTextOwnnerCorp;

    private String standTextOwnnerDept;

    private String standTextStatu;

    private String standTextProp;

    private String useageWide;

    @NotBlank
    private long standTextActiveDate;

    @NotBlank
    private long standTextDeactiveDate;

    @NotBlank
    private String standTextCreator;

    private String standTextDisc;


    public ContractTemplateEntity build(){
        ContractTemplateEntity contractTemplateEntity = new ContractTemplateEntity();
        contractTemplateEntity.setStandeTextName(standeTextName);
        contractTemplateEntity.setStandTextActiveDate(new Date(standTextActiveDate));
        contractTemplateEntity.setStandTextCreator(standTextCreator);
        contractTemplateEntity.setStandTextDeactiveDate(new Date(standTextDeactiveDate));
        contractTemplateEntity.setStandTextDisc(standTextDisc);
        contractTemplateEntity.setStandTextNo(standTextNo);
        contractTemplateEntity.setStandTextOwnnerCorp(standTextOwnnerCorp);
        contractTemplateEntity.setStandTextOwnnerDept(standTextOwnnerDept);
        contractTemplateEntity.setStandTextProp(standTextProp);
        contractTemplateEntity.setVersion(version);
        contractTemplateEntity.setStandTextType(standTextType);
        contractTemplateEntity.setUseageWide(useageWide);
        contractTemplateEntity.setStandTextStatu(standTextStatu);
        return contractTemplateEntity;
    }
}
