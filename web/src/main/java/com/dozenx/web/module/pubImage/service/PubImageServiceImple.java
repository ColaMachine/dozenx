/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明:
 */

package com.dozenx.web.module.pubImage.service;

import com.dozenx.common.Path.PathManager;
import com.dozenx.common.config.Config;
import com.dozenx.common.exception.BizException;
import com.dozenx.common.util.*;
import com.dozenx.web.core.log.ResultDTO;
import com.dozenx.web.module.pubImage.bean.PubImage;
import com.dozenx.web.module.pubImage.dao.PubImageMapper;
import com.dozenx.web.util.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("pubImageService")
@Transactional
public class PubImageServiceImple implements PubImageService {
    private static final Logger logger = LoggerFactory
            .getLogger(PubImageServiceImple.class);
    @Resource
    private PubImageMapper pubImageMapper;

    /**
     * 说明:list by page and params根据参数返回列表
     * @return List<HashMap>
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    @Override
    public List<PubImage> listByParams4Page(HashMap params) {
        return pubImageMapper.listByParams4Page(params);
    }

    @Override
    public List<PubImage> listByParams(HashMap params) {
        return pubImageMapper.listByParams(params);
    }

    /**
     * 说明:countByParams 根据参数提取个数
     * @return int
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    @Override
    public int countByParams(HashMap params) {
        return pubImageMapper.countByParams(params);
    }

    /*
     * 说明:
     * @param PubImage
     * @return
     * @return Object
     * @author dozen.zhang
     * @date 2015年11月15日下午1:33:54
     */
    @Override
    public ResultDTO save(PubImage pubImage) {
        // 进行字段验证
      /* ValidateUtil<PubImage> vu = new ValidateUtil<PubImage>();
        ResultDTO result = vu.valid(pubImage);
        if (result.getR() != 1) {
            return result;
        }*/
        //逻辑业务判断判断
        //判断是否有uq字段

        //判断是更新还是插入
        if (pubImage.getId() == null || this.selectByPrimaryKey(pubImage.getId()) == null) {

            pubImageMapper.insert(pubImage);
        } else {
            pubImageMapper.updateByPrimaryKeySelective(pubImage);
        }

        return ResultUtil.getSuccResult();
    }

    /**
     * 说明:根据主键删除数据
     * description:delete by key
     * @param id
     * @return void
     * @author dozen.zhang
     * @date 2015年12月27日下午10:56:38
     */
    @Override
    public void delete(Integer id) {
        pubImageMapper.deleteByPrimaryKey(id);
    }

    /**
     * 说明:根据主键获取数据
     * description:delete by key
     * @param id
     * @return void
     * @author dozen.zhang
     * @date 2015年12月27日下午10:56:38
     */
    @Override
    public PubImage selectByPrimaryKey(Integer id) {
        return pubImageMapper.selectByPrimaryKey(id);
    }

    /**多id删除
     * @param idAry
     * @return
     * @author dozen.zhang
     */
    @Override
    public ResultDTO multilDelete(Integer[] idAry) {
        for (int i = 0; i < idAry.length; i++) {
            pubImageMapper.deleteByPrimaryKey(idAry[i]);
        }
        return ResultUtil.getSuccResult();
    }

    @Override
    public PubImage getPubImageByFileName(String fileName) {
        Map params = new HashMap();
        if (fileName.indexOf("/") > -1) {
            fileName = fileName.substring(fileName.lastIndexOf("/") + 1);
        }
        params.put("name", fileName);
        List<PubImage> list = pubImageMapper.listByParams(params);
        if (list == null || list.size() == 0)
            return null;
        return list.get(0);
    }

