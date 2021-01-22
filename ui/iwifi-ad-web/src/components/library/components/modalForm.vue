<template>
    <div style="margin:10px 60px;">
      <Form ref="form" :model="formData" :rules="ruleValidate" :label-width="130">
        <FormItem label="人像图片" prop='faces'>
					<div class="upload-list" v-for='(item,index) in formData.faces'>
						<div class="upload-list-cover">
									<Icon type="md-trash" @click.native="handleRemove(item,index)"/>
	            </div>
						<img :src="item" alt="" >
					</div>

					<Upload
		        ref="upload"
		        :show-upload-list="false"
		        :on-success="handleSuccess"
		        :format="['jpg','jpeg','png']"
		        :max-size="5120"
		        :on-format-error="handleFormatError"
		        :on-exceeded-size="handleMaxSize"
		        :before-upload="handleBeforeUpload"
		        multiple
		        type="drag"
		        action="/api/mssrv/pubimage/file/upload"
		        style="display: inline-block;width:100px;height:100px;">
		        <div style="width: 100px;height:100px;line-height: 100px;">
								<Icon type="md-add"  size="40"/>
		        </div>
		    </Upload>
        </FormItem>
				<FormItem label="姓名" prop='userName'>
          <Input type="text" v-model="formData.userName" placeholder="请输入姓名" :maxlength='20'></Input>
        </FormItem>
				<FormItem label="证件号码" prop='code'>
          <Input type="text" v-model="formData.code" placeholder="请输入证件号码" :maxlength='20'></Input>
        </FormItem>
      </Form>
      <div class="btnBar">
          <Button class="btn" size="large" @click="cancel"> 取消</Button>
          <Button type='primary' size="large"  @click="submit"> 确认</Button>
      </div>
    </div>
</template>
<style lang='less' scoped>
	.upload-list{
		display: inline-block;
		text-align: center;
		width: 100px;
		height: 100px;
		overflow: hidden;
    background: #fff;
    position: relative;
    box-shadow: 0 1px 1px rgba(0,0,0,.2);
		margin-right: 4px;
		img{
			width: 100%;
      height: 100%;
		}
		.upload-list-cover{
        display: none;
				position: absolute;
				top:0;
				bottom: 0;
				left: 0;
				right: 0;
        background: rgba(0,0,0,.6);
				i{
					color: #fff;
	        font-size: 30px;
	        cursor: pointer;
	        margin: 0 2px;
				}
    }
		&:hover{
			.upload-list-cover{
	        display: flex;
					justify-content: center;
					align-items: center;
	    }
		}
	}
</style>
<script>
import {validateChnEnNumber} from '@/js/validate'
export default {
  components:{

  },
  props:{
  	'data':{
  		type:[Object]
  	},
  },
  created(){

    let {faces,username,code} = this.data
    console.log(this.data);
    this.formData.userName = username
    this.formData.code = code
    if (faces.length) {
      // 更改faces指向
			this.formData.faces = faces.slice()
		}
  },
  data(){
  	return {
			formData:{
				faces:[],
				userName:'',
				code:''
			},
			ruleValidate:{
				faces:[
					{required:true,type:'array',message:'请至少上传一张图片',trigger:'change'}
				],
				userName:[
					{required:false,validator:validateChnEnNumber,trigger:'blur'}
				],
				code:[
					{required:false,validator:validateChnEnNumber,trigger:'blur'}
				]
			}
  	}
  },
  methods:{
		submit(){
			this.$refs.form.validate((valid)=>{
				if (valid) {
          this.$emit('submit',this.formData)
				}
			})
		},
		cancel(){
			this.$emit('cancel')
		},
		handleRemove (file,index) {
        const fileList = this.$refs.upload.fileList;
        this.$refs.upload.fileList.splice(fileList.indexOf(file), 1);
				this.formData.faces.splice(index,1)
    },
		handleSuccess (res, file) {
        this.formData.faces.push(res.data)
    },
    handleFormatError (file) {
        this.$Notice.warning({
            title: '格式错误',
            desc: '请上传正确图片类型（jpg/jpeg/png）格式'
        });
    },
    handleMaxSize (file) {
        this.$Notice.warning({
            title: '文件大小限制',
            desc: '文件  ' + file.name + ' 太大, 请不要超过5M.'
        });
    },
    handleBeforeUpload (file) {
				// const format = this.$refs.upload.format
				// if (format.length) {
				// 		const _file_format = file.name.split('.').pop().toLocaleLowerCase();
        //
				// 		const checked = format.some(item => item.toLocaleLowerCase() === _file_format);
				// 		if (!checked) {
				// 				this.handleFormatError(file);
				// 		}else{
				// 			let url = window.URL.createObjectURL(file)
				// 			this.formData.faces.push(url)
				// 			this.$refs.form.validateField('faces')
				// 		}
				// }
				return true
    }
  },
  watch:{

  }
}
</script>
