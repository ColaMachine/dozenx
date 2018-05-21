export function button() {

Vue.component('button', {
    template: '<button type="button" class="{{ getClassName() }}">（小按钮）Small button</button>',
      computed: {
                getClassName: function () {
                	var className ="btn ";
                  if(type=='primary'){
            	  	btn+="btn-primary ";
                  }else if(type == 'undefined'){
              	  	btn+="btn-default ";
                  }else if(type == 'dashed'){
            	  	btn+="btn-dashed ";
                  }

                  return className;
                }
              },
     props: ['type'],
})
}