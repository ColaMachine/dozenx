<template>

  <ul class="app-li pc">



    
    <li v-for="(result,index) in list" v-bind:key="index" >



<span class="embed-card  main-site" style=""><a itemprop="description" target="_blank"
                                                onclick="gtmWikiCard({'id':'1759529'});" class=""
                                               :href="result.url" ><span class="z-tag-baike">国内</span></a><span
        class="embed-card-logo"><a itemprop="description" target="_blank" onclick="gtmWikiCard({'id':'1759529'});"
                                   class="" :href="result.url" ><img class=""
                                                                                          :src="result.pic"></a></span><span
        class="embed-card-info"><a itemprop="description" target="_blank" onclick="gtmWikiCard({'id':'1759529'});"
                                   class="" :href="result.url"><span class="card-name">{{result.title}}
         <strong class="price">{{result.price}}</strong></span></a>

         <span class="card-abstract">{{result.subTitle}}</span>
          <span class="card-abstract">{{result.content}}</span>
         <span class="card-controls"><span class="group-data"> <i><zwIcon type="good"></zwIcon>赞</i>{{result.up}}</span>
         <span class="group-data"> <i><zwIcon type="comment"></zwIcon>评论</i>{{result.commments}}</span>
         <span class="group-data"> <i><zwIcon type="star"></zwIcon>收藏</i>{{result.stars}}</span>
         <a itemprop="description"
             target="_blank"
             onclick="gtmAddToCart({'name':'佳偶 双层直筒玻璃杯 350ml *2件','id':'1759529' , 'price':'17.8','brand':'无' ,'mall':'无', 'category':'无/无/无/无','metric1':'17.8','dimension10':'smzdm.com','dimension9':'youhui','dimension11':'1阶价格','dimension20':'无','dimension25':'无','dimension64':'yuanchuang'});if(typeof change_direct_url != 'undefined' &amp;&amp; change_direct_url instanceof Function){change_direct_url(this)}"
             class=""
             href="https://go.smzdm.com/60d47db5d96365b8/ca_aa_yh_1515_14039434_10728_0_0_322337"
             rel="nofollow">
         <span class="card-btn-deep">去购买</span></a></span></span></span>




    </li>;

  </ul>

</template>
<script type="text/javascript">
/**
数据类型 title 标题  content 内容 price 价格 url 点击的链接
subtitle 副标题
采用两个display block 的 span 做横向展示
use two displayblock span to hirozontal show view   the second span  set overflow hidden can make the two block display in one row
 amazing

 and in the second span .head body and foot was three common span set height and line height to make sure it will not beyong the height of first block span
**/
  import zwIcon from '../icon/zwIcon.vue';
  //this.src='/static/phone/img/carousel/1.png'
  export default {
    name: 'zwAppImgList',
    components: {
      zwIcon
    },
    props: ["list"] //"tabs"
      ,
    data() {
      return {

      };
    },
    computed: {

    },
    mounted() {
      /* Ajax.getJSON("/goods/list.json?curpage=1&pagesize=10",null,function(result){
                     if(result.r==AJAX_SUCC){
                         this.list=result.data;
                     }
                 }.Apply(this));*/

    },
    methods: {

    },

    events: {

    }
  };
</script>
<style>

.embed-card, span.embed-card {
    display: block;
    position: relative;
   /* width: 602px;*/
    padding: 10px;
    margin: 10px 10px;
    overflow: hidden;
    background: #fff;
    -webkit-box-shadow: 0 2px 5px 0 rgba(0,0,0,.08);
    box-shadow: 0 2px 5px 0 rgba(0,0,0,.08);
    -webkit-border-radius: 2px;
    border-radius: 2px;
    -webkit-transition: all .2s linear;
    -o-transition: all .2s linear;
    transition: all .2s linear;
}
.embed-card a, span.embed-card a {
    padding-right: 0;
    text-decoration: none;
    color: #313131;
    color: #333;
    background: #ccc;
}
.embed-card span, span.embed-card span {
    display: block;
    -webkit-border-radius: 2px;
    border-radius: 2px;
    overflow: hidden;
    padding-right: 0;
}
.embed-card-logo {
    float: left;
    margin-right: 14px;
}

.embed-card-logo img {
    display: block;
    width: 150px;
    height: 150px;
    margin: 0!important;
}
img {
    font-size: 0;
    border-style: none;
    vertical-align: middle;
}

.embed-card-info {
    padding-right: 4px;
    overflow: hidden;
}



.embed-card.main-site .embed-card-info .card-name {
    height: 44px;
    overflow: hidden;
    margin-top: 7px;
    line-height: 22px;
    white-space: normal;
}
.embed-card-info .card-name {
    font-size: 16px;
    line-height: 34px;
    margin-bottom: 10px;
    white-space: nowrap;
    overflow: hidden;
    -o-text-overflow: ellipsis;
    text-overflow: ellipsis;
    display: -webkit-box;
    -webkit-line-clamp: 2;
    -webkit-box-orient: vertical;
    font-weight: 700;
    word-break: normal;
}


.embed-card {
    -webkit-transition: all .2s linear;
    -o-transition: all .2s linear;
    transition: all .2s linear;
}

.content *, .content .main-left {
    word-break: break-all;
}


.embed-card-info .card-abstract {
    height: 36px;
    margin: 12px 0;
    font-size: 12px;
    color: #999;
    line-height: 18px;
    overflow: hidden;
}

.embed-card-info .card-controls .group-data, .embed-card-info .card-controls .mall-data {
    float: left;
    margin-right: 10px;
    color: #999;
    font-size: 12px;
}


.embed-card.main-site .embed-card-info .card-btn-deep {
    background-color: #f04848;
    color: #fff;
}

.embed-card-info .card-btn-black, .embed-card-info .card-btn-deep, .embed-card-info .card-btn-light {
    float: right;
    width: 68px;
    height: 28px;
    margin-left: 10px;
    -webkit-border-radius: 2px;
    border-radius: 2px;
    line-height: 28px;
    text-align: center;
    font-size: 12px;
}

.embed-card .z-tag-baike, .embed-card .z-tag-brand {
    position: absolute;
    top: 10px;
    left: 10px;
    padding-right: 3px;
}
.z-tag-baike {
    opacity: .8;
    background: #f04848;
    color: #fff;
    -webkit-border-radius: 2px;
    border-radius: 2px;
}
.z-tag-baike, .z-tag-brand {
    display: inline-block;
    height: 17px;
    padding: 0 3px;
    -webkit-border-radius: 2px;
    border-radius: 2px;
    margin-right: 14px;
    line-height: 18px;
    font-size: 12px;
    vertical-align: 3px;
}

</style>