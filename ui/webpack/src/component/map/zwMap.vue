
<template>
<div>
<div ref="container" id="container">12333333333333333333</div>



<div class="m-Map" ><div id='allmap' style="width:100vw,height:100vh"></div>
    <div class="locate" id="locate"></div>

    <div  id="mapAddDialogue" style="display:none" class="m-Hotarea">
        <div class="item">
            <div id="mapAddDialogueName" class="hotareaName">
               123123123123
            </div>
            <div id="mapAddDialogueDist" class="dist">123123</div>
        </div>
        <div class="item">
            <div class="title">标题：</div>
            <div id="mapAddDialogueName123" class="content">
                <input id="name" type="text" style="width:80%;border:1px solid blue;">
                </input>
                <input id="mapAddLat" type="hidden" >  </input>
                <input id="mapAddLng" type="hidden" >  </input>
            </div>
        </div>
        <div class="item">
            <div class="title">描述：</div>
            <div id="mapAddDialogueContent" class="content">

                <textarea id="content" rows="5" style="width:80%;border:1px solid blue"></textarea>

            </div>
        </div>
        <div class="item">
            <div class="title">图片：</div>
            <div id="mapAddDialoguepic" class="content">
                <input  id="file" name="file"  value="/timebuysrv/static/img/timg.jpeg" style="display:none" class="form-control input-sm"   maxlength="100"></input>

            </div>
        </div>

        <div class="buttons">
            <a href="javascript:void(0)" id="saveBtn"  >提交</a>
            <a  class="error"  id="addCancelBtn"  >取消</a>
        </div>

    </div>

    <div  id="mapDialogue" style="display:none" class="m-Hotarea">
        <div class="item">
            <div id="mapDialogueName" class="hotareaName"></div>
            <div id="mapDialogueDist" class="dist"></div>
        </div>
        <div class="item">
            <div class="title">名称：</div>
            <div id="mapDialogueName" class="content"></div>
        </div>
        <div class="item">
            <div class="title">时间：</div>
            <div id="mapDialogueTime" class="content"></div>
        </div>
        <div class="item">
            <div class="title">详细：</div>
            <div id="mapDialogueContent" class="content"></div>
        </div>
        <div class="item">
            <div class="title">图片：</div>
            <div  class="content"><img style="width:50%;max-width:100px;" id="mapDialoguePic" src=""/></div>
        </div>
        <div class="buttons">
            <a href="/hotspot/hotareaList">附近信息列表</a>
            <a  id="viewCancelBtn" class="error" >关闭</a>
        </div>
    </div>
</div>
<div class="toolBar" id="placeBtn" style="background-color:red;position:absolute;width:100px;height:30px;border:1px solid blue;top:10px;right:15px">
    在地图上放置点发布信息

    </div>
<div class="toolBar" id="placeBtn" style="background-color:red;position:absolute;width:100px;height:30px;border:1px solid blue;top:10px;left:15px">
   <a id="loginBtn" href="javascript:void(0)">登录</a>

</div>
</div>
</template>

  <script type="text/javascript">

//import  {baiduApi}  from "https://api.map.baidu.com/api?v=2.0&ak=0B1d2eff62bd35d2d98a8395b2560fe2";
//import badiduDrawingManager from "https://api.map.baidu.com/library/DrawingManager/1.4/src/DrawingManager_min.js";
//import baiduDrawingManagerCss from "https://api.map.baidu.com/library/DrawingManager/1.4/src/DrawingManager_min.css";
//import baidu_map from "../../../../src/main/webapp/static/html/weixin/baidu_map.js";




