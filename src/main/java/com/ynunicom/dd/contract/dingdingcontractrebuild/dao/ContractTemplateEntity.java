package com.ynunicom.dd.contract.dingdingcontractrebuild.dao;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import lombok.Data;

import java.util.Date;

/**
 * @author: jinye.Bai
 * @date: 2020/6/19 10:21
 */
@Data
@Table(name = "Contract_Template")
@TableName("Contract_Template")
public class ContractTemplateEntity extends BaseModel {

    @Column(name = "id", type = MySqlTypeConstant.VARCHAR, isNull = false,
            isKey = true, comment = "id")
    @TableId
    private String id;

    @TableField("standTextNo")
    @Column(name = "standTextNo",type = MySqlTypeConstant.VARCHAR, isNull = false,comment = "标准文本编号")
    private String standTextNo;

    @TableField("version")
    @Column(name = "version",type = MySqlTypeConstant.VARCHAR, isNull = false,comment = "版本编号")
    private String version;

    @TableField("filePath")
    @Column(name = "filePath",type = MySqlTypeConstant.VARCHAR, isNull = false,comment = "文件路径,不包含任何一截前置路径")
    private String filePath;

    @TableField("standeTextName")
    @Column(name = "standeTextName",type = MySqlTypeConstant.VARCHAR, isNull = false,comment = "标准文本名称")
    private String standeTextName;

    @Column(name = "standTextType", type = MySqlTypeConstant.VARCHAR, isNull = false,comment = "标准文本类型")
    @TableField("standTextType")
    private String standTextType;

    @Column(name = "standTextOwnnerCorp", type = MySqlTypeConstant.VARCHAR,comment = "标准文本所属公司")
    @TableField("standTextOwnnerCorp")
    private String standTextOwnnerCorp;

    @Column(name = "standTextOwnnerDept", type = MySqlTypeConstant.VARCHAR,comment = "标准文本所属部门")
    @TableField("standTextOwnnerDept")
    private String standTextOwnnerDept;

    @Column(name = "standTextStatu", type = MySqlTypeConstant.VARCHAR,comment = "标准文本状态")
    @TableField("standTextStatu")
    private String standTextStatu;

    @Column(name = "standTextProp", type = MySqlTypeConstant.VARCHAR,comment = "标准文本属性")
    @TableField("standTextProp")
    private String standTextProp;

    @Column(name = "useageWide", type = MySqlTypeConstant.VARCHAR,comment = "使用范围")
    @TableField("useageWide")
    private String useageWide;

    @Column(name = "standTextActiveDate", type = MySqlTypeConstant.DATE, isNull = false,comment = "标准文本生效日期")
    @TableField("standTextActiveDate")
    private Date standTextActiveDate;

    @Column(name = "standTextDeactiveDate", type = MySqlTypeConstant.DATE, comment = "标准文本失效日期")
    @TableField("standTextDeactiveDate")
    private Date standTextDeactiveDate;

    @Column(name = "standTextCreator", type = MySqlTypeConstant.VARCHAR,isNull = false, comment = "标准文本创建人")
    @TableField("standTextCreator")
    private String standTextCreator;

    @Column(name = "standTextDisc", type = MySqlTypeConstant.VARCHAR, comment = "标准文本备注说明")
    @TableField("standTextDisc")
    private String standTextDisc;
}
