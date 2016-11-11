package com.zyx.controller.zoom;

import com.zyx.rpc.zoom.ZoomFacade;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
public class ZoomController {
    @Resource
    private ZoomFacade zoomFacade;

    @RequestMapping(value = "/v2/follow/add", method = RequestMethod.POST)
    @ApiOperation(value = "添加关注", notes = "添加关注")
    public ModelAndView addFollow(@RequestParam("token") String token,
                                  @ApiParam(required = true, name = "fromUserId", value = "添加关注的人") @RequestParam("fromUserId") Integer fromUserId,
                                  @ApiParam(required = true, name = "toUserId", value = "被添加关注的人") @RequestParam("toUserId") Integer toUserId) {
        AbstractView jsonView = new MappingJackson2JsonView();
        Map<String, Object> map = zoomFacade.addFollow(fromUserId, toUserId);
        jsonView.setAttributesMap(map);
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/v2/follow/list", method = RequestMethod.POST)
    @ApiOperation(value = "获取未关注用户", notes = "获取未关注用户")
    public ModelAndView followList(@RequestParam("token") String token,
                                   @ApiParam(name = "loginUserId", value = "登录用户id") @RequestParam(value = "loginUserId",required = false,defaultValue = "-1") Integer loginUserId) {
        loginUserId = Objects.equals(loginUserId, null) || Objects.equals(loginUserId, "") ? -1 : loginUserId;
        AbstractView jsonView = new MappingJackson2JsonView();
        Map<String, Object> map = zoomFacade.getNoAttentionUser(loginUserId);
        jsonView.setAttributesMap(map);
        return new ModelAndView(jsonView);
    }
}
