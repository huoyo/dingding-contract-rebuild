package com.ynunicom.dd.contract.dingdingcontractrebuild.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author: jinye.Bai
 * @date: 2020/7/8 13:35
 */
@Data
public class DeptEntity implements Serializable {

    private List<Map<String,String>> deptIdList;

}
