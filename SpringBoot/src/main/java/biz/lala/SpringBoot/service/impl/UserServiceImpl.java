package biz.lala.SpringBoot.service.impl;

import biz.lala.SpringBoot.dao.UserPojo;
import biz.lala.SpringBoot.mapper.UserMapper;
import biz.lala.SpringBoot.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author et
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserPojo> implements UserService {
}
