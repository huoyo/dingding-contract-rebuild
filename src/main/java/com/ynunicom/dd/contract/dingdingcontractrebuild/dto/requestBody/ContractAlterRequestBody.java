package com.ynunicom.dd.contract.dingdingcontractrebuild.dto.requestBody;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author: jinye.Bai
 * @date: 2020/7/1 9:34
 */
@Data
public class ContractAlterRequestBody extends  ContractApplyRequestBody{

    @NotBlank
    private  String organizerUserId;

    @NotBlank
    private String organizerName;

    @NotBlank
    String  contractId;

    @NotBlank
    String method;

    String reason;

}
