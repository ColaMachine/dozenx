
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
        this.geolocation=new qq.maps.Geolocation();//初始化经纬度api
        this.map =this. initTecentMap();//初始化地图
        this.infoWin = new qq.maps.InfoWindow({
			map: this.map
		});


        this.marker = [];
		this.prev = {
			time: null,
			LatLng: null
		}
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
    initTecentMap: function(){
       map = new qq.maps.Map(document.getElementById("container"), {
            // 地图的中心地理坐标。
            center: new qq.maps.LatLng(39.916527, 116.397128)
        });
        map.setZoom(this.zoomLevel);
        return  map;
    },
    //定位当前位置
    getTecentIpLocation:function(){
        log("getTecentIpLocation");
        this.geolocation.getIpLocation(this.showRufPosition.Apply(this), this.showErr);
    },
    getCurrentLocation:function(){
        //清空经纬度值
        this.userPos= null;
        log("begin getCurrentLocation");
        this.getRufLocation();
       this.getTecentCurLocation();

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
        this.getTecentIpLocation();
    },
    //获得精确位置
    getTecentCurLocation:function(){
        log("开始获得精确位置");
        var options = {timeout: 9000};

        this.geolocation.getLocation(this.showPosition.Apply(this), this.showErr.Apply(this), options);
    },
    //========地图以当前位置为中心呈现=========
     showPosition:function(position) {
        log("获得精确定位:",position);
        if(position  &&  position.lng && position.lat) {
             log("使用精确定位:",position);
            this.updateMapCenter(position.lat,position.lng);
            this.updateUserTecentPos(position.lat,position.lng);
        }else{
            log("精确位置无效");
        }

    },
    //==如果还没有获得精确的定位就用粗糙的先定位下==
    showRufPosition:function(position){//
        log("获得粗超定位:",position);
         if(!this.userPos  && position  &&  position.lng && position.lat) {
            log("使用粗超定位:",position);
            this.updateMapCenter(position.lat,position.lng);
            this.updateUserTecentPos(position.lat,position.lng);
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

        //this.getNearbyData();
    },
    //废弃了
    getCityLocationByIP:function  (callback){log("getTecentLocationByIP");

        var citylocation= null;

        citylocation = new qq.maps.CityService();
        //请求成功回调函数
        var that =this;
        citylocation.setComplete(function(result) {

            this.updateMapCenter(result.detail.latLng.lat,result.detail.latLng.lng);
             this.updateUserPos(result.detail.latLng.lat,result.detail.latLng.lng);
            //地图移动的时候触发
          //  callback.call(that);//获得经纬度后进行查询
        });
        //请求失败回调函数
        citylocation.setError(function() {
            alert("出错了，请输入正确的经纬度！！！");
        });
        citylocation.searchLocalCity();


    },
    //通过腾讯的接口ip定位
    getTecentLocationByIP:function  (callback){log("getTecentLocationByIP");

        var citylocation= null;

        citylocation = new qq.maps.CityService();
        //请求成功回调函数
        var that =this;
        citylocation.setComplete(function(result) {
           //

            log(result.detail.latLng,result.detail.latLng.lng);
            //callback(result.detail.latLng.lat);
           /* */
           that.map.setCenter(result.detail.latLng);
           that.map.setZoom(20);//设定放大级别
           that.userPos. x=result.detail.latLng.lng;
           that.userPos.y=result.detail.latLng.lat;
           that.centerPos. x=result.detail.latLng.lng;
           that.centerPos.y=result.detail.latLng.lat;
           callback.call(that);//获得经纬度后进行查询
        });
        //请求失败回调函数
        citylocation.setError(function() {
            alert("出错了，请输入正确的经纬度！！！");
        });
        citylocation.searchLocalCity();


    },
    // 请求附近热点
    getNearbyData:function(){
        var that = this;
        Ajax.getJSON(PATH+"/mapdata/nearby",{lng:this.centerPos.x,lat:this.centerPos.y,dist:5000,pageSize:50,curPage:1},function(result){
            //获得了数据之后展示到页面上
            var data = result.data;

            //log(1);
            that.displayAllMapDataOnTecentMap(data);
        });
    },
    //咱先所有的地图点
    displayAllMapDataOnTecentMap:function(latlngs){
      //清楚所有地图点
        this.clearOverlays(this.marker);
        var _this =this;

        for(var i = 0;i < latlngs.length; i++) {
            (function(n){
                _this.addTecentMaker(latlngs[n]);
                qq.maps.event.addListener(_this.marker[n], 'click', function() {
                    _this.showTecentInfoWin(latlngs[n]);
                });
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
		// 定位拖动后的中心
		qq.maps.event.addListener(_this.map, 'center_changed', function() {
			// 距离和时间的判断
			var center = _this.map.getCenter();
			_this.centerPos.x= center.lng;
			_this.centerPos.y = center.lat;

			if(_this.needUpdate(center)){
			    _this.getNearbyData(center.lng ,center.lat);
			 }
		});
		// 点击地图，取消选中的标记
		qq.maps.event.addListener(this.map, 'click', function() {
		    //点击后出现简介 和地图标记
			//_this.setState({hotarea: {}, show: false});

		//	_this.infoWin.close();
		    _this.hideInfoWin();//隐藏地图标记旁边的备注
		  //  this.hideMapTags();
		    _this.hideDialogue();
		});
		// 缩放处理
		qq.maps.event.addListener(this.map,'zoom_changed',function() {
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
		while (overlay = overlays.pop()) {
			overlay.setMap(null);
		}
    },
    addTecentMaker:function(data){
		var position = new qq.maps.LatLng(data.lat,data.lng);
		var marker = new qq.maps.Marker({
			position: position,
			map: this.map
		});
		this.marker.push(marker);
	},
	showTecentInfoWin:function(data){
		var position = new qq.maps.LatLng(data.lat,data.lng);
		this.infoWin.open();
		this.infoWin.setContent("<div style='padding: 5px;'>" + data.name + "</div>");
		this.infoWin.setPosition(position);
        this.showDialogue(data);
		//this.setState({hotarea: data, show: true});
	},
	showDialogue:function(data){
        document.getElementById("mapDialogue").style.display="block";
        document.getElementById("mapDialogueName").innerText=data.name;
        document.getElementById("mapDialogueAddress").innerText=data.address;
        document.getElementById("mapDialogueContent").innerText=data.content;
        document.getElementById("mapDialogueDist").innerText=//this.centerPos
        this.LantitudeLongitudeDist(data,this.userPos)+"米";


	},
	hideDialogue:function(data){
        document.getElementById("mapDialogue").style.display="none";
	},
    getPos:function (lat,lng){
        return {lat:lat,lng:lng};
    },
     //更新用户的位置
    updateUserTecentPos:function(lat,lng){
    	this.userPos= this.getPos(lat,lng);
		if(this.locateMaker)
		    this.locateMaker.setMap(null);//清楚红点
        var anchor = new qq.maps.Point(6, 6),
        size = new qq.maps.Size(24, 24),
        origin = new qq.maps.Point(0, 0),
        icon = new qq.maps.MarkerImage('/static/img/map/center.gif', size, origin, anchor);
	    this.locateMaker = new qq.maps.Marker({
	        icon: icon,
	        map: this.map,
	        position:new qq.maps.LatLng( this.userPos.lat,this.userPos.lng)});
	},
	//更新地图的中心点
	updateMapCenter :function (lat,lng) {
		this.centerPos= this.getPos(lat,lng);//更新中心坐标
        this.map.setCenter(new qq.maps.LatLng( this.centerPos.lat,this.centerPos.lng));//地图中心变换
    }

}

function log(str){
    console.log(str);
}