
<template>
 <div class="comment">
    
    <div @click="showCommentDialog" style="width:98%;text-align:center"><zwButton type="primary" style="width:80%">发表评论</zwButton></div>
    <ul class="hot-comment">
        <li v-bind:key="item.id" class="placeholder main-floor"  v-for="item in this.blogList">
            <Comment :data="item"></Comment>

        </li>

    </ul>

<div style="display:none" id="reply-mask"class="post-comment sdk-mask-close" data-parent-id="" data-user-id="001706319">
    <div class="post-comment-mask sdk-mask"  @click="hideMask( $event)" ></div>
    <div class="post-comment-fixed" role="heading" aria-level="1" aria-label="写评论">
        <div class="post-comment-box">
            <div class="write-comment">
            <input type="hidden" id="pid"></input>
            <input type="hidden" id="commentType"></input>
            <textarea id="pinglun" ref="pinglun"
                    placeholder="平台有您参与更精彩！（政治、色情、喷骂、引战、机型喷、水军、广告等违法违规行为将被封号）"></textarea></div>
            <div class="post-comment-footer"><span class="comment-user-info">dozenx</span>
                <div class="post-comment-btn"><span class="pcb-cannel" role="button">取消</span><span @click="submitComment" class="pcb-post"
                                                                                                    role="button">发送</span>
                </div>
            </div>
        </div>
    </div>
</div>

    <div style="width:100%;text-align:center" class="center"><a @click="goNext()">展开更多评论>></a></div>
 </div>
</template>
<script type="text/javascript">
import zwIcon from '../icon/zwIcon.vue';
import zwBase from '../../component/zwBase.vue';
import zwButton from '../../component/button/zwButton.vue';
import Comment from '../../component/datadisplay/Comment.vue';
export default {
         components:{zwIcon,Comment,zwButton},
        props:["data","pid"],// blogviewlist 列表 blogview 每个item  bloginput输入框 引入 zwRichInput
        data () {
            return {
                //data:{},
                  blogList:[],
                  curPage:1
            };
        },
        computed: {

        },
        mounted () {
            this.getNews();
        },
        methods: {
        showCommentDialog:function(){
            document.getElementsByClassName("post-comment")[0].style.display="block";
            document.getElementById("pid").value=this.pid;
             document.getElementById("commentType").value="msginfo";
        },

        submitComment:function(){
            var pinglun =this.$refs.pinglun.value;
            var content = document.getElementById("pinglun").value;
            var pid = document.getElementById("pid").value;
            var type = document.getElementById("commentType").value;
            if(type=="msginfo"){
                Ajax.post(PATH+"/goods/comment",{pid:pid,content:pinglun,type:1},function(result){
                                //刷新页面
                                //this.refresh();
                                //清空值
                                this.$refs.pinglun.value="";
                                this.blogList =[result.data].concat(this.blogList);
                                  document.getElementsByClassName("post-comment")[0].style.display="none";
                            }.Apply(this))
            }else{
            Ajax.post(PATH+"/msginfo/add.json",{pid:pid,content:pinglun,type:1},function(result){
                //刷新页面
                //this.refresh();
                //清空值
                this.$refs.pinglun.value="";
                document.getElementsByClassName("post-comment")[0].style.display="none";
            }.Apply(this))
            }
        },
        goNext:function(){
            this.curPage++;this.getNews();
        },
        refresh:function(){
           this.getNews();
        },
        getNews:function( content){
            var jso = {pid:this.pid,curPage:this.curPage,pageSize:10};
            if(!this.pid){
                jso.pid=0;
            }
            Ajax.get(PATH+"/msginfo/list.json",jso,this.showData);
        },
        showData:function(result){
            for(var i=0;i<result.data.length;i++){
            this.blogList.push(result.data[i]);
            }
            //this.blogList=result.data;

        },
        hideMask:function(event){//点击黑色部分就关闭评论窗口
            console.log(event.currentTarget)
            console.log("hideMask");
            document.getElementById("reply-mask").style.display="none";
        }
        },


         events: {

          }
    };


</script>
<style>

html {

}
.comment {
    background: #fff;
}
.comment ul {
    padding: 0 1.5rem;
}
.comment .placeholder {
    position: relative;
    padding: .7rem 0;
    margin: 0;
}

.comment .placeholder .user {
    position: absolute;
    left: 0;
    width: 3.5rem;
    text-align: center;
}

.comment .placeholder .review-con {
    width: calc(100% - 4.5rem);
    width: -moz-calc(100% - 4.5rem);
    margin-left: 3.7rem;
    padding: .3rem;
    color: #333;
    border-bottom: solid .03rem #eee;
}


