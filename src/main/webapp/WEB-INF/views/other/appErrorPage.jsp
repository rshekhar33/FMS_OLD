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
	<title>${appTitle}</title>
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
							<h1>Error Page</h1>
						</div>
						<!-- /.col -->
						<div class="col-sm-6">
							<ol class="breadcrumb float-sm-right">
								<li class="breadcrumb-item">
									<a href="${contextPath}home">Home</a>
								</li>
								<li class="breadcrumb-item active">Error</li>
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
				<div class="error-page">
					<div class="error-content">
						<h3>
							<i class="fas fa-exclamation-triangle text-danger"></i> ${exceptionHeader}
						</h3>

						<p>
							${exceptionDesc}
						</p>
						<!-- Error Message : ${exceptionStack} -->
					</div>
				</div>
				<!-- /.error-page -->
		
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
	<!-- overlayScrollbars -->
	<script src="${contextPath}resources/overlayScrollbars/js/jquery.overlayScrollbars.min.js"></script>
	<!-- AdminLTE -->
	<script src="${contextPath}js/adminlte.js"></script>
	<script src="${contextPath}js/demo.js"></script>
</body>
</html>