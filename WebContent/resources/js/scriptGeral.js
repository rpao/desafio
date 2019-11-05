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

	$('[id*=reload]').click();
});

function destroy() {
	var pesquisa = $('#input_idPesquisar').val();

	var table = $('.dataTable').DataTable();

	table.search(pesquisa).draw();
}