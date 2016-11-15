package com.zyx.controller.system;

import com.zyx.param.account.UserMsgParam;
import com.zyx.rpc.system.MsgFacade;

/**
 * Created by wms on 2016/11/11.
 *
 * @author WeiMinSheng
 * @version V2.0
 *          Copyright (c)2016 tyj-版权所有
 * @since 2016/11/11
 */
public class InsertMsgRunnable implements Runnable {

    private MsgFacade msgFacade;

    private UserMsgParam param;

    public InsertMsgRunnable(MsgFacade msgFacade, UserMsgParam param) {
        this.msgFacade = msgFacade;
        this.param = param;
    }

    @Override
    public void run() {
        msgFacade.insertMsg(param);
    }
}
