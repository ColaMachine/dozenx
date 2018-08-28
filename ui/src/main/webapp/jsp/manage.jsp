<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name=”viewport” content=”width=device-width, initial-scale=1, maximum-scale=1″>
<title>后台管理系统</title>
<link rel="stylesheet" type="text/css" href="${path}/static/css/bootstrap.min.css" >

<link rel="stylesheet" type="text/css" href="${path}/static/css/main.css" >
<link rel="stylesheet" type="text/css" href="${path}/static/css/menu3.css" >
<link rel="stylesheet" type="text/css" href="${path}/static/css/head.css" >


<link rel="stylesheet" type="text/css" href="${path}/static/css/collapse.css" >
<link rel="stylesheet" type="text/css" href="${path}/static/css/font-awesome.css" >

<script type="text/javascript" src="${path}/static/js/jquery.min.js"></script>
<script type="text/javascript" src="${path}/static/js/common.js"></script>
<script type="text/javascript" src="${path}/static/js/router.js"></script>
<script type="text/javascript" src="${path}/static/js/menu.js"></script>
<script type="text/javascript" src="${path}/static/js/dom.js"></script>

<script type="text/javascript" src="${path}/static/js/animation.js"></script>
<script type="text/javascript" src="${path}/static/js/slider.js"></script>
<script type="text/javascript" src="${path}/static/js/imageUtil.js"></script>
<script src="/static/js/vue.js"></script>
<link rel="stylesheet" type="text/css" href="/static/css/slider.css" >
<%
String path = request.getContextPath();

String basePath = request.getScheme()+"://"+request.getServerName()
+":"+request.getServerPort()+path+"/";

pageContext.setAttribute("basePath",basePath);
%>

<script type="text/javascript" >
var WEBCONTEXT="${path}";
var PATH="${path}";
var cssAry=[

"/static/css/grid.css",
/*"/static/css/bankgrid.css",*/
/*"/static/css/jqgrid.css",*/
 "/static/css/head.css",

    "/static/css/widget.css",
   "/static/css/window.css",
    "/static/css/zTreeStyle.css",
    "/static/css/layer.css",

];
var jsAry=[/*"/static/js/menu.js" ,
                     "/static/js/validmsg.js",*/
                     "/static/js/DateUtils.js",
                    /* "/static/js/jquery-ui.min.js",*/
                     "/static/js/grid.js",
                      /* "/static/js/jquery.jqGrid.js",*/
                      "/static/js/jquery.form.js",
                      "/static/js/grid.locale-en.js",
                      "/static/js/My97DatePicker/WdatePicker.js",
                      "/static/js/jquery.validate.js",
                      "/static/js/additional-methods.js",
                     /* "/static/js/index.js",*/
                      "/static/js/window.js",
                     "/static/js/bootstrap.min.js",
                      "/static/js/drag.js",
                       /* "/static/js/system/view/calendar/calendarView.js",*/
                      "/static/js/dialog.js",
                      "/static/js/jquery.ztree.core-3.5.js",
                      "/static/js/jquery.ztree.excheck-3.5.js",
                       "/static/js/layer.js",
                        "/ueditor/ueditor.config.js",
                         "/ueditor/ueditor.all.js",
                     /*  "/static/js/location.js",*/


                    ];
includeCSS(cssAry);
includeJS(jsAry);

         /*   setTimeout("loadCssJs()",1000);
                   function loadCssJs(){
                     	Ajax.get(PATH+"/moreCssJs.htm", null, function(data) {
                                 if(data.indexOf("504")!=-1){
                                     window.location="/spcity/login.htm";return;
                                 }
                     			$('#moreCssJs').html(data);
                     		});
                   }*/
</script>


