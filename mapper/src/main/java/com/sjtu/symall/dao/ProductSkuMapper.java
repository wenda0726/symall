package com.sjtu.symall.dao;

import com.sjtu.symall.entity.ProductSku;
import com.sjtu.symall.general.GeneralDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductSkuMapper extends GeneralDao<ProductSku> {

    //根据商品ID查询价格最低的套餐
    public List<ProductSku> selectLowestPriceSku(@Param("pid") String pid);
}