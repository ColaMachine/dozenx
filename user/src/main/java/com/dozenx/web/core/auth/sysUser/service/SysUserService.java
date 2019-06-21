/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明:
 */

package com.dozenx.web.core.auth.sysUser.service;

import com.dozenx.core.exception.ParamException;
import com.dozenx.util.*;
import com.dozenx.web.core.auth.active.bean.Active;
import com.dozenx.web.core.auth.active.service.ActiveService;
import com.dozenx.web.core.auth.bean.Pwdrst;
import com.dozenx.web.core.auth.dao.PwdrstMapper;
import com.dozenx.web.core.auth.sysRole.bean.SysRole;
import com.dozenx.web.core.auth.sysRole.dao.SysRoleMapper;
import com.dozenx.web.core.auth.sysRole.service.SysRoleService;
import com.dozenx.web.core.auth.sysUser.bean.SysUser;
import com.dozenx.web.core.auth.sysUser.dao.SysUserMapper;
import com.dozenx.web.core.auth.sysUserRole.bean.SysUserRole;
import com.dozenx.web.core.auth.sysUserRole.dao.SysUserRoleMapper;
import com.dozenx.web.core.auth.sysUserRole.service.SysUserRoleService;
import com.dozenx.web.core.auth.validcode.service.ValidCodeService;
import com.dozenx.web.core.base.BaseService;

import com.dozenx.web.core.location.service.LocationService;
import com.dozenx.web.core.log.*;
import com.dozenx.web.module.email.email.service.EmailSendService;
import com.dozenx.web.util.ConfigUtil;
import com.dozenx.web.util.ResultUtil;
import com.dozenx.web.util.ValidUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.security.util.Password;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import java.sql.Timestamp;
import java.util.*;

