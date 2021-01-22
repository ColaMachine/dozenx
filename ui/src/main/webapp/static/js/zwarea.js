
function initProCitySel(jso) {
	var defProvince = jso.defProvince;
	var defCity = jso.defCity;
	var provinceId = jso.provinceId;
	var cityId = jso.cityId;

	$("#" + provinceId).change(function() {
		getCity(cityId, provinceId);
	})

	// 初始化省
	$.get(PATH + "/member/getProvince", {}, function(data) {

		jQuery("<option value=''>--请选择--</option>").appendTo("#" + provinceId);
		for (var i = 0, length = data.list.length; i < length; i++) {
			if (defProvince && defProvince == data.list[i]) {
				jQuery(
						"<option selected value='" + data.list[i] + "'>"
								+ data.list[i] + "</option>").appendTo(
						"#" + provinceId);
			} else {
				jQuery(
						"<option value='" + data.list[i] + "'>" + data.list[i]
								+ "</option>").appendTo("#" + provinceId);
			}
		}
		getCity(cityId, provinceId, defCity);
	});

}

function getCity(cityId, provinceId, defCity) {
	$.post(PATH + "/member/getCity", {
		areaname : $("#" + provinceId).val()
	}, function(data) {
		$("#" + cityId).empty();
		jQuery("<option value=''>--请选择--</option>").appendTo("#" + cityId);
		for (var i = 0, length = data.list.length; i < length; i++) {
			if (defCity && defCity == data.list[i]) {
				jQuery(
						"<option selected value='" + data.list[i] + "'>"
								+ data.list[i] + "</option>").appendTo(
						"#" + cityId);
			} else {
				jQuery(
						"<option value='" + data.list[i] + "'>" + data.list[i]
								+ "</option>").appendTo("#" + cityId);
			}
		}
	});
}











try{
 if($!=null){
 $("*[data-toggle='modal']").each(function(){
		if($(this).attr("data-target")){
			$(this).on("click",function(){
				dialog.showMask();
				$($(this).attr("data-target")).show();
			});
		}

	});
	$(".dropdown").each(function(){//*[data-toggle='dropdown']
		//if($(this).attr("role")=="auto"){

	//	}
		if($(this).attr("role")=="btn"){
            $(this).on("click",function(event){
                $(this).addClass('open');
                event.stopPropagation();
            }).on("blur",function(){
                $(this).removeClass('open');
            });// .on("click",function(){
        }else{
            $(this).on("mouseover",function(){
                $(this).addClass('open');
            }).on("mouseout",function(){
                $(this).removeClass('open');
            });// .on("click",function(){

        }

	});
	$(document).click(function(){
		$(".dropdown").removeClass('open');
	})

	// $(".dropdown-menu").blur(function(){
// 					$(this).hide();
// 				});


	$("*[data-dismiss='modal']").each(function(){
		$(this).on("click",function(){
		dialog.hideMask();
		$(this).closest(".modal").hide();
		});

	});

	$(".menu li").each(function(){
		$(this).on("click",function(){
			$(".select").removeClass("select");
			$(this).addClass("select");
		});
	})

	$(".nav-tabs li").each(function(){
		$(this).on("click",function(){
			$(".active").removeClass("active");
			$(this).addClass("active");
		});
	});
	}
	}catch(e){
	console.log("jquery has not include ");
	}









/**
 * 对于模态框 tab页进行自动绑定触发事件
 */
function pageinit(){
	$("*[data-toggle='modal']").each(function(){
		if($(this).attr("data-target")){
			$(this).on("click",function(){
				dialog.showMask();
				$($(this).attr("data-target")).show();
			});
		}

	});

	$("*[data-dismiss='modal']").each(function(){
		$(this).on("click",function(){
		dialog.hideMask();
		$(this).closest(".modal").hide();
		});

	});

	$(".menu li").each(function(){
		$(this).on("click",function(){
			$(".select").removeClass("select");
			$(this).addClass("select");
		});
	})

	$(".nav-tabs li").each(function(){
		$(this).on("click",function(){
			$(".active").removeClass("active");
			$(this).addClass("active");
		});
	});
}

