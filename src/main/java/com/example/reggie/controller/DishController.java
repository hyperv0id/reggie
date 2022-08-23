package com.example.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.reggie.dto.DishDto;
import com.example.reggie.entity.Dish;
import com.example.reggie.service.CategoryService;
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

//    @Autowired
//    private DishFlavorService dfService;

    @Autowired
    private CategoryService categoryService;


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

    /**
     * 菜品信息分页查询
     * @param page 当前页码
     * @param pageSize 每页大小
     * @param name 菜品名称
     */
    @GetMapping("/page")
    public R<Page<DishDto>> page(int page, int pageSize, String name){
        log.info("curPage = {}, pageSize = {}, name = {}", page, pageSize, name);
        // 构造分页构造器
        Page<Dish> pInfo = new Page<>(page, pageSize);
        // 构造条件构造器
        LambdaQueryWrapper<Dish> qw = new LambdaQueryWrapper<>();
        // 添加条件
        qw.like(StringUtils.isNotEmpty(name), Dish::getName, name);
        // 排序
        qw.orderByDesc(Dish::getUpdateTime);
        // 执行查询, 内部封装，不需要返回
        dService.page(pInfo, qw);

        // 对象拷贝
        Page<DishDto> dtoPage = new Page<>();
        BeanUtils.copyProperties(pInfo, dtoPage);
        // 原始数据集合
        List<Dish> dishList = pInfo.getRecords();
        // 转换原始数据集合
        List<DishDto> dtoList = dishList.stream().map((item)->{
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(item, dishDto);
            Long categoryID = item.getCategoryId();
            String categoryName = categoryService.getById(categoryID).getName();
            dishDto.setCategoryName(categoryName);
            return dishDto;
        }).collect(Collectors.toList());
        dtoPage.setRecords(dtoList);

        return R.success(dtoPage);
    }

    /**
     * 根据id获取对应菜品，包括口味和菜品信息
     * @param id 菜品id
     * @return dishDto而非dish实体
     */
    @GetMapping("/{id}")
    public R<DishDto> getOne(@PathVariable Long id){
        DishDto dto = dService.getByIdWithFlavor(id);
        return R.success(dto);
    }

    /**
     * 修改菜品功能实现
     * @param dto 口味及菜品信息
     * @return 修改成功
     */
    @PutMapping
    public R<String> update(@RequestBody DishDto dto){
        dService.updateWithFlavor(dto);
        return R.success("菜品及口味信息更新成功");
    }


    /**
     * 根据条件查询对应菜品
     * @param dish dish
     * @return 菜品
     */
    @GetMapping("/list")
    public R<List<Dish>> list(Dish dish){
        LambdaQueryWrapper<Dish> qw = new LambdaQueryWrapper<>();
        qw.eq(dish.getCategoryId()!=null, Dish::getCategoryId, dish.getCategoryId());
        qw.eq(Dish::getStatus, 1); // 正在售卖的菜品
        qw.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);
        List<Dish> dishes = dService.list(qw);
        return R.success(dishes);
    }
}
