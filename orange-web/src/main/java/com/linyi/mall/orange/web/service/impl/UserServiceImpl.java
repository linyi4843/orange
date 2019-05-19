package com.linyi.mall.orange.web.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.linyi.mall.orange.common.entity.User;
import com.linyi.mall.orange.web.mapper.UserMapper;
import com.linyi.mall.orange.web.service.IUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author linyi
 * @since 2019-04-17
 */
@Service
@Transactional
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
