package com.sjtu.symall.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;


/**
 * 新增了productName，productImg,originalPrice,sellPrice,skuStock,skuName
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingCartVO {

    private Integer cartId;
    private String productId;
    private String skuId;
    private String userId;
    private String cartNum;
    private String cartTime;
    private BigDecimal productPrice;
    private String skuProps;

    private String productName;
    private String productImg;
    private Integer originalPrice;
    private Integer sellPrice;
    private Integer skuStock;
    private String skuName;
}