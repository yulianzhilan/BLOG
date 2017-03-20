package service;

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
}
