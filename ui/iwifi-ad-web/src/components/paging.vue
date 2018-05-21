<template>
   <div class="footPage">
          <Page :current=currentPage :total=total simple @on-change="curPageChange" ></Page>
          <InputNumber :min="1" :max='max' v-model="numberValue"></InputNumber>
           <Button @click="jump">跳转</Button>
    </div>
</template>
<script>
export default {
    props:{
        'max':{
            type:[Number,String],
            default:1
        },
        'current':{
            type:[Number,String],
            default:1
        },
        'total':{
            type:[Number,String],
            default:1
        },
    },
    data () {
        return {
             numberValue:1,
             currentPage:this.current
        }
    },
   methods:{
    jump(){
         this.curPageChange(this.numberValue);
    },
    curPageChange(curPage){ 
        this.currentPage=curPage;
    }
  },
}
</script>

