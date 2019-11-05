$(document).ready(function() {
	var locked = false;

	$(".block").animate({
		"right" : "-58px"
	}, "slow");

	$("#pontos").mouseenter(function() {
		if (!locked) {
			$(".block").animate({
				"left" : "-5px"
			});

			locked = true;
		}
	});

	$(".navbar").mouseleave(function() {

		if (locked) {
			$(".block").animate({
				"left" : "+58px"
			}, "slow");

			locked = false;
		}
	});

	$(document).bind("ajaxSend", function() {

		updateHeader();

	}).bind("ajaxStop", function() {

		updateHeader();
	});

	try {
		$('[id*=reload]').click();
	} catch (e) {

	}
});

function updateHeader() {
	$('th:contains("Column")').addClass("btn-primary t");
	$('th:contains("Column")').html("Ações");
}

function cadastroErro() {
	var login = $('#input_login').val();
	var senha = $('#input_senha').val();

	if ((login === "")) {
		$('#erroLogin').css("visibility", "visible");
	}

	if ((senha === "")) {
		$('#erroSenha').css("visibility", "visible");
	}

	if (login !== "" && senha !== "") {
		$('#modalCadastro').modal('hide');
		return false;
	}
	return true;
}