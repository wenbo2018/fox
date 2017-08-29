package com.github.wenbo2018.fox.admin.services;

import com.github.wenbo2018.fox.admin.dao.UserDao;
import com.github.wenbo2018.fox.admin.entity.User;
import com.github.wenbo2018.fox.admin.services.api.UserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by wenbo.shen on 2017/7/2.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public int addUser(User user) {
        if (user==null) {
            return -1;
        }

        return userDao.addUser(user);
    }

    @Override
    public User loadUserByUserId(int userId) {
        if (userId <= 0) {
            return null;
        }
        User user=userDao.loadUserByUserId(userId);
        return user;
    }

    @Override
    public User loadUserByUserNameAndPassWord(String userName, String passWord) {
        User user = null;
        if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(passWord)) {
            return null;
        }
        user = userDao.loadUserByUserNameAndPassWord(userName, passWord);
        return user;
    }

    @Override
    public User loadUserByUserName(String userName) {
        if (StringUtils.isEmpty(userName)) {
            return null;
        }
        User user= userDao.loadUserByUserName(userName);
        return user;
    }

    @Override
    public User loadUserByToken(String token) {
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        User user= userDao.loadUserByToken(token);
        if (user==null) {
            return null;
        }
        return user;
    }

    @Override
    public int updateUserTokenByUserId(String token, int userId) {
        return userDao.updateUserTokenByUserId(token,userId);
    }
}
