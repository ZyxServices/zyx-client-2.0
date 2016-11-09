package com.zyx.controller.activity;


import com.zyx.constants.Constants;
import com.zyx.entity.activity.ActivityMember;
import com.zyx.param.activity.ActivityMemberParam;
import com.zyx.param.activity.ActivityParam;
import com.zyx.param.activity.QueryActivityMemberParam;
import com.zyx.param.activity.QueryActivityParam;
import com.zyx.rpc.activity.ActivityFacade;
import com.zyx.rpc.activity.ActivityMemberFacade;
import com.zyx.utils.MapUtils;
import io.swagger.annotations.ApiOperation;
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
@RequestMapping("/v1/member")
public class ActivityMemberController {

    @Resource
    private ActivityMemberFacade activityMemberFacade;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ApiOperation(value = "活动报名", notes = "活动报名")
    public ModelAndView add(@RequestParam(name = "token", required = true) String token,
                                @RequestParam(name = "userId", required = true) Integer userId,
                                @RequestParam(name = "activityId", required = true) Integer activityId) {

        AbstractView jsonView = new MappingJackson2JsonView();

        ActivityMemberParam param = new ActivityMemberParam();
        param.setActivityId(activityId);
        param.setUserId(userId);

        Map<String, Object> map = activityMemberFacade.insertMember(param);
        jsonView.setAttributesMap(map);
        return new ModelAndView(jsonView);
    }


    @RequestMapping(value = "/findByActivityId", method = RequestMethod.POST)
    @ApiOperation(value = "通过活动id查询报名用户列表", notes = "通过活动id查询报名用户列表")
    public ModelAndView findByActivityId(@RequestParam(name = "activityId", required = true) Integer activityId,
                                @RequestParam(name = "number", required = true) Integer number,
                                @RequestParam(name = "pageNumber", required = true) Integer pageNumber) {

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
    public ModelAndView findByUserId(@RequestParam(name = "userId", required = true) Integer userId,
                                @RequestParam(name = "number", required = true) Integer number,
                                @RequestParam(name = "pageNumber", required = true) Integer pageNumber) {

        AbstractView jsonView = new MappingJackson2JsonView();
        QueryActivityMemberParam param = new QueryActivityMemberParam();
        param.setUserId(userId);
        param.setNumber(number);
        param.setPageNumber(pageNumber);

        Map<String, Object> map = activityMemberFacade.findMemberByUserId(param);
        jsonView.setAttributesMap(map);
        return new ModelAndView(jsonView);
    }


}