<template>
  <div>
    <article>
      <section>
        <h2>Form表单</h2>
        <p>具有数据收集、校验和提交功能的表单，包含复选框、单选框、输入框、下拉选择框等元素。</p>
        <h3>表单</h3>
        <p>我们为 form 提供了以下三种排列方式： 水平排列：标签和表单控件水平排列；（默认） 垂直排列：标签和表单控件上下垂直排列； 行内排列：表单项水平行内排列。 表单域# 表单一定会包含表单域，表单域可以是输入控件，标准表单域，标签，下拉菜单，文本域等。 这里我们封装了表单域 。 zwForm.Item {...props} {children} /zwForm.Item
        </p>
        <h3>代码演示<zwIcon type="windows" title="展开全部代码"></zwIcon></h3>
        <zwRow>

         <zwCol span=12>

                    <zwBox class="">
                      <div slot="zwCodeBoxDemo">

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
                      <div slot="zwCodeBoxMeta" title="基本使用">
                        基本使用
                      </div>
                      <div slot="zwCodeBoxPanel">

                        <textarea ref="textarea" id="textarea" name="textarea">
                       <zwInput placeholder="Basic usage"></zwInput>
                         </textarea>

                      </div>
                    </zwBox>
                  </zwCol>


          <zwCol span=12>

            <zwBox class="">
              <div slot="zwCodeBoxDemo">
                <zwForm  onSubmit={this.handleSubmit}>
                  <zwFormItem>

                    <zwInput placeholder="username">
                      <zwIcon slot="prefix" type="user"></zwIcon>

                    </zwInput>
                  </zwFormItem>
                  <zwFormItem>
                    <zwInput placeholder="password">
                      <zwIcon slot="prefix" type="lock"></zwIcon>
                    </zwInput>
                  </zwFormItem>
                  <zwFormItem>
                    <input type="checkbox"> remember me</input>
                    <a>forget password</a>
                  </zwFormItem>
                  <zwFormItem>
                    <zwButton>login</zwButton>
                  </zwFormItem>
                </zwForm>
              </div>
              <div slot="zwCodeBoxMeta" title="基本使用">
                基本使用
              </div>
              <div slot="zwCodeBoxPanel">

                <textarea ref="textarea" id="textarea" name="textarea">
               <zwInput placeholder="Basic usage"></zwInput>
                 </textarea>

              </div>
            </zwBox>
          </zwCol>



 <zwCol span=12>

            <zwBox class="">
              <div slot="zwCodeBoxDemo">
                 <zwForm  ref="registerForm" id="loginForm">
                       <zwFormItem>
                               <zwInput  placeholder="请输入邮箱地址" id="email" name="email"></zwInput>

                          </zwFormItem>


                               <zwFormItem>
                               <zwInput placeholder="请输入密码" id="pwd" name="pwd"  type="password"  ></zwInput>
                               </zwFormItem>
                               <zwFormItem>
                               <zwInput placeholder="请输再次入密码"  id="pwdrepeat" name="pwdrepeat"  type="password"></zwInput>
                               </zwFormItem>
                               <zwFormItem>
                               <zwInput id="captcha" name="captcha"   placeholder="请输入右侧图形验证码">

                                    <img id="vcodeImg" slot="append" src="" ></img>

                                    <zwButton ref="captcha" type="primary" slot="append" icon="refresh" v-on:clickFn="getPicCaptcha"  loadedText=""  :loading=true :start_loading_state=false :loading_delay=6></zwButton>

                               </zwInput>
                               </zwFormItem>
                                <zwFormItem>
                                    <zwInput id="emailCode" name="emailCode"  placeholder="邮箱验证码">


                                    <zwButton slot="append" type="primary"  v-on:clickFn="getEamilCaptcha" loadedText="已发送邮箱验证码" :loading=true  :start_loading_state=false  :loading_delay=6>邮箱验证码</zwButton>

                                    </zwInput>
                               </zwFormItem>

                                <zwFormItem>
                                                   <zwButton  v-on:clickFn="saveRegister"  class="login-form-button">立即注册</zwButton>
                                                 </zwFormItem>
                        </zwForm>
              </div>
              <div slot="zwCodeBoxMeta" title="基本使用">
                基本使用
              </div>
              <div slot="zwCodeBoxPanel">

                <textarea ref="textarea" id="textarea" name="textarea">
               <zwInput placeholder="Basic usage"></zwInput>
                 </textarea>

              </div>
            </zwBox>
          </zwCol>

        </zwRow>

        <zwRow>
          <zwCol span=12>

            <zwBox class="">
              <div slot="zwCodeBoxDemo">
                <zwForm class="login-form" onSubmit={this.handleSubmit}>
                  <zwFormItem>

                    <zwInput placeholder="username">
                      <zwIcon slot="prefix" type="user"></zwIcon>

                    </zwInput>
                  </zwFormItem>
                  <zwFormItem>
                    <zwInput placeholder="password">
                      <zwIcon slot="prefix" type="lock"></zwIcon>
                    </zwInput>
                  </zwFormItem>
                  <zwFormItem>
                    <input type="checkbox" class="pull-left"> remember me</input>
                    <a class="pull-right"> forget password</a>
                  </zwFormItem>
                  <zwFormItem>
                    <zwButton class="login-form-button">login</zwButton>
                  </zwFormItem>
                </zwForm>
              </div>
              <div slot="zwCodeBoxMeta" title="基本使用">
                基本使用
              </div>
              <div slot="zwCodeBoxPanel">

                <textarea ref="textarea" id="textarea" name="textarea">
                       <zwInput placeholder="Basic usage"></zwInput>
                         </textarea>

              </div>
            </zwBox>
          </zwCol>


          <zwCol span=12>

            <zwBox class="">
              <div slot="zwCodeBoxDemo">
                <zwForm>
                  <zwFormItem label="E-mail">

                    <zwInput>

                    </zwInput>
                  </zwFormItem>
                  <zwFormItem label="password">
                    <zwInput placeholder="password">

                    </zwInput>
                  </zwFormItem>
                  <zwFormItem label="confirm password">
                    <zwInput> </zwInput>

                  </zwFormItem>
                  <zwFormItem label="nickname">
                    <zwInput> </zwInput>
                  </zwFormItem>
                  <zwFormItem label="phone number">
                    <zwInput> </zwInput>
                  </zwFormItem>
                  <zwFormItem label="Captcha">
                    <zwInput placeholder="Basic usage">

                      <img slot="append" src=""></img>

                      <zwButton slot="append" icon="refresh" loading :loading_delay=5></zwButton>

                    </zwInput>
                  </zwFormItem>
                </zwForm>
              </div>
              <div slot="zwCodeBoxMeta" title="基本使用">
                基本使用
              </div>
              <div slot="zwCodeBoxPanel">

                <textarea ref="textarea" id="textarea" name="textarea">
                               <zwInput placeholder="Basic usage"></zwInput>
                                 </textarea>

              </div>
            </zwBox>
          </zwCol>

        </zwRow>

        <zwRow>
          <zwCol span=24>

            <zwBox class="">
              <div slot="zwCodeBoxDemo">
                <zwForm>
                  <zwFormItem label="E-mail">

                    <zwInput>

                    </zwInput>
                  </zwFormItem>
                  <zwFormItem label="店铺名称">
                    <zwInput placeholder="password">

                    </zwInput>
                  </zwFormItem>
                  <zwFormItem label="店铺介绍">
                    <zwInput> </zwInput>

                  </zwFormItem>
                  <zwFormItem label="店铺地址">
                    <zwInput> </zwInput>
                  </zwFormItem>
                  <zwFormItem label="联系电话">
                    <zwInput> </zwInput>
                  </zwFormItem>
                  <zwFormItem label="营业时间">
                    <zwInput>
                    </zwInput>
                  </zwFormItem>

                </zwForm>
              </div>
              <div slot="zwCodeBoxMeta" title="基本使用">
                基本使用
              </div>
              <div slot="zwCodeBoxPanel">

                <textarea ref="textarea" id="textarea" name="textarea">
                                               <zwInput placeholder="Basic usage"></zwInput>
                                                 </textarea>

              </div>
            </zwBox>
          </zwCol>

        </zwRow>
  <zwRow>
          <zwCol span=24>

            <zwBox class="">
              <div slot="zwCodeBoxDemo">
                <zwForm>
                  <zwFormItem  label="登录名" >

