
function zImageUtil(config) {
	var o = {
		imgDom: null, //回显的image的id
		maxHeight: null, //预设的最大高度
		maxWidth: null, //预设的最大宽度
		postUrl: null, //提交的url"/calendar/image/upload.json"
		preShow: true,
		callback: null,
        fileInput:null,
		fileChange: function(e) {
			var f = e.files[0]; //一次只上传1个文件，其实可以上传多个的
			var FR = new FileReader();
			var _this = this;

			FR.onload = function(f) {

				_this.compressImg(this.result, 300, function(data) { //压缩完成后执行的callback
					console.log("压缩完成后执行的callback");
					//document.getElementById('imgData').value = data;//写到form元素待提交服务器
					//document.getElementById('myImg').src = data;//压缩结果验证
					if (_this.preShow) {
						console.log("img pre show");
						$(_this.imgDom).attr("src", data);

						//console.log(_this.imgDom);

					}
					console.log("begin send img");
					var json = {};
					// json.imageName= "myImage.png";
					data ="+"+ data;//.substring(22);
					//alert(data.substring(0,100));
					// alert(data)
					json.imageData = encodeURIComponent(data);
					console.log("begin post");

					Ajax.post(_this.postUrl,
						json,
						function(data) {console.log("ajax return");
							if (data.r == AJAX_SUCC) {console.log("succ");
								$(_this.imgDom).attr("src", PATH+"/" + data.data);
								//$(_this).parent().find("#picurl")
								console.log("imgUrl:" + data.data);
							} else {
							    console.log("fail");

								//	                        		zalert(data.msg);
							}
							if (_this.callback != null)
								_this.callback(data);
						}
					);
				});
			};
			FR.readAsDataURL(f); //先注册onload，再读取文件内容，否则读取内容是空的
		},
		compressImg: function(imgData, maxHeight, onCompress) {
			var _this = this;
			if (!imgData)
				return;
			onCompress = onCompress || function() {};
			maxHeight = maxHeight || this.maxHeight; //默认最大高度200px
			var canvas = document.createElement('canvas');
			var img = new Image();
			console.log("maxHeight:" + maxHeight);
			img.onload = function() {
				if (img.height > maxHeight) { //按最大高度等比缩放
					img.width *= maxHeight / img.height;
					img.height = maxHeight;
				}
				canvas.width = img.width;
				canvas.height = img.height;
				var ctx = canvas.getContext("2d");

				ctx.clearRect(0, 0, canvas.width, canvas.height); // canvas清屏

				//重置canvans宽高 canvas.width = img.width; canvas.height = img.height;
				console.log("width:" + img.width + "height:" + img.height);
				ctx.drawImage(img, 0, 0, img.width, img.height); // 将图像绘制到canvas上
				console.log("begin compress img");
				onCompress(canvas.toDataURL("image/png")); //必须等压缩完才读取canvas值，否则canvas内容是黑帆布
			};
			// 记住必须先绑定事件，才能设置src属性，否则img没内容可以画到canvas
			console.log("begin origin data load:");
			img.src = imgData;

		},
		init: function(jso) {
			this.imgDom = jso.imgDom;
			this.maxHeight = jso.maxHeight;
			this.maxWidth = jso.maxWidth;
			this.postUrl = jso.postUrl;
			this.callback = jso.callback;
			this.fileInput=jso.fileInput;
            var that =this;
			 $(that.fileInput).change(function(){

              //  console.log("imgDom:"+nowImg);
                //var imageUtil= new zImageUtil({imgDom:nowImg,postUrl:"/calendar/image/upload.json",maxWidth:633,maxHeight:300});
                that.fileChange(this);
            });
            $(this.imgDom).click(function(){


                $(that.fileInput).trigger("click");
            });
		}
	};
	o.init(config);
	return o;
}


