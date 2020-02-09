$(function() {
	$("#roles").select2({
		placeholder : "Select an option"
	});
	loadDataFun();
});

function goToListUsers() {
	location.href = contextPath + "user/list";
}

function loadDataFun() {
	showLoaderRight(true);
	var userId = $("#userId").val();
	if (!isEmpty(userId)) {
		$("#userName").prop("disabled", true);
	}
	$.post({
		url : contextPath + "user/fetchData",
		contentType : "application/json",
		data : {
			userId : userId
		},
		success : function(responseObj) {
			var user = responseObj.user;
			var roles = responseObj.roles;
			var userRoleIds = responseObj.userRoleIds;

			var dropdownData = [];
			if (roles != null) {
				for (var i = 0; i < roles.length; i++) {
					var role = roles[i];
					dropdownData.push({
						id : role.roleId,
						text : role.roleName
					});
				}
			}
			if (!isEmpty(userId) && user != null) {
				$("#userName").val(user.userName);
				$("#firstName").val(user.firstName);
				$("#middleName").val(user.middleName);
				$("#lastName").val(user.lastName);
				$("#emailId").val(user.emailId);
				$("#mobileNo").val(user.mobileNo);
			}
			$("#roles").select2({
				placeholder : "Select an option",
				data : dropdownData
			});
			if (!isEmpty(userId) && userRoleIds != null) {
				$("#roles").val(userRoleIds);
				$("#roles").trigger("change");
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

function validateFun(dataObj) {
	$(".errMsgCls").html("");
	$(".errMsgCls").addClass("hidden");

	$(".has-error").removeClass("has-error");

	var errorObj = {
		userName : "",
		firstName : "",
		middleName : "",
		lastName : "",
		emailId : "",
		mobileNo : "",
		roles : ""
	};

	if (isEmpty(dataObj.userId)) {
		if (isEmpty(dataObj.userName)) {
			errorObj.userName = "Mandatory Field!";
		} else if (hasRestrictedChar2(dataObj.userName)) {
			errorObj.userName = "UserName can only have alphanumeric characters and special characters like '_ @ .'";
		}
	}
	if (isEmpty(dataObj.firstName)) {
		errorObj.firstName = "Mandatory Field!";
	} else if (!hasOnlyAlphabets(dataObj.firstName)) {
		errorObj.firstName = "First Name can only have alphabets!";
	}
	if (!isEmpty(dataObj.middleName) && !hasOnlyAlphabets(dataObj.middleName)) {
		errorObj.middleName = "Middle Name can only have alphabets!";
	}
	if (!isEmpty(dataObj.lastName) && !hasOnlyAlphabets(dataObj.lastName)) {
		errorObj.lastName = "Last Name can only have alphabets!";
	}
	if (isEmpty(dataObj.emailId)) {
		errorObj.emailId = "Mandatory Field!";
	} else if (isNotValidEmail(dataObj.emailId)) {
		errorObj.emailId = "Invalid Email!";
	}
	if (isEmpty(dataObj.mobileNo)) {
		errorObj.mobileNo = "Mandatory Field!";
	} else if (isNotNumber(dataObj.mobileNo)) {
		errorObj.mobileNo = "Must be a number!";
	} else if (dataObj.mobileNo.length != 10) {
		errorObj.mobileNo = "Must be 10 digit!";
	}
	if (isEmpty(dataObj.roles)) {
		errorObj.roles = "Mandatory Field!";
	}

	return showErrors(errorObj);
}

function submitFun(dataObj) {
	$.post({
		url : contextPath + "user/validateSave",
		contentType : "application/json",
		data : dataObj,
		success : function(responseObj) {
			if (responseObj.status == "success") {
				bootbox.alert({
					message : responseObj.msg,
					backdrop : true,
					callback : function() {
						goToListUsers();
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

	var userFormData = $("#userForm").serializeToJSON({});

	var isValid = validateFun(userFormData);

	if (isValid) {
		submitFun(userFormData);
	} else {
		showLoaderRight(false);
	}
}