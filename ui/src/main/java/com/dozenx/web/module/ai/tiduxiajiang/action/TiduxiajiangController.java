package com.dozenx.web.module.ai.tiduxiajiang.action;

import com.cpj.swagger.annotation.API;
import com.cpj.swagger.annotation.APIs;
import com.cpj.swagger.annotation.DataType;
import com.cpj.swagger.annotation.Param;
import com.dozenx.util.DateUtil;
import com.dozenx.util.StringUtil;
import com.dozenx.web.core.base.BaseController;
import com.dozenx.web.core.log.ResultDTO;
import com.dozenx.web.core.page.Page;
import com.dozenx.web.module.pubImage.bean.PubImage;
import com.dozenx.web.module.pubImage.service.PubImageService;
import com.dozenx.web.util.RequestUtil;
import com.dozenx.web.util.ResultUtil;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @Author: dozen.zhang
 * @Description:
 * @Date: Created in 18:13 2018/11/21
 * @Modified By:
 */
@APIs(description = "用户")
@Controller
public class TiduxiajiangController extends BaseController {
    /**
     * 日志
     **/
    private Logger logger = LoggerFactory.getLogger(TiduxiajiangController.class);
    /**
     * 权限service
     **/
    @Autowired
    private PubImageService pubImageService;


    /**
     * 说明:ajax请求PubImage信息
     *
     * @return String
     * @author dozen.zhang
     * @date 2018-9-4 8:29:19
     */
    @API(summary = "用户列表接口",
            description = "用户列表接口",
            parameters = {
                    @Param(name = "pageSize", description = "分页大小", dataType = DataType.INTEGER, required = true),
                    @Param(name = "curPage", description = "当前页", dataType = DataType.INTEGER, required = true),
                    @Param(name = "id", description = "编号", dataType = DataType.INTEGER, required = false),// false
                    @Param(name = "path", description = "文件的绝对路径", dataType = DataType.STRING, required = false),// false
                    @Param(name = "oriName", description = "原始名称", dataType = DataType.STRING, required = false),// false
                    @Param(name = "name", description = "文件名称", dataType = DataType.STRING, required = false),// false
                    @Param(name = "remark", description = "备注", dataType = DataType.STRING, required = false),// false
                    @Param(name = "absPath", description = "绝对路径", dataType = DataType.STRING, required = false),// false
                    @Param(name = "relPath", description = "相对路径", dataType = DataType.STRING, required = false),// false
                    @Param(name = "figure", description = "指纹", dataType = DataType.STRING, required = false),// false
                    @Param(name = "uploadIp", description = "照片上传时的Ip", dataType = DataType.STRING, required = false),// false
                    @Param(name = "creator", description = "上传照片人的Id", dataType = DataType.STRING, required = false),// false
                    @Param(name = "creatorName", description = "上传人的姓名", dataType = DataType.STRING, required = false),// false
                    @Param(name = "createDate", description = "上传照片的时间", dataType = DataType.DATE, required = false),// false
                    @Param(name = "lastModify", description = "照片的创建时间", dataType = DataType.DATE, required = false),// false
                    @Param(name = "status", description = "照片的状态 0 使用状态 1 移除状态 9 彻底删除状态", dataType = DataType.INTEGER, required = false),// false
                    @Param(name = "order", description = "顺序id", dataType = DataType.INTEGER, required = false),// false
                    @Param(name = "pid", description = "父组件id", dataType = DataType.INTEGER, required = false),// false
            })
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public ResultDTO list(HttpServletRequest request) throws Exception {
        Page page = RequestUtil.getPage(request);
        if (page == null) {
            return this.getWrongResultFromCfg("err.param.page");
        }

        HashMap<String, Object> params = new HashMap<String, Object>();
        String id = request.getParameter("id");
        if (!StringUtil.isBlank(id)) {
            params.put("id", id);
        }
        String path = request.getParameter("path");
        if (!StringUtil.isBlank(path)) {
            params.put("path", path);
        }
        String pathLike = request.getParameter("pathLike");
        if (!StringUtil.isBlank(pathLike)) {
            params.put("pathLike", pathLike);
        }
        String oriName = request.getParameter("oriName");
        if (!StringUtil.isBlank(oriName)) {
            params.put("oriName", oriName);
        }
        String oriNameLike = request.getParameter("oriNameLike");
        if (!StringUtil.isBlank(oriNameLike)) {
            params.put("oriNameLike", oriNameLike);
        }
        String name = request.getParameter("name");
        if (!StringUtil.isBlank(name)) {
            params.put("name", name);
        }
        String nameLike = request.getParameter("nameLike");
        if (!StringUtil.isBlank(nameLike)) {
            params.put("nameLike", nameLike);
        }
        String remark = request.getParameter("remark");
        if (!StringUtil.isBlank(remark)) {
            params.put("remark", remark);
        }
        String remarkLike = request.getParameter("remarkLike");
        if (!StringUtil.isBlank(remarkLike)) {
            params.put("remarkLike", remarkLike);
        }
        String absPath = request.getParameter("absPath");
        if (!StringUtil.isBlank(absPath)) {
            params.put("absPath", absPath);
        }
        String absPathLike = request.getParameter("absPathLike");
        if (!StringUtil.isBlank(absPathLike)) {
            params.put("absPathLike", absPathLike);
        }
        String relPath = request.getParameter("relPath");
        if (!StringUtil.isBlank(relPath)) {
            params.put("relPath", relPath);
        }
        String relPathLike = request.getParameter("relPathLike");
        if (!StringUtil.isBlank(relPathLike)) {
            params.put("relPathLike", relPathLike);
        }
        String figure = request.getParameter("figure");
        if (!StringUtil.isBlank(figure)) {
            params.put("figure", figure);
        }
        String figureLike = request.getParameter("figureLike");
        if (!StringUtil.isBlank(figureLike)) {
            params.put("figureLike", figureLike);
        }
        String uploadIp = request.getParameter("uploadIp");
        if (!StringUtil.isBlank(uploadIp)) {
            params.put("uploadIp", uploadIp);
        }
        String uploadIpLike = request.getParameter("uploadIpLike");
        if (!StringUtil.isBlank(uploadIpLike)) {
            params.put("uploadIpLike", uploadIpLike);
        }
        String creator = request.getParameter("creator");
        if (!StringUtil.isBlank(creator)) {
            params.put("creator", creator);
        }
        String creatorLike = request.getParameter("creatorLike");
        if (!StringUtil.isBlank(creatorLike)) {
            params.put("creatorLike", creatorLike);
        }
        String creatorName = request.getParameter("creatorName");
        if (!StringUtil.isBlank(creatorName)) {
            params.put("creatorName", creatorName);
        }
        String creatorNameLike = request.getParameter("creatorNameLike");
        if (!StringUtil.isBlank(creatorNameLike)) {
            params.put("creatorNameLike", creatorNameLike);
        }
        String createDate = request.getParameter("createDate");
        if (!StringUtil.isBlank(createDate)) {
            if (StringUtil.checkNumeric(createDate)) {
                params.put("createDate", createDate);
            } else if (StringUtil.checkDateStr(createDate, "yyyy-MM-dd")) {
                params.put("createDate", DateUtil.parseToDate(createDate, "yyyy-MM-dd"));
            }
        }
        String createDateBegin = request.getParameter("createDateBegin");
        if (!StringUtil.isBlank(createDateBegin)) {
            if (StringUtil.checkNumeric(createDateBegin)) {
                params.put("createDateBegin", createDateBegin);
            } else if (StringUtil.checkDateStr(createDateBegin, "yyyy-MM-dd")) {
                params.put("createDateBegin", DateUtil.parseToDate(createDateBegin, "yyyy-MM-dd"));
            }
        }
        String createDateEnd = request.getParameter("createDateEnd");
        if (!StringUtil.isBlank(createDateEnd)) {
            if (StringUtil.checkNumeric(createDateEnd)) {
                params.put("createDateEnd", createDateEnd);
            } else if (StringUtil.checkDateStr(createDateEnd, "yyyy-MM-dd")) {
                params.put("createDateEnd", DateUtil.parseToDate(createDateEnd, "yyyy-MM-dd"));
            }
        }
        String lastModify = request.getParameter("lastModify");
        if (!StringUtil.isBlank(lastModify)) {
            if (StringUtil.checkNumeric(lastModify)) {
                params.put("lastModify", lastModify);
            } else if (StringUtil.checkDateStr(lastModify, "yyyy-MM-dd")) {
                params.put("lastModify", DateUtil.parseToDate(lastModify, "yyyy-MM-dd"));
            }
        }
        String lastModifyBegin = request.getParameter("lastModifyBegin");
        if (!StringUtil.isBlank(lastModifyBegin)) {
            if (StringUtil.checkNumeric(lastModifyBegin)) {
                params.put("lastModifyBegin", lastModifyBegin);
            } else if (StringUtil.checkDateStr(lastModifyBegin, "yyyy-MM-dd")) {
                params.put("lastModifyBegin", DateUtil.parseToDate(lastModifyBegin, "yyyy-MM-dd"));
            }
        }
        String lastModifyEnd = request.getParameter("lastModifyEnd");
        if (!StringUtil.isBlank(lastModifyEnd)) {
            if (StringUtil.checkNumeric(lastModifyEnd)) {
                params.put("lastModifyEnd", lastModifyEnd);
            } else if (StringUtil.checkDateStr(lastModifyEnd, "yyyy-MM-dd")) {
                params.put("lastModifyEnd", DateUtil.parseToDate(lastModifyEnd, "yyyy-MM-dd"));
            }
        }
        String status = request.getParameter("status");
        if (!StringUtil.isBlank(status)) {
            params.put("status", status);
        }
        String order = request.getParameter("order");
        if (!StringUtil.isBlank(order)) {
            params.put("order", order);
        }
        String pid = request.getParameter("pid");
        if (!StringUtil.isBlank(pid)) {
            params.put("pid", pid);
        }

        params.put("page", page);
        List<PubImage> pubImages = pubImageService.listByParams4Page(params);
        return ResultUtil.getResult(pubImages, page);
    }


