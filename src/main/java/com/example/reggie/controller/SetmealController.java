package com.example.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.reggie.dto.SetmealDto;
import com.example.reggie.entity.Category;
import com.example.reggie.entity.Setmeal;
import com.example.reggie.service.CategoryService;
import com.example.reggie.service.SetMealService;
import com.example.reggie.service.SetmealDishService;
import com.example.reggie.util.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: reggie
 * @description: 套餐管理类
 * @author: 超级虚空
 * @create: 2022-08-21 15:28
 **/

@Slf4j
@RestController
@RequestMapping("/setmeal")
public class SetmealController {
    @Autowired
    private SetMealService setmealService;
    @Autowired
    private SetmealDishService dishService;
    @Autowired
    private CategoryService categoryService;
    /**
     * 新建套餐
     * @param dto 套餐数据
     */
    @PostMapping
    public R<String> save(@RequestBody SetmealDto dto){
        log.info("新建套餐：{}", dto);
        setmealService.saveWithDish(dto);
        return R.success("新增套餐成功");
    }

    /**
     * 套餐分页查询代码实现
     * @param page 当前页码
     * @param pageSize 每页大小
     * @param name 查询菜品名称
     */
    @GetMapping("/page")
    public R<Page<SetmealDto>> page(int page, int pageSize, String name){
        Page<Setmeal> pageInfo = new Page<>(page, pageSize);
        LambdaQueryWrapper<Setmeal> qw = new LambdaQueryWrapper<>();
        // 查询条件
        qw.like(name != null, Setmeal::getName, name);
        // 排序条件，根据更新时间
        qw.orderByDesc(Setmeal::getUpdateTime);
        setmealService.page(pageInfo, qw);
        Page<SetmealDto> dtoPage = new Page<>();

        // 对象拷贝, 不拷贝记录，因为泛型不一样
        BeanUtils.copyProperties(pageInfo, dtoPage, "records");
        List<Setmeal> records = pageInfo.getRecords();
        List<SetmealDto> dtos = records.stream().map(
                (item)->{
                    SetmealDto setmealDto = new SetmealDto();
                    BeanUtils.copyProperties(item, setmealDto);
                    // 分类id
                    Long id = item.getCategoryId();
                    // 查询分类
                    Category category = categoryService.getById(id);
                    if(category != null){
                        String categoryName = category.getName();
                        setmealDto.setCategoryName(categoryName);
                    }
                    return setmealDto;
                }
        ).collect(Collectors.toList());

        dtoPage.setRecords(dtos);

        return R.success(dtoPage);
    }

    /**
     * 删除套餐
     * @param ids 要删除的套餐id
     */
    @DeleteMapping
    public R<String> delete(@RequestParam List<Long> ids){
        log.info("删除套餐，ids={}", ids);
        setmealService.removeWithDish(ids);
        return R.success("套餐删除成功");
    }
}
