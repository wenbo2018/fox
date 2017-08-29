package com.github.wenbo2018.fox.admin.entity;

import lombok.Data;

import java.util.Date;

/**
 * Created by shenwenbo on 2017/4/16.
 */
@Data
public class User {
    private int id;
    private int userId;
    private String userName;
    private String passWord;
    private String userEmail;
    private String token;
    private Date addTime;
}
