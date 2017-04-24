package service.impl;

import dto.CallBackDTO;
import dto.system.SidebarDTO;
import dto.system.UserSummaryDTO;
import entity.system.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import service.ValidateService;
import service.mapper.SidebarMapper;
import service.mapper.UserMapper;
import util.AssembleUtil;

import java.util.List;

/**
 * Created by scott on 2017/3/15.
 */
@Service
public class ValidateServiceImpl implements ValidateService{
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private SidebarMapper sidebarMapper;

    @Override
    public UserSummaryDTO getUserSummaryDTO(String _user, String _token){
        return AssembleUtil.assembleUserSummaryDTO(getUser(_user, _token));
    }

    @Override
    public User getUser(String _user, String _token) {
        if(StringUtils.isEmpty(_user) || StringUtils.isEmpty(_token)){
            return null;
        }
        return userMapper.getUser(_user, _token);
    }

    @Override
    public User getUser(int userId) {
        if(userId == 0){
            return null;
        }
        return userMapper.getUserById(userId);
    }

    @Override
    public CallBackDTO validate(String _user, String _token) {
        CallBackDTO result = new CallBackDTO();
        User user = getUser(_user, _token);
        if(user != null){
            result.setOkay(true);
            result.setObj(user);
            return result;
        }
        int amount = userMapper.countUser(_user);
        if(amount != 0){
            result.setOkay(false);
            result.setErrcode(102);
            result.setErrtx("帐号或密码错误！");
            return result;
        }
        result.setOkay(false);
        result.setErrcode(101);
        result.setErrtx("未注册用户！");
        return result;
    }

    @Override
    public List<SidebarDTO> getSideBar(User user) {
        if(user == null){
            //  这里增加重新登录机制 重新登录机制已经加在controller
            return null;
        }
        return sidebarMapper.getSidebar(user.getRoleId());
    }
}
