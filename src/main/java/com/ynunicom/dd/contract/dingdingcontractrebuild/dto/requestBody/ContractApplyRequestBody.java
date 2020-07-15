package com.ynunicom.dd.contract.dingdingcontractrebuild.dto.requestBody;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author: jinye.Bai
 * @date: 2020/6/17 16:25
 */
@Data
public class ContractApplyRequestBody implements Serializable {

    private String contractName;

    private String contractNo;

    private String contractType;

    private String ourSideEntity;

    private String contractRunnerUserId;

    private String contractRunnerNamer;

    private String theirEntityName;

    private MultipartFile theirQuality;

    private MultipartFile standTemplate;

    private String useageOfStandTemplate;

    private MultipartFile contractText;

    private MultipartFile attachment1;

    private MultipartFile attachment2;

    private MultipartFile attachment3;

    private MultipartFile attachment4;

    private MultipartFile attachment5;

    private String theWayTheyChoice;

    private String theWayToPay;

    private BigDecimal moneyWithOutTax;

    private BigDecimal taxRate;

    private BigDecimal moneyOfTax;

    private BigDecimal moneyWithTax;

    private String deadlineForPerformance;

    private String organizers;

    private  String organizerUserId;

    private String organizerName;

    private  String contactPhone;

    private String reviewerList;

    private String finalReviewerList;

    private String preContractId;

    private String method;

    private String preEndComment;
    //四大类的哪一种,1代表战略框架合作，2支出类框架协议，3支出类固定金额合同，4收入类合同
    @NotNull
    private Integer prop;

    private BigDecimal moneyForConstract;

    private String userDeptId;

    private Integer isSpe;

}
