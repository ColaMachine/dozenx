var Setting1126 = function (entity, divId) {
var setting = null;

var _state = entity.state || {

};

function setStates(item) {
entity.setState(item);
setting.setState(item);
}

var SettingReact = React.createClass({displayName: "SettingReact",
getInitialState: function() {

return _state;
},
componentDidMount: function () {

},
handleSaveClick: function(){

},
render: function () {
return (
React.createElement("div", {className: "container"}, 
React.createElement("form", {className: "form-horizontal"}, 
React.createElement("div", {className: "form-group control-label"}, " 此组件无需进行设置")
)
)
);
}
});

function render() {
setting = ReactDOM.render(React.createElement(SettingReact, null), document.getElementById(divId));
return setting;
}

return {
setting: setting,
setStates: setStates,
render: render
}
};

