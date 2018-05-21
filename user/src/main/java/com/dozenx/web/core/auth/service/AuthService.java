/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明:
 */
package com.dozenx.web.core.auth.service;

import com.dozenx.util.PermissionUtil;
import com.dozenx.web.core.auth.session.SessionUser;
import com.dozenx.core.config.SysConfig;
import com.dozenx.web.core.auth.sysRole.bean.SysRole;
import com.dozenx.web.core.auth.sysUser.service.SysUserService;
import com.dozenx.web.util.ResultUtil;
import com.dozenx.util.StringUtil;
import com.dozenx.web.core.auth.dao.SysAuthMapper;
import com.dozenx.web.core.auth.sysMenu.bean.SysMenu;
import com.dozenx.web.core.auth.sysMenu.service.SysMenuService;
import com.dozenx.web.core.auth.sysPermission.bean.SysPermission;
import com.dozenx.web.core.auth.sysUser.bean.SysUser;
import com.dozenx.web.core.auth.validcode.service.ValidCodeService;
import com.dozenx.web.core.log.LogType;
import com.dozenx.web.core.log.ResultDTO;
import com.dozenx.web.core.log.ServiceCode;
import com.dozenx.web.core.log.service.LogUtilService;
//import com.dozenx.web.module.merchant.bean.SessionDTO;
//import com.dozenx.web.module.merchant.bean.SessionUser;
//import com.dozenx.web.third.dbcenter.service.UserAuthService;
//import com.dozenx.web.third.dbcenter.service.UserBaseService;
//import com.dozenx.web.third.user.service.IThirdUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;


@Service("authService")
public class AuthService {
    private static final Logger logger = LoggerFactory
            .getLogger(AuthService.class);
    ServiceCode serviceCode = ServiceCode.AUTH_SERVICE;
    @Resource
    ValidCodeService validCodeService;
 /*   @Resource

    MngMerchantService mngMerchantService;*/
//    @Resource
//    private UserAuthService userAuthService;
///*    @Resource
//    private MerchantBaseService merchantBaseService;*/
//    @Resource
//    private UserBaseService userBaseService;

    @Autowired
    private SysUserService userService;
    @Resource
    private SysMenuService sysMenuService;
    @Resource
    private SysAuthMapper authMapper;
    @Resource
    private LogUtilService LogUtil;

  //  @Resource(name="thirdUserService")
    private IThirdUserService thirdUserService;

    /**
     * 根据用户id查询用户资源信息
     * @param userid
     * @return
     */
   /* public List<SysResource> listResourcesByUserid(Long userid){
       return  authMapper.selectResourceByUserId(userid);
    }
    public List<SysResource> listMenuResourcesByUserid(Long userid){
        return  authMapper.selectMenuResourceByUserId(userid);
    }*/
    public List<SysPermission> listPermissionByUserid(Long userid) {
        List<SysPermission> permissions =  authMapper.selectPermissionByUserId(userid);

        for(SysPermission sysPermission : permissions){
            if(StringUtil.isNotBlank( sysPermission.getUrl())){
                sysPermission.setUrl(sysPermission.getUrl().replaceAll(SysConfig.PATH,""));
            }

        }
        return permissions;
    }
    public List<String> listPermissionStrByUserid(Long userid) {
        List<String> permissions =  authMapper.selectPermissionStrByUserId(userid);

//        for(SysPermission sysPermission : permissions){
//            if(StringUtil.isNotBlank( sysPermission.getUrl())){
//                sysPermission.setUrl(sysPermission.getUrl().replaceAll(SysConfig.PATH,""));
//            }
//
//        }
        return permissions;
    }

    /**
     * 根据用户id获得角色信息
     * @param userid
     * @return
     */
    public List<SysRole> listRolesByUserid(Long userid) {
        List<SysRole> roles =  authMapper.selectRolesByUserId(userid);
        return roles;
    }

