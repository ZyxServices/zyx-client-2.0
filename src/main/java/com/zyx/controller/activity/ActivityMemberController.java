package com.zyx.controller.activity;


import com.zyx.constants.Constants;
import com.zyx.controller.system.InsertMsgRunnable;
import com.zyx.controller.system.MsgPool;
import com.zyx.param.account.UserMsgParam;
import com.zyx.param.activity.ActivityMemberParam;
import com.zyx.param.activity.QueryActivityMemberParam;
import com.zyx.rpc.account.AccountCommonFacade;
import com.zyx.rpc.activity.ActivityMemberFacade;
import com.zyx.rpc.system.MsgFacade;
import com.zyx.utils.ActivityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.AbstractView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by Rainbow on 16-6-13.
 *
 * @author SubDong
 * @version V1.0
 *          Copyright (c)2016 tyj-版权所有
 */
@RestController
@RequestMapping("/v2/member")
@Api(description = "活动报名接口。【1】活动报名。【2】通过活动id查询报名用户列表。【3】通过用户ID查询用户所报名的活动列表")
public class ActivityMemberController {

    @Resource
    private AccountCommonFacade accountCommonFacade;

    @Resource
    private ActivityMemberFacade activityMemberFacade;

    @Autowired
    private MsgFacade msgFacade;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ApiOperation(value = "活动报名", notes = "活动报名")
    public ModelAndView add(@RequestParam(name = "token", required = true) String token,
                            @ApiParam(required = true, name = "userId", value = "用户ID") @RequestParam(name = "userId", required = true) Integer userId,
                            @ApiParam(required = true, name = "activityId", value = "活动ID") @RequestParam(name = "activityId", required = true) Integer activityId,
                            @ApiParam(required = true, name = "activityUserId", value = "活动创建者ID") @RequestParam(name = "activityUserId", required = true) Integer activityUserId) {

        AbstractView jsonView = new MappingJackson2JsonView();
        boolean token1 = accountCommonFacade.validateToken(token);
        if (!token1) return new ModelAndView(ActivityUtils.tokenFailure());

        ActivityMemberParam param = new ActivityMemberParam();
        param.setActivityId(activityId);
        param.setUserId(userId);

        Map<String, Object> map = activityMemberFacade.insertMember(param);
        // 报名成功，进行消息提示
        checkAndSendMsg(map, userId, activityId, activityUserId);
        jsonView.setAttributesMap(map);
        return new ModelAndView(jsonView);
    }

    private void checkAndSendMsg(Map<String, Object> map, Integer userId, Integer activityId, Integer activityUserId) {
        try {
            Integer state = (Integer) map.get(Constants.STATE);
            if (null != state && state == Constants.SUCCESS) {
                MsgPool.getMsgPool().execute(new InsertMsgRunnable(msgFacade, buildUserMsgParam(userId, activityId, activityUserId, "报名了我的活动:")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private UserMsgParam buildUserMsgParam(Integer userId, Integer activityId, Integer activityUserId, String msg) {
        //TODO:联调的时候参数进行修改
        UserMsgParam param = new UserMsgParam();
        param.setFromUserId(userId);
        param.setToUserId(activityUserId);
        param.setFromContent(msg);
        param.setBodyId(activityId);
        param.setBodyType(4);
        param.setMsgType(2);
        param.setCreateTime(System.currentTimeMillis());
        return param;
    }

    @RequestMapping(value = "/findByActivityId", method = RequestMethod.POST)
    @ApiOperation(value = "通过活动id查询报名用户列表", notes = "通过活动id查询报名用户列表")
    public ModelAndView findByActivityId(@ApiParam(required = true, name = "activityId", value = "活动ID") @RequestParam(name = "activityId", required = true) Integer activityId,
                                         @ApiParam(required = true, name = "number", value = "每页显示多少条") @RequestParam(name = "number", required = true) Integer number,
                                         @ApiParam(required = true, name = "pageNumber", value = "当前第几页") @RequestParam(name = "pageNumber", required = true) Integer pageNumber) {

        AbstractView jsonView = new MappingJackson2JsonView();

        QueryActivityMemberParam param = new QueryActivityMemberParam();
        param.setActivityId(activityId);
        param.setNumber(number);
        param.setPageNumber(pageNumber);

        Map<String, Object> map = activityMemberFacade.findMemberById(param);
        jsonView.setAttributesMap(map);
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/findByUserId", method = RequestMethod.POST)
    @ApiOperation(value = "通过用户ID查询用户所报名的活动列表", notes = "通过用户ID查询用户所报名的活动列表")
    public ModelAndView findByUserId(@ApiParam(required = true, name = "userId", value = "用户ID") @RequestParam(name = "userId", required = true) Integer userId,
                                     @ApiParam(required = true, name = "number", value = "每页显示多少条") @RequestParam(name = "number", required = true) Integer number,
                                     @ApiParam(required = true, name = "pageNumber", value = "当前第几页") @RequestParam(name = "pageNumber", required = true) Integer pageNumber) {

        AbstractView jsonView = new MappingJackson2JsonView();
        QueryActivityMemberParam param = new QueryActivityMemberParam();
        param.setUserId(userId);
        param.setNumber(number);
        param.setPageNumber(pageNumber);

        Map<String, Object> map = activityMemberFacade.findMemberByUserId(param);
        jsonView.setAttributesMap(map);
        return new ModelAndView(jsonView);
    }


    @RequestMapping(value = "/delMember", method = RequestMethod.POST)
    @ApiOperation(value = "取消对应活动的报名", notes = "取消对应活动的报名")
    public ModelAndView delMember(@ApiParam(required = true, name = "userId", value = "用户ID") @RequestParam(name = "userId", required = true) Integer userId,
                                  @ApiParam(required = true, name = "activityId", value = "活动ID") @RequestParam(name = "activityId", required = true) Integer activityId) {

        AbstractView jsonView = new MappingJackson2JsonView();
        QueryActivityMemberParam param = new QueryActivityMemberParam();
        param.setUserId(userId);
        param.setActivityId(activityId);

        Map<String, Object> map = activityMemberFacade.delMember(param);
        jsonView.setAttributesMap(map);
        return new ModelAndView(jsonView);
    }
}
