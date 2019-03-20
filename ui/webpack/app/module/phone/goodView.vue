<template>
  <div>

<zwRow >

<zwCol class="pull-right" span=24>
<zwAppTopBar >
    <a slot="left" href="#/shopMain">
  <img  class=""  :src="getPathValue('/static/img/header/back.png')">
  </a>
<span slot="middle"> 商品详情</span>

           <a slot="right" href="/home/static/html/vue/vueUserInfo.html">
<img  class="" :src="getPathValue('/static/img/header/information.png')">
</a>
</zwAppTopBar>

</zwCol>

</zwRow>
    <zwRow>
      <zwCol class="pull-right" span=24>

      <zwCrousel style="height:150px;" height=200 :imglist="imgList"> </zwCrousel>
      </zwCol>

    </zwRow>


    <div class="details-title details-title-haojia clearfix">
                 <div class="grey ellipsis">
                    <span class="mall"> {{data.platform}}</span>
                    <span class="line"> {{data.createTime}}</span>

                    <span class="line"></span>
                                    <span class="author">爆料人：{{data.creatorName}}</span>
                                </div>
                <h1>{{data.name}}</h1>
                            <p class="price">{{data.price}}元{{data.priceDesc}}</p>
                                            <div class="go-link"><a :href="data.link" target="_blank" onclick="gtm();">直达链接</a></div>
                        </div>



<article class="article">


    <h2 class="title-box"></h2>


        <p></p><p>{{data.remark}}</p><p></p>

    <p></p>
        <blockquote class="baoliao">

       <em class="title"> 网友爆料原文：</em>

        <p v-html="data.detail"></p>


       </p>
    <!--
        <p><a href="https://go.smzdm.com/ee8008d56b674415/cb_aa_yh_95_11787341_10124_36035_111_0" target="_blank" onclick="gtm();">京东</a>

    <p>凑单品：<a href="https://go.smzdm.com/6d167997a53401ac/cb_aa_yh_95_11787341_10124_36035_111_0" target="_blank" onclick="gtm();">梦龙</a></p>
       <p>叠加优惠券：<a href="https://go.smzdm.com/5ebc67870e28af45/cb_aa_yh_95_11787341_10124_36035_111_0" target="_blank" onclick="gtm();">199减100券</a></p>
-->
       </blockquote>


        <!--item-boxs-->
   <!-- <div class="item-boxs">
                                <div class="item-order clearfix">
            <span class="item-left">
                <i class="icons icon-a ctivety"></i>
                <span class="grey">订单截图</span>
            </span>
            <img src="https://y.zdmimg.com/201812/02/5c030002186cd4568.jpg" class="order-img">
        </div>
            </div> -->
    <!--item-boxs end-->

        </article>

<!--<ul class="tags-wrapper clearfix">
<li class="tags"><a href="//m.smzdm.com/fenlei/bingqilin/"
onclick="dataLayer.push({'event':'WAP优惠详情_分类','商品分类名':'冰淇淋'})">分类:冰淇淋</a></li>
<li class="tags"><a href="//m.smzdm.com/tag/%E5%90%83%E8%B4%A7/youhui/" onclick="dataLayer.push({'event':'WAP优惠详情_标签','标签名':'吃货'})">话题:吃货</a></li>
<li class="tags"><a href="//m.smzdm.com/tag/%E4%BA%AC%E4%B8%9C%E9%A3%9F%E5%93%81%E8%B4%AD/youhui/" onclick="dataLayer.push({'event':'WAP优惠详情_标签','标签名':'京东食品购'})">话题:京东食品购</a></li>
<li class="tags"><a href="//m.smzdm.com/mall/jd/" onclick="dataLayer.push({'event':'WAP优惠详情_商城','商城名':'京东'})">商城:京东</a></li>
<li class="tags"> <a href="//pinpai.m.smzdm.com/36035/" onclick="dataLayer.push({'event':'WAP优惠详情_品牌','品牌名':'WALL'S/和路雪'})">品牌:WALL'S/和路雪</a></li>
</ul>-->





     <div class="worth-section" style="padding: 20px 0;">
                 <h2 class="title-box" style="padding-left: 20px;padding-right: 20px;">我觉得这个商品：</h2>
                 <div class="tags-box" style="padding-left: 20px;padding-right: 20px; width: auto;">

                     <span class="J_zhi_like_fav z-group-data" data-type="zhi" data-zhi-type="1" data-channel="3" data-article="11792647" data-cid="1" data-atp="3" data-tagid="无">
                         <a href="javascript:;" @click='zan()' >
                         <i class="z-icon-zhi">顶</i>
                         <span>{{data.up}}</span>
                         </a>
                     </span>


                     <span class="J_zhi_like_fav z-group-data" data-type="zhi" data-zhi-type="-1" data-channel="3" data-article="11792647" data-cid="1" data-atp="3" data-tagid="无">
                         <a href="javascript:;" @click='down()'>
                             <i class="z-icon-not">踩</i>
                             <span>{{data.down}}</span>
                         </a>
                     </span>

                 </div>
                        </div>

  <!-- <zwAppBuyBottomBar ></zwAppBuyBottomBar>-->
