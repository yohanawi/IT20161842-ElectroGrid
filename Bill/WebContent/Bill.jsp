<%@page import="model.Bill"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Bill Management</title>
	<link rel="stylesheet" href="Views/bootstrap.min.css">
	<script src="Components/jquery-3.6.0.min.js"></script>
	<script src="Components/bill.js"></script>
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col-6">
				<h1>Bill Management</h1>
				<form action="Bill.jsp" id="formBill" name="formBill" method="post">
					Bill Amount:
					<input id="billAmount" name="billAmount" type="text" class="form-control form-control-sm">
					<br>
					Bill_User_Contact_No:
					<input id="contactNo" name="contactNo" type="text" class="form-control form-control-sm">
					<br>
					User_Email:
					<input id="userEmail" name="userEmail" type="text" class="form-control form-control-sm">
					<br>
					 <input id="btnSave" name="btnSave" type="button" value="Save" class="btn btn-primary">
 					 <input type="hidden" id="hididSave" name="hididSave" value="">
				</form>
				<div id="alertSuccess" class="alert alert-success"></div>
				<div id="alertError" class="alert alert-danger"></div>
				<br>
				<div id="divBillGrid">
					 <%
					 	Bill BillObj = new Bill();
					 	out.print(BillObj.readBill()); 
					 %>
				</div>
			</div>
		</div>
	</div> 
</body>
</html>