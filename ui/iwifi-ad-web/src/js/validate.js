export function validateMultipleRoles(rule, value, callback, source) {
  if (!value || !value.length) {
    callback(new Error('请选择用户角色'));
  } else {
    callback();
  }
};

export function validateFullLocation(rule, value, callback, source) {
  if (!value || !value.length) {
    callback(new Error('请选择地区'));
  } else if (!value[0]) {
    callback(new Error('请选择省'));
  } else if (!value[1]) {
    callback(new Error('请选择市'));
  } else if (!value[2]) {
    callback(new Error('请选择区/县'));
  } else {
    callback();
  }
};

export function validatePhone(rule, value, callback, source) {
  if (!/^1\d{10}$/.test(value)) {
    callback(new Error('请输入正确的手机号'));
  } else {
    callback();
  }
}

export function validateId(rule, value, callback) {
  if (!/^\w{1,4}$/.test(value)) {
    callback(new Error('请输入1~4位数字或者字母'));
  } else {
    callback();
  }
};

export function validateName(rule, value, callback) {
  if (!/^[0-9a-zA-Z\u4e00-\u9fa5]/.test(value)) {
    callback(new Error('请输入英文中文或者数字'));
  } else {
    callback();
  }
};

export function validateChnEnNumber(rule, value, callback, source) {
  if (!/^[0-9a-zA-Z\u4e00-\u9fa5]*$/.test(value)) {
    callback(new Error('请输入中英文或数字'));
  } else {
    callback();
  }
}

export function validateChn(rule, value, callback, source) {
  if (!/^[\u4e00-\u9fa5]*$/.test(value)) {
    callback(new Error('请输入中文'));
  } else {
    callback();
  }
}
export function  validateTime(rule, value, callback, source) {
  if (value === '') {
    callback(new Error('请选择日期和时间'));
  } else {
    callback();
  }
}

export function validateEnNumber(rule, value, callback, source) {
  if (!/^[0-9a-zA-Z]*$/.test(value)) {
    callback(new Error('请输入英文或数字'));
  } else {
    callback();
  }
}
export function validateCnzzUrl(rule, value, callback, source){
  if(source.auditStatus == 9 && value =="" ){
    callback(new Error('请填写cnzz检测地址'));
  }else{
    callback();
  }
}
export function validatePassword(rule, value, callback, source, options) {
  if (value === '') {
    callback(new Error('请输入密码'));
  } else if (!/(?!^(\d+|[A-Za-z]+|[~!@$%^&*()+]+)$)^([A-Za-z0-9]+|[~!@$%^&*()+]){6,20}$/.test(value)) {
    callback(new Error('密码格式为英文、数字、字符(特殊字符除外)的至少包含两种组成的6-20位字符'));
  } else {
    callback();
  }
}
export function validateMinute(rule, value, callback, source) {
 if (value&&(!/^[0-9]*$/.test(value)||value>360)) {
   callback(new Error('请输入0~360间的数字'));
 }else {
   callback();
 }
}

export function validateOrgName(rule, value, callback, source) {
  if (!value) {
    callback(new Error('必填'));
  }else if (!/^[0-9a-zA-Z\u4e00-\u9fa5]*$/.test(value)) {
   callback(new Error('请输入中英文或数字'));
 } else {
   callback();
 }
}
// 再次输入密码校验
export function validatePassCheck(rule, value, callback, source, options){
    if (value === '') {
      callback(new Error('请再次输入密码'));
    } else if (!/(?!^(\d+|[A-Za-z]+|[~!@$%^&*()+]+)$)^([A-Za-z0-9]+|[~!@$%^&*()+]){6,20}$/.test(value)) {
      callback(new Error('密码格式为英文、数字、字符(特殊字符除外)的至少包含两种组成的6-20位字符'));
    } else if (value !== source.newPassword) {
      callback(new Error('两次输入密码不一致!'));
    } else {
      callback();
    }
}
