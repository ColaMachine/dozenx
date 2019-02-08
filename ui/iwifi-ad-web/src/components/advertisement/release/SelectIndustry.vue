<template>
  <div class="industry">
       <Select v-model="industryList" clearable placeholder="请输入行业" style="width:120px" :disabled="readonly" >
            <Option v-for="item in name" :value="item.industryCode+'-'+item.industryName" :key="item.industryCode">{{ item.industryName }}</Option>
        </Select>
        <Button @click="addIndustry">添加</Button>
        <div  v-for="(Iname,index) in d_value" class="industryOne">
            <Button shape="circle"  @click="deletIndustry(index)">
                <span> {{Iname.industryName}}</span>
                <Icon size="24" color="#EE4D26" type="ios-close-empty"></Icon>
            </Button>
        </div>
  </div>
</template>
<script>
import $http from '@/js/http2.js'
export default {
    props:{
         value:{
          type:Array,
          default(){
              return []
          }
      },
      readonly:{
        type:Boolean
      }
    },
  data () {
      return {
           d_value:this.value,
            flag:true,
            industryList:[],
            name:[],
            first:true
      }
  },
  methods: {
      search(){
        this.sessionStoragePut('industry',this.d_value);
        //获取投放行业
        let _this=this
        $http.get('/home/industry/list',{
             params:{
                params:{parentCode:Number}
            }
        }).then(function(resp){
            let allIndustry ={"industryCode":0, "industryName":"全部行业"};
           _this.name.push(allIndustry , ...resp.data);
           // _this.name=resp.data;
        })
      },
       addIndustry(){
        if(this.industryList.length>0){
            this.d_value.forEach((item,index)=>{
            if(item.industryName==this.industryList.split('-')[1]){
                 this.flag=false;
            }
            })
            if(this.flag){
            let valueIndustry=this.industryList;
            this.d_value.push({
                "industryName":valueIndustry.split("-")[1],
                "industryCode":valueIndustry.split("-")[0]
                });
            this.sessionStoragePut('industry',this.d_value);
            this.industryList=[];
            }else{
            this.$Notice.error({
                    title: '不可以重复添加',
                });
            }
            this.flag=true;
      }else{
          this.$Notice.error({
              title: '请选择投放行业',
           });
      }
    },
    deletIndustry(index){
      this.d_value.splice(index,1);
      this.sessionStoragePut('industry',this.d_value);
    },
      sessionStoragePut(key,value) {
        sessionStorage.removeItem(key);
        sessionStorage.setItem(key,JSON.stringify(value));
    },
    clearIndustry(){
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
  created () {
      this.search();
  }
}
</script>

