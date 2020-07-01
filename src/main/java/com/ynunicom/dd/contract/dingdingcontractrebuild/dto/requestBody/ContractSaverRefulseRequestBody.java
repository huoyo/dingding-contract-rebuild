package com.ynunicom.dd.contract.dingdingcontractrebuild.dto.requestBody;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author: jinye.Bai
 * @date: 2020/7/1 13:40
 */
@Data
public class ContractSaverRefulseRequestBody implements Serializable {
    @NotBlank
    String comment;

    @NotBlank
    String taskId;
}
