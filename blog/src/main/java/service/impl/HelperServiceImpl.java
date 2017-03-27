package service.impl;

import dto.DataBaseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.HelperService;
import service.mapper.DataBaseMapper;

import java.util.List;

/**
 * Created by scott on 2017/3/27.
 */
@Service("helpService")
public class HelperServiceImpl implements HelperService {

    @Autowired
    private DataBaseMapper dataBaseMapper;

    @Override
    public List<DataBaseDTO> getInfoFromDataBase(int id, String table, String column, String query) {
        return dataBaseMapper.getInfoFromDataBase(id,table,column,query);
    }

}
