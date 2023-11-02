<?php
require("connectmapa.php");
$mysqli = new mysqli($host, $user, $pw, $db);
$mysqli->query("SET CHARACTER SET utf8");
$mysqli->query("SET NAMES 'utf8'");
if ($mysqli->connect_error){
    die("Connection failed: " . $mysqli->connect_error);
}

$username = $mysqli->real_escape_string($_GET["username"]);
$useremail = $mysqli->real_escape_string($_GET["useremail"]);
$dateandtime = $mysqli->real_escape_string($_GET["dateandtime"]);
$nombresitio = $mysqli->real_escape_string($_GET["nombresitio"]);
$tiporio = $mysqli->real_escape_string($_GET["tiporio"]);
$lat = $mysqli->real_escape_string($_GET["lat"]);
$lng = $mysqli->real_escape_string($_GET["lng"]);
$indice = $mysqli->real_escape_string($_GET["indice"]);
$precision = $mysqli->real_escape_string($_GET["valorind20"]);
$valorind1 = $mysqli->real_escape_string($_GET["valorind1"]);
$valorind2 = $mysqli->real_escape_string($_GET["valorind2"]);
$valorind3 = $mysqli->real_escape_string($_GET["valorind3"]);
$valorind4 = $mysqli->real_escape_string($_GET["valorind4"]);
$valorind5 = $mysqli->real_escape_string($_GET["valorind5"]);
$valorind6 = $mysqli->real_escape_string($_GET["valorind6"]);
$valorind7 = $mysqli->real_escape_string($_GET["valorind7"]);
$valorind8 = $mysqli->real_escape_string($_GET["valorind8"]);
$valorind9 = $mysqli->real_escape_string($_GET["valorind9"]);
$valorind10 = $mysqli->real_escape_string($_GET["valorind10"]);
$valorind11 = $mysqli->real_escape_string($_GET["valorind11"]);
$valorind12 = $mysqli->real_escape_string($_GET["valorind12"]);
$valorind13 = $mysqli->real_escape_string($_GET["valorind13"]);
$valorind14 = $mysqli->real_escape_string($_GET["valorind14"]);
$valorind15 = $mysqli->real_escape_string($_GET["valorind15"]);
$valorind16 = $mysqli->real_escape_string($_GET["valorind16"]);
$valorind17 = $mysqli->real_escape_string($_GET["valorind17"]);
$valorind18 = $mysqli->real_escape_string($_GET["valorind18"]);
$valorind19 = $mysqli->real_escape_string($_GET["valorind19"]);
$foto1path = $mysqli->real_escape_string($_GET["foto1path"]);
$terminado = $mysqli->real_escape_string($_GET["terminado"]);
$privado = $mysqli->real_escape_string($_GET["privado"]);
$verificado = $mysqli->real_escape_string($_GET["verificado"]);
$bingo = $mysqli->real_escape_string($_GET["bingo"]);
$notas = $mysqli->real_escape_string($_GET["notas"]);
$mapadetect = $mysqli->real_escape_string($_GET["mapadetect"]);
$wifidetect = $mysqli->real_escape_string($_GET["wifidetect"]);
$gpsdetect = $mysqli->real_escape_string($_GET["gpsdetect"]);
$deviceID = $mysqli->real_escape_string($_GET["deviceID"]);
$partido = $mysqli->real_escape_string($_GET["partido"]);

$res = $mysqli->query("INSERT INTO evals_hidro (username, useremail, georeferencedDate, nombresitio, tiporio, decimalLatitude, decimalLongitude, valorind1, valorind2, valorind3, valorind4, valorind5, valorind6, valorind7, valorind8, valorind9, valorind10, valorind11, valorind12, valorind13, valorind14, valorind15, valorind16,  valorind17, valorind18, valorind19, foto1, foto2, foto3, foto4, foto5, terminado, privado, validacion, notas, mapadetect, wifidetect, gpsdetect, deviceID, partido) VALUES ('$username','$useremail', '$dateandtime', '$nombresitio', '$tiporio', '$lat', '$lng', '$valorind1', '$valorind2', '$valorind3', '$valorind4', '$valorind5', '$valorind6', '$valorind7', '$valorind8', '$valorind9', '$valorind10', '$valorind11', '$valorind12', '$valorind13', '$valorind14', '$valorind15', '$valorind16','$valorind17','$valorind18','$valorind19','$foto1path', '$foto2path', '$foto3path', '$foto4path','$foto5path','$terminado','$privado', '$verificado', '$notas', '$mapadetect', '$wifidetect', '$gpsdetect', '$deviceID', '$partido')");

if (!$res) {
    print json_encode("Error");
     echo "<br />" . $mysqli->error;
    exit;
}
else {
  print json_encode ("Mod_Dato_Agregado");

  $res = $mysqli->query("SELECT * FROM evals_hidro ORDER BY id DESC LIMIT 1 ");
	while($row = mysqli_fetch_array($res)) {
		$arr = array('serverId' => $row["id"]);
        print json_encode($arr);
	}    
 
}

?>