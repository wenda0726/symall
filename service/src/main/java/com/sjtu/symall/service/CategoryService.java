package com.sjtu.symall.service;

import com.sjtu.symall.vo.ResultVO;

public interface CategoryService {

    /**
    * @program:
    * @description: 查询首页的分类列表
    * @author: swd   wendusu@sjtu.edu.cn
    ** @create: 2021/9/25
    **/
    public ResultVO listCategories();

    /**
    * @program:
    * @description: 查询每个一级分类下的推荐商品
    * @author: swd   wendusu@sjtu.edu.cn
    ** @create: 2021/9/25
    **/
    public ResultVO selectRecommendByCategory();
}
