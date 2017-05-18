package com.zgsy.bs.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zgsy.bs.common.utils.Arguments;
import lombok.Data;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class Project implements Serializable {
    public static ObjectMapper objectMapper = new ObjectMapper();

    private static final long serialVersionUID = -5505572401173156822L;

    private Long id;
    
    /**
     * 项目名称
     */
    private String name;
    
    /**
     * 项目描述
     */
    private String descMessage;
    
    /**
     * 项目创建ID
     */
    private Long createrId;

    @JsonIgnore
    private List<String> groupsIdsList;
    
    /**
     * 团队ID,数组形式
     */
    private String groupsIds;
    
    /**
     * 项目额外信息,json字符串
     */
    private String extraJson;
    
    private Date createdAt;
    
    private Date updatedAt;



    public void setGroupsIds(String groupsIds) {
        this.groupsIds = groupsIds;
        if (Arguments.isNull(groupsIds)) {
            groupsIdsList = null;
        } else {
            try {
                groupsIdsList = objectMapper.readValue(groupsIds, new TypeReference<List<String>>() {
                });

            } catch (IOException e) {
                //ignore exception
            }
        }
    }

    public void setGroupsIdsList(List<String> groupsIdsList) {
        this.groupsIdsList = groupsIdsList;
        if (Arguments.isNull(groupsIdsList) || groupsIdsList.size() == 0) {
            this.groupsIds = null;
        } else {

            try {
                this.groupsIds = objectMapper.writeValueAsString(groupsIdsList);
            } catch (JsonProcessingException e) {
                //ignore exception
            }

        }

    }





}
