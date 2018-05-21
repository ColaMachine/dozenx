


var baseparam = {
  url: "",
  height: 150,
  page: 1,
  rowNum: 20,
  rowTotal: null,
  records: 0,
  pager: "",
  pgbuttons: true,
  pginput: true,
  colModel: [],
  rowList: [],
  colNames: [],
  sortorder: "asc",
  sortname: "",
  datatype: "xml",
  mtype: "GET",
  altRows: false,
  selarrrow: [],
  savedRow: [],
  shrinkToFit: true,
  xmlReader: {},
  jsonReader: {},
  subGrid: false,
  subGridModel: [],
  reccount: 0,
  lastpage: 0,
  lastsort: 0,
  selrow: null,
  beforeSelectRow: null,
  onSelectRow: null,
  onSortCol: null,
  ondblClickRow: null,
  onRightClickRow: null,
  onPaging: null,
  onSelectAll: null,
  onInitGrid: null,
  loadComplete: null,
  gridComplete: null,
  loadError: null,
  loadBeforeSend: null,
  afterInsertRow: null,
  beforeRequest: null,
  beforeProcessing: null,
  onHeaderClick: null,
  viewrecords: false,
  loadonce: false,
  multiselect: false,
  multikey: false,
  editurl: null,
  search: false,
  caption: "",
  hidegrid: true,
  hiddengrid: false,
  postData: {},
  userData: {},
  treeGrid: false,
  treeGridModel: 'nested',
  treeReader: {},
  treeANode: -1,
  ExpandColumn: null,
  tree_root_level: 0,
  prmNames: {
    page: "curPage",
    rows: "pageSize",
    sort: "sidx",
    order: "sord",
    search: "_search",
    nd: "nd",
    id: "id",
    oper: "oper",
    editoper: "edit",
    addoper: "add",
    deloper: "del",
    subgridid: "id",
    npage: null,
    totalrows: "totalCount"
  },
  forceFit: false,
  gridstate: "visible",
  cellEdit: false,
  cellsubmit: "remote",
  nv: 0,
  loadui: "enable",
  toolbar: [false, ""],
  scroll: false,
  multiboxonly: false,
  deselectAfterSort: true,
  scrollrows: false,
  autowidth: false,
  scrollOffset: 18,
  cellLayout: 5,
  subGridWidth: 20,
  multiselectWidth: 20,
  gridview: false,
  rownumWidth: 25,
  rownumbers: false,
  pagerpos: 'center',
  recordpos: 'right',
  footerrow: false,
  userDataOnFooter: false,
  hoverrows: true,
  altclass: 'ui-priority-secondary',
  viewsortcols: [false, 'vertical', true],
  resizeclass: '',
  autoencode: false,
  remapColumns: [],
  ajaxGridOptions: {},
  direction: "ltr",
  toppager: false,
  headertitles: false,
  scrollTimeout: 40,
  data: [],
  _index: {},
  grouping: false,
};
  function jqGrid(id, param) { log("jqGrid");
  this.p = extend(baseparam, param);
  this.p.id = id;
    if(this.p.grid instanceof HTMLElement){
    }else{
    this.p.grid=this.p.grid|| this.p.grid_selector;
  this.p.grid = $$(this.p.grid);//;document.getElementById(this.p.grid);

  }
 if(this.p.pager instanceof HTMLElement){
 }else{
   this.p.pager=this.p.pager|| this.p.pager_selector;
    this.p.pager =$$(this.p.pager);// document.getElementById(this.p.pager);
}
  this.initGrid();
}

