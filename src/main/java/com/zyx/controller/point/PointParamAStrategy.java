package com.zyx.controller.point;

import com.zyx.constants.point.PointConstants;
import com.zyx.param.point.UserPointParam;


/**
 * Created by wms on 2016/11/4.
 *
 * @author WeiMinSheng
 * @version V2.0
 *          Copyright (c)2016 tyj-版权所有
 * @since 2016/11/4
 */
public class PointParamAStrategy implements PointParamStrategy {
    @Override
    public UserPointParam buildPointParam(Integer userId) {
        return buildPointParam(userId, null, null);
    }

    @Override
    public UserPointParam buildPointParam(Integer userId, Long pointCount) {
        return buildPointParam(userId, pointCount, null);
    }

    @Override
    public UserPointParam buildPointParam(Integer userId, String detailMsg) {
        return buildPointParam(userId, null, detailMsg);
    }

    @Override
    public UserPointParam buildPointParam(Integer userId, Long pointCount, String detailMsg) {
        UserPointParam userPointParam = new UserPointParam();
        userPointParam.setUserId(userId);
        userPointParam.setPointType(PointConstants.POINT_TYPE_PANYAN);
        userPointParam.setPointCount(null == pointCount ? 10L : pointCount);
        userPointParam.setDetailTable(PointConstants.TABLE_PANYAN);
        userPointParam.setDetailType(PointConstants.DETAIL_TYPE_PL);
        userPointParam.setDetailMsg(detailMsg);
        return userPointParam;
    }
}
