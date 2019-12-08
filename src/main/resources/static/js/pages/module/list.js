var modulesDataTable = $("#modulesTable").DataTable({
	ordering : true,
	info : true,
	autoWidth : false,
	paging : true,
	responsive : true,
	pagingType : "full_numbers",
	columns : [ {
		title : "Module Name",
		data : "moduleName"
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
		url : contextPath + "module/fetchDetails",
		success : function(data) {
			var modules = data;
			var modulesData = [];
			for (var i = 0; i < modules.length; i++) {
				var module = modules[i];
				var isActiveStr = '';
				if (activationIsAllowed) {
					isActiveStr = '<div class="ui fitted toggle checkbox"><input type="checkbox" ';
					isActiveStr += (module.isActive == 1) ? 'checked="checked" ' : '';
					isActiveStr += 'onclick="activationFun(\'' + module.moduleId + '\',this)" /><label></label></div>';
				}
				var isActionStr = '';
				if (updateIsAllowed) {
					isActionStr = '<a class="btn" onclick="editFun(\'' + module.moduleId + '\')"><i class="fa fa-edit"></i></a>';
				}
				modulesData.push({
					moduleName : module.moduleName,
					isActive : isActiveStr,
					action : isActionStr
				});
			}
			modulesDataTable.clear().rows.add(modulesData).draw();
			showLoaderRight(false);
		},
		error : function(jqXHR, textStatus, errorThrown) {
			if (jqXHR.status == 403)
				location.reload();
			return;
		}
	});
}

function editFun(moduleId) {
	$("#linkId").val(moduleId);
	$("#linkForm").attr("action", contextPath + "module/update");
	$("#linkForm").submit();

	return false;
}

function activationFun(moduleId, checkBoxObj) {
	showLoaderRight(true);
	var isActive = "0";
	if (checkBoxObj.checked)
		isActive = "1";

	$.ajax({
		type : "POST",
		url : contextPath + "module/activation",
		data : {
			moduleId : moduleId,
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