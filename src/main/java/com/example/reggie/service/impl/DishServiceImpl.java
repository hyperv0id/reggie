package com.example.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.reggie.dto.DishDto;
import com.example.reggie.entity.Dish;
import com.example.reggie.entity.DishFlavor;
import com.example.reggie.mapper.DishMapper;
import com.example.reggie.service.DishFlavorService;
import com.example.reggie.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @program: reggie
 * @description: dishService的实现类
 * @author: 超级虚空
 * @create: 2022-08-17 15:46
 **/
@Service
@Slf4j
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {
    @Autowired
    private DishFlavorService dfService;
    /**
     * 新增菜品，同时插入对应口味
     */
    @Transactional
    public void saveWithFlavor(DishDto dto) {
        // 保存菜品基本信息到菜品表
        this.save(dto);
        // 口味数据保存到口味表

        Long dishID = dto.getId(); // 菜品ID
        // 给菜品口味赋值
        List<DishFlavor> flavors = dto.getFlavors();
        for (DishFlavor flavor : flavors) {
            flavor.setDishId(dishID);
        }
        dfService.saveBatch(flavors);
    }
}