function zImageUtil2(config) {
	var o = {
		imgDom: null, //回显的image的id
		maxHeight: null, //预设的最大高度
		maxWidth: null, //预设的最大宽度
		postUrl: null, //提交的url"/calendar/image/upload.json"
		preShow: false,
		callback: null,
        fileInput:null,
		fileChange: function(e) {
			var f = e.files[0]; //一次只上传1个文件，其实可以上传多个的
			var FR = new FileReader();
			var _this = this;

			FR.onload = function(f) {

				_this.compressImg(this.result, 300, function(data) { //压缩完成后执行的callback
					console.log("压缩完成后执行的callback");
					//document.getElementById('imgData').value = data;//写到form元素待提交服务器
					//document.getElementById('myImg').src = data;//压缩结果验证
					if (_this.preShow) {
						console.log("img pre show");
						$(_this.imgDom).attr("src",data);

						//console.log(_this.imgDom);

					}
					console.log("begin send img");
					var json = {};
					// json.imageName= "myImage.png";
					data ="+"+ data;//.substring(22);
					//alert(data.substring(0,100));
					// alert(data)
					json.imageData = encodeURIComponent(data);
					console.log("begin post");
                  // dialog.showWait();
                   //alert(_this.postUrl);
					Ajax.post(_this.postUrl,
						json,
						function(data) {
						 dialog.hideWait();

							if (data.r == AJAX_SUCC) {
/*
                                console.log(data.r+":"+AJAX_SUCC);
                                console.log(data);
                                console.log(_this.imgDom);
                                console.log("hello:"+PATH+"/" + data.data);*/
								$(_this.imgDom).attr("src", PATH+"/" + data.data);
							/*	$(_this).parent().find("#picurl");
								console.log("imgUrl:" + data.data);*/
							} else {
								//	                        		zalert(data.msg);
							}
							if (_this.callback != null)
								_this.callback(data);
						}
					);
				});
			};
			FR.readAsDataURL(f); //先注册onload，再读取文件内容，否则读取内容是空的
		},
		compressImg: function(imgData, maxHeight, onCompress) {
			var _this = this;
			if (!imgData)
				return;
			onCompress = onCompress || function() {};
			maxHeight = maxHeight || this.maxHeight; //默认最大高度200px
			var canvas = document.createElement('canvas');
			var img = new Image();
			console.log("maxHeight:" + maxHeight);
			img.onload = function() {
				if (img.height > maxHeight) { //按最大高度等比缩放
					img.width *= maxHeight / img.height;
					img.height = maxHeight;
				}
				canvas.width = img.width;
				canvas.height = img.height;
				var ctx = canvas.getContext("2d");

				ctx.clearRect(0, 0, canvas.width, canvas.height); // canvas清屏

				//重置canvans宽高 canvas.width = img.width; canvas.height = img.height;
				console.log("width:" + img.width + "height:" + img.height);
				ctx.drawImage(img, 0, 0, img.width, img.height); // 将图像绘制到canvas上
				console.log("begin compress img");
				onCompress(canvas.toDataURL("image/png")); //必须等压缩完才读取canvas值，否则canvas内容是黑帆布
			};
			// 记住必须先绑定事件，才能设置src属性，否则img没内容可以画到canvas
			console.log("begin origin data load:");
			img.src = imgData;

		},
		init: function(jso) {
			this.imgDom = $("<img class=\" img-upload\" alt=\"请上传图片\"></img>");
			this.maxHeight = jso.maxHeight||633;
			this.maxWidth = jso.maxWidth||300;
			this.postUrl = jso.postUrl||(PATH+"/image/upload.json");
			var that =this;
			this.callback = jso.callback||function(result){
                $(that.input).val(result.data);
			};
			this.fileInput=$("<input type=\"file\" style=\"display:none\"/>");
			this.input =$("#"+jso.input);
            this.imgDom.attr("src",$(this.input).val())
			$(this.input).parent().append(this.imgDom);
			$(this.input).parent().append(this.fileInput);

			 $(this.fileInput).change(function(){

              //  console.log("imgDom:"+nowImg);
                //var imageUtil= new zImageUtil({imgDom:nowImg,postUrl:"/calendar/image/upload.json",maxWidth:633,maxHeight:300});
                that.fileChange(this);
            });

            $(this.imgDom).click(function(){
                $(that.fileInput).trigger("click");
            });
		}
	};
	o.init(config);
	return o;
}

