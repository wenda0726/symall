package com.sjtu.controller;

import com.github.wxpay.sdk.WXPayUtil;
import com.sjtu.symall.service.OrderService;
import com.sjtu.websocket.WebsocketServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/pay")
@CrossOrigin
public class PayController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private WebsocketServer websocketServer;
    /**
    *
    * @description: 支付成功后，回调接口
    * @author: swd   wendusu@sjtu.edu.cn
    ** @create: 2021/10/9
    **/

    @RequestMapping("/callback")
    public String success(HttpServletRequest request) throws Exception {
        //1.通过输入流读取支付结果
        System.out.println("---------callback---------");
        ServletInputStream is = request.getInputStream();
        byte[] bytes = new byte[1024];
        int len = -1;
        StringBuilder sb = new StringBuilder();
        while ((len = is.read(bytes)) != -1){
            sb.append(new String(bytes,0,len));
        }
        String s = sb.toString();
        //使用帮助类，将微信支付xml结果转换成map结构
        Map<String, String> map = WXPayUtil.xmlToMap(s);
        if(map != null && "success".equalsIgnoreCase(map.get("result_code"))){
            //2.支付成功，修改数据库
            String orderId = map.get("out_trade_no");
            int i = orderService.updateStatus(orderId, "2");
            //3.通过websocket向前端发送消息
            websocketServer.sendMsg(orderId,"1");

            //4.响应微信支付平台，微信将不再继续请求接口
            if(i > 0){
                Map<String,String> resp = new HashMap<>();
                resp.put("return_code","success");
                resp.put("return_msg","OK");
                resp.put("appid",map.get("appid"));
                resp.put("result_code","success");
                return WXPayUtil.mapToXml(resp);
            }
        }
        return null;
    }
}
