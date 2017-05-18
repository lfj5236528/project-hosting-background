package com.zgsy.bs.user.service;

import com.zgsy.bs.user.model.UserPost;

/**
 * Created by Java on 2017/5/14.
 */
public interface UserPostWriteService {

    public Boolean createUserPost(UserPost userPost);

    public Boolean updateUserPost(UserPost userPost);

}
