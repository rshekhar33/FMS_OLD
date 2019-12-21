$(function() {
	loadDataFun();
});

function goToListRoles() {
	location.href = contextPath + "role/list";
}

function showErrorMsg(selector, errorMsg) {
	$(selector).html("<i class='fa fa-times-circle-o'></i> " + errorMsg);
	$(selector).removeClass("hidden");
	$(selector).parent("div").parent(".form-group").addClass("has-error");
}

function loadDataFun() {
	showLoaderRight(true);
	var hidRoleId = $("#hidRoleId").val();
	if (!isEmpty(hidRoleId)) {
		$.ajax({
			type : "POST",
			url : contextPath + "role/fetchData",
			data : {
				roleId : hidRoleId
			},
			success : function(responseObj) {
				var role = responseObj.role;

				if (role != null) {
					$("#roleName").val(role.roleName);
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
}

function validateSubmitFun() {
	showLoaderRight(true);
	var validation = true;

	$(".errMsgCls").html("");
	$(".errMsgCls").addClass("hidden");

	$(".has-error").removeClass("has-error");

	var hidRoleId = $("#hidRoleId").val();
	var roleName = $("#roleName").val();

	var roleNameError = "";

	if (isEmpty(roleName)) {
		roleNameError = "Mandatory Field!";
	} else if (hasRestrictedChar3(roleName)) {
		roleNameError = "Role Name can only have alphanumeric characters, spaces and special characters like '_ @ .'";
	}
	if (!isEmpty(roleNameError)) {
		validation = false;
		showErrorMsg("#roleNameError", roleNameError);
	}

	if (validation) {
		$.ajax({
			type : "POST",
			url : contextPath + "role/validateSave",
			data : $("#roleForm").serialize(),
			success : function(responseObj) {
				if (responseObj.status == "success") {
					bootbox.alert({
						message : responseObj.msg,
						backdrop : true,
						callback : function() {
							goToListRoles();
						}
					});
				} else {
					if (!isEmpty(responseObj.roleNameError)) {
						showErrorMsg("#roleNameError", responseObj.roleNameError);
					}

					showLoaderRight(false);
				}
			},
			error : function(jqXHR, textStatus, errorThrown) {
				if (jqXHR.status == 403)
					location.reload();
				return;
			}
		});
	} else {
		showLoaderRight(false);
	}
}