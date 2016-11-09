package com.zyx.rpc.activity;

import com.zyx.param.activity.ActivityMemberParam;
import com.zyx.param.activity.QueryActivityMemberParam;

import java.util.Map;

/**
 * Created by Rainbow
 *
 * @author SubDong
 * @version V2.0
 *          Copyright (c)2016 zyx-版权所有
 * @since 2016/11/9
 */
public interface ActivityMemberFacade {

    /**
     * 活动报名
     * @param memberParam
     * @return
     */
    Map<String, Object> insertMember(ActivityMemberParam memberParam);

    /**
     * 通过活动Id查询报名列表
     * @param memberParam
     * @return
     */
    Map<String, Object> findMemberById(QueryActivityMemberParam memberParam);

    /**
     * 通过用户ID查询当前用户报名的活动
     * @param memberParam
     * @return
     */
    Map<String, Object> findMemberByUserId(QueryActivityMemberParam memberParam);
}
