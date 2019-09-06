import com.dozenx.common.Path.PathManager;
import com.dozenx.common.config.Config;
import com.dozenx.common.config.EmailConfig;
import com.dozenx.common.mail.MailSenderInfo;
import com.dozenx.common.mail.SimpleMailSender;
import com.dozenx.common.util.DateUtil;
import com.dozenx.web.module.email.email.service.EmailSendService;

import javax.annotation.Resource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

/**
 * @Author: dozen.zhang
 * @Description:
 * @Date: Created in 17:26 2019/2/21
 * @Modified By:
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"classpath*:/config/xml/applicationContext.xml"})
public class TestEmailService {
    @Resource
    EmailSendService emailSendService;

   // @Test
    public void sendEmail() {
        String toEmail = "13958173965@189.cn";
        String title = "来自TestEmailService的单元测试,不用回复";
        String content = "来自TestEmailService的单元测试,日期:" + DateUtil.getNow();
        String file = "g:\\corp\\20180615WiFi运营中心通讯录.xlsx";
        String filePath = PathManager.getInstance().getHomePath().resolve("pom.xml").toString();
//        emailSendService.sendEmail(toEmail, title,content );
        //  System.setProperty("mail.mime.splitlongparameters","false");
        emailSendService.sendEmail(toEmail, title, content, filePath);
        toEmail = "371452875@qq.com";//测试qq邮箱能否收到
        // emailSendService.sendEmail(toEmail,title,content,filePath);
        emailSendService.sendEmail(toEmail, title, content);
        //  toEmail = "likegadfly@163.com";//测试163邮箱能否收到
        // emailSendService.sendEmail(toEmail, title, content);


    }

  //  @Test
    /**
     * 测试发送ssl邮件
     */
    public void testSendSSLMail() {

        Config config = Config.getInstance();
        // 发件人电子邮箱
        final String from = "colamachine@163.com";//"likegadfly@163.com";
        // 发件人电子邮箱密码
        final String pass = "dapark123";
        // 指定发送邮件的主机为 smtp.qq.com
        // String host = "smtp.163.com"; // 邮件服务器
        // 获取系统属性
        EmailConfig emailConfig =Config.getInstance().getEmail();
        Properties properties = System.getProperties();
        properties.setProperty("mail.transport.protocol", "smtp");// 设置传输协议
        properties.put("mail.smtp.host", "smtp.163.com");// 设置发信邮箱的smtp地址
        properties.put("mail.smtp.auth", true); // 验证
        properties.put("mail.debug", true);//便于调试
        properties.put("mail.smtp.ssl.enable", true);//便于调试
        // 设置邮件服务器
        properties.put("mail.smtp.port", 465);
//        properties.put("mail.smtp.ssl.enable", "true");
//        MailSSLSocketFactory sf = new MailSSLSocketFactory();
//        properties.put("mail.smtp.ssl.socketFactory", sf);
//        sf.setTrustAllHosts(true);
        // 获取默认session对象
        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() { // qq邮箱服务器账户、第三方登录授权码
                return new PasswordAuthentication(from, pass); // 发件人邮件用户名、密码
            }
        });
        try {
            // 创建默认的 MimeMessage 对象
            MimeMessage message = new MimeMessage(session);
            // Set From: 头部头字段
            message.setFrom(new InternetAddress(from));
            // Set To: 头部头字段
            // 设置收件人
            message.addRecipient(Message.RecipientType.TO, new InternetAddress("371452875@qq.com"));
            // Set Subject: 主题文字
            message.setSubject(MimeUtility.encodeText("biaoti", MimeUtility.mimeCharset("utf-8"), null));
            // 创建消息部分
            BodyPart textMimeBodyPart = new MimeBodyPart();
            // 消息
            textMimeBodyPart.setText(MimeUtility.encodeText("hello", MimeUtility.mimeCharset("utf-8"), null));
            /**MimeMessage
             *          ->MimeMultipart
             *                  ->MimeBodyPart   textMimeBodyPart
             *                                  ->text 文本消息
             *                  ->MimeBodyPart   fileMimeBodyPart
             *                                  ->FileName ==>文件名
             *                                  ->DataHandler ==> file
             */
            // 创建多重消息
            Multipart multipart = new MimeMultipart();
            // 设置文本消息部分
            multipart.addBodyPart(textMimeBodyPart);
           /* // 附件部分

            BodyPart fileMimeBodyPart = new MimeBodyPart();
            // 设置要发送附件的文件路径
            DataSource source = new FileDataSource(filePath);
            fileMimeBodyPart.setDataHandler(new DataHandler(source));
            // messageBodyPart.setFileName(filename);
            // 处理附件名称中文（附带文件路径）乱码问题
            fileMimeBodyPart.setFileName(MimeUtility.encodeText(filename));
            multipart.addBodyPart(fileMimeBodyPart);*/
            // 发送完整消息
            message.setContent(multipart);
            // 发送消息
            Transport.send(message);
            // System.out.println("Sent message successfully....");
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    //@Test
    public void main() {
        //这个类主要是设置邮件
        MailSenderInfo mailInfo = new MailSenderInfo();
        mailInfo.setMailServerHost("smtp.163.com:");
        mailInfo.setMailServerPort("465");
        mailInfo.setValidate(true);
        mailInfo.setUserName("likegadfly");
        mailInfo.setPassword("xxxxxxxx");//您的邮箱密码  dgy123
        mailInfo.setFromAddress("colamachine@163.com");
        mailInfo.setToAddress("371452875@qq.com");
        mailInfo.setSubject("1设置邮箱标题 如http://www.guihua.org 中国桂花网");
        mailInfo.setContent("1设置邮箱内容 如http://www.guihua.org 中国桂花网 是中国最大桂花网站==");
        mailInfo.setSsl(true);
        //这个类主要来发送邮件
        SimpleMailSender sms = new SimpleMailSender();
        sms.sendTextMail(mailInfo);//发送文体格式
        try {
            sms.sendHtmlMail(mailInfo);//发送html格式
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        System.out.println("发送完毕");
    }
}
