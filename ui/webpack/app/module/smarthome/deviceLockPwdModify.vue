<template>
<div>
     <zwRow style="background-color:white">

            <zwCol style="color:black;padding-left:15px;" class="pull-right" span=24>
                      <zwAppTitle >添加密码</zwAppTitle>



                     <zwForm id="loginForm">

      <zwFormItem>
                                              <span style="font-size:17px;background-color:#e5e5e5;padding-bottom:3px;">临时密码</span>

                                         </zwFormItem>
                                    <zwFormItem>
                                          <zwInput  id="tempPwd" name="vcode" placeholder="输入密码">

                                            </zwInput>
                                     </zwFormItem>
                                     <zwFormItem>
                                         <zwInput  id="pwdEffectTime" name="vcode" placeholder="输入密码有效时长">

  <select id="pwdEffectTimeUnit" style="" slot="append" >
                                <option value="minute">分</option>
                              <option value="hour">小时</option>
                               <option value="day">天</option>
                               <option value="month">月</option>
                               <option value="year">年</option>
                              </select>
                                        </zwInput>

                                     </zwFormItem>
                                 </zwForm>





                <zwButton class="fix-bottom" type="yellow" v-on:clickFn="modifyPwd" style="width:89%;" >一键修改</zwButton>

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

export default {
    name:"deviceLockPwdModify",
      components:{zwRow,zwCol,zwIcon,zwButton,zwInput,zwAppTitle,zwFormItem,zwForm
        },
    data () {

        return {
            entity:{}
        }
    }
    ,
    mounted(){

    },
    computed: {

    },
    methods:{

        modifyPwd:function(){
            var inputValue = document.getElementById("tempPwd").value;
            var times =document.getElementById("pwdEffectTime").value;

            var myselect=document.getElementById("pwdEffectTimeUnit");

            var index=myselect.selectedIndex ; // selectedIndex代表的是你所选中项的index

            //3:拿到选中项options的value：
             var pwdEffectTimeUnit=myselect.options[index].value;

            //alert(pwdEffectTimeUnit);

            if(!inputValue){
                             alert("请输入密码");
                             return ;
                    }
                    if(inputValue.length!=8){
                                     alert("请输入8位密码");
                                     return ;
                            }
                    if( !isPositiveInteger(inputValue.substr(2))){


                        alert("请输入数字密码");
                        return;
                    }

                    /*
            if(inputValue &&inputValue.length==8&& isNumber(inputValue)){

            }else{
                alert("请输入8位数字密码");
                return;
            }*/
            if(inputValue.substr(0,2)!="11"){
                alert("临时密码开头必须是11");
                return;
            }

           Ajax.post( PATH+"/smart/home/device/setPwdRemoteCmd",
           {
            deviceId:getDiyQueryString("deviceId"),
            wifiId:getDiyQueryString("wifiId"),
            type:pwdEffectTimeUnit,
            pwd:inputValue,
            timeLong:times,

           },
           function(result){
                  if(result.r==AJAX_SUCC){
                    alert("修改成功");
                 }else{
                    alert(result.msg);
                 }
           }.Apply(this));


        }

    }
}
</script>
<style scoped>


</style>

