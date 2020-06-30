package com.ynunicom.dd.contract.dingdingcontractrebuild.dao;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import com.ynunicom.dd.contract.dingdingcontractrebuild.dto.requestBody.ContractApplyRequestBody;
import lombok.Data;
import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: jinye.Bai
 * @date: 2020/6/18 9:56
 */
@Data
@TableName("CONTRACT_INFO")
@Table(name = "CONTRACT_INFO")
public class ContractInfoEntity extends BaseModel implements Serializable {

    @Column(name = "id", type = MySqlTypeConstant.VARCHAR, isNull = false,
            isKey = true, comment = "id")
    @TableId
    private String id;

    @Column(name = "contractName",type = MySqlTypeConstant.VARCHAR,isNull = false,
            comment = "合同名称")
    @TableField("contractName")
    private String contractName;

    @Column(name = "contractType",type = MySqlTypeConstant.VARCHAR,isNull = false,
            comment = "合同类型")
    @TableField("contractType")
    private String contractType;

    @Column(name = "ourSideEntity",type = MySqlTypeConstant.VARCHAR,isNull = false,
            comment = "我方主体")
    @TableField("ourSideEntity")
    private String ourSideEntity;

    @Column(name = "contractRunnerUserId",type = MySqlTypeConstant.VARCHAR,isNull = false,
            comment = "合同履行人钉钉userId")
    @TableField("contractRunnerUserId")
    private String contractRunnerUserId;

    @Column(name = "contractRunnerName",type = MySqlTypeConstant.VARCHAR,isNull = false,
            comment = "合同履行人姓名")
    @TableField("contractRunnerName")
    private String contractRunnerName;

    @Column(name = "contractRunningComment",type = MySqlTypeConstant.VARCHAR,
            comment = "合同履行人评论")
    @TableField("contractRunningComment")
    private String contractRunningComment;

    @Column(name = "theirEntityName",type = MySqlTypeConstant.VARCHAR,isNull = false,
            comment = "对方主体全称")
    @TableField("theirEntityName")
    private String theirEntityName;

    @Column(name = "theirQualityDingPanId",type = MySqlTypeConstant.VARCHAR,isNull = false,
            comment = "对方资质储存钉盘id")
    @TableField("theirQualityDingPanId")
    private String theirQualityDingPanId;

    @Column(name = "theirQualityFilePath",type = MySqlTypeConstant.VARCHAR,isNull = false,
            comment = "对方资质储存路径")
    @TableField("theirQualityFilePath")
    private String theirQualityFilePath;

    @Column(name = "standTemplateFileId",type = MySqlTypeConstant.VARCHAR,
            comment = "标准合同模板Id")
    @TableField("standTemplateFileId")
    private String standTemplateFileId;

    @Column(name = "useageOfStandTemplate",type = MySqlTypeConstant.VARCHAR,
            comment = "标准合同模板使用情况")
    @TableField("useageOfStandTemplate")
    private String useageOfStandTemplate;

    @Column(name = "standTemplateDingPanId",type = MySqlTypeConstant.VARCHAR,
            comment = "标准合同模板钉盘Id")
    @TableField("standTemplateDingPanId")
    private String standTemplateDingPanId;

    @Column(name = "reasonOfNotUsingStandTemplateDingPanId",type = MySqlTypeConstant.VARCHAR,
            comment = "标准合同模板未使用的原因说明文件存储钉盘id")
    @TableField("reasonOfNotUsingStandTemplateDingPanId")
    private String reasonOfNotUsingStandTemplateDingPanId;

    @Column(name = "reasonOfNotUsingStandTemplateFilePath",type = MySqlTypeConstant.VARCHAR,
            comment = "标准合同模板未使用的原因说明文件存储路径")
    @TableField("reasonOfNotUsingStandTemplateFilePath")
    private String reasonOfNotUsingStandTemplateFilePath;

    @Column(name = "contractTextFilePath",type = MySqlTypeConstant.VARCHAR,isNull = false,
            comment = "合同正文储存路径")
    @TableField("contractTextFilePath")
    private String contractTextFilePath;

    @Column(name = "contractTextDingPanId",type = MySqlTypeConstant.VARCHAR,isNull = false,
            comment = "合同正文钉盘id")
    @TableField("contractTextDingPanId")
    private String contractTextDingPanId;

    @Column(name = "attachmentDingPanid1",type = MySqlTypeConstant.VARCHAR,
            comment = "附件1存储钉盘id")
    @TableField("attachmentDingPanid1")
    private String attachmentDingPanid1;

    @Column(name = "attachmentFilePath1",type = MySqlTypeConstant.VARCHAR,
            comment = "附件1存储路径")
    @TableField("attachmentFilePath1")
    private String attachmentFilePath1;

    @Column(name = "attachmentDingPanid2",type = MySqlTypeConstant.VARCHAR,
            comment = "附件1存储钉盘id")
    @TableField("attachmentDingPanid2")
    private String attachmentDingPanid2;

    @Column(name = "attachmentFilePath2",type = MySqlTypeConstant.VARCHAR,
            comment = "附件1存储路径")
    @TableField("attachmentFilePath2")
    private String attachmentFilePath2;

    @Column(name = "attachmentDingPanid3",type = MySqlTypeConstant.VARCHAR,
            comment = "附件1存储钉盘id")
    @TableField("attachmentDingPanid3")
    private String attachmentDingPanid3;

