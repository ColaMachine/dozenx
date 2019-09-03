<template>
<div>
<article>
<section>
<h2>table</h2>
<p>展现列表数据</p>
<h3>何时使用#</h3>

<p>
对list数据进行展示
</p>

<h3>代码演示<zwIcon type="windows" title="展开全部代码"></zwIcon></h3>
<zwRow >
<zwCol span=24>
    <zwBox class="">
        <div slot="zwCodeBoxDemo">

             <zwTable :param="gridParam"> </zwTable>
        </div>
        <div slot="zwCodeBoxMeta"  title="tab类型">
            zwTable 是适合pc端使用的table表行列数据展示,内部使用grid 实现类似jqgrid
        </div>
        <div slot="zwCodeBoxPanel"  >

            <textarea ref="textarea" id="textarea" name="textarea">
 <zwAppDialogue ></zwAppDialogue>
            </textarea>

       </div>
    </zwBox>
</zwCol>



</zwRow>




<zwRow >
<zwCol span=24>
    <zwBox class="">
        <div slot="zwCodeBoxDemo">

             <zwSimpleTable :list="list" :param="tableParam"> </zwSimpleTable>
        </div>
        <div slot="zwCodeBoxMeta"  title="tab类型">
            zwSimpleTable 是适用于简单的标行列结构 内部用ul li 实现
        </div>
        <div slot="zwCodeBoxPanel"  >

            <textarea ref="textarea" id="textarea" name="textarea">
             <zwAppDialogue ></zwAppDialogue>
            </textarea>

       </div>
    </zwBox>
</zwCol>



</zwRow>


<zwRow >
<zwCol span=24>
    <zwBox class="">
        <div slot="zwCodeBoxDemo">
            <zwPanel state="open">
               <span slot="title" name="title">title123</span>
                <p  slot="body" name="body">

                 <zwSimpleTable :list="list" :param="tableParam"> </zwSimpleTable>

                 </p>

             </zwPanel>
        </div>
        <div slot="zwCodeBoxMeta"  title="tab类型">
            zwSimpleTable 是适用于简单的标行列结构 内部用ul li 实现
        </div>
        <div slot="zwCodeBoxPanel"  >

            <textarea ref="textarea" id="textarea" name="textarea">
 <zwAppDialogue ></zwAppDialogue>
            </textarea>

       </div>
    </zwBox>
</zwCol>

</zwRow>






<zwRow >
<zwCol span=24>
    <zwBox class="">
        <div slot="zwCodeBoxDemo">
<ul class="pagination">
    <li><a href="" class="pageCurrent">1</a></li>
    <li><a href="">2</a></li>
    <li><a href="">3</a></li>
    <li><a href="">4</a></li>
    <li><a href="">5</a></li>
    <li><a href="">6</a></li>
    <li><span class="dotStyle">...</span></li>
    <li><a href="">14</a></li>
    <li class="pagedown"><a href=""><i class="icon-angle-right-o-thin">下一页</i></a>
    </li>
    <li class="jumpToPage">转至<input type="text" class="input_num" id="input_num">页</li>
    <li><a href="javascript:void(0);" class="a_jumpTo"
           onclick="return on_check_comment_page(14, 'https://www.smzdm.com/p/14698489/', this)">GO</a></li>
</ul>
        </div>
        <div slot="zwCodeBoxMeta"  title="tab类型">
            zwSimpleTable 是适用于简单的标行列结构 内部用ul li 实现
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

import zwSimpleTable from '../../../component/datadisplay/zwSimpleTable.vue';
import zwTable from '../../../component/datadisplay/zwTable.vue';
import zwPanel from '../../../component/datadisplay/zwPanel.vue';
export default {
    components:{zwRow,zwCol,zwIcon,zwBox,zwHeader,zwContent,zwFooter,zwLayout,zwSider,zwTable,zwSimpleTable,zwPanel},
    data () {

        return {
            list:[
                             {titile:"1",subTitle:"2",pic:"/static/img/smarthome/烟感1.png"},
                             {titile:"1",subTitle:"2",pic:"/static/img/smarthome/烟感1.png"},
                             {titile:"1",subTitle:"2",pic:"/static/img/smarthome/烟感1.png"},
                             {titile:"1",subTitle:"2",pic:"/static/img/smarthome/烟感1.png"},
                             {titile:"1",subTitle:"2",pic:"/static/img/smarthome/烟感1.png"},
                             {titile:"1",subTitle:"2",pic:"/static/img/smarthome/烟感1.png"},
                             {titile:"1",subTitle:"2",pic:"/static/img/smarthome/烟感1.png"},
                             ],
             tableParam:[
              {   name : 'titile',width : 80,} ,
              {   name : 'subTitle',width : 80, } ,
                  {   name : 'pic',width : 80, } ,
             ],
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
