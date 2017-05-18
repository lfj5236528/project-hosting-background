package com.zgsy.bs.user.impl.serivce;

import com.alibaba.dubbo.config.annotation.Service;
import com.zgsy.bs.user.impl.dao.BasicUserDao;
import com.zgsy.bs.user.model.BasicUser;
import com.zgsy.bs.user.service.BasicUserReadService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by liangfujie on 16/12/19
 */
@Service(version = "1.0.0")
public class BasicUserReadServiceImpl implements BasicUserReadService {

    @Autowired
    private BasicUserDao basicUserDao;

    @Override
    public BasicUser findBasicUserByMobile(String mobile) {
        return basicUserDao.findBasicUserByMobile(mobile);
    }

    @Override
    public BasicUser findBasicUserById(Long userId) {
        return basicUserDao.findById(userId);
    }

    @Override
    public List<BasicUser> findAll() {
        return basicUserDao.findAll();
    }
}
