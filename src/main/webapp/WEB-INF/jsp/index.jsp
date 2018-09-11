<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="itcast" uri="http://itcast.cn/common/"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<title>客户列表-BootCRM</title>

<!-- Bootstrap Core CSS -->
<link href="<%=basePath%>css/bootstrap.min.css" rel="stylesheet">

<!-- MetisMenu CSS -->
<link href="<%=basePath%>css/metisMenu.min.css" rel="stylesheet">

<!-- DataTables CSS -->
<link href="<%=basePath%>css/dataTables.bootstrap.css" rel="stylesheet">

<!-- Custom CSS -->
<link href="<%=basePath%>css/sb-admin-2.css" rel="stylesheet">

<!-- Custom Fonts -->
<link href="<%=basePath%>css/font-awesome.min.css" rel="stylesheet"
	type="text/css">
<link href="<%=basePath%>css/boot-crm.css" rel="stylesheet"
	type="text/css">
<link href="<%=basePath%>js/layer/theme/default/layer.css" rel="stylesheet"
	type="text/css">
<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

</head>

<body>

	<div id="wrapper">

		<!-- Navigation -->
		<nav class="navbar navbar-default navbar-static-top" role="navigation"
			style="margin-bottom: 0">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle" data-toggle="collapse"
				data-target=".navbar-collapse">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<span class="navbar-brand">BOOT客户管理系统 v2.0</span>
		</div>
		<!-- /.navbar-header -->

		 </nav>

		<div class="navbar navbar-default navbar-static-top">
			 <!-- <div class="row">
				<div class="col-lg-12">
					<h1 class="page-header">客户管理</h1>
				</div>
				/.col-lg-12
			</div> -->
			<!-- /.row -->
			<div class="panel panel-default">
				<div class="panel-body">
					<form class="form-inline" action="${pageContext.request.contextPath }/index/list.action" id="serachForm" method="get">
						<div class="form-group">
							<label for="SchoolName">分数</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="text" class="form-control" placeholder="请输入你的分数" id="score" value="${score }" name="score">
						</div>
						<button type="button" class="btn btn-primary serach" style="margin-left: 5%;">查询</button>
						<input type="button" class="btn btn-primary" data-toggle="modal" data-target="#indexInsertDialog" value="增加" style="float: right;">
					</form>
				</div>
			</div>
			<div class="row" style="margin: 0px;">
				<div class="col-lg-12" style="padding: 0px;">
					<div class="panel panel-default">
						<div class="panel-heading">学校信息列表</div>
						<!-- /.panel-heading -->
						<table class="table table-bordered table-striped">
							<thead>
								<tr>
									<th>ID</th>
									<th>学校名称</th>
									<th>分数线</th>
									<th>创建时间</th>
									<th>学校代码</th>
									<th>固定电话</th>	
									<th>操作</th>							
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${page.rows}" var="row">
									<tr>
									    <td style="text-align: center;">${row.id}</td>
										<td style="text-align: center;">${row.schoolname}</td>
										<td style="text-align: center;">${row.score}</td>
										<td style="text-align: center;">${row.createtime}</td>
										<td style="text-align: center;">${row.schoolcode}</td>
										<td style="text-align: center;">${row.tellphone}</td>
										 <td style="text-align: center;">
											<a href="#" class="btn btn-primary btn-xs" data-toggle="modal" data-target="#indexEditDialog" onclick="editIndex(${row.id})">修改</a>&nbsp;&nbsp;
											<a href="#" class="btn btn-danger btn-xs" onclick="deleteIndex(${row.id})">删除</a>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
						<div class="col-md-12 text-center">
							<itcast:page url="${pageContext.request.contextPath }/index/list.action" />
						</div>
						<!-- /.panel-body -->
					</div>
					<!-- /.panel -->
				</div>
				<!-- /.col-lg-12 -->
			</div>
		</div>
		<!-- /#page-wrapper -->

	</div>
	<!-- 客户编辑对话框 -->
	<div class="modal fade" id="indexEditDialog" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">修改客户信息</h4>
				</div>
				<div class="modal-body">
					<form class="form-horizontal" id="edit_index_form">
						<input type="hidden" id="edit_id" name="id"/>
						
						<div class="form-group">
							<label for="edit_schoolname" class="col-sm-2 control-label">学校名称</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" id="edit_schoolname" placeholder="学校名称" name="schoolname">
							</div>
						</div>
						
                        <div class="form-group">
							<label for="edit_schoolcode" class="col-sm-2 control-label">学校编码</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" id="edit_schoolcode" placeholder="学校编码" name="schoolcode">
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit_score" class="col-sm-2 control-label">分数线</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" id="edit_score" placeholder="分数线" name="score">
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit_tellphone" class="col-sm-2 control-label">固定电话</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" id="edit_tellphone" placeholder="移动电话" name="tellphone">
							</div>
						</div>
						
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" onclick="updateIndex()">保存修改</button>
				</div>
			</div>
		</div>
	</div>
	
	
	
	
	<!-- 客户增加对话框 -->
	<div class="modal fade" id="indexInsertDialog" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">增加客户信息</h4>
				</div>
				<div class="modal-body">
					<form class="form-horizontal" id="insert_index_form">
						<div class="form-group">
							<label for="insert_schoolname" class="col-sm-2 control-label">学校名称</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" id="insert_schoolname" placeholder="学校名称" name="schoolname">
							</div>
						</div>
                        <div class="form-group">
							<label for="insert_schoolcode" class="col-sm-2 control-label">学校编码</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" id="insert_schoolcode" placeholder="学校编码" name="schoolcode">
							</div>
						</div>
						<div class="form-group">
							<label for="insert_score" class="col-sm-2 control-label">分数线</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" id="insert_score" placeholder="分数线" name="score">
							</div>
						</div>
						<div class="form-group">
							<label for="insert_tellphone" class="col-sm-2 control-label">固定电话</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" id="insert_tellphone" placeholder="移动电话" name="tellphone">
							</div>
						</div>												
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" onclick="insertIndex()">保存</button>
				</div>
			</div>
		</div>
	</div>
	<!-- /#wrapper -->

	<!-- jQuery -->
	<script src="<%=basePath%>js/jquery.min.js"></script>
   <script src="<%=basePath%>js/layer/layer.js"></script>
	<!-- Bootstrap Core JavaScript -->
	<script src="<%=basePath%>js/bootstrap.min.js"></script>

	<!-- Metis Menu Plugin JavaScript -->
	<script src="<%=basePath%>js/metisMenu.min.js"></script>

	<!-- DataTables JavaScript -->
	<script src="<%=basePath%>js/jquery.dataTables.min.js"></script>
	<script src="<%=basePath%>js/dataTables.bootstrap.min.js"></script>

	<!-- Custom Theme JavaScript -->
	<script src="<%=basePath%>js/sb-admin-2.js"></script>
	
	<script type="text/javascript">
	$(".serach").click(function(){
		var score=$("#score").val();
		if(!score){
			layer.msg("分数不能为空");
			return false;
		}
		$("#serachForm").submit();
	})
	
		function editIndex(id) {
			$.ajax({
				type:"get",
				url:"<%=basePath%>school/edit.action",
				data:{"id":id},
				success:function(data) {
					$("#edit_id").val(data.id);
					$("#edit_schoolname").val(data.schoolname);
					$("#edit_schoolcode").val(data.schoolcode);
					$("#edit_score").val(data.score);
					$("#edit_tellphone").val(data.tellphone);
					
				}
			});
		}
		function updateIndex() {
			$.post("<%=basePath%>school/update.action",$("#edit_index_form").serialize(),function(data){
				if(data.code == 1){
					layer.msg(data.msg,{time:1000,icon: 1,end:function(){
						window.location.reload();}
					});return false;
				}else{
					layer.msg(data.msg,{icon: 2,end:function(){
						layer.closeAll();}
					});return false;
				}
			});
		}
		
		function insertIndex() {
			$.post("<%=basePath%>school/insert.action",$("#insert_index_form").serialize(),function(data){
				if(data.code == 1){
					layer.msg(data.msg,{time:1000,icon: 1,end:function(){
						window.location.reload();}
					});return false;
				}else{
					layer.msg(data.msg,{icon: 2,end:function(){
						layer.closeAll();}
					});return false;
				}
			});
		}
		
		function deleteIndex(id) {
			//询问框
			layer.confirm('是否确认删除？', {
			  btn: ['确认','取消'] //按钮
			}, function(){
				$.post("<%=basePath%>school/delete.action",{"id":id},function(data){
					if(data.code == 1){
						layer.msg(data.msg,{time:1000,icon: 1,end:function(){
							window.location.reload();}
						});return false;
					}else{
						layer.msg(data.msg,{icon: 2,end:function(){
							layer.closeAll();}
						});return false;
					}
				});
			}, function(){
				layer.closeAll();
			});
		}
			
			
	</script>

</body>

</html>
