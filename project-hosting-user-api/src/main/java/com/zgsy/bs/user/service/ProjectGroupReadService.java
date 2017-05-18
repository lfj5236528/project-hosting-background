package com.zgsy.bs.user.service;

import com.zgsy.bs.user.model.ProjectGroup;

import java.util.List;

/**
 * Created by liangfujie on 16/12/20
 */
public interface ProjectGroupReadService {
    public ProjectGroup findById(Long id);

    public List<ProjectGroup> findChildProjectGroup(Long groupId);

    public List<ProjectGroup> findAll();

}
