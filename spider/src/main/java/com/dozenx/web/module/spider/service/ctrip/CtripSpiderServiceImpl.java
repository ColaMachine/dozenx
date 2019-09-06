package com.dozenx.web.module.spider.service.ctrip;

import com.dozenx.common.util.DateUtil;
import com.dozenx.common.util.HttpRequestUtil;
import com.dozenx.common.util.StringUtil;
import com.dozenx.web.module.spider.hotel.comment.hotelComment.bean.HotelComment;
import com.dozenx.web.module.spider.hotel.comment.hotelComment.service.HotelCommentService;
import com.dozenx.web.module.spider.service.SpiderServiceInterface;
import com.dozenx.web.module.spider.sight.artical.sightArtical.bean.SightArtical;
import com.dozenx.web.module.spider.sight.artical.sightArtical.service.SightArticalService;
import com.dozenx.web.module.spider.sight.comment.sightComment.bean.SightComment;
import com.dozenx.web.module.spider.sight.comment.sightComment.service.SightCommentService;
import com.dozenx.web.module.spider.sight.url.sightUrl.bean.SightUrl;
import com.dozenx.web.module.spider.sight.url.sightUrl.service.SightUrlService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.HashMap;

/**
 * @Author: dozen.zhang
 * @Description:
 * @Date: Created in 15:26 2018/8/9
 * @Modified By:
 */
@Service("ctripSpiderServiceImpl")
public class CtripSpiderServiceImpl implements SpiderServiceInterface {
    /**
     * 日志
     **/
    private Logger logger = LoggerFactory.getLogger(CtripSpiderServiceImpl.class);

    @Resource
    private HotelCommentService hotelCommentService;
    @Resource
    private SightCommentService sightCommentService;

    @Resource
    private SightUrlService sightUrlService;

    @Resource
    private SightArticalService sightArticalService;

    @Override
    //http://you.ctrip.com/sight/lishui441/56153.html
    public void spiderSightComment(String url) {
        String html = HttpRequestUtil.sendGet(url);
        //先解析这个网站
        //拿到网页
        //解析出页数
        Document doc = Jsoup.parseBodyFragment(html);
        //解析出id name
        String idStr = url.substring(url.lastIndexOf("/") + 1, url.indexOf(".htm"));
        int id = Integer.valueOf(idStr);
        Element pageNumEle = doc.getElementsByClass("numpage").first();
        if (pageNumEle == null) {
            logger.error("找不到分页标签");
            return;
        }
        //String local =
        String dianpinIdStr = doc.getElementById("dianPing").getElementsByClass("b_orange_m").first().attr("data-id");
        String name = "";
        if (sightUrlService.selectByPrimaryKey(id) != null) {
            return;
        } else {
            SightUrl sightUrl = new SightUrl();

            sightUrl.setId(id);
            sightUrl.setType(1);
            sightUrl.setUrl(url);
            name = doc.getElementsByClass("detail_tt").first().child(0).child(0).child(0).child(0).text();
            sightUrl.setName(name);
            sightUrl.setStatus(1);
            sightUrlService.save(sightUrl);
        }
        int dianpinId = Integer.valueOf(dianpinIdStr);
        //    <input type="hidden" id="putDistrictId" value="441" />
        int putDistrictId = Integer.valueOf(doc.getElementById("putDistrictId").val());//解析地区id
        logger.debug("page num : " + pageNumEle.text());
        int totalPage = Integer.valueOf(pageNumEle.text());
        HashMap<String, String> param = new HashMap<>();
        for (int pageNo = 0; pageNo < totalPage; pageNo++) {
            //  http:
//you.ctrip.com/destinationsite/TTDSecond/SharedView/AsynCommentView
            //post
//            poiID: 10758440
//            districtId: 441
//            districtEName: Lishui
//            pagenow: 3
//            order: 3.0
//            star: 0.0
//            tourist: 0.0
//            resourceId: 56153
//            resourcetype: 2
//            HashMap map =new HashMap();
            param.put("poiID", dianpinId + "");
            param.put("districtId", putDistrictId + "");
            param.put("pagenow", pageNo + "");
            param.put("order", pageNo + "");
            param.put("order", pageNo + "");
            param.put("star", "0.0");
            param.put("star", "0.0");
            param.put("resourceId", id + "");
            param.put("resourcetype", 2 + "");
            String commentUrl = "http://you.ctrip.com/destinationsite/TTDSecond/SharedView/AsynCommentView";
            String commentPageHtml = HttpRequestUtil.sendPost(commentUrl, param);//每一页的评论

            if (StringUtil.isBlank(commentPageHtml)) {
                logger.error("酒店评论分页页面为空");
            }
            Document pageDoc = Jsoup.parseBodyFragment(commentPageHtml);
            //解析并保存评论
            processSightComment(pageDoc, id);
        }

        //开始爬取景点文章

        spiderSightArtical(name, id);
    }

