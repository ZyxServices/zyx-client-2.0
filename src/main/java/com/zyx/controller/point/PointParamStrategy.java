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
public interface PointParamStrategy {
    UserPointParam buildPointParam(Integer userId);

    UserPointParam buildPointParam(Integer userId, Long pointCount);

    UserPointParam buildPointParam(Integer userId, String detailMsg);

    UserPointParam buildPointParam(Integer userId, Long pointCount, String detailMsg);
}
