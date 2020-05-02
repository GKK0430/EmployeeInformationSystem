<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>番茄科技 請假系統</title>
<link
	href="https://fonts.googleapis.com/css2?family=Noto+Sans+TC:wght@900&display=swap"
	rel="stylesheet">
<link
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css"
	rel="stylesheet"></link>

<link rel="stylesheet" type="text/css" href="css/mainCSS.css">
<link rel="stylesheet" type="text/css" href="css/SearchPage.css">
<link rel="icon" href="images/favicon.ico">
<style>
.col-sm-4, .functionTitle {
	text-align: center;
}

p {
	font-family: 'Noto Sans TC', sans-serif;
	font-size: 18px;
}

b {
	font-size: 20px;
}
</style>
</head>
<body>
<body>
	<br>
	<div class="container-fluid">
		<div class="row">

			<!--左邊欄位-->
			<div class="col-sm-4">
				<div class="well">
					<p>
						<b>Hi~</b> ${usersResultMap.Title},
					<p>${usersResultMap.UserName} 您好~
					<p>歡迎登入番茄科技員工資訊系統
				</div>

				<%@ include file="LeaveMain.jsp"%>

			</div>

			<!--右邊欄位-->
			<div class="col-sm-8">

				<div class="panel panel-primary">
					<p class="functionTitle">請假紀錄</p>
					<div class="panel-heading"><%@ include
							file="MainFeatureTopBar.jsp"%></div>

					<table id="idtable1">
						<tr>
							<th>申請時間</th>
							<th>請假類別</th>
							<th>開始時間</th>
							<th>結束時間</th>
							<th>總計時數</th>
							<th>簽核狀態</th>
							<th>詳細資訊</th>
						</tr>

						<c:forEach var='applyDetail' items='${ApplyList}' varStatus='vs'>
							<tr class='classtr1'>
								<td>${applyDetail.createTime}</td>
								<td>${applyDetail.leaveType}</td>
								<td>${applyDetail.startTime}</td>
								<td>${applyDetail.endTime}</td>
								<td>${applyDetail.sumHours}</td>
								<td>${applyDetail.signingProgress}</td>
								<td>
									<button class="btn btn-info" name="${applyDetail.applyId}">詳細資訊</button>
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

	<div class="CanNotRightDownDiv">
		<img class="CanNotRightDown" src="images/CompanyLogo.png">
	</div>

	<script src="js/jquery-3.4.1.min.js"></script>
	<script>
		$(function() {
			$("td:empty").text("-");
		})
		$(".btn-info").click(function() {
			var applyId = $(this).attr("name");
			location.href = "preleavedetail?applyId=" + applyId;
		})
	</script>

</body>
</html>