package controller;

import entity.User;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by scott on 2017/3/15.
 */
public abstract class BaseController {
    public User getCurrentUser(HttpServletRequest request){
        return null;
    }
}
