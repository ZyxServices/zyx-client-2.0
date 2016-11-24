package com.zyx.rpc.user;

import com.zyx.param.user.UserEquipmentParam;

import java.util.Map;

/**
 * Created by wms on 2016/11/24.
 *
 * @author WeiMinSheng
 * @version V2.0
 *          Copyright (c)2016 tyj-版权所有
 * @since 2016/11/24
 */
public interface MyEquipmentFacade {
    /**
     * 我的装备秀列表
     */
    Map<String, Object> myEquipmentList(UserEquipmentParam userEquipmentParam);
}
