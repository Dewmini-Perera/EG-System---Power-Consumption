$(document).ready(function() 
{  
		$("#alertSuccess").hide();  
	    $("#alertError").hide(); 
}); 
 
 
// SAVE ============================================ 
$(document).on("click", "#btnSave", function(event) 
{  
	// Clear alerts---------------------  
	$("#alertSuccess").text("");  
	$("#alertSuccess").hide();  
	$("#alertError").text("");  
	$("#alertError").hide(); 
 
	// Form validation-------------------  
	var status = validateConsumtionForm();  
	if (status != true)  
	{   
		$("#alertError").text(status);   
		$("#alertError").show();   
		return;  
	} 
 
	// If valid------------------------  
	var type = ($("#hidConsumtionIDSave").val() == "") ? "POST" : "PUT"; 

	$.ajax( 
	{  
			url : "ConsumtionService",   
			type : type,  
			data : $("#formConsumtion").serialize(),  
			dataType : "text",  
			complete : function(response, status)  
			{   
				onConsumtionSaveComplete(response.responseText, status);  
			} 
	}); 
}); 


function onConsumtionSaveComplete(response, status) 
{  
	if (status == "success")  
	{   
		var resultSet = JSON.parse(response); 

		if (resultSet.status.trim() == "success")   
		{    
			$("#alertSuccess").text("Successfully saved.");    
			$("#alertSuccess").show(); 

			$("#divConsumtionGrid").html(resultSet.data);   
		} else if (resultSet.status.trim() == "error")   
		{    
			$("#alertError").text(resultSet.data);    
			$("#alertError").show();   
		} 

	} else if (status == "error")  
	{   
		$("#alertError").text("Error while saving.");   
		$("#alertError").show();  
	} else  
	{   
		$("#alertError").text("Unknown error while saving..");   
		$("#alertError").show();  
	} 

	$("#hidConsumtionIDSave").val("");  
	$("#formConsumtion")[0].reset(); 
} 

 
// UPDATE========================================== 
$(document).on("click", ".btnUpdate", function(event) 
{     
	$("#hidConsumtionIDSave").val($(this).closest("tr").find('#hidConsumtionIDUpdate').val());     
	$("#UserAccNo").val($(this).closest("tr").find('td:eq(0)').text());     
	$("#UnitConsumed").val($(this).closest("tr").find('td:eq(1)').text()); 
	$("#Date").val($(this).closest("tr").find('td:eq(2)').text());
	$("#UsageTime").val($(this).closest("tr").find('td:eq(3)').text());   
}); 




//REMOVE===========================================
$(document).on("click", ".btnRemove", function(event) 
{  
	$.ajax(  
	{   
		url : "ConsumtionService",   
		type : "DELETE",   
		data : "UserID=" + $(this).data("Consumtionid"),   
		dataType : "text",   
		complete : function(response, status)   
		{    
			onConsumtionDeleteComplete(response.responseText, status);   
		}  
	}); 
}); 

function onConsumtionDeleteComplete(response, status) 
{  
	if (status == "success")  
	{   
		var resultSet = JSON.parse(response); 

		if (resultSet.status.trim() == "success")   
		{    
			
			$("#alertSuccess").text("Successfully deleted.");    
			$("#alertSuccess").show(); 
		
			$("#divConsumtionGrid").html(resultSet.data); 
			
		} else if (resultSet.status.trim() == "error")   
		{    
			$("#alertError").text(resultSet.data);    
			$("#alertError").show();   
		}
		

	} else if (status == "error")  
	{   
		$("#alertError").text("Error while deleting.");   
		$("#alertError").show();  
	} else  
	{   
		$("#alertError").text("Unknown error while deleting..");   
		$("#alertError").show();  
	}
}
 
// CLIENT-MODEL========================================================================= 
function validateConsumtionForm() 
{  
	// ACCOUNT NO-----------------------
	 var tmpAcc = $("#UserAccNo").val().trim();
	if (!$.isNumeric(tmpAcc)) 
		{
		return "Insert Account No.";
		} 
	
	// UNIT CONSUMED---------------------------  
	var tmpUsage = $("#UnitConsumed").val().trim();
	if (!$.isNumeric(tmpUsage)) 
	 {
	 return "Insert Unit Consumed.";
	 }
	
	
	// DATE-------------------------------
	if ($("#Date").val().trim() == "")  
	{   
		return "Insert Date.";  
	}
	
	// USAGE TIME-------------------------------
	 var tmpUsage = $("#UsageTime").val().trim();
	if (!$.isNumeric(tmpUsage)) 
	 {
	 return "Insert Usage Time.";
	 }
	return true; 
}