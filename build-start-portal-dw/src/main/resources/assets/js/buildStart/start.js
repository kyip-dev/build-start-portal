/*
define(function(require) {
	var $ = require('jquery'),
		angular = require('angular'),
		utils = require('../utils/utils'),
		startLog = require('../utils/startLog');
	*/	
	//(function(){
define([
	'angular',
	'dateUtils',
	'jquery',
	'./build-start-service-client',
	'startLog',
	'angular-route',
	], function(angular, utils, $, startServiceClient) {
	
	'use strict';

	/**
	 * Main application modul
	 */
	var app = angular.module('myapp.start', [], function($locationProvider){
	    $locationProvider.html5Mode(true);
	});
	
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
		
		$scope.profiles= {
			ids: {"": ""}
		};
	
	    // Save the API data in the main application scope
	    $scope.getData = (function() {
	    	alert('in start js');
	        jsonService.getStartProjects($scope);
	        jsonService.getProfiles($scope);
	    }());
	
	    
	    $scope.startStopProject = function(projectName, event) {
	    	var profile;
	    	for (var key in $scope.profiles.ids) {
				if ($scope.profiles.ids.hasOwnProperty(key) || key === projectName) {
					profile = $scope.profiles.ids[key];
				}
			}
	    	startStopService($scope, projectName, profile, event);
		}
	    
	    $scope.showLog = function(projectName) {
	    	console.log('showLog');
	    	showLogService(projectName);
		}
	
	}
	
	app.controller('ProjectController', function($scope){
	    this.allProjects = $scope.d;
	});
	
	function startStopService($scope, projectName, profile, event) {
		var profileJson = profile === ''? null : profile;
		var request = { profile : profileJson };
		alert(JSON.stringify(request));
		
		startServiceClient.startStopProject(projectName, request).done(function(response) {
			$scope.$apply(function () {
				$scope.s.projects.forEach(function(project) {
					if (project.name === projectName) {
						project.started = !project.started;
					}
					if (!project.started) {
						project.pid = null;
					}
				});
			});
			
			stopLoading(event.target.id);
			
		}).fail(function(jqXHR, textStatus) {
			console.log("Request failed: " + textStatus);
			console.log('fail to start' + projectName);
			stopLoading(event.target.id);
		});
		
		/*
		$.ajax({
			url: "/kyip/v1/start/projects/" + projectName,
			type: "POST",
			contentType: "application/json",
			data: JSON.stringify(request),
			dataType: 'json',
			success: function(data) {
				$scope.$apply(function () {
					$scope.s.projects.forEach(function(project) {
						if (project.name === projectName) {
							project.started = !project.started;
						}
						if (!project.started) {
							project.pid = null;
						}
					});
				});
				
				stopLoading(event.target.id);
			},
			error : function(data) {
				console.log('fail to start' + projectName);
				stopLoading(event.target.id);
			}
		});
		*/
	}
	
	
	function showLogService(projectName) {
		console.log('send ajax show log');
		startServiceClient.showLogs(projectName).done(function(response) {
			console.log('success sending log request for ' + projectName);
		}).fail(function(jqXHR, textStatus) {
			console.log("Request failed: " + textStatus);
			console.log('fail to send log request for ' + projectName);
		});
		
		
		/*
		$.ajax({
			url: "/kyip/v1/start/projects/" + projectName + "/logs",
			type: "GET",
			contentType: "application/json",
			dataType: 'json',
			success: function(data) {
				console.log('success sending log request for ' + projectName);
			},
			error : function(data) {
				console.log('fail to send log request for ' + projectName);
			}
		});
		*/
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
		this.getStartProjects = function($scope) {
			startServiceClient.getStartProjects().done(function(response) {
				$scope.$apply(function () {
					$scope.s = response;
					console.log(response);
				});
			}).fail(function(jqXHR, textStatus) {
				console.log("Request failed: " + textStatus);
			});
		};
		
		this.getProfiles = function($scope) {
			$scope.prop = {"type": "select", 
					"name": "Profiles",
				    "value": "localhost", 
				    "values": [] 
			};
			startServiceClient.getProfiles().done(function(response) {
				$scope.$apply(function () {
					$scope.prop.values = response.profiles;
				});
			}).fail(function(jqXHR, textStatus) {
				console.log("Request failed: " + textStatus);
			});
		};

	}
	
	//})();

});