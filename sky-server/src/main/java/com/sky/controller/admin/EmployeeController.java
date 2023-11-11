package com.sky.controller.admin;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.sky.constant.JwtClaimsConstant;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.properties.JwtProperties;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.EmployeeService;
import com.sky.utils.JwtUtil;
import com.sky.vo.EmployeeLoginVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 员工管理
 */
@RestController
@RequestMapping("/admin/employee")
@Slf4j
@Tag(name = "员工管理", description = "员工管理")
//@OpenAPIDefinition(info = @Info(title = "员工管理", description = "这里是描述信息"), extensions = {@Extension(properties = {@ExtensionProperty(name = "", value = "")})})
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 登录
     *
     * @param employeeLoginDTO 员工登录时传递的数据模型
     */
    @Operation(summary = "登录验证", description = "登录验证功能")
    @PostMapping("/login")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "员工登录时传递的数据模型")
    public Result<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO employeeLoginDTO) {
        log.info("员工登录：{}", employeeLoginDTO);

        Employee employee = employeeService.login(employeeLoginDTO);

        // 登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.EMP_ID, employee.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);

        EmployeeLoginVO employeeLoginVO = EmployeeLoginVO.builder()
                .id(employee.getId())
                .userName(employee.getUsername())
                .name(employee.getName())
                .token(token)
                .build();

        return Result.success(employeeLoginVO);
    }

    /**
     * 退出登录
     */
    @PostMapping("/logout")
    @Operation(summary = "退出登录", description = "退出登录")
    public Result<Object> logout() {
        return Result.success();
    }

    @PostMapping
    @Operation(summary = "添加员工", description = "添加员工")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "添加员工接收的数据格式")
    public Result<String> save(@RequestBody EmployeeDTO employeeDTO) {
        log.info("添加员工{}", employeeDTO);
        employeeService.save(employeeDTO);
        return Result.success();
    }

    @GetMapping("/page")
    @Operation(summary = "分页查询", description = "分页查询")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "分页查询")
    public Result<PageResult<Employee>> page(EmployeePageQueryDTO employeePageQueryDTO) {
        return Result.success(employeeService.page(employeePageQueryDTO));
    }

    /**
     * 启用/禁用账号
     *
     * @return {@link Result}
     */
    @PostMapping("/status/{status}")
    @Operation(summary = "启用/禁用账号", description = "启用/禁用账号")
    public Result<Object> changeStatus(@PathVariable Integer status, @RequestParam Long id) {
        Employee employee = Employee.builder()
                .id(id)
                .status(status)
                .build();
        employeeService.updateById(employee);
        return Result.success();
    }

    /**
     * 根据id查询员工
     *
     * @param id 应该ID
     * @return {@link Result}
     */
    @GetMapping("/{id}")
    @Operation(summary = "根据id查询员工", description = "根据id查询员工")
    public Result<Employee> getById(@PathVariable Long id) {
        return Result.success(employeeService.getById(id));
    }

    /**
     * 更新员工
     *
     * @param employeeDTO 添加员工接收的数据格式
     * @return {@link Result}
     */
    @PutMapping
    @Operation(summary = "更新员工", description = "更新员工")
    public Result<Object> update(@RequestBody EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);// 属性拷贝
        employee.setUpdateTime(LocalDateTime.now());// 设置更新时间
        // 更新
        employeeService.update(employee, new LambdaUpdateWrapper<Employee>().eq(Employee::getId, employee.getId()));
        return Result.success();
    }
}
