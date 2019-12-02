<template>
	<div>
		<van-cell-group>
			<van-field
				label="登录密码"
				v-model="password"
				type="password"
				placeholder="请输入登录密码"
				 />

			<van-field
				label="新手机号"
				v-model="mobile"
				placeholder="请输入新手机号"
				/>
<van-field
				label="验证码"
				v-model="code"
				@click-icon="getCode"
				placeholder="请输入验证码">


    <img @click="getCaptcha()" slot="button"  :src="captchaUrl"></img>
	</van-field>







		</van-cell-group>

		<div class="bottom_btn">
			<van-button size="large" type="danger" @click="saveMobile">保存</van-button>
		</div>
	</div>
</template>


<script>
import { authCaptcha,saveTelno,getPicCapthca } from '@/api/api';

import { Field } from 'vant';
import md5 from 'js-md5';
export default {
  data: () => ({
    password: '',
    mobile: '',
    code: '',
    captchaUrl:'',
    counting: false
  }),
    mounted(){
        this.getCaptcha();
    },
  methods: {
    getCode() {
      if (!this.counting && this.vuelidate()) {
        authCaptcha({
          mobile: this.mobile,
          type: 'bind-mobile'
        }).then(() => {
          this.$toast.success('发送成功');
          this.counting = true;
        }).catch(error => {
          this.$toast.fail(error.data.errmsg);
          this.counting = false;
        })

      }
    },
    getCaptcha(){
         getPicCapthca({

                }).then((res) => {
                //  this.$toast.success('获取验证码成功');
                  this.captchaUrl = res.data.data.img;

                }).catch(error => {
                  this.$toast.fail(error.data.msg);

                })

    },
    countdownend() {
      this.counting = false;
    },
    vuelidate() {
      if(this.mobile === ''){
        this.$toast.fail('请输入号码');
        return false;
      }
      return true;
    },
    saveMobile() {
  //  this.getCode();
          saveTelno({
          pwd:md5(this.password),
          code:this.code,
          telno: this.mobile,
          type: 'bind-mobile'
        }).then(() => {
          this.$toast.success('修改手机号成功');
          this.counting = true;
        }).catch(error => {
          this.$toast.fail(error.data.errmsg);
          this.counting = false;
        })
      console.log('保存手机号');
    }
  },

  components: {
    [Field.name]: Field
  }
};
</script>

<style lang="scss" scoped>
@import '../../../../assets/scss/var';
@import '../../../../assets/scss/mixin';
.bottom_btn {
  padding: 30px 15px 0 15px;
}

.verifi_code {
  @include one-border;
  padding-left: 10px;
  &::after {
    border-bottom: 0;
    border-left: 1px solid $border-color;
  }

  &_counting {
    color: $font-color-gray;
  }
}
</style>
