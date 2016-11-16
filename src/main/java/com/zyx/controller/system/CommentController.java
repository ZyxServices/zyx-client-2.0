package com.zyx.controller.system;

import com.zyx.config.BaseResponse;
import com.zyx.constants.Constants;
import com.zyx.param.account.UserMsgParam;
import com.zyx.rpc.system.CommentFacade;
import com.zyx.rpc.system.MsgFacade;
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
 * Create by XiaoWei on 2016/11/11
 */
@RestController
@RequestMapping("/v2/comment")
@Api(description = "通用评论接口")
public class CommentController {
    @Autowired
    private CommentFacade commentFacade;

    @Autowired
    private MsgFacade msgFacade;

    @RequestMapping(value = "/insert", method = {RequestMethod.POST})
    @ApiOperation(value = "发表评论", notes = "发表评论", response = BaseResponse.class)
    public ModelAndView addComment(
            @ApiParam(required = true, name = "comment_type", value = "评论类型，0：文章/教程，1:动态，2：装备控") @RequestParam(value = "comment_type") Integer commentType,
            @ApiParam(required = true, name = "comment_id", value = "评论主体ID") @RequestParam(value = "comment_id") Integer comment_id,
            @ApiParam(required = true, name = "model_create_id", value = "评论主体创建者ID") @RequestParam(value = "model_create_id") Integer model_create_id,
            @ApiParam(required = true, name = "comment_account", value = "发表评论用户id") @RequestParam(value = "comment_account") Integer comment_account,
            @ApiParam(required = true, name = "comment_content", value = "评论内容") @RequestParam(value = "comment_content") String comment_content,
            @RequestParam(value = "comment_state", required = false, defaultValue = "0") Integer comment_state,
            @ApiParam(name = "comment_img_path", value = "评论图片") @RequestParam(value = "comment_img_path", required = false) String comment_img_path) {
        Map<String, Object> map = commentFacade.addComment(commentType, comment_id, comment_content, comment_account, comment_state, comment_img_path);
        AbstractView jsonView = new MappingJackson2JsonView();
        jsonView.setAttributesMap(map);
        // 评论发表成功，进行消息提示
        checkAndSendMsg(map, commentType, comment_id, model_create_id, comment_content, comment_account);
        return new ModelAndView(jsonView);

    }

    private void checkAndSendMsg(Map<String, Object> map, Integer commentType, Integer comment_id, Integer model_create_id, String comment_content, Integer comment_account) {
        try {
            Integer state = (Integer) map.get(Constants.STATE);
            if (null != state && state == Constants.SUCCESS) {
                MsgPool.getMsgPool().execute(new InsertMsgRunnable(msgFacade, buildUserMsgParam(commentType, comment_id, model_create_id, comment_content, comment_account)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private UserMsgParam buildUserMsgParam(Integer commentType, Integer comment_id, Integer model_create_id, String comment_content, Integer comment_account) {
        UserMsgParam param = new UserMsgParam();
        param.setFromUserId(comment_account);
        param.setToUserId(model_create_id);
        param.setBodyId(comment_id);
        param.setBodyType(commentType);
        param.setFromContent(comment_content);
        param.setCreateTime(System.currentTimeMillis());
        return param;
    }

    @RequestMapping(value = "/query/{comment_type}/{comment_id}", method = {RequestMethod.GET})
    @ApiOperation(value = "评论列表", notes = "首推-评论列表", response = BaseResponse.class)
    public ModelAndView queryComment(
            @ApiParam(required = true, name = "comment_type", value = "评论类型，0：文章/教程，1:动态，2：装备控") @PathVariable(value = "comment_type") Integer commentType,
            @ApiParam(required = true, name = "comment_id", value = "评论modelId类型") @PathVariable(value = "comment_id") Integer comment_id) {
        Map<String, Object> map = commentFacade.queryComment(commentType, comment_id);
        AbstractView jsonView = new MappingJackson2JsonView();
        jsonView.setAttributesMap(map);
        return new ModelAndView(jsonView);

    }
}
