<?php
require("connectmapa.php");

$mysqli = new mysqli($host, $user, $pw, $db);
$mysqli->query("SET CHARACTER SET utf8");
$mysqli->query("SET NAMES 'utf8'");
if ($mysqli->connect_error){
    die("Connection failed: " . $mysqli->connect_error);
}
$res = $mysqli->query("SELECT * FROM markers");

if (!$res) {
    print json_encode("Error");
    echo "<br />" . $mysqli->error();
    exit;
}
else {
    if (mysqli_num_rows($res) == 0) {
        print json_encode("Not Found");
        exit;
	}
	print json_encode("GetMapaOk");
	$rowcount = mysqli_num_rows($res);
	print json_encode($rowcount);	
	while($row = mysqli_fetch_array($res)) {
		$arr = array('nombresitio' => $row["nombresitio"], 'lat' => $row["lat"], 'lng' => $row["lng"], 'indice' => $row["indice"], 'tiporio' => $row["tiporio"], 'username' => $row["username"]);
        print json_encode($arr);
	}
}
?>