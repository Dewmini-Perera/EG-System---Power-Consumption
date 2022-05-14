<%@ page import="com.Consumption"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>    

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Consumption</title>
<link rel="stylesheet" href="Views/bootstrap.min.css"> 
<script src="Components/jquery-3.4.1.min.js"></script> 
<script src="Components/Consumptionvali.js"></script> 
</head>
<body>
<div class="container"> 
	<div class="row">  
		<div class="col-6"> 
			<h1>Consumption Service</h1>
				<form id="formConsumption" name="formConsumption" method="post" action="ConsumptionUI.jsp">  
					Account No:  
 	 				<input id="UserAccNo" name="UserAccNo" type="text"  class="form-control form-control-sm">
					<br>Unit Consumed:   
  					<input id="UnitConsumed" name="UnitConsumed" type="text" class="form-control form-control-sm">   
  					<br>Date:   
  					<input id="Date" name="Date" type="date"  class="form-control form-control-sm">
  					<br>Usage Time:   
  					<input id="UsageTime" name="UsageTime" type="text"  class="form-control form-control-sm">
					<br>  
					<input id="btnSave" name="btnSave" type="button" value="SAVE" class="btn btn-primary">  
					<input type="hidden" id="hidConsumptionIDSave" name="hidConsumptionIDSave" value=""> 
				</form>
				
				<div id="alertSuccess" class="alert alert-success"> </div>
				
			   <div id="alertError" class="alert alert-danger"></div>
				
			   <br>
				<div id="divConsumptionGrid">
					<%
					    Consumption ConsumptionObj = new Consumption();
						out.print(ConsumptionObj.readConsumption());
					%>
				</div>
				
				 
			</div>
		</div>
</div>
 
</body>
</html>