jqGrid.prototype.initGrid = function() {

   log("开始initgrid");
  this.p.width_sum = 0;
  for(var i = 0; i < this.p.colModel.length; i++) {
    this.p.width_sum += this.p.colModel[i].width;

  }
  var gridHeadhtml = "<div class='grid-head'><table class='table '> <thead> <tr>  ";

  if(this.p.multiselect) {
    gridHeadhtml += "<th style=\"width:5%;text-align:center\" ><input type='checkbox' class='cbox' id='cb_" + this.id + "' name='cb_" + this.id + "' > </th>";
  }

  var width = getWidth(this.p.pager);

  for(var i = 0; i < this.p.colNames.length; i++) {

    if(this.p.colModel[i].width == 0) {
      gridHeadhtml += "<th style='display:none' width='" +
        this.p.colModel[i].width * width /
        this.p.width_sum
        //* 100
        +
        "px'>" +
        this.p.colNames[i] +
        "</th>";
    } else {

      gridHeadhtml += "<th style=\"width:" +
        this.p.colModel[i].width * width /
        this.p.width_sum // * 100
        +
        "px\"><a>" + this.p.colNames[i] +
        "</a><span class='moveSpan' style='cursor:e-resize;'>&nbsp;</span></th>";
    }
  }
  gridHeadhtml += "</tr></thead></table></div>";
  var gridTableHtml = "<div class=\"grid-content\"></div>";
  var pageHtml = "";
  if(this.p.gridHead != null) {
    //setHtml(this.p.grid, "<div>" + $(this.p.gridHead).setHtml() + gridTableHtml + "</div>");
  } else {


    setHtml(this.p.grid, "<div>" + gridHeadhtml + gridTableHtml + "</div>");
  }
  //    this.p.width_sum=width_sum;
  setHtml(this.p.pager, pageHtml);

  if(this.p.multiselect) {
    var _this = this;
    var id = this.id;
    var ele = findByName("cb_" + this.id);
    ele.onchange = function() {
      _this.selectAll();
    }

  }

  this.ajaxRequest();
}

jqGrid.prototype.initParam = function() {
  if(!this.p.searchParams) {
    this.p.searchParams = {};
  }

   this.p.postData.curPage =this.p.page;
    this.p.postData.pageSize=this.p.rowNum;
    this.p.searchParams.curPage = this.p.page;
    this.p.searchParams.pageSize = this.p.rowNum;


        }
jqGrid.prototype.ajaxRequest = function() {

        this.initParam();
  if(this.p.postData[this.p.prmNames.page] == null) {
    this.p.postData[this.p.prmNames.page] = 1;
    this.p.postData[this.p.prmNames.rows] = 10;
  }
  var _this = this;
  Ajax.getJSON(this.p.url, this.p.postData,

    function(data) {
     // hideWait();
      _this.ajaxCallBack(data);

    }

  );
}

//jqGrid.prototype.initPageParam = function() {
//
//  if(!this.p.searchParams) {
//    this.p.searchParams = {};
//  }
//  this.p.postData.curPage = this.p.page;
//  this.p.postData.pageSize = this.p.rowNum;
//  this.p.searchParams.curPage = this.p.page;
//  this.p.searchParams.pageSize = this.p.rowNum;
//  //alert(this.p.rowNum);
//
//}

jqGrid.prototype.ajaxCallBack = function(result) {

  if(typeof(result) == 'string')
    result = eval("(" + result + ")");
  if(!ajaxResultHandler(result))
    return;
  var dReader = this.p.jsonReader;
  this.p.data =getAccessor(result, dReader.root);

  this.p.page = intNum(getAccessor(result, dReader.page), this.p.page);

  this.p.lastpage = intNum(getAccessor(result, dReader.total), 1);
  this.p.records = intNum(getAccessor(result, dReader.records));

  if(result.r != AJAX_SUCC) {
    alert(result.msg);
    return;
  }

  this.p.records = result.page.totalCount;

  this.render();

  this.p.selrow = null;
  if(this.p.loadComplete != null)
    this.p.loadComplete.call(this);
  if(this.p.multiselect) {
    //            var id =this.id;
    //            $(this).find(".grid-content").find(
    //            "input[type='checkbox']").change( {
    //                id :id
    //            }, function(event) {
    //                $("#"+event.data.id).find(
    //                        "#cb_"+event.data.id).attr("checked",false);
    //            });
  }

}

jqGrid.prototype.render = function() {

  this.renderGrid();
  this.renderPage();

};

