var urlWS = null;
var UPDATE_TIMEOUT = 1000;
var arrayIds;
var listGeral;

$(document).ready(function () {
    arrayIds = new Array();
    urlWS = $('#urlWS').val() + "/internal";
    initialLoad();
});

function cancela(){
	var url = urlWS + '/openGateWithMonitoringMap/' + $('#clientId').val();
	
	var placaProxima, tipoProxima, eixosProxima, sentidoProxima = "";
	if(listGeral[0] != null){
		placaProxima = listGeral[0].placa;
		tipoProxima = listGeral[0].idTipoProduto;
		eixosProxima = listGeral[0].qntEixosVeiculo;
		sentidoProxima = listGeral[0].way;
	} 
	
    var jsonToSend = {
    		id: $("#code").val(),
	        placa: $("#num-placa").val(),
	        tipo:  $("#select1").val(),
	        eixos: $("#num-eixos").val(),
	        sentido: $("#way").val(),
	        login: $("#login").val(),
	        placaProxima: placaProxima,
	        tipoProxima:  tipoProxima,
	        eixosProxima: eixosProxima,
	        sentidoProxima: sentidoProxima
	    };
      
    $.post(url, JSON.stringify(jsonToSend), function (json) {  
    	if(listGeral[0] != null && listGeral[0] !== undefined && arrayIds[0] != null && arrayIds[0] !== undefined){
        	buildDetails(listGeral[0]);  
        	$("#" + fixId(arrayIds[0])).remove();
    	} else{
    		clearContents();
    	}
    	
    	arrayIds.shift();
    	listGeral.shift();
    	
    	if (json !== null && json !== undefined && json !== '' && json.list != null) {
            json.list.forEach(r => {
            	updateStatus(r.statusOcupacao, r.statusVeiculo)
            });
        }
    }).done(function(){ 
    	if(listGeral[0] != null && listGeral[0] !== undefined && arrayIds[0] != null && arrayIds[0] !== undefined){
        	buildDetails(listGeral[0]); 
        	$("#" + fixId(arrayIds[0])).remove();
    	} else{
    		clearContents();
    	}
    	
    	arrayIds.shift();
    	listGeral.shift();
    });	
}

function remove(){
	var url = urlWS + '/removeMonitoringMap/' + $('#clientId').val();
	
	var jsonToSend = {
		id: $("#code").val()
	};
	
    $.post(url, JSON.stringify(jsonToSend), function () {     	
    	if(listGeral[0] != null && listGeral[0] !== undefined && arrayIds[0] != null && arrayIds[0] !== undefined){ 		
        	buildDetails(listGeral[0]);    	
        	$("#" + fixId(arrayIds[0])).remove();
    	} else{
    		clearContents();
    	}
    	
    	arrayIds.shift();
    	listGeral.shift();
    });
}

function clica(){
	var url = urlWS + '/findFromMonitoringMap/' + $('#clientId').val();
	
    var jsonToSend = {
    		id: $("#code").val(),
	        placa: $("#num-placa").val(),
	        tipo:  $("#select1").val(),
	        eixos: $("#num-eixos").val(),
	        sentido: $("#way").val(),
	        placaAnterior: $("#placaAnterior").val(),
	        tipoAnterior: $("#tipoAnterior").val(),
	        eixoAnterior: $("#eixoAnterior").val()
	    };
		
    $.post(url, JSON.stringify(jsonToSend), function (json) {
        if (json !== null && json !== undefined && json !== '' && json.list != null) {
            json.list.forEach(r => {           	
                $("#num-eixos").val(r.qntEixosVeiculo);
                $("#select1").val(r.idTipoProduto);
                
            	$("#placaAnterior").val(r.placa);
            	$("#eixoAnterior").val(r.qntEixosVeiculo);
            	$("#tipoAnterior").val(r.idTipoProduto);
            	
                var tiposProduto = r.tiposProdutoVeiculo;
                $("#select1").empty();
                
                tiposProduto.forEach(r => {
                	$("#select1").append(new Option(r.descricao, r.codigo));
                });
                
                $("#select1").val(r.idTipoProduto); 
            	
            	updateStatus(r.statusOcupacao, r.statusVeiculo)
            });
        }
    });		
}

function updateStatus(ocupacao, veiculo){
	var ocupar;
    var status = `<div id="s-status" class="veiculo-status">${veiculo}</div>`;    
    
    $("#status-car").empty();
    $('#cancela').prop('disabled', false)
    
    if(ocupacao.includes("Vagas designadas à empresa")){
    	ocupar = `<div id="s-alert" class="veiculo-alert">${ocupacao}</div>`;
    } else if(ocupacao.includes("Alocado para a vaga") || ocupacao.includes("liberada!")){
    	ocupar = `<div id="s-suceso" class="veiculo-sucesso">${ocupacao}</div>`;
    } else {
    	$('#cancela').prop('disabled', true);
    	ocupar = `<div id="s-block" class="veiculo-block">${ocupacao}</div>`;
    }
    
    $("#status-car").append(status);
    $("#status-car").append(ocupar);
}

function initialLoad() {
    listAllRegisters();
}

