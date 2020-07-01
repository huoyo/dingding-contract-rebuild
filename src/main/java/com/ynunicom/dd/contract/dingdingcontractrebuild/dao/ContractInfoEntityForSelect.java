package com.ynunicom.dd.contract.dingdingcontractrebuild.dao;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: jinye.Bai
 * @date: 2020/7/1 17:21
 */
@Data
@TableName("CONTRACT_INFO")
public class ContractInfoEntityForSelect extends BaseModel{

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

        @TableField("theirQualityDingPanId")
        private String theirQualityDingPanId;

        @TableField("theirQualityFilePath")
        private String theirQualityFilePath;

        @TableField("standTemplateFileId")
        private String standTemplateFileId;

        @TableField("useageOfStandTemplate")
        private String useageOfStandTemplate;

        @TableField("standTemplateDingPanId")
        private String standTemplateDingPanId;

        @TableField("reasonOfNotUsingStandTemplateDingPanId")
        private String reasonOfNotUsingStandTemplateDingPanId;

        @TableField("reasonOfNotUsingStandTemplateFilePath")
        private String reasonOfNotUsingStandTemplateFilePath;

        @TableField("contractTextFilePath")
        private String contractTextFilePath;

        @TableField("contractTextDingPanId")
        private String contractTextDingPanId;

        @TableField("attachmentDingPanid1")
        private String attachmentDingPanid1;

        @TableField("attachmentFilePath1")
        private String attachmentFilePath1;

        @TableField("attachmentDingPanid2")
        private String attachmentDingPanid2;

        @TableField("attachmentFilePath2")
        private String attachmentFilePath2;

        @TableField("attachmentDingPanid3")
        private String attachmentDingPanid3;

        @TableField("attachmentFilePath3")
        private String attachmentFilePath3;

        @TableField("theWayTheyChoice")
        private String theWayTheyChoice;

        @TableField("theWayToPay")
        private String theWayToPay;

        @TableField("moneyWithOutTax")
        private String moneyWithOutTax;

        @TableField("taxRate")
        private String taxRate;

        @TableField("moneyOfTax")
        private String moneyOfTax;

        @TableField("moneyWithTax")
        private String moneyWithTax;

        @TableField("stratTime")
        private Date stratTime;

        @TableField("endTime")
        private Date endTime;

        @TableField("organizers")
        private String organizers;

        @TableField("organizerUserId")
        private  String organizerUserId;

        @TableField("organizerName")
        private  String organizerName;

        @TableField("contactPhone")
        private  String contactPhone;

        @TableField("reviewerList")
        private String reviewerList;

        @TableField("finalReviewerList")
        private String finalReviewerList;

        @TableField("sign")
        private int sign;

        @TableField("signName")
        private String signName;

        @TableField("save")
        private int save;

        @TableField("saveName")
        private String saveName;

        @TableField("statu")
        private String statu;
}
