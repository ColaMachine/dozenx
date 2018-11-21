
var MyMap = {
    infoWin:null,
    locationType:"iframe",
     geolocation: null,
    prev:{time:null,
        LatLng:null,
    },
    map:null,
    marker:[],
    prev:null,
    zoomLevel:17,
      //是否已经iframe增加了消息接收程序
    hasIframeAdEvt:false,
    loc:null,//iframe 获取经纬度 标志位如果6s后没有值就获取粗糙的经纬度
    userPos:{//当前人的定位经纬度
        x:0,
        y:0,
        },
    centerPos:{//当前地图的中心点
        x:0,
        y:0,
        },

    //初始化
    init:function(){
        this.geolocation=new BMap.Geolocation();;//初始化经纬度api
        var _this =this;
        var opts = {
              width : 200,     // 信息窗口宽度
              height: 100,     // 信息窗口高度
              title : "海底捞王府井店" , // 信息窗口标题
              enableMessage:true,//设置允许信息窗发送短息
              message:"亲耐滴，晚上一起吃个饭吧？戳下面的链接看下地址喔~"
            }
        this.map =this. initBaiduMap();//初始化地图
        this.infoWin =  new BMap.InfoWindow("地址：北京市东城区王府井大街88号乐天银泰百货八层", opts
        );


        this.marker = [];
		this.prev = {
			time: null,
			LatLng: null
		}
//
//$("#placeBtn").click(function(){
//    _this.canPlaceMarker=true;
//    $("#placeBtn").css("backgroundColor","blue");
//});
//		var overlaycomplete = function(e){
//                _this.marker.push(e.overlay);
//                lat=e.overlay.point.lat;
//                lng=e.overlay.point.lng;
//                _this.showAddBaiduInfoWin({name:"开始添加信息","lat":lat,"lng":lng,"marker":e.overlay});
//            };
//            var styleOptions = {
//                strokeColor:"red",    //边线颜色。
//                fillColor:"red",      //填充颜色。当参数为空时，圆形将没有填充效果。
//                strokeWeight: 3,       //边线的宽度，以像素为单位。
//                strokeOpacity: 0.8,	   //边线透明度，取值范围0 - 1。
//                fillOpacity: 0.6,      //填充的透明度，取值范围0 - 1。
//                strokeStyle: 'solid' //边线的样式，solid或dashed。
//            }
//            //实例化鼠标绘制工具
//            var drawingManager = new BMapLib.DrawingManager(map, {
//                isOpen: false, //是否开启绘制模式
//                enableDrawingTool: true, //是否显示工具栏
//                drawingToolOptions: {
//                    anchor: BMAP_ANCHOR_TOP_RIGHT, //位置
//                    offset: new BMap.Size(5, 5), //偏离值
//                },
//                circleOptions: styleOptions, //圆的样式
//                polylineOptions: styleOptions, //线的样式
//                polygonOptions: styleOptions, //多边形的样式
//                rectangleOptions: styleOptions //矩形的样式
//            });
//        	 //添加鼠标绘制工具监听事件，用于获取绘制结果
//            drawingManager.addEventListener('overlaycomplete', overlaycomplete);
//            function clearAll() {
//        		for(var i = 0; i <  _this.marker.length; i++){
//                    map.removeOverlay(_this.marker[i]);
//                }
//                overlays.length = 0
//            }
    },
    //加载后触发
    componentDidMount:function(){
        this.init();
        this.evtset();


//        var params = this.props.location.state;
//		if(!params || !params.userPos) {//如果什么都没有 说明是第一次进入
			this.getCurrentLocation();
//		}
//		else {//根据上次 显示地图  的用户定位 和中心 还有数据点
//			log("params",params);
//
//
//			//this.map.setCenter(this.state.center );
//			this.updateMapCenter(params.ypos,params.xpos);
//
//			if(params.userPos){
//				this.updateUserPos(params.userPos.lat,params.userPos.lng);
//			}
//
//			this.setMaker(params);
//
//			this.hotareaInfoWin(params);
//			//this.map.setZoom(20);
//		}
    },
    //初始化腾讯地图
    initBaiduMap: function(){
       map =  new BMap.Map("container");

         // 创建地图实例
           var point = new BMap.Point(116.404, 39.915);

           // 创建点坐标
           map.centerAndZoom(point, 15);

        map.setZoom(this.zoomLevel);
        return  map;
    },
    //定位当前位置
    getBaiduIpLocation:function(){
     log("getBaiduIpLocation");
        var _this =this;
        function myFun(result){
        		var cityName = result.name;
        		_this.map.setCenter(cityName);
        	//	alert("当前定位城市:"+cityName);
        	}
        	var myCity = new BMap.LocalCity();
        	myCity.get(myFun);


       /// this.geolocation.getBaiduIpLocation(this.showRufPosition.Apply(this), this.showErr);
    },
    getCurrentLocation:function(){
        //清空经纬度值
        this.userPos= null;
        log("begin getCurrentLocation");
      this.getRufLocation();
        this.getBaiduCurLocation();

       //this. getLocationByIframe();
        // this.getTecentLocationByIP(this.getLocationCallBack);
    },

    //更新地图的中心点
    /*updateMapCenter:function(lng,lat){
        this.userPos. x=lng;
        this.userPos.y=lat;
        this.centerPos. x=lng;
        this.centerPos.y=lat;
        this.map.setCenter(new qq.maps.LatLng( this.centerPos.y,this.centerPos. x));
        this.map.setZoom(17);
    },*/
    //通过腾讯的iframe方法2进行定位 缺点耗时比较长
    getLocationByIframe:function(){
        var _this =this;

        if(!this.hasIframeAdEvt){//没有注册过监听就要注册一下
            window.addEventListener('message', function(event) {//获取经纬度后的回调函数
                if(event.data &&event.data.module == 'geolocation' ){ //定位成功,防止其他应用也会向该页面post信息，需判断module是否为'geolocation'
                        _this.showPosition(event.data);
                }else { //定位组件在定位失败后，也会触发message, event.data为null
                        log('iframe 定位失败');
                }
            }, false);
            _this.hasIframeAdEvt=true;
        }
        //为防止定位组件在message事件监听前已经触发定位成功事件，在此处显示请求一次位置信息
        document.getElementById("geoPage").contentWindow.postMessage('getLocation', '*');

        //设置6s超时，防止定位组件长时间获取位置信息未响应
       /* setTimeout(function() {
            if(!_this.loc) {
                //主动与前端定位组件通信（可选），获取粗糙的IP定位结果
            document.getElementById("geoPage")
                .contentWindow.postMessage('getLocation.robust', '*');
            }
        }, 6000); //6s为推荐值，业务调用方可根据自己的需求设置改时间，不建议太短
        */
    },
    //获得粗糙经纬度
    getRufLocation:function(){
        this.getBaiduIpLocation();
    },
    //获得精确位置
    getBaiduCurLocation:function(){
        log("开始获得精确位置");
        var options = {enableHighAccuracy: true,timeout: 9000};
       // this.geolocation.getCurrentPosition(this.showPosition.Apply(this), options);

       var _this = this;
 this.geolocation.getCurrentPosition(function(r) {
                                                 if(this.getStatus() == BMAP_STATUS_SUCCESS){

                                          			  log("使用精确定位:");
                                          			  console.log(r.point);
                                                                _this.updateMapCenter(r.point.lat,r.point.lng);
                                                               _this.updateUserBaiduPos(r.point.lat,r.point.lng);
                                          		}
                                          		else {
                                          		 log("精确位置无效");
                                          			alert('failed'+this.getStatus());
                                          		}



                                         }, options);

    },
    //========地图以当前位置为中心呈现=========
     showPosition:function(r) {

            if(this.getStatus() == BMAP_STATUS_SUCCESS){
     			//var mk = new BMap.Marker(r.point);
     			//map.addOverlay(mk);
     			//map.panTo(r.point);
     			//alert('您的位置：'+r.point.lng+','+r.point.lat);

     			  log("使用精确定位1:");
     			 console.log(r.point);
                           this.updateMapCenter(r.point.lat,r.point.lng);
                          this.updateUserBaiduPos(r.point.lat,r.point.lng);
     		}
     		else {
     		 log("精确位置无效");
     			alert('failed'+this.getStatus());
     		}

//        log("获得精确定位:",position);
//        if(position  &&  position.lng && position.lat) {
//             log("使用精确定位:",position);
//            this.updateMapCenter(position.lat,position.lng);
//            this.updateUserBaiduPos(position.lat,position.lng);
//        }else{
//            log("精确位置无效");
//        }

    },
    //==如果还没有获得精确的定位就用粗糙的先定位下==
    showRufPosition:function(position){//
        log("获得粗超定位:",position);
         if(!this.userPos  && position  &&  position.lng && position.lat) {
            log("使用粗超定位:",position);
            this.updateMapCenter(position.lat,position.lng);
            this.updateUserBaiduPos(position.lat,position.lng);
        }else{
            log("弃用粗糙位置");
        }
    },
    //错误提示
     showErr:function() {
        //positionNum ++;
        log("showErr 定位失败");

    },
    //获取地理位置后的回调 废弃------------------------------------------
    getLocationCallBack:function(){//log("getLocationCallBack");
       // this.userPos. x=result.detail.latLng.lat;
        //this.userPos.y=result.detail.latLng.lng;
        //this.map.setCenter(result.detail.latLng);

       // this.map.setCenter(new qq.maps.LatLng( this.centerPos.y,this.centerPos. x))


        //查找附近数据

       this.getNearbyData();
    },


    // 请求附近热点
    getNearbyData:function(){
        var that = this;
        Ajax.getJSON(PATH+"/mapdata/nearby",{lng:this.centerPos.x,lat:this.centerPos.y,dist:5000,pageSize:50,curPage:1},function(result){
            //获得了数据之后展示到页面上
            var data = result.data;

            //log(1);
            that.displayAllMapDataOnBaiduMap(data);
        });
    },
    //咱先所有的地图点
    displayAllMapDataOnBaiduMap:function(latlngs){
      //清楚所有地图点
        this.clearOverlays(this.marker);
        var _this =this;

        for(var i = 0;i < latlngs.length; i++) {
            (function(n){
                var marker = _this.addBaiduMaker(latlngs[n]);

                _this.marker[n].addEventListener("click", function(e){
                    //e.stopPropagation();
                	//var p = e.target;
                	//	var point = new BMap.Point(p.getPosition().lng, p.getPosition().lat);

                	//	_this.map.openInfoWindow(_this.infoWindow,point); //开启信息窗口
                	latlngs[n].marker=e.target;
                		 _this.showBaiduInfoWin(latlngs[n]);//我们的信息窗口
                	});

                	_this.marker[n].addEventListener("touchstart", function(e){
                      //  e.stopPropagation();
                    //var p = e.target;
                    //	var point = new BMap.Point(p.getPosition().lng, p.getPosition().lat);

                    //	_this.map.openInfoWindow(_this.infoWindow,point); //开启信息窗口
                        latlngs[n].marker=e.target;
                         _this.showBaiduInfoWin(latlngs[n]);//我们的信息窗口
                    });
               /* qq.maps.event.addListener(_this.marker[n], 'click', function() {

                });*/
            })(i);
        }
    },

    // 转化为弧度
	rad:function(d) {
	   return d * Math.PI / 180.0;
	},
	// 通过经纬度算距离，单位：米
	LantitudeLongitudeDist:function(center1, center2) {
		var EARTH_RADIUS = 6378137;//赤道半径(单位m)
		var radLat1 = this.rad(center1.lat);
		var radLat2 = this.rad(center2.lat);

		var radLon1 = this.rad(center1.lng);
		var radLon2 = this.rad(center2.lng);

		if (radLat1 < 0)
			radLat1 = Math.PI / 2 + Math.abs(radLat1);// south
		if (radLat1 > 0)
			radLat1 = Math.PI / 2 - Math.abs(radLat1);// north
		if (radLon1 < 0)
			radLon1 = Math.PI * 2 - Math.abs(radLon1);// west
		if (radLat2 < 0)
			radLat2 = Math.PI / 2 + Math.abs(radLat2);// south
		if (radLat2 > 0)
			radLat2 = Math.PI / 2 - Math.abs(radLat2);// north
		if (radLon2 < 0)
			radLon2 = Math.PI * 2 - Math.abs(radLon2);// west
		var x1 = EARTH_RADIUS * Math.cos(radLon1) * Math.sin(radLat1);
		var y1 = EARTH_RADIUS * Math.sin(radLon1) * Math.sin(radLat1);
		var z1 = EARTH_RADIUS * Math.cos(radLat1);

		var x2 = EARTH_RADIUS * Math.cos(radLon2) * Math.sin(radLat2);
		var y2 = EARTH_RADIUS * Math.sin(radLon2) * Math.sin(radLat2);
		var z2 = EARTH_RADIUS * Math.cos(radLat2);

		var d = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2)+ (z1 - z2) * (z1 - z2));
		//余弦定理求夹角
		var theta = Math.acos((EARTH_RADIUS * EARTH_RADIUS + EARTH_RADIUS * EARTH_RADIUS - d * d) / (2 * EARTH_RADIUS * EARTH_RADIUS));
		var dist = theta * EARTH_RADIUS;
		return parseInt(dist);
	} ,
    needUpdate:function(center){
		// 如果是初始化中心 || 距离上一次请求的(移动时间>1000ms || 移动距离>500m)
		if(!this.prev.time || !this.prev.LatLng ||
			(this.prev.time && new Date().getTime() - this.prev.time > 1000) ||
			(this.prev.LatLng && this.LantitudeLongitudeDist(this.prev.LatLng, center) > 500)){
			this.prev.LatLng = center;
			this.prev.time = new Date().getTime();
			return true;
		}
		return false;
	},
    // 事件绑定
    evtset:function(){
		var _this = this;

		document.getElementById("saveBtn").addEventListener("click",function(){
		    _this.submitData();
		});

			document.getElementById("addCancelBtn").addEventListener("click",function(){
        		    _this.cancelData();
        		});
        			document.getElementById("viewCancelBtn").addEventListener("click",function(){
                		    _this.cancelData();
                		});
		// 定位拖动后的中心
		this.map.addEventListener( 'dragend', function() {
			// 距离和时间的判断
			var center = _this.map.getCenter();
			_this.centerPos.x= center.lng;
			_this.centerPos.y = center.lat;

			if(_this.needUpdate(center)){
			    _this.getNearbyData(center.lng ,center.lat);
			 }
		});
		// 点击地图，取消选中的标记
		this.map.addEventListener( 'touchstart', function(e) {
            if(!_this.canPlaceMarker){
            _this.hideInfoWin();//隐藏地图标记旁边的备注
              _this.hideDialogue();
            return ;
            }
		//$("#placeBtn").click(function(){
            _this.canPlaceMarker=false;
            $("#placeBtn").css("backgroundColor","white");
        //});
		   // alert(e.point.lng +", "+ e.point.lat);
            var marker = _this.addBaiduMaker({lat:e.point.lat,lng:e.point.lng});
 _this.showAddBaiduInfoWin({name:"开始添加信息","lat":e.point.lat,"lng":e.point.lng,"marker":marker});
		    //点击后出现简介 和地图标记
			//_this.setState({hotarea: {}, show: false});

		//	_this.infoWin.close();

		  //  this.hideMapTags();

		});

			this.map.addEventListener( 'click', function(e) {
                    if(!_this.canPlaceMarker){
                         //_this.hideInfoWin();//隐藏地图标记旁边的备注
                              //    _this.hideDialogue();
                    return ;
                    }
        		//$("#placeBtn").click(function(){
                    _this.canPlaceMarker=false;
                    $("#placeBtn").css("backgroundColor","white");
                //});
        		   // alert(e.point.lng +", "+ e.point.lat);
                    var marker = _this.addBaiduMaker({lat:e.point.lat,lng:e.point.lng});
                    _this.showAddBaiduInfoWin({name:"开始添加信息","lat":e.point.lat,"lng":e.point.lng,"marker":marker});
        		    //点击后出现简介 和地图标记
        			//_this.setState({hotarea: {}, show: false});

        		//	_this.infoWin.close();
        		   // _this.hideInfoWin();//隐藏地图标记旁边的备注
        		  //  this.hideMapTags();
        		   // _this.hideDialogue();
        		});
		// 缩放处理
		this.map.addEventListener(this.map,'zoomend',function() {
			var zoomLevel = _this.map.getZoom();
			// 市级及以上，不显示标记
			_this.marker.map(function(maker, i){
				maker.setVisible(zoomLevel < 9 ? false : true);
			})
		});
        var locationEle = document.getElementById("locate");
		locationEle.addEventListener("click",function(){//定位当前位置按钮
		    _this.getCurrentLocation();
		});
	},
	hideMapTags:function(){

	},
	hideInfoWin:function(){
	    this.infoWin.close();//腾讯地图的点备注
	},
	// 清除地图上的点/标记
    clearOverlays:function(overlays){//
        var overlay;
        this.map.clearOverlays();
		while (overlay = overlays.pop()) {
			//overlay.setMap(null);
		}
    },
    addBaiduMaker:function(data){
		var point =new BMap.Point(data.lng, data.lat); //将标注点转化成地图上的点
         var marker = new BMap.Marker(point); //将点转化成标注点
        this. map.addOverlay(marker);  //将标注点添加到地图上
        this.marker.push(marker);
        return marker;
	},
	//showBaiduInfoWin
	showBaiduInfoWin:function(data){
	    data.marker.openInfoWindow(	this.infoWin);
		//var position = new BMap.Point(data.lng,data.lat);//TODO 不确定
		//this.infoWin.open();
		this.infoWin.setTitle(data.name);
		this.infoWin.setContent("<div style='padding: 5px;'>" + data.content + "</div>");
		//this.infoWin.setPosition(position);
        this.showDialogue(data);
		//this.setState({hotarea: data, show: true});
	},
	showAddBaiduInfoWin:function(data){
    	    data.marker.openInfoWindow(	this.infoWin);
    		//var position = new BMap.Point(data.lng,data.lat);//TODO 不确定
    		//this.infoWin.open();
    		var sContent =
            	"<button>开始发布求助信息</button>";

    		this.infoWin.setContent(sContent);  // 创建信息窗口对象
    		this.infoWin.setTitle("");
    		//this.infoWin.setPosition(position);
            this.showAddDialogue(data);
    		//this.setState({hotarea: data, show: true});
    	},
	showDialogue:function(data){
	 document.getElementById("mapAddDialogue").style.display="none";
        document.getElementById("mapDialogue").style.display="block";
        document.getElementById("mapDialogueName").innerText=data.name;
        document.getElementById("mapDialoguePic").src=PATH+data.img;
        document.getElementById("mapDialogueTime").innerText=data.updatetime;
        document.getElementById("mapDialogueContent").innerText=data.content;
        document.getElementById("mapDialogueDist").innerText=//this.centerPos
        this.LantitudeLongitudeDist(data,this.userPos)+"米";
	},
	showAddDialogue:function(data){
	 document.getElementById("mapDialogue").style.display="none";
            document.getElementById("mapAddDialogue").style.display="block";

             document.getElementById("mapAddLat").value=data.lat;
              document.getElementById("mapAddLng").value=data.lng;
          //  document.getElementById("mapAddDialogueName").innerText=data.name;
          //  document.getElementById("mapAddDialogueAddress").innerText=data.address;
           // document.getElementById("mapAddDialogueContent").innerText=data.content;
          //  document.getElementById("mapAddDialogueDist").innerText=//this.centerPos
          //  this.LantitudeLongitudeDist(data,this.userPos)+"米";
    	},
	hideDialogue:function(data){
        document.getElementById("mapDialogue").style.display="none";
	},
    getPos:function (lat,lng){
        return {lat:lat,lng:lng};
    },
     //更新用户的位置
    updateUserBaiduPos:function(lat,lng){
    	this.userPos= this.getPos(lat,lng);
		if(this.locateMaker)
		    this.locateMaker.setMap(null);//清楚红点


        var anchor = new BMap.Point(this.userPos.lng, this.userPos.lat),
        size = new BMap.Size(24, 24),
       // origin = new qq.maps.Point(0, 0),
        icon = new BMap.Icon('../../../static/img/map/center.gif', size);
	    this.locateMaker =BMap.Marker(anchor,{
	        icon: icon,
	     });
	},
	//更新地图的中心点
	updateMapCenter :function (lat,lng) {
		this.centerPos= this.getPos(lat,lng);//更新中心坐标
        this.map.panTo(new BMap.Point(this.centerPos.lng, this.centerPos.lat));//地图中心变换
    },

     cancelData:function(){//alert(0);
           document.getElementById("mapDialogue").style.display="none";
                document.getElementById("mapAddDialogue").style.display="none";

            },

       submitData : function (){
       var _this =this;
            var name =$("#name").val();
            var content =$("#content").val();
            var lat =$("#mapAddLat").val();
              var lng =$("#mapAddLng").val();
              var img = $("#file").val();
            Ajax.post(PATH+"/mapdata/add",{"name":name,"content":content,"lat":lat,"lng":lng,"img":img},function(data){
                if(data.r==0){
                    alert("提交成功");
                }
                _this.getNearbyData();

                document.getElementById("mapDialogue").style.display="none";
                document.getElementById("mapAddDialogue").style.display="none";
                }
            );
        }

}

function log(str){
    console.log(str);
}