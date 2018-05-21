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
export function validateId(rule,value,callback){
  if(!/^\w{4}$/.test(value)){
       callback(new Error('请输入4位数字或者字母'));
  }else{
    callback();
  }
};
export function validateName(rule,value,callback){
  if(!/^[0-9a-zA-Z\u4e00-\u9fa5]/.test(value)){
    callback(new Error('请输入英文中文或者数字'));
  }else{
    callback();
  }
};
