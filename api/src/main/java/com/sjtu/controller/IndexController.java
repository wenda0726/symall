package com.sjtu.controller;

import com.sjtu.symall.entity.Product;
import com.sjtu.symall.service.CategoryService;
import com.sjtu.symall.service.IndexImgService;
import com.sjtu.symall.service.ProductService;
import com.sjtu.symall.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(value = "实现首页数据显示的接口",tags = "首页管理")
@RequestMapping("/index")
@CrossOrigin
public class IndexController {

    @Autowired
    private IndexImgService indexImgService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @GetMapping("/img")
    @ApiOperation("首页轮播图接口")
    public ResultVO listIndexImg(){
        return indexImgService.listIndexImg();
    }

    @GetMapping("/category-list")
    @ApiOperation("商品分类查询接口")
    public ResultVO listCategories(){
        return categoryService.listCategories();
    }


    @GetMapping("/recommend")
    @ApiOperation("首页新品商品推荐查询接口")
    public ResultVO listRecommendProduct(){
        return productService.listCommendProducts();
    }

    @GetMapping("/recommend-first-level")
    @ApiOperation("首页分类的商品推荐查询接口")
    public ResultVO listFirstLevelRecommendProduct(){
        return categoryService.selectRecommendByCategory();
    }
}
