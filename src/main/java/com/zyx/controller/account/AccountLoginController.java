package com.zyx.controller.account;

import com.zyx.constants.account.AccountConstants;
import com.zyx.rpc.account.AccountLoginFacade;
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
 * Created by wms on 2016/11/8.
 *
 * @author WeiMinSheng
 * @version V2.0
 *          Copyright (c)2016 tyj-版权所有
 * @since 2016/11/8
 */
@RestController
@RequestMapping("/v2/account")
@Api(description = "登录接口。【1】手机和密码登录。【2】退出。【3】刷新token")
public class AccountLoginController {
    @Autowired
    private AccountLoginFacade accountLoginFacade;

    @RequestMapping(value = "/log_in", method = RequestMethod.POST)
    @ApiOperation(value = "手机密码登录", notes = "手机密码登录")
    public ModelAndView logIn(@RequestParam(name = "phone") String phone, @RequestParam(name = "pwd") String password) {
        AbstractView jsonView = new MappingJackson2JsonView();

        if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(password)) {// 缺少参数
            jsonView.setAttributesMap(AccountConstants.MAP_PARAM_MISS);
        } else {
            try {
                jsonView.setAttributesMap(accountLoginFacade.loginByPhoneAndPassword(phone, password));
            } catch (Exception e) {
                e.printStackTrace();
                jsonView.setAttributesMap(AccountConstants.MAP_500);
            }
        }

        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/log_out", method = RequestMethod.GET)
    @ApiOperation(value = "退出", notes = "退出")
    public ModelAndView logOut(@RequestParam(name = "token") String token) {
        AbstractView jsonView = new MappingJackson2JsonView();

        if (StringUtils.isEmpty(token)) {// 缺少参数
            jsonView.setAttributesMap(AccountConstants.MAP_PARAM_MISS);
        } else {// 退出
            try {
                jsonView.setAttributesMap(accountLoginFacade.logOut(token));
            } catch (Exception e) {
                e.printStackTrace();
                jsonView.setAttributesMap(AccountConstants.MAP_500);
            }
        }

        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/refresh_token", method = RequestMethod.GET)
    @ApiOperation(value = "刷新token", notes = "刷新token")
    public ModelAndView refreshToken(@RequestParam(name = "token") String token) {
        AbstractView jsonView = new MappingJackson2JsonView();

        if (StringUtils.isEmpty(token)) {// 缺少参数
            jsonView.setAttributesMap(AccountConstants.MAP_PARAM_MISS);
        } else {// 刷新token并返回新token
            try {
                jsonView.setAttributesMap(accountLoginFacade.refreshToken(token));
            } catch (Exception e) {
                e.printStackTrace();
                jsonView.setAttributesMap(AccountConstants.MAP_500);
            }
        }

        return new ModelAndView(jsonView);
    }
}
