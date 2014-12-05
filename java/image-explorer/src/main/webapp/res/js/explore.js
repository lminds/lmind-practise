$(document).ready(function(){
	var contextPath = $("[name='contextPath']")[0].value;
	var item = $("[name='item']")[0].value;
	$("div img").each(function(a, b){
		//b.src = contextPath + "/action/content?name=" + encodeURIComponent(b.name) + "&item=" + item;
	})
	
	$("#navTo").click(function(){
		var toIndex = parseInt($("#toIndex").val()) - 1;
		document.location.href = "explore?name=" + encodeURIComponent($("#comicName").val()) + "&index=" + toIndex;
	})
})