import zwBase from '../zwBase.vue';
import zwIcon from '../icon/zwIcon.vue';
export default {
        name: 'zwMap',
        extends:"zwBase",
         components:{zwIcon,zwBase},
        props: ["images"

        ],
        data () {
            return {

            };
        },
        computed: {

        },
        mounted () {
            MyMap.componentDidMount(this.$refs.container);
            this.$refs.container.style.height=getWinHeight()+"px";
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
<style>
 html,
    body {
    height: 100%;
    margin: 0px;
    padding: 0px
    }
    #container {
    width: 100%;
    height: 100%
    }

    .m-Map{
        border:1px solid red;
        }

        html, body, #app {
        height: 100%; }

        #app > div {
        height: 100%; }
        .m-Error {
        text-align: center; }
        .m-Error h4 {
        margin: 0 auto;
        color: #000;
        font-size: 18px;
        line-height: 40px; }
        .m-Error .first {
        margin-top: 30%; }
        .m-Error .hotpot {
        display: inline-block;
        text-decoration: none;
        background: #FFFFFF;
        border: 1px solid #4A90E2;
        width: 100px;
        height: 34px;
        line-height: 34px;
        font-size: 14px;
        color: #4A90E2;
        margin-top: 20%; }
        .m-Error .active {
        color: #fff;
        background: #4A90E2; }
        html, body, div, span, applet, object, iframe,
        h1, h2, h3, h4, h5, h6, p, blockquote, pre,
        a, abbr, acronym, address, big, cite, code,
        del, dfn, em, img, ins, kbd, q, s, samp,
        small, strike, strong, sub, sup, tt, var,
        b, u, i, center,
        dl, dt, dd, ol, ul, li,
        fieldset, form, label, legend,
        table, caption, tbody, tfoot, thead, tr, th, td,
        article, aside, canvas, details, embed,
        figure, figcaption, footer, header, hgroup,
        menu, nav, output, ruby, section, summary,
        time, mark, audio, video {
        margin: 0;
        padding: 0;
        border: 0;
        font-size: 100%;
        font: inherit;
        vertical-align: baseline; }

        /* HTML5 display-role reset for older browsers */
        article, aside, details, figcaption, figure,
        footer, header, hgroup, menu, nav, section {
        display: block; }

        body {
        line-height: 1; }

        ol, ul {
        list-style: none; }

        blockquote, q {
        quotes: none; }

        blockquote:before, blockquote:after,
        q:before, q:after {
        content: '';
        content: none; }

        table {
        border-collapse: collapse;
        border-spacing: 0; }

        html {
        font-size: 10px; }

        body {
        font-size: 1.4rem; }
        .m-HotareaList {
        height: 100%;
        position: relative;
        overflow: hidden; }
        .m-HotareaList .title {
        position: absolute;
        width: 100%;
        top: 0;
        padding: 0 30px;
        height: 44px;
        line-height: 44px;
        background: #f1f1f1;
        color: #888;
        font-size: 12px;
        overflow: hidden;
        box-sizing: border-box; }
        .m-HotareaList .hotareaList {
        padding: 44px 15px 0;
        line-height: 44px;
        color: #333;
        height: 100%;
        overflow: auto;
        box-sizing: border-box; }
        .m-HotareaList .hotareaList li {
        position: relative;
        padding: 0 15px;
        border-bottom: 1px solid #eee; }
        .m-HotareaList .name {
        float: left;
        width: 75%; }
        .m-HotareaList .distance {
        margin-left: 25%; }
        .m-HotareaList .arrowDown {
        height: 80px;
        background: #fff url(data:image/png;base64,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) no-repeat;
        background-position: center;
        background-size: 32px; }
        .m-HotareaList .arrowRight {
        display: inline-block;
        position: absolute;
        width: 4px;
        height: 4px;
        border: 1px solid #333;
        border-width: 1px 1px 0 0;
        transform: rotate(45deg);
        right: 9px;
        top: 20px; }
        .m-HotareaList .noresult {
        line-height: 100px;
        text-align: center; }
        .m-Map .locate {
        position: absolute;
        left: 15px;
        bottom: 220px;
        width: 32px;
        height: 32px;
        border: 1px solid #bbb;
        background: #fff url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABgAAAAYCAYAAADgdz34AAAAAXNSR0IArs4c6QAAAv5JREFUSA2dlk1IVFEUx31vPmymhRRWpkiBuCo3zYc6ThAtojArbBEIuqtAd0VbA7ctalNYOyWIFokKbqMY0HFmbFNBHxiFWFYUFITaOGO///hePXV843ThcM895/x/Z+579703RoXLaG5u3pfP58+srq62U9aA1VrlH5lnDcOYME1zfHp6+rMV3zQZmyIEYrHY3uXl5eu4FzEf9gPYCxodwq/Af4l/GLcKy7K+5/f7ByYnJ78o7xymcyE/EomcBP4GtxfhOHaio6NjdyaTiROblclXTDnV0KxPGmnJrxvrdhAOhy+TvY1gzuPx9KRSqYSzOhQKZbSemZkJO+PRaPRoLpcbplk98T5+wF07/3cHwE8BviN9MBiMboTbgmKzaqWRVgyx7LpCA7ZWQ+IBv+BDIBA4nUgkvtoF252lkVYMscSUttCAgG6oblh3CfgYNbKiw9J2k6yymBVGW1tbLTdIXUe4theKKssMcq8espPOysrKA+bS0tJZ4F4Yg2Vy3MoHxRTbpJOu2zeO3VM3RTk5sWB+R9NucMdf4SxwtI6VAylVC/cJNTVq8BPnN1t66xTxC0ZoesMZ28qHcQ19pzOPvpG130tCD5sH2+ksIL7DuXbzrdqNejEN7eA1BfOcoONukHJznKTH7KJOz8E7rMnaSbmcovUWq0lsNZjAqvU+KVr9H0GLVS22jukYluO9f6kUi20HW1tbA6XqxBJTbDOdTs8hGMK6eH9E3MQIHmWz2RG3GovRJabYhXeRz+frJ/CLa3c/Ho/v2gpAfo9sq7y0YohlMddedlNTU/MEu0k2Li4ujrk1cYNLK4ZYFnOtgUQc01EuwVXcOO+QJN/jkOLbGdzUsDTUxmFcEcvWFS6RveDJvcmX7Dy/om5lZSXNMzLENY3Z+Y2zcqrha5aSRloYt5x16z6ZdgJhPSdhgHUPppO2wPwcO4JpPMP07OijkseG+XfRbx0Ylv9G0QZ2uqWl5SA7Oce6HWvA9lu5T8z6AzDh9XpHk8nkeyu+afoD5/BdaLh9YOUAAAAASUVORK5CYII=) no-repeat;
        background-position: center;
        background-size: 18px; }

        .m-Hotarea {
        position: absolute;
        bottom: 0;
        padding: 20px;
        width: 100%;
        /*height: 203px;*/
        background: #fff;
        color: #333;
        box-sizing: border-box; }
        .m-Hotarea .item {
        position: relative;
        line-height: 30px;
        overflow: hidden; }
        .m-Hotarea .hotareaName {
        float: left;
        font-size: 16px;
        line-height: 36px;
        font-weight: bold; }
        .m-Hotarea .dist {
        float: right;
        line-height: 36px; }
        .m-Hotarea .title {
        float: left;
        width: 70px; }
        .m-Hotarea .content {
        margin-left: 70px; }
        .m-Hotarea .buttons {
        position: absolute;
        width: 100%;
        left: 0;
        bottom: 20px;
        text-align: center; }
        .m-Hotarea .buttons a {
        display: inline-block;
        padding: 0;
        width: 100px;
        height: 34px;
        line-height: 34px;
        border: 1px solid #4A90E2;
        color: #4A90E2;
        background: #fff;
        text-decoration: none; }
        .m-Hotarea .buttons a + a {
        margin-left: 30px; }
        .m-Hotarea .buttons a.error {
        border-color: #F05C5C;
        color: #F05C5C; }
        #mapAddDialoguepic>img{
            width:100px;
        height:100px;
        }
</style>