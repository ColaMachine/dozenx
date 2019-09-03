<template>

<div class="popup-wrap" style="z-index: 9999;">
    <div class="popup-bg" onclick="dataLayer.push({&quot;event&quot;:&quot;首页_半透明区域&quot;})"></div>
    <div class="popup-content" style="position: relative; top: 122px; width: 500px;">
        <div class="z-popup login-popup" id="J_login_popup" style="display: block;">
            <div class="z-popup-head z-clearfix"><a href="javascript:;"
                                                    onclick="dataLayer.push({&quot;event&quot;:&quot;首页_关闭按钮&quot;})"
                                                    class="z-popup-close z-icons icon-times-o J_popup_close"></a></div>
            <div class="z-popup-content">
                <iframe style="display:none" id="J_login_iframe" name="J_login_iframe" frameborder="0"
                        src="https://zhiyou.smzdm.com/user/login/window/#redirect_url=https%3A%2F%2Fwww.smzdm.com%2F"></iframe>



                <div class="geetest_wrap">
                 <zwTabs  >
                              <zwTabPanel tab="登录" tabIndex=1>
                                  <zwForm  onSubmit={this.handleSubmit}>
                                                            <zwFormItem>

                                                              <zwInput placeholder="邮箱">
                                                                <zwIcon slot="prefix" type="user"></zwIcon>

                                                              </zwInput>
                                                            </zwFormItem>
                                                            <zwFormItem>
                                                              <zwInput placeholder="密码" type="password">
                                                                <zwIcon slot="prefix" type="lock"></zwIcon>
                                                              </zwInput>
                                                            </zwFormItem>


                <div class="twoWeeks">
                                                                                    <div class="loginL">
                                                                                        <a href="javascript:;" data-style="quick" onclick="if (channel_name) { dataLayer.push({'event':'登录浮窗_快捷登陆', '频道名': channel_name}); }">手机快捷登录</a>
                                                                                    </div>
                                                                                    <div class="loginR">
                                                                                        <input type="checkbox" id="rememberme" name="rememberme" class="ckeckBox" checked="checked"><label for="rememberme">记住我</label><span class="cb-line">|</span><a href="/user/pass/lost/" class="a_underline" target="_blank" onclick="if (channel_name) { dataLayer.push({'event':'登录浮窗_忘记密码按钮', '频道名':channel_name}); }">忘记密码</a>
                                                                                    </div>
                                                                                </div>
                                                                                <p class="notice_error notice_error_300"></p>

               <zwFormItem>
                                                              <zwButton color="red" class="btn_login" type="primary" >登 录</zwButton>

                </zwFormItem>
                                                          </zwForm>




                              </zwTabPanel >
                            <zwTabPanel tab="注册" tabIndex=2>
                               <zwForm  ref="registerForm" id="loginForm">
                                                      <zwFormItem>
                                                              <zwInput  placeholder="请输入邮箱地址" id="email" name="email"></zwInput>

                                                         </zwFormItem>


                                                              <zwFormItem>
                                                              <zwInput placeholder="请输入密码" id="pwd" name="pwd"  type="password"  ></zwInput>
                                                              </zwFormItem>

                                                               <zwFormItem>
                                                                   <zwInput id="emailCode" name="emailCode"  placeholder="邮箱验证码">


                                                                   <zwButton slot="append" type="blue"  v-on:clickFn="getEamilCaptcha" loadedText="已发送邮箱验证码" :loading=true  :start_loading_state=false  :loading_delay=6>发送邮箱验证码</zwButton>

                                                                   </zwInput>
                                                              </zwFormItem>
              <div class="captchaBox">
                                      <div class="twoWeeks">
                                          <div class="loginL">
                                              <input type="checkbox" id="agreement" class="ckeckBox" tabindex="6">
                                          <label for="agreement">同意<a href="http://www.smzdm.com/user/register/provisions/" class="a_underline" target="_blank">《用户协议》</a></label>
                                          </div>
                                          <div class="loginR">
                                              <!-- <a target="_top" href="https://zhiyou.smzdm.com/user/register/?redirect_to=">邮箱注册</a> -->
                                          </div>
                                      </div>
                                  </div>
                                                               <zwFormItem>
                                                                                  <zwButton type="primary"  v-on:clickFn="saveRegister"  class="login-form-button btn_reg">立即注册</zwButton>
                                                                                </zwFormItem>
                                                       </zwForm>
                            </zwTabPanel >


                          </zwTabs>

              </div>














            </div>
        </div>
    </div>
