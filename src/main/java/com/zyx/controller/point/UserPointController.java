package com.zyx.controller.point;

import com.zyx.constants.Constants;
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
        Long begin = System.currentTimeMillis();
        if (StringUtils.isEmpty(token)) {// 缺少参数
            jsonView.setAttributesMap(Constants.MAP_PARAM_MISS);
        } else {
            UserPointParam param = new PointParamContext(new PointParamAStrategy()).build(userId);
            param.setPointCount((long) pointCount);
            param.setDetailMsg(detailMsg);
            jsonView.setAttributesMap(userPointFacade.recordPoint(param));
        }
        Long end = System.currentTimeMillis();
        System.out.println("1花费时间 : " + (end - begin));
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/v1/point/insert2", method = RequestMethod.POST)
    @ApiOperation(value = "记录积分", notes = "记录积分")
    public ModelAndView recordPoint2(@RequestParam(name = "token") String token, @RequestParam(name = "id") int userId, @RequestParam(name = "pointCount") int pointCount, @RequestParam(name = "detailType") int detailType, @RequestParam(name = "detailMsg") String detailMsg) {
        AbstractView jsonView = new MappingJackson2JsonView();
        Long begin = System.currentTimeMillis();
        if (StringUtils.isEmpty(token)) {// 缺少参数
            jsonView.setAttributesMap(Constants.MAP_PARAM_MISS);
        } else {
            for (int i = 0; i < 20; i++) {
                PointPool.getPointPool().execute(new RecordPointThread(userPointFacade, new PointParamContext(new PointParamAStrategy()).build(userId)));
                PointPool.getPointPool().execute(new RecordPointThread(userPointFacade, new PointParamContext(new PointParamAStrategy()).build(userId, detailMsg)));
            }
        }
        Long end = System.currentTimeMillis();
        System.out.println("2花费时间 : " + (end - begin));
        return new ModelAndView(jsonView);
    }
}

class RecordPointThread implements Runnable {

    private UserPointFacade userPointFacade;

    private UserPointParam param;

    public RecordPointThread(UserPointFacade userPointFacade, UserPointParam param) {
        this.userPointFacade = userPointFacade;
        this.param = param;
    }

    @Override
    public void run() {
        userPointFacade.recordPoint(param);
    }
}