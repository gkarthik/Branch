<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@page import="org.scripps.branch.service.GoogleAuthHelper"%>



<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<link rel="icon" href="../../favicon.ico">


<!-- Bootstrap core CSS -->
<link href="../../dist/css/bootstrap.min.css" rel="stylesheet">

<title></title>
<link rel="stylesheet" type="text/css"
	href="static/oldStyles/css/social-buttons-3.css" />
<link rel="stylesheet" type="text/css"
	href="static/oldStyles/css/bootstrap-theme.css" />
<link rel="stylesheet" type="text/css"
	href="static/oldStyles/css/bootstrap.css" />
</head>
<style>
.list-group-item {
    display: list-item;
}
</style>
<body>

	<div class="container">
		<div class="header"></div>

		<div>
			<section>
				<center>
					<img alt="branch-logo" src="static/img/logo.png">
				</center>
			</section>
			<div id="column-content" class="col-md-8">
				<div id="sections" class="section">
					<h3 class="about">Decision Trees</h3>
					<p id="about">
						Decision trees help visualize a sequence of rules in a hierarchical fashion.  Rules can encapsulate *hypotheses that can be tested* with data.  They can also be used to make *predictions*.  
					</p>
					<h3 class="background">TOOL: "BRANCH"</h3>
					<div id="background">
						<ul class="unordered">
							<li>Allows you to incrementally construct a decision tree</li>
							<li>At every stage, provides feedback about the accuracy of the tree based on its agreement with known examples from a training dataset.</li>
							<li>Allows you to evaluate the tree on examples from a separate test data set</li>
							<li>
								Provides access to public datasets related to:
								<ol>
									<li>breast cancer progression</li>
									<li>kidney transplant rejection</li>
									<li>HIV tropism</li>
								</ol>
								....coming soon - your dataset!
							</li>
						</ul>
					</div>

					<h3 class="contact">Contact</h3>
					<div id="contact">
						<p>Please feel free to get in touch with us via email,
							twitter, messenger pigeon etc. See details on <a href="${pageContext.request.contextPath}/contact">Contact Page</a>.
					</div>
					<!--
            <h3 class="faq">FAQ</h3>
            <div id="faq" style="display: none;">
              <ol>
                <li><h4>Who can play?</h4>
                  <p>Anyone is welcome to play.  The more you know about biology and disease at the level of gene function, the better you are likely to do, but you can also learn as you go.  The game provides a lot of information about the genes as well as links off to related Web resources.  We hope that anyone who plays will learn something about gene function.</p>
                </li>
                <li><h4>How do you evaluate the quality of the data provided by game players?</h4>
                  <p>The predictors generated using The Cure data are evaluated for accuracy on independent test datasets - just like any other predictor inferred by experts or by statistics would be.
              By testing on real data, we can tell the good players apart from those that are guessing randomly.  Since each player action in the game is associated with their account, it is then very easy to filter out data that is not useful.  
              This approach, while it may seem strange for a scientific project, follows the &lsquo;publish then filter&rsquo; approach that has made the Web so successful.  We hope that it encourages many people to share their time and their intelligence with the project.</p>
              </li>
              </ol>
              </div>
			-->
				</div>
			</div>
			<div class="col-md-4">

				<div id="login-main-wrapper" class="panel panel-info pull-right">
					<div class="panel-heading">
						<section id="head" class="working">
							<div class="inner">

								<span id="toggleDiv" style="display: block; width: 250px;">
									<a class="btn btn-primary btn-block" href="#" role="button">Login</a>
								</span>

							</div>
						</section>
					</div>
					<div id="toggleContent" class="panel-body">
						<div>
							<div id="loginbox" class="mainbox">

								<sec:authorize access="isAnonymous()" >
									<div>
										<div>
											<c:if test="${param.error eq 'bad_credentials'}">
												<div class="alert alert-danger alert-dismissable">
													<button type="button" class="close" data-dismiss="alert"
														aria-hidden="true">&times;</button>
													<spring:message code="text.login.page.login.failed.error" />
												</div>
											</c:if>
											<form action="${pageContext.request.contextPath}/login/authenticate" method="POST"
												role="form">
												<input type="hidden" name="${_csrf.parameterName}"
													value="${_csrf.token}" /> <input type="hidden"
													name="scope"
													value="https://www.googleapis.com/auth/userinfo.profile" />

												<div>
													<div id="form-group-email" class="form-group">
														<input id="user-email" name="username" type="text"
															class="form-control" placeholder="email@example.com" />
													</div>
												</div>

												<div>
													<div id="form-group-password" class="form-group">
														<input id="user-password" name="password" type="password"
															class="form-control" placeholder="password" />
													</div>
												</div>
												<div>
													<div class="form-group">
														<button type="submit" class="btn btn-default"
															class="btn btn-lg btn-success one">
															<spring:message code="label.user.login.submit.button" />
														</button>
													</div>
												</div>
											</form>
											<div>
												<div class="form-group">
													<a href="./user/register"><spring:message
															code="label.navigation.registration.link" /></a> <span
														id="toggleSocial"> </span>

												</div>
											</div>

										</div>
									</div>

									<div>
										<div>
											<!-- 
									<div class="panel-heading">
										<h2>
											<spring:message code="label.social.sign.in.title" />
										</h2>
									</div>
									 -->
											<div>
												<div class="social-button-row" style="display: none;">
													<a href="<c:url value="/auth/facebook"/>"><button
															class="btn btn-facebook">
															<i class="icon-facebook"></i>
															<spring:message code="label.facebook.sign.in.button" />
														</button></a>
												</div>
												<div class="social-button-row"  style="display: none;">
													<a href="<c:url value="/auth/twitter"/>">

														<button class="btn btn-twitter">
															<i class="icon-twitter"></i>
															<spring:message code="label.twitter.sign.in.button" />
														</button>


													</a>
												</div>


												<div class="social-button-row" style="display: none;">

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
																		+ "'> <button class= 'btn btn-google-plus'> <i class= 'icon-google-plus' ></i>Sign in with Google + </button> </a>");

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
								<sec:authorize access="isAuthenticated()" >
									<p>
										<spring:message code="text.login.page.authenticated.user.help" />
									</p>
								</sec:authorize>


							</div>

						</div>




					</div>
				</div>
			</div>
		</div>

	</div>
	<script src="./static/lib/jquery-1.10.1.js"></script>
	<script type="text/javascript">
		$("#toggleDiv").on("click", function() {
			$('#toggleContent').slideToggle();
		});

		if ($(".alert-danger").length == 0) {
			$('#toggleContent').css({
				'display' : 'none'
			});
		}
	</script>
</body>
</html>