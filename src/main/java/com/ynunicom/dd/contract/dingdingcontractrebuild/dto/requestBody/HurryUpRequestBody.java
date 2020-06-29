package com.ynunicom.dd.contract.dingdingcontractrebuild.dto.requestBody;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: jinye.Bai
 * @date: 2020/6/29 11:19
 */
@Data
public class HurryUpRequestBody implements Serializable {

    private String taskId;

    private String userId;
}
