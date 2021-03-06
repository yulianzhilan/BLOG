package service;

import dto.CallBackDTO;
import dto.system.RegisterDTO;
import dto.system.SidebarDTO;
import dto.system.UserInfoSummaryDTO;
import dto.system.UserSummaryDTO;
import entity.system.User;

import java.util.List;

/**
 * Created by scott on 2017/3/15.
 */
public interface ValidateService {
    UserSummaryDTO getUserSummaryDTO(String _user, String _token);

    User getUser(String _user, String _token);

    CallBackDTO validate(String _user, String _token);

    List<SidebarDTO> getSideBar(User user);

    User getUser(int userId);

    UserInfoSummaryDTO getUserInfo(int id);

    void register(RegisterDTO registerDTO);

    void setting(UserInfoSummaryDTO userInfoSummaryDTO);
}
