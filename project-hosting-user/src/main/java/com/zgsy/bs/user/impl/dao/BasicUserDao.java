package com.zgsy.bs.user.impl.dao;

import com.zgsy.bs.common.dao.MyBatisDao;
import com.zgsy.bs.user.model.BasicUser;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by liangfujie on 16/12/19
 */
@Repository
public class BasicUserDao extends MyBatisDao<BasicUser>{
    public BasicUserDao() {
        super("basic.user");
    }

    public BasicUser findBasicUserByMobile(String mobile){
        return this.getSqlSession().selectOne(getSqlId("findByMobile"),mobile);
    }

    public List<BasicUser> findAll(){
        return this.getSqlSession().selectList(getSqlId("findAll"));

    }

}
