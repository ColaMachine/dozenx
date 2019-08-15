<template>
  <div>
<ShopNav></ShopNav>
<zwRow  style="margin-top:20px;">

<zwCol class="pull-right" span=24>
<zwArtical :data="data"></zwArtical>
</zwCol>

</zwRow>
 </div>

  </div>

</template>
<script>

  import zwRow from '../../component/layout/zwRow.vue';
  import ShopNav from '../shop/ShopNav.vue'
  import zwInput from '../../component/dataentry/zwInput.vue';
  import zwCol from '../../component/layout/zwCol.vue';

  import zwBase from '../../component/zwBase.vue';

  import zwAppBuyBottomBar from '../../component/datadisplay/zwAppBuyBottomBar.vue';


   import zwArtical from '../../component/datadisplay/zwArtical.vue'


  export default {
  extends:zwBase,

    components: {
    zwBase,zwArtical,ShopNav,
      zwRow,
      zwCol,

    },
    name: "PcArticalPage",
    data() {

      return {
      id:getQueryString("id"),
        data:{},


       }



    },
    created(){
 Ajax.getJSON(PATH+"/artical/view.json?id="+this.id, null, function(result) {
                               if(result.r == AJAX_SUCC && result.data) {

                                   this.data =result.data.bean;

                                   var ary =[];


                               }
                             }.Apply(this));
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