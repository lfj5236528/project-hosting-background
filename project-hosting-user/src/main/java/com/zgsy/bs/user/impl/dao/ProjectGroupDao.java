package com.zgsy.bs.user.impl.dao;

import com.zgsy.bs.common.dao.MyBatisDao;
import com.zgsy.bs.user.model.ProjectGroup;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by liangfujie on 16/12/20
 */
@Repository
public class ProjectGroupDao extends MyBatisDao<ProjectGroup> {
    public ProjectGroupDao() {
        super("project.group");
    }


    public List<ProjectGroup> findChildProjectGroup(Long parentId) {

        return getSqlSession().selectList(getSqlId("findChildProjectGroup"), parentId);

    }

}
