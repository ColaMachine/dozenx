<template>
<div>

<zwAppTopBar >
                    <a slot="left" :href="getPathValue('/static/html/PhoneCalendarView.html')">
                  <img  class=""  :src="getPathValue('/static/img/header/back.png')">
                  </a>
                <span slot="middle"> 个人信息修改</span>


                </zwAppTopBar>
    <zwRow style="margin-top:30px;">
        <zwCol  span=24>
          <zwForm style="margin-top:22px; padding-top:30px">
           <zwFormItem label="头像">
           <div style="width:50%;float:right;margin-right:0px" >
           <zwUploadImg  v-model="user.face" ></zwUploadImg>
                <!--<input  id="face" name="face"  v-model="user.face"   style="display:none" class="form-control input-sm"   maxlength="100"></input>-->

                </div>
           </zwFormItem>
           <zwFormItem label="昵称">
             <zwInput id="nick" name="nick"  v-model="user.nick"   > </zwInput>
           </zwFormItem>
            <zwFormItem label="性别">
                &nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" v-model="user.sex"   id="sex1" name="sex" value=1>男</input >
                 &nbsp; &nbsp;&nbsp; <input  id="sex2" name="sex" type="radio" v-model="user.sex"   value=2>女</input >
              </zwFormItem>
           <zwFormItem label="生日">
             <zwInput id="birthday" name="birthday" type="date"  v-model="user.birth"> </zwInput>
           </zwFormItem>
           <zwFormItem label="地址">
             <zwInput id="address" name="address"  v-model="user.address">
             </zwInput>
           </zwFormItem>
           <zwFormItem style="padding-left:20px;padding-bottom:25px">
            <zwButton type="yellow"  v-on:clickFn="save" sizeNum="large"  style="width:95%">保存</zwButton>

          </zwFormItem>
         </zwForm>
    </zwCol>

  </zwRow>
  <zwRow >
  <zwCol style="margin-top:50px" class="center" span=24>


   </zwCol>



  </zwRow>

</div>

</template>
<script>

import zwRow from '../../component/layout/zwRow.vue';
import zwCol from '../../component/layout/zwCol.vue';
import zwIcon from '../../component/icon/zwIcon.vue';
import zwAppTopBar from '../../component/navigation/zwAppTopBar.vue';

import zwAppTitle from '../../component/datadisplay/zwAppTitle.vue';
import zwInput from '../../component/dataentry/zwInput.vue';
import zwForm from '../../component/dataentry/zwForm.vue';
import zwFormItem from '../../component/dataentry/zwFormItem.vue';
import zwButton from '../../component/button/zwButton.vue';
import zwChocolatePanel from '../../component/datadisplay/zwChocolatePanel.vue';
import zwTabs from '../../component/datadisplay/zwTabs.vue';
import zwTabPanel from '../../component/datadisplay/zwTabPanel.vue';
import zwUploadImg from '../../component/dataentry/zwUploadImg.vue';
 import zwBottomBar from './bottomBar.vue';
/**登录注册页面**/
export default {
    name:"lockPwdModify",
    components:{zwRow,zwCol,zwIcon,zwButton,zwInput,zwAppTitle,zwFormItem,zwForm,zwChocolatePanel,zwTabs,zwTabPanel,zwBottomBar,zwUploadImg,zwAppTopBar
    },
    data () {

        return {
        user:{},
 list:[
            {type:"shopping-cart",url:"",name:"业务订购"},
            {type:"suitcase",url:"",name:"业务查询"},
            {type:"history",url:"#deviceMsgList",name:"历史查询"},

            ]
      }
    }
    ,
    beforeCreate(){
      Ajax.getJSON(PATH+"/user/info.json",{},function(result){
                ajaxResultHandler(result);
                if(result[AJAX_RESULT] == AJAX_SUCC){
                    this.user=result.data||{};//alert(this.user.nick);
                }else{
                    //alert(result.msg);
                }
            }.Apply(this));
    }
    ,
    mounted(){
        var that =this;
        // var imageUtil=new zImageUtil5({"input":"face",callback:function(result){that.user.face = result.data}});
    },
    computed: {

    },

    methods:{
    changeFace:function(img){alert("changed");
        this.user.face =""+img;
    },
        getImg:function(img){
            return PATH+img;
        },
        logout:function(){
             Ajax.get(PATH+"/sys/auth/logout.json",{},function(){
               window.location=PATH+"/sys/auth/login.htm?pre="+encodeURIComponent(window.location);

             });

           // window.location="#loginSmsStep1";

        },
        getPathValue:function(value){
                        return getPathValue(value);
                    },
         save:function(){
           /* var address= $$("address").value;
             var birthday= $$("birthday").value;
            var nick = $$("nick").value;
            var sex =getRadioValueByName("sex");*/
            var json=this.user;//{birthday:birthday,address:address,nick:nick,sex:sex,face:$$("#face").value};
            console.log(json);
             Ajax.post(PATH+"/user/face/update",json,function(result){
                if (result.r==AJAX_SUCC) {
                			dialog.alert("保存成功");
                	} else {
                		dialog.alert(result.msg);
                	}

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
.zw-form-item{
    border-bottom:1px solid #ecdcdc;
}

</style>