    @Override
    public ResultDTO copyAnotherFoldWithNewName(String url, String fold, String name) {
        //=====先检索是否存在这张图片在图片库中=============
        HashMap params = new HashMap();
        params.put("name", url.substring(url.lastIndexOf("/") + 1));
        logger.debug("原始文件名称" + url.substring(url.lastIndexOf("/") + 1));
        List<PubImage> list = pubImageMapper.listByParams(params);
        if (list == null || list.size() == 0) {
            logger.debug("图片信息不存在" + url.substring(url.lastIndexOf("/") + 1));
            throw new BizException(305105131, "图片信息不存在");
        }
        logger.debug("找到文件" + list.size());
        PubImage pubImage = list.get(0);
        try {

            File sourceFile = Paths.get(pubImage.getAbsPath()).toFile();
            if (!sourceFile.exists()) {
                throw new BizException(305105147, "图片文件不存在");
            }
            File directory = Paths.get(fold).toFile();
            if (!directory.exists()) {
                directory.mkdirs();
            }
            logger.debug("拷贝文件从" + pubImage.getAbsPath() + "到" + Paths.get(fold).resolve(name).toFile());
            FileUtil.copyFile(Paths.get(pubImage.getAbsPath()).toFile(), Paths.get(fold).resolve(name).toFile());
        } catch (IOException e) {
            logger.error("", e);
            throw new BizException(305105147, "图片拷贝异常" + e.getMessage());
        }

        return null;
    }
    @Override
    public ResultDTO saveImg(String base64, byte[] data, String fileNamePrefix, String type, String relativePath, String remoteIp, Long userId) throws Exception {
        BufferedImage bufferedImage;
        String imgUnique = MD5Util.getStringMD5String(base64);
        PubImage pubImage = new PubImage();
        HashMap<String, Object> params = new HashMap<>();
        params.put("figure", imgUnique);
        List<PubImage> pubImageList = listByParams(params);
        String imgPathName = null;//pubImageService.judgeByImgUnique(imgUnique);

        if (pubImageList != null && pubImageList.size() != 0) {
            pubImage = pubImageList.get(0);
        } else {
            //==============如果不存在 则从新将图片 宽高图片写入到磁盘==========================

            //================看看是否有要求存入那个盘符 文件名是否有强制性要求=======================
            String completeFileName = "";
            if (StringUtil.isBlank(fileNamePrefix))//如果有规定的文件名称 就用规定的文件名称
                fileNamePrefix = MD5Util.getStringMD5String(CastUtil.toString(System.currentTimeMillis() + "Awifi"));

            completeFileName = fileNamePrefix + "." + type;
            String aliasName = MD5Util.getStringMD5String(MD5Util.getStringMD5String(CastUtil.toString(System.currentTimeMillis() + "Awifi"))) + ".jpg";

            try {
                //================================
                //得到原始文件路径
                Path rootPath = PathManager.getInstance().getWebRootPath().resolve(Config.getInstance().getImage().getServerDir());
                Path originalPath = rootPath.resolve("original");
                if (StringUtil.isNotBlank(relativePath)) {
                    originalPath = originalPath.resolve(relativePath);
                }
                pubImage.setAbsPath(originalPath.toString());
                File originalDir = originalPath.toFile();
                Path thumbPath = rootPath.resolve("thumb");
                if (StringUtil.isNotBlank(relativePath)) {
                    thumbPath = thumbPath.resolve(relativePath);
                }

                File thumbDir = thumbPath.toFile();
                Path formatPath = rootPath.resolve("jpg");
                if (StringUtil.isNotBlank(relativePath)) {

                    formatPath = formatPath.resolve(relativePath);
                }
                File formatDir = formatPath.toFile();//原始数据先保存一遍


                //得到图片缩略图路径
                if (!originalDir.exists()) {
                    originalDir.mkdirs();
                }
                if (!thumbDir.exists()) {
                    thumbDir.mkdirs();
                }
                if (!formatDir.exists()) {
                    formatDir.mkdirs();
                }
                FileUtil.writeFile(originalPath.resolve(completeFileName).toFile(), data);
                // WebImageUtil.saveUploadFileToDisk(image,originalPath.toString(),fileName+"."+type);
                //ImageUtil.saveBase64Image(originalPath.toString(), fileName + ".png", base64);

                File file = originalPath.resolve(completeFileName).toFile();

                //bufferedImage = ImageUtil.fixSize(bufferedImage, bufferedImage.getWidth(), bufferedImage.getHeight());
//                if(type.equals("jpeg")){
//                    FileUtil.copyFile(  file,formatPath.resolve(fileName + ".jpg").toFile());
//                }else {
                bufferedImage = ImageUtil.saveAsJpg(new FileInputStream(file), formatPath, fileNamePrefix + ".jpg");
//                }
//              //  BufferedImage formatImg = ImageUtil.fixSize(bufferedImage, width, width);
//                ImageIO.write(bufferedImage, "png",  new File(formatDir,fileName));
                logger.info("存储文件位置" + formatPath + "/" + fileNamePrefix);

                BufferedImage thumbImg = ImageUtil.fixSize(bufferedImage, 50, 50);

                ImageIO.write(thumbImg, "jpg", new File(thumbDir, fileNamePrefix + ".jpg"));
                logger.info("存储缩略图文件位置" + thumbPath + "/" + fileNamePrefix);


                pubImage.setAbsPath(file.getAbsolutePath());

                pubImage.setRelPath(FilePathUtil.joinPath(Config.getInstance().getImage().getServerUrl(), "/", "jpg", relativePath));//相对路径
            } catch (Exception e) {
                logger.error("图片保存到磁盘失败!" + e.getMessage());
                e.printStackTrace();
                return ResultUtil.getResult(30102005, "图片保存到磁盘失败");
            }


            pubImage.setName(fileNamePrefix + ".jpg");
            //为对象添加属性
//        pubImage.setOriName(orig);

            pubImage.setRemark(aliasName);

            pubImage.setFigure(imgUnique);
//        merchantImage.setCreateBy(user.getId());
//        merchantImage.setCreateByName(user.getUserName());
            pubImage.setCreateDate(new Timestamp(new Date().getTime()));

            pubImage.setStatus(0);

            pubImage.setUploadIp(remoteIp);

            pubImage.setCreator(userId + "");


            save(pubImage);


            //返回前端用户的信息
//            Map<String, Object> resultMap = new HashMap<>();
//            resultMap.put("imgName", fileName);
//            resultMap.put("imgAliasName", aliasName);
//            resultMap.put("imgPath", pubImage.getAbsPath());
//            resultMap.put("id", pubImage.getId());
//            resultMap.put("url", Config.getInstance().getImage().getServerUrl() + "/jpg/" + fileName + ".jpg");
        }
//        resultMap.put("imgTimePathName", imgWidthAndHeightPath+"/"+imgTimePathName);

        return ResultUtil.getDataResult(FilePathUtil.joinPath(Config.getInstance().getImage().getServerUrl(), "/", "jpg", relativePath, pubImage.getName()));

    }


}
