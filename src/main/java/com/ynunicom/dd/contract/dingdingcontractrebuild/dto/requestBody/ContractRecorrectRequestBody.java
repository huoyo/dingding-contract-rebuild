package com.ynunicom.dd.contract.dingdingcontractrebuild.dto.requestBody;

import lombok.Data;

/**
 * @author: jinye.Bai
 * @date: 2020/6/23 19:06
 */
@Data
public class ContractRecorrectRequestBody extends ContractApplyRequestBody {

    private String taskId;

    //这一属性为真则放弃合同申请
    private boolean drop;
}
