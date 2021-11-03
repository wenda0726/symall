package com.sjtu.symall.service.impl;

import com.sjtu.symall.dao.ProductCommentsMapper;
import com.sjtu.symall.entity.ProductComments;
import com.sjtu.symall.entity.ProductCommentsVO;
import com.sjtu.symall.entity.ProductVO;
import com.sjtu.symall.service.ProductCommentService;
import com.sjtu.symall.service.ProductService;
import com.sjtu.symall.utils.PageHelper;
import com.sjtu.symall.vo.ResStatus;
import com.sjtu.symall.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductCommentServiceImpl implements ProductCommentService {

    @Autowired
    private ProductCommentsMapper productCommentsMapper;

    @Override
    public ResultVO listCommentsByProductId(String productId, int pageNum, int limit) {
        //1.根据商品ID查询总记录数
        Example example = new Example(ProductComments.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("productId",productId);
        int i = productCommentsMapper.selectCountByExample(example);

        //2.计算数据的总页数
        int pageCount = i % limit == 0 ? i / limit : (i / limit) + 1;

        //3.查询当前页的信息

        int start = (pageNum - 1) * limit;
        List<ProductCommentsVO> productCommentsVOS = productCommentsMapper.selectProductCommentByProductId(productId, start, limit);
        PageHelper<ProductCommentsVO> PageHelper = new PageHelper<>(i, pageCount, productCommentsVOS);
        return new ResultVO(ResStatus.RIGHT,"success",PageHelper);
    }

    @Override
    public ResultVO getCommentsCountByProductId(String productId) {
        //根据商品ID查询评价总数
        Example example = new Example(ProductComments.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("productId",productId);
        int totalCount = productCommentsMapper.selectCountByExample(example);
        //查询好评记录数
        criteria.andEqualTo("commType",1);
        int good = productCommentsMapper.selectCountByExample(example);

        //查询中评记录数
        Example example1 = new Example(ProductComments.class);
        Example.Criteria criteria1 = example1.createCriteria();
        criteria1.andEqualTo("commType",0);
        int average  = productCommentsMapper.selectCountByExample(example1);

        //查询差评记录数
        Example example2 = new Example(ProductComments.class);
        Example.Criteria criteria2 = example2.createCriteria();
        criteria2.andEqualTo("commType",-1);
        int poor  = productCommentsMapper.selectCountByExample(example2);

        //好评率计算
        double goodRate = (double) good / totalCount * 100;
        //四舍五入保留两位

        DecimalFormat df = new DecimalFormat("#.00");
        String goodRateVal = df.format(goodRate);

        Map<String,Object> map = new HashMap<String, Object>();
        map.put("totalCount",totalCount);
        map.put("good",good);
        map.put("average",average);
        map.put("poor",poor);
        map.put("goodRateVal",goodRateVal);
        return new ResultVO(ResStatus.RIGHT,"success",map);
    }
}
