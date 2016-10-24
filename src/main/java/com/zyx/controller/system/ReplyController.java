package com.zyx.controller.system;

import com.zyx.rpc.system.ReplyFacade;
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

import java.util.Map;

/**
 * @author XiaoWei
 * @version V 1.0
 * @package com.zyx.controller.system
 * Create by XiaoWei on 2016/8/31
 */
@RestController
@RequestMapping("/v1/reply")
@Api(description = "回复相关接口")
public class ReplyController {
    @Autowired
    private ReplyFacade replyFacade;

    @RequestMapping(value = "/addReply", method = RequestMethod.POST)
    @ApiOperation(value = "发表回复", notes = "发表回复")
    public ModelAndView addReply(@RequestParam("token") String token,
                                 @ApiParam(required = true, name = "reply_parent_id", value = "评论") @RequestParam("reply_parent_id") Integer replyParentId,
                                 @ApiParam(required = true, name = "reply_from_user", value = "发表评论用户id") @RequestParam("reply_from_user") Integer replyFromUser,
                                 @ApiParam(required = true, name = "reply_to_user", value = "发表评论用户id") @RequestParam(value = "reply_to_user", required = false, defaultValue = "-1") Integer replyToUser,
                                 @RequestParam("reply_content") String replyContent) {
        Map<String, Object> map = replyFacade.addReply(replyParentId, replyFromUser, replyToUser, replyContent);
        AbstractView jsonView = new MappingJackson2JsonView();
        jsonView.setAttributesMap(map);
        return new ModelAndView(jsonView);
    }
}
