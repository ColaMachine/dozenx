/**
 *
 *
 * Author:
 *	zzw <zzw1986@gmail.com>
 *
 *
 * File: td_view.js
 * Create Date: 2013-9-23 8:29
 *
 *
 */

var loginForm={
    ids:{
    root:null,
        form:null,
        picCaptchaInput:null,
        picCaptchaImg:null,
        userName:null,
        pwd:null,
        rememberMe:null,
        forgetLink:null,
        submitBtn:null
    },
    doms:{

    },
    init:function(){
        var cfg={
            root:"#login_wrap",
            form:"#login_form",
            userName:"#email",
            pwd:"#loginpwd",
            picCaptchaInput:"#loginPicCaptchaInput",
            picCaptchaImg:"#loginPicCaptchaImg",
            rememberMe:"#rememberme",
            forgetLink:"#forgetLink",
            submitBtn:"#loginBtn"
        };

        extend(this.ids,cfg);
        for(var i in this.ids){
            this.doms[i]=$$(this.ids[i]);
            if(!this.doms[i]){
                console.log(this.ids[i] +"doesn't find ");
            }
        }

        extend(this.valid,BaseValidator);
        this.validator= validator(this.doms.form,this.valid);
        //this.doms.form.validate(this.valid);
        this.doms.submitBtn.removeAttribute("disabled");
        this.addEventListener();
        this.checkCookie();
    },
    addEventListener:function(){
        //注册按钮
        var that=this;
        this.doms.submitBtn.onclick=this.submit.Apply(this) ;
        this.doms.picCaptchaImg.onclick=this.getPicCaptcha.Apply(this);
        this.doms.forgetLink.onclick=this.forgetLink.Apply(this);
       // this.doms.picCaptchaImg.trigger("click");
        this.getPicCaptcha();
        this.doms.picCaptchaInput.onkeydown=function(e){
            var keycode=document.all?event.keyCode:e.which;
            if(keycode==13){
                that.submit();
                this.blur();
            }
        }
        this.doms.pwd.onkeydown=function(e){
            var keycode=document.all?event.keyCode:e.which;
            if(keycode==13){
                that.submit();
                this.blur();
            }
        }
    },
    forgetLink:function(){
        forgetPwdForm.show();
        //emailValidForm.setEmail("371452875@qq.com");
        //emailValidForm.show();
    },

    //登录按扭提交
    submit:function(){
       
        if(!this.validator.valid(this.doms.form)){
            return;
        }

        var dialogId = dialog.showWait();
        var jso = changeForm2Jso("#login_form");
        var jso={};
        jso.email=this.doms.userName.value;
        if(this.doms.pwd.value.length!=32){
         jso.pwd=$.md5($.md5(this.doms.pwd.value));
        }else{
         jso.pwd=this.doms.pwd.value;
        }

       // alert(jso.pwd);
        jso.picCaptcha=this.doms.picCaptchaInput.value;
        //jso.password=$$.md5(jso.password);
        //先禁用按钮
       // $("loginBtn")
        this.doms.submitBtn.setAttribute("disabled", "disabled");
        this.doms.submitBtn.innerText="提交中...";
        setTimeout("$('#loginBtn').removeAttr('disabled').text('登录')",3000);
       // $$("#loginBtn").setAttribute("disabled", "disabled");
        //alert($$("#rememberme").attr("checked"));
        //判断是否使用记住功能

        if ($("#rememberme").attr("checked") == 'checked') {//alert("选中了记住我");
        /*	console.log("选中了记住我" );*/
            console.log("username:" + jso.email);
            this.setCookie('username', jso.email, 365);
            this.setCookie('password', jso.pwd, 365);
        }
        Ajax.post(PATH + "/loginPost.json", jso, function(data) {
            if (data[AJAX_RESULT] == AJAX_SUCC) {
                window.location = PATH + "/index.htm";
            } else {
            dialog.close(dialogId);
            dialog.alert(data.msg);
           // alert(data.msg);
              /*  var ul = $$("#login_form").find(".failure").find("ul");
                ul.empty();
                ul.append("<li>" + data[AJAX_MSG] + "</li>");
                if (data[AJAX_ERRORS] && data[AJAX_ERRORS].length > 0) {
                    for ( var i in data[AJAX_ERRORS]) {
                        ul.append("<li style='color:red'>" + data[AJAX_ERRORS][i]
                                + "</li>");
                    }
                }*/

            }
            $$("#loginBtn").removeAttribute("disabled", "");
        });
    },
    //获取验证码图片点击事件
    getPicCaptcha:function(){
        that =this;
        Ajax.getJSON(PATH+"/code/img/request.json",null,function(result){
            if(result.r==AJAX_SUCC){
             //  that.doms.picCaptchaImg.setAttribute("src","data:image/png;base64,"+result.data.imgdata);

                 that.doms.picCaptchaImg.src="data:image/png;base64,"+result.data.imgdata;
            }else{
                dialog.error(result.msg);
            }
        });
    },
    valid:
    {
        rules : {
            email : {
                required : true,
              /*  email : true,*/
                rangelength : [ 1, 50 ],
                //isemailorphone : true
            },
            pwd : {
                stringCheck : true,
                required : true,
                rangelength : [ 6, 32 ]
            },
            picCaptcha:{
                 required : true
            }
        },
        messages : {
            email : {
                required : "邮箱/手机号未填写",
              /*  isemailorphone:true,*/
                rangelength : "邮箱/手机号长度应在50字符以内"
            },
            pwd : {
                required : "密码未填写",//TODO 增加判断
                rangelength : "密码应由6~20个的数字或字母组成"
            },
            picCaptcha:{
                 required : "请输入验证码"
            }
        }

    },

/***
 ** 取cookie值
 *
 */
getCookie:function (c_name) {
	console.log("cookie:"+document.cookie);
	if (document.cookie.length > 0) {
		c_start = document.cookie.indexOf(c_name + "=")
		if (c_start != -1) {
			c_start = c_start + c_name.length + 1
			c_end = document.cookie.indexOf(";", c_start)
			if (c_end == -1)
				c_end = document.cookie.length
			return unescape(document.cookie.substring(c_start, c_end))
		}
	}
	return ""
},
//设置cookie值
setCookie:function (c_name, value, expiredays) {
    console.log("expiredays:"+expiredays);
	var exdate = new Date();
	exdate.setDate(exdate.getDate() + expiredays);
	console.log(c_name + "=" + escape(value)
			+ ((expiredays == null) ? "" : ";expires=" + exdate.toGMTString()));
	document.cookie = c_name + "=" + escape(value)
			+ ((expiredays == null) ? "" : ";expires=" + exdate.toGMTString())
},

checkCookie:function () {
	var username = this.getCookie('username')

	//alert(username+$$("#login-email").val());
	//return;
	if (username != null && username != "") {//alert('Welcome again '+username+'!')

        this.doms.userName.value=username;
        var pwd =this.getCookie("password");
        if(pwd){
             this.doms.pwd.value=pwd;

             //setTimeout("document.getElementById(\"loginpwd\").value="+pwd,1000);
        }
	}


	else {
		//username = prompt('Please enter your name:', "")
		if (username != null && username != "") {
			this.setCookie('username', username, 7)
		}
	}
}

}




