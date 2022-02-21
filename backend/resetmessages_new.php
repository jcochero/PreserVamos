<?php
require("connect.php");

$mysqli = new mysqli($host, $user, $pw, $db);
$mysqli->query("SET CHARACTER SET utf8");
$mysqli->query("SET NAMES 'utf8'");
if ($mysqli->connect_error){
    die("Connection failed: " . $mysqli->connect_error);
}

$markerID = $mysqli->real_escape_string($_GET["markerID"]);
$res = $mysqli->query("UPDATE mensajes SET leido= '1' WHERE markerID = '$markerID'");

if (!$res) {
    print json_encode("Error");
    echo "<br />" . $mysql->error;
    exit;
}
else {
  print json_encode ("ResetMessages");
}	
?>