@Service("sysUserService")
public class SysUserService extends BaseService {
    ServiceCode serviceCode= ServiceCode.USER_SERVICE;
    private static final Logger logger = LoggerFactory
            .getLogger(SysUserService.class);
    /** 省市区服务**/
    @Resource
    private LocationService locationService;
    /** 用户角色服务**/
    @Autowired
    private SysUserRoleService sysUserRoleService;
    @Autowired
    private SysRoleService sysRoleService;


    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private ActiveService  activeService;
    @Autowired
    private PwdrstMapper pwdrstMapper;
    @Autowired
    private SysRoleMapper roleMapper;

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;
    /**
     * 说明:list by page and params根据参数返回列表
     * @return List<HashMap>
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public List<SysUser> listByParams4Page(Map params) {
        return sysUserMapper.listByParams4Page(params);
    }
    public List<SysUser> listByParams(HashMap params) {
        return sysUserMapper.listByParams(params);
    }

    /**
     * 说明:countByParams 根据参数提取个数
     * @return int
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public int countByParams(HashMap params) {
        return sysUserMapper.countByParams(params);
    }

    /*
     * 说明:
     * @param SysUser
     * @return
     * @return Object
     * @author dozen.zhang
     * @date 2015年11月15日下午1:33:54
     */
    public ResultDTO save(SysUser sysUser) throws Exception {

        //逻辑业务判断判断
        //判断是否有uq字段

        //TODO 通过手机号码查用户信息 需要独立出来一个方法
        SysUser oldSysUser = sysUserMapper.selectConfilic(sysUser);
        if(oldSysUser!=null){
            if(StringUtil.isNotBlank(sysUser.getEmail()) && sysUser.getEmail().equals(oldSysUser.getEmail()) ){
            return ResultUtil.getResult(10002001,ErrorMessage.getErrorMsg("err.user.email.repeat"));//用户邮箱重复
        }
        if(StringUtil.isNotBlank(sysUser.getAccount()) && sysUser.getAccount().equals(oldSysUser.getAccount()) ){
        return ResultUtil.getResult(10002002,ErrorMessage.getErrorMsg("err.user.account.repeat"));      //用户账号重复
        }
        if(StringUtil.isNotBlank(sysUser.getTelno()) && sysUser.getTelno().equals(oldSysUser.getTelno()) ){
        return ResultUtil.getResult(10002003,ErrorMessage.getErrorMsg("err.user.telno.repeat"));     //用户账手机号重复
        }
        if(StringUtil.isNotBlank(sysUser.getUsername()) && sysUser.getUsername().equals(oldSysUser.getUsername()) ){
            return ResultUtil.getResult(10002004,ErrorMessage.getErrorMsg("err.user.username.repeat")); //用户username重复
        }

        }
//        HashMap params =new HashMap();
//
//
//
//
//        SysUser params =new SysUser();
//        params.put("account",sysUser.getAccount());
//        if(StringUtil.isNull(sysUser.getId())&& sysUserMapper.countByOrParams(params)>0 ){
//            return ResultUtil.getResult(10002001,ErrorMessage.getErrorMsg("err.user.account.repeat"));
//        }
//        params.clear();
//        params.put("username",sysUser.getUsername());
//        if(StringUtil.isNull(sysUser.getId())&& sysUserMapper.countByOrParams(params)>0 ){
//            return ResultUtil.getResult(10002001,ErrorMessage.getErrorMsg("err.user.username.repeat"));
//        }
//        params.clear();
//        params.put("telno",sysUser.getTelno());
//
//        if(StringUtil.isNull(sysUser.getId())&& sysUserMapper.countByOrParams(params)>0 ){
//            return ResultUtil.getResult(10002001,ErrorMessage.getErrorMsg("err.user.telno.repeat"));
//        }
//        params.clear();
//        params.put("email",sysUser.getEmail());
//        if(StringUtil.isNull(sysUser.getId())&& sysUserMapper.countByOrParams(params)>0 ){
//            return ResultUtil.getResult(10002001,ErrorMessage.getErrorMsg("err.user.email.repeat"));
//        }
        int result;
        //判断是更新还是插入
        if (sysUser.getId()!=null &&  this.selectByPrimaryKey(sysUser.getId())==null) {
            return ResultUtil.getResult(10003002, ErrorMessage.getErrorMsg("err.db.not.find.msg"));
        }
        if (sysUser.getId()==null ) {//新增用户
            sysUser.setStatus(1);

            if(StringUtil.isBlank(sysUser.getPassword())){
                //给用户设置默认密码
                //如果密码是空的 设置默认密码
                if(StringUtil.isBlank(sysUser.getPassword())){
                    String userPwd= ConfigUtil.getConfig("userPwd");

                    if(StringUtil.isBlank(userPwd)){
                        throw new ParamException(30001001,"未设置用户密码");
                    }
                    sysUser.setPassword(MD5Util.getStringMD5String(userPwd));//用户设置默认密码是config.properties里配置的
                }
            }
            sysUser.setCreatetime(DateUtil.getNowTimeStamp());
            sysUser.setUpdatetime(sysUser.getCreatetime());
            result =sysUserMapper.insertSelective(sysUser);
        } else {//更新用户
            sysUser.setUpdatetime(new Timestamp(new Date().getTime()));
            result = sysUserMapper.updateByPrimaryKeySelective(sysUser);
        }

        return  ResultUtil.getResult(result-1);

    }

    /*
    * 说明:
    * @param SysUser
    * @return
    * @return Object
    * @author dozen.zhang
    * @date 2015年11月15日下午1:33:54
    */
    public ResultDTO saveWithRoleInfo(SysUser sysUser) throws Exception {

        ResultDTO result = this.save(sysUser);
        if (!result.isRight()) {
            return result;

        }

        Long id = sysUser.getId();
        Long[] roleId = sysUser.getRoleIds();
        if (roleId == null || roleId.length == 0) {

        } else{
            sysUserRoleService.batchUpdate(new Long[]{id}, roleId);
         }
        return  ResultUtil.getSuccResult();
    }
    /**
     * 说明:根据主键删除数据
     * description:delete by key
     * @param id
     * @return void
     * @author dozen.zhang
     * @date 2015年12月27日下午10:56:38
     */
    public void delete(Long  id){
        sysUserMapper.deleteByPrimaryKey(id);
    }
    /**
     * 说明:根据主键获取数据
     * description:delete by key
     * @param id
     * @return void
     * @author dozen.zhang
     * @date 2015年12月27日下午10:56:38
     */
    public SysUser selectByPrimaryKey(Long id){
        return sysUserMapper.selectByPrimaryKey(id);
    }
    /**多id删除
     * @param idAry
     * @return
     * @author dozen.zhang
     */
    public ResultDTO multilDelete(Long[] idAry) {
        for(int i=0;i<idAry.length;i++){
            sysUserMapper.deleteByPrimaryKey(idAry[i]);
        }
        return ResultUtil.getSuccResult();
    }

