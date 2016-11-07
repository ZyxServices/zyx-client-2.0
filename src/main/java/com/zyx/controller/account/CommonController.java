package com.zyx.controller.account;

import com.zyx.constants.account.AccountConstants;
import com.zyx.rpc.account.AccountCommonFacade;
import com.zyx.utils.MapUtils;
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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wms on 2016/11/7.
 *
 * @author WeiMinSheng
 * @version V2.0
 *          Copyright (c)2016 tyj-版权所有
 * @since 2016/11/7
 */
@RestController
@RequestMapping("/v2")
@Api(description = "用户公共接口API。1、同步服务器时间戳。2、发送验证码")
public class CommonController {

    @Autowired
    private AccountCommonFacade accountCommonFacade;

    @RequestMapping(value = "/timestamp", method = RequestMethod.GET)
    @ApiOperation(value = "同步服务器时间戳", notes = "同步服务器时间戳，返回两种类型的结果1、1466648250174。2、20160623101730")
    public ModelAndView timestamp() {
        AbstractView jsonView = new MappingJackson2JsonView();
        try {
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            String str = sdf.format(date);
            Map<String, Object> map = new HashMap<>();
            map.put(AccountConstants.TIMESTAMP_LONG, System.currentTimeMillis());
            map.put(AccountConstants.TIMESTAMP_STRING, str);
            jsonView.setAttributesMap(map);
        } catch (Exception e) {
            e.printStackTrace();
            jsonView.setAttributesMap(AccountConstants.MAP_500);
        }
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/sendRegisterCode", method = RequestMethod.POST)
    @ApiOperation(value = "发送验证码", notes = "发送验证码")
    public ModelAndView sendRegisterCode(@RequestParam(name = "phone") String phone) {
        AbstractView jsonView = new MappingJackson2JsonView();

        if (StringUtils.isEmpty(phone)) {
            jsonView.setAttributesMap(AccountConstants.MAP_PARAM_MISS);
        } else {
            try {
                jsonView.setAttributesMap(doSendPhone(phone, AccountConstants.SEND_REGISTER, null));
            } catch (Exception e) {
                e.printStackTrace();
                jsonView.setAttributesMap(AccountConstants.MAP_500);
            }
        }
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/sendCode", method = RequestMethod.POST)
    @ApiOperation(value = "发送验证码", notes = "发送验证码")
    public ModelAndView sendCode(@RequestParam(name = "phone") String phone) {
        AbstractView jsonView = new MappingJackson2JsonView();
        if (StringUtils.isEmpty(phone)) {
            jsonView.setAttributesMap(AccountConstants.MAP_PARAM_MISS);
        } else {
            try {
                jsonView.setAttributesMap(doSendPhone(phone, AccountConstants.SEND_PUBLIC, null));
            } catch (Exception e) {
                e.printStackTrace();
                jsonView.setAttributesMap(AccountConstants.MAP_500);
            }
        }
        return new ModelAndView(jsonView);
    }

    private Map<String, Object> doSendPhone(String phone, String type, String msg) {
        // 判断手机号码
        return isMobileNum(phone) ? accountCommonFacade.sendPhoneCode(phone, type, msg) : MapUtils.buildErrorMap(AccountConstants.ACCOUNT_ERROR_CODE_40100, AccountConstants.ACCOUNT_ERROR_CODE_40100_MSG);
    }

    private boolean isMobileNum(String mobiles) {
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }
}
