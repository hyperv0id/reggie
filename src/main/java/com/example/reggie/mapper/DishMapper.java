package com.example.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.reggie.entity.Dish;
import org.apache.ibatis.annotations.Mapper;

/**
 * @program: reggie
 * @description: 菜品mapper
 * @author: 超级虚空
 * @create: 2022-08-17 15:43
 **/
@Mapper
public interface DishMapper extends BaseMapper<Dish> {
}
