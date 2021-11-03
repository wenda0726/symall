package com.sjtu.controller;


import com.github.wxpay.sdk.WXPay;
import com.sjtu.config.MyPay;
import com.sjtu.symall.entity.Orders;
import com.sjtu.symall.service.OrderService;
import com.sjtu.symall.vo.ResStatus;
import com.sjtu.symall.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/order")
@Api(value = "用户的订单管理接口",tags = "订单管理")
@CrossOrigin
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/add")
    public ResultVO add(String cids, @RequestBody Orders order,@RequestHeader("token") String token){
        ResultVO resultVO = null;
        try {
            Map<String, String> orderInfo = orderService.addOrder(cids, order);
            String orderId = orderInfo.get("orderId");
            if(orderId != null){
                //微信支付，申请支付链接
                WXPay wxPay = new WXPay(new MyPay());
                Map<String,String> data = new HashMap<String, String>();
                data.put("body",orderInfo.get("productNames")); //商品描述
                data.put("out_trade_no",orderId); //商品订单
                data.put("fee_type","CNY"); //支付币种
//                data.put("total_fee",order.getActualAmount() * 100 + ""); //支付总金额，单位为1分钱
                data.put("total_fee","1");   //为了调试、测试, 订单价格统一设置为1分钱
                data.put("trade_type","NATIVE"); //支付方式
                data.put("notify_url","http://47.100.69.253:8080/pay/callback");      //设置支付完成时的回调url,内网穿透

                Map<String, String> res = wxPay.unifiedOrder(data); //微信支付平台返回的数据
                String code_url = res.get("code_url");
//                System.out.println(res);
                orderInfo.put("code_url",code_url);
                return new ResultVO(ResStatus.RIGHT,"订单提交成功！",orderInfo);
            }
        } catch (SQLException e) {
            return new ResultVO(ResStatus.ERROR,"订单提交失败",null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  new ResultVO(ResStatus.ERROR,"订单提交失败",null);
    }

    @GetMapping("/status/{oid}")
    @ApiImplicitParam(dataType = "string",name = "oid",value = "订单编号",required = true)
    public ResultVO getStatusByOrderId(@PathVariable String oid,@RequestHeader("token") String token){
        return orderService.getStatusByOrderId(oid);
    }

    @GetMapping("/list")
    @ApiOperation("订单查询接口")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(dataType = "string", name = "userId", value = "用户名", required = true),
                    @ApiImplicitParam(dataType = "string", name = "status", value = "状态码", required = false),
                    @ApiImplicitParam(dataType = "int", name = "pageNum", value = "当前页码", required = true),
                    @ApiImplicitParam(dataType = "int", name = "limit", value = "每页显示的条数", required = true)
            }
    )
    public ResultVO listOrders(@RequestHeader("token") String token,
            String userId,String status,int pageNum,int limit){
        return orderService.listOrders(userId, status, pageNum, limit);
    }
}
