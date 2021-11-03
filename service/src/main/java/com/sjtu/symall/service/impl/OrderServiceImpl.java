package com.sjtu.symall.service.impl;


import com.sjtu.symall.dao.OrderItemMapper;
import com.sjtu.symall.dao.OrdersMapper;
import com.sjtu.symall.dao.ProductSkuMapper;
import com.sjtu.symall.dao.ShoppingCartMapper;
import com.sjtu.symall.entity.*;
import com.sjtu.symall.service.OrderService;
import com.sjtu.symall.utils.PageHelper;
import com.sjtu.symall.vo.ResStatus;
import com.sjtu.symall.vo.ResultVO;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ShoppingCartMapper shoppingCartMapper;

    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Autowired
    private ProductSkuMapper productSkuMapper;



    /**
     *
     * @param order 前端传过来的基本订单信息
     * @param cids 用户选择的购物车记录ID（"29,10"）
     *             order 需要的数据
     *             库存校验、订单保存、订单快照保存、商品库存修改是事务，要么同时成功，要么同时失败
     * @return
     */
    @Transactional
    public Map<String,String> addOrder(String cids, Orders order) throws SQLException{

        Map<String,String> orderInfo = new HashMap<>();
        //1.校验库存，根据购物车ID字符串（"29,30"）查询购物商品的记录（ShoppingCartVO）
        String[] split = cids.split(",");
        List<Integer> list = new ArrayList<Integer>();
        for (String s : split) {
            int i = Integer.parseInt(s);
            list.add(i);
        }
        List<ShoppingCartVO> shoppingCartVOS = shoppingCartMapper.selectShoppingCartByCids(list);

        //对每个购物记录进行库存校验
        boolean flag = false;
        //购物商品的名称拼接
        String untitled = "";
        for (ShoppingCartVO sc : shoppingCartVOS) {
            if(Integer.parseInt(sc.getCartNum()) < sc.getSkuStock()){
                flag = true;
            }
            untitled = untitled + sc.getProductName() + ",";
        }
        if(untitled.length() > 1){
            untitled = untitled.substring(0,untitled.length() - 1);
        }
        orderInfo.put("productNames",untitled);

        if(flag){
            //2.每一件商品的库存都充足，保存订单
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            order.setUntitled(untitled);
            order.setCreateTime(new Date());
            order.setStatus("1"); //点单初始状态为1 未支付
            //生成订单编号
            String orderId = UUID.randomUUID().toString().replace("-", "");
            order.setOrderId(orderId);
            //保存订单
            int insert = ordersMapper.insert(order);
            orderInfo.put("orderId",orderId);
            //3.生成订单快照
            for (ShoppingCartVO sc : shoppingCartVOS) {
                OrderItem orderItem = new OrderItem();
                String itemId = System.currentTimeMillis() + "" + (new Random().nextInt(89999) + 10000);
                orderItem.setItemId(itemId);
                orderItem.setOrderId(orderId);
                orderItem.setProductId(sc.getProductId());
                orderItem.setProductName(sc.getProductName());
                orderItem.setProductImg(sc.getProductImg());
                orderItem.setSkuId(sc.getSkuId());
                orderItem.setSkuName(sc.getSkuProps());
                orderItem.setProductPrice(sc.getProductPrice());
                orderItem.setBuyCounts(Integer.parseInt(sc.getCartNum()));
                BigDecimal count = new BigDecimal(Integer.parseInt(sc.getCartNum()));
                orderItem.setTotalAmount(sc.getProductPrice().multiply(count));
                orderItem.setBasketDate(new Date());
                orderItem.setBuyTime(new Date());
                orderItem.setIsComment(0);
                orderItemMapper.insert(orderItem);
            }
            //订单快照保存成功
            //4.扣减库存
            for (ShoppingCartVO sc : shoppingCartVOS) {
                int newStock = sc.getSkuStock() - Integer.parseInt(sc.getCartNum());
                String skuId = sc.getSkuId();
                ProductSku productSku = new ProductSku();
                productSku.setSkuId(skuId);
                productSku.setStock(newStock);
                productSkuMapper.updateByPrimaryKeySelective(productSku);
            }
            //5.购买完成后，删除对应的购物车记录
            for (Integer cid : list) {
                shoppingCartMapper.deleteByPrimaryKey(cid);
            }

        }
        return orderInfo;

    }

    @Override
    public int updateStatus(String orderId,String status) {
        Orders order = new Orders();
        order.setOrderId(orderId);
        order.setStatus(status);
        int i = ordersMapper.updateByPrimaryKeySelective(order);
        return i;
    }

    @Override
    public ResultVO getStatusByOrderId(String orderId) {
        Orders order = ordersMapper.selectByPrimaryKey(orderId);

        return new ResultVO(ResStatus.RIGHT,"success",order.getStatus());
    }

    @Override
    //具有事务性，加注解
    @Transactional(isolation = Isolation.SERIALIZABLE)
    //如果有两个同样的productSku需要取消订单，需要考虑并发问题，可以加锁synchronized，或者设置事务的隔离级别
    /*
    SERIALIZABLE 最高隔离级别，不允许事务并发执行，而必须串行化执行，最安全，不可能出现更新、脏读、不可重复读、幻读，但是效率最低
     */
    public void closeOrder(String orderId) {
        Orders cancelOrder = new Orders();
        cancelOrder.setStatus("6");
        cancelOrder.setOrderId(orderId);
        cancelOrder.setCloseType(1);
        ordersMapper.updateByPrimaryKeySelective(cancelOrder);
        /**
         * 还原库存
         */
        Example example1 = new Example(OrderItem.class);
        Example.Criteria criteria1 = example1.createCriteria();
        criteria1.andEqualTo("orderId",orderId);
        List<OrderItem> orderItems = orderItemMapper.selectByExample(example1);
        for (OrderItem orderItem : orderItems) {
            ProductSku productSku = productSkuMapper.selectByPrimaryKey(orderItem.getSkuId());
            productSku.setStock(productSku.getStock() + orderItem.getBuyCounts());
            productSkuMapper.updateByPrimaryKey(productSku);
        }
    }

    @Override
    public ResultVO listOrders(String userId, String status, int pageNum, int limit) {
        //1.查询对应的订单数据
        int start = (pageNum - 1) * limit;
        List<OrdersVO> ordersVOS = ordersMapper.selectOrdersVO(userId, status, start, limit);
        //2.查询总的记录数
        Example example = new Example(Orders.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId",userId);
        if(status != null && !"".equals(status)){
            criteria.andEqualTo("status",status);
        }
        int count = ordersMapper.selectCountByExample(example);

        //3.计算总页数
        int pageCount = count % limit == 0 ? count / limit : count / limit + 1;
        //4.封装返回数据
        PageHelper<OrdersVO> ordersVOPageHelper = new PageHelper<>(count, pageCount, ordersVOS);
        return new ResultVO(ResStatus.RIGHT,"success",ordersVOPageHelper);
    }
}
