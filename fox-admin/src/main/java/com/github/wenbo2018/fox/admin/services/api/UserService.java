package com.github.wenbo2018.fox.admin.services.api;

import com.github.wenbo2018.fox.admin.entity.User;

/**
 * Created by wenbo.shen on 2017/5/19.
 */
public interface UserService {
    int addUser(User user);
    User loadUserByUserId(int userId);
    User loadUserByUserNameAndPassWord(String userName, String passWord);
    User loadUserByUserName(String userName);
    User loadUserByToken(String token);
    int updateUserTokenByUserId(String token, int userId);
}
