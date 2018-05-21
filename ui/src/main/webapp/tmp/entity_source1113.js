var Entity1113 = function(state, divId) {
    var name="header";

    var _state = state || {
    };
var EntityReact = React.createClass({
    getInitialState: function() {
        return _state;
    },

    render: function() {
    return (
        <div className="carousel clr-fix">
            <ul className="carousel-ul" id="carousel-ul">
            <li className="carousel-ul-li" id="pic_1">
            <a href="javascript:void(0)"><img className="carousel-img row-10" src="https://img6.bdstatic.com/img/image/smallpic/weiju1227.jpg" alt="1" id="img"/></a>
            </li>
            <li className="carousel-ul-li" id="pic_2">
            <a href="javascript:void(0)"><img className="carousel-img row-10" src="http://img6.bdstatic.com/img/image/smallpic/chongwu1227.jpg" alt="2"/></a>
            </li>
            <li className="carousel-ul-li" id="pic_3">
            <a href="javascript:void(0)"><img className="carousel-img row-10" src="http://img6.bdstatic.com/img/image/smallpic/mingxing1222.jpg" alt="3"/></a>
            </li>
            <li className="carousel-ul-li" id="pic_4">
            <a href="javascript:void(0)"><img className="carousel-img row-10" src="http://img6.bdstatic.com/img/image/smallpic/dongman1227.jpg" alt="4"/></a>
            </li>
            </ul>
        </div>
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
    /**
     * 制作页面时返回React对象
     * @returns {*}
     */
    function render() {
        return ReactDOM.render(<EntityReact />, document.getElementById(divId));
    }

    return {
        name:name,
        render: render
    }
};
