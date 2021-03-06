package service.mapper;

import dto.article.ArticleDTO;
import dto.article.ArticleSummaryDTO;
import entity.article.Article;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by scott on 2017/3/20.
 */
@Component("articleMapper")
public interface ArticleMapper {
    //随机查询
    List<ArticleDTO> searchQ(@Param("q") String q, @Param("isPrivate") int isPrivate, @Param("userId")int userId);
    //获取用户的所有文章（详细）
    ArticleDTO getArticle(@Param("isPrivate") int isPrivate, @Param("userId")int userId, @Param("id")int id);
    //获取用户的所有文章（概述）
    List<ArticleDTO> getSimpleArticles(@Param("isPrivate") int isPrivate, @Param("userId")int userId);
    //通过分类属性和分类属性值获取文章
    List<ArticleDTO> getSimpleArticlesByName(@Param("isPrivate")int isPrivate, @Param("userId")int userId,@Param("folder")String folder,@Param("location")String location,@Param("person")String person);
    //删除文章(id，userId)
    int deleteById(@Param("id") int id, @Param("userId") int userId);

    List<ArticleDTO> getArticles(@Param("userId")int userId, @Param("isPrivate")int isPrivate);
    //保存文章
    int saveArticle(ArticleDTO articleDTO);
    //编辑文章
    void editArticle(ArticleDTO articleDTO);
    List<ArticleSummaryDTO> getArticlesForHome(@Param("userId") int userId, @Param("id") int id);
}
