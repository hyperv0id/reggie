package com.example.reggie.util;

/**
 * @program: reggie
 * @description: 自定义业务异常
 * @author: 超级虚空
 * @create: 2022-08-17 15:59
 **/

public class CustomException extends RuntimeException{
    public CustomException(String msg){
        super(msg);
    }

}