    /**
     * @Author: dozen.zhang
     * @Description:附加了角色的查询信息
     * @Date: 2018/2/26
     */
    public List<HashMap<String,Object>> listWithRoleInfoByParams4Page(Map params) {

        //顺便加上一个地区信息
        List<HashMap<String,Object> >  list = sysUserMapper.listWithRoleInfoByParams4Page(params);
        for(HashMap<String,Object>  map : list){
            Long province = MapUtils.getLong(map,"province");
            String location="";

            try {
                if (province != null) {

                    String provinceName = locationService.getNameById(province);
                    if (provinceName != null)
                        location += provinceName;
                }
                Long city = MapUtils.getLong(map, "city");
                if (city != null) {
                    String cityName = locationService.getNameById(city);
                    if (cityName != null)
                        location += cityName;
                }
                Long county = MapUtils.getLong(map, "county");
                if (county != null) {
                    String countyName = locationService.getNameById(county);
                    if (countyName != null) {
                        location += countyName;
                    }
                }
            }catch (Exception e){
                logger.error("地区服务错误",e);
            }
            //查找出该用户的角色信息 放入到roleName中 和 roleId当中
            String roleNames = "";
            List<SysRole> roleList = sysRoleService.listByUserId(MapUtils.getLong(map, "id"));

            // Long roleIdAry[] = new Long[roleList.size()];
            if (roleList != null && roleList.size() > 0) {
                for (int i = 0; i < roleList.size(); i++) {
                    //  roleIdAry[i] = roleList.get(i).getId();
                    roleNames += roleList.get(i).getName() + " ";
                }
            }

            map.put("roleNames",roleNames);
            map.put("location",location);
        }
        return list;
    }

    /**
     * @Author: dozen.zhang
     * @Description:根据角色id 获取用户信息
     * @Date: 2018/2/26
     */
    public List<HashMap<String,Object>> listIdNameByRole(Long roleId) {

        //顺便加上一个地区信息
        List<HashMap<String,Object> >  list = sysUserMapper.listIdNameByRole(roleId);

        return list;
    }
    /**
     * @Author: dozen.zhang
     * @Description:根据角色id 只查取少量字段
     * @Date: 2018/2/26
     */
    public List<HashMap<String,Object>> listIdNameByParams(HashMap params) {
        return sysUserMapper.listIdNameByParam(params);
    }

