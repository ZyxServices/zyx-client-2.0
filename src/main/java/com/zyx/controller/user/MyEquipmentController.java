package com.zyx.controller.user;

import com.zyx.annotation.TokenVerify;
import com.zyx.constants.user.UserConstants;
import com.zyx.param.user.UserEquipmentParam;
import com.zyx.rpc.user.MyEquipmentFacade;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.AbstractView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

/**
 * Created by wms on 2016/11/24.
 *
 * @author WeiMinSheng
 * @version V2.0
 *          Copyright (c)2016 tyj-版权所有
 * @since 2016/11/24
 */
@RestController
@RequestMapping("/v2/my/equipment")
@Api(description = "我的装备秀接口。【1】查询我的装备秀列表。")
public class MyEquipmentController {
    @Autowired
    private MyEquipmentFacade myEquipmentFacade;

    @RequestMapping(value = "/create/list", method = RequestMethod.GET)
    @ApiOperation(value = "我的装备秀列表", notes = "我的装备秀列表")
    @TokenVerify(verifyType = TokenVerify.VerifyEnum.MINE)
    public ModelAndView myCreateActivityList(@RequestParam String token, @RequestParam Integer userId, @RequestParam Integer page, @RequestParam Integer pageSize) {
        AbstractView jsonView = new MappingJackson2JsonView();

        if (StringUtils.isEmpty(token)) {// 缺少参数
            jsonView.setAttributesMap(UserConstants.MAP_PARAM_MISS);
        } else {
            try {
                jsonView.setAttributesMap(myEquipmentFacade.myEquipmentList(buildParam(userId, page, pageSize)));
            } catch (Exception e) {
                e.printStackTrace();
                jsonView.setAttributesMap(UserConstants.MAP_500);
            }
        }

        return new ModelAndView(jsonView);
    }

    private UserEquipmentParam buildParam(Integer userId, Integer page, Integer pageSize) {
        UserEquipmentParam param = new UserEquipmentParam();
        param.setUserId(userId);
        param.setPage(page);
        param.setPageSize(pageSize);
        return param;
    }
}
