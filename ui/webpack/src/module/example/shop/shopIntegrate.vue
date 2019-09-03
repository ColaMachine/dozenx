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

                <div id="global-search">
                  <div class="search-inner z-clearfix" _hover-ignore="1">
                    <h1 id="logo" class="index-logo" onclick="dataLayer.push({'event':'11100','button':'什么值得买logo'})">
                                        <a href="/">
                                            <svg width="150" height="48">
                                                <image xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="" src="" width="150" height="48"></image>
                                            </svg>
                                        </a>
                                    </h1>

                    <!-- <div class="logo-special">
                                    <a href="#"><img src="../../../img/embed/jklogo_new.gif" alt="日韩海淘节"></a>
                                </div> -->
                    <!-- 搜索框 -->
                    <form action="//search.smzdm.com/" method="get" id="search-form" _hover-ignore="1">
                      <div class="search-wrap">
                        <div class="search-input-wrap" id="J_search_sug">
                          <input type="hidden" name="c" value="home" readonly="readonly">
                          <input type="search" class="search-input" id="J_search_input" name="s" value="" placeholder="支付" autocomplete="off">
                        </div>
                        <button type="submit" class="search-submit" onclick="dataLayer.push({'event':'11100','button':'搜索框'})">
                                            搜 索
                                        </button>
                      </div>
                      <div class="hot-words">
                        热门搜索：
                        <a target="_blank" rel="nofollow" class="" href="https://search.smzdm.com/?c=youhui&amp;s=%E8%BF%90%E5%8A%A8%E6%9C%8D%E9%A5%B0" onclick="dataLayer.push({'event':'11200','button':'运动服饰'})">
                          运动服饰 </a>
                        <a target="_blank" rel="nofollow" class="" href="https://search.smzdm.com/?c=home&amp;s=%E6%8A%A4%E8%82%A4" onclick="dataLayer.push({'event':'11200','button':'护肤'})">
                          护肤 </a>
                        <a target="_blank" rel="nofollow" class="" href="https://search.smzdm.com/?c=home&amp;s=%E7%A9%BA%E8%B0%83" onclick="dataLayer.push({'event':'11200','button':'空调'})">
                          空调 </a>
                        <a target="_blank" rel="nofollow" class="" href="https://search.smzdm.com/?c=home&amp;s=%E5%AE%B6%E5%85%B7" onclick="dataLayer.push({'event':'11200','button':'家具'})" _hover-ignore="1">
                          家具 </a>
                        <a target="_blank" rel="nofollow" class="" href="https://search.smzdm.com/?c=youhui&amp;s=%E9%85%92%E7%B1%BB" onclick="dataLayer.push({'event':'11200','button':'酒水'})">
                          酒水 </a>
                      </div>
                    </form>
                    <input type="hidden" name="default_keywords" value="支付">
                    <!-- 搜索框 end -->
                    <div class="download">
                      <div class="download-close J_download_close"><i class="icon-times-o-thin"></i></div>
                      <div class="download-text">下载App</div>
                      <a href="http://www.smzdm.com/push/">
                        <svg width="60" height="60">
                          <image xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="https://res.smzdm.com/pc/v1.0/dist/img/embed/download.svg" src="https://res.smzdm.com/pc/v1.0/dist/img/embed/download.png" width="60" height="60">
                          </image>
                        </svg>
                      </a>
                    </div>
                  </div>
                </div>

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

  //import { mavonEditor } from 'mavon-editor'
  //import 'mavon-editor/dist/css/index.css'
  import zwIcon from '../../../component/icon/zwIcon.vue';
  import zwRow from '../../../component/layout/zwRow.vue';
  import zwBox from '../../../component/layout/zwBox.vue';
  import zwCol from '../../../component/layout/zwCol.vue';

  //import zwMarkDown from '../../../component/dataentry/zwMarkDown.vue';zwMarkDown,
  export default {
    components: {
      zwIcon,
      zwBox,
      zwRow,
      zwCol
    },
    data() {

      return {
        msg: 'Hello World!',
        loading: false,
        value: "",
        message_text: ''
      }
    },
    computed: {
      getLoadingVal: function() {
        return this.loading;
      }
    },
    mounted() {

    },
    methods: {
      saveContent: function(content) {
        Ajax.post("/article/save.json", {
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
  /**外包**/
  
  #global-search {
    background-color: #fff;
    margin-bottom: 10px;
    padding-top: 34px;
    height: 100px;
    -webkit-font-smoothing: antialiased;
  }
  
  #header #global-search {
    margin-bottom: 0;
    padding-top: 0;
    height: auto;
  }
  /**内裹层**/
  
  .z-clearfix,
  .z-hor-feed {
    zoom: 1;
  }
  
  #global-search .search-inner {
    position: relative;
    width: 1200px;
    margin: 0 auto;
    padding: 22px 0;
    height: 56px;
  }
  
  #header #global-search .search-inner {
    padding: 30px 0 0;
    height: auto;
    position: relative;
  }
  /**左边logo区域**/
  
  #logo {
    width: 251px;
    margin: 9px 0 0;
  }
  
  #logo,
  #logo a {
    font-size: 0;
  }
  
  #logo {
    float: left;
    margin: 4px 0 0;
  }
  
  h1,
  h2,
  h3,
  h4,
  h5,
  h6 {
    margin: 0;
    font-weight: 400;
    font-size: 1em;
  }
  
  #search-form {
    position: relative;
    float: left;
  }
  
  #search-form .search-wrap {
    overflow: hidden;
    margin-bottom: 6px;
    width: 600px;
    height: 40px;
    font-size: 14px;
  }
  
  #search-form .search-input-wrap {
    float: left;
    padding: 8px 8px 8px 14px;
    border: 2px solid #f04848;
    border-right-width: 0;
    background-color: #fff;
    width: 500px;
    -webkit-box-sizing: border-box;
    box-sizing: border-box;
    height: 40px;
    -webkit-border-top-left-radius: 2px;
    border-top-left-radius: 2px;
    -webkit-border-bottom-left-radius: 2px;
    border-bottom-left-radius: 2px;
  }
  
  #search-form .search-input-wrap .search-input {
    width: 100%;
    height: 20px;
    border: 0;
    line-height: 20px;
    color: #333;
    font-family: inherit;
    -webkit-appearance: none;
    font-size: 14px;
  }
  
  blockquote,
  button,
  dl,
  form,
  input,
  ol,
  p,
  textarea,
  ul {
    margin: 0;
    padding: 0;
  }
  
  * {
    outline: 0;
  }
  
  #search-form .search-submit {
    float: left;
    background-color: #f04848;
    -webkit-border-top-right-radius: 2px;
    border-top-right-radius: 2px;
    -webkit-border-bottom-right-radius: 2px;
    border-bottom-right-radius: 2px;
    line-height: 40px;
    cursor: pointer;
    width: 100px;
    font-size: 20px;
    color: #fff;
    text-align: center;
    background-image: -webkit-gradient(linear, left top, right top, from(#f04848), to(#f36f5d));
    background-image: -webkit-linear-gradient(left, #f04848, #f36f5d);
    background-image: -o-linear-gradient(left, #f04848 0, #f36f5d 100%);
    background-image: linear-gradient(90deg, #f04848, #f36f5d);
  }
  
  #search-form .hot-words,
  #search-form .hot-words a {
    color: #666;
  }
  
  #search-form .hot-words {
    font-size: 14px;
    overflow: hidden;
    white-space: nowrap;
    -o-text-overflow: ellipsis;
    text-overflow: ellipsis;
    height: 20px;
    line-height: 20px;
    margin-bottom: 24px;
  }
  /**热点词**/
  
  #search-form .hot-words {
    font-size: 14px;
    overflow: hidden;
    white-space: nowrap;
    -o-text-overflow: ellipsis;
    text-overflow: ellipsis;
    height: 20px;
    line-height: 20px;
    margin-bottom: 24px;
  }
  
  #search-form .hot-words a {
    margin-left: 9px;
  }
  
  #search-form .hot-words,
  #search-form .hot-words a {
    color: #666;
  }
  /**app下载**/
  
  #header #global-search .download {
    border: 1px solid #eee;
    width: auto;
    height: 90px;
    padding: 6px 8px 8px;
    position: absolute;
    float: left;
    margin-left: 137px;
    top: 15px;
    right: 136px;
    z-index: 10;
    -webkit-box-sizing: border-box;
    box-sizing: border-box;
  }
  
  body {
    -webkit-box-sizing: border-box;
    box-sizing: border-box;
  }
</style>