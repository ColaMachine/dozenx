<template>

<div class="zw-jumbotron-wrap clearfix" :style="getComputeStyle">

<h1 class="zw-jumbotron-title pull-left" >

{{title}}</h1>

 <img style="width:100%;height:" :src="img" :style="getImgStyle" />
<span class="zw-jumbotron-btn pull-right"  >

     <zwDropDown trigger="click" placement="bottomRight">
         <zwButton shape="circle" slot="button"   icon="plus" ></zwButton>
         <zwMenu color="white" slot="menu">
             <zwMenuItem v-on:clickFn="clickFn" key="1">扫描添加网关</zwMenuItem>
         </zwMenu>
     </zwDropDown>

</span>
</div>







</template>
<script type="text/javascript">
/**
设计思路 需要获取整个的屏幕的高度  然后定一个头部大图片的最高高度 现在设计为1/3 然后 按钮的高度 为上边缘1/3的巨鹿
**/
import zwDropDown from '../navigation/menu/zwDropDown.vue';
import zwButton from '../button/zwButton.vue';
import zwMenu from '../navigation/menu/zwMenu.vue';
import zwMenuItem from '../navigation/menu/zwMenuItem.vue';
export default {
        name: 'zwSlidPanel',
         components:{zwButton,zwDropDown,zwMenuItem,zwMenu},
        props:
            ["title","img"]
        ,
        data () {
            return {


            };
        },
        computed: {
 getComputeStyle:function(){
                return "position:relative;max-height:"+parseInt( getWinHeight())/3+"px";//background-image:url("+this.img+"); background-color: #000000;
            },

             getImgStyle:function(){
                            return "max-height:"+parseInt( getWinHeight())/3+"px";
                        },
        },
        mounted () {
            this.maxHeight =parseInt( getWinHeight())/3;


        },
        methods: {

            clickFn:function(){
                this.$emit("clickFn");
            }
        },


        events: {

        }
};



</script>
<style>
.zw-jumbotron-wrap{
min-height:150px;
line-height:150px;
/*background-color:#dfdf1d;*/


background:url(/weixin/static/img/smarthome/背景板.png);
background-size:100% 180px;
background-repeat:no-repeat;
}
.zw-jumbotron-title{
padding-left:15px;
    position:absolute;
    color:white;
    z-index:1000;
}
.zw-jumbotron-btn{
position:absolute;
top:30%;
height:auto;
right:10px;
padding-right:15px;

color:black;
font-size:24px;
}
</style>
