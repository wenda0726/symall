package com.sjtu.symall.service.impl;

import com.sjtu.symall.dao.CategoryMapper;
import com.sjtu.symall.entity.CategoryVO;
import com.sjtu.symall.service.CategoryService;
import com.sjtu.symall.vo.ResStatus;
import com.sjtu.symall.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public ResultVO listCategories() {
        List<CategoryVO> categoryVOS = categoryMapper.listAllCategories();
        return new ResultVO(ResStatus.RIGHT,"success",categoryVOS);
    }

    @Override
    public ResultVO selectRecommendByCategory() {
        List<CategoryVO> categoryVOS = categoryMapper.listRecommendProduct();

        return new ResultVO(ResStatus.RIGHT,"success",categoryVOS);
    }
}
