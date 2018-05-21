<template>
  <div class="serach">
    <span>合作方：</span>
        <Select v-model="d_name.advertPartnerId" style="width:200px" clearable>
          <Option v-for="item in cityList" :value="item.id" :key="item.id">{{ item.platformName }}</Option>
        </Select>
          <Button @click="joinSearch">查询</Button>
  </div>
</template>
<script>
import $http from '@/js/http2'
// import $http from '../../../js/'
export default {
    props:{
    value:{
      type :Object,
      default(){
        return {}
      }
    }
  },
  data () {
    return {
       d_name : this.value ,
       cityList:[]
    }
  },
    methods: {
      search(){
        let _this=this;
         $http.get("/advertsrv/partner/platform/list").then(function(resp){
           _this.cityList=resp.data;
      })
      },
      joinSearch(){
        this.$emit('on-click');
      }
  },
  mounted (){
    this.search();
  }
 
}
</script>

