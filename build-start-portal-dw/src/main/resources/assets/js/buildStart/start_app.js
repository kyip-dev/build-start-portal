'use strict';
define([ 'angular', './start' ], 
	function(angular, view1) {
	
	// Declare app level module which depends on views, and components
	return angular.module('myapp', [ 'myapp.start'])
			;
});