$(function() {
	loadDataFun();
});

function goToListCourseTypes() {
	location.href = contextPath + "courseType/list";
}

function showErrorMsg(selector, errorMsg) {
	$(selector).html("<i class='fa fa-times-circle-o'></i> " + errorMsg);
	$(selector).removeClass("hidden");
	$(selector).parent("div").parent(".form-group").addClass("has-error");
}

function loadDataFun() {
	showLoaderRight(true);
	var hidCourseTypeId = $("#hidCourseTypeId").val();
	if (!isBlank(hidCourseTypeId)) {
		$.ajax({
			type : "POST",
			url : contextPath + "courseType/fetchData",
			data : {
				courseTypeId : hidCourseTypeId
			},
			success : function(responseObj) {
				var courseType = responseObj.courseType;

				if (courseType != null) {
					$("#courseTypeCode").val(courseType.courseTypeCode);
					$("#courseTypeName").val(courseType.courseTypeName);
					$("#noOfDays").val(courseType.noOfDays);
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

	var hidCourseTypeId = $("#hidCourseTypeId").val();
	var courseTypeName = $("#courseTypeName").val();
	var noOfDays = $("#noOfDays").val();

	var courseTypeNameError = "";
	var noOfDaysError = "";

	if (isBlank(courseTypeName)) {
		courseTypeNameError = "Mandatory Field!";
	} else if (hasRestrictedChar3(courseTypeName)) {
		courseTypeNameError = "Course Type Name can only have alphanumeric characters, spaces and special characters like '_ @ .'";
	}
	if (!isBlank(noOfDays) && isNotNumber(noOfDays)) {
		noOfDaysError = "Must be a number!";
	}

	if (!isBlank(courseTypeNameError)) {
		validation = false;
		showErrorMsg("#courseTypeNameError", courseTypeNameError);
	}
	if (!isBlank(noOfDaysError)) {
		validation = false;
		showErrorMsg("#noOfDaysError", noOfDaysError);
	}

	if (validation) {
		$.ajax({
			type : "POST",
			url : contextPath + "courseType/validateSave",
			data : $("#courseTypeForm").serialize(),
			success : function(responseObj) {
				if (responseObj.status == "success") {
					bootbox.alert({
						message : responseObj.msg,
						backdrop : true,
						callback : function() {
							goToListCourseTypes();
						}
					});
				} else {
					if (!isBlank(responseObj.courseTypeNameError)) {
						showErrorMsg("#courseTypeNameError", responseObj.courseTypeNameError);
					}
					if (!isBlank(responseObj.noOfDaysError)) {
						showErrorMsg("#noOfDaysError", responseObj.noOfDaysError);
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