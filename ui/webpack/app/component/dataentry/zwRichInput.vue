<template>

  <div>
    有什么新鲜事想告诉大家
    <textarea ref="textarea" rows="7" cols="80" style="width:100%;height:100%">
                        </textarea>
    <input type="text" style="display:none" id="face" />
    <zwDropDown trigger="click" placement="bottomRight">
      <zwButton slot="button" type="primary" icon="down">表情</zwButton>
      <div slot="menu">
        <zwPanel :canFold=false state="open" style="">
          <span slot="title" name="title">重大事件</span>
          <p slot="body" name="body">

            123123asdfasdf asdfasdfasdf asdfasdf

          </p>

        </zwPanel>

      </div>
    </zwDropDown>

    <zwDropDown trigger="click" placement="bottomRight">
      <zwButton slot="button" type="primary" icon="down">图片</zwButton>
      <div slot="menu">
        <zwPanel :hasHeader=false :canFold=false state="open" style="">
          <span style="display:none" slot="title" name="title">重大事件</span>
          <p slot="body" name="body">

            <ul :style="getComputedUlCls" class="clearfix">
              <li style="float:left; width:100px;height:100px;position:relative" v-for="item in images">

                <img style="width:100px;height:100px" :src="getPathValue(item)" />
                <a style="position:absolute;width:10px;height:10px;font-size:10px;top:5px;right:5px" v-on:click="deletePic(item)">
                  <zwIcon type="close"></zwIcon>
                </a>
              </li>
              <li style="float:left;font-size:40px;border:3px dashed gray;width:40px;height:40px;padding:30px;">
                <a v-on:click="choosePicAndUpload">
                  <zwIcon type="plus">添加</zwIcon>
                </a>
              </li>
            </ul>

          </p>

        </zwPanel>

      </div>
    </zwDropDown>

    <zwButton @clickFn="submit" type="primary">发布</zwButton>

  </div>

</template>
<script type="text/javascript">
  import zwButton from '../../component/button/zwButton.vue';
  import zwIcon from '../../component/icon/zwIcon.vue';
  import zwDropDown from '../../component/navigation/menu/zwDropDown.vue';
  import zwPanel from '../../component/datadisplay/zwPanel.vue';

  export default {

    components: {
      zwIcon,
      zwPanel,
      zwButton,
      zwDropDown
    },
    props: [],
    data() {
      return {
        images: []
      };
    },
    computed: {
      getComputedUlCls: function() {

        return "white-space:nowrap;background-color:white;z-index:100;width:" + ((this.images.length + 1) * 100 + 30) + "px";
      }

    },
    mounted() {

    },
    methods: {
      getPathValue: function(value) {
        return getPathValue(value);
      },

      deletePic: function(item) {
        this.images.push();
        var index = -1;
        for(var i = 0; i < this.images.length; i++) {
          if(this.images[i] == item) {
            index = i;
          }
        }
        if(index >= 0)
          this.images.splice(index, 1);
        this.images = this.images;
      },

      choosePicAndUpload: function() {
        var imageUtil = new zImageUtil5({
            "postUrl":"/home/pubimage/base64/upload",
          "input": "face",
          callback: this.uploadSucc,
          maxHeight: 200,
          maxWidth: 200
        });
      },
      uploadSucc: function(result) {
        this.images.push(result.data);
      },
      submit: function() { //提交 点击提交按钮触发
        var data = {};
        data.pic = this.images.join(",");
        data.content = this.$refs.textarea.value;
        this.$emit("submit", data);
        this.$refs.textarea.value = "";
      }
    },
    watch: {

    },

    events: {

    }
  };
</script>
<style>

</style>