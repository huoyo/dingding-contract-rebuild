package com.ynunicom.dd.contract.dingdingcontractrebuild.config.inceptor;

import com.ynunicom.dd.contract.dingdingcontractrebuild.exception.BussException;
import com.ynunicom.dd.contract.dingdingcontractrebuild.service.UserInfoService;
import com.ynunicom.dd.contract.dingdingcontractrebuild.utils.SpringHelper;
import com.ynunicom.dd.contract.dingdingcontractrebuild.utils.UserVerify;
import lombok.SneakyThrows;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author: jinye.Bai
 * @date: 2020/6/30 14:18
 */
public class UserAuthorInceptor implements HandlerInterceptor {

    @SneakyThrows
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
        Map<String,String[]> map = request.getParameterMap();
        if (map.isEmpty()){
            return true;
        }
        String[] userIds  = map.get("userId");
        String[] accessTokens = map.get("accessToken");
        if (userIds==null||accessTokens==null){
            return true;
        }
        String userId = userIds[0];
        String accessToken = accessTokens[0];
        if (userId==null||accessToken==null){
            return true;
        }
        UserInfoService userInfoService = SpringHelper.getUserInfoService();
        if (UserVerify.verify(accessToken, userId, userInfoService)){
            throw new BussException(userId+"用户不存在");
        }
        return true;
    }
}
