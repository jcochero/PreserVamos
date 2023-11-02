<?php
require("connect.php");
$mysqli = new mysqli($host, $user, $pw, $db);
$mysqli->query("SET CHARACTER SET utf8");
$mysqli->query("SET NAMES 'utf8'");
if ($mysqli->connect_error){
    die("Connection failed: " . $mysqli->connect_error);
}
//get useremail
$useremail = $mysqli->real_escape_string($_GET["useremail"]);
if ($useremail == ""){
	print json_encode("Error");
	echo "<br />" . $mysqli->error;
	exit;
}

// SQL queries to retrieve specific fields from Table 1 and Table 2
$query1 = "SELECT lat,lng,indice,tiporio,dateandtime,partido FROM markers WHERE useremail= '$useremail'";
$query2 = "SELECT decimalLatitude,decimalLongitude,tiporio,georeferencedDate,partido FROM evals_residuos WHERE useremail= '$useremail'";
$query3 = "SELECT decimalLatitude,decimalLongitude,tiporio,georeferencedDate,partido FROM evals_hidro WHERE useremail= '$useremail'";

// Execute the queries
$result1 = mysqli_query($mysqli, $query1);
$result2 = mysqli_query($mysqli, $query2);
$result3 = mysqli_query($mysqli, $query3);

// Check if any rows are returned for Table 1
$table1Count = 0;
$table1Data = array();
if ($result1) {
    $table1Count = mysqli_num_rows($result1);
    while ($row = mysqli_fetch_assoc($result1)) {
       $table1Data[] = $row;
		//$arr = array('lat' => $row["lat"], 'lng' => $row["lng"], 'indice' => $row["indice"], 'tiporio' => $row["tiporio"], 'dateandtime' => $row["dateandtime"], 'partido' => $row["partido"]);
		//print json_encode($arr);
    }
}


// Check if any rows are returned for Table 2
$table2Count = 0;
$table2Data = array();
if ($result2) {
    $table2Count = mysqli_num_rows($result2);
    while ($row = mysqli_fetch_assoc($result2)) {
        $table2Data[] = $row;
    }
}

// Check if any rows are returned for Table 3
$table3Count = 0;
$table3Data = array();
if ($result3) {
    $table3Count = mysqli_num_rows($result3);
    while ($row = mysqli_fetch_assoc($result3)) {
        $table3Data[] = $row;
    }
}

// Combine the result counts
$totalCount = $table1Count + $table2Count + $table3Count;

if ($totalCount > 0){
	print json_encode("GetPuntajeOk");
} else {print json_encode("Not Found");}

// Add the result count to the final data array
print json_encode($table1Data);
print json_encode($table2Data);
print json_encode($table3Data);


// Close the result sets
mysqli_free_result($result1);
mysqli_free_result($result2);
mysqli_free_result($result3);

// Close the database connection
mysqli_close($mysqli);

?>				