package com.zgsy.bs.user.service;

import com.zgsy.bs.user.model.ProjectTask;

import java.util.List;

public interface ProjectTaskReadService {
    public ProjectTask findById(Long taskId);

    public List<ProjectTask> findByUserId(Long userId);

    public List<ProjectTask> findByGroupId(Long groupId);

    public List<ProjectTask> findChildTasks(Long parentId);

    public List<ProjectTask> findProjectModels(Long projectId);

    public List<ProjectTask> findChildTaskByParentIdAndStatus(Long parentId,Integer status);

    public List<ProjectTask> findAll();




}
