package com.zyx.rpc.system;

import com.zyx.param.system.SearchDevaParam;
import com.zyx.param.system.SearchParam;

import java.util.Map;

/**
 * Created by Rainbow
 *
 * @author SubDong
 * @version V2.0
 *          Copyright (c)2016 zyx-版权所有
 * @since 2016/11/9
 */
public interface SearchFacade {

    /**
     * 分模块检索数据
     *
     * @return
     */
    Map<String, Object> modularSearch(SearchParam searchParam);

    /**
     * 官方推荐查询
     *
     * @return
     */
    Map<String, Object> devaSearch(SearchDevaParam searchDevaParam);
}
