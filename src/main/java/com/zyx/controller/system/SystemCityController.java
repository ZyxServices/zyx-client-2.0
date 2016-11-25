package com.zyx.controller.system;

import com.zyx.rpc.system.SystemCityFacade;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.AbstractView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by Rainbow
 *
 * @author SubDong
 * @version V2.0
 *          Copyright (c)2016 zyx-版权所有
 * @since 2016/11/10
 */
@RestController
@RequestMapping("/v2/city")
@Api(description = "城市查询")
public class SystemCityController {

    @Resource
    private SystemCityFacade systemCityFacade;

    @RequestMapping(value = "/findHotCity", method = {RequestMethod.POST})
    @ApiOperation(value = "热门城市查询", notes = "热门城市查询")
    public ModelAndView findHotCity() {

        AbstractView jsonView = new MappingJackson2JsonView();
        Map<String, Object> hotCity = systemCityFacade.findHotCity();
        jsonView.setAttributesMap(hotCity);
        return new ModelAndView(jsonView);
    }

}
