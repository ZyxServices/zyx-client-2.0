package com.zyx.rpc.zoom;

import java.util.Map;

/**
 * @author XiaoWei
 * @version V 1.0
 * @package com.zyx.rpc.zoom
 * Create by XiaoWei on 2016/11/9
 */
public interface ZoomFacade {
    /**
     *  添加关注（人）
     * @param fromUserId
     * @param toUserId
     * @return
     */
    Map<String, Object> addFollow(Integer fromUserId, Integer toUserId);

    /**
     *  添加关注列表获取未关注的用户（认证通过）
     * @param loginUserId
     * @return
     */
    Map<String,Object> getNoAttentionUser(Integer loginUserId);

}
