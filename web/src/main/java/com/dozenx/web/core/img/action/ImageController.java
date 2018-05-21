/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年12月19日
 * 文件说明: 
 */
package com.dozenx.web.core.img.action;

import com.dozenx.core.Path.PathManager;
import com.dozenx.core.config.Config;
import com.dozenx.util.ImageUtil;
import com.dozenx.web.core.base.BaseController;
import com.dozenx.web.util.RedisUtil;
import com.dozenx.web.util.ResultUtil;
import com.dozenx.util.StringUtil;
import com.dozenx.web.core.log.ResultDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;



@Controller
@RequestMapping("/image")
public class ImageController extends BaseController{
    
    static final int[][] sizes= { { 640, 640, 1242, 480, 720, 1080 },
        { 86, 100, 200, 77, 112, 168 }, { 640, 640, 1242, 480, 720, 1080 },
        { 86, 100, 200, 77, 112, 168 }, { 640, 640, 1242, 480, 720, 1080 },
        { 233, 280, 532, 213, 310, 465 } };


/*
static final int[][]imgsizes={{
640,86,
640,100,
1242,200,
480,77,
720,112,
1080,168},
{
    640,86,
    640,100,
    1242,200,
    480,77,
    720,112,
    1080,168},
    {
        640,233,
        640,280,
        1242,532,
        480,213,
        720,310,
        1080,465
        }
};
static final int[]phonesizes={
640,960,
640,1024,
1242,2080,
480,640,
720,800,
1080,1920};
*/
    @RequestMapping(value = "/upload.json")
    @ResponseBody
    public Object upload(HttpServletRequest request) throws IOException {
        String imageNam12e = request.getParameter("imageName");
        String imageData = request.getParameter("imageData");
        //imageData=URLDecoder.decode(imageData);
     //  imageData= URLEncoder.encode(imageData);
      String imageName = ImageUtil.saveBase64Image(PathManager.getInstance().getImagePath().toFile().getAbsolutePath(), "", imageData);
      return  this.getResult(Config.getInstance().getImage().getServerUrl()+"/"+imageName);

    }
    @RequestMapping(value = "/uploadSubmit.json")
    @ResponseBody
    public Object uploadSubmit( @RequestParam(value="pic1")MultipartFile image){


        int index=0;
        // 校验文件类型是否正确
        long fileSize = image.getSize();
        String type = image.getContentType();
        if (!StringUtil.isBlank(type)) {
            if (type.equals("image/jpeg") || type.equals("image/png")
                    || type.equals("image/bmp")) {
            } else {
                return ResultUtil.getResult(0, "图片格式不正确");
            }
        } else {
            return ResultUtil.getResult(0, "图片格式不正确");
        }
        type = type.split("/")[1];

        // 如果用的是Tomcat服务器，则文件会上传到\\%TOMCAT_HOME%\\webapps\\YourWebProject\\WEB-INF\\upload\\文件夹中
        String uploadpath = PathManager.getInstance().getImagePath().toFile().getAbsolutePath();
        // 这里不必处理IO流关闭的问题，因为FileUtils.copyInputStreamToFile()方法内部会自动把用到的IO流关掉，我是看它的源码才知道的
        BufferedImage img = null;
        String fileName="";
      try {
        img = ImageIO.read(image.getInputStream());
        if (img == null || img.getWidth(null) <= 0
                || img.getHeight(null) <= 0) {
            return ResultUtil.getResult(0,"上传的图片为空");
        }
 
        // 检查宽高是否符合指定的宽高
        int sizes_index = 0;
      img=ImageUtil.fixSize(img, 600, 300);
      /*  if (img.getWidth(null) != sizes[sizes_index][index]) {
            return ResultUtil.getResult(0,"图片" + (index + 1) + "的宽度应该为"
                    + sizes[sizes_index][index] + ",所传图片宽度为"
                    + img.getWidth(null) + ",请修改后重新上传");
        }
        if (img.getHeight(null) != sizes[sizes_index + 1][index]) {
            return ResultUtil.getResult(0,"图片" + (index + 1) + "的高度应该为"
                    + sizes[sizes_index + 1][index] + ",所传图片高度为"
                    + img.getHeight(null) + ",请修改后重新上传");
        }*/
        
        if (image.getSize() > Config.getInstance().getImage().getMaxSize() * 1024) {
            ResultUtil.getResult(0,"图片尺寸大小:"+(new
                   DecimalFormat("####.##").format((image.getSize()/1024)))+"KB 大于限制"+ Config.getInstance().getImage().getMaxSize()+"kb上传失败");
        }
        // 进行保存到数据库
         fileName = System.currentTimeMillis() + "."+Config.getInstance().getImage().getType() ;
        ImageIO.write(img, Config.getInstance().getImage().getType(), new File(uploadpath,  fileName ));
      }catch(Exception e){
          return ResultUtil.getResult(0,e.getMessage());
      }
        return  ResultUtil.getResult(1,Config.getInstance().getImage().getServerUrl()+"/"+fileName,"上传成功");
    
    }

