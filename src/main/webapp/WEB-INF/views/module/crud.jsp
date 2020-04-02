<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@ include file="../common/cmnCntxtPath.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">
	<sec:csrfMetaTags/>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta http-equiv="x-ua-compatible" content="ie=edge">
	<title>${appTitle} | Module</title>
	<!-- Font Awesome Icons -->
	<link rel="stylesheet" href="${contextPath}resources/font-awesome/css/all.min.css">
	<!-- IonIcons -->
	<link rel="stylesheet" href="${contextPath}resources/ionicons/css/ionicons.min.css">
	<!-- overlayScrollbars -->
	<link rel="stylesheet" href="${contextPath}resources/overlayScrollbars/css/OverlayScrollbars.min.css">
	<!-- Theme style -->
	<link rel="stylesheet" href="${contextPath}css/adminlte.min.css">
	<!-- Google Font: Source Sans Pro -->
	<link rel="stylesheet" href="${contextPath}resources/google-fonts/google-fonts.css">
	<link rel="shortcut icon" href="${contextPath}images/favicon.ico" />
</head>
<body class="hold-transition sidebar-mini text-sm layout-fixed layout-navbar-fixed layout-footer-fixed">
	<div class="wrapper">
		<%@include file="../common/header.jsp"%>

		<%@include file="../common/leftSidebar.jsp"%>

		<!-- Content Wrapper. Contains page content -->
		<div class="content-wrapper">
			<!-- Content Header (Page header) -->
			<div class="content-header">
				<div class="container-fluid">
					<div class="row mb-2">
						<div class="col-sm-6">
							<h1 class="m-0 text-dark">Module</h1>
						</div>
						<!-- /.col -->
						<div class="col-sm-6">
							<ol class="breadcrumb float-sm-right">
								<li class="breadcrumb-item">
									<a href="${contextPath}home">Home</a>
								</li>
								<c:if test="${appAuthorization.isAccessAllowed('module/list')}">
								<li class="breadcrumb-item">
									<a href="${contextPath}module/list">Module</a>
								</li>
								</c:if>
								<li class="breadcrumb-item active">Add Module</li>
							</ol>
						</div>
						<!-- /.col -->
					</div>
					<!-- /.row -->
				</div>
				<!-- /.container-fluid -->
			</div>
			<!-- /.content-header -->

			<!-- Main content -->
			<div class="content">
				<div class="container-fluid">
					<div class="row">
						<!-- column -->
						<div class="col-md-12">
							<!-- Horizontal Form -->
							<div class="card">
								<div class="card-header">
									<h3 class="card-title">Add Module</h3>
								</div>
								<!-- /.card-header -->
								<!-- form start -->
								<form method="post" id="moduleForm" name="moduleForm" onsubmit="return false;" class="form-horizontal">
									<div class="card-body">
										<input type="hidden" id="moduleId" name="moduleId" value="${hidModuleId}" />
										<div class="form-group row">
											<label for="moduleName" class="col-sm-2 col-form-label">Module Name*</label>

											<div class="col-sm-8">
												<input type="text" id="moduleName" name="moduleName" maxlength="100" class="form-control" placeholder="Module Name">
												<label id="moduleNameError" for="moduleName" class="col-form-label errMsgCls d-none"></label>
											</div>
										</div>
									</div>
									<!-- /.card-body -->
									<div class="card-footer">
										<button type="submit" id="submitBtn" name="submitBtn" onclick="validateSubmitFun();" class="btn btn-primary float-right">Submit</button>
									</div>
									<!-- /.card-footer -->
								</form>
							</div>
							<!-- /.card -->
						</div>
						<!--/column -->
					</div>
					<!-- /.row -->
				</div>
				<!-- /.container-fluid -->
			</div>
			<!-- /.content -->
		</div>
		<!-- /.content-wrapper -->

		<%@include file="../common/rightSidebar.jsp"%>

		<%@include file="../common/footer.jsp"%>
	</div>
	<!-- ./wrapper -->

	<!-- REQUIRED SCRIPTS -->

	<!-- jQuery -->
	<script src="${contextPath}resources/jquery/jquery.min.js"></script>
	<!-- Bootstrap -->
	<script src="${contextPath}resources/bootstrap/js/bootstrap.bundle.min.js"></script>
	<!-- Bootbox -->
	<script src="${contextPath}resources/bootbox/bootbox.min.js"></script>
	<!-- overlayScrollbars -->
	<script src="${contextPath}resources/overlayScrollbars/js/jquery.overlayScrollbars.min.js"></script>
	<!-- SerializeToJSON -->
	<script src="${contextPath}resources/serialize/jquery.serializeToJSON.min.js"></script>
	<!-- AdminLTE -->
	<script src="${contextPath}js/adminlte.min.js"></script>

	<!-- OPTIONAL SCRIPTS -->
	<script src="${contextPath}js/demo.js"></script>
	<!-- page script -->
	<script>
		var contextPath = "${contextPath}";
	</script>
	<script src="${contextPath}js/common.js"></script>
	<script src="${contextPath}js/pages/module/crud.js"></script>
</body>
</html>