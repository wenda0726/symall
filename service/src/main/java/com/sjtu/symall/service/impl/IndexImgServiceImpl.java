package com.sjtu.symall.service.impl;

import com.sjtu.symall.dao.IndexImgMapper;
import com.sjtu.symall.entity.IndexImg;
import com.sjtu.symall.service.IndexImgService;
import com.sjtu.symall.vo.ResStatus;
import com.sjtu.symall.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IndexImgServiceImpl implements IndexImgService {

    @Autowired
    private IndexImgMapper indexImgMapper;


    public ResultVO listIndexImg() {
        List<IndexImg> list = indexImgMapper.listIndexImg();
        if(list.size() == 0){
            return new ResultVO(ResStatus.ERROR,"fail",null);
        }else{
            return new ResultVO(ResStatus.RIGHT,"success",list);
        }
    }
}
