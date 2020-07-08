package com.ynunicom.dd.contract.dingdingcontractrebuild.dao;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author: jinye.Bai
 * @date: 2020/7/8 9:05
 */
@NoArgsConstructor
@Data
@TableName(value = "CONTRACT_TYPES")
@Table(name = "CONTRACT_TYPES")
public class ContractTypeEntity implements Serializable {

    @Column(name = "id", type = MySqlTypeConstant.VARCHAR, isNull = false,
            isKey = true, comment = "id")
    @TableId
    private String id;

    @Column(name = "level1st", type = MySqlTypeConstant.VARCHAR, isNull = false,
            comment = "一级目录")
    @TableField("level1st")
    private String level1st;

    @Column(name = "level2nd", type = MySqlTypeConstant.VARCHAR, isNull = false,
            comment = "二级目录")
    @TableField("level2nd")
    private String level2nd;

    @Column(name = "level3nd", type = MySqlTypeConstant.VARCHAR,
            comment = "三级目录")
    @TableField("level3nd")
    private String level3nd;

    @Column(name = "prop", type = MySqlTypeConstant.INT,
            comment = "四大类的哪一种,1代表战略框架合作，2支出类框架协议，3支出类固定金额合同，4收入类合同")
    @TableField("prop")
    private Integer prop;
}
