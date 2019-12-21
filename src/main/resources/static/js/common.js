$(function() {
	/* Code to include token in all Ajax headers */
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");
	$(document).ajaxSend(function(e, xhr, options) {
		xhr.setRequestHeader(header, token);
	});
});

function isEmpty(fieldVar) {
	if (typeof fieldVar === 'undefined')
		return true;
	else if ((typeof fieldVar === 'string' || fieldVar instanceof String) && fieldVar.trim() == "")
		return true;
	else if (fieldVar)
		return false;
	return true;
}

function isNotNumber(numberVar) {
	var regex = /^\d+$/;
	return !regex.test(numberVar);
}

function isNotValidEmail(emailVar) {
	var regex = /^(([^<>()\[\]\.,;:\s@\"]+(\.[^<>()\[\]\.,;:\s@\"]+)*)|(\".+\"))@(([^<>()[\]\.,;:\s@\"]+\.)+[^<>()[\]\.,;:\s@\"]{2,})$/i;
	return !regex.test(emailVar);
}

function hasOnlyAlphabets(fieldVar) {
	var regex = /^[a-zA-Z]+$/;
	return regex.test(fieldVar);
}

function hasSpecialChar(fieldVar) {
	var regex = /[*|\":<>[\]{}`\\()';@&$]/;
	return regex.test(fieldVar);
}

function hasRestrictedChar1(fieldVar) {
	var regex = /\W/;
	return regex.test(fieldVar);
}

function hasRestrictedChar2(fieldVar) {
	var regex = /^[\w.@]+$/;
	return !regex.test(fieldVar);
}

function hasRestrictedChar3(fieldVar) {
	var regex = /^[\w.@ ]+$/;
	return !regex.test(fieldVar);
}

function showLoader(condition) {
	/*if (condition)
		$("#loading").show();
	else
		$("#loading").hide();*/
}

function showLoaderRight(condition) {
	/*if (condition)
		$("#loadingRight").show();
	else
		$("#loadingRight").hide();*/
}