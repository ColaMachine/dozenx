<template>
<ul class="pagination">

    <li v-for="item in pageAry">
        <template v-if="item=='下一页'">
             <a @click="goNextPage()" href="javascript:void(0)" >{{item}}</a>
        </template>
         <template  v-else-if="item=='上一页'">
            <a @click="goPrePage()"  href="javascript:void(0)"  :class="page.curPage==item?'pageCurrent':''">{{item}}</a>
         </template>
          <template  v-else-if="item==page.curPage">
                     <a   href="javascript:void(0)"  class="pageCurrent">{{item}}</a>
                  </template>
        <template  v-else>
            <a href="javascript:void(0)"  @click="goPage(item)" >{{item}}</a>
         </template>

    </li>
<li class="jumpToPage">转至<input type="text" class="input_num" id="input_num">页</li><li><a href="javascript:void(0);" class="a_jumpTo" onclick="return on_check_comment_page(2, 'https://www.smzdm.com/p/14039324/', this)">GO</a></li>
</ul>

</template>
<script type="text/javascript">
import zwIcon from '../icon/zwIcon.vue';

export default {
        name: 'zwPage',
         components:{zwIcon},
        props:
            ["page"]//"tabs"
        ,
        data () {
            return {
                pageAry:[]
            };
        },
        computed: {

        },
        mounted () {



        },
        methods: {

            goNextPage(){
               if( this.page.curPage>=this.page.totalPage){
                return;

                }

                this.goPage(  this.page.curPage+1);
              //  this.renderPage();
            },
 goPage(index){console.log("跳转到"+index+"页码");
                //调用父亲方法
                this.$emit('goPage',index)
              // this.curPage=index;
                // this.renderPage();
            },
            goPrePage(){
                if( this.page.curPage>1){
                 this.goPage(  this.page.curPage-1);

                }
            },
            renderPage:function(){




                 var page = this.page;
console.log(page);
                  if (page == null)
                         return;

                          if (page.totalCount == null)
                                             return;

                                          if (page.totalCount == 0)
                                             return;



                var totalPage = page.totalPage;
       console.log("发现page有变化");
                console.log(page);




                //一般是显示5个页数
                //从 curPage -2 开始 到 curPage+2
               // var total = 1;
               var curPage = page.curPage;
               this.pageAry=[];
               var pageAry=this.pageAry;
                var pageHtml = "<nav class=\"nav\"> <ul class=\"pagination pagination-sm\"><li>";

                if(curPage==1){
                // pageAry.push(1);
                    pageHtml+="<span href=\"javascript:void(0)\" class=\"page_bg pre \" aria-label=\"Previous\">上一页</span>";
                }else{
                    console.log("显示上一页");
                    pageAry.push("上一页");

                    pageHtml+="<a href=\"javascript:void(0)\" class=\"page_bg pre \" aria-label=\"Previous\">上一页</a>";
                }
                var totalPage = page.totalPage;
                var min = 0, max = totalPage;
                var middel = curPage;
                var _min = 0, _max = 0;
                _min = (middel - 2) < 1 ? 1 : (middel - 2);
                _max = (middel + 2) > max ? max : (middel + 2);

                if (_min == 1) {
                    _max = middel + (5 - middel - _min + 1);
                    if (_max >= max)
                        _max = max;
                }
                if (_max == max) {
                    _min = middel - 5 + (max - middel) + 1;
                    if (_min <= 1) {
                        _min = 1;
                    }
                }
                if(_min>1){
                  pageAry.push(1);

                  //  pageAry.push("上一页");
                     pageAry.push("...");
                    pageHtml += "<li class=\"  num\"><a href=\"javascript:void(0)\" >"+1+"</a></li><li ><span>...</span></li>";
                }
                for (var i = _min; i <= _max; i++) {
                    if (i == page){
                         pageAry.push("当前页");
                      //  pageHtml += "<li class=\"  num\" ><span class='curr page_bg'>"
                        //        + i + "</span></li>";
                   } else{
                    pageAry.push(i);

                     //   pageHtml += "<li class=\"num clearfix\" ><a href=\"javascript:void(0)\" >" + i
                       //         + "</a></li>";
                    }
                }


                if(_max<totalPage){
                     pageAry.push("...");
                     //pageAry.push("下一页");
                      pageAry.push(totalPage);
                    //pageHtml += "<li  ><span>...</span></li><li class=\"  num\" ><a href=\"javascript:void(0)\" >"+totalPage+"</a></li>";
                }
                //加上最后一页 和...号

                //console.log(page);
                if(curPage==totalPage){
                   //  pageAry.push("下一页");
                 //pageHtml+="<li><span href=\"javascript:void(0)\" class=\"page_bg next\" aria-label=\"Next\">下一页</span>";
                 }else{
                      pageAry.push("下一页");

                 //pageHtml+="<li><a href=\"javascript:void(0)\" class=\"page_bg next\" aria-label=\"Next\">下一页</a>";
                 }
                //pageHtml += "</li><li><span style='border-width:0px;white-space:nowrap;'>共"+totalPage+"页，"+this.p.records+"条信息,<a style='border-width:0px;color:black'>每页"+this.p.rowNum+"条<i class='fa fa-caret-down'></i></a></span></li></ul></nav>";

            }

        },

        watch:{
          page(newVal,oldVal){  console.log("page 发生变化了");
            this.renderPage(newVal);
          },
          page:{
            curPage(newVal,oldVal){
                 this.renderPage(this.page);
            }
          }
        },
         events: {

          }
    };



</script>
<style>

</style>
