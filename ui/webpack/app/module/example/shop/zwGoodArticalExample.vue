<template>
  <div>
    <article>
      <section>
        <h2>Input1输入框</h2>
        <p>通过鼠标或键盘输入内容，是最基础的表单域的包装。</p>
        <h3>何时使用</h3>
        <p>需要用户输入表单域内容时。 提供组合型输入框，带搜索的输入框，还可以进行大小选择。
        </p>
        <h3>代码演示<zwIcon type="windows" title="展开全部代码"></zwIcon></h3>
        <zwRow>
          <zwCol span=24>
            <zwBox class="">
              <div slot="zwCodeBoxDemo">
                 <zwGoodArtical :data="data"></zwGoodArtical>
              </div>
              <div slot="zwCodeBoxMeta" title="前缀和后缀">
              </div>
              <div slot="zwCodeBoxPanel">
                <textarea ref="textarea" id="textarea" name="textarea">
                                                         </textarea>
              </div>
            </zwBox>
          </zwCol>
          <zwCol span=12>
          </zwCol>
        </zwRow>

      </section>
    </article>
  </div>
</template>
<script>
  import Vue from 'vue';

  import zwIcon from '../../../component/icon/zwIcon.vue';
  import zwRow from '../../../component/layout/zwRow.vue';
  import zwBox from '../../../component/layout/zwBox.vue';
  import zwCol from '../../../component/layout/zwCol.vue';
  import zwGoodArtical from '../../../component/datadisplay/zwGoodArtical.vue';
  export default {
    components: {
      zwIcon,
      zwBox,
      zwRow,
      zwCol,zwGoodArtical
    },
    data() {

      return {
        data:{
            link:"https://s.click.taobao.com/t?e=m%3D2%26s%3DTvswkEpoRBEcQipKwQzePOeEDrYVVa64yK8Cckff7TVRAdhuF14FMVnRTiiEamcpxq3IhSJN6GSbBjBh3LodcDtMDMUFdHxsXFJCmqJ45rODGdnivX1PMotgwMO1YqTJ4q%2BRXokHRqt22bWK6RCIqBihUpzFc4OPTkPEI0Z1WQQdGlWrfqPtNeCTnndEcWujzdcEa848%2BG7oCHgKHProNVnXNw4RoQJURfoP8u5NmIXGJe8N%2FwNpGw%3D%3D&union_lens=lensId:0b1b4ba8_0bcd_16b88bb7317_9956",
            title:"佳偶 双层直筒玻璃杯 350ml *2件",
            updateTime:"2018-10-29 20:48:35",
            creatorName:"zhangsan",
            platform:"天猫精选",
            priceDesc:"可优惠至19元",
            outTime:"过期",
            remark:"耐热防爆，圆润美观~",
            detail:"<p itemprop=\"description\"><a itemprop=\"description\" id=\"link_1559008647335\" href=\"https://go.smzdm.com/60d47db5d96365b8/ca_aa_yh_1515_14039324_10809_0_47_322337\" target=\"_blank\" syncyh=\"false\" syncact=\"false\" rel=\"nofollow\" onclick=\"gtmAddToCart({'name':'佳偶 双层直筒玻璃杯 350ml *2件','id':'14039324' , 'price':'18','brand':'' ,'mall':'天猫精选', 'category':'日用百货/厨房用具/水具酒具/玻璃杯','metric1':'18','dimension10':'tmall.com','dimension9':'youhui','dimension11':'1阶价格','dimension12':'天猫精选','dimension20':'无','dimension32':'无','dimension25':'10809','dimension39':'直达链接','cd49':'b','cd99':'无','cd96':'无','cd58':'无'});\">天猫佳偶旗舰店</a>现有双层直筒玻璃杯售价22.8元，参加店内2件5折活动，<strong>下单2件</strong>，叠加使用<a itemprop=\"description\" id=\"link_1559012718519\" href=\"https://go.smzdm.com/4ba18192c055eaaf/ca_aa_yh_1515_14039324_10809_0_47_322337\" target=\"_blank\" syncyh=\"true\" syncact=\"false\" rel=\"nofollow\" onclick=\"gtmAddToCart({'name':'佳偶 双层直筒玻璃杯 350ml *2件','id':'14039324' , 'price':'18','brand':'' ,'mall':'天猫精选', 'category':'日用百货/厨房用具/水具酒具/玻璃杯','metric1':'18','dimension10':'tmall.com','dimension9':'youhui','dimension11':'1阶价格','dimension12':'天猫精选','dimension20':'无','dimension32':'无','dimension25':'10809','dimension39':'直达链接','cd49':'b','cd99':'无','cd96':'无','cd58':'无'});\"><strong>5元优惠券</strong></a>后，实付17.8元包邮到手，均价8.9元/只，这款玻璃杯的颜值较高，杯体圆润美观，双层玻璃设计不烫手，结实耐用，建议有需要的值友不要错过。</p>",
            img:"https://y.zdmimg.com/201905/15/5cdb7e0cc1cea6281.jpg_d250.jpg",

        }
      }
    },
    computed: {

    },
    mounted() {

    },
    methods: {
      saveContent: function(content) {
        Ajax.post("/artical/save.json", {
          type: 1,
          title: "123123",
          content: content
        }, function(result) {
          console.log(result.r);

        });
      },
      changeLoading: function() {
        console.log("123");
        this.loading = !this.loading;
        // this.loading={delay:5};
        console.log(this.loading);
      }
    }
  }
</script>
<style scoped>

</style>