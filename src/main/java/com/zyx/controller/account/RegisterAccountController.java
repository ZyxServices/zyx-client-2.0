package com.zyx.controller.account;

import com.zyx.constants.account.AccountConstants;
import com.zyx.param.account.UserLoginParam;
import com.zyx.rpc.account.AccountRegisterFacade;
import com.zyx.utils.FileUploadUtils;
import com.zyx.utils.ImagesVerifyUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.AbstractView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.Map;

/**
 * Created by wms on 2016/11/7.
 *
 * @author WeiMinSheng
 * @version V2.0
 *          Copyright (c)2016 tyj-版权所有
 * @since 2016/11/7
 */
@RestController
@RequestMapping("/v2/account")
@Api(description = "用户注册接口API。1、验证手机验证码。2、注册")
public class RegisterAccountController {

    @Autowired
    private AccountRegisterFacade accountRegisterFacade;

    @RequestMapping(value = "/validate/code", method = RequestMethod.POST)
    @ApiOperation(value = "验证手机验证码", notes = "验证手机号和验证码是否匹配")
    public ModelAndView validatePhoneCode(@RequestParam(name = "phone") String phone,
                                          @ApiParam(required = true, name = "code", value = "验证码")
                                          @RequestParam(name = "code") String code) {
        AbstractView jsonView = new MappingJackson2JsonView();

        if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(code)) {
            jsonView.setAttributesMap(AccountConstants.MAP_PARAM_MISS);
        } else {
            try {
                UserLoginParam userLoginParam = new UserLoginParam();
                userLoginParam.setPhone(phone);
                userLoginParam.setCode(code);
                jsonView.setAttributesMap(accountRegisterFacade.validatePhoneCode(userLoginParam));
            } catch (Exception e) {
                jsonView.setAttributesMap(AccountConstants.MAP_500);
            }
        }

        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ApiOperation(value = "用户注册", notes = "用户注册，头像可不设置")
    public ModelAndView register(@RequestParam(name = "phone") String phone,
                                 @RequestParam(name = "pwd") String password,
//                                 @RequestParam(name = "code") String code,
                                 @RequestParam(name = "nickname") String nickname,
                                 @RequestPart(name = "avatar", required = false) MultipartFile avatar) {
        AbstractView jsonView = new MappingJackson2JsonView();

        if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(password) || StringUtils.isEmpty(nickname)) {
            jsonView.setAttributesMap(AccountConstants.MAP_PARAM_MISS);
        } else {
            try {
                if (avatar != null) {// 用户上传头像
                    String avatarId = FileUploadUtils.uploadFile(avatar);
                    Map<String, Object> map = ImagesVerifyUtils.verify(avatarId);
                    if (map != null) {
                        jsonView.setAttributesMap(map);
                    } else {
                        UserLoginParam userLoginParam = new UserLoginParam();
                        userLoginParam.setPhone(phone);
                        userLoginParam.setPassword(password);
//                    userLoginParam.setCode(code);
                        userLoginParam.setAvatar(avatarId);
                        userLoginParam.setNickname(nickname);
                        jsonView.setAttributesMap(accountRegisterFacade.registerAccount(userLoginParam));
                    }
                } else {
                    UserLoginParam userLoginParam = new UserLoginParam();
                    userLoginParam.setPhone(phone);
                    userLoginParam.setPassword(password);
//                userLoginParam.setCode(code);
                    userLoginParam.setNickname(nickname);
                    jsonView.setAttributesMap(accountRegisterFacade.registerAccount(userLoginParam));
                }
            } catch (Exception e) {
                jsonView.setAttributesMap(AccountConstants.MAP_500);
            }
        }

        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/register2", method = RequestMethod.POST)
    @ApiOperation(value = "用户注册", notes = "用户注册，头像可不设置")
    public ModelAndView register2(@RequestParam(name = "phone") String phone,
                                  @RequestParam(name = "pwd") String password,
                                  @ApiParam(name = "nickname", value = "昵称")
                                  @RequestParam(name = "nickname") String nickname,
                                  @ApiParam(name = "avatar", value = "头像地址，需要先使用文件上传接口上传获取地址")
                                  @RequestParam(name = "avatar", required = false) String avatar) {
        AbstractView jsonView = new MappingJackson2JsonView();

        if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(password) || StringUtils.isEmpty(nickname)) {
            jsonView.setAttributesMap(AccountConstants.MAP_PARAM_MISS);
        } else {
            try {
                UserLoginParam userLoginParam = new UserLoginParam();
                userLoginParam.setPhone(phone);
                userLoginParam.setPassword(password);
                userLoginParam.setNickname(nickname);
                userLoginParam.setAvatar(avatar);
                jsonView.setAttributesMap(accountRegisterFacade.registerAccount(userLoginParam));
            } catch (Exception e) {
                jsonView.setAttributesMap(AccountConstants.MAP_500);
            }
        }

        return new ModelAndView(jsonView);
    }

}