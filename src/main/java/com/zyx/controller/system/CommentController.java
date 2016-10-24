package com.zyx.controller.system;

import com.zyx.config.BaseResponse;
import com.zyx.rpc.system.CommentFacade;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.AbstractView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.Map;

/**
 * @author XiaoWei
 * @version V 1.0
 * @package com.zyx.controller.system
 * Create by XiaoWei on 2016/8/29
 */

@RestController
@RequestMapping("/v1/comment")
@Api(description = "评论接口")
public class CommentController {

    @Autowired
    private CommentFacade commentFacade;

    @RequestMapping(value = "/insert", method = {RequestMethod.POST})
    @ApiOperation(value = "发表评论", notes = "发表评论", response = BaseResponse.class)
    public ModelAndView addComment(
            @ApiParam(required = true, name = "comment_type", value = "评论类型，0：圈子,1：帖子，2：活动，3：动态，4：直播")@RequestParam(value = "comment_type") Integer commentType,
            @ApiParam(required = true, name = "comment_id", value = "评论modelId类型") @RequestParam(value = "comment_id") Integer comment_id,
            @ApiParam(required = true, name = "comment_account", value = "发表评论用户id") @RequestParam(value = "comment_account") Integer comment_account,
            @ApiParam(required = true, name = "comment_content", value = "评论内容") @RequestParam(value = "comment_content") String comment_content,
                                   @RequestParam(value = "comment_state", required = false, defaultValue = "0") Integer comment_state) {
        Map<String, Object> map = commentFacade.addComment(commentType, comment_id, comment_content, comment_account, comment_state);
        AbstractView jsonView = new MappingJackson2JsonView();
        jsonView.setAttributesMap(map);
        return new ModelAndView(jsonView);

    }

    @RequestMapping(value = "/query/{comment_type}/{comment_id}", method = {RequestMethod.GET})
    @ApiOperation(value = "评论列表", notes = "首推-评论列表", response = BaseResponse.class)
    public ModelAndView queryComment(
            @ApiParam(required = true, name = "comment_type", value = "评论类型，0：圈子,1：帖子，2：活动，3：动态，4：直播") @PathVariable(value = "comment_type") Integer commentType,
            @ApiParam(required = true, name = "comment_id", value = "评论modelId类型") @PathVariable(value = "comment_id") Integer comment_id) {
        Map<String, Object> map = commentFacade.queryComment(commentType,comment_id);
        AbstractView jsonView = new MappingJackson2JsonView();
        jsonView.setAttributesMap(map);
        return new ModelAndView(jsonView);

    }


}
