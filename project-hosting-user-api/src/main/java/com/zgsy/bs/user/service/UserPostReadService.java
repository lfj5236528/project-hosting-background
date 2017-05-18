package com.zgsy.bs.user.service;

import com.zgsy.bs.user.model.UserPost;

import java.util.List;

/**
 * Created by Java on 2017/5/14.
 */
public interface UserPostReadService {

    public UserPost findById(Long id);

    public List<UserPost> findAll();
}
