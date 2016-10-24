package com.zyx.controller.account;

import com.zyx.constants.Constants;
import com.zyx.param.account.UserLoginParam;
import com.zyx.rpc.account.RegisterFacade;
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
 * Created by wms on 2016/6/13.
 *
 * @author WeiMinSheng
 * @version V1.0
 *          Copyright (c)2016 tyj-版权所有
 */
@RestController
@RequestMapping("/v1/account")
@Api(description = "用户密码修改API。1、修改密码。2、忘记密码。")
public class RenewPasswordController {

    @Autowired
    private RegisterFacade registerFacade;

    @RequestMapping(value = "/renewpwd", method = RequestMethod.POST)
    @ApiOperation(value = "修改密码", notes = "修改密码")
    public ModelAndView renewpwd(@RequestParam(name = "token") String token, @RequestParam(name = "old_pwd") String password, @RequestParam(name = "new_pwd") String password2) {
        AbstractView jsonView = new MappingJackson2JsonView();

        if (StringUtils.isEmpty(token) || StringUtils.isEmpty(password) || StringUtils.isEmpty(password2)) {
            jsonView.setAttributesMap(Constants.MAP_PARAM_MISS);
        } else {
            try {
                UserLoginParam userLoginParam = new UserLoginParam();
                userLoginParam.setToken(token);
                userLoginParam.setPassword(password);// 旧密码
                userLoginParam.setPassword2(password2);// 新密码
                jsonView.setAttributesMap(registerFacade.renewpwd(userLoginParam));
            } catch (Exception e) {
                jsonView.setAttributesMap(Constants.MAP_500);
            }
        }

        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/retrievepwd", method = RequestMethod.POST)
    @ApiOperation(value = "忘记密码", notes = "忘记密码，通过手机号和验证码修改密码")
    public ModelAndView retrievepwd(@RequestParam(name = "phone") String phone,
                                    @RequestParam(name = "pwd") String password,
                                    @RequestParam(name = "re_pwd") String rePassword) {
        AbstractView jsonView = new MappingJackson2JsonView();

        if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(password) || StringUtils.isEmpty(rePassword)) {
            jsonView.setAttributesMap(Constants.MAP_PARAM_MISS);
        } else {
            try {
                UserLoginParam userLoginParam = new UserLoginParam();
                userLoginParam.setPhone(phone);
                userLoginParam.setPassword(password);
                userLoginParam.setPassword2(rePassword);
                jsonView.setAttributesMap(registerFacade.retrievepwd(userLoginParam));
            } catch (Exception e) {
                jsonView.setAttributesMap(Constants.MAP_500);
            }
        }

        return new ModelAndView(jsonView);
    }

}
