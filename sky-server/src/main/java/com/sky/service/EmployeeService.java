package com.sky.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.entity.Employee;

public interface EmployeeService extends IService<Employee> {

    /**
     * 员工登录
     *
     * @param employeeLoginDTO 员工数据模型
     * @return {@link Employee} 员工信息将响应给前端，还用于生成JWT令牌（token）
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);

    /**
     * 新增员工
     *
     * @param employeeDTO 员工数据模型
     */
    int save(EmployeeDTO employeeDTO);
}
