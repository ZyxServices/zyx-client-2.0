package com.zyx.controller.system;

import com.zyx.config.BaseResponse;
import com.zyx.constants.Constants;
import com.zyx.param.account.UserMsgParam;
import com.zyx.rpc.account.AccountCommonFacade;
import com.zyx.rpc.system.MsgFacade;
import com.zyx.rpc.system.ReplyFacade;
import com.zyx.utils.ActivityUtils;
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
 * Create by XiaoWei on 2016/8/31
 */
@RestController
@RequestMapping("/v2/reply")
@Api(description = "回复相关接口")
public class ReplyController {
    @Autowired
    private ReplyFacade replyFacade;

    @Autowired
    private MsgFacade msgFacade;

    @Autowired
    private AccountCommonFacade commonFacade;

    @RequestMapping(value = "/addReply", method = RequestMethod.POST)
    @ApiOperation(value = "发表回复", notes = "发表回复")
    public ModelAndView addReply(@RequestParam("token") String token,
                                 @ApiParam(required = true, name = "reply_parent_id", value = "评论") @RequestParam("reply_parent_id") Integer replyParentId,
                                 @ApiParam(required = true, name = "reply_from_user", value = "发表评论用户id") @RequestParam("reply_from_user") Integer replyFromUser,
                                 @ApiParam(required = true, name = "reply_to_user", value = "发表评论用户id") @RequestParam(value = "reply_to_user", required = false, defaultValue = "-1") Integer replyToUser,
                                 @RequestParam("reply_content") String replyContent,
                                 @ApiParam(name = "reply_img_path", value = "回复图片") @RequestParam(value = "reply_img_path", required = false) String replyImgPath) {

        boolean token1 = commonFacade.validateToken(token);
        if (!token1) return new ModelAndView(ActivityUtils.tokenFailure());

        Map<String, Object> map = replyFacade.addReply(replyParentId, replyFromUser, replyToUser, replyContent, replyImgPath);
        AbstractView jsonView = new MappingJackson2JsonView();
        jsonView.setAttributesMap(map);
        // 回复成功，进行消息提示
        checkAndSendMsg(map, replyParentId, replyFromUser, replyToUser, replyContent);
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/del/{token}/{id}/{reply_account_id}", method = {RequestMethod.GET})
    @ApiOperation(value = "删除评论", notes = "删除评论", response = BaseResponse.class)
    public ModelAndView delComment(
            @ApiParam(required = true, name = "token") @PathVariable(value = "token") String  token,
            @ApiParam(required = true, name = "id", value = "回复id") @PathVariable(value = "id") Integer id,
            @ApiParam(required = true, name = "reply_account_id", value = "回复用户id") @PathVariable(value = "reply_account_id") Integer comment_account_id) {

        boolean token1 = commonFacade.validateToken(token);
        if (!token1) return new ModelAndView(ActivityUtils.tokenFailure());

        Map<String, Object> map = replyFacade.delReply(id, comment_account_id);
        AbstractView jsonView = new MappingJackson2JsonView();
        jsonView.setAttributesMap(map);
        return new ModelAndView(jsonView);

    }

    private void checkAndSendMsg(Map<String, Object> map, Integer replyParentId, Integer replyFromUser, Integer replyToUser, String replyContent) {
        try {
            Integer state = (Integer) map.get(Constants.STATE);
            if (null != state && state == Constants.SUCCESS) {
                MsgPool.getMsgPool().execute(new InsertMsgRunnable(msgFacade, buildUserMsgParam(replyFromUser, replyToUser, replyContent)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private UserMsgParam buildUserMsgParam(Integer replyFromUser, Integer replyToUser, String replyContent) {
        //TODO:联调的时候参数进行修改
        UserMsgParam param = new UserMsgParam();
        param.setFromUserId(replyFromUser);
        param.setToUserId(replyToUser);
        param.setFromContent(replyContent);
        param.setBodyId(replyToUser);
        param.setBodyType(99);// 消息主体类型。用户99
        param.setMsgType(1);
        param.setCreateTime(System.currentTimeMillis());
        return param;
    }

}
