package com.zgsy.bs.user.dto;

import com.zgsy.bs.user.model.Project;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Java on 2017/5/15
 */
@Data
public class ProjectAndGroupName implements Serializable{
    private static final long serialVersionUID = -5099707902356594857L;

    private Project project;

    private List<String> groupNames;
}
