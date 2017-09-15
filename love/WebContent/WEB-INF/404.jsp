<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isErrorPage="true"%>
<%String path=request.getContextPath(); %>
<!DOCTYPE html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!--> <html class="no-js"> <!--<![endif]-->
	<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>404 </title>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta name="description" content="Free HTML5 Template by FreeHTML5.co" />
	<meta name="keywords" content="free html5, free template, free bootstrap, html5, css3, mobile first, responsive" />
	<meta name="author" content="FreeHTML5.co" />

  

  	

	<!-- Place favicon.ico and apple-touch-icon.png in the root directory -->
	<link rel="shortcut icon" href="<%=path %>/images/favicon.png" />

	<link href='https://fonts.googleapis.com/css?family=Open+Sans:400,700,300' rel='stylesheet' type='text/css'>
	
	<link rel="stylesheet" href="<%=path %>/lib/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" href="<%=path %>/css/animate.css">
	<link rel="stylesheet" href="<%=path %>/css/style2.css">


	<!-- Modernizr JS -->
	<script src="<%=path %>/js/modernizr-2.6.2.min.js"></script>
	<!-- FOR IE9 below -->
	<!--[if lt IE 9]>
	<script src="js/respond.min.js"></script>
	<![endif]-->

	</head>
	<body>


		
		
		<div class="container fh5co-container">

			<div class="row">
				<div class="col-md-12 animate-box" data-animate-effect="fadeIn">
					<div class="fh5co-404-wrap" id="video" data-vide-bg="<%=path %>/video/Crocodile" data-vide-options="position: 0 50%">
						<div class="overlay"></div>
					</div>
				</div>
				<div class="col-md-12 text-center fh5co-404-text animate-box"  data-animate-effect="fadeIn">
					<h2>Looks like you got lost</h2>
					<p><a href="<%=path %>/user?action=index" class="btn btn-primary">Go back home</a></p>
				</div>
				
			</div>
		
		</div>
	
	<!-- jQuery -->
	<script src="<%=path %>/js/jquery.min.js"></script>
	<!-- Bootstrap -->
	<script src="<%=path %>/lib/bootstrap/js/bootstrap.min.js"></script>
	<!-- Vide -->
	<script src="<%=path %>/js/jquery.vide.min.js"></script>
	<!-- Waypoints -->
	<script src="<%=path %>/js/jquery.waypoints.min.js"></script>
	<!-- Main JS -->
	<script src="<%=path %>/js/main.js"></script>


	</body>
</html>

    