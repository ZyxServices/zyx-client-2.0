package com.zyx.controller.live;

import com.alibaba.fastjson.JSON;
import com.zyx.constants.account.AccountConstants;
import com.zyx.constants.live.LiveConstants;
import com.zyx.entity.live.Barrage;
import com.zyx.entity.live.LiveInfo;
import com.zyx.entity.live.TextLiveItem;
import com.zyx.param.Pager;
import com.zyx.param.live.BarrageParam;
import com.zyx.param.live.LiveInfoParam;
import com.zyx.rpc.account.AccountCommonFacade;
import com.zyx.rpc.account.AccountInfoFacade;
import com.zyx.rpc.live.BarrageFacade;
import com.zyx.rpc.live.LiveInfoFacade;
import com.zyx.rpc.live.LiveLabFacade;
import com.zyx.rpc.live.TextLiveItemFacade;
import com.zyx.vo.account.AccountInfoVo;
import com.zyx.vo.live.LiveInfoVo;
import com.zyx.vo.live.TextLiveItemVo;
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
 * @author DengQingHai
 * @version V1.0 Copyright (c)2012 chantsoft-版权所有
 * @title LiveController.java
 * @package com.zyx.controller
 * @description TODO
 * @update 2016年6月14日 下午3:17:26
 */
@RestController
@RequestMapping("/v1/live")
@Api(description = "直播相关接口")
public class LiveController {

