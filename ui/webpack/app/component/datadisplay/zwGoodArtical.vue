<template>
  <div class="main-left" id="feed-main">
    <!--标题 头部info模块-->
    <div style="display:none" class="title-fixed J_title_fixed">
      <div class="title">
        {{data .title}}
        <div></div>
      </div>
      <div class="info">

        <div class="btn-group">
          <!--去购买有下拉情况-->
          <div class="btn-more-box">
            <a :href="data.link" target="_blank" class="go-buy btn"  style="display: inline-block;">
              去购买
              <i class="icon-angle-right-o-thin"></i>
            </a>
            <div class="other-link-box">
              <a :href="data.link" target="_blank" class="other-link" >直筒玻璃杯{{data.id}}</a>

              <a target="_blank" class="other-link" rel="nofollow" title="5元优惠券" :href="data.yhlink" > 5元优惠券 </a>
            </div>
          </div>
          <!-- 二维码购买 -->
          <!-- 二维码购买end -->

        </div>
        <div></div>
      </div>


    </div>









    <div class="info J_info" articleid="3_14039324">
      <a class="img-box" :href="data.link"  target="_blank">
                          <span class="label youhui">优惠精选</span>
                          <!-- 商品图片 -->
          <img class="main-img" :src="data.img" alt="佳偶 双层直筒玻璃杯 350ml *2件"
          onclick="gtmAddToCart({'name':'佳偶 双层直筒玻璃杯 350ml *2件','id':'14039324' ,
           'price':'18','brand':'' ,'mall':'天猫精选', 'category':'日用百货/厨房用具/水具酒具/玻璃杯','metric1':'18',
           'dimension10':'tmall.com','dimension9':'youhui','dimension11':'1阶价格','dimension12':'天猫精选',
           'dimension20':'无','dimension32':'无','dimension25':'10809','dimension39':'直达链接','cd49':'b','cd99':'无','cd96':'无','cd58':'无'});">
      </a>
      <div class="info-right ">
          <div class="title-box">
             <div class="title J_title">
                            {{data.title}}</div>
             <!--正常文章价格模块-->
                         <!--过期售罄价格模块-->
            <div class="old-price-box">
                                <p class="old-price"> <span class="old">{{data.outTime}}</span> <span>{{data.priceDesc}}</span></p>
                                                <p class="tips">小提示：此价格太抢手，发布后很快商家就调价了！</p>
                            </div>
         </div>

          <div class="info-details">
              <div class="author-info J_author_info">
                                                          <a  style="display:none" :href="data.authorUrl" target="_blank" >
                        <img :src="data.authorAvatar" alt="头像" class="author-img">
                        <span class="name">乐选优品</span>
                      </a>
                                          <div class="article_meta_tip">
                          <i class="icon-info-mark-o"></i>
                          <div>
                                                              好价信息来自热心值友爆料和商家自荐，经小编人工审核或小值机器人智能判断后发布。
                                                            <div class="pop-arrow-left"></div>
                          </div>
                      </div>
                      <span class="line"></span>
                                                  <span class="mall">{{data.platform}}</span>
                                  <span class="time">更新时间：{{data.updateTime}}</span>              </div>


            <!-- 优惠新标签 -->


                          <!-- 购买链接 -->
              <div class="btn-group J_btn_group">
                                                                      <!--去购买有下拉情况-->
                                                            <div class="btn-more-box">
                                        <a :href="data.link" target="_blank" class="go-buy btn"  style="display: inline-block;">
                                            去购买
                                            <i class="icon-angle-right-o-thin"></i>
                                        </a>
                                      <div class="other-link-box">
                                       <a :href="data.linkOther" target="_blank" class="other-link" >直筒玻璃杯</a>

                                                                                      <a target="_blank" class="other-link" rel="nofollow" title="5元优惠券" :href="data.linkOther2" > 5元优惠券 </a>
                                                                                                                    </div>
                                </div>
                                                                                <!-- 二维码购买 -->
                                                <!-- 二维码购买end -->

              </div>
              <!-- 购买链接 -->
          </div>
      </div>
  </div>











    <div  style="" class="item-name">



    <div class="coupon-box">
                    <p class="coupon-title">下单必点</p>
                                          <a :href="data.link" class="coupon-item" target="_blank" onclick="gtmAddToCart({'name':'佳偶 双层直筒玻璃杯 350ml *2件','id':'14039324' , 'price':'18','brand':'' ,'mall':'天猫精选', 'category':'日用百货/厨房用具/水具酒具/玻璃杯','metric1':'18','dimension10':'tmall.com','dimension9':'youhui','dimension11':'1阶价格','dimension12':'天猫精选','dimension20':'无','dimension32':'无','dimension25':'10809','dimension39':'直达链接','cd49':'b','cd99':'无','cd96':'无','cd58':'无'});">
                      <span class="coupon-info">5元优惠券</span>
                      <span class="line-img"></span>
                      <span class="btn-get">领取</span>
                  </a>
                                                                                          </div>




      <article class="txt-detail">
                   <div class="describe">{{data.remark}}</div>


       <p v-html='data.detail' itemprop="description"></p>





                        <div class="interests-statement">
                    是一家中立的消费门户网站，好价信息来自热心值友爆料和商家自荐，经小编人工审核或小值机器人智能判断后发布。促销折扣可能随时变化，请值友们购买前注意核实。
                  </div>
                
      

    </article>
      </div>






    <PcComments :pid="data.id"></PcComments>
  </div>
