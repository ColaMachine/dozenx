
var Entity1120= function(state, divId) {
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
        React.createElement("div", null, 
   React.createElement("img", {className: "app-top-img", src: "/"+this.state.pic}), 
   React.createElement("span", null, "来源:", this.state.src), React.createElement("span", null), 
   React.createElement("h1", null, this.state.title), 
   React.createElement("h1", null, this.state.subtitle), 
React.createElement("p", {dangerouslySetInnerHTML: {__html: this.state.content}})
  
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
