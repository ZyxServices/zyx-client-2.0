package com.zyx.controller.point;

import com.zyx.param.point.UserPointParam;

/**
 * Created by wms on 2016/11/4.
 *
 * @author WeiMinSheng
 * @version V2.0
 *          Copyright (c)2016 tyj-版权所有
 * @since 2016/11/4
 */
public abstract class PointParamStrategy {
    UserPointParam buildPointParam(Integer userId) {
        return buildPointParam(userId, null, null);
    }

    UserPointParam buildPointParam(Integer userId, Long pointCount) {
        return buildPointParam(userId, pointCount, null);
    }

    UserPointParam buildPointParam(Integer userId, String detailMsg) {
        return buildPointParam(userId, null, detailMsg);
    }

    abstract UserPointParam buildPointParam(Integer userId, Long pointCount, String detailMsg);
}
