package com.dozenx.web.core.auth.service;

import com.dozenx.web.core.auth.session.SessionUser;

/**
 * @Author: dozen.zhang
 * @Description:
 * @Date: Created in 17:15 2018/3/16
 * @Modified By:
 */
public interface IThirdUserService {
    public SessionUser getUserByPhone(String phone)throws  Exception;

    public SessionUser getUserById(Long id);

    public void updateUser(SessionUser sessionUser) throws Exception;
    public void addUser(SessionUser sessionUser) throws Exception;
    public void loginByPhoneAndSMS(/*SessionDTO sessionDTO, */String phone, String captcha) throws Exception;
}
