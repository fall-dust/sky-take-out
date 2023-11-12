package com.sky.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sky.constant.MessageConstant;
import com.sky.constant.PasswordConstant;
import com.sky.constant.StatusConstant;
import com.sky.context.BaseContext;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.exception.AccountLockedException;
import com.sky.exception.AccountNotFoundException;
import com.sky.exception.PasswordErrorException;
import com.sky.mapper.EmployeeMapper;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.EmployeeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.ResourceBundle;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * 员工登录
     *
     * @param employeeLoginDTO
     * @return
     */
    public Employee login(EmployeeLoginDTO employeeLoginDTO) {
        String username = employeeLoginDTO.getUsername();
        String password = employeeLoginDTO.getPassword();

        //1、根据用户名查询数据库中的数据
        Employee employee = employeeMapper.getByUsername(username);

        //2、处理各种异常情况（用户名不存在、密码不对、账号被锁定）
        if (employee == null) {
            //账号不存在
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        // 密码比对
        /*
         * MD5加密之后与数据库进行比对（数据库中存储的密码是加密过的）。
         * MD5,全称Message Digest Algorithm 5，翻译过来就是消息摘要算法第5版，是计算机安全领域广泛使用的一种散列函数，用于确保信息传输的完整性。MD5算法是由MD2、MD3、MD4演变而来，是一种单向加密算法，一种不可逆的加密方式。
         **/
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!password.equals(employee.getPassword())) {
            //密码错误
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        if (Objects.equals(employee.getStatus(), StatusConstant.DISABLE)) {
            //账号被锁定
            throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
        }

        //3、返回实体对象
        return employee;
    }

    /**
     * 新增员工
     *
     * @param employeeDTO 员工数据模型
     */
    @Override
    public void save(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);// 拷贝属性（属性名须一致）

        LocalDateTime now = LocalDateTime.now();// 当前时间
        Long currentId = BaseContext.getCurrentId();// 执行本操作的用户ID

        employee.setPassword(PasswordConstant.DEFAULT_PASSWORD);// 设置默认密码
        employee.setStatus(StatusConstant.ENABLE);// 设置员工账号状态，默认：启用
        employee.setCreateTime(now);
        employee.setUpdateTime(now);
        employee.setCreateUser(currentId);
        employee.setUpdateUser(currentId);
        employeeMapper.insert(employee);
    }

    /**
     * 分页查询员工
     *
     * @param employeePageQueryDTO 分页查询员工接收的数据格式
     * @return {@link PageResult}
     */
    @Override
    public PageResult<Employee> page(EmployeePageQueryDTO employeePageQueryDTO) {
        Page<Employee> page = new Page<>(employeePageQueryDTO.getPage(), employeePageQueryDTO.getPageSize());
        LambdaQueryWrapper<Employee> employeeLambdaQueryWrapper = new LambdaQueryWrapper<>();// 条件构造器
        employeeLambdaQueryWrapper = employeeLambdaQueryWrapper
                .like(employeePageQueryDTO.getName() != null, Employee::getName/*如果直接对new LambdaQueryWrapper<>()链式调用抽象包装器，则无法使用该方法引用*/, employeePageQueryDTO.getName());
        page = employeeMapper.selectPage(page, employeeLambdaQueryWrapper);
        return new PageResult<>(page.getTotal(), page.getRecords());
    }
}
