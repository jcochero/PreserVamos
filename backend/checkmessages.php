<?php
require("connect.php");

$mysqli = new mysqli($host, $user, $pw, $db);
$mysqli->query("SET CHARACTER SET utf8");
$mysqli->query("SET NAMES 'utf8'");
if ($mysqli->connect_error){
    die("Connection failed: " . $mysqli->connect_error);
}

$deviceID = $mysqli->real_escape_string($_GET["deviceID"]);
$res = $mysqli->query("SELECT * FROM mensajes WHERE deviceID = '$deviceID' AND leido = '0'");

if (!$res) {
    print json_encode("Error");
    echo "<br />" . $mysqli->error;
    exit;
}
else {
    if (mysqli_num_rows($res) == 0) {
        print json_encode("Not Found");
        exit;
	}
	print json_encode("MensajesOK");
	$rowcount = mysqli_num_rows($res);
	print json_encode($rowcount);	
	while($row = mysqli_fetch_array($res)) {
		$arr = array('mensaje_type' => $row["mensaje_type"], 'mensaje' => $row["mensaje"], 'markerID' => $row["markerID"]);
        print json_encode($arr);
	}  
}
mysqli_close($mysqli);
?>