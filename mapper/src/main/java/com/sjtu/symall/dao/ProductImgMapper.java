package com.sjtu.symall.dao;

import com.sjtu.symall.entity.ProductImg;
import com.sjtu.symall.general.GeneralDao;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductImgMapper extends GeneralDao<ProductImg> {

    public List<ProductImg> selectProductImgByProductId(int productId);
}