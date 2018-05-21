<template>
  <div class="validate-code" @click="generateValidateCode">
    <!--{{validateCode}}-->
    <img :src="validateCode" style="width:100%;"/>
  </div>
</template>
<script type="text/javascript">
  import $http from '@/js/http2';

  export default {
    components: {},
    props: ['url'],
    data() {
      return {
        validateCode: ''
      };
    },
    methods: {
      generateValidateCode() {
        $http.get(this.url).then((data) => {
          this.validateCode = `data:image/png;base64,${data.data.imgdata}`;
        });
        // let str = +new Date() + '';
        // this.validateCode = str.substr(str.length - 4);
      },
      getValidateCode() {
        return this.validateCode;
      }
    },
    beforeMount() {
      this.generateValidateCode();
    },
    created() {
      window.VueBus.$on('re-validate-code', () => {
        this.generateValidateCode();
      });
    }
  };
</script>
<style lang="less" scoped>
  .validate-code {
    border: 1px solid #999;
    color: #999;
    font-size: 16px;
    text-align: center;
    cursor: pointer;
    border-radius: 4px;
    display: table-cell;
    vertical-align: middle;
    height: 100%;
    img {
      vertical-align: middle;
    }
  }
</style>
