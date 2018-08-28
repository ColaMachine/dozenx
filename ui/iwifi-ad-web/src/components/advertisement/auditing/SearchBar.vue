<template>
  <ul class="search-bar">
    <li><label>状态：</label></li>
    <li>
      <Select v-model="d_value.auditStatus" style="width:200px" placeholder="全部" clearable>
        <Option v-for="item in statusList" :value="item.id" :key="item.id">{{ item.name }}</Option>
      </Select>
    </li>
    <li>
      <Button type="ghost" class="search-btn" @click="searchBar">查询</Button>
    </li>
  </ul>
</template>
<script>
  import {formatDate} from "@/js/util";
  export default {
    props: {
      value: {
        type: Object,
        default () {
          return {};
        }
      }
    },
    data() {
      return {
        d_value:this.value,
        statusList:[{id:0,name:"未审核"},{id:1,name:"通过"},{id:9,name:"未通过"}],
      }
    },
    methods: {
      searchBar() {
        this.$emit('on-search');
      },
    },
    watch:{
      d_value(val){
        this.$emit('input',val);
      }
    },
    mounted(){
    }
  };
</script>
