define([
	'angular',
	'dateUtils',
	'jquery',
	'./build-start-service-client',
	'angular-route',
	], function(angular, utils, $, buildServiceClient) {
	//(function(){
	
	'use strict';
	
	/**
	 * Main application modul
	 */
	var app = angular.module('myapp.build', ['ngRoute'], function($locationProvider){
	    $locationProvider.html5Mode(true);
	})
	.config([ '$routeProvider', function($routeProvider) {
		$routeProvider.when('/build', {
			//templateUrl : 'view1/view1.html',
			controller : MainController
		});
	} ])
	;
	
	/**
	 * Main API call dispatcher service
	 */
	app.service('jsonService', ['$http', jsonService]);
	
	/**
	 * Main homepage controller
	 */
	app.controller('MainController', ['$scope', 'jsonService', '$location', MainController]);
	
	
	/**
	 * Main controller function declaration
	 * @param $scope        - the scope of the application model
	 * @param jsonService   - HTTP call service dispatcher
	 * @constructor         -
	 */
	function MainController($scope, jsonService, $location) {
		$scope.isActive = function(route) {
	        return route === $location.path();
	    }
		$scope.redirect = function(route) {
			var base = $location.protocol() + "://" + $location.host() + ":" + $location.port();
			window.location.assign(base + route);
		}
		
		$scope.skipTest= {
			ids: {"": false}
		};

	    // Save the API data in the main application scope
		$scope.getData = (function() {
	    	//jsonService.getJSON($scope);
	    	jsonService.getBuildProjects($scope);
		}());

		$scope.buildProject = function(projectName, event) {
	    	var skipTest = false;
			for (var key in $scope.skipTest.ids) {
				if ($scope.skipTest.ids.hasOwnProperty(key) || key === projectName) {
					skipTest = $scope.skipTest.ids[key];
				}
			}
			jsonService.buildProject($scope, projectName, skipTest, event);
		}
	}
	
	app.controller('ProjectController', function($scope){
		this.allProjects = $scope.d;
	});
	
	function updateBuildStatus($scope, projectName, status) {
		var currentdate = utils.getCurrentTime();
		$scope.$apply(function () {
			$scope.d.projects.forEach(function(project) {
				if (project.name === projectName) {
					project.lastSuccess = currentdate;
					project.buildStatus = status;
				}
			});
		});
	}

	
	function stopLoading(btnId) {
		$("#" +btnId).removeClass("active");
		$("#" +btnId).removeClass("disabled");
	}
	
	/**
	 * Main HTTP calls service
	 * 
	 * @param $http -
	 *            HTTP modul
	 */
	function jsonService($http) {
		/*
		this.getJSON = function($scope) {
			var url = '/kyip/v1/build/projects';
	
			$http.get(url)
				.success(function(result) {
					$scope.d = result;
	                console.log(result);
				})
				.error(function(err){
					console.log('Default empty page');
				});
		};*/
		
		this.getBuildProjects = function($scope) {
			buildServiceClient.getBuildProjects().done(function(response) {
				$scope.$apply(function () {
					$scope.d = response;
					console.log(response);
				});
			}).fail(function(jqXHR, textStatus) {
				console.log("Request failed: " + textStatus);
			});
		};
		
		
		this.buildProject = function buildService($scope, projectName, skipTest, event) {
			buildServiceClient.buildProject(projectName, skipTest).done(function(response) {
				updateBuildStatus($scope, projectName, "success");
				stopLoading(event.target.id);
				alert(projectName + ' Build Success');
			}).fail(function(jqXHR, textStatus) {
				console.log("Request failed: " + textStatus);
				updateBuildStatus($scope, projectName, "fail");
				stopLoading(event.target.id);
			});
		};
		
	}
	
	//})();

});