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
		var url = "role/fetchData";
		var data = {
			roleId : roleId
		};
		var successFun = function(responseObj) {
			var role = responseObj.role;

			if (role != null) {
				$("#roleName").val(role.roleName);
			}
			showLoaderRight(false);
		};

		return callAjaxPostFun(url, data, successFun, errorFun1);
	} else {
		return $.when(null);
	}
}

function validateFun(dataObj) {
	$(".errMsgCls").html("");
	$(".errMsgCls").addClass("d-none");

	$(".validationField").removeClass("is-invalid");

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
	var url = "role/validateSave";
	var successFun = function(responseObj) {
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
	};

	return callAjaxPostFun(url, dataObj, successFun, errorFun2);
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