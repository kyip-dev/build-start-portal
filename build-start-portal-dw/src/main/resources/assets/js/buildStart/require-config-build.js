requirejs.config({
	"baseUrl" : "/assets/js",
	"paths" : {
		"jquery" : "lib/jquery-2.1.4.min",
		"bootstrap" : "lib/bootstrap-3.3.5.min",
		"moment": "lib/moment.min",
		"moment-timezone": "lib/moment-timezone.min",
		"angular": "//ajax.googleapis.com/ajax/libs/angularjs/1.3.15/angular",
		"angular-route": "//ajax.googleapis.com/ajax/libs/angularjs/1.3.0/angular-route",
		"ui-bootstrap": "//cdnjs.cloudflare.com/ajax/libs/angular-ui-bootstrap/0.13.0/ui-bootstrap-tpls.min",
		"dateUtils": "utils/dateUtils",
		"startLog": "utils/startLog"
	},
	
	shim: {
		"bootstrap" : ["jquery"],
		"angular": {
		    "exports": "angular"
		},
		"angular-route" : ["angular"]
    },
    priority:['angular']
});

require([
	'angular',
	'./buildStart/build_app'
	], function(angular, app) {
		var $html = angular.element(document.getElementsByTagName('html')[0]);
		angular.element().ready(function() {
		// bootstrap the app manually
		angular.bootstrap(document, ['myapp']);
		});
	}
);