    public static int[][] getGrayPicture(String filename) throws FileNotFoundException, IOException {
        File file = new File(filename);
        BufferedImage originalImage = ImageIO.read(new FileInputStream(file));
        originalImage.getColorModel();
        System.out.println(originalImage.getColorModel());

        int green = 0, red = 0, blue = 0, rgb;
        int imageWidth = originalImage.getWidth();
        int imageHeight = originalImage.getHeight();
        int[][] heights = new int[imageHeight][imageWidth];
        for (int i = originalImage.getMinX(); i < imageWidth; i++) {
            for (int j = originalImage.getMinY(); j < imageHeight; j++) {
                //ͼƬ�����ص���ʵ�Ǹ�����������������forѭ������ÿ�����ؽ��в���
                Object data = originalImage.getRaster().getDataElements(i, j, null);//��ȡ�õ����أ�����object���ͱ�ʾ

                red = originalImage.getColorModel().getRed(data);
                blue = originalImage.getColorModel().getBlue(data);
                green = originalImage.getColorModel().getGreen(data);
                heights[j][i] = red;
            }

        }
        return heights;

    }

    public static float qiuDaoA0(float a0, float a1, List<Pointf> exampleList) {
        float sum = 0;
        for (Pointf pointf : exampleList) {
            sum += hanshu(pointf.x, a0, a1) - pointf.y;
        }
        return sum / exampleList.size();
    }


