package com.zyx.rpc.user;

import com.zyx.param.user.UserSuggestionParam;

import java.util.Map;

/**
 * Created by wms on 2016/12/5.
 *
 * @author WeiMinSheng
 * @version V2.0
 *          Copyright (c)2016 tyj-版权所有
 * @since 2016/12/5
 */
public interface UserSuggestionFacade {

    /**
     * 意见反馈
     */
    Map<String, Object> suggest(UserSuggestionParam userSuggestionParam);
}
