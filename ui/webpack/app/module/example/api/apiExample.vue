<template>
<div>
  <zwLayout>
         <zwHeader class="bg-green">

         api列表  <input id="apiUrl"  value="" name="apiUrl" v-model="apiUrlStr"  type="text" /><zwButton type="dashed"   :loading="false"  :loading_delay=1  v-on:click.native="getApiData" >请求api文档</zwButton>

<zwInput id="apiSearchText" >
    <zwButton  v-on:click.native="search" slot="append">搜索</zwButton>
</zwInput>
         </zwHeader>
         <zwContent>


            <zwCollapse id="apiList" class="row  " >

                <div  is="apiPath"
                     v-for="(value, key) in tags"
                     v-bind:title="key"
                     v-bind:content="value"
                      class="zwlist">


                </div>

            </zwCollapse>
         </zwContent>



   </zwLayout>
</div>
</template>
<script>
import zwRow from '../../../component/layout/zwRow.vue';

import zwCol from '../../../component/layout/zwCol.vue';
import zwIcon from '../../../component/icon/zwIcon.vue';
import zwBox from '../../../component/layout/zwBox.vue';
import zwButton from '../../../component/button/zwButton.vue';
import zwLayout from '../../../component/layout/zwLayout.vue';
import zwHeader from '../../../component/layout/zwHeader.vue';
import zwContent from '../../../component/layout/zwContent.vue';
import zwSider from '../../../component/layout/zwSider.vue';
import zwFooter from '../../../component/layout/zwFooter.vue';

import zwCollapse from '../../../component/datadisplay/zwCollapse.vue';
import zwPanel from '../../../component/datadisplay/zwPanel.vue';

import apiPath from '../../../component/datadisplay/apiPath.vue';
import zwInput from '../../../component/dataentry/zwInput.vue';
var formId=0;
export default {
    components:{zwRow,zwCol,zwIcon,zwBox,zwHeader,zwContent,zwFooter,zwLayout,zwSider,zwCollapse,zwPanel,apiPath,zwButton,zwInput},
    data () {

        return {
            tags:{},
            original:{},
            apiUrlStr:'http://127.0.0.1:80/',//http://alpha-np.51awifi.com/timebuysrv/
        }
    }
    ,
    mounted () {window.APIPATH="/api";
        //从cookie 中读取 apiUrlStr 放入到 apiUrlStr变量中

        var apiUrlStrTemp = getCookie("apiUrlStr");
        if(apiUrlStrTemp){
           this.apiUrlStr= apiUrlStrTemp.replace(/"/g,"");
        }

        var PATH ="";
        var that=this;
        return;
            Ajax.getJSON(PATH+"/hello.json",null,function(data){
            var tags={};
            //console.log(data);
                for(var url in data.paths){
                  //  console.log(url);
                    var postGetData=data.paths[url];

                    for(var httpType in postGetData){
                        var content = postGetData[httpType];
                        content.httpType = httpType;
                        content.url = url;
                         var tagName = content.tags[0];
                          if(tags[tagName]){

                        }else{
                            tags[tagName]=new Array();

                        }
                        tags[tagName].push(content);
                    }
                    //console.log(content);


                }

                that.tags =tags;
            });
    },
    computed: {

    },
    methods:{
    search:function(){
        var value = document.getElementById("apiSearchText").value;

        var newTags={};

        for(var key in this.original){
            if(key.indexOf(value)!=-1){
                newTags[key]= this.original[key];
                console.log(newTags[key]);
                /*for(var j=0;j<this.tags[key].length;j++){
                    if(this.tags[key][j].url.indexOf(value )==-1){
                        this.tags[key][j]==null;
                    }
                }*/
           }else{
             /*  console.log(key);
                this.tags[key]=null;
               delete  this.tags[key];*/
            }
        }
        this.tags=newTags;
    },
        getApiData:function(){
            window.APIPATH="/proxy";
            window.APIDOMAIN=this.apiUrlStr;

            var url = APIPATH+"/api?url="+this.apiUrlStr;
            //alert(url);

             log("设置cookie值");
            setCookie("apiUrlStr",this.apiUrlStr,5);

            var that=this;
            Ajax.getJSON(url,null,function(data){
           var data = ajaxResultHandler(data);
            var tags={};
                for(var url in data.paths){;

                    var postGetData=data.paths[url];

                    for(var httpType in postGetData){
                        var content = postGetData[httpType];
                        content.httpType = httpType;
                        content.url = url;
                         var tagName = content.tags[0];
                          if(tags[tagName]){

                        }else{
                            tags[tagName]=new Array();

                        }
                        tags[tagName].push(content);
                    }
                    //console.log(content);


                }

                that.tags =tags;
                that.original =tags;
            });
        }
    }
}
</script>
<style scoped>
.api-list,.api-list td{
color:black;
}

</style>
