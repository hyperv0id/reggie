package com.example.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.reggie.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

/**
 * @program: reggie
 * @description: 员工mapper, 继承自baseMapper，获得基础增删改查方法
 * @author: 超级虚空
 * @create: 2022-08-15 22:07
 **/

@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {

}
