<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
<meta name="description" content="">
<meta name="author" content="">
<link rel="icon" href="../../favicon.ico">

<title>ebook4u</title>

<!-- Bootstrap core CSS -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">

<!-- Custom styles for this template -->
<link
	href="http://getbootstrap.com/examples/starter-template/starter-template.css"
	rel="stylesheet">

<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>

<body>

	<nav class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed"
					data-toggle="collapse" data-target="#navbar" aria-expanded="false"
					aria-controls="navbar">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#">ebook4u</a>
			</div>

<!-- 以上这个div里面的是左上角ebook4U的button，有个缩小后变成下拉菜单的功能 -->
<!-- 下面的是后面三个小图标的，首先要拿到当前的User信息，后面显示 -->

			<div id="navbar" class="collapse navbar-collapse">
				<%
					String str = (String) session.getAttribute("currentUser");
				%>
				<ul class="nav navbar-nav">
					<li class="active"><a
						href="<%=request.getContextPath()%>/book/search"><span
							class="glyphicon glyphicon-search"></span></a></li>
					<li><a href="<%=request.getContextPath()%>/user/<%=str%>"><span
							class="glyphicon glyphicon-user"></span></a></li>
					<li><a href="<%=request.getContextPath()%>/contact.jsp"><span
							class="glyphicon glyphicon-phone-alt"></span></a></li>
					<%
					//System.out.println(request.getContextPath());
					//拿到的是当前的路径/ssh1
					%> 
				</ul>
			</div>
			<!--/.nav-collapse -->
		</div>
	</nav>

<!-- 这个div和上面的那个是平行的，同时显示，只不过这个占据了中间的位置 -->
	<div class="container">

		<div class="starter-template">
			<h1>
				Welcome back!
				<%=str%>
			</h1>

		</div>

	</div>
	<!-- /.container -->


	<!-- Bootstrap core JavaScript
    ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
	<script src="http://getbootstrap.com/dist/js/bootstrap.min.js"></script>
</body>
</html>
