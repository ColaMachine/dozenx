var Entity1118 = function(state, divId) {
    var _state = state || {

    };

    var EntityReact = React.createClass({displayName: "EntityReact",
        getInitialState: function() {


            return  MapUtil.combine(_state,{data:[]}) ;
        },

         render: function() {
            return (
                React.createElement("ul", {className: "app-li"}, 

                

                          this.state.data.map(function(result,index){
                              return  React.createElement("li", {key: result.id, className: "app-li-it"}, 
                        React.createElement("div", {className: "app-li-it-pic"}, React.createElement("a", {href: "/static/html/editor/index.html?id=3&goodsid="+result.id}, React.createElement("img", {src: "/"+result.pic}))), 
                        React.createElement("div", {className: "app-li-it-content"}, 
                            React.createElement("div", {className: "app-li-it-title"}, React.createElement("a", {href: "/static/html/editor/index.html?id=3&goodsid="+result.id}, React.createElement("h2", null, result.title), React.createElement("h3", null, result.subtitle))), 
                            React.createElement("div", {className: "app-li-it-text"}, React.createElement("span", {className: "src-net"}, result.src, "|23:12"), React.createElement("span", {className: "comment-num"}, "评论:2"), React.createElement("span", {className: "goods-score"}, "评分:98%"))
                        )
                    );
                          }.bind(this))
                      


                )
            );
        },

        componentDidMount:function(){
            Ajax.getJSON("/goods/list.json?curpage=1&pagesize=10",null,function(result){
                if(result.r==AJAX_SUCC){
                    this.setState({data:result.data});
                }
            }.Apply(this));

        },

});
// OtherReact 在这里定义
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
           console.log(_state);
           react.setState(_state);
      }
       return {
           name:name,
           render: render,
           setState:setState,
           state:_state
       }
};
