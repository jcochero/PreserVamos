<?php
require("connect.php");

$mysqli = new mysqli($host, $user, $pw, $db);
$mysqli->query("SET CHARACTER SET utf8");
$mysqli->query("SET NAMES 'utf8'");
if ($mysqli->connect_error){
    die("Connection failed: " . $mysqli->connect_error);
}

$action = $_GET["Action"];

$userid = $mysqli->real_escape_string($_GET["UserID"]);
$numshares = $mysqli->real_escape_string($_GET["NumShares"]);
$puntostotal = $mysqli->real_escape_string($_GET["PuntosTotal"]);


$res=$mysqli->query("UPDATE usuarios SET puntostotales = '$puntostotal', numshares = '$numshares' WHERE user_id = '$userid'");
if (!$res) {
    print json_encode("Error");
    echo "<br />" . $mysql->error;
    exit;
}
else {
  print json_encode ("SharesOK");
}

	
?>