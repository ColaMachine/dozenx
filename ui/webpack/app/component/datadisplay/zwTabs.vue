<template>
<div class="zw-tabs zw-tabs-top zw-tabs-line " :class="getMainClass" >
    <div class="zw-tabs-bar">
        <div class="zw-tabs-nav-container">
             <div class="zw-tabs-nav-wrap" >
                <div class="zw-tabs-nav-scroll">
                    <div class="zw-tabs-nav ant-tabs-nav-animated" :class="getAppBottomClass()">
                        <div v-if="tabUnderLineMarginLeft()" class="zw-tabs-ink-bar zw-tabs-ink-bar-animated" :style="tabUnderLineMarginLeft()"></div>
                        <div  v-on:click="changeTab(tabData.tabIndex)"  v-for="(tabData,index) in tabDatas" role="tab" aria-disabled="false" aria-selected="false" class=" zw-tabs-tab" :class="index==selectedTabIndex?'zw-tabs-tab-active':'zw-tabs-tab-inactive' ">
                        <img vi-if="tabData.img" :src="selectedTabIndex==index?tabData.img2:tabData.img"></img>
                        <zwIcon vi-if="tabData.icon" :type="tabData.icon"></zwIcon>
                        <span> {{ tabData.name }}</span>
                        </div>


                    </div>
                </div>
             </div>
        </div>
    </div>
    <div v-if="!panel" class="zw-tabs-content zw-tabs-content-animated" :style=" marginLeft()">
        <slot></slot>
    </div>

 </div>

</template>
<script type="text/javascript">
import zwIcon from '../icon/zwIcon.vue';

export default {
        name: 'zwTabs',
         components:{zwIcon},
        props:
            ["type","theme","panel","defaultIndex"]//"tabs"
        ,
        data () {
            return {
                //ewaiClass:'',
                selectedTabIndex:0,
                tabDatas:[],
                collapse:true,
            };
        },
        computed: {
            getMainClass:function(){

            var classes ="";
            if(this.type=="card"){
                classes+='zw-tabs-card';
            }
            if(this.theme=="purple_yellow"){
                   classes+=' theme-purple-yellow';
            }
            if(this.panel=="no"){
                 classes+=' zw-tabs-has-no-panel';
            }
            return  classes;
            },
            ewaiClass:function(){
                var str = 'zw-tabs zw-tabs-top zw-tabs-line ';

                if(this.type=='card'){
                    str+=  'zw-tabs-card';
                }
                   return  str;
            }
        },
        mounted () {
        if(this.defaultIndex){
            this.selectedTabIndex=this.defaultIndex;
        }

          /*   if(this.type=='card'){
                                ewaiClass=  'zw-tabs-card';
                            }*/

           //console.log(this.$slots.default);
            var objs = this.$slots.default;
            for(var i=0;i<objs.length;i++){
                if(objs[i].data){

                console.log("icon:"+objs[i].data.attrs["icon"]);

                this.tabDatas.push(
                    {
                        name:objs[i].data.attrs["tab"],

                        tabIndex:objs[i].data.attrs["tabIndex"],
                        icon:objs[i].data.attrs["icon"],
                        img:objs[i].data.attrs["img"],
                         img2:objs[i].data.attrs["img2"],
                    }
                 );
                }
            }
        },
        methods: {
         tabUnderLineMarginLeft:function(){
         if(this.panel=="no"){
            return 'display:none';
         }
                    if(this.type=='card'){
                        return '';
                    }
                        return "display: block; width: 73px;transform: translate3d("+this.selectedTabIndex*96+"px, 0px, 0px);";
        },
            getAppBottomClass:function(){
                if(this.type=='appbottom'){

                    return "zw-tabs-nav-flex";
                }
                return "";
            },

            marginLeft:function(){
                return "margin-left: -"+this.selectedTabIndex*100+"%;";
            },

            changeTab:function(tabIndex){

                this.selectedTabIndex=tabIndex-1;
                this.$emit("changeTab",this.selectedTabIndex);
            }

        },

 render:function(createElement){

            }  ,
         events: {

          }
    };



</script>
<style>

</style>