</div>


</template>


<script type="text/javascript">


  import zwButton from '../../component/button/zwButton.vue';
  import zwButtonGroup from '../../component/button/zwButtonGroup.vue';
  import zwIcon from '../../component/icon/zwIcon.vue';
  import zwRow from '../../component/layout/zwRow.vue';
  import zwBox from '../../component/layout/zwBox.vue';
  import zwDropDown from '../../component/navigation/menu/zwDropDown.vue';
  import zwMenu from '../../component/navigation/menu/zwMenu.vue';
  import zwMenuItem from '../../component/navigation/menu/zwMenuItem.vue';
  import zwInput from '../../component/dataentry/zwInput.vue';
  import zwForm from '../../component/dataentry/zwForm.vue';
  import zwFormItem from '../../component/dataentry/zwFormItem.vue';
  import zwCol from '../../component/layout/zwCol.vue';


import zwCollapse from '../../component/datadisplay/zwCollapse.vue';
import zwTabs from '../../component/datadisplay/zwTabs.vue';
import zwTabPanel from '../../component/datadisplay/zwTabPanel.vue';
  export default {
    name: 'zwLogin',
    components: {
    zwButton,
    zwCollapse,
    zwTabs,
    zwTabPanel,
                       zwIcon,
                       zwBox,
                       zwRow,
                       zwCol,
                       zwDropDown,
                       zwMenu,
                       zwMenuItem,
                       zwButtonGroup,
                       zwInput,
                       zwForm,
                       zwFormItem},
    props: [
      "layout"

    ],
    data() {
      return {
           email:"",

            valid:{
                   rules : {

                       email : {
                       	email : true,
                           required : true,
                           rangelength : [ 1, 50 ]

                       },
                       pwd : {
                           stringCheck : true,
                           required : true,
                           rangelength : [ 6, 15 ]
                       },
                       pwdrepeat : {
                           stringCheck : true,
                           required : true,
                           rangelength : [ 6, 15 ],
                           equalTo : "#pwd"
                       }
                   },
                   messages : {

                       email : {
                           email : "请填写真实的邮箱",
                           required : "邮箱未填写",
                           rangelength : "邮箱长度应在50字符以内"
                       },
                       pwd : {
                           required : "密码未填写",
                           rangelength : "密码应由6~20个的数字或字母组成"
                       },
                       pwdrepeat : {
                           required : "密码未填写",
                           rangelength : "密码应由6~20个的数字或字母组成",
                           equalTo : "两次输入密码不同"
                       }
                   }
               },
         }
       },
       mounted(){
           extend(this.valid,BaseValidator);
             this.validator= validator(this.$refs.registerForm.$el,this.valid);
        //       this.doms.form.validate(this.valid);
    var imageUtil=new zImageUtil2({"input":"face"});
       },
       computed: {
   getPicCaptchaBtnText(){
       return  "获取图片验证码";
   }
       },
       methods: {
   getPicCaptcha:function(){
   console.log("getPicCaptcha");
             Ajax.getJSON(PATH+"/code/img/request.json",null,function(result){//登录的时候获取验证码
                          if(result.r==AJAX_SUCC){
                           //  that.doms.picCaptchaImg.setAttribute("src","data:image/png;base64,"+result.data.imgdata);

                                document.getElementById("vcodeImg").src="data:image/png;base64,"+result.data.imgdata;
                                //this.$refs.captcha.text="";
                          }else{
                              dialog.error(result.msg);
                          }
                      }.Apply(this));
      },
        getEamilCaptcha:function(){

           var email = document.getElementById("email").value;
           if(!StringUtil.isEmail(email)){
               dialog.error("请正确填写邮箱");
               return;
           }else{

           }
                  Ajax.getJSON(PATH+"/sys/auth/reg/email/code.json",{email:email},function(result){//登录的时候获取验证码
                               if(result.r==AJAX_SUCC){
                                //  that.doms.picCaptchaImg.setAttribute("src","data:image/png;base64,"+result.data.imgdata);
                               dialog.alert("请检查邮箱接收验证码");
                               }else{
                                   dialog.error(result.msg);
                               }
                           });

        },
        saveRegister:function(){



                if(!this.validator.valid(this.$refs.registerForm.$el)){
                   	//if (!this.doms.form.valid()) {
                   		return;
                   	}

               var jso={};
              var email = document.getElementById("email").value;
              var pwd = document.getElementById("pwd").value;
                var captcha = document.getElementById("captcha").value;
                 var emailCode = document.getElementById("emailCode").value;
                 jso.captcha= captcha;
                 jso.pwd=pwd;
                 jso.email=email;
                 jso.emailCode = emailCode;


                   	if(StringUtil.isBlank(jso.emailCode)||jso.emailCode.length<4){
                   	    dialog.alert("请输入验证码");
                   	    return;
                   	}


                       Ajax.post(PATH+"/sys/auth/reg/email.json",jso,function(result){
                           if(result.r==AJAX_SUCC){
                                window.location=PATH+"/index.htm";
                           }else{
                               dialog.alert(result.msg);
                           }

                       });

        }
       }
     }
