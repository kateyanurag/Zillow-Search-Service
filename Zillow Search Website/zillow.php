<?php
header('Access-Control-Allow-Origin: *');
header('Access-Control-Allow-Methods: GET, POST');
if(isset($_GET["addr"]) && isset($_GET["city"]) && isset($_GET["state"]))
{
    parse_zillow_xml("GET");
}

if (isset($_POST["request"]) && !empty($_POST["request"])) 
{ 
	$action = $_POST["request"];
	switch($action) {
		case "zillow": parse_zillow_xml("POST"); break;
	}
}

function format_arg($arg){
    $arr= "";
    $deci_part = "";
    $int_part = $arg;
    if(strpos($arg, '.') !== FALSE){
            $int_part = substr($arg, 0, strpos($arg, '.'));
            $deci_part = substr($arg, strpos($arg, '.')+1);        
    }
    $len = strlen($int_part);
    if($len > 3){
        $arg = substr($int_part, 0, $len-3) . "," . substr($int_part, $len-3, $len-1);
    }
    if($deci_part !== ""){
        if(strlen($deci_part)==1){
             $arg = $arg . "." . $deci_part . "0";
        }else{
             $arg = $arg . substr($deci_part, 0, 1);
        }
    }else{
        $arg = $arg . "." . "00";
    }
    return $arg;
}
function get_date($date){
    $month = "";
    $dt = "";
    if($date != NULL){
        $datearr = explode("/", $date);
        $month = $datearr[0];
        switch($month){
            case "01": $month = "Jan"; break;
            case "02": $month = "Feb"; break;
            case "03": $month = "Mar"; break;
            case "04": $month = "Apr"; break;
            case "05": $month = "May"; break;
            case "06": $month = "Jn"; break;
            case "07": $month = "Jul"; break;
            case "08": $month = "Aug"; break;
            case "09": $month = "Sept"; break;
            case "10": $month = "Oct"; break;
            case "11": $month = "Nov"; break;
            case "12": $month = "Dec";
        }
        $dt = $datearr[1]."-".$month."-".$datearr[2];
    }
    
    return $dt;
}

