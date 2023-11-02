<?php
require("connectAlerta.php");
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
$tipo_alerta = $mysqli->real_escape_string($_GET["tipo_alerta"]);
$lat = $mysqli->real_escape_string($_GET["lat"]);
$lng = $mysqli->real_escape_string($_GET["lng"]);
$foto1path = $mysqli->real_escape_string($_GET["foto1path"]);
$foto2path = $mysqli->real_escape_string($_GET["foto2path"]);
$foto3path = $mysqli->real_escape_string($_GET["foto3path"]);
$foto4path = $mysqli->real_escape_string($_GET["foto4path"]);
$terminado = $mysqli->real_escape_string($_GET["terminado"]);
$privado = $mysqli->real_escape_string($_GET["privado"]);
$verificado = $mysqli->real_escape_string($_GET["verificado"]);
$notas = $mysqli->real_escape_string($_GET["notas"]);
$mapadetect = $mysqli->real_escape_string($_GET["mapadetect"]);
$wifidetect = $mysqli->real_escape_string($_GET["wifidetect"]);
$gpsdetect = $mysqli->real_escape_string($_GET["gpsdetect"]);
$deviceID = $mysqli->real_escape_string($_GET["deviceID"]);
$partido = $mysqli->real_escape_string($_GET["partido"]);
$telefono = $mysqli->real_escape_string($_GET["telefono"]);


if ($lat == "0.000000" || $lat == "" ||  is_null($lat)) {
	print json_encode("LatLong Error");
	exit;
}

//Busca el contacto del municipio
$res = $mysqli->query("SELECT * FROM alertas_admins WHERE partido= '$partido'");
if (!$res) {
    print json_encode("Error");
    echo "<br />" . $mysqli->error;
    exit;
}else {
    if (mysqli_num_rows($res) == 0) {
       //no encuentra, usa catchall (seteada en connectAlerta.php)
        $to = $catchall;
	} else {
		//encuentra, usa ese email 
		 while($row = mysqli_fetch_array($res)) {
			$to = $row["email"];
		}
	}
}


//Agrega el dato
$res1 = $mysqli->query("INSERT INTO alertas (username, useremail, dateandtime, tiporio, tipo_alerta, lat, lng, foto1path, foto2path, foto3path, foto4path, terminado, privado, verificado, notas, mapadetect, wifidetect, gpsdetect, deviceID, partido, telefono) VALUES ('$username', '$useremail', '$dateandtime', '$tiporio', '$tipo_alerta', '$lat', '$lng', '$foto1path', '$foto2path', '$foto3path', '$foto4path', '$terminado','$privado', '$verificado', '$notas', '$mapadetect', '$wifidetect', '$gpsdetect', '$deviceID', '$partido', '$telefono')");

if (!$res1) {
    print json_encode("Error");
     echo "<br />" . $mysqli->error;
    exit;
}
else {
  print json_encode ("AlertaAgregada");

}
mysqli_close($mysqli);



///Email sending

$subject = "PreserVamos - Alerta reportada!";
$message = "<b>Un usuario ha enviado un alerta!</b>";
if ($tipo_alerta == "derrame"){
	$message .= "<h1>Derrame industrial</h1>";	
} elseif ($tipo_alerta == "floracion") {
	$message .= "<h1>Floración de algas</h1>";	
} elseif ($tipo_alerta == "peces") {
	$message .= "<h1>Mortandad de peces</h1>";	
} elseif ($tipo_alerta == "otra") {
	$message .= "<h1>Otro tipo:</h1>";	
}
$message .= "<br></br>";
$message .= "Notas del usuario: ";
$message .= $notas;
$message .= "<br></br>";
$message .= "Teléfono de contacto: ";
$message .= $telefono;
$message .= "<br></br>";
$message .= "Fotos: ";
if ($foto1path<> ""){
	$message .= "<a href=https://preservamos.ar/alertas/collected_photos_alert/" . $foto1path . ".jpg>Foto 1</a> ";	
}
if ($foto2path<> ""){
	$message .= "<a href=https://preservamos.ar/alertas/collected_photos_alert/" . $foto2path . ".jpg>Foto 2</a> ";	
}
if ($foto3path<> ""){
	$message .= "<a href=https://preservamos.ar/alertas/collected_photos_alert/" . $foto3path . ".jpg>Foto 3</a> ";	
}
if ($foto4path<> ""){
	$message .= "<a href=https://preservamos.ar/alertas/collected_photos_alert/" . $foto4path . ".jpg>Foto 4</a> ";	
}
$header = "From:hola@preservamos.ar \r\n";
$header .= "Bcc:" . $catchall . "\r\n";
$header .= "MIME-Version: 1.0\r\n";
$header .= "Content-type: text/html; charset=utf-8 \r\n";
$retval = mail ($to,$subject,$message,$header);
    if( $retval == true ) {
       echo "Alerta enviada...";
    }else {
        echo "Alerta no enviada...";
    }
?>