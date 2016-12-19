package com.zyx.rpc.record;

import com.zyx.entity.venue.SportInfo;
import com.zyx.entity.venue.Venue;
import com.zyx.param.record.RankParam;
import com.zyx.param.record.SportRecordParam;
import com.zyx.param.venue.VenueParam;
import com.zyx.vo.account.UserIconVo;
import com.zyx.vo.record.*;

import java.util.List;

/**
 * Created by MrDeng on 2016/11/4.
 */
public interface SportRecordFacade {


    /**
     * 上传用户足迹
     * @param userId
     * @param venueId
     */
//    public void uploadFootprint(Integer userId, Integer venueId);

    /**
     * 获取用户城市的足迹
     * @param userId
     * @return
     */
    public List<CityFootprintVo> getCityFootprints(Integer userId);


    /**
     * 获取用户某个城市的足迹
     * @param userId
     * @param city
     * @return
     */
    public List<FootprintVo> getVenueFootprints(Integer userId, String city);

    /**
     * 上传用户运动记录
     *
     * @param userId
     * @param type 默认1
     * @param spendTime
     */
    public void uploadSportRecord(Integer userId, Integer type,Integer venueId, String level,Integer score, Long spendTime);


    /**
     * 查询用户的运动记录
     * @param param
     * @return
     */
    public List<SportRecordVo> getHistoryRecords(SportRecordParam param);


    /**
     * 获取自身的排名信息
     * @return
     */
    public RankVo getSelfRank(RankParam param);

    /**
     * 获取排行榜信息
     * @param param
     * @return
     */
    public List<RankVo> getRanks(RankParam param);
    /**
     * 秀到动态
     *
     * @param userId
     * @param recordId
     * @return
     */
    public Integer showToCircleItem(Integer userId, Integer recordId);

    /**
     * 获取某个场馆下的所有运动难度
     * @param venueId
     * @return
     */
    public List<SportInfo> getSportInfos(Integer venueId);

    //Todo 测试暂用 正式版本删除

    /**
     * 上传场馆难度等级
     * @param venueId
     * @param level
     */
    public void uploadPathLevel(Integer venueId,String level);

    /**
     * 获取场馆信息
     * @param param
     * @return
     */
    public List<Venue> getVenues(VenueParam param);

    /**
     * 获取用户各人运动概况
     *
     * @param userId
     * @return
     */
    // TODO: 2016/11/8 后期考虑另建表存储
    public SportOverviewVo getSelfRecordOverview(Integer userId);


    public List<SportInfoLevelVo> getSportInfoLevel(Integer venueId);

        public List<UserIconVo> getRecordUserIcon(Integer venueId,Integer pageSize,Integer pageNum);
}
