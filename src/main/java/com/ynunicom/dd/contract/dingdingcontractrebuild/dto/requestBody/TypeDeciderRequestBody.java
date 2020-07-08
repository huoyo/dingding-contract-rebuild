package com.ynunicom.dd.contract.dingdingcontractrebuild.dto.requestBody;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author: jinye.Bai
 * @date: 2020/7/8 13:43
 */
@Data
public class TypeDeciderRequestBody implements Serializable {

    //四大类的哪一种,1代表战略框架合作，2支出类框架协议，3支出类固定金额合同，4收入类合同
    @NotNull
    private Integer type;

    //合同额，可以是0
    @NotNull
    private BigDecimal money;

    @NotBlank
    private String userDeptId;


}
