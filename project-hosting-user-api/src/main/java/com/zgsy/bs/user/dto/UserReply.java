package com.zgsy.bs.user.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Java on 2017/5/14
 */
@Data
public class UserReply implements Serializable {
    private static final long serialVersionUID = 6679938585246407037L;

    private Long userId;

    private String userName;

    private String content;

    private Date replayDate;

    private Integer number;//处于第几楼

}