</template>

<script>
  import zwBase from '../../component/zwBase.vue';
 import PcComments from '../../component/datadisplay/PcComments.vue';
  export default {
    extends: zwBase,

    components: {PcComments

    },
    props:["data"],
    name: "phoneMain",
    data() {

      return {

      }
    },
    created() {




    },
    mounted() {

    },
    computed: {

    },
    methods: {

    }
  }
</script>
<style scoped>

.main-left {
    padding: 30px;
    width: 742px;
    float: left;
    background: #fff;
}


.info .img-box {
    width: 230px;
    height: 230px;
    display: -webkit-box;
    display: -webkit-flex;
    display: -ms-flexbox;
    display: flex;
    float: left;
    -webkit-box-align: center;
    -webkit-align-items: center;
    -ms-flex-align: center;
    align-items: center;
    border: 1px solid #f5f5f5;
    position: relative;
    margin-right: 30px;
}



.info .img-box .label.youhui {
    background: #fff url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAwAAAAQCAIAAACtAwlQAAAAGXRFW…2vgk0riy2aTEbXyZ5Ze3dfu1PrslbVWafvH+nmWFL/CzAAN/oNhvHTKrEAAAAASUVORK5CYII=) no-repeat;
    background-position: 2px;
}
.info .img-box .label {
    position: absolute;
    top: 10px;
    left: 10px;
    font-size: 12px;
    padding: 0 6px 0 22px;
    color: #999;
    z-index: 11;
    opacity: .8;
    height: 22px;
    line-height: 22px;
}

.info .img-box .main-img {
    max-width: 100%;
    max-height: 100%;
    display: block;
    margin: 0 auto;
}


.info .info-right {
    float: right;
    width: 422px;
    position: relative;
}

.info .info-right .price, .info .info-right .title {
    font-size: 20px;
    line-height: 30px;
    display: -webkit-box;
    -webkit-line-clamp: 3;
    -webkit-box-orient: vertical;
    overflow: hidden;
}

.info .info-right .old-price-box .old-price {
    font-size: 20px;
    color: #999;
    line-height: 30px;
    margin-top: 5px;
    display: -webkit-box;
    -webkit-line-clamp: 2;
    -webkit-box-orient: vertical;
    overflow: hidden;
}

.info .info-right .old-price-box .tips {
    margin-top: 6px;
    font-size: 12px;
    line-height: 22px;
    color: #999;
}

.info .info-right .info-details {
    position: relative;
}

.info .info-right .author-info {
    font-size: 0;
    color: #666;
    margin-top: 14px;
    height: 24px;
    line-height: 24px;
}

.info .info-right .author-info>* {
    margin-right: 10px;
    font-size: 12px;
}
.info .info-right a {
    color: #666;
}

.info .info-right .author-info .article_meta_tip {
    display: inline-block;
    position: relative;
    vertical-align: top;
    z-index: 80;
}

.info .info-right .author-info .line {
    display: inline-block;
    width: 1px;
    height: 12px;
    background: #ddd;
    position: relative;
    top: 6px;
    vertical-align: top;
}

.info .info-right .author-info .mall {
    color: #999;
    max-width: 85px;
    display: inline-block;
    overflow: hidden;
    white-space: nowrap;
    -o-text-overflow: ellipsis;
    text-overflow: ellipsis;
}


.info .info-right .author-info .time {
    float: right;
    color: #999;
    margin: 0;
}
.info .btn-group {
    position: relative;
    font-size: 0;
    padding-top: 12px;
}



