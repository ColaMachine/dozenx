<template>
  <ul class="search-bar">
    <li><label>角色：</label></li>
    <li>
      <Select v-model="value.roleId" style="width:200px" clearable>
        <Option v-for="item in roleList" :value="item.id" :key="item.id">{{ item.name }}</Option>
      </Select>
    </li>
    <li>
      <label>地区：</label>
    </li>
    <li>
      <SelectLocation v-model="value.location"/>
    </li>
    <li>
      <Input v-model="value.name" style="width:200px;" placeholder="请输入账号关键字" clearable></Input>
    </li>
    <li>
      <Button type="ghost" class="search-btn" @click="search">查询</Button>
    </li>
  </ul>
</template>
<script>
  import $http from '@/js/http2';
  import SelectLocation from '@/components/SelectLocationSimple.vue';

  export default {
    components: {
      SelectLocation
    },
    props: ['value'],
    data() {
      return {
        roleList: [],
      }
    },
    methods: {
      search() {
        this.$emit('on-search');
      },
      getRoleList() {
        $http.get('/advertsrv/sys/auth/role/drop/list', {
          params: {
            params:
              {
                curPage: 1,
                pageSize: 1000
              }
          }
        }).then((data) => {
          this.roleList = data.data;
        });
      }
    },
    created() {
      this.getRoleList();
    },
    watch: {
      'value.location': {
        // deep: true,
        handler(newVal) {
          // console.log('value.location', newVal);
          this.value.province = newVal[0];
          this.value.city = newVal[1];
          this.value.county = newVal[2];
        }
      }
    }
  };
</script>
