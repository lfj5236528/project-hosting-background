package com.zgsy.bs.user.impl.serivce;

import com.alibaba.dubbo.config.annotation.Service;
import com.zgsy.bs.user.impl.dao.ProjectGroupDao;
import com.zgsy.bs.user.model.ProjectGroup;
import com.zgsy.bs.user.service.ProjectGroupWriteService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by liangfujie on 16/12/20
 */
@Service(version="1.0.0")
public class ProjectGroupWriteServiceImpl implements ProjectGroupWriteService{

    @Autowired
    private ProjectGroupDao projectGroupDao;

    @Override
    public Boolean createProjectGroup(ProjectGroup projectGroup) {
        return projectGroupDao.create(projectGroup);
    }

    @Override
    public Boolean updateProjectGroup(ProjectGroup projectGroup) {
        return projectGroupDao.update(projectGroup);
    }

    @Override
    public Boolean deleteProjectGroup(Long groupId) {
        return projectGroupDao.delete(groupId);
    }
}