    private ResultDTO readBufferedImageFromMultipartFile(MultipartFile image){
        int index=0;
        // 校验文件类型是否正确
        long fileSize = image.getSize();
        String type = image.getContentType();
        if (!StringUtil.isBlank(type)) {
            if (type.equals("image/jpeg") || type.equals("image/png")
                    || type.equals("image/bmp")) {
            } else {
                return ResultUtil.getResult(0, "图片格式不正确");
            }
        } else {
            return ResultUtil.getResult(0, "图片格式不正确");
        }
        type = type.split("/")[1];
        // 如果用的是Tomcat服务器，则文件会上传到\\%TOMCAT_HOME%\\webapps\\YourWebProject\\WEB-INF\\upload\\文件夹中
        String uploadpath = PathManager.getInstance().getImagePath().toFile().getAbsolutePath();
        // 这里不必处理IO流关闭的问题，因为FileUtils.copyInputStreamToFile()方法内部会自动把用到的IO流关掉，我是看它的源码才知道的
        BufferedImage img = null;
        String fileName="";
        try {
            img = ImageIO.read(image.getInputStream());
            if (img == null || img.getWidth(null) <= 0
                    || img.getHeight(null) <= 0) {
                return ResultUtil.getResult(0,"上传的图片为空");
            }
        }catch(Exception e){
            return ResultUtil.getResult(0,e.getMessage());
        }
        return ResultUtil.getDataResult(img);

    }
  /*  @RequestMapping(value = "/uploadToRedis.json")
    @ResponseBody
    public ResultDTO imageToRedis(@RequestParam(value="pic1")MultipartFile image){

        String imagePth ="C:\\Users\\dozen.zhang\\Pictures\\parent.png";
        try {
            BufferedImage bufferedImage = ImageIO.read(new File(imagePth));

            ByteArrayOutputStream outputStream =new ByteArrayOutputStream();
            ImageIO.write( bufferedImage,"png", outputStream);

            byte[] bytes = new byte[bufferedImage.getWidth()*bufferedImage.getHeight()*3];
            int width =bufferedImage.getWidth();
            int height = bufferedImage.getHeight();


            for(int j=0;j<bufferedImage.getHeight();j++){
                for(int i=0;i<bufferedImage.getWidth();i++){
                    Object data = bufferedImage.getRaster().getDataElements(i, j, null);//��ȡ�õ����أ�����object���ͱ�ʾ
                    int red = bufferedImage.getColorModel().getRed(data);
                    int blue = bufferedImage.getColorModel().getBlue(data);
                    int green = bufferedImage.getColorModel().getGreen(data);
                    bytes[(j*width+i)*3+0]= (byte)(red);
                    bytes[(j*width+i)*3+1]= (byte)(green);
                    bytes[(j*width+i)*3+2]= (byte)(blue);
                }
            }
            RedisUtil.setByteAry("hello",bytes);
            System.out.print(bytes.length);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

}