/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明: 
 */

package com.dozenx.web.module.article.service;

import com.dozenx.core.Path.PathManager;
import com.dozenx.util.DateUtil;
import com.dozenx.util.FileUtil;
import com.dozenx.util.StringUtil;
import com.dozenx.web.core.base.BaseService;
import com.dozenx.web.core.log.ResultDTO;
import com.dozenx.web.module.article.bean.Article;
import com.dozenx.web.module.article.dao.ArticleMapper;
import com.dozenx.web.util.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service("articalService")
public class ArticleService extends BaseService {
    private static final Logger logger = LoggerFactory
            .getLogger(ArticleService.class);
    @Resource
    private ArticleMapper articalMapper;
    /**
     * 说明:list by page and params根据参数返回列表
     * @return List<HashMap>
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public List<Article> listByParams4Page(HashMap params) {
        return articalMapper.listByParams4Page(params);
    }

    public List<HashMap<String,Object>> listWithUserInfoByParams4Page(HashMap params) {
        return articalMapper.listWithUserInfoByParams4Page(params);
    }
    public List<Article> listByParams(HashMap params) {
        return articalMapper.listByParams(params);
    }

     /**
     * 说明:countByParams 根据参数提取个数
     * @return int
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public int countByParams(HashMap params) {
           return articalMapper.countByParams(params);
    }

    /*
     * 说明:
     * @param Artical
     * @return
     * @return Object
     * @author dozen.zhang
     * @date 2015年11月15日下午1:33:54
     */
    public ResultDTO save(Article artical) {
        // 进行字段验证
      /* ValidateUtil<Artical> vu = new ValidateUtil<Artical>();
        ResultDTO result = vu.valid(artical);
        if (result.getR() != 1) {
            return result;
        }*/
         //逻辑业务判断判断
       //判断是否有uq字段
       //如果article的字段长度大于某个值那就从本地文件里去取

        if(artical.getContent().length()>5000) {
            artical.setContent("");
        }
       //判断是更新还是插入
        if (artical.getId()==null ||  this.selectByPrimaryKey(artical.getId())==null) {
            artical.setCreatetime(DateUtil.getNowTimeStamp());
            articalMapper.insert(artical);
        } else {
            artical.setUpdatetime(new Timestamp(new Date().getTime()));
            articalMapper.updateByPrimaryKeySelective(artical);
        }

        if(artical.getContent().length()>5000){
            //OSSService.save("key",artical.getContent());
            try {
                FileUtil.writeFile(PathManager.getInstance().getHomePath().resolve("article").resolve("article_"+artical.getId()).toFile(),artical.getContent());
            } catch (IOException e) {
                e.printStackTrace();
            }
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
    public void delete(Long  id){
        articalMapper.deleteByPrimaryKey(id);
    }   
    /**
    * 说明:根据主键获取数据
    * description:delete by key
    * @param id
    * @return void
    * @author dozen.zhang
    * @date 2015年12月27日下午10:56:38
    */
    public Article selectByPrimaryKey(Long id){
        Article article = articalMapper.selectByPrimaryKey(id);
        if(StringUtil.isBlank(article.getContent())){
           // ossService.get("article"+id);
            try {
                article.setContent(FileUtil.readFile2Str(PathManager.getInstance().getHomePath().resolve("article").resolve("article_"+id).toFile()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
       return articalMapper.selectByPrimaryKey(id);
    }
    /**多id删除
     * @param idAry
     * @return
     * @author dozen.zhang
     */
    public ResultDTO multilDelete(Long[] idAry) {
        for(int i=0;i<idAry.length;i++){
            articalMapper.deleteByPrimaryKey(idAry[i]);
        }
        return ResultUtil.getSuccResult();
    }

    public void updatePinglunCount(Long id){
        articalMapper.updateCommentCountById(id);
    }
}
