package com.sjtu.symall.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "ResultVO对象", description = "封装了返回给前端的数据")
public class ResultVO {

    @ApiModelProperty(value = "响应状态码",dataType = "int")
    //响应给前端的状态码
    private int code;


    @ApiModelProperty(value = "响应给前端的提示信息")
    //响应给前端的提示信息
    private String msg;


    @ApiModelProperty(value = "响应给前端的数据")
    //响应给前端的数据
    private Object data;
}
