package com.sjtu.symall.service.impl;

import com.sjtu.symall.dao.UserAddrMapper;
import com.sjtu.symall.entity.UserAddr;
import com.sjtu.symall.service.AddrService;
import com.sjtu.symall.vo.ResStatus;
import com.sjtu.symall.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class AddrServiceImpl implements AddrService {

    @Autowired
    private UserAddrMapper userAddrMapper;

    @Transactional(propagation = Propagation.SUPPORTS)
    public ResultVO listAddrByUserId(String userId) {
        Example example = new Example(UserAddr.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId",userId);
        List<UserAddr> userAddrs = userAddrMapper.selectByExample(example);
        return new ResultVO(ResStatus.RIGHT,"success",userAddrs);
    }
}
