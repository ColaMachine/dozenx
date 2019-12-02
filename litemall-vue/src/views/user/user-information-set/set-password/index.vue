<template>
  <div>
    <van-cell-group>
          <van-field
                label="旧密码"
                v-model="oldpwd"
                type="password"
                placeholder="请输入新密码"
              />
      <van-field
        label="新密码"
        v-model="newpwd"
        type="password"
        placeholder="请输入新密码"
      />
 <van-field
        label="新密码"
        v-model="newpwd1"
        type="password"
        placeholder="请输入新密码"
      />
    </van-cell-group>

    <div class="bottom_btn">
      <van-button size="large" type="danger" @click="modifypassword">保存</van-button>
    </div>
  </div>
</template>


<script>
import { authCaptcha, authReset, authLogout,pwdRest } from '@/api/api';
import { removeLocalStorage } from '@/utils/local-storage';
import { Field } from 'vant';
import md5 from 'js-md5';

export default {
  data: () => ({
   oldpwd: '',
    newpwd: '',
     newpwd1: '',
    mobile: '',
    code: '',
    counting: false
  }),

  methods: {
    modifypassword() {
      if (this.passwordValid()) {
        pwdRest({
          oldpwd: md5(this.oldpwd),
           newpwd: md5(this.newpwd) ,

          code: this.code
        })
        .then(() => {
          this.$dialog.alert({ message: '保存成功, 请重新登录.' })
          authLogout();
        }).catch(error => {
            this.$toast.fail(error.data.msg);

          });
      }
    },
    passwordValid() {
    if(this.newpwd!=this.newpwd1){
         this.$toast.fail("密码不相投");
        return false;
    }
      return true;
    },
    getCode() {
      if(this.mobile === ''){
        this.$toast.fail('请输入号码');
        return
      }

      if (!this.counting) {
        authCaptcha({
          mobile: this.mobile,
          type: 'change-password'
        }).then(() => {
          this.$toast.success('发送成功');
          this.counting = true;
        }).catch(error => {
          this.$toast.fail(error.data.errmsg);
          this.counting = false;
        })

      }
    },
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
