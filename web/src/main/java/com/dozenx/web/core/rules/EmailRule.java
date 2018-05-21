package com.dozenx.web.core.rules;


import com.dozenx.util.StringUtil;

public class EmailRule extends Rule {
	
	public EmailRule() {
		
	}
	
	@Override
	public boolean valid() throws Exception {
		if(this.getValue() == null || this.getValue().equals("")){
			return true;
		}else{
			if (StringUtil.isEmail(this.getValue().toString())) {
				return true;
			}
			else {
				this.setMessage("请输入正确格式的邮箱");
				return false;
			}
		}
	}

}
