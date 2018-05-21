<template>
<div>
<article>
<section>
<h2>Collapse折叠面板</h2>
<p>可以折叠/展开的内容区域。。</p>
<h3>何时使用#</h3>

<p>
对复杂区域进行分组和隐藏，保持页面的整洁。
手风琴 是一种特殊的折叠面板，只允许单个内容区域展开。
</p>

<h3>代码演示<zwIcon type="windows" title="展开全部代码"></zwIcon></h3>
<zwRow >
<zwCol span=24>
    <zwBox class="">
        <div slot="zwCodeBoxDemo">

             <zwTable :param="gridParam"> </zwTable>
        </div>
        <div slot="zwCodeBoxMeta"  title="tab类型">
            zwAppDialogue 是适用于手机端 在底部展现出 弹出框
        </div>
        <div slot="zwCodeBoxPanel"  >

            <textarea ref="textarea" id="textarea" name="textarea">
 <zwAppDialogue ></zwAppDialogue>
            </textarea>

       </div>
    </zwBox>
</zwCol>



</zwRow>




</section>
</article>
</div>
</template>
<script>

import zwLayout from '../../../component/layout/zwLayout.vue';
import zwHeader from '../../../component/layout/zwHeader.vue';
import zwContent from '../../../component/layout/zwContent.vue';
import zwSider from '../../../component/layout/zwSider.vue';
import zwFooter from '../../../component/layout/zwFooter.vue';
import zwRow from '../../../component/layout/zwRow.vue';
import zwCol from '../../../component/layout/zwCol.vue';
import zwIcon from '../../../component/icon/zwIcon.vue';
import zwBox from '../../../component/layout/zwBox.vue';


import zwTable from '../../../component/datadisplay/zwTable.vue';
export default {
    components:{zwRow,zwCol,zwIcon,zwBox,zwHeader,zwContent,zwFooter,zwLayout,zwSider,zwTable},
    data () {

        return {
            gridParam:{
                    datatype: "json",
                    viewrecords: true, sortorder: "desc", caption:"",
                    rowNum:10,
                    rowList:[10,20,30],
                    multiselect : false,
                    url : PATH+'/sysUser/list.json',
                    autowidth:true,
                    grid:"#SysUserGrid",
                    pager:"#SysUserGrid-Pager",
                    jsonReader:jsonReader,
                    colNames : [
                    "编号","用户名","昵称","类型","状态","邮箱地址","手机号码","身份证号码","头像" , '操作' ],
                    colModel : [
                        {   name : 'id',width : 80,

                        } ,
                        {   name : 'username',width : 80,

                        } ,
                        {   name : 'nkname',width : 80,

                        } ,
                        {   name : 'type',width : 80,

                        } ,
                        {   name : 'status',width : 80,
                            formatter : function(value, grid, rows) {
                              var map ={'1':'正常','2':'禁用','3':'未激活',};
                              return map[value];
                            }

                        } ,
                        {   name : 'email',width : 80,

                        } ,
                        {   name : 'telno',width : 80,

                        } ,
                        {   name : 'idcard',width : 80,

                        } ,
                        {   name : 'face',width : 80,

                        } ,

                        {   name : 'operation',
                            width : 150,
                            formatter : function(value, grid, rows) {
                                return gridHelper.getViewHtml(rows.id,"sysUserList")+gridHelper.getEditHtml(rows.id,"sysUserList")+gridHelper.getDelHtml(rows.id,"sysUserList");
                            }
                        }
                    ],
                    onSelectRow: function(id){ //alert("单击选中"+id);
                    },
                    loadComplete:function(data){
                      //  dqData=data;
                    }
                },
            msg: 'Hello World!',
            loading:false,
        }
    }
    ,
    computed: {

    },
    methods:{

    }
}
</script>
<style scoped>

</style>
