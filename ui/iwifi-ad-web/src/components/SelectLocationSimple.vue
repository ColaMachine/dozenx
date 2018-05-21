<template>
  <div class="select-location-simple">
    <Select v-model="location[0]" placeholder="省" @on-change="getCity" clearable style="min-width:120px;"
            class="simple-select" :disabled="disabled">
      <Option v-for="pro in provinceList" :value="pro.id" :key="pro.id">{{pro.areaName}}</Option>
    </Select>
    <Select v-model="location[1]" placeholder="市" @on-change="getArea" clearable style="min-width:120px;"
            class="simple-select" :disabled="disabled">
      <Option v-for="city in cityList" :value="city.id" :key="city.id">{{city.areaName}}</Option>
    </Select>
    <Select v-model="location[2]" placeholder="区/县" clearable style="min-width:120px;" class="simple-select"
            :disabled="disabled">
      <Option v-for="area in areaList" :value="area.id" :key="area.id">{{area.areaName}}</Option>
    </Select>
  </div>
</template>

<script>
  import $http from '@/js/http2';

  export default {
    name: "select-location-simple",
    props: {
      value: Array,
      disabled: Boolean
    },
    data() {
      return {
        provinceList: [],
        cityList: [],
        areaList: [],
        location: this.value || []
      }
    },
    methods: {
      getProvince() {
        console.log('getProvince');
        $http.get('/advertsrv/location/provinces').then((data) => {
          this.provinceList = data.data;
        });
      },
      getCity() {
        console.log('getCity');
        if (!this.location[0]) {
          this.location[1] = '';
          this.cityList = [];
          return;
        }
        $http.get('/advertsrv/location/cities', {
          params: {
            parentid: this.location[0]
          }
        }).then((data) => {
          this.cityList = data.data;
        });
      },
      getArea() {
        console.log('getArea');
        if (!this.location[1]) {
          this.location[2] = '';
          this.areaList = [];
          return;
        }
        $http.get('/advertsrv/location/areas', {
          params: {
            parentid: this.location[1]
          }
        }).then((data) => {
          this.areaList = data.data;
        });
      }
    },
    mounted() {
      this.getProvince();
      if (this.location[0]) {
        this.getCity();
      }
      if (this.location[1]) {
        this.getArea();
      }
    },
    watch: {
      location(val, old) {
        console.log('watch location', val);
        this.$emit('input', val);
      },
    }
  }
</script>

<style lang="less">
  .select-location-simple {
    display: flex;
    justify-content: space-between;
    align-items: center;
    > .simple-select + .simple-select {
      margin-left: 10px;
    }
  }
</style>
