package com.zgsy.bs.common.model;

import lombok.Data;

import java.util.List;

/**
 * Created by liangfujie on 16/12/19
 */
@Data
public class Paging<T> {

    private Long total;
    private Boolean empty;
    private List<T> data;


}
