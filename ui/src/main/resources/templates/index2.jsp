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

 <link rel="stylesheet" type="text/css" href="${path}/static/css/collapse.css" >

<script type="text/javascript" src="${path}/static/js/jquery.min.js"></script>
<script type="text/javascript" src="${path}/static/js/common.js"></script>

	<script type="text/javascript" src="${path}/static/js/react.js"></script>




</head>
<body>

<div id="wrapper"  >

	
</div>
</body>
<script type="text/javascript" src="${path}/static/js/index.js"></script>
<script type="text/javascript" src="${path}/static/js/menu.js"></script>


<script type="text/javascript" >
React.render(React.createElement(Index, null), document.getElementById('wrapper'));
var WEBCONTEXT="${path}";
var PATH="${path}";
includeCSS([
"/static/css/font-awesome.css",
 "/static/css/grid.css",
/*  "/static/css/head.css",*/
   "/static/css/global.css",
    "/static/css/style.css",
    "/static/css/widget.css",
    /*"/static/css/window.css",*/
    "/static/css/zTreeStyle.css",
   /*  "/static/css/layer.css"*/

]);


</script>
<script type="text/javascript" >
Ajax.getJSON(PATH+"/auth/menu/list.json",null,function(result){
var menuList =result.data;
<% HttpSession s= request.getSession();
    String resourceStr = (String)s.getAttribute("resourceStr");
%>


});
</script>



</html>