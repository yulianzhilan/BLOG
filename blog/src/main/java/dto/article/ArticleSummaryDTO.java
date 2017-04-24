package dto.article;

import java.util.Date;

/**
 * 此类用于展示文章
 * Created by scott on 2017/4/24.
 */
public class ArticleSummaryDTO {
    /**
     * PK
     */
    private int id;

    /**
     * 文章名称
     */
    private String name;

    /**
     * 概括
     */
    private String description;

    /**
     * 作者id
     */
    private int userId;

    /**
     * 最后更新时间
     */
    private Date modify;

    /**
     * 作者昵称
     */
    private String nickName;

    /**
     * 评论数
     */
    private int comments;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getModify() {
        return modify;
    }

    public void setModify(Date modify) {
        this.modify = modify;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }
}
