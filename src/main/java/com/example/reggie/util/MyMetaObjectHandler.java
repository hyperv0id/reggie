package com.example.reggie.util;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @program: reggie
 * @description: 公共字段自动填充，元数据处理器
 * @author: 超级虚空
 * @create: 2022-08-17 09:03
 **/
@Component
@Slf4j
public class MyMetaObjectHandler implements MetaObjectHandler {
    /**
     * 当我们执行insert语句时会执行自动填充
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        metaObject.setValue("createTime", LocalDateTime.now());
        metaObject.setValue("updateTime", LocalDateTime.now());
        metaObject.setValue("createUser", BaseContent.getCurId());
        metaObject.setValue("updateUser", BaseContent.getCurId());
    }

    /**
     * 执行update语句时会使用
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        metaObject.setValue("updateTime", LocalDateTime.now());
        metaObject.setValue("updateUser", BaseContent.getCurId());
    }
}
