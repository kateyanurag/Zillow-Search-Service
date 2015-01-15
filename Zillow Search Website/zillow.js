
	 var full_address = "ABCDE";
	 var last_selling_cost="";
	 var overall_change_30days = "";
	 var home_link = "";
	 var home_pic = "";
	 var zestimate_ten = "";
	function prepare_table(data){
	            //document.getElementById("zillow_div").innerHTML("<p> HEYAA </p>");
	            //document.getElementById("zillow_div").style.visibility='visible';
	          
	            var overall_change = "30 days overall change";
	            overall_change_30days = data["overall_change"];
	            if(!data["overall_change"]=="$.00"){
	            		if(data["overall_change"].contains("-")){

		                overall_change = overall_change ;
		                data["overall_change"] = "<img src=\"down.gif\">"+"  $"+data["overall_change"].split("-")[1];
		            }else{
		                overall_change = overall_change ;
		                data["overall_change"] = "<img src=\"up.gif\">"+"  $"+data["overall_change"]
		            }
	            }else{
	            		data["overall_change"] = "N/A";
	            }
		            
	            //data["overall_change"] = "$" + data["overall_change"];
	            overall_change = overall_change + ":";
	            
	            var rent_change = "All Time Rent Change";
	           //  if(!data["rent_change"]=="$.00"){
	             	if(data["rent_change"].contains("-")){
	                		rent_change = rent_change;
	                		data["rent_change"] = "<img src=\"down.gif\">" + "  $" + data["rent_change"].split("-")[1];
	            		}else{

	            			data["rent_change"] = "<img src=\"up.gif\">" + "  $" + data["rent_change"];
	                		rent_change = rent_change ;
	           		 }
	           //  }else{
	             //	data["rent_change"] = "N/A";
	         //    }
	            
	            
	             rent_change = rent_change + ":";
	            
	            var property_estimate = "Zestimate&reg;" +" Property Estimate as of " + data["property_estimate_date"] + ":";
	            var rent_estimate = "Rent Zestimate&reg; Valuation as of " + data["rent_estimate_date"] + ":";
	            
	            full_address = data["street"]+data["city"]+data["state"]+data["zipcode"];
	            var zillow_url = " <a id=\"zillow_link\" href= " + "\"" + data["homedetails"] + "\"" + ">" + full_address +"</a> ";
	            
	            var fb_link = "<a href=\"#\" onclick=\"share_on_fb()\"><img src=\"share_facebook.png\" height=\"40\" width=\"170\"></a> ";
	            var zillow_table = 
	                                "<table style=\"width:100%\" class= \"table table-striped zillow_table\">" +
	                                	"<tr>" +
	                                        "<td colspan=\"3\" ><b>See more details for "+ zillow_url +" on Zillow</b></td><td align=\"right\" >" + fb_link + "</td>"+
                                    	"</tr>" +
	                                    "<tr>" +
	                                        "<td width=\"25%\" ><b> Property Type: </b></td><td align=\"left\" width=\"12%\">" + data["property_type"] + "</td>"+
	                                       "<td width=\"40%\" > <b>Last Sold Price: </td><td align=\"right\">" + data["last_sold_price"] + "</td>"+
	                                    "</tr>" +
	                                    "<tr>" +
	                                        "<td> <b>Year Built: </b></td><td align=\"left\" >" + data["year_built"] + "</td>"+
	                                       "<td> <b>Last Sold Date:</b> </td><td align=\"right\">" + data["last_sold_date"] + "</td>"+
	                                    "</tr>" +
	                                    "<tr>" +
	                                        "<td> <b>Lot Size: </td><td align=\"left\"  >" + data["lot_size"] + "</td>"+
	                                       "<td><b>"+ property_estimate +"</b></td><td align=\"right\">" + data["property_estimate"] + "</td>"+
	                                    "</tr>" +
	                                    "<tr>" +
	                                        "<td> <b>Finished Area: </b></td><td align=\"left\" >" + data["finished_area"] + "</td>"+
	                                       "<td><b>"+ overall_change +"</b></td><td align=\"right\">" + data["overall_change"] + "</td>"+
	                                    "</tr>" +
	                                    "<tr>" +
	                                        "<td> <b>Bathrooms: </b></td><td align=\"left\" >" + data["bathrooms"] + "</td>"+
	                                       "<td> <b>All time property Range:</b> </td><td align=\"right\">" + data["property_range"] + "</td>"+
	                                    "</tr>" +
	                                    "<tr>" +
	                                        "<td><b> Bedrooms: </b></td><td align=\"left\" >" + data["bedrooms"] + "</td>"+
	                                       "<td><b>" + rent_estimate +"</b></td><td align=\"right\">" + data["rent_estimate"] + "</td>"+
	                                    "</tr>" +
	                                    "<tr>" +
	                                        "<td> <b>Tax Assesment Year: </b></td><td align=\"left\" >" + data["taxAssessmentYear"] + "</td>"+
	                                       "<td><b>" + rent_change +" </b></td><td align=\"right\">" + data["rent_change"] + "</td>"+
	                                    "</tr>" +
	                                    "<tr>" +
	                                        "<td> <b>Tax Assesment: </b></td><td align=\"left\" >" + data["taxAssessment"] + "</td>"+
	                                       "<td><b>All Time rent range: </b></td><td align=\"right\">" + data["rent_range"] + "</td>"+
	                                    "</tr>" +
	                                "</table>" ;
	                               /*  "<table>"+
	                                    "<tr>"+ 
	                                        "<td>Property Type::</td>"+
	                                        "<td>"+ data["property_type"] + "</td>" +
	                                        "<td> Last Sold Price: </td>" +
	                                        "<td>" + data["last_sold_price"] + "</td>" +
	                                    "</tr>"+
	                                "</table>"; */
	            return zillow_table;
	}

	function change_blank_fields(data){
	
		if(data["property_type"] == "")	 data["property_type"] = "N/A";
		if(data["year_built"] == "") 	data["year_built"] = "N/A";
		if(data["lot_size"] == "0 sq. ft.") 	data["lot_size"] = "N/A";
		if(data["finished_area"] == "0 sq. ft.") 	data["finished_area"] = "N/A";
		if(data["bathrooms"] == "") 	data["bathrooms"] = "N/A";
		if(data["bedrooms"] == "") 	data["bedrooms"] = "N/A";
		if(data["taxAssessment"] == "$.00") 	data["taxAssessment"]= "N/A";
		if(data["taxAssessmentYear"] == "") 	data["taxAssessmentYear"] = "N/A";
		if(data["last_sold_price"]== "$.00") 	data["last_sold_price"] = "N/A";
		if( data["last_sold_date"] == "")  	data["last_sold_date"] = "N/A";
		if(data["overall_change"] == "$.00") 	data["overall_change"]= "N/A";
		if(data["property_range"] == "$.00 - $.00") 	data["property_range"]= "N/A";
		if(data["rent_estimate"]== "$.00") 	data["rent_estimate"] = "N/A";
		if(data["rent_change"]== "$.00") 	data["rent_change"] = "N/A";
		if(data["rent_range"]== "$.00 - $.00") 	data["rent_range"] = "N/A";
		if(data["property_estimate"] == "$.00") data["property_estimate"]="N/A";
		
		
	}


	function ajax_call(){
		//alert("in ajax_call");
		var address = document.getElementById("addr").value;
		var city = document.getElementById("city").value;
		var state_list = document.getElementById("state");
                	var state = state_list.options[state_list.selectedIndex].text;
                	address = address.split(" ").join("+");
                	city = city.split(" ").join("+");
	   	var fields = {
			"request": "zillow", "addr": address, "city": city, "state": state
		};
                	fields.addr=address;
	 	fields = $(this).serialize() + "&" + $.param(fields);
	 	//alert("json obj created and serialized");
	 	var footer ="<p class=\"footer\"> &copy;Zillow, Inc., 2006-2014. Use is subject to<a href=\"http://www.zillow.com/corp/Terms.htm\" > Terms of Use</a><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href=\"http://www.zillow.com/zestimate/\" > What's a Zestimate?</a></p>";
	 	var carousel_img_one= "";
	 	var carousel_img_five= "";
	 	var carousel_img_ten= "";

	 	$.ajax({
				type: "POST",
				dataType: "json",
				//url: "http://ask-cs571-assign8.elasticbeanstalk.com/zillow.php",
				url: "http://cs-server.usc.edu:24106/assign8/zillow.php",
				data: fields,
				success: function(data) {
								//alert("zpid: "+data["zpid"]);
								//zestimate_ten = "http://www.zillow.com/app?chartDuration=10years&chartType=partner&height=150&page=webservice%2FGetChart&service=chart&showPercent=true&width=300&zpid="+data["zpid"];
								//alert("ajax success");
								
								change_blank_fields(data);
								
                        						var zillow_table = "";
						                        var json_obj = data["json"];
						                        if(data["message"] == "ERROR"){
						                           // alert("RECORD NOT FOUND");
						                            	zillow_table = "<h3 id=\"error_tag\">Error: No exact match found!! - Verify that the given address is correct</h3>";
						                        	//alert("In success -- error part");
						                        	$("#wait_msg").empty();
						                        	$("#wait_msg").append(zillow_table);
						                        	$("#zillow_footer").empty();
							           		$("#zillow_footer").append(""+footer);
						                        	
						                        }else{ 
							                            var zillow_id = "";
							                            zestimate_ten = data["one_year"];
							                            var zillow_id = zestimate_ten.split("zpid=")[1];
							                           // alert("Zillow Id:#"+zillow_id+"#");
							                           if(data["one_year"]==""){
							                           	data["one_year"]="no_image.gif";
							                           }else{
							                           	data["one_year"] = "http://www.zillow.com/app?chartDuration=1year&chartType=partner&height=300&page=webservice%2FGetChart&service=chart&showPercent=true&width=600&zpid="+zillow_id;
							                           }

							                            if(data["five_year"]==""){
							                           	data["five_year"]="no_image.gif";
							                           }else{
							                           	data["five_year"] = "http://www.zillow.com/app?chartDuration=5years&chartType=partner&height=300&page=webservice%2FGetChart&service=chart&showPercent=true&width=600&zpid="+zillow_id;
							                            // alert("Link2"+data["five_year"]);
							                           }

							                            if(data["ten_year"]==""){
							                           	data["ten_year"]="image_not_available.jpg";
							                           }else{
							                           	data["ten_year"] = "http://www.zillow.com/app?chartDuration=10years&chartType=partner&height=300&page=webservice%2FGetChart&service=chart&showPercent=true&width=600&zpid="+zillow_id;
							                           }
							                            
							                            
							                             
							                             // alert("Link3"+data["ten_year"]);

							                            zillow_table = prepare_table(data);
							                           // alert("zillow table prepared");
							                           carousel_img_one= "<img src=\" "+  data["one_year"]  + "\" width=\"600\" height=\"300\" class=\"center-block\">";
							                             carousel_img_five= "<img src=\" "+  data["five_year"]  + "\" width=\"600\" height=\"300\" class=\"center-block\">";
							                               carousel_img_ten= "<img src=\" "+  data["ten_year"]  + "\" width=\"600\" height=\"300\" class=\"center-block\">";
							                           
							                           var caption_one = "<div class=\"carousel-caption\">"+
													"<h3 class=\"caption_h3\">Historical Zestimates for past 1 year</h3>"+
													"<p  class=\"caption_p\">"+  full_address +" </p>" +
												"</div>";
									 var caption_five = "<div class=\"carousel-caption\">"+
													"<h3 class=\"caption_h3\">Historical Zestimates for past 5 years</h3>"+
													"<p  class=\"caption_p\">"+  full_address +" </p>" +
												"</div>";
									 var caption_ten = "<div class=\"carousel-caption\">"+
													"<h3 class=\"caption_h3\">Historical Zestimates for past 10 years</h3>"+
													"<p  class=\"caption_p\">"+  full_address +" </p>" +
												"</div>";
							           		

							                            

							           		$('#carousel_item1').empty();$('#carousel_item2').empty();$('#carousel_item3').empty();

							           		$('#carousel_item1').append(carousel_img_one+caption_one);
							           		$('#carousel_item2').append(carousel_img_five+caption_five);
	   				 				$('#carousel_item3').append(carousel_img_ten+caption_ten);
							           		



							           		$("#basic-info").empty();
		   				 			$("#wait_msg").empty();
							                        $("#basic-info").append(zillow_table);
							                        $("#basic-info").css("visibility","visible");
							                        $('#zillowTab').css("visibility","visible");
							                        $('#zillowCarousel').css("visibility","visible");
							                        $('#zillowTabContent').css("visibility","visible");
							                      //  $('#myCarousel').append(footer);

							                        $('#myCarousel').css("visibility","visible");
							                        last_selling_cost = data["last_sold_price"];
							                        home_link = data["homedetails"];
							                        home_pic = data["one_year"];
							                        $("#zillow_footer").empty();
							           		$("#zillow_footer").append(""+footer);
							           	}
							           		//alert("here");
							           		

				    			},
                     			error: function(data) 	{
                     							zillow_table = "<h3 id=\"error_tag\">Error: No exact match found!! - Verify that the given address is correct</h3>";
						                        $("#wait_msg").empty();
						                        $("#wait_msg").append(zillow_table);

                    						 }
                		});
	}

	
	function func2(){
		alert("validation2 done!");
	}

	

	function func1(){
		//alert("validation1 done!");

		var zillow_url="<a href=\"http://www.google.com\">Google</a>";
		var fb_link = "<a href=\"#\" onclick=\"share_on_fb()\"><img src=\"share_facebook.png\" height=\"40\" width=\"170\"></a> ";
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
                                
                        	  //  alert("filling done!");

	}

	$(".zillow_form").submit(function(){
		func2();
	});


	(function(d, s, id) {
	  var js, fjs = d.getElementsByTagName(s)[0];
	  if (d.getElementById(id)) return;
	  js = d.createElement(s); js.id = id;
	  js.src = "//connect.facebook.net/en_US/all.js";
	  fjs.parentNode.insertBefore(js, fjs);
	}(document, 'script', 'facebook-jssdk'));

		// async init once loading is done
		window.fbAsyncInit = function() {
		  FB.init({appId: 1500065310257838, status : true, xfbml : true});
		};

	function share_on_fb() {
		var desc = "Last sold price: "+ last_selling_cost + ", 30 days overall change: $" + overall_change_30days;
		 FB.ui({
		    method: 'feed',
		    link: home_link, 
		    picture: home_pic,
		    name: full_address,
		    caption: 'Property Information from zillow.com',
		    description: desc
		  },
			function(response) 
			{
			         if (response && response.post_id) 
			         {
			          	 alert('property details posted on facebook.');
			         } 
			         else {
			         alert('Not posted');
			        }
		       }
		);
	} 

	

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
						            return $("#state").val() == '';

						}

						} 
					}
				},
				messages: {
					addr: "Address required",
					city: "City required",
					state: {
						required: "State required",
					},
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
	   				//alert("validation successfull");
	   				 $("#basic-info").empty();
	   				  $("#zillow_footer").empty();
	   				$('#zillowTab').css("visibility","hidden");
	   				$('#myCarousel').css("visibility","hidden");
	   				$('#zillowCarousel').css("visibility","hidden");
					$('#zillowTabContent').css("visibility","hidden");
	   				 $("#wait_msg").empty();
					$("#wait_msg").append("<h3> <b>Wait ..........<br> Information retrival from zillow is in progress</b></h3>");
	   				ajax_call();
	   				//func1();
  				}
			});
		func();
		
	});

