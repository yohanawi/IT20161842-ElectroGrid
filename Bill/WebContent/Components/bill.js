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
	var status = validateBillForm();
	if (status != true) 
	 { 
		 $("#alertError").text(status); 
		 $("#alertError").show(); 
		 return; 
	 } 
	// If valid------------------------
	var type = ($("#hididSave").val() == "") ? "POST" : "PUT"; 
	 $.ajax( 
 	{ 
		 url : "BillServlet", 
		 type : type, 
		 data : $("#formBill").serialize(), 
		 dataType : "text", 
		 complete : function(response, status) 
 		{ 
 			onBillSaveComplete(response.responseText, status); 
 		} 
 	}); 
});

function onBillSaveComplete(response, status)
{ 
	if (status == "success") 
 	{ 
		 var resultSet = JSON.parse(response); 
		 if (resultSet.status.trim() == "success") 
		 { 
			 $("#alertSuccess").text("Successfully saved."); 
			 $("#alertSuccess").show(); 
			 $("#divBillGrid").html(resultSet.data); 
		 } 
		 else if (resultSet.status.trim() == "error") 
		 { 
			 $("#alertError").text(resultSet.data); 
			 $("#alertError").show(); 
 		 } 
 	} 
    else if (status == "error") 
 	{ 
		 $("#alertError").text("Error while saving."); 
		 $("#alertError").show(); 
	 } 
	 else
  	 { 
		 $("#alertError").text("Unknown error while saving.."); 
		 $("#alertError").show(); 
 	 }
	 $("#hididSave").val(""); 
	 $("#formBill")[0].reset(); 
}

// UPDATE==========================================
$(document).on("click", ".btnUpdate", function(event)
	{ 
		 $("#hididSave").val($(this).data("id")); 
		 $("#billAmount").val($(this).closest("tr").find('td:eq(0)').text()); 
		 $("#contactNo").val($(this).closest("tr").find('td:eq(1)').text()); 
		 $("#userEmail").val($(this).closest("tr").find('td:eq(2)').text());  
});

$(document).on("click", ".btnRemove", function(event)
	{ 
		 $.ajax( 
		 { 
		 	url : "BillServlet", 
			type : "DELETE", 
		 	data : "id=" + $(this).data("id"),
		 	dataType : "text", 
		 	complete : function(response, status) 
		 	{ 
		 		onBillDeleteComplete(response.responseText, status); 
		 	} 
	 }); 
});
		
function onBillDeleteComplete(response, status)
{ 
	if (status == "success") 
	 { 
		 var resultSet = JSON.parse(response); 
		 if (resultSet.status.trim() == "success") 
		 { 
			 $("#alertSuccess").text("Successfully deleted."); 
			 $("#alertSuccess").show(); 
			 $("#divBillGrid").html(resultSet.data); 
		 } else if (resultSet.status.trim() == "error") 
		 { 
			 $("#alertError").text(resultSet.data); 
			 $("#alertError").show(); 
		 } 
	} 
	else if (status == "error") 
	{ 
		$("#alertError").text("Error while deleting."); 
		$("#alertError").show(); 
	} 
	else
	{ 
		$("#alertError").text("Unknown error while deleting.."); 
		$("#alertError").show(); 
	 } 
}


// CLIENT-MODEL================================================================
function validateBillForm()
{
	// Amount-------------------------------
	if ($("#billAmount").val().trim() == ""){
		return "Insert Bill_Amount.";
	}
	// is numerical value
	var tmpPrice = $("#billAmount").val().trim();
	if (!$.isNumeric(tmpPrice))
	{
		return "Insert a numerical value for Bill_Amount.";
	}
		
	// convert to decimal price
	$("#billAmount").val(parseFloat(tmpPrice).toFixed(2));

	// CONTACT------------------------
	if ($("#contactNo").val().trim() == ""){
		return "Insert Bill_User_Contact_No.";
	}
	// EMAIL
	if ($("#userEmail").val().trim() == ""){
		return "Insert User_Email.";
	}
	return true;
}