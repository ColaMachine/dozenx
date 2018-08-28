<template>
  <div>
               <Form>
                   <FormItem label="广告编码">
                       <p v-text="id" ></p>
                   </FormItem>
                     <FormItem label="广告名称">
                       <p v-text="name" ></p>
                   </FormItem>
                    <FormItem label="尺寸" class="sizeContent" >
                        <FormItem>
                            <p v-text="height"></p >
                        </FormItem>
                          <Icon type="ios-close-empty" :size="20"></Icon>
                        <FormItem>
                            <p v-text=width></p >
                        </FormItem>
                   </FormItem>
               </Form>
  </div>
</template>
<script>
// import $http from '../../../js/http'
import $http from '@/js/http2';
export default {
  props:['id','name','width','height'],
};
</script>

