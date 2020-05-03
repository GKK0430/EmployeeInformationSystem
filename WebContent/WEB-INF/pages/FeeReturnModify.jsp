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
<script>
	function check(obj) {
		if (obj.id == 'checkbox1' && obj.checked == true) {
			document.getElementById('checkbox2').checked = false;
		} else if (obj.id == 'checkbox2' && obj.checked == true) {
			document.getElementById('checkbox1').checked = false;
		}
	}
</script>
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
					<form class="for1"
						action="<c:url value="/FeeReturnEditPage?feeAppID=${S_feeAppID}"/>"
						method="post">
						<table align="center">
						<tr>
						<td>申請編號:</td>
						<td>${S_feeAppID}</td>
						</tr>
						<tr>
						<td>員工編號:</td>
						<td>${S_feeAppID}</td>
						</tr>
						<tr>
						<td>部門:</td>
						<td>${S_department}</td>
						</tr>
						<tr>
						<td>申請項目:</td>
						<td>${S_appItem}</td>
						</tr>
						<tr>
						<td>申請時間:</td>
						<td>${S_appTime}</td>
						</tr>
						<tr>
						<td>發票時間:</td>
						<td><input type="date" name="invoiceTime" value="${S_invoiceTime}"></td>
						</tr>
						<tr>
						<td>發票號碼:</td>
						<td><input	type="tel" name="invoiceNb" value="${S_invoiceNb}"></td>
						</tr>
						<tr>
						<td>統編:</td>
						<td><input	type="tel" name="editor" value="${S_editor}"></td>
						</tr>
						
						<tr>
						<td>申請金額:</td>
						<td><input	type="tel" name="appMoney" value="${S_appMoney}"></td>
						</tr>
						<tr>
						<td>備註:</td>
						<td><textarea cols="30" rows="3" id="comment1" name="remark" ></textarea></td>
						</tr>
						</table>
						<hr/>
		          
 		               	<div>
						<input type="submit" value="修改">
						</div>
						</form>
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

	

</body>
</html>