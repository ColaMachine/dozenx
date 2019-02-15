/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明: 
 */
package com.dozenx.web.core.auth.sysUser.bean;
import com.dozenx.util.DateUtil;
import com.dozenx.web.core.auth.session.SessionUser;

import javax.mail.Session;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

public class SysUser implements Serializable {
    /**编号**/
    private Long id;



    /**用户名**/

    private String username;
    /**密码**/
    private String password;
    /**昵称**/
    private String nkname;
    /**类型**/
    private Integer type;
    /**状态**/
    private Integer status;
    /**邮箱地址**/
    private String email;
    /**手机号码**/
    private String telno;
    /**身份证号码**/
    private String idcard;
    /**性别**/
    private Integer sex;
    /**出生年月**/
    private Date birth;
    /**积分**/
    private Integer integral;
    /**地址**/
    private String address;
    /**省**/
    private int city;
    /**市**/
    private int province;
    /**区**/
    private int county;
    /*账号*/
    private String account;

    private String code;
    private String org;
    private String depart;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getOrg() {
        return org;
    }

    public void setOrg(String org) {
        this.org = org;
    }

    public String getDepart() {
        return depart;
    }

    public void setDepart(String depart) {
        this.depart = depart;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public int getCity() {
        return city;
    }

    public void setCity(int city) {
        this.city = city;
    }

    public int getProvince() {
        return province;
    }

    public void setProvince(int province) {
        this.province = province;
    }

    public int getCounty() {
        return county;
    }

    public void setCounty(int county) {
        this.county = county;
    }

    /**微信**/
    private String wechat;
    private Long qq;
    private String face;
    private String remark;
    private Timestamp createTime;
    public Long outId;

    public Long[] getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(Long[] roleIds) {
        this.roleIds = roleIds;
    }

    private Long[] roleIds;


    public String getUsername(){
        return username;
    }    public void setUsername(String username){
        this.username=username;
    }
    public String getPassword(){
        return password;
    }    public void setPassword(String password){
        this.password=password;
    }

    public String getNkname(){
        return nkname;
    }    public void setNkname(String nkname){
        this.nkname=nkname;
    }
    public Integer getType(){
        return type;
    }    public void setType(Integer type){
        this.type=type;
    }
    public Integer getStatus(){
        return status;
    }    public void setStatus(Integer status){
        this.status=status;
    }
    public String getEmail(){
        return email;
    }    public void setEmail(String email){
        this.email=email;
    }
    public String getTelno(){
        return telno;
    }    public void setTelno(String telno){
        this.telno=telno;
    }
    public String getIdcard(){
        return idcard;
    }    public void setIdcard(String idcard){
        this.idcard=idcard;
    }
    public Integer getSex(){
        return sex;
    }    public void setSex(Integer sex){
        this.sex=sex;
    }
    public Date getBirth(){
        return birth;
    }    public void setBirth(Date birth){
        this.birth=birth;
    }
    public Integer getIntegral(){
        return integral;
    }    public void setIntegral(Integer integral){
        this.integral=integral;
    }
    public String getAddress(){
        return address;
    }    public void setAddress(String address){
        this.address=address;
    }
    public String getWechat(){
        return wechat;
    }    public void setWeichat(String wechat){
        this.wechat=wechat;
    }/**qq**/

    public Long getQq(){
        return qq;
    }    public void setQq(Long qq){
        this.qq=qq;
    }/**头像**/

    public String getFace(){
        return face;
    }    public void setFace(String face){
        this.face=face;
    }/**备注**/

    public String getRemark(){
        return remark;
    }    public void setRemark(String remark){
        this.remark=remark;
    }/**创建时间**/

    public Timestamp getCreatetime(){
        return createTime;
    }    public void setCreatetime(Timestamp createtime){
        this.createTime=createtime;
    }/**更新时间**/
    private Timestamp updateTime;
    public Timestamp getUpdatetime(){
        return updateTime;
    }    public void setUpdatetime(Timestamp updatetime){
        this.updateTime=updatetime;
    }

    public Long getId(){
        return id;
    }    public void setId(Long id){
        this.id=id;
    }
    public Long getOutId() {
        return outId;
    }

    public void setOutId(Long outId) {
        this.outId = outId;
    }


    public SessionUser getSessionUser(){
        SessionUser sessionUser =new SessionUser();
        sessionUser.setUserId(this.getId());
        sessionUser.setAccount(this.getAccount());
        sessionUser.setPhone(getTelno());
        sessionUser.setAddress(getAddress());
        sessionUser.setBirth(getBirth()==null?"": DateUtil.toDateStr(getBirth(),DateUtil.YYYY_MM_DD));
        sessionUser.setSex(getSex()==null ? 0:getSex());
        sessionUser.setFace(this.getFace());
        sessionUser.setUserName(this.getUsername());
        return sessionUser;
    }

}
