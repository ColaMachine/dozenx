package com.dozenx.web.core.index.action;

import com.dozenx.core.config.SysConfig;
import com.dozenx.web.core.annotation.RequiresAdmin;
import com.dozenx.web.core.base.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: dozen.zhang
 * @Description:
 * @Date: Created in 17:26 2018/3/28
 * @Modified By:
 */
@Controller

public class IndexController extends BaseController {

    @RequestMapping(value = "/index.htm", method = RequestMethod.GET)
    public ModelAndView index(HttpServletRequest request, Model model) {
       return new ModelAndView( "index","path",SysConfig.PATH);
    }

    /**
     * 管理页面
     * @param request
     * @param model
     * @return
     */
    @RequiresAdmin
    @RequestMapping(value = "/manage.htm", method = RequestMethod.GET)
    public ModelAndView manage(HttpServletRequest request, Model model) {
        return new ModelAndView( "manage","path",SysConfig.PATH);
    }


}
