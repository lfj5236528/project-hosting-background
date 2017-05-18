package com.zgsy.bs.user.impl.serivce;

import com.alibaba.dubbo.config.annotation.Service;
import com.zgsy.bs.user.impl.dao.BasicUserDao;
import com.zgsy.bs.user.model.BasicUser;
import com.zgsy.bs.user.service.BasicUserWriteService;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * Created by liangfujie on 16/12/19
 */
@Service(version = "1.0.0")
public class BasicUserWriteServiceImpl implements BasicUserWriteService{

    @Autowired
    private BasicUserDao basicUserDao;

    @Override
    public Boolean createBasicUser(BasicUser basicUser) {
        return basicUserDao.create(basicUser);
    }

    @Override
    public Boolean updateBasicUser(BasicUser basicUser) {
        return basicUserDao.update(basicUser);
    }
}
