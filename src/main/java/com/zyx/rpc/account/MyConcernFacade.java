package com.zyx.rpc.account;

import com.zyx.param.account.UserConcernParam;

import java.util.Map;

/**
 * Created by wms on 2016/8/12.
 *
 * @author WeiMinSheng
 * @version V1.0
 *          Copyright (c)2016 tyj-版权所有
 */
public interface MyConcernFacade {

    Map<String, Object> myList(UserConcernParam userConcernParam);
}
