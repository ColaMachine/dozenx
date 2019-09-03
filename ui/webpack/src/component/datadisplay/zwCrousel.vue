<template>


 <div  ref="wrap" class="slider">
 <ul id="sa">

 <li v-for="item in imglist "><a :href="item.url" target="_blank"><img :src="item.pic" alt=""/></a></li>
 </ul>
 </div>
</template>
<script type="text/javascript">
import zwIcon from '../icon/zwIcon.vue';

export default {
        name: 'zwCrousel',
         components:{zwIcon},
        props:
            ["imglist","width","height"]//"tabs"
        ,
        data () {
            return {

            };
        },
        computed: {

        },
        /*  watch: {
          imglist(val, oldVal) {
          console.log("crousle is changed");
                console.log("inputVal = " + val + " , oldValue = " + oldVal)

              }
              },*/
        updated(){
              yxMobileSlider(this.$refs.wrap ,{width:this.width||340,height:this.height||220,during:3000});
         },
        mounted () {
            console.log(this.$refs.wrap );
          //  yxMobileSlider(this.$refs.wrap ,{width:this.width||340,height:this.height||220,during:3000});

        },
        methods: {


        },


        events: {

        }
};



   var yxMobileSlider = function(id,settings){
        var defaultSettings = {
            width: 640, //容器宽度
            height: 320, //容器高度
            during: 5000, //间隔时间
            speed:30 //滑动速度
        }
        settings = extend(  defaultSettings, settings);//将个性化的配置覆盖默认配置
        return  function(){
            var _this  = null;
            if(id instanceof HTMLElement){
                 _this = id;
            }else{
                 _this = document.getElementById(id)
             }
             var s = settings;//获取容器id
             append(_this,'<div class="focus"><div></div></div>');//添加圆点
            var startX = 0, startY = 0; //触摸开始时手势横纵坐标
            var temPos; //滚动元素当前位置
            var iCurr = 0; //当前滚动屏幕数
            var timer = null; //计时器
            var oMover = _this.getElementsByTagName("ul" )[0]; //滚动元素
            var oLi =_this.getElementsByTagName("li" );  //滚动单元
            var imgs= _this.getElementsByTagName("img"); // 图片
            var num = oLi.length; //滚动屏幕数
            var oPosition = {}; //触点位置
            var moveWidth = s.width; //滚动宽度
            //初始化主体样式
            width(_this,s.width);   //设置配置好的宽度
            height(_this,s.height); //设置配置好的高度
           css(_this,{

                         position: 'relative',
                           overflow: 'hidden',
                        margin:'0 auto'
                      });

          //设定容器宽高及样式
            css(oMover,{
                position: 'absolute',
                left: 0
            });
            css(oLi,{
                float: 'left',
                display: 'inline'
            });

           css(imgs,{
                width: '100%',
                height: '100%'
            });
            //初始化焦点容器及按钮

            var oFocusContainer = document.getElementsByClassName("focus")[0];
            var  oFocusContainerDiv = oFocusContainer.getElementsByTagName("div")[0];
            for (var i = 0; i < num; i++) {
               append(oFocusContainerDiv,"<span></span>") ;

            }
            var oFocus = oFocusContainer.getElementsByTagName("span");
            if(oFocus.length==0){
                            return;
                        }
            css(oFocusContainer,{
                minHeight: oFocus.height * 2,
                position: 'absolute',
                bottom: 0,
                //background: 'rgba(0,0,0,0.5)'
            })

           css(oFocus,{
                display: 'block',
                float: 'left',
                cursor: 'pointer'
            })
            width(oFocusContainerDiv,getStyleWidth(oFocus[0])*num*2);
           // console.log("oFocus.width"+);

           css(oFocusContainerDiv,{
                position: 'absolute',

                right: 10,
                top: '50%',
                marginTop: -oFocus.width / 2
            });

            addClass(oFocus[0],"current");
            //页面加载或发生改变

            var resize=  function(){
                if (isMobile()) {
                    mobileSettings();
                    bindTochuEvent();
                }
                var thisWidth =getWidth(_this);
                var thisHeight = getHeight(_this);
                width(oLi,thisWidth);
                height(oLi,thisHeight);//设定滚动单元宽高

                width(oMover,num * thisWidth);
                width(oFocusContainer,thisWidth);//alert(getWidth(oFocusContainer));
                height(oFocusContainer,thisHeight * 0.15);
                css(oFocusContainer,{
                    zIndex: 2
                });//设定焦点容器宽高样式
                //_this.fadeIn(300);
                css(_this,{
                    display:"block"
                });
            };
            window.onload=resize();

            //页面加载完毕BANNER自动滚动
            if(s.autoMove){//如果配置文件里默认的是自动移动的话
                           autoMove();
                      }
            //PC机下焦点切换
            if (!isMobile()) {
                for(var i=0;i<oFocus.length;i++){
                    let oFocusIndex = i;
                     oFocus[i].addEventListener("mouseenter",
                   function(){

                        iCurr = oFocusIndex - 1;console.log(oFocusIndex);
                        stopMove();
                        doMove();

                    });
                    oFocus[i].addEventListener("onmouseleave",
                        function(){
                        autoMove();

                    });

                }

            }
            //自动运动
            function autoMove(){
                timer = setInterval(doMove, s.during);
            }
            //停止自动运动
            function stopMove(){
                clearInterval(timer);
            }
            //运动效果
            function doMove(){
                iCurr = iCurr >= num - 1 ? 0 : iCurr + 1;
                doAnimate(-moveWidth * iCurr);



                removeClass(oFocus,"current");

                addClass(oFocus[iCurr] ,"current");
            }
            //绑定触摸事件
            function bindTochuEvent(){
                oMover.addEventListener('touchstart', touchStartFunc, false);
                oMover.addEventListener('touchmove', touchMoveFunc, false);
                oMover.addEventListener('touchend', touchEndFunc, false);
            }
            //获取触点位置
            function touchPos(e){
                var touches = e.changedTouches, l = touches.length, touch, tagX, tagY;
                for (var i = 0; i < l; i++) {
                    touch = touches[i];
                    tagX = touch.clientX;
                    tagY = touch.clientY;
                }
                oPosition.x = tagX;
                oPosition.y = tagY;
                return oPosition;
            }
            //触摸开始
            function touchStartFunc(e){
                clearInterval(timer);
                touchPos(e);
                startX = oPosition.x;
                startY = oPosition.y;
                temPos = getLeft(oMover);
            }
            //触摸移动
            function touchMoveFunc(e){
                touchPos(e);
                var moveX = oPosition.x - startX;
                var moveY = oPosition.y - startY;
                if (Math.abs(moveY) < Math.abs(moveX)) {
                    e.preventDefault();
                    css(oMover,{
                        left: temPos + moveX
                    });
                }
            }
            //触摸结束
            function touchEndFunc(e){
                touchPos(e);
                var moveX = oPosition.x - startX;
                var moveY = oPosition.y - startY;
                if (Math.abs(moveY) < Math.abs(moveX)) {
                    if (moveX > 0) {
                        iCurr--;
                        if (iCurr >= 0) {
                            var moveX = iCurr * moveWidth;
                            doAnimate(-moveX, autoMove);
                        }
                        else {//alert(autoMove);
                            doAnimate(0, autoMove);
                            iCurr = 0;
                        }
                    }
                    else {
                        iCurr++;
                        if (iCurr < num && iCurr >= 0) {
                            var moveX = iCurr * moveWidth;
                            doAnimate(-moveX, autoMove);
                        }
                        else {
                            iCurr = num - 1;
                            doAnimate(-(num - 1) * moveWidth, autoMove);
                        }
                    }
                    removeClass(oFocus,"current");
                    addClass(oFocus[iCurr],"current");
                }
            }
            //移动设备基于屏幕宽度设置容器宽高
            function mobileSettings(){
                moveWidth = getWinWidth();
                var iScale =moveWidth / s.width;
                height(_this,s.height * iScale);
                width(_this,moveWidth);
                console.log("moveWidth:"+moveWidth);
                css(oMover,{
                    left: -iCurr * moveWidth
                });
            }
            //动画效果
            function doAnimate(iTarget, fn){

                animation(oMover, "left", iTarget, 200, fn);

            }
            //判断是否是移动设备
            function isMobile(){
                if (navigator.userAgent.match(/Android/i) || navigator.userAgent.indexOf('iPhone') != -1 || navigator.userAgent.indexOf('iPod') != -1 || navigator.userAgent.indexOf('iPad') != -1) {

                    return true;
                }
                else {
                    return false;
                }
            }
        }("hello");
    }



