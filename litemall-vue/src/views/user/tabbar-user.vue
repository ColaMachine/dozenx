<template>
  <div class="tabbar-user">
    <user-header :isLogin="isLogin"/>
     <kq-group/>

    <user-module/>
  </div>
</template>

<script>
import userHeader from './tabbar-user-header';
import userModule from './tabbar-user-module';
import kqGroup from './tabbar-user-kq';
export default {
  data() {
    return {
      isLogin: false
    };
  },

  activated() {
    this.getLoginStatus();
  },

  methods: {
    getLoginStatus() {
      this.isLogin =
        !!localStorage.getItem('Authorization');
    }
  },

  components: {
    [userHeader.name]: userHeader,

    [userModule.name]: userModule,
     [kqGroup.name]: kqGroup
  }
};
</script>


<style scoped lang="scss">
.tabbar-user {
  > div {
    margin-bottom: 10px;
  }
  &__quit {
    border: 0;
    border-radius: 0;
  }
}
</style>
