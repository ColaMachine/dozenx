var path = require('path'); //引文文件路径
var webpack = require('webpack');
var ExtractTextPlugin = require("extract-text-webpack-plugin");
var autoprefixer = require('autoprefixer');
module.exports = {
    devtool: 'source-map',
    entry: ['./src/react/index'], //入口文件
    output: {
        path: path.join(__dirname, 'dist'), //打包出口文件路径
        filename: 'index.js' //打包文件名
    },
    module: {
        loaders: [
            {
                test: /\.css$/,
                use: [
                    {
                        loader: 'style-loader',
                    },
                    {
                        loader: 'css-loader',
                        options: {
                            importLoaders: 1,
                        }
                    },
                    {
                        loader: 'postcss-loader'
                    },
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
                ]
            },
        ]
    },
    plugins: [
        new webpack.HotModuleReplacementPlugin(),
        new webpack.optimize.UglifyJsPlugin({
            mangle: {
                except: ['$super', '$', 'exports', 'require']
            }
        }),
        new ExtractTextPlugin("main.css"),
    ],
    devServer: {
        //配置nodejs本地服务器，
        contentBase: './dist',
        hot: true //本地服务器热更新
    }
}