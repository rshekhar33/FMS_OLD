var courseTypesDataTable = $("#courseTypesTable").DataTable({
	ordering : true,
	info : true,
	autoWidth : false,
	paging : true,
	responsive : true,
	pagingType : "full_numbers",
	columns : [ {
		title : "Course Type Code",
		data : "courseTypeCode"
	}, {
		title : "Course Type Name",
		data : "courseTypeName"
	}, {
		title : "No. Of Days",
		data : "noOfDays"
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
	$.post({
		url : contextPath + "courseType/fetchDetails",
		contentType : "application/json",
		success : function(data) {
			var courseTypes = data;
			var courseTypesData = [];
			for (var i = 0; i < courseTypes.length; i++) {
				var courseType = courseTypes[i];
				var isActiveStr = '';
				if (activationIsAllowed) {
					isActiveStr = '<div class="ui fitted toggle checkbox"><input type="checkbox" ';
					isActiveStr += (courseType.isActive == 1) ? 'checked="checked" ' : '';
					isActiveStr += 'onclick="activationFun(\'' + courseType.courseTypeId + '\',this)" /><label></label></div>';
				}
				var isActionStr = '';
				if (updateIsAllowed) {
					isActionStr = '<a class="btn" onclick="editFun(\'' + courseType.courseTypeId + '\')"><i class="fas fa-edit"></i></a>';
				}
				courseTypesData.push({
					courseTypeCode : courseType.courseTypeCode,
					courseTypeName : courseType.courseTypeName,
					noOfDays : courseType.noOfDays,
					isActive : isActiveStr,
					action : isActionStr
				});
			}
			courseTypesDataTable.clear().rows.add(courseTypesData).draw();
			showLoaderRight(false);
		},
		error : function(jqXHR, textStatus, errorThrown) {
			if (jqXHR.status == 403) {
				location.reload();
			}
			return;
		}
	});
}

function editFun(courseTypeId) {
	$("#linkId").val(courseTypeId);
	$("#linkForm").attr("action", contextPath + "courseType/update");
	$("#linkForm").submit();

	return false;
}

function activationFun(courseTypeId, checkBoxObj) {
	showLoaderRight(true);
	var isActive = 0;
	if (checkBoxObj.checked) {
		isActive = 1;
	}

	$.post({
		url : contextPath + "courseType/activation",
		contentType : "application/json",
		data : {
			courseTypeId : courseTypeId,
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
			if (jqXHR.status == 403) {
				location.reload();
			}
			return;
		}
	});
}