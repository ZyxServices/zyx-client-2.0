package com.zyx.controller.collection;

import com.zyx.constants.Constants;
import com.zyx.constants.account.AccountConstants;
import com.zyx.constants.collection.CollConstants;
import com.zyx.constants.live.LiveConstants;
import com.zyx.entity.collection.Collection;
import com.zyx.param.Pager;
import com.zyx.param.collection.CollectionParam;
import com.zyx.rpc.account.AccountCommonFacade;
import com.zyx.rpc.collection.CollectionFacade;
import com.zyx.vo.account.AccountInfoVo;
import com.zyx.vo.account.UserIconVo;
import com.zyx.vo.collection.CollectionVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.AbstractView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by MrDeng on 2016/8/23.
 */

@RestController
@RequestMapping("/v1/collect")
@Api(description = "直播相关接口")
public class CollectionController {

    @Autowired
    AccountCommonFacade accountCommonFacade;
    @Autowired
    CollectionFacade collectionFacade;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ApiOperation(value = "添加收藏", notes = "收藏-添加收藏记录")
    public ModelAndView createCollection(
            @RequestParam(name = "token", required = true) String token,
            @ApiParam(required = true, name = "model", value = "模块类型（1活动，2直播，3圈子，4帖子，5动态，6用户，7系统）") @RequestParam(name = "model", required = true) Integer model,
            @ApiParam(required = true, name = "modelId", value = "模块ID") @RequestParam(name = "modelId", required = true) Integer modelId) {
        // Token 验证
        Map<String, Object> attrMap = new HashMap<>();
        if (token == null || "".equals(token)) {
            attrMap.put(LiveConstants.STATE, LiveConstants.REQUEST_UNAUTHORIZED);
            attrMap.put(LiveConstants.ERROR_MSG, LiveConstants.MSG_REQUEST_UNAUTHORIZED);
        } else if (model == null || modelId == null) {
            attrMap.put(LiveConstants.STATE, LiveConstants.PARAM_MISS);
            attrMap.put(LiveConstants.ERROR_MSG, LiveConstants.MSG_PARAM_MISS);
        } else if (!(model == 1 || model == 2 || model == 3 || model == 4 || model == 6 || model == 5)) {
            attrMap.put(LiveConstants.STATE, LiveConstants.PARAM_ILIGAL);
            attrMap.put(LiveConstants.ERROR_MSG, LiveConstants.MSG_PARAM_ILIGAL);
        } else {
            AccountInfoVo account = accountCommonFacade.getAccountVoByToken(token);
            if (account == null || account.getId() == null) {
                attrMap.put(LiveConstants.STATE, AccountConstants.ACCOUNT_ERROR_CODE_50000);
                attrMap.put(LiveConstants.ERROR_MSG, AccountConstants.ACCOUNT_ERROR_CODE_50000_MSG);
            } else {
                Collection collection = new Collection();
                collection.setUserId(account.getId());
                collection.setModel(model);
                collection.setModelId(modelId);
                int count = collectionFacade.count(collection);
                if (count != 0) {
                    attrMap.put(LiveConstants.STATE, CollConstants.COLL_EXSIT_COLLECTION);
                    attrMap.put(LiveConstants.ERROR_MSG, CollConstants.MSG_COLL_EXSIT_COLLECTION);
                } else {
                    collectionFacade.addCollection(collection);
                    attrMap.put(LiveConstants.STATE, LiveConstants.SUCCESS);
                }
            }
        }
        AbstractView jsonView = new MappingJackson2JsonView();
        jsonView.setAttributesMap(attrMap);
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ApiOperation(value = "获取收藏列表", notes = "收藏-获取收藏列表")
    public ModelAndView getCollections(@RequestParam(name = "token", required = true) String token,
                                       @ApiParam(required = false, name = "model", value = "模块类型（1活动，2直播，3圈子，4帖子，5动态，6用户，7系统）") @RequestParam(name = "model", required = false) Integer model,
                                       @RequestParam(name = "pageNo", required = false) Integer pageNo,
                                       @RequestParam(name = "pageSize", required = false) Integer pageSize
    ) {
        Map<String, Object> attrMap = new HashMap<>();
        if (token == null || "".equals(token)) {
            attrMap.put(LiveConstants.STATE, LiveConstants.REQUEST_UNAUTHORIZED);
            attrMap.put(LiveConstants.ERROR_MSG, LiveConstants.MSG_REQUEST_UNAUTHORIZED);
        } else if (model == null) {
            attrMap.put(LiveConstants.STATE, LiveConstants.PARAM_MISS);
            attrMap.put(LiveConstants.ERROR_MSG, LiveConstants.MSG_PARAM_MISS);
        } else if (!(model == 1 || model == 2 || model == 3 || model == 4 || model == 6 || model == 5)) {
            attrMap.put(LiveConstants.STATE, LiveConstants.PARAM_ILIGAL);
            attrMap.put(LiveConstants.ERROR_MSG, LiveConstants.MSG_PARAM_ILIGAL);
        } else {
            AccountInfoVo account = accountCommonFacade.getAccountVoByToken(token);
            if (account == null || account.getId() == null) {
                attrMap.put(LiveConstants.STATE, AccountConstants.ACCOUNT_ERROR_CODE_50000);
                attrMap.put(LiveConstants.ERROR_MSG, AccountConstants.ACCOUNT_ERROR_CODE_50000_MSG);
            } else {
                CollectionParam param = new CollectionParam();
                param.setUserId(account.getId());
                param.setModel(model);
                if (pageSize != null && pageNo != null) {
                    Pager pager = new Pager(pageNo, pageSize);
                    param.setPager(pager);
                }
                List<CollectionVo> cs = collectionFacade.selectCollections(param);
                attrMap.put(Constants.DATA, cs);
                attrMap.put(LiveConstants.STATE, LiveConstants.SUCCESS);
            }
        }
        AbstractView jsonView = new MappingJackson2JsonView();
        jsonView.setAttributesMap(attrMap);
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/concel", method = RequestMethod.POST)
    @ApiOperation(value = "取消收藏", notes = "收藏-取消收藏 id不为空 或者（model 与 modelID不为空）")
    public ModelAndView concelCollection(
            @RequestParam(name = "token", required = true) String token,
            @ApiParam(required = false, name = "id", value = "收藏ID") @RequestParam(name = "id", required = false) Integer id,
            @ApiParam(required = false, name = "model", value = "模块类型（1活动，2直播，3圈子，4帖子，5动态，6用户，7系统）") @RequestParam(name = "model", required = false) Integer model,
            @ApiParam(required = false, name = "modelId", value = "模块ID") @RequestParam(name = "modelId", required = false) Integer modelId
    ) {
        // Token 验证
        Map<String, Object> attrMap = new HashMap<>();
        if (token == null || "".equals(token)) {
            attrMap.put(LiveConstants.STATE, LiveConstants.REQUEST_UNAUTHORIZED);
            attrMap.put(LiveConstants.ERROR_MSG, LiveConstants.MSG_REQUEST_UNAUTHORIZED);
        } else if (id == null && (model == null || modelId == null)) {
            attrMap.put(LiveConstants.STATE, LiveConstants.PARAM_MISS);
            attrMap.put(LiveConstants.ERROR_MSG, LiveConstants.MSG_PARAM_MISS);
        } else {
            AccountInfoVo account = accountCommonFacade.getAccountVoByToken(token);
            if (account == null || account.getId() == null) {
                attrMap.put(LiveConstants.STATE, AccountConstants.ACCOUNT_ERROR_CODE_50000);
                attrMap.put(LiveConstants.ERROR_MSG, AccountConstants.ACCOUNT_ERROR_CODE_50000_MSG);
            } else {
                Collection record = new Collection();
                record.setId(id);
                record.setUserId(account.getId());
                record.setModel(model);
                record.setModelId(modelId);
                collectionFacade.cancelCollect(record);
                attrMap.put(LiveConstants.STATE, LiveConstants.SUCCESS);
            }
        }
        AbstractView jsonView = new MappingJackson2JsonView();
        jsonView.setAttributesMap(attrMap);
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    @ApiOperation(value = "获取收藏列表", notes = "收藏-获取收藏列表")
    public ModelAndView getCollUserBaseInfo(
            @ApiParam(required = true, name = "model", value = "模块类型（1活动，2直播，3圈子，4帖子，5动态，6用户，7系统）") @RequestParam(name = "model", required = true) Integer model,
            @ApiParam(required = true, name = "modelId", value = "模块ID") @RequestParam(name = "modelId", required = true) Integer modelId
    ) {
        Map<String, Object> attrMap = new HashMap<>();
        List<UserIconVo> list = collectionFacade.getCollUserIcons(model, modelId);
        attrMap.put(LiveConstants.DATA, list);
        attrMap.put(LiveConstants.STATE, LiveConstants.SUCCESS);
        AbstractView jsonView = new MappingJackson2JsonView();
        jsonView.setAttributesMap(attrMap);
        return new ModelAndView(jsonView);
    }
}
