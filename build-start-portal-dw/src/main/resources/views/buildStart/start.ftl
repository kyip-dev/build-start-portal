
<div ng-controller="ProjectController as project">
	<div class="row">
		<span class="col-md-6 col-sm-offset-2"><h3>Start 2</h3></span>
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
				<th>Profiles</th>
				<th>Commands</th>
				<th>Status</th>
				<th>PID</th>
			</tr>
			<tr ng-repeat="p in s.projects | filter: search">
				<td class="col-md-2"></td>
				<td class="col-md-4">{{p.name}}</td>
				<td class="col-md-1">
					<div ng-if="p.database.length > 0">
						<select ng-model="profiles.ids[p.name]" ng-options="a.name for a in prop.values" ng-disabled="p.started" class="form-control">
							<option value="" ng-if="false"></option> <!-- remove the empty option -->
						</select>
					</div>
				</td>
				<td class="col-md-2">
				    <button id="btnStop-{{$index}}" type="button" ng-click="btnStatus='active disabled';startStopProject(p.name, $event)" 
				    	class="btn btn-danger has-spinner" ng-class="btnStatus" ng-show="p.started">
				    	Stop
				    	<span class="spinner"><i class="icon-spin icon-refresh"></i></span>
				    </button>
				    <button id="btnStart-{{$index}}" type="button" ng-click="btnStatus='active disabled';startStopProject(p.name, $event)" 
				    	class="btn btn-primary has-spinner" ng-class="btnStatus" ng-show="!p.started">
				    	Start
				    	<span class="spinner"><i class="icon-spin icon-refresh"></i></span>
				    </button>
					<button type="button" class="btn btn-primary" ng-click="showLog(p.name)" ng-show="p.log != null">
						Logs
					</button>
				</td>
				<td class="col-md-1" >
					<span ng-if="p.started"><img src="/assets/img/started2.png" width="40px" /></span>
					<span ng-if="!p.started"><img src="/assets/img/stopped.jpg" width="40px" /></span>
				</td>
				<td class="col-md-1">
					{{p.pid}}
				</td>
				<!--<pre ng-bind="profiles.ids | json"></pre>-->
			</tr>
			
		</tbody>
	</table>

	<div class="row col-sm-offset-2">
		<label>Number of Projects: </label>{{s.projects.length}}
    </div>

</div>

<#include "../common/log_footer_component.ftl"/>