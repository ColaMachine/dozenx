var Entity1113 = function(state, divId) {
    var _state = state || {
        login_frame_show:"none",
        fast_login_show:"none",
        get_free_pkg_show:"none",
        left_time_show:"none",
    };

    var EntityReact = React.createClass({displayName: "EntityReact",
        getInitialState: function() {
            return _state;
        },
         render: function() {
            return (
                 React.createElement("div", {className: "carousel clr-fix"}, 
                            React.createElement("ul", {className: "carousel-ul", id: "carousel-ul"}, 
                            React.createElement("li", {className: "carousel-ul-li", id: "pic_1"}, 
                            React.createElement("a", {href: "javascript:void(0)"}, React.createElement("img", {className: "carousel-img row-10", src: "https://img6.bdstatic.com/img/image/smallpic/weiju1227.jpg", alt: "1", id: "img"}))
                            ), 
                            React.createElement("li", {className: "carousel-ul-li", id: "pic_2"}, 
                            React.createElement("a", {href: "javascript:void(0)"}, React.createElement("img", {className: "carousel-img row-10", src: "http://img6.bdstatic.com/img/image/smallpic/chongwu1227.jpg", alt: "2"}))
                            ), 
                            React.createElement("li", {className: "carousel-ul-li", id: "pic_3"}, 
                            React.createElement("a", {href: "javascript:void(0)"}, React.createElement("img", {className: "carousel-img row-10", src: "http://img6.bdstatic.com/img/image/smallpic/mingxing1222.jpg", alt: "3"}))
                            ), 
                            React.createElement("li", {className: "carousel-ul-li", id: "pic_4"}, 
                            React.createElement("a", {href: "javascript:void(0)"}, React.createElement("img", {className: "carousel-img row-10", src: "http://img6.bdstatic.com/img/image/smallpic/dongman1227.jpg", alt: "4"}))
                            )
                            )
                        )
            );
        },

        componentDidMount:function(){
            $(".carousel").show().yxMobileSlider({
                height : 100 + 'px'
            });
        },
        getInitialState:function(){
            return _state
        },
});
// OtherReact 在这里定义
/**
* 制作页面时返回React对象
* @returns {*}
*/
    function render() {
        return ReactDOM.render(React.createElement(EntityReact, null), document.getElementById(divId));
    }

    return {
        name:name,
        render: render
    }
};



