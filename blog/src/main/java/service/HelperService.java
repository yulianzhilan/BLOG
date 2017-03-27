package service;

import dto.DataBaseDTO;

import java.util.List;

/**
 * Created by scott on 2017/3/27.
 */
public interface HelperService {
    List<DataBaseDTO> getInfoFromDataBase(int id, String table, String column, String query);


}
