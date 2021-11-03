package com.sjtu.symall.service;

import com.sjtu.symall.entity.Orders;
import com.sjtu.symall.vo.ResultVO;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Map;


public interface OrderService {
    //添加订单接口
    public Map<String,String> addOrder(String cids, Orders order) throws SQLException;

    //根据订单编号修改订单状态
    public int updateStatus(String orderId,String status);

    //根据订单ID查询订单状态
    public ResultVO getStatusByOrderId(String orderId);

    //关闭订单
    public void closeOrder(String orderId);

    //根据用户ID，状态码查询订单
    public ResultVO listOrders(String userId,String status,int pageNum,int limit);
}
