package com.ynunicom.dd.contract.dingdingcontractrebuild.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: jinye.Bai
 * @date: 2020/6/18 11:30
 */
@Data
@AllArgsConstructor
public class PersonEntity implements Serializable {

    private static final long serialVersionUID = 5690809710817734708L;

    private String name;

    private String userId;

    /**
     * 用户头像URL
     */
    private String avatar;

    private String deptId;

}
