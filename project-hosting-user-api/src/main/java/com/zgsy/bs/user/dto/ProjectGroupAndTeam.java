package com.zgsy.bs.user.dto;

import com.zgsy.bs.user.model.ProjectGroup;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by liangfujie on 16/12/20
 */
@Data
public class ProjectGroupAndTeam implements Serializable{

    private static final long serialVersionUID = -3370976767810485186L;

    private ProjectGroup rootProjectGroup;

    private List<ProjectGroup> projectGroups;

    private Integer total;

}
