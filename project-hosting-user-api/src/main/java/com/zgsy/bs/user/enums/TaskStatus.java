package com.zgsy.bs.user.enums;

/**
 * Created by Java on 2017/1/14
 */
public enum TaskStatus {

    TASK_DELETED(-1,"任务已删除"),
    TASK_NORMAL(1,"任务正常进行中"),
    TASK_OVERDUE(0,"任务逾期"),
    TASK_FINISH(2,"任务完成");

    private final Integer index;

    private final String desc;

     TaskStatus(Integer index ,String desc){
        this.index=index;
        this.desc=desc;
    }

    public static String getDescByIndex(Integer index){
        if(index==-1)return TASK_DELETED.desc;
        if(index==1)return TASK_NORMAL.desc;
        if(index==0)return TASK_OVERDUE.desc;
        if(index==2)return TASK_FINISH.desc;
        return "未知状态";
    }

    public Integer index() {
        return index;
    }

    public String desc() {
        return desc;
    }


}
