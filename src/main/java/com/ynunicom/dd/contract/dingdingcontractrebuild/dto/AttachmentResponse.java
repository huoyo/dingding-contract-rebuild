package com.ynunicom.dd.contract.dingdingcontractrebuild.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * @author: jinye.Bai
 * @date: 2020/7/13 16:06
 */
@Data
public class AttachmentResponse implements Serializable {
    private Map<String,String> map;
}
