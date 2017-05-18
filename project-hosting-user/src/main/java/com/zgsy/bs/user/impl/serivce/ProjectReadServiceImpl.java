package com.zgsy.bs.user.impl.serivce;


import com.alibaba.dubbo.config.annotation.Service;
import com.zgsy.bs.user.impl.dao.ProjectDao;
import com.zgsy.bs.user.model.Project;
import com.zgsy.bs.user.service.ProjectReadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


@Slf4j
@Service(version="1.0.0")
public class ProjectReadServiceImpl implements ProjectReadService {
    @Autowired
    private ProjectDao projectDao;

    @Override
    public List<Project> findByCreaterId(Long createrId) {
        return projectDao.findByCreaterId(createrId);
    }

    @Override
    public Project findById(Long id) {
        return  projectDao.findById(id);
    }

    @Override
    public List<Project> findAll() {
        return projectDao.findAll();
    }
}
