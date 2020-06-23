package com.ynunicom.dd.contract.dingdingcontractrebuild.dto.requestBody;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author: jinye.Bai
 * @date: 2020/6/17 16:25
 */
@Data
public class ContractApplyRequestBody {

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

    @NotBlank
    private String standTemplateId;

    @NotBlank
    private String useageOfStandTemplate;

    @NotNull
    private MultipartFile reasonOfNotUsingStandTemplate;

    @NotBlank
    private String contractText;

    @NotNull
    private MultipartFile attachment;

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
}
