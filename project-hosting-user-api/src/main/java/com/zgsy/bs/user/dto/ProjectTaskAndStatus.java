package com.zgsy.bs.user.dto;

import com.zgsy.bs.user.model.ProjectTask;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by Java on 2017/5/12
 */
@Data
public class ProjectTaskAndStatus implements Serializable{


    private static final long serialVersionUID = -1874179792495058388L;


    private ProjectTask projectTask;

    private String statusDesc;
}
