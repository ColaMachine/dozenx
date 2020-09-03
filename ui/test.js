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
    interfaceInfoTest
}
from '@/api/api'import {
    createStorage,
    uploadPath
}
from '@/api/storage'import Editor from '@tinymce/tinymce-vue'import {
    MessageBox
}
from 'element-ui'import {
    getToken
}
from '@/utils/auth'import axios from 'axios';
export
default {
        name:
        'ApiEdit',
        components: {
            Editor
        },
        data() {
            return {
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
                paramForm: {},
                paramVisiable: false,
                interfaceTests: [],
                //测试用例
                apiParams: [],
                httpResult: null,
                //测试结果
                paramForm: {},

                editorInit: {
                    language: 'zh_CN',
                    convert_urls: false,
                    plugins: ['advlist anchor autolink autosave code codesample colorpicker colorpicker contextmenu directionality emoticons fullscreen hr image imagetools importcss insertdatetime link lists media nonbreaking noneditable pagebreak paste preview print save searchreplace spellchecker tabfocus table template textcolor textpattern visualblocks visualchars wordcount'],
                    toolbar: ['searchreplace bold italic underline strikethrough alignleft aligncenter alignright outdent indent  blockquote undo redo removeformat subscript superscript code codesample', 'hr bullist numlist link image charmap preview anchor pagebreak insertdatetime media table emoticons forecolor backcolor fullscreen'],
                    images_upload_handler: function(blobInfo, success, failure) {
                        const formData = new FormData() formData.append('file', blobInfo.blob()) createStorage(formData).then(res = >{
                            success(res.data.data.url)
                        }).
                        catch(() = >{
                            failure('上传失败，请重新上传')
                        })
                    }
                }

            }
        },
        computed: {

},
        created() {
            this.init()
        },
        methods: {

            init: function() {
                if (this.$route.query.id == null) {
                    return
                }

                const apiId = this.$route.query.id getInfo(apiId).then(response = >{
                    this.api = response.data.data;

                })

                getApiParams(apiId).then(response = >{

                    this.apiParams = response.data.data;

                }) getInterfaceTests(apiId).then(response = >{
                    this.interfaceTests = response.data.data;
                })

            },

            handleparamShow() {
                this.paramForm = {}
                this.paramVisiable = true
            },

            handleparamAdd() {

                if (this.$route.query.id == null) {
                    alert("请先保存主表信息");
                    return;
                }
                this.paramForm.interfaceId = this.api.id;
                if (this.paramForm.id == null) {
                    // alert("id为空请从列表过来");
                    addApiParam(this.paramForm).then(response = >{
                        this.handleReturn(response,
                        function() {
                            this.apiParams.unshift(this.paramForm) this.paramVisiable = false;
                        });
                    }).
                    catch(response = >{
                        this.handleError(response);
                    })
                } else {

                    updateApiParam(this.paramForm).then(response = >{

                        this.handleReturn(response,
                        function() {
                            this.apiParams.unshift(this.paramForm) this.paramVisiable = false;
                        });

                    }).
                    catch(response = >{
                        this.handleError(response);
                    })

                }

                console.log(this.apiParams);
            },

            handleparamDelete(row) {
                const index = this.apiParams.indexOf(row) this.apiParams.splice(index, 1);
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

                if (this.$route.query.id == null) {
                    //alert("id为空请从列表过来");
                    addInterfaceInfo(this.api).then(response = >{
                        this.handleReturn(response,
                        function() {
                            this.$router.push({
                                path: '/api/list'
                            })
                        });
                    }).
                    catch(response = >{
                        this.handleError(response);
                    })

                } else {
                    this.api.id = this.$route.query.id;
                    updateInterfaceInfo(this.api).then(response = >{
                        this.handleReturn(response,
                        function() {
                            this.$router.push({
                                path: '/api/list'
                            })
                        });
                    }).
                    catch(response = >{
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
                var _this = this;
                interfaceInfoTest(this.api).then(response = >{
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
                catch(response = >{
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
                }).then(response = >{
                    this.handleReturn(response,
                    function() {
                        alert("保存测试用例成功")
                    });
                }).
                catch(response = >{
                    this.handleError(response);
                })
            },
            addApiParams() {
                this.apiParams.push({});
            },
            deleteExampleTest(row) {
                deleteExampleTest(row.id).then(response = >{
                    this.handleReturn(response,
                    function() {
                        alert("删除测试用例成功")
                    });
                    const index = this.interfaceTests.indexOf(row);
                    this.interfaceTests.splice(index, 1);

                }).
                catch(response = >{
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

            }

        }
    }