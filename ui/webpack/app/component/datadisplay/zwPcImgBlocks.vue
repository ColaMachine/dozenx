<template>

  <div class="filter-feed-wrap feed-grid-wrap J_scroll_fixed">

<ul id="feed-main-list" class="feed-list-hits z-clearfix feed-main-list-gray">
    <li v-for="result in list" :key="result.id"  class="feed-row-wide" data-type="article" data-publish-time="2019-08-05 15:43:11" timesort="1564990991">
                            <div class="feed-block z-hor-feed  post-feed-block">
                                <div class="z-feed-img block-img-wide">
                                        <a onclick="sqFeedGA({'event':'1200006','tab':'全部','operation':'卡片样式','pagetitle':'${title}','频道':'原创'})" :href="result.url" target="_blank">
                                        <img :src="getPathValue(result.pic)" :alt="result.title">
                                    </a>
                                </div>
                                <div class="z-feed-content">
                                    <h5 class="z-feed-title">
                                        <a onclick="sqFeedGA({'event':'1200006','tab':'全部','operation':'卡片样式','pagetitle':'${title}','频道':'原创'})" :href="result.url" target="_blank">{{result.title}}</a>                                </h5>
                                    <div class="feed-block-describe">
                                      这里写内容简介
                                    </div>
                                    <div class="z-feed-foot z-clearfix">
                                        <div class="z-feed-foot-l">
                                            <span class="z-avatar-group">
                                                    <a onclick="sqFeedGA({'event':'1200006','tab':'全部','operation':'卡片样式','pagetitle':'${title}','name':'${result.creatorName}','频道':'原创'})" :href="getPathValue('member/'+result.creator)" target="_blank" class="z-avatar-pic">
                                                            <img :src="getPathValue('/avatar/'+result.avatar)" alt="${result.creatorName}">
                                                        </a>
                                                        <a onclick="sqFeedGA({'event':'1200006','tab':'全部','operation':'卡片样式','pagetitle':'${title}','name':'${result.creatorName}','频道':'原创'})" :href="getPathValue('member/'+result.creator)"  class="z-avatar-name" target="_blank">
                                                            {{result.creatorName}}                                                    </a>
                                                                                                            <img class="feed-talent-ordinary" src="https://res.smzdm.com/images/certificate/ordinary_life_medal.png">
                                                                                                                                                </span>
                                                        <span class="z-publish-time">{{result.createtime.substr(0,10)}}</span>
                                                    </div>
                                                    <div class="z-feed-foot-r">
                                                        <a href="javascript:;" class="z-group-data J_zhi_like_fav" title="赞" data-channel="11" data-article="70351336" data-type="zan">
                                                            <span class="one-plus">{{result.zanCount}}</span>
                                                            <i class="icon-thumb-up-o-thin"></i><span class="feed-number">1</span>
                                                        </a><a href="javascript:;" class="z-group-data J_zhi_like_fav" title="收藏" data-channel="11" data-article="70351336" data-type="fav" data-severs="收藏">
                                                            <i class="icon-star-o-thin"></i><span>{{result.collectCount}}</span>
                                                        </a>
                                                        <a :href="getPathValue('artical?view/'+result.id+'/#comments')" class="z-group-data" title="评论" target="_blank">
                                                            <i class="icon-comment-o-thin"></i><span>{{result.commentCount}}</span>
                                                        </a>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </li>

  </ul>


  </div>

</template>
<script type="text/javascript">
  import zwIcon from '../icon/zwIcon.vue';
  //this.src='/static/phone/img/carousel/1.png'
  export default {
    name: 'zwAppImgBlocks',
    components: {
      zwIcon
    },
    props: ["list"] //"tabs"
      ,

      /*

      pic 图片
      id主键
      title 标题
      commentCount 评论数
      createTime 创建时间
      zanCount 点赞数
      collectCount 收藏数目
      avatar 头像
      creator
      creatorName
       用户名称
      url 连接
      */
    data() {
      return {

      };
    },
    computed: {

    },
    mounted() {


    },
    methods: {
        getPathValue:function(value){
          return getPathValue(value);
        },
    },

    events: {

    }
  };
</script>
<style scoped>

.feed-grid-wrap #feed-main-list {
    padding: 10px 0 0;
    width: 873px;
}
#feed-main-list {
    padding: 0 14px;
}

.z-clearfix:after, .z-clearfix:before, .z-hor-feed:after, .z-hor-feed:before {
clear:both;
    content: "";
    display: table;
}


.feed-grid-wrap #feed-main-list {
    padding: 10px 0 0;
    width: 873px;
}

#feed-main-list {
    padding: 0 14px;
}

.z-clearfix, .z-hor-feed {
    zoom: 1;
}




.feed-grid-wrap #feed-main-list .feed-row-wide {
    float: left;
    border-bottom: none;
    padding: 0;
    margin-top: 0;
    margin-bottom: 10px;
}




.feed-grid-wrap #feed-main-list .feed-row-wide .feed-block {
    width: 280px;
    margin-right: 11px;
    background-color: #fff;
    position: relative;
    border-radius: 2px;
}


