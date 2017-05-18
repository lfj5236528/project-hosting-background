package com.zgsy.bs.user.enums;

/**
 * Created by liangfujie on 16/12/19
 */
public enum UserStatus {
    USER_STATUS_NORMAL(1, "正常"),
    USER_STATUS_FROZEN(0, "冻结"),
    USER_STATUS_DELETE(-1, "删除");

    private final Integer index;

    private final String desc;

    UserStatus(Integer index, String desc) {
        this.index = index;
        this.desc = desc;
    }

    public Integer index() {
        return index;
    }

    public String desc() {
        return desc;
    }

}
