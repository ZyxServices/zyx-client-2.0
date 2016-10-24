package com.zyx.controller.account;

import com.zyx.constants.Constants;
import com.zyx.rpc.account.UserLoginFacade;
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

/**
 * Created by wms on 2016/6/12.
 *
 * @author WeiMinSheng
 * @version V1.0
 *          Copyright (c)2016 tyj-版权所有
 *          LoginController.java
 */
@RestController
@RequestMapping("/v1/account")
@Api(description = "用户登录相关API。1、手机和密码登录。2、退出。3、刷新token")
public class LoginController {

    @Autowired
    private UserLoginFacade userLoginFacade;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ApiOperation(value = "手机密码登录", notes = "手机密码登录")
    public ModelAndView login(@RequestParam(name = "phone") String phone, @RequestParam(name = "pwd") String password) {
        AbstractView jsonView = new MappingJackson2JsonView();

        if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(password)) {// 缺少参数
            jsonView.setAttributesMap(Constants.MAP_PARAM_MISS);
        } else {
            jsonView.setAttributesMap(userLoginFacade.loginByPhoneAndPassword(phone, password));
        }

        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/signout", method = RequestMethod.GET)
    @ApiOperation(value = "退出", notes = "退出")
    public ModelAndView signout(@RequestParam(name = "token") String token) {
        AbstractView jsonView = new MappingJackson2JsonView();

        if (StringUtils.isEmpty(token)) {// 缺少参数
            jsonView.setAttributesMap(Constants.MAP_PARAM_MISS);
        } else {// 退出
            jsonView.setAttributesMap(userLoginFacade.signout(token));
        }

        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/refreshtoken", method = RequestMethod.GET)
    @ApiOperation(value = "刷新token", notes = "刷新token")
    public ModelAndView refreshtoken(@RequestParam(name = "token") String token) {
        AbstractView jsonView = new MappingJackson2JsonView();

        if (StringUtils.isEmpty(token)) {// 缺少参数
            jsonView.setAttributesMap(Constants.MAP_PARAM_MISS);
        } else {// 刷新token并返回新token
            jsonView.setAttributesMap(userLoginFacade.refreshtoken(token));
        }

        return new ModelAndView(jsonView);
    }

}
