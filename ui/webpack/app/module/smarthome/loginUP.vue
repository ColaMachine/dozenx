<template>
<div>
    <zwRow>
        <zwCol class="pull-right" span=24>
            <zwAppTitle >登录</zwAppTitle>
            <zwForm id="loginForm">
            <zwFormItem>
            <zwInput id="username" name="username" placeholder="请输入用户名"></zwInput>
         </zwFormItem>

          <zwFormItem>
                     <zwInput id="password" name="password" type="password" placeholder="请输入密码"></zwInput>
                  </zwFormItem>

         <zwFormItem style="display:none">
             <zwInput id="vcode" name="vcode" placeholder="输入图片验证码">
                 <img id="vcodeImg" slot="append" src="" ></img>


                 <zwButton  v-on:clickFn="getPicCaptcha" slot="append" icon="refresh" loading :loading_delay=5 ></zwButton>

            </zwInput>

             </zwFormItem>
         </zwForm>
        <zwButton type="yellow"  v-on:clickFn="validImgCode" sizeNum="large" class="" style="width:95%">下一步</zwButton>


    </zwCol>

  </zwRow>

</div>

</template>
<script>

import zwRow from '../../component/layout/zwRow.vue';
import zwCol from '../../component/layout/zwCol.vue';
import zwIcon from '../../component/icon/zwIcon.vue';

import zwAppTitle from '../../component/datadisplay/zwAppTitle.vue';
import zwInput from '../../component/dataentry/zwInput.vue';
import zwForm from '../../component/dataentry/zwForm.vue';
import zwFormItem from '../../component/dataentry/zwFormItem.vue';
import zwButton from '../../component/button/zwButton.vue';

/**登录注册页面**/
export default {
    name:"lockPwdModify",
    components:{zwRow,zwCol,zwIcon,zwButton,zwInput,zwAppTitle,zwFormItem,zwForm
    },
    data () {

        return {
 valid:
        {
            rules : {
                phone : {
                    required : true,

                    phone : true,

                },

                vcode:{
                     required : true,
                      length : 4
                }
            },
            messages : {
                phone : {
                    required : "手机号未填写",

                    phone : "手机号码格式不正确"
                },

                vcode:{
                     required : "图片验证码未填写",
                      length : "图片验证码格式不正确"
                }
            }
    },
      }
    }
    ,
    mounted(){

        this.getPicCaptcha();//首次登录刷新
    },
    computed: {

    },

    methods:{
     getPicCaptcha:function(){
          Ajax.getJSON(PATH+"/code/img/request.json",null,function(result){//登录的时候获取验证码
                       if(result.r==AJAX_SUCC){
                        //  that.doms.picCaptchaImg.setAttribute("src","data:image/png;base64,"+result.data.imgdata);

                             document.getElementById("vcodeImg").src="data:image/png;base64,"+result.data.imgdata;
                       }else{
                           dialog.error(result.msg);
                       }
                   });
     },
     submit:function(){

     },
        validImgCode:function(){//点击下一步的时候验证图片验证码正确性
            var phone = document.getElementById("phone").value;
             var vcode = document.getElementById("vcode").value;
             console.log(phone);
             console.log(vcode);
            extend(this.valid,BaseValidator);
           var validateMachine= validator(document.getElementById("loginForm"),this.valid);

            if(! validateMachine.valid(document.getElementById("loginForm"))){
                   return;
               }


               Ajax.post(PATH + "/code/img/valid.json", {code:vcode,sessionid:phone,systemno:"calendar"}, function(data) {
                   if (data[AJAX_RESULT] == AJAX_SUCC) {
                       window.location ="#loginSmsInput?phone="+phone;
                   } else {
                   //dialog.close(dialogId);
                   //hideMask();
                   zalert(data.msg);



                   }
                   //$$("#loginBtn").removeAttribute("disabled", "");
               });
        }

    }
}
</script>
<style scoped>


</style>

