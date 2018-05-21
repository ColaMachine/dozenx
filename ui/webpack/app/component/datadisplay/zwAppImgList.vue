<template>

<ul class="app-li">
 <li  v-for="result in list" key={{result.id}} class="app-li-it">
<div class="app-li-it-pic"><a :href='"/static/html/editor/index.html?id=3&goodsid="+ result.id ' ><img onerror="this.src='/static/phone/img/carousel/1.png'" :src='"/"+ result.pic'/></a></div>
<div class="app-li-it-content">
<div class="app-li-it-title"><a href='"/static/html/editor/index.html?id=3&goodsid="+ result.id '>
<h2>{{result.title}}</h2>
<p style="display:none">{{result.subtitle}}</p>

</a></div>
<div class="app-li-it-text"><span className="src-net">{{result.src}}|23:12</span><span className="comment-num">评论:2</span><span className="goods-score">评分:98%</span></div>
</div>
</li>;



</ul>

</template>
<script type="text/javascript">
import zwIcon from '../icon/zwIcon.vue';

export default {
        name: 'zwAppImgList',
         components:{zwIcon},
        props:
            ["list"]//"tabs"
        ,
        data () {
            return {

            };
        },
        computed: {

        },
        mounted () {
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
li{ list-style: none; }
.app-li-it{
display:flex;
padding:10px;

}
.app-li-it-pic{
width:30%;
/*display:table-cell;*/
}

.app-li-it-content{
width:80%;
position:relative;
/*margin-left:3%;*/
padding-left:3%;
/*display:table-cell;*/
vertical-align: middle;
}
.app-li-it-pic img{
width:100%;
/*max-height: 100px;
min-height: 100px;*/
}
.app-li-it-text{
width:80%;
font-size:12px;
position:absolute;
bottom:0px;
color:gray;
display:flex;
padding-right:50px;
overflow:hidden;
justify-content:space-between;
}
.src-net{
/*display:table-cell;*/
width:30%;
}
.comment-num{
/*display:table-cell;*/
width:30%;
}
.goods-score{
/*display:table-cell;*/
width:30%;
}
.app-li-it-title h2 {
    font-size:15px !important;
    line-height:18px;
}
.app-li-it-title h3 {
    font-size:13px !important;
}
</style>
