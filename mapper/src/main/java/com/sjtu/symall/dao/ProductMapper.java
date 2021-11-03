package com.sjtu.symall.dao;

import com.sjtu.symall.entity.Product;
import com.sjtu.symall.entity.ProductCommentsVO;
import com.sjtu.symall.entity.ProductVO;
import com.sjtu.symall.general.GeneralDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductMapper extends GeneralDao<Product> {

    public List<ProductVO> selectRecommendProducts();

    public List<ProductVO> selectProductByCategoryTop6(int cid);

    //根据分类Id查询商品信息，需要进行分页，并且关联查询商品图片，套餐最低的价格

    /**
     *
     * @param cid 商品分类ID
     * @param start 起始索引
     * @param limit 查询条数
     * @return
     */
    public List<ProductVO> selectProductVOByCategory(@Param("cid") int cid,
                                                     @Param("start") int start,
                                                     @Param("limit") int limit);


    //根据分类ID，查询分类下所有商品的品牌名
    public List<String> selectProductBrandByCategoryId(@Param("cid") int cid);

    //根据分类ID 和品牌名查询对应的商品信息
    public List<ProductVO> selectProductVOByCategoryIdAndBrand(@Param("cid")int cid,
                                                               @Param("brand")String brand);


    //根据关键字查询商品信息,同样需要进行分类，并且关联查询商品图片，套餐的最低价格
    /**
     *
     * @param kw 关键字
     * @param start 起始索引
     * @param limit 查询条数
     * @return
     */
    public List<ProductVO> selectProductByKeyword(@Param("kw") String kw,
                                                  @Param("start") int start,
                                                  @Param("limit") int limit);

    public List<String> selectBrandByKeyword(@Param("kw")String keyword);
}