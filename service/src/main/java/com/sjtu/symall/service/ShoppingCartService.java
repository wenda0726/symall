package com.sjtu.symall.service;

import com.sjtu.symall.entity.ShoppingCart;
import com.sjtu.symall.vo.ResultVO;

import java.util.List;

public interface ShoppingCartService {

    //添加购物车接口
    public ResultVO addShoppingCart(ShoppingCart cart);

    //按照用户ID查询购物车信息
    public ResultVO listShoppingCartByUserId(String userId);

    //根据购物车ID，修改购物车中的商品数量
    public ResultVO updateShoppingCartByCartID(int carId,int cartNum);


    //根据购物车ID，删除对应的购物车记录
    public ResultVO deleteShoppingCartByCartId(int cartId);

    //根据购物车ID集合，查询对应的购物车记录
    public ResultVO listShoppingCartByCids(String cids);
}
