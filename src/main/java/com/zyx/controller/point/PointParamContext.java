package com.zyx.controller.point;

import com.zyx.param.point.UserPointParam;

/**
 * Created by wms on 2016/11/4.
 *
 * @author WeiMinSheng
 * @version V1.0
 *          Copyright (c)2016 tyj-版权所有
 * @since 2016/11/4
 */
public final class PointParamContext {

    private PointParamStrategy strategy;

    public PointParamContext(PointParamStrategy strategy) {
        this.strategy = strategy;
    }

    public void setStrategy(PointParamStrategy strategy) {
        this.strategy = strategy;
    }

    public UserPointParam build(Integer userId) {
        return strategy.buildPointParam(userId);
    }

    public UserPointParam build(Integer userId, String detailMsg) {
        return strategy.buildPointParam(userId, detailMsg);
    }

    public UserPointParam build(Integer userId, Long pointCount, String detailMsg) {
        return strategy.buildPointParam(userId, pointCount, detailMsg);
    }
}