<zwRow  style="display:block">
<zwCol  style="min-width:300px" class="zw-col-sm-24 pull-left" span=12>

<zwPanel :canFold=false state="open" >
<span slot="title" name="title">最新评论</span>
<p  slot="body" name="body">
<Comments  ref="comments" v-bind:pid="id" ></Comments>
</p>

</zwPanel>


                         </zwCol>
     </zwRow>

     <br></br><br></br><br></br>
     <div class="footer-wrap fixed-bottom lrBot flex">
                 <div class="btn-l">
                     <div @click="zan()" class="J_zhi_like_fav z-group-data" data-type="zhi" data-zhi-type="1" data-channel="3" data-article="11791026" data-cid="1" data-atp="3" data-tagid="无">
                         <i class="zm-icon-zhi-char">赞</i>
                         <span>{{data.up}}</span>
                     </div>

                     <div class="J_zhi_like_fav z-group-data z-group-last" data-type="fav" data-channel="3" data-article="11791026" data-cid="1" data-atp="3" data-tagid="无">

                         <zwIcon type="star">关注</zwIcon>
                         <span>{{data.star}}</span>
                     </div>
                     <div class="share button_share">
                         <i class="zm-icon-share-o"></i>
                         分享
                     </div>
                     <div @click="showCommentDialog()" class="comments" id="js-foot-comments">
                         <i class="zm-icon-comments-o">评论</i>
                         <span>{{data.comments}}</span>
                     </div>
                 </div>
                 <div class="placeholder"></div>
                 <div class="btn-r">
                  <a :href="data.link" id="go-buy-mobile" target="_blank" onclick="gtm();"><s></s>直达链接</a>
                 </div>


             </div>

  </div>

