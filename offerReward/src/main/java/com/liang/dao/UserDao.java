package com.liang.dao;

import com.liang.domain.User;
import org.apache.ibatis.annotations.Param;

public interface UserDao {
    /*
        注：
            当mybatis传入多个参数时：需要使用@Param命名参数
            当传入的是个对象时，需要与属性值一一对应
     */
    User selectUser(@Param("loginAct") String loginAct, @Param("loginPwd")String loginPwd);

    int updateUser(@Param("id") String id,@Param("newPwd") String newPwd);
}
