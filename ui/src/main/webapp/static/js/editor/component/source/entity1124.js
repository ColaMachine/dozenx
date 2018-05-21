var Entity1124 = function (state, divId) {

var app_url = '/mersrv/ms/merchant/app/list';

var _state = state || {
app_list:[],
};

var EntityReact = React.createClass({displayName: "EntityReact",
getInitialState: function () {
return _state;
},
componentDidMount: function(){


var _this = this;
$.ajax({
type:'GET',
url:app_url,
data:{merchantId:""},
success: function (data) {
console.log(data);
if(data.code == 0) {
_state.app_list = data.data.appList;
_this.setAuthState();
}
},
error: function () {

}
});
},
setAuthState: function () {
this.setState(_state);
console.log(_state);
},
openDetail: function (item) {
if(item.configUrl) {
window.location.href = item.configUrl;
}

},
render: function () {

var _this = this;
if(!_state.app_list){
_state.app_list=[];
}


var applist = _state.app_list.map(function (item, index) {
return React.createElement("div", {className: "module", key: index, onClick: _this.openDetail.bind(_this, item)}, 
React.createElement("div", {className: "image"}, 
React.createElement("img", {src: item.appIcon, key: index, alt: index})
), 
React.createElement("div", {className: "title"}, item.appName)
)
});


return (
React.createElement("div", {className: "Entity1124"}, 
React.createElement("div", {className: "container"}, 
applist
)
)
);
}
});





/**
* 制作页面时返回React对象
* @returns {*}
*/
function render() {
return ReactDOM.render(React.createElement(EntityReact, null), document.getElementById(divId));
}

return {
render: render
}
};
