package com.example.reggie.util;

/**
 * @program: reggie
 * @description: 基于ThreadLocal的工具类，保存和获取用户ID
 * @author: 超级虚空
 * @create: 2022-08-17 09:31
 **/

public class BaseContent {
    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();
    public static void setId(Long id){
        threadLocal.set(id);
    }
    public static Long getCurId(){
        return threadLocal.get();
    }
}