function listAllRegisters() {
	console.log("Lista de registro está vazia, buscando para montar diretamente");
    var url = urlWS + '/listAllFromMonitoringMap/' + $('#clientId').val();
    
    $.getJSON(url, function (json) {
        var listJson = json.list;
        
        if (listJson === null || listJson === undefined || listJson.length < 1) {
            setTimeout(() => {
            	$('.warnLoadRegTotal').show();
                listAllRegisters()
            }, UPDATE_TIMEOUT);
            return;
        } else {
        	$('.warnLoadRegTotal').hide();
        	if(listJson[0].code != $("#code").val()){              	
            	buildDetails(listJson[0]);	 		
        	}
        	
        	listJson.shift();
        	
            listJson.forEach(r => {          	
            	if(r.code != $("#code").val()){
                    var id = r.placa + "_" + r.date + "_" + r.code;               
                    $('#list').append(buildRegistro(r, fixId(id), r.placaTipo));
                    $("#" + fixId(id)).append(buildArrow(r.way));
                    arrayIds.push(id);   
            	}
            });
        }
        
        listGeral = listJson;

        setTimeout(() => {
            listRegisters()
        }, UPDATE_TIMEOUT);
    }).fail(function (error) {
        setTimeout(() => {
        	$('.warnLoadRegTotal').show();
            listAllRegisters()
        }, UPDATE_TIMEOUT);
    });
}

function buildArrow(way){
	var arrow;
	
	if(way != "0"){
		arrow = `<div class="arrow-way">
			<i class="fa fa-arrow-down arrow-detail-exit"></i>
		</div>`;
		
	} else {
		arrow = `<div class="arrow-way">
			<i class="fa fa-arrow-up arrow-detail-entry"></i>
		</div>`;
		
	}
	
	return arrow;
}

function listRegisters() {
	console.log("Listando para a fila de registros");
	var url = urlWS + '/listFromMonitoringMap/' + $('#clientId').val();
    var listIds = arrayIds.join('#');
    
    if (listIds.length == 0 || arrayIds == null || arrayIds === undefined || arrayIds.length == 0) {
        setTimeout(() => {
        	$('.warnLoadReg').show();
        	listAllRegisters()
        }, UPDATE_TIMEOUT);
        return;
    }

    var jsonToSend = {
        listIds: listIds
    };
    
    $.post(url, JSON.stringify(jsonToSend), function (json) {
        if (json !== null && json !== undefined && json !== '') {
        	$('.warnLoadReg').hide();
        	
        	if (json.list != null) {
                json.list.forEach(r => {
                	if(r.code != $("#code").val()){
                        var id = r.placa + "_" + r.date + "_" + r.code;
                        var li = buildRegistro(r, fixId(id), r.placaTipo);
                        $('#list').append(li);
                        $("#" + fixId(id)).append(buildArrow(r.way));
                        arrayIds.push(id);
                        listGeral.push(r)
                	}
                });
            }
        }
        setTimeout(() => {
            listRegisters();
        }, UPDATE_TIMEOUT);
    }).fail(function (error) {
        setTimeout(() => {
        	$('.warnLoadReg').show();
            listRegisters()
        }, UPDATE_TIMEOUT);
    });
}

function clearContents(){
	$("#car-image").remove();
	$("#plate-image").remove();
    $("#num-placa").val("");
    $("#num-eixos").val("");
    $("#passagem").val(""); 
    $("#way").val("");
    $("#code").val("");
    $("#select1").val("");
    $("#placaAnterior").val("");
    $("#select1").empty();
    $("#status-car").empty();
}

function buildDetails(register){
	clearContents();
	var tiposProduto = register.tiposProdutoVeiculo;
    updateStatus(register.statusOcupacao, register.statusVeiculo);

    if (!register.imagemCarro.startsWith("data:image"))
    	register.imagemCarro = `<img id="car-image" width="492" height="276" src="${'data:image/png;base64,' + register.imagemCarro}" alt="Carro"/>`;
    
    if (!register.imagemPlaca.startsWith("data:image"))
    	register.imagemPlaca = `<img id="plate-image" width="330" src="${'data:image/png;base64,' + register.imagemPlaca}" alt="Placa"/>`;
    
    tiposProduto.forEach(r => {
    	$("#select1").append(new Option(r.descricao, r.codigo));
    });
           
    $("#placa-anterior").val("");
    $("#veiculo-cap").append(register.imagemCarro);
    $("#placa-cap").append(register.imagemPlaca);
    $("#num-placa").val(register.placa);
    $("#num-eixos").val(register.qntEixosVeiculo);
    $("#passagem").val(register.datePadrao); 
    $("#way").val(register.way);
    $("#code").val(register.code);
    $("#select1").val(register.idTipoProduto);
    
    if(register.way == "1"){
    	 $('#num-eixos').prop('disabled', true);
    	 $('#select1').prop('disabled', true);
    } else{
	 $('#num-eixos').prop('disabled', false);
	 $('#select1').prop('disabled', false);
    }
}

function fixId(id){
	var idSplit = id.split("_");
	return idSplit[0] + "_" + idSplit[2];
}

function buildRegistro(registro, id, tipo) {	
    var li;
   
    if(tipo == "mercosul"){
    	li = `<div id="${id}" class="plate-mercosul-main">
			<div class="plate-mercosul-date">${registro.datePadrao}</div>
			<div class="plate-mercosul-number">${registro.placa}</div>
	</div>`;
    } else {
    	li = `<div id="${id}" class="plate-normal-main">
			<div class="plate-normal-date">${registro.datePadrao}</div>
			<div class="plate-normal-number">${registro.placa}</div>
	</div>`;
    }
    return li;
}