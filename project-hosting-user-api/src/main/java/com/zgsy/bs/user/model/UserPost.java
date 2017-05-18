package com.zgsy.bs.user.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zgsy.bs.common.utils.Arguments;
import com.zgsy.bs.user.dto.UserReply;
import lombok.Data;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Data
public class UserPost implements Serializable {
    private static final long serialVersionUID = -8546535031704448405L;
    public static ObjectMapper objectMapper = new ObjectMapper();

    private Long id;
    
    /**
     * 发布者ID
     */
    private Integer userId;
    
    /**
     * 发布者名称
     */
    private String userName;
    
    /**
     * 主题
     */
    private String title;
    
    /**
     * 内容
     */
    private String content;
    
    /**
     * 状态,1:发布,-1：删除
     */
    private Integer status;
    
    /**
     * 评论信息,json格式
     */
   private String replyInfo;

    private List<UserReply> replyInfoList;
    /**
     * 额外信息
     */
    private String extraJson;

    private Map<String,String> extras;


    /**
     * 创建时间
     */
    private Date createdAt;
    
    /**
     * 更新时间
     */
    private Date updatedAt;


    public  void setReplyInfo(String replyInfo){

        this.replyInfo = replyInfo;
        if (Arguments.isNull(replyInfo)) {
            this.replyInfoList = null;
        } else {
            try {
                replyInfoList = objectMapper.readValue(replyInfo, new TypeReference<List<UserReply>>() {
                });
            } catch (IOException e) {
                //ignore exception
            }
        }


    }

    public void setReplyInfoList(List<UserReply> replyInfoList){

        this.replyInfoList = replyInfoList;
        if (Arguments.isNull(replyInfoList) || replyInfoList.size() == 0) {
            this.replyInfo = null;
        } else {
            try {
                this.replyInfo = objectMapper.writeValueAsString(replyInfoList);
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
