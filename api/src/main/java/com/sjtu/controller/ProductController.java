package com.sjtu.controller;

import com.sjtu.symall.service.ProductCommentService;
import com.sjtu.symall.service.ProductService;
import com.sjtu.symall.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/product")
@Api(value = "商品详情查询接口",tags = "商品管理接口")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductCommentService productCommentService;



    //获取商品的基本信息
    @GetMapping("/detail-info/{pid}")
    @ApiOperation("商品基本信息查询接口")
    public ResultVO getProductInfo(@PathVariable("pid") String pid){
       return productService.getProductBasicInfo(pid);
    }

    //获取商品的参数信息
    @GetMapping("/params/{pid}")
    @ApiOperation("商品的详细信息查询接口")
    public ResultVO getProductParams(@PathVariable("pid") String pid){
        return productService.getProductParams(pid);
    }

    //获取商品的评价信息
    //获取商品的参数信息

    /**
     *
     * @param pid 商品ID
     * @param pageNum 需要显示的当前页数
     * @param limit 每一页显示多少条数据
     * @return
     */
    @GetMapping("/comments/{pid}")
    @ApiOperation("商品的评论信息查询接口")
    @ApiImplicitParams(
            {@ApiImplicitParam(dataType = "int", name = "pageNum", value = "当前页码", required = true),
                    @ApiImplicitParam(dataType = "int", name = "limit", value = "每页显示的条数", required = true)}
    )
    public ResultVO getProductComments(@PathVariable("pid") String pid,
                                       int pageNum,
                                       int limit){
        return productCommentService.listCommentsByProductId(pid,pageNum,limit);
    }


    //根据商品ID查询商品的好评率
    @GetMapping("//commentsCount/{pid}")
    @ApiOperation("商品的评价统计查询接口")
    public ResultVO getProductCommentsCountByProductId(@PathVariable("pid") String pid){
        return productCommentService.getCommentsCountByProductId(pid);
    }

    /**
     *
     * @param categoryId 商品ID
     * @param pageNum 需要显示的当前页数
     * @param limit 每一页显示多少条数据
     * @return
     */
    @GetMapping("/search-by-categoryid/{cid}")
    @ApiOperation("商品分类搜索查询接口")
    @ApiImplicitParams(
            {@ApiImplicitParam(dataType = "int", name = "pageNum", value = "当前页码", required = true),
                    @ApiImplicitParam(dataType = "int", name = "limit", value = "每页显示的条数", required = true)}
    )
    public ResultVO getProductByCategoryId(@PathVariable("cid") int categoryId,int pageNum,int limit){
        return productService.getProductByCategoryId(categoryId,pageNum,limit);
    }

    @GetMapping("/get-product-brand/{cid}")
    @ApiOperation("商品分类品牌查询接口")
    public ResultVO getProductBrandByCategoryId(@PathVariable("cid") int categoryId){
        return productService.getProductBrandByCategoryId(categoryId);
    }

    @GetMapping("/search-by-cid-brand")
    @ApiOperation("根据商品分类和品牌搜索查询接口")
    public ResultVO getProductByCategoryIdAndBrand(int categoryId,String brand){
        return productService.getProductByCategoryIdAndBrand(categoryId,brand);
    }

    @GetMapping("/search-by-keyword/{kw}")
    @ApiOperation("商品模糊查询接口")
    @ApiImplicitParams(
            {@ApiImplicitParam(dataType = "int", name = "pageNum", value = "当前页码", required = true),
                    @ApiImplicitParam(dataType = "int", name = "limit", value = "每页显示的条数", required = true)}
    )
    public ResultVO getProductByKeyword(@PathVariable("kw") String keyword,int pageNum,int limit){
        return productService.getProductByKeyword(keyword,pageNum,limit);
    }

    @GetMapping("/get-brand-key/{keyword}")
    @ApiOperation("根据关键字查询商品品牌接口")
    public ResultVO getBrandByKeyword(@PathVariable("keyword") String keyword){
       return productService.getProductBrandByKeyword(keyword);
    }





}