.comment .placeholder .review-con .user-mes {
    line-height: 2rem;
    font-size: 1.4rem;
}
.comment .placeholder .review-con .user-write-msg {
    color: #999;
    font-size: 1rem;
    margin: .3rem 0 1.2rem 0;
    vertical-align: middle;
}

.comment .placeholder .review-con .user-write-msg .user-ip {
    line-height: 1rem;
    display: inline;
    vertical-align: middle;
}
.comment .placeholder .review-con .user-write-msg .user-write-time {
    display: inline;
    vertical-align: middle;
    line-height: 1rem;
}

.comment .placeholder .review-footer {
    font-size: 1.1rem;
    color: #666;
    margin: 1.2rem 0;
    height: 1.5rem;
    word-wrap: break-word;
}

.comment .placeholder .review-footer .collapse {
    cursor: pointer;
    display: inline-block;
}

.comment .placeholder .review-footer span {
    -webkit-tap-highlight-color: rgba(0,0,0,0);
}

.comment .placeholder .review-footer span {
    -webkit-tap-highlight-color: rgba(0,0,0,0);
}
.review-footer .review-ft-fr {
    float: right;
}
.comment .placeholder .review-footer {
    font-size: 1.1rem;
    color: #666;
    margin: 1.2rem 0;
    height: 1.5rem;
    word-wrap: break-word;
}
.comment .placeholder .review-con .user-review {
    font-size: 1.5rem;
    line-height: 2rem;
    letter-spacing: .03rem;
    word-wrap: break-word;
    word-break: break-all;
    cursor: pointer;
    white-space: pre-wrap;
}

.comment .placeholder .user .lv {
    padding: .3rem;
    color: #666;
    background-color: #f4f4f4;
    text-align: center;
    font-size: 1rem;
}

.comment .placeholder .review-footer span {
    -webkit-tap-highlight-color: rgba(0,0,0,0);
}
.review-footer .review-ft-fr .stand-by {
    color: #7ba62c;
    display: inline-block;
}
.review-footer .review-ft-fr span {
    margin-left: 1.6rem;
    cursor: pointer;
}

.comment .placeholder .review-con ul {
    background-color: #f9f9f9;
    padding: 0 1rem 0 1rem;
}
.comment .placeholder.main-floor ul {
    margin: 0 0 .37rem 0;
}

.comment .placeholder .review-con .user-mes .user-floor {
    color: #999;
    position: relative;
    float: right;
    font-size: 1rem;
}









.post-comment .post-comment-mask {
    width: 100%;
    height: 100%;
    position: fixed;
    top: 0;
    left: 0;
    right: 0;
    margin: 0 auto;
    max-width: 640px;
    z-index: 100;
    background-color: rgba(0,0,0,.8);
    cursor: pointer;
}

.post-comment .post-comment-fixed {
    position: fixed;
    left: 0;
    right: 0;
    bottom: 0;
    margin: 0 auto;
    width: 100%;
    max-width: 640px;
    width: 100%;
    background: #fff;
    z-index: 300;
    border-radius: .5rem .5rem 0 0;
    box-shadow: 0 0 .3rem 0 rgba(6,0,1,.1);
    -moz-box-shadow: 0 0 .3rem 0 rgba(6,0,1,.1);
    -webkit-box-shadow: 0 0 .3rem 0 rgba(6,0,1,.1);
}


.post-comment .write-comment {
    padding: 1rem;
    font-size: 1.2rem;
}

.post-comment .post-comment-footer {
    height: 4.35rem;
    font-size: 1.5rem;
    margin: 0 1rem;
    position: relative;
}

.post-comment textarea {
    width: 100%;
    height: 7rem;
    border: 0;
}


textarea {
    white-space: pre-wrap;
}


comment-footer {
    height: 4.35rem;
    font-size: 1.5rem;
    margin: 0 1rem;
    position: relative;
}

.post-comment .post-comment-footer .comment-user-info {
    line-height: 3.3rem;
}

.post-comment .post-comment-footer .post-comment-btn {
    position: absolute;
    top: 0;
    right: 0;
}

.post-comment .post-comment-footer .post-comment-btn .pcb-cannel {
    width: 3.7rem;
    height: 3.3rem;
    line-height: 3.3rem;
    font-size: 1.5rem;
    display: inline-block;
    color: #333;
    text-align: center;
    cursor: pointer;
}



.post-comment .post-comment-footer .post-comment-btn .pcb-post {
    width: 7.3rem;
    height: 3.3rem;
    display: inline-block;
    text-align: center;
    line-height: 3.3rem;
    background-color: #d22222;
    border-radius: 1.65rem;
    color: #fff;
    cursor: pointer;
}
</style>