<span >阿萨德姐夫克拉;伺机待发</span>

                  </zwFormItem>

                     <zwFormItem label="登录名">
                      <zwInput name="username" :value="user.account" placeholder="登录名">

                      </zwInput>
                      </zwFormItem>
                  <zwFormItem label="密码">
                    <zwInput name="pwd" :value="user.pwd" placeholder="password">

                    </zwInput>
                  </zwFormItem>

                    <zwFormItem label="头像">
                                <zwPopup ref="popup" >  <zwImgInput @saveImg="saveImg"></zwImgInput></zwPopup>
                    </zwFormItem>

 <zwFormItem label="头像">
                    <img :src="user.face"></img>
                  </zwFormItem>
                  <zwFormItem label="头像">


<input  id="face" name="face"  value="/home/static/img/timg.jpeg" style="display:none" class="form-control input-sm"   maxlength="100"></input>
                  </zwFormItem>
                  <zwFormItem label="昵称">
                    <zwInput  name="nkname" :value="user.nkname" > </zwInput>
                  </zwFormItem>
                  <zwFormItem  name="telno" :value="user.telno" label="联系电话">
                    <zwInput> </zwInput>
                  </zwFormItem>
                  <zwFormItem label="地址">
                    <zwInput  name="address" :value="user.address">
                    </zwInput>
                  </zwFormItem>
                   <zwFormItem label="邮箱">
                      <zwInput  name="email" :value="user.email">
                      </zwInput>
                    </zwFormItem>

                </zwForm>
              </div>
              <div slot="zwCodeBoxMeta" title="基本使用">
                基本使用
              </div>
              <div slot="zwCodeBoxPanel">

                <textarea ref="textarea" id="textarea" name="textarea">
                                               <zwInput placeholder="Basic usage"></zwInput>
                                                 </textarea>

              </div>
            </zwBox>
          </zwCol>

        </zwRow>
      </section>
    </article>
  </div>
