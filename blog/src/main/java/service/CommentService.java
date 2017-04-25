package service;

/**
 * Created by scott on 2017/4/25.
 */
public interface CommentService {
    /**
     * 根据文件id和文件类型添加like
     * @param id
     * @param type
     */
    void like(int id, String type);
    void dislike(int id, String type);

}
