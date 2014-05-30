/*
 * Tooltip script 
 * powered by jQuery (http://www.jquery.com)
 * 
 * written by Alen Grakalic (http://cssglobe.com)
 * 
 * for more info visit http://cssglobe.com/post/1695/easiest-tooltip-and-image-preview-using-jquery
 *
 */
 
var contextPath = "";
var content = "";
this.tooltip = function(){
	/* CONFIG */		
		xOffset = 10;
		yOffset = 20;
		// these 2 variable determine popup's distance from the cursor
		// you might want to adjust to get the right result		
	/* END CONFIG */		
	$(".tooltip").hover(function(e){
		var eventSource=$(this);
		$.ajax({
			url			: contextPath + "/helpHint/json/getHelpHintByTitleJson.action",
			type 		: "POST",
			dataType 	: "json",
			data		: {
				"title" : eventSource.attr("code")
			},
			success 	: function(data) {
				if($("#tooltip").attr("id"))
					$("#tooltip").html(data);
				else
					$("body").append("<p id='tooltip'>"+ data +"</p>");
				$("#tooltip")
					.css("top",(e.pageY - xOffset) + "px")
					.css("left",(e.pageX + yOffset) + "px")
					.fadeIn("fast");
			},
			error		:function(){
			}
		});
    	},
		function(){
			$("#tooltip").remove();
	    }
    );
	$(".tooltip").mousemove(function(e){
		$("#tooltip")
			.css("top",(e.pageY - xOffset) + "px")
			.css("left",(e.pageX + yOffset) + "px");
	});			
};

this.loadIma = function() {
	var spans = $(".tooltip");
	for(var i = 0; i < spans.length; i++) {
		var path = spans[i].getAttribute("path");
		contextPath = path;
		if (path && path!="" && path!='' && path!="undefined")
			spans[i].innerHTML="<img src='"+path+"/common/Images/question.gif' />";
		else
			spans[i].innerHTML="？";
	}
};
// starting the script on page load
$(document).ready(function(){
	loadIma();
	tooltip();
});