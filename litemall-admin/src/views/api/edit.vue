<template>
  <div class="app-container">

    <el-card class="box-card">

      <el-form ref="api" :model="api" label-width="150px">


        <el-row>

        <el-col :span="12">
        <el-form-item label="接口名称" prop="interfaceName">
          <el-input v-model="api.interfaceName"/>
        </el-form-item>
        </el-col>

        <el-col :span="8">
          <el-form-item label="contentType" prop="contentType">


            <template slot-scope="scope">
              <el-select v-model="api.contentType"    label="请选择contentType类型"  >
                          
                <el-option
                  v-for="item in contentTypes"
                  :key="item.name"
                  :label="item.name"  
                  :value="item.name">
                   
                </el-option>
              </el-select>
            </template>
          </el-form-item>

        </el-col>

        </el-row>
        <el-row>
          <el-col :span="12">

          </el-col>
          <el-col :span="4">


              <el-select v-model="api.httpType"    label="请选择http类型"  >
                          
                <el-option
                  v-for="item in httpTypes"
                  :key="item.name"
                  :label="item.name"  
                  :value="item.name">
                   
                </el-option>
              </el-select>


          </el-col>
          <el-col :span="5">


              <el-input v-model="api.domain"  placeholder="domain"  label="请选择http类型" />

          </el-col>
          <el-col :span="8">



          <el-input v-model="api.url" placeholder="url"  label="请选择http类型" />

          </el-col>



        </el-row>



        <el-form-item label="接口详情">
          <editor :init="editorInit" v-model="api.interfaceDetail"/>
        </el-form-item>









        <el-collapse  >
          <el-collapse-item title="更多内容  ...." name="1">


        <el-form-item label="项目名称" prop="projectName">
          <el-input v-model="api.projectName"/>
        </el-form-item>
        <el-form-item label="模块名称"  prop="moduleName">
          <el-input :disabled="true" v-model="api.moduleName"/>
          <el-tree
            :data="treeData"
            :props="defaultProps"

            node-key="id"
            default-expand-all
            :expand-on-click-node="false"
            @node-click="nodeClick"
          >
          </el-tree>
        </el-form-item>
        <el-form-item label="interfaceId" prop="moduleName">
          <el-input v-model="api.interfaceId"/>
        </el-form-item>

        <el-form-item label="作者" prop="author">
          <el-input v-model="api.author"/>
        </el-form-item>


        <el-form-item label="版本" prop="version">
          <el-input v-model="api.version"/>
        </el-form-item>

          </el-collapse-item>
        </el-collapse>
      </el-form>
    </el-card>


    <el-tabs v-model="activeName" >
      <el-tab-pane label="params" name="first">

        <!--    参数部分是绑定了 apiParams -->
        <el-card class="box-card">
          <h3>接口参数</h3>
          <el-button :plain="true" type="primary" @click="addApiParams">添加</el-button>
          <el-table :data="apiParams">

            <el-table-column label="参数名称">
              <template slot-scope="scope">
                <el-input type="textarea" v-model="scope.row.paramName"/>
              </template>
            </el-table-column>

            <el-table-column label="参数值">
              <template slot-scope="scope">
                <template v-if="scope.row.paramType=='file'">

                  <el-input type="file" v-model="scope.row.paramValue"/>
                </template>
                <template v-else >

                  <el-input type="textarea" v-model="scope.row.paramValue"/>
                </template>

              </template>
            </el-table-column>

            <el-table-column label="参数描述">
              <template slot-scope="scope">
                <el-input type="textarea" v-model="scope.row.paramComment"/>
              </template>
            </el-table-column>


            <el-table-column property="paramIn" label="参数位置">

              <template slot-scope="scope">
                <el-select v-model="scope.row.paramIn"    label="请选择参数类型"  >
                            
                  <el-option
                    v-for="item in paramIns"
                    :key="item.name"
                    :label="item.name"  
                    :value="item.name">
                     
                  </el-option>
                </el-select>
              </template>
            </el-table-column>

            <el-table-column property="paramType" label="参数类型">

              <template slot-scope="scope">
                <el-select v-model="scope.row.paramType"    label="请选择参数类型"  >
                                          
                  <el-option
                                              v-for="item in paramTypes"
                                              :key="item.name"
                                              :label="item.name"  
                                              :value="item.name">
                                            
                  </el-option>
                </el-select>
              </template>
            </el-table-column>



            <el-table-column align="center" label="操作" width="200" class-name="small-padding fixed-width">
              <template slot-scope="scope">

                <el-button type="danger" size="mini" @click="handleparamDelete(scope.row)">删除</el-button>
              </template>
            </el-table-column>
          </el-table>

          <el-dialog :visible.sync="paramVisiable" title="设置接口参数">
            <el-form
              ref="paramForm"
              :model="paramForm"
              status-icon
              label-position="left"
              label-width="100px"
              style="width: 400px; margin-left:50px;">
              <el-form-item label="参数名称" prop="paramName">
                <el-input v-model="paramForm.paramName"/>
              </el-form-item>
              <el-form-item label="参数值" prop="paramValue">
                <el-input v-model="paramForm.paramValue"/>
              </el-form-item>
              <el-form-item label="参数位置" prop="paramIn">
                <el-select v-model="paramForm.paramIn"    label="请选择参数类型"  >
                            
                  <el-option
                    v-for="item in paramIns"
                    :key="item.name"
                    :label="item.name"  
                    :value="item.name">
                     
                  </el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="参数类型" prop="paramType">


                <el-select v-model="paramForm.paramType"    label="请选择参数类型"  >
                                          
                  <el-option
                                              v-for="item in paramTypes"
                                              :key="item.name"
                                              :label="item.name"  
                                              :value="item.name">
                                            
                  </el-option>
                </el-select>


              </el-form-item>
              <el-form-item label="参数描述" prop="paramComment">
                <el-input v-model="paramForm.paramComment"/>
              </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
              <el-button @click="paramVisiable = false">取消</el-button>
              <el-button type="primary" @click="handleparamAdd">确定</el-button>
            </div>
          </el-dialog>
        </el-card>


      </el-tab-pane>
      <el-tab-pane label="Authorization" name="second">配置管理</el-tab-pane>
      <el-tab-pane label="Headers" name="third">
        <el-card class="box-card">
          <h3>header参数</h3>
          <el-button :plain="true" type="primary" @click="addApiHeaders">添加</el-button>
          <el-table :data="apiHeaders">

            <el-table-column label="参数名称">
              <template slot-scope="scope">
                <el-input type="textarea" v-model="scope.row.paramName"/>
              </template>
            </el-table-column>

            <el-table-column label="参数值">
              <template slot-scope="scope">
                <template v-if="scope.row.paramType=='file'">

                  <el-input type="file" v-model="scope.row.paramValue"/>
                </template>
                <template v-else >

                  <el-input type="textarea" v-model="scope.row.paramValue"/>
                </template>

              </template>
            </el-table-column>

            <el-table-column label="参数描述">
              <template slot-scope="scope">
                <el-input type="textarea" v-model="scope.row.paramComment"/>
              </template>
            </el-table-column>



          </el-table>

          <el-dialog :visible.sync="paramVisiable" title="设置接口参数">
            <el-form
              ref="paramForm"
              :model="paramForm"
              status-icon
              label-position="left"
              label-width="100px"
              style="width: 400px; margin-left:50px;">
              <el-form-item label="参数名称" prop="paramName">
                <el-input v-model="paramForm.paramName"/>
              </el-form-item>
              <el-form-item label="参数值" prop="paramValue">
                <el-input v-model="paramForm.paramValue"/>
              </el-form-item>
              <el-form-item label="参数位置" prop="paramIn">
                <el-select v-model="paramForm.paramIn"    label="请选择参数类型"  >
                            
                  <el-option
                    v-for="item in paramIns"
                    :key="item.name"
                    :label="item.name"  
                    :value="item.name">
                     
                  </el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="参数类型" prop="paramType">


                <el-select v-model="paramForm.paramType"    label="请选择参数类型"  >
                                          
                  <el-option
                                              v-for="item in paramTypes"
                                              :key="item.name"
                                              :label="item.name"  
                                              :value="item.name">
                                            
                  </el-option>
                </el-select>


              </el-form-item>
              <el-form-item label="参数描述" prop="paramComment">
                <el-input v-model="paramForm.paramComment"/>
              </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
              <el-button @click="paramVisiable = false">取消</el-button>
              <el-button type="primary" @click="handleparamAdd">确定</el-button>
            </div>
          </el-dialog>
        </el-card>



      </el-tab-pane>
      <el-tab-pane label="Body" name="fourth"><textarea v-model="api.interfaceBody"
        style="width:80%;height:300px" id="interfaceBody" name="interfaceBody"></textarea>
      </el-tab-pane>
    </el-tabs>



    <div class="op-container">
      <el-button @click="handleCancel">取消</el-button>
      <el-button type="primary" @click="save">保存</el-button>
      <el-button type="primary" @click="handleTest2">测试</el-button>
      <el-button type="primary" @click="addExampleTest">保存为测试用例</el-button>
    </div>

    <el-card class="box-card">
      <h3>返回结果</h3>
      <p>{{httpResult}}</p>
    </el-card>


    <el-card class="box-card">
      <h3>接口测试用例</h3>
      <el-button :plain="true" type="primary" @click="addInterfaceTest">添加</el-button>
      <el-table :data="interfaceTests">

        <el-table-column label="参数值">
          <template slot-scope="scope">
            <el-input type="textarea" v-model="scope.row.params"/>
          </template>
        </el-table-column>


        <el-table-column align="center" label="操作" width="200" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button type="danger" size="mini" @click="applyExampleTest(scope.row)">应用</el-button>
            <el-button type="danger" size="mini" @click="deleteExampleTest(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

    </el-card>
  </div>
