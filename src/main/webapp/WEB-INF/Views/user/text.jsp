	<div class="page-header">
		<h1>
			<spring:message code="label.user.login.page.title" />
		</h1>
	</div>
	<sec:authorize access="isAnonymous()">
		<div class="panel panel-default">
			<div class="panel-body">
				<h2>
					<spring:message code="label.login.form.title" />
				</h2>
				<c:if test="${param.error eq 'bad_credentials'}">
					<div class="alert alert-danger alert-dismissable">
						<button type="button" class="close" data-dismiss="alert"
							aria-hidden="true">&times;</button>
						<spring:message code="text.login.page.login.failed.error" />
					</div>
				</c:if>
				<form action="/branch/login/authenticate" method="POST" role="form">
					<input type="hidden" name="${_csrf.parameterName}"
						value="${_csrf.token}" /> <input type="hidden" name="scope"
						value="https://www.googleapis.com/auth/userinfo.profile" />

					<div class="row">
						<div id="form-group-email" class="form-group col-lg-4">
							<label class="control-label" for="user-email"><spring:message
									code="label.user.email" />:</label> <input id="user-email"
								name="username" type="text" class="form-control" />
						</div>
					</div>

					<div class="row">
						<div id="form-group-password" class="form-group col-lg-4">
							<label class="control-label" for="user-password"><spring:message
									code="label.user.password" />:</label> <input id="user-password"
								name="password" type="password" class="form-control" />
						</div>
					</div>
					<div class="row">
						<div class="form-group col-lg-4">
							<button type="submit" class="btn btn-default">
								<spring:message code="label.user.login.submit.button" />
							</button>
						</div>
					</div>
				</form>
				<div class="row">
					<div class="form-group col-lg-4">
						<a href="/branch/user/register"><spring:message
								code="label.navigation.registration.link" /></a>
					</div>
				</div>
			</div>
		</div>
		<div class="panel panel-default">
			<div class="panel-body">
				<h2>
					<spring:message code="label.social.sign.in.title" />
				</h2>
				<div class="row social-button-row">
					<div class="col-lg-4">
						<a href="<c:url value="/auth/facebook"/>"><button
								class="btn btn-facebook">
								<i class="icon-facebook"></i> |
								<spring:message code="label.facebook.sign.in.button" />
							</button></a>
					</div>
				</div>
				<div class="row social-button-row">
					<div class="col-lg-4">
						<a href="<c:url value="/auth/twitter"/>">

							<button class="btn btn-twitter">
								<i class="icon-twitter"></i> |

								<spring:message code="label.twitter.sign.in.button" />
							</button>


						</a>
					</div>
				</div>


				<div class="row social-button-row">
					<div class="col-lg-4">

						<%
							/*
								 * The GoogleAuthHelper handles all the heavy lifting, and contains all "secrets"
								 * required for constructing a google login url.
								 */
								final GoogleAuthHelper helper = new GoogleAuthHelper();

								if (request.getParameter("code") == null
										|| request.getParameter("state") == null) {

									/*
									 * initial visit to the page
									 */
									out.println("<a href='"
											+ helper.buildLoginUrl()
											+ "'> <button class= 'btn btn-google-plus'> <i class= 'icon-google-plus' ></i> | Sign in with Google + </button> </a>");

									/*
									 * set the secure state token in session to be able to track what we sent to google
									 */
									session.setAttribute("state", helper.getStateToken());

								} else if (request.getParameter("code") != null
										&& request.getParameter("state") != null
										&& request.getParameter("state").equals(
												session.getAttribute("state"))) {

									session.removeAttribute("state");

									out.println("<pre>");
									/*
									 * Executes after google redirects to the callback url.
									 * Please note that the state request parameter is for convenience to differentiate
									 * between authentication methods (ex. facebook oauth, google oauth, twitter, in-house).
									 *
									 * GoogleAuthHelper()#getUserInfoJson(String) method returns a String containing
									 * the json representation of the authenticated user's information.
									 * At this point you should parse and persist the info.
									 */

									out.println(helper.getUserInfoJson(request
											.getParameter("code")));

									out.println("</pre>");

								}
						%>




					</div>
				</div>
			</div>
		</div>
	</sec:authorize>
	<sec:authorize access="isAuthenticated()">
		<p>
			<spring:message code="text.login.page.authenticated.user.help" />
		</p>
	</sec:authorize>
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	<body>
			<div class="container">
			<div class="row" id="toggleDiv">Click Me!</div>
				<div class="row marketing" id="toggleContent">
					<div class="col-lg-6">
						<h4>Subheading</h4>
						<p>Donec id elit non mi porta gravida at eget metus. Maecenas
							faucibus mollis interdum.</p>

						<h4>Subheading</h4>
						<p>Morbi leo risus, porta ac consectetur ac, vestibulum at
							eros. Cras mattis consectetur purus sit amet fermentum.</p>

						<h4>Subheading</h4>
						<p>Maecenas sed diam eget risus varius blandit sit amet non
							magna.</p>
					</div>

					<div class="col-lg-6">
						<h4>Subheading</h4>
						<p>Donec id elit non mi porta gravida at eget metus. Maecenas
							faucibus mollis interdum.</p>

						<h4>Subheading</h4>
						<p>Morbi leo risus, porta ac consectetur ac, vestibulum at
							eros. Cras mattis consectetur purus sit amet fermentum.</p>

						<h4>Subheading</h4>
						<p>Maecenas sed diam eget risus varius blandit sit amet non
							magna.</p>
					</div>
				</div>

				<div class="footer">
					<p>&copy; Company 2014</p>
				</div>

			</div>
			<!-- /container -->


<script src="./static/lib/jquery-1.10.1.js"></script>
<script type="text/javascript">
$("#toggleDiv").on("click",function() {
	$('#toggleContent').slideToggle();
});
</script>
</body>
</html>