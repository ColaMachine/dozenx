/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明: 
 */

package com.dozenx.web.module.spider.hotel.comment.hotelComment.service;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import com.dozenx.common.util.DateUtil;
import com.dozenx.common.util.HttpRequestUtil;
import com.dozenx.web.module.spider.hotel.url.hotelUrl.bean.HotelUrl;
import com.dozenx.web.util.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.dozenx.web.module.spider.hotel.comment.hotelComment.bean.HotelComment;
import com.dozenx.web.module.spider.hotel.comment.hotelComment.dao.HotelCommentMapper;
import com.dozenx.common.util.UUIDUtil;
import com.dozenx.common.util.StringUtil;
import com.dozenx.web.core.base.BaseService;
import com.dozenx.web.core.log.ResultDTO;

@Service("hotelCommentService")
public class HotelCommentService extends BaseService {
    private static final Logger logger = LoggerFactory
            .getLogger(HotelCommentService.class);
    @Resource
    private HotelCommentMapper hotelCommentMapper;
    /**
     * 说明:list by page and params根据参数返回列表
     * @return List<HashMap>
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public List<HotelComment> listByParams4Page(HashMap params) {
        return hotelCommentMapper.listByParams4Page(params);
    }
    public List<HotelComment> listByParams(HashMap params) {
        return hotelCommentMapper.listByParams(params);
    }

     /**
     * 说明:countByParams 根据参数提取个数
     * @return int
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public int countByParams(HashMap params) {
           return hotelCommentMapper.countByParams(params);
    }

    /*
     * 说明:
     * @param HotelComment
     * @return
     * @return Object
     * @author dozen.zhang
     * @date 2015年11月15日下午1:33:54
     */
    public ResultDTO save(HotelComment hotelComment) {
        // 进行字段验证
      /* ValidateUtil<HotelComment> vu = new ValidateUtil<HotelComment>();
        ResultDTO result = vu.valid(hotelComment);
        if (result.getR() != 1) {
            return result;
        }*/
         //逻辑业务判断判断
       //判断是否有uq字段
       
       //判断是更新还是插入
        if (hotelComment.getId()==null ||  this.selectByPrimaryKey(hotelComment.getId())==null) {
            hotelComment.setCreateTime(new Timestamp(new Date().getTime()));

            hotelCommentMapper.insertSelective(hotelComment);
        } else {
            hotelComment.setUpdatetime(new Timestamp(new Date().getTime()));
            hotelCommentMapper.updateByPrimaryKeySelective(hotelComment);
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
    public void delete(Integer  id){
        hotelCommentMapper.deleteByPrimaryKey(id);
    }   
    /**
    * 说明:根据主键获取数据
    * description:delete by key
    * @param id
    * @return void
    * @author dozen.zhang
    * @date 2015年12月27日下午10:56:38
    */
    public HotelComment selectByPrimaryKey(Integer id){
       return hotelCommentMapper.selectByPrimaryKey(id);
    }
    /**多id删除
     * @param idAry
     * @return
     * @author dozen.zhang
     */
    public ResultDTO multilDelete(Integer[] idAry) {
        for(int i=0;i<idAry.length;i++){
            hotelCommentMapper.deleteByPrimaryKey(idAry[i]);
        }
        return ResultUtil.getSuccResult();
    }

    public void updateFromUrl(HotelUrl hotelUrl) {
         //url ="http://hotels.ctrip.com/Domestic/tool/AjaxHotelCommentList.aspx?MasterHotelID=436543&hotel=436543&NewOpenCount=0&AutoExpiredCount=0&RecordCount=3667&OpenDate=&card=-1&property=-1&userType=-1&productcode=&keyword=&roomName=&orderBy=2&currentPage=3&viewVersion=c&contyped=0&eleven=81e516cb15d306dc08c40dba4ff9d2c3d0e79d2130c52e0723887151987b6438&callback=CASRgUUkZcbFNtrUK&_=1533519626390";
       // String html = HttpRequestUtil.UrlRead(url,"http://hotels.ctrip.com/hotel/436543.html","hotels.ctrip.com");
        //拿到id

        //Referer: http://hotels.ctrip.com/hotel/436543.html
        //Host: hotels.ctrip.com
       // String cookie="_abtest_userid=085f2677-7104-40f4-80f7-f37ee7dac182; _RF1=60.191.109.194; _RSG=gxaowQIaTp99c4SVC99WF9; _RDG=28f39d24f34e0f24cf039cc424069787ae; _RGUID=9cdd9126-baca-4eea-93e0-24931f704989; _ga=GA1.2.1552345098.1522147275; traceExt=campaign=CHNbaidu81&adid=index; MKT_Pagesource=PC; Union=SID=155952&AllianceID=4897&OUID=baidu81|index|||; Session=SmartLinkCode=U155952&SmartLinkKeyWord=&SmartLinkQuary=&SmartLinkHost=&SmartLinkLanguage=zh; adscityen=Hangzhou; _gid=GA1.2.1447531838.1533433393; ASP.NET_SessionId=iw0jgctshfpfo5pg0tuh00ft; OID_ForOnlineHotel=15221472691272lf3ez1533433465621102003; appFloatCnt=6; manualclose=1; MjAxNS8wNi8yOSAgSE9URUwgIERFQlVH=OceanBall_comment; _bfa=1.1522147269127.2lf3ez.1.1533516472897.1533519137597.12.43; _bfs=1.2; HotelDomesticVisitedHotels1=436543=0,0,4.7,3667,/fd/hotel/g4/M0A/25/BB/CggYHFYDTOyAO1p-AALrWO--vCA646.jpg,; Mkt_UnionRecord=%5B%7B%22aid%22%3A%224897%22%2C%22timestamp%22%3A1533519152914%7D%5D; _bfi=p1%3D102003%26p2%3D100101991%26v1%3D43%26v2%3D42; _jzqco=%7C%7C%7C%7C1533433393132%7C1.1025372457.1522147274790.1533519140484.1533519152942.1533519140484.1533519152942.undefined.0.0.41.41; __zpspc=9.16.1533519140.1533519152.2%231%7Cbaidu%7Ccpc%7Cbaidu81%7C%25E6%2590%25BA%25E7%25A8%258B%25E6%2597%2585%25E6%25B8%25B8%25E5%25AE%2598%25E7%25BD%2591%7C%23";
    }

    public static void main(String args[]){
        String url ="http://hotels.ctrip.com/Domestic/tool/AjaxHotelCommentList.aspx?MasterHotelID=436543&hotel=436543&NewOpenCount=0&AutoExpiredCount=0&RecordCount=3667&OpenDate=&card=-1&property=-1&userType=-1&productcode=&keyword=&roomName=&orderBy=2&currentPage=3&viewVersion=c&contyped=0&eleven=81e516cb15d306dc08c40dba4ff9d2c3d0e79d2130c52e0723887151987b6438&callback=CASRgUUkZcbFNtrUK&_=1533519626390";
      //  String html = HttpRequestUtil.UrlRead(url,"http://hotels.ctrip.com/hotel/436543.html","hotels.ctrip.com");

      //  System.out.println(html);
    }


}
