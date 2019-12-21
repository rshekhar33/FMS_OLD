$(function() {
	$("#register").click(function() {
		var validation = true;
		$("#userNameError").text("");
		$("#firstNameError").text("");
		$("#middleNameError").text("");
		$("#lastNameError").text("");
		$("#passwordError").text("");
		$("#passwordRepeatError").text("");
		$("#emailIdError").text("");
		$("#mobileNoError").text("");
		var userName = $("#userName").val();
		var firstName = $("#firstName").val();
		var middleName = $("#middleName").val();
		var lastName = $("#lastName").val();
		var password = $("#password").val();
		var passwordRepeat = $("#passwordRepeat").val();
		var emailId = $("#emailId").val();
		var mobileNo = $("#mobileNo").val();

		if (isEmpty(userName)) {
			validation = false;
			$("#userNameError").text("Mandatory Field!");
		}
		if (isEmpty(firstName)) {
			validation = false;
			$("#firstNameError").text("Mandatory Field!");
		}
		if (isEmpty(middleName)) {
			validation = false;
			$("#middleNameError").text("Mandatory Field!");
		}
		if (isEmpty(lastName)) {
			validation = false;
			$("#lastNameError").text("Mandatory Field!");
		}
		if (isEmpty(password)) {
			validation = false;
			$("#passwordError").text("Mandatory Field!");
		}
		if (isEmpty(passwordRepeat)) {
			validation = false;
			$("#passwordRepeatError").text("Mandatory Field!");
		}
		if (!isEmpty(password) && !isEmpty(passwordRepeat) && password != passwordRepeat) {
			validation = false;
			$("#passwordRepeatError").text("Password Not Matching!");
		}
		if (isEmpty(emailId)) {
			validation = false;
			$("#emailIdError").text("Mandatory Field!");
		} else if (isNotValidEmail(emailId)) {
			validation = false;
			$("#emailIdError").text("Invalid Email!");
		}
		if (isEmpty(mobileNo)) {
			validation = false;
			$("#mobileNoError").text("Mandatory Field!");
		} else if (isNotNumber(mobileNo)) {
			validation = false;
			$("#mobileNoError").text("Must be a number!");
		} else if (mobileNo.length != 10) {
			validation = false;
			$("#mobileNoError").text("Must be 10 digit!");
		}

		if (validation) {
			$.ajax({
				type : "POST",
				url : contextPath + "validateSaveRegistration",
				data : $("#regForm").serialize(),
				success : function(data) {
					var responseObj = data;
					if (responseObj.status == "success") {
						$("#successMsg").text("Registration Completed Succesfully.");
						$("#regForm")[0].reset();
						setTimeout(function() {
							location.href = contextPath;
						}, 1000);
					} else {
						$("#userNameError").text(responseObj.userNameError);
						$("#firstNameError").text(responseObj.firstNameError);
						$("#passwordError").text(responseObj.passwordError);
						$("#emailIdError").text(responseObj.userEmailError);
					}
				},
				error : function(jqXHR, textStatus, errorThrown) {
					if (jqXHR.status == 403)
						location.reload();
					return;
				}
			});
		}
	});
});