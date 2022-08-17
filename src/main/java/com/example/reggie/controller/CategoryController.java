package com.example.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.reggie.entity.Category;
import com.example.reggie.service.CategoryService;
import com.example.reggie.util.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @program: reggie
 * @description:
 * @author: 超级虚空
 * @create: 2022-08-17 09:59
 **/
@RestController
@RequestMapping("/category")
@Slf4j
public class CategoryController {
    @Autowired
    private CategoryService service;


    /**
     * 新增分类
     * @param category 分类
     */
    @PostMapping
    public R<String> addCategory(@RequestBody Category category){
        log.info("save category: {}", category);
        service.save(category);
        return R.success("新增分类成功");
    }


    /**
     * 分页获取菜品分页信息
     * @param page 电当前页面
     * @param pageSize 每页大小
     */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize){
        log.info("查询菜品-->curPage = {}, pageSize = {}", page, pageSize);
        // 构造分页构造器
        Page<Category> pInfo = new Page<>(page, pageSize);
        // 构造条件构造器
        LambdaQueryWrapper<Category> qw = new LambdaQueryWrapper<>();
        // 排序
        qw.orderByAsc(Category::getSort);
        // 执行查询, 内部封装，不需要返回
        service.page(pInfo, qw);
        return R.success(pInfo);
    }


    /**
     * 根据id删除菜品，由于前端请求的是ids，在这里我们改成ids而不是id，否则id会为null不能成功删除
     */
    @DeleteMapping
    public R<String> deleteCategory(Long ids){
        log.info("删除菜品，id为：{}", ids);

        service.myRemoveById(ids);
        return R.success("分类信息删除成功");
    }

}
