$(document).ready(function(){
    var idmarche = $('#idmarche').val();
    // The url for our json data
    var jsonurl = "../api/InfoMarcheInverse?marche="+idmarche;
    // Our ajax data renderer which here retrieves a text file.
    // it could contact any source and pull data, however.
    // The options argument isn't used in this renderer.
    var ajaxDataRenderer = function(url, plot, options) {
	var ret = null;
	$.ajax({
	    // have to use synchronous here, else the function
	    // will return before the data is fetched
	    async: false,
	    url: url,
	    dataType:"json",
	    success: function(data) {
		ret = data;
	    }
	});
	return ret;
    }; 
    // passing in the url string as the jqPlot data argument is a handy
    // shortcut for our renderer.  You could also have used the
    // "dataRendererOptions" option to pass in the url.
    var plot2 = $.jqplot('jqplot', jsonurl,{
	title: "Variation de l'action",
	dataRenderer: ajaxDataRenderer,
	dataRendererOptions: {
	    unusedOptionalUrl: jsonurl
	}
    });
});