<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>剩餘假別</title>
<link rel="stylesheet" type="text/css" href="css/SearchPage.css">
<link rel="stylesheet" type="text/css" href="css/Menu.css">
</head>
<body>
	<aside class="menu">
		<table id="employee">
			<tr>
				<td class="tdTitle">個人專區</td>
			</tr>
			<tr class="trbtn">
				<td id="applyPage"><a href="preapplypage">請假申請</a></td>
			</tr>
			<tr class="trbtn">
				<td id="applyRecord"><a href="preapplyrecord">請假紀錄</a></td>
			</tr>
			<tr class="trbtn">
				<td id="leaveType"><a href="preleavetype">剩餘假別</a></td>
			</tr>
		</table>
		<table id="manager">
			<tr>
				<td class="tdTitle">主管專區</td>
			</tr>
			<tr class="trbtn">
				<td id="unsignedPage"><a href="preunsignedpage">人員請假-未簽核</a></td>
			</tr>
			<tr class="trbtn">
				<td id="signedPage"><a href="presignedpage">人員請假-已簽核</a></td>
			</tr>
		</table>
	</aside>

	<article class="content">
		<h2>剩餘假別</h2>

		<table id="idtable1">
			<tr>
				<th>請假類別</th>
				<th>可用時數</th>
				<th>已用時數</th>
				<th>剩餘時數</th>
				<th>有效期限</th>
			</tr>
			<c:forEach var='leaveDetail' items='${LeaveList}' varStatus='vs'>
				<tr class='classtr1'>
					<td>${leaveDetail.leaveType}</td>
					<td>${leaveDetail.maxHours}</td>
					<td>${leaveDetail.usedHours}</td>
					<td>${leaveDetail.surplusHours}</td>
					<td>${leaveDetail.startDate}~${leaveDetail.endDate}</td>
					<!-- <td>
					<button class="classD" name="${leaveDetail.eldId}">詳細資訊</button>
				</td>
				 -->
				</tr>
			</c:forEach>
		</table>
	</article>
	<script src="js/jquery-3.4.1.min.js"></script>
	<script>
		
	</script>

</body>
</html>