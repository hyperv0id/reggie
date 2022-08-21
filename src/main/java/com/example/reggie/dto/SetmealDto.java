package com.example.reggie.dto;

import com.example.reggie.entity.Setmeal;
import com.example.reggie.entity.SetmealDish;
import lombok.Data;
import java.util.List;

/**
 * @program: reggie
 * @description:
 * @author: 超级虚空
 * @create: 2022-08-21 15:24
 **/

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
