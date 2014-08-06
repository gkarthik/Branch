
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<meta name="_csrf" content="${_csrf.token}" />
<!-- default header name is X-CSRF-TOKEN -->
<meta name="_csrf_header" content="${_csrf.headerName}" />

<style>
legend.scheduler-border {
	width: inherit; /* Or auto */
	padding: 0 10px; /* To give a bit of padding on the left and right */
	border-bottom: none;
}

fieldset.scheduler-border {
	border: 1px groove #ddd !important;
	padding: 0 1.4em 1.4em 1.4em !important;
	margin: 0 0 1.5em 0 !important;
	-webkit-box-shadow: 0px 0px 0px 0px #000;
	box-shadow: 0px 0px 0px 0px #000;
}
</style>

<div class="col-md-8" style="float: none; margin: 0 auto;">
	<h1>Collections</h1>
	<p>Definition and about the working stuff!!!</p>
</div>
<div id="row">

	<div class="col-md-2">
		<fieldset class="scheduler-border">
			<legend class="scheduler-border">Collections</legend>
			<ul>
				<a class="showSingle" target="1">View My Collections</a>
			</ul>
			<ul>
				<a class="showSingle" target="2">Upload Dataset</a>
			</ul>
			<ul>
				<a class="showSingle" target="3">View Public Dataset</a>
			</ul>

		</fieldset>
	</div>

	<div id="div1" class="targetDiv">
		<div class="col-md-8" style="float: none; margin: 0 auto;">
			<fieldset class="scheduler-border">
				<legend class="scheduler-border">My Dataset Collections</legend>

				<div id="Add">
					<a href="#">Add New Collection</a>
				</div>

				<div>
					<a href="add">Add New Collection via link</a>
				</div>


				<div id="makeVisible" class="panel"
					style="display: none; position: absolute; top: 0; left: 0; z-index: 1">




					<div id="AddCollection" class="panel panel-default">


						<div class='alert alert-error'>
							<button id='closeID' type="button" class="close"
								data-dismiss="alert">
								<span>&times;</span><span class="sr-only">Close</span>
							</button>
						</div>
						<div class="panel-body">
							<div class="container">
								<div class="col-md-6" style="float: none; margin: 0 auto;">

									<h3>
										<span class="label label-default">Collection Name</span>
									</h3>
									<span class="btn btn-default btn-file"> <input
										id='colName' type="text" name="name">
									</span>

									<h3>
										<span class="label label-default">Description</span>
									</h3>
									<span class="btn btn-default btn-file"> <input
										id='colDesc' type="text" name="description">
									</span>

									<button id="submit" type="submit" class="btn btn-default">
										<span class="glyphicon glyphicon-cloud-upload"></span> Press
										here to add collection.
									</button>

								</div>
							</div>

						</div>
					</div>





				</div>
				<div class="col-md-6" style="float: none; margin: 0 auto;">
					<div id="accordion">
						<c:forEach var="o" items="${result}">
							<dl>
								<dt>${o.name}</dt>
								<dd>${o.description}</dd>
							</dl>

							<div>
								<c:forEach var="dataset" items="${o.datasets}">
									<ul>
										<dl>
											<dt>${dataset.name}</dt>
											<dd>${dataset.description}</dd>
										</dl>
									</ul>
								</c:forEach>
							</div>
						</c:forEach>
					</div>
				</div>
			</fieldset>

		</div>
	</div>

	<div id="div2" class="targetDiv" style="display: none">
		<div class="col-md-8" style="float: none; margin: 0 auto;">
			<form method="GET" action="upload">
				<input type="hidden" name="${_csrf.parameterName}"
					value="${_csrf.token}" /> <select id="names" name="collectionId">
					<c:forEach var="o" items="${result}">
						<option value="${o.id}" selected="selected">${o.name}</option>

					</c:forEach>
				</select>

				<button type="submit" class="btn btn-default">
					<span class="glyphicon glyphicon-cloud-upload"></span> Click here
					to choose your upload files.
				</button>

			</form>
		</div>
	</div>


	<div id="div3" class="targetDiv" style="display: none">
		<div class="col-md-8" style="float: none; margin: 0 auto;">

			<h1>View Public Collections</h1>
			<br>
			<table class="table">
				<tr>

					<th>User Name</th>
					<th>Collection Name</th>
					<th>Collection Description</th>
					<th>Dataset Name</th>
					<th>Dataset Description</th>
					<th></th>

				</tr>
				<tr>
					<c:forEach items="${publicCollection}" var="value">


						<tr>
							<td><c:out value="${value[2].firstName}" /></td>
							<td><c:out value="${value[1].name}" /></td>
							<td><c:out value="${value[1].description}" /></td>
							<td><c:out value="${value[0].name}" /></td>
							<td><c:out value="${value[0].description}" /></td>
							<td><a
								href="/branch/collection?user_id=<c:out value="${value[2].id}"/>">
									<span class="glyphicon glyphicon-ok-circle"></span>
							</a></td>
						</tr>
					</c:forEach>
				</tr>
			</table>
		</div>
	</div>
</div>

<link rel="stylesheet"
	href="//code.jquery.com/ui/1.11.0/themes/smoothness/jquery-ui.css">
<script src="//code.jquery.com/jquery-1.10.2.js"></script>
<script src="//code.jquery.com/ui/1.11.0/jquery-ui.js"></script>
<script>
	$(function() {
		$("#accordion").accordion();
	});
</script>
<script>
	$(document).ready(function() {
		$("#hide").click(function() {
			$("p").hide();
		});
		$("#show").click(function() {
			$("p").show();
		});
	});
</script>

<script type="text/javascript">
	jQuery(function() {
		jQuery('#showall').click(function() {
			jQuery('.targetDiv').show();
		});
		jQuery('.showSingle').click(function() {
			jQuery('.targetDiv').hide();
			jQuery('#div' + $(this).attr('target')).show();
		});
	});
</script>



<script>
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");
	$(document).ajaxSend(function(e, xhr, options) {
		xhr.setRequestHeader(header, token);
	});

	$("#submit").click(function() {
		var name = $("#colName").val();
		var desc = $("#colDesc").val();

		// Returns successful data submission message when the entered information is stored in database.
		var dataString = 'name=' + name + '&description=' + desc;
		if (name == '' || desc == '') {
			alert("Please Fill All Fields");
		} else {
			//AJAX code to submit form.
			$.ajax({
				type : "POST",
				url : "add",
				data : dataString,
				cache : false,
				success : function(result) {
					alert("success");
				}
			});
		}
		return false;
	});
</script>

<script>
	$(document).ready(function() {
		$("#closeID").click(function() {
			location.reload(true);
			$("#makeVisible").hide("slow");

		});

		$("#Add").click(function() {
			$("#makeVisible").show("slow");

		});
	});
</script>

