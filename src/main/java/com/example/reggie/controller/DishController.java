package com.example.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.reggie.dto.DishDto;
import com.example.reggie.entity.Category;
import com.example.reggie.entity.Dish;
import com.example.reggie.entity.Employee;
import com.example.reggie.service.CategoryService;
import com.example.reggie.service.DishFlavorService;
import com.example.reggie.service.DishService;
import com.example.reggie.util.R;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: reggie
 * @description: 菜品管理
 * @author: 超级虚空
 * @create: 2022-08-19 16:00
 **/
@RestController
@RequestMapping("/dish")
@Slf4j
public class DishController {
    @Autowired
    private DishService dService;

    @Autowired
    private DishFlavorService dfService;


    /**
     * 新增菜品功能实现
     * @param dto DishFlavor数据传输
     */
    @PostMapping
    public R<String> save(@RequestBody DishDto dto){
        log.info("新增菜品, dto={}",dto);
        dService.saveWithFlavor(dto);
        return R.success("菜品添加成功");
    }
}
