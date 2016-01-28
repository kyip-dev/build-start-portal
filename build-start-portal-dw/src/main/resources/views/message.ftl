<script src="/assets/js/websocket.js"></script>
<div class="container">
	<h1>Java API for WebSocket (JSR-356)</h1>
	<div>
		<span id="status" class="label label-important">Not Connected</span>
	</div>   
	<br/>
 
	<label style="display:inline-block">Message: </label><input type="text" id="message"  />
	<button id="send" class="btn btn-primary" onclick="sendMessageByRest()">Send</button>
	<table id="received_messages" class="table table-striped">
		<tr>
			<th>#</th>
			<th>Sender</th>
			<th>Message</th>
		</tr>
	</table>
</div>