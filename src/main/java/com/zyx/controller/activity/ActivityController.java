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
                                @RequestParam(name = "userId", required = true) Integer userId,
                                @RequestParam(name = "imageUrl", required = true) String imageUrl,
                                @RequestParam(name = "title", required = true) String title,
                                @RequestParam(name = "descimage", required = false) String[] descimage,
                                @RequestParam(name = "desc", required = true) String desc,
                                @RequestParam(name = "type", required = true) Integer type,
                                @RequestParam(name = "startTime", required = true) Long startTime,
                                @RequestParam(name = "endTime", required = true) Long endTime,
                                @RequestParam(name = "lastTime", required = true) Long lastTime,
                                @RequestParam(name = "address", required = true) String address,
                                @RequestParam(name = "maxPeople", required = true) Integer maxPeople,
                                @RequestParam(name = "price", required = true) Double price) {

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
    @ApiOperation(value = "活动发布", notes = "活动发布")
    public ModelAndView release(@RequestParam(name = "state", required = true) Integer state,
                                @RequestParam(name = "type", required = true) Integer type,
                                @RequestParam(name = "number", required = true) Integer number,
                                @RequestParam(name = "pageNumber", required = true) Integer pageNumber) {

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


}
