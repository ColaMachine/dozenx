package com.dozenx.web.module.spider.action;

import com.dozenx.common.util.DateUtil;
import com.dozenx.common.util.StringUtil;
import com.dozenx.web.module.spider.hotel.comment.hotelComment.service.HotelCommentService;
import com.dozenx.web.module.spider.hotel.url.hotelUrl.bean.HotelUrl;
import com.dozenx.web.module.spider.hotel.url.hotelUrl.service.HotelUrlService;
import com.dozenx.web.module.spider.service.SpiderServiceInterface;
import com.dozenx.web.util.BeanUtil;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.regex.Pattern;

/**
 * @Author: dozen.zhang
 * @Description:
 * @Date: Created in 15:32 2018/8/3
 * @Modified By:
 */
public class MyCrawler extends WebCrawler {
    public static HotelUrlService hotelUrlService;
    public static HotelCommentService hotelCommentService;

    public static SpiderServiceInterface spiderService;
    //private HotelCommentService hotelCommentService;
    /**
     * 正则表达式匹配指定的后缀文件
     */
    private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|gif|jpg" + "|png|mp3|mp4|zip|gz))$");
    private final static Pattern HOTEL_FILTER = Pattern.compile("http://hotels.ctrip.com/hotel/\\d*.html.*");

    private final static Pattern HOTEL_COMMENT_FILTER = Pattern.compile(".*/Domestic/tool/AjaxHotelCommentList.aspx.*");


    /**
     * 这个方法主要是决定哪些url我们需要抓取，返回true表示是我们需要的，返回false表示不是我们需要的Url
     * 第一个参数referringPage封装了当前爬取的页面信息 第二个参数url封装了当前爬取的页面url信息
     * 在这个例子中，我们指定爬虫忽略具有css，js，git，...扩展名的url，只接受以“http://www.ics.uci.edu/”开头的url。
     * 在这种情况下，我们不需要referringPage参数来做出决定。
     */
    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {
        String href = url.getURL().toLowerCase();// 得到小写的url
        return !FILTERS.matcher(href).matches() // 正则匹配，过滤掉我们不需要的后缀文件
                && HOTEL_FILTER.matcher(href).matches() && href.startsWith("http://hotels.ctrip.com/");// 只接受以“http://www.ics.uci.edu/”开头的url
    }

    public HotelUrlService getHotelUrlService() {
        return hotelUrlService;
    }

    public void setHotelUrlService(HotelUrlService hotelUrlService) {
        this.hotelUrlService = hotelUrlService;
    }

    public HotelCommentService getHotelCommentService() {
        return hotelCommentService;
    }

    public void setHotelCommentService(HotelCommentService hotelCommentService) {
        this.hotelCommentService = hotelCommentService;
    }

    /**
     * 当一个页面被提取并准备好被你的程序处理时，这个函数被调用。
     */
    @Override
    public void visit(Page page) {
        String url = page.getWebURL().getURL();// 获取url
        System.out.println("URL: " + url);
        if (hotelUrlService == null) {
            hotelUrlService = (HotelUrlService) BeanUtil.getBean("hoterUrlService");
        }
        if (page.getParseData() instanceof HtmlParseData) {// 判断是否是html数据
            HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();//// 强制类型转换，获取html数据对象
            // String text = htmlParseData.getText();//获取页面纯文本（无html标签）
            String html = htmlParseData.getHtml();//获取页面Html
            //  Set<WebURL> links = htmlParseData.getOutgoingUrls();// 获取页面输出链接


            Document doc = Jsoup.parseBodyFragment(html);
            //doc.select("div[class='cn_n']");
            String name = doc.getElementsByClass("cn_n").first().text();
            System.out.println("name:" + name + "  url:" + url);

            String id = url.substring(url.indexOf("hotel/") + 6, url.indexOf(".htm"));
            int hotelId = Integer.valueOf(id);

            HotelUrl hotelUrl = hotelUrlService.selectByPrimaryKey(hotelId);
            //
            if (hotelUrl != null) {

                System.out.println("已经好了");
                return;
            }
            hotelUrl = new HotelUrl();
            hotelUrl.setName(name);
            hotelUrl.setUrl(url);
            hotelUrl.setType(1);//
            hotelUrl.setCreateTime(DateUtil.getNowTimeStamp());
            hotelUrl.setPlatform("携程");

            hotelUrl.setId(hotelId);
            hotelUrl.setOutId(hotelId);

            try {
                hotelUrlService.save(hotelUrl);
            } catch (Exception e) {
                e.printStackTrace();
            }

            //保存到数据库中
            //System.out.println("纯文本长度: " + text.length());
            //  System.out.println("html长度: " + html.length());
            // System.out.println("链接个数 " + links.size());

            //获取评论节点
          //  spiderService.spiderSight(). processXieChengHotelComment(doc,hotelId);


        }
    }




    public static void main(String args[]) {
        String url = "http://hotels.ctrip.com/hotel/427907.html#ctm_ref=www_hp_bs_lst";
        String id = url.substring(url.indexOf("hotel/") + 6, url.indexOf(".htm"));
        System.out.println(id);
        if (HOTEL_FILTER.matcher(url).matches()) {
            System.out.println("123123");
        }
    }


}