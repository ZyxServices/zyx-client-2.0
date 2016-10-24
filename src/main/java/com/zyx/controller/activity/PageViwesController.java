package com.zyx.controller.activity;

import com.fasterxml.jackson.dataformat.yaml.snakeyaml.scanner.Constant;
import com.zyx.constants.Constants;
import com.zyx.rpc.activity.PageViwesFacade;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.AbstractView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by SubDong on 16-6-27.
 *
 * @author SubDong
 * @version V1.0
 *          Copyright (c)2016 tyj-版权所有
 * @title PageViwesController
 * @package com.zyx.controller.activity
 * @update 16-6-27 上午11:15
 */
@RestController
@RequestMapping("/v1")
public class PageViwesController {

    @Resource
    private PageViwesFacade pageViwesFacade;

    @RequestMapping(value = "/pageViwes", method = RequestMethod.POST)
    @ApiOperation(value = "浏览量（0 直播  1 动态  2 活动  3 帖子  4 个人主页）", notes = "浏览量（0 直播  1 动态  2 活动  3 帖子  4 个人主页）")
    public ModelAndView pageViwes(@RequestParam(name = "types", required = true) Integer types,
                                  @RequestParam(name = "typeId", required = true) Integer typeId) {
        pageViwesFacade.pageViwes(types, typeId);
        AbstractView jsonView = new MappingJackson2JsonView();
        Map<String, Object> map = new HashMap<>();
        map.put(Constants.STATE, Constants.SUCCESS);
        jsonView.setAttributesMap(map);
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/getPageViwes", method = RequestMethod.POST)
    @ApiOperation(value = "获取浏览量", notes = "获取浏览量")
    public ModelAndView getPageViwes(@RequestParam(name = "types", required = true) Integer types,
                                     @RequestParam(name = "typeId", required = true) Integer typeId) {
        AbstractView jsonView = new MappingJackson2JsonView();

        Map<String, Object> map = pageViwesFacade.getPageViwes(types, typeId);

        jsonView.setAttributesMap(map);
        return new ModelAndView(jsonView);
    }

}
