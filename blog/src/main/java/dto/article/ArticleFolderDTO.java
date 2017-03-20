package dto.article;

import java.util.List;

/**
 * Created by scott on 2017/3/20.
 */
public class ArticleFolderDTO {
    private String name;

    private List<ArticleDTO> articleDTOs;

    private String attribute;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ArticleDTO> getArticleDTOs() {
        return articleDTOs;
    }

    public void setArticleDTOs(List<ArticleDTO> articleDTOs) {
        this.articleDTOs = articleDTOs;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }
}
