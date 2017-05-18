package com.zgsy.bs.user.impl.serivce;

import com.alibaba.dubbo.config.annotation.Service;
import com.zgsy.bs.user.impl.dao.UserPostDao;
import com.zgsy.bs.user.model.UserPost;
import com.zgsy.bs.user.service.UserPostReadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by Java on 2017/5/14.
 */
@Slf4j
@Service(version = "1.0.0")
public class UserPostReadServiceImpl implements UserPostReadService {
    @Autowired
    private UserPostDao userPostDao;

    @Override
    public UserPost findById(Long id) {
        return userPostDao.findById(id);
    }

    @Override
    public List<UserPost> findAll() {
        return userPostDao.findAll();
    }
}
