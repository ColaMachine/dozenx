<template>
  <div>



<zwMap></zwMap>






  </div>

</template>
<script>

  import zwRow from '../../component/layout/zwRow.vue';
    import zwInput from '../../component/dataentry/zwInput.vue';
  import zwCol from '../../component/layout/zwCol.vue';
  import zwIcon from '../../component/icon/zwIcon.vue';
   import zwMap from '../../component/map/zwMap.vue';
  export default {
    components: {
      zwRow,zwMap,
      zwCol,
      zwIcon,

    },
    name: "map",
    data() {

      return {

      }
    },
    mounted() {console.log("map.vue");

    },
    computed: {

    },
    methods: {

    }
  }
</script>
<style scoped>

</style>