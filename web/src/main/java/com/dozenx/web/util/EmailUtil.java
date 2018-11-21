package com.dozenx.web.util;



import com.dozenx.util.StringUtil;
import com.dozenx.web.core.mail.MailSenderInfo;
import com.dozenx.web.core.mail.SimpleMailSender;

import javax.mail.MessagingException;

/**
 * Created by dozen.zhang on 2016/5/13.
 */
public class EmailUtil {
    public static void send(String email,String content) throws MessagingException {
        if(StringUtil.isNotBlank(email)) {

            // 发送激活邮件
            MailSenderInfo mailInfo = new MailSenderInfo();
            mailInfo.setMailServerHost("smtp.189.cn");
            mailInfo.setMailServerPort("587");
            mailInfo.setValidate(true);
            mailInfo.setUserName("13958173965@189.cn");
            mailInfo.setPassword("123456qqsh");// 您的邮箱密码
            mailInfo.setFromAddress("13958173965@189.cn");
            mailInfo.setToAddress(email);
            mailInfo.setSubject("ssc财务机器人");
            //mailInfo.setContent("请点击下面的链接进行激活</br><a href=''>http://127.0.0.1:8080/calendar/active.htm?activeid="
            //	+ active.getActiveid() + "</a>");
            mailInfo.setContent(content);
            // 这个类主要来发送邮件
            SimpleMailSender sms = new SimpleMailSender();
            // sms.sendTextMail(mailInfo);//发送文体格式
            try {
                sms.sendHtmlMail(mailInfo);// 发送html格式
            } catch (MessagingException e) {
                e.printStackTrace();
                throw e;
            }
        }

    }

    public static void main(String args[]){
        try {
            EmailUtil.send("371452875@qq.com","test");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
