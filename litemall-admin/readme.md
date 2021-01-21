npm run dev

代理后端在config/index.js里 端口也在这里

编译后的文件在build里

代码在src里

api统一维护在src/api.js里


http://127.0.0.1:9527/#/login?redirect=%2Fdashboard


limit csdn 


菜单再 router的index.js里


跳转页面可以这样写
handleUpdate(row) {
  this.$router.push({ path: '/goods/edit', query: { id: row.id }})
},

分页参数在 component/Pagination/index.vue里改

登录没反应

代理需要维护 dev.env.js 和 config/index.js

http://192.168.120.13:9527/#/dashboard

打包分 npm run build:dep   会依据 dep.env.js 给所有url 加上浅醉
npm run build:pro

因为警告影响了显示,所以先屏蔽掉了 在webpack.base.conf.js 44行