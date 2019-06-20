

npm install html-webpack-plugin@2 --save-dev

npm install marked --save

npm install vue --save

npm install vue-loader --save

//在项目下，运行下列命令行
npm install --save-dev sass-loader
//因为sass-loader依赖于node-sass，所以还要安装node-sass
//npm install --save-dev node-sass
npm install extract-text-webpack-plugin --save-dev

//安装react 插件
npm install --save-dev react react-dom
npm install --save-dev babel-core babel-loader  babel-preset-es2015 babel-preset-react

在webpack.config.js中，修改配置文件如下

{
                //正则匹配后缀.js 和.jsx文件;
                test: /\.(js|jsx)$/,
                //需要排除的目录
                exclude: '/node_modules/',
                //加载babel-loader转译es6
                use: [{
                    loader: 'babel-loader',
                }],
            },

修改.babelrc文件添加如下代码：


"presets": [
   "react",
   "es2015"
 ],



 npm install --save react-router-dom
npm install axios

###markdown js
npm install mavon-editor --save



shopMain
访问方式
http://127.0.0.1:8080/#/shopMain
程序流程
index.html
webpack.config.dev.js
npm run dev1 ==> build/webpack.dev.conf.js ==>webpack.base.conf.js==>src/main.js
==>../app/module/phone/shopIndex.vue ===>../app/module/route/shopRoute.vue