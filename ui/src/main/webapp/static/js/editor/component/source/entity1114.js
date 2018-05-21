var Entity1114 = function(state, divId) {
var _state = state || {
login_frame_show:"none",
fast_login_show:"none",
get_free_pkg_show:"none",
left_time_show:"none123",
};

var EntityReact = React.createClass({displayName: "EntityReact",
getInitialState: function() {
return _state;
},
render: function() {
return (
React.createElement("ul", {className: "app-li"}, 
React.createElement("li", {className: "app-li-it"}, 
React.createElement("div", {className: "app-li-it-pic"}, React.createElement("a", {href: "http://www.baidu.com"}, React.createElement("img", {src: "/static/phone/img/carousel/1.png"}))), 
React.createElement("div", {className: "app-li-it-content"}, 
React.createElement("div", {className: "app-li-it-title"}, React.createElement("a", {href: "http://www.baidu.com"}, React.createElement("h2", null, "标题"))), 
React.createElement("div", {className: "app-li-it-text"}, React.createElement("span", {className: "src-net"}, "淘宝|23:12"), React.createElement("span", {className: "comment-num"}, "评论:2"), React.createElement("span", {className: "goods-score"}, "评分:98%"))
)
), 
React.createElement("li", {className: "app-li-it"}, 
React.createElement("div", {className: "app-li-it-pic"}, React.createElement("a", {href: "http://www.baidu.com"}, React.createElement("img", {src: "/static/phone/img/carousel/1.png"}))), 
React.createElement("div", {className: "app-li-it-content"}, 
React.createElement("div", {className: "app-li-it-title"}, React.createElement("a", {href: "http://www.baidu.com"}, React.createElement("h2", null, "标题"))), 
React.createElement("div", {className: "app-li-it-text"}, React.createElement("span", {className: "src-net"}, "淘宝|23:12"), React.createElement("span", {className: "comment-num"}, "评论:2"), React.createElement("span", {className: "goods-score"}, "评分:98%"))
)
), 
React.createElement("li", {className: "app-li-it"}, 
React.createElement("div", {className: "app-li-it-pic"}, React.createElement("a", {href: "http://www.baidu.com"}, React.createElement("img", {src: "/static/phone/img/carousel/1.png"}))), 
React.createElement("div", {className: "app-li-it-content"}, 
React.createElement("div", {className: "app-li-it-title"}, React.createElement("a", {href: "http://www.baidu.com"}, React.createElement("h2", null, "标题"))), 
React.createElement("div", {className: "app-li-it-text"}, React.createElement("span", {className: "src-net"}, "淘宝|23:12"), React.createElement("span", {className: "comment-num"}, "评论:2"), React.createElement("span", {className: "goods-score"}, "评分:98%"))
)
), 
React.createElement("li", {className: "app-li-it"}, 
React.createElement("div", {className: "app-li-it-pic"}, React.createElement("a", {href: "http://www.baidu.com"}, React.createElement("img", {src: "/static/phone/img/carousel/1.png"}))), 
React.createElement("div", {className: "app-li-it-content"}, 
React.createElement("div", {className: "app-li-it-title"}, React.createElement("a", {href: "http://www.baidu.com"}, React.createElement("h2", null, "标题"))), 
React.createElement("div", {className: "app-li-it-text"}, React.createElement("span", {className: "src-net"}, "淘宝|23:12"), React.createElement("span", {className: "comment-num"}, "评论:2"), React.createElement("span", {className: "goods-score"}, "评分:98%"))
)
), 
React.createElement("li", {className: "app-li-it"}, 
React.createElement("div", {className: "app-li-it-pic"}, React.createElement("a", {href: "http://www.baidu.com"}, React.createElement("img", {src: "/static/phone/img/carousel/1.png"}))), 
React.createElement("div", {className: "app-li-it-content"}, 
React.createElement("div", {className: "app-li-it-title"}, React.createElement("a", {href: "http://www.baidu.com"}, React.createElement("h2", null, "标题"))), 
React.createElement("div", {className: "app-li-it-text"}, React.createElement("span", {className: "src-net"}, "淘宝|23:12"), React.createElement("span", {className: "comment-num"}, "评论:2"), React.createElement("span", {className: "goods-score"}, "评分:98%"))
)
)
)
);
},

componentDidMount:function(){

},
getInitialState:function(){
return _state
},
});
/**
* 制作页面时返回React对象
* @returns {*}
*/
var react =null;
function render() {
react=  ReactDOM.render(React.createElement(EntityReact, null), document.getElementById(divId));
return react;

}
function setState(state_){
for(var i in state_){
_state[i]=state_[i];
}
react.setState(_state);
}
return {
name:name,
render: render,
setState:setState,
state:_state
}
};



