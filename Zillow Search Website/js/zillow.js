
	function func2(){
		alert("validation2 done!");
	}

	function func1(){
		alert("validation1 done!");

		var zillow_url="<a href=\"http://www.google.com\">Google</a>";
		var fb_link = "<a href=\"http://facebook.com\"><img src=\"css/share_facebook.png\" height=\"40\" width=\"170\"></a> ";
		var zillow_table = ""+
                                "<table style=\"width:100%\" class= \"table table-striped zillow_table\"  >" +
                                	"<tr>" +
                                        "<td width=\"25%\">See more details for "+ zillow_url +" on Zillow</td><td align=\"left\" width=\"12%\"></td>"+
                                       "<td  width=\"40%\"></td><td align=\"right\">" + fb_link + "</td>"+
                                    "</tr>" +
                                    "<tr>" +
                                        "<td width=\"25%\"><b> Property Type: </b></td><td align=\"left\" width=\"12%\">" + "ABCDEFGH" + "</td>"+
                                       "<td  width=\"40%\"> <b>Last Sold Price: </td><td align=\"right\">" + "ABCDEFGH" + "</td>"+
                                    "</tr>" +
                                    "<tr>" +
                                        "<td> <b>Year Built: </b></td><td align=\"left\" >" + "ABCDEFGH" + "</td>"+
                                       "<td> <b>Last Sold Date:</b> </td><td align=\"right\">" + "ABCDEFGH" + "</td>"+
                                    "</tr>" +
                                    "<tr>" +
                                        "<td> <b>Lot Size: </td><td align=\"left\"  >" + "ABCDEFGH" + "</td>"+
                                       "<td><b>"+ "ABCDEFGH"+"</b></td><td align=\"right\">" + "ABCDEFGH" + "</td>"+
                                    "</tr>" +
                                    "<tr>" +
                                        "<td> <b>Finished Area: </b></td><td align=\"left\" >" + "ABCDEFGH"+ "</td>"+
                                       "<td><b>"+ "ABCDEFGH" +"</b></td><td align=\"right\">" +"ABCDEFGH" + "</td>"+
                                    "</tr>" +
                                    "<tr>" +
                                        "<td> <b>Bathrooms: </b></td><td align=\"left\" >" + "ABCDEFGH" + "</td>"+
                                       "<td> <b>All time property Range:</b> </td><td align=\"right\">" + "ABCDEFGH" + "</td>"+
                                    "</tr>" +
                                    "<tr>" +
                                        "<td><b> Bedrooms: </b></td><td align=\"left\" >" + "ABCDEFGH"+ "</td>"+
                                       "<td><b>" + "ABCDEFGH" +"</b></td><td align=\"right\">" + "ABCDEFGH"+ "</td>"+
                                    "</tr>" +
                                    "<tr>" +
                                        "<td> <b>Tax Assesment Year: </b></td><td align=\"left\" >" + "ABCDEFGH" + "</td>"+
                                       "<td><b>" +"ABCDEFGH" +" </b></td><td align=\"right\">" + "ABCDEFGH" + "</td>"+
                                    "</tr>" +
                                    "<tr>" +
                                        "<td> <b>Tax Assesment: </b></td><td align=\"left\" >" + "ABCDEFGH" + "</td>"+
                                       "<td><b>All Time rent range: </b></td><td align=\"right\">" + "ABCDEFGH" + "</td>"+
                                    "</tr>" +
                                "</table>" ;

                                //zillow_table="<p>Anurag Katey</p>";
                                $("#basic-info").empty();
                        	    $("#basic-info").append(zillow_table);
                                
                        	    alert("filling done!");

	}

	$(".zillow_form").submit(function(){
		func2();
	});


	

	$(document).ready(function(){

		    $('a[data-toggle="tab"]').on('shown.bs.tab', function(e){
		        var currentTab = $(e.target).text(); // get current tab
		        var LastTab = $(e.relatedTarget).text(); // get last tab
		        $(".current-tab span").html(currentTab); 
		        $(".last-tab span").html(LastTab);
		    });



		$(".form").validate({
				rules: {
					addr: {
						required: true
					},
					city: {
						required: true
					},
					state: {
						required:{
							depends: function(element) {
						            return $("#state").val() == 'select';
						}

						} 
					}
				},
				messages: {
					addr: "**This field is required",
					city: "**This field is required",
					state: "**Please select a state"
				},
				errorPlacement: function (error, element) {

				            var name = $(element).attr("name");
				  	$("#" + name + "_error").empty();
				            error.appendTo($("#" + name + "_error"));
				             $("#" + name + "_error").css({'color':'red'});
				             $("#" + name + "_error").css({'font-size':'95%'});
				             
       				 },
				success: function(label) {
  				},
  				submitHandler: function(form) {
	   				 alert("validation successfull");
	   				 func1();
  				}
			});
		func();
		
	});

