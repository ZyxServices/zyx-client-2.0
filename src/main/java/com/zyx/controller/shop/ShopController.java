package com.zyx.controller.shop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zyx.config.ErrorResponseEntity;
import com.zyx.entity.live.LiveInfo;
import com.zyx.entity.shop.Goods;
import com.zyx.rpc.shop.ShopService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/shop")
public class ShopController {
	@Autowired
	private ShopService shopService;

	@RequestMapping(value = "/view/{id}",method = RequestMethod.POST)
	 @ApiOperation(value="商场接口", notes="登录商品信息")
	public Goods view(@PathVariable Integer id) {
		//Goods goods = shopService.getGoodsbyKey(id);
		Goods goods = new Goods();
		goods.setId(1234);
		
		return goods;
	}
	
	
	 @RequestMapping(value = "/test",method = RequestMethod.GET)
	    @ApiOperation(value="测试接口", notes="还是测试",response = LiveInfo.class)
	    @ApiResponses(value = {
	            @ApiResponse(code = 400, message = "指定信息用户不存在",
	                    response = ErrorResponseEntity.class)
	    })
	    public ResponseEntity test(  @ApiParam(required=true, name="id", value="用户id") @RequestParam(name = "id") Integer id) {
	    	LiveInfo info = new LiveInfo();
	    	info.setId(id);
	    	info.setBgmUrl("www.baidu.com");
	    	info.setCreateTime(12344l);
	    	info.setEndTime(22222l);
	    	info.setTitle("test");
	    	return ResponseEntity.ok(info);
	    }

}
