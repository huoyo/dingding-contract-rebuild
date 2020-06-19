package com.ynunicom.dd.contract.dingdingcontractrebuild.dao;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;

/**
 * @author: jinye.Bai
 * @date: 2020/6/19 10:42
 */
@Table(name = "attachment")
@TableName("attachment")
public class AttachmentEntity extends BaseModel {

    @Column(name = "id", type = MySqlTypeConstant.VARCHAR, isNull = false,
            isKey = true, comment = "id")
    @TableId
    private String id;

    @Column(name = "contractInfoId",type = MySqlTypeConstant.VARCHAR,isNull = false
    ,comment = "合同id")
    @TableField("contractInfoId")
    private String contractInfoId;

    @Column(name = "contractTemplateId",type = MySqlTypeConstant.VARCHAR,isNull = false
            ,comment = "合同模板id")
    @TableField("contractTemplateId")
    private String contractTemplateId;

    @Column(name = "attachmentFilePath",type = MySqlTypeConstant.VARCHAR,isNull = false
            ,comment = "附件文件路径")
    @TableField("attachmentFilePath")
    private String attachmentFilePath;
}
