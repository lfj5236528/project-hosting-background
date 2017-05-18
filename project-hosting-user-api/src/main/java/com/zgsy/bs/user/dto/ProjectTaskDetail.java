package com.zgsy.bs.user.dto;

import com.zgsy.bs.user.model.ProjectTask;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Java on 2017/5/15.
 */
@Data
public class ProjectTaskDetail implements Serializable{
    private static final long serialVersionUID = -1408120801367739844L;

    private ProjectTask projectTask ;

    private String projectName;

    private List<String> userNames;

    private String status ;

}
