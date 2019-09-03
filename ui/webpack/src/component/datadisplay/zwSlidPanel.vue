<template>

<div v-if="deleteState!=3" class="zw-app-slid-it" ref="slidebar" >

<div v-if="imgSrc!=null"  class="zw-app-slid-it-pic v-center" >
    <slot  v-if="imgSrc!=null" name="img"><a  v-link="link"><img doonerror="this.src='static/phone/img/carousel/1.png'" :src='imgSrc'/></a></slot>
</div>
<div  class="zw-app-slid-it-content  ui-flex  v-center">
<a :href="link"><div  class="zw-app-slid-it-title">

    <h1>{{title}}</h1>
    <h2>{{subTitle}}</h2>
    <p>{{thrdTitle}}</p>
</div></a>
</div>
<div  class="zw-app-slid-it-switch  ui-flex center ">
<slot  name="switch">

<zwIcon type="toggle-off"></zwIcon>
</slot>


</div>

<div v-on:click="clickDelBtn" :style='"width:"+this.deleteState*60+"px"' class="zw-app-slid-it-btn ui-flex center">
    {{btnName}}
</div>





</div>

</template>
<script type="text/javascript">
import zwIcon from '../icon/zwIcon.vue';

export default {
        name: 'zwSlidPanel',
         components:{zwIcon},
        props:
        {
        id: {  // 必须提供字段
            required: true
          },
          imgSrc: {   // 可选字段，有默认值

          },
         title:{},
         subTitle:{},
         thrdTitle:{},
         link:{},
         canDel:{default:false},
        }
           // ["id","imgSrc","delFn","title","subTitle","thrdTitle","link","canDel"]//"tabs"
        ,
        data () {
            return {
                deleteState:0,
               // btnName:"",
                oPosition:{},
                startX:0,
                startY:0,
                moveStart:false,


            };
        },
        computed: {
btnName:function(){
     if(this.deleteState===1){
                       return "删除";
                   }
                   if(this.deleteState===0){
                       return "";
                   }
                   if(this.deleteState===2){
                       return "确认删除";
                   }
                    if(this.deleteState===3){
                                          return "已删除";
                                      }
}
        },
        mounted () {
                if(this.canDel){
                  //this.$refs.slidebar.style.border="1px solid blue";
                                 this.$refs.slidebar.addEventListener('touchstart', this.touchStartFunc, false);
                                  this.$refs.slidebar.addEventListener('mousedown', this.touchStartFunc, false);
                                this.$refs.slidebar.addEventListener('touchmove', this.touchMoveFunc, false);
                                    this.$refs.slidebar.addEventListener('mousemove', this.touchMoveFunc, false);
                                this.$refs.slidebar.addEventListener('touchend', this.touchEndFunc, false);
                                 this.$refs.slidebar.addEventListener('mouseup', this.touchEndFunc, false);
                }

        },
        methods: {
            goLink:function(){
                if(this.link){
                    window.location=this.link;
                }
            },
            clickDelBtn:function(){//alert(this.id);
              if(this.deleteState===2){
                               // this.btnName="确认删除";
                this.$emit('deleteCallBack',this.id);//注意这里的方式名称只能是小写的

            }
            this.deleteState += 1;
             //if(this.deleteState >2){
               // this.deleteState = 0;
            //}
                console.log(this.deleteState);
            if(this.deleteState===1){
              //  this.btnName="删除";
            }
            if(this.deleteState===0){
             //   this.btnName="";
            }
            if(this.deleteState===2){
               // this.btnName="确认删除";

              // deleteFn.call(this);
            }

                console.log(this.btnName);

            },



            touchStartFunc:function(e){
                this.moveStart=true;
                console.log("touchStartFunc");
                this.touchPos(e);
                this.startX = this.oPosition.x;
                this. startY =this. oPosition.y;

            },
              //获取触点位置
                      touchPos:  function (e){
                             if(e.clientX){


                                    this.oPosition.x = e.clientX;
                                    this.oPosition.y = e. clientY;
                                    return this.oPosition;
                                }
                            var touches = e.changedTouches, l = touches.length, touch, tagX, tagY;
                            for (var i = 0; i < l; i++) {
                                touch = touches[i];
                                tagX = touch.clientX;
                                tagY = touch.clientY;
                            }
                            this.oPosition.x = tagX;
                            this.oPosition.y = tagY;
                            return this.oPosition;
                        },
        //触摸移动
           touchMoveFunc: function (e){
           if(!this.moveStart){
            return;
           }
          // console.log("touchMoveFunc");
                this.touchPos(e);
                var moveX = this.oPosition.x -  this.startX;
                var moveY = this.oPosition.y -  this.startY;
               // console.log(moveX);
                if (moveX<-20) {
                    e.preventDefault();
                   this.deleteState=1;
                }
                if (moveX>20) {
                                    e.preventDefault();
                                   this.deleteState=0;
                                }
            },
        //触摸结束
        touchEndFunc:function (e){
        this.moveStart=false;
        console.log("touchEndFunc");
        }
        },


        events: {

        }
};



</script>
<style>
li{ list-style: none; }
.zw-app-slid-it{
padding-left:8px;
border-radius:8px;
/*border:1px solid red;*/
border-bottom:1px solid #bcadad;
/*min-height:75px;*/
display:flex;


justify-content: flex-start ;
 align-items: stretch ;
vertical-align:middle;
}
.zw-app-slid-it-pic{
width:40px;
min-width:40px;
/*display:table-cell;*/
}

.zw-app-slid-it-content{
width:80%;
position:relative;
/*margin-left:3%;*/
line-height:50px;
min-height:50px;
padding-left:3%;
/*display:table-cell;*/
vertical-align: middle;
}
.zw-app-slid-it-pic img{

width:100%;
/*max-height: 100px;
min-height: 100px;*/
}
.zw-app-slid-it-text{
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
.zw-app-slid-it h1 {
    font-size:16px !important;
    line-height:18px;
}
.zw-app-slid-it h2 {
    font-size:12px !important;
    line-height:20px;
}
.zw-app-slid-it-btn{
transition: width 0.2s;
white-space:nowrap;
vertical-align:center;
/*min-height:75px;
line-height:75px;*/
text-align:center;
   background-color:#f47d7d;
   width:20%;
  /* height:100%;*/
   padding-top:5px;
   color:white;
}
.zw-app-slid-it h3 {
    font-size:11px !important;
}
.zw-app-slid-it-switch{
    padding-right:15px;
    width:75px;
        width:35px;


}
.zw-app-slid-it-switch img{

    height:15px;/**控制图片的展示大小*/
  /**控制水平居中*/
}
.zw-app-slid-it-title{
display:inline-block;
padding-top:12px;
}
.zw-app-slid-it-switch *{




}


 .ui-flex {
            display: -webkit-box !important;
            display: -webkit-flex !important;
            display: -ms-flexbox !important;
            display: flex !important;
            -webkit-flex-wrap: wrap;
            -ms-flex-wrap: wrap;
            flex-wrap: wrap
        }

        .ui-flex, .ui-flex *, .ui-flex :after, .ui-flex :before {
            box-sizing: border-box
        }

        .ui-flex.justify-center {
            -webkit-box-pack: center;
            -webkit-justify-content: center;
            -ms-flex-pack: center;
            justify-content: center
        }
        .ui-flex.center {
            -webkit-box-pack: center;
            -webkit-justify-content: center;
            -ms-flex-pack: center;
            justify-content: center;
            -webkit-box-align: center;
            -webkit-align-items: center;
            -ms-flex-align: center;
            align-items: center
        }
</style>
