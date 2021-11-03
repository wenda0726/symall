package com.sjtu.controller;


import com.sjtu.symall.service.impl.AddrServiceImpl;
import com.sjtu.symall.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/addr")
@Api(value = "用户的收货地址管理接口",tags = "收货地址管理")
@CrossOrigin
public class AddrController {
    @Autowired
    private AddrServiceImpl addrService;

    @GetMapping("/list/{uid}")
    @ApiOperation("查询用户的地址接口")
    public ResultVO listAddrByUserId(@PathVariable("uid") String uid,@RequestHeader("token")String token){
        return addrService.listAddrByUserId(uid);
    }
}
