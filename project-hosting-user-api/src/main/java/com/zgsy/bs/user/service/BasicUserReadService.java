package com.zgsy.bs.user.service;

import com.zgsy.bs.user.model.BasicUser;

import java.util.List;

/**
 * Created by liangfujie on 16/12/19
 */
public interface BasicUserReadService {

    public BasicUser findBasicUserByMobile(String mobile);

    public BasicUser findBasicUserById(Long userId);

    public List<BasicUser> findAll();
}
