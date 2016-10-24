package com.zyx.controller.account;

import com.zyx.constants.Constants;
import com.zyx.param.account.AccountInfoParam;
import com.zyx.param.account.UserAuthParam;
import com.zyx.rpc.account.AccountInfoFacade;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
 * Created by wms on 2016/6/17.
 *
 * @author WeiMinSheng
 * @version V1.0
 *          Copyright (c)2016 tyj-版权所有
 */
@RestController
@RequestMapping("/v1/account")
@Api(description = "用户信息相关接口。1、查询用户信息。")
public class AccountController {
    @Autowired
    private AccountInfoFacade accountInfoFacade;

    @RequestMapping(value = "/info", method = {RequestMethod.GET, RequestMethod.POST})
    @ApiOperation(value = "通过用户ID查询用户信息", notes = "通过用户ID查询用户信息")
    public ModelAndView info(@RequestParam(name = "token") String token, @RequestParam(name = "account_id") Integer userId) {
        AbstractView jsonView = new MappingJackson2JsonView();
        try {
            if (StringUtils.isEmpty(token) || StringUtils.isEmpty(userId)) {// 缺少参数
                jsonView.setAttributesMap(Constants.MAP_PARAM_MISS);
            } else {
                jsonView.setAttributesMap(accountInfoFacade.queryAccountInfo(token, userId));
            }
        } catch (Exception e) {
            jsonView.setAttributesMap(Constants.MAP_500);
        }
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/center_info", method = {RequestMethod.GET})
    @ApiOperation(value = "通过用户ID查询个人中心用户信息", notes = "通过用户ID查询个人中心用户信息")
    public ModelAndView centerInfo(
            @ApiParam(required = true, name = "token", value = "使用通用token:tiyujia2016可以查询别人的个人中心信息")
            @RequestParam(name = "token") String token,
            @RequestParam(name = "account_id") Integer userId) {
        AbstractView jsonView = new MappingJackson2JsonView();
        try {
            if (StringUtils.isEmpty(token) || StringUtils.isEmpty(userId)) {// 缺少参数
                jsonView.setAttributesMap(Constants.MAP_PARAM_MISS);
            } else {
                jsonView.setAttributesMap(accountInfoFacade.queryMyCenterInfo(token, userId));
            }
        } catch (Exception e) {
            jsonView.setAttributesMap(Constants.MAP_500);
        }
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/auth_info", method = {RequestMethod.GET})
    @ApiOperation(value = "通过用户ID查询用户审核信息", notes = "通过用户ID查询用户审核信息")
    public ModelAndView authInfo(
            @RequestParam(name = "token") String token,
            @RequestParam(name = "account_id") Integer userId) {
        AbstractView jsonView = new MappingJackson2JsonView();
        try {
            if (StringUtils.isEmpty(token) || StringUtils.isEmpty(userId)) {// 缺少参数
                jsonView.setAttributesMap(Constants.MAP_PARAM_MISS);
            } else {
                jsonView.setAttributesMap(accountInfoFacade.queryMyAuthInfo(token, userId));
            }
        } catch (Exception e) {
            jsonView.setAttributesMap(Constants.MAP_500);
        }
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/info/edit", method = {RequestMethod.POST})
    @ApiOperation(value = "通过用户ID编辑用户信息", notes = "通过用户ID编辑用户信息")
    public ModelAndView edit(@RequestParam(name = "token") String token,
                             @RequestParam(name = "account_id") Integer userId,
                             @ApiParam(name = "avatar", value = "头像地址，需要先使用文件上传接口上传获取地址")
                             @RequestParam(required = false) String avatar,
                             @ApiParam(name = "nickname", value = "昵称")
                             @RequestParam(required = false) String nickname,
                             @ApiParam(name = "sex", value = "性别:1男 0女")
                             @RequestParam(required = false) Integer sex,
                             @ApiParam(name = "birthday", value = "生日。日期时间戳")
                             @RequestParam(required = false) Long birthday,
                             @ApiParam(name = "address", value = "地址")
                             @RequestParam(required = false) String address,
                             @ApiParam(name = "signature", value = "签名")
                             @RequestParam(required = false) String signature) {
        AbstractView jsonView = new MappingJackson2JsonView();

        if (StringUtils.isEmpty(token) || StringUtils.isEmpty(userId)) {// 缺少参数
            jsonView.setAttributesMap(Constants.MAP_PARAM_MISS);
        } else {
            // 判断属性是否存在
            if (StringUtils.isEmpty(avatar) && StringUtils.isEmpty(nickname) && StringUtils.isEmpty(sex) && StringUtils.isEmpty(birthday) && StringUtils.isEmpty(address) && StringUtils.isEmpty(signature)) {
                // 必须包含一个参数值
                jsonView.setAttributesMap(Constants.MAP_PARAM_MISS);
            } else {
                AccountInfoParam param = new AccountInfoParam();
                param.setAvatar(avatar);
                param.setNickname(nickname);
                param.setSex(sex);
                param.setBirthday(birthday);
                param.setAddress(address);
                param.setSignature(signature);
                jsonView.setAttributesMap(accountInfoFacade.editAccountInfo(token, userId, param));
            }
        }
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/auth_info/edit", method = {RequestMethod.POST})
    @ApiOperation(value = "通过用户ID提交认证信息", notes = "通过用户ID提交认证信息")
    public ModelAndView editAuthInfo(@RequestParam(name = "token") String token,
                                     @RequestParam(name = "account_id") Integer userId,
                                     @ApiParam(required = true, name = "authName", value = "真实姓名")
                                     @RequestParam() String authName,
                                     @ApiParam(required = true, name = "authIDCard", value = "身份证号码")
                                     @RequestParam() String authIDCard,
                                     @ApiParam(required = true, name = "authMob", value = "手机号码")
                                     @RequestParam() String authMob,
                                     @ApiParam(required = true, name = "authFile", value = "手持身份证照片，图片地址，需要先使用文件上传接口上传获取地址")
                                     @RequestParam() String authFile,
                                     @ApiParam(required = true, name = "authInfo", value = "认证标签")
                                     @RequestParam() String authInfo,
                                     @ApiParam(required = true, name = "authFileWork", value = "工作证明照片，图片地址，需要先使用文件上传接口上传获取地址")
                                     @RequestParam() String authFileWork) {
        AbstractView jsonView = new MappingJackson2JsonView();
        try {
            if (StringUtils.isEmpty(token) || StringUtils.isEmpty(userId)) {// 缺少参数
                jsonView.setAttributesMap(Constants.MAP_PARAM_MISS);
            } else {
                UserAuthParam param = new UserAuthParam();
                param.setUserId(userId);
                param.setToken(token);
                param.setAuthName(authName);
                param.setAuthIDCard(authIDCard);
                param.setAuthMob(authMob);
                param.setAuthFile(authFile);
                param.setAuthInfo(authInfo);
                param.setAuthFileWork(authFileWork);
                param.setModifyTime(System.currentTimeMillis());
                jsonView.setAttributesMap(accountInfoFacade.editAccountAuth(token, userId, param));
            }
        } catch (Exception e) {
            jsonView.setAttributesMap(Constants.MAP_500);
        }
        return new ModelAndView(jsonView);
    }

}
