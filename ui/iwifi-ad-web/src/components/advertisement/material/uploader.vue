/**
* 功能: 文件上传
* 修改记录: xx
* @author haoxu
* @date 2018-06-08
*/

<template>
	<span>
		<Upload  :name="name"
              :format="['jpg','jpeg','png']"
              :action="action"
              :data="data"
              :on-format-error="onFormatError"
              :on-success="onSuccess"
              :on-error="onError"
              :on-remove="onRemove"
              :before-upload="beforeUpload"
              :auto-upload="auto"
              @on-change="onChange"
              ref="uploader">
      <Button type="ghost" icon="ios-cloud-upload-outline">上传图片</Button>
    </Upload>
	</span>
</template>

<script type="text/javascript">
  export default {
    props: {
      action:{
        type:String,
        required:true
      },
      name:{
        type:String,
        required:true
      },
      data: {
        type: Object
      },
      stop:{
        type:[Function,Boolean],
        default:false
      },
      auto:{
        type:Boolean,
        default:true
      }
    },
    data() {
      return {
        showFileName:false,
        fileName:'',
        uploadSuccess:false,
        showModal:false,
        errorMsg:'',
        formatError:'文件格式错误，请上传图片文件'
      }
    },
    methods: {
      onChange(file){
        this.errorMsg='';
        if(file.length>0){
          this.fileName=file[0].name;
          this.showFileName=true;
        }else{
          this.fileName='';
          this.showFileName=false;
        }
        this.$emit('on-change',this.fileName);
        this.dispatch('FormItem', 'on-form-change', this.fileName);
      },
      beforeUpload(file){
        if(typeof(this.stop)==='function'){
          return this.stop();
        }else if(this.stop){
          return false;
        }else{
          this.showFileName=false;
        }
      },
      onFormatError(file, fileList,format){
        this.errorMsg=this.formatError;
      },
      onSuccess(response, file, fileList){
        this.errorMsg=response.msg;
        if(response.r == "0"){
          this.$emit('on-success',response);
          this.uploadSuccess = true;
        }
      },
      onError(error, file, fileList){
        this.errorMsg=error.msg;
        this.$emit('on-error',error);
      },
      onRemove(){
        this.errorMsg='';
        this.fileName='';
        this.showFileName=false;
      },
      clearFiles() {
        this.$refs.uploader.clearFiles();
      }
    },
    mounted(){
      this.clearFiles();
      this.errorMsg='';
      this.$on('trigger-upload',()=>{
        this.$refs.uploader.$emit('trigger-upload');
    });
    },
    components: {
    }
  }
</script>
