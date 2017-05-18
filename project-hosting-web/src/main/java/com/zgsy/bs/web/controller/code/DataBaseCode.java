package com.zgsy.bs.web.controller.code;

import com.zgsy.bs.common.model.Response;
import com.zgsy.bs.common.model.TableInfo;
import com.zgsy.bs.common.utils.MysqlUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Java on 2017/5/13
 */
@RestController
@Slf4j
@CrossOrigin

@RequestMapping("/api/web/db/code")
public class DataBaseCode {



    @RequestMapping(value = "/table-info",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<List<TableInfo>> getTableInfo(String url){
        Response<List<TableInfo>> response = new Response<List<TableInfo>>();
        List<TableInfo> list =new ArrayList<TableInfo>();
        MysqlUtils.initMysqlUtils(url);
        try {
            List<String> tables = MysqlUtils.getAllTableName();
            for (String name :tables){
                TableInfo tableInfo = MysqlUtils.getTableInfo(name);
                list.add(tableInfo);
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.setMessage("查询失败！");
            return response;
        }
        response.setMessage("查询成功！");
        response.setResult(list);
        return response;

    }

    @RequestMapping(value = "/model-code",method = RequestMethod.POST)
    public Response<String> getModelCode(String url,String tableName){
        Response<String> response = new Response<String>();
        StringBuffer stringBuffer =new StringBuffer("@Data \npublic class ");

        try {
            MysqlUtils.initMysqlUtils(url);
            TableInfo tableInfo = MysqlUtils.getTableInfo(tableName);
            String className =tableInfo.getTableName();
            String[] classNames=className.split("_");
            for (String s:classNames){
                stringBuffer.append(String.valueOf(s.charAt(0)).toUpperCase());
                stringBuffer.append(s.substring(1,s.length()));
            }
            stringBuffer.append(" { \n");
            List<String> types =tableInfo.getColumnTypes();
            List<String> names =tableInfo.getColumnNames();
            for(int i=0;i<tableInfo.getColumnSize();i++){
                stringBuffer.append("private ");
                if(types.get(i).contains("int")||types.get(i).contains("INT")){
                    stringBuffer.append("Integer ");
                }
                if(types.get(i).contains("char")||types.get(i).contains("CHAR")||types.get(i).contains("text")||types.get(i).contains("TEXT")){
                    stringBuffer.append("String ");
                }
                if(types.get(i).contains("decimal")||types.get(i).contains("DECIMAL")){
                    stringBuffer.append("Double ");
                }
                if(types.get(i).contains("DATE")||types.get(i).contains("date")){
                    stringBuffer.append("Date ");
                }

                String[] filedNames=names.get(i).split("_");
                stringBuffer.append(filedNames[0]);
                for (int k=1 ;k<filedNames.length;k++){
                    String s = filedNames[k];
                    stringBuffer.append(String.valueOf(s.charAt(0)).toUpperCase());
                    stringBuffer.append(s.substring(1,s.length()));
                }
                stringBuffer.append(";\n");
            }
            stringBuffer.append("}");

        } catch (Exception e) {
            e.printStackTrace();
            response.setMessage("生成失败！");
            return response;
        }
        response.setMessage("生成成功！");
        response.setResult(stringBuffer.toString());
        return response;


    }


}