    /**
     * 爬取url 文章
     */
    @Override
    public void spiderSightArtical(String name, int pid) {
        //这个其实是一个查询接口 后面的参数是查询的景点的名称
        //后面的参数是urlencode 两次的结果
        int pageNo = 1;
        while (true) {
            String url = "http://you.ctrip.com/searchsite/Travels?query=" + name + "&PageNo=" + pageNo;
            String html = HttpRequestUtil.sendGet(url);
            Document doc = Jsoup.parseBodyFragment(html);
            Element listWrapELe = doc.getElementsByClass("youji-ul").first();
            if(listWrapELe==null ){
                break;
            }

            Elements liList = listWrapELe.children();//.getElementsByClass("cf")
            if(liList==null || liList.size()==0){
                break;
            }
            for (int i = 0; i < liList.size(); i++) {
                Element item = liList.get(i);
                //需要解析a标签连接获取url里的id
                String hrefUrl =  item.getElementsByTag("a").get(0).attr("href");

                String itemIdStr = hrefUrl.substring(hrefUrl.lastIndexOf("/")+1, hrefUrl.lastIndexOf(".html"));

                int articalId = Integer.valueOf(itemIdStr);

                //判断是否存在文章
                SightArtical sightArtical = sightArticalService.selectByPrimaryKey(articalId);
                if (sightArtical != null) {
                    continue;
                }
                if (itemIdStr.length() != "2388848".length()) {
                    logger.error("截取id错误");
                }
                String title = item.getElementsByTag("dt").first().child(0).text();
                String shortContent = item.getElementsByTag("dd").first().text();
                String underLine = item.getElementsByTag("dd").get(1).text();
                underLine = underLine.replaceAll(" ", "");
                String time = underLine.substring(underLine.indexOf("发表于") + 4, underLine.indexOf("|")).trim().replaceAll("[\\u00A0]+", "");
                //发表于  2015-6-11  |  51条评论
                String author = item.getElementsByTag("dd").get(1).child(0).text();
                String articalHtml = HttpRequestUtil.sendGet("http://you.ctrip.com" + hrefUrl);
                Document articalDoc = Jsoup.parseBodyFragment(articalHtml);
                Element articalBody = articalDoc.getElementsByClass("ctd_main_body").first();
                String content = articalBody.html();
                Elements pList = articalBody.getElementsByTag("p");
                StringBuffer sb =new StringBuffer();
                for(Element p : pList){
                    sb.append(p.text());
                }
                String articalContent =sb.toString();
                articalContent = StringUtil.filterEmoji(articalContent);
                articalContent= articalContent.replaceAll("[\ud800\udc00-\udbff\udfff\ud800-\udfff]", "");
                SightArtical artical = new SightArtical();
                artical.setCreateTime(new Timestamp(DateUtil.parseToDate(time, "yyyy-MM-dd").getTime()));


                artical.setId(articalId);
                artical.setStatus(1);
                artical.setContent(articalContent);
                artical.setPid(pid);
                artical.setAuthor(author);
                artical.setDataTime(new Timestamp(DateUtil.parseToDate(time, "yyyy-MM-dd").getTime()));
                //artical.setData_time();
                //artical.setPid();
                artical.setTitle(title);
                artical.setUrl("http://you.ctrip.com" + hrefUrl);
                sightArticalService.save(artical);
            }
            pageNo++;
        }
    }
        public static  void main(String args[]){


        }
    /**
     * 处理景点分页返回数据
     *
     * @param doc
     * @param pid
     */
    public void processSightComment(Document doc, int pid) {
        //  Element pinglunBLock = doc.getElementById("sightcommentbox");
        Elements elements = doc.getElementsByClass("comment_single");

        for (int i = 0; i < elements.size(); i++) {
            Element commentBlock = elements.get(i);


            String dataCid = commentBlock.getElementById("usefultodo").attr("data-id");
            if (StringUtil.isBlank(dataCid)) {
                continue;
            }
            int commentid = Integer.valueOf(dataCid);

            SightComment comment = sightCommentService.selectByPrimaryKey(commentid);
            if (comment != null) {
                continue;//如果已经存在了
            }
            String text = commentBlock.getElementsByClass("heightbox").first().text();

            //  String fenshu = commentBlock.getElementsByClass("comment_title").first().getElementsByClass("n").text();
//           String remark =  commentBlock.getElementsByClass("sblockline").first().text().replaceAll(" ","").replaceAll("&emsp;","").replaceAll("&nbsp;", "");
            String time = commentBlock.getElementsByClass("time_line").first().child(0).text();

            //发表于2018-07-31

            comment = new SightComment();
            comment.setPid(pid);
            comment.setId(commentid);
//            comment.setScore(Float.valueOf(fenshu));
//            comment.
            comment.setCreateTime(new Timestamp(DateUtil.parseToDate(time, "yyyy-MM-dd").getTime()));
            comment.setCreateTime(DateUtil.getNowTimeStamp());

            comment.setCotent(StringUtil.filterEmoji(text));
            comment.setStatus(1);
            try {
                sightCommentService.save(comment);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void processHotelComment(Document doc, int pid) {
        Element pinglunBLock = doc.getElementById("commentList");
        Elements elements = pinglunBLock.getElementsByClass("comment_block");

        for (int i = 0; i < elements.size(); i++) {
            Element commentBlock = elements.get(i);

            String dataCid = commentBlock.attr("data-cid");
            if (StringUtil.isBlank(dataCid)) {
                continue;
            }
            int commentid = Integer.valueOf(dataCid);

            HotelComment hotelComment = hotelCommentService.selectByPrimaryKey(commentid);
            if (hotelComment != null) {
                continue;//如果已经存在了
            }
            String text = commentBlock.getElementsByClass("J_commentDetail").first().text();

            String fenshu = commentBlock.getElementsByClass("comment_title").first().getElementsByClass("n").text();

            String time = commentBlock.getElementsByClass("comment_bar").first().getElementsByClass("time").first().text();
            time = time.replace("发表于", "");
            //发表于2018-07-31

            hotelComment = new HotelComment();
            hotelComment.setPid(pid);
            hotelComment.setId(commentid);
            hotelComment.setScore(Float.valueOf(fenshu));
            hotelComment.setCreateTime(new Timestamp(DateUtil.parseToDate(time, "yyyy-MM-dd").getTime()));
            hotelComment.setCreateTime(DateUtil.getNowTimeStamp());

            hotelComment.setCotent(StringUtil.filterEmoji(text));
            try {
                hotelCommentService.save(hotelComment);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void spiderHotel(String url) {

    }


    public void spiderHotelComment(String url, int pid) {

        //先解析这个网站
        //拿到网页
        String html = HttpRequestUtil.sendGet(url);
        //解析出页数
        Document doc = Jsoup.parseBodyFragment(html);
        Element ele = doc.getElementById("cTotalPageNum");

        if (ele == null) {
            logger.error("找不到分页标签");
            return;
        }

        logger.debug("page num : " + ele.val());
        int totalPage = Integer.valueOf(ele.val());


        HashMap<String, String> param = new HashMap<>();
        for (int pageNo = 0; pageNo < totalPage; pageNo++) {
            String commentUrl = "http://hotels.ctrip.com/Domestic/tool/AjaxHotelCommentList.aspx?MasterHotelID=" + pid + "&hotel=" + url + "&NewOpenCount=0&AutoExpiredCount=0&RecordCount=3674&OpenDate=&card=-1&property=-1&userType=-1&productcode=&keyword=&roomName=&orderBy=" + pageNo + "&currentPage=" + pageNo + "&viewVersion=c&contyped=0&eleven=29e7d97aa6f0bbd2e056cf89c99753c59f28f5882490ec341669a8e5b1780652&callback=CASFCTnMcgSaDWMni&_=1533567607095";
            String commentPageHtml = HttpRequestUtil.sendPost(commentUrl, param);//每一页的评论

            if (StringUtil.isBlank(commentPageHtml)) {
                logger.error("酒店评论分页页面为空");
            }
            Document pageDoc = Jsoup.parseBodyFragment(commentPageHtml);
            //解析并保存评论
            processHotelComment(pageDoc, pid);
        }


    }
    //爬取文章


    //
}