</head>
<body>
<div id="moreCssJs"style="display:none"></div>
<div id="page" class="page" >
	   <nav class="page-nav menu-wrap clearfix  navbar-default navbar-static-side">
        <div class="logo">
            <!-- <span class="logo-head">aWiFi</span> -->

            <%-- <img src="${path}/statics/img/logo.png"></img>
 --%>
             <div class="logo-desc" ><span class="nav-icon"><i onclick="$('#page').toggleClass('collapse1')" style="" class="fa fa-reorder">&nbsp;</i></span><span class="logo-desc-text"'>后台管理系统</span></div>
        </div>
        <div id="menu" class="menu">
        </div>
    </nav>
	<div class="page-wrap main-wrap gray-bg">
		<div id="" class="page-hd  border-bottom">
                <nav class="navbar navbar-static-top" role="navigation" style="margin-bottom: 0">
                    <div class="navbar-header">
                        <a id="collapse" class="navbar-minimalize minimalize-styl-2 btn btn-primary " href="#"><i class="fa fa-bars"></i> </a>
                        <form role="search" class="navbar-form-custom" action="search_results.html">
                            <div class="form-group">
                               <!-- <input type="text" placeholder="查询..." class="form-control" name="top-search" id="top-search">-->
                            </div>
                        </form>
                    </div>
                    <ul class="nav navbar-top-links navbar-right">
                        <li>
                            <span class="m-r-sm text-muted welcome-message">欢迎来到后台管理系统.</span>
                        </li>
                        <li class="dropdown">
                            <a class="dropdown-toggle count-info" data-toggle="dropdown" href="#">
                                <i class="fa fa-envelope"></i>  <span class="label label-warning">0</span>
                            </a>
                            <ul class="dropdown-menu dropdown-messages">
                               <!-- <li>
                                    <div class="dropdown-messages-box">
                                        <a href="profile.html" class="pull-left">
                                            <img alt="image" class="img-circle" src="img/a7.jpg">
                                        </a>
                                        <div class="media-body">
                                            <small class="pull-right">46h ago</small>
                                            <strong>Mike Loreipsum</strong> started following <strong>Monica Smith</strong>. <br>
                                            <small class="text-muted">3 days ago at 7:58 pm - 10.06.2014</small>
                                        </div>
                                    </div>
                                </li>
                                <li class="divider"></li>
                                <li>
                                    <div class="dropdown-messages-box">
                                        <a href="profile.html" class="pull-left">
                                            <img alt="image" class="img-circle" src="img/a4.jpg">
                                        </a>
                                        <div class="media-body ">
                                            <small class="pull-right text-navy">5h ago</small>
                                            <strong>Chris Johnatan Overtunk</strong> started following <strong>Monica Smith</strong>. <br>
                                            <small class="text-muted">Yesterday 1:21 pm - 11.06.2014</small>
                                        </div>
                                    </div>
                                </li>
                                <li class="divider"></li>
                                <li>
                                    <div class="dropdown-messages-box">
                                        <a href="profile.html" class="pull-left">
                                            <img alt="image" class="img-circle" src="img/profile.jpg">
                                        </a>
                                        <div class="media-body ">
                                            <small class="pull-right">23h ago</small>
                                            <strong>Monica Smith</strong> love <strong>Kim Smith</strong>. <br>
                                            <small class="text-muted">2 days ago at 2:30 am - 11.06.2014</small>
                                        </div>
                                    </div>
                                </li>
                                <li class="divider"></li>-->
                                <li>
                                    <div class="text-center link-block">
                                        <a href="mailbox.html">
                                            <i class="fa fa-envelope"></i> <strong>暂无消息</strong>
                                        </a>
                                    </div>
                                </li>
                            </ul>
                        </li>
                        <li class="dropdown">
                            <a class="dropdown-toggle count-info" data-toggle="dropdown" href="#">
                                <i class="fa fa-bell"></i>  <span class="label label-primary">0</span>
                            </a>
                            <ul class="dropdown-menu dropdown-alerts">
                                <li>
                                    <a href="mailbox.html">
                                        <div>
                                            <i class="fa fa-envelope fa-fw"></i> 你 有  0 条消息
                                            <span class="pull-right text-muted small">0 分钟之前</span>
                                        </div>
                                    </a>
                                </li>
                                <li class="divider"></li>
                                <li>
                                    <a href="profile.html">
                                        <div>
                                            <i class="fa fa-twitter fa-fw"></i> 0 个关注
                                            <span class="pull-right text-muted small">0 分钟之前</span>
                                        </div>
                                    </a>
                                </li>
                                <li class="divider"></li>
                                <li>
                                    <a href="grid_options.html">
                                        <div>
                                            <i class="fa fa-upload fa-fw"></i> 系统重启
                                            <span class="pull-right text-muted small">很久之前</span>
                                        </div>
                                    </a>
                                </li>
                                <li class="divider"></li>
                                <li>
                                    <div class="text-center link-block">
                                        <a href="#">
                                            <strong>查看所有消息</strong>
                                            <i class="fa fa-angle-right"></i>
                                        </a>
                                    </div>
                                </li>
                            </ul>
                        </li>


                        <li>
                            <a id="loginOut" >
                                <i class="fa fa-sign-out"></i> 退出
                            </a>
                        </li>
                    </ul>

                </nav>
                </div>
