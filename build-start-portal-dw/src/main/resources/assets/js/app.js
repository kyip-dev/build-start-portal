(function(){

'use strict';

/**
 * Main application modul
 */
var app = angular.module('app', ['ui.bootstrap'], function($locationProvider){
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
	
	$scope.skipTest= {
		ids: {"": false}
	};
	$scope.profiles= {
		ids: {"": ""}
	};

    // Save the API data in the main application scope
    $scope.getData = (function() {
        jsonService.getJSON($scope);
        jsonService.getStartJSON($scope);
        jsonService.getProfileJSON($scope);
    }());

    $scope.buildProject = function(projectName, event) {
    	var skipTest = false;
		for (var key in $scope.skipTest.ids) {
			if ($scope.skipTest.ids.hasOwnProperty(key) || key === projectName) {
				skipTest = $scope.skipTest.ids[key];
			}
		}
    	buildService($scope, projectName, skipTest, event);
	}
    
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


function buildService($scope, projectName, skipTest, event) {
	$.ajax({
		url: "/kyip/v1/build/projects/" + projectName,
		type: "GET",
		//data: JSON.stringify(request),
		data: "skipTest=" + skipTest,
		contentType: "application/json",
		dataType: 'json',
		success: function(data) {
			// set last success time
			//var currentdate = JSON.stringify(new Date()).replace(/"/g, '');
			//var currentdate = Utils.prototype.getCurrentTime();
			var currentdate = moment().utcOffset('+0800').format('YYYY-MM-DD hh:mm:ss');
			$scope.$apply(function () {
				$scope.d.projects.forEach(function(project) {
					if (project.name === projectName) {
						project.lastSuccess = currentdate;
						project.buildStatus = "success";
					}
				});
			});
			
			// alert success
			alert(projectName + ' Build Success');
			stopLoading(event.target.id);
		},
		error : function(data) {
			var currentdate = moment().utcOffset('+0800').format('YYYY-MM-DD hh:mm:ss');
			alert(projectName + ' Build Fail');
			$scope.$apply(function () {
				$scope.d.projects.forEach(function(project) {
					if (project.name === projectName) {
						project.lastSuccess = currentdate;
						project.buildStatus = "fail";
					}
				});
			});
			stopLoading(event.target.id);
		}
	});
}

function startStopService($scope, projectName, profile, event) {
	var profileJson = profile === ''? null : profile;
	var request = { profile : profileJson };
	alert(JSON.stringify(request));
	
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
}


function showLogService(projectName) {
	console.log('send ajax show log');
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
	this.getJSON = function($scope) {
		var url = '/kyip/v1/build/projects';

		$http.get(url)
			.success(function(result) {
				$scope.d = result;
                console.log(result);
				if($scope.d){
					//alert($scope.d);
				}
			})
			.error(function(err){
				console.log('Default empty page');
			});
	};
	
	this.getStartJSON = function($scope) {
		var url = '/kyip/v1/start/projects';

		$http.get(url)
			.success(function(result) {
				$scope.s = result;
                console.log(result);
				if($scope.s){
					//alert($scope.d);
				}
			})
			.error(function(err){
				console.log('Default empty page');
			});
	};
	
	this.getProfileJSON = function($scope) {
		var url = '/kyip/v1/start/projects/profiles';

		$scope.prop = {   "type": "select", 
			    "name": "Profiles",
			    "value": "localhost", 
			    "values": [] 
			  };
		$http.get(url)
			.success(function(result) {
				$scope.prop.values = result.profiles;
                console.log(result);
			})
			.error(function(err){
				console.log('No profiles can be found');
			});
	};
}

})();

