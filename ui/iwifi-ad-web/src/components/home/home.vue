<template>
  <div>
    <Layout>

      <TopBar/>
      <Layout id="content">
        <Sider id="siderLayout" width="220">
          <SideBar/>
        </Sider>
        <Layout id="contentLayout">
          <Content>
            <router-view></router-view>
          </Content>
        </Layout>

      </Layout>
    </Layout>
  </div>
</template>

<script>
  import axios from 'axios';
  import TopBar from './topBar.vue';
  import SideBar from './sideBar.vue';
  import {validateToken} from '../../js/util';

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
      }
    },
    created() {
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
      validateToken.call(this);
    }
  }
</script>
