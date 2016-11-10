package com.zyx.interceptor;

import com.zyx.constants.Constants;
import com.zyx.rpc.common.TokenFacade;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * Created by wms on 2016/11/10.
 *
 * @author WeiMinSheng
 * @version V2.0
 *          Copyright (c)2016 tyj-版权所有
 * @since 2016/11/10
 */
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private TokenFacade tokenFacade;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 不是method请求直接跳过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        // 解决service为null无法注入问题
        if (tokenFacade == null) {
            BeanFactory factory = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
            tokenFacade = (TokenFacade) factory.getBean("tokenFacade");
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        // 不使用权限验证的方法直接跳过
        if (method.getAnnotation(Authorization.class) == null) {
            return true;
        }

        try {
            // 从header中获取token字符串
            String anthorization = request.getHeader(Constants.AUTHORIZATION);
            if (tokenFacade.preHandle(anthorization)) {
                return true;
            } else {
                // 验证失败
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return false;
            }
        } catch (Exception e) {
            // dubbo服务请求失败
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
    }
}
