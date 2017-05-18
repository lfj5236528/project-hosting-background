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
public class OperatorRole implements Serializable {
    private static final long serialVersionUID = 6679928858061361535L;

    public static ObjectMapper objectMapper = new ObjectMapper();

    private Long id;

    private String name;

    private String desc;

    private Integer status;

    private String allowJson;
    @JsonIgnore
    private List<String> allowJsonList;

    private String extraJson;
    @JsonIgnore
    private Map<String, String> extras;

    private Date createdAt;

    private Date updatedAt;


    public void setAllowJson(String allowJson) {
        this.allowJson = allowJson;
        if (Arguments.isNull(allowJson)) {
            allowJsonList = null;
        } else {
            try {
                allowJsonList = objectMapper.readValue(allowJson, new TypeReference<List<String>>() {
                });

            } catch (IOException e) {
                //ignore exception
            }
        }
    }

    public void setAllowJsonList(List<String> allowJsonList) {
        this.allowJsonList = allowJsonList;
        if (Arguments.isNull(allowJsonList) || allowJsonList.size() == 0) {
            this.allowJson = null;
        } else {

            try {
                this.allowJson = objectMapper.writeValueAsString(allowJsonList);
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