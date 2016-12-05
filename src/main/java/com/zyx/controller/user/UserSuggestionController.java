package com.zyx.controller.user;

import com.zyx.annotation.TokenVerify;
import com.zyx.constants.user.UserConstants;
import com.zyx.interceptor.Authorization;
import com.zyx.param.user.UserSuggestionParam;
import com.zyx.rpc.user.UserSuggestionFacade;
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

import java.util.Map;

/**
 * Created by wms on 2016/12/5.
 *
 * @author WeiMinSheng
 * @version V2.0
 *          Copyright (c)2016 tyj-版权所有
 * @since 2016/12/5
 */
@RestController
@RequestMapping("/v2/user")
@Api(description = "用户意见反馈接口API。【1】反馈意见。")
public class UserSuggestionController {

    @Autowired
    private UserSuggestionFacade userSuggestionFacade;

    @RequestMapping(value = "/suggest", method = RequestMethod.POST)
    @ApiOperation(value = "反馈意见", notes = "反馈意见")
    @Authorization
    @TokenVerify(verifyType = TokenVerify.VerifyEnum.MINE)
    public ModelAndView suggest(@RequestParam(name = "token") String token,
                                @RequestParam(name = "userId") int userId,
                                @RequestParam(name = "content") String content) {
        AbstractView jsonView = new MappingJackson2JsonView();

        if (StringUtils.isEmpty(token)) {// 缺少参数
            jsonView.setAttributesMap(UserConstants.MAP_PARAM_MISS);
        } else {
            jsonView.setAttributesMap(doSuggest(buildParam(token, userId, content)));
        }

        return new ModelAndView(jsonView);
    }

    private Map<String, Object> doSuggest(UserSuggestionParam param) {
        try {
            return userSuggestionFacade.suggest(param);
        } catch (Exception e) {
            e.printStackTrace();
            return UserConstants.MAP_500;
        }
    }

    private UserSuggestionParam buildParam(String token, int userId, String content) {
        UserSuggestionParam param = new UserSuggestionParam();
        param.setToken(token);
        param.setUserId(userId);
        param.setContent(content);
        return param;
    }
}
