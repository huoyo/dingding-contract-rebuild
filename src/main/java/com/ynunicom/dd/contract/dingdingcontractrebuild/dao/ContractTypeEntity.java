package com.ynunicom.dd.contract.dingdingcontractrebuild.dao;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.OracleTypeConstant;
import com.gitee.sunchenbin.mybatis.actable.constants.OracleTypeConstant;
import com.gitee.sunchenbin.mybatis.actable.constants.OracleTypeConstant;
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

    @Column(name = "id", type = OracleTypeConstant.VARCHAR2, isNull = false,
            isKey = true, comment = "id")
    @TableId
    private String id;

    @Column(name = "name", type =OracleTypeConstant.VARCHAR2,
            comment = "目录名")
    @TableField("name")
    private String name;

    @Column(name = "parentId", type =OracleTypeConstant.VARCHAR2,
            comment = "父目录id")
    @TableField("parentId")
    private String parentId;

    @Column(name = "prop", type =  OracleTypeConstant.NUMBER,
            comment = "四大类的哪一种,1代表战略框架合作，2支出类框架协议，3支出类固定金额合同，4收入类合同")
    @TableField("prop")
    private Integer prop;

    @Column(name = "isSpe", type = OracleTypeConstant.NUMBER,
            comment = "此项为1的类型，不受金额影响其prop")
    @TableField("isSpe")
    private Integer isSpe;
}