function parse_zillow_xml($arg){

    $retval = array ('message' => "N/A",
                     'homedetails' => "N/A", 
                     'zpid' =>  "N/A", 
                     'street' =>  "N/A", 
                     'zipcode' => "N/A", 
                     'city' => "N/A", 
                     'state' =>  "N/A", 
                     'property_type' =>  "N/A", 
                     'year_built' =>  "N/A", 
                     'lot_size' => "",
                     'finished_area' => "",
                     'bathrooms' => "",
                     'bedrooms' => "",
                     'taxAssessmentYear' => "",
                     'taxAssessment' => "",
                     'last_sold_price' => "",
                     'last_sold_date' => "",
                     
                     'property_estimate' => "",
                     'property_estimate_date' => "",
                     'overall_change' => "",
                     'property_range' => "",
                     'rent_estimate' => "",
                     'rent_estimate_date' => "",
                     'rent_change' => "",
                     'rent_range' => "",
                     'graphs_available' => "",
                     'one_year' => "",
                     'five_year' => "",
                     'ten_year' => ""
                    );

    $fields = "";
    $address = "";
    $city = "";
    $state = "";
    if($arg=="POST"){
	$fields = $_POST;
                $address = $fields["addr"];
              $city = $fields["city"];
                $state = $fields["state"];
    }else{
        $address = $_GET["addr"];
        $city = $_GET["city"];
        $state = $_GET["state"];
    }
	
    
    $zillow_xml_url = "http://www.zillow.com/webservice/GetDeepSearchResults.htm?zws-id=X1-ZWz1b2pix9nnrf_3mmvk"."&address=".$address."&citystatezip=".$city."%2C+".$state."&rentzestimate=true";
	
    $xml = simplexml_load_file($zillow_xml_url);
    $count = 0;
    $msg_value = "";
	foreach ($xml->message as $x){
        $count += 1;
        $msg_value =$x -> text;
        if(strpos($msg_value,'Error') !== FALSE){
            $retval["message"] = "ERROR";
            $retval["json"] = json_encode($retval);
	        echo json_encode($retval);
            return;
        }elseif (strpos($msg_value,'Request successfully processed') !== FALSE){
             $retval["message"] = "OK";
        }else{
             $retval["message"] = "Some Different error occured. Request cannot be proceeded";
        }
    }
    $result = $xml->response->results->result;
    $z_pid = "".$xml->response->results->result->zpid."";
    $retval["zpid"] = $z_pid;
    $retval["street"] = $result->address->street.", ";
    $retval["zipcode"] = "-".$result->address->zipcode."";
    $retval["city"] = $result->address->city.", ";
    $retval["state"] = $result->address->state."";
    
    $retval["homedetails"] = $result->links->homedetails."";
    $retval["property_type"] =$result->useCode."";
    $retval["year_built"] = $result->yearBuilt."";
    $var = $result->lotSizeSqFt."";
    if($result->lotSizeSqFt."" == "")
        $var = "0";
    $retval["lot_size"] = $var." sq. ft.";
    $var = $result->finishedSqFt."";
    if($result->lotSizeSqFt."" == "")
        $var = "0";
    $retval["finished_area"] = $var." sq. ft.";
    $retval["bathrooms"] = $result->bathrooms."";
    $retval["bedrooms"] = $result->bedrooms."";
    $retval["taxAssessmentYear"] = $result->taxAssessmentYear."";
    $retval ["taxAssessment"] = "$".format_arg($result->taxAssessment."");
    $retval["last_sold_price"] = "$".format_arg($result->lastSoldPrice."");
    $retval["last_sold_date"] = get_date($result->lastSoldDate."");
    
    

    $retval["property_estimate"] = "$".format_arg($result->zestimate->amount."");
    $r = $result->zestimate;
    $retval["property_estimate_date"] = get_date($r->{'last-updated'}."");
    $retval["overall_change"] = format_arg($result->zestimate->valueChange.""); 
    $low = "$".format_arg($result->zestimate->valuationRange->low."");
    $high = "$".format_arg($result->zestimate->valuationRange->high."");
    $retval["property_range"] = $low . " - " .$high; 
    $retval["rent_estimate"] = "$".format_arg($result->rentzestimate->amount."");  
    $retval["rent_estimate_date"] = get_date($result->rentzestimate->{'last-updated'}."");  
    $retval["rent_change"] = format_arg($result->rentzestimate->valueChange.""); 
    $retval["rent_range"] =  "$" . format_arg($result->rentzestimate->valuationRange->low."") . " - $" . format_arg($result->rentzestimate->valuationRange->high."");  
    

    $zillow_graph_url_one = "http://www.zillow.com/webservice/GetChart.htm?zws-id=X1-ZWz1b2pix9nnrf_3mmvk&unit-type=percent&zpid=".$z_pid."&width=600&height=300&chartDuration=1year";
    $graph_xml_one = simplexml_load_file($zillow_graph_url_one);
    $retval["one_year"]= $graph_xml_one->response->url."";
    
    $zillow_graph_url_five = "http://www.zillow.com/webservice/GetChart.htm?zws-id=X1-ZWz1b2pix9nnrf_3mmvk&unit-type=percent&zpid=".$z_pid."&chartDuration=5years&width=600&height=300";
    $graph_xml_five = simplexml_load_file($zillow_graph_url_five);
    $retval["five_year"]= $graph_xml_five->response->url."";
    
    $zillow_graph_url_ten = "http://www.zillow.com/webservice/GetChart.htm?zws-id=X1-ZWz1b2pix9nnrf_3mmvk&unit-type=percent&zpid=".$z_pid."&chartDuration=10years&width=600&height=300";
    $graph_xml_ten = simplexml_load_file($zillow_graph_url_ten);
    $retval["ten_year"]= $graph_xml_ten->response->url."";



	$retval["json"] = json_encode($retval);
	echo json_encode($retval);
}
?>
