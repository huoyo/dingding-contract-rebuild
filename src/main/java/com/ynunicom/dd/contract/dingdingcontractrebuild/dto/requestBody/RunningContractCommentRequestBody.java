package com.ynunicom.dd.contract.dingdingcontractrebuild.dto.requestBody;

import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author: jinye.Bai
 * @date: 2020/7/1 10:44
 */
@Data
public class RunningContractCommentRequestBody implements Serializable {
    @NotBlank
    String comment;

    @NotBlank
    String contractId;
}
