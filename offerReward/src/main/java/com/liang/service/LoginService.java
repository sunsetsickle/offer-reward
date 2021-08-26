package com.liang.service;

import com.liang.domain.User;
import com.liang.exception.LoginException;

public interface LoginService {
    User login(String loginAct, String loginPwd) throws LoginException;
    int editPwd(User user, String oldPwd, String newPwd) throws LoginException;
}
