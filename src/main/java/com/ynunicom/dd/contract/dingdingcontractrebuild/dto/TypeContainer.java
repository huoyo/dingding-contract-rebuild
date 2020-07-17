package com.ynunicom.dd.contract.dingdingcontractrebuild.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author: jinye.Bai
 * @date: 2020/7/16 17:09
 */
@Data
public class TypeContainer implements Serializable {
    private String name;

    private String id;

    private Integer prop;

    private Integer isSpe;

    private List<TypeContainer> typeContainer;
}
