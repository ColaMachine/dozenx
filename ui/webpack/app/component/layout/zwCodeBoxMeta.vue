
<template>
  <div class="zw-code-box-meta">
     <div class="zw-code-box-meta-title">{{title}}</div>
     <div><slot></slot></div>
     <div :class="collpase" v-on:click="showPanel"></div>
  </div>
</template>
<script type="text/javascript">
export default {
        name: 'zwCodeBoxMeta',
        components: {  },
        props: {
            disabled:Boolean,
            text:String,
            type:String,
            title: String,//标题
            className:String,

        },
        data () {
            return {
                codePanelShow:false
            };
        },
        computed: {
            collpase:function(){
                if(this.codePanelShow){
                    return "fa fa-chevron-circle-up collpase down";
                }else{
                     return "fa fa-chevron-circle-up collpase";
                }
            }
        },

        mounted () {

        /*    if(!this.pdata){
                this.pdata={};
                this.pdata.panelShow=true;
            } */
        },
        methods: {
             showPanel:function(){
                //this.$emit('update:codePanelShow', false)
                this.codePanelShow = !this.codePanelShow;
                 this.$emit('toggleCodePanel');
            }
        },
    };
</script>
<style>

.zw-code-box-meta{
    position:relative;
    font-size:12px;
    padding: 17px 16px 15px 20px;
    color: rgba(0,0,0,.65);
}
.zw-code-box-meta-title {
    background-color:white;     /**让标题背景把横线隔断开来**/
    position:absolute;
    top: -14px;
    font-size:16px;
    margin-left:14px;
    padding:4px 8px;
    color: rgba(0,0,0,.65);
}
.zw-code-box-meta .collpase{
    position:absolute;
    right:15px;
    font-size:20px;
    bottom:15px;
    color: #999;
    transition: all .3s;
}
.zw-code-box-meta  .down{
    color:#0095ff;
   transform: rotate(180deg);
}
</style>