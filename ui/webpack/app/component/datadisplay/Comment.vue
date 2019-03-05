
<template>
<div>
      <div class="user">
               <a title="软媒通行证数字ID：1565223" href="https://m.ithome.com/user/1565223">
               <div class="user-hd">
                    <img :src="getPathValue(data.face)" width="45" height="45" onerror="this.src='//img.ithome.com/m/images/user/noavatar.png'">
               </div><span class="lv">Lv.1</span>
               </a>
               </div>

               <div class="review-con">
                   <div class="user-mes"><a title="软媒通行证数字ID：1565223" href="https://m.ithome.com/user/1565223"><span class="user-name">{{data.userName}}</span></a><span
                           class="mobile android"><a target="_blank" href="https://m.ithome.com/html/app/appdown.html">华为 p90</a></span><span class="user-floor">7楼</span></div>
                   <div class="user-write-msg"><span class="user-ip"></span><span class="user-write-time">{{data.createtime}}</span></div>
                   <div class="user-review">{{data.content}}</div>
                   <div class="review-footer">

                   <span class="collapse" data-collapsed="" role="button">评论({{this.comments.length}})</span>

                   <span class="review-ft-fr">
                   <span @click="up" class="stand-by">支持({{data.up}})</span><span @click="down(data.id)"  class="oppose">反对({{data.down}})</span>
                        <span @click="reply()" class="reply">回复</span></span></div>
                   <ul>
                       <li v-bind:key="item.id"  v-for="item in this.comments"  class="placeholder deputy-floor" data-comment-id="40422725">
                           <div class="user"><a title="软媒通行证数字ID：794314" href="https://m.ithome.com/user/794314">
                               <div class="user-hd"><img data-original="https://avatar.ithome.com/avatars/000/79/43/14_60.jpg"
                                                         width="45" height="45"
                                                         onerror="this.src='//img.ithome.com/m/images/user/noavatar.png'"
                                                         :src="item.face"
                                                         style="display: inline;"></div>
                               <span class="lv">Lv.40</span></a></div>
                           <div class="review-con">
                               <div class="user-mes"><a title="软媒通行证数字ID：794314" href="https://m.ithome.com/user/794314"><span
                                       class="user-name">{{item.userName}}</span></a><span class="mobile android"><a target="_blank"
                                                                                                       href="https://m.ithome.com/html/app/appdown.html">诺基亚
                                   X6</a></span><span class="user-floor">1#</span></div>
                               <div class="user-write-msg"><span class="user-ip">IT之家江苏苏州网友</span><span
                                       class="user-write-time">08:14</span></div>
                               <div class="user-review">{{item.content}}</div>
                               <div class="review-footer"><span class="review-ft-fr"><span @click="up(item.id)"   class="stand-by">支持({{item.up}})</span><span
                                      @click="down(item.id)"   class="oppose">反对({{item.down}})</span><span class="reply">回复</span></span></div>
                           </div>
                       </li>

                   </ul>
               </div>
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
         getCommentList:function(){
                                  Ajax.getJSON(PATH+"/msginfo/list.json?curpage=1&pagesize=10",{pid:this.data.id,curPage:1,pageSize:10},function(result){
                                      if(result.r==AJAX_SUCC){
                                          for(var i=0;i<result.data.length;i++){
                                              if(result.data[i].face){
                                               result.data[i].face=PATH+ result.data[i].face;
                                              }


                                          }
                                          this.comments=result.data;
                                      }
                                  }.Apply(this));
                            },
        reply:function(){
            document.getElementsByClassName("post-comment")[0].style.display="block";
            document.getElementById("pid").value=this.data.id;
            document.getElementById("commentType").value="pubCOmment";
        },
        down:function(id){
            Ajax.post(PATH+"/msginfo/down",{"pid":id,"type":2,"category":2},function(result){
                if(result.r==AJAX_SUCC){
                this.data=result.data
                }else{
                    alert(result.msg);
                }
            })
        },
        up:function(id){
                    Ajax.post(PATH+"/msginfo/up",{"pid":id,"type":1,"category":2},function(result){
                        if(result.r==AJAX_SUCC){
                        this.data=result.data
                        }else{
                            alert(result.msg);
                        }
                    })
                },
        getPathValue:function(value){
            return getPathValue(value);
        },
        isMyArtical:function(data){
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
