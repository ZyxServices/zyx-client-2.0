package com.zyx.controller.account;

import com.zyx.rpc.account.MyCircleFacade;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.AbstractView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by wms on 2016/8/11.
 *
 * @author WeiMinSheng
 * @version V1.0
 *          Copyright (c)2016 tyj-版权所有
 */
@RestController
@RequestMapping("/v1/my/circle")
@Api(description = "我的圈子相关接口。1、查询我的圈子列表。2、查询我创建的圈子列表。3、查询我关注的圈子列表。")
public class MyCircleController {

    @Resource
    private MyCircleFacade myCircleFacade;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ApiOperation(value = "我的圈子列表", notes = "我的圈子列表")
    public ModelAndView myCircleList(@RequestParam String token, @RequestParam Integer accountId) {
        Map<String, Object> map = myCircleFacade.myCircleList(token, accountId);
        AbstractView jsonView = new MappingJackson2JsonView();
        jsonView.setAttributesMap(map);
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/create/ist", method = RequestMethod.GET)
    @ApiOperation(value = "我创建的圈子列表", notes = "我创建的圈子列表")
    public ModelAndView circleList(@RequestParam String token, @RequestParam Integer accountId) {
        Map<String, Object> map = myCircleFacade.myCreateList(token, accountId);
        AbstractView jsonView = new MappingJackson2JsonView();
        jsonView.setAttributesMap(map);
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/concern/list", method = RequestMethod.GET)
    @ApiOperation(value = "我关注的圈子列表", notes = "我关注的圈子列表")
    public ModelAndView concernList(@RequestParam String token, @RequestParam Integer accountId) {
        Map<String, Object> map = myCircleFacade.myConcernList(token, accountId);
        AbstractView jsonView = new MappingJackson2JsonView();
        jsonView.setAttributesMap(map);
        return new ModelAndView(jsonView);
    }

}
