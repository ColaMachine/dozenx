import com.dozenx.common.Path.PathManager;
import com.dozenx.common.util.FileUtil;
import com.dozenx.common.util.HttpRequestUtil;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.File;
import java.net.URLEncoder;
import java.util.HashMap;

/**
 * @Author: dozen.zhang
 * @Description:
 * @Date: Created in 16:36 2018/12/5
 * @Modified By:
 */
public class TestUpgradeController {

    public void exec(){
        String pkgUrl = "http://ezhike.51awifi.com/ezhikesrv/sys/upgrade/exec";
        HashMap map =new HashMap<>();
        map.put("cmd",URLEncoder.encode("chmod"));
        map.put("path",URLEncoder.encode(""));
        try {
            System.out.println(PathManager.getInstance().getHomePath());
            HttpRequestUtil.saveFileFromUrl(pkgUrl, "a.zip", PathManager.getInstance().getHomePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
        File file = PathManager.getInstance().getHomePath() .resolve( "a.zip").toFile();
        if (file.exists()) {
            System.out.println("hello");

            byte[] bts = FileUtil.getBytes(file);
            String md5 = DigestUtils.md5Hex(bts);
            String checkMd5 = md5 + bts.length;
            String version="a";
            System.out.println("alpha-ad.51awifi.com/advertsrv/sys/upgrade/upload?version=a&pkgUrl=" + URLEncoder.encode(pkgUrl) + "&md5=" + checkMd5);
        } else {
            System.out.println("file not exist");
        }

    }
}
