package com.ynunicom.dd.contract.dingdingcontractrebuild.dao;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;

/**
 * @author: jinye.Bai
 * @date: 2020/6/18 10:03
 */
public class BaseModel {

    @TableField(value = "createdtime",fill = FieldFill.INSERT)
    protected String createdtime;

    @TableField(value = "updatetime",fill = FieldFill.INSERT_UPDATE)
    protected String updatetime;
}
