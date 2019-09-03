
<template>
<div style="width:98%;">
<div class="app-card" >

    <div class="app-li-it-pic">
        <img  :src="getPathValue(data.face)" ></img>
    </div>
    <div class="app-li-it-content">
         <span style="color:#333">{{data.creatorname}}</span>
         <p style="color:gray;font-size:8px">{{data.createtime}}</p>

        <p style="margin-top:5px"> {{data.content}}</p>
          <zwDropDown  v-if="isMyArticle(data)" trigger="click" style="position:absolute;right:10px;top:10px;" placement="bottomRight">
          <zwButton slot="button"  type="default" icon="down" ></zwButton>
          <zwMenu slot="menu">
          <zwMenuItem key="1">删除</zwMenuItem>
          <zwMenuItem key="2">举报</zwMenuItem>
          <zwMenuItem key="3">屏蔽</zwMenuItem>
          </zwMenu>
          </zwDropDown>
            <ul style="background-color:white;" class="clearfix">
               <li style="width:100px;height:100px;float:left;position:relative"  v-for="item in images">

                    <img style="width:100px;height:100px" :src="getPathValue(item)"/>
                     <a style="position:absolute;width:10px;height:10px;font-size:10px;top:5px;right:5px" v-on:click="deletePic(item)" > <zwIcon type="close"></zwIcon></a>
               </li>

            </ul>








    </div>


</div>

<div class="WB_feed_handle" sytle="font:normal">
            <div class="WB_handle">
                <ul class="WB_row_line WB_row_r4 clearfix S_line2">
                    <li>
                        <a class="S_txt2">
                            <span class="pos">
                                <span class="line S_line1" >
                                    <span>
                                       <!-- <em class="W_ficon ficon_favorite S_ficon"></em>-->
                                        <em>收藏</em>
                                    </span>
                                </span>
                            </span>
                        </a>
                    </li>
                    <li>
                        <a class="S_txt2" href="javascript:void(0);" >
                            <span class="pos">
                                <span class="line S_line1" node-type="forward_btn_text">
                                    <span>
                                       <!-- <em></em>-->
                                        <em>转0</em>
                                    </span>
                                </span>
                            </span>
                        </a>
                    </li>
                    <li>
                        <a @click="showOrHideComment" class="S_txt2" >
                            <span class="pos">
                                <span class="line S_line1" >
                                    <span>
                                       <!--  <em class="W_ficon ficon_repeat S_ficon"></em>-->
                                        <em>评论({{data.commentCount}})</em>
                                    </span>
                                </span>
                            </span>
                        </a>

                    </li>
                    <li class="">
                        <!--cuslike用于前端判断是否显示个性赞，1:显示 -->
                        <a href="javascript:void(0);" class="S_txt2"  title="赞">
                            <span class="pos">
                                <span class="line S_line1">
                                    <span node-type="like_status" class="">
                                        <em >赞12312</em>
                                        <em>0</em>
                                    </span>
                                </span>
                            </span>
                        </a>



                    </li>
                </ul>
            </div>
        </div>

         <h3 >
                    <zwAppComment v-if="commentShow" style="" :pid="data.id" >
                    </zwAppComment>
        </h3>
</div>
</template>
<script type="text/javascript">
 import zwPubComment from '../../component/datadisplay/zwPubComment.vue';
 import zwAppComment from '../../component/datadisplay/zwAppComment.vue';
  import zwMenu from '../../component/navigation/menu/zwMenu.vue';
   import zwMenuItem from '../../component/navigation/menu/zwMenuItem.vue';

   import zwDropDown from '../../component/navigation/menu/zwDropDown.vue';

  import zwIcon from '../../component/icon/zwIcon.vue';

      import zwButton from '../../component/button/zwButton.vue';
export default {
         components:{zwPubComment,zwButton,zwMenuItem,zwMenu,zwDropDown,zwIcon,zwAppComment},
        props:["data"],
        data () {
            return {
                 commentShow:false,
                 images:[],
                 comments:[
                    { id:1,
                     content:"123",
                     userName:"123123123",
                     face:'https://tvax1.sinaimg.cn/crop.0.0.200.200.180/a8d43f7ely1fnxs86j4maj205k05k74f.jpg',
                     createTime:'2018年 10月 12日'
                     },
                 ]
            };
        },
        computed: {


        },
        mounted () {
        if(this.data.pic && this.data.pic.length>0){
         this.images=this.data.pic.split(",");
        }


            console.log(this.data);
        },
        methods: {
        getPathValue:function(value){
            return getPathValue(value);
        },
        isMyArticle:function(data){
            console.log(data.creator);
            console.log(getLoginUser());
            if(data.creator = getLoginUser()){
                return true;
            }
            return false;
        },
              getPathValue:function(value){
                       return getPathValue(value);
                     },
 showOrHideComment:function(){
            this.commentShow=!this.commentShow;
        }
        },


         events: {

          }
    };


</script>
<style>
.zw-comment-bottom-bar.zw-menu-horizontal > li {
    width:25%;
    text-align:center;
}
.app-card .app-li-it-pic{
    min-width:50px;
     flex-basis: 50px;
}

</style>
