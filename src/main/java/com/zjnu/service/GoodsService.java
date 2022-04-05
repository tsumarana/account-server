package com.zjnu.service;

import com.zjnu.pojo.Goods;
import com.zjnu.pojo.PageBean;

public interface GoodsService {
    public Goods selectInfo(int id);

    PageBean<Goods> selectByPage(int currentPage, int pageSize);

    PageBean<Goods> selectByPageAndCondition(int currentPage, int pageSize, Goods _goods);

    Goods selectById(int id);

    void addGoods(Goods goods);

    void deleteById(Goods goods);

    void deleteBySeller(Goods goods);

    void deleteGoodsByUsername(String username);

    void freezeUser(String username);

    void deleteGoodsById(int id);
}