    /**
     * 根据userid 获取当前的权限菜单
     * @param userid
     * @return
     */
    public List<SysMenu> listMenusByUserid(Long userid) {
        List<SysPermission> permissions = this.listPermissionByUserid(userid);
        HashSet<String> set = new HashSet<String>();
        for (SysPermission sysPermission : permissions) {
            set.add(sysPermission.getCode());

        }
        List<SysMenu> menus = sysMenuService.listByParams(new HashMap());
        Object[] permissionAry = set.toArray();
        String[] permissionStrAry = new String[set.size()];

        Iterator it = set.iterator();
        int j = 0;
        while (it.hasNext()) {
            permissionStrAry[j] = (String) it.next();
            j++;
        }
        for (int i = menus.size() - 1; i >= 0; i--) {
            SysMenu sysMenu = menus.get(i);
            String permissionStr = sysMenu.getPermission();

            if (StringUtil.isBlank(sysMenu.getUrl())) {
                continue;
            }
            if (!PermissionUtil.hasPermission(sysMenu.getPermission(), permissionStrAry)) {
                menus.remove(i);
            }

        }
        return menus;
    }


    /**
     * 直接发送短信 用于登录的时候发送验证码 如果用户没有注册过直接注册 下次登录根据短信验证码登录
     * @param ip
     * @param phone
     * @param helloSmsFlagInSession
     * @return
     * @throws Exception
     */
 /*   public ResultDTO sendSmsValidCode4LoginDirectRegister(String ip,String phone,String helloSmsFlagInSession) throws Exception {
        if(StringUtil.isBlank(helloSmsFlagInSession)){
            logger.error("cheater ip:"+   ip+" xforward "+"phone:"+phone);
            return ResultUtil.getResult();
        }

        if(StringUtil.isBlank(phone) ||!StringUtil.isPhone(phone)){
            return ResultUtil.getWrongResultFromCfg("err.mobile.format");
        }
        //根据手机号码查询用户是否存在 如果不存在 直接注册
        Object user = userService.getUserByTelno(phone);
        if(user==null){
            //直接注册吗
            SysUser registerUser =new SysUser();
            registerUser.setTelno(phone);
            registerUser.setPassword("123456");
            registerUser.setStatus(1);

            userService.saveRegisterUser(registerUser);
        }


        ResultDTO result = validCodeService.getSmsValidCode("calendar",phone,ip);
        return result;
    }*/

    /**
     * 直接发送短信 用于登录的时候发送验证码 如果用户没有注册过直接注册 下次登录根据短信验证码登录
     * @param ip
     * @param phone
     * @param helloSmsFlagInSession
     * @return
     * @throws Exception
     */
    public ResultDTO sendSmsValidCode4LoginDirectRegister(String ip, String phone, String helloSmsFlagInSession) throws Exception {
        if (StringUtil.isBlank(helloSmsFlagInSession)) {
            logger.error("cheater ip:" + ip + " xforward " + "phone:" + phone);
            return ResultUtil.getResult(30106601,"短信发送失败");
        }

        if (StringUtil.isBlank(phone) || !StringUtil.isPhone(phone)) {
            return ResultUtil.getWrongResultFromCfg("err.mobile.format");
        }

        Object user = userService.getUserByTelno(phone);
        if (user == null) {
            //直接注册吗
            SysUser registerUser = new SysUser();
            registerUser.setTelno(phone);
            registerUser.setUsername(phone);
            registerUser.setPassword("123456");
            registerUser.setStatus(1);

            userService.saveRegisterUser(registerUser);
        }
        //调用第三方 根据手机号码查询用户是否存在 如果不存在 直接注册
        if (thirdUserService != null) {
            SessionUser sessionUser = new SessionUser();
            sessionUser.setPhone(phone);
            sessionUser.setPwd("123456");
            thirdUserService.addUser(sessionUser);
        }

        ResultDTO result = validCodeService.getSmsValidCode("calendar", phone, ip);
        return result;
    }


