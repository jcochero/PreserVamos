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
$puntosfotos = $mysqli->real_escape_string($_GET["PuntosFotos"]);
$puntosevals = $mysqli->real_escape_string($_GET["PuntosEvals"]);
$puntostotal = $mysqli->real_escape_string($_GET["PuntosTotal"]);
$numriollanura = $mysqli->real_escape_string($_GET["numriollanura"]);
$numriomontana = $mysqli->real_escape_string($_GET["numriomontana"]);
$numlaguna = $mysqli->real_escape_string($_GET["numlaguna"]);
$numestuario = $mysqli->real_escape_string($_GET["numestuario"]);

$res=$mysqli->query("UPDATE usuarios SET puntosfotos = '$puntosfotos', puntosevals = '$puntosevals', puntostotales = '$puntostotal', numriollanura = '$numriollanura', numriomontana = '$numriomontana', numlaguna = '$numlaguna', numestuario = '$numestuario' WHERE user_id = '$userid'");
if (!$res) {
    print json_encode("Error");
    echo "<br />" . $mysql->error;
    exit;
}
else {
  print json_encode ("Puntos Cargados");
}

	
?>