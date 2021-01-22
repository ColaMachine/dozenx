<template>
  <div class="validate-code" @click="generateValidateCode">
    <img :src="validateCode"/>
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
