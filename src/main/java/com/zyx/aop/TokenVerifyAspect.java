package com.zyx.aop;

import com.zyx.annotation.TokenVerify;
import com.zyx.constants.Constants;
import com.zyx.rpc.common.TokenFacade;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.AbstractView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;

/**
 * Created by wms on 2016/11/25.
 *
 * @author WeiMinSheng
 * @version V2.0
 *          Copyright (c)2016 tyj-版权所有
 * @since 2016/11/25
 */
@Aspect
@Component
public class TokenVerifyAspect {
    private Logger logger = Logger.getLogger(getClass());

    @Autowired
    private TokenFacade tokenFacade;

    @Pointcut("execution(public * com.zyx.controller.user.UserMarkController.*(..))")
    public void verifyAspect() {
    }

    @Before("verifyAspect()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        // 记录下请求内容
        logger.info("URL : " + request.getRequestURL().toString());
        logger.info("HTTP_METHOD : " + request.getMethod());
        logger.info("IP : " + request.getRemoteAddr());
        logger.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        logger.info("ARGS : " + Arrays.toString(joinPoint.getArgs()));


    }

    @Around("verifyAspect()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        // 获取当前执行方法
        Method currentMethod = getCurrentMethod(pjp);
        // 不使用权限验证的方法直接跳过
        if (currentMethod.getAnnotation(TokenVerify.class) != null) {
            Object result = handleToken(pjp.getArgs(), currentMethod);
            if (result != null) return result;
        }
        return pjp.proceed();
    }

    private Object handleToken(Object[] args, Method currentMethod) {

        logger.info("args : " + Arrays.toString(args));
        // 获取token
        String token;
        try {
            token = (String) args[0];
            if (StringUtils.isEmpty(token)) {
                AbstractView jsonView = new MappingJackson2JsonView();
                jsonView.setAttributesMap(Constants.MAP_PARAM_MISS);
                return new ModelAndView(jsonView);
            }
        } catch (Exception e) {
            AbstractView jsonView = new MappingJackson2JsonView();
            jsonView.setAttributesMap(Constants.MAP_PARAM_MISS);
            return new ModelAndView(jsonView);
        }

        TokenVerify.VerifyEnum verifyType = currentMethod.getAnnotation(TokenVerify.class).verifyType();
        Map<String, Object> map;
        if (TokenVerify.VerifyEnum.NORMAL.equals(verifyType)) {
            map = tokenFacade.validateToken(token);
        } else {
            try {
                int userId = (int) args[1];
                if (TokenVerify.VerifyEnum.MINE.equals(verifyType)) {// 判断自身
                    map = tokenFacade.validateToken(token, userId);
                } else {
                    map = tokenFacade.validateTokenIncludeOther(token, userId);
                }
            } catch (Exception e) {
                AbstractView jsonView = new MappingJackson2JsonView();
                jsonView.setAttributesMap(Constants.MAP_PARAM_MISS);
                return new ModelAndView(jsonView);
            }
        }
        if (map != null) {
            AbstractView jsonView = new MappingJackson2JsonView();
            jsonView.setAttributesMap(map);
            return new ModelAndView(jsonView);
        }
        return null;
    }

    private Method getCurrentMethod(ProceedingJoinPoint pjp) throws NoSuchMethodException {
        Signature sig = pjp.getSignature();
        MethodSignature methodSignature;
        if (!(sig instanceof MethodSignature)) {
            throw new IllegalArgumentException("该注解只能用于方法");
        }
        methodSignature = (MethodSignature) sig;
        Object target = pjp.getTarget();
        return target.getClass().getMethod(methodSignature.getName(), methodSignature.getParameterTypes());
    }

    @AfterReturning(returning = "ret", pointcut = "verifyAspect()")
    public void doAfterReturning(Object ret) throws Throwable {
        // 处理完请求，返回内容
        logger.info("RESPONSE : " + ret);
    }
}
