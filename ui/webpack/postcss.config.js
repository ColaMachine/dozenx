/**
 * 版权所有: 爱WiFi无线运营中心
 * 创建日期: 2017-03-03 15:34
 * 创建作者: 朱学煌 17705818601@189.cn
 * 文件名称: postcss.config.js
 * 版本: v1.0
 * 功能: xx
 * 修改记录: xx
 */

module.exports = {
    plugins: [
        // require('postcss-smart-import')({ /* ...options */ }),
        // require('precss')({ /* ...options */ }),
        require('autoprefixer')({
            browsers: ['last 5 versions']
        })
    ]
};