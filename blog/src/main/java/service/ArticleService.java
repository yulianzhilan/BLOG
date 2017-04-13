package service;

import dto.article.ArticleDTO;
import dto.article.ArticleFolderDTO;

import java.util.List;

/**
 * Created by scott on 2017/3/20.
 */
public interface ArticleService {
    /**
     * 使用默认的分组属性获取 所有文章
     * @param usrId
     * @return
     */
    List<ArticleFolderDTO> getDefaultArticleFolders(int usrId);

    /**
     * 根据分类属性 查询 所有文章（非私有）
     * @param attribute
     * @param userId
     * @return
     */
    List<ArticleFolderDTO> getArticleFolders(String attribute, int userId);

    /**
     * 根据 分类属性 & 分类名称查询单组文章
     * @param attribute
     * @param userId
     * @param name
     * @return
     */
    ArticleFolderDTO getArticleFoldersByName(String attribute, int userId, String name);

    /**
     * 删除
     * @param id
     * @param userId
     */
    boolean deleteById(int id, int userId);

    /**
     * 预览
     * @param isPrivate
     * @param userId
     * @param id
     * @return
     */
    ArticleDTO preview(boolean isPrivate, int userId, int id);

    /**
     * 获取所有文章
     * @param isPrivate
     * @param userId
     * @return
     */
    List<ArticleDTO> getArticles(boolean isPrivate, int userId);
}