</template>
<script>

  import zwRow from '../../component/layout/zwRow.vue';

    import zwInput from '../../component/dataentry/zwInput.vue';
  import zwCol from '../../component/layout/zwCol.vue';
  import zwIcon from '../../component/icon/zwIcon.vue';
  import zwBox from '../../component/layout/zwBox.vue';

  import zwCrousel from '../../component/datadisplay/zwCrousel.vue';


  import zwAppBottomBar from '../../component/datadisplay/zwAppBottomBar.vue';
  import zwButton from '../../component/button/zwButton.vue';
  import zwButtonGroup from '../../component/button/zwButtonGroup.vue';

 import zwAppTopBar from '../../component/navigation/zwAppTopBar.vue';

 import zwJumbotron from '../../component/datadisplay/zwJumbotron.vue';

  import zwForm from '../../component/dataentry/zwForm.vue';
  import zwFormItem from '../../component/dataentry/zwFormItem.vue';

  import Comments from '../../component/datadisplay/Comments.vue';
  import zwBase from '../../component/zwBase.vue';
 import zwPanel from '../../component/datadisplay/zwPanel.vue';
  import zwAlbum from '../../component/datadisplay/zwAlbum.vue';

  import zwAppBuyBottomBar from '../../component/datadisplay/zwAppBuyBottomBar.vue'

  //import {PATH,getQueryString,Ajax} from "../../../../src/main/webapp/static/js/zwcommon"

  export default {
  extends:zwBase,

    components: {
    zwBase,zwPanel,
      zwRow,
      zwCol,
      zwIcon,
      zwBox,

      zwCrousel,

      zwAppBottomBar,

      zwButtonGroup,
      zwButton,
      zwInput,
      zwAppTopBar,
      zwJumbotron,
      zwForm,zwAlbum,Comments,
      zwFormItem,zwAppBuyBottomBar
    },
    name: "phoneMain",
    data() {

      return {
      id:getHashValue("id"),

      images:[],
        user:{},
       data:{
       imgList:[],
       },
       }



    },
    created(){
Ajax.getJSON(PATH+"/goods/view.json?id="+this.id, null, function(result) {
                        if(result.r == AJAX_SUCC && result.data) {

                            this.data =result.data;

                            var ary =[];
                            ary.push({url:'',pic:window.getPathValue(this.data.img)});
                             ary.push({url:'',pic:window.getPathValue(this.data.img1)});
                              ary.push({url:'',pic:window.getPathValue(this.data.img2)});
                               ary.push({url:'',pic:window.getPathValue(this.data.img3)});
                               this.imgList= ary;
                               console.log("---------------------------")
                               console.log(ary)
                        }
                      }.Apply(this));
    },
    mounted() {



    },
    computed: {

    },
    methods: {
        showCommentDialog:function(){
            this.$refs.comments.showCommentDialog();
        },
        zan:function(){
            Ajax.post(PATH+"/goods/zan",{"pid":this.id},function(result){
                console.log(result);
                if(result.r==AJAX_SUCC){
                    this.data.up=result.data.up;
                    this.data.down=result.data.down;
                }else{
                     console.log(result.msg);
                     ztips(result.msg);
                 }
            }.Apply(this));

        },
        down:function(){
            Ajax.post(PATH+"/goods/down",{"pid":this.id},function(result){
                            console.log(result);
                            if(result.r==AJAX_SUCC){
                                this.data.up=result.data.up;
                                this.data.down=result.data.down;
                            }else{
                                 console.log(result.msg);
                                 ztips(result.msg);
                             }
                        }.Apply(this));

        }

    }
  }
</script>
<style scoped>

/**标题 **/
.details-title {
    padding: 10px 4% 30px;
    background: #fff;
}

.index-tit-banner {
    height: 0.98rem;
    line-height: 0.98rem;
    text-align: center;
    background-color: #f7f7f7;
}

.details-title .grey {
    color: #888;
    font-size: 12px;
    line-height: 20px;
    margin-bottom: 5px;
}
.details-title .grey span {
    display: inline-block;
    vertical-align: middle;
}
.details-title h1 {
    font-size: 20px;
    line-height: 30px;
    font-weight: 700;
}

