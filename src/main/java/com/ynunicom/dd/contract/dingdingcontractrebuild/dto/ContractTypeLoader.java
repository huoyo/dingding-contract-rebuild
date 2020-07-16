package com.ynunicom.dd.contract.dingdingcontractrebuild.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author: jinye.Bai
 * @date: 2020/7/16 9:55
 */
@Data
public class ContractTypeLoader implements Serializable {

    private String level;

    private List<ContractTypeLoader> child;
}
