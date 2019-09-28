<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@ include file="../common/cmnCntxtPath.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<sec:csrfMetaTags/>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>${appTitle} | All Roles</title>
	<!-- Tell the browser to be responsive to screen width -->
	<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
	<!-- Bootstrap 3.3.7 -->
	<link rel="stylesheet" href="${contextPath}resources/bootstrap/css/bootstrap.min.css">
	<!-- Font Awesome -->
	<link rel="stylesheet" href="${contextPath}resources/font-awesome/css/font-awesome.min.css">
	<!-- Ionicons -->
	<link rel="stylesheet" href="${contextPath}resources/ionicons/css/ionicons.min.css">
	<!-- DataTables -->
	<link rel="stylesheet" href="${contextPath}resources/datatables.net-bs/css/dataTables.bootstrap.min.css">
	<!-- Semantic Checkbox -->
	<link rel="stylesheet" href="${contextPath}resources/semantic-ui/checkbox.min.css">
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
					Role Listing
				</h1>
				<ol class="breadcrumb">
					<li><a href="${contextPath}home"><i class="fa fa-dashboard"></i> Home</a></li>
					<c:if test="${appAuthorization.isAccessAllowed('role/list')}">
					<li><a href="${contextPath}role/list">Role</a></li>
					</c:if>
					<li class="active">List Roles</li>
				</ol>
			</section>

			<!-- Main content -->
			<section class="content">
				<div class="row">
					<div class="col-xs-12">
						<div class="box">
							<div class="box-header">
								<h3 class="box-title">List Roles</h3>
							</div>
							<!-- /.box-header -->
							<div class="box-body">
								<table id="rolesTable" class="table table-bordered table-striped">
								</table>
							</div>
							<!-- /.box-body -->
						</div>
						<!-- /.box -->
					</div>
					<!-- /.col -->
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
	<!-- DataTables -->
	<script src="${contextPath}resources/datatables.net/jquery.dataTables.min.js"></script>
	<script src="${contextPath}resources/datatables.net-bs/js/dataTables.bootstrap.min.js"></script>
	<!-- SlimScroll -->
	<script src="${contextPath}resources/jquery-slimscroll/jquery.slimscroll.min.js"></script>
	<!-- FastClick -->
	<script src="${contextPath}resources/fastclick/fastclick.js"></script>
	<!-- Semantic Checkbox -->
	<script src="${contextPath}resources/semantic-ui/checkbox.min.js"></script>
	<!-- AdminLTE App -->
	<script src="${contextPath}js/adminlte.min.js"></script>
	<!-- AdminLTE for demo purposes -->
	<script src="${contextPath}js/demo.js"></script>
	<!-- page script -->
	<script>
		var contextPath = "${contextPath}";
		var activationIsAllowed = ${appAuthorization.isAccessAllowed('role/activation')};
		var updateIsAllowed = ${appAuthorization.isAccessAllowed('role/update')};
	</script>
	<script src="${contextPath}js/common.js"></script>
	<script src="${contextPath}js/pages/role/list.js"></script>
</body>
</html>