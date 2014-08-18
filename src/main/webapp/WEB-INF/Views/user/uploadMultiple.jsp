
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<div class="container">

	<div class="col-md-6" style="float: none; margin: 0 auto;">


		<h3>

			<span class="label label-default">Collection: <%=request.getParameter("collectionId")%></span>
		</h3>
		<form method="POST" action="upload" enctype="multipart/form-data">
			<input type="hidden" name="user_id"
				value="<sec:authentication property="principal.id" />" /> <input
				type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />


			<h3>
				<span class="label label-default">Dataset File Type</span>
			</h3>
			<input type="radio" name="fileType" value="ARFF" checked> ARFF
			<br> <input type="radio" name="fileType" value="CSV">
			CSV/TSV<br>


			<h3>
				<span class="label label-default">Dataset File</span>
			</h3>
			<span class="btn btn-default btn-file"> <input type="file"
				name="file">
			</span>

			<h3>
				<span class="label label-default">Feature File</span>
			</h3>
			<span class="btn btn-default btn-file"> <input type="file"
				name="file">
			</span>

			<h3>
				<span class="label label-default">Mapping File</span>
			</h3>
			<span class="btn btn-default btn-file"> <input type="file"
				name="file">
			</span>



			<h3>
				<span class="label label-default">Description</span>
			</h3>
			<span class="btn btn-default btn-file"> <input type="textarea"
				name="description">
			</span>

			<h3>
				<span class="label label-default">Dataset Name</span>
			</h3>
			<span class="btn btn-default btn-file"> <input type="text"
				name="datasetName">
			</span> <input type="hidden" name="collectionId"
				value="<%=request.getParameter("collectionId")%>">

			<div>
				<div>
					<input type="checkbox" name="private" value="1" /> Private Dataset

				</div>
				<button type="submit" class="btn btn-default">
					<span class="glyphicon glyphicon-cloud-upload"></span> Click here
					to upload the file!
				</button>
			</div>
		</form>
	</div>
</div>