.details-title .price {
    font-size: 20px;
    line-height: 30px;
    color: #f04848;
    margin-bottom: 5px;
}
.details-title .go-app, .details-title .go-link {
    padding: 0 10px;
    line-height: 25px;
    height: 25px;
    font-size: 12px;
    background-color: #f04848;
    background: -webkit-linear-gradient(#f36e5d,#f04848);
    background: linear-gradient(#f36e5d,#f04848);
    border-radius: 2px;
    float: left;
}



/**文章 **/
.article {
    background: #fff;
    margin-bottom: 0;
}

article {
    margin-bottom: 40px;
    font-size: 14px;
    line-height: 25px;
    padding: 0 4%;
    word-wrap: break-word;
    overflow-x: hidden!important;
}


article, aside, canvas, details, figcaption, figure, footer, header, hgroup, menu, nav, section, summary {
    display: block;
}


article h2, article h3, article h4, article h5, article h6 {
    font-size: 19px;
    margin-bottom: 16px;
    color: #555;
    font-weight: 700;
}

article blockquote.baoliao em.title {
    float: left;
}

article blockquote.baoliao {
    border-left: none;
    padding-left: 0;
}
article blockquote {
    margin: 0 0 25px;
    border-left: 2px solid #e3e3e3;
    padding-left: 4%;
}

article blockquote p {
    line-height: 25px;
    color: #777;
    padding-right: 0;
}

.item-order .item-left {
    display: block;
    margin-bottom: 20px;
}
.item-order img {
    max-width: 100%;
    max-height: 130px;
}

.item-boxs {
    margin-bottom: 20px;
}

.tags-wrapper {
    margin-bottom: 7px;
    padding: 0 4%;
}


.tags-wrapper .tags {
    font-size: 14px;
    line-height: 24px;
    margin: 0 15px 15px 0;
    float: left;
    max-width: 100%;
    border-radius: 2px;
    border: 1px solid #f1f1f1;
}


/**底框 **/



.footer-wrap {
    width: 100%;
    max-width: 750px;
    height: 38px;
    line-height: 38px;
    border-top: 1px solid #f3f3f3;
    position: fixed;
    bottom: 0;
    background: #fbfbfb;
    display: none;
}


.footer-wrap .btn-l {
    -webkit-box-flex: 1;
    -webkit-flex: 1;
    -ms-flex: 1;
    flex: 1;
    display: -webkit-box;
    display: -webkit-flex;
    display: -ms-flexbox;
    display: flex;
    -webkit-justify-content: space-around;
    -ms-flex-pack: distribute;
    justify-content: space-around;
    font-size: 10px;
    color: #888;
    line-height: 42px;
    background-color: #fbfbfb;
}

.flex {
    display: -webkit-box;
    display: -webkit-flex;
    display: -ms-flexbox;
    display: flex;
}

i {
    font-family: zdm_icon!important;
    speak: none;
    font-style: normal;
    font-weight: 400;
    font-variant: normal;
    text-transform: none;
    line-height: 1;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
}


.footer-wrap .placeholder {
    float: right;
    width: 38px;
    height: 38px;
    position: relative;
    z-index: 1;
    border-radius: 50%;
    background: #fbfbfb;
    margin-right: -8px;
}

.footer-wrap .btn-r {
    padding: 0 15px 0 25px;
    background: #f04848;
    color: #fff;
    font-size: 16px;
    float: right;
    height: 40px;
    margin-top: -1px;
}

.footer-wrap .btn-r a {
    color: #fff;
}

/*我觉得赞 */

.worth-section {
    padding: 20px;
    background: #fff;
}

.worth-section .title-box {
    font-size: 13px;
    margin-bottom: 15px;
}


.worth-section .tags-box {

    padding-left: 20px;
    padding-right: 20px;
    width: auto;

    width: 100%;
    height: 36px;
    display: -webkit-box;
    display: -webkit-flex;
    display: -ms-flexbox;
    display: flex;
    font-size: 14px;
    -webkit-box-pack: center;
    -webkit-justify-content: center;
    -ms-flex-pack: center;
    justify-content: center;
}

.worth-section .tags-box .z-group-data {
    width: 84px;
    height: 34px;
    border: 1px solid #ccc;
    border-radius: 2px;
    text-align: center;
    margin-right: 30px;
}

.worth-section .tags-box a {
    display: inline-block;
    line-height:34px;
}

.worth-section .tags-box .z-group-data i.z-icon-zhi {
    background: url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAADwAAAA8CAYAAAA6/NlyAAAAGXRFW…uPemYaA4HzzZ/iwY3n2SXhTkEqlvtEIv7+olr9WOv9PwEGAGgSsahWCJyMAAAAAElFTkSuQmCC) no-repeat 50%/contain;
}

</style>