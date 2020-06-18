package com.ynunicom.dd.contract.dingdingcontractrebuild.dao;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author: jinye.Bai
 * @date: 2020/6/18 9:56
 */
@Data
@TableName("CONTRACT_INFO")
public class ContractInfoEntity extends BaseModel{

    @TableId
    private String id;

    @TableField("contractName")
    private String contractName;

    @TableField("contractType")
    private String contractType;

    @TableField("ourSideEntity")
    private String ourSideEntity;

    @TableField("contractRunnerUserId")
    private String contractRunnerUserId;

    @TableField("contractRunnerName")
    private String contractRunnerName;

    @TableField("contractRunningComment")
    private String contractRunningComment;

    @TableField("theirEntityName")
    private String theirEntityName;

    @TableField("theirQualityFilePath")
    private String theirQualityFilePath;

    @TableField("standTemplateFilePath")
    private String standTemplateFilePath;

    @TableField("useageOfStandTemplate")
    private String useageOfStandTemplate;

    @TableField("reasonOfNotUsingStandTemplateFilePath")
    private String reasonOfNotUsingStandTemplateFilePath;

    @TableField("contractText")
    private String contractText;

    @TableField("attachmentFilePath")
    private String attachmentFilePath;

    @TableField("theWayTheyChoice")
    private String theWayTheyChoice;

    @TableField("theWayToPay")
    private String theWayToPay;

    @TableField("moneyWithOutTax")
    private BigDecimal moneyWithOutTax;

    @TableField("taxRate")
    private BigDecimal taxRate;

    @TableField("moneyOfTax")
    private BigDecimal moneyOfTax;

    @TableField("moneyWithTax")
    private BigDecimal moneyWithTax;

    @TableField("endTime")
    private Long endTime;

    @TableField("organizers")
    private String organizers;

    @TableField("organizerUserId")
    private  String organizerUserId;

    @TableField("contactPhone")
    private  String contactPhone;

    @TableField("reviewerList")
    private String reviewerList;

    @TableField("finalReviewerList")
    private String finalReviewerList;
}
