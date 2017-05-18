package com.zgsy.bs.user.dto;

import com.zgsy.bs.user.model.BasicUser;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by liangfujie on 16/12/20
 */
@Data
public class TaskUser implements Serializable{
    private static final long serialVersionUID = 3730090606930447776L;

    private List<BasicUser> users;

    private List<BasicUser> canAddUsers;

}
