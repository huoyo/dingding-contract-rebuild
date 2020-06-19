package com.ynunicom.dd.contract.dingdingcontractrebuild.dao;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import lombok.Data;
import org.joda.time.DateTime;

/**
 * @author: jinye.Bai
 * @date: 2020/6/18 9:56
 */
@Data
@TableName("CONTRACT_INFO")
@Table(name = "CONTRACT_INFO")
public class ContractInfoEntity extends BaseModel{

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

    @Column(name = "reasonOfNotUsingStandTemplateFilePath",type = MySqlTypeConstant.VARCHAR,
            comment = "标准合同模板未使用的原因说明文件存储路径")
    @TableField("reasonOfNotUsingStandTemplateFilePath")
    private String reasonOfNotUsingStandTemplateFilePath;

    @Column(name = "contractText",type = MySqlTypeConstant.VARCHAR,isNull = false,
            comment = "合同正文")
    @TableField("contractText")
    private String contractText;

    @Column(name = "attachmentFilePath",type = MySqlTypeConstant.VARCHAR,
            comment = "附件存储路径")
    @TableField("attachmentFilePath")
    private String attachmentFilePath;

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

    @Column(name = "endTime",type = MySqlTypeConstant.DATETIME,isNull = false,
            comment = "合同履行期限")
    @TableField("endTime")
    private DateTime endTime;

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
            comment = "最终审批人userId列表")
    @TableField("finalReviewerList")
    private String finalReviewerList;
}