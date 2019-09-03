
<template>

       <li >
                                    <div class="comment_avatar">
                                        <div class="user-box-new">
                                            <a class="user-avatar" :href="'https://user.dapark.com/user/'+data.userId" target="_blank">

                                                <img :src="data.face?getPathValue(data.face):'/home/static/img/noavatar.png' "  onerror="this.src='/home/static/img/noavatar.png'">
                                            </a>
                                        </div>
                                        <span class="grey">{{data.id}}楼</span>
                                    </div>
                                    <div class="comment_conBox">
                                        <div class="comment_avatar_time ">
                                            <div class="time"><meta itemprop="datePublished" content="2019-06-04">{{data.createtime}}</div>
                                                <a class="a_underline user_name" target="_blank"  :href="'https://user.dapark.com/user/'+data.createUser" :userId="data.createUser"><span itemprop="author">
                                                {{data.userName}}
                                                </span></a>
                                                <div class="icon-medal"></div>
                                                <div class="rank face-stuff-level" title="12级">
                                                <a href="https://zhiyou.smzdm.com/user/tequan/" target="_blank">
                                                <i class="icon-biaoqing-moon"></i><i class="icon-biaoqing-moon"></i>
                                                <i class="icon-biaoqing-moon"></i></a>
                                                </div>
                                             </div>






<div data-v-7c423e12="" class="blockquote_wrap">




    <blockquote   v-for="(item,index) in this.comments"   v-bind:key="index"   data-v-7c423e12="" blockquote_cid="132355893" class="comment_blockquote">
        <div data-v-7c423e12="" class="comment_floor">{{index}}</div>
        <div data-v-7c423e12="" class="comment_conWrap">
            <div data-v-7c423e12="" class="comment_con"><a data-v-7c423e12="" target="_blank"
                                                           href="https://zhiyou.smzdm.com/member/3217969762/"
                                                           usmzdmid="3217969762" class="a_underline user_name"><span
                    data-v-7c423e12="" itemprop="author">{{item.userName}}</span></a>
                ：<p data-v-7c423e12="" class="p_content_132355893"><span data-v-7c423e12="" itemprop="description">{{item.content}} </span>
                </p></div>
            <a data-v-7c423e12="" href="javascript:void(0);" class="seaAll" style="display: none;">查看全部</a>
            <div data-v-7c423e12="" class="comment_action"><a data-v-7c423e12="" href="javascript:void(0);"
                                                              id="report_132355893" class="jubao"
                                                              style="display: inline-block;">举报</a> <a
                    data-v-7c423e12="" href="javascript:void(0);" id="cdc_support_132355893"
                    onclick="return comment_rating_ajax(3, 1, 132355893, 14039324, 132373919);" class="dingNum current">顶<span
                    data-v-7c423e12="">({{item.up}})</span></a> <a data-v-7c423e12="" href="javascript:void(0);"
                                                         id="cdc_oppose_132355893"
                                                         onclick="return comment_rating_ajax(3, 0, 132355893, 14039324, 132373919);"
                                                         class="caiNum">踩<span data-v-7c423e12="">({{item.down}})</span></a> <a
                    data-v-7c423e12="" href="javascript:void(0);" title="@HungerCat" class="atta">@TA</a> <a
                    data-v-7c423e12="" href="javascript:void(0);" id="cdc_reply_132355893" @click="replyChildComment(item.id,item.userName)" class="reply">回复</a></div>
            <div data-v-7c423e12="" class="blankDiv"></div>



            <div class="blankDiv"><div  v-if="item.showCommentInput" class="quickReply" id="quickReply">
                            <form id="commentquickform" class="commentquickform" onsubmit="return false;" method="post" action="">
                                <input type="hidden" id="type" name="type" class="quickCommentType" value="3">
                                <input type="hidden" name="comment_type" class="quickCommentType" value="3">
                                <input type="hidden" id="pid" name="pid" value="14652209">
                                <input type="hidden" id="parentid" name="parentid" class="parentid" value="134876586">
                                <textarea rows="1" ref="pinglun" cols="70" id="quickComment" name="content" class="textarea_comment textarea_quick" style="height: 22px; overflow-y: hidden;"></textarea>
                                <div id="loadingquick" style="display: none;"><img style="vertical-align:middle;" src="//www.smzdm.com/resources/public/img/loading_comment.gif"> </div>
                                <p id="quick_error" class="hi-red"></p>
                                <div class="comment_operate">
                                    <div class="comment_smileBox">
                                        <i class="icon-small" id="smileImg"><!--[if lt IE 8]>表情<![endif]--></i>
                                        <div class="smileLayerBg" id="smiley" style="display:none;">
                                        </div>
                                    </div>
                                    <div class="comment_share">
                                        <button type="submit" class="btn_sub" id="textCommentSubmitQuick">提交</button>
                                                                </div>
                                </div>
                            </form>
                        </div></div>







        </div>
    </blockquote>

