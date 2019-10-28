<template>
  <div>
    <van-cell-group>
      <van-cell title="我的考勤" isLink>
        <router-link to="/user/kq/list/0" class="text-desc">全部考勤</router-link>
      </van-cell>
    </van-cell-group>
    <van-row class="order_status">
      <van-col span="8">
        <div class="order_status_icon" @click="$router.push({path: '/user/kq/list/0'})">
          <van-icon name="daifukuan" :info="order.unpaid > 0 ? order.unpaid : ''"/>
        </div>
        <div>考勤机</div>
      </van-col>
      <van-col span="8">
        <div class="order_status_icon" @click="$router.push({path: '/user/kq/list/1'})">
          <van-icon name="daifahuo" :info="order.unship > 0 ? order.unship : ''"/>
        </div>
        <div>摄像头</div>
      </van-col>
      <van-col span="8">
        <div class="order_status_icon" @click="$router.push({path: '/user/kq/list/2'})">
          <van-icon name="wuliu" :info="order.unrecv > 0 ? order.unrecv : ''"/>
        </div>
        <div>迟到</div>
      </van-col>

    </van-row>
  </div>
</template>

<script>
import { Row, Col } from 'vant';
import { userIndex } from '@/api/api';

export default {
  name: 'kq-group',

  data() {
    return {
      order: []
    };
  },
  created() {
    this.init();
  },
  methods: {
    init() {
      userIndex().then(res => {
      //this.order = {unpaid:1,unship:1,unrecv:1}//res.data.data.order;
      });
    }
  },
  components: {
    [Row.name]: Row,
    [Col.name]: Col
  }
};
</script>


<style scoped lang="scss">
@import '../../assets/scss/mixin';
.order_status {
  background-color: #fff;
  text-align: center;
  padding: 10px 0;
  font-size: 12px;

  > div {
    @include one-border;
    &::after {
      top: 50%;
      left: 50%;
      border-bottom: 0;
      border-right: 1px solid $border-color;
      height: 150%;
      transform: scale(0.5) translate3d(-50%, -50%, 0);
      transform-origin: 0 0;
    }
    &:last-child::after {
      border: 0;
    }
  }

  .order_status_icon {
    position: relative;
    width: 36px;
    height: 36px;
    border-radius: 50%;
    display: inline-block;
    i {
      position: absolute;
      top: 50%;
      left: 50%;
      transform: translate3d(-50%, -50%, 0);
      font-size: 24px;
      color: #000;
    }
  }
}
</style>
