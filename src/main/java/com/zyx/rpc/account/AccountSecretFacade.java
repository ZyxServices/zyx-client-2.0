package com.zyx.rpc.account;

import com.zyx.param.account.AccountLoginParam;

import java.util.Map;

/**
 * Created by wms on 2016/11/8.
 *
 * @author WeiMinSheng
 * @version V2.0
 *          Copyright (c)2016 tyj-版权所有
 * @since 2016/11/8
 */
public interface AccountSecretFacade {

    /**
     * 更新密码
     */
    Map<String, Object> renewSecret(AccountLoginParam accountLoginParam);

    /**
     * 忘记密码
     */
    Map<String, Object> retrieveSecret(AccountLoginParam accountLoginParam);
}
