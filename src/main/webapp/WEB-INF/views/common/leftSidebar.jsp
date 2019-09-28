<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- Left side column. contains the logo and sidebar -->
<aside class="main-sidebar">
	<!-- sidebar: style can be found in sidebar.less -->
	<section class="sidebar">
		<!-- Sidebar user panel -->
		<div class="user-panel">
			<div class="pull-left image">
				<img src="${contextPath}images/user2-160x160.jpg" class="img-circle" alt="User Image">
			</div>
			<div class="pull-left info">
				<p>Alexander Pierce</p>
				<a href="#"><i class="fa fa-circle text-success"></i> Online</a>
			</div>
		</div>
		<!-- search form -->
		<form action="#" method="get" class="sidebar-form">
			<div class="input-group">
				<input type="text" name="q" class="form-control" placeholder="Search...">
				<span class="input-group-btn">
					<button type="submit" name="search" id="search-btn" class="btn btn-flat">
						<i class="fa fa-search"></i>
					</button>
				</span>
			</div>
		</form>
		<!-- /.search form -->
		<!-- sidebar menu: : style can be found in sidebar.less -->
		<ul class="sidebar-menu" data-widget="tree">
			<li class="header">MAIN NAVIGATION</li>
			<c:if test="${appAuthorization.isAccessAllowed('dashboard/dashboard')}">
			<li class="${dashboardActiveCls}">
				<a href="${contextPath}dashboard/dashboard">
					<i class="fa fa-dashboard"></i>
					<span>Dashboard</span>
				</a>
			</li>
			</c:if>
			<c:if test="${appAuthorization.isAccessAllowed('user/list') || appAuthorization.isAccessAllowed('user/add')}">
			<li class="treeview ${usersActiveCls} ${userCrudActiveCls}">
				<a href="#">
					<i class="fa fa-users"></i>
					<span>User</span>
					<span class="pull-right-container">
						<i class="fa fa-angle-left pull-right"></i>
					</span>
				</a>
				<ul class="treeview-menu">
					<c:if test="${appAuthorization.isAccessAllowed('user/list')}">
					<li class="${usersActiveCls}"><a href="${contextPath}user/list"><i class="fa fa-list"></i> List Users</a></li>
					</c:if>
					<c:if test="${appAuthorization.isAccessAllowed('user/add')}">
					<li class="${userCrudActiveCls}"><a href="${contextPath}user/add"><i class="fa fa-user-plus"></i> Add User</a></li>
					</c:if>
				</ul>
			</li>
			</c:if>
			<c:if test="${appAuthorization.isAccessAllowed('module/list') || appAuthorization.isAccessAllowed('module/add')}">
			<li class="treeview ${modulesActiveCls} ${moduleCrudActiveCls}">
				<a href="#">
					<i class="fa fa-book"></i>
					<span>Module</span>
					<span class="pull-right-container">
						<i class="fa fa-angle-left pull-right"></i>
					</span>
				</a>
				<ul class="treeview-menu">
					<c:if test="${appAuthorization.isAccessAllowed('module/list')}">
					<li class="${modulesActiveCls}"><a href="${contextPath}module/list"><i class="fa fa-list"></i> List Modules</a></li>
					</c:if>
					<c:if test="${appAuthorization.isAccessAllowed('module/add')}">
					<li class="${moduleCrudActiveCls}"><a href="${contextPath}module/add"><i class="fa fa-plus"></i> Add Module</a></li>
					</c:if>
				</ul>
			</li>
			</c:if>
			<c:if test="${appAuthorization.isAccessAllowed('courseType/list') || appAuthorization.isAccessAllowed('courseType/add')}">
			<li class="treeview ${courseTypesActiveCls} ${courseTypeCrudActiveCls}">
				<a href="#">
					<i class="fa fa-file-text-o"></i>
					<span>Course Type</span>
					<span class="pull-right-container">
						<i class="fa fa-angle-left pull-right"></i>
					</span>
				</a>
				<ul class="treeview-menu">
					<c:if test="${appAuthorization.isAccessAllowed('courseType/list')}">
					<li class="${courseTypesActiveCls}"><a href="${contextPath}courseType/list"><i class="fa fa-list"></i> List Course Type</a></li>
					</c:if>
					<c:if test="${appAuthorization.isAccessAllowed('courseType/add')}">
					<li class="${courseTypeCrudActiveCls}"><a href="${contextPath}courseType/add"><i class="fa fa-plus"></i> Add Course Type</a></li>
					</c:if>
				</ul>
			</li>
			</c:if>
			<c:if test="${appAuthorization.isAccessAllowed('role/list') || appAuthorization.isAccessAllowed('role/add')}">
			<li class="treeview ${rolesActiveCls} ${roleCrudActiveCls}">
				<a href="#">
					<i class="fa fa-wrench"></i>
					<span>Role</span>
					<span class="pull-right-container">
						<i class="fa fa-angle-left pull-right"></i>
					</span>
				</a>
				<ul class="treeview-menu">
					<c:if test="${appAuthorization.isAccessAllowed('role/list')}">
					<li class="${rolesActiveCls}"><a href="${contextPath}role/list"><i class="fa fa-list"></i> List Roles</a></li>
					</c:if>
					<c:if test="${appAuthorization.isAccessAllowed('role/add')}">
					<li class="${roleCrudActiveCls}"><a href="${contextPath}role/add"><i class="fa fa-plus"></i> Add Role</a></li>
					</c:if>
				</ul>
			</li>
			</c:if>
		</ul>
	</section>
	<!-- /.sidebar -->
</aside>