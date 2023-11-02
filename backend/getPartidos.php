<?php
require("connectmapa.php");

$mysqli = new mysqli($host, $user, $pw, $db);
$mysqli->query("SET CHARACTER SET utf8");
$mysqli->query("SET NAMES 'utf8'");
if ($mysqli->connect_error){
    die("Connection failed: " . $mysqli->connect_error);
}

$nombre_partido = $mysqli->real_escape_string($_GET["nombre_partido"]);
$res = $mysqli->query("SELECT * FROM partidos WHERE nombre_partido ='$nombre_partido'");

if (!$res) {
    print json_encode("Error");
    echo "<br />" . $mysqli->error();
    exit;
} else {
    if (mysqli_num_rows($res) == 0) {
        print json_encode("Not Found");
        //exit;
	} else {
		print json_encode("Partido OK");
		//Devuelve los datos básicos del municipio, de "partidos"
		while($row = mysqli_fetch_array($res)) {
			$arr = array('provincia_partido' => $row["provincia_partido"], 'texto_principal' => $row["texto_principal"], 'texto_secundario' => $row["texto_secundario"], 'link1' => $row["link1"], 'link2' => $row["link2"], 'link3' => $row["link3"], 'link4' => $row["link4"], 'email' => $row["email"], 'logo_url' => $row["logo_url"], 'puntos_totales' => $row["puntos_totales"], 'puntos_validos' => $row["puntos_validos"], 'fecha_inicio' => $row["fecha_inicio"]);
        print json_encode($arr);
	}
	}
}

//Busca los reportes de ese municipio
$res2 = $mysqli->query("SELECT * FROM markers WHERE partido ='$nombre_partido'");
if (!$res2) {
    print json_encode("Error");
    echo "<br />" . $mysqli->error();
    exit;
} else {
    if (mysqli_num_rows($res2) == 0) {
        print json_encode("Sin reportes");
        exit;
	}
	print json_encode("Con reportes");
	$rowcount = mysqli_num_rows($res2);
	print json_encode($rowcount);	
	//Devuelve los marcadores del municipio
	while($row = mysqli_fetch_array($res2)) {
		$arr = array('nombresitio' => $row["nombresitio"], 'lat' => $row["lat"], 'lng' => $row["lng"], 'indice' => $row["indice"], 'valorind1' => $row["valorind1"],'valorind2' => $row["valorind2"],'valorind3' => $row["valorind3"],'valorind4' => $row["valorind4"],'valorind5' => $row["valorind5"],'valorind6' => $row["valorind6"],'valorind7' => $row["valorind7"],'valorind8' => $row["valorind8"],'valorind9' => $row["valorind9"],'valorind10' => $row["valorind10"],'ind_pvm_1' => $row["ind_pvm_1"],'ind_pvm_2' => $row["ind_pvm_2"],'ind_pvm_3' => $row["ind_pvm_3"],'ind_pvm_4' => $row["ind_pvm_4"],'ind_pvm_5' => $row["ind_pvm_5"],'ind_pvm_6' => $row["ind_pvm_6"],'ind_pvm_7' => $row["ind_pvm_7"],'ind_pvm_8' => $row["ind_pvm_8"], 'tiporio' => $row["tiporio"], 'username' => $row["username"], 'useremail' => $row["useremail"]);
        print json_encode($arr);
	}
}
mysqli_close($mysqli);

?>


