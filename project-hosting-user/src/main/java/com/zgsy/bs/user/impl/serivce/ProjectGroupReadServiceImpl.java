package com.zgsy.bs.user.impl.serivce;

import com.alibaba.dubbo.config.annotation.Service;
import com.zgsy.bs.user.impl.dao.ProjectGroupDao;
import com.zgsy.bs.user.model.ProjectGroup;
import com.zgsy.bs.user.service.ProjectGroupReadService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by liangfujie on 16/12/20
 */
@Service(version = "1.0.0")
public class ProjectGroupReadServiceImpl implements ProjectGroupReadService {

    @Autowired
    private ProjectGroupDao projectGroupDao;

    @Override
    public ProjectGroup findById(Long id) {
        return projectGroupDao.findById(id);
    }

    @Override
    public List<ProjectGroup> findChildProjectGroup(Long groupId) {
        return projectGroupDao.findChildProjectGroup(groupId);
    }

    @Override
    public List<ProjectGroup> findAll() {
        return projectGroupDao.findAll();
    }


}
