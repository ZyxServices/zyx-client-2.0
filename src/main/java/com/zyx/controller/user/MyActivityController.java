package com.zyx.controller.user;

import com.zyx.annotation.TokenVerify;
import com.zyx.constants.user.UserConstants;
import com.zyx.param.activity.MyActivityListParam;
import com.zyx.param.activity.QueryActivityMemberParam;
import com.zyx.rpc.activity.ActivityFacade;
import com.zyx.rpc.activity.ActivityMemberFacade;
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

import javax.annotation.Resource;

/**
 * Created by wms on 2016/11/8.
 *
 * @author WeiMinSheng
 * @version V2.0
 *          Copyright (c)2016 tyj-版权所有
 * @since 2016/11/8
 */
@RestController
@RequestMapping("/v2/my/activity")
@Api(description = "我的活动接口。【1】查询我发布的活动列表。【2】查询我参与的活动列表。")
public class MyActivityController {

    @Autowired
    private ActivityFacade activityFacade;

    @Resource
    private ActivityMemberFacade activityMemberFacade;

    @RequestMapping(value = "/create/list", method = RequestMethod.GET)
    @ApiOperation(value = "我发起的活动列表", notes = "我发起的活动列表")
    @TokenVerify(verifyType = TokenVerify.VerifyEnum.MINE)
    public ModelAndView myCreateActivityList(@RequestParam String token, @RequestParam Integer accountId, @RequestParam Integer page, @RequestParam Integer pageSize) {
        AbstractView jsonView = new MappingJackson2JsonView();

        if (StringUtils.isEmpty(token)) {// 缺少参数
            jsonView.setAttributesMap(UserConstants.MAP_PARAM_MISS);
        } else {
            try {
                jsonView.setAttributesMap(activityFacade.myActivityList(buildParam(accountId, page, pageSize)));
            } catch (Exception e) {
                e.printStackTrace();
                jsonView.setAttributesMap(UserConstants.MAP_500);
            }
        }

        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/member/list", method = RequestMethod.GET)
    @ApiOperation(value = "我参与的活动列表", notes = "我参与的活动列表")
    public ModelAndView myMemberActivityList(@RequestParam String token, @RequestParam Integer accountId, @RequestParam Integer page, @RequestParam Integer pageSize) {
        AbstractView jsonView = new MappingJackson2JsonView();

        if (StringUtils.isEmpty(token)) {// 缺少参数
            jsonView.setAttributesMap(UserConstants.MAP_PARAM_MISS);
        } else {
            try {
                jsonView.setAttributesMap(activityMemberFacade.findMemberByUserId(buildMemberParam(accountId, page, pageSize)));
            } catch (Exception e) {
                e.printStackTrace();
                jsonView.setAttributesMap(UserConstants.MAP_500);
            }
        }

        return new ModelAndView(jsonView);
    }

    private MyActivityListParam buildParam(Integer accountId, Integer page, Integer pageSize) {
        MyActivityListParam param = new MyActivityListParam();
        param.setUserId(accountId);
        param.setNumber(pageSize);
        param.setPageNumber(page);
        return param;
    }

    private QueryActivityMemberParam buildMemberParam(Integer accountId, Integer page, Integer pageSize) {
        QueryActivityMemberParam param = new QueryActivityMemberParam();
        param.setUserId(accountId);
        param.setNumber(pageSize);
        param.setPageNumber(page);
        return param;
    }

}
