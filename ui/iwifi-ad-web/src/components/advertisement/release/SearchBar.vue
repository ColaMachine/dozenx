<template>
  <ul class="search-bar">
    <li>
      <label>客户：</label>
    </li>
    <li>
      <Select v-model="value.createUser" style="width:200px;" placeholder="全部" clearable>
        <Option v-for="item in partnerList" :value="item.id" :key="item.id">{{ item.account }}</Option>
      </Select>
    </li>
    <li>
      <Input v-model="value.name" style="width:200px;" placeholder="策略名称" clearable></Input>
    </li>
    <li>
      <Button type="ghost" class="search-btn" @click="search">查询</Button>
    </li>
  </ul>
</template>
<script>
  import $http from '@/js/http2';

  export default {
    props: ['value'],
    data() {
      return {
        partnerList: []
      };
    },
    methods: {
      search() {
        console.log('value', this.value);
        this.$emit('on-search');
      },
      getPartnerList() {
        $http.get('/home/sys/auth/user/select').then(({data}) => {
          this.partnerList = data;
        });
      }
    },
    mounted() {
      this.getPartnerList();
    }
  };

</script>
