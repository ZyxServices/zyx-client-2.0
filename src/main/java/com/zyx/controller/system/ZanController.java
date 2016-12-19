package com.zyx.controller.system;

import com.zyx.constants.Constants;
import com.zyx.param.account.UserMsgParam;
import com.zyx.rpc.system.MsgFacade;
import com.zyx.rpc.zoom.ZoomFacade;
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

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author XiaoWei
 * @version V 1.0
 * @package com.zyx.controller.system
 * Create by XiaoWei on 2016/11/21
 */
@RestController
@RequestMapping(name = "/v2")
public class ZanController {

    @Resource
    private ZoomFacade zoomFacade;

    @Autowired
    private MsgFacade msgFacade;

    @RequestMapping(value = "/v2/zan/add", method = RequestMethod.POST)
    @ApiOperation(value = "点赞", notes = "点赞")
    public ModelAndView zan(@RequestParam(name = "token") String token,
                            @ApiParam(required = true, name = "bodyId", value = "模块id") @RequestParam(name = "bodyId") Integer bodyId,
                            @ApiParam(required = true, name = "bodyType", value = "点赞模块类型0：文章,1：动态，2：装备控，3记录，4活动") @RequestParam(name = "bodyType") Integer bodyType,
                            @ApiParam(required = true, name = "bodyUserId", value = "点赞模块创建者ID") @RequestParam(name = "bodyUserId") Integer bodyUserId,
                            @ApiParam(required = true, name = "accountId", value = "点赞人id") @RequestParam(name = "accountId") Integer accountId) {
        Map<String, Object> map = zoomFacade.addZan(bodyId, bodyType, accountId);
        AbstractView jsonView = new MappingJackson2JsonView();
        jsonView.setAttributesMap(map);
        // 报名成功，进行消息提示
        checkAndSendMsg(map, accountId, bodyId, bodyUserId, bodyType);
        return new ModelAndView(jsonView);
    }

    private void checkAndSendMsg(Map<String, Object> map, Integer accountId, Integer bodyId, Integer bodyUserId, Integer bodyType) {
        try {
            Integer state = (Integer) map.get(Constants.STATE);
            if (null != state && state == Constants.SUCCESS) {
                String temp = getFromContent(bodyType);
                MsgPool.getMsgPool().execute(new InsertMsgRunnable(msgFacade, buildUserMsgParam(accountId, bodyId, bodyUserId, bodyType, "赞了我的:" + temp)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getFromContent(Integer bodyType) {
        return bodyType == 0 ? "文章" : bodyType == 1 ? "动态" : bodyType == 2 ? "装备控" : bodyType == 4 ? "活动" : "";
    }

    private UserMsgParam buildUserMsgParam(Integer accountId, Integer bodyId, Integer bodyUserId, Integer bodyType, String msg) {
        //TODO:联调的时候参数进行修改
        UserMsgParam param = new UserMsgParam();
        param.setFromUserId(accountId);
        param.setToUserId(bodyUserId);
        param.setFromContent(msg);
        param.setBodyId(bodyId);
        param.setBodyType(bodyType);
        param.setMsgType(3);
        param.setCreateTime(System.currentTimeMillis());
        return param;
    }
}
