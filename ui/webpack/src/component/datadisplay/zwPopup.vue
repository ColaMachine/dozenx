
<template>
<div>

<button type="button" @click="showThis()" name="上传图片">上传图片</button>
<div  :class="getComputeCls"  style="z-index: 9999;" >
<div class="popup-content" style="position: relative; top: 122px; width: 700px;">

  <div class="z-popup login-popup">
    <div class="z-popup-head z-clearfix">
    <a href="javascript:;" @click="hideThis()"  class="z-popup-close z-icons icon-times-o J_popup_close"></a>



    </div>

    <div class="z-popup-content" >
<slot></slot>

    </div>
    </div>
</div>
</div>
</div>
</template>
<script type="text/javascript">

export default {
         components:{},
        props:[],
        data () {
            return {
                "showFlag":true
            };
        },
        computed: {

        getComputeCls(){
        var str= "popup-wrap" ;
        if(!this.showFlag){
        str+=" hide";
        }
            return str;
        }
        },
        mounted () {


        },
        methods: {
            showThis:function(){
                this.showFlag=true;
            },
            hideThis:function(){
                 this.showFlag=false;
            }
        },


         events: {

          }
    };




</script>
<style>

.popup-wrap {
    position: fixed;
    top: 0;
    right: 0;
    bottom: 0;
    left: 0;
    background-color: rgba(0,0,0,.5);
    overflow-x: hidden;
    overflow-y: auto;
    z-index: 9999;
}


.popup-wrap .popup-bg {
    position: absolute;
    left: 0;
    top: 0;
    right: 0;
    bottom: 0;
}


.popup-wrap .popup-content {
    margin: 0 auto;
}

.z-popup {
    width: 700px;
    padding-bottom: 40px;
    background-color: #fff;
}


.z-popup .z-popup-head {
    position: relative;
    height: 60px;
    padding: 0 20px;
    line-height: 60px;
}
.z-clearfix, .z-hor-feed {
    zoom: 1;
}

.z-clearfix:after, .z-clearfix:before, .z-hor-feed:after, .z-hor-feed:before {
    content: "";
    display: table;
}

.z-popup .z-popup-head .z-popup-close {
    position: absolute;
    top: -6px;
    right: -6px;
    width: 24px;
    height: 24px;
    background-color: #fff;
    border-radius: 50%;
    color: #999;
    font-size: 16px;
    text-align: center;
    line-height: 24px;
}

.z-popup .z-popup-content {
    width: 100%;
}

</style>
