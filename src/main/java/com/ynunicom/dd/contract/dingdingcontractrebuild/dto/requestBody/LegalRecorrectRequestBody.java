package com.ynunicom.dd.contract.dingdingcontractrebuild.dto.requestBody;

import lombok.Data;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author: jinye.Bai
 * @date: 2020/7/13 13:21
 */
@Data
public class LegalRecorrectRequestBody implements Serializable {
    @NotNull
    MultipartFile contractText;

    @NotBlank
    String taskId;
}
