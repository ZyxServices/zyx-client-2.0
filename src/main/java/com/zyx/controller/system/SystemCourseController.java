package com.zyx.controller.system;

import com.zyx.constants.Constants;
import com.zyx.entity.system.Version;
import com.zyx.param.system.CourseParam;
import com.zyx.rpc.system.SystemCourseFacade;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
 * Created by Rainbow
 *
 * @author SubDong
 * @version V2.0
 *          Copyright (c)2016 zyx-版权所有
 * @since 2016/11/15
 */
@RestController
@RequestMapping("/v2/city")
@Api(description = "教程攻略 【1】查询教程攻略分类标签 【2】查询教程攻略列表 【3】通过教程ID查询教程详细信息")
public class SystemCourseController {

    @Resource
    private SystemCourseFacade systemCourseFacade;

    @RequestMapping(value = "/findCourseLabel", method = {RequestMethod.POST})
    @ApiOperation(value = "查询教程攻略分类标签", notes = "查询教程攻略分类标签")
    public ModelAndView findCourseLabel() {
        AbstractView jsonView = new MappingJackson2JsonView();
        Map<String, Object> hotCity = systemCourseFacade.findCourseLabel();
        jsonView.setAttributesMap(hotCity);
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/findCourseList", method = RequestMethod.POST)
    @ApiOperation(value = "查询教程攻略列表", notes = "查询教程攻略列表")
    public ModelAndView findCourseList(
            @ApiParam(name = "labelId", value = "标签ID") @RequestParam(name = "labelId", required = false) Integer labelId,
            @ApiParam(required = true, name = "number", value = "每页显示多少条") @RequestParam(name = "number", required = true) Integer number,
            @ApiParam(required = true, name = "pageNumber", value = "当前第几页") @RequestParam(name = "pageNumber", required = true) Integer pageNumber) {
        AbstractView jsonView = new MappingJackson2JsonView();
        CourseParam courseParam = new CourseParam();
        courseParam.setLabelId(labelId);
        courseParam.setNumber(number);
        courseParam.setPageNumber(pageNumber);
        Map<String, Object> courseList = systemCourseFacade.findCourseList(courseParam);
        jsonView.setAttributesMap(courseList);
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/findCourseById", method = RequestMethod.POST)
    @ApiOperation(value = "通过教程ID查询教程详细信息", notes = "通过教程ID查询教程详细信息")
    public ModelAndView findCourseById(
            @ApiParam(required = false, name = "userId", value = "当前登录用户ID") @RequestParam(name = "userId", required = false) Integer userId,
            @ApiParam(required = true, name = "courseId", value = "标签ID") @RequestParam(name = "courseId", required = true) Integer courseId) {
        AbstractView jsonView = new MappingJackson2JsonView();
        Map<String, Object> courseList = systemCourseFacade.findCourseById(userId,courseId);
        jsonView.setAttributesMap(courseList);
        return new ModelAndView(jsonView);
    }
}