jqGrid.prototype.renderGrid = function() {
  var _this = this;
  var html = "<div><table class='table'>";
  if(this.p.data.length == 0) {
    html += "<thead><div style='text-align:center;'>暂无数据</div></thead>";
  }
  var width = getWidth(this.p.pager);
  html += "<tbody>";
  for(var i = 0; i < this.p.data.length; i++) {
    html += "<tr >";
    if(this.p.multiselect) {
      html += "<td  style=\"width:5%;text-align:center;padding:0\" ><input type='checkbox' class='cbox' id='jqg_" + this.id + "_" + i + "' name='jqg_" + this.id + "_" + i + "' > </td>";
    }
    for(var j = 0; j < this.p.colModel.length; j++) {
      var colName = this.p.colModel[j].name;
      var value = this.p.data[i][colName];
      var title = value;
      if(value) {
        if(value && value.length > 200) {
          value = value.substr(0, 20);
        }
        title = value;
        if(StringUtil.endWith(value, "png") || StringUtil.endWith(value, "jpeg")) {
          value = "<img class='commondity_pic' src='" + value + "'></img>"
        }
      }
      if(typeof(value) == 'undefined' || value == null || value == 'null' || value == 'undefined') {
        value = '';
      }

      if(this.p.colModel[j].width == 0) {

        html += "<td style='display:none'" + (i == 0 ? (" style=\"width:" +
            this.p.colModel[j].width * width /
            this.p.width_sum
            //* 100
            +
            "px\"") : "") + ">" +
          this.p.colNames[j] +
          "</td>";
      } else if(typeof(this.p.colModel[j].formatter) != 'undefined') {

        var _content = this.p.colModel[j].formatter.call(this, value, this.p, this.p.data[i]);
        if(_content) {
          _content = (_content + "").replace(/\'/g, "").replace(/(<.*>)/g, "")
        }
        html += "<td " + (i == 0 ? ("style=\"width:" +
            this.p.colModel[j].width * width /
            this.p.width_sum
            //* 100
            +
            "px\"") : "") + "><span title ='" + _content + "'>" +
          this.p.colModel[j].formatter.call(this, value, this.p, this.p.data[i]); +
        "</span></td>";
      } else {

        html += "<td " + (i == 0 ? ("style=\"width:" +
          this.p.colModel[j].width * width /
          this.p.width_sum +
          "px\"") : "") + "><span title='" + title + "'>" + value + "</span></td>";
      }
    }
    html += "</tr>";

  }
  html += "</tbody></table></div>";

  //tr 添加事件 如果选中就添加样式
  /*tr.click(function(){
      $(".grid-content").find(".selected").removeClass("selected");
      $(this).addClass("selected");
  });*/

  ;
  var gridContent = this.p.grid.getElementsByClassName("grid-content")[0];

  setHtml(gridContent, html);
  var trs = gridContent.getElementsByTagName("tr");
  for(var i = 0; i < trs.length; i++) {

    trs[i].addEventListener("click",
      function() {
        _this.selectRow(i);
      });

    if(_this.ondblClickRow && typeof _this.ondblClickRow != 'undefined') {

      trs[i].addEventListener("dblclick",
        function() {
          this.selrow = i;
          _this.ondblClickRow.call(this, index);

        });

    }

  }

};
jqGrid.prototype.renderPage = function() {
  //首先判断是否时候有加载数据了

  var _this = this;

  var page = this.p.page;
  var totalPage = this.p.lastpage;
  if(page == null)
    return;

  //一般是显示5个页数
  //从 curPage -2 开始 到 curPage+2
  var total = 1;
  var pageHtml = "<nav class=\"nav\"> <ul class=\"pagination pagination-sm\"><li>";

  if(page == 1) {
    pageHtml += "<span href=\"javascript:void(0)\" class=\"page_bg pre \" aria-label=\"Previous\">上一页</span>";
  } else {
    pageHtml += "<a href=\"javascript:void(0)\" class=\"page_bg pre \" aria-label=\"Previous\">上一页</a>";
  }

  var min = 0,
    max = totalPage;
  var middel = page;
  var _min = 0,
    _max = 0;
  _min = (middel - 2) < 1 ? 1 : (middel - 2);
  _max = (middel + 2) > max ? max : (middel + 2);

  if(_min == 1) {
    _max = middel + (5 - middel - _min + 1);
    if(_max >= max)
      _max = max;
  }
  if(_max == max) {
    _min = middel - 5 + (max - middel) + 1;
    if(_min <= 1) {
      _min = 1;
    }
  }
  if(_min > 1) {
    pageHtml += "<li class=\"  num\"><a href=\"javascript:void(0)\" >" + 1 + "</a></li><li ><span>...</span></li>";
  }
  for(var i = _min; i <= _max; i++) {
    if(i == page)
      pageHtml += "<li class=\"  num\" ><span class='curr page_bg'>" +
      i + "</span></li>";
    else
      pageHtml += "<li class=\"num clearfix\" ><a href=\"javascript:void(0)\" >" + i +
      "</a></li>";
  }

  if(_max < totalPage) {
    pageHtml += "<li  ><span>...</span></li><li class=\"  num\" ><a href=\"javascript:void(0)\" >" + totalPage + "</a></li>";
  }
  //加上最后一页 和...号
  /*
  for(var i=page.curPage-4;i<page.curPage+4;i++){
      if(i<1){
          continue;
      }
      total++;
      if(i==page.curPage)
      pageHtml+="<li class=\"active\" ><a href=\"#\">"+i+"</a></li>";
      else
          pageHtml+="<li  ><a href=\"#\" >"+i+"</a></li>";
      if(total>10)
          break;
      if(i>=page.totalPage)
          break;
  }*/
  //console.log(page);
  if(page == totalPage) {
    pageHtml += "<li><span href=\"javascript:void(0)\" class=\"page_bg next\" aria-label=\"Next\">下一页</span>";
  } else {
    pageHtml += "<li><a href=\"javascript:void(0)\" class=\"page_bg next\" aria-label=\"Next\">下一页</a>";
  }
  pageHtml += "</li><li><span style='border-width:0px;white-space:nowrap;'>共" + totalPage + "页，" + this.p.records + "条信息,<a style='border-width:0px;color:black'>每页" + this.p.rowNum + "条<i class='fa fa-caret-down'></i></a></span></li></ul></nav>";
  this.page = page;
  setHtml(this.p.pager, pageHtml);
  var preBtn = this.p.pager.getElementsByClassName("pre")[0];
  preBtn.addEventListener("click", function() {

    _this.goPre();
  });
  var nextBtn = this.p.pager.getElementsByClassName("next")[0];
  nextBtn.addEventListener("click", function() {

    _this.goNext();
  });

  var numBtns = this.p.pager.getElementsByClassName("num");

  for(let i = 0; i < numBtns.length; i++) {
    numBtns[i].addEventListener("click",() =>{

       // console.log(this);
      _this.goPage(numBtns[i].innerText);
    });

  }
};
jqGrid.prototype.selectRow = function(index) {

  removeClass(document.getElementsByClassName("selected"), "selected");
  addClass(this.p.grid, "selected");
  //        alert(event.data.id);
  //        alert(event.data.grid);
  //        alert(event.data.grid.selrow);
  this.p.selrow = index; //

  if(this.onSelectRow) {
    this.onSelectRow.call(this, index);
  }
};

jqGrid.prototype.goPre = function(event) {

    if(this.p.page <= 1)
      return;
    else
      this.p.page--;

    this.ajaxRequest();
};
  jqGrid.prototype.goNext = function(event) {

    console.log("curPage:" + this.p.page + "totalPage")
    if(this.p.page >= this.p.lastpage)
      return;
    this.p.page++;

    this.ajaxRequest();

  };
  jqGrid.prototype.goPage = function(page) {

    this.p.page = page;
    this.ajaxRequest();
  };