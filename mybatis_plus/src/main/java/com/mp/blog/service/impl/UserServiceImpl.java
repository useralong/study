package com.mp.blog.service.impl;

import com.mp.blog.entity.User;
import com.mp.blog.mapper.UserMapper;
import com.mp.blog.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Allen
 * @since 2022-08-25
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
