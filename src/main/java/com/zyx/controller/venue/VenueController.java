package com.zyx.controller.venue;

import com.zyx.constants.Constants;
import com.zyx.entity.venue.Venue;
import com.zyx.param.venue.VenueParam;
import com.zyx.rpc.record.SportRecordFacade;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by MrDeng on 2016/11/9.
 * 场馆相关的接口
 */
@RestController
@RequestMapping(value = "/v2/venue")
@Api(description = "场馆相关接口")
public class VenueController {
    @Autowired
    SportRecordFacade sportRecordFacade;

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ApiOperation(value = "记录-获取用户运动总览概况", notes = "获取用户运动总览概况")
    public ModelAndView getSelfRecordOverview(
            @ApiParam(required = true, name = "latitude", value = "纬度")@RequestParam(name = "latitude", required = true) Double latitude,
            @ApiParam(required = true, name = "longitude", value = "经度")@RequestParam(name = "longitude", required = true) Double longitude,
            @ApiParam(required = true, name = "radius", value = "经纬度半径范围")@RequestParam(name = "radius", required = true) Double radius) {
        AbstractView jsonView = new MappingJackson2JsonView();
        Map<String, Object> attrMap = new HashMap<>();
        VenueParam param = new VenueParam();
        param.setLatitude(latitude);
        param.setLongitude(longitude);
        param.setRadius(radius);
        List<Venue> list = sportRecordFacade.getVenues(param);
        attrMap.put(Constants.STATE, Constants.SUCCESS);
        attrMap.put(Constants.SUCCESS_MSG, Constants.MSG_SUCCESS);
        attrMap.put(Constants.DATA, list);
        jsonView.setAttributesMap(attrMap);
        return new ModelAndView(jsonView);
    }
}
