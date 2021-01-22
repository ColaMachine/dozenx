package com.dozenx.web.core.user.service;

import com.dozenx.web.core.auth.session.SessionUser;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;

/**
 * @Description
 * @Author whc
 * @Date 2020/12/25 11:56
 **/

public interface ThirdUserService {
//    @Autowired
//    private LocationService locationService;
//    @Autowired
//    private FeignUserService feignUserService;

    public SessionUser getUser(HttpServletRequest request);
}
