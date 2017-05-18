package com.zgsy.bs.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zgsy.bs.common.utils.Arguments;
import lombok.Data;

import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by liangfujie on 16/12/19
 */
@Data
public class ProjectTask implements Serializable {

    public static ObjectMapper objectMapper = new ObjectMapper();

    private static final long serialVersionUID = 7033136680465038268L;

    private Long id;
    
    /**
     * 任务名称
     */
    private String name;
    
    /**
     * 任务描述
     */
    private String descMessage;



    /**
     * 状态
     */
    private Integer status;

    /**
     * 所属项目Id
     */
    private Long projectId;
    
    /**
     * 所属负责人,注意这里ID是多个
     */
    private String userId;

    @JsonIgnore
    private List<String> userIdsList;


    private Long parentId;
    
    /**
     * 任务额外信息,json字符串
     */
    private String extraJson;

    @JsonIgnore
    private Map<String,String> extras;

    /**
     * 任务开始时间，注意创建不一定开始
     */
    private Date createdAt;
    
    private Date updatedAt;
    /**
     * 任务截至时间
     */
    private Date endedAt;

    private String endedDate;

    public void setEndedDate(String endedDate){
        if(Arguments.isNull(endedDate)){
            this.endedDate=endedDate;
        }else {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                endedAt=sdf.parse(endedDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }


        }
    }

    public void setUserId(String userId) {
        this.userId = userId;
        if (Arguments.isNull(userId)) {
            userIdsList = null;
        } else {
            try {
                userIdsList = objectMapper.readValue(userId, new TypeReference<List<String>>() {
                });

            } catch (IOException e) {
                //ignore exception
            }
        }
    }

    public void setUserIdsList(List<String> userIdsList) {
        this.userIdsList = userIdsList;
        if (Arguments.isNull(userIdsList) || userIdsList.size() == 0) {
            this.userId = null;
        } else {

            try {
                this.userId = objectMapper.writeValueAsString(userIdsList);
            } catch (JsonProcessingException e) {
                //ignore exception
            }

        }

    }







    public void setExtraJson(String extraJson) {
        this.extraJson = extraJson;
        if (Arguments.isNull(extraJson)) {
            this.extras = null;
        } else {
            try {
                this.extras = objectMapper.readValue(extraJson, new TypeReference<Map<String, String>>() {
                });
            } catch (IOException e) {
                //ignore exception
            }
        }
    }


    public void setExtras(Map<String, String> extra) {
        this.extras = extra;
        if (Arguments.isNull(extra)) {
            this.extraJson = null;
        } else {
            try {
                this.extraJson = objectMapper.writeValueAsString(extra);
            } catch (JsonProcessingException e) {
                //ignore exception
            }

        }


    }

}
