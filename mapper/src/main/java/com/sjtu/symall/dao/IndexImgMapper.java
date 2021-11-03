package com.sjtu.symall.dao;

import com.sjtu.symall.entity.IndexImg;
import com.sjtu.symall.general.GeneralDao;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IndexImgMapper extends GeneralDao<IndexImg> {

    public List<IndexImg> listIndexImg();
}