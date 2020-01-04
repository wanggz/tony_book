<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/12/3/003
  Time: 20:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <meta name="description" content="">
    <meta name="author" content="magic">

    <title>佛学文库-图书检索</title>
    <!-- Bootstrap core CSS -->
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <!-- Custom styles for this template -->
    <link href="css/jumbotron-narrow.css" rel="stylesheet">
  </head>
  <body>
  <div class="container">
    <div class="header clearfix">
      <nav>
        <ul class="nav nav-pills pull-right">
          <li role="presentation" class="active"><a href="#">首页</a></li>
          <li role="presentation"><a href="#">上传</a></li>
          <li role="presentation"><a href="#">Contact</a></li>
        </ul>
      </nav>
      <h3 class="text-muted">佛学文库</h3>
    </div>

    <form>
      <div class="input-group">
        <div id="dropdown1" class="dropdown input-group-btn">
          <div class="btn-group">
            <a class="btn btn-default dropdown-toggle" type="button" id="dropdownMenu1" data-toggle="dropdown">
              <!--选中option之后，要在这里显示选中值，类似原始select里面的文本框-->
              <span class="placeholder" value="1">全部</span>
              <span class="caret"></span>
            </a>
            <ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu1">
              <li role="presentation" value="1"><a role="menuitem" tabindex="1" href="javascript:void(0);">全部</a></li>
              <li role="separator" class="divider"></li>
              <li role="presentation" value="2"><a role="menuitem" tabindex="2" href="javascript:void(0);">题名</a></li>
              <li role="presentation" value="3"><a role="menuitem" tabindex="3" href="javascript:void(0);">责任者</a></li>
              <li role="presentation" value="4"><a role="menuitem" tabindex="4" href="javascript:void(0);">出版者</a></li>
            </ul>
          </div>
        </div>

        <input id="content" type="text" class="form-control" aria-label="..." placeholder="请输入本页面内容">
        <div class="input-group-btn">
          <button id="search" type="button" class="btn btn-default">搜索</button>
        </div>
        <div class="add_strategy_box" style="display: none">
          <input type="text" class="gd-input gd-input-lg">
          <input type="hidden" id="pageStart" name="pageStart" value="0" />
          <input type="hidden" id="pageNoNow" name="pageNoNow" value="1" />
        </div>
      </div>
    </form>

    <p></p>

    <div class="row marketing">


      <div class="panel panel-default" style="display:none;" id="result">

        <div class="panel-heading">搜索引擎为您找到相关结果约<span id="s_count"></span>个</div>

        <table class="table">
          <thead>
          <tr>
            <th>序号</th>
            <th>索书号</th>
            <th>题名</th>
            <th>责任者</th>
            <th>出版者</th>
            <th>册数</th>
            <th>馆藏地址</th>
          </tr>
          </thead>
          <tbody id="tablebox"></tbody>
        </table>

        <nav aria-label="Page navigation" style="text-align: center" id="nav">

        </nav>

      </div>



    </div>

    <footer class="footer">
      <p>&copy; Company 2019</p>
    </footer>

  </div>
  <!-- /container -->

  <script>
      //window.jQuery || document.write('<script src="js/jquery-1.9.1.min.js"><\/script>')
  </script>
  <script type="text/javascript" src="js/jquery-1.9.1.min.js"></script>
  <script type="text/javascript" src="js/bootstrap.min.js"></script>
  <script type="text/javascript" src="js/books.js"></script>
  <script type="text/javascript">




      $(function() {

          function customDropDown(ele){
              this.dropdown=ele;
              this.placeholder=this.dropdown.find(".placeholder");
              this.options=this.dropdown.find("ul.dropdown-menu > li");
              this.val='';
              this.index=-1;//默认为-1;
              this.initEvents();
          }
          customDropDown.prototype={
              initEvents:function(){
                  var obj=this;
                  //点击下拉列表的选项
                  obj.options.on("click",function(){
                      var opt=$(this);
                      obj.text=opt.find("a").text();
                      obj.val=opt.attr("value");
                      obj.index=opt.index();
                      obj.placeholder.text(obj.text);
                      obj.placeholder.value=obj.val;
                  });
              },
              getText:function(){
                  return this.text;
              },
              getValue:function(){
                  return this.val;
              },
              getIndex:function(){
                  return this.index;
              }
          }
          $(document).ready(function(){
              var mydropdown=new customDropDown($("#dropdown1"));
          });

          $(document).keyup(function(event){
              if(event.keyCode ==13){
                  $("#search").trigger("click");
              }
          });

          $("#search").bind("click",function(){
              ajaxFunction();
          });

          function ajaxFunction(){
              var keyWord = $("#content").val().toLowerCase();
              //dropdownMenu1
              var mydropdown = new customDropDown($("#dropdown1"));
              var field = mydropdown.placeholder["0"].textContent;
              var pageStart = $("#pageStart").val();
              var pageNoNow = $("#pageNoNow").val();
              alert("pageNoNow:"+pageNoNow+",pageStart:"+pageStart);

              $.ajax({
                  type : "GET",
                  contentType: "application/json;charset=UTF-8",
                  url : "/query/"+keyWord+"/"+field+"/"+pageStart+"/"+pageNoNow,
                  success : function(result) {
                      //填充s_count
                      $("#s_count").text(result.numHits);
                      //填充pageNoNow
                      $("#pageNoNow").val(result.pageNoNow);
                      //填充pagenav
                      $("#nav").empty();
                      createNav(result.pageNoList);
                      bingPageClick();
                      //填充内容
                      $("#tablebox").empty();
                      for(var i=0;i<result.books.length;i++){
                          appendTr(result.books[i]);
                      }
                    $("#result").show();
                  },
                  error : function(e){
                      console.log(e.status);
                      console.log(e.responseText);
                  }
              });
          }

          function appendTr(item) {
              var $trTemp = $("<tr></tr>");
              $trTemp.append("<td>"+ item.no +"</td>");
              $trTemp.append("<td>"+ item.index +"</td>");
              $trTemp.append("<td>"+ item.name +"</td>");
              $trTemp.append("<td>"+ item.author +"</td>");
              $trTemp.append("<td>"+ item.publisher +"</td>");
              $trTemp.append("<td>"+ item.count +"</td>");
              $trTemp.append("<td>"+ item.address +"</td>");
              $trTemp.appendTo("#tablebox");
          }

          function createNav(pageNums) {
              var $navTemp = $("<ul class='pagination'></ul>");
              if($("#pageNoNow").val() == "1") {
                  $navTemp.append("<li class='disabled'><a aria-label='Previous'><span aria-hidden='true'>&laquo;</span></a></li>");
              } else {
                  $navTemp.append("<li><a aria-label='Previous'><span aria-hidden='true'>&laquo;</span></a></li>");
              }
              for(var i=0;i<pageNums.length;i++){
                  if(pageNums[i] == $("#pageNoNow").val()) {
                      $navTemp.append("<li class='active'><a class='pageClick'>"+pageNums[i]+"<span class='sr-only'>(current)</span></a></li>");
                  } else {
                      $navTemp.append("<li><a class='pageClick'>"+pageNums[i]+"</a></li>");
                  }
              }
              if($("#pageNoNow").val() == pageNums[pageNums.length-1]) {
                  $navTemp.append("<li class='disabled'><a aria-label='Next'><span aria-hidden='true'>&raquo;</span></a></li>");
              } else {
                  $navTemp.append("<li><a aria-label='Next'><span aria-hidden='true'>&raquo;</span></a></li>");
              }
              $navTemp.appendTo("#nav");
          }

          function bingPageClick() {
              $(".pageClick").on('click',function(){
                  alert(this.textContent);
                  if(this.textContent.indexOf("current") == -1 ) {
                      //修改pageNoNow
                      $("#pageNoNow").val(this.textContent);
                      //修改pageStart
                      $("#pageStart").val((Number(this.textContent)-1)*10);
                      //ajax
                      ajaxFunction();
                  }
              });
          }


      });
  </script>

  </body>
</html>
