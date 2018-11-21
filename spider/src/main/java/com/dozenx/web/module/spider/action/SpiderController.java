package com.dozenx.web.module.spider.action;

import com.cpj.swagger.annotation.API;
import com.cpj.swagger.annotation.APIs;
import com.cpj.swagger.annotation.DataType;
import com.cpj.swagger.annotation.Param;
import com.dozenx.util.HttpRequestUtil;
import com.dozenx.util.StringUtil;
import com.dozenx.web.core.base.BaseController;
import com.dozenx.web.core.log.ResultDTO;
import com.dozenx.web.core.page.Page;
import com.dozenx.web.core.page.PageUtil;
import com.dozenx.web.module.spider.hotel.comment.hotelComment.bean.HotelComment;
import com.dozenx.web.module.spider.hotel.url.hotelUrl.bean.HotelUrl;
import com.dozenx.web.module.spider.hotel.url.hotelUrl.service.HotelUrlService;
import com.dozenx.web.module.spider.service.SpiderServiceInterface;
import com.dozenx.web.util.RequestUtil;
import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

/**
 * @Author: dozen.zhang
 * @Description:
 * @Date: Created in 15:13 2018/8/9
 * @Modified By:
 */

@APIs(description = "爬虫主要接口")
@Controller
@RequestMapping("/spider")
public class SpiderController extends BaseController {

    /** 日志 **/
    private Logger logger = LoggerFactory.getLogger(SpiderController.class);

    @Resource(name="ctripSpiderServiceImpl")
    private SpiderServiceInterface spiderService;

    @Resource
    private HotelUrlService hotelUrlService;
    /**
     * 说明:ajax请求HotelComment信息
     * @author dozen.zhang
     * @date 2018-8-5 13:52:14
     * @return String
     */
    @API(summary="景点单页面爬取接口",
            description="景点单页面爬取接口",
            parameters={

            })
    @RequestMapping(value = "/sight/comment/single/page" , method = RequestMethod.GET)
    @ResponseBody
    public ResultDTO singleSightComment(HttpServletRequest request) throws Exception {
        String url =request.getParameter("url");
        spiderService.spiderSightComment(url);
        return this.getResult();
    }



    @API(summary="景点单页面爬取接口",
            description="景点单页面爬取接口",
            parameters={

            })
    @RequestMapping(value = "/hotel/comment/single/page" , method = RequestMethod.GET)
    @ResponseBody
    public ResultDTO hotelCommentSinglePageSpider(HttpServletRequest request) throws Exception {
        String url =request.getParameter("url");
        spiderService.spiderHotel(url);
        return this.getResult();
    }

    /**
     * 说明:ajax请求HotelComment信息
     * @author dozen.zhang
     * @date 2018-8-5 13:52:14
     * @return String
     */
    @API(summary="景点单页面爬取接口",
            description="景点单页面爬取接口",
            parameters={

            })
    @RequestMapping(value = "/sight/artical/single/page" , method = RequestMethod.GET)
    @ResponseBody
    public ResultDTO singleSightArtical(HttpServletRequest request) throws Exception {
        String url =request.getParameter("name");
        String idStr =request.getParameter("id");
        spiderService.spiderSightArtical(url,Integer.valueOf(idStr));
        return this.getResult();
    }
    /**
     * 爬取数据库中的已有酒店url 获取评论 去重后插入数据库
     * @param hotelUrl
     */
//    public void spiderSightComment(HotelUrl hotelUrl) {
//
//        //先解析这个网站
//        //拿到网页
//        String html = HttpRequestUtil.sendGet(hotelUrl.getUrl());
//        //解析出页数
//        Document doc = Jsoup.parseBodyFragment(html);
//
//        Elements eles = doc.getElementsByClass("numpage");
//        if (eles.size() <= 0) {
//            logger.error("找不到分页标签");
//        }
//
//
//        String pageNumStr = eles.get(0).text();
//        if (StringUtil.isBlank(pageNumStr)) {
//            logger.error("分页数无法取到");
//        }
//        int pageNum = Integer.valueOf(pageNumStr);
//        HashMap<String, String> param = new HashMap<>();
//        for (int i = 0; i < pageNum; i++) {
//
//
//            param.put("poiID", "10758440");
//            param.put("districtId", "2");
//            param.put("districtEName", "Lishui");
//
//            param.put("pagenow", "" + i);
//
//            param.put("order", "" + i);
//
//            param.put("star", "0.0");
//
//            param.put("tourist", "0.0");
//            param.put("resourceId", "" + hotelUrl.getId());
//            param.put("resourcetype", "" + 2);
//            String commentPageHtml = HttpRequestUtil.sendPost(hotelUrl.getUrl(), param);//每一页的评论
//            Document pageDoc = Jsoup.parseBodyFragment(commentPageHtml);
//
//            Elements commentEles = pageDoc.getElementsByClass("heightbox");
//            for (int j = 0; j < commentEles.size(); j++) {
//                Element element = commentEles.get(j);
//                String pingLun = element.text();
//
//                HotelComment hotelComment = new HotelComment();
//                hotelComment.setPid(hotelUrl.getId());
//
//            }
//
//        }
//

//    }


    @RequestMapping(value = "/updateAllComment", method = RequestMethod.GET)
    public String update(HttpServletRequest request) throws Exception {
        //将库里的所有url 的评论都更新一篇 注意一件事情 每次抓取都礼貌性的停顿一下

        int count = hotelUrlService.countByParams(new HashMap());
        //分页查询所有的已有url 携程的
        for (int i = 0; i < count / 10; i++) {
            Page page = PageUtil.createPage(10, i, count);

            HashMap<String, Object> map = new HashMap<>();
            map.put("page", page);
            List<HotelUrl> list = hotelUrlService.listByParams4Page(map);//分页查询
            //如果有携程的酒店
            for (int j = 0; j < list.size(); j++) {
                HotelUrl hotelUrl = list.get(j);
                String url = hotelUrl.getUrl();
                int id = hotelUrl.getId();
                //hotelCommentService.updateFromUrl(hotelUrl);
                //开始爬取这个网站的数据
                //开始从第一页开始爬取
               spiderService.spiderHotel(hotelUrl.getUrl());
                //
            }
        }
        return "/static/html/HotelUrlView.html";
    }
    public static void main(String[] args) throws Exception {
        String crawlStorageFolder = "G:/crawler";// 定义爬虫数据存储位置
        int numberOfCrawlers = 7;// 定义了7个爬虫，也就是7个线程

        CrawlConfig config = new CrawlConfig();// 定义爬虫配置
        config.setCrawlStorageFolder(crawlStorageFolder);// 设置爬虫文件存储位置

        /*
         * 实例化爬虫控制器。
         */
        PageFetcher pageFetcher = new PageFetcher(config);// 实例化页面获取器
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();// 实例化爬虫机器人配置
        // 实例化爬虫机器人对目标服务器的配置，每个网站都有一个robots.txt文件
        // 规定了该网站哪些页面可以爬，哪些页面禁止爬，该类是对robots.txt规范的实现
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        // 实例化爬虫控制器
        CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);

        /*
         * 对于每次抓取，您需要添加一些种子网址。 这些是抓取的第一个URL，然后抓取工具开始跟随这些页面中的链接
         */
        controller.addSeed("http://hotels.ctrip.com/");
//        action.addSeed("http://www.ics.uci.edu/~welling/");
//        action.addSeed("http://www.ics.uci.edu/");

        /**
         * 启动爬虫，爬虫从此刻开始执行爬虫任务，根据以上配置
         */
        controller.start(MyCrawler.class, numberOfCrawlers);
    }
}