</script>
<style>

.button {
	display: inline-block;
	zoom: 1;
	*display: inline;
	vertical-align: baseline;
	margin: 0 2px;
	outline: none;
	cursor: pointer;
	text-align: center;
	text-decoration: none;
	font: 14px/100% Arial, Helvetica, sans-serif;
	padding:0.25em 0.6em 0.3em;
	text-shadow: 0 1px 1px rgba(0,0,0,.3);
	-webkit-border-radius: .5em;
	-moz-border-radius: .5em;
	border-radius: .5em;
	-webkit-box-shadow: 0 1px 2px rgba(0,0,0,.2);
	-moz-box-shadow: 0 1px 2px rgba(0,0,0,.2);
	box-shadow: 0 1px 2px rgba(0,0,0,.2);
}
.red {
	color: #faddde;
	border: solid 1px #980c10;
	background: #d81b21;
	background: -webkit-gradient(linear, left top, left bottom, from(#ed1c24), to(#A51715));
	background: -moz-linear-gradient(top, #ed1c24, #A51715);
	filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#ed1c24', endColorstr='#aa1317');
}
.red:hover {
	background: #b61318;
	background: -webkit-gradient(linear, left top, left bottom, from(#c9151b), to(#a11115));
	background: -moz-linear-gradient(top, #c9151b, #a11115);
	filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#c9151b', endColorstr='#a11115');
	color:#fff;
}
.red:active {
	color: #de898c;
	background: -webkit-gradient(linear, left top, left bottom, from(#aa1317), to(#ed1c24));
	background: -moz-linear-gradient(top, #aa1317, #ed1c24);
	filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#aa1317', endColorstr='#ed1c24');
}
.cor_bs,.cor_bs:hover {
	color:#ffffff;
}
   .keBody{
    /*background:url(../images/bodyBg.jpg) repeat #333;*/
   }
.keTitle {
	height:100px;
	line-height:100px;
	font-size:30px;
	font-family:'微软雅黑';
	color:#FFF;
	text-align:center;
	/*background:url(../images/bodyBg3.jpg) repeat-x bottom left;*/
	font-weight:normal
}
.kePublic {
	background:#FFF;
	padding:50px;
}
.keBottom {
	color:#FFF;
	padding-top:25px;
	line-height:28px;
	text-align:center;
	font-family:'微软雅黑';
	/*background:url(../images/bodyBg2.jpg) repeat-x top left;*/
	padding-bottom:25px
}
.keTxtP {
	font-size:16px;
	color:#ffffff;
}
.keUrl {
	color:#FFF;
	font-size:30px;
}
.keUrl:hover {
	text-decoration: underline;
	color: #FFF;
}
.mKeBanner,.mKeBanner div {
	text-align:center;
}
.slider {
	display:none
}
.focus span {
	width:10px;
	height:10px;
	margin-right:10px;
	border-radius:50%;
	background:#666;
	font-size:0
}
.focus span.current {
	background:#fff
}
</style>