.info .btn-group .btn-more-box {
    font-size: 14px;
    display: inline-block;
    margin-right: 14px;
    position: relative;
    -webkit-border-radius: 2px;
    border-radius: 2px;
}


.info .btn-group .btn {
    font-size: 14px;
    margin-right: 14px;
    display: inline-block;
    -webkit-border-radius: 2px;
    border-radius: 2px;
    color: #fff;
    text-align: center;
    position: relative;
    height: 28px;
    line-height: 28px;
}


.info .btn-group .go-buy {
    background: #f04848;
    width: 102px;
}
.info .btn-group .btn-more-box .go-buy {
    position: relative;
    margin-right: 0;
}



.info .btn-group .btn-more-box .other-link-box {
    display: none;
    position: absolute;
    background: #fff;
    top: 28px;
    left: 0;
    width: 100%;
    padding: 7px 0;
    -webkit-box-shadow: 0 2px 12px 0 rgba(0,0,0,.1);
    box-shadow: 0 2px 12px 0 rgba(0,0,0,.1);
    text-align: center;
}

.info .btn-group .btn-more-box:hover .other-link-box {
    display: block;

}
.info .btn-group .btn-more-box .other-link-box>a {
    padding: 0 7px;
    height: 28px;
    line-height: 28px;
    overflow: hidden;
    white-space: nowrap;
    -o-text-overflow: ellipsis;
    text-overflow: ellipsis;
}

.info .btn-group .btn-more-box .go-buy i {
    margin-left: 0;
}
.info .btn-group .go-buy i {
    position: relative;
    top: 1px;
    margin-left: 2px;
}
.info .info-right .author-info .name {
    max-width: 85px;
    display: inline-block;
    overflow: hidden;
    white-space: nowrap;
    -o-text-overflow: ellipsis;
    text-overflow: ellipsis;
}


.info .btn-group .btn-more-box .other-link {
    display: block;
    line-height: 24px;
    color: #666;
    white-space: nowrap;
    overflow: hidden;
    -o-text-overflow: ellipsis;
    text-overflow: ellipsis;
}
.info .info-right a {
    color: #666;
}
.info .info-right .author-info .article_meta_tip>div {
    display: none;
    left: 22px;
    top: -22px;
    color: #999;
    position: absolute;
    height: 68px;
    width: 177px;
    line-height: 16px;
    background: #fff;
    z-index: 3;
    padding: 10px;
    font-size: 12px;
    text-align: justify;
    -webkit-box-shadow: 0 2px 12px 0 rgba(0,0,0,.1);
    box-shadow: 0 2px 12px 0 rgba(0,0,0,.1);
    -webkit-border-radius: 2px;
    border-radius: 2px;
}

.info .info-right .author-info .author-img {
    vertical-align: top;
    margin-right: 6px;
    width: 24px;
    height: 24px;
    -webkit-border-radius: 50%;
    border-radius: 50%;
    display: inline-block;
}











/**cssartical **/
article.txt-detail {
    color: #333;
    margin-top: 30px;
}


article.txt-detail .baoliao-block, article.txt-detail .describe {
    font-size: 16px;
    line-height: 28px;
}

    article.txt-detail p {
        font-size: 16px;
        line-height: 28px;
        margin-bottom: 20px;
        word-break: break-all;
        word-wrap: break-word;
    }

    article.txt-detail p a {
        color: #5188a6;
    }




    .main-left .coupon-box {
        margin-top: 34px;
    }
    .content *, .content .main-left {
        word-break: break-all;
    }



    .main-left .coupon-box .coupon-title {
        font-weight: 700;
    }

    .main-left .coupon-box .coupon-item {
        margin: 20px 20px 0 0;
        background: rgba(240,72,72,.1);
        -webkit-border-radius: 2px;
        border-radius: 2px;
        display: inline-block;
        font-size: 0;
        height: 30px;
        -webkit-box-shadow: 0 1px 3px 0 rgba(0,0,0,.1);
        box-shadow: 0 1px 3px 0 rgba(0,0,0,.1);
        position: relative;
    }

    .main-left .coupon-box .coupon-item .coupon-info {
        float: left;
        color: #f04848;
        min-width: 174px;
        max-width: 376px;
        font-size: 14px;
        background: #fff;
        position: relative;
        padding: 0 14px;
        overflow: hidden;
        white-space: nowrap;
        -o-text-overflow: ellipsis;
        text-overflow: ellipsis;
        height: 30px;
        line-height: 30px;
    }


    .main-left .coupon-box, .main-left .coupon-box * {
        -webkit-box-sizing: border-box;
        box-sizing: border-box;
    }


    .main-left .coupon-box .coupon-item .btn-get {
        float: left;
        color: #fff;
        width: 56px;
        font-size: 14px;
        background: #f04848;
        text-align: center;
        height: 30px;
        line-height: 30px;
    }
    .info:after {
        content: "";
        display: table;
        clear: both;
    }

    article.txt-detail .interests-statement {
        font-size: 12px;
        color: #999;
        line-height: 24px;
    }






