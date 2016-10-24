package com.zyx.controller.account;

import com.zyx.constants.Constants;
import com.zyx.param.account.UserAddressParam;
import com.zyx.rpc.account.AccountAddressFacade;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.AbstractView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.UUID;

/**
 * Created by wms on 2016/6/20.
 *
 * @author WeiMinSheng
 * @version V1.0
 *          Copyright (c)2016 tyj-版权所有
 */
@RestController
@RequestMapping("/v1/account/receiptAddress")
@Api(description = "用户收货地址服务API。1、新增收货地址。2、编辑收货地址。3、获取收货地址。4、删除收货地址。5、设置默认收货地址。6、获取用户收货地址列表")
public class AddressController {

    @Autowired
    private AccountAddressFacade accountAddressFacade;

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ApiOperation(value = "新增收货地址", notes = "新增收货地址。需要token验证。")
    public ModelAndView insert(@RequestParam(name = "token") String token,
                               @RequestParam(name = "account_id") Integer userId,
                               @ApiParam(required = true, name = "name", value = "收货人姓名")
                               @RequestParam(name = "name") String receiver,
                               @RequestParam(name = "phone") String phone,
                               @ApiParam(required = true, name = "zipCode", value = "邮编")
                               @RequestParam(name = "zipCode") String zipCode,
                               @RequestParam(name = "content") String content) {
        AbstractView jsonView = new MappingJackson2JsonView();

        if (StringUtils.isEmpty(token) || StringUtils.isEmpty(userId) || StringUtils.isEmpty(receiver) || StringUtils.isEmpty(phone) || StringUtils.isEmpty(zipCode) || StringUtils.isEmpty(content)) {// 缺少参数
            jsonView.setAttributesMap(Constants.MAP_PARAM_MISS);
        } else {
            UserAddressParam param = new UserAddressParam();
            param.setToken(token);
            param.setUserId(userId);
            param.setContent(content);
            param.setPhone(phone);
            param.setReceiver(receiver);
            param.setZipCode(zipCode);
            param.setAddressId(UUID.randomUUID().toString().replaceAll("-", ""));
            jsonView.setAttributesMap(accountAddressFacade.insertAccountAddressInfo(param));
        }
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ApiOperation(value = "编辑收货地址", notes = "编辑收货地址。需要token验证。")
    public ModelAndView edit(@RequestParam(name = "token") String token,
                             @RequestParam(name = "address_id") String address_id,
                             @ApiParam(required = true, name = "name", value = "收货人姓名")
                             @RequestParam(name = "name", required = false) String receiver,
                             @RequestParam(name = "phone", required = false) String phone,
                             @ApiParam(required = true, name = "zipCode", value = "邮编")
                             @RequestParam(name = "zipCode", required = false) String zipCode,
                             @RequestParam(name = "content", required = false) String content) {
        AbstractView jsonView = new MappingJackson2JsonView();

        if (StringUtils.isEmpty(token) || StringUtils.isEmpty(address_id)) {// 缺少参数
            jsonView.setAttributesMap(Constants.MAP_PARAM_MISS);
        } else {
            UserAddressParam param = new UserAddressParam();
            param.setToken(token);
            param.setAddressId(address_id);
            param.setContent(content);
            param.setPhone(phone);
            param.setReceiver(receiver);
            param.setZipCode(zipCode);
            jsonView.setAttributesMap(accountAddressFacade.editReceiptAddress(param));
        }
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/info", method = {RequestMethod.GET, RequestMethod.POST})
    @ApiOperation(value = "获取收货地址", notes = "获取收货地址。通过address_id查询收货地址信息，需要token验证。")
    public ModelAndView info(@RequestParam(name = "token") String token,
                             @ApiParam(required = true, name = "address_id", value = "address_id：32位的字符串")
                             @RequestParam(name = "address_id") String addressId) {
        AbstractView jsonView = new MappingJackson2JsonView();
        if (StringUtils.isEmpty(token) || StringUtils.isEmpty(addressId)) {// 缺少参数
            jsonView.setAttributesMap(Constants.MAP_PARAM_MISS);
        } else {
            UserAddressParam param = new UserAddressParam();
            param.setToken(token);
            param.setAddressId(addressId);
            jsonView.setAttributesMap(accountAddressFacade.queryAccountAddressInfo(param));
        }
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/delete", method = {RequestMethod.DELETE, RequestMethod.POST})
    @ApiOperation(value = "删除收货地址", notes = "删除收货地址。通过address_id删除收货地址，需要token验证。")
    public ModelAndView delete(@RequestParam(name = "token") String token,
                               @ApiParam(required = true, name = "address_id", value = "address_id：32位的字符串")
                               @RequestParam(name = "address_id") String addressId) {
        AbstractView jsonView = new MappingJackson2JsonView();

        if (StringUtils.isEmpty(token) || StringUtils.isEmpty(addressId)) {// 缺少参数
            jsonView.setAttributesMap(Constants.MAP_PARAM_MISS);
        } else {
            UserAddressParam param = new UserAddressParam();
            param.setToken(token);
            param.setAddressId(addressId);
            jsonView.setAttributesMap(accountAddressFacade.deleteAccountAddressInfo(param));
        }
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/default", method = RequestMethod.POST)
    @ApiOperation(value = "设置默认收货地址", notes = "设置默认收货地址。需要token验证。")
    public ModelAndView defaultAddress(@RequestParam(name = "token") String token,
                                       @RequestParam(name = "account_id") Integer userId,
                                       @ApiParam(required = true, name = "address_id", value = "address_id：32位的字符串")
                                       @RequestParam(name = "address_id")String addressId) {
        AbstractView jsonView = new MappingJackson2JsonView();

        if (StringUtils.isEmpty(token) || StringUtils.isEmpty(userId) || StringUtils.isEmpty(addressId)) {// 缺少参数
            jsonView.setAttributesMap(Constants.MAP_PARAM_MISS);
        } else {
            UserAddressParam param = new UserAddressParam();
            param.setToken(token);
            param.setUserId(userId);
            param.setAddressId(addressId);
            jsonView.setAttributesMap(accountAddressFacade.setDefaultReceiptAddress(param));
        }
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
    @ApiOperation(value = "获取用户收货地址列表", notes = "获取用户收货地址列表。根据account_id获取收货地址列表，需要token验证。")
    public ModelAndView list(@RequestParam(name = "token") String token,
                             @RequestParam(name = "account_id") Integer userId) {
        AbstractView jsonView = new MappingJackson2JsonView();

        if (StringUtils.isEmpty(token) || StringUtils.isEmpty(userId)) {// 缺少参数
            jsonView.setAttributesMap(Constants.MAP_PARAM_MISS);
        } else {
            UserAddressParam param = new UserAddressParam();
            param.setToken(token);
            param.setUserId(userId);
            jsonView.setAttributesMap(accountAddressFacade.queryAccountAddressList(param));
        }
        return new ModelAndView(jsonView);
    }
}
