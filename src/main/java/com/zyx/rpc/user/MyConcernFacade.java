package com.zyx.rpc.user;


import com.zyx.param.user.UserConcernParam;

import java.util.Map;

/**
 * Created by wms on 2016/11/9.
 *
 * @author WeiMinSheng
 * @version V2.0
 *          Copyright (c)2016 tyj-版权所有
 * @since 2016/11/9
 */
public interface MyConcernFacade {

    /**
     *
     */
    Map<String, Object> myList(UserConcernParam userConcernParam);
}
