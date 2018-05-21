<template>
  <div>
   <Form ref="formValidate"  :label-width="80">
        <FormItem label="广告位:">
          <p>{{v_detailId.advertSpaceName}}</p>    
        </FormItem>
        <FormItem label="投放行业:">
           <Button shape="circle" v-for="(item,index) in v_detailId.industryList" :key="index">{{item.industryName}}</Button>
        </FormItem>    
        <FormItem label="投放区域:">
          <Button shape="circle" v-for='(item,index) in v_detailId.areaList' :key="index">{{item.provinceName}}</Button>
        </FormItem>          
        <FormItem label="合作方:">
            <p>{{v_detailId.advertPartnerName}}</p>    
        </FormItem>      
        <FormItem label="起始时间:">
          <span>{{v_detailId.effectStartTime}}</span>  
          <span>{{v_detailId.effectEndTime}}</span>     
        </FormItem>   
        <FormItem label="备注:">
          <p>{{v_detailId.remark}}</p>    
        </FormItem>   
    </Form>
  </div>
</template>
<script>
import $http from '../../../js/http2'
export default {
   props: ['detailId'],
  data () {
    return {
        v_detailId:this.detailId,
    }
  },
}
</script>

