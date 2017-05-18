package com.zgsy.bs.user.service;

import com.zgsy.bs.user.model.ProjectGroup;

/**
 * Created by liangfujie on 16/12/20
 */
public interface ProjectGroupWriteService {

    public Boolean createProjectGroup(ProjectGroup projectGroup);

    public Boolean updateProjectGroup(ProjectGroup projectGroup);

    public Boolean deleteProjectGroup(Long groupId);

}
