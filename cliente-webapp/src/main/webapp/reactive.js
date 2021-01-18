 var dias = "";
 var acciones = "";
 var libro = "";
 var messages = "";
 var movs = [];
    
    $(function(){
    	var sourceDias;
    	var sourceAcciones;
    	var sourceQuijote;
    	var sourceMessages;
    	
    	/**
		 * objeto EventSource para consumir el flujo de datos
		 * La interfaz EventSource se utiliza para recibir eventos del servidor. Se realiza la conexión a un servidor sobre HTTP y se reciben eventos en formato text/event-stream sin tener que cerrar la conexión.
		 */
    	
    	$(".suscribir").click(function(){
    		var url = $(this).parent().find(".url").html();
    		console.log("GET: ",url);
    		if ($(this).attr("id") == 'suscribir_dias') {
    			sourceDias = new EventSource(url);
    		
	    		sourceDias.addEventListener("message", function(e){
	    			dias += e.data + "<br>"
	    			$("#content_dias").html(dias);
	    		});
    		}
    		
    		if ($(this).attr("id") == 'suscribir_acciones') {
    			
    			sourceAcciones = new EventSource(url);
    			
    			sourceAcciones.addEventListener("message", function(flow){
    				var a = JSON.parse(flow.data);
    				
    				if (movs.filter(mov => a.id === mov.id).length == 0){
        				acciones += "<p><strong>Empresa: </strong>"+a.company+" <strong>Valor: </strong>" + a.valor + "</p>";
        				movs.push(a);
        			} 
    				$("#content_acciones").html(acciones);
	    		});
    			
    		}
    		
    		if ($(this).attr("id") == 'suscribir_quijote') {
    			sourceQuijote = new EventSource(url);
    		
    			sourceQuijote.addEventListener("message", function(e){
    				var quote = JSON.parse(e.data);
            		libro += "<p><strong><a href='"+url+"/"+quote.id+"' target='_blank'>"+quote.id+"</a> | </strong>" + quote.content + "<p>";
    				$("#content_quijote").html(libro);
	    		});
    		}
    		
    		if ($(this).attr("id") == 'suscribir_messages') {
    			sourceMessages = new EventSource(url);
    			
    			sourceMessages.addEventListener("message", function(flow){
    				var msg = JSON.parse(flow.data);
    				messages +="<p><strong>"+msg.author+"</strong><br>"+msg.content+"</p>"; 
    				$("#content_messages").html(messages);
	    		});
    		}
    		
    	});
    	
    	$(".cancelar").click(function(){
    		if ($(this).attr("id") == 'cancelar_dias') {
    			sourceDias.close();
    			$("#content_dias").html("Servicio cancelado");
    		}
    		if ($(this).attr("id") == 'cancelar_acciones') {
    			sourceAcciones.close();
    			$("#content_acciones").html("Servicio cancelado");
    		}
    		if ($(this).attr("id") == 'cancelar_quijote') {
    			sourceQuijote.close();
    			$("#content_quijote").html("Servicio cancelado");
    		}
    		if ($(this).attr("id") == 'cancelar_messages') {
    			sourceMessages.close();
    			$("#content_messages").html("Servicio cancelado");
    		}
    	});
    	
    	
    });
