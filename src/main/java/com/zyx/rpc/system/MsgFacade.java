package com.zyx.rpc.system;

import com.zyx.param.account.UserMsgParam;

import java.util.Map;

/**
 * Created by wms on 2016/11/15.
 *
 * @author WeiMinSheng
 * @version V2.0
 *          Copyright (c)2016 tyj-版权所有
 * @since 2016/11/15.
 */
public interface MsgFacade {
    Map<String, Object> insertMsg(UserMsgParam userMsgParam);

    Map<String, Object> deleteMsg(UserMsgParam userMsgParam);

    Map<String, Object> queryMsgCount(UserMsgParam userMsgParam);

    Map<String, Object> queryMsgList(UserMsgParam userMsgParam);
}
