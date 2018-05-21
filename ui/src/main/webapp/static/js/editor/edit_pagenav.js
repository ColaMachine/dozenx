var PageNav = React.createClass({displayName: "PageNav",
	getInitialState: function() {
	    return {
            arr:[{key:"1",name:"页面",content:React.createElement(EditTemplateWrap, null),active:true},{key:"2",name:"图层",content:"hello"}]
	    };
	  },
    selectTab:function(data){
      for(var i in this.state.arr){
          if(this.state.arr[i].name!= data){
              this.state.arr[i].active=false;
          }else{
              this.state.arr[i].active=true;
          }
      }
        this.setState(this.state);
    },
	  render: function() {
          var that =this;
		  return (
          React.createElement("div", {className: "page-navigator "}, 
              React.createElement("div", {className: "mask"}), 
              React.createElement("div", null, 
                  React.createElement("ul", {className: "nav nav-tabs"}, 
                    
                      this.state.arr.map(function(result,index){
                          var data = result.name;
                          return  React.createElement("li", {key: index, className:  result.active ? 'active' : ''}, 
                              React.createElement("a", {href: "#", onClick: that.selectTab}, result.name)
                          );
                      })
                    
                  ), 
                  React.createElement("div", {className: "tab-content"}, 
                    
                        this.state.arr.map(function(result,index){
                            return React.createElement("div", {key: index, className:  result.active ? 'tab-pane fade in  active ' : 'tab-pane fade '}, 
                              result.content
                            );
                        })
                    
                  )
              )
          )

        );
        }
    }
);

