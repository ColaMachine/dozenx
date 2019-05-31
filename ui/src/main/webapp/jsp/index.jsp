<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<head lang=”en”>

    <meta charset=’utf-8′>

    <meta http-equiv=”X-UA-Compatible” content=”IE=edge,chrome=1″/>

    <meta name=”description” content=”不超过150个字符”/>

    <meta name=”keywords” content=””/>

    <meta name=”author” content=”name, email@gmail.com”/>

    <meta name=”robots” content=”index,follow”/>
    <meta name="viewport" content="width=device-width,height=device-height, user-scalable=no,initial-scale=1, minimum-scale=1, maximum-scale=1,target-densitydpi=device-dpi ">
    <meta name=”apple-mobile-web-app-title” content=”标题”>

    <meta name=”apple-mobile-web-app-capable” content=”yes”/>


    <meta name=”apple-itunes-app” content=”app-id=myAppStoreID, affiliate-data=myAffiliateData, app-argument=myURL”>


    <meta name=”apple-mobile-web-app-status-bar-style” content=”black”/>

    <meta name=”format-detection” content=”telphone=no, email=no”/>

    <meta name=”renderer” content=”webkit”>

    <meta http-equiv=”X-UA-Compatible” content=”IE=edge”>

    <meta http-equiv=”Cache-Control” content=”no-siteapp” />

    <meta name=”HandheldFriendly” content=”true”>

    <meta name=”MobileOptimized” content=”320″>

    <meta name=”screen-orientation” content=”portrait”>

    <meta name=”x5-orientation” content=”portrait”>

    <meta name=”full-screen” content=”yes”>

    <meta name=”x5-fullscreen” content=”true”>

    <meta name=”browsermode” content=”application”>

    <meta name=”x5-page-mode” content=”app”>

    <meta name=”msapplication-tap-highlight” content=”no”>


    <meta http-equiv=”pragma” content=”no-cache”>

    <meta http-equiv=”cache-control” content=”no-cache”>

    <meta http-equiv=”expires” content=”0″>

   </head>

 <%
      String path = request.getContextPath();

      String basePath = request.getScheme()+"://"+request.getServerName()
      +":"+request.getServerPort()+path+"/";
      pageContext.setAttribute("basePath",basePath);
      pageContext.setAttribute("path",path);
      %>
      
<link rel="stylesheet" type="text/css" href="${path}/static/css/font-awesome.css" >
<link rel="stylesheet" type="text/css" href="${path}/static/css/slider.css" >


<!--<link rel="stylesheet" type="text/css" href="${path}/static/css/head.css" >
<link rel="stylesheet" type="text/css" href="${path}/static/css/color.css" >

<link rel="stylesheet" type="text/css" href="${path}/static/css/border.css" >
<link rel="stylesheet" type="text/css" href="${path}/static/css/size.css" >
<link rel="stylesheet" type="text/css" href="${path}/static/css/list.css" >-->


<!--<link rel="stylesheet" type="text/css" href="${path}/static/css/window.css" >
<link rel="stylesheet" type="text/css" href="${path}/static/css/img.css" >

<link rel="stylesheet" type="text/css" href="${path}/static/css/form.css" >


<link rel="stylesheet" type="text/css" href="${path}/static/css/page.css" >


<link rel="stylesheet" type="text/css" href="${path}/static/css/other.css" >-->

<body>


        <section id="head"></section>


</div>

<script src="${path}/static/js/jquery.min.js" type="text/javascript"
            charset="utf-8"></script>

<script src="${path}/static/js/grid1.js" type="text/javascript"
        charset="utf-8"></script>



  <script src="${path}/static/js/common.js" type="text/javascript"
            charset="utf-8"></script>
<script src="${path}/static/js/dom.js" type="text/javascript"
        charset="utf-8"></script>
<!--<script src="${path}/static/js/jquery.colorpicker.js" type="text/javascript"
        charset="utf-8"></script>-->
<script src="${path}/static/js/dom.js" type="text/javascript"
        charset="utf-8"></script>
<script src="${path}/static/js/animation.js" type="text/javascript"
        charset="utf-8"></script>
<script src="${path}/static/js/zwcommon.js" type="text/javascript"
        charset="utf-8"></script>

        <script src="${path}/static/js/imageUtil.js" type="text/javascript"
                charset="utf-8"></script>
<script src="${path}/static/js/DateUtils.js" type="text/javascript"
        charset="utf-8"></script>
<!--
<script src="/static/js/zhihuijiating.js"></script>

-->



<script src="/assets/index.js"></script>


<script>

</script>
</body>
