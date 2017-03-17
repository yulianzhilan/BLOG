package util;

import dto.system.UserSummaryDTO;
import entity.User;

/**
 * Created by scott on 2017/3/17.
 */
public class AssembleUtil {
    public static UserSummaryDTO assembleUserSummaryDTO(User source){
        if(source == null){
            return null;
        }
        UserSummaryDTO user = new UserSummaryDTO();
        user.setId(source.getId());
        user.setNickName(source.getNickName());
        user.setAccount(source.getAccount());
        user.setPassword(source.getPassword());
        return user;
    }
}
