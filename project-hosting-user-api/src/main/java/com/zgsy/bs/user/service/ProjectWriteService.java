package com.zgsy.bs.user.service;


import com.zgsy.bs.user.model.Project;

public interface ProjectWriteService {


    public Boolean update(Project project);
    public Boolean create(Project project);

}