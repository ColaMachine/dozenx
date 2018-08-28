/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明: 
 */
package com.dozenx.web.module.spider.art.comment.artComment.bean;
import java.sql.Timestamp;
import java.util.Date;

public class ArtComment {
    
/**编号**/
    private Integer id;
    public Integer getId(){
        return id;
    }    public void setId(Integer id){
        this.id=id;
    }
/**评论内容**/
    private String content;
    public String getContent(){
        return content;
    }    public void setContent(String content){
        this.content=content;
    }
/**分数**/
    private Float score;
    public Float getScore(){
        return score;
    }    public void setScore(Float score){
        this.score=score;
    }
/**类型**/
    private Integer type;
    public Integer getType(){
        return type;
    }    public void setType(Integer type){
        this.type=type;
    }
/**状态**/
    private Integer status;
    public Integer getStatus(){
        return status;
    }    public void setStatus(Integer status){
        this.status=status;
    }
/**原始时间**/
    private Timestamp data_time;
    public Timestamp getData_time(){
        return data_time;
    }    public void setData_time(Timestamp data_time){
        this.data_time=data_time;
    }
/**作者**/
    private String author;
    public String getAuthor(){
        return author;
    }    public void setAuthor(String author){
        this.author=author;
    }
/**创建时间**/
    private Timestamp createTime;
    public Timestamp getCreateTime(){
        return createTime;
    }    public void setCreateTime(Timestamp createTime){
        this.createTime=createTime;
    }
/**更新时间**/
    private Timestamp updatetime;
    public Timestamp getUpdatetime(){
        return updatetime;
    }    public void setUpdatetime(Timestamp updatetime){
        this.updatetime=updatetime;
    }
/**外部id**/
    private Integer outId;
    public Integer getOutId(){
        return outId;
    }    public void setOutId(Integer outId){
        this.outId=outId;
    }
/**酒店id**/
    private Integer pid;
    public Integer getPid(){
        return pid;
    }    public void setPid(Integer pid){
        this.pid=pid;
    }
}
