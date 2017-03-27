package service.mapper;

import dto.DataBaseDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by scott on 2017/3/27.
 */
@Component("databaseMapper")
public interface DataBaseMapper {

    List<DataBaseDTO> getInfoFromDataBase(@Param("id") int id, @Param("table") String table,@Param("column") String column,@Param("query") String query);

}
