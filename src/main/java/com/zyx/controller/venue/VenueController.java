package com.zyx.controller.venue;

import com.zyx.constants.Constants;
import com.zyx.entity.venue.Venue;
import com.zyx.param.venue.VenueParam;
import com.zyx.rpc.record.SportRecordFacade;
import com.zyx.rpc.venue.VenueFacade;
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
    VenueFacade venueFacade;

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ApiOperation(value = "场馆-获取定位点半径范围内场馆信息", notes = "获取定位点半径范围内场馆信息")
    public ModelAndView getSelfRecordOverview(
            @ApiParam(required = true, name = "latitude", value = "纬度") @RequestParam(name = "latitude", required = true) Double latitude,
            @ApiParam(required = true, name = "longitude", value = "经度") @RequestParam(name = "longitude", required = true) Double longitude,
            @ApiParam(required = true, name = "radius", value = "经纬度半径范围") @RequestParam(name = "radius", required = true) Double radius
    ) {
        AbstractView jsonView = new MappingJackson2JsonView();
        Map<String, Object> attrMap = new HashMap<>();
        VenueParam param = new VenueParam();
        param.setLatitude(latitude);
        param.setLongitude(longitude);
        param.setRadius(radius);
        List<Venue> list = venueFacade.getVenues(param);
        attrMap.put(Constants.STATE, Constants.SUCCESS);
        attrMap.put(Constants.SUCCESS_MSG, Constants.MSG_SUCCESS);
        attrMap.put(Constants.DATA, list);
        jsonView.setAttributesMap(attrMap);
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ApiOperation(value = "记录-获取用户运动总览概况", notes = "获取用户运动总览概况")
    public ModelAndView uploadVenue(
            @ApiParam(required = true, name = "type", value = "场馆类型 1-室内 2-室外") @RequestParam(name = "type", required = true) Integer type,
            @ApiParam(required = true, name = "name", value = "场馆名称") @RequestParam(name = "name", required = true) String name,
            @ApiParam(required = true, name = "latitude", value = "纬度") @RequestParam(name = "latitude", required = true) Double latitude,
            @ApiParam(required = true, name = "longitude", value = "经度") @RequestParam(name = "longitude", required = true) Double longitude,
            @ApiParam(required = true, name = "city", value = "场馆所属城市") @RequestParam(name = "city", required = true) String city,
            @ApiParam(required = true, name = "mark", value = "标签") @RequestParam(name = "mark", required = true) String mark,
            @ApiParam(required = false, name = "description", value = "描述") @RequestParam(name = "description", required = false) String description,
            @ApiParam(required = false, name = "phone", value = "联系电话") @RequestParam(name = "phone", required = false) String phone,
            @ApiParam(required = false, name = "address", value = "地址") @RequestParam(name = "address", required = false) String address,
            @ApiParam(required = false, name = "imgUrls", value = "图片") @RequestParam(name = "imgUrls", required = false) String imgUrls
    ) {
        AbstractView jsonView = new MappingJackson2JsonView();
        Map<String, Object> attrMap = new HashMap<>();
        Venue entity = new Venue();
        entity.setType(type);
        entity.setName(name);
        entity.setLongitude(longitude);
        entity.setLatitude(latitude);
        entity.setCreateTime(System.currentTimeMillis());
        entity.setCity(city);
        entity.setMark(mark);
        entity.setDescription(description);
        entity.setPhone(phone);
        entity.setAddress(address);
        entity.setImgUrls(imgUrls);
        venueFacade.uploadVenue(entity);
        attrMap.put(Constants.STATE, Constants.SUCCESS);
        attrMap.put(Constants.SUCCESS_MSG, Constants.MSG_SUCCESS);
        jsonView.setAttributesMap(attrMap);
        return new ModelAndView(jsonView);
    }
}
