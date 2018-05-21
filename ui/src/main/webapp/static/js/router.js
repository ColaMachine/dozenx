function Router(){
    this.routes = {};
    this.curUrl = '';
    //添加router的方法
    this.addRoute = function(path, callback){
        this.routes[path] = callback || function(){
            zMenu.loadPage(path)
        };
    };
    //首页刷新的功能
    this.refresh = function(){
        this.curUrl = location.hash.slice(1) || '/';
        if( this.routes[this.curUrl]){
            this.routes[this.curUrl]();
        }else{
        if(this.curUrl=='/'){//住过主页面不做跳转吗 这里需要改一下 应该是调到主页面 但是内容可以是空白的
            return;
        }
            zMenu.loadPage(this.curUrl);
        }
    };

    this.init = function(){
        window.addEventListener('load', this.refresh.bind(this), false);
        window.addEventListener('hashchange', this.refresh.bind(this), false);

    }
}
window.route = new Router();
window.route.init();//路由初始化