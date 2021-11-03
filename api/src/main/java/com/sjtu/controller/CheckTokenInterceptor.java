package com.sjtu.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sjtu.symall.vo.ResStatus;
import com.sjtu.symall.vo.ResultVO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class CheckTokenInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
       //第一次的预检请求应该给与放行
        String method = request.getMethod();
        if("OPTIONS".equals(method)){
            return true;
        }
        String token = request.getHeader("token");
//        System.out.println("--------------" + token);
        if(token == null) {
            ResultVO resultVO = new ResultVO(ResStatus.LOGIN_FAIL_NOT, "请先登录", null);
            doResponse(response,resultVO);
        }else{
            try{
                JwtParser parser = Jwts.parser();
                parser.setSigningKey("wendasu@sjtu.edu.cn");//必须与设置token时输入的密码一致
                Jws<Claims> jws = parser.parseClaimsJws(token);
                return true;
            }catch (Exception e){
                ResultVO resultVO = new ResultVO(ResStatus.LOGIN_FAIL_OVERDUE, "登录过期，请重新登录！", null);
                doResponse(response,resultVO);
            }
        }
        return false;
    }

    private void doResponse(HttpServletResponse response,ResultVO resultVO) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        String s = new ObjectMapper().writeValueAsString(resultVO);
        writer.print(s);
        writer.flush();
        writer.close();

    }
}
