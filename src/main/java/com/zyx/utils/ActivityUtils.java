package com.zyx.utils;

import com.zyx.constants.Constants;
import org.springframework.web.servlet.view.AbstractView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by SubDong on 16-6-28.
 *
 * @author SubDong
 * @version V1.0
 *          Copyright (c)2016 tyj-版权所有
 * @title ActivityUtils
 * @package com.zyx.utils
 * @update 16-6-28 上午11:35
 */
public class ActivityUtils {

    public static AbstractView tokenFailure(){
        AbstractView jsonView = new MappingJackson2JsonView();
        Map<String, Object> map = new HashMap<>();
        map.put(Constants.STATE, Constants.TOKEN_FAILURE);
        map.put(Constants.ERROR_MSG, "token失效");
        jsonView.setAttributesMap(map);
        return jsonView;
    }

}
