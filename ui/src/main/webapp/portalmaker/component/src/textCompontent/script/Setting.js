
var _Setting_ = function (entity, divId) {
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

        render: function() {
            return (
                <div className="container _Setting_">

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
        setting: setting,

        setStates: setStates,

        render: render
    }
};

