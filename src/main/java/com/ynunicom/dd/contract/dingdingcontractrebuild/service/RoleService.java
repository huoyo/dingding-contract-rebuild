package com.ynunicom.dd.contract.dingdingcontractrebuild.service;

import java.util.List;

/**
 * @author: jinye.Bai
 * @date: 2020/7/1 11:07
 */
public interface RoleService {
    public List<String> getRoleByUserId(String userId, String accessToken);
}
