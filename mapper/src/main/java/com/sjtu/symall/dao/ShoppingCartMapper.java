package com.sjtu.symall.dao;

import com.sjtu.symall.entity.ShoppingCart;
import com.sjtu.symall.entity.ShoppingCartVO;
import com.sjtu.symall.general.GeneralDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShoppingCartMapper extends GeneralDao<ShoppingCart> {

    public List<ShoppingCartVO> selectShoppingCartByUserId(String userId);

    //根据购物车id，商品数量修改购物车记录
    public int updateCartNumByCartId(@Param("cartId") int cartId, @Param("cartNum") int cartNum);

    //根据购物车id集合，查询购物车记录
    public List<ShoppingCartVO> selectShoppingCartByCids(List<Integer> cids);
}