<div class="row  page-heading">
                <div class="col-lg-12
                ">
                 <!--   <span id="page-title">主页</span>-->
                    <ol class="breadcrumb">
                        <li>
                            <a href="index.htm">主页</a>
                        </li>
                        <li>
                            <a id="page-title-2"></a>
                        </li>
                       <!-- <li class="active">
                            <strong>Flot Charts</strong>
                        </li>-->
                    </ol>
                </div>
                <div class="col-lg-2">

                </div>
            </div>

            <div style="display:none" class="page-ft footer-wrap footer">
                        <div class="pull-right">
                            10GB of <strong>250GB</strong> Free.
                        </div>
                        <div>
                            <strong>Copyright</strong> crop © 2014-2020
                        </div>
                    </div>


    <div id="main " class="  row main  "  style="width:100%;height:100%;">


	</div>

</div>



</div>
<div class="widget"></div>
    <div class="mask" ></div>


</body>



<script type="text/javascript" >
Ajax.getJSON(PATH+"/auth/menu/list.json",null,function(result){
    var menuList =result.data;
    <% HttpSession s= request.getSession();
        String resourceStr = (String)s.getAttribute("resourceStr");
    %>

   // zMenu.init("menu",menuList,{id:"id",url:"url",pid:"pid",name:"name"});
});
var resources="<%=resourceStr%>";
var menuList=[

/* {id:100,name:"Dashboard",url:"",pid:0,icon:"fa fa-dashboard"},
{id:1001,name:"Dashboard V1",url:"www.baidu.com",pid:100,icon:"fa fa-circle-o"},
{id:1002,name:"Dashboard V2",url:"www.baidu.om",pid:100,icon:"fa fa-circle-o"},*/
/*{id:1,name:"日志管理",url:"",pid:0,icon:"fa fa-bank"},
{id:2,name:"访问日志",url:"",pid:1},
{id:3,name:"异常日志",url:"/log/listRequestLog",pid:1},
{id:21,name:"访问日志A",url:"/log/listRequestLog",pid:2},
{id:22,name:"访问日志B",url:"/log/listRequestLog",pid:2},*/
{id:5,name:"用户管理",url:"",pid:0,icon:"fa fa-user"},

/*  {id:51,name:"level51",url:"",pid:5,icon:"fa fa-diamond"},
{id:511,name:"level511",url:"",pid:51,icon:"fa fa-diamond"},
{id:5111,name:"level5111",url:"",pid:511,icon:"fa fa-diamond"},
{id:5112,name:"level5112",url:"",pid:511,icon:"fa fa-diamond"},
{id:512,name:"level512",url:"",pid:51,icon:"fa fa-diamond"},*/
/* {id:51,name:"用户管理",url:"/user/list.htm",icon:"fa fa-spinner",pid:5},
{id:6,name:"角色管理",url:"/auth/role/list.htm",pid:5},*/
{id:7,name:"日历",url:"/static/html/CalendarView.html",icon:"fa fa-calendar",pid:0},
{id:8,name:"组件库",url:"",icon:"fa fa-bug",pid:0},
{id:"8-1-1",name:"头部",url:"/static/html/example/head.html",icon:"fa fa-spinner",pid:8},
{id:"8-1",name:"按钮",url:"/static/html/example/button.html",icon:"fa fa-spinner",pid:8},

{id:"8-2",name:"导航条",url:"/static/html/example/navbar.html",icon:"fa fa-spinner",pid:8},
{id:9,name:"手机登录页面",url:"/login/login.htm",icon:"fa fa-spinner",pid:8},
{id:10,name:"上传图片",url:"/static/html/imageCompress.html",icon:"fa fa-bank",pid:8},
{id:11,name:"列表",url:"/static/html/example/table.html",icon:"fa fa-spinner",pid:8},
{id:81,name:"地区选择",url:"/static/html/example/location.html",icon:"fa fa-spinner",pid:8},
{id:82,name:"富文本编辑器",url:"/static/html/example/uedit.html",icon:"fa fa-spinner",pid:8},
{id:12,name:"alert",url:"/static/html/example/alert.html",icon:"fa fa-spinner",pid:8},
/* {id:13,name:"短信",url:"/smsBatch/list.htm",icon:"fa fa-spinner",pid:0},
{id:14,name:"合作伙伴",url:"/PartnerUserlist.htm",icon:"fa fa-spinner",pid:0},*/
{id:15,name:"用户",url:"/sysUser/list.htm",icon:"fa fa-spinner",pid:5},
{id:16,name:"角色",url:"/sysRole/list.htm",icon:"fa fa-spinner",pid:5},
/* {id:17,name:"资源",url:"/sysResource/list.htm",icon:"fa fa-spinner",pid:5},*/
{id:50017,name:"权限",url:"/sysPermission/list.htm",icon:"fa fa-spinner",pid:5},
{id:50018,name:"菜单",url:"/sysMenu/list.htm",icon:"fa fa-spinner",pid:5},
{id:50018,name:"角色权限关联",url:"/sysRolePermission/listMapper.htm",icon:"fa fa-spinner",pid:5},
/*{id:18,name:"用户角色",url:"/sysUserRole/list.htm",icon:"fa fa-spinner",pid:5},
{id:19,name:"角色资源",url:"/sysRoleResource/list.htm",icon:"fa fa-spinner",pid:5},
{id:20,name:"用户资源",url:"/sysUserResource/list.htm",icon:"fa fa-spinner",pid:5},*/
{id:21,name:"用户角色关联",url:"/sysUserRole/listMapper.htm",icon:"fa fa-spinner",pid:5},
/*{id:22,name:"角色资源关联",url:"/sysRoleResource/listMapper.htm",icon:"fa fa-spinner",pid:5},
{id:23,name:"用户资源关联",url:"/sysUserResource/listMapper.htm",icon:"fa fa-spinner",pid:5},*/
/* {id:24,name:"短信验证码",url:"/smsRecord/list.htm",icon:"fa fa-spinner",pid:0},*/
{id:25,name:"系统管理",url:"",icon:"fa fa-gear",pid:0},

{id:2501,name:"系统日志",url:"/sysLog/list.htm",icon:"fa fa-file-archive-o",pid:25},
    {id:2502,name:"日志标签",url:"/sysLogTag/list.htm",icon:"fa fa-tag",pid:25},
    {id:2503,name:"配置",url:"/sysConfig/list.htm",icon:"fa fa-tag",pid:25},

{id:26,name:"动物园管理",url:"",icon:"fa fa-spinner",pid:0},
{id:27,name:"最新视频",url:"/videoNew/list.htm",icon:"fa fa-spinner",pid:26},
{id:28,name:"最新视频",url:"/static/html/videoNew.html",icon:"fa fa-spinner",pid:26},

{id:30,name:"资料",url:"",icon:"fa fa-spinner",pid:0},///*海绵城市*/

    {id:302,name:"新闻资讯",url:"/artical/list.htm",icon:"fa fa-spinner",pid:30},
    {id:303,name:"新闻资讯审核",url:"/artical/listAudit.htm",icon:"fa fa-spinner",pid:30},
    {id:303,name:"新闻资讯审核",url:"/artical/listAudit.htm",icon:"fa fa-spinner",pid:30},
    {id:304,name:"专家智库",url:"/expert/list.htm",icon:"fa fa-spinner",pid:30},
    {id:305,name:"合作伙伴",url:"/partner/list.htm",icon:"fa fa-spinner",pid:30},
    {id:306,name:"专家介绍",url:"/expertDetail/list.htm",icon:"fa fa-spinner",pid:30},
    {id:307,name:"专家文献",url:"/expertArtical/list.htm",icon:"fa fa-spinner",pid:30},
    {id:308,name:"合作伙伴介绍",url:"/partnerDetail/list.htm",icon:"fa fa-spinner",pid:30},

{id:31,name:"图表",url:"",icon:"fa fa-bar-chart",pid:0},
    {id:311,name:"登录人数",url:"/static/html/example/echarts.html",icon:"fa fa-area-chart",pid:31},
{id:32,name:"拍卖行",url:"",url:"/rubish/auction.html",icon:"fa fa-area-chart",pid:0},
{id:33,name:"页面制作",url:"",icon:"fa fa-area-chart",pid:0},
    {id:335,name:"测试",url:"window:/static/html/editor/reactTest.html",icon:"fa fa-area-chart",pid:33},
    {id:331,name:"编辑器",url:"/static/html/editor/edit.html",icon:"fa fa-area-chart",pid:33},
{id:332,name:"组件",url:"/component/list.htm",icon:"fa fa-area-chart",pid:33},
{id:333,name:"模板",url:"/template/list.htm",icon:"fa fa-area-chart",pid:33},
{id:334,name:"模板组件匹配",url:"/editorTempComp/listMapper.htm",icon:"fa fa-area-chart",pid:33},
{id:34,name:"商品",url:"",icon:"fa fa-area-chart",pid:0},
    {id:340,name:"商品列表",url:"/goods/list.htm",icon:"fa fa-area-chart",pid:34},
{id:35,name:"商户",url:"",icon:"fa fa-area-chart",pid:0},
{id:351,name:"商户首页",url:"window:/static/html/merchant/index.html?logId=logId&deviceId=FatAP_31_201603181c537d5c-2333-4611-9d0d-3f5db479e4a6&mobilePhone=13958173965&userMac=1C184A15A5D9&userIP=192.168.10.15&gwAddress=192.168.10.1&gwPort=2020&nasName=&userType=NEW_USER&token=token_test&url=www.163.com",icon:"fa fa-bar-chart",pid:35},
{id:352,name:"商户滚动图片",url:"/static/html/merchant/MerchantPicList.html",icon:"fa fa-bar-chart",pid:35},
{id:353,name:"商户滚动消息",url:"/static/html/merchant/MerchantNoticeList.html",icon:"fa fa-bar-chart",pid:35},
{id:354,name:"商户新闻",url:"/static/html/merchant/MerchantNewsList.html",icon:"fa fa-bar-chart",pid:35},
{id:36,name:"实验室",url:"",icon:"fa fa-bar-chart",pid:0},
{id:361,name:"vue",url:"/static/html/api/apiList.html",icon:"fa fa-bar-chart",pid:36},
{id:362,name:"vueList",url:"/static/html/lab/vue/commonListTemplate.html",icon:"fa fa-bar-chart",pid:36},
{id:37,name:"api",url:"",icon:"fa fa-bar-chart",pid:0},
{id:371,name:"apiUrl",url:"/api/url/list.htm",icon:"fa fa-bar-chart",pid:37},
{id:372,name:"apiUrl",url:"/api/url/list.htm",icon:"fa fa-bar-chart",pid:37},
{id:373,name:"apiUrl",url:"/api/url/list.htm",icon:"fa fa-bar-chart",pid:37},
{id:38,name:"地图",url:"",icon:"fa fa-bar-chart",pid:0},
{id:381,name:"地图数据",url:"/mapdata/list.htm",icon:"fa fa-bar-chart",pid:38},
{id:382,name:"地图展示",url:"window:/static/html/weixin/tecent_map.html",icon:"fa fa-bar-chart",pid:38},
]


