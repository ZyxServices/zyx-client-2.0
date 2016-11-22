package com.zyx.controller.system;

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

/**
 * @author XiaoWei
 * @version V 1.0
 * @package com.zyx.controller.system
 * Create by XiaoWei on 2016/11/21
 */
@RestController
@RequestMapping(name = "/v2")
public class ZanController {

    @Resource
    private ZoomFacade zoomFacade;

    @RequestMapping(value = "/v1/zan/add", method = RequestMethod.POST)
    @ApiOperation(value = "点赞", notes = "点赞")
    public ModelAndView zan(@RequestParam(name = "token") String token,
                            @ApiParam(required = true, name = "bodyId", value = "模块id") @RequestParam(name = "bodyId") Integer bodyId,
                            @ApiParam(required = true, name = "bodyType", value = "点赞模块类型1：文章,2：动态，3：装备控") @RequestParam(name = "bodyType") Integer bodyType,
                            @ApiParam(required = true, name = "accountId", value = "点赞人id") @RequestParam(name = "accountId") Integer accountId) {
        Map<String, Object> map = zoomFacade.addZan(bodyId, bodyType, accountId);
        AbstractView jsonView = new MappingJackson2JsonView();
        jsonView.setAttributesMap(map);
        return new ModelAndView(jsonView);
    }
}