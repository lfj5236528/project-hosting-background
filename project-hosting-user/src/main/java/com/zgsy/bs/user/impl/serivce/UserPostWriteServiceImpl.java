package com.zgsy.bs.user.impl.serivce;

import com.alibaba.dubbo.config.annotation.Service;
import com.zgsy.bs.user.impl.dao.UserPostDao;
import com.zgsy.bs.user.model.UserPost;
import com.zgsy.bs.user.service.UserPostWriteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Java on 2017/5/14.
 */
@Slf4j
@Service(version = "1.0.0")
public class UserPostWriteServiceImpl implements UserPostWriteService {
    @Autowired
    private UserPostDao userPostDao;

    @Override
    public Boolean createUserPost(UserPost userPost) {
        return userPostDao.create(userPost);
    }

    @Override
    public Boolean updateUserPost(UserPost userPost) {
        return userPostDao.update(userPost);
    }
}