function doRegister() {
	document.location = "register.html";
}


var form_type = "login";
//切换登录表单和注册表单
/*function changeForm() {
	$$("#registerBtn").removeAttribute("disabled");
	$$("#loginBtn").removeAttribute("disabled");
	if (form_type == "login") {
		$$("#register_form").show();
		$$("#login_form").hide();
		form_type = "register"
	} else {
		form_type = "login";
		$$("#register_form").hide();
		$$("#login_form").show();
	}
}*/


/**
 *登录
 *
 **/

window.onload=function(){

	//$$("#loginBtn").bind('click',function(){alert('tt!')});
	//登录表单验证器初始化
	loginForm.init();
	//loginValidator.init();
	//注册表单初始化
	//registerValidator.init();
	//registerForm.init();
	//smsValidForm.init();
	//forgetPwdForm.init();
    //emailValidForm.init();

//在这里面输入任何合法的js语句
//页面层-自定义
/*layer.open({
  type: 1,
  title: false,
  closeBtn: 1,
  shadeClose: false,
  skin: 'yourclass',
  content: '自定义HTML内容'
});*/



}
/*
$$(document).ready(function() {



	//show user name and password

});
*/




/**
 *
 *
 * Author:
 *	zzw <zzw1986@gmail.com>
 *
 *
 * File: td_view.js
 * Create Date: 2013-9-23 8:29
 *
 *
 */






















