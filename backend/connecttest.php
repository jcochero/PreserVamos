<?php
require("connect.php");

$mysqli = new mysqli($host, $user, $pw, $db);
$mysqli->query("SET CHARACTER SET utf8");
$mysqli->query("SET NAMES 'utf8'");
if ($mysqli->connect_error){
    die("Connection failed: " . $mysqli->connect_error);
}


$res = $mysqli->query("SELECT nombre_db, currentversion, newstitulo, newstext FROM appconfig");

if (!$res) {
    print json_encode("Error");
    echo "<br />" . $mysqli->error;
    exit;
}
else {

    print json_encode("Connected"); 

	while($row = mysqli_fetch_array($res)) {
	$arr = array('nombre_db' => $row["nombre_db"], 'currentversion' => $row["currentversion"], 'newstitulo' => $row["newstitulo"], 'newstext' => $row["newstext"]);
    print json_encode($arr);
	}  
	
}
mysqli_close($mysqli);
?>