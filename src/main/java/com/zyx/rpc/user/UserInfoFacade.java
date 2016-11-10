package com.zyx.rpc.user;

import com.zyx.param.account.AccountInfoParam;
import com.zyx.param.account.UserAuthParam;

import java.util.Map;

/**
 * Created by wms on 2016/11/9.
 *
 * @author WeiMinSheng
 * @version V2.0
 *          Copyright (c)2016 tyj-版权所有
 * @since 2016/11/9
 */
public interface UserInfoFacade {
    Map<String, Object> queryAccountInfo(String token, int userId);

    Map<String, Object> editAccountInfo(String token, int userId, AccountInfoParam param);

    Map<String, Object> editAccountAuth(String token, int userId, UserAuthParam param);

    /**
     * 个人中心用户信息接口
     */
    Map<String, Object> queryMyCenterInfo(String token, int userId);

    /**
     * 个人中心审核资料接口
     */
    Map<String, Object> queryMyAuthInfo(String token, int userId);

}
