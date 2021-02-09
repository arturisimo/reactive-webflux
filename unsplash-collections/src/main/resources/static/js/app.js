
var sourceInit; 
var sourceSearch;

var collections = {
		
		/**
		 *  request api collection /all
		 *  @param {dom object} code : unsplash code
		 *  @param {dom object} filter : filter collections
		 *  @param {dom object} $content : to print result
		 */
		load: function(url, $content) {
			if (typeof sourceInit === "undefined") {
	    		sourceInit = new EventSource(url);
				sourceInit.addEventListener("message", function(data){
					$content.html(data);
	    		});
			}
		}, 
		
		/**
		 *  request api collection
		 *  @param {dom object} code : unsplash code
		 *  @param {dom object} filter : filter collections
		 *  @param {dom object} $content : to print result
		 */
		get: function(filter, $content, apiUrl) {
			
			$content.html("Cargando...");
			
			var url = apiUrl + (filter == '' ? '/all/' : '/' + filter);
			var c = [];
			
			if (typeof sourceSearch !== "undefined") {
				sourceSearch.close()
			}
			if (typeof sourceInit !== "undefined") {
				sourceInit.close()
			}		
			sourceSearch = new EventSource(url);
			sourceSearch.addEventListener("message", function(flow){
				c.push("<p>"+flow.data+"</p>");
				$content.html(c);
	    	});
		},
		
		close: function(){
			if (typeof sourceSearch !== "undefined") {
				sourceSearch.close()
			}
			if (typeof sourceInit !== "undefined") {
				sourceInit.close()
			}
		}
		
}



$(function(){
   	
	var $content = $(".content");
	var apiUrl = $("#url").val();
	
	collections.load("http://localhost:7000/", $content);
	collections.get("", $content, apiUrl);
	
	$("#list").click(function(){
		collections.get($("#filter").val(), $content, apiUrl);
	});
	$("#close").click(function(){
		collections.close();
	});
	
});    