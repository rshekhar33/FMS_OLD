var usersDataTable = $("#usersTable").DataTable({
	ordering : true,
	info : true,
	autoWidth : false,
	paging : true,
	responsive : true,
	pagingType : "full_numbers",
	columns : [ {
		title : "UserName",
		data : "userName"
	}, {
		title : "Name",
		data : "fullName"
	}, {
		title : "Email ID",
		data : "emailId"
	}, {
		title : "Mobile No",
		data : "mobileNo"
	}, {
		title : "Role",
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
	$.post({
		url : contextPath + "user/fetchDetails",
		contentType : "application/json",
		success : function(data) {
			var users = data;
			var usersData = [];
			for (var i = 0; i < users.length; i++) {
				var user = users[i];
				var isActiveStr = '';
				if (activationIsAllowed) {
					isActiveStr = '<div class="ui fitted toggle checkbox"><input type="checkbox" ';
					isActiveStr += (user.isActive == 1) ? 'checked="checked" ' : '';
					isActiveStr += 'onclick="activationFun(\'' + user.userId + '\',this)" /><label></label></div>';
				}
				var isActionStr = '';
				if (updateIsAllowed) {
					isActionStr = '<a class="btn" onclick="editFun(\'' + user.userId + '\')"><i class="fa fa-edit"></i></a>';
				}
				usersData.push({
					userName : user.userName,
					fullName : user.fullName,
					emailId : user.emailId,
					mobileNo : user.mobileNo,
					roleName : user.roleName,
					isActive : isActiveStr,
					action : isActionStr
				});
			}
			usersDataTable.clear().rows.add(usersData).draw();
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

function editFun(userId) {
	$("#linkId").val(userId);
	$("#linkForm").attr("action", contextPath + "user/update");
	$("#linkForm").submit();

	return false;
}

function activationFun(userId, checkBoxObj) {
	showLoaderRight(true);
	var isActive = 0;
	if (checkBoxObj.checked) {
		isActive = 1;
	}

	$.post({
		url : contextPath + "user/activation",
		contentType : "application/json",
		data : {
			userId : userId,
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