package com.zgsy.bs.user.impl.dao;

import com.zgsy.bs.common.dao.MyBatisDao;
import com.zgsy.bs.user.model.Project;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class ProjectDao extends MyBatisDao<Project> {

    public ProjectDao() {super("project.project");}


    public List<Project> findByCreaterId(Long createrId){

       return this.getSqlSession().selectList(getSqlId("findByCreaterId"),createrId);
    }
}
