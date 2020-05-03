<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>番茄科技 差旅費系統</title>
<link
	href="https://fonts.googleapis.com/css2?family=Noto+Sans+TC:wght@900&display=swap"
	rel="stylesheet">
<link
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css"
	rel="stylesheet"></link>

<link rel="stylesheet" type="text/css" href="css/mainCSS.css">
<link rel="icon" href="images/favicon.ico">
<style>
.well, .panel {
	text-align: center;
}

p {
	font-family: 'Noto Sans TC', sans-serif;
	font-size: 18px;
}
#idtable1 {
	margin: 30px auto;
	border-collapse: collapse;
}

#idtable1 tr {
	text-align: center;
}

#idtable1 th {
	background-color: #E0E0E0;
	padding: 10px 20px;
}

#idtable1 td {
	border-bottom: 1px solid #ddd;
	padding: 10px 20px;
}
</style>

</head>
<body>
	<br>
	<div class="container-fluid">
		<div class="row">

			<!--左邊欄位-->
			<div class="col-sm-4">
				<div class="well">
					<p>Hi, ${usersResultMap.UserName} 您好~
					<p>歡迎登入番茄科技員工資訊系統
				</div>

				<%@ include file="SubFeatureForFee.jsp"%>

			</div>

			<!--右邊欄位-->

			<div class="col-sm-8">

				<div class="panel panel-primary">
					<p class="functionTitle">差旅費退件</p>
					<div class="panel-heading"><%@ include
							file="MainFeatureTopBar.jsp"%></div>
					<div class="panel-body">
						

														<table id="idtable1">
																<tr >
									<th>申請編號</th>
									<th>部門</th>
									<th>員工姓名</th>
									<th>申請項目</th>
									<th>申請日期</th>
									<th>申請金額</th>
									
									<th>申請狀態</th>
									<th>修改退件</th>
								</tr>

								<c:forEach var='qfeeApp' items='${qfeeAppByID}' varStatus='vs'>
									<tr>
										<td>${qfeeApp.feeAppID}</td>
										<td>${qfeeApp.department}</td>
										<td>${qfeeApp.feeAppID}</td>
										<td>${qfeeApp.appItem}</td>
										<td>${qfeeApp.appTime.substring(0,16)}</td>
										<td>${qfeeApp.appMoney}</td>
										<td>${qfeeApp.signerStatus}</td>
										
										
										
										<td>
											<button class="btn-info" name="${qfeeApp.feeAppID}">修改</button>
										</td>
									</tr>
								</c:forEach>
							</table>


							
						
						<div class="list_footer">
							<div id="tag"></div>
							<div id="page"></div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="CanNotRightDownDiv">
		<img class="CanNotRightDown" src="images/CompanyLogo.png">
	</div>

	<script src="js/jquery-3.4.1.min.js"></script>
	<script>
	$(function() {
		$("td:empty").text("-");
	})
	$(".btn-info").click(function() {
		var feeAppID = $(this).attr("name");
		location.href = "ReturnPage?feeAppID=" + feeAppID;
	})
	</script>

</body>
</html>