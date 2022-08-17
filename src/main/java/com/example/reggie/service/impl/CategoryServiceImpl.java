package com.example.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.reggie.entity.Category;
import com.example.reggie.entity.Dish;
import com.example.reggie.entity.Setmeal;
import com.example.reggie.mapper.CategoryMapper;
import com.example.reggie.service.CategoryService;
import com.example.reggie.service.DishService;
import com.example.reggie.service.SetMealService;
import com.example.reggie.util.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program: reggie
 * @description:
 * @author: 超级虚空
 * @create: 2022-08-17 09:58
 **/
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService{

    @Autowired
    private DishService dishService;

    @Autowired
    private SetMealService setMealService;

    /**
     * 按id删除菜品，删除之前宣言判断是否关联了菜单
     * @param id 菜品id
     */
    @Override
    public void myRemoveById(Long id) {
        // 查询关联菜品
        LambdaQueryWrapper<Dish> dishQW = new LambdaQueryWrapper<>();
        dishQW.eq(Dish::getCategoryId, id);
        if(dishService.count(dishQW)>0){
            // 在全局异常处理器中处理异常
            throw new CustomException("当前分类项关联了菜品，不能删除");
        }
        // 查询关联套餐
        LambdaQueryWrapper<Setmeal> setMealLambdaQueryWrapper = new LambdaQueryWrapper<Setmeal>();
        setMealLambdaQueryWrapper.eq(Setmeal::getId, id);
        if(setMealService.count(setMealLambdaQueryWrapper)>0){
            // 在全局异常处理器中处理异常
            throw new CustomException("当前分类项关联了套餐，不能删除");
        }
        // 正常删除
        super.removeById(id);
    }
}
