package com.sjtu.controller;

import com.sjtu.symall.entity.Users;
import com.sjtu.symall.service.UserService;
import com.sjtu.symall.vo.ResStatus;
import com.sjtu.symall.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
@Api(value = "实现用户的登录和注册接口", tags = "用户管理")
@CrossOrigin //解决前后端数据响应的跨域请求问题
public class UserController {

    @Resource
    private UserService userService;

    @ApiOperation("用户登录接口")
    @ApiImplicitParams(
            {@ApiImplicitParam(dataType = "string",name = "username",value = "用户名",required = true),
            @ApiImplicitParam(dataType = "string",name = "password",value = "登录密码",required = true)}
    )
    @GetMapping("/login")
    public ResultVO login(@RequestParam("username") String name,
                          @RequestParam("password") String pwd){
        return userService.checkLogin(name,pwd);
    }


    @ApiOperation("用户注册接口")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "string", name = "username", value = "用户注册名", required = true),
            @ApiImplicitParam(dataType = "string", name = "password", value = "注册密码", required = true)
    }
    )
    @PostMapping("/register")
    public ResultVO register(@RequestBody Users user) {
        return userService.userRegister(user.getUsername(), user.getPassword());
    }

    @GetMapping("/check")
    @ApiOperation("用户token校验接口")
    public ResultVO tokenCheck(@RequestHeader("token") String token){
        return new ResultVO(ResStatus.RIGHT,"success",null);
    }

}