</script>
<style>
.popup-wrap {
    position: fixed;
    top: 0;
    right: 0;
    bottom: 0;
    left: 0;
    background-color: rgba(0,0,0,.5);
    overflow-x: hidden;
    overflow-y: auto;
    z-index: 9999;
}


.popup-wrap .popup-bg {
    position: absolute;
    left: 0;
    top: 0;
    right: 0;
    bottom: 0;
}


.popup-wrap .popup-content {
    margin: 0 auto;
}

.z-popup {
    width: 500px;
    padding-bottom: 40px;
    background-color: #fff;
}


.z-popup .z-popup-head {
    position: relative;
    height: 60px;
    padding: 0 20px;
    line-height: 60px;
}
.z-clearfix, .z-hor-feed {
    zoom: 1;
}

.z-clearfix:after, .z-clearfix:before, .z-hor-feed:after, .z-hor-feed:before {
    content: "";
    display: table;
}

.z-popup .z-popup-head .z-popup-close {
    position: absolute;
    top: -6px;
    right: -6px;
    width: 24px;
    height: 24px;
    background-color: #fff;
    border-radius: 50%;
    color: #999;
    font-size: 16px;
    text-align: center;
    line-height: 24px;
}

.z-popup .z-popup-content {
    width: 100%;
}








.z-popup.login-popup .z-popup-content>iframe,.geetest_wrap {
    display: block;
    width: 315px;
   /* height: 310px;*/
    margin: 0 auto;
}



/***iframe 里面**/


.geetest_wrap {
    position: relative;
    overflow: hidden;
    width: 315px;
}

@media screen and (max-width: 480px)
.geetest_wrap {
    width: auto;
    padding: 0 10px;
}



@media screen and (max-width: 480px)
.login-tab {
    margin-bottom: 24px;
}
.login-tab {
    text-align: center;
    font-size: 18px;
}
.login-tab, .pop-content .login-tab {
    margin-top: 0;
}




.login-tab>li.active {
    border-bottom: 3px solid #f43c37;
}
.login-tab>li {
    margin: 0 20px;
    zoom: 1;
}
.form-item>*, .login-tab>li, .right-inner {
    display: inline-block;
}
.slick-dots, li {
    list-style: none;
}
*, .form-input-focus, .slick-dots li button:focus {
    outline: 0;
}




.login-tab>li.active>a, .pop-title a.a_underline:hover, .refresh a.a_underline:hover, span.error a.a_underline:hover {
    color: #333;
}
.login-tab>li>a {
    color: #666;
    line-height: 20px;
}





</style>