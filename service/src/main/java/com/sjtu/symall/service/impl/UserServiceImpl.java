package com.sjtu.symall.service.impl;

import com.sjtu.symall.dao.UserAddrMapper;
import com.sjtu.symall.dao.UsersMapper;
import com.sjtu.symall.entity.UserAddr;
import com.sjtu.symall.entity.Users;
import com.sjtu.symall.service.UserService;
import com.sjtu.symall.utils.Base64Utils;
import com.sjtu.symall.utils.MD5Utils;
import com.sjtu.symall.vo.ResStatus;
import com.sjtu.symall.vo.ResultVO;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Scope("singleton")
public class UserServiceImpl implements UserService {

    @Autowired //自动装配
    private UsersMapper usersMapper;

    @Autowired
    private UserAddrMapper userAddrMapper;

    //用户注册方法实现
    @Transactional  //考虑并发问题，保证注册过程是一个事务
    public ResultVO userRegister(String name, String password) {
        //考虑到多线程，需要将当前UserServiceImpl对象加锁
        synchronized (this){
            //首先判断输入的用户名是否存在
            Example example = new Example(Users.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("username",name);
            List<Users> users = usersMapper.selectByExample(example);
            if(users.size() == 0){
                //将用户的密码进行加密处理
                String md5Pwd = MD5Utils.md5(password);
                Users user = new Users();
                user.setUsername(name);
                user.setPassword(md5Pwd);
                user.setUserImg("img/default.png");
                user.setUserRegtime(new Date());
                user.setUserModtime(new Date());
                int i = usersMapper.insertUseGeneratedKeys(user);
                int userId = user.getUserId();
                //注册时给用户赋值一个默认地址
                UserAddr userAddr = userAddrMapper.selectByPrimaryKey("1");
                String addrId = System.currentTimeMillis() + "";
                userAddr.setAddrId(addrId);
                userAddr.setUserId(userId + "");
                int insert = userAddrMapper.insert(userAddr);
                if(i > 0){
                    return new ResultVO(ResStatus.RIGHT,"注册成功！",user);
                }else{
                    return new ResultVO(ResStatus.ERROR,"注册失败",null);
                }
            }else{
                return new ResultVO(ResStatus.ERROR,"用户名已存在！",null);
            }
        }
    }

    /**
    * @program:
    * @description: 用户登录功能实现
    * @author: swd   wendusu@sjtu.edu.cn
    ** @create: 2021/10/2
    **/

    @Override //检验不涉及多线程，不用加锁
    public ResultVO checkLogin(String name, String password) {
        Example example = new Example(Users.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("username", name);
        List<Users> users = usersMapper.selectByExample(example);
        if(users.size() == 0){
            return new ResultVO(ResStatus.ERROR,"用户名不存在！",null);
        }else{
            String md5Pwd = MD5Utils.md5(password);
            Users user = users.get(0);
            if(md5Pwd.equals(user.getPassword())){
                Map<String,Object> map = new HashMap<>();
                map.put("k1","v1");
                map.put("k2","v2");
                //如果登录成功就生成令牌token（按照一定规则生成的字符串）
                //1.使用jwt生成token
                JwtBuilder builder = Jwts.builder();
                String token = builder.setSubject(name) //token中携带的数据
                        .setIssuedAt(new Date()) //设置token的生成时间
                        .setId(user.getUserId() + "")
                        .setClaims(map) //map中可以存放用户的权限信息
                        .setExpiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000)) //设置token的过期时间
                        .signWith(SignatureAlgorithm.HS256, "wendasu@sjtu.edu.cn").compact();//设置加密方式和加密的密码

                return new ResultVO(ResStatus.RIGHT,token,user);

            }else{
                return new ResultVO(ResStatus.ERROR,"密码错误！",null);
            }
        }
    }
}
