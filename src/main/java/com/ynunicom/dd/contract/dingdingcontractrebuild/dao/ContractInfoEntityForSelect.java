package com.ynunicom.dd.contract.dingdingcontractrebuild.dao;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import com.ynunicom.dd.contract.dingdingcontractrebuild.dto.requestBody.ContractAlterRequestBody;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: jinye.Bai
 * @date: 2020/7/1 17:21
 */
@Data
@TableName("CONTRACT_INFO")
@NoArgsConstructor
public class ContractInfoEntityForSelect extends BaseModel implements EntityFatherForMerge{

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

        @TableField("deadlineForPerformance")
        private String deadlineForPerformance;

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

        @TableField(value = "createdtime")
        private Date createdtime;

        @TableField(value = "updatetime")
        private Date updatetime;

        public ContractInfoEntityForSelect(ContractAlterRequestBody contractAlterRequestBody){
                this.contactPhone = contractAlterRequestBody.getContactPhone();
                this.contractName = contractAlterRequestBody.getContractName();
                this.contractRunnerName = contractAlterRequestBody.getContractRunnerNamer();
                this.contractRunnerUserId = contractAlterRequestBody.getContractRunnerUserId();
                this.contractType = contractAlterRequestBody.getContractType();
                this.moneyOfTax = String.valueOf(contractAlterRequestBody.getMoneyOfTax());
                this.taxRate = String.valueOf(contractAlterRequestBody.getTaxRate());
                this.moneyWithOutTax = String.valueOf(contractAlterRequestBody.getMoneyWithOutTax());
                this.moneyWithTax = String.valueOf(contractAlterRequestBody.getMoneyWithTax());
                this.ourSideEntity = contractAlterRequestBody.getOurSideEntity();
                this.theirEntityName = contractAlterRequestBody.getTheirEntityName();
                this.organizerName = contractAlterRequestBody.getOrganizerName();
                this.organizers = contractAlterRequestBody.getOrganizers();
                this.organizerUserId = contractAlterRequestBody.getOrganizerUserId();
                this.deadlineForPerformance = contractAlterRequestBody.getDeadlineForPerformance();
                this.reviewerList = contractAlterRequestBody.getReviewerList();
                this.finalReviewerList = contractAlterRequestBody.getFinalReviewerList();
                this.theWayTheyChoice = contractAlterRequestBody.getTheWayTheyChoice();
                this.theWayToPay = contractAlterRequestBody.getTheWayToPay();
                this.useageOfStandTemplate = contractAlterRequestBody.getUseageOfStandTemplate();
        }

        public ContractInfoEntity build(){
                ContractInfoEntity contractInfoEntity = new ContractInfoEntity();
                contractInfoEntity.setContactPhone(this.contactPhone);
                contractInfoEntity.setContractName(this.contractName);
                contractInfoEntity.setContractRunnerName(this.contractRunnerName);
                contractInfoEntity.setContractRunnerUserId(this.contractRunnerUserId);
                contractInfoEntity.setContractType(this.contractType);
                contractInfoEntity.setMoneyOfTax(this.moneyOfTax);
                contractInfoEntity.setTaxRate(this.taxRate);
                contractInfoEntity.setMoneyWithOutTax(this.moneyWithOutTax);
                contractInfoEntity.setMoneyWithTax(this.moneyWithTax);
                contractInfoEntity.setOurSideEntity(this.ourSideEntity);
                contractInfoEntity.setTheirEntityName(this.theirEntityName);
                contractInfoEntity.setOrganizerName(this.organizerName);
                contractInfoEntity.setOrganizers(this.organizers);
                contractInfoEntity.setOrganizerUserId(this.organizerUserId);
                contractInfoEntity.setStandTemplateFileId(this.standTemplateFileId);
                contractInfoEntity.setDeadlineForPerformance(this.deadlineForPerformance);
                contractInfoEntity.setReviewerList(this.reviewerList);
                contractInfoEntity.setFinalReviewerList(this.finalReviewerList);
                contractInfoEntity.setTheWayTheyChoice(this.theWayTheyChoice);
                contractInfoEntity.setTheWayToPay(this.theWayToPay);
                contractInfoEntity.setUseageOfStandTemplate(this.useageOfStandTemplate);
                return contractInfoEntity;
        }
}
