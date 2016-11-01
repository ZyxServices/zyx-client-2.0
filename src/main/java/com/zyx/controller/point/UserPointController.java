package com.zyx.controller.point;

import com.zyx.constants.Constants;
import com.zyx.constants.point.PointConstants;
import com.zyx.param.point.UserPointParam;
import com.zyx.rpc.point.UserPointFacade;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.StringUtils;
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
 * Created by wms on 2016/10/31.
 *
 * @author WeiMinSheng
 * @version V1.0
 *          Copyright (c)2016 tyj-版权所有
 * @since 2016/10/31
 */
@RestController
public class UserPointController {

    @Resource
    private UserPointFacade userPointFacade;

    @RequestMapping(value = "/v1/point", method = RequestMethod.GET)
    @ApiOperation(value = "获取用户积分", notes = "获取用户积分")
    public ModelAndView queryUserPoint(@RequestParam(name = "token") String token, @RequestParam(name = "id") int userId, @RequestParam(name = "type") int pointType) {
        AbstractView jsonView = new MappingJackson2JsonView();

        if (StringUtils.isEmpty(token)) {// 缺少参数
            jsonView.setAttributesMap(Constants.MAP_PARAM_MISS);
        } else {
            UserPointParam param = new UserPointParam();
            param.setUserId(userId);
            param.setPointType(pointType);
            Map<String, Object> map = userPointFacade.queryUserPoint(param);
            jsonView.setAttributesMap(map);
        }

        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/v1/point/insert", method = RequestMethod.POST)
    @ApiOperation(value = "记录积分", notes = "记录积分")
    public ModelAndView recordPoint(@RequestParam(name = "token") String token, @RequestParam(name = "id") int userId, @RequestParam(name = "pointCount") int pointCount, @RequestParam(name = "detailType") int detailType, @RequestParam(name = "detailMsg") String detailMsg) {
        AbstractView jsonView = new MappingJackson2JsonView();

        if (StringUtils.isEmpty(token)) {// 缺少参数
            jsonView.setAttributesMap(Constants.MAP_PARAM_MISS);
        } else {
            UserPointParam param = new UserPointParam();
            param.setUserId(userId);
            param.setPointCount((long) pointCount);
            param.setPointType(PointConstants.PANYAN_TYPE);
            param.setDetailType(detailType);
            param.setDetailMsg(detailMsg);
            param.setDetailTable(PointConstants.PANYAN_TABLE);
            Map<String, Object> map = userPointFacade.recordPoint(param);
            jsonView.setAttributesMap(map);
        }

        return new ModelAndView(jsonView);
    }
}
