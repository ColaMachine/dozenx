var Entity123 = function(state, divId) {
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
                React.createElement("div", {style: {position:"relative"}}, 
                React.createElement("header", {className: "indexWrap  clearfix phone-header back-blue"}, 
                    React.createElement("nav", null, 
                        React.createElement("a", {id: "login", style: {cursor:"pointer"}}, 
                            React.createElement("div", {id: "loginBtn", className: "header-left center-text row-3 left small-text"}, 
                                React.createElement("img", {className: "header-left-img", src: "http://msp-img.51awifi.com/V1/img/header/wifi.png"}), 
                                React.createElement("span", {className: "header-left-span right-text", id: "linkMsg"})
                            )
                        ), 
                        React.createElement("div", {className: "center-text row-4 left normal-text", style: {whiteSpace:"nowrap",textOverflow:"ellipsis",overflow: "hidden"}, id: "merchantname"}, "Yxy"), 
                        React.createElement("a", {href: "#/userCenter", id: "userCenter"}, 
                            React.createElement("div", {className: "header-right center-text row-3 left small-text"}, 
                            React.createElement("img", {className: "header-right-img right", src: "http://msp-img.51awifi.com/V1/img/header/information.png"})
                            )
                        )
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



