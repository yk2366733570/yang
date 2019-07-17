package com.itheima.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

//配置一个加密类
public class BCryptPasswordEncoderUtils {
    //声明加密类
    private static BCryptPasswordEncoder bCryptPasswordEncoder =new BCryptPasswordEncoder();

    //调用加密方法把密码进行加密
    public static String encoderPassword(String password){
        return bCryptPasswordEncoder.encode(password);
    }

}
