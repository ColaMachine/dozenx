
//创建menu数据

var zMenu={
	autoCollapse: true,
	animation: true,
	idName: "",
	urlName: "",
	pidName: "",
	menuName: "",
	r:null,
	init: function(id, data, option) {
		var ele = document.getElementById(id);
		if (ele) {
			var html = "<ul class='nav'>";
			//			document.getElementById(id).appendChild()
			this.idName = option.id;
			this.urlName = option.url;
			this.pidName = option.pid;
			this.menuName = option.name;
			for (var i = 0; i < data.length; i++) {
				if (StringUtil.isBlank(data[i][this.pidName]) || data[i][this.pidName] == 0) {
					html += this.createLi(data, data[i]);
				}
			}
			html += "</ul>";
			ele.innerHTML = html;
			this.addEvent(id);
		}
	},
	addEvent: function(id) {
		var _this = this;
		var menuWrap = document.getElementById(id);
		var aAry = document.getElementById(id).getElementsByTagName("a");
		$("#" + id).find('ul a').click(function(event) {

			event.preventDefault();
			//console.log($(".menu-wrap")[0].offsetWidth);
			if ($(".menu-wrap")[0].offsetWidth < 100) {
				//return;
			}

			if (isNull($(this).attr("href")) || $(this).attr("href") == "null") {

				var id = $(this).attr("id");
				var ul = "m_u_" + id.substr(4);
				//console.log($(this).parent().length);
				//查找当前所属的li状态是否是打开状态的
				if ($(this).parent().hasClass('open')) { //console.log("rm open");
					var that =this;
					$("#" + ul).slideUp(50*$("#" + ul).children().length, function() {$(that).parent().removeClass('open');});
				} else { //console.log("add open");
					//打开当前节点,关闭同级节点里的open元素.

						var that =this;
                    //如果找到任何open的节点不是自己的父类 就收缩起来

                    $(".open").each(function(){
                        if($(this).find("#"+$(that).attr("id")).length>0){

                        }else{
                        var _that=this;
                        var ul=  $(this).find("ul").eq(0);
                             $(ul).slideUp(50*$(ul).children().length,function(){$(_that).removeClass('open');});
                        }


                    });


					$("#" + ul).slideDown(50*$("#" + ul).children().length, function() {$( that).parent().addClass('open');});

				}
			} else {
				$(".menu-wrap").find('.active').removeClass('active');
				$(this).parent().parent().find('a').removeClass('active');
				$(this).addClass('active');
				//_this.loadPage($(this).attr("href"));
				if($(this).attr("href").startWith("#")){
				     window.location.href=$(this).attr("href");

				}else{
				     window.location="#"+$(this).attr("href");
				}



			}
		});

		$('#userprofile').click(function() {
			_this.userprofile();
		});
		$('#userpasswd').click(function() {
			_this.userpasswd();
		});

	},
	loadPage: function(url, fun) {

		window.data = {};
		//截取参数
		var position = url.indexOf("?");
		if (position > 0) {
			var paramsStr = url.substring(position + 1);
			console.log("paramsStr:" + paramsStr);
			var arr = paramsStr.split("&");

			for (var i = 0; i < arr.length; i++) {
				var keyVal = arr[i].split("=");
				var key = keyVal[0];
				var val = keyVal[1];
				console.log(keyVal[0] + ":" + keyVal[1]);
				window.data[key] = val;
			}
		}



        if(url.indexOf("window:")!=-1){

            window.open(url.replace("window:",""));
        }else{
        	if(url.substr(0,1)=="/"){
        		}else if(url.substr(0,3)=="../"){

        		}else{
        		url="/"+url;
        		}
            Ajax.get(PATH+url, null, function(data) {
                $('.main').html(data);

            });
		}

	},

	createLi: function(data, row) {
	var url =row[this.urlName];
	    /*window.route.addRoute(url, function() {
	    zMenu.loadPage(url);

        });*/
		var html =
			"<li ><a id=\"m_a_" + row["id"] + "\" href=\""+ row[this.urlName] + "\" ><span class='nav-icon'><i class='" + (StringUtil.isBlank(row["icon"])?"fa fa-diamond":row["icon"]) + "'></i></span><span class='nav-text'>" + row[this.menuName] + "</span>" + (isNull(row[this.urlName]) ? "<span class='nav-caret'><i class=\"fa fa-caret-down\"></i></span>" : "") + "</a><ul  id=\"m_u_" + row["id"] + "\">";
		for (var i = 0; i < data.length; i++) {
			if (typeof data[i][this.pidName] != 'undefined' && data[i][this.pidName] != null && data[i][this.pidName] == row[this.idName]) { //说明有子项目
				html += this.createLi(data, data[i]);
			}
		}
		html += "</ul></li>";
		return html;
	}



    }
module.exports = zMenu;