function zImageUtil3(config) {
	var o = {
		imgDom: null, //回显的image的id
		maxHeight: null, //预设的最大高度
		maxWidth: null, //预设的最大宽度
		postUrl: null, //提交的url"/calendar/image/upload.json"
		preShow: false,
		callback: null,
        fileInput:null,
		fileChange: function(e) {
			var f = e.files[0]; //一次只上传1个文件，其实可以上传多个的
			var FR = new FileReader();
			var _this = this;

			FR.onload = function(f) {

				_this.compressImg(this.result, 300, function(data,originalWidth,originalHeight) { //压缩完成后执行的callback
					console.log("压缩完成后执行的callback");
					//document.getElementById('imgData').value = data;//写到form元素待提交服务器
					//document.getElementById('myImg').src = data;//压缩结果验证

						console.log("img pre show");
						$(_this.imgDom).attr("src",data);
						var canvas = $("#myCanvas")[0];

                      canvasMain.width = originalWidth;
                       canvasMain.height = originalHeight;
 canvasBak.width = originalWidth*beishu;
                     canvasBak.height = originalHeight*beishu;
                       ctxMain .drawImage(_this.imgDom[0], 0, 0);

                       for(var y =0 ;y<canvas.height;y++){
                            for(var x =0 ;x<canvas.width;x++){
                               var c = ctxMain.getImageData(x, y, 1, 1).data;
                                                   var red = c[0];
                                                   var green = c[1];
                                                   var blue = c[2];
                                      var hex = red.toString(16)+""+green.toString(16)+""+blue.toString(16);
                                      ctxBak[selectedLayer].fillStyle="#"+hex;
                                      console.log();
                                    // $(tds[y*canvas.width+x]).css("background-color",red.toString(16)+""+green.toString(16)+""+blue.toString(16));
                                         ctxBak[selectedLayer].fillRect(x*beishu,y*beishu,beishu,beishu);
                               }
                        }
                       //    ctxMain .drawImage(_this.imgDom[0], 0, 0);
						//console.log(_this.imgDom);
					/*	generate(canvas.width,canvas.height);
						  var tds = $("#canvas").find("td");
						for(var y =0 ;y<canvas.height;y++){
						    for(var x =0 ;x<canvas.width;x++){
						       var c = ctx.getImageData(x, y, 1, 1).data;
                                                var red = c[0];
                                                var green = c[1];
                                                var blue = c[2];
                                    if(red>0){
                                    console.log(red.toString(16)+""+green.toString(16)+""+blue.toString(16));
                                    }
                                  $(tds[y*canvas.width+x]).css("background-color",red.toString(16)+""+green.toString(16)+""+blue.toString(16));

                            }
						}*/



				});
			};
			FR.readAsDataURL(f); //先注册onload，再读取文件内容，否则读取内容是空的
		},
		compressImg: function(imgData, maxHeight, onCompress) {
			var _this = this;
			if (!imgData)
				return;
			onCompress = onCompress || function() {};
			maxHeight = maxHeight || this.maxHeight; //默认最大高度200px
			var canvas = document.createElement('canvas');
			var img = new Image();
			console.log("maxHeight:" + maxHeight);
			img.onload = function() {

				canvas.width = img.width;
				canvas.height = img.height;
				var ctx = canvas.getContext("2d");

				ctx.clearRect(0, 0, canvas.width, canvas.height); // canvas清屏

				//重置canvans宽高 canvas.width = img.width; canvas.height = img.height;
				console.log("width:" + img.width + "height:" + img.height);
				ctx.drawImage(img, 0, 0, img.width, img.height); // 将图像绘制到canvas上
				console.log("begin compress img");
				onCompress(canvas.toDataURL("image/png"),img.width,img.height); //必须等压缩完才读取canvas值，否则canvas内容是黑帆布
			};
			// 记住必须先绑定事件，才能设置src属性，否则img没内容可以画到canvas
			console.log("begin origin data load:");
			img.src = imgData;

		},
		init: function(jso) {
			this.imgDom = $("<img class=\" img-upload\" alt=\"请上传图片\"></img>");
			this.maxHeight = jso.maxHeight||633;
			this.maxWidth = jso.maxWidth||300;
			this.postUrl = jso.postUrl||(PATH+"/image/upload.json");
			var that =this;
			this.callback = jso.callback||function(result){
                $(that.input).val(result.data);
			};
			this.fileInput=$("<input type=\"file\" style=\"display:none\"/>");
			this.input =$("#"+jso.input);
            this.imgDom.attr("src",$(this.input).val())
			$(this.input).parent().append(this.imgDom);
			$(this.input).parent().append(this.fileInput);

			 $(this.fileInput).change(function(){

              //  console.log("imgDom:"+nowImg);
                //var imageUtil= new zImageUtil({imgDom:nowImg,postUrl:"/calendar/image/upload.json",maxWidth:633,maxHeight:300});
                that.fileChange(this);
            });

            $(this.imgDom).click(function(){
                $(that.fileInput).trigger("click");
            });
		}
	};
	o.init(config);
	return o;
}