
<template>
 <div >
    <ul>
        <li v-bind:key="item.id" class="app-li-it" style="background-color:white;margin-top:15px;padding-bottom:10px;width:100%;display:block" v-for="item in this.blogList">
            <blogView :data="item"></blogView>

            <HR class="zw-hr" style="filter:alpha(opacity=100,finishopacity=0,style=2)"  color=#ece3e3 ></HR>
        </li>

    </ul>
    <div style="width:100%;text-align:center" class="center"><a @click="goNext()">展开更多评论>></a></div>
 </div>
</template>
<script type="text/javascript">
import zwIcon from '../icon/zwIcon.vue';
import blogView from '../../component/datadisplay/blogView2.vue';
export default {
         components:{zwIcon,blogView},
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

        }
        },


         events: {

          }
    };


</script>
<style>

</style>
