package com.zyx.controller.coin;

import com.zyx.constants.Constants;
import com.zyx.entity.coin.CoinLog;
import com.zyx.rpc.coin.SportCoinFacade;
import com.zyx.vo.coin.SportCoinVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.AbstractView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by MrDeng on 2016/11/10.
 * 运动币
 */

@RestController
@RequestMapping(value = "/v2/coin")
@Api(description = "运动币接口")
public class CoinController {

    @Autowired
    SportCoinFacade sportCoinFacade;

    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    @ApiOperation(value = "运动币-用户货币操作 该接口APP端禁止调用", notes = "用户货币操作 该接口APP端禁止调用")
    public ModelAndView modifySportCoin(
            @ApiParam(required = true, name = "userId", value = "用户ID") @RequestParam(name = "userId", required = true) Integer userId,
            @ApiParam(required = true, name = "operType", value = "操作类型｛1-新用户第一次使用 2-完善资料 3-分享 4-关注 点赞 5-发布评论 6-发布活动 7-发布动态 8-老带新 9-装备秀 10-被举报｝")
            @RequestParam(name = "operType", required = true) Integer operType,
            @ApiParam(required = false, name = "coin", value = "用户ID") @RequestParam(name = "coin", required = false) Long coin
    ) {
        AbstractView jsonView = new MappingJackson2JsonView();
        Map<String, Object> attrMap = new HashMap<>();
        sportCoinFacade.modify(userId, operType, coin);
        attrMap.put(Constants.STATE, Constants.SUCCESS);
        attrMap.put(Constants.SUCCESS_MSG, Constants.MSG_SUCCESS);
        jsonView.setAttributesMap(attrMap);
        return new ModelAndView(jsonView);
    }


    @RequestMapping(value = "/get", method = RequestMethod.POST)
    @ApiOperation(value = "运动币-获取用户货币信息", notes = "获取用户货币信息")
    public ModelAndView getUserCoin(
            @ApiParam(required = true, name = "userId", value = "用户ID") @RequestParam(name = "userId", required = true) Integer userId) {
        AbstractView jsonView = new MappingJackson2JsonView();
        Map<String, Object> attrMap = new HashMap<>();
        SportCoinVo vo = sportCoinFacade.search(userId);
        attrMap.put(Constants.STATE, Constants.SUCCESS);
        attrMap.put(Constants.SUCCESS_MSG, Constants.MSG_SUCCESS);
        attrMap.put(Constants.DATA, vo);
        jsonView.setAttributesMap(attrMap);
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/log", method = RequestMethod.POST)
    @ApiOperation(value = "运动币-用户货币获取运动币信息", notes = "用户货币获取运动币信息")
    public ModelAndView getUserCoin(
            @ApiParam(required = true, name = "userId", value = "用户ID") @RequestParam(name = "userId", required = true) Integer userId,
            @ApiParam(required = false, name = "operType", value = "操作类型｛1-新用户第一次使用 2-完善资料 3-分享 4-关注 点赞 5-发布评论 6-发布活动 7-发布动态 8-老带新 9-装备秀 10-被举报｝")
            @RequestParam(name = "operType", required = false) Integer operType) {
        AbstractView jsonView = new MappingJackson2JsonView();
        Map<String, Object> attrMap = new HashMap<>();
        List<CoinLog> list = sportCoinFacade.getCoinLog(userId, operType);
        attrMap.put(Constants.STATE, Constants.SUCCESS);
        attrMap.put(Constants.SUCCESS_MSG, Constants.MSG_SUCCESS);
        attrMap.put(Constants.DATA, list);
        jsonView.setAttributesMap(attrMap);
        return new ModelAndView(jsonView);
    }
}
