package com.zyx.controller.account;

import com.zyx.constants.account.AccountConstants;
import com.zyx.param.account.AccountLoginParam;
import com.zyx.rpc.account.AccountSecretFacade;
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
@Api(description = "密码接口。1、修改密码。2、忘记密码。")
public class AccountSecretController {

    @Autowired
    private AccountSecretFacade accountSecretFacade;

    @RequestMapping(value = "/renew_secret", method = RequestMethod.POST)
    @ApiOperation(value = "修改密码", notes = "修改密码")
    public ModelAndView renewSecret(@RequestParam(name = "token") String token,
                                    @RequestParam(name = "old_pwd") String password,
                                    @RequestParam(name = "new_pwd") String password2) {
        AbstractView jsonView = new MappingJackson2JsonView();

        if (StringUtils.isEmpty(token) || StringUtils.isEmpty(password) || StringUtils.isEmpty(password2)) {
            jsonView.setAttributesMap(AccountConstants.MAP_PARAM_MISS);
        } else {
            try {
                AccountLoginParam accountLoginParam = new AccountLoginParam();
                accountLoginParam.setToken(token);
                accountLoginParam.setPassword(password);// 旧密码
                accountLoginParam.setPassword2(password2);// 新密码
                jsonView.setAttributesMap(accountSecretFacade.renewSecret(accountLoginParam));
            } catch (Exception e) {
                e.printStackTrace();
                jsonView.setAttributesMap(AccountConstants.MAP_500);
            }
        }

        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/retrieve_secret", method = RequestMethod.POST)
    @ApiOperation(value = "忘记密码", notes = "忘记密码，通过手机号和验证码修改密码")
    public ModelAndView retrieveSecret(@RequestParam(name = "phone") String phone,
                                       @RequestParam(name = "code") String code,
                                       @RequestParam(name = "pwd") String password) {
        AbstractView jsonView = new MappingJackson2JsonView();

        if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(code) || StringUtils.isEmpty(password)) {
            jsonView.setAttributesMap(AccountConstants.MAP_PARAM_MISS);
        } else {
            try {
                AccountLoginParam accountLoginParam = new AccountLoginParam();
                accountLoginParam.setPhone(phone);
                accountLoginParam.setCode(code);
                accountLoginParam.setPassword(password);
                jsonView.setAttributesMap(accountSecretFacade.retrieveSecret(accountLoginParam));
            } catch (Exception e) {
                e.printStackTrace();
                jsonView.setAttributesMap(AccountConstants.MAP_500);
            }
        }

        return new ModelAndView(jsonView);
    }
}
