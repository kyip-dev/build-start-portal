define(["moment"], function(moment) {
	function Utils() {
	}
	
	Utils.prototype = {
		getDateTime : function(time) {
			var date = moment.utc(time);
			return date.format('YYYY-MM-DD HH:mm:ss');
		},
		getCurrentTime : function() {
			return moment().utcOffset('+0800').format('YYYY-MM-DD HH:mm:ss');
		}
	};

	return new Utils();
});
