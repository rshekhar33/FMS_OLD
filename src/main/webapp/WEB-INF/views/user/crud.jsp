<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@ include file="../common/cmnCntxtPath.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<sec:csrfMetaTags/>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>${appTitle} | Users</title>
	<!-- Tell the browser to be responsive to screen width -->
	<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
	<!-- Bootstrap 3.3.7 -->
	<link rel="stylesheet" href="${contextPath}resources/bootstrap/css/bootstrap.min.css">
	<!-- Font Awesome -->
	<link rel="stylesheet" href="${contextPath}resources/font-awesome/css/font-awesome.min.css">
	<!-- Ionicons -->
	<link rel="stylesheet" href="${contextPath}resources/ionicons/css/ionicons.min.css">
	<!-- Select2 -->
	<link rel="stylesheet" href="${contextPath}resources/select2/css/select2.min.css">
	<!-- Theme style -->
	<link rel="stylesheet" href="${contextPath}css/AdminLTE.min.css">
	<!-- AdminLTE Skins. Choose a skin from the css/skins folder instead of downloading all of them to reduce the load. -->
	<link rel="stylesheet" href="${contextPath}css/skins/_all-skins.min.css">

	<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
	<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
	<!--[if lt IE 9]>
	<script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
	<script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
	<![endif]-->

	<!-- Google Font -->
	<link rel="stylesheet" href="${contextPath}resources/google-fonts/google-fonts.css">
	<link rel="shortcut icon" href="${contextPath}images/favicon.ico" />
</head>
<body class="hold-transition skin-blue sidebar-mini">
	<div class="wrapper">
		<%@include file="../common/header.jsp"%>

		<%@include file="../common/leftSidebar.jsp"%>

		<!-- Content Wrapper. Contains page content -->
		<div class="content-wrapper">
			<!-- Content Header (Page header) -->
			<section class="content-header">
				<h1>
					User Listing
				</h1>
				<ol class="breadcrumb">
					<li><a href="${contextPath}home"><i class="fa fa-dashboard"></i> Home</a></li>
					<c:if test="${appAuthorization.isAccessAllowed('user/list')}">
					<li><a href="${contextPath}user/list">User</a></li>
					</c:if>
					<li class="active">Add User</li>
				</ol>
			</section>

			<!-- Main content -->
			<section class="content">
				<div class="row">
					<!-- column -->
					<div class="col-md-12">
						<!-- Horizontal Form -->
						<div class="box box-info">
							<div class="box-header with-border">
								<h3 class="box-title">Add User</h3>
							</div>
							<!-- /.box-header -->
							<!-- form start -->
							<form method="post" id="userForm" name="userForm" onsubmit="return false;" class="form-horizontal">
								<div class="box-body">
									<input type="hidden" id="hidUserId" name="hidUserId" value="${hidUserId}" />
									<div class="form-group">
										<label for="userName" class="col-sm-2 control-label">UserName*</label>

										<div class="col-sm-8">
											<input type="text" id="userName" name="userName" maxlength="50" class="form-control" placeholder="UserName">
											<label id="userNameError" for="userName" class="control-label errMsgCls hidden"></label>
										</div>
									</div>
									<div class="form-group">
										<label for="firstName" class="col-sm-2 control-label">First Name*</label>

										<div class="col-sm-8">
											<input type="text" id="firstName" name="firstName" maxlength="100" class="form-control" placeholder="First Name">
											<label id="firstNameError" for="firstName" class="control-label errMsgCls hidden"></label>
										</div>
									</div>
									<div class="form-group">
										<label for="middleName" class="col-sm-2 control-label">Middle Name</label>

										<div class="col-sm-8">
											<input type="text" id="middleName" name="middleName" maxlength="100" class="form-control" placeholder="Middle Name">
											<label id="middleNameError" for="middleName" class="control-label errMsgCls hidden"></label>
										</div>
									</div>
									<div class="form-group">
										<label for="lastName" class="col-sm-2 control-label">Last Name</label>

										<div class="col-sm-8">
											<input type="text" id="lastName" name="lastName" maxlength="100" class="form-control" placeholder="Last Name" />
											<label id="lastNameError" for="lastName" class="control-label errMsgCls hidden"></label>
										</div>
									</div>
									<div class="form-group">
										<label for="emailId" class="col-sm-2 control-label">Email ID*</label>

										<div class="col-sm-8">
											<input type="text" id="emailId" name="emailId" maxlength="100" class="form-control" placeholder="Email ID" />
											<label id="emailIdError" for="emailId" class="control-label errMsgCls hidden"></label>
										</div>
									</div>
									<div class="form-group">
										<label for="mobileNo" class="col-sm-2 control-label">Mobile No*</label>

										<div class="col-sm-8">
											<input type="text" id="mobileNo" name="mobileNo" maxlength="10" class="form-control" placeholder="Mobile No" />
											<label id="mobileNoError" for="mobileNo" class="control-label errMsgCls hidden"></label>
										</div>
									</div>
									<div class="form-group">
										<label for="roles" class="col-sm-2 control-label">Role*</label>

										<div class="col-sm-4">
											<select id="roles" name="roles" multiple="multiple" class="form-control">
											</select>
											<input type="hidden" id="rolesStr" name="rolesStr" />
											<label id="rolesError" for="roles" class="control-label errMsgCls hidden"></label>
										</div>
									</div>
								</div>
								<!-- /.box-body -->
								<div class="box-footer">
									<button type="submit" id="submitBtn" name="submitBtn" onclick="validateSubmitFun();" class="btn btn-primary pull-right">Submit</button>
								</div>
								<!-- /.box-footer -->
							</form>
						</div>
						<!-- /.box -->
					</div>
					<!--/.col -->
				</div>
				<!-- /.row -->
			</section>
			<!-- /.content -->
		</div>
		<!-- /.content-wrapper -->

		<%@include file="../common/footer.jsp"%>

		<%@include file="../common/rightSidebar.jsp"%>

		<!-- Add the sidebar's background. This div must be placed immediately after the control sidebar -->
		<div class="control-sidebar-bg"></div>
	</div>
	<!-- ./wrapper -->

	<!-- jQuery 3 -->
	<script src="${contextPath}resources/jquery/jquery.min.js"></script>
	<!-- Bootstrap 3.3.7 -->
	<script src="${contextPath}resources/bootstrap/js/bootstrap.min.js"></script>
	<!-- Bootbox 5.1.0 -->
	<script src="${contextPath}resources/bootbox/bootbox.min.js"></script>
	<!-- Select2 -->
	<script src="${contextPath}resources/select2/js/select2.full.min.js"></script>
	<!-- FastClick -->
	<script src="${contextPath}resources/fastclick/fastclick.js"></script>
	<!-- AdminLTE App -->
	<script src="${contextPath}js/adminlte.min.js"></script>
	<!-- AdminLTE for demo purposes -->
	<script src="${contextPath}js/demo.js"></script>
	<!-- page script -->
	<script>
		var contextPath = "${contextPath}";
	</script>
	<script src="${contextPath}js/common.js"></script>
	<script src="${contextPath}js/pages/user/crud.js"></script>
</body>
</html>