<?php
require("connectmapa.php");
$mysqli = new mysqli($host, $user, $pw, $db);
$mysqli->query("SET CHARACTER SET utf8");
$mysqli->query("SET NAMES 'utf8'");
if ($mysqli->connect_error){
    die("Connection failed: " . $mysqli->connect_error);
}

$username = $mysqli->real_escape_string($_GET["username"]);
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
$valorind20 = $mysqli->real_escape_string($_GET["valorind20"]);
$ind_pvm_1 = $mysqli->real_escape_string($_GET["ind_pvm_1"]);
$ind_pvm_2 = $mysqli->real_escape_string($_GET["ind_pvm_2"]);
$ind_pvm_3 = $mysqli->real_escape_string($_GET["ind_pvm_3"]);
$ind_pvm_4 = $mysqli->real_escape_string($_GET["ind_pvm_4"]);
$ind_pvm_5 = $mysqli->real_escape_string($_GET["ind_pvm_5"]);
$ind_pvm_6 = $mysqli->real_escape_string($_GET["ind_pvm_6"]);
$ind_pvm_7 = $mysqli->real_escape_string($_GET["ind_pvm_7"]);
$ind_pvm_8 = $mysqli->real_escape_string($_GET["ind_pvm_8"]);
$ind_pvm_9 = $mysqli->real_escape_string($_GET["ind_pvm_9"]);
$ind_pvm_10 = $mysqli->real_escape_string($_GET["ind_pvm_10"]);
$ind_pvm_11 = $mysqli->real_escape_string($_GET["ind_pvm_11"]);
$ind_pvm_12 = $mysqli->real_escape_string($_GET["ind_pvm_12"]);
$ind_pvm_13 = $mysqli->real_escape_string($_GET["ind_pvm_13"]);
$foto1path = $mysqli->real_escape_string($_GET["foto1path"]);
$foto2path = $mysqli->real_escape_string($_GET["foto2path"]);
$foto3path = $mysqli->real_escape_string($_GET["foto3path"]);
$foto4path = $mysqli->real_escape_string($_GET["foto4path"]);
$foto5path = $mysqli->real_escape_string($_GET["foto5path"]);
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

$res = $mysqli->query("INSERT INTO markers (username, dateandtime, nombresitio, tiporio, lat, lng, indice, valprecision, valorind1, valorind2, valorind3, valorind4, valorind5, valorind6, valorind7, valorind8, valorind9, valorind10, valorind11, valorind12, valorind13, valorind14, valorind15, valorind16,  valorind17, valorind18, valorind19, valorind20, ind_pvm_1,ind_pvm_2, ind_pvm_3, ind_pvm_4, ind_pvm_5, ind_pvm_6, ind_pvm_7, ind_pvm_8, ind_pvm_9, ind_pvm_10, ind_pvm_11, ind_pvm_12, ind_pvm_13, foto1path, foto2path, foto3path, foto4path, foto5path, terminado, privado, verificado, bingo, notas, mapadetect, wifidetect, gpsdetect, deviceID, partido) VALUES ('$username', '$dateandtime', '$nombresitio', '$tiporio', '$lat', '$lng', '$indice', '$precision', '$valorind1', '$valorind2', '$valorind3', '$valorind4', '$valorind5', '$valorind6', '$valorind7', '$valorind8', '$valorind9', '$valorind10', '$valorind11', '$valorind12', '$valorind13', '$valorind14', '$valorind15', '$valorind16','$valorind17','$valorind18','$valorind19','$valorind20','$ind_pvm_1','$ind_pvm_2','$ind_pvm_3','$ind_pvm_4','$ind_pvm_5','$ind_pvm_6','$ind_pvm_7','$ind_pvm_8','$ind_pvm_9','$ind_pvm_10','$ind_pvm_11','$ind_pvm_12','$ind_pvm_13','$foto1path', '$foto2path', '$foto3path', '$foto4path','$foto5path','$terminado','$privado', '$verificado', '$bingo', '$notas', '$mapadetect', '$wifidetect', '$gpsdetect', '$deviceID', '$partido')");

if (!$res) {
    print json_encode("Error");
     echo "<br />" . $mysqli->error;
    exit;
}
else {
  print json_encode ("MarcadorAgregado");

  $res = $mysqli->query("SELECT * FROM markers ORDER BY id DESC LIMIT 1 ");
	while($row = mysqli_fetch_array($res)) {
		$arr = array('serverId' => $row["id"]);
        print json_encode($arr);
	}    
 
}

?>