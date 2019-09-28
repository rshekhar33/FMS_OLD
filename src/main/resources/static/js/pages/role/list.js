var rolesDataTable = $("#rolesTable").DataTable({
	ordering : true,
	info : true,
	autoWidth : false,
	paging : true,
	responsive : true,
	pagingType : "full_numbers",
	columns : [ {
		title : "Role Name",
		data : "roleName"
	}, {
		title : "Active",
		data : "isActive",
		orderable : false,
		searchable : false
	}, {
		title : "Action",
		data : "action",
		orderable : false,
		searchable : false
	} ]
});

$(function() {
	loadData();
});

function loadData() {
	showLoaderRight(true);
	$.ajax({
		type : "POST",
		url : contextPath + "role/fetchDetails",
		success : function(data) {
			var roles = data;
			var rolesData = [];
			for (var i = 0; i < roles.length; i++) {
				var role = roles[i];
				var isActiveStr = '';
				if (activationIsAllowed) {
					isActiveStr = '<div class="ui fitted toggle checkbox"><input type="checkbox" ';
					isActiveStr += (role.isActive == 1) ? 'checked="checked" ' : '';
					isActiveStr += 'onclick="activationFun(\'' + role.roleId + '\',this)" /><label></label></div>';
				}
				var isActionStr = '';
				if (updateIsAllowed) {
					isActionStr = '<a class="btn" onclick="editFun(\'' + role.roleId + '\')"><i class="fa fa-edit"></i></a>';
				}
				rolesData.push({
					roleName : role.roleName,
					isActive : isActiveStr,
					action : isActionStr
				});
			}
			rolesDataTable.clear().rows.add(rolesData).draw();
			showLoaderRight(false);
		},
		error : function(jqXHR, textStatus, errorThrown) {
			if (jqXHR.status == 403)
				location.reload();
			return;
		}
	});
}

function editFun(roleId) {
	$("#linkId").val(roleId);
	$("#linkForm").attr("action", contextPath + "role/update");
	$("#linkForm").submit();

	return false;
}

function activationFun(roleId, checkBoxObj) {
	showLoaderRight(true);
	var isActive = "0";
	if (checkBoxObj.checked)
		isActive = "1";

	$.ajax({
		type : "POST",
		url : contextPath + "role/activation",
		data : {
			roleId : roleId,
			isActive : isActive
		},
		success : function(responseObj) {
			if (responseObj.status == "success") {
				bootbox.alert({
					message : responseObj.msg,
					backdrop : true
				});
			}
			showLoaderRight(false);
		},
		error : function(jqXHR, textStatus, errorThrown) {
			if (jqXHR.status == 403)
				location.reload();
			return;
		}
	});
}