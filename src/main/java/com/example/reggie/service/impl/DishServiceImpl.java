package com.example.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.reggie.dto.DishDto;
import com.example.reggie.entity.Dish;
import com.example.reggie.entity.DishFlavor;
import com.example.reggie.mapper.DishMapper;
import com.example.reggie.service.DishFlavorService;
import com.example.reggie.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
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


    /**
     * 根据菜品id获取菜品及口味信息
     * @param id 菜品id
     * @return dto
     */
    public DishDto getByIdWithFlavor(Long id) {
        // 1. 查询菜品基本信息
        Dish dish = this.getById(id);
        // 2. 查询菜品口味信息
        LambdaQueryWrapper<DishFlavor> qw = new LambdaQueryWrapper<>();
        qw.eq(DishFlavor::getDishId, dish.getId());
        List<DishFlavor> dishFlavors = dfService.list(qw);
        // 合并信息
        DishDto dto = new DishDto();
        BeanUtils.copyProperties(dish, dto);
        dto.setFlavors(dishFlavors);
        return dto;
    }


    /**
     *  更新菜品和口味
     * @param dto 菜品和口味信息
     */
    @Transactional
    @Override
    public void updateWithFlavor(DishDto dto) {
        // 更新dish表
        Long id = dto.getId();
        this.updateById(dto);
        // 删除对应dishFlavor
        LambdaQueryWrapper<DishFlavor> qw = new LambdaQueryWrapper<>();
        qw.eq(DishFlavor::getDishId, id);
        dfService.remove(qw);
        // 重新添加dishFlavor
        for (DishFlavor df :
                dto.getFlavors()) {
            df.setDishId(id);
            dfService.save(df);
        }

    }
}
