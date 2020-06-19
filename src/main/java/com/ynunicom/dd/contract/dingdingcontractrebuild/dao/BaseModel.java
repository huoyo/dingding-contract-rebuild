package com.ynunicom.dd.contract.dingdingcontractrebuild.dao;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import org.joda.time.DateTime;

import java.util.Date;

/**
 * @author: jinye.Bai
 * @date: 2020/6/18 10:03
 */
public class BaseModel {

    @Column(name = "createdtime", type = MySqlTypeConstant.DATETIME, isNull = false,comment = "创建时间")
    @TableField(value = "createdtime",fill = FieldFill.INSERT)
    protected DateTime createdtime;

    @Column(name = "updatetime", type = MySqlTypeConstant.DATETIME, isNull = false,comment = "更新时间")
    @TableField(value = "updatetime",fill = FieldFill.INSERT_UPDATE)
    protected DateTime updatetime;
}