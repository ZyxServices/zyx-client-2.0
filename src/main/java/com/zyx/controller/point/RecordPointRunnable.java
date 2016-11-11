package com.zyx.controller.point;

import com.zyx.param.point.UserPointParam;
import com.zyx.rpc.point.UserPointFacade;

/**
 * Created by wms on 2016/11/11.
 *
 * @author WeiMinSheng
 * @version V2.0
 *          Copyright (c)2016 tyj-版权所有
 * @since 2016/11/11
 */
public class RecordPointRunnable implements Runnable {

    private UserPointFacade userPointFacade;

    private UserPointParam param;

    public RecordPointRunnable(UserPointFacade userPointFacade, UserPointParam param) {
        this.userPointFacade = userPointFacade;
        this.param = param;
    }

    @Override
    public void run() {
        userPointFacade.recordPoint(param);
    }
}
