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
import java.util.Map;

/**
 * Created by liangfujie on 16/12/19
 */
@Data
public class BasicUser implements Serializable {
    private static final long serialVersionUID = -5668700592382256186L;

    public static ObjectMapper objectMapper = new ObjectMapper();

    private Long id;

    private String name;

    private String email;

    private String mobile;

    private String password;

    private Integer status;

    private String groupIds;

    @JsonIgnore
    private List<String> groupIdsList;

    private String roleJson;

    @JsonIgnore
    private List<String> roleJsonList;

    private String extraJson;

    @JsonIgnore
    private Map<String, String> extras;

    private Date createdAt;

    private Date updatedAt;


    public void setGroupIds(String groupIds) {
        this.groupIds = groupIds;
        if (Arguments.isNull(groupIds)) {
            groupIdsList = null;
        } else {
            try {
                groupIdsList = objectMapper.readValue(groupIds, new TypeReference<List<String>>() {
                });

            } catch (IOException e) {
                //ignore exception
            }
        }
    }

    public void setGroupIdsList(List<String> groupIdsList) {
        this.groupIdsList = groupIdsList;
        if (Arguments.isNull(groupIdsList) || groupIdsList.size() == 0) {
            this.groupIds = null;
        } else {

            try {
                this.groupIds = objectMapper.writeValueAsString(groupIdsList);
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


