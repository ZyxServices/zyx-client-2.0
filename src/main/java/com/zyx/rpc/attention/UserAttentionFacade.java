package com.zyx.rpc.attention;

import com.zyx.param.attention.AttentionParam;

import java.util.Map;

/**
 * Created by wms on 2016/11/8.
 *
 * @author WeiMinSheng
 * @version V2.0
 *          Copyright (c)2016 tyj-版权所有
 * @since 2016/11/8
 */
public interface UserAttentionFacade {

    /**
     * 关注、拉黑
     */
    Map<String, Object> attentionFromAToB(AttentionParam attentionParam);

    /**
     * 取消关注，取消拉黑
     */
    Map<String, Object> unAttentionFromAToB(AttentionParam attentionParam);

    /**
     * 检查用户A是否已经关注用户B
     */
    Map<String, Object> checkAttentionFromAToB(AttentionParam param);
}
