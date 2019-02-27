
<template>
 <div >
    <ul>
        <li v-bind:key="item.id" class="app-li-it" style="background-color:white;margin-top:15px;min-height:100px;width:100%;display:block" v-for="item in this.blogList">
            <Comment :data="item"></Comment>




            <HR class="zw-hr" style="filter:alpha(opacity=100,finishopacity=0,style=2)"  color=#ece3e3 ></HR>
        </li>
    </ul>
 </div>
</template>
<script type="text/javascript">
import zwIcon from '../icon/zwIcon.vue';
import blogView from '../../component/datadisplay/Comment.vue';
export default {
         components:{zwIcon,blogView},
        props:["data"],
        data () {
            return {
                //data:{},
                  blogList:[],
            };
        },
        computed: {

        },
        mounted () {
            this.getCommentList();
        },
        methods: {
        refresh:function(){
           this.getNews();
        },
        getCommentList:function( content){
            Ajax.get(PATH+"/pub/comment/list",{pid:getParam("id"),curPage:1,pageSiez:10},this.showData);
        },
        showData:function(result){

            this.blogList=result.data;
        }
        },


         events: {

          }
    };


</script>
<style>

</style>
