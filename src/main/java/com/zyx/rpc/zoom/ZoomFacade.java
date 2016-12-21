package com.zyx.rpc.zoom;

import com.zyx.param.user.UserConcernParam;

import java.util.Map;

/**
 * @author XiaoWei
 * @version V 1.0
 * @package com.zyx.rpc.zoom
 * Create by XiaoWei on 2016/11/9
 */
public interface ZoomFacade {
    /**
     * 添加关注（人）
     *
     * @param fromUserId
     * @param toUserId
     * @return
     */
    Map<String, Object> addFollow(Integer fromUserId, Integer toUserId);

    /**
     * 添加关注列表获取未关注的用户（认证通过）
     *
     * @param loginUserId
     * @return
     */
    Map<String, Object> getNoAttentionUser(Integer loginUserId);

    /**
     * 获取动态详情
     *
     * @param concernId
     * @return
     */
    Map<String, Object> getOneConcern(Integer concernId, Integer accountId);

    /**
     * 根据登录用户获取该用户关注动态列表
     *
     * @param loginUserId
     * @return
     */
    Map<String, Object> myFollowCon(Integer loginUserId, Integer page, Integer pageSize);

//    /**
//     * 发布动态(多模块)
//     *
//     * @param userId
//     * @param content
//     * @param cernImgurl
//     * @param videoUrl
//     * @param visible
//     * @return
//     */
//    Map<String, Object> addCern(Integer userId, Integer type, String content, String cernImgurl, String videoUrl, Integer visible, String local);

    /**
     * 发布动态(多模块)
     *
     * @param userId
     * @param content
     * @param cernImgurl
     * @param videoUrl
     * @param visible
     * @param recordId
     * @return
     */
    Map<String, Object> addCern(Integer userId, Integer type, String content, String cernImgurl, String videoUrl, Integer visible, String local,Integer recordId);

    /**
     * 各模块点赞
     *
     * @param body_id
     * @param body_type
     * @param account_id
     * @return
     */
    Map<String, Object> addZan(Integer body_id, Integer body_type, Integer account_id);

    /**
     * @param title
     * @param content
     * @param accountId
     * @param labelId
     * @return
     */
    Map<String, Object> addEquip(String title, String content, Integer accountId, Integer labelId,String imgUrls);


    /**
     * 装备控列表
     *
     * @param eId
     * @return
     */
    Map<String, Object> queryEquip(Integer eId);

    /**
     * 装备秀详情
     *
     * @param eId
     * @return
     */
    Map<String, Object> queryOne(Integer eId);


    /**
     * 取消关注
     *
     * @param fromUserId
     * @param toUserId
     * @return
     */
    Map<String, Object> unFollow(Integer fromUserId, Integer toUserId);


    /**
     * 动态用户对象，推荐动态
     *
     * @param userConcernParam
     * @return
     */
    Map<String, Object> getRecommend(UserConcernParam userConcernParam);

    /**
     * 根据标签id获取装备秀列表
     * @param labelId
     * @return
     */
    Map<String, Object> queryEquipByLabelId(Integer labelId,Integer loginUserId,Integer start,Integer pageSize);


    /**
     *  根据动态id获取点赞用户列表
     * @param concernId
     * @param max
     * @return
     */
    Map<String,Object> getConcernZanUser(Integer concernId,Integer max);


    /**
     * 删除动态（软）
     * @param id
     * @param loginUserId
     * @return
     */
    Map<String, Object> delCern(Integer id, Integer loginUserId);

}
