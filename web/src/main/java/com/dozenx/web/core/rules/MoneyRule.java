package com.dozenx.web.core.rules;


import com.dozenx.util.StringUtil;

public class MoneyRule extends Rule {
	
	public MoneyRule() {
		
	}
	
	@Override
	public boolean valid() throws Exception {
		if(this.getValue() == null || this.getValue().equals("")){
			return true;
		}else{
			if (StringUtil.checkNumeric(this.getValue().toString())) {
				return true;
			}
			else {
				this.setMessage("请输入有效金额");
				return false;
			}
		}
	}

}
