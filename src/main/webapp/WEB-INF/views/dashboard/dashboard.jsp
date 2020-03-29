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
	<title>${appTitle} | Dashboard</title>
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
<!--
BODY TAG OPTIONS:
=================
Apply one or more of the following classes to to the body tag
to get the desired effect
|---------------------------------------------------------|
|LAYOUT OPTIONS | sidebar-collapse                        |
|               | sidebar-mini                            |
|---------------------------------------------------------|
-->
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
							<h1 class="m-0 text-dark">Dashboard v3</h1>
						</div>
						<!-- /.col -->
						<div class="col-sm-6">
							<ol class="breadcrumb float-sm-right">
								<li class="breadcrumb-item">
									<a href="${contextPath}home">Home</a>
								</li>
								<li class="breadcrumb-item active">Dashboard v3</li>
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
						<div class="col-lg-6">
							<div class="card">
								<div class="card-header border-0">
									<div class="d-flex justify-content-between">
										<h3 class="card-title">Online Store Visitors</h3>
										<a href="javascript:void(0);">View Report</a>
									</div>
								</div>
								<div class="card-body">
									<div class="d-flex">
										<p class="d-flex flex-column">
											<span class="text-bold text-lg">820</span>
											<span>Visitors Over Time</span>
										</p>
										<p class="ml-auto d-flex flex-column text-right">
											<span class="text-success"> <i class="fas fa-arrow-up"></i> 12.5% </span>
											<span class="text-muted">Since last week</span>
										</p>
									</div>
									<!-- /.d-flex -->

									<div class="position-relative mb-4">
										<canvas id="visitors-chart" height="200"></canvas>
									</div>

									<div class="d-flex flex-row justify-content-end">
										<span class="mr-2"> <i class="fas fa-square text-primary"></i> This Week </span>
										<span> <i class="fas fa-square text-gray"></i> Last Week </span>
									</div>
								</div>
							</div>
							<!-- /.card -->

							<div class="card">
								<div class="card-header border-0">
									<h3 class="card-title">Products</h3>
									<div class="card-tools">
										<a href="#" class="btn btn-tool btn-sm"> <i class="fas fa-download"></i> </a>
										<a href="#" class="btn btn-tool btn-sm"> <i class="fas fa-bars"></i> </a>
									</div>
								</div>
								<div class="card-body table-responsive p-0">
									<table class="table table-striped table-valign-middle">
										<thead>
											<tr>
												<th>Product</th>
												<th>Price</th>
												<th>Sales</th>
												<th>More</th>
											</tr>
										</thead>
										<tbody>
											<tr>
												<td>
													<img src="${contextPath}images/default-150x150.png" alt="Product 1" class="img-circle img-size-32 mr-2">
													Some Product
												</td>
												<td>$13 USD</td>
												<td>
													<small class="text-success mr-1">
														<i class="fas fa-arrow-up"></i> 12% 
													</small>
													12,000 Sold
												</td>
												<td>
													<a href="#" class="text-muted"> <i class="fas fa-search"></i> </a>
												</td>
											</tr>
											<tr>
												<td>
													<img src="${contextPath}images/default-150x150.png" alt="Product 1" class="img-circle img-size-32 mr-2">
													Another Product
												</td>
												<td>$29 USD</td>
												<td>
													<small class="text-warning mr-1">
														<i class="fas fa-arrow-down"></i> 0.5% 
													</small>
													123,234 Sold
												</td>
												<td>
													<a href="#" class="text-muted"> <i class="fas fa-search"></i> </a>
												</td>
											</tr>
											<tr>
												<td>
													<img src="${contextPath}images/default-150x150.png" alt="Product 1" class="img-circle img-size-32 mr-2">
													Amazing Product
												</td>
												<td>$1,230 USD</td>
												<td>
													<small class="text-danger mr-1">
														<i class="fas fa-arrow-down"></i> 3%
													</small>
													198 Sold
												</td>
												<td>
													<a href="#" class="text-muted"> <i class="fas fa-search"></i> </a>
												</td>
											</tr>
											<tr>
												<td>
													<img src="${contextPath}images/default-150x150.png" alt="Product 1" class="img-circle img-size-32 mr-2">
													Perfect Item <span class="badge bg-danger">NEW</span>
												</td>
												<td>$199 USD</td>
												<td>
													<small class="text-success mr-1">
														<i class="fas fa-arrow-up"></i> 63% 
													</small>
													87 Sold
												</td>
												<td>
													<a href="#" class="text-muted"> <i class="fas fa-search"></i> </a>
												</td>
											</tr>
										</tbody>
									</table>
								</div>
							</div>
							<!-- /.card -->
						</div>
						<!-- /.col-md-6 -->
						<div class="col-lg-6">
							<div class="card">
								<div class="card-header border-0">
									<div class="d-flex justify-content-between">
										<h3 class="card-title">Sales</h3>
										<a href="javascript:void(0);">View Report</a>
									</div>
								</div>
								<div class="card-body">
									<div class="d-flex">
										<p class="d-flex flex-column">
											<span class="text-bold text-lg">$18,230.00</span>
											<span>Sales Over Time</span>
										</p>
										<p class="ml-auto d-flex flex-column text-right">
											<span class="text-success">
												<i class="fas fa-arrow-up"></i> 33.1%
											</span>
											<span class="text-muted">Since last month</span>
										</p>
									</div>
									<!-- /.d-flex -->

									<div class="position-relative mb-4">
										<canvas id="sales-chart" height="200"></canvas>
									</div>

									<div class="d-flex flex-row justify-content-end">
										<span class="mr-2">
											<i class="fas fa-square text-primary"></i> This year
										</span>
										<span>
											<i class="fas fa-square text-gray"></i> Last year 
										</span>
									</div>
								</div>
							</div>
							<!-- /.card -->

							<div class="card">
								<div class="card-header border-0">
									<h3 class="card-title">Online Store Overview</h3>
									<div class="card-tools">
										<a href="#" class="btn btn-sm btn-tool"> <i class="fas fa-download"></i> </a>
										<a href="#" class="btn btn-sm btn-tool"> <i class="fas fa-bars"></i> </a>
									</div>
								</div>
								<div class="card-body">
									<div class="d-flex justify-content-between align-items-center border-bottom mb-3">
										<p class="text-success text-xl">
											<i class="ion ion-ios-refresh-empty"></i>
										</p>
										<p class="d-flex flex-column text-right">
											<span class="font-weight-bold">
												<i class="ion ion-android-arrow-up text-success"></i> 12%
											</span>
											<span class="text-muted">CONVERSION RATE</span>
										</p>
									</div>
									<!-- /.d-flex -->
									<div class="d-flex justify-content-between align-items-center border-bottom mb-3">
										<p class="text-warning text-xl">
											<i class="ion ion-ios-cart-outline"></i>
										</p>
										<p class="d-flex flex-column text-right">
											<span class="font-weight-bold">
												<i class="ion ion-android-arrow-up text-warning"></i> 0.8%
											</span>
											<span class="text-muted">SALES RATE</span>
										</p>
									</div>
									<!-- /.d-flex -->
									<div class="d-flex justify-content-between align-items-center mb-0">
										<p class="text-danger text-xl">
											<i class="ion ion-ios-people-outline"></i>
										</p>
										<p class="d-flex flex-column text-right">
											<span class="font-weight-bold">
												<i class="ion ion-android-arrow-down text-danger"></i> 1%
											</span>
											<span class="text-muted">REGISTRATION RATE</span>
										</p>
									</div>
									<!-- /.d-flex -->
								</div>
							</div>
						</div>
						<!-- /.col-md-6 -->
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
	<!-- overlayScrollbars -->
	<script src="${contextPath}resources/overlayScrollbars/js/jquery.overlayScrollbars.min.js"></script>
	<!-- AdminLTE -->
	<script src="${contextPath}js/adminlte.min.js"></script>

	<!-- OPTIONAL SCRIPTS -->
	<script src="${contextPath}resources/chart.js/Chart.min.js"></script>
	<script src="${contextPath}js/demo.js"></script>
	<script src="${contextPath}js/pages/dashboard/dashboard3.js"></script>
</body>
</html>