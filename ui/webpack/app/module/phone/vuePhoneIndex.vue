<template>
  <div>
    <div id="router">
      <router-view></router-view>
    </div>



  </div>




</template>
<script>


  import zwBottomBar from './bottomBar.vue';
 import zwBase from '../../component/zwBase.vue';
  export default {
    components: {
     zwBase,
zwBottomBar,

    },
    name: "phoneIndex",
    data() {

      return {

      }
    },
    mounted() {

    },
    computed: {

    },
    methods: {

    }
  }
</script>
<style scoped>

</style>