<template>
<div>

    <zwRow >

            <zwCol span=24>
            <zwAppTitle title="登录">登录</zwAppTitle>


        </zwCol>
  </zwRow>
    <zwRow class="fix-bottom ">

        <zwCol span=24>

            <zwButton type="yellow" v-on:clickFn="goToLoginInput" sizeNum="large" class="" style="width:95%;bottom:50px">登录/注册</zwButton>

                  <bottomBar  defaultIndex="1"></bottomBar>

    </zwCol>



  </zwRow>

</div>

</template>
<script>

import zwRow from '../../component/layout/zwRow.vue';
import zwCol from '../../component/layout/zwCol.vue';
import zwIcon from '../../component/icon/zwIcon.vue';
import zwTabs from '../../component/datadisplay/zwTabs.vue';
import zwTabPanel from '../../component/datadisplay/zwTabPanel.vue';
import zwAppTitle from '../../component/datadisplay/zwAppTitle.vue';
import bottomBar from '../smarthome/bottomBar.vue';
import zwButton from '../../component/button/zwButton.vue';

/**登录注册页面**/
export default {
    name:"lockPwdModify",
    components:{zwRow,zwCol,zwIcon,zwButton,zwTabs,zwTabPanel,bottomBar,zwAppTitle
    },
    data () {

        return {

      }
    }
    ,
    mounted(){
    //https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140842
    //https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxb34a0f3f4b91e92f&response_type=code&scope=snsapi_userinfo&state=1&redirect_uri=http://alpha-np.51iwifi.com/weixin/static/html/oauth.html&connect_redirect=1#wechat_redirect
    //https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxb34a0f3f4b91e92f&redirect_uri=http://alpha-np.51iwifi.com/weixin/static/html/oauth.html&response_type=code&scope=snsapi_userinfo&state=1#wechat_redirect
    //正在尝试自动登录 请稍后
   // window.location="https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxb34a0f3f4b91e92f&response_type=code&scope=snsapi_userinfo&state=1&redirect_uri=http://alpha-np.51iwifi.com/weixin/static/html/oauth.html#wechat_redirect";
    //alert("cookie:"+getCookie("username"));
        //
        this.loginByOpenId();
    },
    computed: {

    },
    methods:{
        loginByOpenId:function(){

            //var wxId =
        },
        goToLoginInput:function(){
        window.location="#loginInput";
        }

    }
}
</script>
<style scoped>


</style>

