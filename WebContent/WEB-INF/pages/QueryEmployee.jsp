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
<script src="http://code.jquery.com/jquery-1.12.4.min.js"></script>
<link rel="icon" href="images/favicon.ico">
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
.datepicker{
    border:1px  solid #ccc;
    border-radius: 4px;
    height: 24px;
    line-height:24px;
    margin:3px;   
    width: 250px; 
} 
.datepicker:focus{
    outline:0 none;
    border:1px solid #1abc9c;
        
}
b{
	font-size:20px;
}
.inputtd {
	text-align: left;
}
.idtable2 {
	position: relative;
	margin: 50px auto;
	border-collapse: collapse;
	right: 50px;
}
.idtable2 tr {
	text-align: right;
}

.idtable2 td {
	/*border-bottom: 1px solid #ddd;*/
	padding: 2px;
}
.span1 {
	margin-right: 15px;
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
					<p class="functionTitle">查詢/編輯員工</p>
					<div class="panel-heading"><%@ include
							file="MainFeatureTopBar.jsp"%></div>
					<div class="panel-body">
					<table class="idtable2">
						<tr>
							<td>員工Id：</td>
							<td class="inputtd"><input type="text" id="searchid" name="searchid" class="datepicker"></td>
						</tr>
						<tr>
							<td>員工姓名：</td>
							<td class="inputtd"><input type="text" id="searchname" name="searchname" class="datepicker"></td>
						</tr>
						<tr>
							<td></td>
							<td class="inputtd"><select name="searchdept" id="searchdept" class="datepicker"></select></td>
						</tr>
						<tr>
							<td></td>
							<td class="inputtd"><input type="checkbox" id="resigned" name="resigned" value="resigned"><span class="span1">離職員工</span><input type="button" id="search" name="search" value="搜尋" class="btn btn-info"></td>
						</tr>
					</table>
						<table id="emplist" class="idtable1" style="margin: 0 auto"></table>
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
		var depts;
		$('#searchdept').empty();
		$.ajax({
			url : "DeptList",
			type : "GET",
			success : function(Str) {
				depts = JSON.parse(Str);
				$('#searchdept').append("<option value=''>選擇部門</option>");
				for (let i = 0; i < depts.length; i++) {
					$('#searchdept').append(
							"<option value='"+depts[i].deptabb+"'>"
									+ depts[i].deptabb + "</option>")
				}
			}
		});

		var emps;
		const perpage = 10;
		let nowpage = 1;
		showemps();
		//從資料庫取得資料
		function showemps() {
			$.ajax({
				url : "EmpList",
				type : "GET",
				success : function(Str) {
					emps = JSON.parse(Str);
					pagination(emps, nowpage);
				}
			});
		}
		//產生顯示的資料
		function pagination(emps, nowpage) {
			$("#emplist").html("");
			const datatotal = emps.length;
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
			var txt = "<tr><th>EmpID<th>帳號<th>部門<th>職稱<th>姓名<th>主管<th>分機<th>Email<th>";

			if (maxData > datatotal) {
				maxData = datatotal;
			}
			for (let i = minData - 1; i < maxData; i++) {
				txt += "<tr><td>" + emps[i].empID;
				txt += "<td>" + emps[i].account;
				txt += "<td>" + emps[i].department;
				txt += "<td>" + emps[i].title;
				txt += "<td>" + emps[i].name;
				txt += "<td>" + emps[i].manager;
				txt += "<td>" + emps[i].extensionNum;
				txt += "<td>" + emps[i].email;
				txt += "<td><a href='<c:url value='/EditEmployee.do?id="
						+ emps[i].empID + "'/>' name='" + emps[i].empID
						+ "'>Edit</a>";

			}

			$("#emplist").html(txt);
		}

		//換頁		
		function f(obj) {
			nowpage = obj.name;
			$("#page").html("第" + nowpage + "頁");
			showemps();
		}

		var chk_status;
		$("#search").click(
				function() {
					if ($("#resigned").prop("checked")) {
						chk_status = true;
					} else {
						chk_status = false;
					}
					nowpage = 1;
					$.ajax({
						url : "QueryEmp.action?searchid="
								+ $("#searchid").val() + "&searchname="
								+ $("#searchname").val() + "&searchdept="
								+ $("#searchdept").val() + "&resigned="
								+ chk_status,
						type : "GET",
						traditional : true,
						success : function(Str) {
							emps = JSON.parse(Str);
							pagination(emps, nowpage);
						}
					});
				});
	</script>
</body>
</html>