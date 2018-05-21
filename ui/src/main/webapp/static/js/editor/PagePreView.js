var PagePreView = React.createClass({displayName: "PagePreView",
	getInitialState: function() {
	    return {
            titleEdit:false,
            title:"页面1"
	    };
	  },
      showTitleEdit:function(){
//          React.findDOMNode(this.refs.title).hide();
//          React.findDOMNode(this.refs.titleInput).show();
           ReactDOM.findDOMNode(this.refs.titleInput).style.display="block";
          ReactDOM.findDOMNode(this.refs.title).style.display="none";
          //this.setState({titleEdit:true});
          ReactDOM.findDOMNode(this.refs.titleInput).focus();
      },
    handleTitleChange:function(event){
        this.setState({title: event.target.value});

    },
    hideTitleEdit:function(){
        ReactDOM.findDOMNode(this.refs.titleInput).style.display="none";
        ReactDOM.findDOMNode(this.refs.title).style.display="block";
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

                  React.createElement("div", {className: "page-thumb-block active"}, 
                      React.createElement("div", {className: "page-thumb"}, 
                          React.createElement("div", {className: "page-thumb-bg"}), 
                          React.createElement("div", {className: "page-thumb-elements"})
                      ), 
                      React.createElement("span", {className: "page-num", onClick: this.showTitleEdit}, 
                        page_title, page_title_block
                      ), 
                      React.createElement("div", {className: "icon-button icon-uninstall fa fa-trash"}), 
                      React.createElement("div", {className: "icon-button icon-copy fa fa-copy"})
                  )


        );
        }
    }
);

