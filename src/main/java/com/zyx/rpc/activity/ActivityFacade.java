package com.zyx.rpc.activity;

import com.zyx.param.activity.ActivityParam;
import com.zyx.param.activity.MyActivityListParam;
import com.zyx.param.activity.QueryActivityParam;

import java.util.Map;

/**
 * Created by Rainbow on 16-6-12.
 *
 * @author SubDong
 * @version V1.0
 *          Copyright (c)2016 tyj-版权所有
 * @title com.activity
 */
public interface ActivityFacade {
    /**
     * 发布活动
     *
     * @param activityParam
     * @return
     */
    Map<String, Object> insertActivity(ActivityParam activityParam);

    /**
     * 查询活动
     *
     * @param queryParam
     * @return
     */
    Map<String, Object> queryActivity(QueryActivityParam queryParam);

    /**
     * 通过用户ID 查询活动
     *
     * @param myActivityListParam
     * @return
     */
    Map<String, Object> myActivityList(MyActivityListParam myActivityListParam);

    /**
     * 通过ID查询活动详细信息
     *
     * @param activityId
     * @param userId
     * @return
     */
    Map<String, Object> activityById(Integer activityId, Integer userId);

    /**
     * 通过ID删除活动
     *
     * @param activityId
     * @return
     */
    Map<String, Object> delActivityById(Integer activityId, Integer userId);

}
