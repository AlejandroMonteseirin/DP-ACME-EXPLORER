$( document ).ready(function() {

$(function () {
  $("#status").change(function() {
    var val = $(this).val();
    if(val === "REJECTED") {
        $("#cancelReasonDiv").slideDown();
    }
    else if(val != "REJECTED") {
        $("#cancelReasonDiv").slideUp();
        $("#cancelReasonInput").val("");
    }
  });
});

$(function () {
	  $("#statusTrip").change(function() {
	    var val = $(this).val();
	    if(val === "ACTIVE") {
	        $("#cancelReasonTripDiv").slideUp();
	        $("#cancelReasonInputTrip").val("");
	    }
	    else if(val != "ACTIVE") {
	        $("#cancelReasonTripDiv").slideDown();
	    }
	  });
	});
//$(function () {
//	var table = document.getElementById("row");
//	var cells = table.getElementsByTagName('td');
//	for (var i=0, len=cells.length; i<len; i++){
//        if ((cells[i].innerHTML.indexOf("ACCEPTED") >= 0)) {
//        	cells[i].style.background="#14DF16";
//         }
//        if ((cells[i].innerHTML.indexOf("PENDING") >= 0)) {
//        	cells[i].style.color="white";
//         }
//        if ((cells[i].innerHTML.indexOf("CANCELLED") >= 0)) {
//        	cells[i].style.color="#A8A0A0";
//         }
//        if ((cells[i].innerHTML.indexOf("DUE") >= 0)) {
//        	cells[i].style.color="#842BE4";
//         }
//        if ((cells[i].innerHTML.indexOf("REJECTED") >= 0)) {
//        	cells[i].style.color="red";
//         }
//	  }});

//$(".holiwi").change(function(){
//	var status = $(".holiwi").val();
//	
////	$("#listaso").load("application/groupByStatus .displaytag", { yourdata : yourdata });
//	 $.ajax({
//         url : "trip/searchAJAX",
//         data : status,
//         type : "GET",
//
//         success : function(status) {
//        	 $("#listaso").html("hola");
//         },
//         error : function(xhr, status, error) {
//             alert(xhr.responseText);
//         }
//     });
//	 return false;
//   });


});

//function add() {
//	  var element = document.createElement("input");
//	  element.setAttribute("type", "text");
//	  element.setAttribute("name", "mytext");
//	 var spanvar = document.getElementById("stageForm");
//	 spanvar .appendChild(element);
//	}
