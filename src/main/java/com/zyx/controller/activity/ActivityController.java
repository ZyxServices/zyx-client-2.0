package com.zyx.controller.activity;


import com.zyx.constants.Constants;
import com.zyx.entity.activity.Activity;
import com.zyx.param.activity.ActivityParam;
import com.zyx.param.activity.QueryActivityParam;
import com.zyx.rpc.account.AccountCommonFacade;
import com.zyx.utils.ActivityUtils;
import com.zyx.rpc.activity.ActivityFacade;
import com.zyx.utils.MapUtils;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;
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
@RequestMapping("/v1/activity")
public class ActivityController {

    @Resource
    private ActivityFacade activityFacade;

    @RequestMapping(value = "/release", method = RequestMethod.POST)
    @ApiOperation(value = "活动发布", notes = "活动发布")
    public ModelAndView release(@RequestParam(name = "token", required = true) String token,
                                @ApiParam(required = true, name = "userId", value = "用户ID") @RequestParam(name = "userId", required = true) Integer userId,
                                @ApiParam(required = true, name = "imageUrl", value = "封面图片") @RequestParam(name = "imageUrl", required = true) String imageUrl,
                                @ApiParam(required = true, name = "title", value = "标题") @RequestParam(name = "title", required = true) String title,
                                @ApiParam(required = true, name = "descimage", value = "活动内容中的图片") @RequestParam(name = "descimage", required = false) String[] descimage,
                                @ApiParam(required = true, name = "desc", value = "活动内容") @RequestParam(name = "desc", required = true) String desc,
                                @ApiParam(required = true, name = "type", value = "活动类型(1、求约 2、求带)") @RequestParam(name = "type", required = true) Integer type,
                                @ApiParam(required = true, name = "startTime", value = "活动开始时间") @RequestParam(name = "startTime", required = true) Long startTime,
                                @ApiParam(required = true, name = "endTime", value = "活动结束时间") @RequestParam(name = "endTime", required = true) Long endTime,
                                @ApiParam(required = true, name = "lastTime", value = "报名结束时间") @RequestParam(name = "lastTime", required = true) Long lastTime,
                                @ApiParam(required = true, name = "address", value = "集合地址") @RequestParam(name = "address", required = true) String address,
                                @ApiParam(required = true, name = "maxPeople", value = "报名人数限制(0 为不限制)") @RequestParam(name = "maxPeople", required = true) Integer maxPeople,
                                @ApiParam(required = true, name = "price", value = "价格") @RequestParam(name = "price", required = true) Double price,
                                @ApiParam(required = true, name = "city", value = "城市") @RequestParam(name = "city", required = true) String city) {

        AbstractView jsonView = new MappingJackson2JsonView();

        if (descimage != null && descimage.length >= 0) {
            String htmlImage = "";
            desc = desc + "<br/>" + htmlImage;
            for (String s : descimage) {
                String html = "<img src=http://image.tiyujia.com/" + s + "/><br/>";
                htmlImage += html;
            }
        }
        if (imageUrl == null || imageUrl.equals("")) {
            jsonView.setAttributesMap(MapUtils.buildErrorMap(Constants.PARAM_MISS, "参数缺失"));
            return new ModelAndView(jsonView);
        } else {
            ActivityParam activityParam = new ActivityParam();
            activityParam.setUserId(userId);
            activityParam.setImgUrls(imageUrl);
            activityParam.setTitle(title);
            activityParam.setDesc(desc);
            activityParam.setType(type);
            activityParam.setStartTime(startTime);
            activityParam.setEndTime(endTime);
            activityParam.setLastTime(lastTime);
            activityParam.setAddress(address);
            activityParam.setMaxPepople(maxPeople);
            activityParam.setPrice(price);

            Map<String, Object> map = activityFacade.insertActivity(activityParam);
            jsonView.setAttributesMap(map);
            return new ModelAndView(jsonView);
        }
    }

    @RequestMapping(value = "/query", method = RequestMethod.POST)
    @ApiOperation(value = "活动查询筛选", notes = "活动查询筛选")
    public ModelAndView query(@ApiParam(required = true, name = "state", value = "状态（0、全部  1、正在报名 2、已结束）") @RequestParam(name = "state", required = true) Integer state,
                              @ApiParam(required = true, name = "type", value = "类型（0、全部 1、求约 2、求带）") @RequestParam(name = "type", required = true) Integer type,
                              @ApiParam(required = true, name = "number", value = "每页显示条数") @RequestParam(name = "number", required = true) Integer number,
                              @ApiParam(required = true, name = "pageNumber", value = "当前第几页") @RequestParam(name = "pageNumber", required = true) Integer pageNumber) {

        AbstractView jsonView = new MappingJackson2JsonView();

        QueryActivityParam queryActivityParam = new QueryActivityParam();
        queryActivityParam.setActivityState(state);
        queryActivityParam.setActivityType(type);
        queryActivityParam.setNumber(number);
        queryActivityParam.setPageNumber(pageNumber);

        Map<String, Object> map = activityFacade.queryActivity(queryActivityParam);
        jsonView.setAttributesMap(map);
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/activityById", method = RequestMethod.POST)
    @ApiOperation(value = "通过活动ID查询活动详情信息", notes = "通过活动ID查询活动详情信息")
    public ModelAndView activityById(@RequestParam(name = "activityId", required = true) Integer activityId) {

        AbstractView jsonView = new MappingJackson2JsonView();
        Map<String, Object> map = activityFacade.activityById(activityId);
        jsonView.setAttributesMap(map);
        return new ModelAndView(jsonView);
    }


}
