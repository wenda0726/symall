package com.sjtu.symall.dao;

import com.sjtu.symall.entity.ProductComments;
import com.sjtu.symall.entity.ProductCommentsVO;
import com.sjtu.symall.general.GeneralDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ProductCommentsMapper extends GeneralDao<ProductComments> {
    /**
     *
     * @param productId 商品ID
     * @param start 起始条数
     * @param limit 查询数量(每一页显示多少条评论)
     * @return
     */
    public List<ProductCommentsVO> selectProductCommentByProductId(@Param("productId") String productId,
                                                                   @Param("start") int start,
                                                                   @Param("limit") int limit);
}