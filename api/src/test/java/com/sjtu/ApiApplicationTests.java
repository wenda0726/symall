package com.sjtu;

import com.sjtu.symall.dao.*;
import com.sjtu.symall.entity.*;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApiApplication.class)
class ApiApplicationTests {

//    @Autowired
//    private CategoryMapper categoryMapper;
//
//    @Test
//    void contextLoads() {
//        List<CategoryVO> categoryVOS = categoryMapper.listAllCategories2(0);
//        for (CategoryVO c1 : categoryVOS) {
//            System.out.println(c1);
//            for (CategoryVO c2 : c1.getCategoryVOS()) {
//                System.out.println("\t" + c2);
//                for (CategoryVO c3 : c2.getCategoryVOS()) {
//                    System.out.println("\t\t" + c3);
//                }
//            }
//        }
//    }
//
//    @Autowired
//    private ProductMapper productMapper;
//
//    @Autowired
//    private ProductCommentsMapper productCommentsMapper;
//
//    @Test
//    void listCommendProduct(){
//        List<ProductVO> productVOS = productMapper.selectRecommendProducts();
//        for (ProductVO productVO : productVOS) {
//            System.out.println(productVO);
//        }
//    }
//
//    @Test
//    void listRecommendCategoryProducts(){
//        List<CategoryVO> categoryVOS = categoryMapper.listRecommendProduct();
//        for (CategoryVO categoryVO : categoryVOS) {
//            System.out.println(categoryVO);
//        }
//    }
//
////    @Test
//////    void selectProductCommentsByProductID(){
//////        List<ProductCommentsVO> productCommentsVOS = productCommentsMapper.selectProductCommentByProductId("2");
//////        for (ProductCommentsVO productCommentsVO : productCommentsVOS) {
//////            System.out.println(productCommentsVO);
//////        }
//////    }
//    @Autowired
//    private ShoppingCartMapper shoppingCartMapper;
//
//    @Autowired
//    private OrdersMapper ordersMapper;
//
//    @Test
//    public void testSelectShopCartByUserId(){
//        List<ShoppingCartVO> shoppingCartVOS = shoppingCartMapper.selectShoppingCartByUserId("37");
//        for (ShoppingCartVO shoppingCartVO : shoppingCartVOS) {
//            System.out.println(shoppingCartVO);
//        }
//    }
//
//    @Test
//    public void testSelectShopCartByCids(){
//        List<Integer> arr = new ArrayList<>();
//        arr.add(1);
//        arr.add(2);
//        arr.add(8);
//        List<ShoppingCartVO> shoppingCartVOS = shoppingCartMapper.selectShoppingCartByCids(arr);
//        for (ShoppingCartVO shoppingCartVO : shoppingCartVOS) {
//            System.out.println(shoppingCartVO);
//        }
//    }
//
//    @Test
//    public void testTimeoutOrder(){
////        Example example = new Example(Orders.class);
////        Example.Criteria criteria = example.createCriteria();
////        criteria.andEqualTo("status","1");
////        Date time = new Date(System.currentTimeMillis() - 30 * 60 * 1000);
////        criteria.andLessThan("createTime",time);
////        List<Orders> orders = ordersMapper.selectByExample(example);
////        for (Orders order : orders) {
////            System.out.println(order.getOrderId());
////        }
//    }
//
//    @Test
//    public void testSearch(){
//        List<ProductVO> productVOS = productMapper.selectProductVOByCategory(49, 0, 6);
//        for (ProductVO productVO : productVOS) {
//            System.out.println(productVO);
//        }
//    }
}
