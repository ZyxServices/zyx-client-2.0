package com.zyx.rpc.coin;

import java.util.List;
import com.zyx.entity.coin.CoinLog;
import com.zyx.vo.coin.CoinDayLogVo;
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
    public List<CoinLog> getCoinLog(Integer userId, Integer operId);

    /**
     *
     * @param userId
     * @param day 0 为当天 负数N为当天之前第N天
     * @return
     */
    public List<CoinDayLogVo> getDayCoinLog(Integer userId, Integer day);
}
