package com.zgsy.bs.user.dto;

import com.zgsy.bs.user.model.ProjectGroup;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Java on 2017/5/15
 */
@Data
public class ProjectGroupDetail implements Serializable{
    private static final long serialVersionUID = -3207402762109819695L;

    private ProjectGroup projectGroup ;

    private List<String> memberNames;

    private List<String> projectNames;
}
