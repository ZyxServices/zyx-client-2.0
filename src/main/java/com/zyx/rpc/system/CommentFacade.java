package com.zyx.rpc.system;

import java.util.Map;

/**
 * @author XiaoWei
 * @version V 1.0
 * @package com.zyx.rpc.system
 * Create by XiaoWei on 2016/8/29
 */
public interface CommentFacade  {
    /**
     * 发布评论
     * @param commentType 评论model类型
     * @param commentId model id
     * @param commentContent 评论内容
     * @param commentAccount 评论人
     * @param commentState 评论可见范围
     * @return
     */
    Map<String, Object> addComment(Integer commentType, Integer commentId, String commentContent, Integer commentAccount, Integer commentState);

    /**
     *  获取评论列表
     * @param commentType 评论类型
     * @param commentId 评论modelId
     * @return
     */
    Map<String,Object> queryComment(Integer commentType,Integer commentId);
}
