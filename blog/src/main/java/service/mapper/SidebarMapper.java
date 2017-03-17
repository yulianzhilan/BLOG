package service.mapper;

import dto.system.SidebarDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by scott on 2017/3/17.
 */
@Component("sidebarMapper")
public interface SidebarMapper {
    List<SidebarDTO> getSidebar(@Param("roleId")int roleId);
}
