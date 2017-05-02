package util;

import dto.article.ArticleDTO;
import dto.article.ArticleFolderDTO;
import dto.system.UserInfoSummaryDTO;
import dto.system.UserSummaryDTO;
import entity.system.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    /**
     *
     * @param source
     * @param category 分类名称
     * @param attribute 分类属性
     * @return
     */
    public static ArticleFolderDTO assembleArticleFolderDTO(List<ArticleDTO> source, String category, String attribute){
        if(source == null){
            return null;
        }
        ArticleFolderDTO result = new ArticleFolderDTO();
        result.setName(category);
        result.setArticleDTOs(source);
        result.setAttribute(attribute);
        return result;
    }

    public static List<ArticleFolderDTO> assembleArticleFolderDTOs(Map<String,List<ArticleDTO>> source, String attribute){
        if(source == null){
            return null;
        }
        List<ArticleFolderDTO> result = new ArrayList<>();
        for(String category : source.keySet()){
            result.add(assembleArticleFolderDTO(source.get(category), category, attribute));
        }
        return result;
    }

    public static Map<String, List<ArticleDTO>> assembleArticleMaps(List<ArticleDTO> result,String attribute) {
        Map<String, List<ArticleDTO>> map = new HashMap<>();
        if(Constants.ATTRIBUTE_FOLDER.equals(attribute)){
            for (int i = 0; i < result.size(); i++) {
                if (map.containsKey(result.get(i).getFolder())) {
                    map.get(result.get(i).getFolder()).add(result.get(i));
                } else {
                    List<ArticleDTO> list = new ArrayList<>();
                    list.add(result.get(i));
                    map.put(result.get(i).getFolder(), list);
                }
            }
        }else if(Constants.ATTRIBUTE_LOCATION.equals(attribute)){
            for (int i = 0; i < result.size(); i++) {
                if (map.containsKey(result.get(i).getLocation())) {
                    map.get(result.get(i).getLocation()).add(result.get(i));
                } else {
                    List<ArticleDTO> list = new ArrayList<>();
                    list.add(result.get(i));
                    map.put(result.get(i).getLocation(), list);
                }
            }
        }else if(Constants.ATTRIBUTE_PERSON.equals(attribute)){
            for (int i = 0; i < result.size(); i++) {
                if (map.containsKey(result.get(i).getPerson())) {
                    map.get(result.get(i).getPerson()).add(result.get(i));
                } else {
                    List<ArticleDTO> list = new ArrayList<>();
                    list.add(result.get(i));
                    map.put(result.get(i).getPerson(), list);
                }
            }
        }else{
            map = null;
        }
        return map;
    }
}
