
var _Entity_ = function(state, divId) {
(function($) {
	$.fn.yxMobileSlider = function(settings) {
		var defaultSettings = {
			width: '100%', //容器宽度
			height: '239px', //容器高度
			during: 3000, //间隔时间
			speed: 20 //滑动速度
		};
		settings = $.extend(true, {}, defaultSettings, settings);
		return this.each(function() {
			var _this = $(this),
				s = settings;
			var startX = 0,
				startY = 0; //触摸开始时手势横纵坐标
			var temPos; //滚动元素当前位置
			var iCurr = 0; //当前滚动屏幕数
			var timer = null; //计时器
			var oMover = $("ul", _this); //滚动元素
			var oLi = $("li", oMover); //滚动单元
			var num = oLi.length; //滚动屏幕数
			var oPosition = {}; //触点位置
			var moveWidth = s.width; //滚动宽度
			//初始化主体样式
			_this.width(s.width).height(s.height).css({
				position: 'relative',
				overflow: 'hidden',
				margin: '0 auto'
			});
			//设定容器宽高及样式
			oMover.css({
				position: 'absolute',
				left: 0
			});
			oLi.css({
				float: 'left',
				display: 'inline'
			});
			$("img", oLi).css({
				width: document.body.clientWidth+'px',
				height: (document.body.clientWidth*0.56)+'px'
			});
			//初始化焦点容器及按钮
			console.log('slider start');
			_this.append('<div class="focus"><div></div></div>');
			var oFocusContainer = $(".focus");
			for (var i = 0; i < num; i++) {
				$("div", oFocusContainer).append("<span></span>");
			}
			var oFocus = $("span", oFocusContainer);
			oFocusContainer.css({
				minHeight: _this.find('span').height() * 2,
				position: 'absolute',
				bottom: 0
					// background: 'rgba(0,0,0,0.5)'
			})
			$("span", oFocusContainer).css({
				display: 'block',
				float: 'left',
				cursor: 'pointer'
			})
			$("div", oFocusContainer).width(oFocus.outerWidth(true) * num).css({
				position: 'absolute',
				right: _this.width() / 2 - num * 10 - 10,
				top: '50%',
				marginTop: -$(this).find('span').width() / 2
			});
			oFocus.first().addClass("current");
			//页面加载或发生改变
			$(window).on('resize load', function() {
				if (isMobile()) {
					mobileSettings();
					bindTochuEvent();
				}
				oLi.width(_this.width()).height(_this.height()); //设定滚动单元宽高
				oMover.width(num * oLi.width());
				oFocusContainer.width(_this.width()).height(_this.height() * 0.15).css({
					zIndex: 2
				}); //设定焦点容器宽高样式
				_this.fadeIn(300);
			});
			//页面加载完毕BANNER自动滚动
			autoMove();
			//PC机下焦点切换
			if (!isMobile()) {
				oFocus.hover(function() {
					iCurr = $(this).index() - 1;
					stopMove();
					doMove();
				}, function() {
					autoMove();
				})
			}
			//自动运动
			function autoMove() {
				timer = setInterval(doMove, s.during);
			}
			//停止自动运动
			function stopMove() {
				clearInterval(timer);
			}
			//运动效果
			function doMove() {
				iCurr = iCurr >= num - 1 ? 0 : iCurr + 1;
				var a=oLi.width()*iCurr;
//				if (moveWidth == "100%") {
//					a = document.body.clientWidth * iCurr;
//				} else {
//					a = iCurr * moveWidth;
//				}
				doAnimate(-a);
				oFocus.eq(iCurr).addClass("current").siblings().removeClass("current");
			}
			//绑定触摸事件
			function bindTochuEvent() {
				oMover.get(0).addEventListener('touchstart', touchStartFunc, false);
				oMover.get(0).addEventListener('touchmove', touchMoveFunc, false);
				oMover.get(0).addEventListener('touchend', touchEndFunc, false);
			}
			//获取触点位置
			function touchPos(e) {
				var touches = e.changedTouches,
					l = touches.length,
					touch, tagX, tagY;
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
			function touchStartFunc(e) {
				stopMove();
				// clearInterval(timer);
				touchPos(e);
				startX = oPosition.x;
				startY = oPosition.y;
				temPos = oMover.position().left;
			}
			//触摸移动
			function touchMoveFunc(e) {
				touchPos(e);
				var moveX = oPosition.x - startX;
				var moveY = oPosition.y - startY;
				if (Math.abs(moveY) < Math.abs(moveX)) {
					e.preventDefault();
					oMover.css({
						left: temPos + moveX
					});
				}
			}
			//触摸结束
			function touchEndFunc(e) {
				touchPos(e);
				var moveX = oPosition.x - startX;
				var moveY = oPosition.y - startY;
				if (Math.abs(moveY) < Math.abs(moveX)) {
					if (moveX > 0) {
						iCurr--;
						if (iCurr >= 0) {
							var moveX = iCurr * moveWidth;
							doAnimate(-moveX, autoMove);
						} else {
							doAnimate(0, autoMove);
							iCurr = 0;
						}
					} else {
						iCurr++;
						if (iCurr < num && iCurr >= 0) {
							var moveX = iCurr * moveWidth;
							doAnimate(-moveX, autoMove);
						} else {
							iCurr = num - 1;
							doAnimate(-(num - 1) * moveWidth, autoMove);
						}
					}
					oFocus.eq(iCurr).addClass("current").siblings().removeClass("current");
				}
			}
			//移动设备基于屏幕宽度设置容器宽高
			function mobileSettings() {
				moveWidth = $(window).width();
				var iScale = $(window).width() / s.width;
				_this.height(s.height * iScale).width($(window).width());
				oMover.css({
					left: -iCurr * moveWidth
				});
			}
			//动画效果
			function doAnimate(iTarget, fn) {
				oMover.stop().animate({
					left: iTarget
				}, _this.speed, function() {
					if (fn)
						fn();
				});
			}
			//判断是否是移动设备
			function isMobile() {
				if (navigator.userAgent.match(/Android/i) || navigator.userAgent.indexOf('iPhone') != -1 || navigator.userAgent.indexOf('iPod') != -1 || navigator.userAgent.indexOf('iPad') != -1) {
					return true;
				} else {
					return false;
				}
			}
		});
	}
})(jQuery);
var REQUEST_IMG='http://www.baidu.com/img/bdlogo.gif';
        var STATION_TYPE = 'beta';
        var PATH = "", //请求
            JSCSS_PATH = '', //JSCSS
            UPLOAD_PATH = '', //上传
            IMG_PATH = ''; //图片
        if (STATION_TYPE == 'beta') {
            JSCSS_PATH = 'http://beta-msp-static.51awifi.com/V1/';
            UPLOAD_PATH = 'http://beta-msp-img.51awifi.com/upload/';
            IMG_PATH = 'http://beta-msp-img.51awifi.com/V1/';
        } else if (STATION_TYPE == 'pre') {
            JSCSS_PATH = 'http://pre-msp-static.51awifi.com/V1/';
            UPLOAD_PATH = 'http://pre-msp-img.51awifi.com/upload/';
            IMG_PATH = 'http://pre-msp-img.51awifi.com/V1/';
        }else if(STATION_TYPE=='pro'){
            JSCSS_PATH = 'http://msp-static.51awifi.com/V1/';

            UPLOAD_PATH = 'http://msp-img.51awifi.com/upload/';
            IMG_PATH = 'http://msp-img.51awifi.com/V1/';
        }else { //测试！！！
            UPLOAD_PATH = '/statics/upload/';
            JSCSS_PATH = '/statics/static/V1/';
            IMG_PATH = '/statics/static/V1/';
        }
              var PATH= "http://beta-msp.51awifi.com";
                var testMerhcantId = "5590";
                  function mobileStyle(str) {
                                            var re = /^1\d{10}$/;
                                            if (!re.test(str)) {
                                                return true;
                                            }
                                        };
                                        /**
                                         * 验证密码格式
                                         */
                                        function captchaStyle(str) {
                                            var re = /^[0-9]{4}$/;
                                            if (!re.test(str)) {
                                                return true;
                                            }
                                        };
                function tip(msg){
                console.log(msg);
                }
                (function(window) {
                	var loadingHtml =
                		'<div class="loading" id="loading" style="{{height}}">' +
                		'<div class="loading-warp" style="{{style}}">' +
                		'<div class="loading-content"><span class="loading-circle loading-circle-one"></span></div>' +
                		'<div class="loading-content"><span class="loading-circle loading-circle-two"></span></div>' +
                		'<div class="loading-content"><span class="loading-circle loading-circle-three"></span></div>' +
                		'</div>' +
                		'</div>';

                	var loading = {
                		topPx: 0,
                		heightPx: 0,

                		getHeight: function() {
                			return document.body.clientHeight + 60;
                		},

                		init: function() {
                			var self = this;
                			self.topPx = window.screen.availHeight / 2 + window.scrollY;
                			self.heightPx = self.getHeight();
                		},

                		show: function(height) {
                			var self = this;
                			self.init();
                			height = height || self.heightPx;
                			var leftPx = document.body.scrollWidth / 2 - 70 / 2;
                			var html = loadingHtml.replace('{{style}}', 'margin-top:' + self.topPx + 'px;left:' + leftPx + 'px').replace('{{height}}', 'height:' + self.heightPx + 'px');
                			$('body').css({
                				'overflow': 'hidden'
                			}).append(html);
                		},

                		hide: function() {
                			$('#loading').remove();
                			$('body').css({
                				'overflow': ''
                			})
                		}
                	};

                	window.loading = window.loading || loading;


                var Dialogue = {
                		modal: function(html, top, height, fn) {
                			var mask_html = '<div class="mask" > </div>';
                			var modal_html = '<div class="modal" style="z-index:30000"><div class="content row-10 " ></div>' +
                				'<div class="row-6 center-text"><button class="back-orange" >确定</button>' +
                				'</div></div>';
                			var msg_html = '<div class="row-9 center-text" style="min-height:50px"></div>';
                			var m = {};
                			m.showMask = function() {
                				if ($(".mask").length == 0) {
                					$("body").append(mask_html);
                				}
                				$(".mask").show();
                			};
                			m.hideMask = function() {
                				$(".mask").hide();
                			};
                			m.showModal = function() {
                				this.showMask();
                				if ($(".modal").length == 0) {
                					$("body").append(modal_html);
                				}
                				if (top && top != '') {
                					$(".modal").css('top', top);
                				}
                				if (height && height != '') {
                					$(".modal").css('height', height);
                				}
                				$(".modal").find(".content").html(html || msg_html);
                				$(".modal").show();
                				this.distroy();
                			};
                			m.hideModal = function() {
                				this.hideMask();
                				$(".modal").find(".content").html('');
                				$(".modal").hide();
                			};
                			m.distroy = function() {
                				var that = this;
                				$(".modal").find("button").on('click', function() {
                					that.hideModal();
                					if (typeof(fn) == 'function') {
                						fn();
                						fn=null;
                					}
                				});
                			}

                			return m;
                		},
                		tip: function(msg) {

                		}
                	};
                	window.Dialogue = window.Dialogue || Dialogue;
                })(window);

                var Ajax={
                 get:function(url,data,callback){
                    this.AjaxFun(url,data,callback,{type:"GET"});
                 },
                 getJSON:function(url,data,callback){
                    this.AjaxFun(url,data,callback,{type:"GET",dataType:"JSON"});
                 },
                  getJSONP:function(url,data,callback){
                     this.AjaxFun(url,data,callback,{type:"GET",dataType:"jsonp", jsonp: "callback",jsonpCallback:"success_jsonpCallback"});
                  },
                 post:function(url,data,callback){
                    this.AjaxFun(url,data,callback,{type:"POST"});
                 },
                 AjaxFun:function (url, inputData, callback, options, callbackOnError) {
                         	var contextUrl = window.location.href;
                         	options = options || {};
                         	if(url.indexOf("?")!=-1){
                         	    url+="&r="+Math.floor(Math.random() * ( 1000 + 1));
                         	}else{
                         	     url+="?r="+Math.floor(Math.random() * ( 1000 + 1));
                         	}
                         	options.url =  url;
                         	options.type = options.type||"POST";
                         	options.data = inputData;
                         	options.contentType="application/x-www-form-urlencoded; charset=utf-8";
                         	//options.data = encodeURIComponent(JSON.stringify(inputData));
                         	if (typeof options.async == 'undifined')
                         		options.async = false;
                         	var param = options.inputData;
                         	options.success = function(outputData) {//alert("success")
                         		if(outputData.r=="504"){
                         			window.location=PATH+"/login.htm";
                         		}
                         		if (typeof callback == 'function') {
                         			callback(outputData);
                         		}
                         	};
                         	options.error = function(jqXHR, textStatus, errorThrown) {//alert("error")
                         		// $("body").unmask();
                         		var responseText = jqXHR.responseText || "";
                         		if ((jqXHR.status == 500 || jqXHR.status == 1000)
                         				&& responseText.indexOf("dwr.engine.http.1000") >= 0) {
                         			top.location.replace(acwsContext + '/acwsui/pages/logout.htm');// /j_acegi_logout
                         			return;
                         		}
                         		if (typeof callbackOnError == 'function') {
                         			callbackOnError(errorThrown);
                         		} else {//alert(typeof callbackOnError);
                         			alert('参数不是function');
                         		}
                         	};
                         	delete options['inputData'];
                         	//ajax(options);
                         	$.ajax(options);


                         }
                };
                function success_jsonpCallback(){
                }
                (function(window) {


                	var htmlDecode = function(value) {
                		if (value && (value === '&nbsp;' || value === '&#160;' || (value.length === 1 && value
                				.charCodeAt(0) === 160))) {
                			return "";
                		}
                		return !value ? value : String(value).replace(
                				/&gt;/g, ">").replace(/&lt;/g, "<")
                			.replace(/&quot;/g, '"').replace(/&amp;/g,
                				"&");
                	};
                	var htmlEncode = function(value) {
                		return !value ? value : String(value).replace(/&/g,
                			"&amp;").replace(/\"/g, "&quot;").replace(
                			/</g, "&lt;").replace(/>/g, "&gt;");
                	};


                	var setCookie = function(name, value, hours, path) {
                		var name = escape(name);
                		var value = escape(value);
                		var expires = new Date();
                		expires.setTime(expires.getTime() + hours * 3600000);
                		path = path == "" ? "" : ";path=" + path;
                		var _expires = (typeof hours) == "string" ? "" : ";expires=" + expires.toUTCString();
                		document.cookie = name + "=" + value + _expires + path;
                		//		store.set(name, value);
                	};
                	var getCookieValue = function(name) {
                		if(name=="MS_merchantId"){

                		}
                		var value="";
                		switch(name)
                		{
                		case "MS_merchantId":
                		  value=globalParam["merId"];
                		  break;
                		case "merId":
                		  value=globalParam["merId"];
                		  break;
                		case "user_phone":
                		  value=globalParam["user_phone"];
                		  break;

                		case "dev_id":
                		  value=globalParam["dev_id"]||globalParam["devId"];

                		  break;
                		case "user_mac":
                		  value=globalParam["user_mac"];
                		  break;
                		case "url":
                		  value=globalParam["url"];
                		  break;
                		case "login_type":
                		  value=globalParam["login_type"];
                		  break;
                		 case "region":
                		  value=globalParam["region"];
                		  break;
                		 case "belongTo":
                		  value=globalParam["belongTo"];
                		  break;
                		 case "model":
                		  value=globalParam["model"];
                		  break;
                		  case "trafficLimit":
                		  value=globalParam["trafficLimit"];
                		  break;
                		  case "ssid":
                		  value=globalParam["ssid"];
                		  break;
                		  case "devName":
                		  value=globalParam["devName"];
                		  break;
                		  case "corp":
                		  value=globalParam["corp"];
                		  break;
                		case "MS_deviceId":
                		  value=globalParam["devId"]||globalParam["dev_id"];

                		  break;
                		case "ap_mac":
                		  value=globalParam["mac"];
                		  break;
                		case "MS_terMac":
                		  value=globalParam["user_mac"];
                		  break;
                		case "MS_terMac":
                		  value=globalParam["user_mac"];
                		  break;
                		case "gw_address":
                		  value=globalParam["gw_address"];
                		  break;
                		case "gw_url":
                		  value=globalParam["url"];
                		  break;
                		case "host":
                		  value=globalParam["host"];
                		  break;
                		case "gw_port":
                		  value=globalParam["gw_port"];
                		  break;


                		default:
                			value=globalParam[name];
                		}
                		var name = escape(name);
                		var allcookies = document.cookie;
                		name += "=";
                		var pos = allcookies.indexOf(name);
                		if (pos != -1) {
                			var start = pos + name.length;
                			var end = allcookies.indexOf(";", start);
                			if (end == -1) end = allcookies.length;
                			var value = allcookies.substring(start, end);
                			return unescape(value)||value;
                		} else return value;
                		//		return (store.get(name) || '');
                	};
                	window.setCookie = window.setCookie || setCookie;
                	window.getCookieValue = window.getCookieValue || getCookieValue;
                	window.htmlDecode = window.htmlDecode || htmlDecode;
                	window.htmlEncode = window.htmlEncode || htmlEncode;

                })(window);

                 Function.prototype.Apply = function(thisObj)
                        {
                            var _method = this;
                            return function(data)
                            {
                                return _method.apply(thisObj,[data]);
                            };
                        };
                        function imgLoad2() {
                            var img = document.createElement('img');
                            img.style = 'display:none;';
                            img.src = REQUEST_IMG + '?' + new Date().getTime();
                            EventUtil.addHandler(img, 'error', function() {
                                console.log('你没连网');
                               //$("#linkMsg").text('未联网');
                                img = null;
                                imgLoad2();
                            });
                            EventUtil.addHandler(img, 'load', function() {
                                $("#linkMsg").text('已联网');

                                $("#showCompleteFrame").attr("src","https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/css/super_min_9e70e43e.css");
                            });
                        }


                        var EventUtil = {
                                addHandler: function(element, type, handler) {
                                    if (element.addEventListener) {
                                        element.addEventListener(type, handler, false);
                                    } else if (element.attachEvent) {
                                        element.attachEvent("on" + type, handler);
                                    } else {
                                        element["on" + type] = handler;
                                    }
                                },
                                removeHandler: function(element, type, handler) {
                                    if (element.removeEventListener) {
                                        element.removeEventListener(type, handler, false);
                                    } else if (element.detachEvent) {
                                        element.detachEvent("on" + type, handler);
                                    } else {
                                        element["on" + type] = null;
                                    }
                                }
                            };
                var _state = state || {
                        login_frame_show:"none",
                        fast_login_show:"none",
                        get_free_pkg_show:"none",
                        left_time_show:"none",

                    };
                var HeanderReact= React.createClass({
                render:function(){
                   return ( <header className="indexWrap header back-blue">         <nav>
                      <a id="login" style={{cursor:"pointer"}}>
                   <div id="loginBtn" className="header-left center-text row-3 left small-text">
                                 <img className="header-left-img" src="http://msp-img.51awifi.com/V1/img/header/wifi.png"/>
                                           <span className="header-left-span right-text" id="linkMsg">请连网</span>
                                              </div>             </a>
                                                <div className="center-text row-4 left normal-text" style={{whiteSpace:"nowrap",textOverflow:"ellipsis",overflow: "hidden"}} id="merchantname">Yxy</div>
                                                       <a href="#/userCenter" id="userCenter">                 <div className="header-right center-text row-3 left small-text">

                                                            <img className="header-right-img right" src="http://msp-img.51awifi.com/V1/img/header/information.png"/>
                                                                </div>
                                                                   </a>
                                                                     </nav>
                                                                     </header>)
                }
                });
                 var LogoReact= React.createClass({
                                render:function(){
                                   return (
                                    <header className="loginWrap" style={{display:"none"}} >
                                          <a href="#/">
                                              <div className="loginlogo row-10 back-blue center-text">
                                                  <img class="row-5-2 loginlogo-icon" src="http://msp-img.51awifi.com/V1/img/header/wifiLogo.png" alt="logo"/>
                                                  <span className="logoWord">爱WiFi欢迎您</span>
                                              </div>
                                          </a>
                                      </header>

                                   );
                                }
                                });
                var LoginReact= React.createClass({
                                               render:function(){
                                                  return (
                                                  <div className="loginWrap container regcontainer" style={{display:"none"}}>
                                                                      <div className="row-9 loginErr">
                                                                          <span className="errorMsg"></span>
                                                                      </div>
                                                                      <div className="row-9">
                                                                          <input type="text" className="logininput" name="username" placeholder="输入手机号" maxLength="11" />
                                                                      </div>

                                                                      <div className="row-9 captcha">
                                                                          <input className="row-4-7" type="text" placeholder="输入验证码" name="captcha" maxLength="4" />
                                                                          <button className="row-4-7 right clr-fix back-gray gray-text" onClick={this.captchaClick} >获取验证码</button>
                                                                      </div>

                                                                      <div className="row-9 loginbutton">
                                                                          <button className="back-orange" id="login" onClick={this.login} >登录</button>

                                                                      </div>
                                                                  </div>

                                                  );
                                               }
                                               });
               var RollingMsgReact= React.createClass({
                                                           render:function(){
                                                              return (
                                                             <div className="row-10 activeDiv" style={{backgroundImage:" url(http://msp-img.51awifi.com/V1/img/index/activeBack.png)"}}>
                                                             			<div className=" text-indent row-8 left small-text" style={{height: "30px",lineHeight:'30px' }}>
                                                             				<ul id="activeInfo" style={{margin: 0,padding:0,listStyle:"none"}} >


                                                             					<li id="activeInfoLi">
                                                             						<marquee >
                                                             							动态: <span>恭喜AWIFI园区开通 !</span>
                                                             							&nbsp; &nbsp;&nbsp;动态: <span>全场免费！</span>
                                                             							&nbsp;&nbsp;&nbsp;动态:<span>免费是假的！</span>
                                                             							&nbsp;&nbsp;&nbsp;动态: <span>当然要收费！</span>
                                                             							&nbsp;&nbsp;&nbsp; 动态:<span>其实真不贵！</span>
                                                             						</marquee>

                                                             					</li>
                                                             				</ul>

                                                             			</div>
                                                             			<div className="right-text row-2 left small-text" style={{height: '30px',lineHeight: '30px'}} id="closeActive">
                                                             				<div style={{height: '20px',lineHeight: '20px',marginTop: '3px'}}>
                                                             					<img src="http://msp-img.51awifi.com/V1/img/index/activeClose.png" style={{width:'20px',height:'20px',paddingRight: '1rem',verticalAlign:'middle'}}  />
                                                             				</div>
                                                             			</div>
                                                             		</div>

                                                              );
                                                           }
                                                           });

                     var RollingImageReact= React.createClass({
                                                                               render:function(){
                                                                                  return (
                                                                               <div className="carousel clr-fix">
                                                                               			<ul className="carousel-ul" id="carousel-ul">

                                                                               			</ul>
                                                                               		</div>

                                                                                  );
                                                                               }
                                                                               });

                                var AppsReact= React.createClass({
                                  render:function(){
                                                                                                                  return (
                                                                               <div className="app clr-fix">
                                                                               			<div className="row-2 left center-text">
                                                                               				<div className="app-icon row-8">
                                                                               					<a href="javascript:void(0)"><img className="row-9" src="http://msp-img.51awifi.com/V1/img/app/lunch.png"/></a>
                                                                               					<div className="small-text row-10">点餐</div>
                                                                               				</div>
                                                                               			</div>

                                                                               			<div className="row-2 left center-text">
                                                                               				<div className="app-icon row-8">
                                                                               					<a href="javascript:void(0)"><img class="row-9" src="http://msp-img.51awifi.com/V1/img/app/services.png"/></a>
                                                                               					<div className="small-text row-10">服务</div>
                                                                               				</div>
                                                                               			</div>

                                                                               			<div className="row-2 left center-text">
                                                                               				<div className="app-icon row-8">
                                                                               					<a href="javascript:void(0)"><img class="row-9" src="http://msp-img.51awifi.com/V1/img/app/order.png"/></a>
                                                                               					<div className="small-text row-10">买单</div>
                                                                               				</div>
                                                                               			</div>

                                                                               			<div className="row-2 left center-text">
                                                                               				<div className="app-icon row-8">
                                                                               					<a href="javascript:void(0)"><img class="row-9" src="http://msp-img.51awifi.com/V1/img/app/cup.png"/></a>
                                                                               					<div className="small-text row-10">续杯</div>
                                                                               				</div>
                                                                               			</div>

                                                                               			<div className="row-2 left center-text">
                                                                               				<div className="app-icon row-8">
                                                                               					<a href="javascript:void(0)"><img class="row-9" src="http://msp-img.51awifi.com/V1/img/app/share.png"/></a>
                                                                               					<div className="small-text row-10">分享</div>
                                                                               				</div>
                                                                               			</div>
                                                                               		</div>

                                                                               		  );
                                                                               }
                                                                               });

                  var fastLoginReact= React.createClass({
                                                               render:function(){
                                                                  return (
                                                                 <div className="row-10" id="fastLogin" style={{display:"none"}} >
                                                                               			<div className="row-9 " >
                                                                               				<div className="row-2 left" >
                                                                               					<img className="avatar" src="http://msp-img.51awifi.com/V1/img/index/shalouon.png"  />
                                                                               				</div>
                                                                               				<div className="row-4 left small-text " >
                                                                               					<div className="text-left">
                                                                               						当前账号
                                                                               					</div>
                                                                               					<div ><span id="fastLoginAccount" ></span><a className="link" id="switch_account">切换</a></div>
                                                                               				</div>
                                                                               				<div className="row-4 left right-text" >
                                                                               					<div className="row-9">

                                                                               						<button type="button" id="fastLoginBtn" >一键登录</button>

                                                                               					</div>
                                                                               				</div>
                                                                               			</div>
                                                                               		</div>


                                                                  );
                                                               }
                                                               });
                   var getFreePkg= React.createClass({
                                                                                render:function(){
                                                                                   return (
                                                                                <div className="row-10" id="getFreePkgWrap" style={{display:"none"}} >
                                                                                             <div className="row-9 ">


                                                                                                    <div className="row-9">

                                                                                                        <button id="getFreeBtn" type="button" className="back-orange" >领取免费包</button>

                                                                                                    </div>

                                                                                            </div>
                                                                                        </div>


                                                                                   );
                                                                                }
                                                                                });

                   var BuyReact= React.createClass({
                                                                                                  render:function(){
                                                                                                     return (
                                                                                                  <div className="row-10" id="buydivwrap" style={{display:"none"}}>
                                                                                                              <div className="row-9 " >
                                                                                                                  <div className="row-2 left" id="shalou">
                                                                                                                      <img  className="avatar" src="http://msp-img.51awifi.com/V1/img/index/shalouon.png"  />
                                                                                                                  </div>
                                                                                                                  <div className="row-4 left small-text" id="leftDiv">
                                                                                                                      <div id="leftTime">
                                                                                                                          您暂时无法上网
                                                                                                                      </div>
                                                                                                                      <div className="" id="leftTimeTitle">点击请连网登录</div>
                                                                                                                  </div>
                                                                                                                  <div className="row-4 left right-text" id="buyDiv">
                                                                                                                      <div className="row-9">

                                                                                                                          <button id="buy" type="button" className="back-orange" >时长购买</button>

                                                                                                                      </div>
                                                                                                                  </div>
                                                                                                              </div>
                                                                                                          </div>


                                                                                                     );
                                                                                                  }
                                                                                                  });
                var EntityReact = React.createClass({

                    getInitialState: function() {
                        return _state;
                    },

                 render: function() {

            return (
<div style={{position:"relative"}}>
            <LogoReact/>

            <LoginReact/>

            <HeanderReact/>
            <div  className="indexWrap container">
            <RollingMsgReact/>
            <RollingImageReact/>
            <AppsReact/>
            <fastLoginReact/>
            <getFreePkg/>
            <BuyReact/>
            </div>
</div>
            );
        },

componentDidMount:function(){
  this.request();
            	this.addEventListener();
},
                getInitialState:function(){

            	 return _state
                },
                    afterLoginFlag:false,
                    request: function() {
                        window.indexSI = window.indexSI || [];
                            window.globalParam={};
                            var msp_url = window.location.search;
                            if(msp_url && msp_url.length>0){
                                msp_url=msp_url.substr(1);
                                var ary = msp_url.split("&");
                                for(var i=0;i<ary.length;i++){
                                    var str =ary[i];
                                    var ele = str.split("=");
                                    if(ele.length>=2)
                                       globalParam[ele[0]]=ele[1];
                                  //  console.log(ele[0]+":"+ele[1]);
                                }
                            }

                            var dev_info = globalParam["dev_info"];
                                var devInfo = eval("("+decodeURI(dev_info)+")");
                                for(var i in devInfo){
                                    globalParam[i]=devInfo[i];
                                    console.log(i+":"+globalParam[i]);
                                }

                                var StringUtil={};
                                    StringUtil.isNull=function(it){
                                        if(it==null || typeof it=='undefinded' || it==''){
                                            return true;
                                        }
                                        return null;
                                    }
                                    StringUtil.isBlank=function(it){
                                        if(it==null || typeof it=='undefinded' || it==''){
                                            return true;
                                        }
                                        return null;
                                    }
                                    window.StringUtil=StringUtil;
                                    window.afterLoginFlag=false;
                                    window.loading={show:function(){},hide:function(){}};
                    },
                    dqurl: '',
                    noTime: '',
                    /**
                     * 发起请求去后台获取电渠的购买地址
                     * 获得电渠地址请求成功回调
                     * @param {Object} options
                     * @param {Object} result
                     * @param {Object} textStatus
                     * @param {Object} jqXHR
                     */
                    getDqUrlRequest: function(result) {
                        var that = this;
                        var dqurl;
                        console.log("getDqUrlRequest:++");
                        if (result.r == 1) {
                            //获取成功 处理地址
                            /*var data = result.msg || '{}';
                            if (data == 'null') {
                                data = '{}';
                            }*/
                           var data = result.data; //eval("(" + data + ")");
                           // alert( result.data);

                            dqurl =  data.deviceurl || '';
                            if (dqurl == '' || dqurl == 'error') {
                                dqurl = '';
                            } else {
                                dqurl = dqurl + "&link_phone=" + data.deviceusername + "&code_number=" + data.deviceinfo + "&yxgh=" + data.devicemerid;
                            }
                            console.log(dqurl);
                            that.dqurl = dqurl;
                            globalParam["dqUrl"] = dqurl;
                            var noTime = $("#buy").attr("name");
                            if ($("#buy").attr("disabled")) {
                                return;
                            } else if (dqurl != '') {
                                //没时间临时放行
                                if (noTime == 'noTime') {
                                    that.buy(dqurl);
                                } else {
                                    //有时间直接访问
                                    window.location.href = dqurl;
                                }
                            } else {
                                //获取地址不符合期望的情况
                                alert("未获取到宽带账号");
                                that.goLogin();
                            }
                        } else {
                            console.log("未登陆");
                            //获取失败 确认后跳转登录页
                            var html = '<div class="row-9 center-text" >支付服务失败，请重新登录！</div>';
                            Dialogue.modal(html, '25%', '180px', function() {
                                that.goLogin();
                                /*mvc.href("#/login");
                                $("#MainContent").hide();
                                $("#LoginWrap").show();*/
                            }).showModal();
                        }

                    },
                    /**
                     * 领取免费流量包请求成功回调
                     * 左上角要显示已联网
                     * 要有倒计时
                     * 能上网
                     * @param {Object} options
                     * @param {Object} result
                     * @param {Object} textStatus
                     * @param {Object} jqXHR
                     */
                    getFreeRequest: function( result) {
                        var that = this;
                        if (result.r == 1) {
                            var html = '<div class="row-9 center-text" >领取成功</div>';
                            Dialogue.modal(html, '25%', '180px', function() {
                                //mvc.href("#/");

                                var authData = {
                                    "merchantId": getCookieValue("MS_merchantId"),
                                    'deviceId': getCookieValue('MS_deviceId'),
                                    'apMac': getCookieValue('ap_mac'),
                                    'terMac': getCookieValue('MS_terMac'),
                                    'userMac': getCookieValue('MS_terMac'),
                                    'callback': "callback",
                                    'username': getCookieValue("MS_telephone"),
                                    'password': getCookieValue("MS_pcode")
                                };
                                afterLoginFlag = true;
                                that.auther(authData);

                            }).showModal();


                            $("#getFreeBtn").hide();
                            $("#getFreePkgWrap").hide();
                            //$("#buydivwrap").show();
                            $("#buy").show();

                               this.setState({login_frame_show:"block",
                                                get_free_pkg_show:"none",
                                                fast_login_show:"none",
                                                left_time_show:"none"
                                                });
                        } else if (result.msg) {
                            var html = '<div class="row-9 center-text">' + result.msg + '</div>';
                            //$("#timeMsg").html('您已领取免费上网体验包');
                            Dialogue.modal(html, '25%', '180px').showModal();
                        }
                    },
                    showLogin:function(){
                         this.setState({login_frame_show:"block" });
                    },
                     hideLogin:function(){
                         this.setState({login_frame_show:"none" });
                    },
                    showFreePkg:function(){
                           this.setState({get_free_pkg_show:"block" });
                    },
                    hideFreePkg:function(){
                           this.setState({get_free_pkg_show:"none" });
                    },
                    /**显示倒计时**/
                    leftTimeShow: function(user_cutoff) {
                        var cutoffdate = 0;
                        var now_time;
                        //获得剩余时长
                        if (user_cutoff) {
                            cutoffdate = user_cutoff.cutoffdate || 0;
                        } else {
                            cutoffdate = 0;
                        }
                        now_time = new Date().getTime();
                        //时长大于0
                        var leftTime = (cutoffdate - now_time) > 0 ? (cutoffdate - now_time) : 0;
                        if (leftTime > 0) {

                        }

                        //已登录才给弹框

                        if (leftTime <= (60 * 60 * 1000)) {
                            //基本没时间了 认为没时间
                            if (leftTime <= 3000) {
                                //that.noTime='noTime';
                                $("#buy").attr("name", "noTime");
                                var html = '<div class="row-9 center-text" >您已无上网时长</div>';
                                Dialogue.modal(html, '25%', '200px', '').showModal();
                            } else {
                                var html = '<div class="row-9 center-text "  >您的上网时长已不足一小时</div>' +
                                    '<div class="row-9 center-text " >请尽快购买时长</div>';
                                Ajax.getJSONP(PATH+"/ms/sms/send", {
                                    "mobile": getCookieValue("MS_telephone"),
                                    "type": "ms_warn"
                                }, function() {});
                                Dialogue.modal(html, '25%', '200px', '').showModal();
                            }
                        }
                        //倒计时
                        if (window.indexSI.length) {
                            for (var i in window.indexSI) {
                                clearInterval(window.indexSI[i]);
                            }
                        }
                        var indexSI = setInterval(function() {
                            var _html = '';
                            if (leftTime <= 0) {
                                if (window.indexSI.length) {
                                    for (var i in window.indexSI) {
                                        clearInterval(window.indexSI[i]);
                                    }
                                }
                                _html = '<span>您暂时无法上网</span>';
                                $("#leftTimeTitle").html("请购买上网时长");
                            } else {
                                $("#leftTimeTitle").html("剩余上网时间");
                                var d = Math.floor(leftTime / 1000 / 60 / 60 / 24);
                                var h = Math.floor(leftTime / 1000 / 60 / 60 % 24);
                                var m = Math.floor(leftTime / 1000 / 60 % 60);
                                var s = Math.floor(leftTime / 1000 % 60);
                                h = h < 10 ? '0' + h : h;
                                m = m < 10 ? '0' + m : m;
                                s = s < 10 ? '0' + s : s;
                                _html = '<span>' + d + '天' + h + ':' + m + ':' + s + '</span>';
                            }
                            $("#leftTime").html(_html);
                            leftTime = leftTime - 1000;
                        }, 1000);
                        window.indexSI.push(indexSI);

                    },
                    /**
                     * 主页信息回调
                     * @param {Object} options
                     * @param {Object} result
                     * @param {Object} textStatus
                     * @param {Object} jqXHR
                     */
                    indexRequest: function( result) {
                    	 loading.hide();
                        var that = this;
                        var cutoffdate, now_time, leftTime;
                        var topPicList, merchant, accUname, notice;
                        var picHTML = '';
                        if (result.r == 1 && result.data) {
                            topPicList = result.data.topPicList;
                            merchant = result.data.merchant;
                            accUname = result.data.account;
                            notice = result.data.notice;

                            //商户信息
                            if (merchant) {
                                //园区名
                                if (merchant.merchantname) {
                                    var merchantname = merchant.merchantname;
                                    $("#merchantname").html(merchantname);
                                }
                                //园区介绍
                                if (merchant.remarks) {
                                    var info = htmlDecode(merchant.remarks);
                                    info = htmlEncode(info);
                                    $("#remarks").html(info);
                                }
                            }
                            //宽带帐号
                            var html = '<div>客服热线：10000 </div>' + '<div>报障请提供宽带账号：' + (accUname || '') + '</div>';
                            $("#accUname").html(html);

                            //轮播图
                            if (topPicList && topPicList.length < 1) {
                                //无轮播图放默认图
                                picHTML += '<li class="carousel-ul-li" ><a href="javascript:void(0)">' +
                                    '<img class="carousel-img row-10" src="' + IMG_PATH + 'img/carousel/ad1.png"  ></a></li>';
                            } else {
                                for (var i = 0; i < topPicList.length; i++) {
                                    var img = topPicList[i].picpath || '';
                                    if (img != '') {
                                        img = UPLOAD_PATH + '' + img;
                                    }
                                    picHTML += '<li class="carousel-ul-li" ><a href="javascript:void(0)">' +
                                        '<img  class="carousel-img row-10" src="' + img + '"  /></a></li>';
                                }
                            }
                            //滚动播报
                            if (notice) {
                                var notices = eval("(" + notice + ")");
                                var noticeHtml = '<marquee direction=left>';
                                for (var i in notices) {
                                    var noticeTitleInfo = notices[i].noticeTitle || '';
                                    noticeTitleInfo = htmlDecode(noticeTitleInfo);
                                    noticeTitleInfo = htmlEncode(noticeTitleInfo);
                                    var noticeTitle = noticeTitleInfo;

                                    var noticeTextInfo = notices[i].noticeText || '';
                                    noticeTextInfo = htmlDecode(noticeTextInfo);
                                    noticeTextInfo = htmlEncode(noticeTextInfo);
                                    var noticeText = noticeTextInfo;
                                    noticeHtml += '&nbsp; &nbsp;&nbsp;<span>' + noticeTitle + '</span> &nbsp;<span>' + noticeText + '</span> ';
                                }
                                noticeHtml += '</marquee>';
                                $("#activeInfoLi").html(noticeHtml);
                            }
                        } else {
                            //返回不符合期望 展示默认页
                            picHTML = '<li class="carousel-ul-li" ><a href="javascript:void(0)">' +
                                '<img class="carousel-img row-10" src="' + IMG_PATH + 'img/carousel/ad1.png"  ></a></li>';
                        }

                        $("#carousel-ul").html(picHTML);

                        // 开启图片轮播
                        var width = document.body.offsetWidth;
                        var height = width * 9 / 16;
                        $(".carousel").show().yxMobileSlider({
                            height: height + 'px'
                        });
                        $(window).resize();
                    },
                    initUserCallBack: function( result) {

                    	   loading.hide();
                        console.log("initUserCallBack");
                        var that = this;
                        var cutoffdate, now_time, leftTime;


                        if (result.r == 1 && result.data &&afterLoginFlag) {

                            //获得剩余时长
                            if (result.data.user) {
                                console.log("已登录");
                                $("#linkMsg").text("已登录");

                               // $("#fastLogin").hide(); //一建登录隐藏
                               // $("#buydivwrap").show();
                                   this.setState({

                                                    fast_login_show:"none",
                                                    left_time_show:"block"
                                                    });
                                if (result.data.user_cutoff) {
                                    cutoffdate = result.data.user_cutoff.cutoffdate || 0;
                                } else {
                                    cutoffdate = 0;
                                }
                                now_time = result.data.now_date || 0;
                                //时长大于0
                                leftTime = (cutoffdate - now_time) > 0 ? (cutoffdate - now_time) : 0;
                                if (leftTime > 0 || result.data.isVipUser) {
                                    console.log("发现有时间");
                                    if (!afterLoginFlag) {
                                        //console.log("是刚登陆过");
                                        //imgLoad3();//如果没有登录进行登出操作
                                        /*var authData = {
                                            "merchantId": getCookieValue("MS_merchantId"),
                                            'deviceId': getCookieValue('MS_deviceId'),
                                            'apMac': getCookieValue('ap_mac'),
                                            'terMac': getCookieValue('MS_terMac'),
                                            'userMac': getCookieValue('MS_terMac'),
                                            'callback': "callback",
                                            'username': getCookieValue("MS_telephone"),
                                            'password': getCookieValue("MS_pcode")
                                        };
                                        console.log("开始认证");
                                        that.auther(authData);*/
                                    }else{
                                        console.log("不是刚登陆过，不触发联网");
                                    }
                                }




                                //是尊贵会员
                                if (result.data.isVipUser) {
                                    //                  if (true) {
                                    $("#buy").attr("disabled", "disabled");
                                    $("#buy").removeClass("back-orange");
                                    $("#buy").addClass("back-gray");
                                    $("#shalou").hide();
                                    $("#leftDiv").addClass("row-5");
                                    $("#buyDiv").addClass("row-5");
                                    $("#leftTime").html("<span style='font-size:1.7rem;font-weight:500'>电信尊贵客户</span>");
                                    $("#leftTimeTitle").html("<span  >无需购买时长</span>");
                                } else {
                                    //可否领包  vip无需领包
                                    var free_pkg = result.data.free_pkg_get || '';
                                    //free_pkg = 1;
                                    if (free_pkg == 'ok') {
                                        //$("#buy").hide();//现在分开了 不用一个隐藏一个显示
                                       // $("#getFreeBtn").show();
                                        //$("#getFreePkgWrap").show();
                                          this.setState({
                                                                                            get_free_pkg_show:"block",

                                                                                            });

                                        //return   ;  //此时return 无倒计时 无弹框  初次进入不会提示 比较符合交互
                                    }
                                    //已登录才给弹框
                                    if (result.data.user) {
                                        if (leftTime <= (60 * 60 * 1000)) {
                                            //基本没时间了 认为没时间
                                            if (leftTime <= 3000) {
                                                //that.noTime='noTime';
                                                $("#buy").attr("name", "noTime");
                                                var html = '<div class="row-9 center-text" >您已无上网时长</div>';
                                                Dialogue.modal(html, '25%', '200px', '').showModal();
                                            } else {
                                                var html = '<div class="row-9 center-text "  >您的上网时长已不足一小时</div>' +
                                                    '<div class="row-9 center-text " >请尽快购买时长</div>';
                                                Ajax.getJSONP(PATH+"/ms/sms/send", {
                                                    "mobile": getCookieValue("MS_telephone"),
                                                    "type": "ms_warn"
                                                }, function() {});
                                                Dialogue.modal(html, '25%', '200px', '').showModal();
                                            }
                                        }
                                        //倒计时
                                        var indexSI = setInterval(function() {
                                            var _html = '';
                                            if (leftTime <= 0) {
                                                if (window.indexSI.length) {
                                                    for (var i in window.indexSI) {
                                                        clearInterval(window.indexSI[i]);
                                                    }
                                                }
                                                _html = '<span>您暂时无法上网</span>';
                                                $("#leftTimeTitle").html("请购买上网时长");
                                            } else {
                                                $("#leftTimeTitle").html("剩余上网时间");
                                                var d = Math.floor(leftTime / 1000 / 60 / 60 / 24);
                                                var h = Math.floor(leftTime / 1000 / 60 / 60 % 24);
                                                var m = Math.floor(leftTime / 1000 / 60 % 60);
                                                var s = Math.floor(leftTime / 1000 % 60);
                                                h = h < 10 ? '0' + h : h;
                                                m = m < 10 ? '0' + m : m;
                                                s = s < 10 ? '0' + s : s;
                                                _html = '<span>' + d + '天' + h + ':' + m + ':' + s + '</span>';
                                            }
                                            $("#leftTime").html(_html);
                                            leftTime = leftTime - 1000;
                                        }, 1000);
                                        window.indexSI.push(indexSI);
                                    }

                                }

                            } else {

                            }

                            //用户权益===================================
                            //已登录


                        }else{
                            console.log("未登录");
                            //          //     弹窗
                            //      $("#sureMsg").on("click", function() {
                            //          $(".modal").hide();
                            //          $(".mask").hide();
                            //      });
                            var user_phone = getCookieValue("user_phone");
                            var login_type = getCookieValue("login_type");
                            console.log("user_phone:" + user_phone + "login_type:" + login_type);

                            if (login_type && login_type == "authed") {
                                console.log("authed");
                                var re = /^1\d{10}$/;

                                if (user_phone && re.test(user_phone)) {

                                    $("#fastLoginAccount").text(user_phone);
                                 //   $("#fastLogin").show();
                                   // $("#buydivwrap").hide();
                                      this.setState({//login_frame_show:"block",
                                                                                        get_free_pkg_show:"none",
                                                                                        fast_login_show:"block",
                                                                                        left_time_show:"none"
                                                                                        });
                                    $("#switch_account").click(function() {
                                        that.goLogin();
                                        /*mvc.routeRun("#/login");
                                        $("#MainContent").hide();
                                        $("#LoginWrap").show();*/
                                    })
                                    $("#fastLoginBtn").click(function() {
                                        /**
                                         * 登录系统
                                         */

                                        var self = $(this);
                                        var username = user_phone;

                                        //存储此次登录的用户名
                                        setCookie("MS_telephone", username, 24, "/");
                                        loading.show();
                                        //请求
                                        console.log("开始一键登录");
                                        Ajax.getJSONP( PATH + '/ms/login/save',
                                        		{
                                                'username': getCookieValue("MS_telephone"),
                                                'captcha': '1234',
                                                'deviceId': getCookieValue('MS_deviceId'),
                                                'apMac': getCookieValue('ap_mac'),
                                                'merchantId': getCookieValue("MS_merchantId"),
                                                'terMac': getCookieValue('MS_terMac'),
                                                'userMac': getCookieValue('MS_terMac'),
                                                'authed': true,
                                                'callback': "callback"
                                            },
                                          that.afterLogin.Apply(that)

                                       );

                                    })
                                }
                            } else {
                                $("#fastLogin").hide();
                                $("#buydivwrap").show();
                            }
                            //这里判断是否显示一建登录
                            console.log("未登陆");
                            console.log("获取用户信息失败/或者没有用户登录");
                        }
                    },
                    addEventListener: function() {
                        ///以下的代码在修改登录为路由跳转后基本无效可以删除
                        //tip("正在初始化");

                        //      去除定时器
                        if (window.indexSI.length) {
                            for (var i in window.indexSI) {
                                clearInterval(window.indexSI[i]);
                            }

                        }
                        //      if (window.getSuccSI) {
                        //          clearInterval(window.getSuccSI);
                        //      }

                        var that = this;
                        //4.X设备对象

                        $("#loginBtn").click(function() {
                            that.goLogin();

                        })
                        var devInfo = globalParam["dev_info"] || '{}';
                        devInfo = decodeURI(devInfo);
                        devInfo = eval("(" + devInfo + ")");

                        //设备数据存放Cookie
                        var host = globalParam["host"] || '';
                        if (host != '') {
                            setCookie("host", host, 24 * 99, "/");
                        }

                        var deviceId = globalParam["dev_id"] || '';
                        if (deviceId != '') {
                            setCookie("MS_deviceId", deviceId, 24 * 99, "/");
                        }

                        var gw_address = globalParam["gw_address"] || '';
                        if (gw_address != '') {
                            setCookie("gw_address", gw_address, 24 * 99, "/");
                        }

                        var gw_url = globalParam["url"] || '';
                        if (gw_url != '') {
                            setCookie("gw_url", gw_url, 24 * 99, "/");
                        }

                        var gw_port = globalParam["gw_port"] || '';
                        if (gw_port != '') {
                            setCookie("gw_port", gw_port, 24 * 99, "/");
                        }

                        var terMac = globalParam["user_mac"] || '';
                        if (terMac != '') {
                            setCookie("MS_terMac", terMac, 24 * 99, "/");
                        }
                        //only 4
                        //      var ap_mac = devInfo.mac || '';
                        //      if (ap_mac != '') {
                        //          setCookie("ap_mac", ap_mac, 24 * 99, "/");
                        //      }
                        //      var merchantId = devInfo.merId || '';
                        //      if (merchantId != '') {
                        //          setCookie("MS_merchantId", merchantId, 24 * 99, "/");
                        //      }

                        //兼容3 4的补丁
                        var apmacTHREE = globalParam["ap_mac"] || '';
                        var apmacFOUR = devInfo.mac || '';
                        var ap_mac = apmacFOUR != '' ? apmacFOUR : apmacTHREE;
                        if (ap_mac != '') {
                            setCookie("ap_mac", ap_mac, 24 * 99, "/");
                        }
                        var meridTHREE = globalParam["merchantId"] || '';
                        var meridFOUR = devInfo.merId || '';
                        var merchantId = meridFOUR != '' ? meridFOUR : meridTHREE;
                        if (merchantId != '') {
                            setCookie("MS_merchantId", merchantId, 24 * 99, "/");
                        }

                        var portal_type;
                        if (globalParam["portal_type"] & globalParam["portal_type"] != '') {
                            portal_type = globalParam["portal_type"];
                        } else {
                            portal_type = "authFatAP";
                        }
                        console.log("判断设备为:authFatAP");

                        if (portal_type != '') {
                            setCookie("portal_type", portal_type, 24 * 99, "/");
                        }

                        var ac_name = globalParam["ac_name"] || '';
                        if (ac_name != '') {
                            setCookie("ac_name", ac_name, 24 * 99, "/");
                        }
                        //////////////以上的代码可以删除
                        /**
                         * 免登录认证
                         */
                        if (afterLoginFlag) {
                            console.log("登录后跳转首页开始认证");
                            var authData = {
                                "merchantId": getCookieValue("MS_merchantId"),
                                'deviceId': getCookieValue('MS_deviceId'),
                                'apMac': getCookieValue('ap_mac'),
                                'terMac': getCookieValue('MS_terMac'),
                                'userMac': getCookieValue('MS_terMac'),
                                'callback': "callback",
                                'username': getCookieValue("MS_telephone"),
                                'password': getCookieValue("MS_pcode")
                            };

                            that.auther(authData);
                        }
                        //请求首页信息
                        that.index();

                        /**
                         * 购买按钮
                         */
                         this.addBuyEvent();

                        var roleType = getCookieValue('MS_roleType'); //用户权限 2为管理员 1为普通用户

                        /**
                         * 根据权限开启管理功能
                         */
                        if (roleType == 2) {
                            $("#mngPower").show();
                        }

                        /**
                         * 领取免费流量包
                         */
                        $("#getFreeBtn").on("click", function() {
                            loading.show();
                            Ajax.getJSONP(PATH + '/pkg/get', {},that.getFreeRequest.Apply(that));
                        });
                        /**
                         * 关闭园区动态
                         */
                        $("#closeActive").on("click", function() {
                            $("#activeInfo").parent().parent().hide();
                        });
                        console.log("addEventOfLogin");
                        this.addEventOfLogin();

                    },
                    addBuyEvent:function(){console.log("添加购买按钮事件:"+ $("#buy").length);

                      $("#buy").on('click',this.buyHandler.Apply(this));
                    },
                    buyHandler:function(){console.log("触发购买按钮事件");
                        var that =this;
                         Ajax.getJSONP(PATH+"/ms/pay/url/get", {},
                           that.getDqUrlRequest.Apply(that)
                        );
                    },
                    afterLogin: function(data) {
                    	loading.hide();
                        if (data.r == 1) {

                            //成功登陆
                            //隐藏一键登录
                            afterLoginFlag = true;
                            tip("登录成功");
                            console.log("登录成功");
                            this.goIndex();
                            //this.initUser();
                            this.initUserCallBack(data);
                            this.autherCallBack(data);

                        } else {
                            tip("登录失败");
                            console.log("登录失败");
                            alert(data.msg);
                        }
                    },
                    /**
                     * 主页信息
                     */
                    index: function() {
                        var that = this;
                        loading.show();
                        Ajax.getJSONP(PATH + '/ms/member/merchant/index/init',
                            {
                                "deviceId": getCookieValue('MS_deviceId'),
                                "merchantId": getCookieValue('MS_merchantId'),
                                "terMac": getCookieValue('MS_terMac'),

                            },
                            this.indexRequest.Apply(that)

                        );
                        this.initUser();
                    },
                    initUser: function() {
                        console.log("开始initUser");
 var that = this;
                        Ajax.getJSONP(PATH + '/ms/member/merchant/index/initUser',
                         {
                                                         "deviceId": getCookieValue('MS_deviceId'),
                                                        "merchantId": getCookieValue('MS_merchantId'),
                                                        "terMac": getCookieValue('MS_terMac'),
                                                                },
                                                                this.initUserCallBack.Apply(that)
                                                                );






                    },
                    /**
                     * 购买放通10分钟
                     */
                    buy: function(dqurl) {
                        var authData = {
                            "merchantId": getCookieValue("MS_merchantId"),
                            'deviceId': getCookieValue('MS_deviceId'),
                            'apMac': getCookieValue('ap_mac'),
                            'terMac': getCookieValue('MS_terMac'),
                            'userMac': getCookieValue('MS_terMac'),
                            'callback': "callback",
                            'username': getCookieValue("MS_telephone"),
                            'password': getCookieValue("MS_pcode"),
                            'paytype': 1 //放通10分钟标志
                        };
                        loading.show();
                        this.auther(authData, '1');
                    },
                    autherCallBack:function(data){
                        var that =this;
                        var paytype= data.data.paytype;
                        console.log("auther结果");
                        console.log(data);
                        console.log("请求认证：");
                        console.log(data);
                        tip("正在请求上网");
                        if (loading) {
                            loading.hide();
                        }
                        if (data.data && data.data.msg) {

                            console.log("认证报错:" + data.data.msg);
                            tip(data.data.msg);
                            if (data.data.msg == "已超过当日认证次数") {

                               // mvc.href("#/buyTran");
                                window.location="/statics/buyTran.html?dqUrl="+encodeURIComponent(globalParam["dqUrl"]);
                            }
                            return;
                        }

                        //                      var auth, dqurl;
                        //                      if (data.data) {
                        //                          dqurl = data.data.deviceurl || '';
                        //                          if (dqurl == '' || dqurl == 'error') {
                        //                              dqurl = getCookieValue('dqurl');
                        //                          } else {
                        //                              dqurl = dqurl + "&link_phone=" + data.data.deviceusername + "&code_number=" + data.data.deviceinfo + "&yxgh=" + data.data.devicemerid;
                        //                              setCookie("dqurl", dqurl, 24 * 99, "/");
                        //                          }
                        //                      }

                        var auth;
                        if (data.data && data.data.access_auth) {
                            var str = data.data.access_auth.auth || '';
                            if (str != '') {
                                str = str.substring(8, str.length);
                                auth = eval(str);
                            }
                            if (data.data.access_auth.kickoff && data.data.access_auth.kickoff == 1) { //提示其他用户被踢下线
                                console.log("之前的账号被踢下线");
                                var html = '<div class="row-9 center-text" >完成登录,其他设备的账号已经下线！</div>';
                                Dialogue.modal(html, '25%', '180px', function() {}).showModal();
                            }
                        } else {
                            console.log("即不是vip 也没有时长");
                        }
                        if(data.data.isVipUser){

                        }else
                        if (data.data.user_cutoff) {
                            that.leftTimeShow(data.data.user_cutoff);
                        }
                        if (auth && auth.resultCode == '0') {

                            //                      if (true) {
                            $("#linkMsg").text("已连网");

                            console.log("认证登陆成功:" + auth.resultCode);
                            //拿取后端给的token
                            var token = auth.data;
                            //                  测试
                            //                  var token = '';
                            var authUrl;
                            var host = encodeURIComponent((getCookieValue('host') || ''));
                            var dev_id = getCookieValue('MS_deviceId');
                            var gw_address = getCookieValue('gw_address');
                            var gw_url = encodeURIComponent((getCookieValue('gw_url') || ''));
                            var gw_port = getCookieValue('gw_port');
                            var user_mac = getCookieValue('MS_terMac');
                            var ap_mac = getCookieValue('ap_mac');
                            var portal_type = getCookieValue('portal_type');
                            var ac_name = getCookieValue('ac_name');
                            if (gw_port && gw_port != '') {
                                gw_address += ':' + gw_port;
                            }
                            if (portal_type == 'authFatAP') {

                                authUrl = 'http://' + gw_address + '/smartwifi/auth?url=' + gw_url + '&user_mac=' + user_mac + '&token=' + token + '&ac_name=' + ac_name;
                            } else {
                                authUrl = host + '?dev_id=' + dev_id + '&dev_mac=' + ap_mac + '&site_id=' + '1' + '&user_mac=' + user_mac + '&ac_name=' + ac_name;
                            }
                            console.log("请求" + authUrl);
                            $.ajax({
                                url: authUrl,
                                dataType: 'JSONP',
                                jsonp: 'callback',
                                async: false,
                                header: {
                                    'cache-control': 'no-cache'
                                },
                                success: function(data, textStatus, jqXHR) {
                                    tip("上网成功");
                                    console.log("请求sb上网成功");
                                    //$.getJSON("/ms/log/wlan");

                                    imgLoad2();

                                    console.log(data);
                                },
                                error: function(xhr, status, error) {
                                    console.log("请求sb上网失败");
                                    imgLoad2();
                                    console.log(xhr)
                                },
                                complete: function(xhr) {
                                    console.log("complete");

                                }
                            });

                            setTimeout(function() { //应该把paytype == '1' 的判断提到前面来
                                if (paytype == '1') {
                                    if (that.dqurl != '') {
                                        console.log(that.dqurl);
                                        //var url = encodeURIComponent(that.dqurl);
                                        //globalParam["dqUrl"]=that.dqurl;
                                     //   mvc.href("#/buyTran");
                                       // mvc.href("#/buyTran");
                                        window.location="/statics/buyTran.html?dqUrl="+encodeURIComponent(globalParam["dqUrl"]);

                                    } else {
                                        var html = '<div class="row-9 center-text" >支付服务失败，请重新登录！</div>';
                                        Dialogue.modal(html, '25%', '180px', function() {
                                            that.goLogin();
                                        }).showModal();
                                    }
                                }
                            }, 1000);

                        } else {
                            console.log("认证登陆失败:" );
                        }

                    },
                    auther: function(authData, paytype) {
                        afterLoginFlag = false;
                        tip("请稍等，正在网络放行");
                        console.log("请稍等，正在网络放行");
                        //触发登录
                        var that = this;
                        if (authData) { //其实可以不传authData
                            var merchantId = getCookieValue("MS_merchantId");
                            var deviceId = getCookieValue("MS_deviceId");
                            var apMac = getCookieValue("ap_mac");
                            var terMac = getCookieValue("terMac");

                            if (merchantId == '' || deviceId == '' || apMac == '' || terMac == '') {
                                alert("认证失败，请重新连接设备！");
                                return;
                            } else {
                                var authIndexUrl = '/ms/access/auth';
                                $.ajax({
                                    url: authIndexUrl,
                                    data: authData,
                                    dataType: 'JSON',
                                    success: this.autherCallBack.Apply(that),
                                    error: function(xhr, status, error) {}
                                });
                            }
                        }

                    },
                    login:function(){
                               var self = $(this);
                                 var that = this;
                               var deviceId = getCookieValue("MS_deviceId");
                               var terMac = getCookieValue("MS_terMac");
                               var merchantId = getCookieValue("MS_merchantId");
                               var $username = $('input[name=username]');
                               var $captcha = $('input[name=captcha]');
                               var $loginmsg = $(".errorMsg");
                               var cutdownTime = 60;

                                var username = $username.val() || '';
                                var captcha = $captcha.val() || '';
                                if (username == '' || captcha == '' || mobileStyle(username) || captchaStyle(captcha)) {
                                    $loginmsg.text('请输入正确的帐号或验证码');
                                    return false;
                                }
                                //存储此次登录的用户名
                                setCookie("MS_telephone", username, 24, "/");
                                loading.show();
                                //请求

                                Ajax.getJSONP( PATH + '/ms/login/save',
                                        {
                                        'username': username,
                                        'captcha': captcha,
                                        'deviceId': deviceId,
                                        'apMac': getCookieValue('ap_mac'),
                                        'merchantId': merchantId,
                                        'terMac': terMac,
                                        'userMac': terMac,
                                        'callback': "callback"
                                    },
                                     that.afterLogin.Apply(that)
                                );
                    },
                    captchaClick:function(){

                      var that = this;
                                            var deviceId = getCookieValue("MS_deviceId");
                                            var terMac = getCookieValue("MS_terMac");
                                            var merchantId = getCookieValue("MS_merchantId");
                                            var $username = $('input[name=username]');
                                            var $captcha = $('input[name=captcha]');
                                            var $loginmsg = $(".errorMsg");
                                            var cutdownTime = 60;
                        console.log("点击了获取验证码");
                        $(".errorMsg").text('');
                        var self = $(this);
                        if (self.attr("disabled")) {
                            return;
                        }
                        var username = $username.val() || '';
                        if (username == '' || mobileStyle(username)) {
                            $loginmsg.text("请输入正确的手机号");
                            return false;
                        }
                        self.prop('disabled', true);
                        self.text('发送中');
                        var time = cutdownTime;
                        var sI = setInterval(function() {
                            time = time - 1;
                            if (time !== 0) {
                                self.text(time + '秒后重试');
                            } else {
                                window.clearInterval(sI);
                                time = cutdownTime;
                                self.text('重新获取');
                                self.removeAttr("disabled");
                            }
                        }, 1000);
                        Ajax.getJSONP( PATH + '/ms/sms/send',
                             {
                                'mobile': username,
                                'type': 'login'
                                },

                           function(result) {
                                if (result.r != 1 && result.msg) {
                                    $(".errorMsg").text(result.msg)
                                }
                           });

                    },
                    addEventOfLogin: function() {

                        console.log("addEventOfLogin");
                        var that = this;
                        var deviceId = getCookieValue("MS_deviceId");
                        var terMac = getCookieValue("MS_terMac");
                        var merchantId = getCookieValue("MS_merchantId");
                        var $username = $('input[name=username]');
                        var $captcha = $('input[name=captcha]');
                        var $loginmsg = $(".errorMsg");
                        var cutdownTime = 60;
                        //自动填充手机号码
                        //if(!StringUtil.isBlank(getCookieValue("MS_telephone")))
                        //$username.val(getCookieValue("MS_telephone")|| getCookieValue("user_phone"));
                        /**
                         * 登录系统
                         */
                        // alert( $('#login').length);
                        $('#login').on('click', this.login.Apply(this));
                        /**
                         * 验证码
                         */
                        $('.captcha').find('button').on('click',this.captchaClick.Apply(this));
                        //Service
                        /**
                         * 用户输入去除错误信息
                         */
                        $username.on("change", function() {
                            $loginmsg.text('');
                        });
                        $captcha.on("change", function() {
                            $loginmsg.text('');
                        });
                        /**
                         * 验证手机号格式
                         */

                        /**
                         * 获取uri参数
                         */
                        function getParam(name) {
                            var search = document.location.search;
                            var pattern = new RegExp("[?&]" + name + "\=([^&]+)", "g");
                            var matcher = pattern.exec(search);
                            var items = null;
                            if (null != matcher) {
                                try {
                                    items = decodeURIComponent(decodeURIComponent(matcher[1]));
                                } catch (e) {
                                    try {
                                        items = decodeURIComponent(matcher[1]);
                                    } catch (e) {
                                        items = matcher[1];
                                    }
                                }
                            }
                            return items;
                        };
                    },
                    goIndex: function() {
                        $(".loginWrap").hide();
                        $(".indexWrap").show();
                    },
                    goLogin: function() {
                        /*mvc.href("#/login");*/

                      //  $(".indexWrap").hide();
                      //  $(".loginWrap").show();


		$(".indexWrap").hide();
		$(".loginWrap").show();
		var $username = $('input[name=username]');
		var $captcha = $('input[name=captcha]');
		$username.val("");
		$captcha.val("");
                   /* this.setState({login_frame_show:"block",
                    get_free_pkg_show:"none",
                    fast_login_show:"none",
                    left_time_show:"none"
                    });*/


                    },

    });

    // OtherReact 在这里定义

    /**
     * 制作页面时返回React对象
     * @returns {*}
     */
    function render() {
        return ReactDOM.render(<EntityReact />, document.getElementById(divId));
    }

    return {
        render: render
    }
};
