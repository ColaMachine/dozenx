package com.dozenx.web.core.rules;


public class BooleanValue extends Rule {
	
	@Override
	public boolean valid() throws Exception {
        if (this.getValue() == null) {
            return true;
        }

		if (this.getValue() != null && 
				(this.getValue().toString().equalsIgnoreCase(Boolean.TRUE.toString())
						|| this.getValue().toString().equalsIgnoreCase(Boolean.FALSE.toString()))){
			return true;
		}
		else {
			this.setMessage("请选择");
			return false;			
		}
	
	}
}
