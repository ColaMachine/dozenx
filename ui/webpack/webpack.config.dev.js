const path = require("path");
const HtmlWebpackPlugin = require("html-webpack-plugin");

var ExtractTextPlugin = require('extract-text-webpack-plugin');

console.log("app.js 's dirct "+path.resolve(__dirname,'./app/app.js'));
var scssLoader = 'vue-style-loader!css-loader?importLoaders=1!sass-loader!postcss-loader';
//css-loader 和 style-loader，二者处理的任务不同，css-loader使你能够使用类似@import 和 url(...)的方法实现 require()的功能,style-loader将所有的计算后的样式加入页面中，二者组合在一起使你能够把样式表嵌入webpack打包后的JS文件中。


if (process.env.NODE_ENV === 'production') {
    // publicPath = `/static/${suitCode}/`;
    scssLoader = ExtractTextPlugin.extract({
        use: ['css-loader', 'sass-loader', 'postcss-loader'],
        fallback: 'vue-style-loader'
    });
}
const PATHS={
    app:path.resolve(__dirname,'./src/index.js'),

};
console.log("build path:"+PATHS.build);
module.exports = {
     entry:PATHS.app,//会根据这个目录来构建js
     output: {
         path:PATHS.build,//会生成到这个目录下
         publicPath: "assets",//引入静态文件需要加这个前缀
         filename: 'bundle.js'
     },
      devServer: {
      historyApiFallback: true,
              noInfo: true,
         //hot:true,
         //progress:true
       },
     plugins:[
        new HtmlWebpackPlugin({
            title:'Webpack demo',
        }),


         new ExtractTextPlugin("[name].css"),
           // new ExtractTextPlugin("styles.css"),

    //  new ExtractPlugin('[name].css') //提取出来的样式放在style.css文件中

     ],
     resolve: {
       alias: {
         'vue$': 'vue/dist/vue.common.js'
       }
     },
      module: {
          loaders: [{
              test: /\.(js|jsx)$/,
              exclude: /node_modules/,
              loader: 'babel-loader'

          },

          /*{
              test: /\.(scss|sass)$/,
             loader: 'style-loader!css-loader!sass-loader'
          },*/
           {
              test: /\.scss$/,
              // loader: 'vue-style-loader!css-loader!sass-loader'
              loader: scssLoader
          },
          {
              test: /\.vue$/,
              loader: 'vue-loader',
              options: {
                              loaders: {
                                  // Since sass-loader (weirdly) has SCSS as its default parse mode, we map
                                  // the "scss" and "sass" values for the lang attribute to the right configs here.
                                  // other preprocessors should work out of the box, no loader config like this nessessary.
                                  // 'scss': 'vue-style-loader!css-loader!sass-loader',
                                  // 'sass': 'vue-style-loader!css-loader!sass-loader?indentedSyntax',
                                  'scss': scssLoader,
                              }
                              // other vue-loader options go here
                          }
          },
           { test: /\.css$/,
//           use: ExtractTextPlugin.extract({
//                               fallback: "style-loader",
//                               use: "css-loader"
//                           })
           loader: 'style-loader!css-loader'
         //  loader:ExtractPlugin.extract('style-loader', 'css-loader!sass-loader')

            },

          ]
      }
 };