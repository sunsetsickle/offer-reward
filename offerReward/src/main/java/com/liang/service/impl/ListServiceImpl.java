package com.liang.service.impl;

import com.liang.dao.MissionDao;
import com.liang.domain.Mission;
import com.liang.service.ListService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
public class ListServiceImpl implements ListService {

    @Resource
    private MissionDao dao;

    @Override
    public List<Mission> listTest() {
        return dao.selectMission();
    }
}
