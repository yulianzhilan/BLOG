package service.impl;

import framework.core.utils.StringUtils;
import framework.service.ServiceException;
import org.springframework.stereotype.Service;
import service.CommentService;
import util.Constants;

/**
 * Created by scott on 2017/4/25.
 */
@Service("commentService")
public class CommentServiceImpl implements CommentService {
    @Override
    public void like(int id, String type) {
        if(StringUtils.isEmpty(type) || id == 0){
            throw new ServiceException("非空约定！");
        }
        if(Constants.ARTICLE.equals(type)){

        } else if(Constants.IMAGE.equals(type)){

        } else if(Constants.TIMESTAMP.equals(type)){

        }
    }

    @Override
    public void dislike(int id, String type) {

    }
}
