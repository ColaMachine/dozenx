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
                <zwForm layout="inline" onSubmit={this.handleSubmit}>
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
  export default {
    components: {
      zwButton,
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
<style scoped>

</style>