    @Column(name = "attachmentFilePath3",type = MySqlTypeConstant.VARCHAR,
            comment = "附件1存储路径")
    @TableField("attachmentFilePath3")
    private String attachmentFilePath3;

    @Column(name = "theWayTheyChoice",type = MySqlTypeConstant.VARCHAR,isNull = false,
            comment = "对方选择方式")
    @TableField("theWayTheyChoice")
    private String theWayTheyChoice;

    @Column(name = "theWayToPay",type = MySqlTypeConstant.VARCHAR,isNull = false,
            comment = "收付款方式")
    @TableField("theWayToPay")
    private String theWayToPay;

    @Column(name = "moneyWithOutTax",type = MySqlTypeConstant.VARCHAR,isNull = false,
            comment = "不含增值税合同额")
    @TableField("moneyWithOutTax")
    private String moneyWithOutTax;

    @Column(name = "taxRate",type = MySqlTypeConstant.VARCHAR,isNull = false,
            comment = "增值税税率")
    @TableField("taxRate")
    private String taxRate;

    @Column(name = "moneyOfTax",type = MySqlTypeConstant.VARCHAR,isNull = false,
            comment = "增值税税额")
    @TableField("moneyOfTax")
    private String moneyOfTax;

    @Column(name = "moneyWithTax",type = MySqlTypeConstant.VARCHAR,isNull = false,
            comment = "含增值税合同金额")
    @TableField("moneyWithTax")
    private String moneyWithTax;

    @Column(name = "stratTime",type = MySqlTypeConstant.DATETIME,isNull = false,
            comment = "合同开始时间")
    @TableField("stratTime")
    private Date stratTime;

    @Column(name = "endTime",type = MySqlTypeConstant.DATETIME,isNull = false,
            comment = "合同履行期限")
    @TableField("endTime")
    private Date endTime;

    @Column(name = "organizers",type = MySqlTypeConstant.VARCHAR,isNull = false,
            comment = "承办部门")
    @TableField("organizers")
    private String organizers;

    @Column(name = "organizerUserId",type = MySqlTypeConstant.VARCHAR,isNull = false,
            comment = "承办人钉钉userId")
    @TableField("organizerUserId")
    private  String organizerUserId;

    @Column(name = "organizerName",type = MySqlTypeConstant.VARCHAR,isNull = false,
            comment = "承办人姓名")
    @TableField("organizerName")
    private  String organizerName;

    @Column(name = "contactPhone",type = MySqlTypeConstant.VARCHAR,isNull = false,
            comment = "联系电话")
    @TableField("contactPhone")
    private  String contactPhone;

    @Column(name = "reviewerList",type = MySqlTypeConstant.VARCHAR,isNull = false,
            comment = "审批人userId列表")
    @TableField("reviewerList")
    private String reviewerList;

    @Column(name = "finalReviewerList",type = MySqlTypeConstant.VARCHAR,isNull = false,
            comment = "最终审批人userId")
    @TableField("finalReviewerList")
    private String finalReviewerList;

    @Column(name = "sign",type = MySqlTypeConstant.VARCHAR,
            comment = "合同管理员签章")
    @TableField("sign")
    private String sign;

    @Column(name = "save",type = MySqlTypeConstant.VARCHAR,
            comment = "合同管理员存档")
    @TableField("save")
    private String save;

    //合同在履行时为running
    @Column(name = "statu",type = MySqlTypeConstant.VARCHAR,
            comment = "合同运行状态")
    @TableField("statu")
    private String statu;

    public ContractInfoEntity(ContractApplyRequestBody contractApplyRequestBody){
        this.contactPhone = contractApplyRequestBody.getContactPhone();
        this.contractName = contractApplyRequestBody.getContractName();
        this.contractRunnerName = contractApplyRequestBody.getContractRunnerNamer();
        this.contractRunnerUserId = contractApplyRequestBody.getContractRunnerUserId();
        this.contractType = contractApplyRequestBody.getContractType();
        this.moneyOfTax = String.valueOf(contractApplyRequestBody.getMoneyOfTax());
        this.taxRate = String.valueOf(contractApplyRequestBody.getTaxRate());
        this.moneyWithOutTax = String.valueOf(contractApplyRequestBody.getMoneyWithOutTax());
        this.moneyWithTax = String.valueOf(contractApplyRequestBody.getMoneyWithTax());
        this.ourSideEntity = contractApplyRequestBody.getOurSideEntity();
        this.theirEntityName = contractApplyRequestBody.getTheirEntityName();
        this.organizerName = contractApplyRequestBody.getOrganizerName();
        this.organizers = contractApplyRequestBody.getOrganizers();
        this.organizerUserId = contractApplyRequestBody.getOrganizerUserId();
        this.standTemplateFileId = contractApplyRequestBody.getStandTemplateId();
        this.stratTime = new Date(contractApplyRequestBody.getStartTime());
        this.endTime = new Date(contractApplyRequestBody.getEndTime());
        this.reviewerList = contractApplyRequestBody.getReviewerList();
        this.finalReviewerList = contractApplyRequestBody.getFinalReviewerList();
        this.theWayTheyChoice = contractApplyRequestBody.getTheWayTheyChoice();
        this.theWayToPay = contractApplyRequestBody.getTheWayToPay();
        this.useageOfStandTemplate = contractApplyRequestBody.getUseageOfStandTemplate();
    }
}
