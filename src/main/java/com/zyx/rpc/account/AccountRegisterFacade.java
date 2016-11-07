package com.zyx.rpc.account;


import com.zyx.param.account.UserLoginParam;

import java.util.Map;

/**
 * Created by wms on 2016/11/7.
 *
 * @author WeiMinSheng
 * @version V2.0
 *          Copyright (c)2016 tyj-版权所有
 * @since 2016/11/7
 */
public interface AccountRegisterFacade {

    /**
     * 验证手机验证码
     */
    Map<String, Object> validatePhoneCode(UserLoginParam userLoginParam);

    /**
     * 注册账号
     */
    Map<String, Object> registerAccount(UserLoginParam userLoginParam);

    /**
     * 更新密码
     */
    Map<String, Object> renewpwd(UserLoginParam userLoginParam);

    /**
     * 忘记密码
     */
    Map<String, Object> retrievepwd(UserLoginParam userLoginParam);
}
