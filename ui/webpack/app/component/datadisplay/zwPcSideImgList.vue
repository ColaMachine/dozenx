<template>
<ul class="side-img-text side-zhongce-ul">
                                    <li class="imgtext-item">
                        <a class="imgtext-img" onclick="dataLayer.push({'event':'1200034','position':'1','pagetitle':'9102年的卡拉OK，我们应该这么唱！体验HOPE 向往 K500 无线背景音乐K歌主机套装'});" href="https://post.smzdm.com/p/adwrqlpz/" target="_blank">
                            <img src="//am.zdmimg.com/201908/01/5d42e32a87bc39495.jpg_a100.jpg" alt="9102年的卡拉OK，我们应该这么唱！体验HOPE 向往 K500 无线背景音乐K歌主机套装">
                        </a>
                        <div class="imgtext-content">
                            <a class="imgtext-title" onclick="dataLayer.push({'event':'1200034','position':'1','pagetitle':'9102年的卡拉OK，我们应该这么唱！体验HOPE 向往 K500 无线背景音乐K歌主机套装'});" href="https://post.smzdm.com/p/adwrqlpz/" target="_blank">9102年的卡拉OK，我们应该这么唱！体验HOPE 向往 K500 无线背景音乐K歌主机套装</a>
                            <div class="imgtext-foot">
                                <span class="imgtext-thumb">
                                    <i class="icon-thumb-up-o-thin"></i>
                                    <span>65</span>
                                </span>
                                <span class="imgtext-comment">
                                    <i class="icon-comment-o-thin"></i>
                                    <span>98</span>
                                </span>
                            </div>
                        </div>
                    </li>
                                    <li class="imgtext-item">
                        <a class="imgtext-img" onclick="dataLayer.push({'event':'1200034','position':'2','pagetitle':'有了“唱吧”G2小巨蛋喇叭麦克风，随时随地嗨唱，每一首都是我的主打歌'});" href="https://post.smzdm.com/p/apzklq70/" target="_blank">
                            <img src="//qnam.smzdm.com/201908/05/5d47b3f98b6a16674.jpg_a100.jpg" alt="有了“唱吧”G2小巨蛋喇叭麦克风，随时随地嗨唱，每一首都是我的主打歌">
                        </a>
                        <div class="imgtext-content">
                            <a class="imgtext-title" onclick="dataLayer.push({'event':'1200034','position':'2','pagetitle':'有了“唱吧”G2小巨蛋喇叭麦克风，随时随地嗨唱，每一首都是我的主打歌'});" href="https://post.smzdm.com/p/apzklq70/" target="_blank">有了“唱吧”G2小巨蛋喇叭麦克风，随时随地嗨唱，每一首都是我的主打歌</a>
                            <div class="imgtext-foot">
                                <span class="imgtext-thumb">
                                    <i class="icon-thumb-up-o-thin"></i>
                                    <span>65</span>
                                </span>
                                <span class="imgtext-comment">
                                    <i class="icon-comment-o-thin"></i>
                                    <span>135</span>
                                </span>
                            </div>
                        </div>
                    </li>
                                    <li class="imgtext-item">
                        <a class="imgtext-img" onclick="dataLayer.push({'event':'1200034','position':'3','pagetitle':'文zhong艺2青年的自我修养—小米CC9超深入解析'});" href="https://post.smzdm.com/p/amm5eoxz/" target="_blank">
                            <img src="//qnam.smzdm.com/201907/30/5d4057357fdc72414.jpg_a100.jpg" alt="文zhong艺2青年的自我修养—小米CC9超深入解析">
                        </a>
                        <div class="imgtext-content">
                            <a class="imgtext-title" onclick="dataLayer.push({'event':'1200034','position':'3','pagetitle':'文zhong艺2青年的自我修养—小米CC9超深入解析'});" href="https://post.smzdm.com/p/amm5eoxz/" target="_blank">文zhong艺2青年的自我修养—小米CC9超深入解析</a>
                            <div class="imgtext-foot">
                                <span class="imgtext-thumb">
                                    <i class="icon-thumb-up-o-thin"></i>
                                    <span>60</span>
                                </span>
                                <span class="imgtext-comment">
                                    <i class="icon-comment-o-thin"></i>
                                    <span>119</span>
                                </span>
                            </div>
                        </div>
                    </li>
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