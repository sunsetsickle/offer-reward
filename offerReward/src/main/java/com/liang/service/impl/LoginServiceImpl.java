package com.liang.service.impl;

import com.liang.dao.UserDao;
import com.liang.domain.User;
import com.liang.exception.LoginException;
import com.liang.imag.GraphicHelper;
import com.liang.service.LoginService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class LoginServiceImpl implements LoginService {
    @Resource
    private UserDao dao;

    @Override
    public User login(String loginAct, String loginPwd) throws LoginException {
        User user;
        user= dao.selectUser(loginAct, loginPwd);
        if (user==null){
            throw new LoginException("用户名或密码错误");
        }
        return user;
    }

    @Override
    public int editPwd(User user, String oldPwd, String newPwd) throws LoginException {
        if (!user.getLoginPwd().equals(oldPwd)){
            throw new LoginException("原密码错误！！");
        }
        int cont=dao.updateUser(user.getId(),newPwd);
        if (cont==0){
            throw new LoginException("密码修改失败");
        }
        return cont;
    }
}
