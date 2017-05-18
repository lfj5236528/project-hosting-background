package com.zgsy.bs.user.service;

import com.zgsy.bs.user.model.ProjectTask;

public interface ProjectTaskWriteService {

    public Boolean createProjectTask(ProjectTask projectTask);
    public Boolean updateProjectTask(ProjectTask projectTask);
    public Boolean deleteProjectTask(Long taskId);


}