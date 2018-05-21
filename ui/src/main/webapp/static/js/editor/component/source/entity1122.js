
var Entity1122= function(state, divId) {
    var name="header";

    var _state = state || {
    };
var EntityReact = React.createClass({displayName: "EntityReact",
    getInitialState: function() {
        return MapUtil.copy(_state,
        {pic:"",
        content:"",
        title:"",
        subtitle:"",
        src:""
        });
    },

    render: function() {
    return (
        React.createElement("div", {className: "app_bt_fl_nav_bar table"}, 
            React.createElement("div", {className: "select cell col-20"}, React.createElement("i", {className: "fa fa-home"}), "首页"), 
            React.createElement("div", {className: "cell col-20"}, React.createElement("i", {className: "fa fa-female"}), "服饰"), 
            React.createElement("div", {className: "cell col-20"}, React.createElement("i", {className: "fa fa-tv"}), "电器"), 
            React.createElement("div", {className: "cell col-20"}, React.createElement("i", {className: "fa fa-star-o"}), "最爱"), 
            React.createElement("div", {className: "cell col-20"}, React.createElement("i", {className: "fa fa-user"}), "我")
        )
    );
    },

    componentDidMount:function(){
    var that =this;
    var id = getQueryString("goodsid");
       Ajax.getJSON("/goods/view.json?id="+id,null,function(result){
        if(result.r==AJAX_SUCC){
             that.setState(result.data.bean);
        }
       });
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
