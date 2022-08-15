package com.example.reggie.util;

import lombok.Data;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: reggie
 * @description: result的缩写，通用的返回结果类，用来规范后端相应格式
 * @author: 超级虚空
 * @create: 2022-08-15 22:19
 **/

@Data
public class R<T> {
    private Integer code; //编码：1成功，0和其它数字为失败

    private String msg; //错误信息

    private T data; //数据，返回实体或数组

    private Map map = new HashMap<>(); //动态数据

    public static <T> R<T> success(T object) {
        R<T> r = new R<T>();
        r.data = object;
        r.code = 1;
        return r;
    }

    public static <T> R<T> error(String msg) {
        R<T> r = new R<>();
        r.msg = msg;
        r.code = 0;
        return r;
    }

    public R<T> add(String key, Object value) {
        this.map.put(key, value);
        return this;
    }

}
