package com.ynunicom.dd.contract.dingdingcontractrebuild.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: jinye.Bai
 * @date: 2020/6/18 14:54
 */
@Data
public class JudgePersonEntity implements Serializable {
    private static final long serialVersionUID = 189578032602443037L;

    private Boolean isOk;

    private String comment;

    private Date lastJudgeTime;

    private PersonEntity personEntity;
}
