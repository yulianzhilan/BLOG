package service.impl;

import dto.CallBackDTO;
import dto.system.RegisterDTO;
import dto.system.SidebarDTO;
import dto.system.UserInfoSummaryDTO;
import dto.system.UserSummaryDTO;
import entity.system.User;
import framework.service.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import service.PhotoService;
import service.QiNiuService;
import service.ValidateService;
import service.mapper.PhotoMapper;
import service.mapper.SidebarMapper;
import service.mapper.UserMapper;
import util.AssembleUtil;
import util.Constants;

import java.util.List;

/**
 * Created by scott on 2017/3/15.
 */
@Service
public class ValidateServiceImpl implements ValidateService{
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PhotoMapper photoMapper;

    @Autowired
    private PhotoService photoService;

    @Autowired
    private SidebarMapper sidebarMapper;

    @Autowired
    private QiNiuService qiNiuService;

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
            result.setErrcd("L102");
            result.setErrtx("帐号或密码错误！");
            return result;
        }
        result.setOkay(false);
        result.setErrcd("L101");
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

    @Override
    public UserInfoSummaryDTO getUserInfo(int id) {
        UserInfoSummaryDTO userInfoSummaryDTO = userMapper.getUserInfo(id);
        userInfoSummaryDTO.setPhotoUrl(photoService.getUrl(userInfoSummaryDTO.getPhotoId()));
        return userInfoSummaryDTO;
    }

    @Override
    public void register(RegisterDTO registerDTO) {
        if(registerDTO == null || StringUtils.isEmpty(registerDTO.getrAccount())
                || StringUtils.isEmpty(registerDTO.getrNickname()) || StringUtils.isEmpty(registerDTO.getrPassword())
                || StringUtils.isEmpty(registerDTO.getrEmail())){
            throw new ServiceException("lack of main info!");
        }

        if(!registerDTO.getrPassword().equals(registerDTO.getrRetypePassword())){
            throw new ServiceException("two passwords is different!");
        }

        // 设置用户角色
        // fixme 暂时注册用户只能使用article模块
        registerDTO.setRoleId("4");

        userMapper.register(registerDTO);
    }

    @Override
    public void setting(UserInfoSummaryDTO userInfoSummaryDTO) {
        if(userInfoSummaryDTO == null || StringUtils.isEmpty(userInfoSummaryDTO.getUserId())){
            throw new ServiceException("lack of main info!");
        }
        if(!StringUtils.isEmpty(userInfoSummaryDTO.getPassword())){
            if(!userInfoSummaryDTO.getPassword().equals(userInfoSummaryDTO.getRetypePassword())){
                throw new ServiceException("two passwords is different!");
            }
        }
        userMapper.setting(userInfoSummaryDTO);
        String path = qiNiuService.simpleUrl(Constants.QINIUDOMAIN_PHOTO, userInfoSummaryDTO.getPhotoUrl());
        if(!StringUtils.isEmpty(userInfoSummaryDTO.getPhotoUrl()) || StringUtils.isEmpty(path)){
            int photoId = photoMapper.getIdByPath(userInfoSummaryDTO.getUserId(), path);
            userMapper.changePhoto(userInfoSummaryDTO.getUserId(), photoId);
        }
    }
}
