$(document).ready(function () {

	var locked = false;

	$(".block").animate({
		"right": "-58px"
	}, "slow");

	$("#pontos").mouseenter(function () {
		if (!locked) {
			$(".block").animate({
				"left": "-5px"
			});

			locked = true;
		}
	});

	$(".navbar").mouseleave(function () {

		if (locked) {
			$(".block").animate({
				"left": "+58px"
			}, "slow");

			locked = false;
		}
	});

	$(document).bind("ajaxSend", function () {

		// updateHeader();

	}).bind("ajaxStop", function () {

		// updateHeader();
	});

	try {
		$('[id*=reload]').click();
	} catch (e) {

	}

	$('.cep').inputmask({
		"mask": "99999-999",
		"autoUnmask": true
	});
	$('.cnpj').inputmask({
		"mask": "99.999.999/9999-99",
		"autoUnmask": true
	});
	$('.vagaEstac').inputmask("decimal", {
		rightAlign: false
	});
	$('.vagaEstacVinc').inputmask("decimal", {
		rightAlign: false
	});
	$('.multiselect-container').css('width', '400px');
	mostrarRows();
});

function destroy() {
	var pesquisa = $('#input_idPesquisar').val();

	var table = $('.dataTable').DataTable();

	table.search(pesquisa).draw();
}

function mostrarRows() {
	let
		valor = $('#inputTipoEmpresa > input').val();

	if (valor == 'Empresa Cliente') {
		$('#empClient').addClass('active');
		$('#empVinc').removeClass('active');
		$('#empOut').removeClass('active');
	} else if (valor == 'Empresa Vinculada') {
		$('#empVinc').addClass('active');
		$('#empClient').removeClass('active');
		$('#empOut').removeClass('active');
	} else if (valor == 'Empresa Prestadora') {
		$('#empOut').addClass('active');
		$('#empVinc').removeClass('active');
		$('#empClient').removeClass('active');
	}

	$('#empClient').click(function () {
		$('#empClient').addClass('active');
		$('#empVinc').removeClass('active');
		$('#empOut').removeClass('active');
	});

	$('#empVinc').click(function () {
		$('#empVinc').addClass('active');
		$('#empClient').removeClass('active');
		$('#empOut').removeClass('active');
	});

	$('#empOut').click(function () {
		$('#empOut').addClass('active');
		$('#empVinc').removeClass('active');
		$('#empClient').removeClass('active');
	});
}

function checkboxUpdate(el) {
	/*
	 * if ($(el).val() == '') {
	 * $('input[id*="checkVagaExtra"]').prop("disabled", true);
	 * $('#btnAddVaga').prop("disabled", true); } else {
	 * $('input[id*="checkVagaExtra"]').prop("disabled", false);
	 * 
	 * if (!$('#input_checkboxVaga').is(':checked')) { if
	 * ($('#vagasInner').val() != '-1' && $('#input_idQuant').val() != '')
	 * $('#btnAddVaga').prop("disabled", false); } else { if
	 * ($('#input_idQuant').val() != '' && $('#input_idVagaDesc').val() != '')
	 * $('#btnAddVaga').prop("disabled", false); } }
	 * 
	 * if (!$('#input_checkboxVaga').is(':checked')) if ($('#vagasInner').val() ==
	 * '-1') $('#btnAddVaga').prop("disabled", true);
	 */
}

function btnAdicionarUpdate() {
	/*
	 * if ($('#input_checkboxVaga').is(':checked')) { if
	 * ($('#input_idVagaDesc').val() == '' || $('#selectAreasInner').val() ==
	 * '') $('#btnAddVaga').prop("disabled", true); else
	 * $('#btnAddVaga').prop("disabled", false);
	 * 
	 * return; }
	 * 
	 * if ($('#vagasInner').val() == '-1' || $('#selectAreasInner').val() == '' ||
	 * $('#input_idQuant').val() == '') { $('#btnAddVaga').prop("disabled",
	 * true); } else { $('#btnAddVaga').prop("disabled", false); }
	 */
}

function switchUpdate(el) {
	if ($(el).is(':checked')) {
		$('#colVagaDesc').show();
		$('#colVagas').hide();

		if ($('#selectAreasInner').val() == ''
			&& $('#input_idQuant').val() == ''
			&& $('#input_idVagaDesc').val() == '')
			$('#btnAddVaga').prop('disabled', true);
		else
			$('#btnAddVaga').prop('disabled', false);
	} else {
		$('#colVagas').show();
		$('#colVagaDesc').hide();

		if ($('#selectAreasInner').val() == ''
			&& $('#vagasInner').val() == '-1'
			&& $('#input_idQuant').val() == '')
			$('#btnAddVaga').prop('disabled', true);
		else
			$('#btnAddVaga').prop('disabled', false);

	}

}

function quantVagaUpdate(el) {
	if ($(el).val() == '') {
		$('#btnAddVaga').prop('disabled', true);
	} else {
		if (!$('#input_checkboxVaga').is(':checked')) {
			if ($('#vagasInner').val() != '-1'
				&& $('#selectAreasInner').val() != '')
				$('#btnAddVaga').prop("disabled", false);
		} else {
			if ($('#selectAreasInner').val() != ''
				&& $('#input_idVagaDesc').val() != ''
				&& $('#input_idQuant').val() != '')
				$('#btnAddVaga').prop("disabled", false);
		}
	}
}

function maskTelefone(el) {
	let
		valor = $(el).val();

	valor = valor.replace(/[(]+/g, '');
	valor = valor.replace(/[)]+/g, '');
	valor = valor.replace(/[ ]+/g, '');
	valor = valor.replace(/[-]+/g, '');

	if (valor.length <= 10)
		$(el).mask('(00) 0000-00009');
	else
		$(el).mask('(00) 0 0000-0000');
}
