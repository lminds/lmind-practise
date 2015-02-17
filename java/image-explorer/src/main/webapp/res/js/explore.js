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
	
	var img = $("img")[0];
	var imgBox = $("#imgBox")[0];
	if (img.complete) {
		imgBox.scrollLeft = imgBox.scrollWidth;
	} else {
		img.onload = function(){
			imgBox.scrollLeft = imgBox.scrollWidth;
		}
	}
})