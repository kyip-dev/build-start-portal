<!DOCTYPE html>
<html>
<head>
    <title>Calendar</title>
    <link rel="stylesheet" href="/assets/css/bootstrap-flaty.css"/>
	<link rel="stylesheet" href="/assets/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="/assets/css/sticky-footer-navbar.css"/>
	<link rel="stylesheet" href="/assets/css/calendar/calendar.min.css"/>
</head>
<body>
<div class="container">
	<div class="page-header">

		<div class="pull-right form-inline">
			<div class="btn-group">
				<button class="btn btn-primary" data-calendar-nav="prev"><< Prev</button>
				<button class="btn" data-calendar-nav="today">Today</button>
				<button class="btn btn-primary" data-calendar-nav="next">Next >></button>
			</div>
			<div class="btn-group">
				<button class="btn btn-warning" data-calendar-view="year">Year</button>
				<button class="btn btn-warning active" data-calendar-view="month">Month</button>
				<button class="btn btn-warning" data-calendar-view="week">Week</button>
				<button class="btn btn-warning" data-calendar-view="day">Day</button>
			</div>
		</div>

		<h3></h3>
		<small>To see example with events navigate to march 2013</small>
		
		
		<h4>Events</h4>
			<small>This list is populated with events dynamically</small>
			<ul id="eventlist" class="nav nav-list"></ul>
	</div>
    <div id="calendar"></div>

    <script type="text/javascript" src="/assets/js/calendar/jquery.min.js"></script>
    <script type="text/javascript" src="/assets/js/lib/underscore-min.js"></script>
    <script type="text/javascript" src="/assets/js/lib/bootstrap-3.3.5.min.js"></script>
    <script type="text/javascript" src="/assets/js/calendar/calendar.js"></script>
    <script type="text/javascript" src="/assets/js/calendar/app.js"></script>
</div>
</body>
</html>