</div>















                                        <div class="comment_conWrap">
                                            <div class="comment_con">
                                                <p class="p_content_132977043"><span itemprop="description">
                                                 {{data.content}}</span></p>
                                            </div>
                                            <div class="comment_action">
                                                <span class="come_from">来自 <a target="_blank" href="/myapp/android">Android 客户端</a></span>
                                                <a class="jubao" href="javascript:void(0);" id="report_132977043" style="display:inline-block">举报</a>
                                                <a class="dingNum " href="javascript:void(0);" id="cdc_support_132977043" @click="up(data)" >顶<span>({{data.up}})</span></a>
                                                <a class="caiNum" href="javascript:void(0);" id="cdc_oppose_132977043" @click="down(data)" >踩<span>({{data.down}})</span></a>
                                                <a class="atta" @click="alta" href="javascript:void(0);" title="@心之所在">@TA</a>                                    <a class="reply" href="javascript:void(0);"  id="cdc_reply_132977043" @click="replyParentComment">回复</a>
                                            </div>

                                          <div class="blankDiv"><div v-if="data.showCommentInput" class="quickReply" id="quickReply">
                                                                     <form id="commentquickform" class="commentquickform" onsubmit="return false;" method="post" action="">
                                                                         <input type="hidden" id="type" name="type" class="quickCommentType" value="3">
                                                                         <input type="hidden" name="comment_type" class="quickCommentType" value="3">
                                                                         <input type="hidden" id="pid" name="pid" value="14652209">
                                                                         <input type="hidden" id="parentid" name="parentid" class="parentid" value="134876586">
                                                                         <textarea rows="1" ref="pinglun" cols="70" id="quickComment" name="content" class="textarea_comment textarea_quick" style="height: 22px; overflow-y: hidden;"></textarea>
                                                                         <div id="loadingquick" style="display: none;"><img style="vertical-align:middle;" src="//www.smzdm.com/resources/public/img/loading_comment.gif"> </div>
                                                                         <p id="quick_error" class="hi-red"></p>
                                                                         <div class="comment_operate">
                                                                             <div class="comment_smileBox">
                                                                                 <i class="icon-small" id="smileImg"><!--[if lt IE 8]>表情<![endif]--></i>
                                                                                 <div class="smileLayerBg" id="smiley" style="display:none;">
                                                                                 </div>
                                                                             </div>
                                                                             <div class="comment_share">
                                                                                 <button type="submit" @click="submitComment()" class="btn_sub" id="textCommentSubmitQuick">提交</button>
                                                                                                         </div>
                                                                         </div>
                                                                     </form>
                                                                 </div></div>
                                        </div>
                                                                </div>
                                                            <div class="clear"></div>
                                </li>
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

                 ]
            };
        },
        computed: {


        },
        mounted () {

        if(this.data.pic && this.data.pic.length>0){
         this.images=this.data.pic.split(",");
        };


this.getCommentList();

            console.log(this.data);
        },
        methods: {

        submitComment:function(parentCommentId,childCommentId,content){
       // alert(this.$refs.pinglun.value);
                    var pinglun =this.$refs.pinglun.value;
                   // var content = document.getElementById("pinglun").value;
                   // var pid = document.getElementById("pid").value;
                    //var type = document.getElementById("commentType").value;

                    Ajax.post(PATH+"/msginfo/add.json",{type:2,pid:this.data.id,content:pinglun,type:1,objId:this.data.objId},function(result){
                        //刷新页面
                        //this.refresh();
                        //清空值
                        this.$refs.pinglun.value="";

                        this.comments.push(result.data);
                       // document.getElementsByClassName("post-comment")[0].style.display="none";
                    }.Apply(this))

                },
         getCommentList:function(){
         console.log("getCommmentList");
                                  Ajax.getJSON(PATH+"/msginfo/list.json?type=2&curpage=1&pagesize=10",{pid:this.data.id,curPage:1,pageSize:10},function(result){
                                       console.log("/msginfo/list.json");
                                      if(result.r==AJAX_SUCC){
                                          //for(var i=0;i<result.data.length;i++){
                                              //if(result.data[i].face){
                                              // result.data[i].face=PATH+ result.data[i].face;
                                              //}


                                         // }
                                          this.comments=result.data;
                                      }
                                  }.Apply(this));
                            },
        reply:function(){
            //document.getElementsByClassName("post-comment")[0].style.display="block";
           // document.getElementById("pid").value=this.data.id;
            //document.getElementById("commentType").value="pubCOmment";

            this.$emit("readyToReply",this.data.id,this.data.path,this.data.objId);
        },

          replyParentComment:function(){//一级评论回复 只要把子评论的父亲id标记为他就行了 然后展现的时候就可以展现出来 通过2次查询
          //对比贴吧的规则是 1级评论为楼层数  2级评论显示在后面 没有3级数据了 如果2级评论想追加回复的话 必须通过@这个人来实现

          //而网易的做法是所有的每条回复都是一条数据 个人看起来感觉会显得比较乱

          //事实上微博和 百度都是 清晰的结构 而网易的数据看起来重复的比较多 比较乱 我不喜欢  但是网易突出了每个回复者的数据

          //这两种设计能有兼容的方式吗 实际上个从数据结构上看感觉是可以的 只要path上都记录出上级的路径就可以了

          //但是前段来说的话差异比较大 需要重新设计

          //为什么评论不能按照树的方式显示呢

          //三种盖楼设计 http://www.woshipm.com/pd/991160.html

          //
 console.log("回复1级评论"+this.data.id);

                    this.$emit("readyToReply",this.data.id,this.data.objId,false);
                },
        alta:function(){

        },

        replyChildComment:function(commentId,userName){
            this.$emit("readyToReply",this.data.id,this.data.objId,false);
            var t=setTimeout(function(){
                 this.$refs.pinglun.value="@"+userName+" :";
                            this.$refs.pinglun.focus();
            }.Apply(this),500);

        },
        down:function(item){
            Ajax.post(PATH+"/msginfo/down",{"pid":item.id,"type":2,"category":2},function(result){
                if(result.r==AJAX_SUCC){
                    item.up=result.data.up;
                    item.down=result.data.down;
                }else{
                    ztips(result.msg);
                }
            }.Apply(this))
        },
        up:function(item){
            Ajax.post(PATH+"/msginfo/up",{"pid":item.id,"type":1,"category":2},function(result){
                if(result.r==AJAX_SUCC){
                item.up=result.data.up;
                                   item.down=result.data.down;
                }else{
                    ztips(result.msg);
                }
            }.Apply(this))
        },
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

        showOrHideComment:function(){
            this.commentShow=!this.commentShow;
        }
        },


         events: {

          }
    };




</script>
<style>

.textarea_quick {
    width: 610px;
    height: auto;
    min-height: 22px;
    overflow: hidden;
    padding: 4px 10px;
    color: #666;
}
.textarea_comment {
    padding: 10px;
    height: 60px;
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
blockquote .textarea_quick {
    width: 568px;
    border: 1px solid #dcdcdc;
}

</style>
