<template>
	<div class="login">
    	<div class="store_header">
		<div class="store_avatar">
			<img src="../../assets/images/avatar_default.png" alt="头像" width="55" height="55">
		</div>
		<div class="store_name">考勤系统</div>
	</div>

    <md-field-group>
      <md-field
        v-model="account"
        icon="username"
        placeholder="请输入你的姓名"
        right-icon="clear-full"
        name="user"
        data-vv-as="帐号"
        @right-click="clearText"
      />

      <md-field
        v-model="password"
        icon="lock"
        placeholder="请输入密码"
        :type="visiblePass ? 'text' : 'password'"
        :right-icon="visiblePass ? 'eye-open' : 'eye-close'"
        data-vv-as="密码"
        name="password"
        @right-click="visiblePass = !visiblePass"
      />

      <div class="clearfix">
        <div class="float-l">
          <router-link to="/login/registerGetCode">免费注册</router-link>
        </div>
        <div class="float-r">
          <router-link to="/login/forget">忘记密码</router-link>
        </div>
      </div>

      <van-button size="large" type="danger" :loading="isLogining" @click="loginSubmit">登录</van-button>
    </md-field-group>


      <div class="text-desc text-center bottom_positon">技术支持: 371452875@qq.com</div>

	</div>
</template>

<script>
import field from '@/components/field/';
import fieldGroup from '@/components/field-group/';

import { authLoginByAccount,getWxInitData } from '@/api/api';
import { setLocalStorage,getLocalStorage } from '@/utils/local-storage';
import { emailReg, mobileReg } from '@/utils/validate';

import { Toast } from 'vant';


