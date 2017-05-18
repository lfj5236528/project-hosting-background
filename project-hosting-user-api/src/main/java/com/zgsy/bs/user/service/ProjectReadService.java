package com.zgsy.bs.user.service;

import com.zgsy.bs.user.model.Project;

import java.util.List;

public interface ProjectReadService {

    public List<Project> findByCreaterId(Long createrId);
    public Project findById(Long id);
    public List<Project> findAll();
}
