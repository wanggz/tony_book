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
              <span class="placeholder">全部</span>
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
        </div>
      </div>
    </form>

    <p></p>

    <div class="row marketing">


      <div class="panel panel-default" style="display:none;" id="result">

        <div class="panel-heading">Panel heading</div>

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

          $(document).ready(function(){
              var mydropdown=new customDropDown($("#dropdown"));
          });


          $("#search").bind("click",function(){
              var keyWord = $("#content").val().toLowerCase();
              var count = 0;
              $("#tablebox").empty();
              for(var i=0;i<book_arr.length;i++){
                  if(book_arr[i].name.toLowerCase().indexOf(keyWord)>=0){
                      appendTr(book_arr[i]);
                      count++;
                  } else if(book_arr[i].author.toLowerCase().indexOf(keyWord)>=0){
                      appendTr(book_arr[i]);
                      count++;
                  } else if(book_arr[i].publisher.toLowerCase().indexOf(keyWord)>=0){
                      appendTr(book_arr[i]);
                      count++;
                  }
                  if(count>=50){
                      break;
                  }
              }
              $("#result").show();
          });

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


      });
  </script>

  </body>
</html>
