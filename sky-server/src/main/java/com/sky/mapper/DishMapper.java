package com.sky.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sky.entity.Dish;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;

/**
 * @author 高翔宇
 * @since 2023/11/12 周日 15:28
 */
public interface DishMapper extends BaseMapper<Dish> {

}
