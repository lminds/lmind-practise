(function() {
	
	var COLLECTOR_URL = 'http://127.0.0.1:8888/collect';
	
	function collect() {
		var data = {};

		data.userAgent = navigator.userAgent;
		data.referrer = document.referrer;

		data.location = {
			pathname : location.pathname,
			host : location.host,
			hostname : location.hostname,
			href : location.href
		};

		if (typeof performance == 'object') {
			data.performance = {
				timing : performance.timing
			};
			data.performance.timing = performance.timing;
		}
		
		return data;
	}
	
	if (typeof jQuery != 'function') {
		return;
	}
	
	$(document).ready(function(){
		var data = collect();
		$.ajax({
			url : COLLECTOR_URL,
			type : 'POST',
			data : {
				data : JSON.stringify(data)
			}
		});
	});
	
})();