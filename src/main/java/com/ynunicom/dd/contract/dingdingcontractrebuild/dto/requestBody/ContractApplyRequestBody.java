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

    @NotBlank
    private String contractName;

    @NotBlank
    private String contractType;

    @NotBlank
    private String ourSideEntity;

    @NotBlank
    private String contractRunnerUserId;

    @NotBlank
    private String contractRunnerNamer;

    @NotBlank
    private String theirEntityName;

    @NotNull
    private MultipartFile theirQuality;

    private MultipartFile standTemplate;

    @NotBlank
    private String useageOfStandTemplate;

    @NotNull
    private MultipartFile contractText;

    private MultipartFile attachment1;

    private MultipartFile attachment2;

    private MultipartFile attachment3;

    @NotBlank
    private String theWayTheyChoice;

    @NotBlank
    private String theWayToPay;

    @NotNull
    private BigDecimal moneyWithOutTax;

    @NotNull
    private BigDecimal taxRate;

    @NotNull
    private BigDecimal moneyOfTax;

    @NotNull
    private BigDecimal moneyWithTax;

    @NotNull
    private Long startTime;

    @NotNull
    private Long endTime;

    @NotBlank
    private String organizers;

    @NotBlank
    private  String organizerUserId;

    @NotBlank
    private String organizerName;

    @NotBlank
    private  String contactPhone;

    @NotBlank
    private String reviewerList;

    @NotBlank
    private String finalReviewerList;

    private String preContractId;

    @NotBlank
    private String method;

    //四大类的哪一种,1代表战略框架合作，2支出类框架协议，3支出类固定金额合同，4收入类合同
    @NotBlank
    private Integer prop;
}
