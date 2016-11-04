package com.zyx.rpc.common;

/**
 * Created by wms on 2016/7/6.
 *
 * @author WeiMinSheng
 * @version V2.0
 *          Copyright (c)2016 tyj-版权所有
 */
public interface TokenFacade {
    boolean preHandle(String anthorization);
}
