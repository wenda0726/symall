package com.sjtu.symall.dao;

import com.sjtu.symall.entity.Category;
import com.sjtu.symall.entity.CategoryVO;
import com.sjtu.symall.general.GeneralDao;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryMapper extends GeneralDao<Category> {

    public List<CategoryVO> listAllCategories();

    public List<CategoryVO> listAllCategories2(int parentId);

    //查询首页的商品分类推荐
    public List<CategoryVO> listRecommendProduct();

}