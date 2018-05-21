<template>
<div>
    <zwRow>
        <zwCol class="pull-right" span=24>
            <zwAppTitle tips="输入电信手机号">验证码已发送至</zwAppTitle>
            <zwForm id="loginForm">
                <zwFormItem>
                    <span class="zw-input-wrapper">{{phone}}</span>
                 </zwFormItem>
                 <zwFormItem>
                     <zwInput id="vcode" name="vcode" placeholder="输入短信验证码">
                         <zwButton  v-on:clickFn="getSmsCaptcha" slot="append" icon="refresh" loading :loading_delay=60 ></zwButton>

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
        phone:'139',
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
        this.phone = getDiyQueryString("phone");
        this.getSmsCaptcha();//首次刷新
    },
    computed: {

    },

    methods:{
     getSmsCaptcha:function(){

          Ajax.post(PATH+"/sys/auth/login/sms/request.json",{"phone":this.phone},function(result){//登录的时候获取验证码
               if(result.r==AJAX_SUCC){
                    dialog.alert("短信发送成功");
               }else{
                   dialog.error(result.msg);
               }
           });
     },
     submit:function(){

     },
        validImgCode:function(){//点击下一步的时候验证图片验证码正确性
            var phone = this.phone
             var vcode = document.getElementById("vcode").value;

             console.log(phone);
             console.log(vcode);


            if(!(vcode && vcode.length==4)){
                  dialog.error("验证码格式不正确");
               }


               Ajax.post(PATH + "/sys/auth/loginPost2.json", {code:vcode,"phone":phone,systemno:"calendar"}, function(data) {
                   if (data[AJAX_RESULT] == AJAX_SUCC) {
                        setCookie('username', phone, 365);
                       window.location ="#zhihuijiatingExample";


                   } else {
                       //dialog.close(dialogId);
                       //hideMask();
                       alert(data.msg);
                        dialogue.error(data.msg);



                   }
                        //$$("#loginBtn").removeAttribute("disabled", "");
               });
        }

    }
}
</script>
<style scoped>


</style>