</template>
<script>
  import zwButton from '../../../component/button/zwButton.vue';
  import zwButtonGroup from '../../../component/button/zwButtonGroup.vue';
  import zwIcon from '../../../component/icon/zwIcon.vue';
  import zwRow from '../../../component/layout/zwRow.vue';
  import zwBox from '../../../component/layout/zwBox.vue';
  import zwDropDown from '../../../component/navigation/menu/zwDropDown.vue';
  import zwMenu from '../../../component/navigation/menu/zwMenu.vue';
  import zwMenuItem from '../../../component/navigation/menu/zwMenuItem.vue';
  import zwInput from '../../../component/dataentry/zwInput.vue';
  import zwForm from '../../../component/dataentry/zwForm.vue';
  import zwFormItem from '../../../component/dataentry/zwFormItem.vue';
  import zwCol from '../../../component/layout/zwCol.vue';


import zwCollapse from '../../../component/datadisplay/zwCollapse.vue';
import zwTabs from '../../../component/datadisplay/zwTabs.vue';
import zwTabPanel from '../../../component/datadisplay/zwTabPanel.vue';


 import zwImgInput from '../../../component/dataentry/zwImgInput.vue';

import zwPopup from '../../../component/datadisplay/zwPopup.vue';

  export default {
    components: {
      zwButton,zwCollapse,zwTabs,zwTabPanel,zwImgInput,zwPopup,
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
      zwFormItem
    },
    data() {

      return {
        email:"",
        user:getSessionUser(),
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

     },

    saveImg:function(src){console.log("收到saveImg")
        this.$refs.popup.hideThis();
        this.user.face = src
    }



  }

  };
</script>
<style scoped>




</style>