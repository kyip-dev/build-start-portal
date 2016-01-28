<nav class="navbar navbar-default navbar-fixed-top">
	<div class="container-fluid">
		<div class="navbar-header">
			<a class="navbar-brand" href="#">KYIP Playground</a>
		</div>
		<div id="navbar" class="collapse navbar-collapse">
			<ul class="nav navbar-nav">
				<li ng-class="{active:isActive('/kyip/v1/build/projects')}"><a href="" ng-click="redirect('/kyip/v1/build/projects')">Build</a></li>
				<li ng-class="{active:isActive('/kyip/v1/start/projects')}"><a href="" ng-click="redirect('/kyip/v1/start/projects')">Start</a></li>
				<li class="dropdown">
					<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Little Functions <span class="caret"></span></a>
					<ul class="dropdown-menu">
						<li ng-class="{active:isActive('/kyip/v1/messages')}"><a href="" ng-click="redirect('/kyip/v1/messages')">Websocket send message</a></li>
						<li><a href="#">Another action</a></li>
						<li><a href="#">Something else here</a></li>
						<li role="separator" class="divider"></li>
						<li class="dropdown-header">Nav header</li>
						<li><a href="#">Separated link</a></li>
						<li><a href="#">One more separated link</a></li>
					</ul>
				</li>
			</ul>
		</div>
		<!--/.nav-collapse -->
	</div>
</nav>
