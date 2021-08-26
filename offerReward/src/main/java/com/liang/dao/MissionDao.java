package com.liang.dao;

import com.liang.domain.Mission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MissionDao {
    List<Mission> selectMission();
}
