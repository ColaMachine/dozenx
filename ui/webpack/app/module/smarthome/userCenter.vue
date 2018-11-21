<template>
<div>
    <zwRow>
        <zwCol  span=24>
            <div  style="background-color:yellow" class="mubu smart-home-device-view-head-bg" >
                <div class="center-block ">
                    <div class="user-circle">

                        <img :src='getImg("/static/img/smarthome/个人中心1.png")' ></img>

                    </div>
                     <h3>
                            {{user?user.userName:""}}     {{user?user.telno:""}}
                    </h3>
                </div>
            </div>
            <center style="margin-top:-90px;background-color:white;">
            <zwChocolatePanel  class="chocolate" style="width:80%;background-color:white;border-radius:15px;" :data="list"></zwChocolatePanel>
            </center>


    </zwCol>

  </zwRow>
  <zwRow >
 <zwCol style="margin-top:50px" class="center" v-if="user" span=24>
  <zwButton type="yellow"  v-on:clickFn="logout" sizeNum="large"  style="width:95%">退出登录</zwButton>

 </zwCol>

 <zwCol  class="center" style="margin-top:50px" v-if="!user" span=24>
   <zwButton type="yellow"  v-on:clickFn="login" sizeNum="large"  style="width:95%">登录</zwButton>

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
import zwChocolatePanel from '../../component/datadisplay/zwChocolatePanel.vue';
import zwTabs from '../../component/datadisplay/zwTabs.vue';
import zwTabPanel from '../../component/datadisplay/zwTabPanel.vue';
import bottomBar from '../smarthome/bottomBar.vue';
/**登录注册页面**/
export default {
    name:"lockPwdModify",
    components:{zwRow,zwCol,zwIcon,zwButton,zwInput,zwAppTitle,zwFormItem,zwForm,zwChocolatePanel,zwTabs,zwTabPanel,bottomBar
    },
    data () {

        return {
        user:null,
 list:[
            {type:"shopping-cart",url:"",name:"业务订购"},
            {type:"suitcase",url:"",name:"业务查询"},
            {type:"history",url:"#deviceMsgList",name:"历史查询"},

            ]
      }
    }
    ,
    mounted(){
        Ajax.getJSON(PATH+"/user/info.json",{},function(result){
            ajaxResultHandler(result);
            if(result[AJAX_RESULT] == AJAX_SUCC){
                this.user=result.data;
            }else{
                //alert(result.msg);
            }
        }.Apply(this));
    },
    computed: {

    },

    methods:{
        getImg:function(img){
            return PATH+img;
        },
        logout:function(){
             Ajax.get(PATH+"/sys/auth/logout.json",{},function(){
               window.location=PATH+"/sys/auth/login.htm?pre="+encodeURIComponent(window.location);

             });

           // window.location="#loginSmsStep1";

        },
        login:function(){
            window.location=PATH+"/sys/auth/login.htm?pre="+encodeURIComponent(window.location);
        }

    }
}
</script>
<style scoped>
.mubu{
    height:300px;
    color:gray;
    text-align:center;
    position:relative;
}
.user-circle{
    display:inline-block;

    width:30px;
      height:30px;


      border-radius:15px;
       text-align:center;
      padding:10px;
      background-color:white;
}

.user-circle img{
       width:30px;
        height:30px;
}
.center-block{
padding-top:80px;
text-align:center;
}
.chocolate li{
width:30% !important;
}
.zw-icon-list li{
width:30% !important;
}
</style>

