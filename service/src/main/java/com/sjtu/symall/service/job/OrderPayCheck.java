package com.sjtu.symall.service.job;


import com.github.wxpay.sdk.WXPay;
import com.sjtu.symall.dao.OrderItemMapper;
import com.sjtu.symall.dao.OrdersMapper;
import com.sjtu.symall.dao.ProductSkuMapper;
import com.sjtu.symall.entity.OrderItem;
import com.sjtu.symall.entity.Orders;
import com.sjtu.symall.entity.ProductSku;
import com.sjtu.symall.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class OrderPayCheck {

    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private OrderService orderService;

    private WXPay wxPay = new WXPay(new MyPay());

    @Scheduled(cron = "0/60 * * * * ?")
    public void orderPayCheckJOb() {
//        System.out.println("----------");
        try {
            //1.查询30分钟以前，状态为1 的订单集合
            Example example = new Example(Orders.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("status", "1");
            Date time = new Date(System.currentTimeMillis() - 30 * 60 * 1000);
            criteria.andLessThan("createTime", time);
            List<Orders> orders = ordersMapper.selectByExample(example);

            //2.向微信支付平台发送信息，确认orders集合中的订单是否全部为未支付
           for (Orders order : orders) {
                Map<String, String> params = new HashMap<>();
                params.put("out_trade_no", order.getOrderId());
                Map<String, String> resp = wxPay.orderQuery(params);
                if ("SUCCESS".equals(resp.get("trade_state"))) {
                    // 说明用户已经支付，但是由于网络原因，数据库中订单状态没有修改为2
                    //修改数据库中的订单状态为2
                    Orders updateOrder = new Orders();
                    updateOrder.setOrderId(order.getOrderId());
                    updateOrder.setStatus("2");
                    ordersMapper.updateByPrimaryKeySelective(updateOrder);

                } else if ("NOTPAY".equals(resp.get("trade_state"))) {
                    //为了避免在后端在关闭订单的同时，用户进行付款
                    //a.首先向微信支付平台发送请求，将付款链接关闭
                    Map<String, String> map = wxPay.closeOrder(params);
                    System.out.println(map);

                    //b.将订单状态设置为关闭
                    //修改状态和还原库存具有事务性，单独提取到OrderService中
                    orderService.closeOrder(order.getOrderId());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
