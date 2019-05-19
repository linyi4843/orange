package com.linyi.mall.orange.web.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.linyi.mall.orange.common.entity.User;
import com.linyi.mall.orange.web.service.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author linyi
 * @since 2019-04-17
 */
@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final IUserService service;

    @RequestMapping("/h")
    public R getHello(@RequestParam(defaultValue = "1", required = false) Integer id) {
        return R.ok(service.getOne(new QueryWrapper<User>().eq("id",id),true));
    }

}
