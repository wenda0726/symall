package com.sjtu.symall.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CategoryVO {
    private Integer categoryId;
    private String categoryName;
    private Integer categoryLevel;
    private Integer parentId;
    private String categoryIcon;
    private String categorySlogan;
    private String categoryPic;
    private String categoryBgColor;
    private List<CategoryVO> categoryVOS;
    private List<ProductVO> products;

}
