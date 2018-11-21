<template>

  <div class="app-big-img">
  <img :src="src"></img>
  </img>
   </div>

</template>
<script type="text/javascript">
  import zwIcon from '../icon/zwIcon.vue';

  export default {
    name: 'zwBigImg',
    components: {
      zwIcon
    },
    props: ["src"] //"tabs"
      ,
    data() {
      return {

      };
    },
    computed: {

    },
    mounted() {


    },
    methods: {

    },

    events: {

    }
  };
</script>
<style>
.zw-big-img{
    width:100%;

}
</style>