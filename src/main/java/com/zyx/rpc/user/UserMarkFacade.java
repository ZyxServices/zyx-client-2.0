package com.zyx.rpc.user;

import com.zyx.param.user.UserMarkParam;

import java.util.Map;

/**
 * Created by wms on 2016/11/9.
 *
 * @author WeiMinSheng
 * @version V2.0
 *          Copyright (c)2016 tyj-版权所有
 * @since 2016/11/9
 */
public interface UserMarkFacade {
    /**
     * 签到
     */
    Map<String, Object> sign(UserMarkParam userMarkParam);

    /**
     * 签到查询
     */
    Map<String, Object> querySign(UserMarkParam userMarkParam);

}
