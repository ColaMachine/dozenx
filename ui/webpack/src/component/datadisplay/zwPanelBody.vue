
<template>
  <button type="button"   :class="classes" :disabled="isDisabled"><i v-if="iconShow" :class="iconclasses"></i><span v-if="hasText"   class="zw-btn-text" ref="slot"> <slot ref="btnTxt"></slot></span></button>
</template>
<script type="text/javascript">
export default {
        name: 'zwCollapse',
        components: {  },
        props:{
               disabled:Boolean,
               text:String,
               type:String,
               title: String,
               icon:String,
               shape:String,
               sizeNum:String,
               diabled:Boolean,
               loading:{//表示他是一个loading 组件  正真表示他是否在loading 是loading_state loading_state中 按钮不可点击 并且有图标的话 图标会变成旋转
                    type:Boolean,
                    default:false,
               },
               loading_delay:{
                   type:Number,
                   default:0,
               }

       },
        data () {
            return {
                hasText:true,
                loading_state:false,
            };
        },
        computed: {

            size:function(){

            },
            iconShow:function(){
                if(this.icon || this.loading_state){
                    return true;
                }
                return false;
            },
            iconclasses:function(){
                var classStr="zw-btn-icon ";
                if(this.icon=="search"){
                    classStr=" fa fa-search";
                };
                if(this.icon=="down"){
                    classStr=" fa fa-chevron-down";
                };
                if(this.loading_state==true ){
                    classStr="fa fa-spinner";
                }
                return classStr;
            },
            isDisabled:function(){
                if(this.loading_state==true){
                     return true;
                }
                if(this.disabled==true){
                    return true;
                }

                return false;
            },
            classes:function(){
                var classStr="";
                if(this.type=='primary'){
                    classStr= "btn btn-primary";
                }else if(this.type=='dashed'){
                    classStr= "btn btn-dashed";
                }else if(this.type=='danger'){
                     classStr= "btn btn-danger";
                 }else{
                    classStr= "btn btn-default";
                 }

                if(this.shape=="circle"){
                    classStr+= " circle";
                }
                if(this.disabled){this.disabled=true;
                      classStr+= " disabled";
                }

                 if( this.loading_state==true){
                    classStr+=" btn-loading";
                }

                if(this.sizeNum=="large"){
                    classStr+= " zw-btn-large";
                }else if(this.sizeNum=="small"){
                    classStr+= " zw-btn-small";
                }
                 return classStr;
            }
        },
        mounted () {
        if(!StringUtil.isBlank(this.$refs.slot.innerHTML)){


            this.hasText=true;

        }else{
            this.hasText=false;
        }
        this.loading_state = this.loading;
        if(this.loading==true && this.loading_delay>0){
            this.loading_state =true;
            var that = this;

             setTimeout(function(){
               that.loading_state=false

           },this.loading_delay*1000);
           }
        },
        watch:{
　　　　　　　　loading(curVal,oldVal){
　　　　　　　　　　this.loading_state=curVal;
                    var that = this;
                    if(curVal==true && this.loading_delay>0){
                        setTimeout(function(){
                            that.loading_state=false

                        },this.loading_delay*1000);
                    }
　　　　　　　　},
　　　　　　　　
　　　　　　},
        methods: {

        },
    };
</script>
