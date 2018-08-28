/**
  var imageUtil=new zImageUtil2({"input":"face"});


**/
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
//像素
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


function zImageUtil4(config) {
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
						_this.imgDom.src=data;
//						var canvas = $$("#myCanvas")[0];
//
//                      canvasMain.width = originalWidth;
//                       canvasMain.height = originalHeight;
// canvasBak.width = originalWidth*beishu;
//                     canvasBak.height = originalHeight*beishu;
//                       ctxMain .drawImage(_this.imgDom[0], 0, 0);
//
//                       for(var y =0 ;y<canvas.height;y++){
//                            for(var x =0 ;x<canvas.width;x++){
//                               var c = ctxMain.getImageData(x, y, 1, 1).data;
//                                                   var red = c[0];
//                                                   var green = c[1];
//                                                   var blue = c[2];
//                                      var hex = red.toString(16)+""+green.toString(16)+""+blue.toString(16);
//                                      ctxBak[selectedLayer].fillStyle="#"+hex;
//                                      console.log();
//                                    // $(tds[y*canvas.width+x]).css("background-color",red.toString(16)+""+green.toString(16)+""+blue.toString(16));
//                                         ctxBak[selectedLayer].fillRect(x*beishu,y*beishu,beishu,beishu);
//                               }
//                        }
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
			this.imgDom = parseDom("<img class=\" img-upload\" alt=\"请上传图片\"></img>");
			this.maxHeight = jso.maxHeight||633;
			this.maxWidth = jso.maxWidth||300;
			this.postUrl = jso.postUrl||(PATH+"/image/upload.json");
			var that =this;
			this.callback = jso.callback||function(result){
                $(that.input).val(result.data);
			};
			this.fileInput=parseDom("<input type=\"file\" style=\"display:none\"/>");
			this.input =$$("#"+jso.input);
            this.imgDom.src=this.input.value;
			this.input.parentNode.appendChild(this.imgDom);
			this.input.parentNode.appendChild(this.fileInput);
            this.fileInput.addEventListener("change",function(){

              //  console.log("imgDom:"+nowImg);
                //var imageUtil= new zImageUtil({imgDom:nowImg,postUrl:"/calendar/image/upload.json",maxWidth:633,maxHeight:300});
                that.fileChange(this);
            });


            this.imgDom.addEventListener("click",function(){
                 var evt = new MouseEvent("click", { bubbles: false, cancelable: true, view: window });
               that.fileInput.dispatchEvent(evt);
            });



		}
	};
	o.init(config);
	return o;
}




