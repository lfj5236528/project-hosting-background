package com.zgsy.bs.user.impl.serivce;


import com.alibaba.dubbo.config.annotation.Service;
import com.zgsy.bs.user.impl.dao.ProjectTaskDao;
import com.zgsy.bs.user.model.ProjectTask;
import com.zgsy.bs.user.service.ProjectTaskWriteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;



@Slf4j
@Service(version="1.0.0")
public class ProjectTaskWriteServiceImpl implements ProjectTaskWriteService {
    @Autowired
    private  ProjectTaskDao projectTaskDao;


    @Override
    public Boolean createProjectTask(ProjectTask projectTask) {
        return projectTaskDao.create(projectTask);
    }

    @Override
    public Boolean updateProjectTask(ProjectTask projectTask) {
        return projectTaskDao.update(projectTask);
    }

    @Override
    public Boolean deleteProjectTask(Long taskId) {
        return projectTaskDao.delete(taskId);
    }


}