zMenu.init("menu",menuList,{id:"id",url:"url",pid:"pid",name:"name"});
$(document).ready(function(){if($(".page-wrap").height()<$(window).height())
    $(".page-wrap").css("min-height",$(window).height()+$(window).scrollTop());
})
$(document).scroll(function(){if($(".page-wrap").height()<$(window).height())
    $(".page-wrap").css("min-height",$(window).height()+$(window).scrollTop());
})
$(document).resize(function(){console.log(1);

if($(".page-wrap").height()<$(window).height())
 $(".page-wrap").css("min-height",$(window).height()+$(window).scrollTop());

})

 $(document).ready(function(){
    $("#loginOut").click(function(){
        window.location=PATH+"/logout.htm";
    })

    function fix_height(){
    $(".grid-content").css("height",$(window).height()-153-95-60);
    $(".page-wrap").css("min-height",$(window).height()+$(window).scrollTop());
    // var h = $("#tray").height();
    //  $("#preview").attr("height", (($(window).height()) - h) + "px");
    }
    $(window).resize(function(){ fix_height(); }).resize();

});
</script>

<!--
<script type="text/javascript" src="${path}/static/js/head.js"></script>
	<script type="text/javascript" src="${path}/static/js/react.js"></script>
<script type="text/javascript" src="${path}/static/js/JSXTransformer.js"></script>
   <script type="text/jsx"  src="static/js/editor_head.js"></script>-->
</html>