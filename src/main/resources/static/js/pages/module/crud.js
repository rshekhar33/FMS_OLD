$(function() {
	loadDataFun();
});

function goToListModules() {
	location.href = contextPath + "module/list";
}

function loadDataFun() {
	showLoaderRight(true);
	var moduleId = $("#moduleId").val();
	if (!isEmpty(moduleId)) {
		var url = "module/fetchData";
		var data = {
			moduleId : moduleId
		};
		var successFun = function(responseObj) {
			var module = responseObj.module;

			if (module != null) {
				$("#moduleName").val(module.moduleName);
			}
			showLoaderRight(false);
		};

		return callAjaxPostFun(url, data, successFun, errorFun1);
	} else {
		showLoaderRight(false);
		return $.when(null);
	}
}

function validateFun(dataObj) {
	$(".errMsgCls").html("");
	$(".errMsgCls").addClass("d-none");

	$(".validationField").removeClass("is-invalid");

	var errorObj = {};

	if (isEmpty(dataObj.moduleName)) {
		errorObj.moduleName = "Mandatory Field!";
	} else if (hasRestrictedChar3(dataObj.moduleName)) {
		errorObj.moduleName = "Module Name can only have alphanumeric characters, spaces and special characters like '_ @ .'";
	}

	return showErrors(errorObj);
}

function submitFun(dataObj) {
	var url = "module/validateSave";
	var successFun = function(responseObj) {
		if (responseObj.status == "success") {
			bootbox.alert({
				message : responseObj.msg,
				backdrop : true,
				callback : function() {
					goToListModules();
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

	var moduleFormData = $("#moduleForm").serializeToJSON({});

	var isValid = validateFun(moduleFormData);

	if (isValid) {
		submitFun(moduleFormData);
	} else {
		showLoaderRight(false);
	}
}