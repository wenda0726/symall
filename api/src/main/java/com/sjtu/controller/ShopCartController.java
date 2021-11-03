package com.sjtu.controller;

import com.sjtu.symall.entity.ShoppingCart;
import com.sjtu.symall.service.ShoppingCartService;
import com.sjtu.symall.utils.Base64Utils;
import com.sjtu.symall.vo.ResStatus;
import com.sjtu.symall.vo.ResultVO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shopcart")
@Api(value = "提供购物车相关接口",tags = "购物车管理")
@CrossOrigin
public class ShopCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @PostMapping("/add")
    @ApiOperation("商品添加购物车提交接口")
    public ResultVO addShoppingCart(@RequestBody ShoppingCart cart,@RequestHeader("token") String token){
        ResultVO resultVO = shoppingCartService.addShoppingCart(cart);
        return resultVO;
    }

    @GetMapping("/list")
    @ApiOperation("根据用户ID查询购物车信息接口")
    public ResultVO listShoppingCart(String userId,@RequestHeader("token") String token){
        return shoppingCartService.listShoppingCartByUserId(userId);
    }

    @PutMapping("/update/{cid}/{cnum}")
    @ApiOperation("修改购物车中商品数量接口")
    public ResultVO updateShoppingCart(@PathVariable("cid") Integer cartId,@PathVariable("cnum") Integer cartNum,@RequestHeader("token") String token){
        return shoppingCartService.updateShoppingCartByCartID(cartId,cartNum);
    }

    @DeleteMapping("/delete/{cid}")
    @ApiOperation("根据购物车ID删除对应的购物车记录")
    public ResultVO deleteShoppingCart(@PathVariable("cid") Integer cid,@RequestHeader("token") String token){
        return shoppingCartService.deleteShoppingCartByCartId(cid);
    }

    @GetMapping("/listbycids")
    @ApiOperation("根据购物车ID集合查询对应的购物车记录")
    @ApiImplicitParam(dataType = "String",name = "cids",value = "用户ID集合",required = true)
    public ResultVO listShoppingCartByCids(String cids,@RequestHeader("token") String token){
        return shoppingCartService.listShoppingCartByCids(cids);
    }
}
