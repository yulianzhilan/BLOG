package service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import dto.OrderablePaginationDTO;
import dto.PaginationResultDTO;
import dto.article.ArticleDTO;
import dto.article.ArticleFolderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.ArticleService;
import service.mapper.ArticleMapper;
import util.AssembleUtil;
import util.Constants;

import java.util.List;

/**
 * Created by scott on 2017/3/20.
 */
@Service("articleService")
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public List<ArticleFolderDTO> getDefaultArticleFolders(int userId) {
        List<ArticleDTO> result = articleMapper.getSimpleArticles(0, userId);
        if(result == null){
            return  null;
        }

        return AssembleUtil.assmbleArticleFolderDTOs(AssembleUtil.assmbleArticleMaps(result, Constants.ATTRIBUTE_FOLDER), Constants.ATTRIBUTE_FOLDER);
    }

    @Override
    public List<ArticleFolderDTO> getArticleFolders(String flag , int userId) {
        List<ArticleDTO> result = articleMapper.getSimpleArticles(0, userId);
        if(result == null){
            return null;
        }
        return AssembleUtil.assmbleArticleFolderDTOs(AssembleUtil.assmbleArticleMaps(result, flag), flag);
    }

    @Override
    public ArticleFolderDTO getArticleFoldersByName(String attribute, int userId, String name) {
        // fixme 这里有一个问题，如果该字段的值为null，怎么获取该列表？mybatis查询
        // 现在采用非空解决
        String folder = null;
        String location = null;
        String person = null;
        if(Constants.ATTRIBUTE_PERSON.equals(attribute)){
            person = name;
        } else if(Constants.ATTRIBUTE_FOLDER.equals(attribute)){
            folder = name;
        } else if(Constants.ATTRIBUTE_LOCATION.equals(attribute)){
            location = name;
        } else {
            return null;
        }
        List<ArticleDTO> result = articleMapper.getSimpleArticlesByName(0,userId,folder,location,person);
        return AssembleUtil.assembleArticleFolderDTO(result, name, attribute);
    }

    @Override
    public boolean deleteById(int id, int userId) {
        return articleMapper.deleteById(id, userId) == 1;
    }

    @Override
    public ArticleDTO preview(int isPrivate, int userId, int id) {
        return articleMapper.getArticle(isPrivate,userId,id);
    }

    @Override
    public PaginationResultDTO<ArticleDTO> getArticles(OrderablePaginationDTO op, int isPrivate, int userId) {
        Page<ArticleDTO> page = PageHelper.startPage(op.getPage(), op.getSize()).doSelectPage(() -> articleMapper.getArticles(isPrivate, userId));
        return new PaginationResultDTO<>(op, page.getResult());
    }

    @Override
    public void saveArticle(ArticleDTO articleDTO, int userId) {
        articleDTO.setUserId(userId);
        articleMapper.saveArticle(articleDTO);
    }

    @Override
    public void editArticle(ArticleDTO articleDTO) {
        articleMapper.editArticle(articleDTO);
    }
}
