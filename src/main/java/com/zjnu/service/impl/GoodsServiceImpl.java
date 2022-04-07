package com.zjnu.service.impl;

import com.zjnu.mapper.GoodsMapper;
import com.zjnu.pojo.Goods;
import com.zjnu.pojo.PageBean;
import com.zjnu.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class GoodsServiceImpl implements GoodsService {
    //注入Mapper
    @Autowired
    private GoodsMapper mapper;

    //查询信息
    @Override
    public Goods selectInfo(int id) {
        Goods good = mapper.selectInfo(id);
        return good;
    }
    //分页查询
    @Override
    public PageBean<Goods> selectByPage(int currentPage, int pageSize) {
        int begin = (currentPage - 1) * pageSize;
        int size = pageSize;
        List<Goods> goods = mapper.selectByPage(begin, size);
        int count = mapper.selectTotalCount();
        PageBean<Goods> pageBean= new PageBean<Goods>();
        pageBean.setTotalCount(count);
        pageBean.setRows(goods);
        return pageBean;
    }
    //根据条件分页查询
    @Override
    public PageBean<Goods> selectByPageAndCondition(int currentPage, int pageSize, Goods _goods) {
        int begin = (currentPage - 1) * pageSize;
        int size = pageSize;
        String title = _goods.getTitle();
        if(title != null && title.length() > 0){
            _goods.setTitle("%"+title+"%");
        }
        String type = _goods.getType();
        if(type != null && type.length() > 0){
            _goods.setType("%"+type+"%");
        }
        String seller = _goods.getSeller();
        if(seller != null && seller.length()>0){
            _goods.setSeller("%"+seller+"%");
        }
        List<Goods> goods = mapper.selectByPageAndCondition(begin,size,_goods);
        int count = mapper.selectTotalCountByCondition(_goods);
        PageBean<Goods> pageBean= new PageBean<Goods>();
        pageBean.setTotalCount(count);
        pageBean.setRows(goods);
        return pageBean;
    }

    //根据id查询
    @Override
    public Goods selectById(int id) {
        Goods goods = mapper.selectById(id);
        return goods;
    }
    //新增商品
    @Override
    public void addGoods(Goods goods) {
        mapper.addGoods(goods);

    }
    //删除商品
    @Override
    public void deleteById(Goods goods) {
        mapper.deleteById(goods);
    }

    @Override
    public void deleteBySeller(Goods goods) {
        mapper.deleteBySeller(goods);
    }

    @Override
    public void deleteGoodsByUsername(String username) {
        mapper.deleteGoodsByUsername(username);
    }

    @Override
    public void freezeUser(String username) {
        mapper.freezeUser(username);
    }

    @Override
    public void deleteGoodsById(int id) {
        mapper.deleteGoodsById(id);
    }

    @Override
    public Goods selectByReId(Goods goods) {
        Goods goods1 = mapper.selectByReId(goods);
        return goods1;
    }

    @Override
    public List<Goods> selectGoods(Goods _goods) {
        String title = _goods.getTitle();
        if(title != null && title.length() > 0){
            _goods.setTitle("%"+title+"%");
        }
        String type = _goods.getType();
        if(type != null && type.length() > 0){
            _goods.setType("%"+type+"%");
        }
        String seller = _goods.getSeller();
        if(seller != null && seller.length()>0){
            _goods.setSeller("%"+seller+"%");
        }
        List<Goods> goods = mapper.selectGoods(_goods);
        return goods;
    }

    @Override
    public void alterByPay(Goods goods) {
        mapper.alterByPay(goods);
    }

    @Override
    public void cancel(Goods goods) {
        mapper.cancel(goods);
    }


}
