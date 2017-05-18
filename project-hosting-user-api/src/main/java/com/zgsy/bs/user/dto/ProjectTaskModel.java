package com.zgsy.bs.user.dto;

import com.zgsy.bs.user.model.ProjectTask;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by liangfujie on 16/12/20
 */
@Data
public class ProjectTaskModel implements Serializable{
    private static final long serialVersionUID = -7156931117850964530L;

    private String name;

    private String descMessage;

    private String createrName;

    private Long createrId;

    private Date createdDate;

    private Long projectId;

    private List<ProjectTask> projectTasks;

}
