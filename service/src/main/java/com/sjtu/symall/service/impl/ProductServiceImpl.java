package com.sjtu.symall.service.impl;

import com.sjtu.symall.dao.ProductImgMapper;
import com.sjtu.symall.dao.ProductMapper;
import com.sjtu.symall.dao.ProductParamsMapper;
import com.sjtu.symall.dao.ProductSkuMapper;
import com.sjtu.symall.entity.*;
import com.sjtu.symall.service.ProductService;
import com.sjtu.symall.utils.PageHelper;
import com.sjtu.symall.vo.ResStatus;
import com.sjtu.symall.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductImgMapper productImgMapper;

    @Autowired
    private ProductSkuMapper productSkuMapper;

    @Autowired
    private ProductParamsMapper productParamsMapper;

    //查询展示到首页的商品
    public ResultVO listCommendProducts() {
        List<ProductVO> productVOS = productMapper.selectRecommendProducts();
        return new ResultVO(ResStatus.RIGHT,"success",productVOS);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    //如果其他bean调用这个方法,在其他bean中声明事务,那就用事务.如果其他bean没有声明事务,那就不用事务.
    public ResultVO getProductBasicInfo(String productId) {
        //1.商品基本信息
        Example example = new Example(Product.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("productId",productId);
        criteria.andEqualTo("productStatus",1);//状态为1的商品才表示正常上架
        List<Product> products = productMapper.selectByExample(example);
        if(products.size() > 0){
            //2.商品图片
            Example example1 = new Example(ProductImg.class);
            Example.Criteria criteria1 = example1.createCriteria();
            criteria1.andEqualTo("itemId",productId);
            List<ProductImg> productImgs = productImgMapper.selectByExample(example1);

            //3.商品sku套餐
            Example example2 = new Example(ProductSku.class);
            Example.Criteria criteria2 = example2.createCriteria();
            criteria2.andEqualTo("productId",productId);
            criteria2.andEqualTo("status",1);
            List<ProductSku> productSkus = productSkuMapper.selectByExample(example2);

            Map<String,Object> basicInfo = new HashMap<String, Object>();
            basicInfo.put("products",products.get(0));
            basicInfo.put("productImgs",productImgs);
            basicInfo.put("productSkus",productSkus);
           return new ResultVO(ResStatus.RIGHT,"success",basicInfo);
        }else{
            return new ResultVO(ResStatus.ERROR,"查询的商品不存在",null);
        }
    }

    @Override
    public ResultVO getProductParams(String productId) {
        Example example = new Example(ProductParams.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("productId",productId);
        List<ProductParams> productParams = productParamsMapper.selectByExample(example);
        if(productParams.size() > 0){
            return new ResultVO(ResStatus.RIGHT,"success",productParams.get(0));
        }else{
            return new ResultVO(ResStatus.ERROR,"可能是三无产品",null);

        }

    }

    @Override
    public ResultVO getProductByCategoryId(int categoryId, int pageNum, int pageSize) {
        int start = (pageNum - 1) * pageSize;
        List<ProductVO> productVOS = productMapper.selectProductVOByCategory(categoryId, start, pageSize);
        Example example = new Example(Product.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("categoryId",categoryId);
        int count = productMapper.selectCountByExample(example); // 总记录数
        int pageCount = count % pageSize == 0 ? count / pageSize : count / pageSize + 1;
        PageHelper<ProductVO> pageHelper = new PageHelper<>(count,pageCount,productVOS);
        return new ResultVO(ResStatus.RIGHT,"success",pageHelper);
    }

    @Override
    public ResultVO getProductBrandByCategoryId(int categoryId) {
        List<String> brands = productMapper.selectProductBrandByCategoryId(categoryId);
        return new ResultVO(ResStatus.RIGHT,"success",brands);
    }

    @Override
    public ResultVO getProductByCategoryIdAndBrand(int categoryId, String brand) {
        List<ProductVO> productVOS = productMapper.selectProductVOByCategoryIdAndBrand(categoryId, brand);
        return new ResultVO(ResStatus.RIGHT,"success",productVOS);
    }


    @Override
    public ResultVO getProductByKeyword(String keyword, int pageNum, int pageSize) {
        keyword = "%" +keyword+ "%";
        int start = (pageNum - 1) * pageSize;
        //1.查询总记录数
        Example example = new Example(Product.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andLike("productName",keyword);
        int count = productMapper.selectCountByExample(example);
        //2.计算总页数
        int pageCount  = count % pageSize == 0 ? count / pageSize : count / pageSize + 1;
        List<ProductVO> productVOS = productMapper.selectProductByKeyword(keyword, start, pageSize);
        PageHelper<ProductVO> pageHelper = new PageHelper<>(count,pageCount,productVOS);
        return new ResultVO(ResStatus.RIGHT,"success",pageHelper);
    }

    @Override
    public ResultVO getProductBrandByKeyword(String keyword) {
        keyword = "%" + keyword + "%";
        List<String> brands = productMapper.selectBrandByKeyword(keyword);
        return new ResultVO(ResStatus.RIGHT,"success",brands);
    }
}
