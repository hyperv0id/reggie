package com.example.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.reggie.dto.DishDto;
import com.example.reggie.entity.Dish;

/**
 * @program: reggie
 * @description: 菜品service
 * @author: 超级虚空
 * @create: 2022-08-17 15:44
 **/

public interface DishService extends IService<Dish> {
    /**
     * 新增菜品，同时插入对应口味
     */
    void saveWithFlavor(DishDto dto);

    DishDto getByIdWithFlavor(Long id);

    void updateWithFlavor(DishDto dto);
}
