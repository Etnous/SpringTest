package biz.lala.SpringBoot.mapper;

import biz.lala.SpringBoot.dao.UserPojo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 用户
 * @author et
 */
public interface UserMapper extends BaseMapper<UserPojo> {

    UserPojo myGetById(Long id);
}
