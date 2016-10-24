package com.zyx.controller.account;

import com.zyx.constants.Constants;
import com.zyx.param.attention.AttentionParam;
import com.zyx.rpc.attention.UserAttentionFacade;
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
 * Created by wms on 2016/8/17.
 *
 * @author WeiMinSheng
 * @version V1.0
 *          Copyright (c)2016 tyj-版权所有
 */
@RestController
@RequestMapping("/v1/my/attention")
@Api(description = "我的关注/粉丝相关接口。1、查询我的关注列表。2、查询我的粉丝列表。")
public class MyAttentionController {

    @Autowired
    private UserAttentionFacade userAttentionFacade;

    @RequestMapping(value = "/from", method = RequestMethod.GET)
    @ApiOperation(value = "我的关注列表", notes = "我的关注列表")
    public ModelAndView myGZList(@RequestParam String token, @RequestParam Integer accountId) {
        AbstractView jsonView = new MappingJackson2JsonView();

        if (StringUtils.isEmpty(token) || StringUtils.isEmpty(accountId)) {// 缺少参数
            jsonView.setAttributesMap(Constants.MAP_PARAM_MISS);
        } else {
            jsonView.setAttributesMap(doMyGZList(token, accountId));
        }
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/dk/from", method = RequestMethod.GET)
    @ApiOperation(value = "我的关注大咖列表", notes = "我的关注大咖列表")
    public ModelAndView myDKGZList(@RequestParam String token, @RequestParam Integer accountId) {
        AbstractView jsonView = new MappingJackson2JsonView();

        if (StringUtils.isEmpty(token) || StringUtils.isEmpty(accountId)) {// 缺少参数
            jsonView.setAttributesMap(Constants.MAP_PARAM_MISS);
        } else {
            jsonView.setAttributesMap(doMyDKGZList(token, accountId));
        }
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/to", method = RequestMethod.GET)
    @ApiOperation(value = "我的粉丝列表", notes = "我的粉丝列表")
    public ModelAndView myFSList(@RequestParam String token, @RequestParam Integer accountId) {
        AbstractView jsonView = new MappingJackson2JsonView();

        if (StringUtils.isEmpty(token) || StringUtils.isEmpty(accountId)) {// 缺少参数
            jsonView.setAttributesMap(Constants.MAP_PARAM_MISS);
        } else {
            jsonView.setAttributesMap(doMyFSList(token, accountId));
        }

        return new ModelAndView(jsonView);
    }

    private Map<String, Object> doMyGZList(String token, Integer accountId) {
        try {
            return userAttentionFacade.myGZList(getAttentionParam(token, accountId, null));
        } catch (Exception e) {
            return Constants.MAP_500;
        }
    }

    private Map<String, Object> doMyDKGZList(String token, Integer accountId) {
        try {
            return userAttentionFacade.myDKGZList(getAttentionParam(token, accountId, null));
        } catch (Exception e) {
            return Constants.MAP_500;
        }
    }

    private Map<String, Object> doMyFSList(String token, Integer accountId) {
        try {
            return userAttentionFacade.myFSList(getAttentionParam(token, null, accountId));
        } catch (Exception e) {
            return Constants.MAP_500;
        }
    }

    private AttentionParam getAttentionParam(String token, Integer fromId, Integer toId) {
        AttentionParam attentionParam = new AttentionParam();
        attentionParam.setToken(token);
        attentionParam.setFromId(fromId);
        attentionParam.setToId(toId);
        return attentionParam;
    }


}
