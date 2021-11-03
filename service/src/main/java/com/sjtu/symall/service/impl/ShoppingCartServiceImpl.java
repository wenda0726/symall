package com.sjtu.symall.service.impl;

import com.sjtu.symall.dao.ShoppingCartMapper;
import com.sjtu.symall.entity.ShoppingCart;
import com.sjtu.symall.entity.ShoppingCartVO;
import com.sjtu.symall.service.ShoppingCartService;
import com.sjtu.symall.vo.ResStatus;
import com.sjtu.symall.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Autowired
    private ShoppingCartMapper shoppingCartMapper;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    @Override
    public ResultVO addShoppingCart(ShoppingCart cart) {
        cart.setCartTime(sdf.format(new Date()));
        int insert = shoppingCartMapper.insert(cart);
        if(insert > 0){
            return new ResultVO(ResStatus.RIGHT,"success",null);
        }else{
            return new ResultVO(ResStatus.ERROR,"fail",null);

        }
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public ResultVO listShoppingCartByUserId(String userId) {
        List<ShoppingCartVO> shoppingCartVOS = shoppingCartMapper.selectShoppingCartByUserId(userId);
        if(shoppingCartVOS.size() > 0){
            return new ResultVO(ResStatus.RIGHT,"success",shoppingCartVOS);
        }else{
            return new ResultVO(ResStatus.ERROR,"没有查询到相关信息",null);
        }
    }

    @Override
    public ResultVO updateShoppingCartByCartID(int carId, int cartNum) {
        int i = shoppingCartMapper.updateCartNumByCartId(carId, cartNum);
        if(i > 0 ){
            return new ResultVO(ResStatus.RIGHT,"success",null);
        }else {
            return new ResultVO(ResStatus.ERROR,"fail",null);
        }
    }

    @Override
    public ResultVO deleteShoppingCartByCartId(int cartId) {
        Example example = new Example(ShoppingCart.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("cartId",cartId);
        int i = shoppingCartMapper.deleteByExample(example);
        if(i > 0){
            return new ResultVO(ResStatus.RIGHT,"delete success",null);
        }else{
            return new ResultVO(ResStatus.ERROR,"fail",null);
        }

    }

    @Override
    public ResultVO listShoppingCartByCids(String cids) {
        String[] arr = cids.split(",");
        List<Integer> list = new ArrayList<Integer>();
        for (String s : arr) {
            int i = Integer.parseInt(s);
            list.add(i);
        }
        List<ShoppingCartVO> shoppingCartVOS = shoppingCartMapper.selectShoppingCartByCids(list);
        return new ResultVO(ResStatus.RIGHT,"success",shoppingCartVOS);
    }
}
