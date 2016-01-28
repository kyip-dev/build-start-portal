define(["jquery"], function($) {

	function BuildStartServiceClient() {
	}

	BuildStartServiceClient.prototype = {	
		getBuildProjects : function() {
			return $.ajax({
				url : '/kyip/v1/build/projects',
				dataType : "json",
				method : "GET"
			});
		},
		getStartProjects : function() {
			return $.ajax({
				url : '/kyip/v1/start/projects',
				dataType : "json",
				method : "GET"
			});
		},
		getProfiles : function() {
			return $.ajax({
				url : '/kyip/v1/start/projects/profiles',
				dataType : "json",
				method : "GET"
			});
		},
		
		buildProject : function(projectName, skipTest) {
			return $.ajax({
				url : '/kyip/v1/build/projects/' + projectName,
				contentType: "application/json",
				dataType : "json",
				method : "GET",
				data: "skipTest=" + skipTest
				
			});
		},
		startStopProject : function(projectName, profileRequest) {
			return $.ajax({
				url : '/kyip/v1/start/projects/' + projectName,
				dataType : "json",
				method : "POST",
				contentType: "application/json",
				data: JSON.stringify(profileRequest)
			});
		},
		showLogs : function(projectName) {
			return $.ajax({
				url : '/kyip/v1/start/projects/' + projectName + '/logs',
				dataType : "json",
				method : "GET"
			});
		}

	};

	return new BuildStartServiceClient();
});
