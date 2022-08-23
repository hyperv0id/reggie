package com.example.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.reggie.dto.SetmealDto;
import com.example.reggie.entity.Setmeal;
import com.example.reggie.entity.SetmealDish;
import com.example.reggie.mapper.SetMealMapper;
import com.example.reggie.service.SetMealService;
import com.example.reggie.service.SetmealDishService;
import com.example.reggie.util.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: reggie
 * @description:
 * @author: 超级虚空
 * @create: 2022-08-17 15:47
 **/
@Service
@Slf4j
public class SetMealServiceImpl extends ServiceImpl<SetMealMapper, Setmeal> implements SetMealService {

    @Autowired
    private SetmealDishService setmealDishService;

    /**
     * 新增套餐，同时保存套餐和菜品之间的关系
     * @param dto 套餐数据
     */
    @Override
    @Transactional
    public void saveWithDish(SetmealDto dto) {
        // insert setmeal
        this.save(dto); // dto继承自Setmeal

        // insert setmeal_dish
        List<SetmealDish> dishes = dto.getSetmealDishes();
        dishes = dishes.stream().peek((item)-> item.setSetmealId(dto.getId())).collect(Collectors.toList());
        setmealDishService.saveBatch(dishes);
    }

    /**
     * 删除套餐
     * @param ids 套餐ids
     */
    @Override
    @Transactional
    public void removeWithDish(List<Long> ids) {
        // 查询套餐状态，确定是否可以删除
        LambdaQueryWrapper<Setmeal> qw = new LambdaQueryWrapper<>();
        qw.in(Setmeal::getId, ids);
        qw.eq(Setmeal::getStatus, 1);

        // 如果正在售卖，那么不能删除
        int cnt = this.count(qw);
        if(cnt > 0){
            throw new CustomException("套餐正在售卖中，不能删除");
        }
        // 先删除setmeal
        this.removeByIds(ids);

        // 删除setmeal_dish
        // select setmeal_dish where setmeal_id in ${ids};
        LambdaQueryWrapper<SetmealDish> dishQW = new LambdaQueryWrapper<>();
        dishQW.in(SetmealDish::getSetmealId, ids);
        setmealDishService.remove(dishQW);
    }
}
