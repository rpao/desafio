$(document).ready(function() {
	$('#menu-toggle').click(function(e) {
		e.preventDefault()
		$("#wrapper").toggleClass('active')
		
		$('#barraInferior').toggleClass('large')
		$('#divSaldo').toggleClass('smaller')
		$('#operacoesTela').toggleClass('smaller')
		$('#pagamentosTela').toggleClass('smaller')
	})
})