    @Autowired
    LiveInfoFacade liveInfoFacade;
    @Autowired
    TextLiveItemFacade textLiveItemFacade;
    @Autowired
    BarrageFacade barrageFacade;
    @Autowired
    AccountInfoFacade accountInfoFacade;
    @Autowired
    LiveLabFacade liveLabFacade;
    @Autowired
    AccountCommonFacade accountCommonFacade;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ApiOperation(value = "直播发布", notes = "直播-直播发布")
    public ModelAndView createLive(@RequestParam(name = "token") String token,
                                   @ApiParam(required = true, name = "auth", value = "权限：1-公开，默认 2-我的粉丝可见 3-我关注人可见 4-包括2、3情况") @RequestParam(name = "auth", required = true) Integer auth,
                                   @ApiParam(required = true, name = "type", value = "直播类型 1-图文直播 2-视频直播") @RequestParam(name = "type") Integer type,
                                   @ApiParam(required = false, name = "start", value = "直播开始时间，默认为当前立即开始") @RequestParam(name = "start", required = false) Long start,
                                   @ApiParam(required = false, name = "end", value = "直播结束时间") @RequestParam(name = "end", required = false) Long end,
                                   @ApiParam(required = true, name = "title", value = "直播标题") @RequestParam(name = "title") String title,
                                   @ApiParam(required = true, name = "lab", value = "直播所属标签") @RequestParam(name = "lab") Integer lab,
                                   @ApiParam(required = false, name = "bgmUrl", value = "直播背景图片") @RequestParam(name = "bgmUrl", required = false) String bgmUrl
    ) {
        // Token 验证
        Map<String, Object> attrMap = new HashMap<>();
        attrMap.put(LiveConstants.STATE, LiveConstants.ERROR);
        if (token == null || "".equals(token)) {
            attrMap.put(LiveConstants.STATE, LiveConstants.REQUEST_UNAUTHORIZED);
            attrMap.put(LiveConstants.ERROR_MSG, LiveConstants.MSG_REQUEST_UNAUTHORIZED);
        } else if (type == null || title == null || "".equals(title) || lab == null) {// 判断参数必要性
            attrMap.put(LiveConstants.STATE, LiveConstants.PARAM_MISS);
            attrMap.put(LiveConstants.ERROR_MSG, LiveConstants.MSG_PARAM_MISS);
        } else if (!(type == 1 || type == 2) || !(lab == 1 || lab == 2 || lab == 3 || lab == 4 || lab == 5 || lab == 6)
                || !(auth == 1 || auth == 2 || auth == 3 || auth == 4)) {// 判断参数合法性
            attrMap.put(LiveConstants.STATE, LiveConstants.PARAM_ILIGAL);
            attrMap.put(LiveConstants.ERROR_MSG, LiveConstants.MSG_PARAM_ILIGAL);
        } else {
            boolean flag = accountCommonFacade.validateToken(token);
            if (flag) {
                AccountInfoVo account = accountCommonFacade.getAccountVoByToken(token);
                if (account == null || account.getId() == null) {
                    attrMap.put(LiveConstants.STATE, AccountConstants.ACCOUNT_ERROR_CODE_50000);
                    attrMap.put(LiveConstants.ERROR_MSG, AccountConstants.ACCOUNT_ERROR_CODE_50000_MSG);
                } else {
                    LiveInfo liveInfo = new LiveInfo();
                    // 系统补全参数
                    liveInfo.setCreateTime(System.currentTimeMillis());
                    liveInfo.setAuth(auth);
                    // 传入参数构造
                    liveInfo.setType(type);
                    liveInfo.setStartTime(start);
                    liveInfo.setEndTime(end);
                    liveInfo.setTitle(title);
                    liveInfo.setLab(lab);
                    liveInfo.setUserId(account.getId());
                    // 不必须字段
                    liveInfo.setBgmUrl(bgmUrl);
//                    liveInfo.setGroupId(groupId);
                    Integer id = liveInfoFacade.add(liveInfo);
                    attrMap.put("id", id);
                    attrMap.put(LiveConstants.STATE, LiveConstants.SUCCESS);
                }
            } else {
                attrMap.put(LiveConstants.STATE, AccountConstants.REQUEST_UNAUTHORIZED);
                attrMap.put(LiveConstants.ERROR_MSG, AccountConstants.REQUEST_UNAUTHORIZED);
            }
        }
        AbstractView jsonView = new MappingJackson2JsonView();
        jsonView.setAttributesMap(attrMap);
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/labs", method = {RequestMethod.GET, RequestMethod.POST})
    @ApiOperation(value = "直播-获取 标签页面 多条直播", notes = "直播-取 标签页面 多条直播")
    public ModelAndView getLabs() {
        Map<String, Object> attrMap = new HashMap<>();
        attrMap.put(LiveConstants.STATE, LiveConstants.SUCCESS);
        attrMap.put(LiveConstants.SUCCESS_MSG, LiveConstants.MSG_SUCCESS);
        List<String> list = liveLabFacade.getAllLabs();
        attrMap.put(LiveConstants.DATA, list);
        AbstractView jsonView = new MappingJackson2JsonView();
        jsonView.setAttributesMap(attrMap);
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/update_live", method = RequestMethod.POST)
    @ApiOperation(value = "直播更新修改", notes = "直播-直播更新修改")
    public ModelAndView updateLive(@RequestParam(name = "token") String token, @RequestParam(name = "id") Integer id,
                                   @RequestParam(name = "isPublic", required = false) Boolean isPublic,
                                   @RequestParam(name = "type", required = false) Integer type,
                                   @RequestParam(name = "start", required = false) Long start,
                                   @RequestParam(name = "end", required = false) Long end,
                                   @RequestParam(name = "title", required = false) String title,
                                   @RequestParam(name = "lab", required = false) Integer lab,
                                   @RequestParam(name = "bgmUrl", required = false) String bgmUrl,
                                   @RequestParam(name = "vedioUrl", required = false) String vedioUrl,
                                   @ApiParam(required = false, name = "groupId", value = "环信组ID") @RequestParam(name = "groupId", required = false) Long groupId) {
        Map<String, Object> attrMap = new HashMap<>();
        if (token == null || "".equals(token) || null == id) {
            attrMap.put(LiveConstants.STATE, LiveConstants.PARAM_MISS);
            attrMap.put(LiveConstants.ERROR_MSG, LiveConstants.MSG_PARAM_MISS);
        } else {
            try {
                boolean flag = accountCommonFacade.validateToken(token);
                if (flag) {
                    LiveInfo liveInfo = new LiveInfo();
                    liveInfo.setId(id);
                    // 传入参数构造
                    liveInfo.setCreateTime(System.currentTimeMillis());
                    // 传入参数构造
                    liveInfo.setType(type);
                    liveInfo.setTitle(title);
                    liveInfo.setLab(lab);
                    // 不必须字段
                    liveInfo.setStartTime(start);
                    liveInfo.setEndTime(end);
                    liveInfo.setBgmUrl(bgmUrl);
                    liveInfo.setVedioUrl(vedioUrl);
                    liveInfo.setGroupId(groupId);
                    // 系统补全参数
                    liveInfoFacade.updateNotNull(liveInfo);
                    attrMap.put(LiveConstants.STATE, LiveConstants.SUCCESS);
                } else {
                    attrMap.put(LiveConstants.STATE, AccountConstants.REQUEST_UNAUTHORIZED);
                    attrMap.put(LiveConstants.ERROR_MSG, AccountConstants.REQUEST_UNAUTHORIZED);
                }
            } catch (NumberFormatException nfe) {
                nfe.printStackTrace();
                attrMap.put(LiveConstants.STATE, LiveConstants.PARAM_ILIGAL);
            } catch (Exception e) {
                e.printStackTrace();
                attrMap.put(LiveConstants.STATE, LiveConstants.ERROR);
            }
        }
        AbstractView jsonView = new MappingJackson2JsonView();
        jsonView.setAttributesMap(attrMap);
        return new ModelAndView(jsonView);

    }

    @RequestMapping(value = "/update_status", method = RequestMethod.POST)
    @ApiOperation(value = "直播更新修改", notes = "直播-直播更新修改 ")
    public ModelAndView updateLiveStatus(@RequestParam(name = "token", required = true) String token,
                                         @RequestParam(name = "id", required = true) Integer id,
                                         @ApiParam(required = true, name = "status", value = "直播状态 -1:结束 0:未开始 1:直播中 2:暂停") @RequestParam(name = "status", required = true) Integer status) {
        Map<String, Object> attrMap = new HashMap<>();
        if (token == null || "".equals(token) || null == status || null == id) {
            attrMap.put(LiveConstants.STATE, LiveConstants.PARAM_MISS);
            attrMap.put(LiveConstants.ERROR_MSG, LiveConstants.MSG_PARAM_MISS);
        } else if (!(status == 1 || status == 2 || status == 0 || status == -1)) {
            attrMap.put(LiveConstants.STATE, LiveConstants.PARAM_ILIGAL);
            attrMap.put(LiveConstants.ERROR_MSG, LiveConstants.MSG_PARAM_ILIGAL);
        } else {
            try {
                boolean flag = accountCommonFacade.validateToken(token);
                if (flag) {
                    AccountInfoVo account = accountCommonFacade.getAccountVoByToken(token);
                    if (account == null || account.getId() == null) {
                        attrMap.put(LiveConstants.STATE, AccountConstants.ACCOUNT_ERROR_CODE_50000);
                        attrMap.put(LiveConstants.ERROR_MSG, AccountConstants.ACCOUNT_ERROR_CODE_50000_MSG);
                    } else {
                        LiveInfo dlive = liveInfoFacade.getById(id);
                        if (dlive == null || !dlive.getUserId().equals(account.getId())) {
                            attrMap.put(LiveConstants.STATE, AccountConstants.ACCOUNT_ERROR_CODE_50301);
                            attrMap.put(LiveConstants.ERROR_MSG, AccountConstants.ACCOUNT_ERROR_CODE_50301_MSG);
                        } else {
                            LiveInfo liveInfo = new LiveInfo();
                            liveInfo.setId(id);
                            liveInfo.setState(status);
                            System.out.println(JSON.toJSONString(liveInfo));
                            liveInfoFacade.updateNotNull(liveInfo);
                            attrMap.put(LiveConstants.STATE, LiveConstants.SUCCESS);
                        }
                    }
                } else {
                    attrMap.put(LiveConstants.STATE, AccountConstants.REQUEST_UNAUTHORIZED);
                    attrMap.put(LiveConstants.ERROR_MSG, AccountConstants.REQUEST_UNAUTHORIZED);
                }
            } catch (Exception e) {
                e.printStackTrace();
                attrMap.put(LiveConstants.STATE, LiveConstants.ERROR);
            }
        }
        AbstractView jsonView = new MappingJackson2JsonView();
        jsonView.setAttributesMap(attrMap);
        return new ModelAndView(jsonView);

    }

    @RequestMapping(value = "/list/lab", method = {RequestMethod.GET, RequestMethod.POST})
    @ApiOperation(value = "直播-获取 标签页面 多条直播", notes = "直播-取 标签页面 多条直播")
    public ModelAndView getLiveList(@RequestParam(name = "token", required = false) String token,
                                    @RequestParam(name = "lab", required = true) Integer lab,
                                    @RequestParam(name = "pageNo", required = true) Integer pageNo,
                                    @RequestParam(name = "pageSize", required = true) Integer pageSize) {
        Map<String, Object> attrMap = new HashMap<>();
        attrMap.put(LiveConstants.STATE, LiveConstants.ERROR);
        if (null == lab) {
            attrMap.put(LiveConstants.STATE, LiveConstants.PARAM_MISS);
            attrMap.put(LiveConstants.ERROR_MSG, LiveConstants.MSG_PARAM_MISS);
        } else if (!(lab == 1 || lab == 2 || lab == 3 || lab == 4)) {// 判断参数合法性
            attrMap.put(LiveConstants.STATE, LiveConstants.PARAM_ILIGAL);
            attrMap.put(LiveConstants.ERROR_MSG, LiveConstants.MSG_PARAM_ILIGAL);
        } else {
            try {
                LiveInfoParam liveInfoParam = new LiveInfoParam();
                boolean flag = accountCommonFacade.validateToken(token);
                liveInfoParam.setLab(lab);
                if (pageNo != null && pageSize != null) {
                    Pager pager = new Pager(pageNo, pageSize);
                    liveInfoParam.setPager(pager);
                } else {
                    Pager pager = new Pager(1, 10);
                    liveInfoParam.setPager(pager);
                }
                List<LiveInfoVo> list = liveInfoFacade.getList(liveInfoParam);
                attrMap.put(LiveConstants.DATA, list);
                attrMap.put(LiveConstants.STATE, LiveConstants.SUCCESS);
            } catch (Exception e) {
                e.printStackTrace();
                attrMap.put(LiveConstants.STATE, LiveConstants.ERROR);
                attrMap.put(LiveConstants.ERROR_MSG, AccountConstants.MSG_ERROR);
            }
        }
        AbstractView jsonView = new MappingJackson2JsonView();
        jsonView.setAttributesMap(attrMap);
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/get", method = {RequestMethod.POST})
    @ApiOperation(value = "直播-获取单个直播", notes = "直播-获取单个直播")
    public ModelAndView getLiveByKey(
            @RequestParam(name = "id",required = true) Integer id,
            @ApiParam(required = false, name = "veiwUserId", value = "查看该直播的用户ID")@RequestParam(name = "veiwUserId",required = false) Integer veiwUserId) {
        Map<String, Object> attrMap = new HashMap<>();
        attrMap.put(LiveConstants.STATE, LiveConstants.ERROR);
        if (id == null) {
            attrMap.put(LiveConstants.STATE, LiveConstants.PARAM_MISS);
        } else {
            try {
                LiveInfoParam param = new LiveInfoParam();
                param.setId(id);
                param.setViewUserId(veiwUserId);
                LiveInfoVo liveInfo = liveInfoFacade.getLiveInfo(param);
                attrMap.put(LiveConstants.DATA, liveInfo);
                attrMap.put(LiveConstants.STATE, LiveConstants.SUCCESS);
            } catch (NumberFormatException nfe) {
                attrMap.put(LiveConstants.STATE, LiveConstants.PARAM_ILIGAL);
            } catch (Exception e) {
                attrMap.put(LiveConstants.STATE, LiveConstants.ERROR);
            }
        }
        AbstractView jsonView = new MappingJackson2JsonView();
        jsonView.setAttributesMap(attrMap);
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/end", method = {RequestMethod.POST})
    @ApiOperation(value = "直播-结束直播", notes = "直播-结束直播")
    public ModelAndView getEndLive(
            @RequestParam(name = "token") String token,
            @ApiParam(required = true, name = "liveId", value = "直播ID") @RequestParam(name = "liveId") Integer liveId) {
        Map<String, Object> attrMap = new HashMap<>();
        if (token == null || "".equals(token) || null == liveId) {
            attrMap.put(LiveConstants.STATE, LiveConstants.PARAM_MISS);
            attrMap.put(LiveConstants.ERROR_MSG, LiveConstants.MSG_PARAM_MISS);
        } else if (liveId == null) {
            attrMap.put(LiveConstants.STATE, LiveConstants.ERROR);
            attrMap.put(LiveConstants.STATE, LiveConstants.PARAM_MISS);
        } else {
            boolean flag = accountCommonFacade.validateToken(token);
            if (flag) {
                AccountInfoVo account = accountCommonFacade.getAccountVoByToken(token);
                if (account == null || account.getId() == null) {
                    attrMap.put(LiveConstants.STATE, AccountConstants.ACCOUNT_ERROR_CODE_50000);
                    attrMap.put(LiveConstants.ERROR_MSG, AccountConstants.ACCOUNT_ERROR_CODE_50000_MSG);
                } else {
                    LiveInfo liveInfo = liveInfoFacade.getById(liveId);
                    if (liveInfo == null || !liveInfo.getUserId().equals(account.getId())) {
                        attrMap.put(LiveConstants.STATE, AccountConstants.ACCOUNT_ERROR_CODE_50301);
                        attrMap.put(LiveConstants.ERROR_MSG, AccountConstants.ACCOUNT_ERROR_CODE_50301_MSG);
                    } else {
                        LiveInfoVo liveInfoVo = liveInfoFacade.endLive(liveId);
                        attrMap.put(LiveConstants.DATA, liveInfoVo);
                        attrMap.put(LiveConstants.STATE, LiveConstants.SUCCESS);
                    }
                }
            } else {
                attrMap.put(LiveConstants.STATE, AccountConstants.REQUEST_UNAUTHORIZED);
                attrMap.put(LiveConstants.ERROR_MSG, AccountConstants.REQUEST_UNAUTHORIZED);
            }
        }
        AbstractView jsonView = new MappingJackson2JsonView();
        jsonView.setAttributesMap(attrMap);
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ApiOperation(value = "直播-删除直播", notes = "直播-删除直播")
    public ModelAndView deleteLiveByKey(@RequestParam(name = "token") String token,
                                        @RequestParam(name = "id") Integer id) {
        Map<String, Object> attrMap = new HashMap<>();
        attrMap.put(LiveConstants.STATE, LiveConstants.ERROR);
        if (token == null || "".equals(token)) {
            attrMap.put(LiveConstants.STATE, LiveConstants.REQUEST_UNAUTHORIZED);
        } else if (id == null) {
            attrMap.put(LiveConstants.STATE, LiveConstants.PARAM_MISS);
        } else {
            try {
                boolean flag = accountCommonFacade.validateToken(token);
                if (flag) {
                    liveInfoFacade.delete(id);
                    attrMap.put(LiveConstants.STATE, LiveConstants.SUCCESS);
                } else {
                    attrMap.put(LiveConstants.STATE, AccountConstants.REQUEST_UNAUTHORIZED);
                    attrMap.put(LiveConstants.ERROR_MSG, AccountConstants.REQUEST_UNAUTHORIZED);
                }
            } catch (NumberFormatException nfe) {
                attrMap.put(LiveConstants.ERROR_MSG, LiveConstants.PARAM_ILIGAL);
            } catch (Exception e) {
                attrMap.put(LiveConstants.STATE, LiveConstants.ERROR);
            }
        }
        AbstractView jsonView = new MappingJackson2JsonView();
        jsonView.setAttributesMap(attrMap);
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/in", method = RequestMethod.POST)
    @ApiOperation(value = "直播-进入直播", notes = "直播-进入直播")
    public ModelAndView inLiveByKey(@RequestParam(name = "token") String token,
                                    @RequestParam(name = "id") Integer id) {
        Map<String, Object> attrMap = new HashMap<>();

        if (token == null || "".equals(token)) {
            attrMap.put(LiveConstants.STATE, LiveConstants.ERROR);
            attrMap.put(LiveConstants.STATE, LiveConstants.REQUEST_UNAUTHORIZED);
        } else if (id == null) {
            attrMap.put(LiveConstants.STATE, LiveConstants.ERROR);
            attrMap.put(LiveConstants.STATE, LiveConstants.PARAM_MISS);
        } else {
            try {
                boolean flag = accountCommonFacade.validateToken(token);
                if (flag) {
                    liveInfoFacade.inOrOutLive(id, 1);
                    attrMap.put(LiveConstants.STATE, LiveConstants.SUCCESS);
                } else {
                    attrMap.put(LiveConstants.STATE, AccountConstants.REQUEST_UNAUTHORIZED);
                    attrMap.put(LiveConstants.ERROR_MSG, AccountConstants.REQUEST_UNAUTHORIZED);
                }
            } catch (NumberFormatException nfe) {
                attrMap.put(LiveConstants.ERROR_MSG, LiveConstants.PARAM_ILIGAL);
            } catch (Exception e) {
                attrMap.put(LiveConstants.STATE, LiveConstants.ERROR);
            }
        }
        AbstractView jsonView = new MappingJackson2JsonView();
        jsonView.setAttributesMap(attrMap);
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/out", method = RequestMethod.POST)
    @ApiOperation(value = "直播-退出直播", notes = "直播-退出直播")
    public ModelAndView outLiveByKey(@RequestParam(name = "token") String token,
                                     @RequestParam(name = "id") Integer id) {
        Map<String, Object> attrMap = new HashMap<>();
        if (token == null || "".equals(token)) {
            attrMap.put(LiveConstants.STATE, LiveConstants.ERROR);
            attrMap.put(LiveConstants.STATE, LiveConstants.REQUEST_UNAUTHORIZED);
        } else if (id == null) {
            attrMap.put(LiveConstants.STATE, LiveConstants.ERROR);
            attrMap.put(LiveConstants.STATE, LiveConstants.PARAM_MISS);
        } else {
            try {
                boolean flag = accountCommonFacade.validateToken(token);
                if (flag) {
                    liveInfoFacade.inOrOutLive(id, 0);
                    attrMap.put(LiveConstants.STATE, LiveConstants.SUCCESS);
                } else {
                    attrMap.put(LiveConstants.STATE, AccountConstants.REQUEST_UNAUTHORIZED);
                    attrMap.put(LiveConstants.ERROR_MSG, AccountConstants.REQUEST_UNAUTHORIZED);
                }
            } catch (NumberFormatException nfe) {
                attrMap.put(LiveConstants.ERROR_MSG, LiveConstants.PARAM_ILIGAL);
            } catch (Exception e) {
                attrMap.put(LiveConstants.STATE, LiveConstants.ERROR);
            }
        }
        AbstractView jsonView = new MappingJackson2JsonView();
        jsonView.setAttributesMap(attrMap);
        return new ModelAndView(jsonView);
    }
    @RequestMapping(value = "/number", method = RequestMethod.POST)
    @ApiOperation(value = "直播-获取观看直播人数", notes = "直播-获取观看直播人数")
    public ModelAndView getLiveWatchNumber(@RequestParam(name = "token") String token,
                                     @RequestParam(name = "id") Integer id) {
        Map<String, Object> attrMap = new HashMap<>();
        if (token == null || "".equals(token)) {
            attrMap.put(LiveConstants.STATE, LiveConstants.ERROR);
            attrMap.put(LiveConstants.STATE, LiveConstants.REQUEST_UNAUTHORIZED);
        } else if (id == null) {
            attrMap.put(LiveConstants.STATE, LiveConstants.ERROR);
            attrMap.put(LiveConstants.STATE, LiveConstants.PARAM_MISS);
        } else {
            try {
                boolean flag = accountCommonFacade.validateToken(token);
                if (flag) {
                    attrMap.put(LiveConstants.DATA, liveInfoFacade.getWatchNumber(id));
                    attrMap.put(LiveConstants.STATE, LiveConstants.SUCCESS);
                } else {
                    attrMap.put(LiveConstants.STATE, AccountConstants.REQUEST_UNAUTHORIZED);
                    attrMap.put(LiveConstants.ERROR_MSG, AccountConstants.REQUEST_UNAUTHORIZED);
                }
            } catch (NumberFormatException nfe) {
                attrMap.put(LiveConstants.ERROR_MSG, LiveConstants.PARAM_ILIGAL);
            } catch (Exception e) {
                attrMap.put(LiveConstants.STATE, LiveConstants.ERROR);
            }
        }
        AbstractView jsonView = new MappingJackson2JsonView();
        jsonView.setAttributesMap(attrMap);
        return new ModelAndView(jsonView);
    }

    ///////////
    ////
    ///////////
    @RequestMapping(value = "/text/create", method = RequestMethod.POST)
    @ApiOperation(value = "直播-发布直播图文内容", notes = "直播-发布直播图文内容")
    public ModelAndView createTextLiveItem(@RequestParam(name = "token") String token,
                                           @RequestParam(name = "liveId") Integer liveId, @RequestParam(name = "imgUrl", required = false) String imgUrl,
                                           @RequestParam(name = "content", required = false) String content) {
        Map<String, Object> attrMap = new HashMap<>();
        attrMap.put(LiveConstants.STATE, LiveConstants.ERROR);
        if (token == null) {
            attrMap.put(LiveConstants.STATE, LiveConstants.REQUEST_UNAUTHORIZED);
        } else if (liveId == null || !(content != null || imgUrl != null)) {
            attrMap.put(LiveConstants.STATE, LiveConstants.PARAM_MISS);
        } else {
            TextLiveItem item = new TextLiveItem();
            try {
                boolean flag = accountCommonFacade.validateToken(token);
                if (flag) {
                    item.setLiveId(liveId);
                    item.setContent(content);
                    item.setImgUrl(imgUrl);
                    item.setCreateTime(System.currentTimeMillis());
                    textLiveItemFacade.add(item);
                    attrMap.put(LiveConstants.STATE, LiveConstants.SUCCESS);
                } else {
                    attrMap.put(LiveConstants.STATE, AccountConstants.REQUEST_UNAUTHORIZED);
                    attrMap.put(LiveConstants.ERROR_MSG, AccountConstants.REQUEST_UNAUTHORIZED);
                }
            } catch (NumberFormatException nfe) {
                attrMap.put(LiveConstants.STATE, LiveConstants.PARAM_ILIGAL);
            } catch (Exception e) {
                attrMap.put(LiveConstants.STATE, LiveConstants.ERROR);
            }
        }
        AbstractView jsonView = new MappingJackson2JsonView();
        jsonView.setAttributesMap(attrMap);
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/text/list", method = {RequestMethod.POST, RequestMethod.GET})
    @ApiOperation(value = "直播-获取多条直播图文内容", notes = "直播-获取多条直播图文内容")
    public ModelAndView getTextLiveItemList(@RequestParam(name = "liveId", required = true) Integer liveId,
                                            @RequestParam(name = "createTimeLower", required = false) Long createTimeLower,
                                            @RequestParam(name = "createTimeUpper", required = false) Long createTimeUpper) {
        Map<String, Object> attrMap = new HashMap<>();
        if (liveId == null && createTimeLower == null && createTimeUpper == null) {
            attrMap.put(LiveConstants.STATE, LiveConstants.ERROR);
            attrMap.put(LiveConstants.STATE, LiveConstants.PARAM_MISS);
        } else {
            try {
                TextLiveItemVo vo = new TextLiveItemVo();
                vo.setCreateTimeLower(createTimeLower);
                vo.setCreateTimeUpper(createTimeUpper);
                vo.setLiveId(liveId);
                List<TextLiveItem> list = textLiveItemFacade.getList(vo);
                attrMap.put(LiveConstants.DATA, list);
                attrMap.put(LiveConstants.STATE, LiveConstants.SUCCESS);
            } catch (NumberFormatException nfe) {
                nfe.printStackTrace();
                attrMap.put(LiveConstants.STATE, LiveConstants.PARAM_ILIGAL);
            } catch (Exception e) {
                attrMap.put(LiveConstants.STATE, LiveConstants.ERROR);
            }
        }
        AbstractView jsonView = new MappingJackson2JsonView();
        jsonView.setAttributesMap(attrMap);
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/text/get", method = {RequestMethod.POST, RequestMethod.GET})
    @ApiOperation(value = "直播-获取单条直播图文内容", notes = "直播-获取单条直播图文内容")
    public ModelAndView getTextLiveItemByKey(@RequestParam(name = "id") Integer id) {
        Map<String, Object> attrMap = new HashMap<>();
        attrMap.put(LiveConstants.STATE, LiveConstants.ERROR);
        if (id == null) {
            attrMap.put(LiveConstants.STATE, LiveConstants.PARAM_MISS);
        } else {
            try {
                TextLiveItem textLiveItem = textLiveItemFacade.getById(id);
                attrMap.put(LiveConstants.DATA, textLiveItem);
                attrMap.put(LiveConstants.STATE, LiveConstants.SUCCESS);
            } catch (NumberFormatException nfe) {
                attrMap.put(LiveConstants.STATE, LiveConstants.PARAM_ILIGAL);
            } catch (Exception e) {
                attrMap.put(LiveConstants.STATE, LiveConstants.ERROR);
            }
        }

        AbstractView jsonView = new MappingJackson2JsonView();
        jsonView.setAttributesMap(attrMap);
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/text/delete", method = RequestMethod.POST)
    @ApiOperation(value = "直播-删除图文直播", notes = "直播-删除图文直播")
    public ModelAndView deleteTextLiveItemByKey(@RequestParam(name = "token") String token,
                                                @RequestParam(name = "id") Integer id) {
        Map<String, Object> attrMap = new HashMap<>();
        attrMap.put(LiveConstants.STATE, LiveConstants.ERROR);
        if (token == null || "".equals(token)) {
            attrMap.put(LiveConstants.STATE, LiveConstants.REQUEST_UNAUTHORIZED);
        } else if (id == null) {
            attrMap.put(LiveConstants.STATE, LiveConstants.PARAM_MISS);
        } else {
            try {
                boolean flag = accountCommonFacade.validateToken(token);
                if (flag) {
                    textLiveItemFacade.deleteById(id);
                    attrMap.put(LiveConstants.STATE, LiveConstants.SUCCESS);
                } else {
                    attrMap.put(LiveConstants.STATE, AccountConstants.REQUEST_UNAUTHORIZED);
                    attrMap.put(LiveConstants.ERROR_MSG, AccountConstants.REQUEST_UNAUTHORIZED);
                }
            } catch (NumberFormatException nfe) {
                attrMap.put(LiveConstants.STATE, LiveConstants.PARAM_ILIGAL);
            } catch (Exception e) {
                attrMap.put(LiveConstants.STATE, LiveConstants.ERROR);
            }
        }
        AbstractView jsonView = new MappingJackson2JsonView();
        jsonView.setAttributesMap(attrMap);
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/barrage/create", method = RequestMethod.POST)
    @ApiOperation(value = "直播-发送直播弹", notes = "直播-发送直播弹幕")
    public ModelAndView createBarrage(@RequestParam(name = "token", required = true) String token,
                                      @RequestParam(name = "liveId", required = true) Integer liveId,
                                      @RequestParam(name = "content", required = true) String content) {
        Map<String, Object> attrMap = new HashMap<>();
        attrMap.put(LiveConstants.STATE, LiveConstants.ERROR);
        if (token == null) {
            attrMap.put(LiveConstants.STATE, LiveConstants.REQUEST_UNAUTHORIZED);
        } else if (liveId == null) {
            attrMap.put(LiveConstants.STATE, LiveConstants.PARAM_MISS);
            attrMap.put(LiveConstants.ERROR_MSG, LiveConstants.MSG_PARAM_MISS);
        } else if (content == null || "".equals(content)) {
            attrMap.put(LiveConstants.STATE, LiveConstants.LIVE_BARRAGE_NULL_CONTENT);
            attrMap.put(LiveConstants.ERROR_MSG, LiveConstants.MSG_LIVE_BARRAGE_NULL_CONTENT);
        } else {
            AccountInfoVo account = accountCommonFacade.getAccountVoByToken(token);
            if (account == null || account.getId() == null) {
                attrMap.put(LiveConstants.STATE, AccountConstants.ACCOUNT_ERROR_CODE_50000);
                attrMap.put(LiveConstants.ERROR_MSG, AccountConstants.ACCOUNT_ERROR_CODE_50000_MSG);
            } else {
                Barrage entity = new Barrage();
                entity.setLiveId(liveId);
                entity.setContent(content);
                entity.setUserId(account.getId());
                entity.setNickName(account.getNickname());
                entity.setAvatar(account.getAvatar());
                entity.setCreateTime(System.currentTimeMillis());
                barrageFacade.add(entity);
                attrMap.put(LiveConstants.STATE, LiveConstants.SUCCESS);
            }
        }
        AbstractView jsonView = new MappingJackson2JsonView();
        jsonView.setAttributesMap(attrMap);
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/barrage/list", method = {RequestMethod.POST, RequestMethod.GET})
    @ApiOperation(value = "直播-获取直播弹幕", notes = "直播-获取直播弹幕 ")
    public ModelAndView getBarrageList(@RequestParam(name = "liveId", required = true) Integer liveId
//                                       @RequestParam(name = "createTimeLower", required = false) Long createTimeLower,
//                                       @RequestParam(name = "createTimeUpper", required = false) Long createTimeUpper,
                                      /* @RequestParam(name = "index", required = false) Long index*/) {
        Map<String, Object> attrMap = new HashMap<>();
        if (liveId == null) {
            attrMap.put(LiveConstants.STATE, LiveConstants.PARAM_MISS);
            attrMap.put(LiveConstants.ERROR_MSG, LiveConstants.MSG_PARAM_MISS);
        } else {
            try {
                BarrageParam param = new BarrageParam();
                param.setLiveId(liveId);
//                vo.setCreateTimeLower(createTimeLower);
//                vo.setCreateTimeUpper(createTimeUpper);
//                param.setIndex(index);
                attrMap.put("barrages", barrageFacade.getLast(param));
                attrMap.put(LiveConstants.STATE, LiveConstants.SUCCESS);
            } catch (NumberFormatException nfe) {
                nfe.printStackTrace();
                attrMap.put(LiveConstants.ERROR_MSG, LiveConstants.MSG_PARAM_ILIGAL);
                attrMap.put(LiveConstants.STATE, LiveConstants.PARAM_ILIGAL);
            } catch (Exception e) {
                e.printStackTrace();
                attrMap.put(LiveConstants.ERROR_MSG, LiveConstants.MSG_ERROR);
                attrMap.put(LiveConstants.STATE, LiveConstants.ERROR);
            }
        }
        AbstractView jsonView = new MappingJackson2JsonView();
        jsonView.setAttributesMap(attrMap);
        return new ModelAndView(jsonView);
    }

    //    @ApiParam(required = true, name = "status", value = "直播状态 -1:结束 0:未开始 1:直播中 2:暂停")
    @RequestMapping(value = "/barrage/history", method = {RequestMethod.POST, RequestMethod.GET})
    @ApiOperation(value = "直播-获取直播弹幕历史消息", notes = "直播-获取直播弹幕历史消息 ")
    public ModelAndView getBarrageHistory(@RequestParam(name = "liveId", required = true) Integer liveId,
                                          @ApiParam(required = false, name = "pageNo", value = "页码 1开始 错误分页将不分页") @RequestParam(name = "pageNo", required = false) Integer pageNo,
                                          @ApiParam(required = false, name = "pageSize", value = "页大小") @RequestParam(name = "pageSize", required = false) Integer pageSize
                                      /* @RequestParam(name = "index", required = false) Long index*/) {
        Map<String, Object> attrMap = new HashMap<>();
        if (liveId == null) {
            attrMap.put(LiveConstants.STATE, LiveConstants.PARAM_MISS);
            attrMap.put(LiveConstants.ERROR_MSG, LiveConstants.MSG_PARAM_MISS);
        } else {
            try {
                BarrageParam param = new BarrageParam();
                System.out.println(pageNo + "   " + pageSize);
                if (pageNo != null && pageSize != null && pageSize > 0 && pageNo > 0) {
                    System.out.println("wocao");
                    Pager pager = new Pager(pageNo, pageSize);
                    param.setPager(pager);
                }
                param.setLiveId(liveId);
                System.out.println(JSON.toJSONString(param));
                attrMap.put("barrages", barrageFacade.getList(param));
                attrMap.put(LiveConstants.STATE, LiveConstants.SUCCESS);
            } catch (NumberFormatException nfe) {
                nfe.printStackTrace();
                attrMap.put(LiveConstants.ERROR_MSG, LiveConstants.MSG_PARAM_ILIGAL);
                attrMap.put(LiveConstants.STATE, LiveConstants.PARAM_ILIGAL);
            } catch (Exception e) {
                e.printStackTrace();
                attrMap.put(LiveConstants.ERROR_MSG, LiveConstants.MSG_ERROR);
                attrMap.put(LiveConstants.STATE, LiveConstants.ERROR);
            }
        }
        AbstractView jsonView = new MappingJackson2JsonView();
        jsonView.setAttributesMap(attrMap);
        return new ModelAndView(jsonView);
    }
}
