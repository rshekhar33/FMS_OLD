$(function() {
	loadDataFun();
});

function goToListCourseTypes() {
	location.href = contextPath + "courseType/list";
}

function loadDataFun() {
	showLoaderRight(true);
	var courseTypeId = $("#courseTypeId").val();
	if (!isEmpty(courseTypeId)) {
		$.post({
			url : contextPath + "courseType/fetchData",
			contentType : "application/json",
			data : {
				courseTypeId : courseTypeId
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

function validateFun(dataObj) {
	$(".errMsgCls").html("");
	$(".errMsgCls").addClass("d-none");

	$(".form-horizontal .is-invalid").removeClass("is-invalid");

	var errorObj = {
		courseTypeName : "",
		noOfDays : ""
	};

	if (isEmpty(dataObj.courseTypeName)) {
		errorObj.courseTypeName = "Mandatory Field!";
	} else if (hasRestrictedChar3(dataObj.courseTypeName)) {
		errorObj.courseTypeName = "Course Type Name can only have alphanumeric characters, spaces and special characters like '_ @ .'";
	}
	if (!isEmpty(dataObj.noOfDays) && isNotNumber(dataObj.noOfDays)) {
		errorObj.noOfDays = "Must be a number!";
	}

	return showErrors(errorObj);
}

function submitFun(dataObj) {
	$.post({
		url : contextPath + "courseType/validateSave",
		contentType : "application/json",
		data : dataObj,
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

	var courseTypeFormData = $("#courseTypeForm").serializeToJSON({});

	var isValid = validateFun(courseTypeFormData);

	if (isValid) {
		submitFun(courseTypeFormData);
	} else {
		showLoaderRight(false);
	}
}