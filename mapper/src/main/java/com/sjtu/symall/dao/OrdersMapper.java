package com.sjtu.symall.dao;

import com.sjtu.symall.entity.Orders;
import com.sjtu.symall.entity.OrdersVO;
import com.sjtu.symall.general.GeneralDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersMapper extends GeneralDao<Orders> {

    //根据用户ID查询用户的订单信息
    public List<OrdersVO> selectOrdersVO(@Param("uid") String userId,@Param("sta") String status,
                                         @Param("start") int start, @Param("limit") int limit);
}