$(function() {
	loadDataFun();
});

function goToListModules() {
	location.href = contextPath + "module/list";
}

function showErrorMsg(selector, errorMsg) {
	$(selector).html("<i class='fa fa-times-circle-o'></i> " + errorMsg);
	$(selector).removeClass("hidden");
	$(selector).parent("div").parent(".form-group").addClass("has-error");
}

function loadDataFun() {
	showLoaderRight(true);
	var hidModuleId = $("#hidModuleId").val();
	if (!isBlank(hidModuleId)) {
		$.ajax({
			type : "POST",
			url : contextPath + "module/fetchData",
			data : {
				moduleId : hidModuleId
			},
			success : function(responseObj) {
				var module = responseObj.module;

				if (module != null) {
					$("#moduleName").val(module.moduleName);
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

	var hidModuleId = $("#hidModuleId").val();
	var moduleName = $("#moduleName").val();

	var moduleNameError = "";

	if (isBlank(moduleName)) {
		moduleNameError = "Mandatory Field!";
	} else if (hasRestrictedChar3(moduleName)) {
		moduleNameError = "Module Name can only have alphanumeric characters, spaces and special characters like '_ @ .'";
	}
	if (!isBlank(moduleNameError)) {
		validation = false;
		showErrorMsg("#moduleNameError", moduleNameError);
	}

	if (validation) {
		$.ajax({
			type : "POST",
			url : contextPath + "module/validateSave",
			data : $("#moduleForm").serialize(),
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
					if (!isBlank(responseObj.moduleNameError)) {
						showErrorMsg("#moduleNameError", responseObj.moduleNameError);
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