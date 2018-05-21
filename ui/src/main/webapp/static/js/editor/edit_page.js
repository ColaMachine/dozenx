var EditPage = React.createClass({displayName: "EditPage",
	getInitialState: function() {
	    return {
            titleEdit:false,
            title:"页面1"
	    };
	  },
      showTitleEdit:function(){
        //React.findDOMNode(this.refs.title).hide();
        //React.findDOMNode(this.refs.titleInput).show();
        React.findDOMNode(this.refs.titleInput).style.display="block";
        React.findDOMNode(this.refs.title).style.display="none";
        //this.setState({titleEdit:true});
        React.findDOMNode(this.refs.titleInput).focus();
      },
    handleTitleChange:function(event){
        this.setState({title: event.target.value});

    },
    hideTitleEdit:function(){
        React.findDOMNode(this.refs.titleInput).style.display="none";
        React.findDOMNode(this.refs.title).style.display="block";
       // this.setState({titleEdit:false});
    },
	  render: function() {
           var title =this.state.title;
          var page_title ;
          var page_title_block;
          var hideStyle ={display:'none'};
          if(this.state.titleEdit){//显示input
              page_title= React.createElement("span", {style: hideStyle, ref: "title"}, this.state.title);
              page_title_block=React.createElement("input", {type: "text", value: this.state.title, ref: "titleInput", onBlur: this.hideTitleEdit, onChange: this.handleTitleChange});
          }else{
              page_title= React.createElement("span", {ref: "title"}, this.state.title);
              page_title_block=React.createElement("input", {style: hideStyle, type: "text", value: this.state.title, ref: "titleInput", onBlur: this.hideTitleEdit, onChange: this.handleTitleChange});
          }
		  return (
            React.createElement("div", null, 
              React.createElement("div", {className: "page-reorder"}, 
                  React.createElement("ul", {className: "sortable"}, 
                      React.createElement("li", {className: "page-menu pageActive"}, 
                          React.createElement(PagePreView, null)
                      )
                  )
              ), 
              React.createElement("div", {className: "add-blank-page"}, "+")
         )

        );
        }
    }
);

