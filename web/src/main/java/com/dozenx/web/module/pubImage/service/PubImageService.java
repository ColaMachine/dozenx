/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明: 
 */

package com.dozenx.web.module.pubImage.service;
import java.util.HashMap;
import java.util.List;

import com.dozenx.web.module.pubImage.bean.PubImage;
import com.dozenx.common.util.UUIDUtil;
import com.dozenx.common.util.StringUtil;
import com.dozenx.web.core.log.ResultDTO;
import org.springframework.transaction.annotation.Transactional;

public interface PubImageService {
    public List<PubImage> listByParams4Page(HashMap params);

    public List<PubImage> listByParams(HashMap params);

    /**
     * 说明:countByParams 根据参数提取个数
     *
     * @return int
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public int countByParams(HashMap params);

    /*
     * 说明:
     * @param PubImage
     * @return
     * @return Object
     * @author dozen.zhang
     * @date 2015年11月15日下午1:33:54
     */
    @Transactional
    public ResultDTO save(PubImage pubImage);

    /**
     * 说明:根据主键删除数据
     * description:delete by key
     *
     * @param id
     * @return void
     * @author dozen.zhang
     * @date 2015年12月27日下午10:56:38
     */
    public void delete(Integer id);

    /**
     * 说明:根据主键获取数据
     * description:delete by key
     *
     * @param id
     * @return void
     * @author dozen.zhang
     * @date 2015年12月27日下午10:56:38
     */
    public PubImage selectByPrimaryKey(Integer id);

    /**
     * 多id删除
     *
     * @param idAry
     * @return
     * @author dozen.zhang
     */
    public ResultDTO multilDelete(Integer[] idAry);

    /**
     *
     * @param fileName
     * @return
     */
    public PubImage getPubImageByFileName(String fileName);

    /**
     *
     * @param url
     * @param fold
     * @param name
     * @return
     */
    public ResultDTO copyAnotherFoldWithNewName(String url, String fold, String name);

    /**
     *
     * @param base64
     * @param data
     * @param fileNamePrefix
     * @param type
     * @param relativePath
     * @param remoteIp
     * @param userId
     * @return
     * @throws Exception
     */
    public ResultDTO saveImg(String base64, byte[] data, String fileNamePrefix, String type, String relativePath, String remoteIp, Long userId) throws Exception;
}