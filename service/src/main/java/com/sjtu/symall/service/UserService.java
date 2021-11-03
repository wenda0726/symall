package com.sjtu.symall.service;

import com.sjtu.symall.vo.ResultVO;

public interface UserService {

    //用户注册
    public ResultVO userRegister(String name, String password);

    //用户登录
    public ResultVO checkLogin(String name, String password);
}
