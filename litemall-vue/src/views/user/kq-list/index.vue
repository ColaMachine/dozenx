<template>
  <div class="order_list">
<van-button type="primary" @click="showPopup">
选择月份
 </van-button>

 <van-popup   :style="{ width: '90%' }" v-model="show">
 <van-datetime-picker
                             v-model="selectDay"
                             type="year-month"
                               @confirm="confirm"
                             :formatter="formatter"
                           /></van-popup>

    <van-tabs v-model="activeIndex"
              :swipe-threshold="5"
              @click="handleTabClick">
      <van-tab v-for="(tabTitle, index) in tabTitles"
               :title="tabTitle"
               :key="index">
        <van-list v-model="loading"
                  :finished="finished"
                  :immediate-check="false"
                  finished-text="没有更多了"
                  @load="getOrderList">
          <van-panel v-for="(el, i) in orderList"
                     :key="i"
                     :title="'日期'+el.date"
                     :status="el.orderStatusText"
                   >

                    <van-tag plain
                                              style="margin-right:6px;"
                                              v-for="(goods, goodsI) in el.ary"
                                              :key="index">
                                       {{goods.title}}
                                     </van-tag>




          </van-panel>

        </van-list>

      </van-tab>
    </van-tabs>
  </div>
</template>

<script>
import { kqList } from '@/api/api';
import _ from 'lodash';


import { Tab, Tabs, Panel, Card, List, Tag ,DatetimePicker,Popup} from 'vant';



import {DateUtil} from '../../../DateUtils.js';
export default {
  name: 'order-list',

  props: {
    active: {
      type: [String, Number],
      default: 0
    }
  },
  created() {
    this.init();
  },
  data() {
    return {
      activeIndex: Number(this.active),
      tabTitles: ['考勤机', '摄像头', '迟到'],
      orderList: [],
      page: 0,
      limit: 10,
      loading: false,
      finished: false,
       selectDay:new Date(),
      dummyDay:new Date(),
        show: false
    };
  },

  methods: {
   showPopup() {
        this.show = true;
      },
  confirm(){
    this.show=false;
    this.dummyDay=this.selectDay;
    this.getOrderList();
    console.log("confirm");
  },
    formatter(type, value) {
        if (type === 'year') {
          return `${value}年`;
        } else if (type === 'month') {
          return `${value}月`
        }
        return value;
      },


    init() {
      this.page = 0;
      this.orderList = [];
      this.getOrderList();
    },
    getOrderList() {
      this.page++;
      kqList({

       STARTDATE : parseInt(DateUtil.getFirstMonthDay(this.dummyDay).getTime() / 60000),
        ENDDATE : parseInt((DateUtil.getLastMonthDay(this.dummyDay).getTime() + 24 * 60 * 60 * 1000) / 60000),
        showType: this.activeIndex,
        page: this.page,
        limit: this.limit
      }).then(res => {
        console.log("结果");
        console.log(res.data);

       //按照天组合在一起
        var dateAry = new Array();
        var nowDate;
        var nowAry=[];
        var monthDay = DateUtil.CaculateMonthDays(this.dummyDay.getYear(),this.dummyDay.getMonth()+1);
var firstDay = DateUtil.getFirstMonthDay(this.dummyDay);

        for(var i=0;i<monthDay;i++){

       var newDay =  DateUtil.DateAdd(firstDay,i);
            dateAry .push({date:newDay.format("yyyy-MM-dd"),ary:new Array()});
        }
        for(var i=0;i<res.data.data.length;i++){
            var ce = this.changeJson2CE(res.data.data[i]);
           // console.log(ce.day.getDate());
         //    console.log(dateAry[ ce.day.getDay()]);
         console.log(ce.day.getDate());
           dateAry[ ce.day.getDate()-1].ary.push(ce);
            /*if(nowDate!= ce.startDay){
                nowDate = ce.startDay;
                nowAry=[];
                dateAry.ary.push({date:nowDate,ary:nowAry});
            }*/
           // nowAry.push(ce);
          //  this.orderList.push(ce);
        }
       // this.orderList.push(...res.data.data);
        this.orderList = dateAry;
        this.loading = false;
         this.finished = true;
       // this.finished = res.data.data.page >= res.data.data.pages;
        console.log(this.orderList);
      });
    },


 changeJson2CE:function (data) {
  var ce = {};
  ce.id = data.id; //对应数据库id

  ce.title = data.title; //对应数据库标题
  ce.startDay = ce.day = new Date(data.startTime * 60000).format("yyyy-MM-dd"); //对应开始日期 yyyy-MM-dd
ce.day = new Date(data.startTime * 60000);
  ce.startTimeSV = new Date(data.startTime * 60000).format("HH:mm"); //对应开始日期 小时分钟

  ce.endTimeSV = new Date(data.endTime * 60000).format("HH:mm"); //对应结束时间 小时分钟
  ce.endDay = new Date(data.endTime * 60000).format("yyyy-MM-dd"); //对应结束日期
  console.log((data.startTime % (24 * 60) / 60) + ":" + (data.startTime % (60)));

  ce.startTime = data.startTime % (24 * 60);
  ce.endTime = data.endTime % (24 * 60);
  ce.isdel = data.isdel;
  ce.data = data;
  ce.type=data.type;
   ce.edit=data.edit;
  //{"r":0,"data":[{"isdel":false,"title":"123","id":201641653,"startTime":24347040,"endTime":24347100,"address":null,"userId":7,"description":null,"type":0,"privacy":0,"busyLevel":null}],"msg":null,"page":null,"right":true}
  return ce;
},

    handleTabClick() {
      this.page = 0;
      this.orderList = [];
      this.getOrderList();
    }

  },
  components: {
    [Tab.name]: Tab,
    [Tabs.name]: Tabs,
    [Panel.name]: Panel,
    [Card.name]: Card,
    [List.name]: List,
    [Tag.name]: Tag,
     [DatetimePicker.name]: DatetimePicker,
      [Popup.name]: Popup,


  }
};
</script>

<style lang="scss" scoped>
.order_list {
  .van-panel {
    margin-top: 20px;
  }

  .van-card {
    background-color: #fff;
  }

  .total {
    text-align: right;
    padding: 10px;
  }

  .footer_btn {
    text-align: right;
    .van-button {
      margin-left: 10px;
    }
  }
}
</style>
