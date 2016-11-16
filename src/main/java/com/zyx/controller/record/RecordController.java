package com.zyx.controller.record;

import com.zyx.constants.record.RecordConstants;
import com.zyx.param.record.RankParam;
import com.zyx.param.record.SportRecordParam;
import com.zyx.rpc.account.AccountCommonFacade;
import com.zyx.rpc.record.SportRecordFacade;
import com.zyx.vo.account.AccountInfoVo;
import com.zyx.vo.record.*;
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

@RestController
@RequestMapping(value = "/v2/record")
@Api(description = "记录相关接口")
public class RecordController {

    @Autowired
    SportRecordFacade sportRecordFacade;
    @Autowired
    AccountCommonFacade accountCommonFacade;

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ApiOperation(value = "记录-上传用户运动记录", notes = "上传用户运动记录")
    public ModelAndView uploadSportRecord(
            @RequestParam(name = "token", required = true) String token,
            @ApiParam(required = true, name = "venueId", value = "场馆ID") @RequestParam(name = "venueId", required = true) Integer venueId,
            @ApiParam(required = true, name = "sportInfoId", value = "运动场馆路线信息ID") @RequestParam(name = "sportInfoId", required = true) Integer sportInfoId,
            @ApiParam(required = true, name = "spendTime", value = "运动时长") @RequestParam(name = "spendTime", required = true) Long spendTime) {
        AbstractView jsonView = new MappingJackson2JsonView();
        Map<String, Object> attrMap = new HashMap<>();
        if (!accountCommonFacade.validateToken(token)) {
            attrMap = RecordConstants.MAP_TOKEN_FAILURE;
        } else {
            AccountInfoVo account = accountCommonFacade.getAccountVoByToken(token);
            if (account == null) {
                attrMap = RecordConstants.MAP_TOKEN_FAILURE;
            } else {
                sportRecordFacade.uploadSportRecord(account.getId(), 1, venueId, sportInfoId, spendTime);
                attrMap.put(RecordConstants.STATE, RecordConstants.SUCCESS);
                attrMap.put(RecordConstants.SUCCESS_MSG, RecordConstants.MSG_SUCCESS);
            }
        }
        jsonView.setAttributesMap(attrMap);
        return new ModelAndView(jsonView);
    }


