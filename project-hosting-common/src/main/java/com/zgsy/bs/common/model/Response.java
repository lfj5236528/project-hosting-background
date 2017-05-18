package com.zgsy.bs.common.model;

import lombok.Data;

/**
 * Created by liangfujie on 16/12/20
 */

/**
 * 结果返回类,所有控制器返回给前端对象应该是此类型
 * @param <T>
 */
@Data
public class Response<T> {

    private T result;

    private String message;

    private Boolean invoking=Boolean.TRUE;
}
