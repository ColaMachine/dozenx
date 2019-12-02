/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明:
 */

package com.dozenx.web.module.email.email.service.impl;

import com.dozenx.common.exception.BizException;
import com.dozenx.common.mail.MailSenderInfo;
import com.dozenx.common.mail.SimpleMailSender;
import com.dozenx.common.util.StringUtil;
import com.dozenx.web.core.base.BaseService;
import com.dozenx.web.module.email.email.dao.EmailMapper;
import com.dozenx.web.module.email.email.service.EmailSendService;
import com.dozenx.web.module.email.email.service.EmailService;
import com.dozenx.web.util.ConfigUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.List;

@Service("emailSendService")
public class EmailSendServiceImpl extends BaseService implements EmailSendService {
    private static final Logger logger = LoggerFactory
            .getLogger(EmailService.class);
    @Resource
    private EmailMapper emailMapper;
    //配置好的邮件参数
    private MailSenderInfo globalMailSenderInfo;

    @Override
    public void sendEmail(String to, String title, String content) {
        MailSenderInfo mailSenderInfo = getMailInfoFromConfig();
        SimpleMailSender simpleMailSender = new SimpleMailSender();
        mailSenderInfo.setSubject(title);
        mailSenderInfo.setToAddress(to);
        mailSenderInfo.setContent(content);
        try {
            simpleMailSender.sendHtmlMail(mailSenderInfo);
        } catch (MessagingException e) {
            logger.error("", e);
            throw new BizException(30105197, "发送邮件报错");
        } catch (UnsupportedEncodingException e) {
            logger.error("", e);
            throw new BizException(30105197, "发送邮件报错");
        }

        //保存操作记录


    }

    @Override
    public void sendEmail(String to, String title, String content, String file) {
        MailSenderInfo mailSenderInfo = getMailInfoFromConfig();
        mailSenderInfo.setToAddress(to);
        SimpleMailSender simpleMailSender = new SimpleMailSender();
        mailSenderInfo.setSubject(title);
        if (StringUtil.isNotBlank(file)) {
            mailSenderInfo.setAttachFileNames(new String[]{file});
        }
        mailSenderInfo.setContent(content);
        try {
            simpleMailSender.sendHtmlMail(mailSenderInfo);
        } catch (MessagingException e) {
            logger.error("", e);
            throw new BizException(30105197, "发送邮件报错");
        } catch (UnsupportedEncodingException e) {
            logger.error("", e);
            throw new BizException(30105197, "发送邮件报错");
        }

    }

    @Override
    public void sendEmail(List<String> toList, String title, String content, String file) {
        MailSenderInfo mailSenderInfo = getMailInfoFromConfig();//从配置configUtil里读取邮件配置
        mailSenderInfo.setToAddressList(toList);//设置发送目的地
        SimpleMailSender simpleMailSender = new SimpleMailSender();
        mailSenderInfo.setSubject(title);
        if (StringUtil.isNotBlank(file)) {
            mailSenderInfo.setAttachFileNames(new String[]{file});
        }
        mailSenderInfo.setContent(content);
        try {
            simpleMailSender.sendHtmlMail(mailSenderInfo);
        } catch (MessagingException e) {
            logger.error("", e);
            throw new BizException(30105197, "发送邮件报错");
        } catch (UnsupportedEncodingException e) {
            logger.error("", e);
            throw new BizException(30105197, "发送邮件报错");
        }

    }

    //从配置文件获取参数并设置mailSenderInfo 并准备发送邮件
    public MailSenderInfo getMailInfoFromConfig() {
        if (globalMailSenderInfo == null) {
            MailSenderInfo tempMailSenderInfo = new MailSenderInfo(ConfigUtil.getConfig("email.host"),//主机
                    Integer.valueOf(ConfigUtil.getConfig("email.port")),//端口
                    ConfigUtil.getConfig("email.username"),//用户名
                    ConfigUtil.getConfig("email.pwd"),//密码
                    ConfigUtil.getConfig("email.user"));
            if (("true").equals(ConfigUtil.getConfig("email.ssl"))) {//如果启动了ssl 要设置为true
                logger.info("use ssl");
                tempMailSenderInfo.setSsl(true);
            }
            tempMailSenderInfo.setDebug(ConfigUtil.getConfig("email.debug"));
            this.globalMailSenderInfo = tempMailSenderInfo;
        }
        logger.info("email.ssl"+ConfigUtil.getConfig("email.ssl"));
        //复制一个唯一的对象给发送邮件用
        MailSenderInfo mailSenderInfo = globalMailSenderInfo.clone();
        return mailSenderInfo;
    }
}
