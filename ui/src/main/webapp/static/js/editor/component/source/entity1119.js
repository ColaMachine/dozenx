var Entity1116= function(state, divId) {
    var name="header";

    var _state = state || {
    };
var EntityReact = React.createClass({displayName: "EntityReact",
    getInitialState: function() {
        return _state;
    },

    render: function() {
    return (
        React.createElement("div", {className: "slider"}, 
    React.createElement("ul", null, 
        React.createElement("li", null, React.createElement("a", {href: "http://www.internetke.com", target: "_blank"}, React.createElement("img", {src: "http://www.internetke.com/jsEffects/2014052304/images/1.jpg", alt: "科e互联网站建设团队"}))), 
        React.createElement("li", null, React.createElement("a", {href: "http://www.internetke.com", target: "_blank"}, React.createElement("img", {src: "http://www.internetke.com/jsEffects/2014052304/images/2.jpg", alt: "网页特效集锦"}))), 
        React.createElement("li", null, React.createElement("a", {href: "http://www.internetke.com", target: "_blank"}, React.createElement("img", {src: "http://www.internetke.com/jsEffects/2014052304/images/3.jpg", alt: "JS代码素材库"}))), 
        React.createElement("li", null, React.createElement("a", {href: "http://www.internetke.com", target: "_blank"}, React.createElement("img", {src: "http://www.internetke.com/jsEffects/2014052304/images/4.jpg", alt: "用心建站用心服务"}))), 
        React.createElement("li", null, React.createElement("a", {href: "http://www.internetke.com", target: "_blank"}, React.createElement("img", {src: "http://www.internetke.com/jsEffects/2014052304/images/5.jpg", alt: "学会用才能学会写"})))
    )
)
    );
    },

    componentDidMount:function(){
          // $(".slider").yxMobileSlider({width:640,height:320,during:3000})
             $('.slider').yxMobileSlider({width:640,height:320,during:3000})
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

