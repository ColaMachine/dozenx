/******/ (function(modules) { // webpackBootstrap
/******/ 	// The module cache
/******/ 	var installedModules = {};
/******/
/******/ 	// The require function
/******/ 	function __webpack_require__(moduleId) {
/******/
/******/ 		// Check if module is in cache
/******/ 		if(installedModules[moduleId]) {
/******/ 			return installedModules[moduleId].exports;
/******/ 		}
/******/ 		// Create a new module (and put it into the cache)
/******/ 		var module = installedModules[moduleId] = {
/******/ 			i: moduleId,
/******/ 			l: false,
/******/ 			exports: {}
/******/ 		};
/******/
/******/ 		// Execute the module function
/******/ 		modules[moduleId].call(module.exports, module, module.exports, __webpack_require__);
/******/
/******/ 		// Flag the module as loaded
/******/ 		module.l = true;
/******/
/******/ 		// Return the exports of the module
/******/ 		return module.exports;
/******/ 	}
/******/
/******/
/******/ 	// expose the modules object (__webpack_modules__)
/******/ 	__webpack_require__.m = modules;
/******/
/******/ 	// expose the module cache
/******/ 	__webpack_require__.c = installedModules;
/******/
/******/ 	// identity function for calling harmony imports with the correct context
/******/ 	__webpack_require__.i = function(value) { return value; };
/******/
/******/ 	// define getter function for harmony exports
/******/ 	__webpack_require__.d = function(exports, name, getter) {
/******/ 		if(!__webpack_require__.o(exports, name)) {
/******/ 			Object.defineProperty(exports, name, {
/******/ 				configurable: false,
/******/ 				enumerable: true,
/******/ 				get: getter
/******/ 			});
/******/ 		}
/******/ 	};
/******/
/******/ 	// getDefaultExport function for compatibility with non-harmony modules
/******/ 	__webpack_require__.n = function(module) {
/******/ 		var getter = module && module.__esModule ?
/******/ 			function getDefault() { return module['default']; } :
/******/ 			function getModuleExports() { return module; };
/******/ 		__webpack_require__.d(getter, 'a', getter);
/******/ 		return getter;
/******/ 	};
/******/
/******/ 	// Object.prototype.hasOwnProperty.call
/******/ 	__webpack_require__.o = function(object, property) { return Object.prototype.hasOwnProperty.call(object, property); };
/******/
/******/ 	// __webpack_public_path__
/******/ 	__webpack_require__.p = "assets";
/******/
/******/ 	// Load entry module and return exports
/******/ 	return __webpack_require__(__webpack_require__.s = 673);
/******/ })
/************************************************************************/
/******/ ({

/***/ 0:
/***/ (function(module, exports, __webpack_require__) {

"use strict";


/*
	MIT License http://www.opensource.org/licenses/mit-license.php
	Author Tobias Koppers @sokra
*/
// css base code, injected by the css-loader
module.exports = function (useSourceMap) {
	var list = [];

	// return the list of modules as css string
	list.toString = function toString() {
		return this.map(function (item) {
			var content = cssWithMappingToString(item, useSourceMap);
			if (item[2]) {
				return "@media " + item[2] + "{" + content + "}";
			} else {
				return content;
			}
		}).join("");
	};

	// import a list of modules into the list
	list.i = function (modules, mediaQuery) {
		if (typeof modules === "string") modules = [[null, modules, ""]];
		var alreadyImportedModules = {};
		for (var i = 0; i < this.length; i++) {
			var id = this[i][0];
			if (typeof id === "number") alreadyImportedModules[id] = true;
		}
		for (i = 0; i < modules.length; i++) {
			var item = modules[i];
			// skip already imported module
			// this implementation is not 100% perfect for weird media query combinations
			//  when a module is imported multiple times with different media queries.
			//  I hope this will never occur (Hey this way we have smaller bundles)
			if (typeof item[0] !== "number" || !alreadyImportedModules[item[0]]) {
				if (mediaQuery && !item[2]) {
					item[2] = mediaQuery;
				} else if (mediaQuery) {
					item[2] = "(" + item[2] + ") and (" + mediaQuery + ")";
				}
				list.push(item);
			}
		}
	};
	return list;
};

function cssWithMappingToString(item, useSourceMap) {
	var content = item[1] || '';
	var cssMapping = item[3];
	if (!cssMapping) {
		return content;
	}

	if (useSourceMap && typeof btoa === 'function') {
		var sourceMapping = toComment(cssMapping);
		var sourceURLs = cssMapping.sources.map(function (source) {
			return '/*# sourceURL=' + cssMapping.sourceRoot + source + ' */';
		});

		return [content].concat(sourceURLs).concat([sourceMapping]).join('\n');
	}

	return [content].join('\n');
}

// Adapted from convert-source-map (MIT)
function toComment(sourceMap) {
	// eslint-disable-next-line no-undef
	var base64 = btoa(unescape(encodeURIComponent(JSON.stringify(sourceMap))));
	var data = 'sourceMappingURL=data:application/json;charset=utf-8;base64,' + base64;

	return '/*# ' + data + ' */';
}

/***/ }),

/***/ 1:
/***/ (function(module, exports, __webpack_require__) {

/*
  MIT License http://www.opensource.org/licenses/mit-license.php
  Author Tobias Koppers @sokra
  Modified by Evan You @yyx990803
*/

var hasDocument = typeof document !== 'undefined'

if (typeof DEBUG !== 'undefined' && DEBUG) {
  if (!hasDocument) {
    throw new Error(
    'vue-style-loader cannot be used in a non-browser environment. ' +
    "Use { target: 'node' } in your Webpack config to indicate a server-rendering environment."
  ) }
}

var listToStyles = __webpack_require__(93)

/*
type StyleObject = {
  id: number;
  parts: Array<StyleObjectPart>
}

type StyleObjectPart = {
  css: string;
  media: string;
  sourceMap: ?string
}
*/

var stylesInDom = {/*
  [id: number]: {
    id: number,
    refs: number,
    parts: Array<(obj?: StyleObjectPart) => void>
  }
*/}

var head = hasDocument && (document.head || document.getElementsByTagName('head')[0])
var singletonElement = null
var singletonCounter = 0
var isProduction = false
var noop = function () {}

// Force single-tag solution on IE6-9, which has a hard limit on the # of <style>
// tags it will allow on a page
var isOldIE = typeof navigator !== 'undefined' && /msie [6-9]\b/.test(navigator.userAgent.toLowerCase())

module.exports = function (parentId, list, _isProduction) {
  isProduction = _isProduction

  var styles = listToStyles(parentId, list)
  addStylesToDom(styles)

  return function update (newList) {
    var mayRemove = []
    for (var i = 0; i < styles.length; i++) {
      var item = styles[i]
      var domStyle = stylesInDom[item.id]
      domStyle.refs--
      mayRemove.push(domStyle)
    }
    if (newList) {
      styles = listToStyles(parentId, newList)
      addStylesToDom(styles)
    } else {
      styles = []
    }
    for (var i = 0; i < mayRemove.length; i++) {
      var domStyle = mayRemove[i]
      if (domStyle.refs === 0) {
        for (var j = 0; j < domStyle.parts.length; j++) {
          domStyle.parts[j]()
        }
        delete stylesInDom[domStyle.id]
      }
    }
  }
}

function addStylesToDom (styles /* Array<StyleObject> */) {
  for (var i = 0; i < styles.length; i++) {
    var item = styles[i]
    var domStyle = stylesInDom[item.id]
    if (domStyle) {
      domStyle.refs++
      for (var j = 0; j < domStyle.parts.length; j++) {
        domStyle.parts[j](item.parts[j])
      }
      for (; j < item.parts.length; j++) {
        domStyle.parts.push(addStyle(item.parts[j]))
      }
      if (domStyle.parts.length > item.parts.length) {
        domStyle.parts.length = item.parts.length
      }
    } else {
      var parts = []
      for (var j = 0; j < item.parts.length; j++) {
        parts.push(addStyle(item.parts[j]))
      }
      stylesInDom[item.id] = { id: item.id, refs: 1, parts: parts }
    }
  }
}

function createStyleElement () {
  var styleElement = document.createElement('style')
  styleElement.type = 'text/css'
  head.appendChild(styleElement)
  return styleElement
}

function addStyle (obj /* StyleObjectPart */) {
  var update, remove
  var styleElement = document.querySelector('style[data-vue-ssr-id~="' + obj.id + '"]')

  if (styleElement) {
    if (isProduction) {
      // has SSR styles and in production mode.
      // simply do nothing.
      return noop
    } else {
      // has SSR styles but in dev mode.
      // for some reason Chrome can't handle source map in server-rendered
      // style tags - source maps in <style> only works if the style tag is
      // created and inserted dynamically. So we remove the server rendered
      // styles and inject new ones.
      styleElement.parentNode.removeChild(styleElement)
    }
  }

  if (isOldIE) {
    // use singleton mode for IE9.
    var styleIndex = singletonCounter++
    styleElement = singletonElement || (singletonElement = createStyleElement())
    update = applyToSingletonTag.bind(null, styleElement, styleIndex, false)
    remove = applyToSingletonTag.bind(null, styleElement, styleIndex, true)
  } else {
    // use multi-style-tag mode in all other cases
    styleElement = createStyleElement()
    update = applyToTag.bind(null, styleElement)
    remove = function () {
      styleElement.parentNode.removeChild(styleElement)
    }
  }

  update(obj)

  return function updateStyle (newObj /* StyleObjectPart */) {
    if (newObj) {
      if (newObj.css === obj.css &&
          newObj.media === obj.media &&
          newObj.sourceMap === obj.sourceMap) {
        return
      }
      update(obj = newObj)
    } else {
      remove()
    }
  }
}

var replaceText = (function () {
  var textStore = []

  return function (index, replacement) {
    textStore[index] = replacement
    return textStore.filter(Boolean).join('\n')
  }
})()

function applyToSingletonTag (styleElement, index, remove, obj) {
  var css = remove ? '' : obj.css

  if (styleElement.styleSheet) {
    styleElement.styleSheet.cssText = replaceText(index, css)
  } else {
    var cssNode = document.createTextNode(css)
    var childNodes = styleElement.childNodes
    if (childNodes[index]) styleElement.removeChild(childNodes[index])
    if (childNodes.length) {
      styleElement.insertBefore(cssNode, childNodes[index])
    } else {
      styleElement.appendChild(cssNode)
    }
  }
}

function applyToTag (styleElement, obj) {
  var css = obj.css
  var media = obj.media
  var sourceMap = obj.sourceMap

  if (media) {
    styleElement.setAttribute('media', media)
  }

  if (sourceMap) {
    // https://developer.chrome.com/devtools/docs/javascript-debugging
    // this makes source maps inside style tags work properly in Chrome
    css += '\n/*# sourceURL=' + sourceMap.sources[0] + ' */'
    // http://stackoverflow.com/a/26603875
    css += '\n/*# sourceMappingURL=data:application/json;base64,' + btoa(unescape(encodeURIComponent(JSON.stringify(sourceMap)))) + ' */'
  }

  if (styleElement.styleSheet) {
    styleElement.styleSheet.cssText = css
  } else {
    while (styleElement.firstChild) {
      styleElement.removeChild(styleElement.firstChild)
    }
    styleElement.appendChild(document.createTextNode(css))
  }
}


/***/ }),

/***/ 100:
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__(0)(undefined);
// imports


// module
exports.push([module.i, "@charset \"UTF-8\";\n/*color*/\n/**font-size**/\nh1, h2, p, span {\n  word-wrap: break-word; }\n\n@CHARSET \"UTF-8\";\n.zw-dropdown {\n  display: inline-block;\n  line-height: 25px;\n  z-index: 150;\n  position: relative;\n  /** 防止弹出的菜单 被其他div 遮挡住*/ }\n\n.zw-dropdown .zw-dropdown-overlay {\n  padding: 5px;\n  position: relative;\n  background: #fff;\n  border-radius: 3px;\n  border: 1px solid #ccc;\n  box-shadow: 0 4px 20px 1px rgba(0, 0, 0, 0.2); }\n\n.zw-dropdown .zw-menu {\n  /* min-width:155px;*/\n  /*  position:absolute;\r\n    left:-150px;*/\n  list-style: none;\n  padding: 0px 0px;\n  background-color: #fff;\n  margin: 0px 0px 0px;\n  border-radius: 5px;\n  /*border:none;1px solid #F0F0EE;*/\n  border: 1px solid #ccc;\n  box-shadow: 0 0px 12px rgba(0, 0, 0, 0.25);\n  text-align: left;\n  z-index: 1000; }\n  .zw-dropdown .zw-menu > li {\n    line-height: 42px;\n    margin: 0px;\n    padding: 0px;\n    border-bottom: 1px dashed #a79595;\n    font-size: 0.5em; }\n    .zw-dropdown .zw-menu > li:last-child {\n      border-bottom: none; }\n    .zw-dropdown .zw-menu > li > a {\n      border: none;\n      margin: 0px;\n      padding: 0 0 0 5px;\n      cursor: pointer;\n      line-height: 42px;\n      display: block;\n      width: 100%; }\n    .zw-dropdown .zw-menu > li:hover {\n      /*background-color:$light-gray;\r\n                border-color:$light-gray;*/ }\n\n.open > .dropdown-menu {\n  display: block; }\n\n.dropdown-menu-justify {\n  position: absolute !important;\n  width: 80%;\n  /*left:-50%;*/\n  position: fixed !important;\n  left: 200px;\n  /*  left: 10%x;*/\n  background-color: white;\n  top: 43px; }\n\n.quicknav .nav-pills > li {\n  border-right: none; }\n", ""]);

// exports


/***/ }),

/***/ 101:
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__(0)(undefined);
// imports


// module
exports.push([module.i, "@charset \"UTF-8\";\n/*color*/\n/**font-size**/\nh1, h2, p, span {\n  word-wrap: break-word; }\n\n@CHARSET \"UTF-8\";\n/**Position**/\n.zw-input-wrapper {\n  display: inline-block;\n  font-size: 14px;\n  line-height: 1.5;\n  color: rgba(0, 0, 0, 0.65);\n  box-sizing: border-box;\n  margin: 0;\n  padding: 0;\n  list-style: none;\n  position: relative;\n  display: table;\n  border-collapse: separate;\n  border-spacing: 0;\n  width: 100%; }\n\n.zw-input-group {\n  /*margin-top:15px;\r\n    margin-bottom:15px;*/ }\n\n.zw-input-group .zw-input {\n  width: 100%; }\n\n.zw-input-group {\n  /* display:flex;\r\n    display:-webkit-flex;*/\n  /*    display: flex;\r\nflex-direction: row;*/\n  boder: 1px solid gray;\n  display: table;\n  border-collapse: separate;\n  /* flex: auto;*/ }\n\n.zw-input-group-addon {\n  font-size: 14px;\n  padding: 0 11px;\n  font-size: 14px;\n  font-weight: normal;\n  line-height: 1;\n  color: rgba(0, 0, 0, 0.65);\n  text-align: center;\n  background-color: #fafafa;\n  /*border: 1px solid #d9d9d9;*/\n  border-radius: 0px;\n  position: relative;\n  transition: all .3s;\n  max-width: 100px; }\n  .zw-input-group-addon * {\n    vertical-align: middle;\n    max-height: 20px; }\n  .zw-input-group-addon .btn {\n    color: #8d8d8d;\n    background-color: #e5e5e5;\n    border-color: #e5e5e5;\n    margin-top: 0px;\n    margin-right: 0px;\n    margin-left: 0px;\n    margin-bottom: 0px;\n    border-bottom-width: 0px;\n    border-left-width: 0px;\n    border-top-width: 0px;\n    border-right-width: 0px;\n    padding-top: 0px;\n    padding-left: 0px;\n    padding-bottom: 0px;\n    padding-right: 0px;\n    line-height: 28px;\n    height: 28px;\n    min-height: 28px; }\n  .zw-input-group-addon .btn-primary {\n    /*  background-color:red;\r\n            border-color: #e5e5e5;*/ }\n\n.zw-input-affix {\n  position: relative; }\n  .zw-input-affix .zw-input-prefix, .zw-input-affix .zw-input-suffix {\n    /* position:absolute;\r\n        top:50%;\r\n        transform: translateY(-50%);*/\n    display: inline-block;\n    /*  border-top:1px solid gray;\r\n     border-bottom:1px solid gray;*/ }\n  .zw-input-affix .zw-input {\n    padding-left: 34px;\n    min-height: 100%;\n    /*padding-left:30px;\r\n                    padding-right:30px;*/ }\n  .zw-input-affix .zw-input-prefix {\n    left: 18px;\n    position: absolute;\n    top: 50%;\n    z-index: 1000;\n    -webkit-transform: translateY(-50%);\n    -ms-transform: translateY(-50%);\n    transform: translateY(-50%);\n    /*   border-left:1px solid $light-gray;\r\n        border-top:1px solid $light-gray;\r\n        border-bottom:1px solid $light-gray;*/\n    /*border-left:1px solid gray;*/ }\n  .zw-input-affix .zw-input-suffix {\n    position: absolute;\n    top: 50%;\n    right: 18px;\n    -webkit-transform: translateY(-50%);\n    -ms-transform: translateY(-50%);\n    transform: translateY(-50%);\n    /*   border-right:1px solid $light-gray;\r\n        border-top:1px solid $light-gray;\r\n        border-bottom:1px solid $light-gray;*/\n    /* border-right:1px solid gray;*/\n    /*  right:0px;*/ }\n\n.search .zw-input-prefix {\n  left: 0px; }\n\n.search .zw-input-suffix {\n  right: 0px; }\n\n/**Box-model**/\n.zw-form {\n  padding: 5px 12.5px; }\n\n.zw-input-wrapper, .zw-input {\n  /* width:100%;*/\n  white-space: nowrap; }\n\n.zw-input-group .btn {\n  /* width:100%;*/ }\n\n/*\r\n.zw-input-affix .zw-input{\r\n  padding-left:$ft-nm*2;\r\n  padding-right:$ft-nm*2;\r\n}*/\n.zw-input-wrapper {\n  line-height: 21px;\n  /* height:$size*2;*/\n  /*  min-height: $size*2.5;\r\n      max-height: $size*2.5;*/\n  box-sizing: border-box;\n  -moz-box-sizing: border-box;\n  /* Firefox */\n  -webkit-box-sizing: border-box;\n  /* Safari */\n  /*  position: relative;\r\n        border-collapse: separate;*/\n  padding: 2.8px 7px;\n  /*\r\n      nowrap:norap;\r\n      white-space:normal ;*/ }\n\n.zw-input-group-prepend > span, .zw-input-group-append > span {\n  /**子元素需要缩小 不然会撑开父元素**/\n  line-height: 21px;\n  /* height:$size*2;*/\n  /*  min-height: $size*2.5;\r\n      max-height: $size*2.5;*/\n  box-sizing: border-box;\n  -moz-box-sizing: border-box;\n  /* Firefox */\n  -webkit-box-sizing: border-box;\n  /* Safari */\n  /*  position: relative;\r\n        border-collapse: separate;*/\n  padding: 0 0; }\n\n.zw-input-group .zw-input, .zw-input-group .zw-input-group-prepend, .zw-input-group .zw-input-group-append {\n  display: table-cell;\n  height: 30px;\n  box-sizing: border-box;\n  vertical-align: middle;\n  /* flex: auto;*/ }\n\n.zw-input-prefix, .zw-input-suffix, .zw-input, .zw-input-group-prepend, .zw-input-group-append {\n  line-height: 21px;\n  /* height:$size*2;*/\n  /*  min-height: $size*2.5;\r\n      max-height: $size*2.5;*/\n  box-sizing: border-box;\n  -moz-box-sizing: border-box;\n  /* Firefox */\n  -webkit-box-sizing: border-box;\n  /* Safari */\n  /*  position: relative;\r\n        border-collapse: separate;*/\n  /*@include padding($ft-nm);*/ }\n\n.zw-input-group-append > *, .zw-input-group-append > * {\n  margin: 0px;\n  height: 25px; }\n\n.zw-input-group-prepend, .zw-input-group-append {\n  border-right-top-radius: 0px;\n  border-radius: 0px; }\n\n.zw-input-group-prepend, .zw-input-group > .zw-input:first-child, .zw-input-group > span > .zw-input:first-child {\n  border-bottom-right-radius: 0 !important;\n  border-top-right-radius: 0 !important; }\n\n.zw-input-group-append, .zw-input-group > .zw-input:last-child, .zw-input-group > span > .zw-input:last-child {\n  border-bottom-left-radius: 0 !important;\n  border-top-left-radius: 0 !important; }\n\n/**Typography**/\n.zw-input, .zw-input-group-prepend, .zw-input-group-append {\n  font-size: 14px; }\n\n/**Visual**/\n.zw-input, .zw-input-group-prepend, .zw-input-group-append {\n  border: 1px solid #e5e5e5;\n  border-bottom: 1px solid #e5e5e5; }\n\n.zw-input, .zw-input-suffix, .zw-input-prefix {\n  color: #2E3E4E; }\n\n.zw-input-suffix:hover, .zw-input-prefix:hover {\n  color: #8d8d8d; }\n\n.zw-form {\n  background-color: #fff; }\n\n.zw-input-group-prepend, .zw-input-group-append {\n  background-color: #e5e5e5; }\n\n.zw-form-item {\n  font-size: 14px;\n  line-height: 1.5;\n  color: rgba(0, 0, 0, 0.65);\n  box-sizing: border-box;\n  margin: 0;\n  padding: 0;\n  list-style: none;\n  margin-bottom: 24px;\n  vertical-align: top;\n  transition: margin 0.15s steps(1);\n  font-family: \"Monospace Number\", \"Chinese Quote\", -apple-system, BlinkMacSystemFont, \"Segoe UI\", Roboto, \"PingFang SC\", \"Hiragino Sans GB\", \"Microsoft YaHei\", \"Helvetica Neue\", Helvetica, Arial, sans-serif; }\n\n/* misc */\n.zw-input {\n  /* font-variant: tabular-nums; */\n  box-sizing: border-box;\n  margin: 0;\n  padding: 0;\n  list-style: none;\n  position: relative;\n  display: inline-block;\n  padding: 4px 11px;\n  width: 100%;\n  height: 32px;\n  font-size: 14px;\n  line-height: 1.5;\n  color: rgba(0, 0, 0, 0.65);\n  background-color: #fff;\n  background-image: none;\n  border: 1px solid #d9d9d9;\n  border-radius: 4px;\n  transition: all .3s; }\n\n.zw-input-prefix .btn, .zw-input-suffix .btn {\n  border-width: 0px !important;\n  border-color: transparent;\n  height: auto;\n  line-height: auto;\n  border-radius: 0px;\n  min-height: auto;\n  margin: 0px;\n  padding: 0px; }\n\n.zw-input-prefix, .zw-input-suffix, .zw-input, .zw-input-group-prepend, .zw-input-group-append {\n  display: table-cell; }\n\n.login-form {\n  max-width: 300px; }\n\n.login-form-button {\n  width: 100%; }\n", ""]);

// exports


/***/ }),

/***/ 102:
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__(0)(undefined);
// imports


// module
exports.push([module.i, "/*color*/\n/**font-size**/\nh1, h2, p, span {\n  word-wrap: break-word; }\n\n@CHARSET \"UTF-8\";\n.zw-icon-list {\n  /*   display:table;*/\n  /*  width:90%;*/\n  border-collapse: 0px;\n  margin-left: auto;\n  margin-right: auto;\n  /*flex-direction:row;\r\n        justify-content: space-between;*/\n  background-color: white; }\n  .zw-icon-list li:first-child {\n    border-top-left-radius: 7px;\n    border-bottom-left-radius: 7px; }\n  .zw-icon-list li:last-child {\n    border-top-right-radius: 7px;\n    border-bottom-right-radius: 7px; }\n  .zw-icon-list li {\n    /*  float:left;\r\n            width:20%;\r\n             display:table-cell;*/\n    text-align: center;\n    list-style: none;\n    cursor: pointer;\n    color: #555;\n    transition: all .3s;\n    position: relative;\n    margin: 3px -3px;\n    background-color: #fff;\n    overflow: hidden;\n    padding: 10px 0 10px 0;\n    border: 0px; }\n    .zw-icon-list li .zw-icon-desc {\n      display: block;\n      text-align: center;\n      -webkit-transform: scale(0.83);\n      -ms-transform: scale(0.83);\n      transform: scale(0.83);\n      font-family: \"Lucida Console\", Consolas;\n      white-space: nowrap; }\n      .zw-icon-list li .zw-icon-desc .zw-badge {\n        display: block;\n        font-family: \"Helvetica Neue For Number\", -apple-system, BlinkMacSystemFont, \"Segoe UI\", Roboto, \"PingFang SC\", \"Hiragino Sans GB\", \"Microsoft YaHei\", \"Helvetica Neue\", Helvetica, Arial, sans-serif;\n        font-size: 14px;\n        line-height: 1.5;\n        color: rgba(0, 0, 0, 0.65);\n        box-sizing: border-box;\n        margin: 0;\n        padding: 0;\n        list-style: none;\n        position: relative;\n        display: inline-block;\n        line-height: 1;\n        vertical-align: middle; }\n", ""]);

// exports


/***/ }),

/***/ 103:
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__(0)(undefined);
// imports


// module
exports.push([module.i, "@charset \"UTF-8\";\n/*color*/\n/**font-size**/\nh1, h2, p, span {\n  word-wrap: break-word; }\n\n@CHARSET \"UTF-8\";\n.zw-col-1 {\n  box-sizing: border-box;\n  float: left;\n  width: 4.16667%;\n  min-height: 14px; }\n\n.zw-col-2 {\n  box-sizing: border-box;\n  float: left;\n  width: 8.33333%;\n  min-height: 14px; }\n\n.zw-col-3 {\n  box-sizing: border-box;\n  float: left;\n  width: 12.5%;\n  min-height: 14px; }\n\n.zw-col-4 {\n  box-sizing: border-box;\n  float: left;\n  width: 16.66667%;\n  min-height: 14px; }\n\n.zw-col-5 {\n  box-sizing: border-box;\n  float: left;\n  width: 20.83333%;\n  min-height: 14px; }\n\n.zw-col-6 {\n  box-sizing: border-box;\n  float: left;\n  width: 25%;\n  min-height: 14px; }\n\n.zw-col-7 {\n  box-sizing: border-box;\n  float: left;\n  width: 29.16667%;\n  min-height: 14px; }\n\n.zw-col-8 {\n  box-sizing: border-box;\n  float: left;\n  width: 33.33333%;\n  min-height: 14px; }\n\n.zw-col-9 {\n  box-sizing: border-box;\n  float: left;\n  width: 37.5%;\n  min-height: 14px; }\n\n.zw-col-10 {\n  box-sizing: border-box;\n  float: left;\n  width: 41.66667%;\n  min-height: 14px; }\n\n.zw-col-11 {\n  box-sizing: border-box;\n  float: left;\n  width: 45.83333%;\n  min-height: 14px; }\n\n.zw-col-12 {\n  box-sizing: border-box;\n  float: left;\n  width: 50%;\n  min-height: 14px; }\n\n.zw-col-13 {\n  box-sizing: border-box;\n  float: left;\n  width: 54.16667%;\n  min-height: 14px; }\n\n.zw-col-14 {\n  box-sizing: border-box;\n  float: left;\n  width: 58.33333%;\n  min-height: 14px; }\n\n.zw-col-15 {\n  box-sizing: border-box;\n  float: left;\n  width: 62.5%;\n  min-height: 14px; }\n\n.zw-col-16 {\n  box-sizing: border-box;\n  float: left;\n  width: 66.66667%;\n  min-height: 14px; }\n\n.zw-col-17 {\n  box-sizing: border-box;\n  float: left;\n  width: 70.83333%;\n  min-height: 14px; }\n\n.zw-col-18 {\n  box-sizing: border-box;\n  float: left;\n  width: 75%;\n  min-height: 14px; }\n\n.zw-col-19 {\n  box-sizing: border-box;\n  float: left;\n  width: 79.16667%;\n  min-height: 14px; }\n\n.zw-col-20 {\n  box-sizing: border-box;\n  float: left;\n  width: 83.33333%;\n  min-height: 14px; }\n\n.zw-col-21 {\n  box-sizing: border-box;\n  float: left;\n  width: 87.5%;\n  min-height: 14px; }\n\n.zw-col-22 {\n  box-sizing: border-box;\n  float: left;\n  width: 91.66667%;\n  min-height: 14px; }\n\n.zw-col-23 {\n  box-sizing: border-box;\n  float: left;\n  width: 95.83333%;\n  min-height: 14px; }\n\n.zw-col-24 {\n  box-sizing: border-box;\n  float: left;\n  width: 100%;\n  min-height: 14px; }\n\n@media (min-width: 0) and (max-width: 768px) {\n  .zw-col-sm-1 {\n    box-sizing: border-box;\n    float: left;\n    width: 4.16667%;\n    min-height: 14px; }\n  .zw-col-sm-2 {\n    box-sizing: border-box;\n    float: left;\n    width: 8.33333%;\n    min-height: 14px; }\n  .zw-col-sm-3 {\n    box-sizing: border-box;\n    float: left;\n    width: 12.5%;\n    min-height: 14px; }\n  .zw-col-sm-4 {\n    box-sizing: border-box;\n    float: left;\n    width: 16.66667%;\n    min-height: 14px; }\n  .zw-col-sm-5 {\n    box-sizing: border-box;\n    float: left;\n    width: 20.83333%;\n    min-height: 14px; }\n  .zw-col-sm-6 {\n    box-sizing: border-box;\n    float: left;\n    width: 25%;\n    min-height: 14px; }\n  .zw-col-sm-7 {\n    box-sizing: border-box;\n    float: left;\n    width: 29.16667%;\n    min-height: 14px; }\n  .zw-col-sm-8 {\n    box-sizing: border-box;\n    float: left;\n    width: 33.33333%;\n    min-height: 14px; }\n  .zw-col-sm-9 {\n    box-sizing: border-box;\n    float: left;\n    width: 37.5%;\n    min-height: 14px; }\n  .zw-col-sm-10 {\n    box-sizing: border-box;\n    float: left;\n    width: 41.66667%;\n    min-height: 14px; }\n  .zw-col-sm-11 {\n    box-sizing: border-box;\n    float: left;\n    width: 45.83333%;\n    min-height: 14px; }\n  .zw-col-sm-12 {\n    box-sizing: border-box;\n    float: left;\n    width: 50%;\n    min-height: 14px; }\n  .zw-col-sm-13 {\n    box-sizing: border-box;\n    float: left;\n    width: 54.16667%;\n    min-height: 14px; }\n  .zw-col-sm-14 {\n    box-sizing: border-box;\n    float: left;\n    width: 58.33333%;\n    min-height: 14px; }\n  .zw-col-sm-15 {\n    box-sizing: border-box;\n    float: left;\n    width: 62.5%;\n    min-height: 14px; }\n  .zw-col-sm-16 {\n    box-sizing: border-box;\n    float: left;\n    width: 66.66667%;\n    min-height: 14px; }\n  .zw-col-sm-17 {\n    box-sizing: border-box;\n    float: left;\n    width: 70.83333%;\n    min-height: 14px; }\n  .zw-col-sm-18 {\n    box-sizing: border-box;\n    float: left;\n    width: 75%;\n    min-height: 14px; }\n  .zw-col-sm-19 {\n    box-sizing: border-box;\n    float: left;\n    width: 79.16667%;\n    min-height: 14px; }\n  .zw-col-sm-20 {\n    box-sizing: border-box;\n    float: left;\n    width: 83.33333%;\n    min-height: 14px; }\n  .zw-col-sm-21 {\n    box-sizing: border-box;\n    float: left;\n    width: 87.5%;\n    min-height: 14px; }\n  .zw-col-sm-22 {\n    box-sizing: border-box;\n    float: left;\n    width: 91.66667%;\n    min-height: 14px; }\n  .zw-col-sm-23 {\n    box-sizing: border-box;\n    float: left;\n    width: 95.83333%;\n    min-height: 14px; }\n  .zw-col-sm-24 {\n    box-sizing: border-box;\n    float: left;\n    width: 100%;\n    min-height: 14px; } }\n\n@media (min-width: 768px) and (max-width: 1200px) {\n  .zw-col-md-1 {\n    box-sizing: border-box;\n    float: left;\n    width: 4.16667%;\n    min-height: 14px; }\n  .zw-col-md-2 {\n    box-sizing: border-box;\n    float: left;\n    width: 8.33333%;\n    min-height: 14px; }\n  .zw-col-md-3 {\n    box-sizing: border-box;\n    float: left;\n    width: 12.5%;\n    min-height: 14px; }\n  .zw-col-md-4 {\n    box-sizing: border-box;\n    float: left;\n    width: 16.66667%;\n    min-height: 14px; }\n  .zw-col-md-5 {\n    box-sizing: border-box;\n    float: left;\n    width: 20.83333%;\n    min-height: 14px; }\n  .zw-col-md-6 {\n    box-sizing: border-box;\n    float: left;\n    width: 25%;\n    min-height: 14px; }\n  .zw-col-md-7 {\n    box-sizing: border-box;\n    float: left;\n    width: 29.16667%;\n    min-height: 14px; }\n  .zw-col-md-8 {\n    box-sizing: border-box;\n    float: left;\n    width: 33.33333%;\n    min-height: 14px; }\n  .zw-col-md-9 {\n    box-sizing: border-box;\n    float: left;\n    width: 37.5%;\n    min-height: 14px; }\n  .zw-col-md-10 {\n    box-sizing: border-box;\n    float: left;\n    width: 41.66667%;\n    min-height: 14px; }\n  .zw-col-md-11 {\n    box-sizing: border-box;\n    float: left;\n    width: 45.83333%;\n    min-height: 14px; }\n  .zw-col-md-12 {\n    box-sizing: border-box;\n    float: left;\n    width: 50%;\n    min-height: 14px; }\n  .zw-col-md-13 {\n    box-sizing: border-box;\n    float: left;\n    width: 54.16667%;\n    min-height: 14px; }\n  .zw-col-md-14 {\n    box-sizing: border-box;\n    float: left;\n    width: 58.33333%;\n    min-height: 14px; }\n  .zw-col-md-15 {\n    box-sizing: border-box;\n    float: left;\n    width: 62.5%;\n    min-height: 14px; }\n  .zw-col-md-16 {\n    box-sizing: border-box;\n    float: left;\n    width: 66.66667%;\n    min-height: 14px; }\n  .zw-col-md-17 {\n    box-sizing: border-box;\n    float: left;\n    width: 70.83333%;\n    min-height: 14px; }\n  .zw-col-md-18 {\n    box-sizing: border-box;\n    float: left;\n    width: 75%;\n    min-height: 14px; }\n  .zw-col-md-19 {\n    box-sizing: border-box;\n    float: left;\n    width: 79.16667%;\n    min-height: 14px; }\n  .zw-col-md-20 {\n    box-sizing: border-box;\n    float: left;\n    width: 83.33333%;\n    min-height: 14px; }\n  .zw-col-md-21 {\n    box-sizing: border-box;\n    float: left;\n    width: 87.5%;\n    min-height: 14px; }\n  .zw-col-md-22 {\n    box-sizing: border-box;\n    float: left;\n    width: 91.66667%;\n    min-height: 14px; }\n  .zw-col-md-23 {\n    box-sizing: border-box;\n    float: left;\n    width: 95.83333%;\n    min-height: 14px; }\n  .zw-col-md-24 {\n    box-sizing: border-box;\n    float: left;\n    width: 100%;\n    min-height: 14px; } }\n\n.w-grid-5 {\n  width: calc(100%/5); }\n\n@media screen and (max-width: 60em) and (min-width: 30em) {\n  .w-grid-4-m {\n    width: calc(100%/4); } }\n\n.w-grid-4 {\n  width: calc(100%/4); }\n\n.w-grid-3 {\n  width: calc(100%/3); }\n\n.w-grid-2 {\n  width: calc(100%/2); }\n\n@media (min-width: 1200px) {\n  .zw-col-lg-1 {\n    box-sizing: border-box;\n    float: left;\n    width: 4.16667%;\n    min-height: 14px; }\n  .zw-col-lg-2 {\n    box-sizing: border-box;\n    float: left;\n    width: 8.33333%;\n    min-height: 14px; }\n  .zw-col-lg-3 {\n    box-sizing: border-box;\n    float: left;\n    width: 12.5%;\n    min-height: 14px; }\n  .zw-col-lg-4 {\n    box-sizing: border-box;\n    float: left;\n    width: 16.66667%;\n    min-height: 14px; }\n  .zw-col-lg-5 {\n    box-sizing: border-box;\n    float: left;\n    width: 20.83333%;\n    min-height: 14px; }\n  .zw-col-lg-6 {\n    box-sizing: border-box;\n    float: left;\n    width: 25%;\n    min-height: 14px; }\n  .zw-col-lg-7 {\n    box-sizing: border-box;\n    float: left;\n    width: 29.16667%;\n    min-height: 14px; }\n  .zw-col-lg-8 {\n    box-sizing: border-box;\n    float: left;\n    width: 33.33333%;\n    min-height: 14px; }\n  .zw-col-lg-9 {\n    box-sizing: border-box;\n    float: left;\n    width: 37.5%;\n    min-height: 14px; }\n  .zw-col-lg-10 {\n    box-sizing: border-box;\n    float: left;\n    width: 41.66667%;\n    min-height: 14px; }\n  .zw-col-lg-11 {\n    box-sizing: border-box;\n    float: left;\n    width: 45.83333%;\n    min-height: 14px; }\n  .zw-col-lg-12 {\n    box-sizing: border-box;\n    float: left;\n    width: 50%;\n    min-height: 14px; }\n  .zw-col-lg-13 {\n    box-sizing: border-box;\n    float: left;\n    width: 54.16667%;\n    min-height: 14px; }\n  .zw-col-lg-14 {\n    box-sizing: border-box;\n    float: left;\n    width: 58.33333%;\n    min-height: 14px; }\n  .zw-col-lg-15 {\n    box-sizing: border-box;\n    float: left;\n    width: 62.5%;\n    min-height: 14px; }\n  .zw-col-lg-16 {\n    box-sizing: border-box;\n    float: left;\n    width: 66.66667%;\n    min-height: 14px; }\n  .zw-col-lg-17 {\n    box-sizing: border-box;\n    float: left;\n    width: 70.83333%;\n    min-height: 14px; }\n  .zw-col-lg-18 {\n    box-sizing: border-box;\n    float: left;\n    width: 75%;\n    min-height: 14px; }\n  .zw-col-lg-19 {\n    box-sizing: border-box;\n    float: left;\n    width: 79.16667%;\n    min-height: 14px; }\n  .zw-col-lg-20 {\n    box-sizing: border-box;\n    float: left;\n    width: 83.33333%;\n    min-height: 14px; }\n  .zw-col-lg-21 {\n    box-sizing: border-box;\n    float: left;\n    width: 87.5%;\n    min-height: 14px; }\n  .zw-col-lg-22 {\n    box-sizing: border-box;\n    float: left;\n    width: 91.66667%;\n    min-height: 14px; }\n  .zw-col-lg-23 {\n    box-sizing: border-box;\n    float: left;\n    width: 95.83333%;\n    min-height: 14px; }\n  .zw-col-lg-24 {\n    box-sizing: border-box;\n    float: left;\n    width: 100%;\n    min-height: 14px; } }\n\n.pull-right {\n  float: right; }\n\n.pull-left {\n  float: left; }\n\n.zw-gutter-row {\n  padding-left: 15px;\n  padding-right: 15px; }\n\n.example .zw-header {\n  background-color: rgba(0, 160, 233, 0.7); }\n\n.zw-content {\n  background-color: #fff;\n  /*padding-left:20px;*/\n  -webkit-flex: auto;\n  -ms-flex: auto;\n  flex: auto; }\n\n.zw-footer {\n  /**background-color: rgba(0, 160, 233, .3);**/\n  /** @include height_size(65px);**/\n  -webkit-flex: 0 0 auto;\n  -ms-flex: 0 0 auto;\n  flex: 0 0 auto; }\n\n.example .zw-footer {\n  background-color: rgba(0, 160, 233, 0.3);\n  line-height: 97.5px;\n  /* height:$size*2;*/\n  /*  min-height: $size*2.5;\r\n      max-height: $size*2.5;*/\n  box-sizing: border-box;\n  -moz-box-sizing: border-box;\n  /* Firefox */\n  -webkit-box-sizing: border-box;\n  /* Safari */\n  /*  position: relative;\r\n        border-collapse: separate;*/ }\n\n.zw-layout {\n  display: -webkit-flex;\n  display: -ms-flexbox;\n  display: flex;\n  -webkit-flex-direction: column;\n  -ms-flex-direction: column;\n  flex-direction: column;\n  -webkit-flex: auto;\n  -ms-flex: auto;\n  flex: auto;\n  /*overflow: auto;\r\n\r\n    background: #ececec;\r\n    */ }\n\n.example .zw-layout {\n  background: #ececec; }\n\n.zw-layout.zw-layout-has-sider {\n  -webkit-flex-direction: row;\n  -ms-flex-direction: row;\n  flex-direction: row; }\n\n.zw-sider {\n  /** background-color: rgba(0, 160, 233, .1);\r\n    background-color: $light-black;\r\n     @include height_size(120px);\r\n    */\n  width: 200px;\n  -webkit-flex: 0 0 200px;\n  -ms-flex: 0 0 200px;\n  flex: 0 0 200px; }\n\n.example .zw-sider {\n  background-color: rgba(0, 160, 233, 0.1);\n  background-color: #2E3E4E;\n  line-height: 180px;\n  /* height:$size*2;*/\n  /*  min-height: $size*2.5;\r\n      max-height: $size*2.5;*/\n  box-sizing: border-box;\n  -moz-box-sizing: border-box;\n  /* Firefox */\n  -webkit-box-sizing: border-box;\n  /* Safari */\n  /*  position: relative;\r\n        border-collapse: separate;*/ }\n\n.zw-collapse {\n  /* background-color:$lighter-gray;*/\n  /* border-radius: 4px;*/\n  /*  border: 1px solid $gray;\r\n    border-bottom: 1px solid $gray;*/ }\n\n.zw-collapse-item {\n  border-bottom: 1px solid #d9d9d9; }\n  .zw-collapse-item .zw-panel-body {\n    padding: 0 5px 0 15px; }\n  .zw-collapse-item i {\n    transition: all 0.3s;\n    margin-right: 15px; }\n\n.zw-collapse-item .zw-panel-body {\n  /*collapse use */\n  visibility: hidden;\n  height: 0;\n  overflow: hidden;\n  transition: all 0.3s; }\n\n.zw-collapse-item-active .zw-panel-body {\n  visibility: visible;\n  height: auto; }\n\n.zw-collapse-item-active i {\n  -webkit-transform: rotate(90deg);\n  -ms-transform: rotate(90deg);\n  transform: rotate(90deg); }\n\n.zw-panel-body {\n  font-size: 14px;\n  /* padding:5px 15px;*/\n  /*display:none;*/\n  background-color: #fff;\n  line-height: 21px;\n  /* @include height_size($ft-nm);\r\n\r\n\r\n     background-color:$lighter-gray;*/\n  padding-bottom: 25px;\n  padding-top: 15px; }\n\n.zw-panel {\n  margin-top: 15px; }\n\n.zw-panel-header {\n  line-height: 21px;\n  /* height:$size*2;*/\n  /*  min-height: $size*2.5;\r\n      max-height: $size*2.5;*/\n  box-sizing: border-box;\n  -moz-box-sizing: border-box;\n  /* Firefox */\n  -webkit-box-sizing: border-box;\n  /* Safari */\n  /*  position: relative;\r\n        border-collapse: separate;*/\n  font-size: 14px;\n  /*line-height: 22px;*/\n  padding: 8px 0 8px 32px;\n  color: rgba(0, 0, 0, 0.85);\n  cursor: pointer;\n  position: relative;\n  transition: all 0.3s;\n  line-height: 21px;\n  /* height:$size*2;*/\n  /*  min-height: $size*2.5;\r\n      max-height: $size*2.5;*/\n  box-sizing: border-box;\n  -moz-box-sizing: border-box;\n  /* Firefox */\n  -webkit-box-sizing: border-box;\n  /* Safari */\n  /*  position: relative;\r\n        border-collapse: separate;*/\n  background-color: #e5e5e5;\n  border-bottom: 1px solid #d9d9d9; }\n\n.zw-panel-header > span {\n  display: inline-block;\n  width: 90%; }\n\n.expand-transition {\n  transition: all 0.3s ease;\n  height: 30px;\n  padding: 10px;\n  background-color: #eee;\n  overflow: hidden; }\n\n/* .expand-enter 定义进入的开始状态 */\n/* .expand-leave 定义离开的结束状态 */\n.expand-enter,\n.expand-leave {\n  height: 0;\n  padding: 0 10px;\n  opacity: 0; }\n\n.zw-header {\n  /* background-color: #89bf04;*/\n  /*  padding: 9px 14px 19px;*/\n  /* min-width: 775px; */\n  width: 100%; }\n\n.zw-header-wrap {\n  line-height: 1;\n  font-family: Droid Sans, sans-serif;\n  min-width: 760px;\n  max-width: 960px;\n  margin-left: auto;\n  color: white;\n  margin-right: auto; }\n\n.zw-panel-heading {\n  font-size: 20px; }\n\nli {\n  list-style-type: none; }\n\nbody {\n  font-size: 20px; }\n\n.bar-title {\n  background-color: green; }\n\n.zw-row {\n  /*border-bottom:1px dashed  gray;*/\n  font-size: 20px;\n  width: 100%; }\n\n.panel-title,\n.panel-body,\n.panel-heading {\n  padding: 2px; }\n\na {\n  cursor: pointer; }\n\n.zw-panel-title {\n  border: 1px solid gray;\n  font-size: 100%;\n  line-height: 21px;\n  /* height:$size*2;*/\n  /*  min-height: $size*2.5;\r\n      max-height: $size*2.5;*/\n  box-sizing: border-box;\n  -moz-box-sizing: border-box;\n  /* Firefox */\n  -webkit-box-sizing: border-box;\n  /* Safari */\n  /*  position: relative;\r\n        border-collapse: separate;*/\n  font-size: 14px;\n  /*  height:32px; */\n  /*  display:inline-block; */ }\n\n.zw-row-title {\n  color: #00a0e9;\n  /*     display:inline-block */\n  font-bold: 12;\n  line-height: 21px;\n  /* height:$size*2;*/\n  /*  min-height: $size*2.5;\r\n      max-height: $size*2.5;*/\n  box-sizing: border-box;\n  -moz-box-sizing: border-box;\n  /* Firefox */\n  -webkit-box-sizing: border-box;\n  /* Safari */\n  /*  position: relative;\r\n        border-collapse: separate;*/\n  /*  height:32px; */\n  display: inline-block;\n  width: 100%; }\n\n.zw-row-title-main {\n  display: inline-block;\n  /* padding: 7px 0 4px;*/\n  font-size: 15px;\n  text-align: center;\n  width: 50px;\n  color: white; }\n\n.zw-row-title-sub {\n  display: inline-block;\n  padding: 7px 0 4px;\n  font-size: 15px;\n  text-align: center;\n  color: black; }\n\n.post .zw-row-title-main {\n  background-color: #10a54a; }\n\n.get .zw-row-title-main {\n  background-color: #0f6ab4; }\n\n.put .zw-row-title-main {\n  background-color: #ffd20f; }\n\n.delete .zw-row-title-main {\n  background-color: #a41e22; }\n\n.zw-app-list li {\n  padding-left: 15px; }\n\n.zw-app-list li a {\n  display: inline-block; }\n\n/**app-img-list */\nli {\n  list-style: none; }\n\n.img-upload {\n  max-width: 50px; }\n\n.app-ul {\n  background-color: #eee; }\n\n.app-card {\n  margin: 0 0 10px;\n  padding: 15px 10px;\n  background-color: #fff;\n  display: -webkit-flex !important;\n  display: -ms-flexbox !important;\n  display: flex !important;\n  padding: 10px;\n  min-height: 50px;\n  -webkit-justify-content: flex-start;\n  -ms-flex-pack: start;\n  justify-content: flex-start;\n  -webkit-align-content: stretch;\n  -ms-flex-line-pack: stretch;\n  align-content: stretch;\n  -webkit-align-items: stretch;\n  -ms-flex-align: stretch;\n  align-items: stretch;\n  /* min-width:30px;\r\n        max-width:20%;*/\n  /*display:table-cell;*/ }\n\n.app-li-it {\n  -webkit-justify-content: flex-start;\n  -ms-flex-pack: start;\n  justify-content: flex-start;\n  -webkit-align-content: stretch;\n  -ms-flex-line-pack: stretch;\n  align-content: stretch;\n  -webkit-align-items: stretch;\n  -ms-flex-align: stretch;\n  align-items: stretch;\n  display: -webkit-flex !important;\n  display: -ms-flexbox !important;\n  display: flex !important; }\n\n.app-li-it-pic {\n  display: block;\n  /* width:30%;*/\n  /* float:left; w图片居左;*/\n  max-width: 33.3%;\n  /*\r\n              手机上不超过30% 的屏幕\r\n              pc上建议200px;\r\n              max-height: 100px;\r\n                min-height: 100px;*/ }\n  .app-li-it-pic img {\n    width: 99%;\n    height: 100%; }\n\n.app-li-it-content {\n  -webkit-flex: 2;\n  -ms-flex: 2;\n  flex: 2;\n  /*width:80%;*/\n  font-size: 12px;\n  position: relative;\n  /*margin-left:3%;*/\n  padding-left: 15px;\n  /*display:table-cell;*/\n  vertical-align: middle; }\n  .app-li-it-content h1 {\n    font-size: 15px !important;\n    line-height: 18px;\n    /*margin-top: 10px;*/\n    font-weight: bold; }\n  .app-li-it-content h2 {\n    font-size: 12px !important;\n    line-height: 14px;\n    /*margin-top: 10px;*/ }\n  .app-li-it-content h3 {\n    font-size: 13px !important;\n    line-height: 14px; }\n  .app-li-it-content h3 {\n    font-size: 13px !important;\n    line-height: 14px;\n    color: gray; }\n  .app-li-it-content h4 {\n    font-size: 13px !important;\n    line-height: 14px; }\n  .app-li-it-content h5 {\n    font-size: 13px !important;\n    line-height: 14px;\n    color: red; }\n  .app-li-it-content .app-li-it-content-top-corner {\n    position: absolute;\n    top: 5px;\n    font-size: 24px !important;\n    font-weight: bold;\n    right: 5px;\n    color: red; }\n\n.app-li-it-text {\n  width: 80%;\n  font-size: 12px;\n  color: gray;\n  display: -webkit-flex;\n  display: -ms-flexbox;\n  display: flex;\n  padding-right: 50px;\n  overflow: hidden;\n  -webkit-justify-content: space-between;\n  -ms-flex-pack: justify;\n  justify-content: space-between; }\n  .app-li-it-text .src-net {\n    /*display:table-cell;*/\n    width: 30%; }\n  .app-li-it-text .comment-num {\n    /*display:table-cell;*/\n    width: 30%; }\n  .app-li-it-text .goods-score {\n    /*display:table-cell;*/\n    width: 30%; }\n\n.app-li.pc .app-li-it .app-li-it-content-body h2, .app-li.pc .app-li-it .app-li-it-content-body h3, .app-li.pc .app-li-it .app-li-it-content-body h4, .app-li.pc .app-li-it .app-li-it-content-body h5 {\n  margin-top: 10px;\n  float: left;\n  display: inline-block; }\n\n.app-li.pc .app-li-it .app-li-it-text {\n  position: absolute;\n  bottom: 0; }\n\n.zw-hr {\n  color: #f7f1f1; }\n\n.zw-hr2 {\n  color: #ece3e3; }\n\n.zw-box {\n  border: 1px solid #e9e9e9;\n  border-radius: 4px;\n  display: inline-block;\n  width: 90%;\n  position: relative;\n  margin: 16px 16px 16px 16px;\n  transition: all .2s;\n  margin-left: 30px; }\n\n.zw-app-box1 {\n  border: 1px solid #e9e9e9;\n  border-radius: 4px;\n  display: inline-block;\n  width: 90%;\n  position: relative;\n  margin: 10px 4px 0px 4px;\n  transition: all .2s; }\n", ""]);

// exports


/***/ }),

/***/ 104:
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__(0)(undefined);
// imports


// module
exports.push([module.i, "@charset \"UTF-8\";\n/*color*/\n/**font-size**/\nh1, h2, p, span {\n  word-wrap: break-word; }\n\n@CHARSET \"UTF-8\";\n.page {\n  margin-left: 50px;\n  margin-right: 50px;\n  margin-top: 100px;\n  background-color: #fff;\n  border: 1px solid #e5e5e5; }\n\ni.undefined {\n  /*有的时候 没有样式的情况下 也要占用一个位置*/\n  display: inline-block;\n  min-width: 1rem; }\n\ndiv ul li {\n  display: block; }\n\n.body-wrapper {\n  padding-top: 0; }\n\n.main-wrap {\n  display: table-cell;\n  /*width:80%;*/\n  min-width: 800px;\n  /*margin-left: 0px;*/ }\n\n/**Position**/\n/**Box-model**/\n/**Typography**/\n/**Visual**/\n/* misc */\n/*********************************Position 位置**/\nbody {\n  font-size: 14px; }\n\n.zw-menu {\n  display: list-item;\n  font-size: 14px; }\n\n.zw-menu {\n  /*position:absolute;*/\n  list-style: none;\n  padding: 0;\n  background-color: #fff;\n  margin: 0;\n  border-radius: 5px;\n  border: none;\n  /*1px solid #F0F0EE;*/\n  text-align: left;\n  z-index: 1000;\n  /*  width: 200px;*/\n  /* 为了让弹出来的菜单能够只适应宽度*/\n  /***li**/ }\n  .zw-menu li {\n    position: relative;\n    line-height: 28px;\n    margin: 0;\n    padding: 0; }\n    .zw-menu li:last-child {\n      /*border-bottom: 1px solid $gray1;*/ }\n    .zw-menu li > a {\n      border: none;\n      margin: 0;\n      /* padding:0 0 0 5px;*/\n      cursor: pointer;\n      line-height: 42px;\n      display: block;\n      width: 100%; }\n    .zw-menu li ul {\n      /**ul**/\n      margin-left: 10px;\n      min-width: 30px;\n      /**为了防止ie7下展开不显示 to prevent in ie7 not show **/\n      min-height: 20px; }\n  .zw-menu .zw-menu-item {\n    border-bottom: 1px dashed #a79595;\n    line-height: 37.5px;\n    /* height:$size*2;*/\n    /*  min-height: $size*2.5;\r\n      max-height: $size*2.5;*/\n    box-sizing: border-box;\n    -moz-box-sizing: border-box;\n    /* Firefox */\n    -webkit-box-sizing: border-box;\n    /* Safari */\n    /*  position: relative;\r\n        border-collapse: separate;*/\n    @inclue padding($ft-h1); }\n    .zw-menu .zw-menu-item:hover {\n      /**background-color:$light-gray;**/\n      color: #00a0e9; }\n\n.zw-menu > ul > li {\n  list-style-type: none;\n  position: relative;\n  border-bottom: 1px solid #e5e5e5; }\n\n/**a**/\n.zw-menu li > a {\n  position: relative; }\n\n.zw-menu li > a {\n  display: block;\n  width: 100%; }\n\n.zw-menu li a {\n  @inclulde height_size($ft-nm); }\n\n.zw-menu li > a {\n  @inclulde height_size($ft-nm); }\n\n/***子菜单区域**/\n.zw-menu-submenu {\n  position: relative; }\n\n.zw-menu-sub {\n  padding-left: 0; }\n\n/**打开的情况**/\n.zw-menu-submenu-open > .zw-menu-sub {\n  display: block;\n  /**z只有当水平的菜单 鼠标移动上去会自动打开**/ }\n\n/**.zw-menu-item-group菜单集合 保证group的内容菜单项目比title 有缩进**/\n.zw-menu-item-group zw-menu-item {\n  padding-left: 50px; }\n\n.zw-menu-item-group-title {\n  color: #8d8d8d;\n  padding-left: 10px; }\n\n.zw-menu-wrap {\n  /*  z-index: 2000;*/\n  /**Position**/\n  font-size: 12px;\n  top: 0;\n  left: 0;\n  position: absolute; }\n\n/**zw-icon**/\n/*.fa{\n   font-size:1.2rem;\n}\n ul ul .fa{\n font-size:0.8rem;\n\n  }*/\n.zw-menu > ul.nav > li > ul > li .zw-icon {\n  padding-left: 10px;\n  padding-right: 10px; }\n\n.zw-menu .zw-icon {\n  /* margin-left:7px;*/\n  margin-left: 0;\n  /* margin-top:7px;*/ }\n\nul.zw-menu > li > a > .zw-icon {\n  /* margin-left:7px;*/\n  /* display:block;*/\n  /*width:100%;*/\n  text-align: center; }\n\n.zw-menu-item-text .zw-icon {\n  position: relative;\n  line-height: 2.25rem;\n  min-height: 2.25rem;\n  height: 2.25rem; }\n\n.zw-menu-item-text .zw-icon-arrow {\n  position: relative;\n  line-height: 10px;\n  min-height: 10px;\n  height: 10px; }\n\n.zw-menu .zw-icon {\n  /*margin-left: 15px;*/\n  margin-right: 15px; }\n\n/**arrow**/\n.zw-menu .zw-icon-arrow {\n  transition: -webkit-transform 0.3s ease-in-out 0s;\n  transition: transform 0.3s ease-in-out 0s;\n  transition: transform 0.3s ease-in-out 0s, -webkit-transform 0.3s ease-in-out 0s; }\n\n.zw-menu-submenu > a > .zw-icon-arrow {\n  transition: all 0.3s;\n  /*  @include height_size($ft-h3);*/ }\n\n.zw-menu-item-text {\n  position: relative;\n  min-width: 100px;\n  padding-right: 20px; }\n\n.zw-menu-submenu > a .zw-icon-arrow {\n  position: absolute;\n  /*   display:inline-block;*/\n  top: 50%;\n  /*  transform: translateY(-50%);*/\n  margin-top: -5px;\n  height: 10px;\n  /*  left:120px;*/\n  right: -5px;\n  /*top:$ft-h1*0.3;*/\n  /*    vertical-align: baseline;\n     text-align: center;*/ }\n\n/*\n .zw-menu .zw-icon-arrow {\n\tposition: absolute;\n\ttop: 50%;\n\tmargin-top: -$ft-nm/2;\n\tright: $ft-nm/2;\n}*/\n.zw-menu-submenu-open > a .zw-icon-arrow {\n  -webkit-transform: rotate(180deg);\n  -ms-transform: rotate(180deg);\n  transform: rotate(180deg); }\n\n/**文本**/\n.zw-menu-item-text {\n  width: 0;\n  /*   opacity:1;\n    filter: alpha(opacity=1); */ }\n\nzw-menu > li > a > .zw-menu-item-text {\n  /* display: none;*/\n  /* font-size:0.5*$ft-nm;*/ }\n\nul.zw-menu > li > ul > li .zw-menu-item-text {\n  /*   margin-left:5px;\n    padding-left:5px;*/ }\n\n.zw-menu .zw-menu-item-text {\n  margin-left: 0; }\n\nul.zw-menu > li > a > .zw-menu-item-text {\n  display: block;\n  width: 100%;\n  /*text-align:center;*/\n  /*line-height:1.55rem;\n min-height:1.55rem;*/ }\n\n.zw-menu .zw-menu-item-text {\n  /*margin-left: 15px;*/\n  padding-left: 20px; }\n\n/*********************************Box-model 盒子模型**/\n.menu-wrap {\n  width: 200px;\n  height: 100%;\n  text-align: left; }\n\n/*div ,ul,li,a {\n    margin: 0px 0px 0px 0px;\n  \tpadding: 0px 0px 0px 0px;\n}*/\n/**覆盖 bootstrap**/\n.zw-menu-item-text {\n  box-sizing: border-box;\n  padding-right: 25px; }\n\n/********************************Typography 字体**/\n.menu-wrap .logo {\n  font-weight: 700;\n  font-size: 1.25rem; }\n\n.zw-menu > .nav > li a {\n  font-weight: 500; }\n\n.zw-menu .fa-caret-down {\n  font-size: 14px; }\n\n/**Visual 视觉**/\n/**颜色**/\n.zw-menu ul,\n.zw-menu,\n.zw-menu a {\n  color: #fff;\n  background-color: #2E3E4E; }\n\n.zw-menu-white {\n  width: 100%;\n  color: #2E3E4E;\n  border-top: 1px solid #f7f1f1;\n  border-bottom: 1px solid #f7f1f1;\n  background-color: #fff !important; }\n  .zw-menu-white ul {\n    color: #2E3E4E;\n    background-color: #fff; }\n  .zw-menu-white a {\n    color: #2E3E4E;\n    background-color: #fff; }\n  .zw-menu-white li {\n    border-left: 1px solid #f7f1f1; }\n\n.zw-menu-item:hover,\n.zw-menu-item:focus,\n.zw-menu-sub > li:hover,\n.zw-menu-sub > li > a:hover,\n.zw-menu-sub:focus,\n.zw-menu-sub > li:focus,\n.zw-menu-sub > li > a:focus,\n.zw-menu-sub {\n  background-color: rgba(0, 0, 0, 0.067); }\n\n.zw-menu-sub > li.open {\n  background-color: rgba(0, 0, 0, 0.077); }\n\n.zw-menu-submenu-open > a,\n.menu-wrap .nav .open > a.active,\n.zw-menu-submenu-open > a:focus,\n.zw-menu-submenu-open > a:hover {\n  background-color: rgba(0, 0, 0, 0.077);\n  border-color: rgba(0, 0, 0, 0.077); }\n\n.zw-menu-submenu-active {\n  background-color: rgba(1, 1, 1, 0.077); }\n\n.zw-menu-sub > li a {\n  opacity: 0.85; }\n\n.zw-menu-submenu-open > a,\n.zw-menu li > a:hover {\n  /*opacity: 1;*/\n  /*  color: #fff;*/\n  /*background: #1e282c;*/ }\n\n.zw-menu > ul > li > a,\n.zw-menu > ul > li > a {\n  box-sizing: border-box;\n  border-left: 5px solid transparent; }\n\n.zw-menu > ul > li:hover > a,\n.zw-menu-submenu-open > a {\n  box-sizing: border-box;\n  border-left: 5px solid #3c8dbc !important; }\n\n.zw-menu-white ul,\n.zw-menu-white,\n.zw-menu-white a {\n  color: #555;\n  background-color: #fff; }\n\n.zw-menu-white .zw-menu > ul.nav > li.open > a,\n.zw-menu-white .zw-menu li > a:hover {\n  /*\topacity: 1;*/\n  color: #111;\n  background: #f8f3f3; }\n\n.zw-menu-white .zw-menu-item:hover,\n.zw-menu-white .zw-menu-item > a:hover,\n.zw-menu-white .zw-menu-item:focus,\n.zw-menu-white .zw-menu-sub:hover,\n.zw-menu-white .zw-menu-item-group,\n.zw-menu-white .zw-menu-sub,\n.zw-menu-white .zw-menu-sub:hover,\n.zw-menu-white .zw-menu-item-group:hover,\n.zw-menu-white .zw-menu-sub:focus {\n  color: #2E3E4E;\n  background-color: #fff !important; }\n\n.zw-menu-white .zw-menu-submenu-open > a,\n.zw-menu-white .zw-menu li > a:hover {\n  opacity: 0 !important;\n  color: #8d8d8d;\n  background-color: #fff !important; }\n\n/*************************************************************** misc 杂项 */\n.zw-menu-submenu-open > .zw-menu-sub {\n  display: block;\n  /**z只有当水平的菜单 鼠标移动上去会自动打开**/ }\n\n.zw-menu-sub {\n  display: none;\n  /**默认是隐藏的 当有open的时候打开**/ }\n\n.zw-menu span,\n.zw-menu a {\n  white-space: nowrap;\n  /*overflow: hidden;会导致 如果有下拉弹出框 看不见 被隐藏*/ }\n\n.zw-menu li {\n  list-style-type: none; }\n\n.zw-menu a,\n.zw-menu a:hover,\n.zw-menu a:focus {\n  /*color: white;*/\n  text-decoration: none; }\n\n.zw-menu > ul li > ul {\n  /* transition: height 0.6s; */\n  transition: -webkit-transform 3s ease-in-out 0s;\n  transition: transform 3s ease-in-out 0s;\n  transition: transform 3s ease-in-out 0s, -webkit-transform 3s ease-in-out 0s;\n  display: none; }\n\n/** li **/\n/***展开动画***/\n/***收缩动画****/\n.menu-wrap {\n  transition: width 0.5s;\n  -moz-transition: width 0.5s;\n  -webkit-transition: width 0.5s;\n  -o-transition: width 0.5s; }\n\n.main-wrap {\n  /*margin-left:200px;*/\n  transition: margin-left 0.5s;\n  -moz-transition: margin-left 0.5s;\n  -webkit-transition: margin-left 0.5s;\n  -o-transition: margin-left 0.5s; }\n\n/**收缩**/\n.collapse1 {\n  /** make icon don't get too colose with the left side **/\n  /** when hover the li show the ul  **/\n  /**icon **/ }\n  .collapse1 .zw-menu {\n    width: 100px; }\n  .collapse1 .zw-menu {\n    margin-left: 50px; }\n  .collapse1 .zw-menu > ul > li > ul {\n    margin-left: 0; }\n  .collapse1 .zw-menu-submenu:hover > .zw-menu-sub {\n    display: block !important; }\n  .collapse1 .zw-menu .zw-menu-sub {\n    display: none !important; }\n  .collapse1 ul.zw-menu > li:hover > .zw-menu-sub {\n    display: block !important; }\n  .collapse1 .zw-menu {\n    width: 50px; }\n  .collapse1 .main-wrap {\n    margin-left: 50px; }\n  .collapse1 .logo-desc-text {\n    display: none; }\n  .collapse1 .zw-menu > li > ul {\n    position: absolute;\n    left: 40px;\n    top: 0;\n    diplay: block;\n    width: 150px;\n    z-index: 2000;\n    /*  background-color:  #2E3E4E;*/ }\n  .collapse1 .zw-menu li > a {\n    /*min-height:3.25rem;*/\n    /* text-align:center;*/\n    line-height: 2.25rem;\n    min-height: 2.25rem; }\n\n/**水平菜单区域**/\n.zw-menu-horizontal {\n  /* display:table;\n   flex-wrap: wrap;\n   */\n  min-height: 50px;\n  border-radius: 0;\n  background-color: #2E3E4E;\n  width: 100% !important;\n  text-align: left;\n  padding-top: 5px;\n  line-height: 27px;\n  /* height:$size*2;*/\n  /*  min-height: $size*2.5;\r\n      max-height: $size*2.5;*/\n  box-sizing: border-box;\n  -moz-box-sizing: border-box;\n  /* Firefox */\n  -webkit-box-sizing: border-box;\n  /* Safari */\n  /*  position: relative;\r\n        border-collapse: separate;*/ }\n  .zw-menu-horizontal > li {\n    float: left;\n    padding-left: 15px;\n    /*display:table-cell;*/ }\n    .zw-menu-horizontal > li > a > .zw-menu-item-text {\n      display: block;\n      width: 100%;\n      text-align: center;\n      padding-left: 0; }\n  .zw-menu-horizontal .zw-menu-item {\n    /*float:left;会引起二级菜单异常*/\n    border-bottom: none;\n    margin-left: 1px; }\n  .zw-menu-horizontal .zw-menu-item-text {\n    position: relative;\n    min-width: 50px;\n    padding-right: 0; }\n  .zw-menu-horizontal .zw-menu-sub {\n    position: absolute;\n    min-width: 155px;\n    list-style: none;\n    padding: 0px 0px;\n    margin: 0px 0px 0px;\n    border-radius: 5px;\n    border: none;\n    box-shadow: 0 0px 12px rgba(0, 0, 0, 0.25);\n    text-align: left;\n    z-index: 1000;\n    /*  background-color:  #2E3E4E;*/ }\n    .zw-menu-horizontal .zw-menu-sub a > .zw-icon-arrow {\n      position: absolute;\n      min-width: 155px;\n      list-style: none;\n      padding: 0;\n      /* background-color: $white;*/\n      margin: 0;\n      border-radius: 5px;\n      border: none;\n      /*1px solid #F0F0EE;*/\n      box-shadow: 0 0 12px rgba(0, 0, 0, 0.25);\n      text-align: left;\n      z-index: 1000;\n      position: relative;\n      top: 0;\n      margin-left: 5px;\n      /*如果有子菜单需要缩进*/ }\n  .zw-menu-horizontal .zw-menu-submenu-active .zw-menu-sub {\n    display: block;\n    /**z只有当水平的菜单 鼠标移动上去会自动打开**/ }\n\n.zw-menu-horizontal:after {\n  visibility: hidden;\n  display: block;\n  font-size: 0;\n  content: \" \";\n  clear: both;\n  height: 0; }\n\n.ul-flex {\n  display: -webkit-flex;\n  display: -ms-flexbox;\n  display: flex; }\n  .ul-flex li {\n    -webkit-flex: 1;\n    -ms-flex: 1;\n    flex: 1; }\n\n/***row line **/\n.WB_feed_handle .WB_row_line {\n  border-top-width: 1px;\n  border-top-style: solid; }\n\n.S_line2 {\n  border-color: #f2f2f5; }\n\n.WB_row_line {\n  margin: 0 0 0 -1px;\n  zoom: 1; }\n\n.clearfix {\n  display: block; }\n\nol,\nul {\n  list-style: none; }\n\n.WB_row_r4 li {\n  width: 25%; }\n\n.WB_row_line li {\n  float: left;\n  height: 38px; }\n\n.WB_frame_a,\n.WB_frame_a_fix,\n.WB_frame_b,\n.WB_frame_c,\n.WB_frame_d,\n.WB_main_l,\n.WB_main_c,\n.WB_main_r,\n.WB_main_cr,\n.WB_main_a,\n.WB_toptips {\n  display: inline-block;\n  letter-spacing: normal;\n  word-spacing: normal;\n  vertical-align: top;\n  font-size: 12px; }\n\n.WB_row_line a {\n  display: block;\n  margin: 0 0 0 1px;\n  padding: 1px 0;\n  text-align: center; }\n\n.S_txt2,\n.W_input,\n.W_btn_b_disable,\n.W_btn_b_disable:hover {\n  color: #808080;\n  text-decoration: none; }\n\n.WB_row_line a {\n  display: block;\n  margin: 0 0 0 1px;\n  padding: 1px 0;\n  text-align: center; }\n\n.S_txt2,\n.W_input,\n.W_btn_b_disable,\n.W_btn_b_disable:hover {\n  color: #808080;\n  text-decoration: none; }\n\n.WB_row_line .line {\n  display: block;\n  height: 22px;\n  margin: 7px 0;\n  border-left-width: 1px;\n  border-left-style: solid;\n  line-height: 22px; }\n\n.S_line1,\n.W_btn_prev,\n.W_btn_next,\n.W_btn_b {\n  border-color: #d9d9d9; }\n\n.WB_row_line a {\n  display: block;\n  margin: 0 0 0 1px;\n  padding: 1px 0;\n  text-align: center; }\n\n.S_txt2,\n.W_input,\n.W_btn_b_disable,\n.W_btn_b_disable:hover {\n  color: #808080;\n  text-decoration: none; }\n\n.WB_feed_handle .WB_handle {\n  overflow: hidden; }\n\n.WB_row_line .pos {\n  display: block;\n  margin-left: -1px; }\n", ""]);

// exports


/***/ }),

/***/ 105:
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__(0)(undefined);
// imports


// module
exports.push([module.i, "/*color*/\n/**font-size**/\nh1, h2, p, span {\n  word-wrap: break-word; }\n\n@CHARSET \"UTF-8\";\n.zw-slider-text {\n  height: 50px;\n  line-height: 50px;\n  max-height: 50px;\n  overflow: hidden; }\n", ""]);

// exports


/***/ }),

/***/ 106:
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__(0)(undefined);
// imports


// module
exports.push([module.i, "@charset \"UTF-8\";\n/*color*/\n/**font-size**/\nh1, h2, p, span {\n  word-wrap: break-word; }\n\n@CHARSET \"UTF-8\";\n/** zw-tabs 是外层包裹  加上zw-tabs-top 是说明tab页是在tabpanel上面的zw-tabs-bottom 标明是是在下方的 **/\n/** zw-tabs-bar 是标签页的外部 **/\n/** zw-tabs-nav-container 是标签页的第二层包裹 **/\n/** zw-tabs-nav-wrap 是标签页的第三层包裹 **/\n/** zw-tabs-nav-scroll 是标签页的第三层包裹 **/\n/** zw-tabs-nav 是标签页的第三层包裹 **/\n/** zw-tabs-tab 是标签页的第三层包裹 **/\n/**  zw-tabs-content zw-tabs-content-no-animated **/\n/**  zw-tabs-tabpane zw-tabs-tabpane-inactive **/\n.zw-tabs {\n  font-family: \"Helvetica Neue For Number\", -apple-system, BlinkMacSystemFont, \"Segoe UI\", Roboto, \"PingFang SC\", \"Hiragino Sans GB\", \"Microsoft YaHei\", \"Helvetica Neue\", Helvetica, Arial, sans-serif;\n  line-height: 1.5;\n  color: rgba(0, 0, 0, 0.65);\n  box-sizing: border-box;\n  margin: 0;\n  padding: 0;\n  list-style: none;\n  position: relative;\n  background-color: #fff;\n  border-top: 1px solid #e5e5e5;\n  overflow: hidden;\n  font-size: 12px;\n  zoom: 1; }\n\n.zw-tabs-tab img {\n  width: 15px; }\n\n.zw-tabs-tab {\n  color: gray; }\n\n.zw-tabs-bar {\n  border-bottom: 1px solid #e8e8e8;\n  margin-bottom: 16px;\n  outline: none;\n  transition: padding 0.3s cubic-bezier(0.645, 0.045, 0.355, 1); }\n\n.zw-tabs-bar {\n  border-bottom: 1px solid #e8e8e8;\n  margin-bottom: 16px;\n  outline: none;\n  transition: padding 0.3s cubic-bezier(0.645, 0.045, 0.355, 1); }\n\n.zw-tabs-nav-container {\n  overflow: hidden;\n  line-height: 1.5;\n  box-sizing: border-box;\n  position: relative;\n  white-space: nowrap;\n  margin-bottom: -1px;\n  transition: padding 0.3s cubic-bezier(0.645, 0.045, 0.355, 1);\n  zoom: 1; }\n\n.zw-tabs-nav-wrap {\n  margin-bottom: -1px;\n  overflow: hidden; }\n\n.zw-tabs-nav-scroll {\n  white-space: nowrap;\n  overflow: hidden; }\n\n.zw-tabs-nav {\n  box-sizing: border-box;\n  padding-left: 0;\n  transition: -webkit-transform 0.3s cubic-bezier(0.645, 0.045, 0.355, 1);\n  transition: transform 0.3s cubic-bezier(0.645, 0.045, 0.355, 1);\n  transition: transform 0.3s cubic-bezier(0.645, 0.045, 0.355, 1), -webkit-transform 0.3s cubic-bezier(0.645, 0.045, 0.355, 1);\n  position: relative;\n  margin: 0;\n  list-style: none;\n  display: inline-block; }\n\n.zw-tabs-nav-flex {\n  display: -webkit-flex;\n  display: -ms-flexbox;\n  display: flex;\n  -webkit-justify-content: space-between;\n  -ms-flex-pack: justify;\n  justify-content: space-between; }\n\n.zw-tabs-nav .zw-tabs-tab {\n  display: inline-block;\n  /*height: 100%;*/\n  /*margin-right: 24px;*/\n  box-sizing: border-box;\n  position: relative;\n  cursor: pointer;\n  padding: 8px 20px;\n  transition: color 0.3s cubic-bezier(0.645, 0.045, 0.355, 1);\n  text-decoration: none; }\n\n.zw-tabs-bottom .zw-tabs-ink-bar-animated, .zw-tabs-top .zw-tabs-ink-bar-animated {\n  transition: width 0.3s cubic-bezier(0.645, 0.045, 0.355, 1), -webkit-transform 0.3s cubic-bezier(0.645, 0.045, 0.355, 1);\n  transition: transform 0.3s cubic-bezier(0.645, 0.045, 0.355, 1), width 0.3s cubic-bezier(0.645, 0.045, 0.355, 1);\n  transition: transform 0.3s cubic-bezier(0.645, 0.045, 0.355, 1), width 0.3s cubic-bezier(0.645, 0.045, 0.355, 1), -webkit-transform 0.3s cubic-bezier(0.645, 0.045, 0.355, 1); }\n\n.zw-tabs-ink-bar {\n  z-index: 1;\n  position: absolute;\n  left: 0px;\n  bottom: 1px;\n  box-sizing: border-box;\n  height: 2px;\n  background-color: #108ee9;\n  -webkit-transform-origin: 0px 0px 0px;\n  -ms-transform-origin: 0px 0px 0px;\n  transform-origin: 0px 0px 0px; }\n\n.zw-tabs:not(.zw-tabs-vertical) > .zw-tabs-content > .zw-tabs-tabpane {\n  -ms-flex-negative: 0;\n  -webkit-flex-shrink: 0;\n  flex-shrink: 0;\n  width: 100%;\n  transition: opacity .45s;\n  opacity: 1; }\n\n/** tab pane的切换动画 **/\n.zw-tabs:not(.zw-tabs-vertical) .zw-tabs-content-animated {\n  display: -webkit-flex;\n  display: -ms-flexbox;\n  display: flex;\n  -webkit-flex-direction: row;\n  -ms-flex-direction: row;\n  flex-direction: row;\n  will-change: margin-left;\n  transition: margin-left 0.3s cubic-bezier(0.645, 0.045, 0.355, 1); }\n\n.zw-tabs:not(.zw-tabs-vertical) .zw-tabs-content {\n  width: 100%; }\n\n.zw-tabs:not(.zw-tabs-vertical) .zw-tabs-content .zw-tabs-tabpane {\n  -ms-flex-negative: 0;\n  -webkit-flex-shrink: 0;\n  flex-shrink: 0;\n  width: 100%;\n  transition: opacity .45s;\n  opacity: 1; }\n\n/** 标签页在未选中时候的样式**/\n.zw-tabs.zw-tabs-card .zw-tabs-bar .zw-tabs-tab {\n  margin: 0;\n  border: 1px solid #e8e8e8;\n  border-bottom: 0;\n  border-radius: 4px 4px 0 0;\n  background: #fafafa;\n  margin-right: 2px;\n  padding: 0 16px;\n  transition: all 0.3s cubic-bezier(0.645, 0.045, 0.355, 1);\n  line-height: 38px; }\n\n/** 标签页在选中后的样式 **/\n.zw-tabs.zw-tabs-card > .zw-tabs-bar .zw-tabs-tab-active {\n  background: #fff;\n  border-color: #e8e8e8;\n  color: #fcd70f;\n  padding-bottom: 1px; }\n\n.zw-tabs-tab-active {\n  background: #fff;\n  border-color: #e8e8e8;\n  color: #fcd70f;\n  padding-bottom: 1px; }\n\n.zw-tabs-tab {\n  text-align: center; }\n  .zw-tabs-tab span {\n    /**通过元素 居中 文字的span 是block 实现上线分离 居中对齐 **/\n    display: block; }\n\n.theme-purple-yellow {\n  border-top: 1px solid #292035;\n  color: gray;\n  background-color: #292035; }\n  .theme-purple-yellow .zw-tabs-tab-active {\n    color: yellow;\n    background-color: #292035; }\n  .theme-purple-yellow .zw-tabs-nav {\n    border: 1px solid #292035;\n    background-color: #292035; }\n\n.zw-tabs-has-no-panel .zw-tabs-bar {\n  margin-bottom: 0px; }\n\n.zw-tabs-has-no-panel .zw-tabs-ink-bar {\n  display: none; }\n", ""]);

// exports


/***/ }),

/***/ 107:
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__(0)(undefined);
// imports


// module
exports.push([module.i, "@charset \"UTF-8\";\n/**grid 锟斤拷锟�*/\n/** grid 全锟斤拷锟斤拷式 start **/\n.dot_under_line {\n  border-bottom: 1px dashed  gray; }\n\n.grid {\n  color: #64686D;\n  font-family: \"Microsoft Yahei\",Arial,Helvetica,sans-serif,Verdana,\"Trebuchet MS\";\n  margin-top: 15px;\n  font-size: 12px;\n  overflow-x: scroll;\n  overflow-y: hidden;\n  margin-left: 1px;\n  margin-right: 1px; }\n\ntable {\n  border-spacing: 0px; }\n\n.clearfix:after {\n  visibility: hidden;\n  display: block;\n  font-size: 0;\n  content: \" \";\n  clear: both;\n  height: 0; }\n\n/** cover the evfect of col-sm-8 the margin-left and rgith**/\n.mapper-grid-parent-wrap {\n  /*margin-left:0px;\r\nmargin-right:0px;*/\n  padding-left: 0px;\n  padding-right: 0px; }\n\n/** 确锟斤拷锟斤拷锟斤拷锟斤拷**/\n.grid-content td {\n  overflow: hidden;\n  white-space: nowrap;\n  text-overflow: ellipsis; }\n\n.grid-head th {\n  background-color: #ecf0f1;\n  overflow: hidden;\n  white-space: nowrap;\n  text-overflow: ellipsis; }\n\n.grid-content {\n  overflow-y: scroll;\n  overflow-x: hidden;\n  cell-padding: 0px;\n  border-spacing: 0px;\n  border-collapse: collapse;\n  width: 100%;\n  max-width: 100%;\n  line-height: 1.625; }\n\n.table {\n  table-layout: fixed;\n  margin-bottom: 0px; }\n\n/** grid 全锟斤拷锟斤拷式 end **/\n/**grid head start**/\n.grid-head thead > tr > th {\n  /*border:1px solid #1ab394;\r\n\tbackground-color: #ecf0f1;*/\n  height: 35px;\n  line-height: 1.625;\n  font-weight: bold;\n  font-size: 12px;\n  font-color: #686868;\n  color: #686868;\n  border-bottom: 0px solid #ddd;\n  position: relative;\n  white-space: nowrap;\n  /*  height:35px;*/\n  /*   padding:0px 0px 0px 0px;*/ }\n\n.grid-head thead > tr > th a {\n  display: block;\n  margin: 0px 0px 0px 0px;\n  /* padding:8px 10px;*/\n  cursor: pointer;\n  /* height:28px;*/\n  /*    border-left: 1px solid gray;*/\n  text-decoration: none; }\n\n.grid-head thead > tr > th span {\n  width: 5px;\n  height: 60%;\n  position: absolute;\n  right: 0px;\n  top: 20%;\n  border-left: 1px solid gray; }\n\n.grid-content tr > th:first-child {\n  /* border-left:1px solid #DDD; */ }\n\n/**grid head end**/\n/**grid body start**/\n.grid-content tr:hover {\n  background-color: #fcfcfc; }\n\n.grid-content td {\n  font-size: 12px;\n  padding-left: 15px;\n  border: 0px none none;\n  /* \tborder-top: 1px solid #DDD; */\n  height: 35px;\n  vertical-align: middle;\n  /* text-align:center; */\n  line-height: 1.625; }\n\n.grid-content tr td {\n  border: 0px solid red; }\n\n.grid-content tr:first-child td {\n  border-top: 0px solid gray; }\n\n.grid-content tr:first-child {\n  border-top: 0px solid gray; }\n\n.grid-content tr:last-child {\n  border-bottom: 0px solid gray; }\n\n.grid-content tr > th {\n  padding-left: 1px;\n  padding-right: 1px; }\n\n/* .table tr:last-child th{\r\n\tborder-bottom: 1px  ;\r\n} */\n.grid-content {\n  border-top: 0px solid gray; }\n\n.grid-content button:hover {\n  background-color: #5296BA; }\n\n.grid-content button {\n  display: inline-block;\n  font-family: \"SimSun\";\n  text-decoration: none;\n  text-align: center;\n  background-color: #4184A8;\n  color: #FFF;\n  border-radius: 3px; }\n\n.grid-content td img {\n  max-height: 1.625rem; }\n\n.grid-content td a {\n  cursor: pointer;\n  display: inline-block;\n  background-color: #1ab394;\n  text-decoration: none;\n  padding: 0px 3px;\n  color: #fff;\n  padding: 2px 5px;\n  margin-left: 1px;\n  /*border: 1px solid red;*/\n  border: 0px solid #ddd;\n  border-radius: 2px;\n  /*  border: 1px solid #979797;*/\n  /*border: 2px solid #1ab394;*/ }\n\n.grid-content td a:hover {\n  /* border: 2px solid transparent;\r\n    background-color: rgba(238,238,238,0.1); */\n  /*background-color: #EEE;\r\n    border-color: #DDD;\r\n        */\n  background: #1ab394;\n  border-color: #1ab394;\n  color: #fff;\n  /*   filter:alpah(opacity=50);\r\n    -moz-opacity:0.5;opacity:0.5; */ }\n\n.grid-content button {\n  display: inline-block;\n  font-family: \"SimSun\";\n  text-decoration: none;\n  text-align: center;\n  background-color: #4184A8;\n  color: #FFF;\n  border-radius: 3px;\n  white-space: nowrap; }\n\n.grid-content button[disabled] {\n  background-color: #9b9b9b; }\n\n.grid-content .selected {\n  background-color: #ecf0f1 !important; }\n\n/* .table>tbody>tr>td {\r\n\r\n} */\n/**grid body end**/\n/**pagination**/\n.pager {\n  height: auto; }\n\n.pager li span {\n  display: block;\n  /* border: 0px solid #1ab394;*/\n  padding: 5px 10px;\n  color: #ccc; }\n\n.pager li .curr {\n  display: block;\n  /* border: 0px solid #1ab394;*/\n  border: 0px solid #1ab394;\n  padding: 5px 10px;\n  background-color: #1ab394;\n  color: white; }\n\n.pager li .curr:focus, .pager li .curr:hover {\n  display: block;\n  /* border: 0px solid #1ab394;*/\n  border: 0px solid #1ab394;\n  padding: 5px 10px;\n  background-color: #1ab394;\n  color: white; }\n\n.pagination > li > a, .pagination > li > span {\n  display: block;\n  /* border: 2px solid #1ab394;*/\n  padding: 0 8px; }\n\n/*\r\n.nav{\r\n\ttext-align:center;\r\n\talign:center;\r\n\tline-height:1.6;\r\n}\r\n.nav::after {\r\n    content: \".\";\r\n    display: block;\r\n    height: 0px;\r\n    clear: both;\r\n    visibility: hidden;\r\n}*/\n/** pre next 's boxing*/\n.pagination {\n  margin: auto 0px;\n  display: inline-block;\n  padding-left: 0;\n  margin: 20px 0;\n  border-radius: 4px; }\n\n.pagination > li {\n  display: inline-block; }\n\n.pagination > li > a, .pagination > li > span {\n  position: relative;\n  float: left;\n  margin-left: -1px;\n  line-height: 1.42857143;\n  text-decoration: none;\n  margin-left: 15px;\n  font-size: 18px;\n  padding: 5px 10px;\n  font-size: 12px;\n  line-height: 1.3333333;\n  border-top-left-radius: 1px;\n  border-bottom-left-radius: 1px;\n  border-top-right-radius: 1px;\n  border-bottom-right-radius: 1px; }\n\n.pagination > li:first-child > a, .pagination > li:first-child > span {\n  margin-left: 0; }\n\n.pagination > li > span:focus, .pagination > li > span:hover {\n  background-color: #fff;\n  text-align: center;\n  border: 1px solid #ccc;\n  color: #ccc; }\n\n.pagination > li > a {\n  /*    background:#cecece;\r\n    border-color: #cecece;*/\n  color: #64686D; }\n\n/**page hover color & backgroud color 分页按钮hover的颜色 **/\n.pagination > li > a:focus, .pagination > li > a:hover {\n  z-index: 3;\n  /*    background:#1ab394;\r\n    border-color: #1ab394;*/\n  color: #64686D; }\n\n.commondity_pic {\n  /* background:url('http://content.battlenet.com.cn/wow/icons/36/inv_misc_food_70.jpg') center no-repeat ;*/\n  width: 36px;\n  height: 36px;\n  border-radius: 3px;\n  border: 1px solid #434445;\n  display: block;\n  border-color: #B1B2B4 #434445 #2F3032;\n  box-shadow: 0px 0px 2px #000;\n  /*margin:1px 1px 1px 1px;*/\n  padding: 1px 1px 1px 1px; }\n\n.pagination > .active > a, .pagination > .active > a:focus, .pagination > .active > a:hover, .pagination > .active > span, .pagination > .active > span:focus, .pagination > .active > span:hover {\n  z-index: 2;\n  color: #fff;\n  cursor: default; }\n\n.pagination > .disabled > a, .pagination > .disabled > a:focus, .pagination > .disabled > a:hover, .pagination > .disabled > span, .pagination > .disabled > span:focus, .pagination > .disabled > span:hover {\n  color: #777;\n  cursor: not-allowed; }\n\n.pagination > li:first-child > a, .pagination > li:first-child > span {\n  margin-left: 0px;\n  border-top-left-radius: 1px;\n  border-bottom-left-radius: 1px; }\n\n.pagination-sm > li:first-child > a, .pagination-sm > li:first-child > span {\n  border-top-left-radius: 1px;\n  border-bottom-left-radius: 1px; }\n\n.pagination-sm > li:first-child > a, .pagination-sm > li:first-child > span {\n  border-top-right-radius: 1px;\n  border-bottom-right-radius: 1px; }\n\n.pagination-sm > li:last-child > a, .pagination-sm > li:last-child > span {\n  border-top-right-radius: 1px;\n  border-bottom-right-radius: 1px; }\n\n.ui-icon {\n  display: block;\n  width: 50px;\n  height: 20px;\n  color: #333;\n  background-color: #fff;\n  text-align: center;\n  border: 1px solid #ccc; }\n\n.ui-icon:focus, .ui-icon:hover {\n  color: #333;\n  background-color: #e6e6e6;\n  border-color: #adadad; }\n\n.ui-icon-seek-first:after {\n  content: \"\\7B2C\\4E00\\9875\"; }\n\n.ui-icon-seek-prev:after {\n  content: \"\\4E0A\\4E00\\9875\"; }\n\n.ui-icon-seek-next:after {\n  content: \"\\4E0B\\4E00\\9875\"; }\n\n.ui-icon-seek-end:after {\n  content: \"\\6700\\540E\\9875\"; }\n\n.ui-jqgrid .ui-pg-input {\n  height: 20px; }\n\n.ui-jqgrid tr.jqgrow td {\n  height: 2.5rem; }\n\n.ui-jqgrid tr.ui-row-ltr td {\n  border-right-width: 0px; }\n\n.ui-jqgrid .ui-jqgrid-htable th div {\n  height: 2.5rem;\n  /*line-height:2.5rem;*/\n  padding-top: 0.6rem; }\n\n.ui-jqgrid .ui-jqgrid-view {\n  font-size: 1rem;\n  color: gray; }\n", ""]);

// exports


/***/ }),

/***/ 108:
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__(0)(undefined);
// imports


// module
exports.push([module.i, "/*color*/\n/**font-size**/\nh1, h2, p, span {\n  word-wrap: break-word; }\n\n@CHARSET \"UTF-8\";\n.zwindow {\n  position: fixed;\n  width: 100%;\n  left: 0px;\n  z-index: 1000;\n  background-color: white; }\n\n.zwidget_wrap {\n  position: fixed;\n  min-width: 200px;\n  max-width: 70%;\n  background-color: white;\n  top: 50%;\n  left: 10%;\n  margin-top: -75px;\n  /*\r\n\tleft:50%;\r\n\tmargin-left:-150px;\r\n\r\n   -webkit-box-shadow: 0px 0px 4px;\r\n  -moz-box-shadow: 0px 0px 4px;\r\n  box-shadow: 0px 0px 4px;\r\n*/\n  z-index: 23000;\n  -webkit-animation-name: bounceIn;\n  animation-name: bounceIn;\n  border-radius: 2px;\n  -webkit-animation-fill-mode: both;\n  animation-fill-mode: both;\n  -webkit-animation-duration: .3s;\n  animation-duration: .3s;\n  background-color: #fff;\n  -webkit-background-clip: content;\n  box-shadow: 1px 1px 50px rgba(0, 0, 0, 0.3); }\n\n.zwidget_hd {\n  /*\tpadding:5px 5px 5px 15px;\r\n\tdiplay:relative;\r\n\tfont-weight:normal;\r\n\tbackground-color:rgb(238,238,238);\r\n\tcolor:gray;\r\n\tfont-size:12px;*/\n  padding: 0 10px 0 20px;\n  height: 42px;\n  line-height: 42px;\n  border-bottom: 1px solid #eee;\n  font-size: 14px;\n  color: #333;\n  overflow: hidden;\n  text-overflow: ellipsis;\n  white-space: nowrap;\n  background-color: #F8F8F8;\n  border-radius: 2px 2px 0 0;\n  cursor: move; }\n\n.zwidget_bd {\n  position: relative;\n  padding: 20px;\n  line-height: 24px;\n  word-break: break-all;\n  overflow: hidden;\n  font-size: 14px;\n  overflow: auto; }\n\n.zwidget_hd img {\n  width: 25px; }\n\n.zclose {\n  float: right;\n  cursor: pointer;\n  margin-top: 12px;\n  margin-right: 1px;\n  /* border-radius:15px;\r\n\tbackground-color:#d3d3d3; */\n  color: #000;\n  width: 20px;\n  align: center;\n  font-weight: 23;\n  text-align: center;\n  font-family: '\\5FAE\\8F6F\\96C5\\9ED1';\n  font-size: 14px;\n  font-style: normal; }\n\n.zclose:hover {\n  color: gray; }\n\n.zinfo-icon {\n  text-align: center;\n  margin-top: 15px;\n  font-size: 10px; }\n\n.zinfo {\n  text-align: center;\n  margin-top: 5px;\n  margin-bottom: 5px;\n  font-size: 14px; }\n\n.zinfo * {\n  width: 95%;\n  color: #777777; }\n\n.zbutton_wrap {\n  postion: relative;\n  text-align: center; }\n\n.zwidget_wrap .zwidget_ft {\n  padding-left: 5px;\n  padding-right: 5px;\n  padding-bottom: 15px; }\n\n.zwidget_ft {\n  text-align: right; }\n\n.zbutton_wrap .btn {\n  height: 28px;\n  line-height: 28px;\n  margin: 0 6px;\n  padding: 0 15px;\n  border: 1px #dedede solid;\n  background-color: #f1f1f1;\n  color: #333;\n  border-radius: 2px;\n  font-weight: 400;\n  cursor: pointer;\n  text-decoration: none;\n  border-color: #4898d5;\n  background-color: #2e8ded;\n  color: #fff; }\n\n.zinfo-icon {\n  display: inline; }\n\n.zinfo-icon .fa {\n  color: #3ebd59; }\n\n/*\r\nbounceIn{0%{opacity:0;-webkit-transform:scale(.5);transform:scale(.5)}100%{opacity:1;-webkit-transform:scale(1);transform:scale(1)}}*/\n@-webkit-keyframes bounceIn {\n  0% {\n    opacity: 0;\n    -webkit-transform: scale(0.5);\n    -ms-transform: scale(0.5);\n    transform: scale(0.5); }\n  100% {\n    opacity: 1;\n    -webkit-transform: scale(1);\n    -ms-transform: scale(1);\n    transform: scale(1); } }\n\n@keyframes bounceIn {\n  0% {\n    opacity: 0;\n    -webkit-transform: scale(0.5);\n    -ms-transform: scale(0.5);\n    transform: scale(0.5); }\n  100% {\n    opacity: 1;\n    -webkit-transform: scale(1);\n    -ms-transform: scale(1);\n    transform: scale(1); } }\n\n.widget {\n  display: none; }\n\n.zbutton_wrap .btn {\n  background-color: #fcd70f !important;\n  color: black !important; }\n\n#zpromptText {\n  line-height: 24px; }\n\n.zwidget_wrap.tips {\n  background-color: rgba(0, 0, 0, 0.5); }\n  .zwidget_wrap.tips .zinfo {\n    color: #fff; }\n  .zwidget_wrap.tips .zwidget_ft {\n    display: none; }\n", ""]);

// exports


/***/ }),

/***/ 113:
/***/ (function(module, exports, __webpack_require__) {

module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', {
    staticClass: "zw-form-item form-group clearfix"
  }, [(_vm.label) ? _c('div', {
    class: _vm.labelWrapper
  }, [_c('label', [_vm._v(_vm._s(_vm.label))]), _vm._v(":\n  ")]) : _vm._e(), _vm._v(" "), _c('div', {
    class: _vm.controlWrapper
  }, [_c('div', {
    staticClass: "zw-form-item-control"
  }, [_c('div', {
    staticClass: "zw-form-item-children"
  }, [_vm._t("default")], 2)])])])
},staticRenderFns: []}
module.exports.render._withStripped = true
if (false) {
  module.hot.accept()
  if (module.hot.data) {
     require("vue-hot-reload-api").rerender("data-v-02709eaa", module.exports)
  }
}

/***/ }),

/***/ 114:
/***/ (function(module, exports, __webpack_require__) {

module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('form', {
    class: _vm.getComputeClass
  }, [_vm._t("default")], 2)
},staticRenderFns: []}
module.exports.render._withStripped = true
if (false) {
  module.hot.accept()
  if (module.hot.data) {
     require("vue-hot-reload-api").rerender("data-v-0524c2f8", module.exports)
  }
}

/***/ }),

/***/ 115:
/***/ (function(module, exports, __webpack_require__) {

module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', {
    ref: "slot",
    staticClass: "clearfix zw-row"
  }, [_vm._t("default", null, {
    gutter: 6
  })], 2)
},staticRenderFns: []}
module.exports.render._withStripped = true
if (false) {
  module.hot.accept()
  if (module.hot.data) {
     require("vue-hot-reload-api").rerender("data-v-5dacc2b0", module.exports)
  }
}

/***/ }),

/***/ 116:
/***/ (function(module, exports, __webpack_require__) {

module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', {
    class: _vm.classes,
    style: (_vm.styles)
  }, [_vm._t("default")], 2)
},staticRenderFns: []}
module.exports.render._withStripped = true
if (false) {
  module.hot.accept()
  if (module.hot.data) {
     require("vue-hot-reload-api").rerender("data-v-91ddb964", module.exports)
  }
}

/***/ }),

/***/ 117:
/***/ (function(module, exports, __webpack_require__) {

module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('i', {
    class: _vm.classes
  })
},staticRenderFns: []}
module.exports.render._withStripped = true
if (false) {
  module.hot.accept()
  if (module.hot.data) {
     require("vue-hot-reload-api").rerender("data-v-aa4ed928", module.exports)
  }
}

/***/ }),

/***/ 118:
/***/ (function(module, exports, __webpack_require__) {

module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('button', {
    staticClass: "btn",
    class: _vm.classes,
    attrs: {
      "type": "button",
      "disabled": _vm.isDisabled
    },
    on: {
      "click": _vm.diabledForAWhile
    }
  }, [(_vm.iconShow) ? _c('i', {
    class: _vm.iconclasses
  }) : _vm._e(), _vm._v(" "), (_vm.hasText) ? _c('span', {
    ref: "slot",
    staticClass: "zw-btn-text"
  }, [_vm._t("default")], 2) : _vm._e(), _vm._v(_vm._s(this.coolDown > 0 ? (this.coolDown + 's') : "") + " " + _vm._s(_vm.text))])
},staticRenderFns: []}
module.exports.render._withStripped = true
if (false) {
  module.hot.accept()
  if (module.hot.data) {
     require("vue-hot-reload-api").rerender("data-v-b9a5e268", module.exports)
  }
}

/***/ }),

/***/ 119:
/***/ (function(module, exports, __webpack_require__) {

module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('span', {
    staticStyle: {
      "display": "inline"
    }
  }, [_c('span', {
    class: _vm.groupWrapperClasses
  }, [_c('span', {
    class: _vm.wrapperClasses
  }, [(_vm.icon) ? _c('span', {
    staticClass: "zw-input-prefix .zw-input-group-addon"
  }, [(_vm.icon) ? _c('zwIcon', {
    attrs: {
      "type": _vm.icon
    }
  }) : _vm._e()], 1) : _vm._e(), _vm._v(" "), (this.$slots.prepend) ? _c('span', {
    staticClass: "zw-input-group-prepend .zw-input-group-addon",
    style: (_vm.getComputedHeight)
  }, [_vm._t("prepend")], 2) : _vm._e(), _vm._v(" "), (this.$slots.prefix) ? _c('span', {
    staticClass: "zw-input-prefix ",
    style: (_vm.getComputedHeight)
  }, [_vm._t("prefix")], 2) : _vm._e(), _vm._v(" "), _c('input', {
    ref: "text",
    staticClass: "zw-input",
    style: (_vm.getComputedHeight),
    attrs: {
      "id": _vm.id,
      "name": _vm.name,
      "placeholder": _vm.placeholder,
      "type": _vm.type
    },
    domProps: {
      "value": _vm.value
    },
    on: {
      "input": _vm.handleInput,
      "change": _vm.onchange
    }
  }), _vm._v(" "), (this.closeSee) ? _c('span', {
    staticClass: "zw-input-suffix ",
    style: (_vm.getComputedHeight),
    on: {
      "click": _vm.clearContent
    }
  }, [_c('zwIcon', {
    attrs: {
      "name": "suffix",
      "type": "close"
    }
  })], 1) : _vm._e(), _vm._v(" "), (this.$slots.suffix) ? _c('span', {
    staticClass: "zw-input-suffix ",
    style: (_vm.getComputedHeight)
  }, [_vm._t("suffix")], 2) : _vm._e(), _vm._v(" "), (this.$slots.append) ? _c('span', {
    staticClass: "zw-input-group-append zw-input-group-addon",
    style: (_vm.getComputedHeight)
  }, [_vm._t("append")], 2) : _vm._e()])])])
},staticRenderFns: []}
module.exports.render._withStripped = true
if (false) {
  module.hot.accept()
  if (module.hot.data) {
     require("vue-hot-reload-api").rerender("data-v-c8187974", module.exports)
  }
}

/***/ }),

/***/ 120:
/***/ (function(module, exports, __webpack_require__) {

// style-loader: Adds some css to the DOM by adding a <style> tag

// load the styles
var content = __webpack_require__(94);
if(typeof content === 'string') content = [[module.i, content, '']];
if(content.locals) module.exports = content.locals;
// add the styles to the DOM
var update = __webpack_require__(1)("5334ab4f", content, false);
// Hot Module Replacement
if(false) {
 // When the styles change, update the <style> tags
 if(!content.locals) {
   module.hot.accept("!!../../../node_modules/css-loader/index.js!../../../node_modules/vue-loader/lib/style-compiler/index.js?{\"vue\":true,\"id\":\"data-v-02709eaa\",\"scoped\":false,\"hasInlineConfig\":false}!../../../node_modules/vue-loader/lib/selector.js?type=styles&index=0!./zwFormItem.vue", function() {
     var newContent = require("!!../../../node_modules/css-loader/index.js!../../../node_modules/vue-loader/lib/style-compiler/index.js?{\"vue\":true,\"id\":\"data-v-02709eaa\",\"scoped\":false,\"hasInlineConfig\":false}!../../../node_modules/vue-loader/lib/selector.js?type=styles&index=0!./zwFormItem.vue");
     if(typeof newContent === 'string') newContent = [[module.id, newContent, '']];
     update(newContent);
   });
 }
 // When the module is disposed, remove the <style> tags
 module.hot.dispose(function() { update(); });
}

/***/ }),

/***/ 121:
/***/ (function(module, exports, __webpack_require__) {

// style-loader: Adds some css to the DOM by adding a <style> tag

// load the styles
var content = __webpack_require__(95);
if(typeof content === 'string') content = [[module.i, content, '']];
if(content.locals) module.exports = content.locals;
// add the styles to the DOM
var update = __webpack_require__(1)("d0992cfe", content, false);
// Hot Module Replacement
if(false) {
 // When the styles change, update the <style> tags
 if(!content.locals) {
   module.hot.accept("!!../../../node_modules/css-loader/index.js!../../../node_modules/vue-loader/lib/style-compiler/index.js?{\"vue\":true,\"id\":\"data-v-0524c2f8\",\"scoped\":false,\"hasInlineConfig\":false}!../../../node_modules/vue-loader/lib/selector.js?type=styles&index=0!./zwForm.vue", function() {
     var newContent = require("!!../../../node_modules/css-loader/index.js!../../../node_modules/vue-loader/lib/style-compiler/index.js?{\"vue\":true,\"id\":\"data-v-0524c2f8\",\"scoped\":false,\"hasInlineConfig\":false}!../../../node_modules/vue-loader/lib/selector.js?type=styles&index=0!./zwForm.vue");
     if(typeof newContent === 'string') newContent = [[module.id, newContent, '']];
     update(newContent);
   });
 }
 // When the module is disposed, remove the <style> tags
 module.hot.dispose(function() { update(); });
}

/***/ }),

/***/ 122:
/***/ (function(module, exports, __webpack_require__) {

// style-loader: Adds some css to the DOM by adding a <style> tag

// load the styles
var content = __webpack_require__(96);
if(typeof content === 'string') content = [[module.i, content, '']];
if(content.locals) module.exports = content.locals;
// add the styles to the DOM
var update = __webpack_require__(1)("2598e638", content, false);
// Hot Module Replacement
if(false) {
 // When the styles change, update the <style> tags
 if(!content.locals) {
   module.hot.accept("!!../../../node_modules/css-loader/index.js!../../../node_modules/vue-loader/lib/style-compiler/index.js?{\"vue\":true,\"id\":\"data-v-c8187974\",\"scoped\":false,\"hasInlineConfig\":false}!../../../node_modules/vue-loader/lib/selector.js?type=styles&index=0!./zwInput.vue", function() {
     var newContent = require("!!../../../node_modules/css-loader/index.js!../../../node_modules/vue-loader/lib/style-compiler/index.js?{\"vue\":true,\"id\":\"data-v-c8187974\",\"scoped\":false,\"hasInlineConfig\":false}!../../../node_modules/vue-loader/lib/selector.js?type=styles&index=0!./zwInput.vue");
     if(typeof newContent === 'string') newContent = [[module.id, newContent, '']];
     update(newContent);
   });
 }
 // When the module is disposed, remove the <style> tags
 module.hot.dispose(function() { update(); });
}

/***/ }),

/***/ 123:
/***/ (function(module, exports, __webpack_require__) {

var disposed = false
function injectStyle (ssrContext) {
  if (disposed) return
  __webpack_require__(268)
}
var Component = __webpack_require__(2)(
  /* script */
  __webpack_require__(175),
  /* template */
  __webpack_require__(235),
  /* styles */
  injectStyle,
  /* scopeId */
  "data-v-40eb3cef",
  /* moduleIdentifier (server only) */
  null
)
Component.options.__file = "G:\\workspace\\dozenx\\ui\\webpack\\app\\module\\phone\\userInfo.vue"
if (Component.esModule && Object.keys(Component.esModule).some(function (key) {return key !== "default" && key.substr(0, 2) !== "__"})) {console.error("named exports are not supported in *.vue files.")}
if (Component.options.functional) {console.error("[vue-loader] userInfo.vue: functional components are not supported with templates, they should use render functions.")}

/* hot reload */
if (false) {(function () {
  var hotAPI = require("vue-hot-reload-api")
  hotAPI.install(require("vue"), false)
  if (!hotAPI.compatible) return
  module.hot.accept()
  if (!module.hot.data) {
    hotAPI.createRecord("data-v-40eb3cef", Component.options)
  } else {
    hotAPI.reload("data-v-40eb3cef", Component.options)
  }
  module.hot.dispose(function (data) {
    disposed = true
  })
})()}

module.exports = Component.exports


/***/ }),

/***/ 14:
/***/ (function(module, exports, __webpack_require__) {

var disposed = false
function injectStyle (ssrContext) {
  if (disposed) return
  __webpack_require__(122)
}
var Component = __webpack_require__(2)(
  /* script */
  __webpack_require__(89),
  /* template */
  __webpack_require__(119),
  /* styles */
  injectStyle,
  /* scopeId */
  null,
  /* moduleIdentifier (server only) */
  null
)
Component.options.__file = "G:\\workspace\\dozenx\\ui\\webpack\\app\\component\\dataentry\\zwInput.vue"
if (Component.esModule && Object.keys(Component.esModule).some(function (key) {return key !== "default" && key.substr(0, 2) !== "__"})) {console.error("named exports are not supported in *.vue files.")}
if (Component.options.functional) {console.error("[vue-loader] zwInput.vue: functional components are not supported with templates, they should use render functions.")}

/* hot reload */
if (false) {(function () {
  var hotAPI = require("vue-hot-reload-api")
  hotAPI.install(require("vue"), false)
  if (!hotAPI.compatible) return
  module.hot.accept()
  if (!module.hot.data) {
    hotAPI.createRecord("data-v-c8187974", Component.options)
  } else {
    hotAPI.reload("data-v-c8187974", Component.options)
  }
  module.hot.dispose(function (data) {
    disposed = true
  })
})()}

module.exports = Component.exports


/***/ }),

/***/ 144:
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
        value: true
});

var _zwIcon = __webpack_require__(3);

var _zwIcon2 = _interopRequireDefault(_zwIcon);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

exports.default = {
        name: 'zwAppTitle',
        components: { zwIcon: _zwIcon2.default },
        props: [],
        data: function data() {
                return {};
        },

        computed: {},
        mounted: function mounted() {},

        methods: {},

        events: {}
}; //
//
//
//
//
//
//

/***/ }),

/***/ 146:
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
    value: true
});

var _zwIcon = __webpack_require__(3);

var _zwIcon2 = _interopRequireDefault(_zwIcon);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

exports.default = {
    name: 'zwChocolatePanel',
    components: { zwIcon: _zwIcon2.default },
    props: ["data", "rowNum"] //"tabs"

    , data: function data() {
        return {};
    },

    computed: {
        getCls: function getCls() {
            var size = this.rowNum || this.data.length;
            return " dib ma0 pb3 w-grid-" + size + " w-grid-4-m w-grid-" + size + "-l w-grid-" + size + "-xl ";
        }
    },
    mounted: function mounted() {},

    methods: {
        goLink: function goLink(url) {
            if (url) {
                window.location = url;
            }
        }

    },

    events: {}
}; //
//
//
//
//
//
//
//
//
//
//
//
//

/***/ }),

/***/ 151:
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
        value: true
});

var _zwIcon = __webpack_require__(3);

var _zwIcon2 = _interopRequireDefault(_zwIcon);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

exports.default = {
        name: 'zwTabPanel',
        components: { zwIcon: _zwIcon2.default },
        props: [], //"tab","indexkey"
        data: function data() {
                return {
                        collapse: true
                };
        },

        computed: {},
        mounted: function mounted() {},

        methods: {
                clickFunction: function clickFunction() {
                        alert(123);
                        this.$emit("clickFn", "");
                }
        },

        events: {}
}; //
//
//
//
//
//

/***/ }),

/***/ 153:
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
    value: true
});

var _zwIcon = __webpack_require__(3);

var _zwIcon2 = _interopRequireDefault(_zwIcon);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

exports.default = {
    name: 'zwTabs',
    components: { zwIcon: _zwIcon2.default },
    props: ["type", "theme", "panel", "defaultIndex"] //"tabs"

    , data: function data() {
        return {
            //ewaiClass:'',
            selectedTabIndex: 0,
            tabDatas: [],
            collapse: true
        };
    },

    computed: {
        getMainClass: function getMainClass() {

            var classes = "";
            if (this.type == "card") {
                classes += 'zw-tabs-card';
            }
            if (this.theme == "purple_yellow") {
                classes += ' theme-purple-yellow';
            }
            if (this.panel == "no") {
                classes += ' zw-tabs-has-no-panel';
            }
            return classes;
        },
        ewaiClass: function ewaiClass() {
            var str = 'zw-tabs zw-tabs-top zw-tabs-line ';

            if (this.type == 'card') {
                str += 'zw-tabs-card';
            }
            return str;
        }
    },
    mounted: function mounted() {
        if (this.defaultIndex) {
            this.selectedTabIndex = this.defaultIndex;
        }

        /*   if(this.type=='card'){
                              ewaiClass=  'zw-tabs-card';
                          }*/

        //console.log(this.$slots.default);
        var objs = this.$slots.default;
        for (var i = 0; i < objs.length; i++) {
            if (objs[i].data) {

                console.log("icon:" + objs[i].data.attrs["icon"]);

                this.tabDatas.push({
                    name: objs[i].data.attrs["tab"],

                    tabIndex: objs[i].data.attrs["tabIndex"],
                    icon: objs[i].data.attrs["icon"],
                    img: objs[i].data.attrs["img"],
                    img2: objs[i].data.attrs["img2"]
                });
            }
        }
    },

    methods: {
        tabUnderLineMarginLeft: function tabUnderLineMarginLeft() {
            if (this.panel == "no") {
                return 'display:none';
            }
            if (this.type == 'card') {
                return '';
            }
            return "display: block; width: 73px;transform: translate3d(" + this.selectedTabIndex * 96 + "px, 0px, 0px);";
        },
        getAppBottomClass: function getAppBottomClass() {
            if (this.type == 'appbottom') {

                return "zw-tabs-nav-flex";
            }
            return "";
        },

        marginLeft: function marginLeft() {
            return "margin-left: -" + this.selectedTabIndex * 100 + "%;";
        },

        changeTab: function changeTab(tabIndex) {

            this.selectedTabIndex = tabIndex - 1;
            this.$emit("changeTab", this.selectedTabIndex);
        }

    },

    render: function render(createElement) {},
    events: {}
}; //
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//

/***/ }),

/***/ 155:
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
  value: true
});

var _zwIcon = __webpack_require__(3);

var _zwIcon2 = _interopRequireDefault(_zwIcon);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

exports.default = {
  name: 'zwInput',
  components: {
    zwIcon: _zwIcon2.default
  },
  props: { value: { type: String },
    img: { type: String } },
  data: function data() {
    return {
      ok: false,
      textvalue: "",
      closeSee: false,
      imgDom: null, //回显的image的id
      maxHeight: null, //预设的最大高度
      maxWidth: null, //预设的最大宽度
      postUrl: null, //提交的url"/calendar/image/upload.json"
      preShow: false,
      callback: null,
      fileInput: null
    };
  },

  computed: {},
  mounted: function mounted() {

    this.init({});
  },

  methods: {
    getPathValue: function (_getPathValue) {
      function getPathValue(_x) {
        return _getPathValue.apply(this, arguments);
      }

      getPathValue.toString = function () {
        return _getPathValue.toString();
      };

      return getPathValue;
    }(function (value) {
      return getPathValue(value);
    }),
    fileChange: function fileChange(e) {
      var f = e.files[0]; //一次只上传1个文件，其实可以上传多个的
      var FR = new FileReader();
      var _this = this;

      FR.onload = function (f) {

        _this.compressImg(this.result, 300, function (data, originalWidth, originalHeight) {
          console.log("压缩完成后执行的callback");
          if (_this.preShow) {
            console.log("img pre show");
            _this.imgDom.src = data;
          }
          console.log("begin send img");
          var json = {};
          data = "+" + data; //.substring(22);
          json.imageData = encodeURIComponent(data);
          console.log("begin post");
          Ajax.post(_this.postUrl, json, function (data) {
            console.log("ajax return");
            if (_this.callback != null) _this.callback(data);
          });
        });
      };
      FR.readAsDataURL(f); //先注册onload，再读取文件内容，否则读取内容是空的
    },
    compressImg: function compressImg(imgData, maxHeight, onCompress) {
      var _this = this;
      if (!imgData) return;
      onCompress = onCompress || function () {};
      maxHeight = maxHeight || this.maxHeight; //默认最大高度200px
      var canvas = document.createElement('canvas');
      var img = new Image();
      console.log("maxHeight:" + maxHeight);
      img.onload = function () {
        if (img.height > maxHeight) {
          //按最大高度等比缩放
          img.width *= maxHeight / img.height;
          img.height = maxHeight;
        }
        canvas.width = img.width;
        canvas.height = img.height;
        var ctx = canvas.getContext("2d");
        ctx.clearRect(0, 0, canvas.width, canvas.height); // canvas清屏
        //重置canvans宽高 canvas.width = img.width; canvas.height = img.height;
        console.log("width:" + img.width + "height:" + img.height);
        ctx.drawImage(img, 0, 0, img.width, img.height); // 将图像绘制到canvas上
        console.log("begin compress img");
        onCompress(canvas.toDataURL("image/png"), img.width, img.height); //必须等压缩完才读取canvas值，否则canvas内容是黑帆布
      };
      // 记住必须先绑定事件，才能设置src属性，否则img没内容可以画到canvas
      console.log("begin origin data load:");
      img.src = imgData;
    },
    init: function init(jso) {
      this.imgDom = this.$refs.img; //parseDom("<img class=\" img-upload\" alt=\"请上传图片\"></img>");
      this.maxHeight = jso.maxHeight || 633;
      this.maxWidth = jso.maxWidth || 300;
      this.postUrl = jso.postUrl || PATH + "/image/upload.json";
      var that = this;
      this.callback = jso.callback || function (result) {
        //that.src=result.data;
        that.$emit('input', result.data); //触发 input 事件，并传入新值
        // that.$emit('inputChange', result.data);
        that.value = result.data;
        // that.img=result.data;
      };
      this.fileInput = this.$refs.input; //parseDom("<input type=\"file\" style=\"display:none\"/>");
      this.fileInput.addEventListener("change", function () {
        that.fileChange(this);
      });
      this.imgDom.addEventListener("click", function () {
        var evt = new MouseEvent("click", { bubbles: false, cancelable: true, view: window });
        that.fileInput.dispatchEvent(evt);
      });
    },
    handleInput: function handleInput(event) {
      var value = event.target.value;
      this.$emit('input', value); //触发 input 事件，并传入新值
    },

    onchange: function onchange() {
      console.log("onchange");
      this.$emit('onchange', this.textvalue);
    }

  },
  watch: {},

  events: {}
}; //
//
//
//
//
//
//

/***/ }),

/***/ 171:
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
        value: true
});

var _zwBase = __webpack_require__(35);

var _zwBase2 = _interopRequireDefault(_zwBase);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

exports.default = {
        extends: _zwBase2.default, //继承组件

        name: 'zwAppTopBar',
        components: { zwBase: _zwBase2.default },
        props: [],
        data: function data() {
                return {};
        },

        computed: {},
        mounted: function mounted() {},

        watch: {},
        methods: {}
}; //
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//

/***/ }),

/***/ 172:
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
    value: true
});
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//

exports.default = {

    name: 'zwBase',
    components: {},
    props: [],
    data: function data() {
        return {};
    },

    computed: {},
    mounted: function mounted() {},

    watch: {},
    methods: {
        getPathValue: function (_getPathValue) {
            function getPathValue(_x) {
                return _getPathValue.apply(this, arguments);
            }

            getPathValue.toString = function () {
                return _getPathValue.toString();
            };

            return getPathValue;
        }(function (value) {
            return getPathValue(value);
        })
    }
};

/***/ }),

/***/ 174:
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
  value: true
});

var _zwTabs = __webpack_require__(18);

var _zwTabs2 = _interopRequireDefault(_zwTabs);

var _zwBase = __webpack_require__(35);

var _zwBase2 = _interopRequireDefault(_zwBase);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//

exports.default = {
  components: {
    zwBase: _zwBase2.default,

    zwTabs: _zwTabs2.default

  },
  name: "bottomBar",
  data: function data() {

    return {};
  },
  mounted: function mounted() {},

  computed: {},
  methods: {
    selectTab: function selectTab(index) {
      if (index == 0) {
        window.location = "#phoneMain";
      }
      if (index == 1) {
        window.location = "#map";
      }
      if (index == 4) {
        window.location = "#userCenter";
      }
    },
    getPathValue: function (_getPathValue) {
      function getPathValue(_x) {
        return _getPathValue.apply(this, arguments);
      }

      getPathValue.toString = function () {
        return _getPathValue.toString();
      };

      return getPathValue;
    }(function (value) {

      return getPathValue(value);
    })
  }
};

/***/ }),

/***/ 175:
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
    value: true
});

var _zwRow = __webpack_require__(5);

var _zwRow2 = _interopRequireDefault(_zwRow);

var _zwCol = __webpack_require__(4);

var _zwCol2 = _interopRequireDefault(_zwCol);

var _zwIcon = __webpack_require__(3);

var _zwIcon2 = _interopRequireDefault(_zwIcon);

var _zwAppTopBar = __webpack_require__(49);

var _zwAppTopBar2 = _interopRequireDefault(_zwAppTopBar);

var _zwAppTitle = __webpack_require__(33);

var _zwAppTitle2 = _interopRequireDefault(_zwAppTitle);

var _zwInput = __webpack_require__(14);

var _zwInput2 = _interopRequireDefault(_zwInput);

var _zwForm = __webpack_require__(20);

var _zwForm2 = _interopRequireDefault(_zwForm);

var _zwFormItem = __webpack_require__(21);

var _zwFormItem2 = _interopRequireDefault(_zwFormItem);

var _zwButton = __webpack_require__(6);

var _zwButton2 = _interopRequireDefault(_zwButton);

var _zwChocolatePanel = __webpack_require__(27);

var _zwChocolatePanel2 = _interopRequireDefault(_zwChocolatePanel);

var _zwTabs = __webpack_require__(18);

var _zwTabs2 = _interopRequireDefault(_zwTabs);

var _zwTabPanel = __webpack_require__(23);

var _zwTabPanel2 = _interopRequireDefault(_zwTabPanel);

var _zwUploadImg = __webpack_require__(220);

var _zwUploadImg2 = _interopRequireDefault(_zwUploadImg);

var _bottomBar = __webpack_require__(64);

var _bottomBar2 = _interopRequireDefault(_bottomBar);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

/**登录注册页面**/
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//


exports.default = {
    name: "lockPwdModify",
    components: { zwRow: _zwRow2.default, zwCol: _zwCol2.default, zwIcon: _zwIcon2.default, zwButton: _zwButton2.default, zwInput: _zwInput2.default, zwAppTitle: _zwAppTitle2.default, zwFormItem: _zwFormItem2.default, zwForm: _zwForm2.default, zwChocolatePanel: _zwChocolatePanel2.default, zwTabs: _zwTabs2.default, zwTabPanel: _zwTabPanel2.default, zwBottomBar: _bottomBar2.default, zwUploadImg: _zwUploadImg2.default, zwAppTopBar: _zwAppTopBar2.default
    },
    data: function data() {

        return {
            user: {},
            list: [{ type: "shopping-cart", url: "", name: "业务订购" }, { type: "suitcase", url: "", name: "业务查询" }, { type: "history", url: "#deviceMsgList", name: "历史查询" }]
        };
    },
    beforeCreate: function beforeCreate() {
        Ajax.getJSON(PATH + "/user/info.json", {}, function (result) {
            ajaxResultHandler(result);
            if (result[AJAX_RESULT] == AJAX_SUCC) {
                this.user = result.data || {}; //alert(this.user.nick);
            } else {
                    //alert(result.msg);
                }
        }.Apply(this));
    },
    mounted: function mounted() {
        var that = this;
        // var imageUtil=new zImageUtil5({"input":"face",callback:function(result){that.user.face = result.data}});
    },

    computed: {},

    methods: {
        changeFace: function changeFace(img) {
            alert("changed");
            this.user.face = "" + img;
        },
        getImg: function getImg(img) {
            return PATH + img;
        },
        logout: function logout() {
            Ajax.get(PATH + "/sys/auth/logout.json", {}, function () {
                window.location = PATH + "/sys/auth/login.htm?pre=" + encodeURIComponent(window.location);
            });

            // window.location="#loginSmsStep1";
        },
        getPathValue: function (_getPathValue) {
            function getPathValue(_x) {
                return _getPathValue.apply(this, arguments);
            }

            getPathValue.toString = function () {
                return _getPathValue.toString();
            };

            return getPathValue;
        }(function (value) {
            return getPathValue(value);
        }),
        save: function save() {
            /* var address= $$("address").value;
              var birthday= $$("birthday").value;
             var nick = $$("nick").value;
             var sex =getRadioValueByName("sex");*/
            var json = this.user; //{birthday:birthday,address:address,nick:nick,sex:sex,face:$$("#face").value};
            console.log(json);
            Ajax.post(PATH + "/user/face/update", json, function (result) {
                if (result.r == AJAX_SUCC) {
                    dialog.alert("保存成功");
                } else {
                    dialog.alert(result.msg);
                }
            });

            // window.location="#loginSmsStep1";
        },
        login: function login() {
            window.location = PATH + "/sys/auth/login.htm?pre=" + encodeURIComponent(window.location);
        }

    }
};

/***/ }),

/***/ 179:
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__(0)(undefined);
// imports


// module
exports.push([module.i, "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n", ""]);

// exports


/***/ }),

/***/ 18:
/***/ (function(module, exports, __webpack_require__) {

var disposed = false
function injectStyle (ssrContext) {
  if (disposed) return
  __webpack_require__(289)
}
var Component = __webpack_require__(2)(
  /* script */
  __webpack_require__(153),
  /* template */
  __webpack_require__(256),
  /* styles */
  injectStyle,
  /* scopeId */
  null,
  /* moduleIdentifier (server only) */
  null
)
Component.options.__file = "G:\\workspace\\dozenx\\ui\\webpack\\app\\component\\datadisplay\\zwTabs.vue"
if (Component.esModule && Object.keys(Component.esModule).some(function (key) {return key !== "default" && key.substr(0, 2) !== "__"})) {console.error("named exports are not supported in *.vue files.")}
if (Component.options.functional) {console.error("[vue-loader] zwTabs.vue: functional components are not supported with templates, they should use render functions.")}

/* hot reload */
if (false) {(function () {
  var hotAPI = require("vue-hot-reload-api")
  hotAPI.install(require("vue"), false)
  if (!hotAPI.compatible) return
  module.hot.accept()
  if (!module.hot.data) {
    hotAPI.createRecord("data-v-ed137c3c", Component.options)
  } else {
    hotAPI.reload("data-v-ed137c3c", Component.options)
  }
  module.hot.dispose(function (data) {
    disposed = true
  })
})()}

module.exports = Component.exports


/***/ }),

/***/ 180:
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__(0)(undefined);
// imports


// module
exports.push([module.i, "\n.img-upload{\n    max-width:100%;\n}\n", ""]);

// exports


/***/ }),

/***/ 182:
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__(0)(undefined);
// imports


// module
exports.push([module.i, "\n.mubu[data-v-40eb3cef]{\r\n    height:300px;\r\n    color:gray;\r\n    text-align:center;\r\n    position:relative;\n}\n.user-circle[data-v-40eb3cef]{\r\n    display:inline-block;\r\n\r\n    width:30px;\r\n      height:30px;\r\n\r\n\r\n      border-radius:15px;\r\n       text-align:center;\r\n      padding:10px;\r\n      background-color:white;\n}\n.user-circle img[data-v-40eb3cef]{\r\n       width:30px;\r\n        height:30px;\n}\n.center-block[data-v-40eb3cef]{\r\npadding-top:80px;\r\ntext-align:center;\n}\n.chocolate li[data-v-40eb3cef]{\r\nwidth:30% !important;\n}\n.zw-icon-list li[data-v-40eb3cef]{\r\nwidth:30% !important;\n}\n.zw-form-item[data-v-40eb3cef]{\r\n    border-bottom:1px solid #ecdcdc;\n}\r\n\r\n", ""]);

// exports


/***/ }),

/***/ 187:
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__(0)(undefined);
// imports


// module
exports.push([module.i, "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n", ""]);

// exports


/***/ }),

/***/ 195:
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__(0)(undefined);
// imports


// module
exports.push([module.i, "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n", ""]);

// exports


/***/ }),

/***/ 198:
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__(0)(undefined);
// imports


// module
exports.push([module.i, "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n", ""]);

// exports


/***/ }),

/***/ 2:
/***/ (function(module, exports) {

/* globals __VUE_SSR_CONTEXT__ */

// this module is a runtime utility for cleaner component module output and will
// be included in the final webpack user bundle

module.exports = function normalizeComponent (
  rawScriptExports,
  compiledTemplate,
  injectStyles,
  scopeId,
  moduleIdentifier /* server only */
) {
  var esModule
  var scriptExports = rawScriptExports = rawScriptExports || {}

  // ES6 modules interop
  var type = typeof rawScriptExports.default
  if (type === 'object' || type === 'function') {
    esModule = rawScriptExports
    scriptExports = rawScriptExports.default
  }

  // Vue.extend constructor export interop
  var options = typeof scriptExports === 'function'
    ? scriptExports.options
    : scriptExports

  // render functions
  if (compiledTemplate) {
    options.render = compiledTemplate.render
    options.staticRenderFns = compiledTemplate.staticRenderFns
  }

  // scopedId
  if (scopeId) {
    options._scopeId = scopeId
  }

  var hook
  if (moduleIdentifier) { // server build
    hook = function (context) {
      // 2.3 injection
      context =
        context || // cached call
        (this.$vnode && this.$vnode.ssrContext) || // stateful
        (this.parent && this.parent.$vnode && this.parent.$vnode.ssrContext) // functional
      // 2.2 with runInNewContext: true
      if (!context && typeof __VUE_SSR_CONTEXT__ !== 'undefined') {
        context = __VUE_SSR_CONTEXT__
      }
      // inject component styles
      if (injectStyles) {
        injectStyles.call(this, context)
      }
      // register component module identifier for async chunk inferrence
      if (context && context._registeredComponents) {
        context._registeredComponents.add(moduleIdentifier)
      }
    }
    // used by ssr in case component is cached and beforeCreate
    // never gets called
    options._ssrRegister = hook
  } else if (injectStyles) {
    hook = injectStyles
  }

  if (hook) {
    var functional = options.functional
    var existing = functional
      ? options.render
      : options.beforeCreate
    if (!functional) {
      // inject component registration as beforeCreate hook
      options.beforeCreate = existing
        ? [].concat(existing, hook)
        : [hook]
    } else {
      // register for functioal component in vue file
      options.render = function renderWithStyleInjection (h, context) {
        hook.call(context)
        return existing(h, context)
      }
    }
  }

  return {
    esModule: esModule,
    exports: scriptExports,
    options: options
  }
}


/***/ }),

/***/ 20:
/***/ (function(module, exports, __webpack_require__) {

var disposed = false
function injectStyle (ssrContext) {
  if (disposed) return
  __webpack_require__(121)
}
var Component = __webpack_require__(2)(
  /* script */
  __webpack_require__(87),
  /* template */
  __webpack_require__(114),
  /* styles */
  injectStyle,
  /* scopeId */
  null,
  /* moduleIdentifier (server only) */
  null
)
Component.options.__file = "G:\\workspace\\dozenx\\ui\\webpack\\app\\component\\dataentry\\zwForm.vue"
if (Component.esModule && Object.keys(Component.esModule).some(function (key) {return key !== "default" && key.substr(0, 2) !== "__"})) {console.error("named exports are not supported in *.vue files.")}
if (Component.options.functional) {console.error("[vue-loader] zwForm.vue: functional components are not supported with templates, they should use render functions.")}

/* hot reload */
if (false) {(function () {
  var hotAPI = require("vue-hot-reload-api")
  hotAPI.install(require("vue"), false)
  if (!hotAPI.compatible) return
  module.hot.accept()
  if (!module.hot.data) {
    hotAPI.createRecord("data-v-0524c2f8", Component.options)
  } else {
    hotAPI.reload("data-v-0524c2f8", Component.options)
  }
  module.hot.dispose(function (data) {
    disposed = true
  })
})()}

module.exports = Component.exports


/***/ }),

/***/ 200:
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__(0)(undefined);
// imports


// module
exports.push([module.i, "\n.header[data-v-a4caae92] {\r\n    height: 60px;\r\n    line-height: 60px;\r\n    color: #ffffff;\r\n    /* box-shadow: 0 2px 0 #2372CC, 0 1px 0 #438EE4; */\r\n    box-shadow: 0 2px 5px rgba(0,0,0,0.26);\r\n    position: fixed;\r\n    top: 0;\r\n    left: 0;\r\n    right: 0;\r\n    z-index: 10;\n}\n.row-5[data-v-a4caae92] {\r\n    width: 50%;\r\n    margin: 0 auto;\n}\n.left[data-v-a4caae92] {\r\n    float: left;\n}\n.center-text[data-v-a4caae92] {\r\n    text-align: center;\n}\n.normal-text[data-v-a4caae92] {\r\n    font-size: 1.6rem;\n}\n.header-left[data-v-a4caae92], .header-right[data-v-a4caae92] {\r\n    /* background: url(../img/header/back.png) no-repeat 15px center; */\r\n    /* background-size: 20%; */\r\n    /* color: transparent; */\r\n    /* padding: 30px 0; */\r\n    height: 60px;\r\n    line-height: 60px;\r\n    color: #ffffff;\n}\n.row-2-5[data-v-a4caae92] {\r\n    width: 25%;\r\n    margin: 0 auto;\n}\n.left[data-v-a4caae92] {\r\n    float: left;\n}\n.center-text[data-v-a4caae92] {\r\n    text-align: center;\n}\n.small-text[data-v-a4caae92] {\r\n    font-size: 1.3rem;\n}\n.header-left img[data-v-a4caae92]{\r\n    display: block;\r\n    position: relative;\r\n    top: 30%;\r\n    left: 10%;\r\n    height: 40%;\r\n     float: left;\n}\n.header-left span[data-v-a4caae92] {\r\n    position: relative;\r\n    top: -24px;\r\n    color: #ffffff;\r\n    padding-left: 42px;\n}\n.right-text[data-v-a4caae92] {\r\n    text-align: right;\n}\n.header-right img[data-v-a4caae92] {\r\n    display: block;\r\n    position: relative;\r\n    top: 30%;\r\n    height: 40%;\r\n    right: 15px;\r\n     float: right;\n}\n.right[data-v-a4caae92] {\r\n    float: right;\n}\r\n", ""]);

// exports


/***/ }),

/***/ 203:
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__(0)(undefined);
// imports


// module
exports.push([module.i, "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n", ""]);

// exports


/***/ }),

/***/ 205:
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__(0)(undefined);
// imports


// module
exports.push([module.i, "\n.zw-app-title {\r\npadding-top:40px;\r\npadding-bottom:20px;\r\nfont-weight:500;\r\npadding-left:20px;\n}\r\n", ""]);

// exports


/***/ }),

/***/ 21:
/***/ (function(module, exports, __webpack_require__) {

var disposed = false
function injectStyle (ssrContext) {
  if (disposed) return
  __webpack_require__(120)
}
var Component = __webpack_require__(2)(
  /* script */
  __webpack_require__(88),
  /* template */
  __webpack_require__(113),
  /* styles */
  injectStyle,
  /* scopeId */
  null,
  /* moduleIdentifier (server only) */
  null
)
Component.options.__file = "G:\\workspace\\dozenx\\ui\\webpack\\app\\component\\dataentry\\zwFormItem.vue"
if (Component.esModule && Object.keys(Component.esModule).some(function (key) {return key !== "default" && key.substr(0, 2) !== "__"})) {console.error("named exports are not supported in *.vue files.")}
if (Component.options.functional) {console.error("[vue-loader] zwFormItem.vue: functional components are not supported with templates, they should use render functions.")}

/* hot reload */
if (false) {(function () {
  var hotAPI = require("vue-hot-reload-api")
  hotAPI.install(require("vue"), false)
  if (!hotAPI.compatible) return
  module.hot.accept()
  if (!module.hot.data) {
    hotAPI.createRecord("data-v-02709eaa", Component.options)
  } else {
    hotAPI.reload("data-v-02709eaa", Component.options)
  }
  module.hot.dispose(function (data) {
    disposed = true
  })
})()}

module.exports = Component.exports


/***/ }),

/***/ 220:
/***/ (function(module, exports, __webpack_require__) {

var disposed = false
function injectStyle (ssrContext) {
  if (disposed) return
  __webpack_require__(266)
}
var Component = __webpack_require__(2)(
  /* script */
  __webpack_require__(155),
  /* template */
  __webpack_require__(232),
  /* styles */
  injectStyle,
  /* scopeId */
  null,
  /* moduleIdentifier (server only) */
  null
)
Component.options.__file = "G:\\workspace\\dozenx\\ui\\webpack\\app\\component\\dataentry\\zwUploadImg.vue"
if (Component.esModule && Object.keys(Component.esModule).some(function (key) {return key !== "default" && key.substr(0, 2) !== "__"})) {console.error("named exports are not supported in *.vue files.")}
if (Component.options.functional) {console.error("[vue-loader] zwUploadImg.vue: functional components are not supported with templates, they should use render functions.")}

/* hot reload */
if (false) {(function () {
  var hotAPI = require("vue-hot-reload-api")
  hotAPI.install(require("vue"), false)
  if (!hotAPI.compatible) return
  module.hot.accept()
  if (!module.hot.data) {
    hotAPI.createRecord("data-v-2e207904", Component.options)
  } else {
    hotAPI.reload("data-v-2e207904", Component.options)
  }
  module.hot.dispose(function (data) {
    disposed = true
  })
})()}

module.exports = Component.exports


/***/ }),

/***/ 23:
/***/ (function(module, exports, __webpack_require__) {

var disposed = false
function injectStyle (ssrContext) {
  if (disposed) return
  __webpack_require__(265)
}
var Component = __webpack_require__(2)(
  /* script */
  __webpack_require__(151),
  /* template */
  __webpack_require__(231),
  /* styles */
  injectStyle,
  /* scopeId */
  null,
  /* moduleIdentifier (server only) */
  null
)
Component.options.__file = "G:\\workspace\\dozenx\\ui\\webpack\\app\\component\\datadisplay\\zwTabPanel.vue"
if (Component.esModule && Object.keys(Component.esModule).some(function (key) {return key !== "default" && key.substr(0, 2) !== "__"})) {console.error("named exports are not supported in *.vue files.")}
if (Component.options.functional) {console.error("[vue-loader] zwTabPanel.vue: functional components are not supported with templates, they should use render functions.")}

/* hot reload */
if (false) {(function () {
  var hotAPI = require("vue-hot-reload-api")
  hotAPI.install(require("vue"), false)
  if (!hotAPI.compatible) return
  module.hot.accept()
  if (!module.hot.data) {
    hotAPI.createRecord("data-v-29593753", Component.options)
  } else {
    hotAPI.reload("data-v-29593753", Component.options)
  }
  module.hot.dispose(function (data) {
    disposed = true
  })
})()}

module.exports = Component.exports


/***/ }),

/***/ 231:
/***/ (function(module, exports, __webpack_require__) {

module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', {
    staticClass: "zw-tabs-tabpane zw-tabs-tabpane-active",
    attrs: {
      "role": "tabpanel",
      "aria-hidden": "false"
    },
    on: {
      "click": _vm.clickFunction
    }
  }, [_vm._t("default")], 2)
},staticRenderFns: []}
module.exports.render._withStripped = true
if (false) {
  module.hot.accept()
  if (module.hot.data) {
     require("vue-hot-reload-api").rerender("data-v-29593753", module.exports)
  }
}

/***/ }),

/***/ 232:
/***/ (function(module, exports, __webpack_require__) {

module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', [_c('input', {
    ref: "input",
    staticStyle: {
      "display": "none"
    },
    attrs: {
      "type": "file"
    }
  }), _vm._v(" "), _c('img', {
    ref: "img",
    staticClass: " img-upload",
    attrs: {
      "alt": "请上传图片",
      "src": _vm.getPathValue(_vm.value)
    }
  })])
},staticRenderFns: []}
module.exports.render._withStripped = true
if (false) {
  module.hot.accept()
  if (module.hot.data) {
     require("vue-hot-reload-api").rerender("data-v-2e207904", module.exports)
  }
}

/***/ }),

/***/ 235:
/***/ (function(module, exports, __webpack_require__) {

module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', [_c('zwAppTopBar', [_c('a', {
    attrs: {
      "href": _vm.getPathValue('/static/html/PhoneCalendarView.html')
    },
    slot: "left"
  }, [_c('img', {
    attrs: {
      "src": _vm.getPathValue('/static/img/header/back.png')
    }
  })]), _vm._v(" "), _c('span', {
    slot: "middle"
  }, [_vm._v(" 个人信息修改")])]), _vm._v(" "), _c('zwRow', {
    staticStyle: {
      "margin-top": "30px"
    }
  }, [_c('zwCol', {
    attrs: {
      "span": "24"
    }
  }, [_c('zwForm', {
    staticStyle: {
      "margin-top": "22px",
      "padding-top": "30px"
    }
  }, [_c('zwFormItem', {
    attrs: {
      "label": "头像"
    }
  }, [_c('div', {
    staticStyle: {
      "width": "50%",
      "float": "right",
      "margin-right": "0px"
    }
  }, [_c('zwUploadImg', {
    model: {
      value: (_vm.user.face),
      callback: function($$v) {
        _vm.user.face = $$v
      },
      expression: "user.face"
    }
  })], 1)]), _vm._v(" "), _c('zwFormItem', {
    attrs: {
      "label": "昵称"
    }
  }, [_c('zwInput', {
    attrs: {
      "id": "nick",
      "name": "nick"
    },
    model: {
      value: (_vm.user.nick),
      callback: function($$v) {
        _vm.user.nick = $$v
      },
      expression: "user.nick"
    }
  })], 1), _vm._v(" "), _c('zwFormItem', {
    attrs: {
      "label": "性别"
    }
  }, [_vm._v("\r\n                    "), _c('input', {
    directives: [{
      name: "model",
      rawName: "v-model",
      value: (_vm.user.sex),
      expression: "user.sex"
    }],
    attrs: {
      "type": "radio",
      "id": "sex1",
      "name": "sex",
      "value": "1"
    },
    domProps: {
      "checked": _vm._q(_vm.user.sex, "1")
    },
    on: {
      "__c": function($event) {
        _vm.user.sex = "1"
      }
    }
  }), _vm._v("男"), _vm._v("\r\n                      "), _c('input', {
    directives: [{
      name: "model",
      rawName: "v-model",
      value: (_vm.user.sex),
      expression: "user.sex"
    }],
    attrs: {
      "id": "sex2",
      "name": "sex",
      "type": "radio",
      "value": "2"
    },
    domProps: {
      "checked": _vm._q(_vm.user.sex, "2")
    },
    on: {
      "__c": function($event) {
        _vm.user.sex = "2"
      }
    }
  }), _vm._v("女")]), _vm._v(" "), _c('zwFormItem', {
    attrs: {
      "label": "生日"
    }
  }, [_c('zwInput', {
    attrs: {
      "id": "birthday",
      "name": "birthday",
      "type": "date"
    },
    model: {
      value: (_vm.user.birth),
      callback: function($$v) {
        _vm.user.birth = $$v
      },
      expression: "user.birth"
    }
  })], 1), _vm._v(" "), _c('zwFormItem', {
    attrs: {
      "label": "地址"
    }
  }, [_c('zwInput', {
    attrs: {
      "id": "address",
      "name": "address"
    },
    model: {
      value: (_vm.user.address),
      callback: function($$v) {
        _vm.user.address = $$v
      },
      expression: "user.address"
    }
  })], 1), _vm._v(" "), _c('zwFormItem', {
    staticStyle: {
      "padding-left": "20px",
      "padding-bottom": "25px"
    }
  }, [_c('zwButton', {
    staticStyle: {
      "width": "95%"
    },
    attrs: {
      "type": "yellow",
      "sizeNum": "large"
    },
    on: {
      "clickFn": _vm.save
    }
  }, [_vm._v("保存")])], 1)], 1)], 1)], 1), _vm._v(" "), _c('zwRow', [_c('zwCol', {
    staticClass: "center",
    staticStyle: {
      "margin-top": "50px"
    },
    attrs: {
      "span": "24"
    }
  })], 1)], 1)
},staticRenderFns: []}
module.exports.render._withStripped = true
if (false) {
  module.hot.accept()
  if (module.hot.data) {
     require("vue-hot-reload-api").rerender("data-v-40eb3cef", module.exports)
  }
}

/***/ }),

/***/ 240:
/***/ (function(module, exports, __webpack_require__) {

module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('ul', {
    staticClass: "zw-icon-list list ma0 pa0 flex flex-row flex-wrap justify-start items-start"
  }, _vm._l((_vm.data), function(item) {
    return _c('li', {
      class: _vm.getCls
    }, [_c('a', {
      on: {
        "click": function($event) {
          _vm.goLink(item.url)
        }
      }
    }, [_c('zwIcon', {
      attrs: {
        "type": item.type
      }
    }), _vm._v(" "), _c('span', {
      staticClass: "zw-icon-desc"
    }, [_c('span', {
      staticClass: "zw-badge"
    }, [_vm._v(_vm._s(item.name))])])], 1)])
  }))
},staticRenderFns: []}
module.exports.render._withStripped = true
if (false) {
  module.hot.accept()
  if (module.hot.data) {
     require("vue-hot-reload-api").rerender("data-v-52ca50e8", module.exports)
  }
}

/***/ }),

/***/ 248:
/***/ (function(module, exports, __webpack_require__) {

module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('zwTabs', {
    staticClass: "bg-black fix-bottom",
    staticStyle: {
      "width": "100%"
    },
    attrs: {
      "theme": "purple_yellow",
      "defaultIndex": "1",
      "type": "appbottom",
      "panel": "no"
    },
    on: {
      "changeTab": _vm.selectTab
    }
  }, [_c('dataitem', {
    attrs: {
      "tab": "首页",
      "img": _vm.getPathValue('/static/img/smarthome/网络管理1.png'),
      "img2": _vm.getPathValue('/static/img/smarthome/网络管理2.png'),
      "tabIndex": "1"
    }
  }), _vm._v(" "), _c('dataitem', {
    attrs: {
      "tab": "附近",
      "img": _vm.getPathValue('/static/img/smarthome/智能家居1.png'),
      "img2": _vm.getPathValue('/static/img/smarthome/智能家居2.png'),
      "tabIndex": "2"
    }
  }, [_vm._v("\n                    Content of Tab Pane 21\n                ")]), _vm._v(" "), _c('dataitem', {
    attrs: {
      "tab": "发现",
      "img": _vm.getPathValue('/static/img/smarthome/智能家居1.png'),
      "img2": _vm.getPathValue('/static/img/smarthome/智能家居2.png'),
      "tabIndex": "3"
    }
  }, [_vm._v("\n                          Content of Tab Pane 21\n                      ")]), _vm._v(" "), _c('dataitem', {
    attrs: {
      "tab": "订单",
      "img": _vm.getPathValue('/static/img/smarthome/智能家居1.png'),
      "img2": _vm.getPathValue('/static/img/smarthome/智能家居2.png'),
      "tabIndex": "4"
    }
  }, [_vm._v("\n          Content of Tab Pane 21\n      ")]), _vm._v(" "), _c('dataitem', {
    attrs: {
      "tab": "我的",
      "img": _vm.getPathValue('/static/img/smarthome/个人中心1.png'),
      "img2": _vm.getPathValue('/static/img/smarthome/个人中心2.png'),
      "tabIndex": "5"
    }
  }, [_vm._v("\n          Content of Tab Pane 31\n      ")])], 1)
},staticRenderFns: []}
module.exports.render._withStripped = true
if (false) {
  module.hot.accept()
  if (module.hot.data) {
     require("vue-hot-reload-api").rerender("data-v-7cd356fc", module.exports)
  }
}

/***/ }),

/***/ 251:
/***/ (function(module, exports, __webpack_require__) {

module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('header', {
    staticClass: "header bg-blue"
  }, [_c('nav', [_c('a', {
    attrs: {
      "id": "login",
      "href": "login.html"
    }
  }, [_c('div', {
    staticClass: "header-left center-text zw-col-6 left small-text"
  }, [_c('img', {
    staticClass: "header-left-img",
    attrs: {
      "src": _vm.getPathValue('/static/img/header/wifi.png')
    }
  }), _vm._v(" "), _c('span', {
    staticClass: "header-left-span right-text",
    slot: "left"
  }, [_vm._t("left")], 2)])]), _vm._v(" "), _c('div', {
    staticClass: "center-text zw-col-11 left normal-text",
    slot: "middle"
  }, [_vm._t("middle")], 2), _vm._v(" "), _c('div', {
    staticClass: "header-right center-text zw-col-6 left small-text"
  }, [_vm._t("right")], 2)])])
},staticRenderFns: []}
module.exports.render._withStripped = true
if (false) {
  module.hot.accept()
  if (module.hot.data) {
     require("vue-hot-reload-api").rerender("data-v-a1a1d9c4", module.exports)
  }
}

/***/ }),

/***/ 253:
/***/ (function(module, exports, __webpack_require__) {

module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('header', {
    staticClass: "header bg-blue"
  }, [_c('nav', [_c('div', {
    attrs: {
      "id": "login"
    }
  }, [_c('div', {
    staticClass: "header-left center-text zw-col-6 left small-text"
  }, [_vm._t("left")], 2)]), _vm._v(" "), _c('div', {
    staticClass: "center-text zw-col-11 left normal-text",
    slot: "middle"
  }, [_vm._t("middle")], 2), _vm._v(" "), _c('div', {
    staticClass: "header-right center-text zw-col-6 left small-text"
  }, [_vm._t("right")], 2)])])
},staticRenderFns: []}
module.exports.render._withStripped = true
if (false) {
  module.hot.accept()
  if (module.hot.data) {
     require("vue-hot-reload-api").rerender("data-v-a4caae92", module.exports)
  }
}

/***/ }),

/***/ 256:
/***/ (function(module, exports, __webpack_require__) {

module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', {
    staticClass: "zw-tabs zw-tabs-top zw-tabs-line ",
    class: _vm.getMainClass
  }, [_c('div', {
    staticClass: "zw-tabs-bar"
  }, [_c('div', {
    staticClass: "zw-tabs-nav-container"
  }, [_c('div', {
    staticClass: "zw-tabs-nav-wrap"
  }, [_c('div', {
    staticClass: "zw-tabs-nav-scroll"
  }, [_c('div', {
    staticClass: "zw-tabs-nav ant-tabs-nav-animated",
    class: _vm.getAppBottomClass()
  }, [(_vm.tabUnderLineMarginLeft()) ? _c('div', {
    staticClass: "zw-tabs-ink-bar zw-tabs-ink-bar-animated",
    style: (_vm.tabUnderLineMarginLeft())
  }) : _vm._e(), _vm._v(" "), _vm._l((_vm.tabDatas), function(tabData, index) {
    return _c('div', {
      staticClass: " zw-tabs-tab",
      class: index == _vm.selectedTabIndex ? 'zw-tabs-tab-active' : 'zw-tabs-tab-inactive',
      attrs: {
        "role": "tab",
        "aria-disabled": "false",
        "aria-selected": "false"
      },
      on: {
        "click": function($event) {
          _vm.changeTab(tabData.tabIndex)
        }
      }
    }, [_c('img', {
      attrs: {
        "vi-if": "tabData.img",
        "src": _vm.selectedTabIndex == index ? tabData.img2 : tabData.img
      }
    }), _vm._v(" "), _c('zwIcon', {
      attrs: {
        "vi-if": "tabData.icon",
        "type": tabData.icon
      }
    }), _vm._v(" "), _c('span', [_vm._v(" " + _vm._s(tabData.name))])], 1)
  })], 2)])])])]), _vm._v(" "), (!_vm.panel) ? _c('div', {
    staticClass: "zw-tabs-content zw-tabs-content-animated",
    style: (_vm.marginLeft())
  }, [_vm._t("default")], 2) : _vm._e()])
},staticRenderFns: []}
module.exports.render._withStripped = true
if (false) {
  module.hot.accept()
  if (module.hot.data) {
     require("vue-hot-reload-api").rerender("data-v-ed137c3c", module.exports)
  }
}

/***/ }),

/***/ 258:
/***/ (function(module, exports, __webpack_require__) {

module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('h1', {
    staticClass: "zw-app-title"
  }, [_vm._t("default")], 2)
},staticRenderFns: []}
module.exports.render._withStripped = true
if (false) {
  module.hot.accept()
  if (module.hot.data) {
     require("vue-hot-reload-api").rerender("data-v-f4b72eca", module.exports)
  }
}

/***/ }),

/***/ 265:
/***/ (function(module, exports, __webpack_require__) {

// style-loader: Adds some css to the DOM by adding a <style> tag

// load the styles
var content = __webpack_require__(179);
if(typeof content === 'string') content = [[module.i, content, '']];
if(content.locals) module.exports = content.locals;
// add the styles to the DOM
var update = __webpack_require__(1)("1d154231", content, false);
// Hot Module Replacement
if(false) {
 // When the styles change, update the <style> tags
 if(!content.locals) {
   module.hot.accept("!!../../../node_modules/css-loader/index.js!../../../node_modules/vue-loader/lib/style-compiler/index.js?{\"vue\":true,\"id\":\"data-v-29593753\",\"scoped\":false,\"hasInlineConfig\":false}!../../../node_modules/vue-loader/lib/selector.js?type=styles&index=0!./zwTabPanel.vue", function() {
     var newContent = require("!!../../../node_modules/css-loader/index.js!../../../node_modules/vue-loader/lib/style-compiler/index.js?{\"vue\":true,\"id\":\"data-v-29593753\",\"scoped\":false,\"hasInlineConfig\":false}!../../../node_modules/vue-loader/lib/selector.js?type=styles&index=0!./zwTabPanel.vue");
     if(typeof newContent === 'string') newContent = [[module.id, newContent, '']];
     update(newContent);
   });
 }
 // When the module is disposed, remove the <style> tags
 module.hot.dispose(function() { update(); });
}

/***/ }),

/***/ 266:
/***/ (function(module, exports, __webpack_require__) {

// style-loader: Adds some css to the DOM by adding a <style> tag

// load the styles
var content = __webpack_require__(180);
if(typeof content === 'string') content = [[module.i, content, '']];
if(content.locals) module.exports = content.locals;
// add the styles to the DOM
var update = __webpack_require__(1)("7fe7e17c", content, false);
// Hot Module Replacement
if(false) {
 // When the styles change, update the <style> tags
 if(!content.locals) {
   module.hot.accept("!!../../../node_modules/css-loader/index.js!../../../node_modules/vue-loader/lib/style-compiler/index.js?{\"vue\":true,\"id\":\"data-v-2e207904\",\"scoped\":false,\"hasInlineConfig\":false}!../../../node_modules/vue-loader/lib/selector.js?type=styles&index=0!./zwUploadImg.vue", function() {
     var newContent = require("!!../../../node_modules/css-loader/index.js!../../../node_modules/vue-loader/lib/style-compiler/index.js?{\"vue\":true,\"id\":\"data-v-2e207904\",\"scoped\":false,\"hasInlineConfig\":false}!../../../node_modules/vue-loader/lib/selector.js?type=styles&index=0!./zwUploadImg.vue");
     if(typeof newContent === 'string') newContent = [[module.id, newContent, '']];
     update(newContent);
   });
 }
 // When the module is disposed, remove the <style> tags
 module.hot.dispose(function() { update(); });
}

/***/ }),

/***/ 268:
/***/ (function(module, exports, __webpack_require__) {

// style-loader: Adds some css to the DOM by adding a <style> tag

// load the styles
var content = __webpack_require__(182);
if(typeof content === 'string') content = [[module.i, content, '']];
if(content.locals) module.exports = content.locals;
// add the styles to the DOM
var update = __webpack_require__(1)("533de9bc", content, false);
// Hot Module Replacement
if(false) {
 // When the styles change, update the <style> tags
 if(!content.locals) {
   module.hot.accept("!!../../../node_modules/css-loader/index.js!../../../node_modules/vue-loader/lib/style-compiler/index.js?{\"vue\":true,\"id\":\"data-v-40eb3cef\",\"scoped\":true,\"hasInlineConfig\":false}!../../../node_modules/vue-loader/lib/selector.js?type=styles&index=0!./userInfo.vue", function() {
     var newContent = require("!!../../../node_modules/css-loader/index.js!../../../node_modules/vue-loader/lib/style-compiler/index.js?{\"vue\":true,\"id\":\"data-v-40eb3cef\",\"scoped\":true,\"hasInlineConfig\":false}!../../../node_modules/vue-loader/lib/selector.js?type=styles&index=0!./userInfo.vue");
     if(typeof newContent === 'string') newContent = [[module.id, newContent, '']];
     update(newContent);
   });
 }
 // When the module is disposed, remove the <style> tags
 module.hot.dispose(function() { update(); });
}

/***/ }),

/***/ 27:
/***/ (function(module, exports, __webpack_require__) {

var disposed = false
function injectStyle (ssrContext) {
  if (disposed) return
  __webpack_require__(273)
}
var Component = __webpack_require__(2)(
  /* script */
  __webpack_require__(146),
  /* template */
  __webpack_require__(240),
  /* styles */
  injectStyle,
  /* scopeId */
  null,
  /* moduleIdentifier (server only) */
  null
)
Component.options.__file = "G:\\workspace\\dozenx\\ui\\webpack\\app\\component\\datadisplay\\zwChocolatePanel.vue"
if (Component.esModule && Object.keys(Component.esModule).some(function (key) {return key !== "default" && key.substr(0, 2) !== "__"})) {console.error("named exports are not supported in *.vue files.")}
if (Component.options.functional) {console.error("[vue-loader] zwChocolatePanel.vue: functional components are not supported with templates, they should use render functions.")}

/* hot reload */
if (false) {(function () {
  var hotAPI = require("vue-hot-reload-api")
  hotAPI.install(require("vue"), false)
  if (!hotAPI.compatible) return
  module.hot.accept()
  if (!module.hot.data) {
    hotAPI.createRecord("data-v-52ca50e8", Component.options)
  } else {
    hotAPI.reload("data-v-52ca50e8", Component.options)
  }
  module.hot.dispose(function (data) {
    disposed = true
  })
})()}

module.exports = Component.exports


/***/ }),

/***/ 273:
/***/ (function(module, exports, __webpack_require__) {

// style-loader: Adds some css to the DOM by adding a <style> tag

// load the styles
var content = __webpack_require__(187);
if(typeof content === 'string') content = [[module.i, content, '']];
if(content.locals) module.exports = content.locals;
// add the styles to the DOM
var update = __webpack_require__(1)("9f1e2d88", content, false);
// Hot Module Replacement
if(false) {
 // When the styles change, update the <style> tags
 if(!content.locals) {
   module.hot.accept("!!../../../node_modules/css-loader/index.js!../../../node_modules/vue-loader/lib/style-compiler/index.js?{\"vue\":true,\"id\":\"data-v-52ca50e8\",\"scoped\":false,\"hasInlineConfig\":false}!../../../node_modules/vue-loader/lib/selector.js?type=styles&index=0!./zwChocolatePanel.vue", function() {
     var newContent = require("!!../../../node_modules/css-loader/index.js!../../../node_modules/vue-loader/lib/style-compiler/index.js?{\"vue\":true,\"id\":\"data-v-52ca50e8\",\"scoped\":false,\"hasInlineConfig\":false}!../../../node_modules/vue-loader/lib/selector.js?type=styles&index=0!./zwChocolatePanel.vue");
     if(typeof newContent === 'string') newContent = [[module.id, newContent, '']];
     update(newContent);
   });
 }
 // When the module is disposed, remove the <style> tags
 module.hot.dispose(function() { update(); });
}

/***/ }),

/***/ 281:
/***/ (function(module, exports, __webpack_require__) {

// style-loader: Adds some css to the DOM by adding a <style> tag

// load the styles
var content = __webpack_require__(195);
if(typeof content === 'string') content = [[module.i, content, '']];
if(content.locals) module.exports = content.locals;
// add the styles to the DOM
var update = __webpack_require__(1)("768ecac8", content, false);
// Hot Module Replacement
if(false) {
 // When the styles change, update the <style> tags
 if(!content.locals) {
   module.hot.accept("!!../../../node_modules/css-loader/index.js!../../../node_modules/vue-loader/lib/style-compiler/index.js?{\"vue\":true,\"id\":\"data-v-7cd356fc\",\"scoped\":true,\"hasInlineConfig\":false}!../../../node_modules/vue-loader/lib/selector.js?type=styles&index=0!./bottomBar.vue", function() {
     var newContent = require("!!../../../node_modules/css-loader/index.js!../../../node_modules/vue-loader/lib/style-compiler/index.js?{\"vue\":true,\"id\":\"data-v-7cd356fc\",\"scoped\":true,\"hasInlineConfig\":false}!../../../node_modules/vue-loader/lib/selector.js?type=styles&index=0!./bottomBar.vue");
     if(typeof newContent === 'string') newContent = [[module.id, newContent, '']];
     update(newContent);
   });
 }
 // When the module is disposed, remove the <style> tags
 module.hot.dispose(function() { update(); });
}

/***/ }),

/***/ 284:
/***/ (function(module, exports, __webpack_require__) {

// style-loader: Adds some css to the DOM by adding a <style> tag

// load the styles
var content = __webpack_require__(198);
if(typeof content === 'string') content = [[module.i, content, '']];
if(content.locals) module.exports = content.locals;
// add the styles to the DOM
var update = __webpack_require__(1)("b1f185f0", content, false);
// Hot Module Replacement
if(false) {
 // When the styles change, update the <style> tags
 if(!content.locals) {
   module.hot.accept("!!../../node_modules/css-loader/index.js!../../node_modules/vue-loader/lib/style-compiler/index.js?{\"vue\":true,\"id\":\"data-v-a1a1d9c4\",\"scoped\":true,\"hasInlineConfig\":false}!../../node_modules/vue-loader/lib/selector.js?type=styles&index=0!./zwBase.vue", function() {
     var newContent = require("!!../../node_modules/css-loader/index.js!../../node_modules/vue-loader/lib/style-compiler/index.js?{\"vue\":true,\"id\":\"data-v-a1a1d9c4\",\"scoped\":true,\"hasInlineConfig\":false}!../../node_modules/vue-loader/lib/selector.js?type=styles&index=0!./zwBase.vue");
     if(typeof newContent === 'string') newContent = [[module.id, newContent, '']];
     update(newContent);
   });
 }
 // When the module is disposed, remove the <style> tags
 module.hot.dispose(function() { update(); });
}

/***/ }),

/***/ 286:
/***/ (function(module, exports, __webpack_require__) {

// style-loader: Adds some css to the DOM by adding a <style> tag

// load the styles
var content = __webpack_require__(200);
if(typeof content === 'string') content = [[module.i, content, '']];
if(content.locals) module.exports = content.locals;
// add the styles to the DOM
var update = __webpack_require__(1)("8c4b900e", content, false);
// Hot Module Replacement
if(false) {
 // When the styles change, update the <style> tags
 if(!content.locals) {
   module.hot.accept("!!../../../node_modules/css-loader/index.js!../../../node_modules/vue-loader/lib/style-compiler/index.js?{\"vue\":true,\"id\":\"data-v-a4caae92\",\"scoped\":true,\"hasInlineConfig\":false}!../../../node_modules/vue-loader/lib/selector.js?type=styles&index=0!./zwAppTopBar.vue", function() {
     var newContent = require("!!../../../node_modules/css-loader/index.js!../../../node_modules/vue-loader/lib/style-compiler/index.js?{\"vue\":true,\"id\":\"data-v-a4caae92\",\"scoped\":true,\"hasInlineConfig\":false}!../../../node_modules/vue-loader/lib/selector.js?type=styles&index=0!./zwAppTopBar.vue");
     if(typeof newContent === 'string') newContent = [[module.id, newContent, '']];
     update(newContent);
   });
 }
 // When the module is disposed, remove the <style> tags
 module.hot.dispose(function() { update(); });
}

/***/ }),

/***/ 289:
/***/ (function(module, exports, __webpack_require__) {

// style-loader: Adds some css to the DOM by adding a <style> tag

// load the styles
var content = __webpack_require__(203);
if(typeof content === 'string') content = [[module.i, content, '']];
if(content.locals) module.exports = content.locals;
// add the styles to the DOM
var update = __webpack_require__(1)("354145dc", content, false);
// Hot Module Replacement
if(false) {
 // When the styles change, update the <style> tags
 if(!content.locals) {
   module.hot.accept("!!../../../node_modules/css-loader/index.js!../../../node_modules/vue-loader/lib/style-compiler/index.js?{\"vue\":true,\"id\":\"data-v-ed137c3c\",\"scoped\":false,\"hasInlineConfig\":false}!../../../node_modules/vue-loader/lib/selector.js?type=styles&index=0!./zwTabs.vue", function() {
     var newContent = require("!!../../../node_modules/css-loader/index.js!../../../node_modules/vue-loader/lib/style-compiler/index.js?{\"vue\":true,\"id\":\"data-v-ed137c3c\",\"scoped\":false,\"hasInlineConfig\":false}!../../../node_modules/vue-loader/lib/selector.js?type=styles&index=0!./zwTabs.vue");
     if(typeof newContent === 'string') newContent = [[module.id, newContent, '']];
     update(newContent);
   });
 }
 // When the module is disposed, remove the <style> tags
 module.hot.dispose(function() { update(); });
}

/***/ }),

/***/ 291:
/***/ (function(module, exports, __webpack_require__) {

// style-loader: Adds some css to the DOM by adding a <style> tag

// load the styles
var content = __webpack_require__(205);
if(typeof content === 'string') content = [[module.i, content, '']];
if(content.locals) module.exports = content.locals;
// add the styles to the DOM
var update = __webpack_require__(1)("67b3037c", content, false);
// Hot Module Replacement
if(false) {
 // When the styles change, update the <style> tags
 if(!content.locals) {
   module.hot.accept("!!../../../node_modules/css-loader/index.js!../../../node_modules/vue-loader/lib/style-compiler/index.js?{\"vue\":true,\"id\":\"data-v-f4b72eca\",\"scoped\":false,\"hasInlineConfig\":false}!../../../node_modules/vue-loader/lib/selector.js?type=styles&index=0!./zwAppTitle.vue", function() {
     var newContent = require("!!../../../node_modules/css-loader/index.js!../../../node_modules/vue-loader/lib/style-compiler/index.js?{\"vue\":true,\"id\":\"data-v-f4b72eca\",\"scoped\":false,\"hasInlineConfig\":false}!../../../node_modules/vue-loader/lib/selector.js?type=styles&index=0!./zwAppTitle.vue");
     if(typeof newContent === 'string') newContent = [[module.id, newContent, '']];
     update(newContent);
   });
 }
 // When the module is disposed, remove the <style> tags
 module.hot.dispose(function() { update(); });
}

/***/ }),

/***/ 3:
/***/ (function(module, exports, __webpack_require__) {

var disposed = false
var Component = __webpack_require__(2)(
  /* script */
  __webpack_require__(90),
  /* template */
  __webpack_require__(117),
  /* styles */
  null,
  /* scopeId */
  null,
  /* moduleIdentifier (server only) */
  null
)
Component.options.__file = "G:\\workspace\\dozenx\\ui\\webpack\\app\\component\\icon\\zwIcon.vue"
if (Component.esModule && Object.keys(Component.esModule).some(function (key) {return key !== "default" && key.substr(0, 2) !== "__"})) {console.error("named exports are not supported in *.vue files.")}
if (Component.options.functional) {console.error("[vue-loader] zwIcon.vue: functional components are not supported with templates, they should use render functions.")}

/* hot reload */
if (false) {(function () {
  var hotAPI = require("vue-hot-reload-api")
  hotAPI.install(require("vue"), false)
  if (!hotAPI.compatible) return
  module.hot.accept()
  if (!module.hot.data) {
    hotAPI.createRecord("data-v-aa4ed928", Component.options)
  } else {
    hotAPI.reload("data-v-aa4ed928", Component.options)
  }
  module.hot.dispose(function (data) {
    disposed = true
  })
})()}

module.exports = Component.exports


/***/ }),

/***/ 33:
/***/ (function(module, exports, __webpack_require__) {

var disposed = false
function injectStyle (ssrContext) {
  if (disposed) return
  __webpack_require__(291)
}
var Component = __webpack_require__(2)(
  /* script */
  __webpack_require__(144),
  /* template */
  __webpack_require__(258),
  /* styles */
  injectStyle,
  /* scopeId */
  null,
  /* moduleIdentifier (server only) */
  null
)
Component.options.__file = "G:\\workspace\\dozenx\\ui\\webpack\\app\\component\\datadisplay\\zwAppTitle.vue"
if (Component.esModule && Object.keys(Component.esModule).some(function (key) {return key !== "default" && key.substr(0, 2) !== "__"})) {console.error("named exports are not supported in *.vue files.")}
if (Component.options.functional) {console.error("[vue-loader] zwAppTitle.vue: functional components are not supported with templates, they should use render functions.")}

/* hot reload */
if (false) {(function () {
  var hotAPI = require("vue-hot-reload-api")
  hotAPI.install(require("vue"), false)
  if (!hotAPI.compatible) return
  module.hot.accept()
  if (!module.hot.data) {
    hotAPI.createRecord("data-v-f4b72eca", Component.options)
  } else {
    hotAPI.reload("data-v-f4b72eca", Component.options)
  }
  module.hot.dispose(function (data) {
    disposed = true
  })
})()}

module.exports = Component.exports


/***/ }),

/***/ 35:
/***/ (function(module, exports, __webpack_require__) {

var disposed = false
function injectStyle (ssrContext) {
  if (disposed) return
  __webpack_require__(284)
}
var Component = __webpack_require__(2)(
  /* script */
  __webpack_require__(172),
  /* template */
  __webpack_require__(251),
  /* styles */
  injectStyle,
  /* scopeId */
  "data-v-a1a1d9c4",
  /* moduleIdentifier (server only) */
  null
)
Component.options.__file = "G:\\workspace\\dozenx\\ui\\webpack\\app\\component\\zwBase.vue"
if (Component.esModule && Object.keys(Component.esModule).some(function (key) {return key !== "default" && key.substr(0, 2) !== "__"})) {console.error("named exports are not supported in *.vue files.")}
if (Component.options.functional) {console.error("[vue-loader] zwBase.vue: functional components are not supported with templates, they should use render functions.")}

/* hot reload */
if (false) {(function () {
  var hotAPI = require("vue-hot-reload-api")
  hotAPI.install(require("vue"), false)
  if (!hotAPI.compatible) return
  module.hot.accept()
  if (!module.hot.data) {
    hotAPI.createRecord("data-v-a1a1d9c4", Component.options)
  } else {
    hotAPI.reload("data-v-a1a1d9c4", Component.options)
  }
  module.hot.dispose(function (data) {
    disposed = true
  })
})()}

module.exports = Component.exports


/***/ }),

/***/ 4:
/***/ (function(module, exports, __webpack_require__) {

var disposed = false
var Component = __webpack_require__(2)(
  /* script */
  __webpack_require__(91),
  /* template */
  __webpack_require__(116),
  /* styles */
  null,
  /* scopeId */
  null,
  /* moduleIdentifier (server only) */
  null
)
Component.options.__file = "G:\\workspace\\dozenx\\ui\\webpack\\app\\component\\layout\\zwCol.vue"
if (Component.esModule && Object.keys(Component.esModule).some(function (key) {return key !== "default" && key.substr(0, 2) !== "__"})) {console.error("named exports are not supported in *.vue files.")}
if (Component.options.functional) {console.error("[vue-loader] zwCol.vue: functional components are not supported with templates, they should use render functions.")}

/* hot reload */
if (false) {(function () {
  var hotAPI = require("vue-hot-reload-api")
  hotAPI.install(require("vue"), false)
  if (!hotAPI.compatible) return
  module.hot.accept()
  if (!module.hot.data) {
    hotAPI.createRecord("data-v-91ddb964", Component.options)
  } else {
    hotAPI.reload("data-v-91ddb964", Component.options)
  }
  module.hot.dispose(function (data) {
    disposed = true
  })
})()}

module.exports = Component.exports


/***/ }),

/***/ 43:
/***/ (function(module, exports, __webpack_require__) {

"use strict";
/* WEBPACK VAR INJECTION */(function(process, global) {/*!
 * Vue.js v2.3.4
 * (c) 2014-2017 Evan You
 * Released under the MIT License.
 */


/*  */

// these helpers produces better vm code in JS engines due to their
// explicitness and function inlining

var _typeof = typeof Symbol === "function" && typeof Symbol.iterator === "symbol" ? function (obj) { return typeof obj; } : function (obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj; };

function isUndef(v) {
  return v === undefined || v === null;
}

function isDef(v) {
  return v !== undefined && v !== null;
}

function isTrue(v) {
  return v === true;
}

function isFalse(v) {
  return v === false;
}
/**
 * Check if value is primitive
 */
function isPrimitive(value) {
  return typeof value === 'string' || typeof value === 'number';
}

/**
 * Quick object check - this is primarily used to tell
 * Objects from primitive values when we know the value
 * is a JSON-compliant type.
 */
function isObject(obj) {
  return obj !== null && (typeof obj === 'undefined' ? 'undefined' : _typeof(obj)) === 'object';
}

var _toString = Object.prototype.toString;

/**
 * Strict object type check. Only returns true
 * for plain JavaScript objects.
 */
function isPlainObject(obj) {
  return _toString.call(obj) === '[object Object]';
}

function isRegExp(v) {
  return _toString.call(v) === '[object RegExp]';
}

/**
 * Convert a value to a string that is actually rendered.
 */
function toString(val) {
  return val == null ? '' : (typeof val === 'undefined' ? 'undefined' : _typeof(val)) === 'object' ? JSON.stringify(val, null, 2) : String(val);
}

/**
 * Convert a input value to a number for persistence.
 * If the conversion fails, return original string.
 */
function toNumber(val) {
  var n = parseFloat(val);
  return isNaN(n) ? val : n;
}

/**
 * Make a map and return a function for checking if a key
 * is in that map.
 */
function makeMap(str, expectsLowerCase) {
  var map = Object.create(null);
  var list = str.split(',');
  for (var i = 0; i < list.length; i++) {
    map[list[i]] = true;
  }
  return expectsLowerCase ? function (val) {
    return map[val.toLowerCase()];
  } : function (val) {
    return map[val];
  };
}

/**
 * Check if a tag is a built-in tag.
 */
var isBuiltInTag = makeMap('slot,component', true);

/**
 * Remove an item from an array
 */
function remove(arr, item) {
  if (arr.length) {
    var index = arr.indexOf(item);
    if (index > -1) {
      return arr.splice(index, 1);
    }
  }
}

/**
 * Check whether the object has the property.
 */
var hasOwnProperty = Object.prototype.hasOwnProperty;
function hasOwn(obj, key) {
  return hasOwnProperty.call(obj, key);
}

/**
 * Create a cached version of a pure function.
 */
function cached(fn) {
  var cache = Object.create(null);
  return function cachedFn(str) {
    var hit = cache[str];
    return hit || (cache[str] = fn(str));
  };
}

/**
 * Camelize a hyphen-delimited string.
 */
var camelizeRE = /-(\w)/g;
var camelize = cached(function (str) {
  return str.replace(camelizeRE, function (_, c) {
    return c ? c.toUpperCase() : '';
  });
});

/**
 * Capitalize a string.
 */
var capitalize = cached(function (str) {
  return str.charAt(0).toUpperCase() + str.slice(1);
});

/**
 * Hyphenate a camelCase string.
 */
var hyphenateRE = /([^-])([A-Z])/g;
var hyphenate = cached(function (str) {
  return str.replace(hyphenateRE, '$1-$2').replace(hyphenateRE, '$1-$2').toLowerCase();
});

/**
 * Simple bind, faster than native
 */
function bind(fn, ctx) {
  function boundFn(a) {
    var l = arguments.length;
    return l ? l > 1 ? fn.apply(ctx, arguments) : fn.call(ctx, a) : fn.call(ctx);
  }
  // record original fn length
  boundFn._length = fn.length;
  return boundFn;
}

/**
 * Convert an Array-like object to a real Array.
 */
function toArray(list, start) {
  start = start || 0;
  var i = list.length - start;
  var ret = new Array(i);
  while (i--) {
    ret[i] = list[i + start];
  }
  return ret;
}

/**
 * Mix properties into target object.
 */
function extend(to, _from) {
  for (var key in _from) {
    to[key] = _from[key];
  }
  return to;
}

/**
 * Merge an Array of Objects into a single Object.
 */
function toObject(arr) {
  var res = {};
  for (var i = 0; i < arr.length; i++) {
    if (arr[i]) {
      extend(res, arr[i]);
    }
  }
  return res;
}

/**
 * Perform no operation.
 */
function noop() {}

/**
 * Always return false.
 */
var no = function no() {
  return false;
};

/**
 * Return same value
 */
var identity = function identity(_) {
  return _;
};

/**
 * Generate a static keys string from compiler modules.
 */
function genStaticKeys(modules) {
  return modules.reduce(function (keys, m) {
    return keys.concat(m.staticKeys || []);
  }, []).join(',');
}

/**
 * Check if two values are loosely equal - that is,
 * if they are plain objects, do they have the same shape?
 */
function looseEqual(a, b) {
  var isObjectA = isObject(a);
  var isObjectB = isObject(b);
  if (isObjectA && isObjectB) {
    try {
      return JSON.stringify(a) === JSON.stringify(b);
    } catch (e) {
      // possible circular reference
      return a === b;
    }
  } else if (!isObjectA && !isObjectB) {
    return String(a) === String(b);
  } else {
    return false;
  }
}

function looseIndexOf(arr, val) {
  for (var i = 0; i < arr.length; i++) {
    if (looseEqual(arr[i], val)) {
      return i;
    }
  }
  return -1;
}

/**
 * Ensure a function is called only once.
 */
function once(fn) {
  var called = false;
  return function () {
    if (!called) {
      called = true;
      fn.apply(this, arguments);
    }
  };
}

var SSR_ATTR = 'data-server-rendered';

var ASSET_TYPES = ['component', 'directive', 'filter'];

var LIFECYCLE_HOOKS = ['beforeCreate', 'created', 'beforeMount', 'mounted', 'beforeUpdate', 'updated', 'beforeDestroy', 'destroyed', 'activated', 'deactivated'];

/*  */

var config = {
  /**
   * Option merge strategies (used in core/util/options)
   */
  optionMergeStrategies: Object.create(null),

  /**
   * Whether to suppress warnings.
   */
  silent: false,

  /**
   * Show production mode tip message on boot?
   */
  productionTip: process.env.NODE_ENV !== 'production',

  /**
   * Whether to enable devtools
   */
  devtools: process.env.NODE_ENV !== 'production',

  /**
   * Whether to record perf
   */
  performance: false,

  /**
   * Error handler for watcher errors
   */
  errorHandler: null,

  /**
   * Ignore certain custom elements
   */
  ignoredElements: [],

  /**
   * Custom user key aliases for v-on
   */
  keyCodes: Object.create(null),

  /**
   * Check if a tag is reserved so that it cannot be registered as a
   * component. This is platform-dependent and may be overwritten.
   */
  isReservedTag: no,

  /**
   * Check if an attribute is reserved so that it cannot be used as a component
   * prop. This is platform-dependent and may be overwritten.
   */
  isReservedAttr: no,

  /**
   * Check if a tag is an unknown element.
   * Platform-dependent.
   */
  isUnknownElement: no,

  /**
   * Get the namespace of an element
   */
  getTagNamespace: noop,

  /**
   * Parse the real tag name for the specific platform.
   */
  parsePlatformTagName: identity,

  /**
   * Check if an attribute must be bound using property, e.g. value
   * Platform-dependent.
   */
  mustUseProp: no,

  /**
   * Exposed for legacy reasons
   */
  _lifecycleHooks: LIFECYCLE_HOOKS
};

/*  */

var emptyObject = Object.freeze({});

/**
 * Check if a string starts with $ or _
 */
function isReserved(str) {
  var c = (str + '').charCodeAt(0);
  return c === 0x24 || c === 0x5F;
}

/**
 * Define a property.
 */
function def(obj, key, val, enumerable) {
  Object.defineProperty(obj, key, {
    value: val,
    enumerable: !!enumerable,
    writable: true,
    configurable: true
  });
}

/**
 * Parse simple path.
 */
var bailRE = /[^\w.$]/;
function parsePath(path) {
  if (bailRE.test(path)) {
    return;
  }
  var segments = path.split('.');
  return function (obj) {
    for (var i = 0; i < segments.length; i++) {
      if (!obj) {
        return;
      }
      obj = obj[segments[i]];
    }
    return obj;
  };
}

/*  */

var warn = noop;
var tip = noop;
var formatComponentName = null; // work around flow check

if (process.env.NODE_ENV !== 'production') {
  var hasConsole = typeof console !== 'undefined';
  var classifyRE = /(?:^|[-_])(\w)/g;
  var classify = function classify(str) {
    return str.replace(classifyRE, function (c) {
      return c.toUpperCase();
    }).replace(/[-_]/g, '');
  };

  warn = function warn(msg, vm) {
    if (hasConsole && !config.silent) {
      console.error("[Vue warn]: " + msg + (vm ? generateComponentTrace(vm) : ''));
    }
  };

  tip = function tip(msg, vm) {
    if (hasConsole && !config.silent) {
      console.warn("[Vue tip]: " + msg + (vm ? generateComponentTrace(vm) : ''));
    }
  };

  formatComponentName = function formatComponentName(vm, includeFile) {
    if (vm.$root === vm) {
      return '<Root>';
    }
    var name = typeof vm === 'string' ? vm : typeof vm === 'function' && vm.options ? vm.options.name : vm._isVue ? vm.$options.name || vm.$options._componentTag : vm.name;

    var file = vm._isVue && vm.$options.__file;
    if (!name && file) {
      var match = file.match(/([^/\\]+)\.vue$/);
      name = match && match[1];
    }

    return (name ? "<" + classify(name) + ">" : "<Anonymous>") + (file && includeFile !== false ? " at " + file : '');
  };

  var repeat = function repeat(str, n) {
    var res = '';
    while (n) {
      if (n % 2 === 1) {
        res += str;
      }
      if (n > 1) {
        str += str;
      }
      n >>= 1;
    }
    return res;
  };

  var generateComponentTrace = function generateComponentTrace(vm) {
    if (vm._isVue && vm.$parent) {
      var tree = [];
      var currentRecursiveSequence = 0;
      while (vm) {
        if (tree.length > 0) {
          var last = tree[tree.length - 1];
          if (last.constructor === vm.constructor) {
            currentRecursiveSequence++;
            vm = vm.$parent;
            continue;
          } else if (currentRecursiveSequence > 0) {
            tree[tree.length - 1] = [last, currentRecursiveSequence];
            currentRecursiveSequence = 0;
          }
        }
        tree.push(vm);
        vm = vm.$parent;
      }
      return '\n\nfound in\n\n' + tree.map(function (vm, i) {
        return "" + (i === 0 ? '---> ' : repeat(' ', 5 + i * 2)) + (Array.isArray(vm) ? formatComponentName(vm[0]) + "... (" + vm[1] + " recursive calls)" : formatComponentName(vm));
      }).join('\n');
    } else {
      return "\n\n(found in " + formatComponentName(vm) + ")";
    }
  };
}

/*  */

function handleError(err, vm, info) {
  if (config.errorHandler) {
    config.errorHandler.call(null, err, vm, info);
  } else {
    if (process.env.NODE_ENV !== 'production') {
      warn("Error in " + info + ": \"" + err.toString() + "\"", vm);
    }
    /* istanbul ignore else */
    if (inBrowser && typeof console !== 'undefined') {
      console.error(err);
    } else {
      throw err;
    }
  }
}

/*  */
/* globals MutationObserver */

// can we use __proto__?
var hasProto = '__proto__' in {};

// Browser environment sniffing
var inBrowser = typeof window !== 'undefined';
var UA = inBrowser && window.navigator.userAgent.toLowerCase();
var isIE = UA && /msie|trident/.test(UA);
var isIE9 = UA && UA.indexOf('msie 9.0') > 0;
var isEdge = UA && UA.indexOf('edge/') > 0;
var isAndroid = UA && UA.indexOf('android') > 0;
var isIOS = UA && /iphone|ipad|ipod|ios/.test(UA);
var isChrome = UA && /chrome\/\d+/.test(UA) && !isEdge;

var supportsPassive = false;
if (inBrowser) {
  try {
    var opts = {};
    Object.defineProperty(opts, 'passive', {
      get: function get() {
        /* istanbul ignore next */
        supportsPassive = true;
      }
    }); // https://github.com/facebook/flow/issues/285
    window.addEventListener('test-passive', null, opts);
  } catch (e) {}
}

// this needs to be lazy-evaled because vue may be required before
// vue-server-renderer can set VUE_ENV
var _isServer;
var isServerRendering = function isServerRendering() {
  if (_isServer === undefined) {
    /* istanbul ignore if */
    if (!inBrowser && typeof global !== 'undefined') {
      // detect presence of vue-server-renderer and avoid
      // Webpack shimming the process
      _isServer = global['process'].env.VUE_ENV === 'server';
    } else {
      _isServer = false;
    }
  }
  return _isServer;
};

// detect devtools
var devtools = inBrowser && window.__VUE_DEVTOOLS_GLOBAL_HOOK__;

/* istanbul ignore next */
function isNative(Ctor) {
  return typeof Ctor === 'function' && /native code/.test(Ctor.toString());
}

var hasSymbol = typeof Symbol !== 'undefined' && isNative(Symbol) && typeof Reflect !== 'undefined' && isNative(Reflect.ownKeys);

/**
 * Defer a task to execute it asynchronously.
 */
var nextTick = function () {
  var callbacks = [];
  var pending = false;
  var timerFunc;

  function nextTickHandler() {
    pending = false;
    var copies = callbacks.slice(0);
    callbacks.length = 0;
    for (var i = 0; i < copies.length; i++) {
      copies[i]();
    }
  }

  // the nextTick behavior leverages the microtask queue, which can be accessed
  // via either native Promise.then or MutationObserver.
  // MutationObserver has wider support, however it is seriously bugged in
  // UIWebView in iOS >= 9.3.3 when triggered in touch event handlers. It
  // completely stops working after triggering a few times... so, if native
  // Promise is available, we will use it:
  /* istanbul ignore if */
  if (typeof Promise !== 'undefined' && isNative(Promise)) {
    var p = Promise.resolve();
    var logError = function logError(err) {
      console.error(err);
    };
    timerFunc = function timerFunc() {
      p.then(nextTickHandler).catch(logError);
      // in problematic UIWebViews, Promise.then doesn't completely break, but
      // it can get stuck in a weird state where callbacks are pushed into the
      // microtask queue but the queue isn't being flushed, until the browser
      // needs to do some other work, e.g. handle a timer. Therefore we can
      // "force" the microtask queue to be flushed by adding an empty timer.
      if (isIOS) {
        setTimeout(noop);
      }
    };
  } else if (typeof MutationObserver !== 'undefined' && (isNative(MutationObserver) ||
  // PhantomJS and iOS 7.x
  MutationObserver.toString() === '[object MutationObserverConstructor]')) {
    // use MutationObserver where native Promise is not available,
    // e.g. PhantomJS IE11, iOS7, Android 4.4
    var counter = 1;
    var observer = new MutationObserver(nextTickHandler);
    var textNode = document.createTextNode(String(counter));
    observer.observe(textNode, {
      characterData: true
    });
    timerFunc = function timerFunc() {
      counter = (counter + 1) % 2;
      textNode.data = String(counter);
    };
  } else {
    // fallback to setTimeout
    /* istanbul ignore next */
    timerFunc = function timerFunc() {
      setTimeout(nextTickHandler, 0);
    };
  }

  return function queueNextTick(cb, ctx) {
    var _resolve;
    callbacks.push(function () {
      if (cb) {
        try {
          cb.call(ctx);
        } catch (e) {
          handleError(e, ctx, 'nextTick');
        }
      } else if (_resolve) {
        _resolve(ctx);
      }
    });
    if (!pending) {
      pending = true;
      timerFunc();
    }
    if (!cb && typeof Promise !== 'undefined') {
      return new Promise(function (resolve, reject) {
        _resolve = resolve;
      });
    }
  };
}();

var _Set;
/* istanbul ignore if */
if (typeof Set !== 'undefined' && isNative(Set)) {
  // use native Set when available.
  _Set = Set;
} else {
  // a non-standard Set polyfill that only works with primitive keys.
  _Set = function () {
    function Set() {
      this.set = Object.create(null);
    }
    Set.prototype.has = function has(key) {
      return this.set[key] === true;
    };
    Set.prototype.add = function add(key) {
      this.set[key] = true;
    };
    Set.prototype.clear = function clear() {
      this.set = Object.create(null);
    };

    return Set;
  }();
}

/*  */

var uid = 0;

/**
 * A dep is an observable that can have multiple
 * directives subscribing to it.
 */
var Dep = function Dep() {
  this.id = uid++;
  this.subs = [];
};

Dep.prototype.addSub = function addSub(sub) {
  this.subs.push(sub);
};

Dep.prototype.removeSub = function removeSub(sub) {
  remove(this.subs, sub);
};

Dep.prototype.depend = function depend() {
  if (Dep.target) {
    Dep.target.addDep(this);
  }
};

Dep.prototype.notify = function notify() {
  // stabilize the subscriber list first
  var subs = this.subs.slice();
  for (var i = 0, l = subs.length; i < l; i++) {
    subs[i].update();
  }
};

// the current target watcher being evaluated.
// this is globally unique because there could be only one
// watcher being evaluated at any time.
Dep.target = null;
var targetStack = [];

function pushTarget(_target) {
  if (Dep.target) {
    targetStack.push(Dep.target);
  }
  Dep.target = _target;
}

function popTarget() {
  Dep.target = targetStack.pop();
}

/*
 * not type checking this file because flow doesn't play well with
 * dynamically accessing methods on Array prototype
 */

var arrayProto = Array.prototype;
var arrayMethods = Object.create(arrayProto);['push', 'pop', 'shift', 'unshift', 'splice', 'sort', 'reverse'].forEach(function (method) {
  // cache original method
  var original = arrayProto[method];
  def(arrayMethods, method, function mutator() {
    var arguments$1 = arguments;

    // avoid leaking arguments:
    // http://jsperf.com/closure-with-arguments
    var i = arguments.length;
    var args = new Array(i);
    while (i--) {
      args[i] = arguments$1[i];
    }
    var result = original.apply(this, args);
    var ob = this.__ob__;
    var inserted;
    switch (method) {
      case 'push':
        inserted = args;
        break;
      case 'unshift':
        inserted = args;
        break;
      case 'splice':
        inserted = args.slice(2);
        break;
    }
    if (inserted) {
      ob.observeArray(inserted);
    }
    // notify change
    ob.dep.notify();
    return result;
  });
});

/*  */

var arrayKeys = Object.getOwnPropertyNames(arrayMethods);

/**
 * By default, when a reactive property is set, the new value is
 * also converted to become reactive. However when passing down props,
 * we don't want to force conversion because the value may be a nested value
 * under a frozen data structure. Converting it would defeat the optimization.
 */
var observerState = {
  shouldConvert: true,
  isSettingProps: false
};

/**
 * Observer class that are attached to each observed
 * object. Once attached, the observer converts target
 * object's property keys into getter/setters that
 * collect dependencies and dispatches updates.
 */
var Observer = function Observer(value) {
  this.value = value;
  this.dep = new Dep();
  this.vmCount = 0;
  def(value, '__ob__', this);
  if (Array.isArray(value)) {
    var augment = hasProto ? protoAugment : copyAugment;
    augment(value, arrayMethods, arrayKeys);
    this.observeArray(value);
  } else {
    this.walk(value);
  }
};

/**
 * Walk through each property and convert them into
 * getter/setters. This method should only be called when
 * value type is Object.
 */
Observer.prototype.walk = function walk(obj) {
  var keys = Object.keys(obj);
  for (var i = 0; i < keys.length; i++) {
    defineReactive$$1(obj, keys[i], obj[keys[i]]);
  }
};

/**
 * Observe a list of Array items.
 */
Observer.prototype.observeArray = function observeArray(items) {
  for (var i = 0, l = items.length; i < l; i++) {
    observe(items[i]);
  }
};

// helpers

/**
 * Augment an target Object or Array by intercepting
 * the prototype chain using __proto__
 */
function protoAugment(target, src) {
  /* eslint-disable no-proto */
  target.__proto__ = src;
  /* eslint-enable no-proto */
}

/**
 * Augment an target Object or Array by defining
 * hidden properties.
 */
/* istanbul ignore next */
function copyAugment(target, src, keys) {
  for (var i = 0, l = keys.length; i < l; i++) {
    var key = keys[i];
    def(target, key, src[key]);
  }
}

/**
 * Attempt to create an observer instance for a value,
 * returns the new observer if successfully observed,
 * or the existing observer if the value already has one.
 */
function observe(value, asRootData) {
  if (!isObject(value)) {
    return;
  }
  var ob;
  if (hasOwn(value, '__ob__') && value.__ob__ instanceof Observer) {
    ob = value.__ob__;
  } else if (observerState.shouldConvert && !isServerRendering() && (Array.isArray(value) || isPlainObject(value)) && Object.isExtensible(value) && !value._isVue) {
    ob = new Observer(value);
  }
  if (asRootData && ob) {
    ob.vmCount++;
  }
  return ob;
}

/**
 * Define a reactive property on an Object.
 */
function defineReactive$$1(obj, key, val, customSetter) {
  var dep = new Dep();

  var property = Object.getOwnPropertyDescriptor(obj, key);
  if (property && property.configurable === false) {
    return;
  }

  // cater for pre-defined getter/setters
  var getter = property && property.get;
  var setter = property && property.set;

  var childOb = observe(val);
  Object.defineProperty(obj, key, {
    enumerable: true,
    configurable: true,
    get: function reactiveGetter() {
      var value = getter ? getter.call(obj) : val;
      if (Dep.target) {
        dep.depend();
        if (childOb) {
          childOb.dep.depend();
        }
        if (Array.isArray(value)) {
          dependArray(value);
        }
      }
      return value;
    },
    set: function reactiveSetter(newVal) {
      var value = getter ? getter.call(obj) : val;
      /* eslint-disable no-self-compare */
      if (newVal === value || newVal !== newVal && value !== value) {
        return;
      }
      /* eslint-enable no-self-compare */
      if (process.env.NODE_ENV !== 'production' && customSetter) {
        customSetter();
      }
      if (setter) {
        setter.call(obj, newVal);
      } else {
        val = newVal;
      }
      childOb = observe(newVal);
      dep.notify();
    }
  });
}

/**
 * Set a property on an object. Adds the new property and
 * triggers change notification if the property doesn't
 * already exist.
 */
function set(target, key, val) {
  if (Array.isArray(target) && typeof key === 'number') {
    target.length = Math.max(target.length, key);
    target.splice(key, 1, val);
    return val;
  }
  if (hasOwn(target, key)) {
    target[key] = val;
    return val;
  }
  var ob = target.__ob__;
  if (target._isVue || ob && ob.vmCount) {
    process.env.NODE_ENV !== 'production' && warn('Avoid adding reactive properties to a Vue instance or its root $data ' + 'at runtime - declare it upfront in the data option.');
    return val;
  }
  if (!ob) {
    target[key] = val;
    return val;
  }
  defineReactive$$1(ob.value, key, val);
  ob.dep.notify();
  return val;
}

/**
 * Delete a property and trigger change if necessary.
 */
function del(target, key) {
  if (Array.isArray(target) && typeof key === 'number') {
    target.splice(key, 1);
    return;
  }
  var ob = target.__ob__;
  if (target._isVue || ob && ob.vmCount) {
    process.env.NODE_ENV !== 'production' && warn('Avoid deleting properties on a Vue instance or its root $data ' + '- just set it to null.');
    return;
  }
  if (!hasOwn(target, key)) {
    return;
  }
  delete target[key];
  if (!ob) {
    return;
  }
  ob.dep.notify();
}

/**
 * Collect dependencies on array elements when the array is touched, since
 * we cannot intercept array element access like property getters.
 */
function dependArray(value) {
  for (var e = void 0, i = 0, l = value.length; i < l; i++) {
    e = value[i];
    e && e.__ob__ && e.__ob__.dep.depend();
    if (Array.isArray(e)) {
      dependArray(e);
    }
  }
}

/*  */

/**
 * Option overwriting strategies are functions that handle
 * how to merge a parent option value and a child option
 * value into the final value.
 */
var strats = config.optionMergeStrategies;

/**
 * Options with restrictions
 */
if (process.env.NODE_ENV !== 'production') {
  strats.el = strats.propsData = function (parent, child, vm, key) {
    if (!vm) {
      warn("option \"" + key + "\" can only be used during instance " + 'creation with the `new` keyword.');
    }
    return defaultStrat(parent, child);
  };
}

/**
 * Helper that recursively merges two data objects together.
 */
function mergeData(to, from) {
  if (!from) {
    return to;
  }
  var key, toVal, fromVal;
  var keys = Object.keys(from);
  for (var i = 0; i < keys.length; i++) {
    key = keys[i];
    toVal = to[key];
    fromVal = from[key];
    if (!hasOwn(to, key)) {
      set(to, key, fromVal);
    } else if (isPlainObject(toVal) && isPlainObject(fromVal)) {
      mergeData(toVal, fromVal);
    }
  }
  return to;
}

/**
 * Data
 */
strats.data = function (parentVal, childVal, vm) {
  if (!vm) {
    // in a Vue.extend merge, both should be functions
    if (!childVal) {
      return parentVal;
    }
    if (typeof childVal !== 'function') {
      process.env.NODE_ENV !== 'production' && warn('The "data" option should be a function ' + 'that returns a per-instance value in component ' + 'definitions.', vm);
      return parentVal;
    }
    if (!parentVal) {
      return childVal;
    }
    // when parentVal & childVal are both present,
    // we need to return a function that returns the
    // merged result of both functions... no need to
    // check if parentVal is a function here because
    // it has to be a function to pass previous merges.
    return function mergedDataFn() {
      return mergeData(childVal.call(this), parentVal.call(this));
    };
  } else if (parentVal || childVal) {
    return function mergedInstanceDataFn() {
      // instance merge
      var instanceData = typeof childVal === 'function' ? childVal.call(vm) : childVal;
      var defaultData = typeof parentVal === 'function' ? parentVal.call(vm) : undefined;
      if (instanceData) {
        return mergeData(instanceData, defaultData);
      } else {
        return defaultData;
      }
    };
  }
};

/**
 * Hooks and props are merged as arrays.
 */
function mergeHook(parentVal, childVal) {
  return childVal ? parentVal ? parentVal.concat(childVal) : Array.isArray(childVal) ? childVal : [childVal] : parentVal;
}

LIFECYCLE_HOOKS.forEach(function (hook) {
  strats[hook] = mergeHook;
});

/**
 * Assets
 *
 * When a vm is present (instance creation), we need to do
 * a three-way merge between constructor options, instance
 * options and parent options.
 */
function mergeAssets(parentVal, childVal) {
  var res = Object.create(parentVal || null);
  return childVal ? extend(res, childVal) : res;
}

ASSET_TYPES.forEach(function (type) {
  strats[type + 's'] = mergeAssets;
});

/**
 * Watchers.
 *
 * Watchers hashes should not overwrite one
 * another, so we merge them as arrays.
 */
strats.watch = function (parentVal, childVal) {
  /* istanbul ignore if */
  if (!childVal) {
    return Object.create(parentVal || null);
  }
  if (!parentVal) {
    return childVal;
  }
  var ret = {};
  extend(ret, parentVal);
  for (var key in childVal) {
    var parent = ret[key];
    var child = childVal[key];
    if (parent && !Array.isArray(parent)) {
      parent = [parent];
    }
    ret[key] = parent ? parent.concat(child) : [child];
  }
  return ret;
};

/**
 * Other object hashes.
 */
strats.props = strats.methods = strats.computed = function (parentVal, childVal) {
  if (!childVal) {
    return Object.create(parentVal || null);
  }
  if (!parentVal) {
    return childVal;
  }
  var ret = Object.create(null);
  extend(ret, parentVal);
  extend(ret, childVal);
  return ret;
};

/**
 * Default strategy.
 */
var defaultStrat = function defaultStrat(parentVal, childVal) {
  return childVal === undefined ? parentVal : childVal;
};

/**
 * Validate component names
 */
function checkComponents(options) {
  for (var key in options.components) {
    var lower = key.toLowerCase();
    if (isBuiltInTag(lower) || config.isReservedTag(lower)) {
      warn('Do not use built-in or reserved HTML elements as component ' + 'id: ' + key);
    }
  }
}

/**
 * Ensure all props option syntax are normalized into the
 * Object-based format.
 */
function normalizeProps(options) {
  var props = options.props;
  if (!props) {
    return;
  }
  var res = {};
  var i, val, name;
  if (Array.isArray(props)) {
    i = props.length;
    while (i--) {
      val = props[i];
      if (typeof val === 'string') {
        name = camelize(val);
        res[name] = { type: null };
      } else if (process.env.NODE_ENV !== 'production') {
        warn('props must be strings when using array syntax.');
      }
    }
  } else if (isPlainObject(props)) {
    for (var key in props) {
      val = props[key];
      name = camelize(key);
      res[name] = isPlainObject(val) ? val : { type: val };
    }
  }
  options.props = res;
}

/**
 * Normalize raw function directives into object format.
 */
function normalizeDirectives(options) {
  var dirs = options.directives;
  if (dirs) {
    for (var key in dirs) {
      var def = dirs[key];
      if (typeof def === 'function') {
        dirs[key] = { bind: def, update: def };
      }
    }
  }
}

/**
 * Merge two option objects into a new one.
 * Core utility used in both instantiation and inheritance.
 */
function mergeOptions(parent, child, vm) {
  if (process.env.NODE_ENV !== 'production') {
    checkComponents(child);
  }

  if (typeof child === 'function') {
    child = child.options;
  }

  normalizeProps(child);
  normalizeDirectives(child);
  var extendsFrom = child.extends;
  if (extendsFrom) {
    parent = mergeOptions(parent, extendsFrom, vm);
  }
  if (child.mixins) {
    for (var i = 0, l = child.mixins.length; i < l; i++) {
      parent = mergeOptions(parent, child.mixins[i], vm);
    }
  }
  var options = {};
  var key;
  for (key in parent) {
    mergeField(key);
  }
  for (key in child) {
    if (!hasOwn(parent, key)) {
      mergeField(key);
    }
  }
  function mergeField(key) {
    var strat = strats[key] || defaultStrat;
    options[key] = strat(parent[key], child[key], vm, key);
  }
  return options;
}

/**
 * Resolve an asset.
 * This function is used because child instances need access
 * to assets defined in its ancestor chain.
 */
function resolveAsset(options, type, id, warnMissing) {
  /* istanbul ignore if */
  if (typeof id !== 'string') {
    return;
  }
  var assets = options[type];
  // check local registration variations first
  if (hasOwn(assets, id)) {
    return assets[id];
  }
  var camelizedId = camelize(id);
  if (hasOwn(assets, camelizedId)) {
    return assets[camelizedId];
  }
  var PascalCaseId = capitalize(camelizedId);
  if (hasOwn(assets, PascalCaseId)) {
    return assets[PascalCaseId];
  }
  // fallback to prototype chain
  var res = assets[id] || assets[camelizedId] || assets[PascalCaseId];
  if (process.env.NODE_ENV !== 'production' && warnMissing && !res) {
    warn('Failed to resolve ' + type.slice(0, -1) + ': ' + id, options);
  }
  return res;
}

/*  */

function validateProp(key, propOptions, propsData, vm) {
  var prop = propOptions[key];
  var absent = !hasOwn(propsData, key);
  var value = propsData[key];
  // handle boolean props
  if (isType(Boolean, prop.type)) {
    if (absent && !hasOwn(prop, 'default')) {
      value = false;
    } else if (!isType(String, prop.type) && (value === '' || value === hyphenate(key))) {
      value = true;
    }
  }
  // check default value
  if (value === undefined) {
    value = getPropDefaultValue(vm, prop, key);
    // since the default value is a fresh copy,
    // make sure to observe it.
    var prevShouldConvert = observerState.shouldConvert;
    observerState.shouldConvert = true;
    observe(value);
    observerState.shouldConvert = prevShouldConvert;
  }
  if (process.env.NODE_ENV !== 'production') {
    assertProp(prop, key, value, vm, absent);
  }
  return value;
}

/**
 * Get the default value of a prop.
 */
function getPropDefaultValue(vm, prop, key) {
  // no default, return undefined
  if (!hasOwn(prop, 'default')) {
    return undefined;
  }
  var def = prop.default;
  // warn against non-factory defaults for Object & Array
  if (process.env.NODE_ENV !== 'production' && isObject(def)) {
    warn('Invalid default value for prop "' + key + '": ' + 'Props with type Object/Array must use a factory function ' + 'to return the default value.', vm);
  }
  // the raw prop value was also undefined from previous render,
  // return previous default value to avoid unnecessary watcher trigger
  if (vm && vm.$options.propsData && vm.$options.propsData[key] === undefined && vm._props[key] !== undefined) {
    return vm._props[key];
  }
  // call factory function for non-Function types
  // a value is Function if its prototype is function even across different execution context
  return typeof def === 'function' && getType(prop.type) !== 'Function' ? def.call(vm) : def;
}

/**
 * Assert whether a prop is valid.
 */
function assertProp(prop, name, value, vm, absent) {
  if (prop.required && absent) {
    warn('Missing required prop: "' + name + '"', vm);
    return;
  }
  if (value == null && !prop.required) {
    return;
  }
  var type = prop.type;
  var valid = !type || type === true;
  var expectedTypes = [];
  if (type) {
    if (!Array.isArray(type)) {
      type = [type];
    }
    for (var i = 0; i < type.length && !valid; i++) {
      var assertedType = assertType(value, type[i]);
      expectedTypes.push(assertedType.expectedType || '');
      valid = assertedType.valid;
    }
  }
  if (!valid) {
    warn('Invalid prop: type check failed for prop "' + name + '".' + ' Expected ' + expectedTypes.map(capitalize).join(', ') + ', got ' + Object.prototype.toString.call(value).slice(8, -1) + '.', vm);
    return;
  }
  var validator = prop.validator;
  if (validator) {
    if (!validator(value)) {
      warn('Invalid prop: custom validator check failed for prop "' + name + '".', vm);
    }
  }
}

var simpleCheckRE = /^(String|Number|Boolean|Function|Symbol)$/;

function assertType(value, type) {
  var valid;
  var expectedType = getType(type);
  if (simpleCheckRE.test(expectedType)) {
    valid = (typeof value === 'undefined' ? 'undefined' : _typeof(value)) === expectedType.toLowerCase();
  } else if (expectedType === 'Object') {
    valid = isPlainObject(value);
  } else if (expectedType === 'Array') {
    valid = Array.isArray(value);
  } else {
    valid = value instanceof type;
  }
  return {
    valid: valid,
    expectedType: expectedType
  };
}

/**
 * Use function string name to check built-in types,
 * because a simple equality check will fail when running
 * across different vms / iframes.
 */
function getType(fn) {
  var match = fn && fn.toString().match(/^\s*function (\w+)/);
  return match ? match[1] : '';
}

function isType(type, fn) {
  if (!Array.isArray(fn)) {
    return getType(fn) === getType(type);
  }
  for (var i = 0, len = fn.length; i < len; i++) {
    if (getType(fn[i]) === getType(type)) {
      return true;
    }
  }
  /* istanbul ignore next */
  return false;
}

/*  */

var mark;
var measure;

if (process.env.NODE_ENV !== 'production') {
  var perf = inBrowser && window.performance;
  /* istanbul ignore if */
  if (perf && perf.mark && perf.measure && perf.clearMarks && perf.clearMeasures) {
    mark = function mark(tag) {
      return perf.mark(tag);
    };
    measure = function measure(name, startTag, endTag) {
      perf.measure(name, startTag, endTag);
      perf.clearMarks(startTag);
      perf.clearMarks(endTag);
      perf.clearMeasures(name);
    };
  }
}

/* not type checking this file because flow doesn't play well with Proxy */

var initProxy;

if (process.env.NODE_ENV !== 'production') {
  var allowedGlobals = makeMap('Infinity,undefined,NaN,isFinite,isNaN,' + 'parseFloat,parseInt,decodeURI,decodeURIComponent,encodeURI,encodeURIComponent,' + 'Math,Number,Date,Array,Object,Boolean,String,RegExp,Map,Set,JSON,Intl,' + 'require' // for Webpack/Browserify
  );

  var warnNonPresent = function warnNonPresent(target, key) {
    warn("Property or method \"" + key + "\" is not defined on the instance but " + "referenced during render. Make sure to declare reactive data " + "properties in the data option.", target);
  };

  var hasProxy = typeof Proxy !== 'undefined' && Proxy.toString().match(/native code/);

  if (hasProxy) {
    var isBuiltInModifier = makeMap('stop,prevent,self,ctrl,shift,alt,meta');
    config.keyCodes = new Proxy(config.keyCodes, {
      set: function set(target, key, value) {
        if (isBuiltInModifier(key)) {
          warn("Avoid overwriting built-in modifier in config.keyCodes: ." + key);
          return false;
        } else {
          target[key] = value;
          return true;
        }
      }
    });
  }

  var hasHandler = {
    has: function has(target, key) {
      var has = key in target;
      var isAllowed = allowedGlobals(key) || key.charAt(0) === '_';
      if (!has && !isAllowed) {
        warnNonPresent(target, key);
      }
      return has || !isAllowed;
    }
  };

  var getHandler = {
    get: function get(target, key) {
      if (typeof key === 'string' && !(key in target)) {
        warnNonPresent(target, key);
      }
      return target[key];
    }
  };

  initProxy = function initProxy(vm) {
    if (hasProxy) {
      // determine which proxy handler to use
      var options = vm.$options;
      var handlers = options.render && options.render._withStripped ? getHandler : hasHandler;
      vm._renderProxy = new Proxy(vm, handlers);
    } else {
      vm._renderProxy = vm;
    }
  };
}

/*  */

var VNode = function VNode(tag, data, children, text, elm, context, componentOptions) {
  this.tag = tag;
  this.data = data;
  this.children = children;
  this.text = text;
  this.elm = elm;
  this.ns = undefined;
  this.context = context;
  this.functionalContext = undefined;
  this.key = data && data.key;
  this.componentOptions = componentOptions;
  this.componentInstance = undefined;
  this.parent = undefined;
  this.raw = false;
  this.isStatic = false;
  this.isRootInsert = true;
  this.isComment = false;
  this.isCloned = false;
  this.isOnce = false;
};

var prototypeAccessors = { child: {} };

// DEPRECATED: alias for componentInstance for backwards compat.
/* istanbul ignore next */
prototypeAccessors.child.get = function () {
  return this.componentInstance;
};

Object.defineProperties(VNode.prototype, prototypeAccessors);

var createEmptyVNode = function createEmptyVNode() {
  var node = new VNode();
  node.text = '';
  node.isComment = true;
  return node;
};

function createTextVNode(val) {
  return new VNode(undefined, undefined, undefined, String(val));
}

// optimized shallow clone
// used for static nodes and slot nodes because they may be reused across
// multiple renders, cloning them avoids errors when DOM manipulations rely
// on their elm reference.
function cloneVNode(vnode) {
  var cloned = new VNode(vnode.tag, vnode.data, vnode.children, vnode.text, vnode.elm, vnode.context, vnode.componentOptions);
  cloned.ns = vnode.ns;
  cloned.isStatic = vnode.isStatic;
  cloned.key = vnode.key;
  cloned.isComment = vnode.isComment;
  cloned.isCloned = true;
  return cloned;
}

function cloneVNodes(vnodes) {
  var len = vnodes.length;
  var res = new Array(len);
  for (var i = 0; i < len; i++) {
    res[i] = cloneVNode(vnodes[i]);
  }
  return res;
}

/*  */

var normalizeEvent = cached(function (name) {
  var passive = name.charAt(0) === '&';
  name = passive ? name.slice(1) : name;
  var once$$1 = name.charAt(0) === '~'; // Prefixed last, checked first
  name = once$$1 ? name.slice(1) : name;
  var capture = name.charAt(0) === '!';
  name = capture ? name.slice(1) : name;
  return {
    name: name,
    once: once$$1,
    capture: capture,
    passive: passive
  };
});

function createFnInvoker(fns) {
  function invoker() {
    var arguments$1 = arguments;

    var fns = invoker.fns;
    if (Array.isArray(fns)) {
      for (var i = 0; i < fns.length; i++) {
        fns[i].apply(null, arguments$1);
      }
    } else {
      // return handler return value for single handlers
      return fns.apply(null, arguments);
    }
  }
  invoker.fns = fns;
  return invoker;
}

function updateListeners(on, oldOn, add, remove$$1, vm) {
  var name, cur, old, event;
  for (name in on) {
    cur = on[name];
    old = oldOn[name];
    event = normalizeEvent(name);
    if (isUndef(cur)) {
      process.env.NODE_ENV !== 'production' && warn("Invalid handler for event \"" + event.name + "\": got " + String(cur), vm);
    } else if (isUndef(old)) {
      if (isUndef(cur.fns)) {
        cur = on[name] = createFnInvoker(cur);
      }
      add(event.name, cur, event.once, event.capture, event.passive);
    } else if (cur !== old) {
      old.fns = cur;
      on[name] = old;
    }
  }
  for (name in oldOn) {
    if (isUndef(on[name])) {
      event = normalizeEvent(name);
      remove$$1(event.name, oldOn[name], event.capture);
    }
  }
}

/*  */

function mergeVNodeHook(def, hookKey, hook) {
  var invoker;
  var oldHook = def[hookKey];

  function wrappedHook() {
    hook.apply(this, arguments);
    // important: remove merged hook to ensure it's called only once
    // and prevent memory leak
    remove(invoker.fns, wrappedHook);
  }

  if (isUndef(oldHook)) {
    // no existing hook
    invoker = createFnInvoker([wrappedHook]);
  } else {
    /* istanbul ignore if */
    if (isDef(oldHook.fns) && isTrue(oldHook.merged)) {
      // already a merged invoker
      invoker = oldHook;
      invoker.fns.push(wrappedHook);
    } else {
      // existing plain hook
      invoker = createFnInvoker([oldHook, wrappedHook]);
    }
  }

  invoker.merged = true;
  def[hookKey] = invoker;
}

/*  */

function extractPropsFromVNodeData(data, Ctor, tag) {
  // we are only extracting raw values here.
  // validation and default values are handled in the child
  // component itself.
  var propOptions = Ctor.options.props;
  if (isUndef(propOptions)) {
    return;
  }
  var res = {};
  var attrs = data.attrs;
  var props = data.props;
  if (isDef(attrs) || isDef(props)) {
    for (var key in propOptions) {
      var altKey = hyphenate(key);
      if (process.env.NODE_ENV !== 'production') {
        var keyInLowerCase = key.toLowerCase();
        if (key !== keyInLowerCase && attrs && hasOwn(attrs, keyInLowerCase)) {
          tip("Prop \"" + keyInLowerCase + "\" is passed to component " + formatComponentName(tag || Ctor) + ", but the declared prop name is" + " \"" + key + "\". " + "Note that HTML attributes are case-insensitive and camelCased " + "props need to use their kebab-case equivalents when using in-DOM " + "templates. You should probably use \"" + altKey + "\" instead of \"" + key + "\".");
        }
      }
      checkProp(res, props, key, altKey, true) || checkProp(res, attrs, key, altKey, false);
    }
  }
  return res;
}

function checkProp(res, hash, key, altKey, preserve) {
  if (isDef(hash)) {
    if (hasOwn(hash, key)) {
      res[key] = hash[key];
      if (!preserve) {
        delete hash[key];
      }
      return true;
    } else if (hasOwn(hash, altKey)) {
      res[key] = hash[altKey];
      if (!preserve) {
        delete hash[altKey];
      }
      return true;
    }
  }
  return false;
}

/*  */

// The template compiler attempts to minimize the need for normalization by
// statically analyzing the template at compile time.
//
// For plain HTML markup, normalization can be completely skipped because the
// generated render function is guaranteed to return Array<VNode>. There are
// two cases where extra normalization is needed:

// 1. When the children contains components - because a functional component
// may return an Array instead of a single root. In this case, just a simple
// normalization is needed - if any child is an Array, we flatten the whole
// thing with Array.prototype.concat. It is guaranteed to be only 1-level deep
// because functional components already normalize their own children.
function simpleNormalizeChildren(children) {
  for (var i = 0; i < children.length; i++) {
    if (Array.isArray(children[i])) {
      return Array.prototype.concat.apply([], children);
    }
  }
  return children;
}

// 2. When the children contains constructs that always generated nested Arrays,
// e.g. <template>, <slot>, v-for, or when the children is provided by user
// with hand-written render functions / JSX. In such cases a full normalization
// is needed to cater to all possible types of children values.
function normalizeChildren(children) {
  return isPrimitive(children) ? [createTextVNode(children)] : Array.isArray(children) ? normalizeArrayChildren(children) : undefined;
}

function isTextNode(node) {
  return isDef(node) && isDef(node.text) && isFalse(node.isComment);
}

function normalizeArrayChildren(children, nestedIndex) {
  var res = [];
  var i, c, last;
  for (i = 0; i < children.length; i++) {
    c = children[i];
    if (isUndef(c) || typeof c === 'boolean') {
      continue;
    }
    last = res[res.length - 1];
    //  nested
    if (Array.isArray(c)) {
      res.push.apply(res, normalizeArrayChildren(c, (nestedIndex || '') + "_" + i));
    } else if (isPrimitive(c)) {
      if (isTextNode(last)) {
        // merge adjacent text nodes
        // this is necessary for SSR hydration because text nodes are
        // essentially merged when rendered to HTML strings
        last.text += String(c);
      } else if (c !== '') {
        // convert primitive to vnode
        res.push(createTextVNode(c));
      }
    } else {
      if (isTextNode(c) && isTextNode(last)) {
        // merge adjacent text nodes
        res[res.length - 1] = createTextVNode(last.text + c.text);
      } else {
        // default key for nested array children (likely generated by v-for)
        if (isTrue(children._isVList) && isDef(c.tag) && isUndef(c.key) && isDef(nestedIndex)) {
          c.key = "__vlist" + nestedIndex + "_" + i + "__";
        }
        res.push(c);
      }
    }
  }
  return res;
}

/*  */

function ensureCtor(comp, base) {
  return isObject(comp) ? base.extend(comp) : comp;
}

function resolveAsyncComponent(factory, baseCtor, context) {
  if (isTrue(factory.error) && isDef(factory.errorComp)) {
    return factory.errorComp;
  }

  if (isDef(factory.resolved)) {
    return factory.resolved;
  }

  if (isTrue(factory.loading) && isDef(factory.loadingComp)) {
    return factory.loadingComp;
  }

  if (isDef(factory.contexts)) {
    // already pending
    factory.contexts.push(context);
  } else {
    var contexts = factory.contexts = [context];
    var sync = true;

    var forceRender = function forceRender() {
      for (var i = 0, l = contexts.length; i < l; i++) {
        contexts[i].$forceUpdate();
      }
    };

    var resolve = once(function (res) {
      // cache resolved
      factory.resolved = ensureCtor(res, baseCtor);
      // invoke callbacks only if this is not a synchronous resolve
      // (async resolves are shimmed as synchronous during SSR)
      if (!sync) {
        forceRender();
      }
    });

    var reject = once(function (reason) {
      process.env.NODE_ENV !== 'production' && warn("Failed to resolve async component: " + String(factory) + (reason ? "\nReason: " + reason : ''));
      if (isDef(factory.errorComp)) {
        factory.error = true;
        forceRender();
      }
    });

    var res = factory(resolve, reject);

    if (isObject(res)) {
      if (typeof res.then === 'function') {
        // () => Promise
        if (isUndef(factory.resolved)) {
          res.then(resolve, reject);
        }
      } else if (isDef(res.component) && typeof res.component.then === 'function') {
        res.component.then(resolve, reject);

        if (isDef(res.error)) {
          factory.errorComp = ensureCtor(res.error, baseCtor);
        }

        if (isDef(res.loading)) {
          factory.loadingComp = ensureCtor(res.loading, baseCtor);
          if (res.delay === 0) {
            factory.loading = true;
          } else {
            setTimeout(function () {
              if (isUndef(factory.resolved) && isUndef(factory.error)) {
                factory.loading = true;
                forceRender();
              }
            }, res.delay || 200);
          }
        }

        if (isDef(res.timeout)) {
          setTimeout(function () {
            if (isUndef(factory.resolved)) {
              reject(process.env.NODE_ENV !== 'production' ? "timeout (" + res.timeout + "ms)" : null);
            }
          }, res.timeout);
        }
      }
    }

    sync = false;
    // return in case resolved synchronously
    return factory.loading ? factory.loadingComp : factory.resolved;
  }
}

/*  */

function getFirstComponentChild(children) {
  if (Array.isArray(children)) {
    for (var i = 0; i < children.length; i++) {
      var c = children[i];
      if (isDef(c) && isDef(c.componentOptions)) {
        return c;
      }
    }
  }
}

/*  */

/*  */

function initEvents(vm) {
  vm._events = Object.create(null);
  vm._hasHookEvent = false;
  // init parent attached events
  var listeners = vm.$options._parentListeners;
  if (listeners) {
    updateComponentListeners(vm, listeners);
  }
}

var target;

function add(event, fn, once$$1) {
  if (once$$1) {
    target.$once(event, fn);
  } else {
    target.$on(event, fn);
  }
}

function remove$1(event, fn) {
  target.$off(event, fn);
}

function updateComponentListeners(vm, listeners, oldListeners) {
  target = vm;
  updateListeners(listeners, oldListeners || {}, add, remove$1, vm);
}

function eventsMixin(Vue) {
  var hookRE = /^hook:/;
  Vue.prototype.$on = function (event, fn) {
    var this$1 = this;

    var vm = this;
    if (Array.isArray(event)) {
      for (var i = 0, l = event.length; i < l; i++) {
        this$1.$on(event[i], fn);
      }
    } else {
      (vm._events[event] || (vm._events[event] = [])).push(fn);
      // optimize hook:event cost by using a boolean flag marked at registration
      // instead of a hash lookup
      if (hookRE.test(event)) {
        vm._hasHookEvent = true;
      }
    }
    return vm;
  };

  Vue.prototype.$once = function (event, fn) {
    var vm = this;
    function on() {
      vm.$off(event, on);
      fn.apply(vm, arguments);
    }
    on.fn = fn;
    vm.$on(event, on);
    return vm;
  };

  Vue.prototype.$off = function (event, fn) {
    var this$1 = this;

    var vm = this;
    // all
    if (!arguments.length) {
      vm._events = Object.create(null);
      return vm;
    }
    // array of events
    if (Array.isArray(event)) {
      for (var i$1 = 0, l = event.length; i$1 < l; i$1++) {
        this$1.$off(event[i$1], fn);
      }
      return vm;
    }
    // specific event
    var cbs = vm._events[event];
    if (!cbs) {
      return vm;
    }
    if (arguments.length === 1) {
      vm._events[event] = null;
      return vm;
    }
    // specific handler
    var cb;
    var i = cbs.length;
    while (i--) {
      cb = cbs[i];
      if (cb === fn || cb.fn === fn) {
        cbs.splice(i, 1);
        break;
      }
    }
    return vm;
  };

  Vue.prototype.$emit = function (event) {
    var vm = this;
    if (process.env.NODE_ENV !== 'production') {
      var lowerCaseEvent = event.toLowerCase();
      if (lowerCaseEvent !== event && vm._events[lowerCaseEvent]) {
        tip("Event \"" + lowerCaseEvent + "\" is emitted in component " + formatComponentName(vm) + " but the handler is registered for \"" + event + "\". " + "Note that HTML attributes are case-insensitive and you cannot use " + "v-on to listen to camelCase events when using in-DOM templates. " + "You should probably use \"" + hyphenate(event) + "\" instead of \"" + event + "\".");
      }
    }
    var cbs = vm._events[event];
    if (cbs) {
      cbs = cbs.length > 1 ? toArray(cbs) : cbs;
      var args = toArray(arguments, 1);
      for (var i = 0, l = cbs.length; i < l; i++) {
        cbs[i].apply(vm, args);
      }
    }
    return vm;
  };
}

/*  */

/**
 * Runtime helper for resolving raw children VNodes into a slot object.
 */
function resolveSlots(children, context) {
  var slots = {};
  if (!children) {
    return slots;
  }
  var defaultSlot = [];
  for (var i = 0, l = children.length; i < l; i++) {
    var child = children[i];
    // named slots should only be respected if the vnode was rendered in the
    // same context.
    if ((child.context === context || child.functionalContext === context) && child.data && child.data.slot != null) {
      var name = child.data.slot;
      var slot = slots[name] || (slots[name] = []);
      if (child.tag === 'template') {
        slot.push.apply(slot, child.children);
      } else {
        slot.push(child);
      }
    } else {
      defaultSlot.push(child);
    }
  }
  // ignore whitespace
  if (!defaultSlot.every(isWhitespace)) {
    slots.default = defaultSlot;
  }
  return slots;
}

function isWhitespace(node) {
  return node.isComment || node.text === ' ';
}

function resolveScopedSlots(fns, // see flow/vnode
res) {
  res = res || {};
  for (var i = 0; i < fns.length; i++) {
    if (Array.isArray(fns[i])) {
      resolveScopedSlots(fns[i], res);
    } else {
      res[fns[i].key] = fns[i].fn;
    }
  }
  return res;
}

/*  */

var activeInstance = null;

function initLifecycle(vm) {
  var options = vm.$options;

  // locate first non-abstract parent
  var parent = options.parent;
  if (parent && !options.abstract) {
    while (parent.$options.abstract && parent.$parent) {
      parent = parent.$parent;
    }
    parent.$children.push(vm);
  }

  vm.$parent = parent;
  vm.$root = parent ? parent.$root : vm;

  vm.$children = [];
  vm.$refs = {};

  vm._watcher = null;
  vm._inactive = null;
  vm._directInactive = false;
  vm._isMounted = false;
  vm._isDestroyed = false;
  vm._isBeingDestroyed = false;
}

function lifecycleMixin(Vue) {
  Vue.prototype._update = function (vnode, hydrating) {
    var vm = this;
    if (vm._isMounted) {
      callHook(vm, 'beforeUpdate');
    }
    var prevEl = vm.$el;
    var prevVnode = vm._vnode;
    var prevActiveInstance = activeInstance;
    activeInstance = vm;
    vm._vnode = vnode;
    // Vue.prototype.__patch__ is injected in entry points
    // based on the rendering backend used.
    if (!prevVnode) {
      // initial render
      vm.$el = vm.__patch__(vm.$el, vnode, hydrating, false /* removeOnly */
      , vm.$options._parentElm, vm.$options._refElm);
    } else {
      // updates
      vm.$el = vm.__patch__(prevVnode, vnode);
    }
    activeInstance = prevActiveInstance;
    // update __vue__ reference
    if (prevEl) {
      prevEl.__vue__ = null;
    }
    if (vm.$el) {
      vm.$el.__vue__ = vm;
    }
    // if parent is an HOC, update its $el as well
    if (vm.$vnode && vm.$parent && vm.$vnode === vm.$parent._vnode) {
      vm.$parent.$el = vm.$el;
    }
    // updated hook is called by the scheduler to ensure that children are
    // updated in a parent's updated hook.
  };

  Vue.prototype.$forceUpdate = function () {
    var vm = this;
    if (vm._watcher) {
      vm._watcher.update();
    }
  };

  Vue.prototype.$destroy = function () {
    var vm = this;
    if (vm._isBeingDestroyed) {
      return;
    }
    callHook(vm, 'beforeDestroy');
    vm._isBeingDestroyed = true;
    // remove self from parent
    var parent = vm.$parent;
    if (parent && !parent._isBeingDestroyed && !vm.$options.abstract) {
      remove(parent.$children, vm);
    }
    // teardown watchers
    if (vm._watcher) {
      vm._watcher.teardown();
    }
    var i = vm._watchers.length;
    while (i--) {
      vm._watchers[i].teardown();
    }
    // remove reference from data ob
    // frozen object may not have observer.
    if (vm._data.__ob__) {
      vm._data.__ob__.vmCount--;
    }
    // call the last hook...
    vm._isDestroyed = true;
    // invoke destroy hooks on current rendered tree
    vm.__patch__(vm._vnode, null);
    // fire destroyed hook
    callHook(vm, 'destroyed');
    // turn off all instance listeners.
    vm.$off();
    // remove __vue__ reference
    if (vm.$el) {
      vm.$el.__vue__ = null;
    }
    // remove reference to DOM nodes (prevents leak)
    vm.$options._parentElm = vm.$options._refElm = null;
  };
}

function mountComponent(vm, el, hydrating) {
  vm.$el = el;
  if (!vm.$options.render) {
    vm.$options.render = createEmptyVNode;
    if (process.env.NODE_ENV !== 'production') {
      /* istanbul ignore if */
      if (vm.$options.template && vm.$options.template.charAt(0) !== '#' || vm.$options.el || el) {
        warn('You are using the runtime-only build of Vue where the template ' + 'compiler is not available. Either pre-compile the templates into ' + 'render functions, or use the compiler-included build.', vm);
      } else {
        warn('Failed to mount component: template or render function not defined.', vm);
      }
    }
  }
  callHook(vm, 'beforeMount');

  var updateComponent;
  /* istanbul ignore if */
  if (process.env.NODE_ENV !== 'production' && config.performance && mark) {
    updateComponent = function updateComponent() {
      var name = vm._name;
      var id = vm._uid;
      var startTag = "vue-perf-start:" + id;
      var endTag = "vue-perf-end:" + id;

      mark(startTag);
      var vnode = vm._render();
      mark(endTag);
      measure(name + " render", startTag, endTag);

      mark(startTag);
      vm._update(vnode, hydrating);
      mark(endTag);
      measure(name + " patch", startTag, endTag);
    };
  } else {
    updateComponent = function updateComponent() {
      vm._update(vm._render(), hydrating);
    };
  }

  vm._watcher = new Watcher(vm, updateComponent, noop);
  hydrating = false;

  // manually mounted instance, call mounted on self
  // mounted is called for render-created child components in its inserted hook
  if (vm.$vnode == null) {
    vm._isMounted = true;
    callHook(vm, 'mounted');
  }
  return vm;
}

function updateChildComponent(vm, propsData, listeners, parentVnode, renderChildren) {
  // determine whether component has slot children
  // we need to do this before overwriting $options._renderChildren
  var hasChildren = !!(renderChildren || // has new static slots
  vm.$options._renderChildren || // has old static slots
  parentVnode.data.scopedSlots || // has new scoped slots
  vm.$scopedSlots !== emptyObject // has old scoped slots
  );

  vm.$options._parentVnode = parentVnode;
  vm.$vnode = parentVnode; // update vm's placeholder node without re-render
  if (vm._vnode) {
    // update child tree's parent
    vm._vnode.parent = parentVnode;
  }
  vm.$options._renderChildren = renderChildren;

  // update props
  if (propsData && vm.$options.props) {
    observerState.shouldConvert = false;
    if (process.env.NODE_ENV !== 'production') {
      observerState.isSettingProps = true;
    }
    var props = vm._props;
    var propKeys = vm.$options._propKeys || [];
    for (var i = 0; i < propKeys.length; i++) {
      var key = propKeys[i];
      props[key] = validateProp(key, vm.$options.props, propsData, vm);
    }
    observerState.shouldConvert = true;
    if (process.env.NODE_ENV !== 'production') {
      observerState.isSettingProps = false;
    }
    // keep a copy of raw propsData
    vm.$options.propsData = propsData;
  }
  // update listeners
  if (listeners) {
    var oldListeners = vm.$options._parentListeners;
    vm.$options._parentListeners = listeners;
    updateComponentListeners(vm, listeners, oldListeners);
  }
  // resolve slots + force update if has children
  if (hasChildren) {
    vm.$slots = resolveSlots(renderChildren, parentVnode.context);
    vm.$forceUpdate();
  }
}

function isInInactiveTree(vm) {
  while (vm && (vm = vm.$parent)) {
    if (vm._inactive) {
      return true;
    }
  }
  return false;
}

function activateChildComponent(vm, direct) {
  if (direct) {
    vm._directInactive = false;
    if (isInInactiveTree(vm)) {
      return;
    }
  } else if (vm._directInactive) {
    return;
  }
  if (vm._inactive || vm._inactive === null) {
    vm._inactive = false;
    for (var i = 0; i < vm.$children.length; i++) {
      activateChildComponent(vm.$children[i]);
    }
    callHook(vm, 'activated');
  }
}

function deactivateChildComponent(vm, direct) {
  if (direct) {
    vm._directInactive = true;
    if (isInInactiveTree(vm)) {
      return;
    }
  }
  if (!vm._inactive) {
    vm._inactive = true;
    for (var i = 0; i < vm.$children.length; i++) {
      deactivateChildComponent(vm.$children[i]);
    }
    callHook(vm, 'deactivated');
  }
}

function callHook(vm, hook) {
  var handlers = vm.$options[hook];
  if (handlers) {
    for (var i = 0, j = handlers.length; i < j; i++) {
      try {
        handlers[i].call(vm);
      } catch (e) {
        handleError(e, vm, hook + " hook");
      }
    }
  }
  if (vm._hasHookEvent) {
    vm.$emit('hook:' + hook);
  }
}

/*  */

var MAX_UPDATE_COUNT = 100;

var queue = [];
var activatedChildren = [];
var has = {};
var circular = {};
var waiting = false;
var flushing = false;
var index = 0;

/**
 * Reset the scheduler's state.
 */
function resetSchedulerState() {
  index = queue.length = activatedChildren.length = 0;
  has = {};
  if (process.env.NODE_ENV !== 'production') {
    circular = {};
  }
  waiting = flushing = false;
}

/**
 * Flush both queues and run the watchers.
 */
function flushSchedulerQueue() {
  flushing = true;
  var watcher, id;

  // Sort queue before flush.
  // This ensures that:
  // 1. Components are updated from parent to child. (because parent is always
  //    created before the child)
  // 2. A component's user watchers are run before its render watcher (because
  //    user watchers are created before the render watcher)
  // 3. If a component is destroyed during a parent component's watcher run,
  //    its watchers can be skipped.
  queue.sort(function (a, b) {
    return a.id - b.id;
  });

  // do not cache length because more watchers might be pushed
  // as we run existing watchers
  for (index = 0; index < queue.length; index++) {
    watcher = queue[index];
    id = watcher.id;
    has[id] = null;
    watcher.run();
    // in dev build, check and stop circular updates.
    if (process.env.NODE_ENV !== 'production' && has[id] != null) {
      circular[id] = (circular[id] || 0) + 1;
      if (circular[id] > MAX_UPDATE_COUNT) {
        warn('You may have an infinite update loop ' + (watcher.user ? "in watcher with expression \"" + watcher.expression + "\"" : "in a component render function."), watcher.vm);
        break;
      }
    }
  }

  // keep copies of post queues before resetting state
  var activatedQueue = activatedChildren.slice();
  var updatedQueue = queue.slice();

  resetSchedulerState();

  // call component updated and activated hooks
  callActivatedHooks(activatedQueue);
  callUpdateHooks(updatedQueue);

  // devtool hook
  /* istanbul ignore if */
  if (devtools && config.devtools) {
    devtools.emit('flush');
  }
}

function callUpdateHooks(queue) {
  var i = queue.length;
  while (i--) {
    var watcher = queue[i];
    var vm = watcher.vm;
    if (vm._watcher === watcher && vm._isMounted) {
      callHook(vm, 'updated');
    }
  }
}

/**
 * Queue a kept-alive component that was activated during patch.
 * The queue will be processed after the entire tree has been patched.
 */
function queueActivatedComponent(vm) {
  // setting _inactive to false here so that a render function can
  // rely on checking whether it's in an inactive tree (e.g. router-view)
  vm._inactive = false;
  activatedChildren.push(vm);
}

function callActivatedHooks(queue) {
  for (var i = 0; i < queue.length; i++) {
    queue[i]._inactive = true;
    activateChildComponent(queue[i], true /* true */);
  }
}

/**
 * Push a watcher into the watcher queue.
 * Jobs with duplicate IDs will be skipped unless it's
 * pushed when the queue is being flushed.
 */
function queueWatcher(watcher) {
  var id = watcher.id;
  if (has[id] == null) {
    has[id] = true;
    if (!flushing) {
      queue.push(watcher);
    } else {
      // if already flushing, splice the watcher based on its id
      // if already past its id, it will be run next immediately.
      var i = queue.length - 1;
      while (i > index && queue[i].id > watcher.id) {
        i--;
      }
      queue.splice(i + 1, 0, watcher);
    }
    // queue the flush
    if (!waiting) {
      waiting = true;
      nextTick(flushSchedulerQueue);
    }
  }
}

/*  */

var uid$2 = 0;

/**
 * A watcher parses an expression, collects dependencies,
 * and fires callback when the expression value changes.
 * This is used for both the $watch() api and directives.
 */
var Watcher = function Watcher(vm, expOrFn, cb, options) {
  this.vm = vm;
  vm._watchers.push(this);
  // options
  if (options) {
    this.deep = !!options.deep;
    this.user = !!options.user;
    this.lazy = !!options.lazy;
    this.sync = !!options.sync;
  } else {
    this.deep = this.user = this.lazy = this.sync = false;
  }
  this.cb = cb;
  this.id = ++uid$2; // uid for batching
  this.active = true;
  this.dirty = this.lazy; // for lazy watchers
  this.deps = [];
  this.newDeps = [];
  this.depIds = new _Set();
  this.newDepIds = new _Set();
  this.expression = process.env.NODE_ENV !== 'production' ? expOrFn.toString() : '';
  // parse expression for getter
  if (typeof expOrFn === 'function') {
    this.getter = expOrFn;
  } else {
    this.getter = parsePath(expOrFn);
    if (!this.getter) {
      this.getter = function () {};
      process.env.NODE_ENV !== 'production' && warn("Failed watching path: \"" + expOrFn + "\" " + 'Watcher only accepts simple dot-delimited paths. ' + 'For full control, use a function instead.', vm);
    }
  }
  this.value = this.lazy ? undefined : this.get();
};

/**
 * Evaluate the getter, and re-collect dependencies.
 */
Watcher.prototype.get = function get() {
  pushTarget(this);
  var value;
  var vm = this.vm;
  if (this.user) {
    try {
      value = this.getter.call(vm, vm);
    } catch (e) {
      handleError(e, vm, "getter for watcher \"" + this.expression + "\"");
    }
  } else {
    value = this.getter.call(vm, vm);
  }
  // "touch" every property so they are all tracked as
  // dependencies for deep watching
  if (this.deep) {
    traverse(value);
  }
  popTarget();
  this.cleanupDeps();
  return value;
};

/**
 * Add a dependency to this directive.
 */
Watcher.prototype.addDep = function addDep(dep) {
  var id = dep.id;
  if (!this.newDepIds.has(id)) {
    this.newDepIds.add(id);
    this.newDeps.push(dep);
    if (!this.depIds.has(id)) {
      dep.addSub(this);
    }
  }
};

/**
 * Clean up for dependency collection.
 */
Watcher.prototype.cleanupDeps = function cleanupDeps() {
  var this$1 = this;

  var i = this.deps.length;
  while (i--) {
    var dep = this$1.deps[i];
    if (!this$1.newDepIds.has(dep.id)) {
      dep.removeSub(this$1);
    }
  }
  var tmp = this.depIds;
  this.depIds = this.newDepIds;
  this.newDepIds = tmp;
  this.newDepIds.clear();
  tmp = this.deps;
  this.deps = this.newDeps;
  this.newDeps = tmp;
  this.newDeps.length = 0;
};

/**
 * Subscriber interface.
 * Will be called when a dependency changes.
 */
Watcher.prototype.update = function update() {
  /* istanbul ignore else */
  if (this.lazy) {
    this.dirty = true;
  } else if (this.sync) {
    this.run();
  } else {
    queueWatcher(this);
  }
};

/**
 * Scheduler job interface.
 * Will be called by the scheduler.
 */
Watcher.prototype.run = function run() {
  if (this.active) {
    var value = this.get();
    if (value !== this.value ||
    // Deep watchers and watchers on Object/Arrays should fire even
    // when the value is the same, because the value may
    // have mutated.
    isObject(value) || this.deep) {
      // set new value
      var oldValue = this.value;
      this.value = value;
      if (this.user) {
        try {
          this.cb.call(this.vm, value, oldValue);
        } catch (e) {
          handleError(e, this.vm, "callback for watcher \"" + this.expression + "\"");
        }
      } else {
        this.cb.call(this.vm, value, oldValue);
      }
    }
  }
};

/**
 * Evaluate the value of the watcher.
 * This only gets called for lazy watchers.
 */
Watcher.prototype.evaluate = function evaluate() {
  this.value = this.get();
  this.dirty = false;
};

/**
 * Depend on all deps collected by this watcher.
 */
Watcher.prototype.depend = function depend() {
  var this$1 = this;

  var i = this.deps.length;
  while (i--) {
    this$1.deps[i].depend();
  }
};

/**
 * Remove self from all dependencies' subscriber list.
 */
Watcher.prototype.teardown = function teardown() {
  var this$1 = this;

  if (this.active) {
    // remove self from vm's watcher list
    // this is a somewhat expensive operation so we skip it
    // if the vm is being destroyed.
    if (!this.vm._isBeingDestroyed) {
      remove(this.vm._watchers, this);
    }
    var i = this.deps.length;
    while (i--) {
      this$1.deps[i].removeSub(this$1);
    }
    this.active = false;
  }
};

/**
 * Recursively traverse an object to evoke all converted
 * getters, so that every nested property inside the object
 * is collected as a "deep" dependency.
 */
var seenObjects = new _Set();
function traverse(val) {
  seenObjects.clear();
  _traverse(val, seenObjects);
}

function _traverse(val, seen) {
  var i, keys;
  var isA = Array.isArray(val);
  if (!isA && !isObject(val) || !Object.isExtensible(val)) {
    return;
  }
  if (val.__ob__) {
    var depId = val.__ob__.dep.id;
    if (seen.has(depId)) {
      return;
    }
    seen.add(depId);
  }
  if (isA) {
    i = val.length;
    while (i--) {
      _traverse(val[i], seen);
    }
  } else {
    keys = Object.keys(val);
    i = keys.length;
    while (i--) {
      _traverse(val[keys[i]], seen);
    }
  }
}

/*  */

var sharedPropertyDefinition = {
  enumerable: true,
  configurable: true,
  get: noop,
  set: noop
};

function proxy(target, sourceKey, key) {
  sharedPropertyDefinition.get = function proxyGetter() {
    return this[sourceKey][key];
  };
  sharedPropertyDefinition.set = function proxySetter(val) {
    this[sourceKey][key] = val;
  };
  Object.defineProperty(target, key, sharedPropertyDefinition);
}

function initState(vm) {
  vm._watchers = [];
  var opts = vm.$options;
  if (opts.props) {
    initProps(vm, opts.props);
  }
  if (opts.methods) {
    initMethods(vm, opts.methods);
  }
  if (opts.data) {
    initData(vm);
  } else {
    observe(vm._data = {}, true /* asRootData */);
  }
  if (opts.computed) {
    initComputed(vm, opts.computed);
  }
  if (opts.watch) {
    initWatch(vm, opts.watch);
  }
}

var isReservedProp = {
  key: 1,
  ref: 1,
  slot: 1
};

function initProps(vm, propsOptions) {
  var propsData = vm.$options.propsData || {};
  var props = vm._props = {};
  // cache prop keys so that future props updates can iterate using Array
  // instead of dynamic object key enumeration.
  var keys = vm.$options._propKeys = [];
  var isRoot = !vm.$parent;
  // root instance props should be converted
  observerState.shouldConvert = isRoot;
  var loop = function loop(key) {
    keys.push(key);
    var value = validateProp(key, propsOptions, propsData, vm);
    /* istanbul ignore else */
    if (process.env.NODE_ENV !== 'production') {
      if (isReservedProp[key] || config.isReservedAttr(key)) {
        warn("\"" + key + "\" is a reserved attribute and cannot be used as component prop.", vm);
      }
      defineReactive$$1(props, key, value, function () {
        if (vm.$parent && !observerState.isSettingProps) {
          warn("Avoid mutating a prop directly since the value will be " + "overwritten whenever the parent component re-renders. " + "Instead, use a data or computed property based on the prop's " + "value. Prop being mutated: \"" + key + "\"", vm);
        }
      });
    } else {
      defineReactive$$1(props, key, value);
    }
    // static props are already proxied on the component's prototype
    // during Vue.extend(). We only need to proxy props defined at
    // instantiation here.
    if (!(key in vm)) {
      proxy(vm, "_props", key);
    }
  };

  for (var key in propsOptions) {
    loop(key);
  }observerState.shouldConvert = true;
}

function initData(vm) {
  var data = vm.$options.data;
  data = vm._data = typeof data === 'function' ? getData(data, vm) : data || {};
  if (!isPlainObject(data)) {
    data = {};
    process.env.NODE_ENV !== 'production' && warn('data functions should return an object:\n' + 'https://vuejs.org/v2/guide/components.html#data-Must-Be-a-Function', vm);
  }
  // proxy data on instance
  var keys = Object.keys(data);
  var props = vm.$options.props;
  var i = keys.length;
  while (i--) {
    if (props && hasOwn(props, keys[i])) {
      process.env.NODE_ENV !== 'production' && warn("The data property \"" + keys[i] + "\" is already declared as a prop. " + "Use prop default value instead.", vm);
    } else if (!isReserved(keys[i])) {
      proxy(vm, "_data", keys[i]);
    }
  }
  // observe data
  observe(data, true /* asRootData */);
}

function getData(data, vm) {
  try {
    return data.call(vm);
  } catch (e) {
    handleError(e, vm, "data()");
    return {};
  }
}

var computedWatcherOptions = { lazy: true };

function initComputed(vm, computed) {
  var watchers = vm._computedWatchers = Object.create(null);

  for (var key in computed) {
    var userDef = computed[key];
    var getter = typeof userDef === 'function' ? userDef : userDef.get;
    if (process.env.NODE_ENV !== 'production') {
      if (getter === undefined) {
        warn("No getter function has been defined for computed property \"" + key + "\".", vm);
        getter = noop;
      }
    }
    // create internal watcher for the computed property.
    watchers[key] = new Watcher(vm, getter, noop, computedWatcherOptions);

    // component-defined computed properties are already defined on the
    // component prototype. We only need to define computed properties defined
    // at instantiation here.
    if (!(key in vm)) {
      defineComputed(vm, key, userDef);
    } else if (process.env.NODE_ENV !== 'production') {
      if (key in vm.$data) {
        warn("The computed property \"" + key + "\" is already defined in data.", vm);
      } else if (vm.$options.props && key in vm.$options.props) {
        warn("The computed property \"" + key + "\" is already defined as a prop.", vm);
      }
    }
  }
}

function defineComputed(target, key, userDef) {
  if (typeof userDef === 'function') {
    sharedPropertyDefinition.get = createComputedGetter(key);
    sharedPropertyDefinition.set = noop;
  } else {
    sharedPropertyDefinition.get = userDef.get ? userDef.cache !== false ? createComputedGetter(key) : userDef.get : noop;
    sharedPropertyDefinition.set = userDef.set ? userDef.set : noop;
  }
  Object.defineProperty(target, key, sharedPropertyDefinition);
}

function createComputedGetter(key) {
  return function computedGetter() {
    var watcher = this._computedWatchers && this._computedWatchers[key];
    if (watcher) {
      if (watcher.dirty) {
        watcher.evaluate();
      }
      if (Dep.target) {
        watcher.depend();
      }
      return watcher.value;
    }
  };
}

function initMethods(vm, methods) {
  var props = vm.$options.props;
  for (var key in methods) {
    vm[key] = methods[key] == null ? noop : bind(methods[key], vm);
    if (process.env.NODE_ENV !== 'production') {
      if (methods[key] == null) {
        warn("method \"" + key + "\" has an undefined value in the component definition. " + "Did you reference the function correctly?", vm);
      }
      if (props && hasOwn(props, key)) {
        warn("method \"" + key + "\" has already been defined as a prop.", vm);
      }
    }
  }
}

function initWatch(vm, watch) {
  for (var key in watch) {
    var handler = watch[key];
    if (Array.isArray(handler)) {
      for (var i = 0; i < handler.length; i++) {
        createWatcher(vm, key, handler[i]);
      }
    } else {
      createWatcher(vm, key, handler);
    }
  }
}

function createWatcher(vm, key, handler) {
  var options;
  if (isPlainObject(handler)) {
    options = handler;
    handler = handler.handler;
  }
  if (typeof handler === 'string') {
    handler = vm[handler];
  }
  vm.$watch(key, handler, options);
}

function stateMixin(Vue) {
  // flow somehow has problems with directly declared definition object
  // when using Object.defineProperty, so we have to procedurally build up
  // the object here.
  var dataDef = {};
  dataDef.get = function () {
    return this._data;
  };
  var propsDef = {};
  propsDef.get = function () {
    return this._props;
  };
  if (process.env.NODE_ENV !== 'production') {
    dataDef.set = function (newData) {
      warn('Avoid replacing instance root $data. ' + 'Use nested data properties instead.', this);
    };
    propsDef.set = function () {
      warn("$props is readonly.", this);
    };
  }
  Object.defineProperty(Vue.prototype, '$data', dataDef);
  Object.defineProperty(Vue.prototype, '$props', propsDef);

  Vue.prototype.$set = set;
  Vue.prototype.$delete = del;

  Vue.prototype.$watch = function (expOrFn, cb, options) {
    var vm = this;
    options = options || {};
    options.user = true;
    var watcher = new Watcher(vm, expOrFn, cb, options);
    if (options.immediate) {
      cb.call(vm, watcher.value);
    }
    return function unwatchFn() {
      watcher.teardown();
    };
  };
}

/*  */

function initProvide(vm) {
  var provide = vm.$options.provide;
  if (provide) {
    vm._provided = typeof provide === 'function' ? provide.call(vm) : provide;
  }
}

function initInjections(vm) {
  var result = resolveInject(vm.$options.inject, vm);
  if (result) {
    Object.keys(result).forEach(function (key) {
      /* istanbul ignore else */
      if (process.env.NODE_ENV !== 'production') {
        defineReactive$$1(vm, key, result[key], function () {
          warn("Avoid mutating an injected value directly since the changes will be " + "overwritten whenever the provided component re-renders. " + "injection being mutated: \"" + key + "\"", vm);
        });
      } else {
        defineReactive$$1(vm, key, result[key]);
      }
    });
  }
}

function resolveInject(inject, vm) {
  if (inject) {
    // inject is :any because flow is not smart enough to figure out cached
    // isArray here
    var isArray = Array.isArray(inject);
    var result = Object.create(null);
    var keys = isArray ? inject : hasSymbol ? Reflect.ownKeys(inject) : Object.keys(inject);

    for (var i = 0; i < keys.length; i++) {
      var key = keys[i];
      var provideKey = isArray ? key : inject[key];
      var source = vm;
      while (source) {
        if (source._provided && provideKey in source._provided) {
          result[key] = source._provided[provideKey];
          break;
        }
        source = source.$parent;
      }
    }
    return result;
  }
}

/*  */

function createFunctionalComponent(Ctor, propsData, data, context, children) {
  var props = {};
  var propOptions = Ctor.options.props;
  if (isDef(propOptions)) {
    for (var key in propOptions) {
      props[key] = validateProp(key, propOptions, propsData || {});
    }
  } else {
    if (isDef(data.attrs)) {
      mergeProps(props, data.attrs);
    }
    if (isDef(data.props)) {
      mergeProps(props, data.props);
    }
  }
  // ensure the createElement function in functional components
  // gets a unique context - this is necessary for correct named slot check
  var _context = Object.create(context);
  var h = function h(a, b, c, d) {
    return createElement(_context, a, b, c, d, true);
  };
  var vnode = Ctor.options.render.call(null, h, {
    data: data,
    props: props,
    children: children,
    parent: context,
    listeners: data.on || {},
    injections: resolveInject(Ctor.options.inject, context),
    slots: function slots() {
      return resolveSlots(children, context);
    }
  });
  if (vnode instanceof VNode) {
    vnode.functionalContext = context;
    vnode.functionalOptions = Ctor.options;
    if (data.slot) {
      (vnode.data || (vnode.data = {})).slot = data.slot;
    }
  }
  return vnode;
}

function mergeProps(to, from) {
  for (var key in from) {
    to[camelize(key)] = from[key];
  }
}

/*  */

// hooks to be invoked on component VNodes during patch
var componentVNodeHooks = {
  init: function init(vnode, hydrating, parentElm, refElm) {
    if (!vnode.componentInstance || vnode.componentInstance._isDestroyed) {
      var child = vnode.componentInstance = createComponentInstanceForVnode(vnode, activeInstance, parentElm, refElm);
      child.$mount(hydrating ? vnode.elm : undefined, hydrating);
    } else if (vnode.data.keepAlive) {
      // kept-alive components, treat as a patch
      var mountedNode = vnode; // work around flow
      componentVNodeHooks.prepatch(mountedNode, mountedNode);
    }
  },

  prepatch: function prepatch(oldVnode, vnode) {
    var options = vnode.componentOptions;
    var child = vnode.componentInstance = oldVnode.componentInstance;
    updateChildComponent(child, options.propsData, // updated props
    options.listeners, // updated listeners
    vnode, // new parent vnode
    options.children // new children
    );
  },

  insert: function insert(vnode) {
    var context = vnode.context;
    var componentInstance = vnode.componentInstance;
    if (!componentInstance._isMounted) {
      componentInstance._isMounted = true;
      callHook(componentInstance, 'mounted');
    }
    if (vnode.data.keepAlive) {
      if (context._isMounted) {
        // vue-router#1212
        // During updates, a kept-alive component's child components may
        // change, so directly walking the tree here may call activated hooks
        // on incorrect children. Instead we push them into a queue which will
        // be processed after the whole patch process ended.
        queueActivatedComponent(componentInstance);
      } else {
        activateChildComponent(componentInstance, true /* direct */);
      }
    }
  },

  destroy: function destroy(vnode) {
    var componentInstance = vnode.componentInstance;
    if (!componentInstance._isDestroyed) {
      if (!vnode.data.keepAlive) {
        componentInstance.$destroy();
      } else {
        deactivateChildComponent(componentInstance, true /* direct */);
      }
    }
  }
};

var hooksToMerge = Object.keys(componentVNodeHooks);

function createComponent(Ctor, data, context, children, tag) {
  if (isUndef(Ctor)) {
    return;
  }

  var baseCtor = context.$options._base;

  // plain options object: turn it into a constructor
  if (isObject(Ctor)) {
    Ctor = baseCtor.extend(Ctor);
  }

  // if at this stage it's not a constructor or an async component factory,
  // reject.
  if (typeof Ctor !== 'function') {
    if (process.env.NODE_ENV !== 'production') {
      warn("Invalid Component definition: " + String(Ctor), context);
    }
    return;
  }

  // async component
  if (isUndef(Ctor.cid)) {
    Ctor = resolveAsyncComponent(Ctor, baseCtor, context);
    if (Ctor === undefined) {
      // return nothing if this is indeed an async component
      // wait for the callback to trigger parent update.
      return;
    }
  }

  // resolve constructor options in case global mixins are applied after
  // component constructor creation
  resolveConstructorOptions(Ctor);

  data = data || {};

  // transform component v-model data into props & events
  if (isDef(data.model)) {
    transformModel(Ctor.options, data);
  }

  // extract props
  var propsData = extractPropsFromVNodeData(data, Ctor, tag);

  // functional component
  if (isTrue(Ctor.options.functional)) {
    return createFunctionalComponent(Ctor, propsData, data, context, children);
  }

  // extract listeners, since these needs to be treated as
  // child component listeners instead of DOM listeners
  var listeners = data.on;
  // replace with listeners with .native modifier
  data.on = data.nativeOn;

  if (isTrue(Ctor.options.abstract)) {
    // abstract components do not keep anything
    // other than props & listeners
    data = {};
  }

  // merge component management hooks onto the placeholder node
  mergeHooks(data);

  // return a placeholder vnode
  var name = Ctor.options.name || tag;
  var vnode = new VNode("vue-component-" + Ctor.cid + (name ? "-" + name : ''), data, undefined, undefined, undefined, context, { Ctor: Ctor, propsData: propsData, listeners: listeners, tag: tag, children: children });
  return vnode;
}

function createComponentInstanceForVnode(vnode, // we know it's MountedComponentVNode but flow doesn't
parent, // activeInstance in lifecycle state
parentElm, refElm) {
  var vnodeComponentOptions = vnode.componentOptions;
  var options = {
    _isComponent: true,
    parent: parent,
    propsData: vnodeComponentOptions.propsData,
    _componentTag: vnodeComponentOptions.tag,
    _parentVnode: vnode,
    _parentListeners: vnodeComponentOptions.listeners,
    _renderChildren: vnodeComponentOptions.children,
    _parentElm: parentElm || null,
    _refElm: refElm || null
  };
  // check inline-template render functions
  var inlineTemplate = vnode.data.inlineTemplate;
  if (isDef(inlineTemplate)) {
    options.render = inlineTemplate.render;
    options.staticRenderFns = inlineTemplate.staticRenderFns;
  }
  return new vnodeComponentOptions.Ctor(options);
}

function mergeHooks(data) {
  if (!data.hook) {
    data.hook = {};
  }
  for (var i = 0; i < hooksToMerge.length; i++) {
    var key = hooksToMerge[i];
    var fromParent = data.hook[key];
    var ours = componentVNodeHooks[key];
    data.hook[key] = fromParent ? mergeHook$1(ours, fromParent) : ours;
  }
}

function mergeHook$1(one, two) {
  return function (a, b, c, d) {
    one(a, b, c, d);
    two(a, b, c, d);
  };
}

// transform component v-model info (value and callback) into
// prop and event handler respectively.
function transformModel(options, data) {
  var prop = options.model && options.model.prop || 'value';
  var event = options.model && options.model.event || 'input';(data.props || (data.props = {}))[prop] = data.model.value;
  var on = data.on || (data.on = {});
  if (isDef(on[event])) {
    on[event] = [data.model.callback].concat(on[event]);
  } else {
    on[event] = data.model.callback;
  }
}

/*  */

var SIMPLE_NORMALIZE = 1;
var ALWAYS_NORMALIZE = 2;

// wrapper function for providing a more flexible interface
// without getting yelled at by flow
function createElement(context, tag, data, children, normalizationType, alwaysNormalize) {
  if (Array.isArray(data) || isPrimitive(data)) {
    normalizationType = children;
    children = data;
    data = undefined;
  }
  if (isTrue(alwaysNormalize)) {
    normalizationType = ALWAYS_NORMALIZE;
  }
  return _createElement(context, tag, data, children, normalizationType);
}

function _createElement(context, tag, data, children, normalizationType) {
  if (isDef(data) && isDef(data.__ob__)) {
    process.env.NODE_ENV !== 'production' && warn("Avoid using observed data object as vnode data: " + JSON.stringify(data) + "\n" + 'Always create fresh vnode data objects in each render!', context);
    return createEmptyVNode();
  }
  if (!tag) {
    // in case of component :is set to falsy value
    return createEmptyVNode();
  }
  // support single function children as default scoped slot
  if (Array.isArray(children) && typeof children[0] === 'function') {
    data = data || {};
    data.scopedSlots = { default: children[0] };
    children.length = 0;
  }
  if (normalizationType === ALWAYS_NORMALIZE) {
    children = normalizeChildren(children);
  } else if (normalizationType === SIMPLE_NORMALIZE) {
    children = simpleNormalizeChildren(children);
  }
  var vnode, ns;
  if (typeof tag === 'string') {
    var Ctor;
    ns = config.getTagNamespace(tag);
    if (config.isReservedTag(tag)) {
      // platform built-in elements
      vnode = new VNode(config.parsePlatformTagName(tag), data, children, undefined, undefined, context);
    } else if (isDef(Ctor = resolveAsset(context.$options, 'components', tag))) {
      // component
      vnode = createComponent(Ctor, data, context, children, tag);
    } else {
      // unknown or unlisted namespaced elements
      // check at runtime because it may get assigned a namespace when its
      // parent normalizes children
      vnode = new VNode(tag, data, children, undefined, undefined, context);
    }
  } else {
    // direct component options / constructor
    vnode = createComponent(tag, data, context, children);
  }
  if (isDef(vnode)) {
    if (ns) {
      applyNS(vnode, ns);
    }
    return vnode;
  } else {
    return createEmptyVNode();
  }
}

function applyNS(vnode, ns) {
  vnode.ns = ns;
  if (vnode.tag === 'foreignObject') {
    // use default namespace inside foreignObject
    return;
  }
  if (isDef(vnode.children)) {
    for (var i = 0, l = vnode.children.length; i < l; i++) {
      var child = vnode.children[i];
      if (isDef(child.tag) && isUndef(child.ns)) {
        applyNS(child, ns);
      }
    }
  }
}

/*  */

/**
 * Runtime helper for rendering v-for lists.
 */
function renderList(val, render) {
  var ret, i, l, keys, key;
  if (Array.isArray(val) || typeof val === 'string') {
    ret = new Array(val.length);
    for (i = 0, l = val.length; i < l; i++) {
      ret[i] = render(val[i], i);
    }
  } else if (typeof val === 'number') {
    ret = new Array(val);
    for (i = 0; i < val; i++) {
      ret[i] = render(i + 1, i);
    }
  } else if (isObject(val)) {
    keys = Object.keys(val);
    ret = new Array(keys.length);
    for (i = 0, l = keys.length; i < l; i++) {
      key = keys[i];
      ret[i] = render(val[key], key, i);
    }
  }
  if (isDef(ret)) {
    ret._isVList = true;
  }
  return ret;
}

/*  */

/**
 * Runtime helper for rendering <slot>
 */
function renderSlot(name, fallback, props, bindObject) {
  var scopedSlotFn = this.$scopedSlots[name];
  if (scopedSlotFn) {
    // scoped slot
    props = props || {};
    if (bindObject) {
      extend(props, bindObject);
    }
    return scopedSlotFn(props) || fallback;
  } else {
    var slotNodes = this.$slots[name];
    // warn duplicate slot usage
    if (slotNodes && process.env.NODE_ENV !== 'production') {
      slotNodes._rendered && warn("Duplicate presence of slot \"" + name + "\" found in the same render tree " + "- this will likely cause render errors.", this);
      slotNodes._rendered = true;
    }
    return slotNodes || fallback;
  }
}

/*  */

/**
 * Runtime helper for resolving filters
 */
function resolveFilter(id) {
  return resolveAsset(this.$options, 'filters', id, true) || identity;
}

/*  */

/**
 * Runtime helper for checking keyCodes from config.
 */
function checkKeyCodes(eventKeyCode, key, builtInAlias) {
  var keyCodes = config.keyCodes[key] || builtInAlias;
  if (Array.isArray(keyCodes)) {
    return keyCodes.indexOf(eventKeyCode) === -1;
  } else {
    return keyCodes !== eventKeyCode;
  }
}

/*  */

/**
 * Runtime helper for merging v-bind="object" into a VNode's data.
 */
function bindObjectProps(data, tag, value, asProp) {
  if (value) {
    if (!isObject(value)) {
      process.env.NODE_ENV !== 'production' && warn('v-bind without argument expects an Object or Array value', this);
    } else {
      if (Array.isArray(value)) {
        value = toObject(value);
      }
      var hash;
      for (var key in value) {
        if (key === 'class' || key === 'style') {
          hash = data;
        } else {
          var type = data.attrs && data.attrs.type;
          hash = asProp || config.mustUseProp(tag, type, key) ? data.domProps || (data.domProps = {}) : data.attrs || (data.attrs = {});
        }
        if (!(key in hash)) {
          hash[key] = value[key];
        }
      }
    }
  }
  return data;
}

/*  */

/**
 * Runtime helper for rendering static trees.
 */
function renderStatic(index, isInFor) {
  var tree = this._staticTrees[index];
  // if has already-rendered static tree and not inside v-for,
  // we can reuse the same tree by doing a shallow clone.
  if (tree && !isInFor) {
    return Array.isArray(tree) ? cloneVNodes(tree) : cloneVNode(tree);
  }
  // otherwise, render a fresh tree.
  tree = this._staticTrees[index] = this.$options.staticRenderFns[index].call(this._renderProxy);
  markStatic(tree, "__static__" + index, false);
  return tree;
}

/**
 * Runtime helper for v-once.
 * Effectively it means marking the node as static with a unique key.
 */
function markOnce(tree, index, key) {
  markStatic(tree, "__once__" + index + (key ? "_" + key : ""), true);
  return tree;
}

function markStatic(tree, key, isOnce) {
  if (Array.isArray(tree)) {
    for (var i = 0; i < tree.length; i++) {
      if (tree[i] && typeof tree[i] !== 'string') {
        markStaticNode(tree[i], key + "_" + i, isOnce);
      }
    }
  } else {
    markStaticNode(tree, key, isOnce);
  }
}

function markStaticNode(node, key, isOnce) {
  node.isStatic = true;
  node.key = key;
  node.isOnce = isOnce;
}

/*  */

function initRender(vm) {
  vm._vnode = null; // the root of the child tree
  vm._staticTrees = null;
  var parentVnode = vm.$vnode = vm.$options._parentVnode; // the placeholder node in parent tree
  var renderContext = parentVnode && parentVnode.context;
  vm.$slots = resolveSlots(vm.$options._renderChildren, renderContext);
  vm.$scopedSlots = emptyObject;
  // bind the createElement fn to this instance
  // so that we get proper render context inside it.
  // args order: tag, data, children, normalizationType, alwaysNormalize
  // internal version is used by render functions compiled from templates
  vm._c = function (a, b, c, d) {
    return createElement(vm, a, b, c, d, false);
  };
  // normalization is always applied for the public version, used in
  // user-written render functions.
  vm.$createElement = function (a, b, c, d) {
    return createElement(vm, a, b, c, d, true);
  };
}

function renderMixin(Vue) {
  Vue.prototype.$nextTick = function (fn) {
    return nextTick(fn, this);
  };

  Vue.prototype._render = function () {
    var vm = this;
    var ref = vm.$options;
    var render = ref.render;
    var staticRenderFns = ref.staticRenderFns;
    var _parentVnode = ref._parentVnode;

    if (vm._isMounted) {
      // clone slot nodes on re-renders
      for (var key in vm.$slots) {
        vm.$slots[key] = cloneVNodes(vm.$slots[key]);
      }
    }

    vm.$scopedSlots = _parentVnode && _parentVnode.data.scopedSlots || emptyObject;

    if (staticRenderFns && !vm._staticTrees) {
      vm._staticTrees = [];
    }
    // set parent vnode. this allows render functions to have access
    // to the data on the placeholder node.
    vm.$vnode = _parentVnode;
    // render self
    var vnode;
    try {
      vnode = render.call(vm._renderProxy, vm.$createElement);
    } catch (e) {
      handleError(e, vm, "render function");
      // return error render result,
      // or previous vnode to prevent render error causing blank component
      /* istanbul ignore else */
      if (process.env.NODE_ENV !== 'production') {
        vnode = vm.$options.renderError ? vm.$options.renderError.call(vm._renderProxy, vm.$createElement, e) : vm._vnode;
      } else {
        vnode = vm._vnode;
      }
    }
    // return empty vnode in case the render function errored out
    if (!(vnode instanceof VNode)) {
      if (process.env.NODE_ENV !== 'production' && Array.isArray(vnode)) {
        warn('Multiple root nodes returned from render function. Render function ' + 'should return a single root node.', vm);
      }
      vnode = createEmptyVNode();
    }
    // set parent
    vnode.parent = _parentVnode;
    return vnode;
  };

  // internal render helpers.
  // these are exposed on the instance prototype to reduce generated render
  // code size.
  Vue.prototype._o = markOnce;
  Vue.prototype._n = toNumber;
  Vue.prototype._s = toString;
  Vue.prototype._l = renderList;
  Vue.prototype._t = renderSlot;
  Vue.prototype._q = looseEqual;
  Vue.prototype._i = looseIndexOf;
  Vue.prototype._m = renderStatic;
  Vue.prototype._f = resolveFilter;
  Vue.prototype._k = checkKeyCodes;
  Vue.prototype._b = bindObjectProps;
  Vue.prototype._v = createTextVNode;
  Vue.prototype._e = createEmptyVNode;
  Vue.prototype._u = resolveScopedSlots;
}

/*  */

var uid$1 = 0;

function initMixin(Vue) {
  Vue.prototype._init = function (options) {
    var vm = this;
    // a uid
    vm._uid = uid$1++;

    var startTag, endTag;
    /* istanbul ignore if */
    if (process.env.NODE_ENV !== 'production' && config.performance && mark) {
      startTag = "vue-perf-init:" + vm._uid;
      endTag = "vue-perf-end:" + vm._uid;
      mark(startTag);
    }

    // a flag to avoid this being observed
    vm._isVue = true;
    // merge options
    if (options && options._isComponent) {
      // optimize internal component instantiation
      // since dynamic options merging is pretty slow, and none of the
      // internal component options needs special treatment.
      initInternalComponent(vm, options);
    } else {
      vm.$options = mergeOptions(resolveConstructorOptions(vm.constructor), options || {}, vm);
    }
    /* istanbul ignore else */
    if (process.env.NODE_ENV !== 'production') {
      initProxy(vm);
    } else {
      vm._renderProxy = vm;
    }
    // expose real self
    vm._self = vm;
    initLifecycle(vm);
    initEvents(vm);
    initRender(vm);
    callHook(vm, 'beforeCreate');
    initInjections(vm); // resolve injections before data/props
    initState(vm);
    initProvide(vm); // resolve provide after data/props
    callHook(vm, 'created');

    /* istanbul ignore if */
    if (process.env.NODE_ENV !== 'production' && config.performance && mark) {
      vm._name = formatComponentName(vm, false);
      mark(endTag);
      measure(vm._name + " init", startTag, endTag);
    }

    if (vm.$options.el) {
      vm.$mount(vm.$options.el);
    }
  };
}

function initInternalComponent(vm, options) {
  var opts = vm.$options = Object.create(vm.constructor.options);
  // doing this because it's faster than dynamic enumeration.
  opts.parent = options.parent;
  opts.propsData = options.propsData;
  opts._parentVnode = options._parentVnode;
  opts._parentListeners = options._parentListeners;
  opts._renderChildren = options._renderChildren;
  opts._componentTag = options._componentTag;
  opts._parentElm = options._parentElm;
  opts._refElm = options._refElm;
  if (options.render) {
    opts.render = options.render;
    opts.staticRenderFns = options.staticRenderFns;
  }
}

function resolveConstructorOptions(Ctor) {
  var options = Ctor.options;
  if (Ctor.super) {
    var superOptions = resolveConstructorOptions(Ctor.super);
    var cachedSuperOptions = Ctor.superOptions;
    if (superOptions !== cachedSuperOptions) {
      // super option changed,
      // need to resolve new options.
      Ctor.superOptions = superOptions;
      // check if there are any late-modified/attached options (#4976)
      var modifiedOptions = resolveModifiedOptions(Ctor);
      // update base extend options
      if (modifiedOptions) {
        extend(Ctor.extendOptions, modifiedOptions);
      }
      options = Ctor.options = mergeOptions(superOptions, Ctor.extendOptions);
      if (options.name) {
        options.components[options.name] = Ctor;
      }
    }
  }
  return options;
}

function resolveModifiedOptions(Ctor) {
  var modified;
  var latest = Ctor.options;
  var extended = Ctor.extendOptions;
  var sealed = Ctor.sealedOptions;
  for (var key in latest) {
    if (latest[key] !== sealed[key]) {
      if (!modified) {
        modified = {};
      }
      modified[key] = dedupe(latest[key], extended[key], sealed[key]);
    }
  }
  return modified;
}

function dedupe(latest, extended, sealed) {
  // compare latest and sealed to ensure lifecycle hooks won't be duplicated
  // between merges
  if (Array.isArray(latest)) {
    var res = [];
    sealed = Array.isArray(sealed) ? sealed : [sealed];
    extended = Array.isArray(extended) ? extended : [extended];
    for (var i = 0; i < latest.length; i++) {
      // push original options and not sealed options to exclude duplicated options
      if (extended.indexOf(latest[i]) >= 0 || sealed.indexOf(latest[i]) < 0) {
        res.push(latest[i]);
      }
    }
    return res;
  } else {
    return latest;
  }
}

function Vue$3(options) {
  if (process.env.NODE_ENV !== 'production' && !(this instanceof Vue$3)) {
    warn('Vue is a constructor and should be called with the `new` keyword');
  }
  this._init(options);
}

initMixin(Vue$3);
stateMixin(Vue$3);
eventsMixin(Vue$3);
lifecycleMixin(Vue$3);
renderMixin(Vue$3);

/*  */

function initUse(Vue) {
  Vue.use = function (plugin) {
    /* istanbul ignore if */
    if (plugin.installed) {
      return this;
    }
    // additional parameters
    var args = toArray(arguments, 1);
    args.unshift(this);
    if (typeof plugin.install === 'function') {
      plugin.install.apply(plugin, args);
    } else if (typeof plugin === 'function') {
      plugin.apply(null, args);
    }
    plugin.installed = true;
    return this;
  };
}

/*  */

function initMixin$1(Vue) {
  Vue.mixin = function (mixin) {
    this.options = mergeOptions(this.options, mixin);
    return this;
  };
}

/*  */

function initExtend(Vue) {
  /**
   * Each instance constructor, including Vue, has a unique
   * cid. This enables us to create wrapped "child
   * constructors" for prototypal inheritance and cache them.
   */
  Vue.cid = 0;
  var cid = 1;

  /**
   * Class inheritance
   */
  Vue.extend = function (extendOptions) {
    extendOptions = extendOptions || {};
    var Super = this;
    var SuperId = Super.cid;
    var cachedCtors = extendOptions._Ctor || (extendOptions._Ctor = {});
    if (cachedCtors[SuperId]) {
      return cachedCtors[SuperId];
    }

    var name = extendOptions.name || Super.options.name;
    if (process.env.NODE_ENV !== 'production') {
      if (!/^[a-zA-Z][\w-]*$/.test(name)) {
        warn('Invalid component name: "' + name + '". Component names ' + 'can only contain alphanumeric characters and the hyphen, ' + 'and must start with a letter.');
      }
    }

    var Sub = function VueComponent(options) {
      this._init(options);
    };
    Sub.prototype = Object.create(Super.prototype);
    Sub.prototype.constructor = Sub;
    Sub.cid = cid++;
    Sub.options = mergeOptions(Super.options, extendOptions);
    Sub['super'] = Super;

    // For props and computed properties, we define the proxy getters on
    // the Vue instances at extension time, on the extended prototype. This
    // avoids Object.defineProperty calls for each instance created.
    if (Sub.options.props) {
      initProps$1(Sub);
    }
    if (Sub.options.computed) {
      initComputed$1(Sub);
    }

    // allow further extension/mixin/plugin usage
    Sub.extend = Super.extend;
    Sub.mixin = Super.mixin;
    Sub.use = Super.use;

    // create asset registers, so extended classes
    // can have their private assets too.
    ASSET_TYPES.forEach(function (type) {
      Sub[type] = Super[type];
    });
    // enable recursive self-lookup
    if (name) {
      Sub.options.components[name] = Sub;
    }

    // keep a reference to the super options at extension time.
    // later at instantiation we can check if Super's options have
    // been updated.
    Sub.superOptions = Super.options;
    Sub.extendOptions = extendOptions;
    Sub.sealedOptions = extend({}, Sub.options);

    // cache constructor
    cachedCtors[SuperId] = Sub;
    return Sub;
  };
}

function initProps$1(Comp) {
  var props = Comp.options.props;
  for (var key in props) {
    proxy(Comp.prototype, "_props", key);
  }
}

function initComputed$1(Comp) {
  var computed = Comp.options.computed;
  for (var key in computed) {
    defineComputed(Comp.prototype, key, computed[key]);
  }
}

/*  */

function initAssetRegisters(Vue) {
  /**
   * Create asset registration methods.
   */
  ASSET_TYPES.forEach(function (type) {
    Vue[type] = function (id, definition) {
      if (!definition) {
        return this.options[type + 's'][id];
      } else {
        /* istanbul ignore if */
        if (process.env.NODE_ENV !== 'production') {
          if (type === 'component' && config.isReservedTag(id)) {
            warn('Do not use built-in or reserved HTML elements as component ' + 'id: ' + id);
          }
        }
        if (type === 'component' && isPlainObject(definition)) {
          definition.name = definition.name || id;
          definition = this.options._base.extend(definition);
        }
        if (type === 'directive' && typeof definition === 'function') {
          definition = { bind: definition, update: definition };
        }
        this.options[type + 's'][id] = definition;
        return definition;
      }
    };
  });
}

/*  */

var patternTypes = [String, RegExp];

function getComponentName(opts) {
  return opts && (opts.Ctor.options.name || opts.tag);
}

function matches(pattern, name) {
  if (typeof pattern === 'string') {
    return pattern.split(',').indexOf(name) > -1;
  } else if (isRegExp(pattern)) {
    return pattern.test(name);
  }
  /* istanbul ignore next */
  return false;
}

function pruneCache(cache, current, filter) {
  for (var key in cache) {
    var cachedNode = cache[key];
    if (cachedNode) {
      var name = getComponentName(cachedNode.componentOptions);
      if (name && !filter(name)) {
        if (cachedNode !== current) {
          pruneCacheEntry(cachedNode);
        }
        cache[key] = null;
      }
    }
  }
}

function pruneCacheEntry(vnode) {
  if (vnode) {
    vnode.componentInstance.$destroy();
  }
}

var KeepAlive = {
  name: 'keep-alive',
  abstract: true,

  props: {
    include: patternTypes,
    exclude: patternTypes
  },

  created: function created() {
    this.cache = Object.create(null);
  },

  destroyed: function destroyed() {
    var this$1 = this;

    for (var key in this$1.cache) {
      pruneCacheEntry(this$1.cache[key]);
    }
  },

  watch: {
    include: function include(val) {
      pruneCache(this.cache, this._vnode, function (name) {
        return matches(val, name);
      });
    },
    exclude: function exclude(val) {
      pruneCache(this.cache, this._vnode, function (name) {
        return !matches(val, name);
      });
    }
  },

  render: function render() {
    var vnode = getFirstComponentChild(this.$slots.default);
    var componentOptions = vnode && vnode.componentOptions;
    if (componentOptions) {
      // check pattern
      var name = getComponentName(componentOptions);
      if (name && (this.include && !matches(this.include, name) || this.exclude && matches(this.exclude, name))) {
        return vnode;
      }
      var key = vnode.key == null
      // same constructor may get registered as different local components
      // so cid alone is not enough (#3269)
      ? componentOptions.Ctor.cid + (componentOptions.tag ? "::" + componentOptions.tag : '') : vnode.key;
      if (this.cache[key]) {
        vnode.componentInstance = this.cache[key].componentInstance;
      } else {
        this.cache[key] = vnode;
      }
      vnode.data.keepAlive = true;
    }
    return vnode;
  }
};

var builtInComponents = {
  KeepAlive: KeepAlive
};

/*  */

function initGlobalAPI(Vue) {
  // config
  var configDef = {};
  configDef.get = function () {
    return config;
  };
  if (process.env.NODE_ENV !== 'production') {
    configDef.set = function () {
      warn('Do not replace the Vue.config object, set individual fields instead.');
    };
  }
  Object.defineProperty(Vue, 'config', configDef);

  // exposed util methods.
  // NOTE: these are not considered part of the public API - avoid relying on
  // them unless you are aware of the risk.
  Vue.util = {
    warn: warn,
    extend: extend,
    mergeOptions: mergeOptions,
    defineReactive: defineReactive$$1
  };

  Vue.set = set;
  Vue.delete = del;
  Vue.nextTick = nextTick;

  Vue.options = Object.create(null);
  ASSET_TYPES.forEach(function (type) {
    Vue.options[type + 's'] = Object.create(null);
  });

  // this is used to identify the "base" constructor to extend all plain-object
  // components with in Weex's multi-instance scenarios.
  Vue.options._base = Vue;

  extend(Vue.options.components, builtInComponents);

  initUse(Vue);
  initMixin$1(Vue);
  initExtend(Vue);
  initAssetRegisters(Vue);
}

initGlobalAPI(Vue$3);

Object.defineProperty(Vue$3.prototype, '$isServer', {
  get: isServerRendering
});

Object.defineProperty(Vue$3.prototype, '$ssrContext', {
  get: function get() {
    /* istanbul ignore next */
    return this.$vnode.ssrContext;
  }
});

Vue$3.version = '2.3.4';

/*  */

// these are reserved for web because they are directly compiled away
// during template compilation
var isReservedAttr = makeMap('style,class');

// attributes that should be using props for binding
var acceptValue = makeMap('input,textarea,option,select');
var mustUseProp = function mustUseProp(tag, type, attr) {
  return attr === 'value' && acceptValue(tag) && type !== 'button' || attr === 'selected' && tag === 'option' || attr === 'checked' && tag === 'input' || attr === 'muted' && tag === 'video';
};

var isEnumeratedAttr = makeMap('contenteditable,draggable,spellcheck');

var isBooleanAttr = makeMap('allowfullscreen,async,autofocus,autoplay,checked,compact,controls,declare,' + 'default,defaultchecked,defaultmuted,defaultselected,defer,disabled,' + 'enabled,formnovalidate,hidden,indeterminate,inert,ismap,itemscope,loop,multiple,' + 'muted,nohref,noresize,noshade,novalidate,nowrap,open,pauseonexit,readonly,' + 'required,reversed,scoped,seamless,selected,sortable,translate,' + 'truespeed,typemustmatch,visible');

var xlinkNS = 'http://www.w3.org/1999/xlink';

var isXlink = function isXlink(name) {
  return name.charAt(5) === ':' && name.slice(0, 5) === 'xlink';
};

var getXlinkProp = function getXlinkProp(name) {
  return isXlink(name) ? name.slice(6, name.length) : '';
};

var isFalsyAttrValue = function isFalsyAttrValue(val) {
  return val == null || val === false;
};

/*  */

function genClassForVnode(vnode) {
  var data = vnode.data;
  var parentNode = vnode;
  var childNode = vnode;
  while (isDef(childNode.componentInstance)) {
    childNode = childNode.componentInstance._vnode;
    if (childNode.data) {
      data = mergeClassData(childNode.data, data);
    }
  }
  while (isDef(parentNode = parentNode.parent)) {
    if (parentNode.data) {
      data = mergeClassData(data, parentNode.data);
    }
  }
  return genClassFromData(data);
}

function mergeClassData(child, parent) {
  return {
    staticClass: concat(child.staticClass, parent.staticClass),
    class: isDef(child.class) ? [child.class, parent.class] : parent.class
  };
}

function genClassFromData(data) {
  var dynamicClass = data.class;
  var staticClass = data.staticClass;
  if (isDef(staticClass) || isDef(dynamicClass)) {
    return concat(staticClass, stringifyClass(dynamicClass));
  }
  /* istanbul ignore next */
  return '';
}

function concat(a, b) {
  return a ? b ? a + ' ' + b : a : b || '';
}

function stringifyClass(value) {
  if (isUndef(value)) {
    return '';
  }
  if (typeof value === 'string') {
    return value;
  }
  var res = '';
  if (Array.isArray(value)) {
    var stringified;
    for (var i = 0, l = value.length; i < l; i++) {
      if (isDef(value[i])) {
        if (isDef(stringified = stringifyClass(value[i])) && stringified !== '') {
          res += stringified + ' ';
        }
      }
    }
    return res.slice(0, -1);
  }
  if (isObject(value)) {
    for (var key in value) {
      if (value[key]) {
        res += key + ' ';
      }
    }
    return res.slice(0, -1);
  }
  /* istanbul ignore next */
  return res;
}

/*  */

var namespaceMap = {
  svg: 'http://www.w3.org/2000/svg',
  math: 'http://www.w3.org/1998/Math/MathML'
};

var isHTMLTag = makeMap('html,body,base,head,link,meta,style,title,' + 'address,article,aside,footer,header,h1,h2,h3,h4,h5,h6,hgroup,nav,section,' + 'div,dd,dl,dt,figcaption,figure,hr,img,li,main,ol,p,pre,ul,' + 'a,b,abbr,bdi,bdo,br,cite,code,data,dfn,em,i,kbd,mark,q,rp,rt,rtc,ruby,' + 's,samp,small,span,strong,sub,sup,time,u,var,wbr,area,audio,map,track,video,' + 'embed,object,param,source,canvas,script,noscript,del,ins,' + 'caption,col,colgroup,table,thead,tbody,td,th,tr,' + 'button,datalist,fieldset,form,input,label,legend,meter,optgroup,option,' + 'output,progress,select,textarea,' + 'details,dialog,menu,menuitem,summary,' + 'content,element,shadow,template');

// this map is intentionally selective, only covering SVG elements that may
// contain child elements.
var isSVG = makeMap('svg,animate,circle,clippath,cursor,defs,desc,ellipse,filter,font-face,' + 'foreignObject,g,glyph,image,line,marker,mask,missing-glyph,path,pattern,' + 'polygon,polyline,rect,switch,symbol,text,textpath,tspan,use,view', true);

var isPreTag = function isPreTag(tag) {
  return tag === 'pre';
};

var isReservedTag = function isReservedTag(tag) {
  return isHTMLTag(tag) || isSVG(tag);
};

function getTagNamespace(tag) {
  if (isSVG(tag)) {
    return 'svg';
  }
  // basic support for MathML
  // note it doesn't support other MathML elements being component roots
  if (tag === 'math') {
    return 'math';
  }
}

var unknownElementCache = Object.create(null);
function isUnknownElement(tag) {
  /* istanbul ignore if */
  if (!inBrowser) {
    return true;
  }
  if (isReservedTag(tag)) {
    return false;
  }
  tag = tag.toLowerCase();
  /* istanbul ignore if */
  if (unknownElementCache[tag] != null) {
    return unknownElementCache[tag];
  }
  var el = document.createElement(tag);
  if (tag.indexOf('-') > -1) {
    // http://stackoverflow.com/a/28210364/1070244
    return unknownElementCache[tag] = el.constructor === window.HTMLUnknownElement || el.constructor === window.HTMLElement;
  } else {
    return unknownElementCache[tag] = /HTMLUnknownElement/.test(el.toString());
  }
}

/*  */

/**
 * Query an element selector if it's not an element already.
 */
function query(el) {
  if (typeof el === 'string') {
    var selected = document.querySelector(el);
    if (!selected) {
      process.env.NODE_ENV !== 'production' && warn('Cannot find element: ' + el);
      return document.createElement('div');
    }
    return selected;
  } else {
    return el;
  }
}

/*  */

function createElement$1(tagName, vnode) {
  var elm = document.createElement(tagName);
  if (tagName !== 'select') {
    return elm;
  }
  // false or null will remove the attribute but undefined will not
  if (vnode.data && vnode.data.attrs && vnode.data.attrs.multiple !== undefined) {
    elm.setAttribute('multiple', 'multiple');
  }
  return elm;
}

function createElementNS(namespace, tagName) {
  return document.createElementNS(namespaceMap[namespace], tagName);
}

function createTextNode(text) {
  return document.createTextNode(text);
}

function createComment(text) {
  return document.createComment(text);
}

function insertBefore(parentNode, newNode, referenceNode) {
  parentNode.insertBefore(newNode, referenceNode);
}

function removeChild(node, child) {
  node.removeChild(child);
}

function appendChild(node, child) {
  node.appendChild(child);
}

function parentNode(node) {
  return node.parentNode;
}

function nextSibling(node) {
  return node.nextSibling;
}

function tagName(node) {
  return node.tagName;
}

function setTextContent(node, text) {
  node.textContent = text;
}

function setAttribute(node, key, val) {
  node.setAttribute(key, val);
}

var nodeOps = Object.freeze({
  createElement: createElement$1,
  createElementNS: createElementNS,
  createTextNode: createTextNode,
  createComment: createComment,
  insertBefore: insertBefore,
  removeChild: removeChild,
  appendChild: appendChild,
  parentNode: parentNode,
  nextSibling: nextSibling,
  tagName: tagName,
  setTextContent: setTextContent,
  setAttribute: setAttribute
});

/*  */

var ref = {
  create: function create(_, vnode) {
    registerRef(vnode);
  },
  update: function update(oldVnode, vnode) {
    if (oldVnode.data.ref !== vnode.data.ref) {
      registerRef(oldVnode, true);
      registerRef(vnode);
    }
  },
  destroy: function destroy(vnode) {
    registerRef(vnode, true);
  }
};

function registerRef(vnode, isRemoval) {
  var key = vnode.data.ref;
  if (!key) {
    return;
  }

  var vm = vnode.context;
  var ref = vnode.componentInstance || vnode.elm;
  var refs = vm.$refs;
  if (isRemoval) {
    if (Array.isArray(refs[key])) {
      remove(refs[key], ref);
    } else if (refs[key] === ref) {
      refs[key] = undefined;
    }
  } else {
    if (vnode.data.refInFor) {
      if (Array.isArray(refs[key]) && refs[key].indexOf(ref) < 0) {
        refs[key].push(ref);
      } else {
        refs[key] = [ref];
      }
    } else {
      refs[key] = ref;
    }
  }
}

/**
 * Virtual DOM patching algorithm based on Snabbdom by
 * Simon Friis Vindum (@paldepind)
 * Licensed under the MIT License
 * https://github.com/paldepind/snabbdom/blob/master/LICENSE
 *
 * modified by Evan You (@yyx990803)
 *

/*
 * Not type-checking this because this file is perf-critical and the cost
 * of making flow understand it is not worth it.
 */

var emptyNode = new VNode('', {}, []);

var hooks = ['create', 'activate', 'update', 'remove', 'destroy'];

function sameVnode(a, b) {
  return a.key === b.key && a.tag === b.tag && a.isComment === b.isComment && isDef(a.data) === isDef(b.data) && sameInputType(a, b);
}

// Some browsers do not support dynamically changing type for <input>
// so they need to be treated as different nodes
function sameInputType(a, b) {
  if (a.tag !== 'input') {
    return true;
  }
  var i;
  var typeA = isDef(i = a.data) && isDef(i = i.attrs) && i.type;
  var typeB = isDef(i = b.data) && isDef(i = i.attrs) && i.type;
  return typeA === typeB;
}

function createKeyToOldIdx(children, beginIdx, endIdx) {
  var i, key;
  var map = {};
  for (i = beginIdx; i <= endIdx; ++i) {
    key = children[i].key;
    if (isDef(key)) {
      map[key] = i;
    }
  }
  return map;
}

function createPatchFunction(backend) {
  var i, j;
  var cbs = {};

  var modules = backend.modules;
  var nodeOps = backend.nodeOps;

  for (i = 0; i < hooks.length; ++i) {
    cbs[hooks[i]] = [];
    for (j = 0; j < modules.length; ++j) {
      if (isDef(modules[j][hooks[i]])) {
        cbs[hooks[i]].push(modules[j][hooks[i]]);
      }
    }
  }

  function emptyNodeAt(elm) {
    return new VNode(nodeOps.tagName(elm).toLowerCase(), {}, [], undefined, elm);
  }

  function createRmCb(childElm, listeners) {
    function remove$$1() {
      if (--remove$$1.listeners === 0) {
        removeNode(childElm);
      }
    }
    remove$$1.listeners = listeners;
    return remove$$1;
  }

  function removeNode(el) {
    var parent = nodeOps.parentNode(el);
    // element may have already been removed due to v-html / v-text
    if (isDef(parent)) {
      nodeOps.removeChild(parent, el);
    }
  }

  var inPre = 0;
  function createElm(vnode, insertedVnodeQueue, parentElm, refElm, nested) {
    vnode.isRootInsert = !nested; // for transition enter check
    if (createComponent(vnode, insertedVnodeQueue, parentElm, refElm)) {
      return;
    }

    var data = vnode.data;
    var children = vnode.children;
    var tag = vnode.tag;
    if (isDef(tag)) {
      if (process.env.NODE_ENV !== 'production') {
        if (data && data.pre) {
          inPre++;
        }
        if (!inPre && !vnode.ns && !(config.ignoredElements.length && config.ignoredElements.indexOf(tag) > -1) && config.isUnknownElement(tag)) {
          warn('Unknown custom element: <' + tag + '> - did you ' + 'register the component correctly? For recursive components, ' + 'make sure to provide the "name" option.', vnode.context);
        }
      }
      vnode.elm = vnode.ns ? nodeOps.createElementNS(vnode.ns, tag) : nodeOps.createElement(tag, vnode);
      setScope(vnode);

      /* istanbul ignore if */
      {
        createChildren(vnode, children, insertedVnodeQueue);
        if (isDef(data)) {
          invokeCreateHooks(vnode, insertedVnodeQueue);
        }
        insert(parentElm, vnode.elm, refElm);
      }

      if (process.env.NODE_ENV !== 'production' && data && data.pre) {
        inPre--;
      }
    } else if (isTrue(vnode.isComment)) {
      vnode.elm = nodeOps.createComment(vnode.text);
      insert(parentElm, vnode.elm, refElm);
    } else {
      vnode.elm = nodeOps.createTextNode(vnode.text);
      insert(parentElm, vnode.elm, refElm);
    }
  }

  function createComponent(vnode, insertedVnodeQueue, parentElm, refElm) {
    var i = vnode.data;
    if (isDef(i)) {
      var isReactivated = isDef(vnode.componentInstance) && i.keepAlive;
      if (isDef(i = i.hook) && isDef(i = i.init)) {
        i(vnode, false /* hydrating */, parentElm, refElm);
      }
      // after calling the init hook, if the vnode is a child component
      // it should've created a child instance and mounted it. the child
      // component also has set the placeholder vnode's elm.
      // in that case we can just return the element and be done.
      if (isDef(vnode.componentInstance)) {
        initComponent(vnode, insertedVnodeQueue);
        if (isTrue(isReactivated)) {
          reactivateComponent(vnode, insertedVnodeQueue, parentElm, refElm);
        }
        return true;
      }
    }
  }

  function initComponent(vnode, insertedVnodeQueue) {
    if (isDef(vnode.data.pendingInsert)) {
      insertedVnodeQueue.push.apply(insertedVnodeQueue, vnode.data.pendingInsert);
      vnode.data.pendingInsert = null;
    }
    vnode.elm = vnode.componentInstance.$el;
    if (isPatchable(vnode)) {
      invokeCreateHooks(vnode, insertedVnodeQueue);
      setScope(vnode);
    } else {
      // empty component root.
      // skip all element-related modules except for ref (#3455)
      registerRef(vnode);
      // make sure to invoke the insert hook
      insertedVnodeQueue.push(vnode);
    }
  }

  function reactivateComponent(vnode, insertedVnodeQueue, parentElm, refElm) {
    var i;
    // hack for #4339: a reactivated component with inner transition
    // does not trigger because the inner node's created hooks are not called
    // again. It's not ideal to involve module-specific logic in here but
    // there doesn't seem to be a better way to do it.
    var innerNode = vnode;
    while (innerNode.componentInstance) {
      innerNode = innerNode.componentInstance._vnode;
      if (isDef(i = innerNode.data) && isDef(i = i.transition)) {
        for (i = 0; i < cbs.activate.length; ++i) {
          cbs.activate[i](emptyNode, innerNode);
        }
        insertedVnodeQueue.push(innerNode);
        break;
      }
    }
    // unlike a newly created component,
    // a reactivated keep-alive component doesn't insert itself
    insert(parentElm, vnode.elm, refElm);
  }

  function insert(parent, elm, ref) {
    if (isDef(parent)) {
      if (isDef(ref)) {
        if (ref.parentNode === parent) {
          nodeOps.insertBefore(parent, elm, ref);
        }
      } else {
        nodeOps.appendChild(parent, elm);
      }
    }
  }

  function createChildren(vnode, children, insertedVnodeQueue) {
    if (Array.isArray(children)) {
      for (var i = 0; i < children.length; ++i) {
        createElm(children[i], insertedVnodeQueue, vnode.elm, null, true);
      }
    } else if (isPrimitive(vnode.text)) {
      nodeOps.appendChild(vnode.elm, nodeOps.createTextNode(vnode.text));
    }
  }

  function isPatchable(vnode) {
    while (vnode.componentInstance) {
      vnode = vnode.componentInstance._vnode;
    }
    return isDef(vnode.tag);
  }

  function invokeCreateHooks(vnode, insertedVnodeQueue) {
    for (var i$1 = 0; i$1 < cbs.create.length; ++i$1) {
      cbs.create[i$1](emptyNode, vnode);
    }
    i = vnode.data.hook; // Reuse variable
    if (isDef(i)) {
      if (isDef(i.create)) {
        i.create(emptyNode, vnode);
      }
      if (isDef(i.insert)) {
        insertedVnodeQueue.push(vnode);
      }
    }
  }

  // set scope id attribute for scoped CSS.
  // this is implemented as a special case to avoid the overhead
  // of going through the normal attribute patching process.
  function setScope(vnode) {
    var i;
    var ancestor = vnode;
    while (ancestor) {
      if (isDef(i = ancestor.context) && isDef(i = i.$options._scopeId)) {
        nodeOps.setAttribute(vnode.elm, i, '');
      }
      ancestor = ancestor.parent;
    }
    // for slot content they should also get the scopeId from the host instance.
    if (isDef(i = activeInstance) && i !== vnode.context && isDef(i = i.$options._scopeId)) {
      nodeOps.setAttribute(vnode.elm, i, '');
    }
  }

  function addVnodes(parentElm, refElm, vnodes, startIdx, endIdx, insertedVnodeQueue) {
    for (; startIdx <= endIdx; ++startIdx) {
      createElm(vnodes[startIdx], insertedVnodeQueue, parentElm, refElm);
    }
  }

  function invokeDestroyHook(vnode) {
    var i, j;
    var data = vnode.data;
    if (isDef(data)) {
      if (isDef(i = data.hook) && isDef(i = i.destroy)) {
        i(vnode);
      }
      for (i = 0; i < cbs.destroy.length; ++i) {
        cbs.destroy[i](vnode);
      }
    }
    if (isDef(i = vnode.children)) {
      for (j = 0; j < vnode.children.length; ++j) {
        invokeDestroyHook(vnode.children[j]);
      }
    }
  }

  function removeVnodes(parentElm, vnodes, startIdx, endIdx) {
    for (; startIdx <= endIdx; ++startIdx) {
      var ch = vnodes[startIdx];
      if (isDef(ch)) {
        if (isDef(ch.tag)) {
          removeAndInvokeRemoveHook(ch);
          invokeDestroyHook(ch);
        } else {
          // Text node
          removeNode(ch.elm);
        }
      }
    }
  }

  function removeAndInvokeRemoveHook(vnode, rm) {
    if (isDef(rm) || isDef(vnode.data)) {
      var i;
      var listeners = cbs.remove.length + 1;
      if (isDef(rm)) {
        // we have a recursively passed down rm callback
        // increase the listeners count
        rm.listeners += listeners;
      } else {
        // directly removing
        rm = createRmCb(vnode.elm, listeners);
      }
      // recursively invoke hooks on child component root node
      if (isDef(i = vnode.componentInstance) && isDef(i = i._vnode) && isDef(i.data)) {
        removeAndInvokeRemoveHook(i, rm);
      }
      for (i = 0; i < cbs.remove.length; ++i) {
        cbs.remove[i](vnode, rm);
      }
      if (isDef(i = vnode.data.hook) && isDef(i = i.remove)) {
        i(vnode, rm);
      } else {
        rm();
      }
    } else {
      removeNode(vnode.elm);
    }
  }

  function updateChildren(parentElm, oldCh, newCh, insertedVnodeQueue, removeOnly) {
    var oldStartIdx = 0;
    var newStartIdx = 0;
    var oldEndIdx = oldCh.length - 1;
    var oldStartVnode = oldCh[0];
    var oldEndVnode = oldCh[oldEndIdx];
    var newEndIdx = newCh.length - 1;
    var newStartVnode = newCh[0];
    var newEndVnode = newCh[newEndIdx];
    var oldKeyToIdx, idxInOld, elmToMove, refElm;

    // removeOnly is a special flag used only by <transition-group>
    // to ensure removed elements stay in correct relative positions
    // during leaving transitions
    var canMove = !removeOnly;

    while (oldStartIdx <= oldEndIdx && newStartIdx <= newEndIdx) {
      if (isUndef(oldStartVnode)) {
        oldStartVnode = oldCh[++oldStartIdx]; // Vnode has been moved left
      } else if (isUndef(oldEndVnode)) {
        oldEndVnode = oldCh[--oldEndIdx];
      } else if (sameVnode(oldStartVnode, newStartVnode)) {
        patchVnode(oldStartVnode, newStartVnode, insertedVnodeQueue);
        oldStartVnode = oldCh[++oldStartIdx];
        newStartVnode = newCh[++newStartIdx];
      } else if (sameVnode(oldEndVnode, newEndVnode)) {
        patchVnode(oldEndVnode, newEndVnode, insertedVnodeQueue);
        oldEndVnode = oldCh[--oldEndIdx];
        newEndVnode = newCh[--newEndIdx];
      } else if (sameVnode(oldStartVnode, newEndVnode)) {
        // Vnode moved right
        patchVnode(oldStartVnode, newEndVnode, insertedVnodeQueue);
        canMove && nodeOps.insertBefore(parentElm, oldStartVnode.elm, nodeOps.nextSibling(oldEndVnode.elm));
        oldStartVnode = oldCh[++oldStartIdx];
        newEndVnode = newCh[--newEndIdx];
      } else if (sameVnode(oldEndVnode, newStartVnode)) {
        // Vnode moved left
        patchVnode(oldEndVnode, newStartVnode, insertedVnodeQueue);
        canMove && nodeOps.insertBefore(parentElm, oldEndVnode.elm, oldStartVnode.elm);
        oldEndVnode = oldCh[--oldEndIdx];
        newStartVnode = newCh[++newStartIdx];
      } else {
        if (isUndef(oldKeyToIdx)) {
          oldKeyToIdx = createKeyToOldIdx(oldCh, oldStartIdx, oldEndIdx);
        }
        idxInOld = isDef(newStartVnode.key) ? oldKeyToIdx[newStartVnode.key] : null;
        if (isUndef(idxInOld)) {
          // New element
          createElm(newStartVnode, insertedVnodeQueue, parentElm, oldStartVnode.elm);
          newStartVnode = newCh[++newStartIdx];
        } else {
          elmToMove = oldCh[idxInOld];
          /* istanbul ignore if */
          if (process.env.NODE_ENV !== 'production' && !elmToMove) {
            warn('It seems there are duplicate keys that is causing an update error. ' + 'Make sure each v-for item has a unique key.');
          }
          if (sameVnode(elmToMove, newStartVnode)) {
            patchVnode(elmToMove, newStartVnode, insertedVnodeQueue);
            oldCh[idxInOld] = undefined;
            canMove && nodeOps.insertBefore(parentElm, newStartVnode.elm, oldStartVnode.elm);
            newStartVnode = newCh[++newStartIdx];
          } else {
            // same key but different element. treat as new element
            createElm(newStartVnode, insertedVnodeQueue, parentElm, oldStartVnode.elm);
            newStartVnode = newCh[++newStartIdx];
          }
        }
      }
    }
    if (oldStartIdx > oldEndIdx) {
      refElm = isUndef(newCh[newEndIdx + 1]) ? null : newCh[newEndIdx + 1].elm;
      addVnodes(parentElm, refElm, newCh, newStartIdx, newEndIdx, insertedVnodeQueue);
    } else if (newStartIdx > newEndIdx) {
      removeVnodes(parentElm, oldCh, oldStartIdx, oldEndIdx);
    }
  }

  function patchVnode(oldVnode, vnode, insertedVnodeQueue, removeOnly) {
    if (oldVnode === vnode) {
      return;
    }
    // reuse element for static trees.
    // note we only do this if the vnode is cloned -
    // if the new node is not cloned it means the render functions have been
    // reset by the hot-reload-api and we need to do a proper re-render.
    if (isTrue(vnode.isStatic) && isTrue(oldVnode.isStatic) && vnode.key === oldVnode.key && (isTrue(vnode.isCloned) || isTrue(vnode.isOnce))) {
      vnode.elm = oldVnode.elm;
      vnode.componentInstance = oldVnode.componentInstance;
      return;
    }
    var i;
    var data = vnode.data;
    if (isDef(data) && isDef(i = data.hook) && isDef(i = i.prepatch)) {
      i(oldVnode, vnode);
    }
    var elm = vnode.elm = oldVnode.elm;
    var oldCh = oldVnode.children;
    var ch = vnode.children;
    if (isDef(data) && isPatchable(vnode)) {
      for (i = 0; i < cbs.update.length; ++i) {
        cbs.update[i](oldVnode, vnode);
      }
      if (isDef(i = data.hook) && isDef(i = i.update)) {
        i(oldVnode, vnode);
      }
    }
    if (isUndef(vnode.text)) {
      if (isDef(oldCh) && isDef(ch)) {
        if (oldCh !== ch) {
          updateChildren(elm, oldCh, ch, insertedVnodeQueue, removeOnly);
        }
      } else if (isDef(ch)) {
        if (isDef(oldVnode.text)) {
          nodeOps.setTextContent(elm, '');
        }
        addVnodes(elm, null, ch, 0, ch.length - 1, insertedVnodeQueue);
      } else if (isDef(oldCh)) {
        removeVnodes(elm, oldCh, 0, oldCh.length - 1);
      } else if (isDef(oldVnode.text)) {
        nodeOps.setTextContent(elm, '');
      }
    } else if (oldVnode.text !== vnode.text) {
      nodeOps.setTextContent(elm, vnode.text);
    }
    if (isDef(data)) {
      if (isDef(i = data.hook) && isDef(i = i.postpatch)) {
        i(oldVnode, vnode);
      }
    }
  }

  function invokeInsertHook(vnode, queue, initial) {
    // delay insert hooks for component root nodes, invoke them after the
    // element is really inserted
    if (isTrue(initial) && isDef(vnode.parent)) {
      vnode.parent.data.pendingInsert = queue;
    } else {
      for (var i = 0; i < queue.length; ++i) {
        queue[i].data.hook.insert(queue[i]);
      }
    }
  }

  var bailed = false;
  // list of modules that can skip create hook during hydration because they
  // are already rendered on the client or has no need for initialization
  var isRenderedModule = makeMap('attrs,style,class,staticClass,staticStyle,key');

  // Note: this is a browser-only function so we can assume elms are DOM nodes.
  function hydrate(elm, vnode, insertedVnodeQueue) {
    if (process.env.NODE_ENV !== 'production') {
      if (!assertNodeMatch(elm, vnode)) {
        return false;
      }
    }
    vnode.elm = elm;
    var tag = vnode.tag;
    var data = vnode.data;
    var children = vnode.children;
    if (isDef(data)) {
      if (isDef(i = data.hook) && isDef(i = i.init)) {
        i(vnode, true /* hydrating */);
      }
      if (isDef(i = vnode.componentInstance)) {
        // child component. it should have hydrated its own tree.
        initComponent(vnode, insertedVnodeQueue);
        return true;
      }
    }
    if (isDef(tag)) {
      if (isDef(children)) {
        // empty element, allow client to pick up and populate children
        if (!elm.hasChildNodes()) {
          createChildren(vnode, children, insertedVnodeQueue);
        } else {
          var childrenMatch = true;
          var childNode = elm.firstChild;
          for (var i$1 = 0; i$1 < children.length; i$1++) {
            if (!childNode || !hydrate(childNode, children[i$1], insertedVnodeQueue)) {
              childrenMatch = false;
              break;
            }
            childNode = childNode.nextSibling;
          }
          // if childNode is not null, it means the actual childNodes list is
          // longer than the virtual children list.
          if (!childrenMatch || childNode) {
            if (process.env.NODE_ENV !== 'production' && typeof console !== 'undefined' && !bailed) {
              bailed = true;
              console.warn('Parent: ', elm);
              console.warn('Mismatching childNodes vs. VNodes: ', elm.childNodes, children);
            }
            return false;
          }
        }
      }
      if (isDef(data)) {
        for (var key in data) {
          if (!isRenderedModule(key)) {
            invokeCreateHooks(vnode, insertedVnodeQueue);
            break;
          }
        }
      }
    } else if (elm.data !== vnode.text) {
      elm.data = vnode.text;
    }
    return true;
  }

  function assertNodeMatch(node, vnode) {
    if (isDef(vnode.tag)) {
      return vnode.tag.indexOf('vue-component') === 0 || vnode.tag.toLowerCase() === (node.tagName && node.tagName.toLowerCase());
    } else {
      return node.nodeType === (vnode.isComment ? 8 : 3);
    }
  }

  return function patch(oldVnode, vnode, hydrating, removeOnly, parentElm, refElm) {
    if (isUndef(vnode)) {
      if (isDef(oldVnode)) {
        invokeDestroyHook(oldVnode);
      }
      return;
    }

    var isInitialPatch = false;
    var insertedVnodeQueue = [];

    if (isUndef(oldVnode)) {
      // empty mount (likely as component), create new root element
      isInitialPatch = true;
      createElm(vnode, insertedVnodeQueue, parentElm, refElm);
    } else {
      var isRealElement = isDef(oldVnode.nodeType);
      if (!isRealElement && sameVnode(oldVnode, vnode)) {
        // patch existing root node
        patchVnode(oldVnode, vnode, insertedVnodeQueue, removeOnly);
      } else {
        if (isRealElement) {
          // mounting to a real element
          // check if this is server-rendered content and if we can perform
          // a successful hydration.
          if (oldVnode.nodeType === 1 && oldVnode.hasAttribute(SSR_ATTR)) {
            oldVnode.removeAttribute(SSR_ATTR);
            hydrating = true;
          }
          if (isTrue(hydrating)) {
            if (hydrate(oldVnode, vnode, insertedVnodeQueue)) {
              invokeInsertHook(vnode, insertedVnodeQueue, true);
              return oldVnode;
            } else if (process.env.NODE_ENV !== 'production') {
              warn('The client-side rendered virtual DOM tree is not matching ' + 'server-rendered content. This is likely caused by incorrect ' + 'HTML markup, for example nesting block-level elements inside ' + '<p>, or missing <tbody>. Bailing hydration and performing ' + 'full client-side render.');
            }
          }
          // either not server-rendered, or hydration failed.
          // create an empty node and replace it
          oldVnode = emptyNodeAt(oldVnode);
        }
        // replacing existing element
        var oldElm = oldVnode.elm;
        var parentElm$1 = nodeOps.parentNode(oldElm);
        createElm(vnode, insertedVnodeQueue,
        // extremely rare edge case: do not insert if old element is in a
        // leaving transition. Only happens when combining transition +
        // keep-alive + HOCs. (#4590)
        oldElm._leaveCb ? null : parentElm$1, nodeOps.nextSibling(oldElm));

        if (isDef(vnode.parent)) {
          // component root element replaced.
          // update parent placeholder node element, recursively
          var ancestor = vnode.parent;
          while (ancestor) {
            ancestor.elm = vnode.elm;
            ancestor = ancestor.parent;
          }
          if (isPatchable(vnode)) {
            for (var i = 0; i < cbs.create.length; ++i) {
              cbs.create[i](emptyNode, vnode.parent);
            }
          }
        }

        if (isDef(parentElm$1)) {
          removeVnodes(parentElm$1, [oldVnode], 0, 0);
        } else if (isDef(oldVnode.tag)) {
          invokeDestroyHook(oldVnode);
        }
      }
    }

    invokeInsertHook(vnode, insertedVnodeQueue, isInitialPatch);
    return vnode.elm;
  };
}

/*  */

var directives = {
  create: updateDirectives,
  update: updateDirectives,
  destroy: function unbindDirectives(vnode) {
    updateDirectives(vnode, emptyNode);
  }
};

function updateDirectives(oldVnode, vnode) {
  if (oldVnode.data.directives || vnode.data.directives) {
    _update(oldVnode, vnode);
  }
}

function _update(oldVnode, vnode) {
  var isCreate = oldVnode === emptyNode;
  var isDestroy = vnode === emptyNode;
  var oldDirs = normalizeDirectives$1(oldVnode.data.directives, oldVnode.context);
  var newDirs = normalizeDirectives$1(vnode.data.directives, vnode.context);

  var dirsWithInsert = [];
  var dirsWithPostpatch = [];

  var key, oldDir, dir;
  for (key in newDirs) {
    oldDir = oldDirs[key];
    dir = newDirs[key];
    if (!oldDir) {
      // new directive, bind
      callHook$1(dir, 'bind', vnode, oldVnode);
      if (dir.def && dir.def.inserted) {
        dirsWithInsert.push(dir);
      }
    } else {
      // existing directive, update
      dir.oldValue = oldDir.value;
      callHook$1(dir, 'update', vnode, oldVnode);
      if (dir.def && dir.def.componentUpdated) {
        dirsWithPostpatch.push(dir);
      }
    }
  }

  if (dirsWithInsert.length) {
    var callInsert = function callInsert() {
      for (var i = 0; i < dirsWithInsert.length; i++) {
        callHook$1(dirsWithInsert[i], 'inserted', vnode, oldVnode);
      }
    };
    if (isCreate) {
      mergeVNodeHook(vnode.data.hook || (vnode.data.hook = {}), 'insert', callInsert);
    } else {
      callInsert();
    }
  }

  if (dirsWithPostpatch.length) {
    mergeVNodeHook(vnode.data.hook || (vnode.data.hook = {}), 'postpatch', function () {
      for (var i = 0; i < dirsWithPostpatch.length; i++) {
        callHook$1(dirsWithPostpatch[i], 'componentUpdated', vnode, oldVnode);
      }
    });
  }

  if (!isCreate) {
    for (key in oldDirs) {
      if (!newDirs[key]) {
        // no longer present, unbind
        callHook$1(oldDirs[key], 'unbind', oldVnode, oldVnode, isDestroy);
      }
    }
  }
}

var emptyModifiers = Object.create(null);

function normalizeDirectives$1(dirs, vm) {
  var res = Object.create(null);
  if (!dirs) {
    return res;
  }
  var i, dir;
  for (i = 0; i < dirs.length; i++) {
    dir = dirs[i];
    if (!dir.modifiers) {
      dir.modifiers = emptyModifiers;
    }
    res[getRawDirName(dir)] = dir;
    dir.def = resolveAsset(vm.$options, 'directives', dir.name, true);
  }
  return res;
}

function getRawDirName(dir) {
  return dir.rawName || dir.name + "." + Object.keys(dir.modifiers || {}).join('.');
}

function callHook$1(dir, hook, vnode, oldVnode, isDestroy) {
  var fn = dir.def && dir.def[hook];
  if (fn) {
    try {
      fn(vnode.elm, dir, vnode, oldVnode, isDestroy);
    } catch (e) {
      handleError(e, vnode.context, "directive " + dir.name + " " + hook + " hook");
    }
  }
}

var baseModules = [ref, directives];

/*  */

function updateAttrs(oldVnode, vnode) {
  if (isUndef(oldVnode.data.attrs) && isUndef(vnode.data.attrs)) {
    return;
  }
  var key, cur, old;
  var elm = vnode.elm;
  var oldAttrs = oldVnode.data.attrs || {};
  var attrs = vnode.data.attrs || {};
  // clone observed objects, as the user probably wants to mutate it
  if (isDef(attrs.__ob__)) {
    attrs = vnode.data.attrs = extend({}, attrs);
  }

  for (key in attrs) {
    cur = attrs[key];
    old = oldAttrs[key];
    if (old !== cur) {
      setAttr(elm, key, cur);
    }
  }
  // #4391: in IE9, setting type can reset value for input[type=radio]
  /* istanbul ignore if */
  if (isIE9 && attrs.value !== oldAttrs.value) {
    setAttr(elm, 'value', attrs.value);
  }
  for (key in oldAttrs) {
    if (isUndef(attrs[key])) {
      if (isXlink(key)) {
        elm.removeAttributeNS(xlinkNS, getXlinkProp(key));
      } else if (!isEnumeratedAttr(key)) {
        elm.removeAttribute(key);
      }
    }
  }
}

function setAttr(el, key, value) {
  if (isBooleanAttr(key)) {
    // set attribute for blank value
    // e.g. <option disabled>Select one</option>
    if (isFalsyAttrValue(value)) {
      el.removeAttribute(key);
    } else {
      el.setAttribute(key, key);
    }
  } else if (isEnumeratedAttr(key)) {
    el.setAttribute(key, isFalsyAttrValue(value) || value === 'false' ? 'false' : 'true');
  } else if (isXlink(key)) {
    if (isFalsyAttrValue(value)) {
      el.removeAttributeNS(xlinkNS, getXlinkProp(key));
    } else {
      el.setAttributeNS(xlinkNS, key, value);
    }
  } else {
    if (isFalsyAttrValue(value)) {
      el.removeAttribute(key);
    } else {
      el.setAttribute(key, value);
    }
  }
}

var attrs = {
  create: updateAttrs,
  update: updateAttrs
};

/*  */

function updateClass(oldVnode, vnode) {
  var el = vnode.elm;
  var data = vnode.data;
  var oldData = oldVnode.data;
  if (isUndef(data.staticClass) && isUndef(data.class) && (isUndef(oldData) || isUndef(oldData.staticClass) && isUndef(oldData.class))) {
    return;
  }

  var cls = genClassForVnode(vnode);

  // handle transition classes
  var transitionClass = el._transitionClasses;
  if (isDef(transitionClass)) {
    cls = concat(cls, stringifyClass(transitionClass));
  }

  // set the class
  if (cls !== el._prevClass) {
    el.setAttribute('class', cls);
    el._prevClass = cls;
  }
}

var klass = {
  create: updateClass,
  update: updateClass
};

/*  */

var validDivisionCharRE = /[\w).+\-_$\]]/;

function parseFilters(exp) {
  var inSingle = false;
  var inDouble = false;
  var inTemplateString = false;
  var inRegex = false;
  var curly = 0;
  var square = 0;
  var paren = 0;
  var lastFilterIndex = 0;
  var c, prev, i, expression, filters;

  for (i = 0; i < exp.length; i++) {
    prev = c;
    c = exp.charCodeAt(i);
    if (inSingle) {
      if (c === 0x27 && prev !== 0x5C) {
        inSingle = false;
      }
    } else if (inDouble) {
      if (c === 0x22 && prev !== 0x5C) {
        inDouble = false;
      }
    } else if (inTemplateString) {
      if (c === 0x60 && prev !== 0x5C) {
        inTemplateString = false;
      }
    } else if (inRegex) {
      if (c === 0x2f && prev !== 0x5C) {
        inRegex = false;
      }
    } else if (c === 0x7C && // pipe
    exp.charCodeAt(i + 1) !== 0x7C && exp.charCodeAt(i - 1) !== 0x7C && !curly && !square && !paren) {
      if (expression === undefined) {
        // first filter, end of expression
        lastFilterIndex = i + 1;
        expression = exp.slice(0, i).trim();
      } else {
        pushFilter();
      }
    } else {
      switch (c) {
        case 0x22:
          inDouble = true;break; // "
        case 0x27:
          inSingle = true;break; // '
        case 0x60:
          inTemplateString = true;break; // `
        case 0x28:
          paren++;break; // (
        case 0x29:
          paren--;break; // )
        case 0x5B:
          square++;break; // [
        case 0x5D:
          square--;break; // ]
        case 0x7B:
          curly++;break; // {
        case 0x7D:
          curly--;break; // }
      }
      if (c === 0x2f) {
        // /
        var j = i - 1;
        var p = void 0;
        // find first non-whitespace prev char
        for (; j >= 0; j--) {
          p = exp.charAt(j);
          if (p !== ' ') {
            break;
          }
        }
        if (!p || !validDivisionCharRE.test(p)) {
          inRegex = true;
        }
      }
    }
  }

  if (expression === undefined) {
    expression = exp.slice(0, i).trim();
  } else if (lastFilterIndex !== 0) {
    pushFilter();
  }

  function pushFilter() {
    (filters || (filters = [])).push(exp.slice(lastFilterIndex, i).trim());
    lastFilterIndex = i + 1;
  }

  if (filters) {
    for (i = 0; i < filters.length; i++) {
      expression = wrapFilter(expression, filters[i]);
    }
  }

  return expression;
}

function wrapFilter(exp, filter) {
  var i = filter.indexOf('(');
  if (i < 0) {
    // _f: resolveFilter
    return "_f(\"" + filter + "\")(" + exp + ")";
  } else {
    var name = filter.slice(0, i);
    var args = filter.slice(i + 1);
    return "_f(\"" + name + "\")(" + exp + "," + args;
  }
}

/*  */

function baseWarn(msg) {
  console.error("[Vue compiler]: " + msg);
}

function pluckModuleFunction(modules, key) {
  return modules ? modules.map(function (m) {
    return m[key];
  }).filter(function (_) {
    return _;
  }) : [];
}

function addProp(el, name, value) {
  (el.props || (el.props = [])).push({ name: name, value: value });
}

function addAttr(el, name, value) {
  (el.attrs || (el.attrs = [])).push({ name: name, value: value });
}

function addDirective(el, name, rawName, value, arg, modifiers) {
  (el.directives || (el.directives = [])).push({ name: name, rawName: rawName, value: value, arg: arg, modifiers: modifiers });
}

function addHandler(el, name, value, modifiers, important, warn) {
  // warn prevent and passive modifier
  /* istanbul ignore if */
  if (process.env.NODE_ENV !== 'production' && warn && modifiers && modifiers.prevent && modifiers.passive) {
    warn('passive and prevent can\'t be used together. ' + 'Passive handler can\'t prevent default event.');
  }
  // check capture modifier
  if (modifiers && modifiers.capture) {
    delete modifiers.capture;
    name = '!' + name; // mark the event as captured
  }
  if (modifiers && modifiers.once) {
    delete modifiers.once;
    name = '~' + name; // mark the event as once
  }
  /* istanbul ignore if */
  if (modifiers && modifiers.passive) {
    delete modifiers.passive;
    name = '&' + name; // mark the event as passive
  }
  var events;
  if (modifiers && modifiers.native) {
    delete modifiers.native;
    events = el.nativeEvents || (el.nativeEvents = {});
  } else {
    events = el.events || (el.events = {});
  }
  var newHandler = { value: value, modifiers: modifiers };
  var handlers = events[name];
  /* istanbul ignore if */
  if (Array.isArray(handlers)) {
    important ? handlers.unshift(newHandler) : handlers.push(newHandler);
  } else if (handlers) {
    events[name] = important ? [newHandler, handlers] : [handlers, newHandler];
  } else {
    events[name] = newHandler;
  }
}

function getBindingAttr(el, name, getStatic) {
  var dynamicValue = getAndRemoveAttr(el, ':' + name) || getAndRemoveAttr(el, 'v-bind:' + name);
  if (dynamicValue != null) {
    return parseFilters(dynamicValue);
  } else if (getStatic !== false) {
    var staticValue = getAndRemoveAttr(el, name);
    if (staticValue != null) {
      return JSON.stringify(staticValue);
    }
  }
}

function getAndRemoveAttr(el, name) {
  var val;
  if ((val = el.attrsMap[name]) != null) {
    var list = el.attrsList;
    for (var i = 0, l = list.length; i < l; i++) {
      if (list[i].name === name) {
        list.splice(i, 1);
        break;
      }
    }
  }
  return val;
}

/*  */

/**
 * Cross-platform code generation for component v-model
 */
function genComponentModel(el, value, modifiers) {
  var ref = modifiers || {};
  var number = ref.number;
  var trim = ref.trim;

  var baseValueExpression = '$$v';
  var valueExpression = baseValueExpression;
  if (trim) {
    valueExpression = "(typeof " + baseValueExpression + " === 'string'" + "? " + baseValueExpression + ".trim()" + ": " + baseValueExpression + ")";
  }
  if (number) {
    valueExpression = "_n(" + valueExpression + ")";
  }
  var assignment = genAssignmentCode(value, valueExpression);

  el.model = {
    value: "(" + value + ")",
    expression: "\"" + value + "\"",
    callback: "function (" + baseValueExpression + ") {" + assignment + "}"
  };
}

/**
 * Cross-platform codegen helper for generating v-model value assignment code.
 */
function genAssignmentCode(value, assignment) {
  var modelRs = parseModel(value);
  if (modelRs.idx === null) {
    return value + "=" + assignment;
  } else {
    return "var $$exp = " + modelRs.exp + ", $$idx = " + modelRs.idx + ";" + "if (!Array.isArray($$exp)){" + value + "=" + assignment + "}" + "else{$$exp.splice($$idx, 1, " + assignment + ")}";
  }
}

/**
 * parse directive model to do the array update transform. a[idx] = val => $$a.splice($$idx, 1, val)
 *
 * for loop possible cases:
 *
 * - test
 * - test[idx]
 * - test[test1[idx]]
 * - test["a"][idx]
 * - xxx.test[a[a].test1[idx]]
 * - test.xxx.a["asa"][test1[idx]]
 *
 */

var len;
var str;
var chr;
var index$1;
var expressionPos;
var expressionEndPos;

function parseModel(val) {
  str = val;
  len = str.length;
  index$1 = expressionPos = expressionEndPos = 0;

  if (val.indexOf('[') < 0 || val.lastIndexOf(']') < len - 1) {
    return {
      exp: val,
      idx: null
    };
  }

  while (!eof()) {
    chr = next();
    /* istanbul ignore if */
    if (isStringStart(chr)) {
      parseString(chr);
    } else if (chr === 0x5B) {
      parseBracket(chr);
    }
  }

  return {
    exp: val.substring(0, expressionPos),
    idx: val.substring(expressionPos + 1, expressionEndPos)
  };
}

function next() {
  return str.charCodeAt(++index$1);
}

function eof() {
  return index$1 >= len;
}

function isStringStart(chr) {
  return chr === 0x22 || chr === 0x27;
}

function parseBracket(chr) {
  var inBracket = 1;
  expressionPos = index$1;
  while (!eof()) {
    chr = next();
    if (isStringStart(chr)) {
      parseString(chr);
      continue;
    }
    if (chr === 0x5B) {
      inBracket++;
    }
    if (chr === 0x5D) {
      inBracket--;
    }
    if (inBracket === 0) {
      expressionEndPos = index$1;
      break;
    }
  }
}

function parseString(chr) {
  var stringQuote = chr;
  while (!eof()) {
    chr = next();
    if (chr === stringQuote) {
      break;
    }
  }
}

/*  */

var warn$1;

// in some cases, the event used has to be determined at runtime
// so we used some reserved tokens during compile.
var RANGE_TOKEN = '__r';
var CHECKBOX_RADIO_TOKEN = '__c';

function model(el, dir, _warn) {
  warn$1 = _warn;
  var value = dir.value;
  var modifiers = dir.modifiers;
  var tag = el.tag;
  var type = el.attrsMap.type;

  if (process.env.NODE_ENV !== 'production') {
    var dynamicType = el.attrsMap['v-bind:type'] || el.attrsMap[':type'];
    if (tag === 'input' && dynamicType) {
      warn$1("<input :type=\"" + dynamicType + "\" v-model=\"" + value + "\">:\n" + "v-model does not support dynamic input types. Use v-if branches instead.");
    }
    // inputs with type="file" are read only and setting the input's
    // value will throw an error.
    if (tag === 'input' && type === 'file') {
      warn$1("<" + el.tag + " v-model=\"" + value + "\" type=\"file\">:\n" + "File inputs are read only. Use a v-on:change listener instead.");
    }
  }

  if (tag === 'select') {
    genSelect(el, value, modifiers);
  } else if (tag === 'input' && type === 'checkbox') {
    genCheckboxModel(el, value, modifiers);
  } else if (tag === 'input' && type === 'radio') {
    genRadioModel(el, value, modifiers);
  } else if (tag === 'input' || tag === 'textarea') {
    genDefaultModel(el, value, modifiers);
  } else if (!config.isReservedTag(tag)) {
    genComponentModel(el, value, modifiers);
    // component v-model doesn't need extra runtime
    return false;
  } else if (process.env.NODE_ENV !== 'production') {
    warn$1("<" + el.tag + " v-model=\"" + value + "\">: " + "v-model is not supported on this element type. " + 'If you are working with contenteditable, it\'s recommended to ' + 'wrap a library dedicated for that purpose inside a custom component.');
  }

  // ensure runtime directive metadata
  return true;
}

function genCheckboxModel(el, value, modifiers) {
  var number = modifiers && modifiers.number;
  var valueBinding = getBindingAttr(el, 'value') || 'null';
  var trueValueBinding = getBindingAttr(el, 'true-value') || 'true';
  var falseValueBinding = getBindingAttr(el, 'false-value') || 'false';
  addProp(el, 'checked', "Array.isArray(" + value + ")" + "?_i(" + value + "," + valueBinding + ")>-1" + (trueValueBinding === 'true' ? ":(" + value + ")" : ":_q(" + value + "," + trueValueBinding + ")"));
  addHandler(el, CHECKBOX_RADIO_TOKEN, "var $$a=" + value + "," + '$$el=$event.target,' + "$$c=$$el.checked?(" + trueValueBinding + "):(" + falseValueBinding + ");" + 'if(Array.isArray($$a)){' + "var $$v=" + (number ? '_n(' + valueBinding + ')' : valueBinding) + "," + '$$i=_i($$a,$$v);' + "if($$c){$$i<0&&(" + value + "=$$a.concat($$v))}" + "else{$$i>-1&&(" + value + "=$$a.slice(0,$$i).concat($$a.slice($$i+1)))}" + "}else{" + genAssignmentCode(value, '$$c') + "}", null, true);
}

function genRadioModel(el, value, modifiers) {
  var number = modifiers && modifiers.number;
  var valueBinding = getBindingAttr(el, 'value') || 'null';
  valueBinding = number ? "_n(" + valueBinding + ")" : valueBinding;
  addProp(el, 'checked', "_q(" + value + "," + valueBinding + ")");
  addHandler(el, CHECKBOX_RADIO_TOKEN, genAssignmentCode(value, valueBinding), null, true);
}

function genSelect(el, value, modifiers) {
  var number = modifiers && modifiers.number;
  var selectedVal = "Array.prototype.filter" + ".call($event.target.options,function(o){return o.selected})" + ".map(function(o){var val = \"_value\" in o ? o._value : o.value;" + "return " + (number ? '_n(val)' : 'val') + "})";

  var assignment = '$event.target.multiple ? $$selectedVal : $$selectedVal[0]';
  var code = "var $$selectedVal = " + selectedVal + ";";
  code = code + " " + genAssignmentCode(value, assignment);
  addHandler(el, 'change', code, null, true);
}

function genDefaultModel(el, value, modifiers) {
  var type = el.attrsMap.type;
  var ref = modifiers || {};
  var lazy = ref.lazy;
  var number = ref.number;
  var trim = ref.trim;
  var needCompositionGuard = !lazy && type !== 'range';
  var event = lazy ? 'change' : type === 'range' ? RANGE_TOKEN : 'input';

  var valueExpression = '$event.target.value';
  if (trim) {
    valueExpression = "$event.target.value.trim()";
  }
  if (number) {
    valueExpression = "_n(" + valueExpression + ")";
  }

  var code = genAssignmentCode(value, valueExpression);
  if (needCompositionGuard) {
    code = "if($event.target.composing)return;" + code;
  }

  addProp(el, 'value', "(" + value + ")");
  addHandler(el, event, code, null, true);
  if (trim || number || type === 'number') {
    addHandler(el, 'blur', '$forceUpdate()');
  }
}

/*  */

// normalize v-model event tokens that can only be determined at runtime.
// it's important to place the event as the first in the array because
// the whole point is ensuring the v-model callback gets called before
// user-attached handlers.
function normalizeEvents(on) {
  var event;
  /* istanbul ignore if */
  if (isDef(on[RANGE_TOKEN])) {
    // IE input[type=range] only supports `change` event
    event = isIE ? 'change' : 'input';
    on[event] = [].concat(on[RANGE_TOKEN], on[event] || []);
    delete on[RANGE_TOKEN];
  }
  if (isDef(on[CHECKBOX_RADIO_TOKEN])) {
    // Chrome fires microtasks in between click/change, leads to #4521
    event = isChrome ? 'click' : 'change';
    on[event] = [].concat(on[CHECKBOX_RADIO_TOKEN], on[event] || []);
    delete on[CHECKBOX_RADIO_TOKEN];
  }
}

var target$1;

function add$1(event, _handler, once$$1, capture, passive) {
  if (once$$1) {
    var oldHandler = _handler;
    var _target = target$1; // save current target element in closure
    _handler = function handler(ev) {
      var res = arguments.length === 1 ? oldHandler(ev) : oldHandler.apply(null, arguments);
      if (res !== null) {
        remove$2(event, _handler, capture, _target);
      }
    };
  }
  target$1.addEventListener(event, _handler, supportsPassive ? { capture: capture, passive: passive } : capture);
}

function remove$2(event, handler, capture, _target) {
  (_target || target$1).removeEventListener(event, handler, capture);
}

function updateDOMListeners(oldVnode, vnode) {
  if (isUndef(oldVnode.data.on) && isUndef(vnode.data.on)) {
    return;
  }
  var on = vnode.data.on || {};
  var oldOn = oldVnode.data.on || {};
  target$1 = vnode.elm;
  normalizeEvents(on);
  updateListeners(on, oldOn, add$1, remove$2, vnode.context);
}

var events = {
  create: updateDOMListeners,
  update: updateDOMListeners
};

/*  */

function updateDOMProps(oldVnode, vnode) {
  if (isUndef(oldVnode.data.domProps) && isUndef(vnode.data.domProps)) {
    return;
  }
  var key, cur;
  var elm = vnode.elm;
  var oldProps = oldVnode.data.domProps || {};
  var props = vnode.data.domProps || {};
  // clone observed objects, as the user probably wants to mutate it
  if (isDef(props.__ob__)) {
    props = vnode.data.domProps = extend({}, props);
  }

  for (key in oldProps) {
    if (isUndef(props[key])) {
      elm[key] = '';
    }
  }
  for (key in props) {
    cur = props[key];
    // ignore children if the node has textContent or innerHTML,
    // as these will throw away existing DOM nodes and cause removal errors
    // on subsequent patches (#3360)
    if (key === 'textContent' || key === 'innerHTML') {
      if (vnode.children) {
        vnode.children.length = 0;
      }
      if (cur === oldProps[key]) {
        continue;
      }
    }

    if (key === 'value') {
      // store value as _value as well since
      // non-string values will be stringified
      elm._value = cur;
      // avoid resetting cursor position when value is the same
      var strCur = isUndef(cur) ? '' : String(cur);
      if (shouldUpdateValue(elm, vnode, strCur)) {
        elm.value = strCur;
      }
    } else {
      elm[key] = cur;
    }
  }
}

// check platforms/web/util/attrs.js acceptValue


function shouldUpdateValue(elm, vnode, checkVal) {
  return !elm.composing && (vnode.tag === 'option' || isDirty(elm, checkVal) || isInputChanged(elm, checkVal));
}

function isDirty(elm, checkVal) {
  // return true when textbox (.number and .trim) loses focus and its value is not equal to the updated value
  return document.activeElement !== elm && elm.value !== checkVal;
}

function isInputChanged(elm, newVal) {
  var value = elm.value;
  var modifiers = elm._vModifiers; // injected by v-model runtime
  if (isDef(modifiers) && modifiers.number || elm.type === 'number') {
    return toNumber(value) !== toNumber(newVal);
  }
  if (isDef(modifiers) && modifiers.trim) {
    return value.trim() !== newVal.trim();
  }
  return value !== newVal;
}

var domProps = {
  create: updateDOMProps,
  update: updateDOMProps
};

/*  */

var parseStyleText = cached(function (cssText) {
  var res = {};
  var listDelimiter = /;(?![^(]*\))/g;
  var propertyDelimiter = /:(.+)/;
  cssText.split(listDelimiter).forEach(function (item) {
    if (item) {
      var tmp = item.split(propertyDelimiter);
      tmp.length > 1 && (res[tmp[0].trim()] = tmp[1].trim());
    }
  });
  return res;
});

// merge static and dynamic style data on the same vnode
function normalizeStyleData(data) {
  var style = normalizeStyleBinding(data.style);
  // static style is pre-processed into an object during compilation
  // and is always a fresh object, so it's safe to merge into it
  return data.staticStyle ? extend(data.staticStyle, style) : style;
}

// normalize possible array / string values into Object
function normalizeStyleBinding(bindingStyle) {
  if (Array.isArray(bindingStyle)) {
    return toObject(bindingStyle);
  }
  if (typeof bindingStyle === 'string') {
    return parseStyleText(bindingStyle);
  }
  return bindingStyle;
}

/**
 * parent component style should be after child's
 * so that parent component's style could override it
 */
function getStyle(vnode, checkChild) {
  var res = {};
  var styleData;

  if (checkChild) {
    var childNode = vnode;
    while (childNode.componentInstance) {
      childNode = childNode.componentInstance._vnode;
      if (childNode.data && (styleData = normalizeStyleData(childNode.data))) {
        extend(res, styleData);
      }
    }
  }

  if (styleData = normalizeStyleData(vnode.data)) {
    extend(res, styleData);
  }

  var parentNode = vnode;
  while (parentNode = parentNode.parent) {
    if (parentNode.data && (styleData = normalizeStyleData(parentNode.data))) {
      extend(res, styleData);
    }
  }
  return res;
}

/*  */

var cssVarRE = /^--/;
var importantRE = /\s*!important$/;
var setProp = function setProp(el, name, val) {
  /* istanbul ignore if */
  if (cssVarRE.test(name)) {
    el.style.setProperty(name, val);
  } else if (importantRE.test(val)) {
    el.style.setProperty(name, val.replace(importantRE, ''), 'important');
  } else {
    var normalizedName = normalize(name);
    if (Array.isArray(val)) {
      // Support values array created by autoprefixer, e.g.
      // {display: ["-webkit-box", "-ms-flexbox", "flex"]}
      // Set them one by one, and the browser will only set those it can recognize
      for (var i = 0, len = val.length; i < len; i++) {
        el.style[normalizedName] = val[i];
      }
    } else {
      el.style[normalizedName] = val;
    }
  }
};

var prefixes = ['Webkit', 'Moz', 'ms'];

var testEl;
var normalize = cached(function (prop) {
  testEl = testEl || document.createElement('div');
  prop = camelize(prop);
  if (prop !== 'filter' && prop in testEl.style) {
    return prop;
  }
  var upper = prop.charAt(0).toUpperCase() + prop.slice(1);
  for (var i = 0; i < prefixes.length; i++) {
    var prefixed = prefixes[i] + upper;
    if (prefixed in testEl.style) {
      return prefixed;
    }
  }
});

function updateStyle(oldVnode, vnode) {
  var data = vnode.data;
  var oldData = oldVnode.data;

  if (isUndef(data.staticStyle) && isUndef(data.style) && isUndef(oldData.staticStyle) && isUndef(oldData.style)) {
    return;
  }

  var cur, name;
  var el = vnode.elm;
  var oldStaticStyle = oldData.staticStyle;
  var oldStyleBinding = oldData.normalizedStyle || oldData.style || {};

  // if static style exists, stylebinding already merged into it when doing normalizeStyleData
  var oldStyle = oldStaticStyle || oldStyleBinding;

  var style = normalizeStyleBinding(vnode.data.style) || {};

  // store normalized style under a different key for next diff
  // make sure to clone it if it's reactive, since the user likley wants
  // to mutate it.
  vnode.data.normalizedStyle = isDef(style.__ob__) ? extend({}, style) : style;

  var newStyle = getStyle(vnode, true);

  for (name in oldStyle) {
    if (isUndef(newStyle[name])) {
      setProp(el, name, '');
    }
  }
  for (name in newStyle) {
    cur = newStyle[name];
    if (cur !== oldStyle[name]) {
      // ie9 setting to null has no effect, must use empty string
      setProp(el, name, cur == null ? '' : cur);
    }
  }
}

var style = {
  create: updateStyle,
  update: updateStyle
};

/*  */

/**
 * Add class with compatibility for SVG since classList is not supported on
 * SVG elements in IE
 */
function addClass(el, cls) {
  /* istanbul ignore if */
  if (!cls || !(cls = cls.trim())) {
    return;
  }

  /* istanbul ignore else */
  if (el.classList) {
    if (cls.indexOf(' ') > -1) {
      cls.split(/\s+/).forEach(function (c) {
        return el.classList.add(c);
      });
    } else {
      el.classList.add(cls);
    }
  } else {
    var cur = " " + (el.getAttribute('class') || '') + " ";
    if (cur.indexOf(' ' + cls + ' ') < 0) {
      el.setAttribute('class', (cur + cls).trim());
    }
  }
}

/**
 * Remove class with compatibility for SVG since classList is not supported on
 * SVG elements in IE
 */
function removeClass(el, cls) {
  /* istanbul ignore if */
  if (!cls || !(cls = cls.trim())) {
    return;
  }

  /* istanbul ignore else */
  if (el.classList) {
    if (cls.indexOf(' ') > -1) {
      cls.split(/\s+/).forEach(function (c) {
        return el.classList.remove(c);
      });
    } else {
      el.classList.remove(cls);
    }
  } else {
    var cur = " " + (el.getAttribute('class') || '') + " ";
    var tar = ' ' + cls + ' ';
    while (cur.indexOf(tar) >= 0) {
      cur = cur.replace(tar, ' ');
    }
    el.setAttribute('class', cur.trim());
  }
}

/*  */

function resolveTransition(def$$1) {
  if (!def$$1) {
    return;
  }
  /* istanbul ignore else */
  if ((typeof def$$1 === 'undefined' ? 'undefined' : _typeof(def$$1)) === 'object') {
    var res = {};
    if (def$$1.css !== false) {
      extend(res, autoCssTransition(def$$1.name || 'v'));
    }
    extend(res, def$$1);
    return res;
  } else if (typeof def$$1 === 'string') {
    return autoCssTransition(def$$1);
  }
}

var autoCssTransition = cached(function (name) {
  return {
    enterClass: name + "-enter",
    enterToClass: name + "-enter-to",
    enterActiveClass: name + "-enter-active",
    leaveClass: name + "-leave",
    leaveToClass: name + "-leave-to",
    leaveActiveClass: name + "-leave-active"
  };
});

var hasTransition = inBrowser && !isIE9;
var TRANSITION = 'transition';
var ANIMATION = 'animation';

// Transition property/event sniffing
var transitionProp = 'transition';
var transitionEndEvent = 'transitionend';
var animationProp = 'animation';
var animationEndEvent = 'animationend';
if (hasTransition) {
  /* istanbul ignore if */
  if (window.ontransitionend === undefined && window.onwebkittransitionend !== undefined) {
    transitionProp = 'WebkitTransition';
    transitionEndEvent = 'webkitTransitionEnd';
  }
  if (window.onanimationend === undefined && window.onwebkitanimationend !== undefined) {
    animationProp = 'WebkitAnimation';
    animationEndEvent = 'webkitAnimationEnd';
  }
}

// binding to window is necessary to make hot reload work in IE in strict mode
var raf = inBrowser && window.requestAnimationFrame ? window.requestAnimationFrame.bind(window) : setTimeout;

function nextFrame(fn) {
  raf(function () {
    raf(fn);
  });
}

function addTransitionClass(el, cls) {
  (el._transitionClasses || (el._transitionClasses = [])).push(cls);
  addClass(el, cls);
}

function removeTransitionClass(el, cls) {
  if (el._transitionClasses) {
    remove(el._transitionClasses, cls);
  }
  removeClass(el, cls);
}

function whenTransitionEnds(el, expectedType, cb) {
  var ref = getTransitionInfo(el, expectedType);
  var type = ref.type;
  var timeout = ref.timeout;
  var propCount = ref.propCount;
  if (!type) {
    return cb();
  }
  var event = type === TRANSITION ? transitionEndEvent : animationEndEvent;
  var ended = 0;
  var end = function end() {
    el.removeEventListener(event, onEnd);
    cb();
  };
  var onEnd = function onEnd(e) {
    if (e.target === el) {
      if (++ended >= propCount) {
        end();
      }
    }
  };
  setTimeout(function () {
    if (ended < propCount) {
      end();
    }
  }, timeout + 1);
  el.addEventListener(event, onEnd);
}

var transformRE = /\b(transform|all)(,|$)/;

function getTransitionInfo(el, expectedType) {
  var styles = window.getComputedStyle(el);
  var transitionDelays = styles[transitionProp + 'Delay'].split(', ');
  var transitionDurations = styles[transitionProp + 'Duration'].split(', ');
  var transitionTimeout = getTimeout(transitionDelays, transitionDurations);
  var animationDelays = styles[animationProp + 'Delay'].split(', ');
  var animationDurations = styles[animationProp + 'Duration'].split(', ');
  var animationTimeout = getTimeout(animationDelays, animationDurations);

  var type;
  var timeout = 0;
  var propCount = 0;
  /* istanbul ignore if */
  if (expectedType === TRANSITION) {
    if (transitionTimeout > 0) {
      type = TRANSITION;
      timeout = transitionTimeout;
      propCount = transitionDurations.length;
    }
  } else if (expectedType === ANIMATION) {
    if (animationTimeout > 0) {
      type = ANIMATION;
      timeout = animationTimeout;
      propCount = animationDurations.length;
    }
  } else {
    timeout = Math.max(transitionTimeout, animationTimeout);
    type = timeout > 0 ? transitionTimeout > animationTimeout ? TRANSITION : ANIMATION : null;
    propCount = type ? type === TRANSITION ? transitionDurations.length : animationDurations.length : 0;
  }
  var hasTransform = type === TRANSITION && transformRE.test(styles[transitionProp + 'Property']);
  return {
    type: type,
    timeout: timeout,
    propCount: propCount,
    hasTransform: hasTransform
  };
}

function getTimeout(delays, durations) {
  /* istanbul ignore next */
  while (delays.length < durations.length) {
    delays = delays.concat(delays);
  }

  return Math.max.apply(null, durations.map(function (d, i) {
    return toMs(d) + toMs(delays[i]);
  }));
}

function toMs(s) {
  return Number(s.slice(0, -1)) * 1000;
}

/*  */

function enter(vnode, toggleDisplay) {
  var el = vnode.elm;

  // call leave callback now
  if (isDef(el._leaveCb)) {
    el._leaveCb.cancelled = true;
    el._leaveCb();
  }

  var data = resolveTransition(vnode.data.transition);
  if (isUndef(data)) {
    return;
  }

  /* istanbul ignore if */
  if (isDef(el._enterCb) || el.nodeType !== 1) {
    return;
  }

  var css = data.css;
  var type = data.type;
  var enterClass = data.enterClass;
  var enterToClass = data.enterToClass;
  var enterActiveClass = data.enterActiveClass;
  var appearClass = data.appearClass;
  var appearToClass = data.appearToClass;
  var appearActiveClass = data.appearActiveClass;
  var beforeEnter = data.beforeEnter;
  var enter = data.enter;
  var afterEnter = data.afterEnter;
  var enterCancelled = data.enterCancelled;
  var beforeAppear = data.beforeAppear;
  var appear = data.appear;
  var afterAppear = data.afterAppear;
  var appearCancelled = data.appearCancelled;
  var duration = data.duration;

  // activeInstance will always be the <transition> component managing this
  // transition. One edge case to check is when the <transition> is placed
  // as the root node of a child component. In that case we need to check
  // <transition>'s parent for appear check.
  var context = activeInstance;
  var transitionNode = activeInstance.$vnode;
  while (transitionNode && transitionNode.parent) {
    transitionNode = transitionNode.parent;
    context = transitionNode.context;
  }

  var isAppear = !context._isMounted || !vnode.isRootInsert;

  if (isAppear && !appear && appear !== '') {
    return;
  }

  var startClass = isAppear && appearClass ? appearClass : enterClass;
  var activeClass = isAppear && appearActiveClass ? appearActiveClass : enterActiveClass;
  var toClass = isAppear && appearToClass ? appearToClass : enterToClass;

  var beforeEnterHook = isAppear ? beforeAppear || beforeEnter : beforeEnter;
  var enterHook = isAppear ? typeof appear === 'function' ? appear : enter : enter;
  var afterEnterHook = isAppear ? afterAppear || afterEnter : afterEnter;
  var enterCancelledHook = isAppear ? appearCancelled || enterCancelled : enterCancelled;

  var explicitEnterDuration = toNumber(isObject(duration) ? duration.enter : duration);

  if (process.env.NODE_ENV !== 'production' && explicitEnterDuration != null) {
    checkDuration(explicitEnterDuration, 'enter', vnode);
  }

  var expectsCSS = css !== false && !isIE9;
  var userWantsControl = getHookArgumentsLength(enterHook);

  var cb = el._enterCb = once(function () {
    if (expectsCSS) {
      removeTransitionClass(el, toClass);
      removeTransitionClass(el, activeClass);
    }
    if (cb.cancelled) {
      if (expectsCSS) {
        removeTransitionClass(el, startClass);
      }
      enterCancelledHook && enterCancelledHook(el);
    } else {
      afterEnterHook && afterEnterHook(el);
    }
    el._enterCb = null;
  });

  if (!vnode.data.show) {
    // remove pending leave element on enter by injecting an insert hook
    mergeVNodeHook(vnode.data.hook || (vnode.data.hook = {}), 'insert', function () {
      var parent = el.parentNode;
      var pendingNode = parent && parent._pending && parent._pending[vnode.key];
      if (pendingNode && pendingNode.tag === vnode.tag && pendingNode.elm._leaveCb) {
        pendingNode.elm._leaveCb();
      }
      enterHook && enterHook(el, cb);
    });
  }

  // start enter transition
  beforeEnterHook && beforeEnterHook(el);
  if (expectsCSS) {
    addTransitionClass(el, startClass);
    addTransitionClass(el, activeClass);
    nextFrame(function () {
      addTransitionClass(el, toClass);
      removeTransitionClass(el, startClass);
      if (!cb.cancelled && !userWantsControl) {
        if (isValidDuration(explicitEnterDuration)) {
          setTimeout(cb, explicitEnterDuration);
        } else {
          whenTransitionEnds(el, type, cb);
        }
      }
    });
  }

  if (vnode.data.show) {
    toggleDisplay && toggleDisplay();
    enterHook && enterHook(el, cb);
  }

  if (!expectsCSS && !userWantsControl) {
    cb();
  }
}

function leave(vnode, rm) {
  var el = vnode.elm;

  // call enter callback now
  if (isDef(el._enterCb)) {
    el._enterCb.cancelled = true;
    el._enterCb();
  }

  var data = resolveTransition(vnode.data.transition);
  if (isUndef(data)) {
    return rm();
  }

  /* istanbul ignore if */
  if (isDef(el._leaveCb) || el.nodeType !== 1) {
    return;
  }

  var css = data.css;
  var type = data.type;
  var leaveClass = data.leaveClass;
  var leaveToClass = data.leaveToClass;
  var leaveActiveClass = data.leaveActiveClass;
  var beforeLeave = data.beforeLeave;
  var leave = data.leave;
  var afterLeave = data.afterLeave;
  var leaveCancelled = data.leaveCancelled;
  var delayLeave = data.delayLeave;
  var duration = data.duration;

  var expectsCSS = css !== false && !isIE9;
  var userWantsControl = getHookArgumentsLength(leave);

  var explicitLeaveDuration = toNumber(isObject(duration) ? duration.leave : duration);

  if (process.env.NODE_ENV !== 'production' && isDef(explicitLeaveDuration)) {
    checkDuration(explicitLeaveDuration, 'leave', vnode);
  }

  var cb = el._leaveCb = once(function () {
    if (el.parentNode && el.parentNode._pending) {
      el.parentNode._pending[vnode.key] = null;
    }
    if (expectsCSS) {
      removeTransitionClass(el, leaveToClass);
      removeTransitionClass(el, leaveActiveClass);
    }
    if (cb.cancelled) {
      if (expectsCSS) {
        removeTransitionClass(el, leaveClass);
      }
      leaveCancelled && leaveCancelled(el);
    } else {
      rm();
      afterLeave && afterLeave(el);
    }
    el._leaveCb = null;
  });

  if (delayLeave) {
    delayLeave(performLeave);
  } else {
    performLeave();
  }

  function performLeave() {
    // the delayed leave may have already been cancelled
    if (cb.cancelled) {
      return;
    }
    // record leaving element
    if (!vnode.data.show) {
      (el.parentNode._pending || (el.parentNode._pending = {}))[vnode.key] = vnode;
    }
    beforeLeave && beforeLeave(el);
    if (expectsCSS) {
      addTransitionClass(el, leaveClass);
      addTransitionClass(el, leaveActiveClass);
      nextFrame(function () {
        addTransitionClass(el, leaveToClass);
        removeTransitionClass(el, leaveClass);
        if (!cb.cancelled && !userWantsControl) {
          if (isValidDuration(explicitLeaveDuration)) {
            setTimeout(cb, explicitLeaveDuration);
          } else {
            whenTransitionEnds(el, type, cb);
          }
        }
      });
    }
    leave && leave(el, cb);
    if (!expectsCSS && !userWantsControl) {
      cb();
    }
  }
}

// only used in dev mode
function checkDuration(val, name, vnode) {
  if (typeof val !== 'number') {
    warn("<transition> explicit " + name + " duration is not a valid number - " + "got " + JSON.stringify(val) + ".", vnode.context);
  } else if (isNaN(val)) {
    warn("<transition> explicit " + name + " duration is NaN - " + 'the duration expression might be incorrect.', vnode.context);
  }
}

function isValidDuration(val) {
  return typeof val === 'number' && !isNaN(val);
}

/**
 * Normalize a transition hook's argument length. The hook may be:
 * - a merged hook (invoker) with the original in .fns
 * - a wrapped component method (check ._length)
 * - a plain function (.length)
 */
function getHookArgumentsLength(fn) {
  if (isUndef(fn)) {
    return false;
  }
  var invokerFns = fn.fns;
  if (isDef(invokerFns)) {
    // invoker
    return getHookArgumentsLength(Array.isArray(invokerFns) ? invokerFns[0] : invokerFns);
  } else {
    return (fn._length || fn.length) > 1;
  }
}

function _enter(_, vnode) {
  if (vnode.data.show !== true) {
    enter(vnode);
  }
}

var transition = inBrowser ? {
  create: _enter,
  activate: _enter,
  remove: function remove$$1(vnode, rm) {
    /* istanbul ignore else */
    if (vnode.data.show !== true) {
      leave(vnode, rm);
    } else {
      rm();
    }
  }
} : {};

var platformModules = [attrs, klass, events, domProps, style, transition];

/*  */

// the directive module should be applied last, after all
// built-in modules have been applied.
var modules = platformModules.concat(baseModules);

var patch = createPatchFunction({ nodeOps: nodeOps, modules: modules });

/**
 * Not type checking this file because flow doesn't like attaching
 * properties to Elements.
 */

/* istanbul ignore if */
if (isIE9) {
  // http://www.matts411.com/post/internet-explorer-9-oninput/
  document.addEventListener('selectionchange', function () {
    var el = document.activeElement;
    if (el && el.vmodel) {
      trigger(el, 'input');
    }
  });
}

var model$1 = {
  inserted: function inserted(el, binding, vnode) {
    if (vnode.tag === 'select') {
      var cb = function cb() {
        setSelected(el, binding, vnode.context);
      };
      cb();
      /* istanbul ignore if */
      if (isIE || isEdge) {
        setTimeout(cb, 0);
      }
    } else if (vnode.tag === 'textarea' || el.type === 'text' || el.type === 'password') {
      el._vModifiers = binding.modifiers;
      if (!binding.modifiers.lazy) {
        // Safari < 10.2 & UIWebView doesn't fire compositionend when
        // switching focus before confirming composition choice
        // this also fixes the issue where some browsers e.g. iOS Chrome
        // fires "change" instead of "input" on autocomplete.
        el.addEventListener('change', onCompositionEnd);
        if (!isAndroid) {
          el.addEventListener('compositionstart', onCompositionStart);
          el.addEventListener('compositionend', onCompositionEnd);
        }
        /* istanbul ignore if */
        if (isIE9) {
          el.vmodel = true;
        }
      }
    }
  },
  componentUpdated: function componentUpdated(el, binding, vnode) {
    if (vnode.tag === 'select') {
      setSelected(el, binding, vnode.context);
      // in case the options rendered by v-for have changed,
      // it's possible that the value is out-of-sync with the rendered options.
      // detect such cases and filter out values that no longer has a matching
      // option in the DOM.
      var needReset = el.multiple ? binding.value.some(function (v) {
        return hasNoMatchingOption(v, el.options);
      }) : binding.value !== binding.oldValue && hasNoMatchingOption(binding.value, el.options);
      if (needReset) {
        trigger(el, 'change');
      }
    }
  }
};

function setSelected(el, binding, vm) {
  var value = binding.value;
  var isMultiple = el.multiple;
  if (isMultiple && !Array.isArray(value)) {
    process.env.NODE_ENV !== 'production' && warn("<select multiple v-model=\"" + binding.expression + "\"> " + "expects an Array value for its binding, but got " + Object.prototype.toString.call(value).slice(8, -1), vm);
    return;
  }
  var selected, option;
  for (var i = 0, l = el.options.length; i < l; i++) {
    option = el.options[i];
    if (isMultiple) {
      selected = looseIndexOf(value, getValue(option)) > -1;
      if (option.selected !== selected) {
        option.selected = selected;
      }
    } else {
      if (looseEqual(getValue(option), value)) {
        if (el.selectedIndex !== i) {
          el.selectedIndex = i;
        }
        return;
      }
    }
  }
  if (!isMultiple) {
    el.selectedIndex = -1;
  }
}

function hasNoMatchingOption(value, options) {
  for (var i = 0, l = options.length; i < l; i++) {
    if (looseEqual(getValue(options[i]), value)) {
      return false;
    }
  }
  return true;
}

function getValue(option) {
  return '_value' in option ? option._value : option.value;
}

function onCompositionStart(e) {
  e.target.composing = true;
}

function onCompositionEnd(e) {
  // prevent triggering an input event for no reason
  if (!e.target.composing) {
    return;
  }
  e.target.composing = false;
  trigger(e.target, 'input');
}

function trigger(el, type) {
  var e = document.createEvent('HTMLEvents');
  e.initEvent(type, true, true);
  el.dispatchEvent(e);
}

/*  */

// recursively search for possible transition defined inside the component root
function locateNode(vnode) {
  return vnode.componentInstance && (!vnode.data || !vnode.data.transition) ? locateNode(vnode.componentInstance._vnode) : vnode;
}

var show = {
  bind: function bind(el, ref, vnode) {
    var value = ref.value;

    vnode = locateNode(vnode);
    var transition = vnode.data && vnode.data.transition;
    var originalDisplay = el.__vOriginalDisplay = el.style.display === 'none' ? '' : el.style.display;
    if (value && transition && !isIE9) {
      vnode.data.show = true;
      enter(vnode, function () {
        el.style.display = originalDisplay;
      });
    } else {
      el.style.display = value ? originalDisplay : 'none';
    }
  },

  update: function update(el, ref, vnode) {
    var value = ref.value;
    var oldValue = ref.oldValue;

    /* istanbul ignore if */
    if (value === oldValue) {
      return;
    }
    vnode = locateNode(vnode);
    var transition = vnode.data && vnode.data.transition;
    if (transition && !isIE9) {
      vnode.data.show = true;
      if (value) {
        enter(vnode, function () {
          el.style.display = el.__vOriginalDisplay;
        });
      } else {
        leave(vnode, function () {
          el.style.display = 'none';
        });
      }
    } else {
      el.style.display = value ? el.__vOriginalDisplay : 'none';
    }
  },

  unbind: function unbind(el, binding, vnode, oldVnode, isDestroy) {
    if (!isDestroy) {
      el.style.display = el.__vOriginalDisplay;
    }
  }
};

var platformDirectives = {
  model: model$1,
  show: show
};

/*  */

// Provides transition support for a single element/component.
// supports transition mode (out-in / in-out)

var transitionProps = {
  name: String,
  appear: Boolean,
  css: Boolean,
  mode: String,
  type: String,
  enterClass: String,
  leaveClass: String,
  enterToClass: String,
  leaveToClass: String,
  enterActiveClass: String,
  leaveActiveClass: String,
  appearClass: String,
  appearActiveClass: String,
  appearToClass: String,
  duration: [Number, String, Object]
};

// in case the child is also an abstract component, e.g. <keep-alive>
// we want to recursively retrieve the real component to be rendered
function getRealChild(vnode) {
  var compOptions = vnode && vnode.componentOptions;
  if (compOptions && compOptions.Ctor.options.abstract) {
    return getRealChild(getFirstComponentChild(compOptions.children));
  } else {
    return vnode;
  }
}

function extractTransitionData(comp) {
  var data = {};
  var options = comp.$options;
  // props
  for (var key in options.propsData) {
    data[key] = comp[key];
  }
  // events.
  // extract listeners and pass them directly to the transition methods
  var listeners = options._parentListeners;
  for (var key$1 in listeners) {
    data[camelize(key$1)] = listeners[key$1];
  }
  return data;
}

function placeholder(h, rawChild) {
  if (/\d-keep-alive$/.test(rawChild.tag)) {
    return h('keep-alive', {
      props: rawChild.componentOptions.propsData
    });
  }
}

function hasParentTransition(vnode) {
  while (vnode = vnode.parent) {
    if (vnode.data.transition) {
      return true;
    }
  }
}

function isSameChild(child, oldChild) {
  return oldChild.key === child.key && oldChild.tag === child.tag;
}

var Transition = {
  name: 'transition',
  props: transitionProps,
  abstract: true,

  render: function render(h) {
    var this$1 = this;

    var children = this.$slots.default;
    if (!children) {
      return;
    }

    // filter out text nodes (possible whitespaces)
    children = children.filter(function (c) {
      return c.tag;
    });
    /* istanbul ignore if */
    if (!children.length) {
      return;
    }

    // warn multiple elements
    if (process.env.NODE_ENV !== 'production' && children.length > 1) {
      warn('<transition> can only be used on a single element. Use ' + '<transition-group> for lists.', this.$parent);
    }

    var mode = this.mode;

    // warn invalid mode
    if (process.env.NODE_ENV !== 'production' && mode && mode !== 'in-out' && mode !== 'out-in') {
      warn('invalid <transition> mode: ' + mode, this.$parent);
    }

    var rawChild = children[0];

    // if this is a component root node and the component's
    // parent container node also has transition, skip.
    if (hasParentTransition(this.$vnode)) {
      return rawChild;
    }

    // apply transition data to child
    // use getRealChild() to ignore abstract components e.g. keep-alive
    var child = getRealChild(rawChild);
    /* istanbul ignore if */
    if (!child) {
      return rawChild;
    }

    if (this._leaving) {
      return placeholder(h, rawChild);
    }

    // ensure a key that is unique to the vnode type and to this transition
    // component instance. This key will be used to remove pending leaving nodes
    // during entering.
    var id = "__transition-" + this._uid + "-";
    child.key = child.key == null ? id + child.tag : isPrimitive(child.key) ? String(child.key).indexOf(id) === 0 ? child.key : id + child.key : child.key;

    var data = (child.data || (child.data = {})).transition = extractTransitionData(this);
    var oldRawChild = this._vnode;
    var oldChild = getRealChild(oldRawChild);

    // mark v-show
    // so that the transition module can hand over the control to the directive
    if (child.data.directives && child.data.directives.some(function (d) {
      return d.name === 'show';
    })) {
      child.data.show = true;
    }

    if (oldChild && oldChild.data && !isSameChild(child, oldChild)) {
      // replace old child transition data with fresh one
      // important for dynamic transitions!
      var oldData = oldChild && (oldChild.data.transition = extend({}, data));
      // handle transition mode
      if (mode === 'out-in') {
        // return placeholder node and queue update when leave finishes
        this._leaving = true;
        mergeVNodeHook(oldData, 'afterLeave', function () {
          this$1._leaving = false;
          this$1.$forceUpdate();
        });
        return placeholder(h, rawChild);
      } else if (mode === 'in-out') {
        var delayedLeave;
        var performLeave = function performLeave() {
          delayedLeave();
        };
        mergeVNodeHook(data, 'afterEnter', performLeave);
        mergeVNodeHook(data, 'enterCancelled', performLeave);
        mergeVNodeHook(oldData, 'delayLeave', function (leave) {
          delayedLeave = leave;
        });
      }
    }

    return rawChild;
  }
};

/*  */

// Provides transition support for list items.
// supports move transitions using the FLIP technique.

// Because the vdom's children update algorithm is "unstable" - i.e.
// it doesn't guarantee the relative positioning of removed elements,
// we force transition-group to update its children into two passes:
// in the first pass, we remove all nodes that need to be removed,
// triggering their leaving transition; in the second pass, we insert/move
// into the final desired state. This way in the second pass removed
// nodes will remain where they should be.

var props = extend({
  tag: String,
  moveClass: String
}, transitionProps);

delete props.mode;

var TransitionGroup = {
  props: props,

  render: function render(h) {
    var tag = this.tag || this.$vnode.data.tag || 'span';
    var map = Object.create(null);
    var prevChildren = this.prevChildren = this.children;
    var rawChildren = this.$slots.default || [];
    var children = this.children = [];
    var transitionData = extractTransitionData(this);

    for (var i = 0; i < rawChildren.length; i++) {
      var c = rawChildren[i];
      if (c.tag) {
        if (c.key != null && String(c.key).indexOf('__vlist') !== 0) {
          children.push(c);
          map[c.key] = c;(c.data || (c.data = {})).transition = transitionData;
        } else if (process.env.NODE_ENV !== 'production') {
          var opts = c.componentOptions;
          var name = opts ? opts.Ctor.options.name || opts.tag || '' : c.tag;
          warn("<transition-group> children must be keyed: <" + name + ">");
        }
      }
    }

    if (prevChildren) {
      var kept = [];
      var removed = [];
      for (var i$1 = 0; i$1 < prevChildren.length; i$1++) {
        var c$1 = prevChildren[i$1];
        c$1.data.transition = transitionData;
        c$1.data.pos = c$1.elm.getBoundingClientRect();
        if (map[c$1.key]) {
          kept.push(c$1);
        } else {
          removed.push(c$1);
        }
      }
      this.kept = h(tag, null, kept);
      this.removed = removed;
    }

    return h(tag, null, children);
  },

  beforeUpdate: function beforeUpdate() {
    // force removing pass
    this.__patch__(this._vnode, this.kept, false, // hydrating
    true // removeOnly (!important, avoids unnecessary moves)
    );
    this._vnode = this.kept;
  },

  updated: function updated() {
    var children = this.prevChildren;
    var moveClass = this.moveClass || (this.name || 'v') + '-move';
    if (!children.length || !this.hasMove(children[0].elm, moveClass)) {
      return;
    }

    // we divide the work into three loops to avoid mixing DOM reads and writes
    // in each iteration - which helps prevent layout thrashing.
    children.forEach(callPendingCbs);
    children.forEach(recordPosition);
    children.forEach(applyTranslation);

    // force reflow to put everything in position
    var body = document.body;
    var f = body.offsetHeight; // eslint-disable-line

    children.forEach(function (c) {
      if (c.data.moved) {
        var el = c.elm;
        var s = el.style;
        addTransitionClass(el, moveClass);
        s.transform = s.WebkitTransform = s.transitionDuration = '';
        el.addEventListener(transitionEndEvent, el._moveCb = function cb(e) {
          if (!e || /transform$/.test(e.propertyName)) {
            el.removeEventListener(transitionEndEvent, cb);
            el._moveCb = null;
            removeTransitionClass(el, moveClass);
          }
        });
      }
    });
  },

  methods: {
    hasMove: function hasMove(el, moveClass) {
      /* istanbul ignore if */
      if (!hasTransition) {
        return false;
      }
      if (this._hasMove != null) {
        return this._hasMove;
      }
      // Detect whether an element with the move class applied has
      // CSS transitions. Since the element may be inside an entering
      // transition at this very moment, we make a clone of it and remove
      // all other transition classes applied to ensure only the move class
      // is applied.
      var clone = el.cloneNode();
      if (el._transitionClasses) {
        el._transitionClasses.forEach(function (cls) {
          removeClass(clone, cls);
        });
      }
      addClass(clone, moveClass);
      clone.style.display = 'none';
      this.$el.appendChild(clone);
      var info = getTransitionInfo(clone);
      this.$el.removeChild(clone);
      return this._hasMove = info.hasTransform;
    }
  }
};

function callPendingCbs(c) {
  /* istanbul ignore if */
  if (c.elm._moveCb) {
    c.elm._moveCb();
  }
  /* istanbul ignore if */
  if (c.elm._enterCb) {
    c.elm._enterCb();
  }
}

function recordPosition(c) {
  c.data.newPos = c.elm.getBoundingClientRect();
}

function applyTranslation(c) {
  var oldPos = c.data.pos;
  var newPos = c.data.newPos;
  var dx = oldPos.left - newPos.left;
  var dy = oldPos.top - newPos.top;
  if (dx || dy) {
    c.data.moved = true;
    var s = c.elm.style;
    s.transform = s.WebkitTransform = "translate(" + dx + "px," + dy + "px)";
    s.transitionDuration = '0s';
  }
}

var platformComponents = {
  Transition: Transition,
  TransitionGroup: TransitionGroup
};

/*  */

// install platform specific utils
Vue$3.config.mustUseProp = mustUseProp;
Vue$3.config.isReservedTag = isReservedTag;
Vue$3.config.isReservedAttr = isReservedAttr;
Vue$3.config.getTagNamespace = getTagNamespace;
Vue$3.config.isUnknownElement = isUnknownElement;

// install platform runtime directives & components
extend(Vue$3.options.directives, platformDirectives);
extend(Vue$3.options.components, platformComponents);

// install platform patch function
Vue$3.prototype.__patch__ = inBrowser ? patch : noop;

// public mount method
Vue$3.prototype.$mount = function (el, hydrating) {
  el = el && inBrowser ? query(el) : undefined;
  return mountComponent(this, el, hydrating);
};

// devtools global hook
/* istanbul ignore next */
setTimeout(function () {
  if (config.devtools) {
    if (devtools) {
      devtools.emit('init', Vue$3);
    } else if (process.env.NODE_ENV !== 'production' && isChrome) {
      console[console.info ? 'info' : 'log']('Download the Vue Devtools extension for a better development experience:\n' + 'https://github.com/vuejs/vue-devtools');
    }
  }
  if (process.env.NODE_ENV !== 'production' && config.productionTip !== false && inBrowser && typeof console !== 'undefined') {
    console[console.info ? 'info' : 'log']("You are running Vue in development mode.\n" + "Make sure to turn on production mode when deploying for production.\n" + "See more tips at https://vuejs.org/guide/deployment.html");
  }
}, 0);

/*  */

// check whether current browser encodes a char inside attribute values
function shouldDecode(content, encoded) {
  var div = document.createElement('div');
  div.innerHTML = "<div a=\"" + content + "\">";
  return div.innerHTML.indexOf(encoded) > 0;
}

// #3663
// IE encodes newlines inside attribute values while other browsers don't
var shouldDecodeNewlines = inBrowser ? shouldDecode('\n', '&#10;') : false;

/*  */

var isUnaryTag = makeMap('area,base,br,col,embed,frame,hr,img,input,isindex,keygen,' + 'link,meta,param,source,track,wbr');

// Elements that you can, intentionally, leave open
// (and which close themselves)
var canBeLeftOpenTag = makeMap('colgroup,dd,dt,li,options,p,td,tfoot,th,thead,tr,source');

// HTML5 tags https://html.spec.whatwg.org/multipage/indices.html#elements-3
// Phrasing Content https://html.spec.whatwg.org/multipage/dom.html#phrasing-content
var isNonPhrasingTag = makeMap('address,article,aside,base,blockquote,body,caption,col,colgroup,dd,' + 'details,dialog,div,dl,dt,fieldset,figcaption,figure,footer,form,' + 'h1,h2,h3,h4,h5,h6,head,header,hgroup,hr,html,legend,li,menuitem,meta,' + 'optgroup,option,param,rp,rt,source,style,summary,tbody,td,tfoot,th,thead,' + 'title,tr,track');

/*  */

var decoder;

function decode(html) {
  decoder = decoder || document.createElement('div');
  decoder.innerHTML = html;
  return decoder.textContent;
}

/**
 * Not type-checking this file because it's mostly vendor code.
 */

/*!
 * HTML Parser By John Resig (ejohn.org)
 * Modified by Juriy "kangax" Zaytsev
 * Original code by Erik Arvidsson, Mozilla Public License
 * http://erik.eae.net/simplehtmlparser/simplehtmlparser.js
 */

// Regular Expressions for parsing tags and attributes
var singleAttrIdentifier = /([^\s"'<>/=]+)/;
var singleAttrAssign = /(?:=)/;
var singleAttrValues = [
// attr value double quotes
/"([^"]*)"+/.source,
// attr value, single quotes
/'([^']*)'+/.source,
// attr value, no quotes
/([^\s"'=<>`]+)/.source];
var attribute = new RegExp('^\\s*' + singleAttrIdentifier.source + '(?:\\s*(' + singleAttrAssign.source + ')' + '\\s*(?:' + singleAttrValues.join('|') + '))?');

// could use https://www.w3.org/TR/1999/REC-xml-names-19990114/#NT-QName
// but for Vue templates we can enforce a simple charset
var ncname = '[a-zA-Z_][\\w\\-\\.]*';
var qnameCapture = '((?:' + ncname + '\\:)?' + ncname + ')';
var startTagOpen = new RegExp('^<' + qnameCapture);
var startTagClose = /^\s*(\/?)>/;
var endTag = new RegExp('^<\\/' + qnameCapture + '[^>]*>');
var doctype = /^<!DOCTYPE [^>]+>/i;
var comment = /^<!--/;
var conditionalComment = /^<!\[/;

var IS_REGEX_CAPTURING_BROKEN = false;
'x'.replace(/x(.)?/g, function (m, g) {
  IS_REGEX_CAPTURING_BROKEN = g === '';
});

// Special Elements (can contain anything)
var isPlainTextElement = makeMap('script,style,textarea', true);
var reCache = {};

var decodingMap = {
  '&lt;': '<',
  '&gt;': '>',
  '&quot;': '"',
  '&amp;': '&',
  '&#10;': '\n'
};
var encodedAttr = /&(?:lt|gt|quot|amp);/g;
var encodedAttrWithNewLines = /&(?:lt|gt|quot|amp|#10);/g;

function decodeAttr(value, shouldDecodeNewlines) {
  var re = shouldDecodeNewlines ? encodedAttrWithNewLines : encodedAttr;
  return value.replace(re, function (match) {
    return decodingMap[match];
  });
}

function parseHTML(html, options) {
  var stack = [];
  var expectHTML = options.expectHTML;
  var isUnaryTag$$1 = options.isUnaryTag || no;
  var canBeLeftOpenTag$$1 = options.canBeLeftOpenTag || no;
  var index = 0;
  var last, lastTag;
  while (html) {
    last = html;
    // Make sure we're not in a plaintext content element like script/style
    if (!lastTag || !isPlainTextElement(lastTag)) {
      var textEnd = html.indexOf('<');
      if (textEnd === 0) {
        // Comment:
        if (comment.test(html)) {
          var commentEnd = html.indexOf('-->');

          if (commentEnd >= 0) {
            advance(commentEnd + 3);
            continue;
          }
        }

        // http://en.wikipedia.org/wiki/Conditional_comment#Downlevel-revealed_conditional_comment
        if (conditionalComment.test(html)) {
          var conditionalEnd = html.indexOf(']>');

          if (conditionalEnd >= 0) {
            advance(conditionalEnd + 2);
            continue;
          }
        }

        // Doctype:
        var doctypeMatch = html.match(doctype);
        if (doctypeMatch) {
          advance(doctypeMatch[0].length);
          continue;
        }

        // End tag:
        var endTagMatch = html.match(endTag);
        if (endTagMatch) {
          var curIndex = index;
          advance(endTagMatch[0].length);
          parseEndTag(endTagMatch[1], curIndex, index);
          continue;
        }

        // Start tag:
        var startTagMatch = parseStartTag();
        if (startTagMatch) {
          handleStartTag(startTagMatch);
          continue;
        }
      }

      var text = void 0,
          rest$1 = void 0,
          next = void 0;
      if (textEnd >= 0) {
        rest$1 = html.slice(textEnd);
        while (!endTag.test(rest$1) && !startTagOpen.test(rest$1) && !comment.test(rest$1) && !conditionalComment.test(rest$1)) {
          // < in plain text, be forgiving and treat it as text
          next = rest$1.indexOf('<', 1);
          if (next < 0) {
            break;
          }
          textEnd += next;
          rest$1 = html.slice(textEnd);
        }
        text = html.substring(0, textEnd);
        advance(textEnd);
      }

      if (textEnd < 0) {
        text = html;
        html = '';
      }

      if (options.chars && text) {
        options.chars(text);
      }
    } else {
      var stackedTag = lastTag.toLowerCase();
      var reStackedTag = reCache[stackedTag] || (reCache[stackedTag] = new RegExp('([\\s\\S]*?)(</' + stackedTag + '[^>]*>)', 'i'));
      var endTagLength = 0;
      var rest = html.replace(reStackedTag, function (all, text, endTag) {
        endTagLength = endTag.length;
        if (!isPlainTextElement(stackedTag) && stackedTag !== 'noscript') {
          text = text.replace(/<!--([\s\S]*?)-->/g, '$1').replace(/<!\[CDATA\[([\s\S]*?)]]>/g, '$1');
        }
        if (options.chars) {
          options.chars(text);
        }
        return '';
      });
      index += html.length - rest.length;
      html = rest;
      parseEndTag(stackedTag, index - endTagLength, index);
    }

    if (html === last) {
      options.chars && options.chars(html);
      if (process.env.NODE_ENV !== 'production' && !stack.length && options.warn) {
        options.warn("Mal-formatted tag at end of template: \"" + html + "\"");
      }
      break;
    }
  }

  // Clean up any remaining tags
  parseEndTag();

  function advance(n) {
    index += n;
    html = html.substring(n);
  }

  function parseStartTag() {
    var start = html.match(startTagOpen);
    if (start) {
      var match = {
        tagName: start[1],
        attrs: [],
        start: index
      };
      advance(start[0].length);
      var end, attr;
      while (!(end = html.match(startTagClose)) && (attr = html.match(attribute))) {
        advance(attr[0].length);
        match.attrs.push(attr);
      }
      if (end) {
        match.unarySlash = end[1];
        advance(end[0].length);
        match.end = index;
        return match;
      }
    }
  }

  function handleStartTag(match) {
    var tagName = match.tagName;
    var unarySlash = match.unarySlash;

    if (expectHTML) {
      if (lastTag === 'p' && isNonPhrasingTag(tagName)) {
        parseEndTag(lastTag);
      }
      if (canBeLeftOpenTag$$1(tagName) && lastTag === tagName) {
        parseEndTag(tagName);
      }
    }

    var unary = isUnaryTag$$1(tagName) || tagName === 'html' && lastTag === 'head' || !!unarySlash;

    var l = match.attrs.length;
    var attrs = new Array(l);
    for (var i = 0; i < l; i++) {
      var args = match.attrs[i];
      // hackish work around FF bug https://bugzilla.mozilla.org/show_bug.cgi?id=369778
      if (IS_REGEX_CAPTURING_BROKEN && args[0].indexOf('""') === -1) {
        if (args[3] === '') {
          delete args[3];
        }
        if (args[4] === '') {
          delete args[4];
        }
        if (args[5] === '') {
          delete args[5];
        }
      }
      var value = args[3] || args[4] || args[5] || '';
      attrs[i] = {
        name: args[1],
        value: decodeAttr(value, options.shouldDecodeNewlines)
      };
    }

    if (!unary) {
      stack.push({ tag: tagName, lowerCasedTag: tagName.toLowerCase(), attrs: attrs });
      lastTag = tagName;
    }

    if (options.start) {
      options.start(tagName, attrs, unary, match.start, match.end);
    }
  }

  function parseEndTag(tagName, start, end) {
    var pos, lowerCasedTagName;
    if (start == null) {
      start = index;
    }
    if (end == null) {
      end = index;
    }

    if (tagName) {
      lowerCasedTagName = tagName.toLowerCase();
    }

    // Find the closest opened tag of the same type
    if (tagName) {
      for (pos = stack.length - 1; pos >= 0; pos--) {
        if (stack[pos].lowerCasedTag === lowerCasedTagName) {
          break;
        }
      }
    } else {
      // If no tag name is provided, clean shop
      pos = 0;
    }

    if (pos >= 0) {
      // Close all the open elements, up the stack
      for (var i = stack.length - 1; i >= pos; i--) {
        if (process.env.NODE_ENV !== 'production' && (i > pos || !tagName) && options.warn) {
          options.warn("tag <" + stack[i].tag + "> has no matching end tag.");
        }
        if (options.end) {
          options.end(stack[i].tag, start, end);
        }
      }

      // Remove the open elements from the stack
      stack.length = pos;
      lastTag = pos && stack[pos - 1].tag;
    } else if (lowerCasedTagName === 'br') {
      if (options.start) {
        options.start(tagName, [], true, start, end);
      }
    } else if (lowerCasedTagName === 'p') {
      if (options.start) {
        options.start(tagName, [], false, start, end);
      }
      if (options.end) {
        options.end(tagName, start, end);
      }
    }
  }
}

/*  */

var defaultTagRE = /\{\{((?:.|\n)+?)\}\}/g;
var regexEscapeRE = /[-.*+?^${}()|[\]\/\\]/g;

var buildRegex = cached(function (delimiters) {
  var open = delimiters[0].replace(regexEscapeRE, '\\$&');
  var close = delimiters[1].replace(regexEscapeRE, '\\$&');
  return new RegExp(open + '((?:.|\\n)+?)' + close, 'g');
});

function parseText(text, delimiters) {
  var tagRE = delimiters ? buildRegex(delimiters) : defaultTagRE;
  if (!tagRE.test(text)) {
    return;
  }
  var tokens = [];
  var lastIndex = tagRE.lastIndex = 0;
  var match, index;
  while (match = tagRE.exec(text)) {
    index = match.index;
    // push text token
    if (index > lastIndex) {
      tokens.push(JSON.stringify(text.slice(lastIndex, index)));
    }
    // tag token
    var exp = parseFilters(match[1].trim());
    tokens.push("_s(" + exp + ")");
    lastIndex = index + match[0].length;
  }
  if (lastIndex < text.length) {
    tokens.push(JSON.stringify(text.slice(lastIndex)));
  }
  return tokens.join('+');
}

/*  */

var onRE = /^@|^v-on:/;
var dirRE = /^v-|^@|^:/;
var forAliasRE = /(.*?)\s+(?:in|of)\s+(.*)/;
var forIteratorRE = /\((\{[^}]*\}|[^,]*),([^,]*)(?:,([^,]*))?\)/;

var argRE = /:(.*)$/;
var bindRE = /^:|^v-bind:/;
var modifierRE = /\.[^.]+/g;

var decodeHTMLCached = cached(decode);

// configurable state
var warn$2;
var delimiters;
var transforms;
var preTransforms;
var postTransforms;
var platformIsPreTag;
var platformMustUseProp;
var platformGetTagNamespace;

/**
 * Convert HTML string to AST.
 */
function parse(template, options) {
  warn$2 = options.warn || baseWarn;
  platformGetTagNamespace = options.getTagNamespace || no;
  platformMustUseProp = options.mustUseProp || no;
  platformIsPreTag = options.isPreTag || no;
  preTransforms = pluckModuleFunction(options.modules, 'preTransformNode');
  transforms = pluckModuleFunction(options.modules, 'transformNode');
  postTransforms = pluckModuleFunction(options.modules, 'postTransformNode');
  delimiters = options.delimiters;

  var stack = [];
  var preserveWhitespace = options.preserveWhitespace !== false;
  var root;
  var currentParent;
  var inVPre = false;
  var inPre = false;
  var warned = false;

  function warnOnce(msg) {
    if (!warned) {
      warned = true;
      warn$2(msg);
    }
  }

  function endPre(element) {
    // check pre state
    if (element.pre) {
      inVPre = false;
    }
    if (platformIsPreTag(element.tag)) {
      inPre = false;
    }
  }

  parseHTML(template, {
    warn: warn$2,
    expectHTML: options.expectHTML,
    isUnaryTag: options.isUnaryTag,
    canBeLeftOpenTag: options.canBeLeftOpenTag,
    shouldDecodeNewlines: options.shouldDecodeNewlines,
    start: function start(tag, attrs, unary) {
      // check namespace.
      // inherit parent ns if there is one
      var ns = currentParent && currentParent.ns || platformGetTagNamespace(tag);

      // handle IE svg bug
      /* istanbul ignore if */
      if (isIE && ns === 'svg') {
        attrs = guardIESVGBug(attrs);
      }

      var element = {
        type: 1,
        tag: tag,
        attrsList: attrs,
        attrsMap: makeAttrsMap(attrs),
        parent: currentParent,
        children: []
      };
      if (ns) {
        element.ns = ns;
      }

      if (isForbiddenTag(element) && !isServerRendering()) {
        element.forbidden = true;
        process.env.NODE_ENV !== 'production' && warn$2('Templates should only be responsible for mapping the state to the ' + 'UI. Avoid placing tags with side-effects in your templates, such as ' + "<" + tag + ">" + ', as they will not be parsed.');
      }

      // apply pre-transforms
      for (var i = 0; i < preTransforms.length; i++) {
        preTransforms[i](element, options);
      }

      if (!inVPre) {
        processPre(element);
        if (element.pre) {
          inVPre = true;
        }
      }
      if (platformIsPreTag(element.tag)) {
        inPre = true;
      }
      if (inVPre) {
        processRawAttrs(element);
      } else {
        processFor(element);
        processIf(element);
        processOnce(element);
        processKey(element);

        // determine whether this is a plain element after
        // removing structural attributes
        element.plain = !element.key && !attrs.length;

        processRef(element);
        processSlot(element);
        processComponent(element);
        for (var i$1 = 0; i$1 < transforms.length; i$1++) {
          transforms[i$1](element, options);
        }
        processAttrs(element);
      }

      function checkRootConstraints(el) {
        if (process.env.NODE_ENV !== 'production') {
          if (el.tag === 'slot' || el.tag === 'template') {
            warnOnce("Cannot use <" + el.tag + "> as component root element because it may " + 'contain multiple nodes.');
          }
          if (el.attrsMap.hasOwnProperty('v-for')) {
            warnOnce('Cannot use v-for on stateful component root element because ' + 'it renders multiple elements.');
          }
        }
      }

      // tree management
      if (!root) {
        root = element;
        checkRootConstraints(root);
      } else if (!stack.length) {
        // allow root elements with v-if, v-else-if and v-else
        if (root.if && (element.elseif || element.else)) {
          checkRootConstraints(element);
          addIfCondition(root, {
            exp: element.elseif,
            block: element
          });
        } else if (process.env.NODE_ENV !== 'production') {
          warnOnce("Component template should contain exactly one root element. " + "If you are using v-if on multiple elements, " + "use v-else-if to chain them instead.");
        }
      }
      if (currentParent && !element.forbidden) {
        if (element.elseif || element.else) {
          processIfConditions(element, currentParent);
        } else if (element.slotScope) {
          // scoped slot
          currentParent.plain = false;
          var name = element.slotTarget || '"default"';(currentParent.scopedSlots || (currentParent.scopedSlots = {}))[name] = element;
        } else {
          currentParent.children.push(element);
          element.parent = currentParent;
        }
      }
      if (!unary) {
        currentParent = element;
        stack.push(element);
      } else {
        endPre(element);
      }
      // apply post-transforms
      for (var i$2 = 0; i$2 < postTransforms.length; i$2++) {
        postTransforms[i$2](element, options);
      }
    },

    end: function end() {
      // remove trailing whitespace
      var element = stack[stack.length - 1];
      var lastNode = element.children[element.children.length - 1];
      if (lastNode && lastNode.type === 3 && lastNode.text === ' ' && !inPre) {
        element.children.pop();
      }
      // pop stack
      stack.length -= 1;
      currentParent = stack[stack.length - 1];
      endPre(element);
    },

    chars: function chars(text) {
      if (!currentParent) {
        if (process.env.NODE_ENV !== 'production') {
          if (text === template) {
            warnOnce('Component template requires a root element, rather than just text.');
          } else if (text = text.trim()) {
            warnOnce("text \"" + text + "\" outside root element will be ignored.");
          }
        }
        return;
      }
      // IE textarea placeholder bug
      /* istanbul ignore if */
      if (isIE && currentParent.tag === 'textarea' && currentParent.attrsMap.placeholder === text) {
        return;
      }
      var children = currentParent.children;
      text = inPre || text.trim() ? isTextTag(currentParent) ? text : decodeHTMLCached(text)
      // only preserve whitespace if its not right after a starting tag
      : preserveWhitespace && children.length ? ' ' : '';
      if (text) {
        var expression;
        if (!inVPre && text !== ' ' && (expression = parseText(text, delimiters))) {
          children.push({
            type: 2,
            expression: expression,
            text: text
          });
        } else if (text !== ' ' || !children.length || children[children.length - 1].text !== ' ') {
          children.push({
            type: 3,
            text: text
          });
        }
      }
    }
  });
  return root;
}

function processPre(el) {
  if (getAndRemoveAttr(el, 'v-pre') != null) {
    el.pre = true;
  }
}

function processRawAttrs(el) {
  var l = el.attrsList.length;
  if (l) {
    var attrs = el.attrs = new Array(l);
    for (var i = 0; i < l; i++) {
      attrs[i] = {
        name: el.attrsList[i].name,
        value: JSON.stringify(el.attrsList[i].value)
      };
    }
  } else if (!el.pre) {
    // non root node in pre blocks with no attributes
    el.plain = true;
  }
}

function processKey(el) {
  var exp = getBindingAttr(el, 'key');
  if (exp) {
    if (process.env.NODE_ENV !== 'production' && el.tag === 'template') {
      warn$2("<template> cannot be keyed. Place the key on real elements instead.");
    }
    el.key = exp;
  }
}

function processRef(el) {
  var ref = getBindingAttr(el, 'ref');
  if (ref) {
    el.ref = ref;
    el.refInFor = checkInFor(el);
  }
}

function processFor(el) {
  var exp;
  if (exp = getAndRemoveAttr(el, 'v-for')) {
    var inMatch = exp.match(forAliasRE);
    if (!inMatch) {
      process.env.NODE_ENV !== 'production' && warn$2("Invalid v-for expression: " + exp);
      return;
    }
    el.for = inMatch[2].trim();
    var alias = inMatch[1].trim();
    var iteratorMatch = alias.match(forIteratorRE);
    if (iteratorMatch) {
      el.alias = iteratorMatch[1].trim();
      el.iterator1 = iteratorMatch[2].trim();
      if (iteratorMatch[3]) {
        el.iterator2 = iteratorMatch[3].trim();
      }
    } else {
      el.alias = alias;
    }
  }
}

function processIf(el) {
  var exp = getAndRemoveAttr(el, 'v-if');
  if (exp) {
    el.if = exp;
    addIfCondition(el, {
      exp: exp,
      block: el
    });
  } else {
    if (getAndRemoveAttr(el, 'v-else') != null) {
      el.else = true;
    }
    var elseif = getAndRemoveAttr(el, 'v-else-if');
    if (elseif) {
      el.elseif = elseif;
    }
  }
}

function processIfConditions(el, parent) {
  var prev = findPrevElement(parent.children);
  if (prev && prev.if) {
    addIfCondition(prev, {
      exp: el.elseif,
      block: el
    });
  } else if (process.env.NODE_ENV !== 'production') {
    warn$2("v-" + (el.elseif ? 'else-if="' + el.elseif + '"' : 'else') + " " + "used on element <" + el.tag + "> without corresponding v-if.");
  }
}

function findPrevElement(children) {
  var i = children.length;
  while (i--) {
    if (children[i].type === 1) {
      return children[i];
    } else {
      if (process.env.NODE_ENV !== 'production' && children[i].text !== ' ') {
        warn$2("text \"" + children[i].text.trim() + "\" between v-if and v-else(-if) " + "will be ignored.");
      }
      children.pop();
    }
  }
}

function addIfCondition(el, condition) {
  if (!el.ifConditions) {
    el.ifConditions = [];
  }
  el.ifConditions.push(condition);
}

function processOnce(el) {
  var once$$1 = getAndRemoveAttr(el, 'v-once');
  if (once$$1 != null) {
    el.once = true;
  }
}

function processSlot(el) {
  if (el.tag === 'slot') {
    el.slotName = getBindingAttr(el, 'name');
    if (process.env.NODE_ENV !== 'production' && el.key) {
      warn$2("`key` does not work on <slot> because slots are abstract outlets " + "and can possibly expand into multiple elements. " + "Use the key on a wrapping element instead.");
    }
  } else {
    var slotTarget = getBindingAttr(el, 'slot');
    if (slotTarget) {
      el.slotTarget = slotTarget === '""' ? '"default"' : slotTarget;
    }
    if (el.tag === 'template') {
      el.slotScope = getAndRemoveAttr(el, 'scope');
    }
  }
}

function processComponent(el) {
  var binding;
  if (binding = getBindingAttr(el, 'is')) {
    el.component = binding;
  }
  if (getAndRemoveAttr(el, 'inline-template') != null) {
    el.inlineTemplate = true;
  }
}

function processAttrs(el) {
  var list = el.attrsList;
  var i, l, name, rawName, value, modifiers, isProp;
  for (i = 0, l = list.length; i < l; i++) {
    name = rawName = list[i].name;
    value = list[i].value;
    if (dirRE.test(name)) {
      // mark element as dynamic
      el.hasBindings = true;
      // modifiers
      modifiers = parseModifiers(name);
      if (modifiers) {
        name = name.replace(modifierRE, '');
      }
      if (bindRE.test(name)) {
        // v-bind
        name = name.replace(bindRE, '');
        value = parseFilters(value);
        isProp = false;
        if (modifiers) {
          if (modifiers.prop) {
            isProp = true;
            name = camelize(name);
            if (name === 'innerHtml') {
              name = 'innerHTML';
            }
          }
          if (modifiers.camel) {
            name = camelize(name);
          }
          if (modifiers.sync) {
            addHandler(el, "update:" + camelize(name), genAssignmentCode(value, "$event"));
          }
        }
        if (isProp || platformMustUseProp(el.tag, el.attrsMap.type, name)) {
          addProp(el, name, value);
        } else {
          addAttr(el, name, value);
        }
      } else if (onRE.test(name)) {
        // v-on
        name = name.replace(onRE, '');
        addHandler(el, name, value, modifiers, false, warn$2);
      } else {
        // normal directives
        name = name.replace(dirRE, '');
        // parse arg
        var argMatch = name.match(argRE);
        var arg = argMatch && argMatch[1];
        if (arg) {
          name = name.slice(0, -(arg.length + 1));
        }
        addDirective(el, name, rawName, value, arg, modifiers);
        if (process.env.NODE_ENV !== 'production' && name === 'model') {
          checkForAliasModel(el, value);
        }
      }
    } else {
      // literal attribute
      if (process.env.NODE_ENV !== 'production') {
        var expression = parseText(value, delimiters);
        if (expression) {
          warn$2(name + "=\"" + value + "\": " + 'Interpolation inside attributes has been removed. ' + 'Use v-bind or the colon shorthand instead. For example, ' + 'instead of <div id="{{ val }}">, use <div :id="val">.');
        }
      }
      addAttr(el, name, JSON.stringify(value));
    }
  }
}

function checkInFor(el) {
  var parent = el;
  while (parent) {
    if (parent.for !== undefined) {
      return true;
    }
    parent = parent.parent;
  }
  return false;
}

function parseModifiers(name) {
  var match = name.match(modifierRE);
  if (match) {
    var ret = {};
    match.forEach(function (m) {
      ret[m.slice(1)] = true;
    });
    return ret;
  }
}

function makeAttrsMap(attrs) {
  var map = {};
  for (var i = 0, l = attrs.length; i < l; i++) {
    if (process.env.NODE_ENV !== 'production' && map[attrs[i].name] && !isIE && !isEdge) {
      warn$2('duplicate attribute: ' + attrs[i].name);
    }
    map[attrs[i].name] = attrs[i].value;
  }
  return map;
}

// for script (e.g. type="x/template") or style, do not decode content
function isTextTag(el) {
  return el.tag === 'script' || el.tag === 'style';
}

function isForbiddenTag(el) {
  return el.tag === 'style' || el.tag === 'script' && (!el.attrsMap.type || el.attrsMap.type === 'text/javascript');
}

var ieNSBug = /^xmlns:NS\d+/;
var ieNSPrefix = /^NS\d+:/;

/* istanbul ignore next */
function guardIESVGBug(attrs) {
  var res = [];
  for (var i = 0; i < attrs.length; i++) {
    var attr = attrs[i];
    if (!ieNSBug.test(attr.name)) {
      attr.name = attr.name.replace(ieNSPrefix, '');
      res.push(attr);
    }
  }
  return res;
}

function checkForAliasModel(el, value) {
  var _el = el;
  while (_el) {
    if (_el.for && _el.alias === value) {
      warn$2("<" + el.tag + " v-model=\"" + value + "\">: " + "You are binding v-model directly to a v-for iteration alias. " + "This will not be able to modify the v-for source array because " + "writing to the alias is like modifying a function local variable. " + "Consider using an array of objects and use v-model on an object property instead.");
    }
    _el = _el.parent;
  }
}

/*  */

var isStaticKey;
var isPlatformReservedTag;

var genStaticKeysCached = cached(genStaticKeys$1);

/**
 * Goal of the optimizer: walk the generated template AST tree
 * and detect sub-trees that are purely static, i.e. parts of
 * the DOM that never needs to change.
 *
 * Once we detect these sub-trees, we can:
 *
 * 1. Hoist them into constants, so that we no longer need to
 *    create fresh nodes for them on each re-render;
 * 2. Completely skip them in the patching process.
 */
function optimize(root, options) {
  if (!root) {
    return;
  }
  isStaticKey = genStaticKeysCached(options.staticKeys || '');
  isPlatformReservedTag = options.isReservedTag || no;
  // first pass: mark all non-static nodes.
  markStatic$1(root);
  // second pass: mark static roots.
  markStaticRoots(root, false);
}

function genStaticKeys$1(keys) {
  return makeMap('type,tag,attrsList,attrsMap,plain,parent,children,attrs' + (keys ? ',' + keys : ''));
}

function markStatic$1(node) {
  node.static = isStatic(node);
  if (node.type === 1) {
    // do not make component slot content static. this avoids
    // 1. components not able to mutate slot nodes
    // 2. static slot content fails for hot-reloading
    if (!isPlatformReservedTag(node.tag) && node.tag !== 'slot' && node.attrsMap['inline-template'] == null) {
      return;
    }
    for (var i = 0, l = node.children.length; i < l; i++) {
      var child = node.children[i];
      markStatic$1(child);
      if (!child.static) {
        node.static = false;
      }
    }
  }
}

function markStaticRoots(node, isInFor) {
  if (node.type === 1) {
    if (node.static || node.once) {
      node.staticInFor = isInFor;
    }
    // For a node to qualify as a static root, it should have children that
    // are not just static text. Otherwise the cost of hoisting out will
    // outweigh the benefits and it's better off to just always render it fresh.
    if (node.static && node.children.length && !(node.children.length === 1 && node.children[0].type === 3)) {
      node.staticRoot = true;
      return;
    } else {
      node.staticRoot = false;
    }
    if (node.children) {
      for (var i = 0, l = node.children.length; i < l; i++) {
        markStaticRoots(node.children[i], isInFor || !!node.for);
      }
    }
    if (node.ifConditions) {
      walkThroughConditionsBlocks(node.ifConditions, isInFor);
    }
  }
}

function walkThroughConditionsBlocks(conditionBlocks, isInFor) {
  for (var i = 1, len = conditionBlocks.length; i < len; i++) {
    markStaticRoots(conditionBlocks[i].block, isInFor);
  }
}

function isStatic(node) {
  if (node.type === 2) {
    // expression
    return false;
  }
  if (node.type === 3) {
    // text
    return true;
  }
  return !!(node.pre || !node.hasBindings && // no dynamic bindings
  !node.if && !node.for && // not v-if or v-for or v-else
  !isBuiltInTag(node.tag) && // not a built-in
  isPlatformReservedTag(node.tag) && // not a component
  !isDirectChildOfTemplateFor(node) && Object.keys(node).every(isStaticKey));
}

function isDirectChildOfTemplateFor(node) {
  while (node.parent) {
    node = node.parent;
    if (node.tag !== 'template') {
      return false;
    }
    if (node.for) {
      return true;
    }
  }
  return false;
}

/*  */

var fnExpRE = /^\s*([\w$_]+|\([^)]*?\))\s*=>|^function\s*\(/;
var simplePathRE = /^\s*[A-Za-z_$][\w$]*(?:\.[A-Za-z_$][\w$]*|\['.*?']|\[".*?"]|\[\d+]|\[[A-Za-z_$][\w$]*])*\s*$/;

// keyCode aliases
var keyCodes = {
  esc: 27,
  tab: 9,
  enter: 13,
  space: 32,
  up: 38,
  left: 37,
  right: 39,
  down: 40,
  'delete': [8, 46]
};

// #4868: modifiers that prevent the execution of the listener
// need to explicitly return null so that we can determine whether to remove
// the listener for .once
var genGuard = function genGuard(condition) {
  return "if(" + condition + ")return null;";
};

var modifierCode = {
  stop: '$event.stopPropagation();',
  prevent: '$event.preventDefault();',
  self: genGuard("$event.target !== $event.currentTarget"),
  ctrl: genGuard("!$event.ctrlKey"),
  shift: genGuard("!$event.shiftKey"),
  alt: genGuard("!$event.altKey"),
  meta: genGuard("!$event.metaKey"),
  left: genGuard("'button' in $event && $event.button !== 0"),
  middle: genGuard("'button' in $event && $event.button !== 1"),
  right: genGuard("'button' in $event && $event.button !== 2")
};

function genHandlers(events, isNative, warn) {
  var res = isNative ? 'nativeOn:{' : 'on:{';
  for (var name in events) {
    var handler = events[name];
    // #5330: warn click.right, since right clicks do not actually fire click events.
    if (process.env.NODE_ENV !== 'production' && name === 'click' && handler && handler.modifiers && handler.modifiers.right) {
      warn("Use \"contextmenu\" instead of \"click.right\" since right clicks " + "do not actually fire \"click\" events.");
    }
    res += "\"" + name + "\":" + genHandler(name, handler) + ",";
  }
  return res.slice(0, -1) + '}';
}

function genHandler(name, handler) {
  if (!handler) {
    return 'function(){}';
  }

  if (Array.isArray(handler)) {
    return "[" + handler.map(function (handler) {
      return genHandler(name, handler);
    }).join(',') + "]";
  }

  var isMethodPath = simplePathRE.test(handler.value);
  var isFunctionExpression = fnExpRE.test(handler.value);

  if (!handler.modifiers) {
    return isMethodPath || isFunctionExpression ? handler.value : "function($event){" + handler.value + "}"; // inline statement
  } else {
    var code = '';
    var genModifierCode = '';
    var keys = [];
    for (var key in handler.modifiers) {
      if (modifierCode[key]) {
        genModifierCode += modifierCode[key];
        // left/right
        if (keyCodes[key]) {
          keys.push(key);
        }
      } else {
        keys.push(key);
      }
    }
    if (keys.length) {
      code += genKeyFilter(keys);
    }
    // Make sure modifiers like prevent and stop get executed after key filtering
    if (genModifierCode) {
      code += genModifierCode;
    }
    var handlerCode = isMethodPath ? handler.value + '($event)' : isFunctionExpression ? "(" + handler.value + ")($event)" : handler.value;
    return "function($event){" + code + handlerCode + "}";
  }
}

function genKeyFilter(keys) {
  return "if(!('button' in $event)&&" + keys.map(genFilterCode).join('&&') + ")return null;";
}

function genFilterCode(key) {
  var keyVal = parseInt(key, 10);
  if (keyVal) {
    return "$event.keyCode!==" + keyVal;
  }
  var alias = keyCodes[key];
  return "_k($event.keyCode," + JSON.stringify(key) + (alias ? ',' + JSON.stringify(alias) : '') + ")";
}

/*  */

function bind$1(el, dir) {
  el.wrapData = function (code) {
    return "_b(" + code + ",'" + el.tag + "'," + dir.value + (dir.modifiers && dir.modifiers.prop ? ',true' : '') + ")";
  };
}

/*  */

var baseDirectives = {
  bind: bind$1,
  cloak: noop
};

/*  */

// configurable state
var warn$3;
var transforms$1;
var dataGenFns;
var platformDirectives$1;
var isPlatformReservedTag$1;
var staticRenderFns;
var onceCount;
var currentOptions;

function generate(ast, options) {
  // save previous staticRenderFns so generate calls can be nested
  var prevStaticRenderFns = staticRenderFns;
  var currentStaticRenderFns = staticRenderFns = [];
  var prevOnceCount = onceCount;
  onceCount = 0;
  currentOptions = options;
  warn$3 = options.warn || baseWarn;
  transforms$1 = pluckModuleFunction(options.modules, 'transformCode');
  dataGenFns = pluckModuleFunction(options.modules, 'genData');
  platformDirectives$1 = options.directives || {};
  isPlatformReservedTag$1 = options.isReservedTag || no;
  var code = ast ? genElement(ast) : '_c("div")';
  staticRenderFns = prevStaticRenderFns;
  onceCount = prevOnceCount;
  return {
    render: "with(this){return " + code + "}",
    staticRenderFns: currentStaticRenderFns
  };
}

function genElement(el) {
  if (el.staticRoot && !el.staticProcessed) {
    return genStatic(el);
  } else if (el.once && !el.onceProcessed) {
    return genOnce(el);
  } else if (el.for && !el.forProcessed) {
    return genFor(el);
  } else if (el.if && !el.ifProcessed) {
    return genIf(el);
  } else if (el.tag === 'template' && !el.slotTarget) {
    return genChildren(el) || 'void 0';
  } else if (el.tag === 'slot') {
    return genSlot(el);
  } else {
    // component or element
    var code;
    if (el.component) {
      code = genComponent(el.component, el);
    } else {
      var data = el.plain ? undefined : genData(el);

      var children = el.inlineTemplate ? null : genChildren(el, true);
      code = "_c('" + el.tag + "'" + (data ? "," + data : '') + (children ? "," + children : '') + ")";
    }
    // module transforms
    for (var i = 0; i < transforms$1.length; i++) {
      code = transforms$1[i](el, code);
    }
    return code;
  }
}

// hoist static sub-trees out
function genStatic(el) {
  el.staticProcessed = true;
  staticRenderFns.push("with(this){return " + genElement(el) + "}");
  return "_m(" + (staticRenderFns.length - 1) + (el.staticInFor ? ',true' : '') + ")";
}

// v-once
function genOnce(el) {
  el.onceProcessed = true;
  if (el.if && !el.ifProcessed) {
    return genIf(el);
  } else if (el.staticInFor) {
    var key = '';
    var parent = el.parent;
    while (parent) {
      if (parent.for) {
        key = parent.key;
        break;
      }
      parent = parent.parent;
    }
    if (!key) {
      process.env.NODE_ENV !== 'production' && warn$3("v-once can only be used inside v-for that is keyed. ");
      return genElement(el);
    }
    return "_o(" + genElement(el) + "," + onceCount++ + (key ? "," + key : "") + ")";
  } else {
    return genStatic(el);
  }
}

function genIf(el) {
  el.ifProcessed = true; // avoid recursion
  return genIfConditions(el.ifConditions.slice());
}

function genIfConditions(conditions) {
  if (!conditions.length) {
    return '_e()';
  }

  var condition = conditions.shift();
  if (condition.exp) {
    return "(" + condition.exp + ")?" + genTernaryExp(condition.block) + ":" + genIfConditions(conditions);
  } else {
    return "" + genTernaryExp(condition.block);
  }

  // v-if with v-once should generate code like (a)?_m(0):_m(1)
  function genTernaryExp(el) {
    return el.once ? genOnce(el) : genElement(el);
  }
}

function genFor(el) {
  var exp = el.for;
  var alias = el.alias;
  var iterator1 = el.iterator1 ? "," + el.iterator1 : '';
  var iterator2 = el.iterator2 ? "," + el.iterator2 : '';

  if (process.env.NODE_ENV !== 'production' && maybeComponent(el) && el.tag !== 'slot' && el.tag !== 'template' && !el.key) {
    warn$3("<" + el.tag + " v-for=\"" + alias + " in " + exp + "\">: component lists rendered with " + "v-for should have explicit keys. " + "See https://vuejs.org/guide/list.html#key for more info.", true /* tip */
    );
  }

  el.forProcessed = true; // avoid recursion
  return "_l((" + exp + ")," + "function(" + alias + iterator1 + iterator2 + "){" + "return " + genElement(el) + '})';
}

function genData(el) {
  var data = '{';

  // directives first.
  // directives may mutate the el's other properties before they are generated.
  var dirs = genDirectives(el);
  if (dirs) {
    data += dirs + ',';
  }

  // key
  if (el.key) {
    data += "key:" + el.key + ",";
  }
  // ref
  if (el.ref) {
    data += "ref:" + el.ref + ",";
  }
  if (el.refInFor) {
    data += "refInFor:true,";
  }
  // pre
  if (el.pre) {
    data += "pre:true,";
  }
  // record original tag name for components using "is" attribute
  if (el.component) {
    data += "tag:\"" + el.tag + "\",";
  }
  // module data generation functions
  for (var i = 0; i < dataGenFns.length; i++) {
    data += dataGenFns[i](el);
  }
  // attributes
  if (el.attrs) {
    data += "attrs:{" + genProps(el.attrs) + "},";
  }
  // DOM props
  if (el.props) {
    data += "domProps:{" + genProps(el.props) + "},";
  }
  // event handlers
  if (el.events) {
    data += genHandlers(el.events, false, warn$3) + ",";
  }
  if (el.nativeEvents) {
    data += genHandlers(el.nativeEvents, true, warn$3) + ",";
  }
  // slot target
  if (el.slotTarget) {
    data += "slot:" + el.slotTarget + ",";
  }
  // scoped slots
  if (el.scopedSlots) {
    data += genScopedSlots(el.scopedSlots) + ",";
  }
  // component v-model
  if (el.model) {
    data += "model:{value:" + el.model.value + ",callback:" + el.model.callback + ",expression:" + el.model.expression + "},";
  }
  // inline-template
  if (el.inlineTemplate) {
    var inlineTemplate = genInlineTemplate(el);
    if (inlineTemplate) {
      data += inlineTemplate + ",";
    }
  }
  data = data.replace(/,$/, '') + '}';
  // v-bind data wrap
  if (el.wrapData) {
    data = el.wrapData(data);
  }
  return data;
}

function genDirectives(el) {
  var dirs = el.directives;
  if (!dirs) {
    return;
  }
  var res = 'directives:[';
  var hasRuntime = false;
  var i, l, dir, needRuntime;
  for (i = 0, l = dirs.length; i < l; i++) {
    dir = dirs[i];
    needRuntime = true;
    var gen = platformDirectives$1[dir.name] || baseDirectives[dir.name];
    if (gen) {
      // compile-time directive that manipulates AST.
      // returns true if it also needs a runtime counterpart.
      needRuntime = !!gen(el, dir, warn$3);
    }
    if (needRuntime) {
      hasRuntime = true;
      res += "{name:\"" + dir.name + "\",rawName:\"" + dir.rawName + "\"" + (dir.value ? ",value:(" + dir.value + "),expression:" + JSON.stringify(dir.value) : '') + (dir.arg ? ",arg:\"" + dir.arg + "\"" : '') + (dir.modifiers ? ",modifiers:" + JSON.stringify(dir.modifiers) : '') + "},";
    }
  }
  if (hasRuntime) {
    return res.slice(0, -1) + ']';
  }
}

function genInlineTemplate(el) {
  var ast = el.children[0];
  if (process.env.NODE_ENV !== 'production' && (el.children.length > 1 || ast.type !== 1)) {
    warn$3('Inline-template components must have exactly one child element.');
  }
  if (ast.type === 1) {
    var inlineRenderFns = generate(ast, currentOptions);
    return "inlineTemplate:{render:function(){" + inlineRenderFns.render + "},staticRenderFns:[" + inlineRenderFns.staticRenderFns.map(function (code) {
      return "function(){" + code + "}";
    }).join(',') + "]}";
  }
}

function genScopedSlots(slots) {
  return "scopedSlots:_u([" + Object.keys(slots).map(function (key) {
    return genScopedSlot(key, slots[key]);
  }).join(',') + "])";
}

function genScopedSlot(key, el) {
  if (el.for && !el.forProcessed) {
    return genForScopedSlot(key, el);
  }
  return "{key:" + key + ",fn:function(" + String(el.attrsMap.scope) + "){" + "return " + (el.tag === 'template' ? genChildren(el) || 'void 0' : genElement(el)) + "}}";
}

function genForScopedSlot(key, el) {
  var exp = el.for;
  var alias = el.alias;
  var iterator1 = el.iterator1 ? "," + el.iterator1 : '';
  var iterator2 = el.iterator2 ? "," + el.iterator2 : '';
  el.forProcessed = true; // avoid recursion
  return "_l((" + exp + ")," + "function(" + alias + iterator1 + iterator2 + "){" + "return " + genScopedSlot(key, el) + '})';
}

function genChildren(el, checkSkip) {
  var children = el.children;
  if (children.length) {
    var el$1 = children[0];
    // optimize single v-for
    if (children.length === 1 && el$1.for && el$1.tag !== 'template' && el$1.tag !== 'slot') {
      return genElement(el$1);
    }
    var normalizationType = checkSkip ? getNormalizationType(children) : 0;
    return "[" + children.map(genNode).join(',') + "]" + (normalizationType ? "," + normalizationType : '');
  }
}

// determine the normalization needed for the children array.
// 0: no normalization needed
// 1: simple normalization needed (possible 1-level deep nested array)
// 2: full normalization needed
function getNormalizationType(children) {
  var res = 0;
  for (var i = 0; i < children.length; i++) {
    var el = children[i];
    if (el.type !== 1) {
      continue;
    }
    if (needsNormalization(el) || el.ifConditions && el.ifConditions.some(function (c) {
      return needsNormalization(c.block);
    })) {
      res = 2;
      break;
    }
    if (maybeComponent(el) || el.ifConditions && el.ifConditions.some(function (c) {
      return maybeComponent(c.block);
    })) {
      res = 1;
    }
  }
  return res;
}

function needsNormalization(el) {
  return el.for !== undefined || el.tag === 'template' || el.tag === 'slot';
}

function maybeComponent(el) {
  return !isPlatformReservedTag$1(el.tag);
}

function genNode(node) {
  if (node.type === 1) {
    return genElement(node);
  } else {
    return genText(node);
  }
}

function genText(text) {
  return "_v(" + (text.type === 2 ? text.expression // no need for () because already wrapped in _s()
  : transformSpecialNewlines(JSON.stringify(text.text))) + ")";
}

function genSlot(el) {
  var slotName = el.slotName || '"default"';
  var children = genChildren(el);
  var res = "_t(" + slotName + (children ? "," + children : '');
  var attrs = el.attrs && "{" + el.attrs.map(function (a) {
    return camelize(a.name) + ":" + a.value;
  }).join(',') + "}";
  var bind$$1 = el.attrsMap['v-bind'];
  if ((attrs || bind$$1) && !children) {
    res += ",null";
  }
  if (attrs) {
    res += "," + attrs;
  }
  if (bind$$1) {
    res += (attrs ? '' : ',null') + "," + bind$$1;
  }
  return res + ')';
}

// componentName is el.component, take it as argument to shun flow's pessimistic refinement
function genComponent(componentName, el) {
  var children = el.inlineTemplate ? null : genChildren(el, true);
  return "_c(" + componentName + "," + genData(el) + (children ? "," + children : '') + ")";
}

function genProps(props) {
  var res = '';
  for (var i = 0; i < props.length; i++) {
    var prop = props[i];
    res += "\"" + prop.name + "\":" + transformSpecialNewlines(prop.value) + ",";
  }
  return res.slice(0, -1);
}

// #3895, #4268
function transformSpecialNewlines(text) {
  return text.replace(/\u2028/g, '\\u2028').replace(/\u2029/g, '\\u2029');
}

/*  */

// these keywords should not appear inside expressions, but operators like
// typeof, instanceof and in are allowed
var prohibitedKeywordRE = new RegExp('\\b' + ('do,if,for,let,new,try,var,case,else,with,await,break,catch,class,const,' + 'super,throw,while,yield,delete,export,import,return,switch,default,' + 'extends,finally,continue,debugger,function,arguments').split(',').join('\\b|\\b') + '\\b');

// these unary operators should not be used as property/method names
var unaryOperatorsRE = new RegExp('\\b' + 'delete,typeof,void'.split(',').join('\\s*\\([^\\)]*\\)|\\b') + '\\s*\\([^\\)]*\\)');

// check valid identifier for v-for
var identRE = /[A-Za-z_$][\w$]*/;

// strip strings in expressions
var stripStringRE = /'(?:[^'\\]|\\.)*'|"(?:[^"\\]|\\.)*"|`(?:[^`\\]|\\.)*\$\{|\}(?:[^`\\]|\\.)*`|`(?:[^`\\]|\\.)*`/g;

// detect problematic expressions in a template
function detectErrors(ast) {
  var errors = [];
  if (ast) {
    checkNode(ast, errors);
  }
  return errors;
}

function checkNode(node, errors) {
  if (node.type === 1) {
    for (var name in node.attrsMap) {
      if (dirRE.test(name)) {
        var value = node.attrsMap[name];
        if (value) {
          if (name === 'v-for') {
            checkFor(node, "v-for=\"" + value + "\"", errors);
          } else if (onRE.test(name)) {
            checkEvent(value, name + "=\"" + value + "\"", errors);
          } else {
            checkExpression(value, name + "=\"" + value + "\"", errors);
          }
        }
      }
    }
    if (node.children) {
      for (var i = 0; i < node.children.length; i++) {
        checkNode(node.children[i], errors);
      }
    }
  } else if (node.type === 2) {
    checkExpression(node.expression, node.text, errors);
  }
}

function checkEvent(exp, text, errors) {
  var stipped = exp.replace(stripStringRE, '');
  var keywordMatch = stipped.match(unaryOperatorsRE);
  if (keywordMatch && stipped.charAt(keywordMatch.index - 1) !== '$') {
    errors.push("avoid using JavaScript unary operator as property name: " + "\"" + keywordMatch[0] + "\" in expression " + text.trim());
  }
  checkExpression(exp, text, errors);
}

function checkFor(node, text, errors) {
  checkExpression(node.for || '', text, errors);
  checkIdentifier(node.alias, 'v-for alias', text, errors);
  checkIdentifier(node.iterator1, 'v-for iterator', text, errors);
  checkIdentifier(node.iterator2, 'v-for iterator', text, errors);
}

function checkIdentifier(ident, type, text, errors) {
  if (typeof ident === 'string' && !identRE.test(ident)) {
    errors.push("invalid " + type + " \"" + ident + "\" in expression: " + text.trim());
  }
}

function checkExpression(exp, text, errors) {
  try {
    new Function("return " + exp);
  } catch (e) {
    var keywordMatch = exp.replace(stripStringRE, '').match(prohibitedKeywordRE);
    if (keywordMatch) {
      errors.push("avoid using JavaScript keyword as property name: " + "\"" + keywordMatch[0] + "\" in expression " + text.trim());
    } else {
      errors.push("invalid expression: " + text.trim());
    }
  }
}

/*  */

function baseCompile(template, options) {
  var ast = parse(template.trim(), options);
  optimize(ast, options);
  var code = generate(ast, options);
  return {
    ast: ast,
    render: code.render,
    staticRenderFns: code.staticRenderFns
  };
}

function makeFunction(code, errors) {
  try {
    return new Function(code);
  } catch (err) {
    errors.push({ err: err, code: code });
    return noop;
  }
}

function createCompiler(baseOptions) {
  var functionCompileCache = Object.create(null);

  function compile(template, options) {
    var finalOptions = Object.create(baseOptions);
    var errors = [];
    var tips = [];
    finalOptions.warn = function (msg, tip$$1) {
      (tip$$1 ? tips : errors).push(msg);
    };

    if (options) {
      // merge custom modules
      if (options.modules) {
        finalOptions.modules = (baseOptions.modules || []).concat(options.modules);
      }
      // merge custom directives
      if (options.directives) {
        finalOptions.directives = extend(Object.create(baseOptions.directives), options.directives);
      }
      // copy other options
      for (var key in options) {
        if (key !== 'modules' && key !== 'directives') {
          finalOptions[key] = options[key];
        }
      }
    }

    var compiled = baseCompile(template, finalOptions);
    if (process.env.NODE_ENV !== 'production') {
      errors.push.apply(errors, detectErrors(compiled.ast));
    }
    compiled.errors = errors;
    compiled.tips = tips;
    return compiled;
  }

  function compileToFunctions(template, options, vm) {
    options = options || {};

    /* istanbul ignore if */
    if (process.env.NODE_ENV !== 'production') {
      // detect possible CSP restriction
      try {
        new Function('return 1');
      } catch (e) {
        if (e.toString().match(/unsafe-eval|CSP/)) {
          warn('It seems you are using the standalone build of Vue.js in an ' + 'environment with Content Security Policy that prohibits unsafe-eval. ' + 'The template compiler cannot work in this environment. Consider ' + 'relaxing the policy to allow unsafe-eval or pre-compiling your ' + 'templates into render functions.');
        }
      }
    }

    // check cache
    var key = options.delimiters ? String(options.delimiters) + template : template;
    if (functionCompileCache[key]) {
      return functionCompileCache[key];
    }

    // compile
    var compiled = compile(template, options);

    // check compilation errors/tips
    if (process.env.NODE_ENV !== 'production') {
      if (compiled.errors && compiled.errors.length) {
        warn("Error compiling template:\n\n" + template + "\n\n" + compiled.errors.map(function (e) {
          return "- " + e;
        }).join('\n') + '\n', vm);
      }
      if (compiled.tips && compiled.tips.length) {
        compiled.tips.forEach(function (msg) {
          return tip(msg, vm);
        });
      }
    }

    // turn code into functions
    var res = {};
    var fnGenErrors = [];
    res.render = makeFunction(compiled.render, fnGenErrors);
    var l = compiled.staticRenderFns.length;
    res.staticRenderFns = new Array(l);
    for (var i = 0; i < l; i++) {
      res.staticRenderFns[i] = makeFunction(compiled.staticRenderFns[i], fnGenErrors);
    }

    // check function generation errors.
    // this should only happen if there is a bug in the compiler itself.
    // mostly for codegen development use
    /* istanbul ignore if */
    if (process.env.NODE_ENV !== 'production') {
      if ((!compiled.errors || !compiled.errors.length) && fnGenErrors.length) {
        warn("Failed to generate render function:\n\n" + fnGenErrors.map(function (ref) {
          var err = ref.err;
          var code = ref.code;

          return err.toString() + " in\n\n" + code + "\n";
        }).join('\n'), vm);
      }
    }

    return functionCompileCache[key] = res;
  }

  return {
    compile: compile,
    compileToFunctions: compileToFunctions
  };
}

/*  */

function transformNode(el, options) {
  var warn = options.warn || baseWarn;
  var staticClass = getAndRemoveAttr(el, 'class');
  if (process.env.NODE_ENV !== 'production' && staticClass) {
    var expression = parseText(staticClass, options.delimiters);
    if (expression) {
      warn("class=\"" + staticClass + "\": " + 'Interpolation inside attributes has been removed. ' + 'Use v-bind or the colon shorthand instead. For example, ' + 'instead of <div class="{{ val }}">, use <div :class="val">.');
    }
  }
  if (staticClass) {
    el.staticClass = JSON.stringify(staticClass);
  }
  var classBinding = getBindingAttr(el, 'class', false /* getStatic */);
  if (classBinding) {
    el.classBinding = classBinding;
  }
}

function genData$1(el) {
  var data = '';
  if (el.staticClass) {
    data += "staticClass:" + el.staticClass + ",";
  }
  if (el.classBinding) {
    data += "class:" + el.classBinding + ",";
  }
  return data;
}

var klass$1 = {
  staticKeys: ['staticClass'],
  transformNode: transformNode,
  genData: genData$1
};

/*  */

function transformNode$1(el, options) {
  var warn = options.warn || baseWarn;
  var staticStyle = getAndRemoveAttr(el, 'style');
  if (staticStyle) {
    /* istanbul ignore if */
    if (process.env.NODE_ENV !== 'production') {
      var expression = parseText(staticStyle, options.delimiters);
      if (expression) {
        warn("style=\"" + staticStyle + "\": " + 'Interpolation inside attributes has been removed. ' + 'Use v-bind or the colon shorthand instead. For example, ' + 'instead of <div style="{{ val }}">, use <div :style="val">.');
      }
    }
    el.staticStyle = JSON.stringify(parseStyleText(staticStyle));
  }

  var styleBinding = getBindingAttr(el, 'style', false /* getStatic */);
  if (styleBinding) {
    el.styleBinding = styleBinding;
  }
}

function genData$2(el) {
  var data = '';
  if (el.staticStyle) {
    data += "staticStyle:" + el.staticStyle + ",";
  }
  if (el.styleBinding) {
    data += "style:(" + el.styleBinding + "),";
  }
  return data;
}

var style$1 = {
  staticKeys: ['staticStyle'],
  transformNode: transformNode$1,
  genData: genData$2
};

var modules$1 = [klass$1, style$1];

/*  */

function text(el, dir) {
  if (dir.value) {
    addProp(el, 'textContent', "_s(" + dir.value + ")");
  }
}

/*  */

function html(el, dir) {
  if (dir.value) {
    addProp(el, 'innerHTML', "_s(" + dir.value + ")");
  }
}

var directives$1 = {
  model: model,
  text: text,
  html: html
};

/*  */

var baseOptions = {
  expectHTML: true,
  modules: modules$1,
  directives: directives$1,
  isPreTag: isPreTag,
  isUnaryTag: isUnaryTag,
  mustUseProp: mustUseProp,
  canBeLeftOpenTag: canBeLeftOpenTag,
  isReservedTag: isReservedTag,
  getTagNamespace: getTagNamespace,
  staticKeys: genStaticKeys(modules$1)
};

var ref$1 = createCompiler(baseOptions);
var compileToFunctions = ref$1.compileToFunctions;

/*  */

var idToTemplate = cached(function (id) {
  var el = query(id);
  return el && el.innerHTML;
});

var mount = Vue$3.prototype.$mount;
Vue$3.prototype.$mount = function (el, hydrating) {
  el = el && query(el);

  /* istanbul ignore if */
  if (el === document.body || el === document.documentElement) {
    process.env.NODE_ENV !== 'production' && warn("Do not mount Vue to <html> or <body> - mount to normal elements instead.");
    return this;
  }

  var options = this.$options;
  // resolve template/el and convert to render function
  if (!options.render) {
    var template = options.template;
    if (template) {
      if (typeof template === 'string') {
        if (template.charAt(0) === '#') {
          template = idToTemplate(template);
          /* istanbul ignore if */
          if (process.env.NODE_ENV !== 'production' && !template) {
            warn("Template element not found or is empty: " + options.template, this);
          }
        }
      } else if (template.nodeType) {
        template = template.innerHTML;
      } else {
        if (process.env.NODE_ENV !== 'production') {
          warn('invalid template option:' + template, this);
        }
        return this;
      }
    } else if (el) {
      template = getOuterHTML(el);
    }
    if (template) {
      /* istanbul ignore if */
      if (process.env.NODE_ENV !== 'production' && config.performance && mark) {
        mark('compile');
      }

      var ref = compileToFunctions(template, {
        shouldDecodeNewlines: shouldDecodeNewlines,
        delimiters: options.delimiters
      }, this);
      var render = ref.render;
      var staticRenderFns = ref.staticRenderFns;
      options.render = render;
      options.staticRenderFns = staticRenderFns;

      /* istanbul ignore if */
      if (process.env.NODE_ENV !== 'production' && config.performance && mark) {
        mark('compile end');
        measure(this._name + " compile", 'compile', 'compile end');
      }
    }
  }
  return mount.call(this, el, hydrating);
};

/**
 * Get outerHTML of elements, taking care
 * of SVG elements in IE as well.
 */
function getOuterHTML(el) {
  if (el.outerHTML) {
    return el.outerHTML;
  } else {
    var container = document.createElement('div');
    container.appendChild(el.cloneNode(true));
    return container.innerHTML;
  }
}

Vue$3.compile = compileToFunctions;

module.exports = Vue$3;
/* WEBPACK VAR INJECTION */}.call(exports, __webpack_require__(63), __webpack_require__(70)))

/***/ }),

/***/ 49:
/***/ (function(module, exports, __webpack_require__) {

var disposed = false
function injectStyle (ssrContext) {
  if (disposed) return
  __webpack_require__(286)
}
var Component = __webpack_require__(2)(
  /* script */
  __webpack_require__(171),
  /* template */
  __webpack_require__(253),
  /* styles */
  injectStyle,
  /* scopeId */
  "data-v-a4caae92",
  /* moduleIdentifier (server only) */
  null
)
Component.options.__file = "G:\\workspace\\dozenx\\ui\\webpack\\app\\component\\navigation\\zwAppTopBar.vue"
if (Component.esModule && Object.keys(Component.esModule).some(function (key) {return key !== "default" && key.substr(0, 2) !== "__"})) {console.error("named exports are not supported in *.vue files.")}
if (Component.options.functional) {console.error("[vue-loader] zwAppTopBar.vue: functional components are not supported with templates, they should use render functions.")}

/* hot reload */
if (false) {(function () {
  var hotAPI = require("vue-hot-reload-api")
  hotAPI.install(require("vue"), false)
  if (!hotAPI.compatible) return
  module.hot.accept()
  if (!module.hot.data) {
    hotAPI.createRecord("data-v-a4caae92", Component.options)
  } else {
    hotAPI.reload("data-v-a4caae92", Component.options)
  }
  module.hot.dispose(function (data) {
    disposed = true
  })
})()}

module.exports = Component.exports


/***/ }),

/***/ 5:
/***/ (function(module, exports, __webpack_require__) {

var disposed = false
var Component = __webpack_require__(2)(
  /* script */
  __webpack_require__(92),
  /* template */
  __webpack_require__(115),
  /* styles */
  null,
  /* scopeId */
  null,
  /* moduleIdentifier (server only) */
  null
)
Component.options.__file = "G:\\workspace\\dozenx\\ui\\webpack\\app\\component\\layout\\zwRow.vue"
if (Component.esModule && Object.keys(Component.esModule).some(function (key) {return key !== "default" && key.substr(0, 2) !== "__"})) {console.error("named exports are not supported in *.vue files.")}
if (Component.options.functional) {console.error("[vue-loader] zwRow.vue: functional components are not supported with templates, they should use render functions.")}

/* hot reload */
if (false) {(function () {
  var hotAPI = require("vue-hot-reload-api")
  hotAPI.install(require("vue"), false)
  if (!hotAPI.compatible) return
  module.hot.accept()
  if (!module.hot.data) {
    hotAPI.createRecord("data-v-5dacc2b0", Component.options)
  } else {
    hotAPI.reload("data-v-5dacc2b0", Component.options)
  }
  module.hot.dispose(function (data) {
    disposed = true
  })
})()}

module.exports = Component.exports


/***/ }),

/***/ 51:
/***/ (function(module, exports, __webpack_require__) {

// style-loader: Adds some css to the DOM by adding a <style> tag

// load the styles
var content = __webpack_require__(97);
if(typeof content === 'string') content = [[module.i, content, '']];
if(content.locals) module.exports = content.locals;
// add the styles to the DOM
var update = __webpack_require__(1)("f34cf7b8", content, false);
// Hot Module Replacement
if(false) {
 // When the styles change, update the <style> tags
 if(!content.locals) {
   module.hot.accept("!!../../node_modules/css-loader/index.js?importLoaders=1!../../node_modules/sass-loader/lib/loader.js!../../node_modules/postcss-loader/index.js!./app_dialog.scss", function() {
     var newContent = require("!!../../node_modules/css-loader/index.js?importLoaders=1!../../node_modules/sass-loader/lib/loader.js!../../node_modules/postcss-loader/index.js!./app_dialog.scss");
     if(typeof newContent === 'string') newContent = [[module.id, newContent, '']];
     update(newContent);
   });
 }
 // When the module is disposed, remove the <style> tags
 module.hot.dispose(function() { update(); });
}

/***/ }),

/***/ 52:
/***/ (function(module, exports, __webpack_require__) {

// style-loader: Adds some css to the DOM by adding a <style> tag

// load the styles
var content = __webpack_require__(98);
if(typeof content === 'string') content = [[module.i, content, '']];
if(content.locals) module.exports = content.locals;
// add the styles to the DOM
var update = __webpack_require__(1)("1f21b9b4", content, false);
// Hot Module Replacement
if(false) {
 // When the styles change, update the <style> tags
 if(!content.locals) {
   module.hot.accept("!!../../node_modules/css-loader/index.js?importLoaders=1!../../node_modules/sass-loader/lib/loader.js!../../node_modules/postcss-loader/index.js!./btn.scss", function() {
     var newContent = require("!!../../node_modules/css-loader/index.js?importLoaders=1!../../node_modules/sass-loader/lib/loader.js!../../node_modules/postcss-loader/index.js!./btn.scss");
     if(typeof newContent === 'string') newContent = [[module.id, newContent, '']];
     update(newContent);
   });
 }
 // When the module is disposed, remove the <style> tags
 module.hot.dispose(function() { update(); });
}

/***/ }),

/***/ 53:
/***/ (function(module, exports, __webpack_require__) {

// style-loader: Adds some css to the DOM by adding a <style> tag

// load the styles
var content = __webpack_require__(99);
if(typeof content === 'string') content = [[module.i, content, '']];
if(content.locals) module.exports = content.locals;
// add the styles to the DOM
var update = __webpack_require__(1)("6db40a29", content, false);
// Hot Module Replacement
if(false) {
 // When the styles change, update the <style> tags
 if(!content.locals) {
   module.hot.accept("!!../../node_modules/css-loader/index.js?importLoaders=1!../../node_modules/sass-loader/lib/loader.js!../../node_modules/postcss-loader/index.js!./common.scss", function() {
     var newContent = require("!!../../node_modules/css-loader/index.js?importLoaders=1!../../node_modules/sass-loader/lib/loader.js!../../node_modules/postcss-loader/index.js!./common.scss");
     if(typeof newContent === 'string') newContent = [[module.id, newContent, '']];
     update(newContent);
   });
 }
 // When the module is disposed, remove the <style> tags
 module.hot.dispose(function() { update(); });
}

/***/ }),

/***/ 54:
/***/ (function(module, exports, __webpack_require__) {

// style-loader: Adds some css to the DOM by adding a <style> tag

// load the styles
var content = __webpack_require__(100);
if(typeof content === 'string') content = [[module.i, content, '']];
if(content.locals) module.exports = content.locals;
// add the styles to the DOM
var update = __webpack_require__(1)("34f8684c", content, false);
// Hot Module Replacement
if(false) {
 // When the styles change, update the <style> tags
 if(!content.locals) {
   module.hot.accept("!!../../node_modules/css-loader/index.js?importLoaders=1!../../node_modules/sass-loader/lib/loader.js!../../node_modules/postcss-loader/index.js!./dropmenu.scss", function() {
     var newContent = require("!!../../node_modules/css-loader/index.js?importLoaders=1!../../node_modules/sass-loader/lib/loader.js!../../node_modules/postcss-loader/index.js!./dropmenu.scss");
     if(typeof newContent === 'string') newContent = [[module.id, newContent, '']];
     update(newContent);
   });
 }
 // When the module is disposed, remove the <style> tags
 module.hot.dispose(function() { update(); });
}

/***/ }),

/***/ 55:
/***/ (function(module, exports, __webpack_require__) {

// style-loader: Adds some css to the DOM by adding a <style> tag

// load the styles
var content = __webpack_require__(101);
if(typeof content === 'string') content = [[module.i, content, '']];
if(content.locals) module.exports = content.locals;
// add the styles to the DOM
var update = __webpack_require__(1)("388dfcc2", content, false);
// Hot Module Replacement
if(false) {
 // When the styles change, update the <style> tags
 if(!content.locals) {
   module.hot.accept("!!../../node_modules/css-loader/index.js?importLoaders=1!../../node_modules/sass-loader/lib/loader.js!../../node_modules/postcss-loader/index.js!./form.scss", function() {
     var newContent = require("!!../../node_modules/css-loader/index.js?importLoaders=1!../../node_modules/sass-loader/lib/loader.js!../../node_modules/postcss-loader/index.js!./form.scss");
     if(typeof newContent === 'string') newContent = [[module.id, newContent, '']];
     update(newContent);
   });
 }
 // When the module is disposed, remove the <style> tags
 module.hot.dispose(function() { update(); });
}

/***/ }),

/***/ 56:
/***/ (function(module, exports, __webpack_require__) {

// style-loader: Adds some css to the DOM by adding a <style> tag

// load the styles
var content = __webpack_require__(102);
if(typeof content === 'string') content = [[module.i, content, '']];
if(content.locals) module.exports = content.locals;
// add the styles to the DOM
var update = __webpack_require__(1)("abc2fd56", content, false);
// Hot Module Replacement
if(false) {
 // When the styles change, update the <style> tags
 if(!content.locals) {
   module.hot.accept("!!../../node_modules/css-loader/index.js?importLoaders=1!../../node_modules/sass-loader/lib/loader.js!../../node_modules/postcss-loader/index.js!./iconlist.scss", function() {
     var newContent = require("!!../../node_modules/css-loader/index.js?importLoaders=1!../../node_modules/sass-loader/lib/loader.js!../../node_modules/postcss-loader/index.js!./iconlist.scss");
     if(typeof newContent === 'string') newContent = [[module.id, newContent, '']];
     update(newContent);
   });
 }
 // When the module is disposed, remove the <style> tags
 module.hot.dispose(function() { update(); });
}

/***/ }),

/***/ 57:
/***/ (function(module, exports, __webpack_require__) {

// style-loader: Adds some css to the DOM by adding a <style> tag

// load the styles
var content = __webpack_require__(103);
if(typeof content === 'string') content = [[module.i, content, '']];
if(content.locals) module.exports = content.locals;
// add the styles to the DOM
var update = __webpack_require__(1)("51d09fb0", content, false);
// Hot Module Replacement
if(false) {
 // When the styles change, update the <style> tags
 if(!content.locals) {
   module.hot.accept("!!../../node_modules/css-loader/index.js?importLoaders=1!../../node_modules/sass-loader/lib/loader.js!../../node_modules/postcss-loader/index.js!./layout.scss", function() {
     var newContent = require("!!../../node_modules/css-loader/index.js?importLoaders=1!../../node_modules/sass-loader/lib/loader.js!../../node_modules/postcss-loader/index.js!./layout.scss");
     if(typeof newContent === 'string') newContent = [[module.id, newContent, '']];
     update(newContent);
   });
 }
 // When the module is disposed, remove the <style> tags
 module.hot.dispose(function() { update(); });
}

/***/ }),

/***/ 58:
/***/ (function(module, exports, __webpack_require__) {

// style-loader: Adds some css to the DOM by adding a <style> tag

// load the styles
var content = __webpack_require__(104);
if(typeof content === 'string') content = [[module.i, content, '']];
if(content.locals) module.exports = content.locals;
// add the styles to the DOM
var update = __webpack_require__(1)("2390e51d", content, false);
// Hot Module Replacement
if(false) {
 // When the styles change, update the <style> tags
 if(!content.locals) {
   module.hot.accept("!!../../node_modules/css-loader/index.js?importLoaders=1!../../node_modules/sass-loader/lib/loader.js!../../node_modules/postcss-loader/index.js!./menu.scss", function() {
     var newContent = require("!!../../node_modules/css-loader/index.js?importLoaders=1!../../node_modules/sass-loader/lib/loader.js!../../node_modules/postcss-loader/index.js!./menu.scss");
     if(typeof newContent === 'string') newContent = [[module.id, newContent, '']];
     update(newContent);
   });
 }
 // When the module is disposed, remove the <style> tags
 module.hot.dispose(function() { update(); });
}

/***/ }),

/***/ 59:
/***/ (function(module, exports, __webpack_require__) {

// style-loader: Adds some css to the DOM by adding a <style> tag

// load the styles
var content = __webpack_require__(105);
if(typeof content === 'string') content = [[module.i, content, '']];
if(content.locals) module.exports = content.locals;
// add the styles to the DOM
var update = __webpack_require__(1)("2871d955", content, false);
// Hot Module Replacement
if(false) {
 // When the styles change, update the <style> tags
 if(!content.locals) {
   module.hot.accept("!!../../node_modules/css-loader/index.js?importLoaders=1!../../node_modules/sass-loader/lib/loader.js!../../node_modules/postcss-loader/index.js!./slider_text.scss", function() {
     var newContent = require("!!../../node_modules/css-loader/index.js?importLoaders=1!../../node_modules/sass-loader/lib/loader.js!../../node_modules/postcss-loader/index.js!./slider_text.scss");
     if(typeof newContent === 'string') newContent = [[module.id, newContent, '']];
     update(newContent);
   });
 }
 // When the module is disposed, remove the <style> tags
 module.hot.dispose(function() { update(); });
}

/***/ }),

/***/ 6:
/***/ (function(module, exports, __webpack_require__) {

var disposed = false
var Component = __webpack_require__(2)(
  /* script */
  __webpack_require__(86),
  /* template */
  __webpack_require__(118),
  /* styles */
  null,
  /* scopeId */
  null,
  /* moduleIdentifier (server only) */
  null
)
Component.options.__file = "G:\\workspace\\dozenx\\ui\\webpack\\app\\component\\button\\zwButton.vue"
if (Component.esModule && Object.keys(Component.esModule).some(function (key) {return key !== "default" && key.substr(0, 2) !== "__"})) {console.error("named exports are not supported in *.vue files.")}
if (Component.options.functional) {console.error("[vue-loader] zwButton.vue: functional components are not supported with templates, they should use render functions.")}

/* hot reload */
if (false) {(function () {
  var hotAPI = require("vue-hot-reload-api")
  hotAPI.install(require("vue"), false)
  if (!hotAPI.compatible) return
  module.hot.accept()
  if (!module.hot.data) {
    hotAPI.createRecord("data-v-b9a5e268", Component.options)
  } else {
    hotAPI.reload("data-v-b9a5e268", Component.options)
  }
  module.hot.dispose(function (data) {
    disposed = true
  })
})()}

module.exports = Component.exports


/***/ }),

/***/ 60:
/***/ (function(module, exports, __webpack_require__) {

// style-loader: Adds some css to the DOM by adding a <style> tag

// load the styles
var content = __webpack_require__(106);
if(typeof content === 'string') content = [[module.i, content, '']];
if(content.locals) module.exports = content.locals;
// add the styles to the DOM
var update = __webpack_require__(1)("2cbdd59f", content, false);
// Hot Module Replacement
if(false) {
 // When the styles change, update the <style> tags
 if(!content.locals) {
   module.hot.accept("!!../../node_modules/css-loader/index.js?importLoaders=1!../../node_modules/sass-loader/lib/loader.js!../../node_modules/postcss-loader/index.js!./tab.scss", function() {
     var newContent = require("!!../../node_modules/css-loader/index.js?importLoaders=1!../../node_modules/sass-loader/lib/loader.js!../../node_modules/postcss-loader/index.js!./tab.scss");
     if(typeof newContent === 'string') newContent = [[module.id, newContent, '']];
     update(newContent);
   });
 }
 // When the module is disposed, remove the <style> tags
 module.hot.dispose(function() { update(); });
}

/***/ }),

/***/ 61:
/***/ (function(module, exports, __webpack_require__) {

// style-loader: Adds some css to the DOM by adding a <style> tag

// load the styles
var content = __webpack_require__(107);
if(typeof content === 'string') content = [[module.i, content, '']];
if(content.locals) module.exports = content.locals;
// add the styles to the DOM
var update = __webpack_require__(1)("43ccacd0", content, false);
// Hot Module Replacement
if(false) {
 // When the styles change, update the <style> tags
 if(!content.locals) {
   module.hot.accept("!!../../node_modules/css-loader/index.js?importLoaders=1!../../node_modules/sass-loader/lib/loader.js!../../node_modules/postcss-loader/index.js!./table.scss", function() {
     var newContent = require("!!../../node_modules/css-loader/index.js?importLoaders=1!../../node_modules/sass-loader/lib/loader.js!../../node_modules/postcss-loader/index.js!./table.scss");
     if(typeof newContent === 'string') newContent = [[module.id, newContent, '']];
     update(newContent);
   });
 }
 // When the module is disposed, remove the <style> tags
 module.hot.dispose(function() { update(); });
}

/***/ }),

/***/ 62:
/***/ (function(module, exports, __webpack_require__) {

// style-loader: Adds some css to the DOM by adding a <style> tag

// load the styles
var content = __webpack_require__(108);
if(typeof content === 'string') content = [[module.i, content, '']];
if(content.locals) module.exports = content.locals;
// add the styles to the DOM
var update = __webpack_require__(1)("aeba587c", content, false);
// Hot Module Replacement
if(false) {
 // When the styles change, update the <style> tags
 if(!content.locals) {
   module.hot.accept("!!../../node_modules/css-loader/index.js?importLoaders=1!../../node_modules/sass-loader/lib/loader.js!../../node_modules/postcss-loader/index.js!./widget.scss", function() {
     var newContent = require("!!../../node_modules/css-loader/index.js?importLoaders=1!../../node_modules/sass-loader/lib/loader.js!../../node_modules/postcss-loader/index.js!./widget.scss");
     if(typeof newContent === 'string') newContent = [[module.id, newContent, '']];
     update(newContent);
   });
 }
 // When the module is disposed, remove the <style> tags
 module.hot.dispose(function() { update(); });
}

/***/ }),

/***/ 63:
/***/ (function(module, exports, __webpack_require__) {

"use strict";


// shim for using process in browser
var process = module.exports = {};

// cached from whatever global is present so that test runners that stub it
// don't break things.  But we need to wrap it in a try catch in case it is
// wrapped in strict mode code which doesn't define any globals.  It's inside a
// function because try/catches deoptimize in certain engines.

var cachedSetTimeout;
var cachedClearTimeout;

function defaultSetTimout() {
    throw new Error('setTimeout has not been defined');
}
function defaultClearTimeout() {
    throw new Error('clearTimeout has not been defined');
}
(function () {
    try {
        if (typeof setTimeout === 'function') {
            cachedSetTimeout = setTimeout;
        } else {
            cachedSetTimeout = defaultSetTimout;
        }
    } catch (e) {
        cachedSetTimeout = defaultSetTimout;
    }
    try {
        if (typeof clearTimeout === 'function') {
            cachedClearTimeout = clearTimeout;
        } else {
            cachedClearTimeout = defaultClearTimeout;
        }
    } catch (e) {
        cachedClearTimeout = defaultClearTimeout;
    }
})();
function runTimeout(fun) {
    if (cachedSetTimeout === setTimeout) {
        //normal enviroments in sane situations
        return setTimeout(fun, 0);
    }
    // if setTimeout wasn't available but was latter defined
    if ((cachedSetTimeout === defaultSetTimout || !cachedSetTimeout) && setTimeout) {
        cachedSetTimeout = setTimeout;
        return setTimeout(fun, 0);
    }
    try {
        // when when somebody has screwed with setTimeout but no I.E. maddness
        return cachedSetTimeout(fun, 0);
    } catch (e) {
        try {
            // When we are in I.E. but the script has been evaled so I.E. doesn't trust the global object when called normally
            return cachedSetTimeout.call(null, fun, 0);
        } catch (e) {
            // same as above but when it's a version of I.E. that must have the global object for 'this', hopfully our context correct otherwise it will throw a global error
            return cachedSetTimeout.call(this, fun, 0);
        }
    }
}
function runClearTimeout(marker) {
    if (cachedClearTimeout === clearTimeout) {
        //normal enviroments in sane situations
        return clearTimeout(marker);
    }
    // if clearTimeout wasn't available but was latter defined
    if ((cachedClearTimeout === defaultClearTimeout || !cachedClearTimeout) && clearTimeout) {
        cachedClearTimeout = clearTimeout;
        return clearTimeout(marker);
    }
    try {
        // when when somebody has screwed with setTimeout but no I.E. maddness
        return cachedClearTimeout(marker);
    } catch (e) {
        try {
            // When we are in I.E. but the script has been evaled so I.E. doesn't  trust the global object when called normally
            return cachedClearTimeout.call(null, marker);
        } catch (e) {
            // same as above but when it's a version of I.E. that must have the global object for 'this', hopfully our context correct otherwise it will throw a global error.
            // Some versions of I.E. have different rules for clearTimeout vs setTimeout
            return cachedClearTimeout.call(this, marker);
        }
    }
}
var queue = [];
var draining = false;
var currentQueue;
var queueIndex = -1;

function cleanUpNextTick() {
    if (!draining || !currentQueue) {
        return;
    }
    draining = false;
    if (currentQueue.length) {
        queue = currentQueue.concat(queue);
    } else {
        queueIndex = -1;
    }
    if (queue.length) {
        drainQueue();
    }
}

function drainQueue() {
    if (draining) {
        return;
    }
    var timeout = runTimeout(cleanUpNextTick);
    draining = true;

    var len = queue.length;
    while (len) {
        currentQueue = queue;
        queue = [];
        while (++queueIndex < len) {
            if (currentQueue) {
                currentQueue[queueIndex].run();
            }
        }
        queueIndex = -1;
        len = queue.length;
    }
    currentQueue = null;
    draining = false;
    runClearTimeout(timeout);
}

process.nextTick = function (fun) {
    var args = new Array(arguments.length - 1);
    if (arguments.length > 1) {
        for (var i = 1; i < arguments.length; i++) {
            args[i - 1] = arguments[i];
        }
    }
    queue.push(new Item(fun, args));
    if (queue.length === 1 && !draining) {
        runTimeout(drainQueue);
    }
};

// v8 likes predictible objects
function Item(fun, array) {
    this.fun = fun;
    this.array = array;
}
Item.prototype.run = function () {
    this.fun.apply(null, this.array);
};
process.title = 'browser';
process.browser = true;
process.env = {};
process.argv = [];
process.version = ''; // empty string to avoid regexp issues
process.versions = {};

function noop() {}

process.on = noop;
process.addListener = noop;
process.once = noop;
process.off = noop;
process.removeListener = noop;
process.removeAllListeners = noop;
process.emit = noop;
process.prependListener = noop;
process.prependOnceListener = noop;

process.listeners = function (name) {
    return [];
};

process.binding = function (name) {
    throw new Error('process.binding is not supported');
};

process.cwd = function () {
    return '/';
};
process.chdir = function (dir) {
    throw new Error('process.chdir is not supported');
};
process.umask = function () {
    return 0;
};

/***/ }),

/***/ 64:
/***/ (function(module, exports, __webpack_require__) {

var disposed = false
function injectStyle (ssrContext) {
  if (disposed) return
  __webpack_require__(281)
}
var Component = __webpack_require__(2)(
  /* script */
  __webpack_require__(174),
  /* template */
  __webpack_require__(248),
  /* styles */
  injectStyle,
  /* scopeId */
  "data-v-7cd356fc",
  /* moduleIdentifier (server only) */
  null
)
Component.options.__file = "G:\\workspace\\dozenx\\ui\\webpack\\app\\module\\phone\\bottomBar.vue"
if (Component.esModule && Object.keys(Component.esModule).some(function (key) {return key !== "default" && key.substr(0, 2) !== "__"})) {console.error("named exports are not supported in *.vue files.")}
if (Component.options.functional) {console.error("[vue-loader] bottomBar.vue: functional components are not supported with templates, they should use render functions.")}

/* hot reload */
if (false) {(function () {
  var hotAPI = require("vue-hot-reload-api")
  hotAPI.install(require("vue"), false)
  if (!hotAPI.compatible) return
  module.hot.accept()
  if (!module.hot.data) {
    hotAPI.createRecord("data-v-7cd356fc", Component.options)
  } else {
    hotAPI.reload("data-v-7cd356fc", Component.options)
  }
  module.hot.dispose(function (data) {
    disposed = true
  })
})()}

module.exports = Component.exports


/***/ }),

/***/ 673:
/***/ (function(module, exports, __webpack_require__) {

"use strict";


__webpack_require__(53);

__webpack_require__(52);

__webpack_require__(58);

__webpack_require__(54);

__webpack_require__(57);

__webpack_require__(55);

__webpack_require__(60);

__webpack_require__(51);

__webpack_require__(61);

__webpack_require__(56);

__webpack_require__(59);

__webpack_require__(62);

var _vue = __webpack_require__(43);

var _vue2 = _interopRequireDefault(_vue);

var _userInfo = __webpack_require__(123);

var _userInfo2 = _interopRequireDefault(_userInfo);

function _interopRequireDefault(obj) {
  return obj && obj.__esModule ? obj : { default: obj };
}

//初始化一个路由

//import app from "./vueTest"
//const app= require("./head/head.vue");

//document.write("It works1.");
//document.write(require("./content.js"));

/*
const PATH = require("path");
console.log("path:"+PATH)
cats = require('./cats.js');
console.log("define cats");*/
//require("!style-loader!css-loader!./css/style.css");
//require("!style-loader!css-loader!./css/sass.scss");
new _vue2.default({
  el: '#head',
  render: function render(h) {
    return h(_userInfo2.default);
  }
});
//这个作为主页面

/***/ }),

/***/ 70:
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var _typeof = typeof Symbol === "function" && typeof Symbol.iterator === "symbol" ? function (obj) { return typeof obj; } : function (obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj; };

var g;

// This works in non-strict mode
g = function () {
	return this;
}();

try {
	// This works if eval is allowed (see CSP)
	g = g || Function("return this")() || (1, eval)("this");
} catch (e) {
	// This works if the window reference is available
	if ((typeof window === "undefined" ? "undefined" : _typeof(window)) === "object") g = window;
}

// g can still be undefined, but nothing to do about it...
// We return undefined, instead of nothing here, so it's
// easier to handle this case. if(!global) { ...}

module.exports = g;

/***/ }),

/***/ 86:
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
    value: true
});

var _props;

function _defineProperty(obj, key, value) { if (key in obj) { Object.defineProperty(obj, key, { value: value, enumerable: true, configurable: true, writable: true }); } else { obj[key] = value; } return obj; }

//
//
//
//
//

exports.default = {
    name: 'zwButton',
    components: {},
    props: (_props = {
        disabled: Boolean,
        text: String,
        type: String,
        title: String,
        icon: String
    }, _defineProperty(_props, "text", String), _defineProperty(_props, "click", Function), _defineProperty(_props, "shape", String), _defineProperty(_props, "sizeNum", String), _defineProperty(_props, "diabled", Boolean), _defineProperty(_props, "loading", { //表示他是一个loading 组件  正真表示他是否在loading 是loading_state loading_state中 按钮不可点击 并且有图标的话 图标会变成旋转
        type: Boolean,
        default: false
    }), _defineProperty(_props, "start_loading_state", { //表示他是一个loading 组件  正真表示他是否在loading 是loading_state loading_state中 按钮不可点击 并且有图标的话 图标会变成旋转
        type: Boolean,
        default: false
    }), _defineProperty(_props, "loading_delay", {
        type: Number,
        default: 0
    }), _props),
    data: function data() {
        return {
            intervalHandler: 0,
            hasText: true,
            coolDown: 0, //冷却时间
            loading_state: false //当前等待的状态
        };
    },

    computed: {

        size: function size() {},
        iconShow: function iconShow() {
            if (this.icon || this.loading_state) {
                return true;
            }
            return false;
        },
        iconclasses: function iconclasses() {
            var classStr = "zw-btn-icon ";
            if (this.icon == "search") {
                classStr = " fa fa-search";
            };
            if (this.icon == "down") {
                classStr = " fa fa-chevron-down";
            };
            if (this.icon == "plus") {
                classStr = " fa fa-plus";
            };
            if (this.icon == "refresh") {
                classStr = " fa fa-refresh";
            };
            if (this.loading_state == true) {
                classStr = "fa fa-spinner";
                classStr = "";
            }
            return classStr;
        },
        isDisabled: function isDisabled() {
            if (this.loading_state == true) {
                return true;
            }
            if (this.disabled == true) {
                return true;
            }

            return false;
        },
        classes: function classes() {
            var classStr = "";
            if (this.type == 'primary') {
                classStr = " btn-primary";
            } else if (this.type == 'dashed') {
                classStr = " btn-dashed";
            } else if (this.type == 'danger') {
                classStr = " btn-danger";
            } else if (this.type == 'blue') {
                classStr = " btn-border-blue";
            } else if (this.type == 'red') {
                classStr = " btn-border-red";
            } else if (this.type == 'yellow') {
                classStr = " btn-border-yellow btn-bg-yellow ";
            } else {
                classStr = " btn-default";
            }

            if (this.shape == "circle") {
                classStr += " circle";
            }
            if (this.disabled) {
                this.disabled = true;
                classStr += " disabled";
            }

            if (this.loading_state == true) {
                classStr += " btn-loading";
            }

            if (this.sizeNum == "large") {
                classStr += " zw-btn-large";
            } else if (this.sizeNum == "small") {
                classStr += " zw-btn-small";
            }
            return classStr;
        }
    },
    mounted: function mounted() {
        if (!StringUtil.isBlank(this.$refs.slot.innerHTML)) {

            this.hasText = true;
        } else {
            this.hasText = false;
        }
        this.loading_state = this.start_loading_state;

        /*  this.judgeNeedWait();
        if(this.loading==true && this.loading_delay>0){
             this.loading_state =true;
             var that = this;
                setTimeout(function(){
                that.loading_state=false
              },this.loading_delay*1000);
            }*/
    },

    watch: {
        loading: function loading(curVal, oldVal) {
            //利用loading_state做disable 和 icon 和样式的控制 当loading_state为true的时候 显示为等待的样子 loading_state会追踪传入的loading 状态
            //所以当loading_state 改成false的时候 loading 还是true  当再次按下的时候 loading 从true 到true 没有发生变化 所以第二次按下去就不会变化

            this.loading_state = curVal;
            var that = this;
            this.judgeNeedWait();
            /* if(curVal==true && this.loading_delay>0){
              this.coolDown=this.loading_delay;
             setInterval(this.coolDownFn,1000);
                 setTimeout(function(){
                     that.loading_state=false
                   },this.loading_delay*1000);
             }*/
        }
    },
    methods: {
        coolDownFn: function coolDownFn() {
            this.coolDown--;
            console.log("--");
            if (this.coolDown <= 0) {

                this.loading_state = false;
                window.clearInterval(this.intervalHandler);
            }
        },
        judgeNeedWait: function judgeNeedWait() {
            var that = this;
            if (this.loading_state && this.loading_delay > 0) {

                this.loading_state = true;

                this.coolDown = this.loading_delay;
                this.intervalHandler = setInterval(this.coolDownFn, 1000);
                setTimeout(function () {
                    that.loading_state = false;
                }, this.loading_delay * 1000);
            }
        },
        diabledForAWhile: function diabledForAWhile() {
            console.log("diabledForAWhile");
            this.$emit("clickFn");
            console.log("$emit");
            if (this.loading && !this.loading_state) {
                this.loading_state = true;
            }

            this.judgeNeedWait();
        }
    }
};

/***/ }),

/***/ 87:
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
  value: true
});
//
//
//
//
//

exports.default = {
  name: 'zwForm',
  components: {},
  props: ["layout"],
  data: function data() {
    return {};
  },

  computed: {
    getComputeClass: function getComputeClass() {
      var cls = "zw-form ";
      if (this.layout == "inline") {
        cls += " zw-form-inline";
      }
      return cls;
    }
  },
  mounted: function mounted() {},

  methods: {},

  events: {}
};

/***/ }),

/***/ 88:
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
  value: true
});
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//

exports.default = {
  name: 'zwFormItem',
  components: {},
  props: ["label"],
  data: function data() {
    return {};
  },

  computed: {

    controlWrapper: function controlWrapper() {
      var cls = "zw-form-item-control-wrapper ";
      if (this.label) {
        cls += " zw-col-sm-16 zw-col-xs-16 zw-col-lg-16";
      }
      return cls;
    },
    labelWrapper: function labelWrapper() {
      var cls = " zw-form-item-label ";
      if (this.label) {
        cls += " zw-col-sm-6 zw-col-xs-8 zw-col-lg-8";
      }
      return cls;
    }
  },
  mounted: function mounted() {},

  methods: {},

  events: {}
};

/***/ }),

/***/ 89:
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
  value: true
});

var _zwIcon = __webpack_require__(3);

var _zwIcon2 = _interopRequireDefault(_zwIcon);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

exports.default = {
  name: 'zwInput',
  components: {
    zwIcon: _zwIcon2.default
  },
  props: { value: { type: String }, icon: { type: String }, type: { type: String, default: "text" }, placeholder: { type: String, default: "" }, clear: { type: Boolean, default: false }, id: { type: String }, name: { type: String }, defaultValue: { type: String }, lineHeight: { type: String } },
  data: function data() {
    return {
      ok: false,
      textvalue: "",
      closeSee: false
    };
  },

  computed: {
    getComputedHeight: function getComputedHeight() {
      if (this.lineHeight) {
        return "height:" + this.lineHeight + ";line-height:" + this.lineHeight;
      }
      return "";
    },
    /*canClear:function(){
        if(this.clear ){console.log("canclear");
           // if(this.$refs.text!=null && this.$refs.text.value!=null && this.$refs.text.value.length>0){
           console.log("canclear"+this.textvalue);
             if(this.textvalue!=null && this.textvalue.length>0){console.log(this.textvalue);
                return true;
            }
        }
        return false;
     },*/
    getType: function getType() {
      var type = "text";
      if (this.type == "password") {
        type = "password";
      }
      return type;
    },
    isGroup: function isGroup() {
      return this.$slots.prepend != null;
    },

    groupWrapperClasses: function groupWrapperClasses() {

      var wrapperClasses = "";
      if (this.$slots.prepend != null || this.$slots.append != null) {
        wrapperClasses += " zw-input-group-wrapper  ";
      }
      if (this.$slots.suffix != null || this.$slots.prefix != null || this.clear) {
        wrapperClasses += " ";
      }

      return wrapperClasses;
    },
    wrapperClasses: function wrapperClasses() {
      var wrapperClasses = "zw-input-wrapper";
      if (this.$slots.prepend != null || this.$slots.append != null) {
        wrapperClasses += " zw-input-group  ";
      }
      if (this.$slots.suffix != null || this.$slots.prefix != null || this.clear) {
        wrapperClasses += " zw-input-affix";
      }

      return wrapperClasses;
    }
  },
  mounted: function mounted() {
    if (this.defaultValue) {
      this.textvalue = this.defaultValue;
    }
    if (this.clear && this.textvalue != null && this.textvalue.length > 0) {
      this.closeSee = true;
    } else {
      this.closeSee = false;
    }
  },

  methods: {
    handleInput: function handleInput(event) {
      var value = event.target.value;
      this.$emit('input', value); //触发 input 事件，并传入新值
    },

    onchange: function onchange() {
      console.log("onchange");
      this.$emit('onchange', this.textvalue);
    },
    clearContent: function clearContent() {
      this.textvalue = "";
    }
  },
  watch: {
    defaultValue: function (_defaultValue) {
      function defaultValue(_x, _x2) {
        return _defaultValue.apply(this, arguments);
      }

      defaultValue.toString = function () {
        return _defaultValue.toString();
      };

      return defaultValue;
    }(function (curVal, oldVal) {
      this.textvalue = defaultValue;
    }),
    textvalue: function textvalue(curVal, oldVal) {
      if (this.clear && curVal != null && curVal.length > 0) {
        this.closeSee = true;
      } else {
        this.closeSee = false;
      }
    }
  },

  events: {}
}; //
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//

/***/ }),

/***/ 90:
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
    value: true
});
//
//
//
//

exports.default = {
    name: 'zwIcon',
    components: {},
    props: {
        disabled: Boolean,
        text: String,
        type: String,
        title: String
        // className:String,
    },
    data: function data() {
        return {};
    },

    computed: {
        classes: function classes() {
            if (this.type == "windows") {
                return "zw-icon zw-icon-window fa fa-th-large";
            }
            if (this.type == "arrow-right") {
                return "zw-icon  zw-icon-arrow  fa fa-angle-right";
            }
            if (this.type == "arrow-down") {
                return "zw-icon  zw-icon-arrow fa fa-angle-down";
            }
            if (this.type == "home") {
                return "zw-icon  zw-icon-arrow fa fa-home";
            }
            if (this.type == "history") {
                return "zw-icon  zw-icon-arrow fa fa-history";
            }
            if (this.type == "plus") {
                return "zw-icon  fa fa-plus";
            }
            if (this.type == "shopping-cart") {
                return "zw-icon  zw-icon-arrow fa fa-shopping-cart ";
            }
            if (this.type == "suitcase") {
                return "zw-icon  zw-icon-arrow fa fa-suitcase ";
            }
            if (this.type == "user") {
                return "zw-icon  zw-icon-user fa fa-user";
            }
            if (this.type == "star") {
                return "zw-icon  zw-icon-star fa fa-star";
            }
            if (this.type == "lock") {
                return "zw-icon  zw-icon-user fa fa-lock";
            }
            if (this.type == "close") {
                return "zw-icon  zw-icon-user fa  fa-times";
            }

            if (this.type == "swimmer") {
                return "zw-icon  zw-icon-user fa  fa-swimmer";
            }
            if (this.type == "toggle-off") {
                return "zw-icon fa fa-toggle-off";
            }
            if (this.type == "refresh") {
                return "zw-icon fa fa-refresh";
            }if (this.type == "search") {
                return "zw-icon fa fa-search";
            }

            if (this.type == "toggle-on") {
                return "zw-icon fa fa-toggle-on";
            }
            if (this.type == "sign-out") {
                return "zw-icon  zw-icon-user fa  fa-sign-out";
            }
            if (this.type && this.type.indexOf("fa") != -1) {
                return this.type;
            }
            return "zw-icon   fas fa-" + this.type;
        }
    },
    mounted: function mounted() {},

    methods: {}
};

/***/ }),

/***/ 91:
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
    value: true
});
//
//
//
//

exports.default = {
    name: 'zwCol',
    components: {},
    props: {
        disabled: Boolean,
        text: String,
        type: String,
        title: String,
        span: String,
        gutter: String,
        className: String
    },
    data: function data() {
        return {};
    },

    computed: {
        styles: function styles() {
            console.log("gutter" + this.gutter);
            if (this.gutter > 0) {
                return "padding-left:" + gutter + ",padding-right:" + gutter;
            }
        },
        classes: function classes() {

            return "zw-col-" + this.span;
        }
    },
    mounted: function mounted() {},

    methods: {}
};

/***/ }),

/***/ 92:
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
    value: true
});
//
//
//
//
//
//

exports.default = {
    name: 'zwRow',
    components: {},
    props: {
        disabled: Boolean,
        text: String,
        type: String,
        title: String,
        className: String,
        gutter: String
    },
    data: function data() {
        return {};
    },

    computed: {
        styles: function styles() {
            if (this.gutter > 0) {
                return "padding-left:" + gutter + ",padding-right:" + gutter;
            }
        }
    },
    mounted: function mounted() {
        // alert();
        if (this.gutter > 0) {
            for (var i = 0; i < this.$refs.slot.childNodes.length; i++) {
                this.$refs.slot.childNodes[i].style = {};
                if (this.$refs.slot.childNodes[i].style) {
                    this.$refs.slot.childNodes[i].style.paddingLeft = 0.5 * this.gutter;
                    this.$refs.slot.childNodes[i].style.paddingRight = 0.5 * this.gutter;
                }
            }
        }
    },

    methods: {}
};

/***/ }),

/***/ 93:
/***/ (function(module, exports, __webpack_require__) {

"use strict";


/**
 * Translates the list format produced by css-loader into something
 * easier to manipulate.
 */
module.exports = function listToStyles(parentId, list) {
  var styles = [];
  var newStyles = {};
  for (var i = 0; i < list.length; i++) {
    var item = list[i];
    var id = item[0];
    var css = item[1];
    var media = item[2];
    var sourceMap = item[3];
    var part = {
      id: parentId + ':' + i,
      css: css,
      media: media,
      sourceMap: sourceMap
    };
    if (!newStyles[id]) {
      styles.push(newStyles[id] = { id: id, parts: [part] });
    } else {
      newStyles[id].parts.push(part);
    }
  }
  return styles;
};

/***/ }),

/***/ 94:
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__(0)(undefined);
// imports


// module
exports.push([module.i, "\n.zw-form-inline .zw-form-item {\n  display: inline-block;\n  margin-right: 16px;\n  margin-bottom: 0;\n}\n.zw-form-item {\n  font-family: \"Chinese Quote\", -apple-system, BlinkMacSystemFont, \"Segoe UI\", \"PingFang SC\", \"Hiragino Sans GB\", \"Microsoft YaHei\", \"Helvetica Neue\", Helvetica, Arial, sans-serif, \"Apple Color Emoji\", \"Segoe UI Emoji\", \"Segoe UI Symbol\";\n  font-size: 14px;\n  font-variant: tabular-nums;\n  line-height: 1.5;\n  color: rgba(0, 0, 0, 0.65);\n  box-sizing: border-box;\n  margin: 0;\n  padding: 0;\n  list-style: none;\n  margin-bottom: 24px;\n  vertical-align: top;\n}\n.zw-form-inline .zw-form-item {\n  display: inline-block;\n  margin-right: 16px;\n  margin-bottom: 0;\n}\n.zw-form-item-label {\n  text-align: right;\n  vertical-align: middle;\n  line-height: 39.9999px;\n  display: inline-block;\n  overflow: hidden;\n  white-space: nowrap;\n}\n.zw-form-item-control {\n  line-height: 39.9999px;\n  position: relative;\n  zoom: 1;\n}\n", ""]);

// exports


/***/ }),

/***/ 95:
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__(0)(undefined);
// imports


// module
exports.push([module.i, "\n.zw-form {\n  font-family: \"Chinese Quote\", -apple-system, BlinkMacSystemFont, \"Segoe UI\", \"PingFang SC\", \"Hiragino Sans GB\", \"Microsoft YaHei\", \"Helvetica Neue\", Helvetica, Arial, sans-serif, \"Apple Color Emoji\", \"Segoe UI Emoji\", \"Segoe UI Symbol\";\n  font-size: 14px;\n  font-variant: tabular-nums;\n  line-height: 1.5;\n  color: rgba(0, 0, 0, 0.65);\n  box-sizing: border-box;\n  margin: 0;\n  padding: 0;\n  list-style: none;\n}\n.zw-form {\n  font-family: \"Chinese Quote\", -apple-system, BlinkMacSystemFont, \"Segoe UI\", \"PingFang SC\", \"Hiragino Sans GB\", \"Microsoft YaHei\", \"Helvetica Neue\", Helvetica, Arial, sans-serif, \"Apple Color Emoji\", \"Segoe UI Emoji\", \"Segoe UI Symbol\";\n  font-size: 14px;\n  font-variant: tabular-nums;\n  line-height: 1.5;\n  color: rgba(0, 0, 0, 0.65);\n  box-sizing: border-box;\n  margin: 0;\n  padding: 0;\n  list-style: none;\n}\n", ""]);

// exports


/***/ }),

/***/ 96:
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__(0)(undefined);
// imports


// module
exports.push([module.i, "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n", ""]);

// exports


/***/ }),

/***/ 97:
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__(0)(undefined);
// imports


// module
exports.push([module.i, "/*color*/\n/**font-size**/\nh1, h2, p, span {\n  word-wrap: break-word; }\n\n@CHARSET \"UTF-8\";\n.zw-app-bottom-dialog {\n  position: absolute;\n  bottom: 0;\n  padding: 20px;\n  width: 100%;\n  height: 203px;\n  background: #fff;\n  color: #333;\n  box-sizing: border-box; }\n  .zw-app-bottom-dialog .mainTitle {\n    float: left;\n    font-size: 16px;\n    line-height: 36px;\n    font-weight: bold; }\n  .zw-app-bottom-dialog .subTitle {\n    float: right;\n    font-size: 12px;\n    line-height: 36px; }\n  .zw-app-bottom-dialog .title {\n    float: left;\n    font-size: 12px;\n    width: 70px; }\n  .zw-app-bottom-dialog .content {\n    margin-left: 70px;\n    font-size: 12px; }\n  .zw-app-bottom-dialog .item {\n    position: relative;\n    line-height: 30px;\n    overflow: hidden; }\n  .zw-app-bottom-dialog .buttons {\n    text-align: center; }\n  .zw-app-bottom-dialog .buttons a {\n    display: inline-block;\n    padding: 0;\n    width: 100px;\n    height: 34px;\n    line-height: 34px;\n    border: 1px solid #4A90E2;\n    color: #4A90E2;\n    background: #fff;\n    text-decoration: none; }\n  .zw-app-bottom-dialog .buttons .error {\n    border-color: #F05C5C;\n    color: #F05C5C; }\n", ""]);

// exports


/***/ }),

/***/ 98:
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__(0)(undefined);
// imports


// module
exports.push([module.i, "/*color*/\n/**font-size**/\nh1, h2, p, span {\n  word-wrap: break-word; }\n\n@CHARSET \"UTF-8\";\nbutton {\n  border: 1px solid transparent;\n  /*\theight: inherit;*/\n  line-height: inherit;\n  /*width: 100%;*/\n  /*padding: 0;*/\n  background: #fff;\n  color: #ffffff;\n  border-radius: 2%;\n  /*box-shadow: 0 2px 5px rgba(0, 0, 0, 0.26);*/\n  border-radius: 5px; }\n\n[role=\"button\"] {\n  cursor: pointer; }\n\nbutton,\ninput,\nselect,\ntextarea {\n  /*font-size: $ft-nm * 1.1;*/\n  outline: 0;\n  /*border-radius: 1%;*/\n  border: 1px solid #D4D4D4;\n  padding: 0; }\n\n.btn {\n  display: inline-block;\n  margin-bottom: 0px;\n  line-height: 28px;\n  height: 28px;\n  min-height: 28px;\n  font-size: 14px;\n  font-weight: 400;\n  text-align: center;\n  white-space: nowrap;\n  vertical-align: middle;\n  cursor: pointer;\n  -moz-user-select: none;\n  background-image: none;\n  border: 1px solid transparent;\n  border-radius: 4px;\n  /*  margin:5px 5px 5px;*/\n  font-size: 14px;\n  height: 32.2px;\n  line-height: 32.2px;\n  min-height: 32.2px;\n  padding: 1.4px 7px;\n  height: 32px;\n  color: #000; }\n  .btn span, .btn i {\n    padding: 1.68px 2.1px;\n    line-height: 23.8px;\n    height: 23.8px;\n    min-height: 23.8px; }\n\n.btn-border-blue {\n  border: 1px solid #4A90E2;\n  color: #4A90E2;\n  border-radius: 0px; }\n\n.btn-border-red {\n  border: 1px solid #F05C5C;\n  color: #F05C5C;\n  border-radius: 0px; }\n\n.btn-border-yellow {\n  border: 1px solid #fcd70f;\n  color: #F05C5C;\n  background-color: #fcd70f; }\n\n.btn.focus, .btn:focus, .btn:hover {\n  color: #333;\n  text-decoration: none; }\n\n/**default**/\n.btn-default {\n  color: #8d8d8d;\n  /*rgba(0,0,0,.65)*/\n  background-color: #fff;\n  border-color: #d9d9d9; }\n\n.btn-default.focus, .btn-default:focus {\n  color: #8d8d8d;\n  background-color: #fff;\n  border-color: #d9d9d9; }\n\n.btn-default:hover {\n  color: #333;\n  background-color: #fff;\n  border-color: #adadad; }\n\n/**primary**/\n.btn-primary {\n  color: #fff;\n  background-color: #1ab394;\n  border-color: #1ab394; }\n\n.btn-primary:hover {\n  color: #fff;\n  background-color: #aab2b0;\n  border-color: #aab2b0; }\n\n.btn-primary:focus {\n  color: #fff;\n  background-color: #0c8365;\n  border-color: #0c8365; }\n\n/**dashed**/\n.btn-dashed {\n  color: rgba(0, 0, 0, 0.65);\n  background-color: #fff;\n  border-color: #d9d9d9;\n  border-style: dashed; }\n\n.btn-dashed:hover {\n  color: rgba(0, 0, 0, 0.65);\n  background-color: #fff;\n  border-color: #49a9ee; }\n\n.btn-dashed:focus {\n  color: rgba(0, 0, 0, 0.65);\n  background-color: #fff;\n  border-color: #49a9ee; }\n\n/**danger**/\n.btn-danger {\n  color: #f04134;\n  background-color: #fff;\n  border-color: #d9d9d9; }\n\n.btn-danger:hover {\n  color: #fff;\n  background-color: #f04134;\n  border-color: #aab2b0; }\n\n.btn-danger:focus {\n  color: #fff;\n  background-color: #f04134;\n  border-color: #0c8365; }\n\n.circle {\n  border-radius: 25px;\n  width: 42px;\n  height: 42px;\n  padding: 0 0; }\n\n.zw-btn-large {\n  font-size: 16.8px;\n  height: 38.64px;\n  line-height: 38.64px;\n  min-height: 38.64px;\n  padding: 1.68px 8.4px; }\n  .zw-btn-large span, .zw-btn-large i {\n    padding: 2.016px 2.52px;\n    line-height: 28.56px;\n    height: 28.56px;\n    min-height: 28.56px; }\n\n.zw-btn-small {\n  font-size: 9.8px;\n  height: 22.54px;\n  line-height: 22.54px;\n  min-height: 22.54px;\n  padding: 0.98px 4.9px; }\n  .zw-btn-small span, .zw-btn-small i {\n    padding: 1.176px 1.47px;\n    line-height: 16.66px;\n    height: 16.66px;\n    min-height: 16.66px; }\n\n/*.btn-default,.btn-default:hover,.btn-default:focus {\r\n    background-color: #FFF;\r\n    border-color: #000;\r\n    color: #FFFFFF;\r\n}*/\n.zw-btn-text {\n  margin-left: 5px;\n  margin-right: 5px; }\n\n.zw-btn-group .btn {\n  border-radius: 0px;\n  margin-top: 0px;\n  margin-bottom: 0px;\n  margin-left: -3px;\n  margin-right: -2px; }\n  .zw-btn-group .btn span {\n    vertical-align: center; }\n  .zw-btn-group .btn i {\n    vertical-align: center; }\n\n.btn[disabled] {\n  border-color: #d9d9d9;\n  color: rgba(0, 0, 0, 0.25);\n  background: #f7f7f7;\n  cursor: not-allowed; }\n\n.btn-loading .fa-spinner {\n  animation: mymove 2s   linear   infinite;\n  -webkit-animation: mymove 2s  linear   infinite;\n  /*Safari and Chrome*/\n  /*transition: all 2s;*/ }\n\n@-webkit-keyframes mymove {\n  from {\n    -webkit-transform: rotate(0);\n    transform: rotate(0); }\n  to {\n    -webkit-transform: rotate(360deg);\n    transform: rotate(360deg); } }\n\n@keyframes mymove {\n  from {\n    -webkit-transform: rotate(0);\n    transform: rotate(0); }\n  to {\n    -webkit-transform: rotate(360deg);\n    transform: rotate(360deg); } }\n", ""]);

// exports


/***/ }),

/***/ 99:
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__(0)(undefined);
// imports


// module
exports.push([module.i, "@charset \"UTF-8\";\n/*color*/\n/*color*/\n/**font-size**/\nh1, h2, p, span {\n  word-wrap: break-word; }\n\n@CHARSET \"UTF-8\";\nhtml {\n  font-size: 14px;\n  background-color: #eee; }\n\nbody {\n  margin: 0px;\n  padding: 0px; }\n\n.phone-artical img {\n  width: 100%;\n  height: 100%;\n  display: block; }\n\nul, li {\n  margin: 0px;\n  padding: 0px; }\n\n.clearfix {\n  display: block; }\n\np {\n  font-size: 14px;\n  line-height: 21px;\n  /* height:$size*2;*/\n  /*  min-height: $size*2.5;\r\n      max-height: $size*2.5;*/\n  box-sizing: border-box;\n  -moz-box-sizing: border-box;\n  /* Firefox */\n  -webkit-box-sizing: border-box;\n  /* Safari */\n  /*  position: relative;\r\n        border-collapse: separate;*/ }\n\nh1 {\n  font-size: 25px;\n  line-height: 37.5px;\n  /* height:$size*2;*/\n  /*  min-height: $size*2.5;\r\n      max-height: $size*2.5;*/\n  box-sizing: border-box;\n  -moz-box-sizing: border-box;\n  /* Firefox */\n  -webkit-box-sizing: border-box;\n  /* Safari */\n  /*  position: relative;\r\n        border-collapse: separate;*/ }\n\nh2 {\n  font-size: 21px;\n  line-height: 31.5px;\n  /* height:$size*2;*/\n  /*  min-height: $size*2.5;\r\n      max-height: $size*2.5;*/\n  box-sizing: border-box;\n  -moz-box-sizing: border-box;\n  /* Firefox */\n  -webkit-box-sizing: border-box;\n  /* Safari */\n  /*  position: relative;\r\n        border-collapse: separate;*/ }\n\nh3 {\n  font-size: 18px;\n  line-height: 27px;\n  /* height:$size*2;*/\n  /*  min-height: $size*2.5;\r\n      max-height: $size*2.5;*/\n  box-sizing: border-box;\n  -moz-box-sizing: border-box;\n  /* Firefox */\n  -webkit-box-sizing: border-box;\n  /* Safari */\n  /*  position: relative;\r\n        border-collapse: separate;*/ }\n\nbody {\n  font-size: 14px;\n  background-color: #fff;\n  font-family: \"Helvetica Neue\", \"Hiragino Sans GB\", \"Microsoft YaHei\", \"\\9ED1\\4F53\", Helvetica, Arial, sans-serif; }\n\n.bg-black {\n  background-color: #2E3E4E; }\n\n.bg-blue {\n  background-color: #00a0e9; }\n\n.bg-blue1 {\n  background: #2C8FFF; }\n\n.bg-white {\n  background-color: #fff; }\n\n.bg-sm-gray {\n  background-color: #f7f1f1; }\n\n.clearfix:after {\n  visibility: hidden;\n  display: block;\n  font-size: 0;\n  content: \" \";\n  clear: both;\n  height: 0; }\n\nul {\n  padding: 0 0; }\n\n.fix-bottom {\n  position: fixed !important;\n  bottom: 0 !important; }\n\n.fix-top {\n  position: fixed !important;\n  top: 0 !important; }\n\n.v-center {\n  display: -webkit-flex;\n  display: -ms-flexbox;\n  display: flex;\n  -webkit-align-items: center;\n  -ms-flex-align: center;\n  align-items: center; }\n\n.middle {\n  -webkit-justify-content: center;\n  -ms-flex-pack: center;\n  justify-content: center;\n  -webkit-align-items: center;\n  -ms-flex-align: center;\n  align-items: center; }\n\n.center {\n  text-align: center; }\n\n.ui-flex.center {\n  -webkit-justify-content: center;\n  -ms-flex-pack: center;\n  justify-content: center;\n  -webkit-align-items: center;\n  -ms-flex-align: center;\n  align-items: center; }\n\n.mask {\n  position: fixed;\n  top: 0px;\n  right: 0px;\n  bottom: 0px;\n  left: 0px;\n  background-color: #000;\n  z-index: 999;\n  display: none;\n  background-color: #000000;\n  filter: alpha(opacity=50);\n  /* IE 透明度20% */\n  -moz-opacity: 0.5;\n  /* Moz + FF 透明度20% */\n  opacity: 0.5;\n  /*  支持CSS3的浏览器（FF 1.5也支持）透明度20% */ }\n\n.wait {\n  position: absolute;\n  top: 50%;\n  left: 50%;\n  font-size: 30px;\n  margin-left: -15px;\n  margin-top: -15px;\n  width: 30px;\n  height: 30px;\n  z-index: 1999; }\n\n.fa-spinner {\n  animation: mymove 2s   linear   infinite;\n  -webkit-animation: mymove 2s  linear   infinite;\n  /*Safari and Chrome*/\n  /*transition: all 2s;*/ }\n\n@-webkit-keyframes mymove {\n  from {\n    -webkit-transform: rotate(0);\n    transform: rotate(0); }\n  to {\n    -webkit-transform: rotate(360deg);\n    transform: rotate(360deg); } }\n\n@keyframes mymove {\n  from {\n    -webkit-transform: rotate(0);\n    transform: rotate(0); }\n  to {\n    -webkit-transform: rotate(360deg);\n    transform: rotate(360deg); } }\n\naddress, caption, cite, code, dfn, em, th, var, i {\n  font-style: normal;\n  font-weight: normal; }\n\n/*icon list */\n.flex {\n  display: -ms-flexbox;\n  display: -webkit-flex;\n  display: flex;\n  /*水平布局*/ }\n\n.flex-row {\n  -ms-flex-direction: row;\n  -webkit-flex-direction: row;\n  flex-direction: row; }\n\n.flex-wrap {\n  -ms-flex-wrap: wrap;\n  -webkit-flex-wrap: wrap;\n  flex-wrap: wrap; }\n\n.items-start {\n  -ms-flex-align: start;\n  -webkit-align-items: flex-start;\n  align-items: flex-start; }\n\n.justify-start {\n  -ms-flex-pack: start;\n  -webkit-justify-content: flex-start;\n  justify-content: flex-start; }\n\n.list {\n  list-style-type: none; }\n\n.ma0 {\n  margin: 0; }\n\n.pa0 {\n  padding: 0; }\n", ""]);

// exports


/***/ })

/******/ });