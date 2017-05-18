package com.zgsy.bs.user.impl.manager;

import com.alibaba.dubbo.config.annotation.Service;
import com.zgsy.bs.user.impl.dao.BasicUserDao;
import com.zgsy.bs.user.impl.dao.ProjectGroupDao;
import com.zgsy.bs.user.model.BasicUser;
import com.zgsy.bs.user.model.ProjectGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by liangfujie on 16/12/20
 */
@Service(version = "1.0.0")
public class UserAndGroupManager {
    @Autowired
    private BasicUserDao basicUserDao;

    @Autowired
    private ProjectGroupDao projectGroupDao;


    @Transactional
    public void updateBasicUserAndProjectGroup(ProjectGroup projectGroup , BasicUser basicUser){
        projectGroupDao.update(projectGroup);
        basicUserDao.update(basicUser);
    }





}
