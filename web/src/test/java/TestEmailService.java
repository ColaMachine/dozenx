import com.dozenx.core.Path.PathManager;
import com.dozenx.util.DateUtil;
import com.dozenx.web.module.email.email.service.EmailSendService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * @Author: dozen.zhang
 * @Description:
 * @Date: Created in 17:26 2019/2/21
 * @Modified By:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/config/xml/applicationContext.xml"})
public class TestEmailService {
    @Resource
    EmailSendService emailSendService;

    @Test
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
        toEmail = "likegadfly@163.com";//测试163邮箱能否收到
        emailSendService.sendEmail(toEmail, title, content);


    }
}
