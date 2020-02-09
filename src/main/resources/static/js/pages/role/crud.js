$(function() {
	loadDataFun();
});

function goToListRoles() {
	location.href = contextPath + "role/list";
}

function loadDataFun() {
	showLoaderRight(true);
	var roleId = $("#roleId").val();
	if (!isEmpty(roleId)) {
		$.post({
			url : contextPath + "role/fetchData",
			contentType : "application/json",
			data : {
				roleId : roleId
			},
			success : function(responseObj) {
				var role = responseObj.role;

				if (role != null) {
					$("#roleName").val(role.roleName);
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
}

function validateFun(dataObj) {
	$(".errMsgCls").html("");
	$(".errMsgCls").addClass("hidden");

	$(".has-error").removeClass("has-error");

	var errorObj = {
		roleName : ""
	};

	if (isEmpty(dataObj.roleName)) {
		errorObj.roleName = "Mandatory Field!";
	} else if (hasRestrictedChar3(dataObj.roleName)) {
		errorObj.roleName = "Role Name can only have alphanumeric characters, spaces and special characters like '_ @ .'";
	}

	return showErrors(errorObj);
}

function submitFun(dataObj) {
	$.post({
		url : contextPath + "role/validateSave",
		data : dataObj,
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
				showLoaderRight(false);
			}
		},
		error : function(jqXHR, textStatus, errorThrown) {
			if (jqXHR.status == 403) {
				location.reload();
			} else {
				var errorObj = jqXHR.responseJSON;
				if (errorObj.status == "fail") {
					showErrors(errorObj);
				}
				showLoaderRight(false);
			}
			return;
		}
	});
}

function validateSubmitFun() {
	showLoaderRight(true);

	var roleFormData = $("#roleForm").serializeToJSON({});

	var isValid = validateFun(roleFormData);

	if (isValid) {
		submitFun(roleFormData);
	} else {
		showLoaderRight(false);
	}
}