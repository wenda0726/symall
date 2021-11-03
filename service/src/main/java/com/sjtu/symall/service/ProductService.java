package com.sjtu.symall.service;

import com.sjtu.symall.vo.ResultVO;

public interface ProductService {

    public ResultVO listCommendProducts();

    public ResultVO getProductBasicInfo(String productId);

    public ResultVO getProductParams(String productId);

    public ResultVO getProductByCategoryId(int categoryId,int pageNum,int pageSize);

    public ResultVO getProductBrandByCategoryId(int categoryId);

    public ResultVO getProductByCategoryIdAndBrand(int categoryId,String brand);

    public ResultVO getProductByKeyword(String keyword,int pageNum,int pageSize);

    public ResultVO getProductBrandByKeyword(String keyword);
}
