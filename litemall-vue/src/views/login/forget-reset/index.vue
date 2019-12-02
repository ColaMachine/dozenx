<template>
	<md-field-group class="foget_view">
		<md-field
			v-model="password"
			icon="lock"
			:is-error="isErrow"
			placeholder="请输入新密码"/>

		<md-field
			v-model="passwordRepeat"
			type="password"
			icon="lock"
			:is-error="isErrow"
			placeholder="请再次输入密码" />
		<div class="red" v-show="isErrow">两次密码输入不一致</div>

		<div class="foget_submit">
			<van-button size="large" type="danger" @click="submitCode">重置</van-button>
		</div>
	</md-field-group>
</template>

<script>
import { Toast } from 'vant';
import field from '@/components/field/';
import fieldGroup from '@/components/field-group/';
import { setLocalStorage,getLocalStorage } from '@/utils/local-storage';

import { pwdSmsCodeRest } from '@/api/api';
export default {
  data() {
    return {
      isErrow: false,
      password: '',
      passwordRepeat: ''
    };
  },

  methods: {
    submitCode() {
        if(this.password!= this.passwordRepeat){
            this.isError=true;
        }else{
            this.isError=false
        }
    var storage =getLocalStorage("smsCaptcha","phone");
        var data ={
        account:storage.phone,
        code:storage.smsCaptcha,
        pwd:this.password,

        };

           pwdSmsCodeRest(data).then(res => {
            if(res.data.r==0){
                 this.$router.push({ name: 'forgetStatus' });
            }else{
                 Toast.fail(error.data.msg);
            }
         }).catch(error => {
            console.log(error)
           Toast.fail(error.data.msg);
         });
    }
  },

  components: {
    [field.name]: field,
    [fieldGroup.name]: fieldGroup
  }
};
</script>

<style lang="scss" scoped>
div.foget_view {
  background-color: #fff;
  padding-top: 30px;
}

div.foget_submit {
  padding-top: 30px;
  padding-bottom: 20px;
}
</style>