    /**
     * . 登录获取用户信息并做相关保存操作
     *
     * @param  sessionDTO session
     * @param phone 账号
     * @param captcha yanzhengma
     *       1验证验证码是否通过, 2验证手机号是否在auth中存在 没有的话报错 3验证手机号码在base 中是否存在 没有的话报错 4查询昵称是否存在 5检查用户商户角色放入sessionUser中的role
     *                 6如果不是商户管理员检查用户是否是代理管理员 7初始化用户上网时长信息 8 存入 最近的用户 手机+ 用户手机mac +商户id 做校验符
     * @return Map{
     *      user:
     *      write_user_info:ok|null
     *       //role:Constants.SESSION_MERCHANT
     *       timeInfo: {boolean vip;
     *                    boolean canGetFreePgk;
     *                   endTime:
     * }
     * @throws Exception
     * @author 宋展辉 2016年1月27日 上午10:33:53
     */
//    public ResultDTO loginByPhoneAndSms(SessionDTO sessionDTO,
//                               String phone, String captcha) throws Exception {
//        ResultDTO resultDTO = new ResultDTO();
//        if (sessionDTO == null) {//sessionDTO不能为空
//            logger.error("sessionDTO can't not be null phone:" + phone);
//            throw new Exception("sessionDTO can't not be null");
//        }
//        SessionUser sessionUser = sessionDTO.getSessionUser();
//        if (sessionUser == null) {//sessionUser初始化
//            sessionUser = new SessionUser();
//            sessionDTO.setSessionUser(sessionUser);
//        }
//
//        // 2.判断验证是否通过 一键登录   1验证验证码是否通过,
//
//        if (StringUtil.isBlank(sessionDTO.getUserType()) || sessionDTO.getUserType().equals("NEW_USER")) {
//            try {
//                resultDTO = validCodeService.validCode("calendar", phone, captcha, true);//.checkCaptcha(username, "login", captcha, request);
//
//                if (!resultDTO.isRight()) {
//                    return resultDTO;
//                }
//            } catch (Exception e) {
//                logger.error(e.getMessage());
//                return ResultUtil.getResult(serviceCode, LogType.INFO, 108, null,
//                        e.getMessage());
//            }
//        }
//
//
//        //调用认证记录接口查询用户信息
//        LogUtil.track(serviceCode, 101, phone + "|"
//                        + sessionDTO.getDeviceId() + "|" + sessionDTO.getUserMac(),
//                "before userAuthServiceApi.queryUserAuthByParam", phone);
//        //在发送短信的时候已经发送过了 这里反查是为了检查是否发送过短信
//        //根据手机号码反查用户信息 userid
//        //2验证手机号是否在auth中存在 没有的话报错
//        SysUser sysUser = userService.getUserByTelno(phone);//.getUserByTelno(phone);
//
//       if(sysUser!=null) {
//           sessionUser.setId(sysUser.getId());
//           sessionUser.setAuthPswd(sysUser.getPassword());
//           sessionUser.setFace(sysUser.getFace());
//           sessionUser.setPhone(sysUser.getTelno());
//           sessionUser.setAddress(sysUser.getAddress());
//           sessionUser.setNick(sysUser.getNkname());
//           sessionUser.setBirthday(sysUser.getBirth()!=null?sysUser.getBirth().getTime():null);
//
//       }
//        //2验证手机号是否在auth中存在 没有的话报错
//        //3验证手机号码在base 中是否存在 没有的话报错
//
//        //4查询昵称是否存在 5检查用户商户角色放入sessionUser中的role
//        //6如果不是商户管理员检查用户是否是代理管理员 7初始化用户上网时长信息 8 存入 最近的用户 手机+ 用户
//        if (thirdUserService != null) {//这里有一个同步机制 就是 所有的登录用户信息都保存在本地库 ,登录的时候直接去本地库获取用户信息,
//            thirdUserService.loginByPhoneAndSMS(sessionDTO,phone,captcha);
//        }
//        return ResultUtil.getResult();
//    }



}
