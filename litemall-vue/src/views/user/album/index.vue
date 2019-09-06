<template>
  <div class="order_list">
<van-field
  readonly
  clickable
  label="所属部门"
  :value="value"
  placeholder="选择部门"
  @click="showPicker = true"
/>

<van-popup v-model="showPicker" position="bottom">
  <van-picker
    show-toolbar
    :columns="columns"
    @cancel="showPicker = false"
    @confirm="onConfirm"
  />
</van-popup>


   <van-uploader v-model="fileList" multiple :after-read="afterRead"  />
<div style="height:100px;"></div>

   <van-button size="large" type="danger" :loading="isLogining" @click="submit">提交</van-button>

  </div>
</template>

<script>
import { imageUploadIndex,faceBatchUpdate,getUsersFaceData } from '@/api/api';
import _ from 'lodash';
import { Uploader ,Button ,Toast,Picker ,Popup  ,Field} from 'vant';

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
    value:"",
     showPicker: false,
 columns: ['网金', '创业'],
      fileList: [

            ]
    };
  },

  methods: {
    onConfirm(value, index) {
     this.value = value;
        Toast(`当前值：${value}, 当前索引：${index}`);
        this.showPicker=false;
      },
    init(){

        getUsersFaceData().then(res => {
             var list =new Array();
                console.log("list结果",res.data.data);
                for(var i=0;i<res.data.data.length;i++){
                    list.push({url:"/home/"+res.data.data[i].face});
                }
                  console.log("fileList",this.fileList );
                this.fileList = list;
              }).catch(error => {
                Toast.fail(error.data.errmsg);
              });
    },
   afterRead(file) {
   console.log(this.fileList);
    //alert("afterRead"+this.fileList.length);
        // 此时可以自行将文件上传至服务器

        this.upload(file);
        console.log(file);
      },


 upload(file) {


        let fd = new FormData()
        fd.append('file', file.file)

    imageUploadIndex(fd).then(res => {
        console.log("上传结果",res);

        this.fileList[this.fileList.length-1]= { url: "/home/"+res.data.data };
        console.log("fileList",fileList);
      }).catch(error => {
        Toast.fail(error.data.errmsg);
      });
  },
  submit(){
    //提交这个fileList;
    if(!this.value){
        Toast.fail("请先选择部门!");
        return;
    }
     var  groupId =25;

    if(this.value==网金){
        groupId=26;
    }else{
        groupId=25;
    }
    var  urlList = new Array();
    for(var i=0;i<this.fileList.length;i++){
        urlList.push(this.fileList[i].url);
    }
      faceBatchUpdate({groupId:groupId,faces:urlList}).then(res => {
               console.log("上传结果",res);
                 Toast.fail("提交成功");
          }).catch(error => {
            Toast.fail(error.data.errmsg);
          });
  }
      },
  components: {

    [Uploader .name]: Uploader,
     [Button .name]: Button,
      [Picker .name]: Picker,
       [Popup .name]: Popup,
        [Field .name]: Field,

Toast
  }
};
</script>

<style lang="scss" scoped>
    .order_list{
    padding-top :50px;
    }

</style>
