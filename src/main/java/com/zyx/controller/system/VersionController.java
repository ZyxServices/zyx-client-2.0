package com.zyx.controller.system;

import java.util.List;
import com.zyx.constants.Constants;
import com.zyx.entity.system.Version;
import com.zyx.rpc.system.VersionFacade;
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
import java.util.Map;

/**
 * Created by MrDeng on 2016/11/10.
 */

@RestController
@RequestMapping(value = "/v2/vesion")
@Api(description = "记录相关接口")
public class VersionController {

    @Autowired
    VersionFacade versionFacade;

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    @ApiOperation(value = "记录-获取最新版本信息", notes = "获取最新版本信息")
    public ModelAndView getNewVersion(
            @ApiParam(required = true, name = "type", value = "类型 1-正式发布版 2-内测版本 3-公测版本") @RequestParam(name = "type", required = true) Integer type,
            @ApiParam(required = true, name = "platform", value = "平台 1-Android 2-IOS") @RequestParam(name = "platform", required = true) Integer platform) {
        AbstractView jsonView = new MappingJackson2JsonView();
        Map<String, Object> attrMap = new HashMap<>();
        Version version = versionFacade.getNewVersion(type, platform);
        attrMap.put(Constants.STATE, Constants.SUCCESS);
        attrMap.put(Constants.SUCCESS_MSG, Constants.MSG_SUCCESS);
        attrMap.put(Constants.DATA, version);
        jsonView.setAttributesMap(attrMap);
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/history", method = RequestMethod.POST)
    @ApiOperation(value = "记录-获取历史版本信息", notes = "获取历史版本信息")
    public ModelAndView getHistoryVersion(
            @ApiParam(required = true, name = "type", value = "类型 1-正式发布版 2-内测版本 3-公测版本") @RequestParam(name = "type", required = true) Integer type,
            @ApiParam(required = true, name = "platform", value = "平台 1-Android 2-IOS") @RequestParam(name = "platform", required = true) Integer platform) {
        AbstractView jsonView = new MappingJackson2JsonView();
        Map<String, Object> attrMap = new HashMap<>();
        List<Version> list = versionFacade.getHistoryVersion(type, platform);
        attrMap.put(Constants.STATE, Constants.SUCCESS);
        attrMap.put(Constants.SUCCESS_MSG, Constants.MSG_SUCCESS);
        attrMap.put(Constants.DATA, list);
        jsonView.setAttributesMap(attrMap);
        return new ModelAndView(jsonView);
    }
}
