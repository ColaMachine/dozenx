/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: @date 2018-10-24 23:32:59
 * 文件说明: 
 */

package com.dozenx.web.module.goods.action;

import com.cpj.swagger.annotation.*;
import com.dozenx.core.Path.PathManager;
import com.dozenx.core.config.SysConfig;
import com.dozenx.core.exception.BizException;
import com.dozenx.core.exception.ParamException;
import com.dozenx.util.*;
import com.dozenx.web.core.Constants;
import com.dozenx.web.core.auth.session.SessionUser;
import com.dozenx.web.core.base.BaseController;
import com.dozenx.web.core.log.ResultDTO;
import com.dozenx.web.core.page.Page;
import com.dozenx.web.core.rules.*;
import com.dozenx.web.module.goods.bean.Goods;
import com.dozenx.web.module.goods.service.GoodsService;
import com.dozenx.web.util.RequestUtil;
import com.dozenx.web.util.ResultUtil;
import com.dozenx.web.util.ValidateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.nio.file.Files;
import java.sql.Timestamp;
import java.util.*;

@APIs(description = "商品")
@Controller
@RequestMapping("/chat")
public class ChatController extends BaseController{
    /** 日志 **/
    private Logger logger = LoggerFactory.getLogger(ChatController.class);
    /** 权限service **/
    @Autowired
    private GoodsService goodsService;

    @Autowired
    ChatAction chatAction;
    
    HashMap chatSession =new HashMap();
    HashMap wattingRoom =new HashMap();


    /**
     * 开始配对
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/match" , method = RequestMethod.GET)
    @ResponseBody
    public ResultDTO match(HttpServletRequest request) throws Exception{
      SessionUser sessionUser = (SessionUser) request.getSession().getAttribute(Constants.SESSION_USER);
//       String userName =  sessionUser.getUserName();
//        chatSession.remove(userName);//重新开始匹配了的话 从已匹配中离开
//        if(wattingRoom.get(userName)!=null){//已经在等待了
//
//
//            return this.getResult(40101001,"正在等待");
//
//
//        }
//
//        //寻找可以匹配的人物
//
//        wattingRoom.put(userName,userName); //开始排队等待
        ChatSessionUser chatSessionUser = ChatRegistry.getSessionUser(sessionUser.getUserId());
        if(chatSessionUser==null){
            chatSessionUser = new ChatSessionUser();
            chatSessionUser.setUserId(sessionUser.getUserId());
            chatSessionUser.setFace(sessionUser.getFace());
            chatSessionUser.setUserName(sessionUser.getUserName());
            ChatRegistry.sessionUserMap.put(sessionUser.getUserId(),chatSessionUser);
        }
        chatAction.joinRandom(chatSessionUser);
        return this.getResult(0,"正在排队中");

    }
    @RequestMapping(value = "/status" , method = RequestMethod.GET)
    @ResponseBody
    public ResultDTO  status(HttpServletRequest request){
        SessionUser sessionUser = (SessionUser) request.getSession().getAttribute(Constants.SESSION_USER);
        ChatSessionUser chatSessionUser = ChatRegistry.getSessionUser(sessionUser.getUserId());
        return chatAction.status(chatSessionUser);
    }

    @RequestMapping(value = "/quit" , method = RequestMethod.GET)
    @ResponseBody
    public ResultDTO  quit(HttpServletRequest request){
        SessionUser sessionUser = (SessionUser) request.getSession().getAttribute(Constants.SESSION_USER);
        ChatSessionUser chatSessionUser = ChatRegistry.getSessionUser(sessionUser.getUserId());
        return chatAction.quit(chatSessionUser);
    }

    //定时任务
    public void tictack(){
        chatAction.pair();//智能组队
    }

//    @RequestMapping(value = "/sendMsg" , method = RequestMethod.GET)
//    @ResponseBody
//    public ResultDTO sendMsg(HttpServletRequest request) throws Exception{
//        SessionUser sessionUser = (SessionUser) request.getSession().getAttribute(Constants.SESSION_USER);
//        ChatSessionUser chatSessionUser = ChatRegistry.getSessionUser(sessionUser.getUserId());
//
//        chatAction.sendMsg();
//        String userName =  sessionUser.getUserName();
//        chatSession.remove(userName);//重新开始匹配了的话 从已匹配中离开
//        if(wattingRoom.get(userName)!=null){//已经在等待了
//            return this.getResult(40101001,"正在等待");
//        }
//        wattingRoom.put(userName,userName); //开始排队等待
//        return this.getResult(40101001,"加入成功");
//
//    }
    //发送消息通过 websocket 来进行

}
