package com.zyx.controller.zoom;

import com.zyx.rpc.zoom.ZoomFacade;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.AbstractView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Objects;

/**
 * @author XiaoWei
 * @version V 1.0
 * @package com.zyx.controller
 * Create by XiaoWei on 2016/11/9
 */
@RestController
@RequestMapping(name = "/v2")
public class ZoomController {
    @Resource
    private ZoomFacade zoomFacade;

    @RequestMapping(value = "follow/add", method = RequestMethod.POST)
    @ApiOperation(value = "添加关注", notes = "添加关注")
    public ModelAndView addFollow(@RequestParam("token") String token,
                                  @ApiParam(required = true, name = "fromUserId", value = "添加关注的人") @RequestParam("fromUserId") Integer fromUserId,
                                  @ApiParam(required = true, name = "toUserId", value = "被添加关注的人") @RequestParam("toUserId") Integer toUserId) {
        AbstractView jsonView = new MappingJackson2JsonView();
        Map<String, Object> map = zoomFacade.addFollow(fromUserId, toUserId);
        jsonView.setAttributesMap(map);
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "follow/list", method = RequestMethod.POST)
    @ApiOperation(value = "获取未关注用户", notes = "获取未关注用户")
    public ModelAndView followList(@RequestParam("token") String token,
                                   @ApiParam(name = "loginUserId", value = "登录用户id") @RequestParam(value = "loginUserId", required = false, defaultValue = "-1") Integer loginUserId) {
        loginUserId = Objects.equals(loginUserId, null) || Objects.equals(loginUserId, "") ? -1 : loginUserId;
        AbstractView jsonView = new MappingJackson2JsonView();
        Map<String, Object> map = zoomFacade.getNoAttentionUser(loginUserId);
        jsonView.setAttributesMap(map);
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "concern/getOne", method = RequestMethod.GET)
    @ApiOperation(value = "动态详情", notes = "concernId：动态id")
    public ModelAndView getOneConcern(
            @RequestParam(value = "concernId") Integer concernId,
            @RequestParam(value = "accountId", required = false) Integer accountId) {
        Map<String, Object> returnMap = zoomFacade.getOneConcern(concernId, accountId);
        AbstractView jsonView = new MappingJackson2JsonView();
        jsonView.setAttributesMap(returnMap);
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "concern/getFollow/{token}/{loginUserId}/{start}/{pageSize}", method = RequestMethod.GET)
    @ApiOperation(value = "根据登录用户查询当前用户的关注的动态列表", notes = "loginUserId：登录用户id")
    public ModelAndView getFollow(
            @PathVariable(value = "token") String token,
            @PathVariable(value = "loginUserId") Integer loginUserId,
            @PathVariable(value = "start") Integer start,
            @PathVariable(value = "pageSize") Integer pageSize) {
        Map<String, Object> returnMap = zoomFacade.myFollowCon(loginUserId, start, pageSize);
        AbstractView jsonView = new MappingJackson2JsonView();
        jsonView.setAttributesMap(returnMap);
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "cern/insert", method = RequestMethod.POST)
    @ApiOperation(value = "发布动态", notes = "发布动态")
    public ModelAndView addCern(@RequestParam(name = "token", value = "", required = false) String token,
                                @ApiParam(required = true, name = "userId", value = "发布动态id") @RequestParam(name = "userId") Integer userId,
//                                @ApiParam(required = true, name = "type", value = "动态类型1为个人动态，2为活动动态，3为明星动态，4为圈子动态") @RequestParam(name = "type") Integer type,
                                @RequestParam(name = "cernTitle") String cernTitle,
                                @RequestParam(name = "content") String content,
                                @RequestParam(name = "imgUrl", required = false) String imgUrl,
                                @ApiParam(required = true, name = "videoUrl", value = "视频url，一期暂无") @RequestParam(name = "videoUrl", required = false) String videoUrl,
                                @ApiParam(required = true, name = "visible", value = "可见范围，可见范围0所有可见，1好友可见") @RequestParam(name = "visible") Integer visible) {
        AbstractView jsonView = new MappingJackson2JsonView();
        Map<String, Object> map = zoomFacade.addCern(userId, 0, cernTitle, content, imgUrl, videoUrl, visible);
        jsonView.setAttributesMap(map);
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "equip/insert", method = RequestMethod.POST)
    @ApiOperation(value = "发布装备秀", notes = "发布装备秀")
    public ModelAndView addEquip(@RequestParam(name = "token", value = "", required = false) String token,
                                 @ApiParam(required = true, name = "title", value = "装备秀标题") @RequestParam(name = "title") String title,
                                 @RequestParam(name = "content") String content,
                                 @ApiParam(required = true, name = "accountId", value = "创建者id") @RequestParam(name = "accountId", required = false) Integer accountId,
                                 @ApiParam(required = true, name = "labelId", value = "装备秀标签id") @RequestParam(name = "labelId") Integer labelId) {
        AbstractView jsonView = new MappingJackson2JsonView();
        Map<String, Object> map = zoomFacade.addEquip(title, content, accountId, labelId);
        jsonView.setAttributesMap(map);
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "equip/query", method = RequestMethod.POST)
    @ApiOperation(value = "装备控查询", notes = "装备控发布")
    public ModelAndView queryEquip(@RequestParam(name = "token", value = "", required = false) String token,
                                   @ApiParam(required = false, name = "id", value = "装备控id") @RequestParam(name = "eId", required = false) Integer eid) {
        AbstractView jsonView = new MappingJackson2JsonView();
        Map<String, Object> map = zoomFacade.queryEquip(eid);
        jsonView.setAttributesMap(map);
        return new ModelAndView(jsonView);
    }


}
