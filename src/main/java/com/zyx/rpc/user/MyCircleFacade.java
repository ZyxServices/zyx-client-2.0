package com.zyx.rpc.user;

import java.util.Map;

/**
 * Created by wms on 2016/11/9.
 *
 * @author WeiMinSheng
 * @version V2.0
 *          Copyright (c)2016 tyj-版权所有
 * @since 2016/11/9
 */
public interface MyCircleFacade {
    Map<String, Object> myCircleList(String token, Integer accountId);

    Map<String, Object> myCreateList(String token, Integer createId);

    Map<String, Object> myConcernList(String token, Integer accountId);
}