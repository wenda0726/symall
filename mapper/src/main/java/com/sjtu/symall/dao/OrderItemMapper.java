package com.sjtu.symall.dao;

import com.sjtu.symall.entity.OrderItem;
import com.sjtu.symall.general.GeneralDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemMapper extends GeneralDao<OrderItem> {
    public List<OrderItem> selectOrderItemByOrderId(@Param("oid") String orderId);
}