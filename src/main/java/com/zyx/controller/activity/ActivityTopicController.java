package com.zyx.controller.activity;

import com.zyx.param.activity.AddTopicParm;
import com.zyx.param.activity.QueryTopicParm;
import com.zyx.rpc.account.AccountCommonFacade;
import com.zyx.utils.ActivityUtils;
import com.zyx.constants.Constants;
import com.zyx.constants.activity.ActivityConstants;
import com.zyx.rpc.activity.ActivityTopicFacade;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.AbstractView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by SubDong on 16-6-16.
 *
 * @author SubDong
 * @version V1.0
 *          Copyright (c)2016 tyj-版权所有
 * @title ActivityTopicController
 * @package com.zyx.controller.activity
 * @update 16-6-16 下午2:45
 */
@RestController
@RequestMapping("/v1/activity")
public class ActivityTopicController {

    @Resource
    private ActivityTopicFacade activityTopicFacade;
    @Resource
    private AccountCommonFacade accountCommonFacade;

    @RequestMapping(value = "/dynamic", method = RequestMethod.POST)
    @ApiOperation(value = "发布活动动态", notes = "发布活动动态")
    public ModelAndView dynamic(@RequestParam(name = "token", required = false) String token,
                                @RequestParam(name = "activityId", required = true) Integer activitiId,
                                @RequestParam(name = "userId", required = true) Integer userId,
                                @RequestParam(name = "topicTitle", required = true) String topicTitle,
                                @RequestParam(name = "topicContent", required = true) String topicContent,
                                @RequestPart(name = "image", required = true) String[] image) {


        AbstractView jsonView = new MappingJackson2JsonView();

        boolean token1 = accountCommonFacade.validateToken(token);
        if (!token1) return new ModelAndView(ActivityUtils.tokenFailure());

        Map<String, Object> map = new HashMap<>();

        if (image == null) {
            jsonView.setAttributesMap(Constants.MAP_PARAM_MISS);
            return new ModelAndView(jsonView);
        }

        if (image.length > 9) {
            map.put(Constants.STATE,ActivityConstants.AUTH_ERROR_10007);
            map.put(Constants.ERROR_MSG,"图片上传超过9张图片");
        }


        AddTopicParm parm = new AddTopicParm();
        parm.setUserId(userId);
        parm.setActivityId(activitiId);
        parm.setImages(Arrays.toString(image));
        parm.setTopicTitle(topicTitle);
        parm.setTopicContent(topicContent);


        Map<String, Object> topic = activityTopicFacade.addActivityTopic(parm);

        jsonView.setAttributesMap(topic);
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/dynamicQuery", method = RequestMethod.POST)
    @ApiOperation(value = "查询当前活动的发布动态", notes = "查询当前活动的发布动态")
    public ModelAndView dynamicQuery(@RequestParam(name = "token", required = false) String token,
                                @RequestParam(name = "activityId", required = true) Integer activitiId,
                                @RequestParam(name = "pageNumber", required = true) Integer pageNumber,
                                @RequestParam(name = "page", required = true) Integer page) {


        AbstractView jsonView = new MappingJackson2JsonView();

/*        boolean token1 = accountCommonFacade.validateToken(token);
        if (!token1) return new ModelAndView(ActivityUtils.tokenFailure());*/

        QueryTopicParm parm = new QueryTopicParm();
        parm.setActivityId(activitiId);
        parm.setPages(page);
        parm.setPageNumber(pageNumber);


        Map<String, Object> topic = activityTopicFacade.dynamicQuery(parm);

        jsonView.setAttributesMap(topic);
        return new ModelAndView(jsonView);
    }
}
