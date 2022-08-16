package com.example.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.reggie.entity.Employee;
import com.example.reggie.service.EmployeeService;
import com.example.reggie.util.R;
import lombok.extern.slf4j.Slf4j;
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
}
