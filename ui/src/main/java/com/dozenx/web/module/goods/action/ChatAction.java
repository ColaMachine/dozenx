/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: @date 2018-10-24 23:32:59
 * 文件说明:
 */

package com.dozenx.web.module.goods.action;

import com.dozenx.web.core.log.ResultDTO;
import com.dozenx.web.util.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
@Service
public class ChatAction {
    private Logger logger = LoggerFactory.getLogger(ChatAction.class);

    /**
     * 查询状态
     * @param newPlayer
     * @return
     */
    public ResultDTO status(ChatSessionUser newPlayer) {//开始配对


        if(newPlayer==null || newPlayer.getUserId()==null){
            return ResultUtil.getResult(40105003, null, "请登录");
        }
        if(newPlayer==null || newPlayer.getUserId()==null){
            return ResultUtil.getResult(40105003, null, "请登录");
        }
        if(newPlayer.chatRoom!=null ){
            return ResultUtil.getResult(0, null, "开始聊天吧");
        }else{
            return ResultUtil.getResult(1, null, "继续等待吧");
        }


    }
    /*
    随机配对
     */
    public ResultDTO joinRandom(ChatSessionUser newPlayer) {//开始配对

        ChatRegistry.WAITTINGPEOPLE.add(newPlayer);
        newPlayer.status=ChatStatus.wait;
        return ResultUtil.getResult();

    }

    public ResultDTO quit(ChatSessionUser newPlayer) {
        //看看有没有现成的 有的话 就直接加入
        if(newPlayer.chatRoom!=null){
            newPlayer.chatRoom.sendMsg(newPlayer,"对方已经离开了");

            if(newPlayer.chatRoom.a== newPlayer){
                newPlayer.chatRoom.a=null;
            }else if(newPlayer.chatRoom.b== newPlayer){
                newPlayer.chatRoom.b=null;
            }
            newPlayer.chatRoom.status=-1;//关闭
            newPlayer.chatRoom=null;
        }
        return ResultUtil.getResult();
        //如果没有的话就自己创建一个房间 并放入到 未满的房间队列
    }

    /**
     * 开始配对
     * @return
     */
    public ResultDTO pair(){
        //开始配对

        while(true){
            if(ChatRegistry.WAITTINGPEOPLE.peek()!=null){
                ChatSessionUser user = ChatRegistry.WAITTINGPEOPLE.poll();
                if(user.isActive()){
                    //再找一个人

                    while (true) {

                        if(!user.isActive()){//用户
                            logger.info("再次检测 用户a已经掉线了 开始新一对的匹配");
                            break ;//开始下一对的匹配
                        }

                        ChatSessionUser another = ChatRegistry.WAITTINGPEOPLE.peek();

                        if (another != null) {
                            if (another.isActive()) {
                                logger.info("=======用户b仍然在线开始聊天吧========");
                                Only2ChatRoom chatRoom = new Only2ChatRoom(user, another);
                                break;//开始下一对的匹配
                            } else {//找寻下一个可用yonghu 再次循环 容易死循环
                                logger.info("用户b已经掉线了 开始查找下一个匹配者");
                                ChatRegistry.removeUser(user);
                            }
                        }


                    }
                }else{
                    //过滤掉当前用户了
                    logger.info("用户a已经掉线了 开始新一对的匹配");
                    ChatRegistry.removeUser(user);
                }

            }
        }

    }


}