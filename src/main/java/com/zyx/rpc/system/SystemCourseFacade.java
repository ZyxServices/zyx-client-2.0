package com.zyx.rpc.system;

import com.zyx.param.system.CourseParam;

import java.util.Map;

/**
 * Created by Rainbow
 *
 * @author SubDong
 * @version V2.0
 *          Copyright (c)2016 zyx-版权所有
 * @since 2016/11/14
 */
public interface SystemCourseFacade {

    /**
     * 查询教程筛选标签
     *
     * @return
     */
    Map<String, Object> findCourseLabel();

    /**
     * 查询教程列表
     *
     * @return
     */
    Map<String, Object> findCourseList(CourseParam param);

    /**
     * 通过教程ID 查询教程详细信息
     *
     * @return
     */
    Map<String, Object> findCourseById(Integer courseId);
}
