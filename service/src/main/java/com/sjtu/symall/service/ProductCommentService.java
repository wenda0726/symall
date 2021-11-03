package com.sjtu.symall.service;

import com.sjtu.symall.vo.ResultVO;

public interface ProductCommentService {

    /**
     *
     * @param productId 商品ID
     * @param pageNum 查询的页码
     * @param limit 每页显示的评论条数
     * @return
     */
    public ResultVO listCommentsByProductId(String productId,int pageNum,int limit);

    //根据商品ID查询商品的好评率
    public ResultVO getCommentsCountByProductId(String productId);
}
