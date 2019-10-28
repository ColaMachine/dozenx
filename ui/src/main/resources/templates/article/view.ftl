<!DOCTYPE html>

<html lang="zh-cmn-Hans"><head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0,viewport-fit=cover">
    <title>${artical.title}</title>


</head>
<body ontouchstart="">

<div class="page">
    <div class="page__hd">
    <img style="width:100%" src="${artical.pic}" ></img>
        <h1 class="page__title">${artical.title}</h1>
        <p class="page__desc">${artical.createtime}  ${artical.creatorname}</p>
    </div>
    <div class="page__bd">
        <artical class="weui-artical">
             ${artical.content}
        </artical>
    </div>
    <div class="page__ft">
    </div>
</div>

<link rel="stylesheet" href="https://weui.io/style/weui.css">
    <script type="text/javascript" src="https://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>



 <script type="text/javascript" src="/home/static/js/zwajax.js"></script>

<script type="text/javascript">



Ajax.getJSON(PATH+"/wx/signatrue",{url:this.location},function(result){
   var data = result.data;
    initWx(data);


});
var debug =false;

function initWx(data){

    //说明文档https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141115
    //https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141115
    //步骤二：引入JS文件

    //步骤三：通过config接口注入权限验证配置
    //alert(data.signature);
    console.log(data);
    wx.config({

        debug: debug, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。

        appId: data.appId, // 必填，公众号的唯一标识

        timestamp: data.timestamp, // 必填，生成签名的时间戳

        nonceStr: data.noncestr, // 必填，生成签名的随机串

        signature:data.signature,// 必填，签名，见附录1

        jsApiList: ['onMenuShareTimeline', 'onMenuShareAppMessage',
              'onMenuShareQQ',
              'onMenuShareWeibo'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2

    });
    //步骤四：通过ready接口处理成功验证
var json = {
            title: '${artical.title}', // 分享标题
            desc: '${artical.remark}', // 分享描述
            link: 'http://www.dapark.top/home/artical/viewpage/${artical.id}', // 分享链接，该链接域名或路径必须与当前页面对应的公
            imgUrl: 'http://www.dapark.top/home/${artical.pic}', // 分享图标

 trigger: function (res) {
                // 不要尝试在trigger中使用ajax异步请求修改本次分享的内容，因为客户端分享操作是一个同步操作，这时候使用ajax的回包会还没有返回
  if(debug)              alert('用户点击分享到朋友圈');
            },
            success: function (res) {
          if(debug)      alert('已分享');
            },
            cancel: function (res) {
                if(debug)alert('已取消');
            },
            fail: function (res) {
if(debug)                alert(JSON.stringify(res));
            }
            };

    wx.ready(function(){
      wx.onMenuShareAppMessage(json);

        wx.onMenuShareTimeline({
            title: '${artical.title}', // 分享标题
            desc: '${artical.remark}', // 分享描述
            link: 'http://www.dapark.top/home/artical/viewpage/${artical.id}', // 分享链接，该链接域名或路径必须与当前页面对应的公众号JS安全域名一致
            imgUrl: 'http://www.dapark.top/home/${artical.pic}', // 分享图标
            success: function () {
              if(debug)alert("设置成功");
              // 设置成功
            }
            });

            if(debug)
             alert("微信调用成功");
        // config信息验证后会执行ready方法，所有接口调用都必须在config接口获得结果之后，config是一个客户端的异步操作，所以如果需要在页面加载时就调用相关接口，则须把相关接口放在ready函数中调用来确保正确执行。对于用户触发时才调用的接口，则可以直接调用，不需要放在ready函数中。

    });
    //步骤五：通过error接口处理失败验证

    wx.error(function(res){
        alert("微信调用失败");
        // config信息验证失败会执行error函数，如签名过期导致验证失败，具体错误信息可以打开config的debug模式查看，也可以在返回的res参数中查看，对于SPA可以在这里更新签名。

    });

}

</script>


</body></html>
