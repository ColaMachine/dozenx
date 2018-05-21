var EditorCanvas = React.createClass({displayName: "EditorCanvas",
	getInitialState: function() {
	    return {
            titleEdit:false,
            title:"页面1"
	    };
	  },

	  render: function() {

		  return (
            React.createElement("div", {className: "canvas-area col-xs-11 col-sm-7"}, 
                React.createElement("div", {className: "mask-left"}), 
                React.createElement("div", {className: "mask-right"}), 
                React.createElement("div", {className: "canvas-viewport"}, 
                    React.createElement("div", {className: "canvas-window"}, 
                        React.createElement("div", {className: "mask-top"}, " "), 
                        React.createElement("div", {className: "mask-bottom"}), 
                        React.createElement("div", {className: "canvas-animation"}, 
                            React.createElement("div", {className: "bg-layer dropable selected"}), 
                            React.createElement("div", {className: "elements-layer"})
                        )

                    )


                )

            )

        );
        }
    }
);

