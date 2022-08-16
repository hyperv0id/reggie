package com.example.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.reggie.entity.Employee;
import com.example.reggie.service.EmployeeService;
import com.example.reggie.util.R;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * @program: reggie
 * @description: 员工控制类
 * @author: 超级虚空
 * @create: 2022-08-15 22:16
 **/
@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    /**
     * 员工登录
     * @param request request对象，
     * @param e 员工实体，需要从json转换
     * @return R对象
     */
    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee e){
        // 1. 将密码就行md5加密
        String password = e.getPassword();
        // TODO: 相当于没加密，实际开发中需要加盐处理
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        // 2. 查数据库
        LambdaQueryWrapper<Employee> qw = new LambdaQueryWrapper<>();
        qw.eq(Employee::getUsername, e.getUsername());
        Employee emp = employeeService.getOne(qw);
        // 3. 判断账号密码
        if(emp==null || !emp.getPassword().equals(password)){
            return R.error("账号或密码错误");
        }
        // 4. 查看员工状态，看看是否被封禁
        if(emp.getStatus() == 0){
            return R.error("员工已被关进小黑屋");
        }
        // 5. 登录成功, 将员工id存入Session并返回登陆成功
        request.getSession().setAttribute("employee", emp.getId());
        return R.success(emp);
    }

    /**
     * 新增员工方法, 使用post方法
     * @param employee 员工
     * @return 返回是否添加成功
     */
    @PostMapping
    public R<String> addEmployee(HttpServletRequest request, @RequestBody Employee employee){
        log.info("新增员工：{}", employee);
        // 加密处理 TODO: 加盐
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());
        Long user = (Long) request.getSession().getAttribute("employee");
        employee.setCreateUser(user);
        employee.setUpdateUser(user);
        employeeService.save(employee);
        return R.success("新增员工成功。");
    }

    /**
     * 员工信息分页查询
     * @param page 当前页面
     * @param pageSize 煤业大小
     * @param name 员工姓名，正则匹配
     * @return 返回page的R对象而不是Employee对象
     */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name){
        log.info("curPage = {}, pageSize = {}, name = {}", page, pageSize, name);
        // 构造分页构造器
        Page pInfo = new Page(page, pageSize);
        // 构造条件构造器
        LambdaQueryWrapper<Employee> qw = new LambdaQueryWrapper<>();
        // 添加条件
        qw.like(StringUtils.isNotEmpty(name), Employee::getName, name);
        // 排序
        qw.orderByDesc(Employee::getUpdateTime);
        // 执行查询, 内部封装，不需要返回
        employeeService.page(pInfo, qw);
        return R.success(pInfo);
    }

    @PutMapping
    public R<String> update(HttpServletRequest request, @RequestBody Employee employee){
        // JavaScript会放不下，需要在jackson中配置
        Long empId = (Long)request.getSession().getAttribute("employee");
        employee.setUpdateTime(LocalDateTime.now());
        employee.setUpdateUser(empId);
        employeeService.updateById(employee);
        return R.success("员工信息修改");
    }
}
