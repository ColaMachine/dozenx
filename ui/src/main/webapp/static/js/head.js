
var headWidget={
    ids:{
        root:".page-hd",
        loginOut:"#loginOut",
        collapse:"#collapse"
    },
    doms:{

    },
    init:function(cfg){
        extend(this.ids,cfg);
        this.doms.root= $(this.ids.root);

        for(var i in this.ids){
            if(i=="root")continue;
            this.doms[i]=this.doms["root"].find(this.ids[i]);
            if(this.doms[i].length==0){
                console.log(this.ids[i] +" doesn't find ");
            }
        }

        this.addEventListener();

    },
    addEventListener:function(){
        //注册按钮
        var that=this;
        this.doms.loginOut.click(this.loginOut);
        this.doms.collapse.click(this.collapse);

    },

    loginOut:function(){
        window.location=PATH+"/logout.htm";
    },
    collapse:function(){
        $('#page').toggleClass('collapse1');
    }

}
headWidget.init();