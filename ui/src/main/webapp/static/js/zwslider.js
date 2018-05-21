

    yxMobileSlider = function(id,settings){
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
             s = settings;//获取容器id
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
            css(oFocusContainer,{
                minHeight: oFocus.height * 2,
                position: 'absolute',
                bottom: 0,
                background: 'rgba(0,0,0,0.5)'
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
           autoMove();
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
                        else {alert(autoMove);
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
