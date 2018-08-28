<template>
  <div>
      <div class="location">
       <Select v-model="locationValue.provinceId" @on-change="getCity" placeholder="省" clearable style="width:120px" ref="mySelector">
            <Option v-for="item in provinceIdOption" :value="item.id+'-'+item.areaName" :key="item.id">{{ item.areaName }}</Option>
        </Select>
        <Select v-model="locationValue.cityId" @on-change="getLocation" placeholder="市" clearable style="width:120px">
            <Option v-for="item in cityIdOption" :value="item.id+'-'+item.areaName" :key="item.id">{{ item.areaName }}</Option>
        </Select>
        <Select  v-model="locationValue.locationId" placeholder="区/县" clearable style="width:120px">
            <Option v-for="item in locationIdOption" :value="item.id+'-'+item.areaName" :key="item.id">{{ item.areaName }}</Option>
        </Select>
         <Button @click="addLocation">添加</Button>
     </div>
     <div class="industryOne industryTwo" v-for="(item,index) in d_value">
        <Button shape="circle"  @click="deletLocation(index)">
            <span>{{item.provinceName}}</span>
            <span>{{item.cityName}}</span>
            <span>{{item.countyName}}</span>
            <Icon size="24" color="#EE4D26" type="ios-close-empty"></Icon>
        </Button>
     </div>
  </div>
</template>
<script>
import $http from "../../../js/http"
export default {
  props:{
      value:{
          type:Array,
          default(){
              return []
          }
      }
  },
  data () {
      return {
          d_value:this.value,
          flag:true,
          provinceIdOption: [],
          cityIdOption: [],
          locationIdOption: [],
          locationValue:{
              provinceId:'',
              cityId:'',
              locationId:''
          }
      }
  },
  methods: {
      //获取省
      getProvince(){
          this.sessionStoragePut('locationValue',this.d_value);
          let httpConfig={
              _this:this,
              _key:'provinceIdOption'
          }
          $http.get('/advertsrv/db/location/provinces',httpConfig);

      },
      //获取市
      getCity(value){
        let valueItem = value.split("-");
        let httpConfig={
            params:{parentid:valueItem[0]},
            _this:this,
            _key:'cityIdOption'
        }
        $http.get('/advertsrv/db/location/cities',httpConfig)
      },
      //获取县
      getLocation(value){
           let valueItem = value.split("-");
           let httpConfig={
            params:{parentid:valueItem[0]},
            _this:this,
            _key:'locationIdOption'
        }
        $http.get('/advertsrv/db/location/areas',httpConfig)
      },
      //添加省市县
      addLocation(){
          //判断是否有值
          if(this.locationValue.provinceId){
              //  判断是否重复添加
              let _this=this;
              let province = this.locationValue.provinceId;
              let city = this.locationValue.cityId;
              let location = this.locationValue.locationId
              this.d_value.forEach(function(element){
                 if(element.provinceName==province.split('-')[1]&&element.cityName==city.split('-')[1]&&element.countyName==location.split('-')[1]){
                    _this.flag=false
                    _this.$Notice.error({
                        title:'不可以重复添加'
                    })
                 }
              })
              if(_this.flag){
                  this.d_value.push({
                      provinceId:province.split('-')[0],
                      provinceName:province.split('-')[1],
                      cityId:city.split('-')[0],
                      cityName:city.split('-')[1],
                      countyId:location.split('-')[0],
                      countyName:location.split('-')[1]
                  })
                   this.sessionStoragePut('locationValue',this.d_value);
                //清空选项
                this.locationValue={
                    provinceId:'',
                    cityId:'',
                    locationId:''
                }
              }
              _this.flag=true;
          }else{
              this.$Notice.error({
                  title:"请选择省份"
              })
          }
      },
      //清空省市县
      clearLocation(){
          this.d_value=[];
      },
      //删除区域
      deletLocation(index){
         this.d_value.splice(index,1);
         this.sessionStoragePut('locationValue',this.d_value);
      },
    //   存储省市
    sessionStoragePut(key,value) {
        sessionStorage.setItem(key,JSON.stringify(value))
    },
    clearArea(){
        this.d_value=[];
    }
  },
  watch:{
    value(val){
      this.d_value=val;
    },
    d_value(val){
      this.$emit('input',val);
    }
  },

  mounted () {
      this.getProvince();
  }
}
</script>

