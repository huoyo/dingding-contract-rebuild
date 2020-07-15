package com.ynunicom.dd.contract.dingdingcontractrebuild.dto.requestBody;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author: jinye.Bai
 * @date: 2020/6/19 11:14
 */
@Data
public class JudgeRequestBody {

    @NotBlank
    private String userId;

    @NotBlank
    private String taskId;

    @NotNull
    private Boolean isOk;

    private String comment;
}
