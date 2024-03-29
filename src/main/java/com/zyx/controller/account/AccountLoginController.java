package com.zyx.controller.account;

import com.zyx.constants.account.AccountConstants;
import com.zyx.controller.point.PointParamContext;
import com.zyx.controller.point.PointPool;
import com.zyx.controller.point.RecordPointRunnable;
import com.zyx.controller.point.strategy.FBPLStrategy;
import com.zyx.controller.point.strategy.LoginStrategy;
import com.zyx.rpc.account.AccountLoginFacade;
import com.zyx.rpc.point.UserPointFacade;
import com.zyx.vo.account.AccountInfoVo;
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

    @Autowired
    private UserPointFacade userPointFacade;

    @RequestMapping(value = "/log_in", method = RequestMethod.POST)
    @ApiOperation(value = "手机密码登录", notes = "手机密码登录")
    public ModelAndView logIn(@RequestParam(name = "phone") String phone, @RequestParam(name = "pwd") String password) {
        AbstractView jsonView = new MappingJackson2JsonView();

        if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(password)) {// 缺少参数
            jsonView.setAttributesMap(AccountConstants.MAP_PARAM_MISS);
        } else {
            try {
                Map<String, Object> map = accountLoginFacade.loginByPhoneAndPassword(phone, password);
                jsonView.setAttributesMap(map);
                checkAndRecordPoint(map);
            } catch (Exception e) {
                e.printStackTrace();
                jsonView.setAttributesMap(AccountConstants.MAP_500);
            }
        }

        return new ModelAndView(jsonView);
    }

    private void checkAndRecordPoint(Map<String, Object> map) {
        try {// 积分方法不保证成功或失败
            Object state = map.get(AccountConstants.STATE);
            if (state != null && AccountConstants.SUCCESS == (int) map.get(AccountConstants.STATE)) {
                AccountInfoVo vo = (AccountInfoVo) map.get(AccountConstants.DATA);
                PointPool.getPointPool().execute(new RecordPointRunnable(userPointFacade, new PointParamContext(new LoginStrategy()).build(vo.getId())));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
