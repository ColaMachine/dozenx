
/**
 * Created by zhuxh on 15-12-2.
 */

'use strict';



var gulp = require('gulp'),
    plug = require('gulp-load-plugins')(),
    config = require('./config');

// js文件压缩配置
var uglify_option = {
    mangle: false, // 类型：Boolean 默认：true 是否修改变量名
    compress: true, //类型：Boolean 默认：true 是否完全压缩,
    drop_console: true
};

// 组件复制
gulp.task('comp_copy', function () {
    return gulp.src([config.comp.src + '/**/*', '!'+config.comp.src+'/**/test.*'])
        .pipe(gulp.dest(config.comp.dest))
});

// 组件react jsx
gulp.task('comp_react_jsx', function() {
    return gulp.src(config.comp.dest + '/**/*.js')
        //.pipe(plug.jshint({asi: true, eqnull: true, sub: true})) // 检查js
        //.pipe(plug.jshint.reporter()) // 输出检查结果信息
        .pipe(plug.react())
        .pipe(plug.uglify(uglify_option))
        .pipe(gulp.dest(config.comp.dest));
});

// 组件css压缩
gulp.task('comp_css_min', function() {
    return gulp.src(config.comp.dest + '/**/*.css')
        .pipe(plug.minifyCss())
        .pipe(gulp.dest(config.comp.dest));
});

// 删除test目录
gulp.task('clean_test', function () {
    return gulp.src(config.comp.dest + '/**/test')
        .pipe(plug.clean({force: true}));
});


// build comp 组件打包任务
gulp.task('build', ['comp_copy'], function () {
    gulp.run(['comp_react_jsx', 'comp_css_min', 'clean_test']);
});