/**css评论css**/

#comments {
font-size: 14px;
    color: #333;
    margin-top: -30px;
}
#comments {
    padding: 50px 0 20px;
    word-break: break-all;
    word-wrap: break-word;
}


.panelTitle {
    font-size: 18px;
    clear: both;
    overflow: hidden;
    height: 40px;
    line-height: 40px;
    font-weight: 400;
}
.greyNum {
    font-size: 12px;
    color: #999;
    font-weight: 400;
}


.comment_sendwrap {
    font-size: 12px;
    margin-bottom: 16px;
    position: relative;
    padding-top: 20px;
}

#comment_error {
    margin-bottom: 10px;
}
#comment_error, .comment_sendPart {
    padding-left: 80px;
}

.comment_avatar {
    width: 60px;
    float: left;
}

.comment_list .userPic, .comment_sendwrap .userPic {
    float: none;
    display: block;
    margin-right: 0;
}

.userPic {
    width: 60px;
    height: 60px;
    float: left;
    margin-bottom: 8px;
    margin-right: 20px;
    text-align: center;
    overflow: hidden;
}

.userPic img {
    -webkit-border-radius: 100%;
    border-radius: 100%;
}

img {
    font-size: 0;
    border-style: none;
    vertical-align: middle;
}




.noLogin .comment_sendPart {
    position: relative;
}


.noLogin .comment_tips {
    display: block;
}

.comment_tips {
    position: absolute;
    left: 90px;
    top: 10px;
    font-size: 12px;
    color: #999;
    display: none;
}

.textarea_comment {
    padding: 10px;
    height: 60px;
    -webkit-border-radius: 2px;
    border-radius: 2px;
    width: 582px;
    margin-bottom: 10px;
    font-size: 12px;
    line-height: 20px;
    color: #999;
    resize: none;
    background-color: #f5f5f5;
    border: none;
}
.comment_operate {
    height: 28px;
    line-height: 28px;
}

.comment_operate {
    height: 28px;
    line-height: 28px;
}

.comment_operate .comment_smileBox {
    width: 380px;
    height: 40px;
    float: left;
    position: relative;
    z-index: 1;
}
i.icon-small {
    padding-left: 1px;
    font-size: 22px;
    color: #999;
}

.comment_operate .comment_share {
    width: 90px;
    height: 28px;
    float: right;
    color: #999;
}

.comment_operate .btn_sub, .comment_operate .btn_subGrey {
    width: 90px;
    height: 28px;
    text-align: center;
    background-color: #f04848;
    color: #fff;
    -webkit-border-radius: 2px;
    border-radius: 2px;
    font-size: 14px;
    float: right;
    cursor: pointer;
}

.btn_grey, .comment_operate .btn_subGrey {
    background-color: #ccc;
}


#comments .icon-medal {
    display: inline-block;
    height: 16px;
    font-size: 0;
    vertical-align: -4px;
}
.comment_list .user-box-new .user-medal-new {
    width: 12px;
    height: 12px;
    position: absolute;
    right: -1px;
    bottom: 0;
}
.comment_list .user-box-new .user-medal-new img {
    width: 100%;
    height: 100%;
}
#comments .icon-medal img {
    height: 16px;
    display: block;
}
#comments .icon-medal a {
    display: inline-block;
    margin-right: 4px;
    height: 16px;
}
/**css表情css**/


.smileLayerBg {
    position: absolute;
    top: 34px;
    left: 0;
    clear: both;
    width: 386px;
    height: 176px;
    padding: 12px;
    background-color: #f5f5f5;
    border: 1px solid #dcdcdc;
}
ul.smileBox {
    width: 386px;
    height: 140px;
    clear: both;
}
ul.smileBox li {
    width: 36px;
    height: 36px;
    float: left;
    margin: -1px 0 0 -1px;
    padding: 0;
    border-bottom: 0;
}

