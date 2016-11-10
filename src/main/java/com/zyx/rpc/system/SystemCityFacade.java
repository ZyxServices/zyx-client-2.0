package com.zyx.rpc.system;

import java.util.Map;

/**
 * Created by Rainbow
 *
 * @author SubDong
 * @version V2.0
 *          Copyright (c)2016 zyx-版权所有
 * @since 2016/11/10
 */
public interface SystemCityFacade {
    /**
     * 查询热门城市（平台开放城市）
     * @return
     */
    Map<String, Object> findHotCity();
}
