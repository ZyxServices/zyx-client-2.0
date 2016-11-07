package com.zyx.rpc.activity;

import com.zyx.param.activity.ActivityParam;
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
     * 添加活动
     * @param activityParam
     * @return
     */
    Map<String, Object> insertActivity(ActivityParam activityParam);

    /**
     * 查询活动
     * @param queryParam
     * @return
     */
    Map<String, Object> queryActivity(QueryActivityParam queryParam);
}
