package com.ynunicom.dd.contract.dingdingcontractrebuild.dao;

import com.baomidou.mybatisplus.annotation.*;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import com.ynunicom.dd.contract.dingdingcontractrebuild.dto.requestBody.ContractApplyRequestBody;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: jinye.Bai
 * @date: 2020/6/18 9:56
 */
@NoArgsConstructor
@Data
@TableName(value = "CONTRACT_INFO")
@Table(name = "CONTRACT_INFO")
public class ContractInfoEntity extends BaseModel implements Serializable,EntityFatherForMerge{

    @Column(name = "id", type = MySqlTypeConstant.VARCHAR, isNull = false,
            isKey = true, comment = "id")
    @TableId
    private String id;

    @Column(name = "contractNo",type = MySqlTypeConstant.VARCHAR,isNull = false,
            comment = "合同编码")
    @TableField("contractNo")
    private String contractNo;

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

    @Column(name = "standTemplateFilePath",type = MySqlTypeConstant.VARCHAR,
            comment = "标准合同模板储存路径")
    @TableField("standTemplateFilePath")
    private String standTemplateFilePath;

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
            comment = "附件2存储钉盘id")
    @TableField("attachmentDingPanid2")
    private String attachmentDingPanid2;

    @Column(name = "attachmentFilePath2",type = MySqlTypeConstant.VARCHAR,
            comment = "附件2存储路径")
    @TableField("attachmentFilePath2")
    private String attachmentFilePath2;

    @Column(name = "attachmentDingPanid3",type = MySqlTypeConstant.VARCHAR,
            comment = "附件3存储钉盘id")
    @TableField("attachmentDingPanid3")
    private String attachmentDingPanid3;

    @Column(name = "attachmentFilePath3",type = MySqlTypeConstant.VARCHAR,
            comment = "附件3存储路径")
    @TableField("attachmentFilePath3")
    private String attachmentFilePath3;

    @Column(name = "attachmentDingPanid4",type = MySqlTypeConstant.VARCHAR,
            comment = "附件4存储钉盘id")
    @TableField("attachmentDingPanid4")
    private String attachmentDingPanid4;

    @Column(name = "attachmentFilePath4",type = MySqlTypeConstant.VARCHAR,
            comment = "附件4存储路径")
    @TableField("attachmentFilePath4")
    private String attachmentFilePath4;

    @Column(name = "attachmentDingPanid5",type = MySqlTypeConstant.VARCHAR,
            comment = "附件5存储钉盘id")
    @TableField("attachmentDingPanid5")
    private String attachmentDingPanid5;

    @Column(name = "attachmentFilePath5",type = MySqlTypeConstant.VARCHAR,
            comment = "附件5存储路径")
    @TableField("attachmentFilePath5")
    private String attachmentFilePath5;

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

    @Column(name = "deadlineForPerformance",type = MySqlTypeConstant.VARCHAR,isNull = false,
            comment = "合同履行期限,有以下几种情况：" +
                    "一年以内，一至三年，三年以上，完成一定工作为期限，其他，自动延期")
    @TableField("deadlineForPerformance")
    private String deadlineForPerformance;

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

    @Column(name = "sign",type = MySqlTypeConstant.INT,
            comment = "合同管理员签章,1代表已完成")
    @TableField("sign")
    private int sign;

    @Column(name = "signName",type = MySqlTypeConstant.VARCHAR,
            comment = "签章人")
    @TableField("signName")
    private String signName;

    @Column(name = "save",type = MySqlTypeConstant.INT,
            comment = "合同管理员存档,1代表已完成")
    @TableField("save")
    private int save;

    @Column(name = "saveName",type = MySqlTypeConstant.VARCHAR,
            comment = "存档人")
    @TableField("saveName")
    private String saveName;

    //合同在履行时为running
    @Column(name = "statu",type = MySqlTypeConstant.VARCHAR,
            comment = "合同运行状态")
    @TableField("statu")
    private String statu;

    @Column(name = "preContractId",type = MySqlTypeConstant.VARCHAR,
            comment = "修改之前的原合同id")
    @TableField("preContractId")
    private String preContractId;

    @Column(name = "method",type = MySqlTypeConstant.VARCHAR,
            comment = "合同的操作方法")
    @TableField("method")
    private String method;

    @Column(name = "prop",type = MySqlTypeConstant.INT,
            comment = "四大类的哪一种,1代表战略框架合作，2支出类框架协议，3支出类固定金额合同，4收入类合同")
    @TableField("prop")
    private Integer prop;

    @Column(name = "createdtime", type = MySqlTypeConstant.DATETIME, isNull = false,comment = "创建时间")
    @TableField(value = "createdtime",fill = FieldFill.INSERT)
    protected Date createdtime;

    @Column(name = "updatetime", type = MySqlTypeConstant.DATETIME, isNull = false,comment = "更新时间")
    @TableField(value = "updatetime",fill = FieldFill.INSERT_UPDATE)
    protected Date updatetime;

    @Column(name = "preEndComment", type = MySqlTypeConstant.VARCHAR,comment = "终止理由")
    @TableField(value = "preEndComment")
    protected String preEndComment;

    public ContractInfoEntity(ContractApplyRequestBody contractApplyRequestBody){
        this.contractNo = contractApplyRequestBody.getContractNo();
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
        this.deadlineForPerformance = contractApplyRequestBody.getDeadlineForPerformance();
        this.reviewerList = contractApplyRequestBody.getReviewerList();
        this.finalReviewerList = contractApplyRequestBody.getFinalReviewerList();
        this.theWayTheyChoice = contractApplyRequestBody.getTheWayTheyChoice();
        this.theWayToPay = contractApplyRequestBody.getTheWayToPay();
        this.useageOfStandTemplate = contractApplyRequestBody.getUseageOfStandTemplate();
        this.preContractId = contractApplyRequestBody.getPreContractId();
        this.method = contractApplyRequestBody.getMethod();
        this.prop = contractApplyRequestBody.getProp();
        this.preEndComment = contractApplyRequestBody.getPreEndComment();
    }
}
