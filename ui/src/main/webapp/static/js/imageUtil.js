

function zImageUtil2(config) {
	var o = {
		imgDom: null, //回显的image的dom
		fileInput:null,// input type = file 节点
		input: null ,  //保存 url 的节点id
		maxHeight: null, //预设的最大高度
		maxWidth: null, //预设的最大宽度
		postUrl: null, //提交的url"/calendar/image/upload.json"
		preShow: false,
		callback: null,

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
								$(_this.imgDom).attr("src",  data.data);
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




			this.maxHeight = jso.maxHeight||633;
			this.maxWidth = jso.maxWidth||300;
			this.postUrl = jso.postUrl||(PATH+"/pubimage/base64/upload");
			var that =this;
			this.callback = jso.callback||function(result){
                $(that.input).val(result.data);
			};



			this.input =$("#"+jso.input);

			if(!config.imgDom){//如果不存在 img节点
                this.imgDom = $("<img class=\" img-upload\" alt=\"请上传图片\"> "
                //	+" <svg class=\"Zi Zi--Camera WriteCover-uploadIcon\" fill=\"currentColor\" viewBox=\"0 0 24 24\" width=\"42\" height=\"42\"><path d=\"M20.094 6S22 6 22 8v10.017S22 20 19 20H4.036S2 20 2 18V7.967S2 6 4 6h3s1-2 2-2h6c1 0 2 2 2 2h3.094zM12 16a3.5 3.5 0 1 1 0-7 3.5 3.5 0 0 1 0 7zm0 1.5a5 5 0 1 0-.001-10.001A5 5 0 0 0 12 17.5zm7.5-8a1 1 0 1 0 0-2 1 1 0 0 0 0 2z\" fill-rule=\"evenodd\"></path></svg>"
                            +" </img>");
                $(this.input).parent().append(this.imgDom);
            }else{
                this.imgDom  = $(config.imgDom);
            }
            if(!config.fileInput){
                this.fileInput=$("<input type=\"file\" style=\"display:none\"/>");
                 $(this.input).parent().append(this.fileInput);
            }else{
                this.fileInput=  $(config.fileInput) ;

            }
            this.imgDom.attr("src",$(this.input).val())



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
function getUploadImag(config) {

	var o = {
		//imgDom: null, //回显的image的id
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
                      //  _this.imgDom.src=data;
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
//_this.imgDom.src=data.data;
//alert(data.data);
console.log(data);
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
			this.clickDom = jso.clickDom;
			this.maxHeight = jso.maxHeight||633;
			this.maxWidth = jso.maxWidth||300;
			this.postUrl = jso.postUrl||(PATH+"/image/upload.json");
			var that =this;
			this.callback = jso.callback||function(result){
               // that.input.value=result.data;
               // this.imgDom.src=PATH+"/"+result.data;
			};
			this.fileInput=parseDom("<input type=\"file\" style=\"display:none\"/>");
			//this.input =isDom(jso.input)?json.input:$$("#"+jso.input);
            //this.imgDom.src=this.input.value;
			//this.input.parentNode.appendChild(this.imgDom);
			//this.input.parentNode.appendChild(this.fileInput);
			document.body.appendChild(this.fileInput);
            this.fileInput.addEventListener("change",function(){

              //  console.log("imgDom:"+nowImg);
                //var imageUtil= new zImageUtil({imgDom:nowImg,postUrl:"/calendar/image/upload.json",maxWidth:633,maxHeight:300});
                console.log("fileChange");
                that.fileChange(this);
            });
            //this.fileInput.click();
            var that =this;
            //setTimeout(function(){



            // that.fileInput.dispatchEvent(evt);
           // this.imgDom.addEventListener("click",function(){
           //                  var evt = new MouseEvent("click", { bubbles: false, cancelable: true, view: window });
           //                that.fileInput.dispatchEvent(evt);
           //             });
           // var evt = new MouseEvent("click", { bubbles: false, cancelable: true, view: window });
           //                that.fileInput.dispatchEvent(evt);
            this.clickDom.addEventListener("click",function(){
               var evt = new MouseEvent("click", { bubbles: false, cancelable: true, view: window });
               that.fileInput.dispatchEvent(evt);
            });



		}
	};
	o.init(config);
	return o;
}