var _Entity_ = function(state, divId) {
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
                React.createElement("table", null, 
                    React.createElement("td", {className: "border-blue minH21  dropable"}, "shangge1"), 
                    React.createElement("td", {className: "border-blue  minH21 dropable"}, "shangge2"), 
                    React.createElement("td", {className: "border-blue minH21  dropable"}, "shangge3"), 
                    React.createElement("td", {className: "border-blue  minH21 dropable"}, "shangge4")
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