ul.smileBox li a.smile147 {
    background-position: 0 -71px;
}
ul.smileBox li a {
    background: url(//www.smzdm.com/resources/public/img/static_v2.gif) no-repeat;
    width: 34px;
    height: 34px;
    display: block;
    border: 1px solid #dcdcdc;
    background-color: #fff;
}
.smilePage {
    text-align: right;
    width: 384px;
    line-height: 24px;
    padding-top: 12px;
}

.smilePage a.current, .smilePage a:hover {
    background-color: #f04848;
    color: #fff!important;
    font-weight: 700;
    text-decoration: none;
}

.smilePage a {
    display: inline-block;
    width: 24px;
    height: 24px;
    margin-left: 4px;
    text-align: center;
    -webkit-border-radius: 2px;
    border-radius: 2px;
}


/**css评论区**/

.comment_listBox {
    margin-bottom: 0;
}
/**css评论每一行**/
.comment_list {
    padding-bottom: 20px;
    border-bottom: 1px solid #f5f5f5;
    margin-bottom: 16px;
}

/**css评论头像**/
.comment_avatar {
    width: 60px;
    float: left;
}
.comment_list .comment_avatar {
    height: auto;
    font-size: 12px;
    text-align: center;
    width: 36px;
    padding-right: 16px;
    position: relative;
    color: #999;
}
.comment_list .user-box-new {
    width: 36px;
    height: 36px;
    margin-bottom: 10px;
    margin-right: 0;
    text-align: center;
    float: none;
    position: relative;
}
.comment_list .user-box-new .user-avatar {
    width: 100%;
    height: 100%;
    -webkit-border-radius: 50%;
    border-radius: 50%;
    overflow: hidden;
    display: block;
}

.comment_list .user-box-new .user-avatar img {
    width: 100%;
    height: 100%;
}
img {
    font-size: 0;
    border-style: none;
    vertical-align: middle;
}

.comment_list .comment_avatar .grey {
    white-space: nowrap;
}

.comment_conBox {
    width: 630px;
    line-height: 24px;
    color: #666;
    float: left;
    font-size: 14px;
}

.comment_conBox {
    width: 630px;
    line-height: 24px;
    color: #666;
    float: left;
    font-size: 14px;
}


/**css评论右上角时间**/
.comment_avatar_time {
    height: 36px;
    line-height: 36px;
    overflow: hidden;
    margin-bottom: 4px;
}

.comment_avatar_time .time {
    float: right;
    margin-top: 2px;
    font-size: 12px;
    color: #999;
}
/**css用户名称**/
.comment_avatar_time>a.a_underline {
    margin-right: 4px;
    color: #333;
}
.comment_avatar_time>a.a_underline {
    margin-right: 4px;
    color: #333;
}

/**css月亮图片**/
.rank {
    display: inline;
    font-size: 12px;
}

i.icon-biaoqing-moon, i.icon-biaoqing-star, i.icon-biaoqing-sun, i.icon-crown {
    color: #fdcc38;
    margin-left: 4px;
    vertical-align: -2px;
    font-size: 16px;
}

.icon-biaoqing-moon:before {
    content: "\e97f";
}



/**css评论内容**/
.comment_con p {
    word-wrap: break-word;
    word-break: break-all;

}
/**css评论下方 备注**/
.comment_action {
    text-align: right;
}
/**css评论下方 备注  那个终端发送的**/
.comment_action .come_from {
    float: left;
    padding-right: 12px;
    color: #999;
    font-size: 12px;
}
/**css自家的app软件连接**/
.comment_action .come_from a {
    padding-left: 0;
    color: #5188a6;
}

/**css举报**/
.comment_action a {
    padding-left: 16px;
    color: #999;
    font-size: 12px;
}

.clear {
    float: none!important;
    clear: both;
    height: 0;
    overflow: hidden;

}


/**css 他人评论引用**/
.comment_blockquote:last-child, .comment_conBox blockquote:last-child {
    border-bottom: none;
}
.comment_blockquote, .comment_conBox blockquote {
    background-color: #f5f5f5;
    padding: 15px 19px;
    border-bottom: 1px solid #eee;
}


.comment_floor {
    float: right;
    color: #999;
    margin: -14px -12px 0 0;
}

blockquote .comment_con {
    padding-right: 15px;
    padding-top: 0;
    overflow: hidden;
}

blockquote .comment_con a.user_name {
    color: #333;
}

blockquote .comment_con p {
    display: inline;
}


</style>