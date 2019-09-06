<template>
  <div class="user_module">
    <van-cell-group>
      <van-cell icon="shoucang" title="我的人脸" to="/user/album" isLink/>

      <van-cell icon="kefu" title="服务中心" to="/user/server" isLink/>
    </van-cell-group>
  </div>
</template>

<script>
export default {
  name: 'user-module'
};
</script>


<style scoped lang="scss">
.user_module {
  background-color: #fff;
}
</style>
