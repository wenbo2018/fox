package com.github.wenbo2018.fox.admin.web.user;


import com.github.wenbo2018.fox.admin.bean.CommonResultJson;
import com.github.wenbo2018.fox.admin.constants.ResultCode;
import com.github.wenbo2018.fox.admin.entity.User;
import com.github.wenbo2018.fox.admin.services.api.UserService;
import com.github.wenbo2018.fox.admin.utils.MD5Utils;
import com.github.wenbo2018.fox.admin.utils.WebUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wenbo.shen on 2017/5/19.
 */
@Controller
@RequestMapping(value = "/jconf/user")
public class UserController extends AbstractController {

    private static org.slf4j.Logger logger= LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @RequestMapping(value = "login/index",method = RequestMethod.GET)
    public String loginIndex() {
        return "admin/login";
    }

    /**
     * 登录验证
     * @param userName
     * @param passWord
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "login/checkUser",method = RequestMethod.POST)
    public CommonResultJson login(String userName, String passWord) {
        CommonResultJson result=new CommonResultJson();
        result.setCode(ResultCode.SUCCESS);
        if (StringUtils.isEmpty(userName)||StringUtils.isEmpty(passWord)) {
            result.setMessage("用户名或密码不能问空!");
            result.setCode(ResultCode.PARAMETER_ERROR);
            return result;
        }
        String md5Pass=null;
        try {
            md5Pass= MD5Utils.EncoderByMd5(passWord);
        } catch (NoSuchAlgorithmException e) {
            logger.error("md5 encodeing error:{}",e);
        }
        User userDTO = userService.loadUserByUserNameAndPassWord(userName,md5Pass);
        if (userDTO == null) {
            result.setMessage("用户名或密码错误!");
            result.setCode(ResultCode.PARAMETER_ERROR);
            return result;
        }
        //生产token并保存于cookie中
        Map<String , Object> payload=new HashMap<String, Object>();
        Date date=new Date();
        payload.put("uid", userDTO.getUserId());//用户ID
        payload.put("iat", date.getTime());//生成时间
        payload.put("ext",date.getTime()+1800);//过期时间1小时
        String _token = WebUtils.produceToken(payload);
        userService.updateUserTokenByUserId(_token,userDTO.getUserId());
        WebUtils.addStringToCookie(response,"token",_token,1800);
        result.setMessage("登录成功!");
        return result;
    }

    /**
     * 注册验证
     * @param userName
     * @param passWord
     * @param email
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "login/userSignIn",method = RequestMethod.POST)
    public CommonResultJson signIn(String userName,String passWord,String email) {
        CommonResultJson result=new CommonResultJson();
        result.setCode(ResultCode.SUCCESS);
        if (userService.loadUserByUserName(userName)==null) {
           result.setCode(ResultCode.PARAMETER_ERROR);
           result.setMessage("用户名已存在!");
           return result;
        }
        String md5Pass=null;
        try {
            md5Pass= MD5Utils.EncoderByMd5(passWord);
        } catch (NoSuchAlgorithmException e) {
            logger.error("md5 encodeing error:{}",e);
        }
        User userDTO=new User();
        userDTO.setUserName(userName);
        userDTO.setPassWord(md5Pass);
        userDTO.setUserName(email);
        int userId = userService.addUser(userDTO);
        //生产token并保存于cookie中
        Map<String , Object> payload=new HashMap<String, Object>();
        Date date=new Date();
        payload.put("uid", userId);//用户ID
        payload.put("iat", date.getTime());//生成时间
        payload.put("ext",date.getTime()+1800);//过期时间1小时
        String _token = WebUtils.produceToken(payload);
        WebUtils.addStringToCookie(response,"token",_token,1800);
        result.setMessage("注册成功!");
        return result;
    }
}
