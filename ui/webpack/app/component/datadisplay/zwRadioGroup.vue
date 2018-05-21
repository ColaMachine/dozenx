
<template>
<div class="zw-radio-group">
<slot ></slot>

<zwRadioButton  v-on:changeSelect="changeSelect" v-for="item in options" :value="item.value"  :checked="checkedValue==item.value" >{{item.name}}
</zwRadioButton>
</div>


</template>
<script type="text/javascript">

import zwIcon from '../icon/zwIcon.vue';
import zwRadioButton from './zwRadioButton.vue';
export default {
        name: 'zwRadioGroup',

         components:{zwIcon,zwRadioButton},
        props: ["defaultValue","options"

        ],
        data () {
            return {
                checkedValue:null,
            };
        },
        computed: {

        },
        mounted () {

                //如果有默认选中的default value 属性
                if(this.defaultValue){
                    this.checkedValue=this.defaultValue;

                   // this.selectRadio(this.defaultValue);
                }




        },
        methods: {
        changeSelect:function(value){
            //alert(value);
           this.checkedValue=value;
           this.$emit("change",value);
          //  this.selectRadio(value);
        },

            /* selectRadio:function(selectValue){

                 var objs = this.$slots.default;
                    for(var i=0;i<objs.length;i++){
                           if(objs[i].data){
                                if(objs[i].data && objs[i].data.attrs.value==selectValue ){

                                    objs[i].$on("selectRadio",this.selectRadio);
                                    objs[i].data.attrs.checked=true;

                                }else{
                                     objs[i].data.attrs.checked=false;
                                }
                            }
                        }

            }*/
        },


         events: {

          }
    };
</script>
<style>
.zw-radio-group {
           font-family: "Monospace Number", "Chinese Quote", -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, "PingFang SC", "Hiragino Sans GB", "Microsoft YaHei", "Helvetica Neue", Helvetica, Arial, sans-serif;
           font-size: 14px;
           line-height: 1.5;
           color: rgba(0, 0, 0, 0.65);
           -webkit-box-sizing: border-box;
           box-sizing: border-box;
           margin: 0;
           padding: 0;
           list-style: none;
           display: inline-block;
           line-height: unset;
       }

       .zw-radio-button-wrapper:hover, .ant-radio-button-wrapper-focused {
           color: #1890ff;
           position: relative;
       }

       .zw-radio-button-wrapper-checked {
           background: #fff;
           border-color: #1890ff;
           color: #1890ff;
           -webkit-box-shadow: -1px 0 0 0 #1890ff;
           box-shadow: -1px 0 0 0 #1890ff;
           z-index: 1;
       }
</style>