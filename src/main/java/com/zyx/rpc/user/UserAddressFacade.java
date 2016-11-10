package com.zyx.rpc.user;


import com.zyx.param.user.UserAddressParam;

import java.util.Map;

/**
 * Created by wms on 2016/11/9.
 *
 * @author WeiMinSheng
 * @version V2.0
 *          Copyright (c)2016 tyj-版权所有
 * @since 2016/11/9
 */
public interface UserAddressFacade {

    /**
     * 新增收货地址
     */
    Map<String, Object> insertUserAddressInfo(UserAddressParam param);

    /**
     * 查询收货地址
     */
    Map<String, Object> queryUserAddressInfo(UserAddressParam param);

    /**
     * 查询收货地址列表
     */
    Map<String, Object> queryUserAddressList(UserAddressParam param);

    /**
     * 删除收货地址
     */
    Map<String, Object> deleteUserAddressInfo(UserAddressParam param);

    /**
     * 编辑收货地址
     */
    Map<String, Object> editReceiptAddress(UserAddressParam param);

    /**
     * 设置默认收货地址
     */
    Map<String, Object> setDefaultReceiptAddress(UserAddressParam param);
}
