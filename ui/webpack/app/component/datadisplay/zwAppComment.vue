<template>

<ul style="padding-left:30px;padding-bottom:20px;" class="app-ul app-comment bg-sm-gray" >
    <li  class="app-li-it">

        <input type ="text"  style="height:30px;width:90%" ref="pinglun"></input>
        <zwButton style="float:right;"   sizeNum="small" @clickFn="submitComment" type="primary"> 评论</zwButton>
    </li>
    <li   style="" v-for="result in list" key={{result.id}} class="app-li-it">

       <!-- <div style="width:30px" class="app-li-it-pic">
            <img style="width:30px"  :src='result.face'/>
        </div>-->
        <div class="app-li-it-content">
            <div class="app-li-it-title">
                <a >
                    <span>{{result.userName}}:</span><span>{{result.content}}</span>
                </a>
            </div>
            <!--<div class="app-li-it-text" style="font-size:10px"><span className="src-net">{{result.createtime}}</span><span className="comment-num">回复:</span><span className="goods-score">点赞</span></div>
            -->
        </div>

    </li>
</ul>

</template>
<script type="text/javascript">
import zwIcon from '../icon/zwIcon.vue';
import zwInput from '../../component/dataentry/zwInput.vue';
import zwButton from '../../component/button/zwButton.vue';
//this.src='/static/phone/img/carousel/1.png'
export default {
        name: 'zwPubComment',
         components:{zwIcon,zwButton,zwInput},
        props:
            ["pid"]//"tabs"
        ,
        data () {
            return {
                list:[]
            };
        },
        computed: {

        },
        mounted () {
            this.getCommentList();


        },
        methods: {
            getCommentList:function(){
                  Ajax.getJSON(PATH+"/pub/comment/list.json?curpage=1&pagesize=10",{pid:this.pid,curPage:1,pageSize:10},function(result){
                      if(result.r==AJAX_SUCC){
                          for(var i=0;i<result.data.length;i++){
                              if(result.data[i].face){
                               result.data[i].face=PATH+ result.data[i].face;
                              }


                          }
                          this.list=result.data;
                      }
                  }.Apply(this));
            },
            submitComment:function(){
                var pinglun =this.$refs.pinglun.value;
                Ajax.post(PATH+"/pub/comment/add.json",{pid:this.pid,content:pinglun,type:1},function(result){
                    //刷新页面
                    this.getCommentList();
                    //清空值
                    this.$refs.pinglun.value="";
                }.Apply(this))
            }

        },


        events: {

        }
};



</script>
<style>
.app-li{
font-size:8px;

}
.app-li-it-text{



}

 .app-comment .app-card{
background-color:transparent;
}
</style>
