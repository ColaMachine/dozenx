

var NavBar = React.createClass({
    displayName: "NavBar",

    render: function () {
        return React.createElement(
            "div",
            { id: "page-wrapper", className: "gray-bg" },
            React.createElement(
                "div",
                { className: "row border-bottom" },
                React.createElement(
                    "nav",
                    { className: "navbar navbar-static-top white-bg", role: "navigation" },
                    React.createElement(
                        "div",
                        { className: "navbar-header" },
                        React.createElement(
                            "a",
                            { className: "navbar-minimalize minimalize-styl-2 btn btn-primary ", href: "#" },
                            React.createElement("i", { className: "fa fa-bars" }),
                            " "
                        ),
                        React.createElement(
                            "form",
                            { role: "search", className: "navbar-form-custom", action: "search_results.html" },
                            React.createElement(
                                "div",
                                { className: "form-group" },
                                React.createElement("input", { type: "text", placeholder: "Search for something...", className: "form-control", name: "top-search", id: "top-search" })
                            )
                        )
                    ),
                    React.createElement(
                        "ul",
                        { className: "nav navbar-top-links navbar-right" },
                        React.createElement(
                            "li",
                            null,
                            React.createElement(
                                "span",
                                { className: "m-r-sm text-muted welcome-message" },
                                "Welcome to INSPINIA+ Admin Theme."
                            )
                        ),
                        React.createElement(
                            "li",
                            { className: "dropdown" },
                            React.createElement(
                                "a",
                                { className: "dropdown-toggle count-info", "data-toggle": "dropdown", href: "#" },
                                React.createElement("i", { className: "fa fa-envelope" }),
                                "  ",
                                React.createElement(
                                    "span",
                                    { className: "label label-warning" },
                                    "16"
                                )
                            ),
                            React.createElement(
                                "ul",
                                { className: "dropdown-menu dropdown-messages" },
                                React.createElement(
                                    "li",
                                    null,
                                    React.createElement(
                                        "div",
                                        { className: "dropdown-messages-box" },
                                        React.createElement(
                                            "a",
                                            { href: "profile.html", className: "pull-left" },
                                            React.createElement("img", { alt: "image", className: "img-circle", src: "img/a7.jpg" })
                                        ),
                                        React.createElement(
                                            "div",
                                            null,
                                            React.createElement(
                                                "small",
                                                { className: "pull-right" },
                                                "46h ago"
                                            ),
                                            React.createElement(
                                                "strong",
                                                null,
                                                "Mike Loreipsum"
                                            ),
                                            " started following ",
                                            React.createElement(
                                                "strong",
                                                null,
                                                "Monica Smith"
                                            ),
                                            ". ",
                                            React.createElement("br", null),
                                            React.createElement(
                                                "small",
                                                { className: "text-muted" },
                                                "3 days ago at 7:58 pm - 10.06.2014"
                                            )
                                        )
                                    )
                                ),
                                React.createElement("li", { className: "divider" }),
                                React.createElement(
                                    "li",
                                    null,
                                    React.createElement(
                                        "div",
                                        { className: "dropdown-messages-box" },
                                        React.createElement(
                                            "a",
                                            { href: "profile.html", className: "pull-left" },
                                            React.createElement("img", { alt: "image", className: "img-circle", src: "img/a4.jpg" })
                                        ),
                                        React.createElement(
                                            "div",
                                            null,
                                            React.createElement(
                                                "small",
                                                { className: "pull-right text-navy" },
                                                "5h ago"
                                            ),
                                            React.createElement(
                                                "strong",
                                                null,
                                                "Chris Johnatan Overtunk"
                                            ),
                                            " started following ",
                                            React.createElement(
                                                "strong",
                                                null,
                                                "Monica Smith"
                                            ),
                                            ". ",
                                            React.createElement("br", null),
                                            React.createElement(
                                                "small",
                                                { className: "text-muted" },
                                                "Yesterday 1:21 pm - 11.06.2014"
                                            )
                                        )
                                    )
                                ),
                                React.createElement("li", { className: "divider" }),
                                React.createElement(
                                    "li",
                                    null,
                                    React.createElement(
                                        "div",
                                        { className: "dropdown-messages-box" },
                                        React.createElement(
                                            "a",
                                            { href: "profile.html", className: "pull-left" },
                                            React.createElement("img", { alt: "image", className: "img-circle", src: "img/profile.jpg" })
                                        ),
                                        React.createElement(
                                            "div",
                                            null,
                                            React.createElement(
                                                "small",
                                                { className: "pull-right" },
                                                "23h ago"
                                            ),
                                            React.createElement(
                                                "strong",
                                                null,
                                                "Monica Smith"
                                            ),
                                            " love ",
                                            React.createElement(
                                                "strong",
                                                null,
                                                "Kim Smith"
                                            ),
                                            ". ",
                                            React.createElement("br", null),
                                            React.createElement(
                                                "small",
                                                { className: "text-muted" },
                                                "2 days ago at 2:30 am - 11.06.2014"
                                            )
                                        )
                                    )
                                ),
                                React.createElement("li", { className: "divider" }),
                                React.createElement(
                                    "li",
                                    null,
                                    React.createElement(
                                        "div",
                                        { className: "text-center link-block" },
                                        React.createElement(
                                            "a",
                                            { href: "mailbox.html" },
                                            React.createElement("i", { className: "fa fa-envelope" }),
                                            " ",
                                            React.createElement(
                                                "strong",
                                                null,
                                                "Read All Messages"
                                            )
                                        )
                                    )
                                )
                            )
                        ),
                        React.createElement(
                            "li",
                            { className: "dropdown" },
                            React.createElement(
                                "a",
                                { className: "dropdown-toggle count-info", "data-toggle": "dropdown", href: "#" },
                                React.createElement("i", { className: "fa fa-bell" }),
                                "  ",
                                React.createElement(
                                    "span",
                                    { className: "label label-primary" },
                                    "8"
                                )
                            ),
                            React.createElement(
                                "ul",
                                { className: "dropdown-menu dropdown-alerts" },
                                React.createElement(
                                    "li",
                                    null,
                                    React.createElement(
                                        "a",
                                        { href: "mailbox.html" },
                                        React.createElement(
                                            "div",
                                            null,
                                            React.createElement("i", { className: "fa fa-envelope fa-fw" }),
                                            " You have 16 messages",
                                            React.createElement(
                                                "span",
                                                { className: "pull-right text-muted small" },
                                                "4 minutes ago"
                                            )
                                        )
                                    )
                                ),
                                React.createElement("li", { className: "divider" }),
                                React.createElement(
                                    "li",
                                    null,
                                    React.createElement(
                                        "a",
                                        { href: "profile.html" },
                                        React.createElement(
                                            "div",
                                            null,
                                            React.createElement("i", { className: "fa fa-twitter fa-fw" }),
                                            " 3 New Followers",
                                            React.createElement(
                                                "span",
                                                { className: "pull-right text-muted small" },
                                                "12 minutes ago"
                                            )
                                        )
                                    )
                                ),
                                React.createElement("li", { className: "divider" }),
                                React.createElement(
                                    "li",
                                    null,
                                    React.createElement(
                                        "a",
                                        { href: "grid_options.html" },
                                        React.createElement(
                                            "div",
                                            null,
                                            React.createElement("i", { className: "fa fa-upload fa-fw" }),
                                            " Server Rebooted",
                                            React.createElement(
                                                "span",
                                                { className: "pull-right text-muted small" },
                                                "4 minutes ago"
                                            )
                                        )
                                    )
                                ),
                                React.createElement("li", { className: "divider" }),
                                React.createElement(
                                    "li",
                                    null,
                                    React.createElement(
                                        "div",
                                        { className: "text-center link-block" },
                                        React.createElement(
                                            "a",
                                            { href: "notifications.html" },
                                            React.createElement(
                                                "strong",
                                                null,
                                                "See All Alerts"
                                            ),
                                            React.createElement("i", { className: "fa fa-angle-right" })
                                        )
                                    )
                                )
                            )
                        ),
                        React.createElement(
                            "li",
                            null,
                            React.createElement(
                                "a",
                                { href: "login.html" },
                                React.createElement("i", { className: "fa fa-sign-out" }),
                                " Log out"
                            )
                        ),
                        React.createElement(
                            "li",
                            null,
                            React.createElement(
                                "a",
                                { className: "right-sidebar-toggle" },
                                React.createElement("i", { className: "fa fa-tasks" })
                            )
                        )
                    )
                )
            )
        );
    }
});
React.render(React.createElement(NavBar, null), document.getElementById('page-hd'));