package com.zyx.controller.zoom;

import com.zyx.rpc.pg.ZoomFacade;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.AbstractView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author XiaoWei
 * @version V 1.0
 * @package com.zyx.controller.zoom
 * Create by XiaoWei on 2016/11/9
 */
public class zoomController {
    @Resource
    private ZoomFacade zoomFacade;

    @RequestMapping(value = "/v1/circle/add", method = RequestMethod.POST)
    @ApiOperation(value = "添加圈子", notes = "添加圈子")
    public ModelAndView addCircle(@RequestParam("token") String token,
                                  @ApiParam(required = true, name = "fromUserId", value = "添加关注的人") @RequestParam("fromUserId") Integer fromUserId,
                                  @ApiParam(required = true, name = "toUserId", value = "被添加关注的人") @RequestParam("toUserId") Integer toUserId) {
        AbstractView jsonView = new MappingJackson2JsonView();
        Map<String, Object> map = zoomFacade.addFollow(fromUserId, toUserId);
        jsonView.setAttributesMap(map);
        return new ModelAndView(jsonView);
    }
}
