<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>build</title>
    <base href="/">

    <!-- Stylesheets -->
	<link id="powatag-css" rel="stylesheet" href="http://assets.powatag.com/qr/sandbox/nextRelease/css/powatag.css">
	<link rel="stylesheet" href="http://netdna.bootstrapcdn.com/font-awesome/3.2.1/css/font-awesome.css">
	<link rel="stylesheet" href="/assets/css/bootstrap-flaty.css"/>
	<link rel="stylesheet" href="/assets/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="/assets/css/sticky-footer-navbar.css"/>
	<link rel="stylesheet" href="/assets/css/main.css"/>

    <!-- js 
    <script src="/assets/js/jquery/1.11.1/jquery.min.js"></script>
    <script src="/assets/js/lib/bootstrap-3.3.5.min.js"></script>
    <script src="/assets/js/lib/moment.min.js"></script>
    <script src="/assets/js/lib/moment-timezone.min.js"></script>
    <script src="/assets/js/utils.js"></script>
    -->
    <!--<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.3.15/angular.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/angular-ui-bootstrap/0.13.0/ui-bootstrap-tpls.min.js"></script>-->
	<!--<script src="/assets/js/app.js"></script>-->

	
</head>
	<body ng-controller="MainController as main">
		<#include "./common/nav_bar.ftl"/>

		<div class="container-fluid">
			<#if sectionTemplatePath??>
				<#include sectionTemplatePath>
			</#if>
		</div>
		<script data-main="${scriptPath}" src="/assets/js/lib/require.js"></script>
	</body>
</html>
