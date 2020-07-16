package com.ynunicom.dd.contract.dingdingcontractrebuild.dao;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: jinye.Bai
 * @date: 2020/7/8 14:02
 */
@NoArgsConstructor
@Data
@TableName(value = "DEPT_RELATION")
@Table(name = "DEPT_RELATION")
public class DeptRelation {

    @Column(name = "id", type = MySqlTypeConstant.VARCHAR, isNull = false,
            isKey = true, comment = "id")
    @TableId
    private String id;

    @Column(name = "deptId", type = MySqlTypeConstant.VARCHAR, isNull = false, comment = "部门id")
    @TableField("deptId")
    private String deptId;

    @Column(name = "upperestFatherDeptId", type = MySqlTypeConstant.VARCHAR, isNull = false, comment = "最上级父部门id")
    @TableField("upperestFatherDeptId")
    private String upperestFatherDeptId;
}
