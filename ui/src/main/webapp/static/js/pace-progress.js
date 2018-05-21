var NavBar = React.createClass({
    displayName: "NavBar",

    render: function () {
        return React.createElement(
            "div",
            { className: "pace  pace-inactive" },
            React.createElement(
                "div",
                { className: "pace-progress", "data-progress-text": "100%", "data-progress": "99", style: "transform: translate3d(100%, 0px, 0px);" },
                React.createElement("div", { className: "pace-progress-inner" })
            ),
            React.createElement("div", { className: "pace-activity" })
        );
    }
});