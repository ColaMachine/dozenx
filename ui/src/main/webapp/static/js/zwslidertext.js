

    textSlider = function(id,settings){
        var defaultSettings = {
            width: 640, //容器宽度
            height: 50, //容器高度
            during: 5000, //间隔时间
            speed:30 //滑动速度
        }
        settings = extend(  defaultSettings, settings);//将个性化的配置覆盖默认配置
        return  function(){
            var v =50;
            var _this  = null;

            if(id instanceof HTMLElement){
                 _this = id;
            }else{
                 _this = document.getElementById(id)
             }
             s = settings;//获取容器id
               var oMover = _this.getElementsByTagName("ul" )[0]; //滚动元素

            var timer = null; //计时器
            var oLi =oMover.getElementsByTagName("li" );  //滚动单元
 var moveWidth = getHeight(oLi[0]);
            var num = oLi.length; //滚动屏幕数
            var iCurr =0;


                        height(_this,s.height); //设置配置好的高度

              css(_this,{

                                     position: 'relative',
                                       overflow: 'hidden',
                                    margin:'0 auto'
                                  });
            css(oMover,{
                position: 'absolute',
                left: 0
            });

             height(oMover,getStyleHeight(oLi[0])*num);
            //页面加载完毕BANNER自动滚动
           autoMove();

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



                //removeClass(oFocus,"current");

                //addClass(oFocus[iCurr] ,"current");
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

                animation(oMover, "top", iTarget, 200, fn);

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
