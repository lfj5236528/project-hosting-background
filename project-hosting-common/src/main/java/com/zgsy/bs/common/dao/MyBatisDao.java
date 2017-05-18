package com.zgsy.bs.common.dao;


import com.zgsy.bs.common.model.Paging;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * Created by liangfujie on 16/12/19
 */

/**
 * 所有继承此类的子类需要调用父类构造函数,传如指定的nameSpace.
 *
 * @param <T>
 */

public abstract class MyBatisDao<T> {
    @Autowired
    private SqlSession sqlSession;

    private String nameSpace;

    public MyBatisDao(String nameSpace) {
        this.nameSpace = nameSpace;
    }

    public String getSqlId(String id) {
        return nameSpace + "." + id;
    }

    public SqlSession getSqlSession() {
        return sqlSession;
    }

    public Boolean create(T t) {
        return sqlSession.insert(this.getSqlId("create"), t) == 1;
    }

    public Boolean update(T t) {
        return sqlSession.update(this.getSqlId("update"), t) == 1;
    }

    public Boolean delete(Long id) {

        return sqlSession.delete(this.getSqlId("delete"), id) == 1;
    }

    public T findById(Long id) {
        return sqlSession.selectOne(this.getSqlId("findById"), id);

    }

    public List<T> findAll() {
        return sqlSession.selectList(this.getSqlId("findAll"));

    }


    public Paging<T> paging(Integer pageNo, Integer pageSize, Map<String, String> criteria) {
        Paging<T> paging = new Paging<T>();
        Long total = counts(criteria);
        List<T> lists = sqlSession.selectList(this.getSqlId("paging"), criteria);
        if (total == null || total == 0 || lists.isEmpty()) {
            paging.setEmpty(Boolean.TRUE);
        } else {
            paging.setEmpty(Boolean.FALSE);
        }
        paging.setTotal(total);
        paging.setData(lists);
        return paging;
    }

    public Long counts(Map<String, String> criteria) {
        return sqlSession.selectOne(this.getSqlId("counts"), criteria);
    }



}
