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
public class ProjectGroup implements Serializable{
    private static final long serialVersionUID = -6284840581074013660L;

    public static ObjectMapper objectMapper = new ObjectMapper();


    private Long id;

    private Long parentId;

    private String name;

    private String descMessage;

    private Long leaderId;

    private String membersIds;

    @JsonIgnore
    private List<String> membersIdsList;


    private String extraJson;

    @JsonIgnore
    private Map<String, String> extras;
    private Date createdAt;
    private Date updatedAt;


    public void setMembersIds(String membersIds) {
        this.membersIds = membersIds;
        if (Arguments.isNull(membersIds)) {
            membersIdsList = null;
        } else {
            try {
                membersIdsList = objectMapper.readValue(membersIds, new TypeReference<List<String>>() {
                });

            } catch (IOException e) {
                //ignore exception
            }
        }
    }

    public void setMembersIdsList(List<String> membersIdsList) {
        this.membersIdsList = membersIdsList;
        if (Arguments.isNull(membersIdsList) || membersIdsList.size() == 0) {
            this.membersIds = null;
        } else {

            try {
                this.membersIds = objectMapper.writeValueAsString(membersIdsList);
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