    @RequestMapping(value = "/overview", method = RequestMethod.POST)
    @ApiOperation(value = "记录-获取用户运动总览概况", notes = "获取用户运动总览概况")
    public ModelAndView getSelfRecordOverview(
            @RequestParam(name = "token", required = true) String token) {
        AbstractView jsonView = new MappingJackson2JsonView();
        Map<String, Object> attrMap = new HashMap<>();
        if (!accountCommonFacade.validateToken(token)) {
            attrMap = RecordConstants.MAP_TOKEN_FAILURE;
        } else {
            AccountInfoVo account = accountCommonFacade.getAccountVoByToken(token);
            if (account == null || account.getId() == null) {
                attrMap = RecordConstants.MAP_TOKEN_FAILURE;
            } else {
                SportOverviewVo vo = sportRecordFacade.getSelfRecordOverview(account.getId());
                attrMap.put(RecordConstants.STATE, RecordConstants.SUCCESS);
                attrMap.put(RecordConstants.SUCCESS_MSG, RecordConstants.MSG_SUCCESS);
                attrMap.put(RecordConstants.DATA, vo);
            }
        }
        jsonView.setAttributesMap(attrMap);
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/history", method = RequestMethod.POST)
    @ApiOperation(value = "记录-获取用户运动历史情况", notes = "获取用户运动历史情况")
    public ModelAndView getHistoryRecords(
            @RequestParam(name = "token", required = true) String token,
            @ApiParam(required = true, name = "pageSize", value = "分页大小") @RequestParam(name = "pageSize", required = true) Integer pageSize,
            @ApiParam(required = true, name = "pageNum", value = "页码 1开始") @RequestParam(name = "pageNum", required = true) Integer pageNum) {
        AbstractView jsonView = new MappingJackson2JsonView();
        Map<String, Object> attrMap = new HashMap<>();
        if (!accountCommonFacade.validateToken(token)) {
            attrMap = RecordConstants.MAP_TOKEN_FAILURE;
        } else {
            AccountInfoVo account = accountCommonFacade.getAccountVoByToken(token);
            if (account == null || account.getId() == null) {
                attrMap = RecordConstants.MAP_TOKEN_FAILURE;
            } else {
                SportRecordParam param = new SportRecordParam();
                param.setUserId(account.getId());
                param.setPageNum(pageNum);
                param.setPageSize(pageSize);
                List<SportRecordVo> list = sportRecordFacade.getHistoryRecords(param);
                attrMap.put(RecordConstants.STATE, RecordConstants.SUCCESS);
                attrMap.put(RecordConstants.SUCCESS_MSG, RecordConstants.MSG_SUCCESS);
                attrMap.put(RecordConstants.DATA, list);
            }
        }
        jsonView.setAttributesMap(attrMap);
        return new ModelAndView(jsonView);
    }


    @RequestMapping(value = "/footprint/city", method = RequestMethod.POST)
    @ApiOperation(value = "记录-获取用户城市级别足迹", notes = "获取用户城市级别足迹")
    public ModelAndView getCityFootprints(
            @RequestParam(name = "token", required = true) String token) {
        AbstractView jsonView = new MappingJackson2JsonView();
        Map<String, Object> attrMap = new HashMap<>();
        if (!accountCommonFacade.validateToken(token)) {
            attrMap = RecordConstants.MAP_TOKEN_FAILURE;
        } else {
            if (!accountCommonFacade.validateToken(token)) {
                attrMap = RecordConstants.MAP_TOKEN_FAILURE;
            } else {
                AccountInfoVo account = accountCommonFacade.getAccountVoByToken(token);
                if (account == null || account.getId() == null) {
                    attrMap = RecordConstants.MAP_TOKEN_FAILURE;
                } else {
                    SportRecordParam param = new SportRecordParam();
                    List<CityFootprintVo> list = sportRecordFacade.getCityFootprints(account.getId());
                    attrMap.put(RecordConstants.STATE, RecordConstants.SUCCESS);
                    attrMap.put(RecordConstants.SUCCESS_MSG, RecordConstants.MSG_SUCCESS);
                    attrMap.put(RecordConstants.DATA, list);
                }
            }
        }
        jsonView.setAttributesMap(attrMap);
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/footprint/venue", method = RequestMethod.POST)
    @ApiOperation(value = "记录-获取用户场馆级别足迹", notes = "获取用户场馆级别足迹")
    public ModelAndView getVenueFootprints(
            @RequestParam(name = "token", required = true) String token,
            @ApiParam(required = true, name = "city", value = "所需详细查询的城市名称") @RequestParam(name = "city", required = true) String city) {
        AbstractView jsonView = new MappingJackson2JsonView();
        Map<String, Object> attrMap = new HashMap<>();
        if (!accountCommonFacade.validateToken(token)) {
            attrMap = RecordConstants.MAP_TOKEN_FAILURE;
        } else {
            AccountInfoVo account = accountCommonFacade.getAccountVoByToken(token);
            if (account == null || account.getId() == null) {
                attrMap = RecordConstants.MAP_TOKEN_FAILURE;
            } else {
                SportRecordParam param = new SportRecordParam();
                List<FootprintVo> list = sportRecordFacade.getVenueFootprints(account.getId(), city);
                attrMap.put(RecordConstants.STATE, RecordConstants.SUCCESS);
                attrMap.put(RecordConstants.SUCCESS_MSG, RecordConstants.MSG_SUCCESS);
                attrMap.put(RecordConstants.DATA, list);
            }
        }
        jsonView.setAttributesMap(attrMap);
        return new ModelAndView(jsonView);
    }


    @RequestMapping(value = "/rank/self", method = RequestMethod.POST)
    @ApiOperation(value = "记录-获取用户个人排行信息", notes = "获取用户个人排行信息")
    public ModelAndView getSelfRank(
            @RequestParam(name = "token", required = true) String token) {
        AbstractView jsonView = new MappingJackson2JsonView();
        Map<String, Object> attrMap = new HashMap<>();
        if (!accountCommonFacade.validateToken(token)) {
            attrMap = RecordConstants.MAP_TOKEN_FAILURE;
        } else {
            AccountInfoVo account = accountCommonFacade.getAccountVoByToken(token);
            if (account == null || account.getId() == null) {
                attrMap = RecordConstants.MAP_TOKEN_FAILURE;
            } else {
                RankParam param = new RankParam();
                param.setUserId(account.getId());
                RankVo vo = sportRecordFacade.getSelfRank(param);
                attrMap.put(RecordConstants.STATE, RecordConstants.SUCCESS);
                attrMap.put(RecordConstants.SUCCESS_MSG, RecordConstants.MSG_SUCCESS);
                attrMap.put(RecordConstants.DATA, vo);
            }
        }
        jsonView.setAttributesMap(attrMap);
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/rank/list", method = RequestMethod.POST)
    @ApiOperation(value = "记录-获取所有用户排行信息", notes = "获取用户排行信息")
    public ModelAndView getRanks(
            @RequestParam(name = "token", required = true) String token,
            @ApiParam(required = true, name = "pageSize", value = "分页大小") @RequestParam(name = "pageSize", required = false) Integer pageSize,
            @ApiParam(required = true, name = "pageNum", value = "页码 1开始") @RequestParam(name = "pageNum", required = false) Integer pageNum) {
        AbstractView jsonView = new MappingJackson2JsonView();
        Map<String, Object> attrMap = new HashMap<>();
        if (pageSize < 1 || pageSize < 1) {
            attrMap.put(RecordConstants.STATE, RecordConstants.PARAM_ERROR);
            attrMap.put(RecordConstants.ERROR_MSG, RecordConstants.MSG_PARAM_ERROR);
        } else {
            if (!accountCommonFacade.validateToken(token)) {
                attrMap = RecordConstants.MAP_TOKEN_FAILURE;
            } else {
                AccountInfoVo account = accountCommonFacade.getAccountVoByToken(token);
                if (account == null || account.getId() == null) {
                    attrMap = RecordConstants.MAP_TOKEN_FAILURE;
                } else {
                    RankParam param = new RankParam();
                    param.setUserId(account.getId());
                    param.setPageSize(pageSize);
                    param.setPageNum(pageNum);
                    List<RankVo> list = sportRecordFacade.getRanks(param);
                    attrMap.put(RecordConstants.STATE, RecordConstants.SUCCESS);
                    attrMap.put(RecordConstants.SUCCESS_MSG, RecordConstants.MSG_SUCCESS);
                    attrMap.put(RecordConstants.DATA, list);
                }
            }
        }
        jsonView.setAttributesMap(attrMap);
        return new ModelAndView(jsonView);
    }
}