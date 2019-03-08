<template>
  <ul class="search-bar">
    <li><label>姓名：</label></li>
      <li>
          <Input v-model="value.userName" style="width:200px;" placeholder="姓名" clearable></Input>
        </li>
   <li><label>起止时间：</label></li>
       <li>
         <DatePicker v-model="value.checkTimeBegin" format="yyyy-MM-dd" type="date" placeholder="开始时间"
                     style="width: 200px" :options="dateOptions"></DatePicker>
       </li>
       <li>
         <DatePicker v-model="value.checkTimeEnd" format="yyyy-MM-dd" type="date" placeholder="结束时间"
                     style="width: 200px" :options="dateOptions"></DatePicker>
       </li>
    <li>
      <Button type="ghost" class="search-btn" @click="search">查询</Button>
    </li>
  </ul>
</template>
<script>
  import $http from '@/js/http2';

  export default {
    components: {
    },
    props: ['value'],
    data() {
      return {
      dateOptions: {
                disabledDate(date) {
                  // return false;
                  return date && date.valueOf() > Date.now();
                }
              },
        roleList: [],
      }
    },
    methods: {
      search() {
        this.$emit('on-search');
      },

    },
    created() {
    },
    watch: {

    }
  };
</script>
