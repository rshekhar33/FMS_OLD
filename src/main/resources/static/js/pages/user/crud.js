$(function() {
	$("#roles").select2({
		placeholder : "Select an option"
	});
	loadDataFun();
});

function goToListUsers() {
	location.href = contextPath + "user/list";
}

function showErrorMsg(selector, errorMsg) {
	$(selector).html("<i class='fa fa-times-circle-o'></i> " + errorMsg);
	$(selector).removeClass("hidden");
	$(selector).parent("div").parent(".form-group").addClass("has-error");
}

function loadDataFun() {
	showLoaderRight(true);
	var hidUserId = $("#hidUserId").val();
	if (!isBlank(hidUserId)) {
		$("#userName").prop("disabled", true);
	}
	$.ajax({
		type : "POST",
		url : contextPath + "user/fetchData",
		data : {
			userId : hidUserId
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
			if (!isBlank(hidUserId) && user != null) {
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
			if (!isBlank(hidUserId) && userRoleIds != null) {
				$("#roles").val(userRoleIds);
				$("#roles").trigger("change");
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

function validateSubmitFun() {
	showLoaderRight(true);
	var validation = true;

	$(".errMsgCls").html("");
	$(".errMsgCls").addClass("hidden");

	$(".has-error").removeClass("has-error");

	$("#rolesStr").val($("#roles").val().toString());

	var hidUserId = $("#hidUserId").val();
	var userName = $("#userName").val();
	var firstName = $("#firstName").val();
	var middleName = $("#middleName").val();
	var lastName = $("#lastName").val();
	var emailId = $("#emailId").val();
	var mobileNo = $("#mobileNo").val();
	var rolesStr = $("#rolesStr").val();

	var userNameError = "";
	var firstNameError = "";
	var middleNameError = "";
	var lastNameError = "";
	var emailIdError = "";
	var mobileNoError = "";
	var rolesError = "";

	if (isBlank(hidUserId)) {
		if (isBlank(userName)) {
			userNameError = "Mandatory Field!";
		} else if (hasRestrictedChar2(userName)) {
			userNameError = "UserName can only have alphanumeric characters and special characters like '_ @ .'";
		}
	}
	if (isBlank(firstName)) {
		firstNameError = "Mandatory Field!";
	} else if (!hasOnlyAlphabets(firstName)) {
		firstNameError = "First Name can only have alphabets!";
	}
	if (!isBlank(middleName) && !hasOnlyAlphabets(middleName)) {
		middleNameError = "Middle Name can only have alphabets!";
	}
	if (!isBlank(lastName) && !hasOnlyAlphabets(lastName)) {
		lastNameError = "Last Name can only have alphabets!";
	}
	if (isBlank(emailId)) {
		emailIdError = "Mandatory Field!";
	} else if (isNotValidEmail(emailId)) {
		emailIdError = "Invalid Email!";
	}
	if (isBlank(mobileNo)) {
		mobileNoError = "Mandatory Field!";
	} else if (isNotNumber(mobileNo)) {
		mobileNoError = "Must be a number!";
	} else if (mobileNo.length != 10) {
		mobileNoError = "Must be 10 digit!";
	}
	if (isBlank(rolesStr)) {
		rolesError = "Mandatory Field!";
	}

	if (!isBlank(userNameError)) {
		validation = false;
		showErrorMsg("#userNameError", userNameError);
	}
	if (!isBlank(firstNameError)) {
		validation = false;
		showErrorMsg("#firstNameError", firstNameError);
	}
	if (!isBlank(middleNameError)) {
		validation = false;
		showErrorMsg("#middleNameError", middleNameError);
	}
	if (!isBlank(lastNameError)) {
		validation = false;
		showErrorMsg("#lastNameError", lastNameError);
	}
	if (!isBlank(emailIdError)) {
		validation = false;
		showErrorMsg("#emailIdError", emailIdError);
	}
	if (!isBlank(mobileNoError)) {
		validation = false;
		showErrorMsg("#mobileNoError", mobileNoError);
	}
	if (!isBlank(rolesError)) {
		validation = false;
		showErrorMsg("#rolesError", rolesError);
	}

	if (validation) {
		$.ajax({
			type : "POST",
			url : contextPath + "user/validateSave",
			data : $("#userForm").serialize(),
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
					if (!isBlank(responseObj.userNameError)) {
						showErrorMsg("#userNameError", responseObj.userNameError);
					}
					if (!isBlank(responseObj.firstNameError)) {
						showErrorMsg("#firstNameError", responseObj.firstNameError);
					}
					if (!isBlank(responseObj.middleNameError)) {
						showErrorMsg("#middleNameError", responseObj.middleNameError);
					}
					if (!isBlank(responseObj.lastNameError)) {
						showErrorMsg("#lastNameError", responseObj.lastNameError);
					}
					if (!isBlank(responseObj.emailIdError)) {
						showErrorMsg("#emailIdError", responseObj.emailIdError);
					}
					if (!isBlank(responseObj.mobileNoError)) {
						showErrorMsg("#mobileNoError", responseObj.mobileNoError);
					}
					if (!isBlank(responseObj.rolesError)) {
						showErrorMsg("#rolesError", responseObj.rolesError);
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