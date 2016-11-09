package com.zyx.rpc.pg;

import java.util.Map;

/**
 * @author XiaoWei
 * @version V 1.0
 * @package com.zyx.rpc.pg
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

}
