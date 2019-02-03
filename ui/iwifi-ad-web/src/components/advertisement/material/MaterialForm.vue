<template>
  <div>
    <Form ref="form" :model="formData" :rules="readonly?{}:formRules" :label-width="120">
      <FormItem prop="name" label="素材名称" required>
        <Input v-model="formData.name" placeholder="素材名称" :disabled="readonly"></Input>
      </FormItem>
        <FormItem prop="size" label="素材尺寸" required>
          <Select v-model="formData.size" :disabled="readonly" placeholder="素材尺寸" style="width:100%;">
            <Option v-for="item in widthList" :value="item.id" :key="item.id">{{ item.label }}</Option>
          </Select>
        </FormItem>
      <FormItem prop="type" label="素材类型">
        <Select v-model="formData.type" style="height:auto;" :disabled="readonly" placeholder="素材类型">
          <Option v-for="item in typeList" :value="item.id" :key="item.id">{{ item.label }}</Option>
        </Select>
      </FormItem>
      <Row  v-if="formData.type == '1'">
        <Col span="19">
        <FormItem prop="imgUrl" label="素材地址">
          <Input v-model="formData.imgUrl" placeholder="素材地址" :disabled="readonly"></Input>
        </FormItem>
        </Col>
        <Col span="1">&nbsp;</Col>
        <Col span="4" v-if="!readonly">
        <!--<Button type="default" size="large" @click="uploadImg(formData.imgUrl)" long>上传</Button>-->
        <ImgUploader :name="uploaderName" :action="uploadAction" @on-success="uploadSuccess" ></ImgUploader>
        </Col>
      </Row>
      <FormItem prop="jumpUrl" label="跳转地址">
        <Input v-model="formData.jumpUrl" placeholder="跳转地址" :disabled="readonly"></Input>
      </FormItem>
      <FormItem prop="auditStatus" label="状态" v-if="readonly">
        <Select v-model="formData.auditStatus" style="height:auto;" :disabled="readonly">
          <Option v-for="item in statusList" :value="item.id" :key="item.id">{{ item.name }}</Option>
        </Select>
      </FormItem>
      <Row v-if="!readonly">
        <Col span="8">&nbsp;</Col>
        <Col span="8">
        <Button type="primary" size="large" @click="submit" long>提交</Button>
        </Col>
        <Col span="8">&nbsp;</Col>
      </Row>
    </Form>
  </div>
</template>
<script type="text/javascript">
  import ImgUploader from './uploader.vue';
  import $http from '@/js/http2';
  import {validateMultipleRoles, validateFullLocation, validatePhone} from "@/js/validate";
import Vue from "vue";
  export default {
    components: {
      ImgUploader
    },
    props: ['value', 'readonly', 'resetFlag'],
    data() {
      return {
        uploaderName:"pic1",
        formData:this.value,
        typeList: [{
          id: '1',
          label: '图片素材'
        },{
          id: '2',
          label: '跳转素材'
        }],
        widthList:[{
          id: '480*360',
          label: '480*360'
        }],
        statusList: [{id: 0, name: '未审核'},{id:1, name: '通过'},{id: 9, name: '未通过'}],
        formRules: {
          name: [
            {required: true, message: '请填写素材名称', trigger: 'change'}
          ],
          type: [
            {required: true, message: '请选择图片素材', trigger: 'change'}
          ],
          imgUrl: [
            {required: true, message: '请填写素材地址', trigger: 'blur'},
            {type: 'url', message: '请填写正确的url地址,如:  http://www.iwifi.com', trigger: 'change'},
          ],
          jumpUrl: [
            {type: 'url', message: '请填写正确的url地址,如:  http://www.iwifi.com', trigger: 'change'},
          ],
        },
      };
    },
    computed:{
      uploadAction(){
        return `/home/image/upload/submit`
      }
    },
    methods: {
      submit(name) {
        var sizeArr = this.formData.size.split('*');
        this.formData.width = sizeArr[0];
        this.formData.height = sizeArr[1];
        this.$refs.form.validate((valid) => {
          if (valid) {
            this.$emit('on-submit',this.formData);
          }
        });
      },
      uploadSuccess(response){
       // this.value.imgUrl =""+ response.data;
        //console.log( this.formData);
       // this.formData = Object.assign({},  this.value);
        //this.formData.imgUrl = response.data;
        Vue.set(this.formData,"imgUrl",response.data)
      },
      resetForm() {
        this.$nextTick(() => {
          this.$refs.form.resetFields();
          this.formData = Object.assign({}, this.value);
        });
      },
      // initFormData(id = this.roleId) {
      //   if (!id) {
      //     this.formData = {};
      //
      //   } else {
      //     $http.get(`/home/sys/auth/role/view/${id}`).then((data) => {
      //       this.formData = data.data;
      //       this.$refs.roleForm.resetFields();
      //     }).catch((error) => {
      //       this.formData = {};
      //       this.$refs.roleForm.resetFields();
      //     });
      //   }
      // }
    },
    mounted() {
    },
    watch: {
      resetFlag(flag) {
        if (flag) {
          this.resetForm();
        }
      },
    }
  };
</script>
<style lang="less">
  .ivu-select-multiple {
    .ivu-select-selection {
      height: auto !important;
    }
  }
</style>