    public static float qiuDaoA1(float a0, float a1, List<Pointf> exampleList) {
        float sum = 0;
        for (Pointf pointf : exampleList) {
            sum += (hanshu(pointf.x, a0, a1) - pointf.y) * pointf.x;
        }
        return sum / exampleList.size();
    }

    public static float hanshu(float x, float a0, float a1) {
        return a0 + a1 * x;
    }

    public static float costFunction(float a0, float a1, List<Pointf> exampleList) {
        float sum = 0;
        float m = exampleList.size();
        for (Pointf point : exampleList) {
            sum += (hanshu(point.x, a0, a1) - point.y) * (hanshu(point.x, a0, a1) - point.y);
        }
        return sum / m / 2;
    }

    @Test
    public void test() {
        String pngPath = "C:\\Users\\dozen.zhang\\Desktop\\test.png";
        File file = new File(pngPath);
        List<Pointf> exampleList = new ArrayList<Pointf>();


        float minValue = 0.0005f;
        try {
//            int[][] matrix = getGrayPicture(pngPath);
//            for(int x=0;x<matrix[0].length;x++){
//                for(int y=matrix.length-1;y>=0;y--){
//                    if(matrix[y][x]<10){//说明他是黑色
//                        boolean taijin=false;
//                        for(Pointf point:exampleList){
//                            //如果距离太近不算
//
//                            if(Math.abs(point.x *point.x+ point.y*point.y  -  x*x-y*y ) <minDistance*minDistance ){
//                                taijin=true;
//                                break;
//                            }
//
//                        }
//
//                        if(!taijin){
//                            exampleList.add(new Pointf(x,matrix.length-y));
//                        }
//
//                        if(exampleList.size()>30){
//                            System.out.println("distance 不够长 导致样本过多 请调整distance:"+exampleList.size());
//                            System.exit(0);
//                        }
//                    }
//                }
//            }
//            if(exampleList.size()==0){
//                System.out.println("样本为空 结束");
//                System.exit(0);
//            }
//            System.out.println("example size:"+exampleList.size());
//
//            for(Pointf point: exampleList){
//                System.out.println("point "+point);
//            }
            exampleList.clear();
            exampleList.add(new Pointf(1, 1));
            exampleList.add(new Pointf(2, 2));
            exampleList.add(new Pointf(3, 3));
            //
            System.out.println("得到样本之后开始训练");
            float a0 = 0, a1 = 0;
            float step = 0.1f;
            int index = 0;
            while (true) {
                float xielv0 = qiuDaoA0(a0, a1, exampleList);
                float xielv1 = qiuDaoA1(a0, a1, exampleList);

                System.out.println("xielv0:" + xielv0 + " xielv1:" + xielv1);
                a0 = a0 - step * xielv0;
                a1 = a1 - step * xielv1;
                ;
                System.out.println("theta_0:" + a0 + "  theta_1:" + a1);
                float cost = costFunction(a0, a1, exampleList);
                System.out.println("costFUnction:" + cost);
                if (cost < minValue) {
                    System.out.println(" final result theta_0:" + a0 + " theta_1:" + a1);
                    break;
                }


                System.out.println("第" + (index++) + "次");
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
            }
//            BufferedImage image = ImageIO.read(file);
            //获取灰度图片
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}