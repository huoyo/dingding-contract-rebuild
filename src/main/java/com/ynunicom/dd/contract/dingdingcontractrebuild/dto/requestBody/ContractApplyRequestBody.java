package com.ynunicom.dd.contract.dingdingcontractrebuild.dto.requestBody;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

/**
 * @author: jinye.Bai
 * @date: 2020/6/17 16:25
 */
@Data
public class ContractApplyRequestBody {

    private String contractName;

    private String contractType;

    private String ourSideEntity;

    private String contractRunnerUserId;

    private String theirEntityName;

    private MultipartFile theirQuality;

    private MultipartFile standTemplate;

    private String useageOfStandTemplate;

    private MultipartFile reasonOfNotUsingStandTemplate;

    private String contractText;

    private MultipartFile attachment;

    private String theWayTheyChoice;

    private String theWayToPay;

    private BigDecimal moneyWithOutTax;

    private BigDecimal taxRate;

    private BigDecimal moneyOfTax;

    private BigDecimal moneyWithTax;

    private Long endTime;

    private String organizers;

    private  String organizerUserId;

    private  String contactPhone;

    private String reviewerList;

    private String finalReviewerList;
}