    public SysUser selectWithRoleInfoByPrimaryKey(Long id){


        List<SysRole> roleList = sysRoleService.listByUserId(id);
        Long roleIdAry[] = new Long[roleList.size()];
        if(roleList!=null && roleList.size()>0){
            for(int i=0;i<roleList.size();i++){
                roleIdAry[i] = roleList.get(i).getId();
                // roleNames+= roleList.get(i).getName()+" ";
            }
        }

        SysUser sysUser =  sysUserMapper.selectByPrimaryKey(id);
        sysUser.setRoleIds(roleIdAry);
        return sysUser;
    }
    public static void main(String args[]){
        try {
            System.out.println(MD5Util.getStringMD5String("123456"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public SysUser getUserByEmail(String email) {
        SysUser user = sysUserMapper.selectUserByEmail(email);
        return user;
    }
    public SysUser getUserByTelno(String email) {
        SysUser user = sysUserMapper.selectUserByTelno(email);
        return user;
    }

    public SysUser getUserById(Long id) {
        SysUser user = sysUserMapper.selectByPrimaryKey(id);
        return user;
    }


    public int countAll() {
        return sysUserMapper.countAll();
    }




    /**
     * 登录验证 UnencryptedPwd 采用md5两次算法
     */
    public ResultDTO loginValid(String email, String encryptedPwd) {
        // / this.sysUserMapper.getUsersByParam(map)
        //String pwd = MD5Utils.MD5Encode(UnencryptedPwd);
        if (StringUtil.isBlank(email) || StringUtil.isBlank(encryptedPwd)) {
            return ResultUtil.getWrongResultFromCfg("err.account.empty");
        }

        HashMap<String, String> params = new HashMap<String, String>();
        if(StringUtil.isEmail(email)){
            //是手机号码
            params.put("email", email);
        }else if(StringUtil.isPhone(email)){
            params.put("telno", email);
        }else{
            params.put("account", email);
            //return ResultUtil.getResult(ResultUtil.fail,"既不是手机号也不是邮箱");
        }

        //params.put("password", pwd);
        List list = sysUserMapper.listByParams(params);
        if (list != null && list.size() > 0) {
            SysUser  user = (SysUser) list.get(0);
            if(StringUtil.isNotBlank(user.getPassword())){
                try {//二次加密后传入给后台 跟数据库中的拿出来md5之后做对比
                    if(encryptedPwd.equals(MD5Util.getStringMD5String(user.getPassword()))){
                        ResultDTO result = ResultUtil.getSuccResult();
                        result.setData(user);
                        return result;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
//				returnMap.putAll(userMap);

			/* SysUser user =new SysUser();
			  user.setEmail(MapUtils.getString(userMap, "email"));
			  user.setTelno(MapUtils.getString(userMap, "telno"));
			  user.setUsername(MapUtils.getString(userMap, "username"));
			  user.setId(MapUtils.getLong(userMap, "userid"));
			  user.setStatus(MapUtils.getIntValue(userMap, "active"));*/


            return ResultUtil.getWrongResultFromCfg("err.accountorpwd.wrong");
        } else {
            return ResultUtil
                    .getWrongResultFromCfg("err.accountorpwd.wrong");
        }
    }

    /**
     * @Author: dozen.zhang
     * @Description:验证机密后的密码
     * @Date: 2018/2/9
     */
    public ResultDTO loginValidWithEncryptedPwd(String account, String encryptedPwd) {
        // / this.sysUserMapper.getUsersByParam(map)
        //String pwd = MD5Utils.MD5Encode(UnencryptedPwd);
        if (StringUtil.isBlank(account) || StringUtil.isBlank(encryptedPwd)) {
            return ResultUtil.getWrongResultFromCfg("err.account.empty");
        }

        HashMap<String, String> params = new HashMap<String, String>();
//		if(StringUtil.isEmail(account)){
//			//是手机号码
//			params.put("email", email);
//		}else if(StringUtil.isPhone(email)){
//			params.put("telno", email);
//		}else{
        params.put("account", account);
        //return ResultUtil.getResult(ResultUtil.fail,"既不是手机号也不是邮箱");
        //}

        //params.put("password", pwd);
        List list = sysUserMapper.listByParams(params);
        if (list != null && list.size() > 0) {
            SysUser  user = (SysUser) list.get(0);
            if(StringUtil.isNotBlank(user.getPassword())){
                try {
                    if(encryptedPwd.equals(user.getPassword())){
                        ResultDTO result = ResultUtil.getSuccResult();
                        user.setPassword(null);//返回的时候不能把密码返回给对方
                        result.setData(user);
                        return result;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return ResultUtil.getWrongResultFromCfg("err.accountorpwd.wrong");
        } else {
            return ResultUtil
                    .getWrongResultFromCfg("err.accountorpwd.wrong");

        }
    }

    public ResultDTO saveRegisterUserSimple(SysUser user) {
        user.setStatus(0);//什么都没初始化过 激活过
        // 检测是否有冲突的用户
        // TODO 改成int 数
        //检查是否有相同账号用户
        //TODO 改成count 就可以了
        SysUser sameEmailUser =new SysUser();
        if(this.getUserByUserName(user.getUsername())!=null){
            return ResultUtil.getResult(30105089,"用户名重复");
        }
        if(StringUtil.isNotBlank(user.getEmail())){
            sameEmailUser = this.getUserByEmail(user.getEmail());

        }else if(StringUtil.isNotBlank(user.getTelno())){
            sameEmailUser = this.getUserByTelno(user.getTelno());
        }else{
            return ResultUtil.getResult(30105090,"邮箱地址必填");
        }

        if (sameEmailUser != null) {
            return ResultUtil.getResult(303,"邮箱地址已被注册");
        } else {
            user.setEmail(user.getEmail());
            user.setPassword(MD5Utils.MD5Encode(user.getPassword()));
            this.sysUserMapper.insert(user);

            return ResultUtil.getSuccResult();
            // }
        }
    }
    /**
     * 注册
     */
    public ResultDTO saveRegisterUser(SysUser user) {
        user.setStatus(0);//什么都没初始化过 激活过
        // / this.sysUserMapper.getUsersByParam(map)
        // 校验数据

        //ValidateUtil<User> valid = new ValidateUtil<User>();
		/*
		 * ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		 * Validator validator = factory.getValidator(); //
		 * validator.validateValue(User.class,"email",user.getEmail(), //
		 * javax.validation.groups.Default.class);
		 * Set<ConstraintViolation<User>> constraintViolations = validator
		 * .validate(user);
		 */

		/*ResultDTO result = valid.valid(user);
		if (result.getR() != 1) {
			return result;
		}*/
		/*
		 * if (!constraintViolations.isEmpty()) { HashMap errorMap = new
		 * HashMap(); for (ConstraintViolation<User> constraintViolation :
		 * constraintViolations) {
		 * errorMap.put(constraintViolation.getPropertyPath(),
		 * constraintViolation.getMessage()); } result = FAIL; msg = "注册失败";
		 * errors=errorMap;
		 *
		 * }else{
		 */

        // 检测是否有冲突的用户
        // TODO 改成int 数
        //检查是否有相同账号用户
        //TODO 改成count 就可以了
        SysUser sameEmailUser =new SysUser();
        if(this.getUserByUserName(user.getUsername())!=null){//用户名是否有重复
            return ResultUtil.getResult(30105087,"用户名重复");
        }
        if(StringUtil.isNotBlank(user.getEmail())){//使用的邮箱是否有重复
            sameEmailUser = this.getUserByEmail(user.getEmail());

        }else if(StringUtil.isNotBlank(user.getTelno())){
            sameEmailUser = this.getUserByTelno(user.getTelno());//使用的手机是否有重复
        }else{
            return ResultUtil.getResult(3015088,"手机号或邮箱地址必填");
        }

        if (sameEmailUser != null) {
            return ResultUtil.getResult(303,"手机号或邮箱地址已被注册");//爆出异常
        } else {//没有重复就发送验证码
            user.setEmail(user.getEmail());
            user.setPassword(MD5Utils.MD5Encode(user.getPassword()));
            user.setAccount(StringUtil.isBlank(user.getEmail())?user.getTelno():user.getEmail());
            this.sysUserMapper.insert(user);

            if(StringUtil.isNotBlank(user.getEmail())) {
                // 插入激活数据
//                Active active = new Active();
//                active.setUserId(user.getId());
                String code = UUIDUtil.getUUID().substring(0,6);
//                active.setCode();
//                this.activeService.save(active);//插入一个激活记录
                this.activeService.updateActive(user.getEmail(),"register",code);
                HashMap params =new HashMap();
                params.put("code","role_guest");
                List<SysRole> sysRoles=  roleMapper.listByParams(params);//查询系统里是否有普通用户角色

                //TODO assign guest role
                if(sysRoles==null || sysRoles.size()==0){
                   SysRole sysRole =  new SysRole();
                    sysRole.setCode("role_guest");
                    sysRole.setName("普通用户");
                    sysRole.setRemark("普通用户");
                    int id =  roleMapper.insert(sysRole);
                    sysRoles=new ArrayList<>();
                    sysRoles.add(sysRole);
                }

                SysUserRole sysUserRole=new SysUserRole();//为了给新注册的用户赋值普通用户
                sysUserRole.setUserId(user.getId());

                if(sysRoles==null || sysRoles.size()==0){
                    return ResultUtil.getFailResult("系统异常");
                }
                sysUserRole.setRoleId(sysRoles.get(0).getId());
                sysUserRoleMapper.insert(sysUserRole);//给当前用户赋值一个普通角色
                // 发送激活邮件
                try {
                    emailSendService.sendEmail(user.getEmail(),"邮件验证码" ,"你的邮件验证码:" + code + "");
                }catch (Exception e){
                    logger.error("发送邮件失败");
                    LogUtilFeichu.system(serviceCode,218,user.getEmail()+code,e.getMessage()+" 发送邮件失败","");
                    return ResultUtil.getResultDetail(serviceCode, LogType.THIRD, 219, "SEND_FAIL");
                }
            }
            return ResultUtil.getSuccResult();
            // }
        }
    }
    @Resource
    EmailSendService emailSendService;
    public ResultDTO sendEmailCode(String email ){
        //首先去判断有没有用户是用这个email 了
        int num = sysUserMapper.countUserByEmail(email);
        if(num > 0 ){
            return ResultUtil.getResult(30102312,"此邮箱已被使用");
        }
        //发送邮件和记录active

        // 插入激活数据
//        Active active = new Active();
//        active.setAccount(email);
//        active.setCode(UUIDUtil.getUUID().substring(0,6));
//        this.activeMapper.insert(active);//插入一个激活记录


        try {
            String code = UUIDUtil.getUUID().substring(0,6);
            emailSendService.sendEmail(email,"邮件验证码", "你的邮件验证码:" + code + "");
            activeService.save(email,"register",code);
        }catch (Exception e){
            logger.error("发送邮件失败",e);
            LogUtilFeichu.system(serviceCode,218,email,e.getMessage()+" 发送邮件失败","");
            return ResultUtil.getResultDetail(serviceCode, LogType.THIRD, 219, "SEND_FAIL");
        }
        return ResultUtil.getSuccResult();
    }

//    @Transactional (rollbackFor = Exception.class)
    //注册用户前先校验邮箱验证码是否正确
    public ResultDTO saveRegisterUserWithEamilCode(SysUser user,String emailCode) throws Exception{
        user.setStatus(1);//什么都没初始化过 激活过
        //校验邮箱验证码
//        Active active = activeService.selectByParam(user.getEmail(),"registeremail",emailCode);
//        //通过邮箱的验证码的前几位去验证
//        if(active==null || !active.getCode().startsWith(emailCode) /*|| !active.getEmail().equals(user.getEmail())*/){
//            return ResultUtil.getResult(30101105,"邮箱验证码校验错误");
//        }

        activeService.updateActive(user.getEmail(),"registeremail",emailCode);
        // 检测是否有冲突的用户
        // TODO 改成int 数
        //检查是否有相同账号用户
        //TODO 改成count 就可以了
        SysUser sameEmailUser =new SysUser();
        if(this.getUserByUserName(user.getUsername())!=null){//用户名是否有重复
            return ResultUtil.getResult(30105087,"用户名重复");
        }
        if(StringUtil.isNotBlank(user.getEmail())){//使用的邮箱是否有重复
            sameEmailUser = this.getUserByEmail(user.getEmail());

        }else{
            return ResultUtil.getResult(3015088,"手机号或邮箱地址必填");
        }

        if (sameEmailUser != null) {
            return ResultUtil.getResult(303,"手机号或邮箱地址已被注册");//爆出异常
        } else {//没有重复就发送验证码
            user.setEmail(user.getEmail());
            user.setUsername(user.getEmail());
            user.setPassword(MD5Utils.MD5Encode(user.getPassword()));
            user.setAccount(StringUtil.isBlank(user.getEmail())?user.getTelno():user.getEmail());
            this.sysUserMapper.insert(user);

            if(StringUtil.isNotBlank(user.getEmail())) {
                // 插入激活数据
               // activeService.save(active);
                //维护用户角色关系
                HashMap params =new HashMap();
                params.put("code","role_guest");
                List<SysRole> sysRoles=  roleMapper.listByParams(params);//查询系统里是否有普通用户角色

                //TODO assign guest role
                if(sysRoles==null || sysRoles.size()==0){
                    SysRole sysRole =  new SysRole();
                    sysRole.setCode("role_guest");
                    sysRole.setName("普通用户");
                    sysRole.setRemark("普通用户");
                    sysRole.setStatus(1);
                    int id =  roleMapper.insert(sysRole);
                    sysRoles=new ArrayList<>();
                    sysRoles.add(sysRole);
                }

                SysUserRole sysUserRole=new SysUserRole();//为了给新注册的用户赋值普通用户
                sysUserRole.setUserId(user.getId());

                if(sysRoles==null || sysRoles.size()==0){
                    return ResultUtil.getFailResult("系统异常");
                }
                sysUserRole.setRoleId(sysRoles.get(0).getId());
                sysUserRoleMapper.insert(sysUserRole);//给当前用户赋值一个普通角色
                // 发送激活邮件
            }

            return ResultUtil.getSuccResult();
            // }
        }
    }

/*	String MSG = "msg";
	String ERRORS = "errrors";
	String RESULT = "result";*/

    /* (non-Javadoc)
     * @see cola.machine.service.UserService#saveSenPwdrstEmail(java.lang.String)
     * @author colamachine
     */
    public ResultDTO saveSendPwdrstEmail(String email) {

        // 格式判断

        if (! StringUtil.isEmail(email)) {
            return ResultUtil.getResult(30105100,"邮箱格式不正确");
        }
        // 数据判断
        SysUser user = sysUserMapper.selectUserByEmail(email);
        if (user == null ||user.getId()==null||user.getId()==0 ) {
            return ResultUtil.getWrongResultFromCfg("mail.not.register");
        }
        // 判断是否有未使用的邮件重置数据
        // 根据used=0 和 userid 来判断 发送手机验证码到用户邮箱地址
        List list = pwdrstMapper.selectUnusedPwdrstByUserId(user.getId());
        Pwdrst pwdrst = new Pwdrst();
        if (list != null && list.size() > 0) {
            pwdrst = (Pwdrst) list.get(0);
            // 防止重复发送激活邮件
        } else {
            // List list=
            // 保存数据
            pwdrst = new Pwdrst();
            pwdrst.setUserid(user.getId());
            pwdrst.setUsed(false);
            pwdrst.setCreatetime(new Timestamp((new Date()).getTime()));
            pwdrst.setResettime(new Timestamp((new Date()).getTime()));
            pwdrst.setIdpwdrst(UUIDUtil.getUUID());
            pwdrstMapper.insertPwdrst(pwdrst);
            // 发送邮件
        }


        // 这个类主要来发送邮件

        // sms.sendTextMail(mailInfo);//发送文体格式
        try {
            emailSendService.sendEmail(email,"重置密码",pwdrst.getIdpwdrst());
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.getResult(30105101,"发送邮件失败");
        }

        // 发送邮件
        // 是否已经发送过重置邮件了
        // 如果有没有用过的重置信息 重复利用


        return ResultUtil.getSuccResult();
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * cola.machine.calendar.user.service.UserService#updateUserActive(java.
     * lang.String)
     *
     * @author colamachine 涉及两张表 user 表 和 active 表 user表有active 字段标记账户是否激活状态
     * active 表记录激活过程和激活码
     */
    public ResultDTO updateUserActive(String code,String account,String type) {

        Active active = this.activeService.selectByParam(code,account,type);
        if (active == null || StringUtil.isBlank(active.getCode())) {
            return ResultUtil.getWrongResultFromCfg("active.url.not.valid");
        }
        if (active.getStatus()==0) {
            return ResultUtil.getWrongResultFromCfg("active.url.used");
        }
        active.setStatus(1);
        active.setActivedTime(new Timestamp((new Date()).getTime()));
        SysUser user =null;
        if(StringUtil.isEmail(account)){
             user = this.sysUserMapper.selectUserByEmail(account);

        }else if(StringUtil.isPhone(account)){
             user = this.sysUserMapper.selectUserByTelno(account);

        }else{
            return ResultUtil.getResult(30101775,"既不是手机号也不是邮箱");
        }
        if(user==null){
            return ResultUtil.getResult(30101778,"未找到关联用户");
        }
        active.setUserId(user.getId());

        this.activeService.save(active);
        user.setStatus(2);
        this.sysUserMapper.updateByPrimaryKey(user);

        return ResultUtil.getSuccResult();
		/*HashMap returnMap = new HashMap();
		returnMap.put("user", user);
		returnMap.put(SysConfig.AJAX_RESULT, SysConfig.AJAX_SUCC);
		returnMap.put(SysConfig.AJAX_MSG, "激活成功");
		return returnMap;*/
    }
    @Autowired
    private ValidCodeService validCodeService;
    /* (non-Javadoc)
     * @see cola.machine.service.UserService#savePwdrst(java.lang.String, java.lang.String)
     * @author colamachine
     */
    public ResultDTO savePwdrst(String account,String pwd, String code) {
        // 数据格式校验

        //ValidUtil.valid();
//		ValidateUtil validateUtil =new ValidateUtil();
//		validateUtil.add("pwd",pwd,"密码",new Rule[]{new Required(),new Length(1,50)});
//		validateUtil.add("account",account,"账号",new Rule[]{new Required(),new Length(1,50)});
//
//		ValidUtil.valid("pwd",pwd,"");
        ResultDTO msgBox = ValidUtil.pwd(pwd);
        if (!msgBox.isRight()) {
            return msgBox;
        }
        if (StringUtil.isBlank(code)) {
            return ResultUtil.getWrongResultFromCfg("pwd.reset.code.wrong");
        }
        Long userid=null;
        if(StringUtil.isPhone(account)){
            SysUser user = this.getUserByTelno(account);
            if(user==null)
                return  ResultUtil.getResult("USER_NOT_FOUND");
            ResultDTO result = validCodeService.smsValidCode("calendar", account, code);
            userid =user.getId();
        }else if(StringUtil.isEmail(account)){

            SysUser user = this.getUserByEmail(account);
            if(user==null)
                return  ResultUtil.getResult("USER_NOT_FOUND");
            userid=user.getId();
            // db校验
            Pwdrst pwdrst = pwdrstMapper.selectPwdrstById(code);
            if (pwdrst == null || pwdrst.getUserid().longValue()!= userid.longValue()) {
                return ResultUtil.getResult("VALIDCODE_MATCH_ERR");
            }
            if (pwdrst.isUsed()) {
                return ResultUtil.getResult("VALIDCODE_USED");
            }

            pwdrst.setUsed(true);
            pwdrstMapper.used(code);
            userid = pwdrst.getUserid();
        }else{
            return ResultUtil.getResult("ACCOUNT_FORMAT_ERR");
        }



        SysUser user = new SysUser();
        user.setId(userid);
        user.setPassword(MD5Utils.MD5Encode(pwd));
        sysUserMapper.resetPwd(user);
        return ResultUtil.getSuccResult();
    }

    /**
     * 更新用户状态
     * @param status
     * @param userid
     */
    public void updateStatus(int status ,Long userid) {
        SysUser user =new SysUser();
        user.setStatus(status);
        user.setId(userid);
        sysUserMapper.updateStatus(user);
    }


    public ResultDTO activeWithEmail(String email,String code){
        if(StringUtil.isBlank(email)|| !StringUtil.isEmail(email) || StringUtil.isBlank(code)){
            return ResultUtil.getResultDetail(serviceCode,LogType.PARAM,395,"PARAM_ERR");
        }

        Active active = this.activeService.selectByParam(email,"register",code);

        if (active == null || StringUtil.isBlank(active.getCode())) {
            return ResultUtil.getResult("VALIDCODE_ERR");
        }
        if (active.getStatus()==1) {
            return ResultUtil.getResult("VALIDCODE_USED");
        }


        SysUser olduser =sysUserMapper.selectByPrimaryKey(active.getUserId());
        if(!email.equals(olduser.getEmail())){
            return ResultUtil.getResultDetail(serviceCode,LogType.PARAM,410,"PARAM_ERR");
        }
        active.setStatus(1);
        active.setActivedTime(new Timestamp((new Date()).getTime()));
        this.activeService.save(active);
        SysUser user = this.sysUserMapper.selectByPrimaryKey(active.getUserId());
        user.setStatus(2);
        this.sysUserMapper.updateByPrimaryKey(user);

        return ResultUtil.getSuccResult();
    }

    /**
     * 根据微信id获取用户信息
     * @param openid
     * @return
     */
    public SysUser getUserByWxopenid(String openid) {
        SysUser user = sysUserMapper.selectUserByWeichat(openid);
        return user;
    }

    public SysUser getUserByUserName(String loginname) {
        SysUser user = sysUserMapper.selectUserByUsername(loginname);
        return user;
    }

      public ResultDTO updateUserPwd(Long userId,String oldPassword, String newPassword) {
        try {
            oldPassword = MD5Util.getStringMD5String(oldPassword);
            newPassword = MD5Util.getStringMD5String(newPassword);
        }catch (Exception e){
            return ResultUtil.getResult(10305038,"MD5失败");
        }
          if(newPassword.equals(oldPassword)){
              return ResultUtil.getResult(10305039,ErrorMessage.getErrorMsg("pwd.reset.old.equal.new"));
          }
          SysUser userInfo = this.getUserById(userId);
        if(!userInfo.getPassword().equals(oldPassword)){
            return ResultUtil.getResult(10305038,ErrorMessage.getErrorMsg("pwd.reset.wrong.old.pwd"));
        }



        SysUser user = new SysUser();
        user.setId(userId);
        user.setPassword(newPassword);
        sysUserMapper.resetPwd(user);
        return ResultUtil.getSuccResult();
    }


    public SysUser getUserByOutId(Long id) {
        SysUser user = sysUserMapper.selectUserByOutId(id);
        return user;
    }


    public void updatePinyin(Long userId,String pinying){
        sysUserMapper.updatePinyin(userId,pinying);
    }
}
