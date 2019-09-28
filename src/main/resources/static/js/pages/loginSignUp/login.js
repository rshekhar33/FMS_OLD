var LoginModalController = {
	inputElementsName : ".logmod__form .input",
	hidePasswordName : ".hide-password",

	inputElements : null,
	hidePassword : null,

	findElements : function() {
		var base = this;
		
		base.inputElements = $(base.inputElementsName);
		base.hidePassword = $(base.hidePasswordName);

		return base;
	},

	addClickEvents : function() {
		var base = this;

		base.hidePassword.on("click", function(e) {
			var $this = $(this), $pwInput = $this.prev("input");

			if ($pwInput.attr("type") == "password") {
				$pwInput.attr("type", "text");
				$this.text("Hide");
			} else {
				$pwInput.attr("type", "password");
				$this.text("Show");
			}
		});

		base.inputElements.find("label").on("click", function(e) {
			var $this = $(this), $input = $this.next("input");

			$input.focus();
		});

		return base;
	},

	initialize : function() {
		var base = this;

		base.findElements().addClickEvents();
	}
};

$(document).ready(function() {
	LoginModalController.initialize();

	$("#login").click(function() {
		var validation = true;
		$("#userNameError").text("");
		$("#loginError").text("");
		var userName = $("#userName").val();
		var password = $("#password").val();
		
		if(userName == "") {
			validation = false;
			$("#userNameError").text("Username cannot be left Empty!");
		}
		if(password == "") {
			validation = false;
			$("#loginError").text("Password cannot be left Empty!");
		}
		
		if(validation) {
			$("#loginForm").submit();
			/*$.ajax({
				type : "POST",
				url : contextPath + "loginCheck",
				data : {
					userName : userName,
					password : password
				},
				success : function(data) {
					var responseObj = data;
					if(responseObj.login == "failure") {
						location.reload();
						return;
					}
					if(responseObj.status == "success") {
						//$("#loginForm").submit();
						location.href = contextPath + "home";
					} else {
						$("#loginError").text(responseObj.errMsg);
					}
				},
				error : function(jqXHR, textStatus, errorThrown) {
					if (jqXHR.status == 403) {
						location.reload();
						return;
					}
					$("#loginError").text("Server failed to process request");
				}
			});*/
		}
	});
});