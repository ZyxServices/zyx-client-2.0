package com.zyx.controller.account;

import com.zyx.constants.Constants;
import com.zyx.param.account.UserCollectionParam;
import com.zyx.param.account.UserConcernParam;
import com.zyx.rpc.account.MyConcernFacade;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
 * Created by wms on 2016/8/12.
 *
 * @author WeiMinSheng
 * @version V1.0
 *          Copyright (c)2016 tyj-版权所有
 */
@RestController
@RequestMapping("/v1/my/concern")
@Api(description = "我的动态相关接口。1、查询我的动态列表。")
public class MyConcernController {
    @Resource
    private MyConcernFacade myConcernFacade;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ApiOperation(value = "我的动态列表", notes = "我的动态列表")
    public ModelAndView myCircleList(@RequestParam String token,
                                     @RequestParam Integer accountId,
                                     @ApiParam(name = "page", value = "页数")
                                     @RequestParam(required = false) Integer page,
                                     @ApiParam(name = "pageSize", value = "每页显示的记录数")
                                     @RequestParam(required = false) Integer pageSize) {
        AbstractView jsonView = new MappingJackson2JsonView();
        try {
            if (StringUtils.isEmpty(token) || StringUtils.isEmpty(accountId)) {// 缺少参数
                jsonView.setAttributesMap(Constants.MAP_PARAM_MISS);
            } else {
                jsonView.setAttributesMap(myConcernFacade.myList(buildUserConcernParam(token, accountId, page, pageSize)));
            }
        } catch (Exception e) {
            e.printStackTrace();
            jsonView.setAttributesMap(Constants.MAP_500);
        }
        return new ModelAndView(jsonView);
    }

    private UserConcernParam buildUserConcernParam(String token, Integer accountId, Integer page, Integer pageSize) {
        UserConcernParam _param = new UserConcernParam();
        _param.setToken(token);
        _param.setUserId(accountId);
        if (page != null && pageSize != null) {
            _param.setPage((page - 1) * pageSize);
            _param.setPageSize(pageSize);
        }
        return _param;
    }
}
