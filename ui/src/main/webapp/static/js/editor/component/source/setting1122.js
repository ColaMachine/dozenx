var Setting1122 = function (entity, divId) {
    var setting = null;
    var _state = entity.state || {

        };

    function setStates(state) {
        entity.setState(state);
        setting.setState(state);
    }

    var SettingReact = React.createClass({displayName: "SettingReact",
        getInitialState: function() {

            return _state
        },

        componentDidMount: function () {

        },

        eventSave: function () {

        },
        save:function(){
            var title=$("#msp_header_title").val();
            console.log(title);
            setStates({title:title});

        },
        render: function() {
            return (
                React.createElement("div", {className: "container Setting123"}, 
                    React.createElement("label", null, "标题"), React.createElement("input", {type: "text", id: "msp_header_title", name: "msp_header_title"}), 

                    React.createElement("div", null, React.createElement("button", {onClick: this.save}, "保存"))
                )

            );
        }
    });

    /**
     * 制作页面时返回React对象
     * @returns {*}
     */
    function render() {
        setting = ReactDOM.render(React.createElement(SettingReact, null), document.getElementById(divId));
        return setting;
    }

    return {
        name:name,
        setting: setting,

        setStates: setStates,

        render: render

    }
};
  //ConfigStatck.push("header","Setting123");

