package com.zgsy.bs.common.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Java on 2017/5/13
 */
@Data
public class TableInfo implements Serializable{
    private static final long serialVersionUID = -4997158306661060514L;

    private String tableName;

    private Integer columnSize;

    private String tableDesc;

    private List<String> columnNames;

    private List<String> columnDescs;

    private List<String> columnTypes;
}
