
<template>

  <div>
  <script src="https://unpkg.com/marked@0.3.6"></script>
  <script src="https://unpkg.com/lodash@4.16.0"></script>

                      <div id="editor">
                        <textarea :value="input" @input="update"></textarea>
                        <div v-html="compiledMarkdown"></div>
                      </div>
                      <div>

                        </div>

</template>
<script type="text/javascript">

import zwButton from '../../component/button/zwButton.vue';
import zwIcon from '../../component/icon/zwIcon.vue';
import zwDropDown from '../../component/navigation/menu/zwDropDown.vue';
import zwPanel from '../../component/datadisplay/zwPanel.vue';



export default {
el: '#editor',
         components:{zwIcon,zwPanel,zwButton,zwDropDown},
        props: [],
        data () {

            return {
            input: '# hello',
                images:[]
            };
        },
        computed: {
            getComputedUlCls:function(){

                return "white-space:nowrap;background-color:white;z-index:100;width:"+ ((this.images.length+1)*100+30)+"px";
            },
            compiledMarkdown: function () {
                  return marked(this.input, { sanitize: true })
                }

        },
        mounted () {

        },
        methods: {
         update: _.debounce(function (e) {
              this.input = e.target.value
            }, 300),

        deletePic:function(item){
               this.images.push();
                var index =-1;
               for(var i=0;i< this.images.length;i++){
                 if(this.images[i]==item){
                   index=i;
                 }
               }
               if(index>=0)
                  this.images.splice(index,1);
                this.images= this.images;
        },

        choosePicAndUpload:function(){
              var imageUtil=new zImageUtil5({"input":"face",callback:this.uploadSucc,maxHeight:200,maxWidth:200});
        },
        uploadSucc:function(result){
            this.images.push(result.data);
        },
            submit:function(){//提交 点击提交按钮触发
                var data={};
                data.pic=this.images.join(",");
                data.content=this.$refs.textarea.value;
                this.$emit("submit",data);
                this.$refs.textarea.value="";
            }
        },
        watch:{

        },

         events: {


          }
    };
</script>
<style>

</style>