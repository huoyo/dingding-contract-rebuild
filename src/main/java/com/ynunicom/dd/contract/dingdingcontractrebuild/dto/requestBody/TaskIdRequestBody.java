package com.ynunicom.dd.contract.dingdingcontractrebuild.dto.requestBody;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author: jinye.Bai
 * @date: 2020/7/1 15:09
 */
@Data
public class TaskIdRequestBody implements Serializable {

    @NotBlank
    String taskId;
}
