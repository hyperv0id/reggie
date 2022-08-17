package com.example.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.reggie.entity.Category;
import org.apache.ibatis.annotations.Mapper;

/**
 * @program: reggie
 * @description:
 * @author: 超级虚空
 * @create: 2022-08-17 09:56
 **/
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
}
