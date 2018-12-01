<template>
    <div>
    <input ref="input" type="file" style="display:none">
    <img ref="img" class=" img-upload" alt="请上传图片" :src="getPathValue(value)"></img>
</div>

</template>
<script type="text/javascript">
  import zwIcon from "../icon/zwIcon.vue"
  export default {
    name: 'zwInput',
    components: {
      zwIcon
    },
    props: {value:{type:String}},
    data() {
      return {
        ok: false,
        textvalue: "",
        closeSee: false,
        imgDom: null, //回显的image的id
        		maxHeight: null, //预设的最大高度
        		maxWidth: null, //预设的最大宽度
        		postUrl: null, //提交的url"/calendar/image/upload.json"
        		preShow: false,
        		callback: null,
                fileInput:null
      };
    },
    computed: {

    },
    mounted() {

        this.init({});
    },
    methods: {
    getPathValue:function(value){
                    return getPathValue(value);
                },
    fileChange: function(e) {
    			var f = e.files[0]; //一次只上传1个文件，其实可以上传多个的
    			var FR = new FileReader();
    			var _this = this;

    			FR.onload = function(f) {

    				_this.compressImg(this.result, 300, function(data,originalWidth,originalHeight) {
    					console.log("压缩完成后执行的callback");

                        if (_this.preShow) {
                            console.log("img pre show");

                            _this.imgDom.src=data;

                        }
                        console.log("begin send img");
                        var json = {};
                        data ="+"+ data;//.substring(22);
                        json.imageData = encodeURIComponent(data);
                        console.log("begin post");
                        Ajax.post(_this.postUrl,
                            json,
                            function(data) {console.log("ajax return");

                                if (_this.callback != null)
                                    _this.callback(data);
                            }
                        );
    				});
    			};
    			FR.readAsDataURL(f); //先注册onload，再读取文件内容，否则读取内容是空的
    		},
    		compressImg: function(imgData, maxHeight, onCompress) {
            			var _this = this;
            			if (!imgData)
            				return;
            			onCompress = onCompress || function() {};
            			maxHeight = maxHeight || this.maxHeight; //默认最大高度200px
            			var canvas = document.createElement('canvas');
            			var img = new Image();
            			console.log("maxHeight:" + maxHeight);
            			img.onload = function() {
                            if (img.height > maxHeight) { //按最大高度等比缩放
                                img.width *= maxHeight / img.height;
                                img.height = maxHeight;
                            }
                            canvas.width = img.width;
                            canvas.height = img.height;
            				var ctx = canvas.getContext("2d");
            				ctx.clearRect(0, 0, canvas.width, canvas.height); // canvas清屏
            				//重置canvans宽高 canvas.width = img.width; canvas.height = img.height;
            				console.log("width:" + img.width + "height:" + img.height);
            				ctx.drawImage(img, 0, 0, img.width, img.height); // 将图像绘制到canvas上
            				console.log("begin compress img");
            				onCompress(canvas.toDataURL("image/png"),img.width,img.height); //必须等压缩完才读取canvas值，否则canvas内容是黑帆布
            			};
            			// 记住必须先绑定事件，才能设置src属性，否则img没内容可以画到canvas
            			console.log("begin origin data load:");
            			img.src = imgData;

            		},
            		init: function(jso) {
            			this.imgDom =this. $refs.img;//parseDom("<img class=\" img-upload\" alt=\"请上传图片\"></img>");
            			this.maxHeight = jso.maxHeight||633;
            			this.maxWidth = jso.maxWidth||300;
            			this.postUrl = jso.postUrl||(PATH+"/image/upload.json");
            			var that =this;
            			this.callback = jso.callback||function(result){
                            //that.src=result.data;
                               that.$emit('input', result.data); //触发 input 事件，并传入新值

            			};
            			this.fileInput=this.$refs.input;//parseDom("<input type=\"file\" style=\"display:none\"/>");
                        this.fileInput.addEventListener("change",function(){
                            that.fileChange(this);
                        });
                        this.imgDom.addEventListener("click",function(){
                             var evt = new MouseEvent("click", { bubbles: false, cancelable: true, view: window });
                           that.fileInput.dispatchEvent(evt);
                        });
            		},
     handleInput(event) {
          var value = event.target.value;
          this.$emit('input', value); //触发 input 事件，并传入新值
        },
      onchange: function() {
        console.log("onchange");
        this.$emit('onchange', this.textvalue);
      }

    },
    watch: {

    },

    events: {

    }
  };
</script>
<style>
.img-upload{
    max-width:100%;
}
</style>