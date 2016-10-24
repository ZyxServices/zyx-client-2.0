package com.zyx.controller.system;

import com.zyx.constants.Constants;
import com.zyx.constants.live.LiveConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.AbstractView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by MrDeng on 2016/8/19.
 */
@RestController
@RequestMapping("/v1/sys")
@Api(description = "首推相关接口")
public class SystemController {
    @RequestMapping(value = "/time", method = {RequestMethod.GET})
    @ApiOperation(value = "系统-获取系统时间", notes = "系统-获取系统时间")
    public ModelAndView getSystemTime() {
        Map<String, Object> attrMap = new HashMap<>();
        Map<String, Long> timeMap = new HashMap<>();
        timeMap.put("sysTime", System.currentTimeMillis());
        attrMap.put(Constants.DATA, timeMap);
        attrMap.put(LiveConstants.STATE, LiveConstants.SUCCESS);
        attrMap.put(LiveConstants.SUCCESS_MSG, LiveConstants.MSG_SUCCESS);
        AbstractView jsonView = new MappingJackson2JsonView();
        jsonView.setAttributesMap(attrMap);
        return new ModelAndView(jsonView);
    }
}
