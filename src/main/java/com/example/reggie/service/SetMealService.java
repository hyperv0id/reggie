package com.example.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.reggie.dto.SetmealDto;
import com.example.reggie.entity.Setmeal;

/**
 * @program: reggie
 * @description: 套餐service
 * @author: 超级虚空
 * @create: 2022-08-17 15:45
 **/

public interface SetMealService extends IService<Setmeal> {

    /**
     * 新增套餐，同时保存套餐和菜品之间的关系
     * @param dto 套餐数据
     */
    void saveWithDish(SetmealDto dto);
}
