package com.zjnu.service.impl;

import com.zjnu.mapper.TrolleyMapper;
import com.zjnu.pojo.Trolley;
import com.zjnu.service.TrolleyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TrolleyServiceImpl implements TrolleyService {
    @Autowired
    private TrolleyMapper mapper;
    @Override
    public List<Trolley> selectTrolley(Trolley trolley) {
        List<Trolley> trolleys = mapper.selectTrolley(trolley);
        return trolleys;
    }

    @Override
    public void deleteTrolley(Trolley trolley) {
        mapper.deleteTrolley(trolley);
    }

    @Override
    public void saveCount(Trolley trolley) {
        mapper.saveCount(trolley);
    }

    @Override
    public void addTrolley(Trolley trolley) {
        mapper.addTrolley(trolley);
    }

    @Override
    public void deleteTrolleyByUsername(String username) {
        mapper.deleteTrolleyByUsername(username);
    }

    @Override
    public void freezeUser(String username) {
        mapper.freezeUser(username);
    }

    @Override
    public void deleteTrolleyById(int id) {
        mapper.deleteTrolleyById(id);
    }


}