.feed-grid-wrap #feed-main-list .z-feed-img {
    border-radius: 2px 2px 0 0;
    width: 100%;
    height: 158px;
    margin-right: 0;
}
.feed-row-wide .z-feed-img {
    width: 280px;
    height: 157px;
    overflow: hidden;
    border-radius: 3px;
}
.feed-row-wide .block-img-wide {
    margin-right: 15px;
    position: relative;
}
.z-hor-feed .z-feed-img {
    position: relative;
    float: left;
}
.z-hor-feed .z-feed-img, .z-img-center {
    text-align: center;
    font-size: 0;
    zoom: 1;
}




.feed-grid-wrap #feed-main-list .z-feed-content {
    width: 100%;
    margin-top: 0;
    height: auto;
    position: static;
}
.feed-row-wide .z-feed-content {
    float: left;
    width: 538px;
    position: relative;
    height: 158px;
}







.feed-grid-wrap #feed-main-list .z-feed-content .z-feed-title {
    padding: 0 14px;
    font-size: 16px;
    margin: 14px 0;
    max-height: 48px;
    height: 48px;
}
.feed-row-wide .z-feed-title {
    font-size: 18px;
    color: #333;
    line-height: 24px;
    margin-bottom: 10px;
    max-height: 48px;
    overflow: hidden;
    margin-top: 6px;
}







.feed-grid-wrap #feed-main-list .z-feed-content .feed-block-describe {
    display: none;
}
.feed-row-wide .feed-block-describe {
    margin-bottom: 15px;
}
.feed-row-wide .feed-block-describe {
    font-size: 14px;
    line-height: 24px;
    color: #666;
    height: 48px;
    overflow: hidden;
    margin-bottom: 16px;
}


.feed-grid-wrap #feed-main-list .z-feed-content .z-feed-foot {
    border-top: 1px solid #f5f5f5;
    position: static;
}
.feed-row-wide .z-feed-foot {
    position: absolute;
    width: 100%;
    left: 0;
    bottom: 0;
}




.feed-grid-wrap #feed-main-list .z-feed-foot-l {
    position: absolute;
    background-image: -webkit-gradient(linear,left top,left bottom,from(hsla(0,0%,60%,0)),color-stop(98%,rgba(51,51,51,.29)));
    background-image: -o-linear-gradient(top,hsla(0,0%,60%,0) 0,rgba(51,51,51,.29) 98%);
    background-image: linear-gradient(-180deg,hsla(0,0%,60%,0),rgba(51,51,51,.29) 98%);
    top: 116px;
    left: 0;
    width: 252px;
    padding: 15px 14px 5px;
    font-size: 12px;
    color: #fff;
}
.z-hor-feed .z-feed-foot .z-feed-foot-l {
    float: left;
    position: relative;
    font-size: 0;
}



.z-avatar-group {
    line-height: 20px;
}



.z-avatar-group .z-avatar-pic {
    display: inline-block;
    width: 20px;
    height: 20px;
    margin-right: 10px;
    border-radius: 50%;
    overflow: hidden;
    text-align: center;
    vertical-align: -5px;
}

.feed-grid-wrap #feed-main-list .z-avatar-pic {
    width: 20px;
    height: 20px;
    margin-right: 6px;
}
.z-avatar-group .z-avatar-pic {
    vertical-align: middle;
}




.z-avatar-group .z-avatar-name {
    color: #666;
}

.feed-row-wide .z-avatar-name {
    font-size: 14px;
    max-width: 118px;
    display: inline-block;
    white-space: nowrap;
    overflow: hidden;
    vertical-align: middle;
    margin-right: 2px;
    -o-text-overflow: ellipsis;
    text-overflow: ellipsis;
}

.feed-row-wide .z-avatar-name {
    line-height: 20px;
    margin-top: 2px;
    color: #999;
}
.main-list .z-avatar-name, .feed-grid-wrap #feed-main-list .z-publish-time {
    font-size: 14px;
    color: #fff;
}


main-list .feed-row-wide .feed-talent-ordinary {
    vertical-align: -5px;
}
.feed-row-wide .feed-talent-ordinary {
    width: 14px;
    height: 14px;
    vertical-align: middle;
}






.feed-grid-wrap #feed-main-list .z-publish-time {
    font-size: 12px;
    float: right;
    line-height: 24px;
    height: 20px;
}
.feed-grid-wrap #feed-main-list .z-avatar-name, .feed-grid-wrap #feed-main-list .z-publish-time {
    font-size: 14px;
    color: #fff;
}
.feed-row-wide .z-publish-time {
    display: inline-block;
    line-height: 20px;
    vertical-align: -6px;
    font-size: 12px;
    margin-left: 15px;
}
.feed-row-wide .z-publish-time {
    color: #999;
}



.feed-grid-wrap #feed-main-list .z-feed-foot-r {
    width: 100%;
    height: 46px!important;
    font-size: 0;
    margin-top: 0;
    float: none;
    margin-left: -1px;
}




.z-group-data {
    display: inline-block;
    margin-right: 20px;
    font-size: 14px;
}


.J_zhi_like_fav {
    position: relative;
}

.feed-row-wide .z-group-data {
    color: #999;
}

.feed-grid-wrap #feed-main-list .z-group-data {
    font-size: 14px;
    margin: 14px 0;
    padding-left: 28px;
    width: 65px;
    line-height: 20px;
}

</style>