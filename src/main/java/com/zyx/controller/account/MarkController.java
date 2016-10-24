package com.zyx.controller.account;

import com.zyx.constants.Constants;
import com.zyx.interceptor.Authorization;
import com.zyx.param.account.UserMarkParam;
import com.zyx.rpc.account.MarkFacade;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.AbstractView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.Map;

/**
 * Created by WeiMinSheng on 2016/6/16.
 *
 * @author WeiMinSheng
 * @version V1.0
 *          Copyright (c)2016 tyj-版权所有
 *          MarkController.java
 */
@RestController
@RequestMapping("/v1/account")
@Api(description = "用户签到接口API。1、签到。2、查询用户签到信息")
public class MarkController {

    @Autowired
    private MarkFacade markFacade;

    @RequestMapping(value = "/sign", method = RequestMethod.GET)
    @ApiOperation(value = "签到", notes = "签到")
    @Authorization
    public ModelAndView sign(@RequestParam(name = "token") String token, @RequestParam(name = "accountId") int userId) {
        AbstractView jsonView = new MappingJackson2JsonView();

        if (StringUtils.isEmpty(token) || StringUtils.isEmpty(userId)) {// 缺少参数
            jsonView.setAttributesMap(Constants.MAP_PARAM_MISS);
        } else {
            jsonView.setAttributesMap(doSign(token, userId));
        }

        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/querySign", method = RequestMethod.GET)
    @ApiOperation(value = "查询签到信息", notes = "查询用户签到信息")
    @Authorization
    public ModelAndView querySign(@RequestParam(name = "token") String token, @RequestParam(name = "accountId") int userId) {
        AbstractView jsonView = new MappingJackson2JsonView();

        if (StringUtils.isEmpty(token) || StringUtils.isEmpty(userId)) {// 缺少参数
            jsonView.setAttributesMap(Constants.MAP_PARAM_MISS);
        } else {
            jsonView.setAttributesMap(doQuerySign(token, userId));
        }

        return new ModelAndView(jsonView);
    }

    private Map<String, Object> doSign(String token, int userId) {
        try {
            return markFacade.sign(buildUserMarkParam(token, userId));
        } catch (Exception e) {
            return Constants.MAP_500;
        }
    }

    private Map<String, Object> doQuerySign(String token, int userId) {
        try {
            return markFacade.querySign(buildUserMarkParam(token, userId));
        } catch (Exception e) {
            return Constants.MAP_500;
        }
    }

    private UserMarkParam buildUserMarkParam(String token, int userId) {
        UserMarkParam userMarkParam = new UserMarkParam();
        userMarkParam.setToken(token);
        userMarkParam.setUserId(userId);
        return userMarkParam;
    }

}