function zImageUtil5(config) {
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

				_this.compressImg(this.result, 300, function(data,originalWidth,originalHeight) {
					console.log("压缩完成后执行的callback");
                    //document.getElementById('imgData').value = data;//写到form元素待提交服务器
                    //document.getElementById('myImg').src = data;//压缩结果验证
                    if (_this.preShow) {
                        console.log("img pre show");
                        //$(_this.imgDom).attr("src", data);
                        //console.log(_this.imgDom);
                    }
                    console.log("begin send img");
                    var json = {};
                    // json.imageName= "myImage.png";
                    data ="+"+ data;//.substring(22);
                    //alert(data.length);
                    //alert(data.substring(0,100));
                    // alert(data)
                    json.imageData = encodeURIComponent(data);
                    console.log("begin post");

                    Ajax.post(_this.postUrl,
                        json,
                        function(data) {console.log("ajax return");
//                							if (data.r == AJAX_SUCC) {console.log("succ");
//                								$(_this.imgDom).attr("src", PATH+"/" + data.data);
//                								//$(_this).parent().find("#picurl")
//                								console.log("imgUrl:" + data.data);
//                							} else {
//                							    console.log("fail");
//
//                								//	                        		zalert(data.msg);
//                							}
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
				onCompress(canvas.toDataURL("image/png"),img.width,img.height); //必须等压缩完才读取canvas值，否则canvas内容是黑帆布
			};
			// 记住必须先绑定事件，才能设置src属性，否则img没内容可以画到canvas
			console.log("begin origin data load:");
			img.src = imgData;

		},
		init: function(jso) {
			this.imgDom = parseDom("<img class=\" img-upload\" alt=\"请上传图片\"></img>");
			this.maxHeight = jso.maxHeight||633;
			this.maxWidth = jso.maxWidth||300;
			this.postUrl = jso.postUrl||(PATH+"/image/upload.json");
			var that =this;
			this.callback = jso.callback||function(result){
                $(that.input).val(result.data);
			};
			this.fileInput=parseDom("<input type=\"file\" style=\"display:none\"/>");
			this.input =$$("#"+jso.input);
            this.imgDom.src=this.input.value;
			//this.input.parentNode.appendChild(this.imgDom);
			//this.input.parentNode.appendChild(this.fileInput);
            this.fileInput.addEventListener("change",function(){

              //  console.log("imgDom:"+nowImg);
                //var imageUtil= new zImageUtil({imgDom:nowImg,postUrl:"/calendar/image/upload.json",maxWidth:633,maxHeight:300});
                that.fileChange(this);
            });


            //this.imgDom.addEventListener("click",function(){
               var evt = new MouseEvent("click", { bubbles: false, cancelable: true, view: window });
               this.fileInput.dispatchEvent(evt);
           // });



		}
	};
	o.init(config);
	return o;
}


//利用FormData 实际不可用
function zImageUtil6(config) {
	var o = {
		imgDom: null, //回显的image的id
		maxHeight: null, //预设的最大高度
		maxWidth: null, //预设的最大宽度
		postUrl: null, //提交的url"/calendar/image/upload.json"
		preShow: false,
		callback: null,
        fileInput:null,
		fileChange: function(e) {
			var fd =new FormData(this.form);
                    	$.ajax({
                         		url: "/image/uploadSubmit.json",
                         		type: "POST",
                         		data: fd,
                         		success: function(response,status,xhr){

                            		var json=$.parseJSON(response);
                                    console.log(response);
                        		 }
                    		});

		},

		init: function(jso) {
			this.imgDom = parseDom("<img class=\" img-upload\" alt=\"请上传图片\"></img>");
			this.maxHeight = jso.maxHeight||633;
			this.maxWidth = jso.maxWidth||300;
			this.postUrl = jso.postUrl||(PATH+"/image/upload.json");
			var that =this;
			this.callback = jso.callback||function(result){
                $(that.input).val(result.data);
			};
			this.form = parseDom("<form enctype='multipart/form-data' ></form>");

			document.body.appendChild(this.form);
			this.fileInput=parseDom("<input type='file' name='pic1'></input>");
            this.form.appendChild(this.fileInput);
            this.fileInput.addEventListener("change",function(){

              //  console.log("imgDom:"+nowImg);
                //var imageUtil= new zImageUtil({imgDom:nowImg,postUrl:"/calendar/image/upload.json",maxWidth:633,maxHeight:300});
                that.fileChange(this);
            });


            //this.imgDom.addEventListener("click",function(){
               var evt = new MouseEvent("click", { bubbles: false, cancelable: true, view: window });
               this.fileInput.dispatchEvent(evt);
           // });



		}
	};
	o.init(config);
	return o;
}


//https://blog.csdn.net/smilefyx/article/details/49332433

function zImageUtil7(config) {
	var o = {
		imgDom: null, //回显的image的id
		maxHeight: null, //预设的最大高度
		maxWidth: null, //预设的最大宽度
		postUrl: null, //提交的url"/calendar/image/upload.json"
		preShow: false,
		callback: null,
        fileInput:null,
		fileChange: function(e) {
           this.form.submit();

		},
		ActionHandler:function ()  {
             //alert("call");
             //文档加载或刷新时也会调用，因此需要通过标志位控制，提交时将标志位置为1，在这里处理之后修改标志位为0
            if(0 != handlerFlag)  {
                //do action
            //	alert("123123");
                var iframe=this.iframe;
                var value = this.iframe.contentWindow.document.body.innerText;
                if(null!=value)  {
                     //alert(value);
                     var obj = eval("("+value+")");
                    this.callback(obj);
                }
                                    //update flag.
                handlerFlag = 0;
            }
        },

		init: function(jso) {
			this.imgDom = parseDom("<img class=\" img-upload\" alt=\"请上传图片\"></img>");
			this.maxHeight = jso.maxHeight||633;
			this.maxWidth = jso.maxWidth||300;
			this.postUrl = jso.postUrl||(PATH+"/image/upload.json");
			var that =this;
			this.callback = jso.callback||function(result){
                $(that.input).val(result.data);
			};
			 this.iframe= parseDom("<iframe id='ifActionResult' name='ifActionResult' style='display:none;'></iframe>");

			document.body.appendChild(this.iframe);
			this.form = parseDom("<form enctype='multipart/form-data' target='ifActionResult' method='post' action='/image/upload/submit' ><input type='file' name='pic1'></input><button type='submit' /></form>");
			document.body.appendChild(this.form);
			this.fileInput=this.form.childNodes[0];
            this.submitBtn=this.form.childNodes[1];

            setOnloadCallBask(this.iframe,'load',this.ActionHandler.Apply(this));


            this.fileInput.addEventListener("change",function(){
              //  console.log("imgDom:"+nowImg);
                //var imageUtil= new zImageUtil({imgDom:nowImg,postUrl:"/calendar/image/upload.json",maxWidth:633,maxHeight:300});
                that.fileChange(this);
            });
            //this.imgDom.addEventListener("click",function(){
           var evt = new MouseEvent("click", { bubbles: false, cancelable: true, view: window });

           sub();
           this.fileInput.dispatchEvent(evt);
           // });



		}
	};
	o.init(config);
	return o;
}

var handlerFlag = 0;
	function setOnloadCallBask(obj, event, handler) {
			    //for most explores
				if (null != obj && null != obj.addEventListener) {
					obj.addEventListener(event, handler, false);
				}
				//for IE
				else if (null != obj && null != obj.attachEvent) {
					obj.attachEvent('on'+event, handler);
				}
				//not support
				else {
					//选择dom元素错误
					throw new Error('不支持该dom元素');
				}
			}


			function ActionHandler()  {
            			     //alert("call");
            				 //文档加载或刷新时也会调用，因此需要通过标志位控制，提交时将标志位置为1，在这里处理之后修改标志位为0
            			    if(0 != handlerFlag)  {
            				    //do action
            				//	alert("123123");
            					var iframe=document.getElementById("ifActionResult");
            					var value = document.getElementById("ifActionResult").contentWindow.document.body.innerText;
                                                    if(null!=value)  {
                                                         //(value);
                                                         var obj = eval("("+value+")");
                                                         if(obj.result=="ok")  {
                                                            top.document.location.href="resource.shtml";
                                                         }
                                                         else {
                                                            window.parent.doNotice(obj.message);
                                                         }
                                                    }
                                                    //update flag.
            					handlerFlag = 0;
            				}
            			}

            			function sub()  {
                        				handlerFlag = 1;
                        			}
