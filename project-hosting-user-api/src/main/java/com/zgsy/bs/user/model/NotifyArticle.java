package com.zgsy.bs.user.model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zgsy.bs.common.utils.Arguments;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created with terminus generator
 * Author:  liangfujie
 * Date: 2016-11-29 04:05:56
 */
@Data
public class NotifyArticle implements Serializable {

    private static final long serialVersionUID = -6748197760465329367L;

    public static ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 
     */

    private Long id;


    /**
     * 发布者ID
     */

    private Long userId;

    /**
     * 发布者名称
     */

    private String userName;

    /**
     * 主题
     */

    private String theme;

    /**
     * 内容
     */

    private String content;

    /**
     * 状态,1:发布，0:未发布,-1：删除
     */

    private Integer status;

    /**
     * 通知对象,可多个,json格式
     */
    private List<String> notifyMembersList;

    private String notifyMembers;

    /**
     * 额外信息
     */

    private String extraJson;

    /**
     * 创建时间
     */

    private Date createdAt;

    /**
     * 更新时间
     */

    private Date updatedAt;

   
    public void setNotifyMembersList(List<String> notifyMembersList){

        this.notifyMembersList =notifyMembersList;
        if(Arguments.isNull(notifyMembersList)){
            this.notifyMembers = null;
        }else{
            try {
                this.notifyMembers = objectMapper.writeValueAsString(notifyMembersList);
            } catch (Exception e) {
                //ignore this exception
            }
        }
    }


    public void setNotifyMembers(String notifyMembers){

        this.notifyMembers =notifyMembers;
        if(notifyMembers.equals("")||Arguments.isNull(notifyMembers)){
            this.notifyMembersList = null;
        }else{
            try {
                this.notifyMembersList = objectMapper.readValue(notifyMembers, new TypeReference<List<String>>() {
                });
            } catch (Exception e) {
                //ignore this exception
            }
        }
    }






}