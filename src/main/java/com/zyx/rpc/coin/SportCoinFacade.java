package com.zyx.rpc.coin;

import com.zyx.entity.coin.CoinLog;
import com.zyx.vo.coin.SportCoinVo;

/**
 * Created by MrDeng on 2016/10/31.
 */
public interface SportCoinFacade {

    /**
     *
     * 增加用户运动币
     * @param userId 必传
     * @param operType 必传
     */
    public void modify(Integer userId,Integer operType);
    /**
     *
     * 增加用户运动币
     * @param userId 必传
     * @param operType 必传
     * @param coin 非必传
     */
    public void modify(Integer userId,Integer operType,Long coin);

    /**
     * 查询用户运动币
     * @param userId
     * @return
     */
    public SportCoinVo search(Integer userId);

    /**
     * 获取用户的货币变化列表
     * @param userId
     * @return
     */
    public java.util.List<CoinLog> getCoinLog(Integer userId, Integer operId);
}
