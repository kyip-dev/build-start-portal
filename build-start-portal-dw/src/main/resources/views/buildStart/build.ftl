
<div ng-controller="ProjectController as project">
	<div class="row">
		<span class="col-md-6 col-sm-offset-2"><h3>Build 2</h3></span>
	</div>

	<div class="form-inline col-sm-offset-2">
		<div class="form-group">
			<label>Search Project: </label>
			<input class="form-control" type="text" id="searchInput" ng-model="search.name" placeHolder="project name"/>
		</div>
	</div>

	<table class="table table-striped">
		<tbody>
			<tr>
				<th></th>
				<th>Project name</th>
				<th>Last Build Time</th>
				<th>Build status</th>
				<th>Skip test</th>
				<th></th>
			</tr>
			<tr ng-repeat="p in d.projects | filter: search">
				<td class="col-md-2"></td>
				<td class="col-md-4">{{p.name}}</td>
				<td class="col-md-1">{{p.lastSuccess}}</td>
				<td class="col-md-1">
						<span ng-show="p.buildStatus == null" class="label label-default">Build me</span>
						<span ng-show="p.buildStatus == 'fail'" class="label label-danger">Fail</span>
					
						<span ng-show="p.buildStatus == 'success'" class="label label-success">Success</span>
				</td>
				<td class="col-md-1">
					<label class="col-sm-offset-4 checkbox" for="{{p.name}}">
						<input type="checkbox" ng-model="skipTest.ids[p.name]" name="group" id="{{p.name}}" />
					</label>
				</td>
				<td class="col-md-2">
					<button id="btnBuild-{{$index}}" type="button" ng-click="btnStatus='active disabled';buildProject(p.name, $event)" class="btn btn-primary has-spinner" ng-class="btnStatus">
						Build
						<span class="spinner"><i class="icon-spin icon-refresh"></i></span>
					</button>
				</td>

				<!--<pre ng-bind="skipTest.ids | json"></pre>-->
			</tr>
			
		</tbody>
	</table>
	
	
	<div class="row col-sm-offset-2">
		<label>Number of Projects: </label>{{d.projects.length}}
    </div>
</div>
