package com.zyx.controller.system;

import com.zyx.param.system.SearchParam;
import com.zyx.rpc.account.AccountCommonFacade;
import com.zyx.rpc.system.SearchFacade;
import com.zyx.utils.ActivityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(description = "App 热门查询")
public class SearchController {

    @Resource
    private SearchFacade searchFacade;

    @RequestMapping(value = "/model", method = {RequestMethod.POST})
    @ApiOperation(value = "热门查询", notes = "热门查询")
    public ModelAndView searchModel(@RequestParam(name = "userId", required = false) Integer userId,
                                    @RequestParam(name = "character", required = false) String character,
                                    @RequestParam(name = "model") int model,
                                    @RequestParam(name = "pageNumber") Integer pageNumber,
                                    @RequestParam(name = "pages") Integer pages) {

        AbstractView jsonView = new MappingJackson2JsonView();
        SearchParam searchParam = new SearchParam();
        searchParam.setUserId(userId);
        searchParam.setCharacter(character);
        searchParam.setModel(model);
        searchParam.setPageNumber(pageNumber);
        searchParam.setPages(pages);
        Map<String, Object> map = searchFacade.modularSearch(searchParam);
        jsonView.setAttributesMap(map);
        return new ModelAndView(jsonView);
    }


    @RequestMapping(value = "/accountByNo", method = {RequestMethod.POST})
    @ApiOperation(value = "热门查询", notes = "热门查询")
    public ModelAndView accountByNo(@RequestParam(name = "userId", required = false) Integer userId,
                                    @RequestParam(name = "pageNumber") Integer pageNumber,
                                    @RequestParam(name = "pages") Integer pages) {

        AbstractView jsonView = new MappingJackson2JsonView();
        SearchParam searchParam = new SearchParam();
        searchParam.setUserId(userId);
        searchParam.setPageNumber(pageNumber);
        searchParam.setPages(pages);
        Map<String, Object> map = searchFacade.searchAccountByNO(searchParam);
        jsonView.setAttributesMap(map);
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/cirleByNo", method = {RequestMethod.POST})
    @ApiOperation(value = "热门查询", notes = "热门查询")
    public ModelAndView cirleByNo(@RequestParam(name = "userId", required = false) Integer userId,
                                    @RequestParam(name = "pageNumber") Integer pageNumber,
                                    @RequestParam(name = "pages") Integer pages) {

        AbstractView jsonView = new MappingJackson2JsonView();
        SearchParam searchParam = new SearchParam();
        searchParam.setUserId(userId);
        searchParam.setPageNumber(pageNumber);
        searchParam.setPages(pages);
        Map<String, Object> map = searchFacade.searchCirleByNo(searchParam);
        jsonView.setAttributesMap(map);
        return new ModelAndView(jsonView);
    }


}
