package com.zyx.controller.point.strategy;

import com.zyx.constants.point.PointConstants;
import com.zyx.controller.point.PointParamStrategy;
import com.zyx.param.point.UserPointParam;
import com.zyx.param.point.UserPointRuleParam;


/**
 * Created by wms on 2016/11/4.
 *
 * @author WeiMinSheng
 * @version V2.0
 *          Copyright (c)2016 tyj-版权所有
 * @since 2016/11/4
 */
public class DZStrategy extends PointParamStrategy {
    @Override
    public UserPointParam buildPointParam(Integer userId, Long pointCount, String detailMsg) {
        UserPointParam userPointParam = new UserPointParam();
        userPointParam.setUserId(userId);
        userPointParam.setPointType(PointConstants.POINT_TYPE_PANYAN);
        userPointParam.setPointCount(null == pointCount ? PointConstants.POINT_COUNT_DZ : pointCount);
        userPointParam.setDetailTable(PointConstants.TABLE_PANYAN);
        userPointParam.setDetailType(PointConstants.DETAIL_TYPE_DZ);
        userPointParam.setDetailMsg(detailMsg + PointConstants.DETAIL_TYPE_MSG_DZ);
        userPointParam.setRuleParam(new UserPointRuleParam(1, 1));
        return userPointParam;
    }
}
