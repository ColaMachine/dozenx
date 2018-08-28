<template>
  <ul class="search-bar">
    <li><label>时间：</label></li>
    <li>
      <Date-picker type="date" placeholder="日期" format="yyyy-MM-dd" :options="dateOptions" v-model="d_value.date"></Date-picker>
    </li>
    <li>
      <Input v-model="d_value.userName" style="width:200px;" placeholder="请输入用户名" clearable></Input>
    </li>
    <li>
      <Input v-model="d_value.keywords" style="width:200px;" placeholder="请输入操作模块关键字" clearable></Input>
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
        dateOptions: {
          disabledDate(date){
            var startTime = new Date('1/1/2012');
            return (date && date.valueOf() > Date.now())||(date && date.valueOf() < startTime.getTime())
          }
        }
      }
    },
    methods: {
      searchBar() {
        var data = this.d_value;
        if(data.date == ''){
          this.$Notice.error({
            title: "请选择时间"
          });
          return false;
        }
        if(data.date instanceof Date){
          data.date = formatDate(data.date);
        }
        this.$emit('on-search');
      },
      dateFormat(day){
        return day > 9 ? day : ("0" + day);
      }
    },
    watch:{
      d_value(val){
        this.$emit('input',val);
      }
    },
    mounted(){
      var curDate = new Date();
      var beginDate = new Date(curDate.getFullYear(), curDate.getMonth(), curDate.getDate());
      this.d_value.date = [beginDate.getFullYear(),this.dateFormat(beginDate.getMonth()+1), this.dateFormat(beginDate.getDate())].join("-");
    }
  };
</script>
