package com.zyx.controller.system;

import com.zyx.annotation.TokenVerify;
import com.zyx.config.BaseResponse;
import com.zyx.constants.Constants;
import com.zyx.param.Pager;
import com.zyx.param.account.UserMsgParam;
import com.zyx.rpc.system.MsgFacade;
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
 * Created by wms on 2016/11/15.
 *
 * @author WeiMinSheng
 * @version V2.0
 *          Copyright (c)2016 tyj-版权所有
 * @since 2016/11/15.
 */
@RestController
@RequestMapping("/v2/msg")
@Api(description = "消息接口")
public class MsgController {
    @Autowired
    private MsgFacade msgFacade;

    @RequestMapping(value = "/info", method = {RequestMethod.DELETE})
    @ApiOperation(value = "删除消息", notes = "删除消息", response = BaseResponse.class)
    @TokenVerify(verifyType = TokenVerify.VerifyEnum.NORMAL)
    public ModelAndView deleteMsg(
            @ApiParam(required = true, name = "token", value = "token") @RequestParam(value = "token") String token,
            @ApiParam(required = true, name = "from_user_id", value = "当前用户ID") @RequestParam(value = "from_user_id") Integer fromUserId,
            @ApiParam(required = true, name = "msg_id", value = "消息ID") @RequestParam(value = "msg_id") Integer msgId) {
        AbstractView jsonView = new MappingJackson2JsonView();
        try {
            if (StringUtils.isEmpty(token) || StringUtils.isEmpty(fromUserId)) {// 缺少参数
                jsonView.setAttributesMap(Constants.MAP_PARAM_MISS);
            } else {
                UserMsgParam userMsgParam = new UserMsgParam();
                userMsgParam.setToken(token);
                userMsgParam.setFromUserId(fromUserId);
                userMsgParam.setId(msgId);
                jsonView.setAttributesMap(msgFacade.deleteMsg(userMsgParam));
                return new ModelAndView(jsonView);
            }
        } catch (Exception e) {
            e.printStackTrace();
            jsonView.setAttributesMap(Constants.MAP_500);
        }
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/setMsgState", method = {RequestMethod.POST})
    @ApiOperation(value = "设置消息为已读状态", notes = "设置消息为已读状态", response = BaseResponse.class)
    @TokenVerify(verifyType = TokenVerify.VerifyEnum.NORMAL)
    public ModelAndView setMsgState(
            @ApiParam(required = true, name = "token", value = "token") @RequestParam(value = "token") String token,
            @ApiParam(required = true, name = "user_id", value = "当前用户ID") @RequestParam(value = "user_id") Integer userId,
            @ApiParam(required = true, name = "msg_type", value = "消息类型") @RequestParam(value = "msg_type") Integer msgType,
            @ApiParam(required = true, name = "create_time", value = "创建时间") @RequestParam(value = "create_time") Long createTime) {
        AbstractView jsonView = new MappingJackson2JsonView();
        try {
            if (StringUtils.isEmpty(token)) {// 缺少参数
                jsonView.setAttributesMap(Constants.MAP_PARAM_MISS);
            } else {
                UserMsgParam userMsgParam = new UserMsgParam();
                userMsgParam.setToken(token);
                userMsgParam.setToUserId(userId);
                userMsgParam.setMsgType(msgType);
                userMsgParam.setCreateTime(createTime);
                jsonView.setAttributesMap(msgFacade.setMsgState(userMsgParam));
                return new ModelAndView(jsonView);
            }
        } catch (Exception e) {
            e.printStackTrace();
            jsonView.setAttributesMap(Constants.MAP_500);
        }
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/count", method = {RequestMethod.GET})
    @ApiOperation(value = "消息数量", notes = "消息数量", response = BaseResponse.class)
    @TokenVerify(verifyType = TokenVerify.VerifyEnum.NORMAL)
    public ModelAndView queryMsgCount(
            @ApiParam(required = true, name = "token", value = "token") @RequestParam(value = "token") String token,
            @ApiParam(required = true, name = "user_id", value = "当前用户ID") @RequestParam(value = "user_id") Integer toUserId,
            @ApiParam(name = "msg_type", value = "消息类型") @RequestParam(value = "msg_type", required = false) Integer msgType) {
        AbstractView jsonView = new MappingJackson2JsonView();
        try {
            if (StringUtils.isEmpty(token) || StringUtils.isEmpty(toUserId)) {// 缺少参数
                jsonView.setAttributesMap(Constants.MAP_PARAM_MISS);
            } else {
                UserMsgParam userMsgParam = new UserMsgParam();
                userMsgParam.setToken(token);
                userMsgParam.setToUserId(toUserId);
                userMsgParam.setMsgType(msgType);
                jsonView.setAttributesMap(msgFacade.queryMsgCount(userMsgParam));
                return new ModelAndView(jsonView);
            }
        } catch (Exception e) {
            e.printStackTrace();
            jsonView.setAttributesMap(Constants.MAP_500);
        }
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/list", method = {RequestMethod.GET})
    @ApiOperation(value = "消息列表", notes = "消息列表", response = BaseResponse.class)
    @TokenVerify(verifyType = TokenVerify.VerifyEnum.NORMAL)
    public ModelAndView queryMsgList(
            @ApiParam(required = true, name = "token", value = "token") @RequestParam(value = "token") String token,
            @ApiParam(required = true, name = "user_id", value = "当前用户ID") @RequestParam(value = "user_id") Integer toUserId,
            @ApiParam(required = true, name = "msg_type", value = "消息类型") @RequestParam(value = "msg_type") Integer msgType,
            @ApiParam(name = "page", value = "页数") @RequestParam(required = false) Integer page,
            @ApiParam(name = "pageSize", value = "每页显示的记录数") @RequestParam(required = false) Integer pageSize) {
        AbstractView jsonView = new MappingJackson2JsonView();
        try {
            if (StringUtils.isEmpty(token) || StringUtils.isEmpty(toUserId)) {// 缺少参数
                jsonView.setAttributesMap(Constants.MAP_PARAM_MISS);
            } else {
                UserMsgParam userMsgParam = new UserMsgParam();
                userMsgParam.setToken(token);
                userMsgParam.setToUserId(toUserId);
                userMsgParam.setMsgType(msgType);
                userMsgParam.setPager(new Pager(page, pageSize));
                jsonView.setAttributesMap(msgFacade.queryMsgList(userMsgParam));
                return new ModelAndView(jsonView);
            }
        } catch (Exception e) {
            e.printStackTrace();
            jsonView.setAttributesMap(Constants.MAP_500);
        }
        return new ModelAndView(jsonView);
    }
}