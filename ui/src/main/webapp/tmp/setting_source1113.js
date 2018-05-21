
var Setting1113 = function (entity, divId) {
    var setting = null;
    var _state = entity.state || {

        };

    function setStates(state) {
        entity.setState(state);
        setting.setState(state);
    }

    var SettingReact = React.createClass({
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
                <div className="container Setting123">
                    <label>标题</label><input type="text" id="msp_header_title" name="msp_header_title" />

                    <div><button onClick={this.save}>保存</button></div>
                </div>

            );
        }
    });

    /**
     * 制作页面时返回React对象
     * @returns {*}
     */
    function render() {
        setting = ReactDOM.render(<SettingReact />, document.getElementById(divId));
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

