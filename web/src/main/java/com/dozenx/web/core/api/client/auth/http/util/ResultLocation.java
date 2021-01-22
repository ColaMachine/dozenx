package com.dozenx.web.core.api.client.auth.http.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2020/3/15.
 */


public class ResultLocation {
    private Long id;
    private Long parentId;
    private String areaName;
    private String code;
    private String fullName;
    private String type;

    List<ResultLocation> childList;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<ResultLocation> getChildList() {
        return childList;
    }

    public void setChildList(List<ResultLocation> childList) {
        this.childList = childList;
    }



    //获取整个地区 格式
    public  static Map checkPCA(ResultLocation result){
        Map<String,Object> pMap=new HashMap<>();


        List<ResultLocation> fChildList = result.getChildList();

        //封装每个市
        List< Map<String,Object> > cityList=new ArrayList<>();
        for (ResultLocation  city:fChildList) {
            //获取每个市code+名称
            String sonkey = city.getCode() + "," + city.getAreaName();
            //获取每个区的code+名称
            Map<String,Object> areaMap=new HashMap<>();
            List<ResultLocation> sChildList = city.getChildList();
            List<String> areaList=new ArrayList<>();
            for (ResultLocation  last:sChildList) {

                String ssonkey = last.getCode() + "," + last.getAreaName();
                areaList.add(ssonkey);

            }
            areaMap.put("name",sonkey);
            areaMap.put("area",areaList);
            //添加到List里面
            cityList.add(areaMap);
        }
        //拿到省的
        String pkey=result.getCode() + "," + result.getAreaName();
        pMap.put("name",pkey);
        pMap.put("city",cityList);



        return pMap;
    }
}
