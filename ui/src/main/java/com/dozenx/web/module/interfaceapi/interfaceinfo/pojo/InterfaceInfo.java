package com.dozenx.web.module.interfaceapi.interfaceinfo.pojo;

import com.dozenx.web.module.interfaceapi.interfaceParam.pojo.InterfaceHeader;
import com.dozenx.web.module.interfaceapi.interfaceParam.pojo.InterfaceParam;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class InterfaceInfo {
    private Integer id;
    private String projectName;     //项目名称
    private String domain;

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }
    private String author;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
    private int  moduleId;       //模块id

    public int getModuleId() {
        return moduleId;
    }

    public void setModuleId(int moduleId) {
        this.moduleId = moduleId;
    }

    private String moduleName;       //模块名称
    private String url;             //请求地址
    private String httpType;        //http协议
    private String contentType;    //请求格式
    private String interfaceName;   //接口描述
    private String interfaceId;     //方法名
    private String interfaceDetail; //接口详情
    private String imageUrl;        //UML图片地址
    private String version;         //版本
    private String createTime;
    private int historyFlag;    //是否历史版本
    private Integer splicing;       //请求参数拼接标记 0不用，1要拼接
    private Date updateTime;//修改时间

    private List<InterfaceHeader> interfaceHeaders;//header头参数
    private List<InterfaceParam> interfaceParams;//参数

    private List<Integer > deleteParamsIds ;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHttpType() {
        return httpType;
    }

    public void setHttpType(String httpType) {
        this.httpType = httpType;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getInterfaceId() {
        return interfaceId;
    }

    public void setInterfaceId(String interfaceId) {
        this.interfaceId = interfaceId;
    }

    public String getInterfaceDetail() {
        return interfaceDetail;
    }

    public void setInterfaceDetail(String interfaceDetail) {
        this.interfaceDetail = interfaceDetail;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getHistoryFlag() {
        return historyFlag;
    }

    public void setHistoryFlag(int historyFlag) {
        this.historyFlag = historyFlag;
    }

    public Integer getSplicing() {
        return splicing;
    }

    public void setSplicing(Integer splicing) {
        this.splicing = splicing;
    }
    //接口测试用例项文杰
    @Override
    public String toString() {
        return "InterfaceInfo{" +
                "id=" + id +
                ", projectName='" + projectName + '\'' +
                ", moduleName='" + moduleName + '\'' +
                ", url='" + url + '\'' +
                ", httpType='" + httpType + '\'' +
                ", httpType='" + httpType + '\'' +
                ", interfaceName='" + interfaceName + '\'' +
                ", interfaceId='" + interfaceId + '\'' +
                ", interfaceDetail='" + interfaceDetail + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", version='" + version + '\'' +
                ", createTime='" + createTime + '\'' +
                ", historyFlag=" + historyFlag +
                ", splicing=" + splicing +
                '}';
    }

    public InterfaceInfo() {
    }
    //接口测试用例项文杰
    public InterfaceInfo(Integer id, String projectName, String moduleName, String url, String httpType, String contentType, String interfaceName, String interfaceId, String interfaceDetail, String imageUrl, String version, String createTime, int historyFlag, Integer splicing) {
        this.id = id;
        this.projectName = projectName;
        this.moduleName = moduleName;
        this.url = url;
        this.httpType = httpType;
        this.contentType = contentType;
        this.interfaceName = interfaceName;
        this.interfaceId = interfaceId;
        this.interfaceDetail = interfaceDetail;
        this.imageUrl = imageUrl;
        this.version = version;
        this.createTime = createTime;
        this.historyFlag = historyFlag;
        this.splicing = splicing;
    }


    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public List<InterfaceParam> getInterfaceParams() {
        return interfaceParams;
    }

    public void setInterfaceParams(List<InterfaceParam> interfaceParams) {
        this.interfaceParams = interfaceParams;
    }
}
