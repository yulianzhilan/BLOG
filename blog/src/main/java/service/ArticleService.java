package service;

import com.infoccsp.framework.core.pagination.OrderablePaginationDTO;
import com.infoccsp.framework.core.pagination.PaginationResultDTO;
import dto.article.ArticleDTO;
import dto.article.ArticleFolderDTO;
import dto.article.ArticleSummaryDTO;

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
    ArticleDTO preview(int isPrivate, int userId, int id);

    /**
     * 获取所有文章
     * @param isPrivate
     * @param userId
     * @return
     */
    PaginationResultDTO<ArticleDTO> getArticles(OrderablePaginationDTO op, int isPrivate, int userId);

    /**
     * 获取所有文章
     * @param isPrivate
     * @param userId
     * @return
     */
    List<ArticleDTO> getArticles(int isPrivate, int userId);

    /**
     * 保存文章
     * @param articleDTO
     * @param userId
     */
    int saveArticle(ArticleDTO articleDTO, int userId);

    /**
     * 编辑文章
     * @param articleDTO
     */
    void editArticle(ArticleDTO articleDTO);

    /**
     * 获取文章
     * 暂时用于加载home页
     * @param isHome
     * @return
     */
    PaginationResultDTO<ArticleSummaryDTO> getArticles(OrderablePaginationDTO op, boolean isHome, int userId);

    /**
     * 文章id获取文章
     * @param id
     * @return
     */
    ArticleSummaryDTO getArticle(int id);
}
