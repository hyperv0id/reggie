package com.example.reggie.dto;

import com.example.reggie.entity.Dish;
import com.example.reggie.entity.DishFlavor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: reggie
 * @description: DishFlavor数据传输对象
 * @author: 超级虚空
 * @create: 2022-08-19 16:25
 **/
@Data
public class DishDto extends Dish {
    private List<DishFlavor> flavors = new ArrayList<>();

    private String categoryName;

    private Integer copies;
}
