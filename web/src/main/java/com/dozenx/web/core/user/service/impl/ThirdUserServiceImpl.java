package com.dozenx.web.core.user.service.impl;

import com.dozenx.web.core.auth.session.SessionUser;
import com.dozenx.web.core.user.service.ThirdUserService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description
 * @Author whc
 * @Date 2020/12/25 11:56
 **/
@Service
public class ThirdUserServiceImpl implements ThirdUserService {
//    @Autowired
//    private LocationService locationService;
//    @Autowired
//    private FeignUserService feignUserService;

    public SessionUser getUser(HttpServletRequest request) {
         return null;
    }
}
