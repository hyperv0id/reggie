package com.example.reggie.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @program: reggie
 * @description: 员工实体类
 * @author: 超级虚空
 * @create: 2022-08-15 22:07
 **/
@Data
public class Employee implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id; // 用户ID
    private String username;
    private String name;
    private String password;
    private String phone;
    private String sex;
    private String idNumber; // 身份证号码
    private Integer status;
    private LocalDateTime createTime; // 创建时间
    private LocalDateTime updateTime; // 更新时间

    @TableField(fill = FieldFill.INSERT)
    private Long createUser;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;

}
