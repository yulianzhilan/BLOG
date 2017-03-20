package service.mapper;

import dto.article.ArticleDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by scott on 2017/3/20.
 */
@Component("articleMapper")
public interface ArticleMapper {
    //随机查询
    List<ArticleDTO> searchQ(@Param("q") String q, @Param("isPrivate") boolean isPrivate, @Param("userId")int userId);
    List<ArticleDTO> getArticles(@Param("isPrivate") boolean isPrivate, @Param("userId")int userId);
    List<ArticleDTO> getSimpleArticles(@Param("isPrivate") boolean isPrivate, @Param("userId")int userId);
    List<ArticleDTO> getSimpleArticlesByName(@Param("isPrivate")boolean isPrivate, @Param("userId")int userId,@Param("folder")String folder,@Param("location")String location,@Param("person")String person);
}
