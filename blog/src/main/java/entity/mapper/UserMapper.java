package entity.mapper;

import entity.User;
import org.apache.ibatis.annotations.Param;

/**
 * Created by scott on 2017/3/15.
 */
public interface UserMapper {
    User getUser(@Param("account")String account, @Param("password")String password);
}
