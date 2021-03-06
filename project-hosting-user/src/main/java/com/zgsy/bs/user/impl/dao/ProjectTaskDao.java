package com.zgsy.bs.user.impl.dao;

import com.zgsy.bs.common.dao.MyBatisDao;
import com.zgsy.bs.user.model.ProjectTask;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Code generated by CodeGen
 * Desc: 项目任务表Dao类
 * Date: 2017-01-14
 */
@Repository
public class ProjectTaskDao extends MyBatisDao<ProjectTask> {

    public ProjectTaskDao() {
        super("project.task");
    }

    public List<ProjectTask> findByUserId(Long userId){

        return getSqlSession().selectList(getSqlId("findByUserId"),userId);
    }
    public List<ProjectTask> findByGroupId(Long groupId){

        return getSqlSession().selectList(getSqlId("findByGroupId"),groupId);
    }

    public List<ProjectTask> findChildTasks(Long parentId){

        return getSqlSession().selectList(getSqlId("findChildTasks"),parentId);
    }

    public List<ProjectTask> findProjectModels(Long projectId){

        return getSqlSession().selectList(getSqlId("findProjectModels"),projectId);
    }


    public List<ProjectTask> findChildTaskByParentIdAndStatus(Long parentId,Integer status){
        Map<String,String> map = new HashMap<String,String>();
        map.put("parentId",String.valueOf(parentId));
        map.put("status",String.valueOf(status));

        return getSqlSession().selectList(getSqlId("findChildTaskByParentIdAndStatus"),map);
    }


}
