package com.zyx.controller.system;

//import com.zyx.param.system.SearchParam;
import com.zyx.param.system.SearchParam;
import com.zyx.rpc.account.AccountCommonFacade;
import com.zyx.rpc.system.SearchFacade;
import com.zyx.utils.ActivityUtils;
import io.swagger.annotations.Api;
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
 * Created by Rainbow on 2016/8/24.
 */
@RestController
@RequestMapping("/v1/search")
@Api(description = "App 多模块查询")
public class SearchController {

    @Resource
    private SearchFacade searchFacade;

    @RequestMapping(value = "/model", method = {RequestMethod.POST})
    @ApiOperation(value = "热门查询", notes = "热门查询")
    public ModelAndView searchModel(@ApiParam(name = "character", value = "查询关键词") @RequestParam(name = "character", required = false) String character,
                                    @ApiParam(required = true, name = "model", value = "查询分类model（1、活动，2、装备，3、动态，4、场馆，5、教程，6、用户）") @RequestParam(name = "model") Integer model,
                                    @ApiParam(required = true, name = "number", value = "每页显示多少条") @RequestParam(name = "number") Integer number,
                                    @ApiParam(required = true, name = "pageNumber", value = "当前第几页") @RequestParam(name = "pageNumber") Integer pageNumber) {

        AbstractView jsonView = new MappingJackson2JsonView();
        SearchParam searchParam = new SearchParam();
        searchParam.setCharacter(character);
        searchParam.setModel(model);
        searchParam.setPageNumber(pageNumber);
        searchParam.setNumber(number);
        Map<String, Object> map = searchFacade.modularSearch(searchParam);
        jsonView.setAttributesMap(map);
        return new ModelAndView(jsonView);
    }
}
