
var Entity123 = function(state, divId) {
        var name="header";

                var _state = state || {
                        login_frame_show:"none",
                        fast_login_show:"none",
                        get_free_pkg_show:"none",
                        left_time_show:"none",

                    };
                var HeanderReact= React.createClass({displayName: "HeanderReact",
                render:function(){
                   return ( React.createElement("header", {className: "indexWrap header back-blue"}, "         ", React.createElement("nav", null, 
                      React.createElement("a", {id: "login", style: {cursor:"pointer"}}, 
                   React.createElement("div", {id: "loginBtn", className: "header-left center-text row-3 left small-text"}, 
                                 React.createElement("img", {className: "header-left-img", src: "http://msp-img.51awifi.com/V1/img/header/wifi.png"}), 
                                           React.createElement("span", {className: "header-left-span right-text", id: "linkMsg"}, "请连网")
                                              ), "             "), 
                                                React.createElement("div", {className: "center-text row-4 left normal-text", style: {whiteSpace:"nowrap",textOverflow:"ellipsis",overflow: "hidden"}, id: "merchantname"}, "Yxy"), 
                                                       React.createElement("a", {href: "#/userCenter", id: "userCenter"}, "                 ", React.createElement("div", {className: "header-right center-text row-3 left small-text"}, 

                                                            React.createElement("img", {className: "header-right-img right", src: "http://msp-img.51awifi.com/V1/img/header/information.png"})
                                                                )
                                                                   )
                                                                     )
                                                                     ))
                }
                });
                 var LogoReact= React.createClass({displayName: "LogoReact",
                                render:function(){
                                   return (
                                    React.createElement("header", {className: "loginWrap", style: {display:"none"}}, 
                                          React.createElement("a", {href: "#/"}, 
                                              React.createElement("div", {className: "loginlogo row-10 back-blue center-text"}, 
                                                  React.createElement("img", {class: "row-5-2 loginlogo-icon", src: "http://msp-img.51awifi.com/V1/img/header/wifiLogo.png", alt: "logo"}), 
                                                  React.createElement("span", {className: "logoWord"}, "爱WiFi欢迎您")
                                              )
                                          )
                                      )

                                   );
                                }
                                });



                var EntityReact = React.createClass({displayName: "EntityReact",

                    getInitialState: function() {
                        return _state;
                    },

                 render: function() {

            return (
React.createElement("div", {style: {position:"relative"}}, 
            React.createElement(HeanderReact, null)
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

  //EntityStatck.push("header","component123");

