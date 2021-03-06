<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>番茄科技 員工管理</title>

<link href="https://fonts.googleapis.com/css?family=Open+Sans:300,600"
	rel="stylesheet">
<link
	href="https://fonts.googleapis.com/css2?family=Noto+Sans+TC:wght@900&display=swap"
	rel="stylesheet">
<link
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css"
	rel="stylesheet"></link>

<link rel="stylesheet" type="text/css" href="css/mainCSS.css">
<link rel="icon" href="images/favicon.ico">
<script src="http://code.jquery.com/jquery-1.12.4.min.js"></script>
<style>
.well, .panel {
	text-align: center;
}

body {
	font-family: 微軟正黑體;
}

p {
	font-family: 'Noto Sans TC', sans-serif;
	font-size: 18px;
}

.idtable1 {
	margin: 30px auto;
	border-collapse: collapse;
}

.idtable1 tr {
	text-align: center;
}

.idtable1 th {
	text-align: center;
	background-color: #E0E0E0;
	padding: 10px 20px;
}

.idtable1 td {
	border-bottom: 1px solid #ddd;
	padding: 10px 20px;
}
b{
	font-size:20px;
}
.pInLeft{
	margin:0;
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
					<p class="pInLeft">
						<b>Hi~</b> ${usersResultMap.Title},
					<p class="pInLeft">${ LoginOK.employee.name} (${usersResultMap.UserName})您好~
					<p class="pInLeft">歡迎登入番茄科技員工資訊系統
				</div>

				<%@ include file="SubFeatureForEmpManage.jsp"%>

			</div>




			<!--右邊欄位-->

			<div class="col-sm-8">

				<div class="panel panel-primary">
					<p class="functionTitle">HR部門公告</p>
					<div class="panel-heading"><%@ include
							file="MainFeatureTopBar.jsp"%></div>
					<div class="panel-body">
						<table id="boardlist" class="idtable1" style="margin: 0 auto"></table>
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




	<script>
		var messages;
		const perpage = 10;
		let nowpage = 1;
		showmessages();
		//從資料庫取得資料
		function showmessages() {
			$.ajax({
				url : "BullBoardListOfHR",
				type : "GET",
				success : function(Str) {
					messages = JSON.parse(Str);
					pagination(messages, nowpage);
				}
			});
		}
		//產生顯示的資料
		function pagination(messages, nowpage) {
			$("#boardlist").html("");
			const datatotal = messages.length;
			const pagesTotal = Math.ceil(datatotal / perpage);
			let currentPage = nowpage;
			var minData = (currentPage * perpage) - perpage + 1;
			var maxData = (currentPage * perpage);
			//產生<a>標籤
			atag = "<a href=# name='1' onclick='f(this)'>" + 1 + "</a> ";
			for (let i = 2; i <= pagesTotal; i++) {
				atag += "<a href=# name='" + i + "' onclick='f(this)'>" + i
						+ "</a> ";
			}
			document.getElementById("tag").innerHTML = atag;
			$("#page").html("第" + nowpage + "頁");
			var txt = "<tr><th>發布人<th>主題<th>內容<th>發布時間";

			if (maxData > datatotal) {
				maxData = datatotal;
			}
			for (let i = minData - 1; i < maxData; i++) {
				txt += "<tr><td>" + messages[i].announcer;
				txt += "<td>" + messages[i].title;
				txt += "<td>" + messages[i].content;
				txt += "<td>" + messages[i].announcDate;
				/*
				txt += "<td><a href='<c:url value='/EditEmployee.do?id="
						+ emps[i].empID + "'/>' name='" + emps[i].empID
						+ "'>Edit</a>";
				 */
			}

			$("#boardlist").html(txt);
		}

		//換頁		
		function f(obj) {
			nowpage = obj.name;
			$("#page").html("第" + nowpage + "頁");
			showmessages();
		}
	</script>
</body>
</html>