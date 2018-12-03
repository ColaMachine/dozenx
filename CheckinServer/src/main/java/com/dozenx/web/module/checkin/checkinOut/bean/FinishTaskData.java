/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年11月7日 上午9:30:42
* 创建作者：尤小平
* 文件名称：FinishTaskData.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.dozenx.web.module.checkin.checkinOut.bean;

import java.util.List;

public class FinishTaskData {
    private String taskId;
    private Integer resultNum;
    private List<FinishTask> result;

    /**
     * @return the taskId
     */
    public String getTaskId() {
        return taskId;
    }
    /**
     * @param taskId the taskId to set
     */
    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
    
    /**
     * @return the resultNum
     */
    public Integer getResultNum() {
        return resultNum;
    }

    /**
     * @param resultNum the resultNum to set
     */
    public void setResultNum(Integer resultNum) {
        this.resultNum = resultNum;
    }

    /**
     * @return the result
     */
    public List<FinishTask> getResult() {
        return result;
    }
    /**
     * @param result the result to set
     */
    public void setResult(List<FinishTask> result) {
        this.result = result;
    }
}
