package com.example.reggie.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * @program: reggie
 * @description: 全局异常处理器
 * @author: 超级虚空
 * @create: 2022-08-16 15:24
 **/
// 所有使用Rest和普通Controller注解的类都会被处理
@ControllerAdvice(annotations = {RestController.class, Controller.class})
// 将结果返回json数据
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 处理sql异常，如果添加已存在的员工等会出现此异常
     */
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public R<String> exceptionHandler(SQLIntegrityConstraintViolationException ex){
        String errMessage = ex.getMessage();
        log.info(errMessage);
        //
        if(errMessage.contains("Duplicate entry")){
            String[] split = errMessage.split(" ");
            return R.error("值重复，值为：" + split[2]);
        }
        return R.error("未知错误，操作失败");
    }

    /**
     * 处理删除菜品时存在已关联的菜品或套餐的异常
     */
    @ExceptionHandler(CustomException.class)
    public R<String> exceptionHandler(CustomException ex){
        return R.error(ex.getMessage());
    }
}
