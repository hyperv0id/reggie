package com.example.reggie.controller;

import com.example.reggie.service.SetMealService;
import com.example.reggie.service.SetmealDishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: reggie
 * @description: 套餐管理类
 * @author: 超级虚空
 * @create: 2022-08-21 15:28
 **/

@Slf4j
@RestController
@RequestMapping("/setmeal")
public class SetmealController {
    @Autowired
    private SetMealService setmealService;
    @Autowired
    private SetmealDishService dishService;


}
