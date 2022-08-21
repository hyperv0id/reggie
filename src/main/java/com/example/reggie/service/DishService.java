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

    /**
     * 根据菜品id获取菜品及口味信息
     * @param id 菜品id
     * @return dto
     */
    DishDto getByIdWithFlavor(Long id);

    /**
     *  更新菜品和口味
     * @param dto 菜品和口味信息
     */
    void updateWithFlavor(DishDto dto);
}
