package com.zgsy.bs.user.impl.serivce;

import com.alibaba.dubbo.config.annotation.Service;
import com.zgsy.bs.user.impl.dao.ProjectDao;
import com.zgsy.bs.user.model.Project;
import com.zgsy.bs.user.service.ProjectWriteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;


@Slf4j
@Service(version = "1.0.0")
public class ProjectWriteServiceImpl implements ProjectWriteService {
    @Autowired
    private ProjectDao projectDao;


    @Override
    public Boolean update(Project project) {
        return projectDao.update(project);
    }
}
