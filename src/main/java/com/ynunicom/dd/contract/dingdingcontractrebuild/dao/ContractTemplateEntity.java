package com.ynunicom.dd.contract.dingdingcontractrebuild.dao;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;

/**
 * @author: jinye.Bai
 * @date: 2020/6/19 10:21
 */
@Table(name = "Contract_Template")
@TableName("Contract_Template")
public class ContractTemplateEntity extends BaseModel {

    @Column(name = "id", type = MySqlTypeConstant.VARCHAR, isNull = false,
            isKey = true, comment = "id")
    @TableId
    private String id;

    @Column(name = "templateName", type = MySqlTypeConstant.VARCHAR, isNull = false,comment = "模板名")
    @TableField("templateName")
    private String templateName;

    @Column(name = "templateFilePath", type = MySqlTypeConstant.VARCHAR, isNull = false,comment = "模板文件路径")
    @TableField("templateFilePath")
    private String templateFilePath;
}
