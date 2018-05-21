<template>
  <div>
    <Layout>
      <Header id="headerLayout">
        <TopBar/>
      </Header>
      <Layout id="content">
        <Sider id="siderLayout">
          <SideBar/>
        </Sider>
        <Layout id="contentLayout">
          <Content>
            <router-view></router-view>
          </Content>
          <Footer>
            footer
          </Footer>
        </Layout>
      </Layout>
    </Layout>
  </div>
</template>

<script>
  import axios from 'axios';
  import TopBar from './TopBar.vue';
  import SideBar from './SideBar.vue';
  import {validateToken} from '../js/util';

  export default {
    components: {
      TopBar,
      SideBar
    },
    data() {
      return {
        clientHeight: 0
      }
    },
    methods: {
      setClientHeight() {
        this.clientHeight = document.documentElement.clientHeight;
      },
    },
    beforeCreate() {
    },
    created() {
      console.log('Home');
      this.setClientHeight();
    },
    mounted() {
      validateToken.call(this);
      window.onresize = () => {
        window.requestAnimationFrame(() => {
          this.setClientHeight()
        });
      };
    },
    updated() {
      console.log('validate token');
      validateToken.call(this);
    }
  }
</script>
<!--<style lang="less">-->
<!--@import '../css/theme';-->

<!--#headerLayout {-->
<!--background-color: @primary-color;-->
<!--z-index: 10000;-->
<!--position: fixed;-->
<!--width: 100%;-->
<!--}-->

<!--#content {-->
<!--/*padding: 30px 80px 0 80px;*/-->
<!--}-->

<!--#siderLayout {-->
<!--/*position: fixed;*/-->
<!--height: 100vh;-->
<!--/*left: 0;*/-->
<!--overflow: auto;-->
<!--z-index: 9999;-->
<!--background-color: #fff;-->
<!--padding-top: @layout-header-height;-->
<!--}-->

<!--#contentLayout {-->
<!--padding-top: @layout-header-height;-->
<!--}-->
<!--</style>-->
