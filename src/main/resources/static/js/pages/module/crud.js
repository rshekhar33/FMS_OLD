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
		$.post({
			url : contextPath + "module/fetchData",
			contentType : "application/json",
			data : {
				moduleId : moduleId
			},
			success : function(responseObj) {
				var module = responseObj.module;

				if (module != null) {
					$("#moduleName").val(module.moduleName);
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
	$(".errMsgCls").addClass("d-none");

	$(".form-horizontal .is-invalid").removeClass("is-invalid");

	var errorObj = {
		moduleName : ""
	};

	if (isEmpty(dataObj.moduleName)) {
		errorObj.moduleName = "Mandatory Field!";
	} else if (hasRestrictedChar3(dataObj.moduleName)) {
		errorObj.moduleName = "Module Name can only have alphanumeric characters, spaces and special characters like '_ @ .'";
	}

	return showErrors(errorObj);
}

function submitFun(dataObj) {
	$.post({
		url : contextPath + "module/validateSave",
		contentType : "application/json",
		data : dataObj,
		success : function(responseObj) {
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

	var moduleFormData = $("#moduleForm").serializeToJSON({});

	var isValid = validateFun(moduleFormData);

	if (isValid) {
		submitFun(moduleFormData);
	} else {
		showLoaderRight(false);
	}
}