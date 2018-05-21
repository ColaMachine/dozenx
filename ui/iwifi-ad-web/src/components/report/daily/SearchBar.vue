<template>
  <ul class="search-bar">
    <li><label>合作平台：</label></li>
    <li>
      <Select v-model="value.advertPartnerName" placeholder="全部" style="width:200px" clearable>
        <Option v-for="item in platformList" :value="item.platformName" :key="item.id">{{ item.platformName }}</Option>
      </Select>
    </li>
    <li><label>起止时间：</label></li>
    <li>
      <DatePicker v-model="value.startTime" format="yyyy-MM-dd" type="date" placeholder="开始时间"
                  style="width: 200px" :options="dateOptions"></DatePicker>
    </li>
    <li>
      <DatePicker v-model="value.endTime" format="yyyy-MM-dd" type="date" placeholder="结束时间"
                  style="width: 200px" :options="dateOptions"></DatePicker>
    </li>
    <!--<li><label>地区：</label></li>-->
    <!--<li></li>-->
    <!--<li><label>行业：</label></li>-->
    <!--<li>-->
    <!--<Select v-model="value.industry" placeholder="全部" style="width:120px">-->
    <!--<Option v-for="item in industryList" :value="item.value" :key="item.value">{{ item.label }}</Option>-->
    <!--</Select>-->
    <!--</li>-->
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
        // value: this.propValue,
        dateOptions: {
          disabledDate(date) {
            return date && date.valueOf() > Date.now();
          }
        },
        platformList: [],
      }
    },
    methods: {
      search() {
        // console.log('search', this.value);
        let st = this.value.startTime, et = this.value.endTime;
        if (st && et) {
          if (st.valueOf() > et.valueOf()) {
            this.$Notice.warning({
              title: '开始时间不得大于结束时间'
            });
            return;
          } else if (et.valueOf() - st.valueOf() > 1000 * 60 * 60 * 24 * 365) {
            this.$Notice.warning({
              title: '起止时间范围不得超过一年'
            });
            return;
          }
        } else if (!st && !et) {
        } else {
          this.$Notice.warning({
            title: '起止时间只能同时选择或者不选'
          });
          return;
        }
        this.$emit('on-search');
      },
      getPlatformList() {
        $http.get('/advertsrv/partner/platform/list').then(({data}) => {
          this.platformList = data;
        });
      }
    },
    created() {
      this.getPlatformList();
    },
    // watch: {
    //   //actually this watcher does not work,
    //   // but you can make it work by using deep config.
    //   value(val) {
    //     this.$emit('input', val);
    //   }
    // }
  };
</script>