export default {
  name: 'login-request',
  components: {
    [field.name]: field,
    [fieldGroup.name]: fieldGroup,
    Toast
  },
  data() {
    return {
      account: '',
      password: '',
      visiblePass: false,
      isLogining: false,
      userInfo: {},
      debug:true,
    };
  },
 mounted(){
       // https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxc38e214e9732a3a9&redirect_uri=http://http://192.168.120.13:6255/#/login/
      /*  let recaptchaScript = document.createElement('script')
        recaptchaScript.setAttribute('src', 'https://res.wx.qq.com/open/js/jweixin-1.0.0.js')
        document.head.appendChild(recaptchaScript);
        this.loginByOpenId();*/

        var storageData  =    getLocalStorage("userName","password");
        console.log(storageData);
        var userName = storageData.userName;
        var password =storageData.password;

        this.account = userName;
        this.password =password;

    },
  methods: {
    clearText() {
      this.account = '';
    },

    validate() {

    },

    loginByOpenId:function(){
      getWxInitData({url:this.location}).then(res => {
          this. initWx(res.data);
         }).catch(error => {
            console.log(error)
           Toast.fail(error.data.msg);
         });
          this.debug =true;
    },
    login() {
      let loginData = this.getLoginData();
      authLoginByAccount(loginData).then(res => {
        this.userInfo = res.data.data;
        this.userInfo.nickName = this.userInfo.username;
        setLocalStorage({
          Authorization: res.data.data.id,
          avatar: this.userInfo.face,
          nickName: this.userInfo.nkname,
          userName:this.userInfo.username,
          password:this.password
        });

        this.routerRedirect();
      }).catch(error => {
        Toast.fail(error.data.msg);
      });
    },

    loginSubmit() {
      this.isLogining = true;
      try {
        this.validate();
        this.login();
        this.isLogining = false;
      } catch (err) {
        console.log(err.message);
        this.isLogining = false;
      }
    },

    routerRedirect() {
      // const { query } = this.$route;
      // this.$router.replace({
      //   name: query.redirect || 'home',
      //   query: query
      // });
      window.location = '#/user/';
    },

    getLoginData() {
      const password = this.password;
      const account =this.account;// this.getUserType(this.account);
      return {
        username: this.account,
        password: this.password
      };
    },

    getUserType(account) {
      const accountType = mobileReg.test(account)
        ? 'mobile'
        : emailReg.test(account)
        ? 'email'
        : 'username';
      return accountType;
    }

    ,



 initWx:function(data){

    //说明文档https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141115
    //https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141115
    //步骤二：引入JS文件

    //步骤三：通过config接口注入权限验证配置
    //alert(data.signature);
    console.log(data);
    wx.config({

        debug: this.debug, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。

        appId: data.appId, // 必填，公众号的唯一标识

        timestamp: data.timestamp, // 必填，生成签名的时间戳

        nonceStr: data.noncestr, // 必填，生成签名的随机串

        signature:data.signature,// 必填，签名，见附录1

        jsApiList: ['onMenuShareTimeline', 'onMenuShareAppMessage',
              'onMenuShareQQ',
              'onMenuShareWeibo',''] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2

    });
    //步骤四：通过ready接口处理成功验证
var json = {
            title: '${article.title}', // 分享标题
            desc: '${article.remark}', // 分享描述
            link: 'http://www.dapark.top/home/article/viewpage/${article.id}', // 分享链接，该链接域名或路径必须与当前页面对应的公
            imgUrl: 'http://www.dapark.top/home/${article.pic}', // 分享图标

 trigger: function (res) {
                // 不要尝试在trigger中使用ajax异步请求修改本次分享的内容，因为客户端分享操作是一个同步操作，这时候使用ajax的回包会还没有返回
  if(this.debug)              alert('用户点击分享到朋友圈');
            },
            success: function (res) {
          if(this.debug)      alert('已分享');
            },
            cancel: function (res) {
                if(debug)alert('已取消');
            },
            fail: function (res) {
if(this.debug)                alert(JSON.stringify(res));
            }
            };

    wx.ready(function(){

    wx.getUserInfo({
       success:function(res) {
       alert("微信获取用户信息结果"+res.userInfo.nickName);

       /*that.setData({

         nickName: res.userInfo.nickName,
         avatarUrl: res.userInfo.avatarUrl,
       })*/
       },
    });
      wx.onMenuShareAppMessage(json);

        wx.onMenuShareTimeline({
            title: '${article.title}', // 分享标题
            desc: '${article.remark}', // 分享描述
            link: 'http://www.dapark.top/home/article/viewpage/${article.id}', // 分享链接，该链接域名或路径必须与当前页面对应的公众号JS安全域名一致
            imgUrl: 'http://www.dapark.top/home/${article.pic}', // 分享图标
            success: function () {
              if(this.debug)alert("设置成功");
              // 设置成功
            }
            });

            if(this.debug)
             alert("微信调用成功");
        // config信息验证后会执行ready方法，所有接口调用都必须在config接口获得结果之后，config是一个客户端的异步操作，所以如果需要在页面加载时就调用相关接口，则须把相关接口放在ready函数中调用来确保正确执行。对于用户触发时才调用的接口，则可以直接调用，不需要放在ready函数中。

    });
    //步骤五：通过error接口处理失败验证

    wx.error(function(res){
        alert("微信调用失败");
        // config信息验证失败会执行error函数，如签名过期导致验证失败，具体错误信息可以打开config的debug模式查看，也可以在返回的res参数中查看，对于SPA可以在这里更新签名。

    });

}
  }
};
</script>


<style lang="scss" scoped>
@import '../../assets/scss/mixin';
.login {
  position: relative;
  background-color: #fff;
}
.store_header {
  text-align: center;
  padding: 30px 0;
  .store_avatar img {
    border-radius: 50%;
  }
  .store_name {
    padding-top: 5px;
    font-size: 16px;
  }
}
.register {
  padding-top: 40px;
  color: $font-color-gray;
  a {
    color: $font-color-gray;
  }
  > div {
    width: 50%;
    box-sizing: border-box;
    padding: 0 20px;
  }
  .connect {
    @include one-border(right);
    text-align: right;
  }
}
.bottom_positon {
  position: absolute;
  bottom: 30px;
  width: 100%;
}
</style>
