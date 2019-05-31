<template>
<div>




     <zwRow>

        <zwCol class="pull-right" span=24>
                微信组件
        </zwCol>

    </zwRow>
</div>

</template>
<script>

import zwRow from '../../component/layout/zwRow.vue';
import zwCol from '../../component/layout/zwCol.vue';
import zwIcon from '../../component/icon/zwIcon.vue';


export default {
    components:{zwRow,zwCol,zwIcon
    },
    data () {

        return {

        ]
      }
    }
    ,
    mounted(){


    },
    computed: {

    },
    methods:{

    initWx:function(data){


    //说明文档https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141115
    //https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141115
    //步骤二：引入JS文件

    //步骤三：通过config接口注入权限验证配置
   //alert(data.signature);

    wx.config({

        debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。

        appId: data.appId, // 必填，公众号的唯一标识

        timestamp: data.timestamp, // 必填，生成签名的时间戳

        nonceStr: data.noncestr, // 必填，生成签名的随机串

        signature:data.signature,// 必填，签名，见附录1

        jsApiList: [ 'getLocation','scanQRCode'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2

    });
    //步骤四：通过ready接口处理成功验证

    wx.ready(function(){
        wxInit=true;
        console.log("进入而微信ready");
       // alert("进入而微信ready");
        // config信息验证后会执行ready方法，所有接口调用都必须在config接口获得结果之后，config是一个客户端的异步操作，所以如果需要在页面加载时就调用相关接口，则须把相关接口放在ready函数中调用来确保正确执行。对于用户触发时才调用的接口，则可以直接调用，不需要放在ready函数中。

          if(wx){
                     wx.scanQRCode({//扫描二维码
                        needResult: 1, // 默认为0，扫描结果由微信处理，1则直接返回扫描结果，
                        scanType: ["qrCode","barCode"], // 可以指定扫二维码还是一维码，默认二者都有
                        success: function (res) {
                            var result = res.resultStr; // 当needResult 为 1 时，扫码返回的结果
                           // alert("扫描结果"+result);
                            this.addWifi(result);
                         }.Apply(this)
                    });
                    }else{
                    alert("微信初始化失败!");
                    }

    }.Apply(this));
    //步骤五：通过error接口处理失败验证

    wx.error(function(res){
        alert("微信调用失败");
        // config信息验证失败会执行error函数，如签名过期导致验证失败，具体错误信息可以打开config的debug模式查看，也可以在返回的res参数中查看，对于SPA可以在这里更新签名。

    });


    },
    scanCode:function(){//alert("开始scanCode")


        if(!wxInit){//如果wx已经初始化过了 就不需要再次初始化了

        Ajax.getJSON(PATH+"/weixin/signatrue",{url:location.href.split('#')[0]},function(result){
                  var data = result.data;
                  this.initWx(data);


              }.Apply(this));
        }else{


                if(wx){
                     wx.scanQRCode({//扫描二维码
                        needResult: 1, // 默认为0，扫描结果由微信处理，1则直接返回扫描结果，
                        scanType: ["qrCode","barCode"], // 可以指定扫二维码还是一维码，默认二者都有
                        success: function (res) {

                            var result = res.resultStr; // 当needResult 为 1 时，扫码返回的结果
                           // alert("扫描结果"+result);
                            this.addWifi(result);
                         }.Apply(this)
                    });
                    }else{
                    alert("微信初始化失败!");
                    }
        }
    },

    }
}
</script>
<style scoped>



</style>

