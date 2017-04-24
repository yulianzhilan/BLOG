package service.mapper;

import entity.system.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * Created by scott on 2017/3/15.
 */
@Component("userMapper")
public interface UserMapper {
    User getUser(@Param("account")String account, @Param("password")String password);
    int countUser(@Param("account")String account);
    User getUserById(@Param("userId")int userId);
}