</template>

<style>
.el-card {
  margin-bottom: 10px;
}
.el-tag + .el-tag {
  margin-left: 10px;
}
.input-new-keyword {
  width: 90px;
  margin-left: 10px;
  vertical-align: bottom;
}
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #20a0ff;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 120px;
  height: 120px;
  line-height: 120px;
  text-align: center;
}
.avatar {
  width: 145px;
  height: 145px;
  display: block;
}



</style>

<script>
import {
    getInfo,
    getApiParams,
    updateApiParam,
    addApiParam,
    deleteApiParam,
    addInterfaceInfo,
    updateInterfaceInfo,
    testInterfaceInfo,
    addExampleTest,
    getInterfaceTests,
    deleteExampleTest,
    interfaceInfoTest,
    interfaceInfoModuleTree
} from '@/api/api'
import {
    createStorage,
    uploadPath
} from '@/api/storage'
import Editor from '@tinymce/tinymce-vue'
import { MessageBox} from 'element-ui'
import {    getToken} from '@/utils/auth'
import axios from 'axios';
export
default {
        name:
        'ApiEdit',
        components: {
            Editor
        },
        data() {
            return {
                 defaultProps: {
              children: 'children',
              label: 'name'
            },
                treeData:[],
                api: {},
                httpTypes: [{
                    "name": "get"
                },
                {
                    "name": "post"
                },
                {
                    "name": "put"
                },
                {
                    "name": "delete"
                }],
                paramIns: [{
                    "name": "query"
                },
                {
                    "name": "path"
                },
                {
                    "name": "form"
                },
                {
                    "name": "body"
                }],
                paramTypes: [{
                    "name": "string"
                },
                {
                    "name": "integer"
                },
                {
                    "name": "array"
                },
                {
                    "name": "json"
                },
                {
                    "name": "file"
                }],
                contentTypes: [{
                    "name": "application/json"
                },
                {
                    "name": "application/form"
                },
                {
                    "name": "application/x-www-form-urlencoded"
                },
                {
                    "name": "multipart/form-data"
                },
                {
                    "name": "text/plain"
                }],
                deleteParamsId: [],
                apiId:null,
                paramForm: {},
                paramVisiable: false,
                interfaceTests: [],
                //param参数
                apiParams: [],
                //头部参数
                apiHeaders: [],
                body:null,
                httpResult: null,
                //测试结果
                paramForm: {},

                editorInit: {
                    language: 'zh_CN',
                    convert_urls: false,
                    plugins: ['advlist anchor autolink autosave code codesample colorpicker colorpicker contextmenu directionality emoticons fullscreen hr image imagetools importcss insertdatetime link lists media nonbreaking noneditable pagebreak paste preview print save searchreplace spellchecker tabfocus table template textcolor textpattern visualblocks visualchars wordcount'],
                    toolbar: ['searchreplace bold italic underline strikethrough alignleft aligncenter alignright outdent indent  blockquote undo redo removeformat subscript superscript code codesample', 'hr bullist numlist link image charmap preview anchor pagebreak insertdatetime media table emoticons forecolor backcolor fullscreen'],
                    images_upload_handler: function(blobInfo, success, failure) {
                        const formData = new FormData();
                        formData.append('file', blobInfo.blob());
                        createStorage(formData).then(res=>{
                            success(res.data.data.url)
                        }).catch(()=>{
                            failure('上传失败，请重新上传')
                        })
                    }
                }

            }
        },
        computed: {

        },
        mounted() {
         // this.restaurants = this.loadAll();
           this.getTreeData();
        },
        created() {
            this.init()
        },
        methods: {
            nodeClick(data){
              //this.api.moduleId = data.id;
              //this.api.moduleName = data.name;
              this.$set(this.api,"moduleId",data.id);
              this.$set(this.api,"moduleName",data.name);
            },
            getTreeData(){
              interfaceInfoModuleTree().then(response => {
              console.log(response.data.data)
                  this.treeData = response.data.data.children

                }).catch(() => {

                })

            },
            init: function() {


                if (this.$route.query.id == null) {
                    return;
                }

                this. apiId = this.$route.query.id;
                //获取主体信息

                getInfo(this. apiId ).then(response =>{
                    this.api = response.data.data;
                    //this.interfaceHeaders =
                      var interfaceHeadersStr = response.data.data.interfaceHeaders;

                      this.apiHeaders = eval('(' + interfaceHeadersStr + ')');
                      console.log("interfaceHanders:"+interfaceHeadersStr);
                      this.apiHeaders = this.apiHeaders||[];
                })
                //获取参数部分
                getApiParams(apiId).then(response =>{
                    this.apiParams = response.data.data;
                })

                //获取测试数据
                getInterfaceTests(apiId).then(response =>{
                    this.interfaceTests = response.data.data;
                })

            },
            init2: function(id) {



                this. apiId  = id;
                getInfo(this. apiId ).then(response =>{
                    this.api = response.data.data;
   var interfaceHeadersStr = response.data.data.interfaceHeaders;

                      this.apiHeaders = eval('(' + interfaceHeadersStr + ')');
                      console.log("interfaceHanders:"+interfaceHeadersStr);
                      this.apiHeaders = this.apiHeaders||[];
                })

                getApiParams(this.apiId).then(response =>{

                    this.apiParams = response.data.data;

                })
                getInterfaceTests(this.apiId).then(response =>{
                    this.interfaceTests = response.data.data;
                })

            },
            handleparamShow() {
                this.paramForm = {}
                this.paramVisiable = true
            },

            handleparamAdd() {

                if (this.appId == null) {
                    alert("请先保存主表信息");
                    return;
                }
                this.paramForm.interfaceId = this.api.id;
                if (this.paramForm.id == null) {
                    // alert("id为空请从列表过来");
                    addApiParam(this.paramForm).then(response =>{
                        this.handleReturn(response,
                        function() {
                            this.apiParams.unshift(this.paramForm) ;
                            this.paramVisiable = false;
                        });
                    }).
                    catch(response =>{
                        this.handleError(response);
                    })
                } else {

                    updateApiParam(this.paramForm).then(response =>{

                        this.handleReturn(response,
                        function() {
                            this.apiParams.unshift(this.paramForm);
                            this.paramVisiable = false;
                        });

                    }).
                    catch(response =>{
                        this.handleError(response);
                    })

                }

                console.log(this.apiParams);
            },

            handleparamDelete(row) {
                const index = this.apiParams.indexOf(row);
                this.apiParams.splice(index, 1);
                if (row.id) {
                    this.deleteParamsId.push(row.id);
                }
                /*
              deleteApiParam(row.id).then(response => {

                             this.handleReturn(response,function(){ this.apiParams.splice(index, 1)});

                         })  .catch(response => {
                                                                                   this.handleError(response);
                                                                                                })
                                                                                                */

            },
            handleReturn: function(response, callback) {
                if (response.data.r == 0) {
                    this.$notify.success({
                        title: '成功',
                        message: '执行成功'
                    })

                    callback.apply(this);
                } else {
                    this.$notify.success({
                        title: '失败',
                        message: response.data.data.msg
                    })
                }
            },
            //处理错误信息
            handleError: function(response) {

                MessageBox.alert('业务错误：' + response.data.msg, '警告', {
                    confirmButtonText: '确定',
                    type: 'error'
                })
            },
            //保存主表信息
            save: function() {
                this.api.interfaceParams = this.apiParams;
                var interfaceHeaders = JSON.stringify(this.apiHeaders);
                console.log(interfaceHeaders);
                this.api.interfaceHeaders = interfaceHeaders;

                if (this. apiId  == null) {
                    //alert("id为空请从列表过来");


//alert(this.apiHeaders.length)
//return;
                    addInterfaceInfo(this.api).then(response =>{
                        this.handleReturn(response,
                        function() {
                            //this.$router.push({
                           //     path: '/api/list'
                            //})
                        });
                    }).
                    catch(response =>{
                        this.handleError(response);
                    })

                } else {
                    this.api.id = this. apiId ;
                    updateInterfaceInfo(this.api).then(response =>{
                        this.handleReturn(response,
                        function() {
                            this.$router.push({
                                path: '/api/list'
                            })
                        });
                    }).
                    catch(response =>{
                        this.handleError(response);
                    })

                }

            },

            handleParamEdit(row) {
                this.paramForm = row;
                this.paramVisiable = true
            },
            handleTest2() {
                this.api.interfaceParams = this.apiParams;
                 this.api.headers = this.apiHeaders;
                var _this = this;
                interfaceInfoTest(this.api).then(response =>{
                    this.handleReturn(response,
                    function() {
                        //  _this.httResult =response.data.data;
                        _this.httpResult = response.data.data;
                        console.log(response);

                        this.$notify.success({
                            title: '执行结果',
                            message: response.data.data
                        })

                    });
                }).
                catch(response =>{
                    this.handleError(response);
                })
            },
            handleTest() {

                var url = this.api.url;
                var contentType = this.api.contentType;

                var json = {};

                for (var i = 0; i < this.apiParams.length; i++) {
                    json["" + this.apiParams[i].paramName] = this.apiParams[i].paramValue;
                }

                var bodyJsonFlag = false;

                var url = this.api.url + "?1=1"; //获取请求 get the request url 默认加上?号
                var params = {};
                var paramsGetFlag = false;

                if (this.apiParams && this.apiParams.length > 0 && this.apiParams[0].paramIn.toLocaleLowerCase() == 'body' && this.apiParams.length == 1) {
                    bodyJsonFlag = true;
                    json = eval('(' + this.apiParams[0].paramValue + ')');
                } else {
                    for (var i = 0; i < this.apiParams.length; i++) {
                        var nowParam = this.apiParams[i];
                        var paramName = nowParam.paramName;
                        var paramType = nowParam.paramType;
                        var paramIn = nowParam.paramIn;
                        var paramValue = nowParam.paramValue;
                        if (paramType && paramType.toLocaleLowerCase() == 'array') { //如果参数的格式是数组的话
                            if (paramValue) { //alert("怎么会有数组的");
                                if (paramValue.indexOf("[") != -1) {
                                    json[paramName] = eval('(' + json[paramName] + ')'); //如果有数组参数就转换成字符串json格式
                                }
                            }

                        }
                        if (paramIn.toLocaleLowerCase() == 'body') {
                            json = eval('(' + paramValue + ')');
                            // json = json["body请求体"];//eval('{'++'}');
                            // break;
                            bodyJsonFlag = true;
                            if (paramValue) { //alert("怎么会有数组的");
                                if (paramValue.indexOf("[") != -1) {
                                    json[paramName] = eval('(' + json[paramName] + ')'); //如果有数组参数就转换成字符串json格式
                                }
                            }
                        }

                        if (paramIn.toLocaleLowerCase() == 'query') { //如果有url参数 就放到url参数里面
                            //如果是查询参数就拼接到url里
                            url += "&" + paramName + "=" + paramValue;
                            delete json[paramName]; //如果在query 里的 就删除掉
                        }

                        if (paramIn.toLocaleLowerCase() == 'path') { //如果有url参数 就放到url参数里面
                            //如果是url path 参数就拼接到url里
                            //  url+="/"+json[this.content.parameters[i].name];
                            if (this.api.url.indexOf("{") > 0) { //如果含有 if contain path variable
                                console.log("进行替换");
                                var replaceId = url.substr(url.indexOf("{") + 1, url.indexOf("}") - url.indexOf("{") - 1);

                                url = url.replace("{" + replaceId + "}", json[replaceId]); //replace it put the variable into url
                                //alert(replaceId);
                            }
                        }
                    }
                }
                var _this = this;

                axios({
                    method: this.api.httpType,
                    url: url,
                    data: json,
                    headers: {
                        'Content-Type': this.api.contentType
                    },
                }).then(function(response) {
                    _this.httpResult = response.data;
                    console.log(response);
                }).
                catch(function(error) {
                    console.log(error);
                })

                /* this.api.params=this.apiParams;

                   testInterfaceInfo(this.api).then(response => {
                    this.handleReturn(response,function(){ this.$router.push({ path: '/api/list' })});
                    })  .catch(response => {
                    this.handleError(response);
                    })*/
            },
            handleCancel() {
                this.$router.push({
                    path: '/api/list'
                });
            },
            addExampleTest() {
                var testExamples = {};
                for (var i = 0; i < this.apiParams.length; i++) {
                    testExamples[this.apiParams[i].paramName] = this.apiParams[i].paramValue;
                }

                addExampleTest({
                    id: this.api.id,
                    params: JSON.stringify(testExamples)
                }).then(response =>{
                    this.handleReturn(response,
                    function() {
                        alert("保存测试用例成功")
                    });
                }).
                catch(response =>{
                    this.handleError(response);
                })
            },
            addApiParams() {
                this.apiParams.push({});
            },
            addApiHeaders() {
                this.apiHeaders.push({});
            },
            addInterfaceTest(){
            this.interfaceTests.push({});
            },
            deleteExampleTest(row) {
                deleteExampleTest(row.id).then(response =>{
                    this.handleReturn(response,
                    function() {
                        alert("删除测试用例成功")
                    });
                    const index = this.interfaceTests.indexOf(row);
                    this.interfaceTests.splice(index, 1);

                }).
                catch(response =>{
                    this.handleError(response);
                });
            },

            applyExampleTest(row) {
                var params = row.params;
                var json = eval("(" + params + ")");
                //  var arr = this.apiParams.slice();
                for (var key in json) {

                    for (var i = 0; i < this.apiParams.length; i++) {
                        if (this.apiParams[i].paramName == key) {
                            this.apiParams[i].paramValue = json[key];
                            break;
                        }
                    }

                }

            },






        }
    }


</script>
