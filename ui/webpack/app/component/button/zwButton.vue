
<template>
  <button type="button"  v-on:click="diabledForAWhile" class="btn" :class="classes" :disabled="isDisabled"><i v-if="iconShow" :class="iconclasses"></i>
  <span v-if="hasText"   class="zw-btn-text" ref="slot"> <slot ref="btnTxt"></slot></span>{{this.coolDown>0?(this.coolDown+'s'):"" }}</button>
</template>
<script type="text/javascript">
export default {
        name: 'zwButton',
        components: {  },
        props:{
               disabled:Boolean,
               text:String,
               type:String,
               title: String,
               icon:String,
               click:Function,
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
                intervalHandler:0,
                hasText:true,
                coolDown:0,//冷却时间
                loading_state:false,//当前等待的状态
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
                 if(this.icon=="plus"){
                    classStr=" fa fa-plus";
                };
                  if(this.icon=="refresh"){
                                    classStr=" fa fa-refresh";
                                };
                if(this.loading_state==true ){
                    classStr="fa fa-spinner";
                     classStr="";
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
                    classStr= " btn-primary";
                }else if(this.type=='dashed'){
                    classStr= " btn-dashed";
                }else if(this.type=='danger'){
                     classStr= " btn-danger";
                 }else if(this.type=='blue'){
                    classStr= " btn-border-blue";
                }else if(this.type=='red'){
                     classStr= " btn-border-red";
                 }else if(this.type=='yellow'){
                   classStr= " btn-border-yellow btn-bg-yellow ";
               }else{
                    classStr= " btn-default";
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
        this.judgeNeedWait();
       /* if(this.loading==true && this.loading_delay>0){
            this.loading_state =true;
            var that = this;

             setTimeout(function(){
               that.loading_state=false

           },this.loading_delay*1000);
           }*/
        },
        watch:{
　　　　　　　　loading(curVal,oldVal){
//利用loading_state做disable 和 icon 和样式的控制 当loading_state为true的时候 显示为等待的样子 loading_state会追踪传入的loading 状态
                        //所以当loading_state 改成false的时候 loading 还是true  当再次按下的时候 loading 从true 到true 没有发生变化 所以第二次按下去就不会变化

　　　　　　　　　　this.loading_state=curVal;
                    var that = this;
                    this.judgeNeedWait();
                   /* if(curVal==true && this.loading_delay>0){
                     this.coolDown=this.loading_delay;
                    setInterval(this.coolDownFn,1000);
                        setTimeout(function(){
                            that.loading_state=false

                        },this.loading_delay*1000);
                    }*/
　　　　　　　　},
　　　　　　　　
　　　　　　},
        methods: {
        coolDownFn:function(){
                            this.coolDown--;
                            console.log("--");
                            if(this.coolDown<=0){

                                this.loading_state=false;
                                window.clearInterval(this.intervalHandler);
                            }
                            },
            judgeNeedWait:function(){
                 var that = this;
                if(this.loading_state && this.loading_delay>0){

                this.loading_state=true;

                  this.coolDown=this.loading_delay;
                this.intervalHandler=setInterval(this.coolDownFn,1000);
                setTimeout(function(){
                    that.loading_state=false

                },this.loading_delay*1000);
                }
            },
            diabledForAWhile:function(){
            this.$emit("clickFn");
            if(this.loading && !this.loading_state){
                   this.loading_state=true;
            }

           this.judgeNeedWait();
            }
        },
    };
</script>
