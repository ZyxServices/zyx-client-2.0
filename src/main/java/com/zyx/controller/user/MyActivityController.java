package com.zyx.controller.user;

import com.zyx.constants.user.UserConstants;
import com.zyx.param.activity.MyActivityListParam;
import com.zyx.rpc.account.AccountCommonFacade;
import com.zyx.rpc.activity.ActivityFacade;
import com.zyx.utils.ActivityUtils;
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
@RequestMapping("/v2/my/activity")
@Api(description = "我的活动接口。【1】查询我的活动列表。")
public class MyActivityController {

    @Autowired
    private ActivityFacade activityFacade;

    @Autowired
    private AccountCommonFacade accountCommonFacade;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ApiOperation(value = "我的活动列表", notes = "我的活动列表")
    public ModelAndView myActivityList(@RequestParam String token, @RequestParam Integer accountId, @RequestParam Integer page, @RequestParam Integer pageSize) {
        AbstractView jsonView = new MappingJackson2JsonView();

        if (StringUtils.isEmpty(token)) {// 缺少参数
            jsonView.setAttributesMap(UserConstants.MAP_PARAM_MISS);
        } else {
            try {
                // 验证token
                if (!accountCommonFacade.validateToken(token)) {
                    return new ModelAndView(ActivityUtils.tokenFailure());
                }
                jsonView.setAttributesMap(activityFacade.myActivityList(buildParam(accountId, page, pageSize)));
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


}
